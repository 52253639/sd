/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleFactory;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.DeptTaskProgressReportCollection;
import com.kingdee.eas.fdc.schedule.DeptTaskProgressReportFactory;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationCollection;
import com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationEntryFactory;
import com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationEntryInfo;
import com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationFactory;
import com.kingdee.eas.fdc.schedule.SelfAndFinalEvaluationInfo;
import com.kingdee.eas.fdc.schedule.framework.DateUtils;
import com.kingdee.eas.fdc.schedule.framework.client.KDTaskStatePanel;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * @(#)	DeptMonthlyTaskExecListUI.java 版权： 金蝶国际软件集团有限公司版权所有 描述：部门月度计划执行列表界面
 * @author 王维强
 * @version EAS7.0
 * @createDate 2011-9-7
 * @see
 */
public class DeptMonthlyTaskExecListUI extends AbstractDeptMonthlyTaskExecListUI {
	private static final Logger logger = CoreUIObject.getLogger(DeptMonthlyTaskExecListUI.class);
	public static final String strDataFormat = "#,##0.00;-#,##0.00";

	/**
	 * output class constructor
	 */
	public DeptMonthlyTaskExecListUI() throws Exception {
		super();
	}

	protected String getKeyFieldName() {
		return "id";
	}

	protected void initWorkButton() {
		super.initWorkButton();
		btnScheduleReport.setIcon(EASResource.getIcon("imgTbtn_execute"));
		btnSelfEvaluation.setIcon(EASResource.getIcon("imgTbtn_duizsetting"));
		btnFinalEvaluation.setIcon(EASResource.getIcon("imgTbtn_alreadycollate"));
		btnMonthlyReport.setIcon(EASResource.getIcon("imgTbtn_declarecollect"));
		btnOutputExcel.setIcon(EASResource.getIcon("imgTbtn_output"));
		btnScheduleReport.setEnabled(true);
		btnSelfEvaluation.setEnabled(true);
		btnFinalEvaluation.setEnabled(true);
		btnMonthlyReport.setEnabled(true);
		btnOutputExcel.setEnabled(true);

	}

	private void initF7() throws BOSException {
		String cuid = SysContext.getSysContext().getCurrentAdminUnit().getCU().getId().toString();
		FDCClientUtils.setRespDeptF7(prmtAdminDept, this);
		
		prmtAdminDept.setValue(SysContext.getSysContext().getCurrentAdminUnit());
		/*
		 * prmtAdminDept.setQueryInfo("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery"
		 * ); prmtAdminDept.setDisplayFormat("$name$");
		 * prmtAdminDept.setEditFormat("$name$");
		 * prmtAdminDept.setCommitFormat("$number$");
		 * prmtAdminDept.setEditable(false);
		 * prmtAdminDept.setValue(SysContext.getSysContext
		 * ().getCurrentAdminUnit());
		 */
		FilterInfo curProjectFilter = new FilterInfo();
		curProjectFilter.getFilterItems().add(
				new FilterItemInfo("fullOrgUnit.id", SysContext.getSysContext().getCurrentOrgUnit().getId().toString(), CompareType.EQUALS));
		prmtProject.getQueryAgent().getRuntimeEntityView().getFilter().mergeFilter(curProjectFilter, "and");
	}

	public void onLoad() throws Exception {

		super.onLoad();
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		initF7();
		pkScheduleMonth.setValue(new Date());
		pkScheduleMonth.setDatePattern("yyyy-MM");

		btnAttachment.setVisible(false);
		btnAuditResult.setVisible(false);

		// 状态面板说明
		KDTaskStatePanel pnlState = new KDTaskStatePanel(new String[] { "按时完成", "延时完成 ", "延时未完成" }, new String[] { "√", "√", "●" }, new Color[] {
				Color.GREEN, Color.RED, Color.RED }, new boolean[] { true, true, true });
		pnlState.setBounds(0, 0, pnlState.getWidth() + 20, pnlState.getHeight());
		this.logoPanel.add(pnlState);
		// tblMain.getStyleAttributes().setLocked(false);
		

	}

	/**
	 * 设置加权得分合计值
	 */
	private void getCountNum() {
		AdminOrgUnitInfo currentAdminUnit = (AdminOrgUnitInfo) this.prmtAdminDept.getValue();
		SelfAndFinalEvaluationCollection selfAndFinalEvaluationCollection = null;
		try {
			selfAndFinalEvaluationCollection = getSelfAndFinalCollection(currentAdminUnit, (Date) pkScheduleMonth.getValue());
		} catch (BOSException e) {
			e.printStackTrace();
		}
		// 自评加权得分
		Object selfEvaluationScore = "0";
		// 终评加权得分
		Object finalEvaluationScore = "0";
		if (selfAndFinalEvaluationCollection.size() != 0) {
			SelfAndFinalEvaluationInfo selfAndFinalEvaluationInfo = selfAndFinalEvaluationCollection.get(0);
			selfEvaluationScore = selfAndFinalEvaluationInfo.getSelfEvaluationScore();
			finalEvaluationScore = selfAndFinalEvaluationInfo.getFinalEvaluationScore();
		}

		this.tblMain.setRowCount(tblMain.getBody().getRows().size());
		FDCTableHelper.apendFootRow(tblMain, new String[] { "weightRate" });
		tblMain.getFootRow(0).getCell("weightRate").getStyleAttributes().setNumberFormat("#,##0");
		/*
		 * 设置合计行格式，以小数形式显示
		 */
		tblMain.getFootRow(0).getCell("selfEvaluationScore").getStyleAttributes().setNumberFormat(strDataFormat);
		tblMain.getFootRow(0).getCell("selfEvaluationScore").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getFootRow(0).getCell("finalEvaluationScore").getStyleAttributes().setNumberFormat(strDataFormat);
		tblMain.getFootRow(0).getCell("finalEvaluationScore").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		if (tblMain.getRowCount() != 0 && selfEvaluationScore != null) {
			tblMain.getFootRow(0).getCell("selfEvaluationScore").setValue(selfEvaluationScore);
		} else {
			tblMain.getFootRow(0).getCell("selfEvaluationScore").setValue("0.00");
		}
		if (tblMain.getRowCount() != 0 && finalEvaluationScore != null) {
			tblMain.getFootRow(0).getCell("finalEvaluationScore").setValue(finalEvaluationScore);
		} else {
			// tblMain.getFootRow(0).getCell("weightRate").setValue("");
			tblMain.getFootRow(0).getCell("finalEvaluationScore").setValue("0.00");
		}
	}

	private SelfAndFinalEvaluationCollection getSelfAndFinalCollection(AdminOrgUnitInfo adminDept, Date scheudleMonth) throws BOSException {

		// 计划月份最后一天
		Date firstMonth = DateUtils.startOfMonth(scheudleMonth);
		Date endMonth = DateUtils.endOfMonth(scheudleMonth);
		// Date scheudleMonth1 =scheudleMonth.+30;

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("adminDept.id", adminDept.getId(), CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("scheduleMonth", firstMonth, CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("scheduleMonth", endMonth, CompareType.LESS_EQUALS));

		view.setFilter(filter);
		SelfAndFinalEvaluationCollection selfAndFinalEvaluationCollection = SelfAndFinalEvaluationFactory.getRemoteInstance()
				.getSelfAndFinalEvaluationCollection(view);

		return selfAndFinalEvaluationCollection;
	}

	public void onShow() throws Exception {
		super.onShow();
		this.tblMain.getColumn("state").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.CENTER);
		this.tblMain.getColumn("weightRate").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("selfEvaluationScore").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("finalEvaluationScore").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("relatetaskid").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("finishStandard").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("taskState").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("progressEdition").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("intendEndDate").getStyleAttributes().setHided(true);
	}

	/**
	 * @describes
	 * @author 王维强
	 * @createDate 2011-11-12
	 */
	public void setScoreZero() throws BOSException, SQLException {
		if (tblMain.getRowCount() != 0) {
			for (int i = 0; i < tblMain.getRowCount(); i++) {
				IRow row = tblMain.getRow(i);
				String taskId = row.getCell("id").getValue().toString();
				FDCSQLBuilder sql = new FDCSQLBuilder();
				sql
						.appendSql(" select FSelfEvaluationScore as selfScore,FFinalEvaluationScore as finalScore from T_SCH_SelfAndFinalEvaluatEntry where FrelateTaskID ='"
								+ taskId + "'");
				IRowSet rs = null;
				rs = sql.executeQuery();
				String selfScore = "";
				String finalScore = "";
				while (rs.next()) {
					selfScore = rs.getString("selfScore");
					if (selfScore != null && selfScore.equals("0")) {
						row.getCell("selfEvaluationScore").setValue("0");
					}
					finalScore = rs.getString("finalScore");
					if (finalScore != null && finalScore.equals("0")) {
						row.getCell("finalEvaluationScore").setValue("0");
					}
					break;
				}
			}
		}
	}
	public boolean isAutoIgnoreZero() {
		return false;
	}

	protected void execQuery() {
		try {
			SorterItemCollection item = new SorterItemCollection();
			SorterItemInfo sorter = new SorterItemInfo("tasks.planFinishDate");
			sorter.setSortType(SortType.ASCEND);
			item.add(sorter);
			
			/* modified by zhaoqin for R140620-0109 on 2014/06/30 */
			SorterItemInfo seq = new SorterItemInfo("tasks.seq");
			seq.setSortType(SortType.ASCEND);
			item.add(seq);
			
			mainQuery.setFilter(getMainFilter());
			mainQuery.setSorter(item);

		} catch (Exception e) {
			e.printStackTrace();
		}
		super.execQuery();
		 removeRedundancyRows();
		initStateCell();
		getCountNum();
		try {
			this.setScoreZero();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @describes 删除冗余行数据
	 * @author 王维强
	 * @createDate 2011-11-16
	 */
	private void removeRedundancyRows() {
		try {
			for (int i = 0; i < tblMain.getRowCount(); i++) {
				
				String id = tblMain.getRow(i).getCell("id").getValue().toString();
				String version = tblMain.getRow(i).getCell("progressEdition").getValue().toString();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("relateTask", id, CompareType.EQUALS));
				view.setFilter(filter);
				DeptTaskProgressReportCollection reportCollection = DeptTaskProgressReportFactory.getRemoteInstance().getDeptTaskProgressReportCollection(view);
				if (reportCollection.size() != 0) {
					if(version.equals("false")){
						this.tblMain.removeRow(i);
						i--;
					}
				}
			}
			for (int m = 0; m < tblMain.getRowCount(); m++) {
				FDCSQLBuilder builder = new FDCSQLBuilder();
				IRowSet rowSet = null;
				String complete = null;
				String infoid = tblMain.getRow(m).getCell("id").getValue().toString();
				builder.appendSql("select FcompletePrecent as complete from T_SCH_DeptTaskProgressReport where FRelateTaskID = '" + infoid
						+ "' and FProgressEdition = '1'");
				rowSet = builder.executeQuery();
				builder.clear();
				try {
					while (rowSet.next()) {
						complete = rowSet.getString("complete");
					}
					if (complete != null && complete.equals("0")) {
						tblMain.getRow(m).getCell("completePrecent").setValue(new BigDecimal(0));
					} else {
						builder.appendSql("select FComplete as complete from T_SCH_DeptMonthlyScheduleTask where FID = '" + infoid + "'");
						rowSet = builder.executeQuery();
						while (rowSet.next()) {
							complete = rowSet.getString("complete");
							if (complete != null) {
								tblMain.getRow(m).getCell("completePrecent").setValue(new BigDecimal(complete.toString()));
							}
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				handleTaskTypeCell();
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * @return
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("progressReport.completePrecent"));
		return sic;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	protected ICoreBase getBizInterface() throws Exception {
		return DeptMonthlyScheduleFactory.getRemoteInstance();
	}

	/**
	 * @return
	 */
	protected String getEditUIName() {
		return DeptMonthlyTaskExecEditUI.class.getName();
	}

	/**
	 * 计划月份值改变事件 by 李建波
	 */
	protected void pkScheduleMonth_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception {
		super.pkScheduleMonth_dataChanged(e);
		execQuery();
	}

	/**
	 * 责任部门值改变事件 by 李建波
	 */
	protected void prmtAdminDept_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception {
		super.prmtAdminDept_dataChanged(e);
		execQuery();

	}

	/**
	 * 
	 * @description 查询数据，填充
	 * @author 李健波
	 * @createDate 2011-8-20 void
	 * @version EAS7.0
	 * @see
	 */
	private FilterInfo getMainFilter() {
		AdminOrgUnitInfo adminDept = (AdminOrgUnitInfo) prmtAdminDept.getValue();
		CurProjectInfo project = (CurProjectInfo) prmtProject.getValue();
		Calendar cal = Calendar.getInstance();
		Calendar pkCal = Calendar.getInstance();
		pkCal.setTime((Date) pkScheduleMonth.getValue());
		int year = pkCal.get(Calendar.YEAR);
		int month = pkCal.get(Calendar.MONTH);
		Date date = new Date();
		cal.setTime(date);
		cal.set(year, month, 1, 0, 0, 0);
		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(new FilterItemInfo("adminDept.id", adminDept.getId()));
		filter.getFilterItems().add(new FilterItemInfo("year", new Integer(year), CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("month", month + 1, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED.getValue(), CompareType.EQUALS));
		
		if (project != null) {
			filter.getFilterItems().add(new FilterItemInfo("tasks.project.id", project.getId()));
		}

		SorterItemCollection sort = new SorterItemCollection();
		SorterItemInfo sort1 = new SorterItemInfo("createTime");
		sort1.setSortType(SortType.DESCEND);
		sort.add(sort1);
		return filter;
	}

	/**
	 * 打开进度汇报窗口
	 * 
	 * @throws Exception
	 */
	private void openScheduleReportWindow() throws Exception {
		AdminOrgUnitInfo adminOrgUnitInfo = (AdminOrgUnitInfo) this.prmtAdminDept.getValue();
		Date scheudleMonthStr = (Date) this.pkScheduleMonth.getValue();
		Map uiContext = getUIContext();
		uiContext.put("adminDept", adminOrgUnitInfo);
		uiContext.put("Owner2", this);
		uiContext.put("scheduleMonth", scheudleMonthStr);
		String editUIName = getEditUIName();
		int i = tblMain.getSelectManager().getActiveRowIndex();
		if (i < 0) {
			MsgBox.showWarning(this, "任务列表为空！");
			return;
		}

		IRow row = tblMain.getRow(i);
		String projectId = row.getCell("id").getValue().toString();
		BigDecimal completePrecentTemp = (BigDecimal) row.getCell("completePrecent").getValue();
		String completePrecent = completePrecentTemp == null ? "0" : String.valueOf(completePrecentTemp.intValue());
		Date realEndDate = (Date) row.getCell("realEndDate").getValue();
		Date realStartDate = (Date) row.getCell("realStartDate").getValue();
		Date intendEndDate = (Date) row.getCell("intendEndDate").getValue();
		// uiContext.put("taskId", projectId);

		uiContext.put(UIContext.ID, projectId);
		uiContext.put("completePrecent", completePrecent);
		uiContext.put("realEndDate", realEndDate);
		uiContext.put("realStartDate", realStartDate);
		uiContext.put("intendEndDate", intendEndDate);
		uiContext.put("rid", projectId);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(editUIName, uiContext, null, OprtState.VIEW);
		uiWindow.show();

	}

	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 2) {
			openScheduleReportWindow();
		}
	}

	/**
	 * @param e
	 * @throws Exception
	 */

	/**
	 * 直接进入进度汇报
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionScheduleReport_actionPerformed(ActionEvent e) throws Exception {
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row == null) {
			MsgBox.showWarning(this, "请选择任务行！");
			return;
		}
		String projectId = row.getCell("id").getValue().toString();
		Map uiContext = getUIContext();
		uiContext.put("Owner", this);
		uiContext.put("rid", projectId);
		uiContext.put("selectRow", row);
		uiContext.put("isReport", Boolean.FALSE);
		DeptMonthlyScheduleTaskInfo relateTask = DeptMonthlyScheduleTaskFactory.getRemoteInstance().getDeptMonthlyScheduleTaskInfo(
				new ObjectStringPK(projectId));
		uiContext.put("taskInfo", relateTask);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		String editUIName = DeptTaskProgressReportEditUI.class.getName();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("relateTask.id", projectId.toString());
		view.setFilter(filter);
		DeptTaskProgressReportCollection col = DeptTaskProgressReportFactory.getRemoteInstance().getDeptTaskProgressReportCollection(view);
		String state = OprtState.ADDNEW;
		if (col != null && col.size() > 0) {
			uiContext.put("isReport", Boolean.TRUE);
			// state = OprtState.EDIT;
			uiContext.put(UIContext.ID, col.get(0).getId().toString());
		}
		IUIWindow uiWindow = uiFactory.create(editUIName, uiContext, null, state);
		uiWindow.show();
	}

	/**
	 * 自评
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionSelfEvaluation_actionPerformed(ActionEvent e) throws Exception {
		String editUIName = SelfEvaluationEditUI.class.getName();
		selfAndFinalEvaluationInfo(editUIName);

	}

	/**
	 * 终评
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionFinalEvaluation_actionPerformed(ActionEvent e) throws Exception {
		String editUIName = FinalEvaluationEditUI.class.getName();
		selfAndFinalEvaluationInfo(editUIName);

	}

	/**
	 * 打开自评/终评界面
	 * 
	 * @param editUIName
	 * @throws BOSException
	 */
	private void selfAndFinalEvaluationInfo(String editUIName) throws BOSException {
		if (tblMain.getRowCount() == 0) {
			MsgBox.showInfo("任务列表为空!");
			return;
		}
		Map uiContext = getUIContext();
		// 取得当前部门和计划月份
		AdminOrgUnitInfo adminDept = (AdminOrgUnitInfo) prmtAdminDept.getValue();
		Date scheudleMonth = (Date) this.pkScheduleMonth.getValue();
		UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
		String scheudleMonthStr = this.pkScheduleMonth.getText();
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		SelfAndFinalEvaluationCollection selfAndFinalEvaluationCollection = getSelfAndFinalCollection(adminDept, scheudleMonth);
		if (selfAndFinalEvaluationCollection.size() != 0) {
			SelfAndFinalEvaluationInfo selfAndFinalEvaluationInfo = selfAndFinalEvaluationCollection.get(0);
			uiContext.put(UIContext.ID, selfAndFinalEvaluationInfo.getId().toString());
			uiContext.put("state", selfAndFinalEvaluationInfo.getState());
			uiContext.put("Owner", this);
			uiContext.put("adminDept", adminDept);
			uiContext.put("scheudleMonth", scheudleMonth);
			uiContext.put("scheudleMonthStr", scheudleMonthStr);
			uiContext.put("userInfo", userInfo);
			uiContext.put("tableMain", tblMain);
			// 任务条数
			int mm = tblMain.getRowCount();
			// 分录条数
			String infoid = selfAndFinalEvaluationInfo.getId().toString();
			SelfAndFinalEvaluationInfo info2;
			try {
				info2 = SelfAndFinalEvaluationFactory.getRemoteInstance().getSelfAndFinalEvaluationInfo(new ObjectUuidPK(infoid));
				int mm2 = info2.getEntries().size();
				if (mm != mm2) {
					for (int x = 0; x < tblMain.getRowCount(); x++) {
						String strID = tblMain.getRow(x).getCell("id").getValue().toString();
						FilterInfo finfo = new FilterInfo();
						finfo.getFilterItems().add(new FilterItemInfo("relateTask.id", strID));

						if (SelfAndFinalEvaluationEntryFactory.getRemoteInstance().exists(finfo)) {
							continue;
						} else {
							SelfAndFinalEvaluationEntryInfo entryInfo = new SelfAndFinalEvaluationEntryInfo();
							DeptMonthlyScheduleTaskInfo schTask = DeptMonthlyScheduleTaskFactory.getRemoteInstance().getDeptMonthlyScheduleTaskInfo(
									new ObjectUuidPK(strID));
							SelfAndFinalEvaluationInfo pinfo = new SelfAndFinalEvaluationInfo();
							pinfo.setId(selfAndFinalEvaluationInfo.getId());
							entryInfo.setParent(pinfo);
							entryInfo.setRelateTask(schTask);
							SelfAndFinalEvaluationEntryFactory.getRemoteInstance().addnew(entryInfo);
						}

					}
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			}

			IUIWindow uiWindow = null;
			uiWindow = uiFactory.create(editUIName, uiContext, null, OprtState.EDIT);
			uiWindow.show();
		} else {
			uiContext.put("Owner", this);
			uiContext.put("adminDept", adminDept);
			uiContext.put("scheudleMonth", scheudleMonth);
			uiContext.put("scheudleMonthStr", scheudleMonthStr);
			uiContext.put("userInfo", userInfo);
			uiContext.put("tableMain", tblMain);
			IUIWindow uiWindow = uiFactory.create(editUIName, uiContext, null, OprtState.ADDNEW);
			uiWindow.show();
		}
	}

	/**
	 * 部门月度报告
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionMonthlyReport_actionPerformed(ActionEvent e) throws Exception {
		AdminOrgUnitInfo adminOrgUnitInfo = (AdminOrgUnitInfo) this.prmtAdminDept.getValue();
		String scheduleMonthStr = this.pkScheduleMonth.getText();
		Map uiContext = getUIContext();
		uiContext.put("adminDept", adminOrgUnitInfo);
		uiContext.put("scheduleMonth", scheduleMonthStr);
		if (tblMain.getRowCount() == 0) {
			MsgBox.showInfo("任务列表为空!");
			return;
		}
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
		IUIWindow uiWindow = uiFactory.create(DeptMonthlyTaskReportUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}

	/**
	 * 导出exceonload
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionOutputExcel_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		this.tblMain.getColumn("realStartDate").getStyleAttributes().setHided(false);
		List tables = new ArrayList();
		tables.add(new Object[] { "任务列表", this.tblMain });
		FDCTableHelper.exportExcel(this, tables);
		this.tblMain.getColumn("realStartDate").getStyleAttributes().setHided(true);
	}

	/**
	 * @param uiContext
	 * @param e
	 */
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		e.getSource();
		super.prepareUIContext(uiContext, e);
		AdminOrgUnitInfo adminOrgUnitInfo = (AdminOrgUnitInfo) this.prmtAdminDept.getValue();
		Date scheudleMonthStr = (Date) this.pkScheduleMonth.getValue();
		uiContext.put("adminDept", adminOrgUnitInfo);
		uiContext.put("scheduleMonth", scheudleMonthStr);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		super.tblMain_tableSelectChanged(e);
	}

	/*
	 * 展示的时候，循环表格行，根据该隐藏列的值，设置显示的stateLogo列的展示方式。<br>
	 * 
	 * 注意：<br> 1、打钩的单元格需要设置字体为粗体，否则钩太细，画圈的则不需要；<br>
	 * 2、颜色未使用标准的Color.Green和Color.Orange，而使用了新构建的近似颜色代替，<br>
	 * 是因为纯色太亮，容易刺眼，大量使用时需减少亮度。
	 */
	public void initStateCell() {
		String rsPath = "com.kingdee.eas.fdc.schedule.ScheduleResource";
		// 勾
		String achieve = EASResource.getString(rsPath, "achieve");
		// 圈
		String pending = EASResource.getString(rsPath, "pending");
		// 红
		Color red = new Color(245, 0, 0);
		// 绿
		Color green = new Color(10, 220, 10);
		// 橙
		// Color orange = new Color(220, 180, 0);

		tblMain.checkParsed();
		String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String planDate = null;
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			if (row != null) {
				BigDecimal complete = new BigDecimal("0");
				if (row.getCell("completePrecent").getValue() != null) {
					complete = new BigDecimal(row.getCell("completePrecent").getValue().toString());
				}
				int state = ((Integer) row.getCell("taskState").getValue()).intValue();
				planDate = new SimpleDateFormat("yyyy-MM-dd").format(row.getCell("planEndDate").getValue());
				// 计划完成日期大于等于当前日期且未完成的任务状态为空
				if (complete.compareTo(new BigDecimal(100)) == -1 && planDate.compareTo(nowDate) >= 0) {
					state = 3;
				}
				
				
				switch (state) {
				case 0:
					row.getCell("state").setValue(achieve);
					row.getCell("state").getStyleAttributes().setBold(true);
					row.getCell("state").getStyleAttributes().setFontColor(green);
					break;
				case 1:
					row.getCell("state").setValue(achieve);
					row.getCell("state").getStyleAttributes().setBold(true);
					row.getCell("state").getStyleAttributes().setFontColor(red);
					break;
				case 2:
					row.getCell("state").setValue(pending);
					row.getCell("state").getStyleAttributes().setFontColor(red);
					break;
				case 3:
					row.getCell("state").setValue(null);
					row.getCell("state").getStyleAttributes().setFontColor(null);
					break;
				}
			}
		}
	}

	/**
	 * @describes 对日常任务处理
	 * @author 王维强
	 * @createDate 2011-11-9
	 */
	private void handleTaskTypeCell() {
		for (int i = 0; i < tblMain.getRowCount(); i++) {
			IRow row = tblMain.getRow(i);
			Object value = row.getCell("taskType").getValue();
			if (value == null) {
				row.getCell("taskType").setValue(RESchTaskTypeEnum.NORMAL);
			}
		}
	}

	public void loadFields() {
		super.loadFields();
	}
	
	@Override
	protected void prmtProject_dataChanged(DataChangeEvent e) throws Exception {
		execQuery();
	}

	@Override
	protected void pkStartDate_dataChanged(DataChangeEvent e) throws Exception {
		/* TODO 自动生成方法存根 */
		super.pkStartDate_dataChanged(e);
	}

	@Override
	protected void pkEndDate_dataChanged(DataChangeEvent e) throws Exception {
		/* TODO 自动生成方法存根 */
		super.pkEndDate_dataChanged(e);
	}

}