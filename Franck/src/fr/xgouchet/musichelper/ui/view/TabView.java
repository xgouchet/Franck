package fr.xgouchet.musichelper.ui.view;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import fr.xgouchet.musichelper.R;
import fr.xgouchet.musichelper.model.Combo;

public class TabView extends View {

	/** Utility to convert Dip values to Pixel */
	private float mDipToPixel;

	// Drawable
	private Drawable mFretboard, mNut;
	private Paint mStrings, mFrets;

	//
	private int mNeededFrets, mMinFret, mMaxFret, mFirstFret;
	private float mFretWidth, mStringHeight;

	// Data
	private List<Combo> mCombos;

	/**
	 * Simple constructor to use when creating a view from code.
	 * 
	 * @param context
	 *            The Context the view is running in, through which it can
	 *            access the current theme, resources, etc.
	 */
	public TabView(final Context context) {
		super(context);

		initTabView();
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
	public TabView(final Context context, final AttributeSet attrs) {
		super(context, attrs);

		initTabView();
		readTabViewAttributes(attrs);
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
	public TabView(final Context context, final AttributeSet attrs,
			final int defStyle) {
		super(context, attrs, defStyle);

		initTabView();
		readTabViewAttributes(attrs);
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

		// tab needed width / height
		neededWidth = (int) (mNeededFrets * mFretWidth);
		neededHeight = (int) (6 * mStringHeight);

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

		canvas.clipRect(getPaddingLeft(), getPaddingTop(), getWidth()
				- getPaddingRight(), getHeight() - getPaddingBottom());

		// draw fretboard
		mFretboard.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		mFretboard.draw(canvas);

		// draw frets
		int width = canvas.getWidth() - getPaddingLeft() - getPaddingRight();
		int frets = (int) (width / mFretWidth);
		if (mMaxFret <= frets) {
			mFirstFret = 0;
		} else {
			mFirstFret = mMinFret - 1;
		}
		float x;
		for (int fret = 0; fret < frets; ++fret) {
			x = getPaddingLeft() + (fret * mFretWidth);
			canvas.drawLine(x, 0, x, canvas.getHeight(), mFrets);
		}

		// draw strings
		float y;
		for (int s = 0; s < 6; ++s) {
			y = getPaddingTop() + (mStringHeight * (s + 0.5f));
			canvas.drawLine(0, y, canvas.getWidth(), y, mStrings);
		}

	}

	/**
	 * @param combos
	 *            the combos to display
	 */
	public void setCombos(final List<Combo> combos) {
		mCombos = combos;

		int fret;
		mMinFret = 100;
		mMaxFret = -1;
		for (Combo combo : combos) {
			fret = combo.getFret();
			if (fret <= 0) {
				continue;
			}

			if (fret < mMinFret) {
				mMinFret = fret;
			}

			if (fret > mMaxFret) {
				mMaxFret = fret;
			}
		}
		mNeededFrets = (mMaxFret - mMinFret) + 1;
	}

	/**
	 * Initialiser the Tab view
	 */
	private void initTabView() {
		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		Resources res = getContext().getResources();

		mDipToPixel = res.getDisplayMetrics().density;

		mStrings = new Paint();
		mStrings.setStrokeWidth(2 * mDipToPixel);

		mFrets = new Paint();
		mFrets.setStrokeWidth(4 * mDipToPixel);

		if (isInEditMode()) {
			mNeededFrets = 4;
			mMaxFret = 3;
			mMinFret = 1;

			mStrings.setColor(Color.LTGRAY);
			mFrets.setColor(Color.GRAY);
			mFretboard = new ColorDrawable(Color.DKGRAY);
		} else {
			mStrings.setColor(res.getColor(R.color.string));
			mFrets.setColor(res.getColor(R.color.fret));
			mFretboard = res.getDrawable(R.drawable.fretboard);
		}
	}

	/**
	 * Read the attributes taken from XML
	 * 
	 * @param attrs
	 *            The attributes of the XML tag that is inflating the view.
	 */
	private void readTabViewAttributes(final AttributeSet attrs) {

		if (isInEditMode()) {
			mStringHeight = mDipToPixel * 16;
			mFretWidth = mDipToPixel * 32;
			return;
		}

		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.StaffView);
		mStringHeight = a.getDimension(R.styleable.PianoView_keyHeight, 0);
		if (mStringHeight == 0) {
			mStringHeight = mDipToPixel * 16;
		}

		mFretWidth = a.getDimension(R.styleable.PianoView_keyWidth, 0);
		if (mFretWidth == 0) {
			mFretWidth = mDipToPixel * 32;
		}

		a.recycle();
	}

}
