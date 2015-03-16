package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;

/**
 * 
 * 立面图专用背景色设置
 * @author jian_wen
 *
 */

public interface IBackgroundColor {
	/**
	 * 根据传进参数 决定背景色
	 * @param key
	 * @return
	 */
	public Color getCellBackgroundColor(String key);
}
