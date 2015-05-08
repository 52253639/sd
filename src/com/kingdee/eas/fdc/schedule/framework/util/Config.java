package com.kingdee.eas.fdc.schedule.framework.util;

import java.awt.Color;

public class Config {
	// ID
	public static String ID = "id";

	// NAME
	public static String NAME = "name";

	// 行的关键字
	public static String ROW_KEY = "rowId";

	// 列的关键字
	public static String COL_KEY = "colId";

	// 文档状态关键字
	public static String STATUS_KEY = "status";

	// 文档状态-已审核
	public static String STATUS_AUDITED = "03";
	// 文档状态-审核中
	public static String STATUS_AUDITING = "02";
	// 文档状态-未提交
	public static String STATUS_NOTSUBMIT = "00";

	// 文档背景颜色 已审核
	public static Color BG_AUDITED_COLOR = new Color(44, 220, 98); // 已审核
	// 文档背景颜色 审核中
	public static Color BG_AUDITING_COLOR = new Color(216, 169, 118); // 审核中
	// 文档背景颜色 未提交
	public static Color BG_NOTSUBMIT_COLOR = new Color(191, 191, 191); // 未提交

	// 选中的背景颜色
	public static Color BG_SELECTED_COLOR = new Color(204, 255, 255);

	// 表头的背景颜色
	public static Color HEADER_COLOR = new Color(176, 196, 222);

}
