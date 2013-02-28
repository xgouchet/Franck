package fr.xgouchet.musichelper.ui.view;

import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import fr.xgouchet.musichelper.R;
import fr.xgouchet.musichelper.model.Chord;

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

		// staff height alone
		neededHeight = (int) (((LINES - 1) * mLineSpacing)
				+ (LINES * mLineWidth) + 0.5f);

		// Treble key
		neededHeight += mLineSpacing * 4;

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
		super.onDraw(canvas);
		drawLines(canvas);
	}

	/**
	 * Draws the staff lines and treble key
	 * 
	 * @param canvas
	 *            the canvas on which the view will be drawn
	 */
	private void drawLines(final Canvas canvas) {
		float offsetY = getPaddingTop();

		// treble key
		offsetY += 2 * mLineSpacing;

		float x1, x2, y;

		x1 = getPaddingLeft();
		x2 = getWidth() - getPaddingRight();

		// Draw Lines
		for (int i = 0; i < LINES; ++i) {
			y = offsetY + (i * mLineSpacing) + (mLineWidth / 2);
			canvas.drawLine(x1, y, x2, y, mStaffPaint);
		}

		if (!isInEditMode()) {
			int height = (int) (mLineSpacing * 8);
			int width = (int) (mLineSpacing * 3);
			mTrebbleDrawable.setBounds(getPaddingLeft(), getPaddingTop(),
					getPaddingLeft() + width, height + getPaddingTop());
			mTrebbleDrawable.draw(canvas);
		}
	}

	/**
	 * Initialiser the Staff view
	 */
	private void initStaffView() {
		mDipToPixel = getContext().getResources().getDisplayMetrics().density;

		mStaffPaint = new Paint();
		mStaffPaint.setColor(Color.BLACK);

		if (!isInEditMode()) {
			mTrebbleDrawable = getContext().getResources().getDrawable(
					R.drawable.treble);
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
			mLineWidth = mDipToPixel * 2;
			return;
		}

		TypedArray a = getContext().obtainStyledAttributes(attrs,
				R.styleable.StaffView);

		mLineSpacing = a.getDimension(R.styleable.StaffView_lineSpacing, 0);
		if (mLineSpacing == 0) {
			mLineSpacing = mDipToPixel * 16;
		}

		mLineWidth = a.getDimension(R.styleable.StaffView_lineWidth, 0);
		if (mLineWidth == 0) {
			mLineWidth = mDipToPixel * 2;
		}

		mStaffPaint.setStrokeWidth(mLineWidth);

		a.recycle();
	}

	/** Utility to convert Dip values to Pixel */
	private float mDipToPixel;

	private Drawable mTrebbleDrawable;
	private Paint mStaffPaint;
	private List<Chord> mChords;
	private float mLineSpacing, mLineWidth;
}
