package com.dyzs.labels.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

public class DrawableUtil {
	public static final int STROKE_DEEPLY = 1;
	/**
	A Drawable with a color gradient for buttons, backgrounds, etc. 

	It can be defined in an XML file with the <shape> element. For more information, see the guide to Drawable Resources.

	@attr
	ref android.R.styleable#GradientDrawable_visible
	@attr
	ref android.R.styleable#GradientDrawable_shape
	@attr
	ref android.R.styleable#GradientDrawable_innerRadiusRatio
	@attr
	ref android.R.styleable#GradientDrawable_innerRadius
	@attr
	ref android.R.styleable#GradientDrawable_thicknessRatio
	@attr
	ref android.R.styleable#GradientDrawable_thickness
	@attr
	ref android.R.styleable#GradientDrawable_useLevel
	@attr
	ref android.R.styleable#GradientDrawableSize_width
	@attr
	ref android.R.styleable#GradientDrawableSize_height
	@attr
	ref android.R.styleable#GradientDrawableGradient_startColor
	@attr
	ref android.R.styleable#GradientDrawableGradient_centerColor
	@attr
	ref android.R.styleable#GradientDrawableGradient_endColor
	@attr
	ref android.R.styleable#GradientDrawableGradient_useLevel
	@attr
	ref android.R.styleable#GradientDrawableGradient_angle
	@attr
	ref android.R.styleable#GradientDrawableGradient_type
	@attr
	ref android.R.styleable#GradientDrawableGradient_centerX
	@attr
	ref android.R.styleable#GradientDrawableGradient_centerY
	@attr
	ref android.R.styleable#GradientDrawableGradient_gradientRadius
	@attr
	ref android.R.styleable#GradientDrawableSolid_color
	@attr
	ref android.R.styleable#GradientDrawableStroke_width
	@attr
	ref android.R.styleable#GradientDrawableStroke_color
	@attr
	ref android.R.styleable#GradientDrawableStroke_dashWidth
	@attr
	ref android.R.styleable#GradientDrawableStroke_dashGap
	@attr
	ref android.R.styleable#GradientDrawablePadding_left
	@attr
	ref android.R.styleable#GradientDrawablePadding_top
	@attr
	ref android.R.styleable#GradientDrawablePadding_right
	@attr
	ref android.R.styleable#GradientDrawablePadding_bottom*/
	/**
	 * 生成 drawable 资源，对应的是 xml 中的 shape 标签
	 * @param argb
	 * @param radius
	 * @return
	 */
	public static GradientDrawable generateDrawable(int argb, float radius) {
		GradientDrawable drawable = new GradientDrawable();
		drawable.setShape(GradientDrawable.RECTANGLE);	// 设置为矩形，默认就是矩形
		drawable.setCornerRadius(radius);				// 设置圆形的半径
		drawable.setColor(argb);
		return drawable;
	}
	public static GradientDrawable generateDrawable(int argb, float radius, int stroke) {
		GradientDrawable drawable = new GradientDrawable();
		drawable.setStroke(stroke, argb);
		drawable.setShape(GradientDrawable.RECTANGLE);	// 设置为矩形，默认就是矩形
		drawable.setCornerRadius(radius);				// 设置圆形的半径
		drawable.setColor(argb);
		return drawable;
	}
	/**
	 * maidou add 添加边框颜色
	 * @param argb
	 * @param radius
	 * @param stroke
	 * @param strokeMode		表示当前的边框是否颜色加深
	 * @return
	 */
	public static GradientDrawable generateDrawable(int argb, float radius, int stroke, int strokeMode) {
		GradientDrawable drawable = new GradientDrawable();
		drawable.setStroke(stroke, argb);
		if (strokeMode == STROKE_DEEPLY) {
			drawable.setStroke(stroke, ColorUtil.getColorDeeply(argb));
		}
		drawable.setShape(GradientDrawable.RECTANGLE);	// 设置为矩形，默认就是矩形
		drawable.setCornerRadius(radius);				// 设置圆形的半径
		drawable.setColor(argb);
		return drawable;
	}
	
	/**
	 * 动态创建 Selector 状态选择器，Selector 对应的 drawable 是 StateListDrawable，
	 * 可以动态的生成 selector 状态选择器
	 * @param pressed
	 * @param normal
	 * @return
	 */
	public static StateListDrawable generateSelector(Drawable pressed, Drawable normal) {
		StateListDrawable drawable = new StateListDrawable();
		/**
		 * 设置按下的图片
		 */
		drawable.addState(new int[]{android.R.attr.state_pressed}, pressed);
		/**
		 * 设置默认的图片，参考写 Selector xml 文件的时候。
		 * 因为在 xml 文件中，Selector 的 normal 状态下是什么都不用写是可以的，
		 * 所以我们传递一个空的 int 数组，表示没有状态，填 null 就报空指针了
		 */
		drawable.addState(new int[]{}, normal);
		return drawable;
	}
}
