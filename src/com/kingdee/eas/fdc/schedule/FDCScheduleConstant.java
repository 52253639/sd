/*
 * @(#)FDCScheduleConstant.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.schedule;

public class FDCScheduleConstant {
	
	/**形象进度*/
	public final static String PROJECTIMAGE = "JM2iYBxoReukgy90hZJbW2LF6cA=";
	/**阶段性成果*/
	public final static String ACHIEVEMANAGER = "Rz+dS7ECSfqM4kEJqGawYWLF6cA=";
	/**质量检查点*/
	public final static String QUALITYCHECKPOINT = "8PR0EhHFTaiDGdtCmQ19SGLF6cA=";
	
	/** 按时完成 */
	public final static int ONTIME = 0;
	/** 延时完成 */
	public final static int DELAY = 1;
	/** 待确认 */
	public final static int CONFIRMED = 2;
	/** 延时未完成 */
	public final static int DELAYANDNOTCOMPLETE = 3;
	/** 其他 */
	public final static int UNDEF = 4;
	
	private FDCScheduleConstant(){
		
	}
	/*
	 * 以下为迁移旭辉报表常量，由于用到报表组织树选择工具。但是代码搞耦合，分离成本大，后续报表推测还会用到，所以全部迁移过来！
	 */

	// 进度项目ID
	public static final String REPORT_CHART_ID = "id";
	
	// 进度项目名称
	public static final String REPORT_CHART_PROJECT_ID = "projectID";

	// 进度项目名称
	public static final String REPORT_CHART_PROJECT_NAME = "projectName";

	// 计划完成项目数
	public static final String REPORT_CHART_PLANNED_COMP = "plannedComp";

	// 按时完成项目数
	public static final String REPORT_CHART_TIMMED_COMP = "timedComp";

	// 延迟15天内完成项目数
	public static final String REPORT_CHART_FIF_IN_COMP = "fifteenInComp";

	// 延迟15天外完成项目数
	public static final String REPORT_CHART_FIF_OUT_COMP = "fifteenOutComp";

	// 未完成项目数
	public static final String REPORT_CHART_UNCOMP = "unComp";

	// 待确认项目数
	public static final String REPORT_CHART_CONFIRMED_COMP = "confirmedComp";

	// 按时完成项目数比率
	public static final String REPORT_CHART_TIMED_COMP_RATE = "timedCompRate";

	// 延迟完成项目数比率
	public static final String REPORT_CHART_DELAYED_COMP_RATE = "delayedCompRate";
	
	// 开始日期
	public static final String REPORT_CHART_START_DATE = "startDate";
	
	// 结束日期
	public static final String REPORT_CHART_END_DATE = "endDate";
	
	// 进度考核类型（依次为考核节点、主项计划、专项计划、里程碑）
	public static final String REPORT_CHART_SCHEDULE_TYPE_CHECK = "check";
	
	public static final String REPORT_CHART_SCHEDULE_TYPE_MAIN_PRJ = "mainPrj";
	
	public static final String REPORT_CHART_SCHEDULE_TYPE_PRO_PRJ = "proPrj";
	
	public static final String REPORT_CHART_SCHEDULE_TYPE_MILESTONE = "milestone";
	
	// 专项整期
	public static final String REPORT_CHART_SCHEDULE_ALL_EXPERT_PLAN_ID = "allExpertPlan";
	
	public static final String REPORT_CHART_SCHEDULE_ALL_EXPERT_PLAN_NAME = "整期";
	
	// 图形中横坐标显示的key值
	public static final String CHART_X_AXIS_NAME = "x_axis_name";

	// 选择的组织和工程项目
	public static final String CHART_SELECT_ORG_PRO = "select_org_pro";
	
	// 排序后的组织和工程项目
	public static final String CHART_SELECT_SORTED_ORG = "sorted_select_org_pro";
	
	// 排序后的组织和工程项目的排序号
	public static final String CHART_SELECT_SORTED_BY_NUMBWE_ORG = "sorted_select_number_org_pro";
	
	// 排序后的父组织和工程项目
	public static final String CHART_SELECT_SORTED_PARENT_ORG = "sorted_select_parent_org_pro";
	
	// 选中节点的公共父级节点名称
	public static final String CHART_SELECT_PROJECT_NAME = "chart_select_project_name";

	// 集团ID
	public static final String PROJECT_TREE_NODE_GROUP_ID = "00000000-0000-0000-0000-00000000000232B85C74";

	// 旭辉集团ID
	public static final String PROJECT_TREE_NODE_XUHUI_GROUP_ID = "+D9V2+gnSWOoycJ2ikueqTK4XHQ=";

	// 组织为集团时的组织ID
	public static final String PROJECT_TREE_NODE_GROUP_CURRENT_ORG_ID = "00000000-0000-0000-0000-000000000000CCE7AED4";
	public static final String PROJECT_TREE_NODE_GROUP_CURRENT_ORG_NAME = "集团";
	public static final String PROJECT_TREE_NODE_GROUP_CURRENT_ORG_NUMBER = "G";

	// 组织为旭辉集团时的组织ID
	public static final String PROJECT_TREE_NODE_XUHUI_GROUP_CURRENT_ORG_ID = "xpNSW8jbR52f4Zw1HZXhCsznrtQ=";
	
	// 报表树节点间标识符号
	public static final String REPORT_TREE_SEPARATOR = "!";
	
	// 组织名称分隔符
	public static final String ORG_PROJECT_NAME_SEPARATOR = "/";

	// 操作类型    增加行和插入行、删除行操作
	public static final int ACTION_TYPE_ADD = 1;
	public static final int ACTION_TYPE_INSERT = 2;
	public static final int ACTION_TYPE_DELETE = 3;
	public static final int ACTION_TYPE_UP = 4;
	public static final int ACTION_TYPE_NEXT = 5;

}
