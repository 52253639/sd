/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.report.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.schedule.FDCScheduleConstant;

/**
 * 描述:进度报表考核节点达成率界面
 * @author adward_huang  date:2012-10-17
 * @version EAS6.1
 */
public class ScheduleCheckNodeRateUI extends AbstractScheduleCheckNodeRateUI {

	private static final long serialVersionUID = 3697649841220425218L;

	private static final Logger logger = CoreUIObject.getLogger(ScheduleCheckNodeRateUI.class);

	private static final String CHART_TITLE = "考核节点达成率";

	public ScheduleCheckNodeRateUI() throws Exception {
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

		return FDCScheduleConstant.REPORT_CHART_SCHEDULE_TYPE_CHECK;
	}

}