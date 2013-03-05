package fr.xgouchet.musichelper.ui.view;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import fr.xgouchet.musichelper.R;
import fr.xgouchet.musichelper.model.Chord;
import fr.xgouchet.musichelper.model.Key;
import fr.xgouchet.musichelper.model.Note;

public class StaffView extends View {

	public static final int LINES = 5;

	/**
	 * Simple constructor to use when creating a view from code.
	 * 
	 * @param context
	 *            The Context the view is running in, through which it can
	 *            access the current theme, resources, etc.
	 */
	public StaffView(final Context context) {
		super(context);

		initStaffView();
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
	public StaffView(final Context context, final AttributeSet attrs) {
		super(context, attrs);

		initStaffView();
		readStaffViewAttributes(attrs);
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
	public StaffView(final Context context, final AttributeSet attrs,
			final int defStyle) {
		super(context, attrs, defStyle);

		initStaffView();
		readStaffViewAttributes(attrs);
	}

	/**
	 * @param chords
	 *            the chords to display
	 */
	public void setChords(final List<Chord> chords) {
		mChords = chords;

		int highestOffset, lowestOffset, offset;
		lowestOffset = 0;
		highestOffset = 8;

		for (Chord chord : mChords) {
			for (Note note : chord.getNotes()) {
				offset = note.getOffsetFromC4() + mKey.c4Offset();

				if (offset < lowestOffset) {
					lowestOffset = offset;
				} else if (offset > highestOffset) {
					highestOffset = offset;
				}
			}
		}

		mSpacesAfterStaff = 1 + Math.max(1, (1 - lowestOffset) / 2);
		mSpacesBeforeStaff = 1 + Math.max(1, (highestOffset - 7) / 2);

		invalidate();
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

		// staff height + spaces above and below
		int spaces = mSpacesBeforeStaff + 4 + mSpacesAfterStaff;
		neededHeight = (int) ((spaces * mLineSpacing) + 0.5f);

		// TODO measure width

		neededWidth += getPaddingLeft() + getPaddingRight();
		neededHeight += getPaddingTop() + getPaddingBottom();

		mTopOffsetToBottomLine = getPaddingTop()
				+ ((mSpacesBeforeStaff + 4f) * mLineSpacing);

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
		super.onDraw(canvas);

		// draw the staff
		drawLines(canvas);
		drawKey(canvas);

		// draw the chords
		if (mChords != null) {
			drawChords(canvas);
		}

	}

	/**
	 * Draws the staff lines
	 * 
	 * @param canvas
	 *            the canvas on which the view will be drawn
	 */
	private void drawLines(final Canvas canvas) {
		float offsetY = getPaddingTop();

		// treble key
		offsetY += mSpacesBeforeStaff * mLineSpacing;

		float x1, x2, y;

		x1 = getPaddingLeft();
		x2 = getWidth() - getPaddingRight();

		// Draw Lines
		for (int i = 0; i < LINES; ++i) {
			y = offsetY + (i * mLineSpacing);
			canvas.drawLine(x1, y, x2, y, mLinePaint);
		}
	}

	/**
	 * Draws the staff key
	 * 
	 * @param canvas
	 *            the canvas on which the view will be drawn
	 */
	private void drawKey(final Canvas canvas) {

		int topOffset = getPaddingTop()
				+ (int) ((mSpacesBeforeStaff - 2) * mLineSpacing);

		Drawable drawable;
		switch (mKey) {
		case treble:
			drawable = mTrebble;
			break;
		case alto:
			drawable = mAlto;
			break;
		case bass:
			drawable = mBass;
			break;
		default:
			drawable = null;
		}

		if (drawable != null) {
			int height = (int) (mLineSpacing * 8);
			int width = (int) (mLineSpacing * 4);
			drawable.setBounds(getPaddingLeft(), topOffset, getPaddingLeft()
					+ width, topOffset + height);
			drawable.draw(canvas);
		}
	}

	/**
	 * Draws the chords on the staff
	 * 
	 * @param canvas
	 *            the canvas on which the view will be drawn
	 */
	private void drawChords(final Canvas canvas) {
		float offsetX;

		offsetX = getPaddingLeft() + (5f * mLineSpacing);
		for (Chord chord : mChords) {
			if (chord.hasAlteration()) {
				offsetX += 2 * mLineSpacing;
			}
			drawChord(canvas, chord, offsetX);
			offsetX += mLineSpacing * 2;
		}
	}

	/**
	 * Draws the given chord on the staff at the given offset
	 * 
	 * @param canvas
	 *            the canvas on which the view will be drawn
	 * @param chord
	 *            the chord to draw
	 * @param offsetX
	 *            the horizontal offset
	 */
	private void drawChord(final Canvas canvas, final Chord chord,
			final float offsetX) {
		int offset, prevOffset;

		boolean overlap, overlapAlt, previousAlt, previousOverlap;

		previousAlt = previousOverlap = false;

		prevOffset = -256;
		for (Note note : chord.getNotes()) {
			offset = note.getOffsetFromC4() + mKey.c4Offset();

			overlap = ((offset - prevOffset) <= 1);

			prevOffset = offset;

			overlapAlt = note.isAltered() & previousAlt & (!previousOverlap);

			drawNote(canvas, note, offsetX, offset, overlap, overlapAlt);

			previousAlt = note.isAltered();
			previousOverlap = overlapAlt;
		}
	}

	/**
	 * 
	 * @param canvas
	 * @param note
	 * @param x
	 * @param offset
	 * @param overlap
	 * @param overlapAlt
	 */
	private void drawNote(final Canvas canvas, final Note note, final float x,
			final int offset, final boolean overlap, final boolean overlapAlt) {

		float y = mTopOffsetToBottomLine - (offset * mHalfSpacing);
		float overlapOffset = (overlap ? mLineSpacing : 0);

		drawExtraLines(canvas, x + overlapOffset, offset);
		drawWhole(canvas, x + overlapOffset, y);

		overlapOffset = (overlapAlt ? mLineSpacing : 0);
		if (note.isAltered()) {
			switch (note.getAccidental()) {
			case doubleSharp:
				drawSharp(canvas, x - (2 * mLineSpacing) - overlapOffset, y);
			case sharp:
				drawSharp(canvas, x - mLineSpacing - overlapOffset, y);
				break;
			case doubleFlat:
				drawFlat(canvas, x - (2 * mLineSpacing) - overlapOffset, y);
			case flat:
				drawFlat(canvas, x - mLineSpacing - overlapOffset, y);
				break;
			case natural:
			default:
				break;
			}
		}
	}

	/**
	 * Draws extra lines, needed for a note outside of the staff
	 * 
	 * 
	 * @param canvas
	 *            the canvas on which the view will be drawn
	 * @param offsetX
	 *            the horizontal offset
	 * @param offset
	 *            the line offset
	 */
	private void drawExtraLines(final Canvas canvas, final float offsetX,
			final int offset) {
		float y;
		if (offset < 0) {
			for (int i = 0; i >= (offset - 1); i -= 2) {
				y = mTopOffsetToBottomLine - (i * mHalfSpacing);
				canvas.drawLine(offsetX - mLineSpacing, y, offsetX
						+ mLineSpacing + mHalfSpacing, y, mLinePaint);
			}
		} else if (offset > 8) {
			for (int i = 8; i <= (offset + 1); i += 2) {
				y = mTopOffsetToBottomLine - (i * mHalfSpacing);
				canvas.drawLine(offsetX - mLineSpacing, y, offsetX
						+ mLineSpacing + mHalfSpacing, y, mLinePaint);
			}
		}
	}

	/**
	 * Draws a whole note at the given position
	 * 
	 * @param canvas
	 *            the canvas on which the view will be drawn
	 * @param x
	 * @param y
	 */
	private void drawWhole(final Canvas canvas, final float x, final float y) {
		mWhole.setBounds((int) (x - mLineSpacing), (int) (y - mLineSpacing),
				(int) (x + mLineSpacing), (int) (y + mLineSpacing));
		mWhole.draw(canvas);
	}

	/**
	 * Draws a Sharp at the given position
	 * 
	 * @param canvas
	 *            the canvas on which the view will be drawn
	 * @param x
	 * @param y
	 */
	private void drawSharp(final Canvas canvas, final float x, final float y) {
		mSharp.setBounds((int) (x - mLineSpacing), (int) (y - mLineSpacing),
				(int) (x + mLineSpacing), (int) (y + mLineSpacing));
		mSharp.draw(canvas);
	}

	/**
	 * Draws a Flat at the given position
	 * 
	 * @param canvas
	 *            the canvas on which the view will be drawn
	 * @param x
	 * @param y
	 */
	private void drawFlat(final Canvas canvas, final float x, final float y) {
		mFlat.setBounds((int) (x - mLineSpacing), (int) (y - mLineSpacing),
				(int) (x + mLineSpacing), (int) (y + mLineSpacing));
		mFlat.draw(canvas);
	}

	/**
	 * Initialiser the Staff view
	 */
	private void initStaffView() {
		Resources res = getContext().getResources();

		mDipToPixel = res.getDisplayMetrics().density;

		mLinePaint = new Paint();
		mLinePaint.setColor(Color.BLACK);
		mLinePaint.setStyle(Style.STROKE);

		// Default space above and below staff (for treble key mainly)
		mSpacesAfterStaff = 2;
		mSpacesBeforeStaff = 2;

		mKey = Key.treble;

		if (!isInEditMode()) {
			// keys
			mTrebble = res.getDrawable(R.drawable.treble);
			mAlto = res.getDrawable(R.drawable.alto);
			mBass = res.getDrawable(R.drawable.bass);

			// notes
			mWhole = res.getDrawable(R.drawable.whole);

			// alterations
			mSharp = res.getDrawable(R.drawable.sharp);
			mFlat = res.getDrawable(R.drawable.flat);
		}
	}

	/**
	 * Read the attributes taken from XML
	 * 
	 * @param attrs
	 *            The attributes of the XML tag that is inflating the view.
	 */
	private void readStaffViewAttributes(final AttributeSet attrs) {
		if (isInEditMode()) {
			mLineSpacing = mDipToPixel * 16;
			mHalfSpacing = mLineSpacing / 2.0f;
			mLineWidth = mDipToPixel * 2;
			return;
		}

		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.StaffView);

		mLineSpacing = a.getDimension(R.styleable.StaffView_lineSpacing, 0);
		if (mLineSpacing == 0) {
			mLineSpacing = mDipToPixel * 16;
		}
		mHalfSpacing = mLineSpacing / 2.0f;

		mLineWidth = a.getDimension(R.styleable.StaffView_lineWidth, 0);
		if (mLineWidth == 0) {
			mLineWidth = mDipToPixel * 1;
		}

		mLinePaint.setStrokeWidth(mLineWidth);

		a.recycle();
	}

	/** Utility to convert Dip values to Pixel */
	private float mDipToPixel;

	// Drawables
	private Drawable mTrebble, mAlto, mBass;
	private Drawable mWhole, mHalf, mQuarter;
	private Drawable mSharp, mFlat;

	private Paint mLinePaint;
	private float mLineSpacing, mLineWidth, mHalfSpacing;
	private int mSpacesBeforeStaff, mSpacesAfterStaff;
	private float mTopOffsetToBottomLine;

	// Data
	private List<Chord> mChords;
	private Key mKey;

}
