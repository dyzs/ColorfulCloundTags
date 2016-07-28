package com.dyzs.customcloundtags.utils;

import java.util.Random;

import android.graphics.Color;

public class ColorUtil {
	/**
	 * 随机生成漂亮的颜色
	 * @return
	 */
	public static int randomColor() {
		Random random = new Random();

		int red = random.nextInt(150) + 50;
		
		int green = random.nextInt(150) + 50;
		
		int blue = random.nextInt(150) + 50;

		return Color.rgb(red, green, blue);		// 根据rgb混合生成一种新的颜色
	}

	/**
	 * 随机生成漂亮的颜色,带透明度的
	 * @return
	 */
	public static int randomColorArgb() {
		Random random = new Random();

		int alpha = random.nextInt(70) + 30;

		int red = random.nextInt(150) + 50;

		int green = random.nextInt(150) + 50;

		int blue = random.nextInt(150) + 50;

		return Color.argb(alpha, red, green, blue);		// 根据argb混合生成一种新的颜色
	}


	/**
	 * 颜色与上一个十六进制数ARGB，得到一个颜色加深的效果，效果从 0-F 深
	 * @param color
	 * @return
	 */
	public static int getColorDeeply(int color) {
//		| 0xF0000000 & 0xFFF5F5F5
		return color & 0xFFD9D9D9;
	}
}
