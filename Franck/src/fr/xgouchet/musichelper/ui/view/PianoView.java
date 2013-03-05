package fr.xgouchet.musichelper.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import fr.xgouchet.musichelper.R;
import fr.xgouchet.musichelper.model.Accidental;
import fr.xgouchet.musichelper.model.Chord;
import fr.xgouchet.musichelper.model.Note;

public class PianoView extends View {

	private static final int[] BLACK_KEYS_OFFSET = new int[] { 0, 1, 3, 4, 5 };

	private static Path sPathC, sPathD, sPathE, sPathBlack;

	/**
	 * Simple constructor to use when creating a view from code.
	 * 
	 * @param context
	 *            The Context the view is running in, through which it can
	 *            access the current theme, resources, etc.
	 */
	public PianoView(final Context context) {
		super(context);

		initPianoView();
	}

	/**
	 * Constructor that is called when inflating a view from XML. This is called
	 * when a view is being constructed from an XML file, supplying attributes
	 * that were specified in the XML file. This version uses a default style of
	 * 0, so the only attribute values applied are those in the Context's Theme
	 * and the given AttributeSet.
	 * 
	 * The method onFinishInflate() will be called after all children have been
	 * added.
	 * 
	 * @param context
	 *            The Context the view is running in, through which it can
	 *            access the current theme, resources, etc.
	 * @param attrsThe
	 *            attributes of the XML tag that is inflating the view.
	 * @see ShapeButton#ShapeButton(Context, AttributeSet, int)
	 */
	public PianoView(final Context context, final AttributeSet attrs) {
		super(context, attrs);

		initPianoView();
		readPianoViewAttributes(attrs);
	}

	/**
	 * Perform inflation from XML and apply a class-specific base style. This
	 * constructor of View allows subclasses to use their own base style when
	 * they are inflating. For example, a Button class's constructor would call
	 * this version of the super class constructor and supply R.attr.buttonStyle
	 * for defStyle; this allows the theme's button style to modify all of the
	 * base view attributes (in particular its background) as well as the Button
	 * class's attributes.
	 * 
	 * @param context
	 *            The Context the view is running in, through which it can
	 *            access the current theme, resources, etc.
	 * @param attrs
	 *            The attributes of the XML tag that is inflating the view.
	 * @param defStyle
	 *            The default style to apply to this view. If 0, no style will
	 *            be applied (beyond what is included in the theme). This may
	 *            either be an attribute resource, whose value will be retrieved
	 *            from the current theme, or an explicit style resource.
	 * @see #ShapeButton(Context, AttributeSet)
	 */
	public PianoView(final Context context, final AttributeSet attrs,
			final int defStyle) {
		super(context, attrs, defStyle);

		initPianoView();
		readPianoViewAttributes(attrs);
	}

	/**
	 * @see android.view.View#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(final int widthMeasureSpec,
			final int heightMeasureSpec) {

		final int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
		final int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
		final int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
		final int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

		// Compute needed width
		int neededWidth = 0, neededHeight = 0;

		// piano needed width / height
		neededWidth = (int) (((mMaxOffset - mMinOffset) + 4) * mKeyWidth);
		neededHeight = (int) (mKeyHeight);

		neededWidth += getPaddingLeft() + getPaddingRight();
		neededHeight += getPaddingTop() + getPaddingBottom();

		// Adapt width to constraints
		switch (widthSpecMode) {
		case MeasureSpec.EXACTLY:
			neededWidth = widthSpecSize;
			break;
		case MeasureSpec.AT_MOST:
			neededWidth = Math.min(widthSpecSize, neededWidth);
			break;
		case MeasureSpec.UNSPECIFIED:
		default:
			break;
		}

		// Adapt height to constraints
		switch (heightSpecMode) {
		case MeasureSpec.EXACTLY:
			neededHeight = heightSpecSize;
			break;
		case MeasureSpec.AT_MOST:
			neededHeight = Math.min(heightSpecSize, neededHeight);
			break;
		case MeasureSpec.UNSPECIFIED:
		default:
			break;
		}

		setMeasuredDimension(neededWidth, neededHeight);
	}

	/**
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(final Canvas canvas) {
		if (mChord == null) {
			return;
		}

		canvas.clipRect(getPaddingLeft(), getPaddingTop(), getWidth()
				- getPaddingRight(), getHeight() - getPaddingBottom());

		int keysCount = (int) (getWidth() / mKeyWidth);
		float decal = (getWidth() - getPaddingLeft() - getPaddingRight() - (keysCount * mKeyWidth)) / 2f;
		float x, y;

		x = getPaddingLeft() + decal;
		y = getPaddingRight();

		int midPos = ((keysCount - 1) / 2);
		int startXPos = ((midPos - mMediumOffset) % 7) - 7;

		int minPos = midPos - (mMediumOffset - mMinOffset);
		int maxPos = midPos + (mMaxOffset - mMediumOffset);
		int offset;

		for (int xPos = startXPos; xPos < (keysCount + 7); xPos += 7) {
			x = getPaddingLeft() + decal + (xPos * mKeyWidth);

			drawOctave(canvas, x, y);

			if ((xPos <= maxPos) && ((xPos + 7) > minPos)) {
				offset = (xPos - midPos) + mMediumOffset;
				drawHighlights(canvas, offset, x, y);
			}

		}

	}

	/**
	 * Draws an octave of a piano keyboard from the C to the following B
	 * (including flats and bemols)
	 * 
	 * @param canvas
	 *            the canvas to draw onto
	 * @param x
	 *            the starting x position
	 * @param y
	 *            the starting y position
	 */
	protected void drawOctave(final Canvas canvas, final float x, final float y) {

		// draw whites
		float offsetX;
		for (int pos = 0; pos < 7; pos++) {
			offsetX = x + (pos * mKeyWidth);
			canvas.drawRect(offsetX, y, offsetX + mKeyWidth, y + mKeyHeight,
					mWhite);
		}

		// draw blacks
		for (int pos : BLACK_KEYS_OFFSET) {
			offsetX = x + (pos * mKeyWidth) + mBlackKeyDecal;
			canvas.drawRect(offsetX, y, offsetX + mBlackKeyWidth, y
					+ mBlackKeyHeight, mBlack);
		}
	}

	/**
	 * 
	 * @param canvas
	 *            the canvas to draw onto
	 * @param firstNoteOffset
	 *            the offset of the first note on the octave
	 * @param x
	 *            the starting x position
	 * @param y
	 *            the starting y position
	 */
	protected void drawHighlights(final Canvas canvas,
			final int firstNoteOffset, final float x, final float y) {
		Note[] notes = mChord.getNotes();

		int maxNoteOffset = firstNoteOffset + 7;
		int noteOffset, notePos;
		float offsetX;
		Note simpleNote;

		for (Note note : notes) {
			simpleNote = note.simplify();
			noteOffset = simpleNote.getOffsetFromC4();
			if ((noteOffset >= firstNoteOffset) && (noteOffset < maxNoteOffset)) {
				notePos = noteOffset - firstNoteOffset;
				offsetX = x + (notePos * mKeyWidth);

				if (simpleNote.isAltered()) {
					if (simpleNote.getAccidental() == Accidental.flat) {
						offsetX -= mKeyWidth;
					}
					canvas.save();
					canvas.translate(offsetX, y);
					canvas.drawPath(getPathBlack(), mHighlight);
					canvas.restore();
				} else {
					canvas.save();
					canvas.translate(offsetX, y);
					if ((notePos == 0) || (notePos == 3)) {
						canvas.drawPath(getPathC(), mHighlight);
					} else if ((notePos == 1) || (notePos == 4)
							|| (notePos == 5)) {
						canvas.drawPath(getPathD(), mHighlight);
					} else if ((notePos == 2) || (notePos == 6)) {
						canvas.drawPath(getPathE(), mHighlight);
					}
					canvas.restore();
				}

			}
		}

	}

	/**
	 * @param chord
	 *            the chord to display
	 */
	public void setChord(final Chord chord) {
		mChord = chord;

		int offset;
		mMinOffset = Integer.MAX_VALUE;
		mMaxOffset = Integer.MIN_VALUE;

		for (Note note : mChord.getNotes()) {
			offset = note.getOffsetFromC4();

			if (offset < mMinOffset) {
				mMinOffset = offset;
			} else if (offset > mMaxOffset) {
				mMaxOffset = offset;
			}
		}

		mMediumOffset = (mMinOffset + mMaxOffset) / 2;

		invalidate();
	}

	/**
	 * Initialiser the Staff view
	 */
	private void initPianoView() {
		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		Resources res = getContext().getResources();

		mDipToPixel = res.getDisplayMetrics().density;

		mBlack = new Paint();
		mBlack.setColor(Color.BLACK);
		mBlack.setStyle(Style.FILL);

		mWhite = new Paint();
		mWhite.setColor(Color.DKGRAY);
		mWhite.setStyle(Style.STROKE);
		mWhite.setStrokeWidth(2 * mDipToPixel);

		mHighlight = new Paint();
		mHighlight.setColor(Color.argb(128, 255, 64, 64));
		mHighlight.setStyle(Paint.Style.FILL);

		if (!isInEditMode()) {
			// Get drawables
			mChord = Chord.buildDominant7thChord(new Note());
		}
	}

	/**
	 * Read the attributes taken from XML
	 * 
	 * @param attrs
	 *            The attributes of the XML tag that is inflating the view.
	 */
	private void readPianoViewAttributes(final AttributeSet attrs) {
		if (isInEditMode()) {
			mKeyHeight = mDipToPixel * 64;
			mKeyWidth = mDipToPixel * 16;
			return;
		}

		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.StaffView);
		mKeyHeight = a.getDimension(R.styleable.PianoView_keyHeight, 0);
		if (mKeyHeight == 0) {
			mKeyHeight = mDipToPixel * 64;
		}

		mKeyWidth = a.getDimension(R.styleable.PianoView_keyWidth, 0);
		if (mKeyWidth == 0) {
			mKeyWidth = mDipToPixel * 16;
		}

		// update black keys size
		mBlackKeyHeight = mKeyHeight * 0.75f;
		mBlackKeyWidth = mKeyWidth * 0.75f;
		mBlackKeyDecal = mKeyWidth * 0.625f; // (2 - 0.75) / 2

		a.recycle();
	}

	/**
	 * @return
	 */
	private Path getPathC() {
		if (sPathC == null) {
			sPathC = new Path();
			sPathC.moveTo(0, 0);
			sPathC.lineTo(mBlackKeyDecal, 0);
			sPathC.lineTo(mBlackKeyDecal, mBlackKeyHeight);
			sPathC.lineTo(mKeyWidth, mBlackKeyHeight);
			sPathC.lineTo(mKeyWidth, mKeyHeight);
			sPathC.lineTo(0, mKeyHeight);
			sPathC.close();
		}
		return sPathC;
	}

	/**
	 * @return
	 */
	private Path getPathD() {
		if (sPathD == null) {
			sPathD = new Path();
			sPathD.moveTo(mKeyWidth - mBlackKeyDecal, 0);
			sPathD.lineTo(mBlackKeyDecal, 0);
			sPathD.lineTo(mBlackKeyDecal, mBlackKeyHeight);
			sPathD.lineTo(mKeyWidth, mBlackKeyHeight);
			sPathD.lineTo(mKeyWidth, mKeyHeight);
			sPathD.lineTo(0, mKeyHeight);
			sPathD.lineTo(0, mBlackKeyHeight);
			sPathD.lineTo(mKeyWidth - mBlackKeyDecal, mBlackKeyHeight);
			sPathD.close();
		}
		return sPathD;
	}

	/**
	 * @return
	 */
	private Path getPathE() {
		if (sPathE == null) {
			sPathE = new Path();
			sPathE.moveTo(mKeyWidth - mBlackKeyDecal, 0);
			sPathE.lineTo(mKeyWidth, 0);
			sPathE.lineTo(mKeyWidth, mKeyHeight);
			sPathE.lineTo(0, mKeyHeight);
			sPathE.lineTo(0, mBlackKeyHeight);
			sPathE.lineTo(mKeyWidth - mBlackKeyDecal, mBlackKeyHeight);
			sPathE.close();
		}
		return sPathE;
	}

	/**
	 * @return the path to draw black Key's highlight
	 */
	private Path getPathBlack() {
		if (sPathBlack == null) {
			sPathBlack = new Path();
			sPathBlack.addRect(mBlackKeyDecal, 0, mBlackKeyDecal
					+ mBlackKeyWidth, mBlackKeyHeight, Path.Direction.CCW);
		}
		return sPathBlack;
	}

	/** Utility to convert Dip values to Pixel */
	private float mDipToPixel;

	// Drawable
	private Paint mBlack, mWhite, mHighlight;
	private float mKeyWidth, mKeyHeight;
	private float mBlackKeyWidth, mBlackKeyHeight, mBlackKeyDecal;

	// Data
	private Chord mChord;
	private int mMinOffset, mMaxOffset, mMediumOffset;
}
