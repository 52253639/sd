package com.kingdee.eas.fdc.schedule.framework;

import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;

public class ScheduleDependHelper {

	/**
	 * 取得搭接关系的默认简称
	 * 
	 * @param type
	 * @return
	 */
	public static String getSimpleName(TaskLinkTypeEnum type) {
		if (type == null) {
			return "";
		} else if (TaskLinkTypeEnum.FINISH_START.equals(type)) {
			return "FS";
		} else if (TaskLinkTypeEnum.FINISH_FINISH.equals(type)) {
			return "FF";
		} else if (TaskLinkTypeEnum.START_START.equals(type)) {
			return "SS";
		} else {
			return "";
		}
	}

	/**
	 * 取得表示一个关系的字符串
	 * <p>
	 * xFS+0时，默认只显示x即可<br>
	 * 
	 * @param dep
	 * @param type
	 * @param diff
	 * @return
	 */
	public static String getDependStr(String dep, String type, int diff) {
		StringBuffer strDep = new StringBuffer();
		strDep.append(dep);
		if (!("FS".equals(type) && diff == 0)) {
			strDep.append(type);
		}
		if (diff > 0) {
			strDep.append("+");
		}
		strDep.append(diff);
		return strDep.toString();
	}

}
