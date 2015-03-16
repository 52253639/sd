package com.kingdee.eas.fdc.market.client;

import java.awt.Graphics;

/**
 * 控件的打印接口
 */
public interface XPrint {
	/**
	 * 打印过程调用
	 * @param rX 相对坐标 rX
	 * @param rY 相对坐标 rY
	 * @param graphics
	 */
	public void xPrint(int rX,int rY,Graphics graphics);
}
