package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;

import javax.swing.JButton;

/**
 *  监听立面图单个单元格被单击 <br>
 *  立面图被调用时调用界面响应立面图单元格单击事件必须实现此接口
 * @author jian_wen
 *
 */
public interface ISolidSideImageListener {
	/**
	 *  响应动作
	 * @param obj
	 */
	void actionResponse(Object obj) throws Exception;
	/**
	 *  双击事件
	 * @param obj
	 * 
	 */
	void actionBothClicked(Object obj) throws Exception;
	/**
	 *  设置被单击按钮背景色
	 * @param btn
	 * @param c
	 * @throws Exception
	 */
	void setColor(JButton btn,Color c) throws Exception;
}
