/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.report.client;

import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.schedule.ScheduleChartHelper;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 描述:进度报表查询过滤界面
 * @author adward_huang  date:2012-10-17
 * @version EAS6.1
 */ 
@SuppressWarnings("deprecation")
public class ScheduleReportQueryPanelUI extends AbstractScheduleReportQueryPanelUI {

	private static final long serialVersionUID = 2742191723711223558L;

	private static final Logger logger = CoreUIObject.getLogger(ScheduleReportQueryPanelUI.class);

	// 进度报告选择工程项目窗口
	private F7ScheduleReportDialog orgDialog = null;

	// 所有进度报告基类对象
	private ScheduleReportBaseUI baseUI = null;

	// 判断是否为修改操作
	private boolean isUpdate = false;

	/**
	 * output class constructor
	 */
	public ScheduleReportQueryPanelUI() throws Exception {
		super();
	}

	/**
	 * 描述：将父窗体对象传递到子窗体中
	 * @param baseUI
	 * @Author：adward_huang
	 * @CreateTime：2012-9-18
	 */
	public void setScheduleReportBaseUI(ScheduleReportBaseUI baseUI, boolean isUpdate) {
		this.baseUI = baseUI;
		this.isUpdate = isUpdate;
	}

	/**
	 * @see com.kingdee.eas.fdc.schedule.client.report.AbstractScheduleReportQueryPanelUI#actionSelectOrg_actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionSelectOrg_actionPerformed(ActionEvent e) throws Exception {
		/* TODO 自动生成方法存根 */
		super.actionSelectOrg_actionPerformed(e);

		// 弹出选择组织项目窗口
		Map<String, Map<String, String>> selectOrg = selectOrgDialogData();

		// 填充选择范围文本控件，显示组织名和项目名
		//		fillQueryRangeTxtByOrgName(selectOrg);
		String selectValue = getSelectValue();
		String showProjectName = orgDialog.getProjectName();
		this.orgIDList.setText(selectValue);
		this.checkedIdsArea.setText(ScheduleChartHelper.getMapToString(selectOrg));
		this.projectNameTxtField.setText(showProjectName);
	}

	/**
	 * 描述：根据选择的组织及工程项目来填充查询范围组建Text
	 * @param map
	 * @Author：adward_huang
	 * @CreateTime：2012-9-18
	 */
	@SuppressWarnings("unused")
	private void fillQueryRangeTxtByOrgName(Map<String, String> map) {

		if (map == null || map.isEmpty()) {
			return;
		}
		Iterator<Map.Entry<String, String>> iter = map.entrySet().iterator();
		int index = 0;
		int size = map.size();
		StringBuffer fillOrgName = new StringBuffer(100);
		// 拼接组织名称
		while (iter.hasNext()) {
			Map.Entry<String, String> entryMap = iter.next();
			index++;
			String name = entryMap.getValue();
			if (index == size) {
				fillOrgName.append(name);
			} else {
				fillOrgName.append(name).append(";");
			}
		}

		this.orgIDList.setText(fillOrgName.toString());
	}

	/**
	 * @see com.kingdee.eas.base.commonquery.client.CustomerQueryPanel#verify()
	 */
	@Override
	public boolean verify() {

		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		if (para.isNotNull("endQueryDate") && para.isNotNull("beginQueryDate")) {
			if (para.getDate("endQueryDate").before(para.getDate("beginQueryDate"))) {
				MsgBox.showWarning(this, "开始日期不能大于结束日期，请重新选择！");
				return false;
			}
		}

		if (para.isNull("orgIDs")) {
			MsgBox.showWarning(this, "查询范围不能为空，请选择查询范围！");
			return false;
		}

		try {
			rebuildBaseUI();
		} catch (Exception e) {
			/* TODO 自动生成 catch 块 */
			this.handUIException(e);
		}

		return true;
	}

	/**
	 * @see com.kingdee.eas.base.commonquery.client.CustomerQueryPanel#getCustomerParams()
	 */
	public CustomerParams getCustomerParams() {

		FDCCustomerParams param = new FDCCustomerParams();

		Date startDate = (Date) this.startDate.getValue();
		Date endDate = (Date) this.endDate.getValue();
		String orgIDs = this.orgIDList.getText();
		String data = this.checkedIdsArea.getText();
		String projectName = this.projectNameTxtField.getText();
		if (!FDCHelper.isEmpty(startDate)) {
			param.add("beginQueryDate", startDate);
		}
		if (!FDCHelper.isEmpty(endDate)) {
			param.add("endQueryDate", endDate);
		}

		if (!FDCHelper.isEmpty(orgIDs)) {
			param.add("orgIDs", orgIDs);
		}

		if (!FDCHelper.isEmpty(orgIDs)) {
			param.add("data", data);
		}
		
		if (!FDCHelper.isEmpty(projectName)) {
			param.add("projectName", projectName);
		}

		return param.getCustomerParams();
	}

	/**
	 * @see com.kingdee.eas.base.commonquery.client.CustomerQueryPanel#setCustomerParams(com.kingdee.eas.base.commonquery.client.CustomerParams)
	 */
	public void setCustomerParams(CustomerParams cp) {

		if (cp == null)
			return;

		FDCCustomerParams para = new FDCCustomerParams(cp);
		this.orgIDList.setText(para.get("orgIDs"));
		this.startDate.setValue(para.getDate("beginQueryDate"));
		this.endDate.setValue(para.getDate("endQueryDate"));
		this.checkedIdsArea.setText(para.getString("data"));
		this.projectNameTxtField.setText(para.getString("projectName"));
		super.setCustomerParams(para.getCustomerParams());
	}

	/**
	 * 描述：重绘报表基类
	 * @throws Exception 
	 * @Author：adward_huang
	 * @CreateTime：2012-9-18
	 */
	private void rebuildBaseUI() throws Exception {
		Date startDate = (Date) this.startDate.getValue();
		Date endDate = (Date) this.endDate.getValue();
		//		Map<String,Map<String, String>> selectOrg = orgDialog.getData();
		String data = checkedIdsArea.getText().toString();
		Map<String, Map<String, String>> selectOrg = ScheduleChartHelper.getStringToMap(data);
		String projectName = projectNameTxtField.getText().toString();
		if (this.baseUI == null) {
			return;
		} else {
			this.baseUI.setSelectorAllValue(selectOrg, startDate, endDate, projectName, null);

			// 重绘父窗体
			if (isUpdate) {
				this.baseUI.buildTableAndChartPanel();
			}
		}
	}

	/**
	 * 描述：显示工程树弹出窗体及获取窗体所选择的组织和工程
	 * @throws Exception
	 * @Author：adward_huang
	 * @CreateTime：2012-9-18
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Map<String, String>> selectOrgDialogData() throws Exception {

		ScheduleReportOrgQueryUI orgUI = new ScheduleReportOrgQueryUI();
		orgDialog = new F7ScheduleReportDialog(orgUI);
		// 设置该窗体用于选择报表组织结构数据
		orgDialog.show();

		Map<String, Map<String, String>> returnDataMap = (Map<String, Map<String, String>>) orgDialog.getData();
		//		if(ScheduleReportOrgQueryUI.DATA_FROM_REPORT.equals(orgDialog.getDataType().toString())){
		//			returnDataMap = (Map<String,Map<String, String>>)orgDialog.getData();
		//		}
		return returnDataMap;
	}

	/**
	 * 描述：拼接工程名称和项目名称
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-20
	 */
	private String getSelectValue() {
		List<String> list = orgDialog.getSelectProAndOrgList();
		if (list == null || list.isEmpty()) {
			return "";
		}
		StringBuffer sb = new StringBuffer(100);
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				sb.append(list.get(i));
			} else {
				sb.append(list.get(i)).append(";");
			}
		}
		return sb.toString();
	}
	
}