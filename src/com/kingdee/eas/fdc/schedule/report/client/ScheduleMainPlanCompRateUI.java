/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.report.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.schedule.FDCScheduleConstant;

/**
 * 描述:进度报表主项计划达成率界面
 * @author adward_huang  date:2012-10-17
 * @version EAS6.1
 */
public class ScheduleMainPlanCompRateUI extends AbstractScheduleMainPlanCompRateUI {
	
	private static final long serialVersionUID = 5207530871092606342L;

	private static final Logger logger = CoreUIObject.getLogger(ScheduleMainPlanCompRateUI.class);

	private static final String CHART_TITLE = "主项计划达成率";

	private static final String CHART_TYPE = FDCScheduleConstant.REPORT_CHART_SCHEDULE_TYPE_MAIN_PRJ;

	/**
	 * output class constructor
	 */
	public ScheduleMainPlanCompRateUI() throws Exception {
		super();
	}

	/**
	 * 描述：提供对外的接口，传入的图形的标题
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-12
	 */
	protected String getChartTitle() {

		return getProjectName() + CHART_TITLE;
	}

	/**
	 * 描述：提供对外的接口，传入的统计指标类型
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-12
	 */
	protected String getScheduleType() {

		return CHART_TYPE;
	}

}