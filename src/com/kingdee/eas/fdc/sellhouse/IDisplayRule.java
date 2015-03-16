package com.kingdee.eas.fdc.sellhouse;

import java.awt.Color;
import java.awt.Font;

import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;


public interface IDisplayRule {
	//返回系统属性
	public MoneySysTypeEnum getSysType();
	
	//返回字体颜
	public Font getFont();

	//返回字体颜色
	public Color getFrontColor();

	//房间显示字段 
	public String getDisplayField();

	//绑定房间显示 
	public int getAttachDisType();
	
	//根据房间 状态返回颜色
	public Color getCellBackgroundColor(String key);

	//房间单元格宽度
	public int getRoomWidth();

	//房间单元格高度
	public int getRoomHeight();
	
}
