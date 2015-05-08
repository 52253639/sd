package com.kingdee.eas.fdc.schedule.framework.util;

import java.util.List;

public interface TableBuilder {
	/**
	 * 创建表格，
	 * 
	 * @param header1
	 *            列名
	 * @param header2
	 *            行名
	 * @param datas
	 *            数据
	 * @return DocTable
	 */
	DocTable createTable(List header1, List header2, List datas);
}
