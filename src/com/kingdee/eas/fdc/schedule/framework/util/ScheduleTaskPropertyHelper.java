package com.kingdee.eas.fdc.schedule.framework.util;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskPropertyCollection;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskPropertyFactory;

/**
 * 甘特图列显示隐藏控制
 * <p>
 * 在T_SCH_ScheduleTaskProperty中有两个字段，FDisplayUI、FHideUI<br>
 * 这两个字段分别控制该列是否在某个UI显示<br>
 * 比如FDisplayUI = 7，则代表1 + 2 + 4，即在主项、专项、主项执行3个界面默认显示该列<br>
 * 比如FHideUI = 28,则代表4 + 8 + 16，即在主项执行、专项执行、总进度计划中默认隐藏该列<br>
 * 判断算法：<br>
 * FDisplayUI & DISPLAY_MAIN == DISPLAY_MAIN ?来判断是否在主项显示<br>
 * 
 * @author emanon
 * 
 */
public class ScheduleTaskPropertyHelper {

	// 所有UI
	public static int ALL = 0;
	// 主项标识
	public static int MAIN = 1;
	// 专项标识
	public static int SPCL = 2;
	// 主项执行标识
	public static int MAIN_EX = 4;
	// 专项执行标识
	public static int SPCL_EX = 8;
	// 总进度计划标识
	public static int TOTAL = 16;

	/**
	 * 是否包含指定UI
	 * 
	 * @param UI
	 * @param field
	 * @return
	 */
	public static boolean isIncludeUI(int UI, int field) {
		return isInclude(UI, field);
	}

	/**
	 * 是否包含主项编制
	 * <p>
	 * 
	 * @param field
	 * @return
	 */
	public static boolean isIncludeMain(int field) {
		return isInclude(MAIN, field);
	}

	/**
	 * 是否包含专项编制
	 * <p>
	 * 
	 * @param field
	 * @return
	 */
	public static boolean isIncludeSpcl(int field) {
		return isInclude(SPCL, field);
	}

	/**
	 * 是否包含主项执行
	 * <p>
	 * 
	 * @param field
	 * @return
	 */
	public static boolean isIncludeMainEx(int field) {
		return isInclude(MAIN_EX, field);
	}

	/**
	 * 是否包含专项执行
	 * <p>
	 * 
	 * @param field
	 * @return
	 */
	public static boolean isIncludeSpclEx(int field) {
		return isInclude(SPCL_EX, field);
	}

	/**
	 * 是否包含总进度计划
	 * <p>
	 * 
	 * @param field
	 * @return
	 */
	public static boolean isIncludeTotal(int field) {
		return isInclude(TOTAL, field);
	}

	/**
	 * 计算field中是否包含include基本元素
	 * <p>
	 * 原理：<br>
	 * 比如field=5，转换成二进制则为101<br>
	 * 要查看field中是否包含在主项编制显示，用5 & 1，即 101 & 001 = 001，可知包含<br>
	 * 同理查看是否包含专项编制，用5 & 2，即 101 & 010 = 000，可知不包含<br>
	 * 同理主项执行，5 & 4，即 101 & 100 = 100，可知包含<br>
	 * 
	 * @param include
	 * @param field
	 * @return
	 */
	private static boolean isInclude(int include, int field) {
		if (include == (include & field)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取对应界面的默认显示列
	 * <p>
	 * 
	 * @param UI
	 * @return
	 * @throws BOSException
	 */
	public static ScheduleTaskPropertyCollection getDisplayColumns(int UI)
			throws BOSException {
		ScheduleTaskPropertyCollection allColumns = getAllColumns(UI);
		for (int i = allColumns.size() - 1; i >= 0; i--) {
			int hideUI = allColumns.get(i).getHideUI();
			if (ScheduleTaskPropertyHelper.isIncludeUI(UI, hideUI)) {
				allColumns.removeObject(i);
			}
		}
		return allColumns;
	}

	/**
	 * 获取对应界面的所有列，包括显示以及隐藏
	 * <p>
	 * 之前写死的列，现改为从数据库取值，可以自定义列。<br>
	 * 
	 * add by emanon
	 * 
	 * @return
	 * @throws BOSException
	 */
	public static ScheduleTaskPropertyCollection getAllColumns(int UI)
			throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("type", "custom"));
		view.setFilter(filter);
		view.getSorter().add(new SorterItemInfo("seq"));
		ScheduleTaskPropertyCollection taskPropertys = ScheduleTaskPropertyFactory
				.getRemoteInstance().getScheduleTaskPropertyCollection(view);
		for (int i = taskPropertys.size() - 1; i >= 0; i--) {
			int displayUI = taskPropertys.get(i).getDisplayUI();
			int hideUI = taskPropertys.get(i).getHideUI();
			if (!ScheduleTaskPropertyHelper.isIncludeUI(UI, displayUI)
					&& !ScheduleTaskPropertyHelper.isIncludeUI(UI, hideUI)) {
				taskPropertys.removeObject(i);
			}
		}
		return taskPropertys;
	}

}
