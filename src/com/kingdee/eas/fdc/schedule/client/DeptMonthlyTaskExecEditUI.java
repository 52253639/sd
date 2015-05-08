/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleCollection;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleFactory;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleInfo;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.DeptTaskProgressReportCollection;
import com.kingdee.eas.fdc.schedule.DeptTaskProgressReportFactory;
import com.kingdee.eas.fdc.schedule.DeptTaskProgressReportInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class DeptMonthlyTaskExecEditUI extends AbstractDeptMonthlyTaskExecEditUI {
	private static final Logger logger = CoreUIObject.getLogger(DeptMonthlyTaskExecEditUI.class);

	/**
	 * output class constructor
	 */
	public DeptMonthlyTaskExecEditUI() throws Exception {
		super();
	}

	/**
	 * @return
	 * @throws Exception
	 */
	protected ICoreBase getBizInterface() throws Exception {
		return DeptMonthlyScheduleTaskFactory.getRemoteInstance();
	}
	protected void initWorkButton() {
		super.initWorkButton();
	}

	/**
	 * @throws Exception
	 */
	public void onLoad() throws Exception {
		super.onLoad();
		this.kDSchReport.addButton(btnSchReport);
		this.kDSchReport.addButton(btnSchDelete);
		this.windowTitle = "任务信息";
	}
	
	
	/**
	 * 进度汇报
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionSchReport_actionPerformed(ActionEvent e) throws Exception {
		String TaskId = (String) getUIContext().get("rid");
		Map uiContext = getUIContext();
		DeptMonthlyScheduleTaskInfo taskInfo = editData;
		uiContext.put(UIContext.ID, this);
		uiContext.put("Owner", this);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("relateTask.id", TaskId, CompareType.EQUALS));
		view.setFilter(filter);
		String comText = txtCompletePrecent.getText();
		DeptTaskProgressReportCollection reportInfo = DeptTaskProgressReportFactory.getRemoteInstance().getDeptTaskProgressReportCollection(view);
		if (reportInfo != null) {
			uiContext.put("isReport", Boolean.TRUE);
		} else {
			uiContext.put("isReport", Boolean.FALSE);
		}
		Date planEndDate = (Date) this.pkPlanEndDate.getValue();
		uiContext.put("rid", TaskId);
		// uiContext.put("taskInfo", taskInfo.getRelatedTask());
		uiContext.put("OwnerParent", getUIContext().get("Owner2"));
		uiContext.put("taskInfo", taskInfo);
		uiContext.put("table", schTable);
		uiContext.put("editData", editData);
		uiContext.put("planEndDate", planEndDate);
		uiContext.put("selectRow", null);
		uiContext.put("comText", comText);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		String editUIName = DeptTaskProgressReportEditUI.class.getName();
		IUIWindow uiWindow = uiFactory.create(editUIName, uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}
	/**
	 * @return
	 */
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectors = super.getSelectors();
		selectors.add("relateTask.id");
		selectors.add("relateTask.name");
		selectors.add("relateTask.number");
		selectors.add("adminDept.id");
		selectors.add("year");
		selectors.add("month");
		selectors.add("state");
		// libing
		selectors.add("relateTask.schedule.project.name");
		return selectors;
	}
	public void refreshValue(DeptTaskProgressReportInfo info) {
		if (info != null && info.getCompletePrecent() != null) {
			this.txtCompletePrecent.setText(info.getCompletePrecent().toString());
		}
		this.realEndDate.setValue(info.getRealEndDate());
		this.realStartDate.setValue(info.getRealStartDate());
		Date PlanEndDate = (Date) this.pkPlanEndDate.getValue();
		if (realEndDate.getValue() != null) {
			this.intendEndDate.setValue(info.getRealEndDate());
		} else {
			this.intendEndDate.setValue(PlanEndDate);
		}
	}
	/**
	 * @throws Exception
	 */
	public void onShow() throws Exception {
		super.onShow();
		String completePrecent = getUIContext().get("completePrecent").toString();
		Date realEndDate = (Date) getUIContext().get("realEndDate");
		Date realStartDate = (Date) getUIContext().get("realStartDate");
		this.txtCompletePrecent.setText(completePrecent);
		this.realEndDate.setValue(realEndDate);
		this.realStartDate.setValue(realStartDate);
		Date planEndDate = (Date) this.pkPlanEndDate.getValue();
		if (realEndDate != null) {
			this.intendEndDate.setValue(realEndDate);
		} else {
			this.intendEndDate.setValue(planEndDate);
		}
		//schTable.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT)
		// ;
		 btnExit.setVisible(false);
	}

	/**
	 * 删除进度
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionSchDelete_actionPerformed(ActionEvent e) throws Exception {
		
		ArrayList blocks = schTable.getSelectManager().getBlocks();
		ArrayList idList = new ArrayList();
		Iterator iter = blocks.iterator();
		if (schTable.getSelectManager().getActiveRowIndex() == -1) {
			MsgBox.showWarning("请先选择需要删除的进度汇报行！");
			SysUtil.abort();
		} else {
			if (isRemove()) {
				while (iter.hasNext()) {
					KDTSelectBlock block = (KDTSelectBlock) iter.next();
					int top = block.getTop();
					int bottom = block.getBottom();

					for (int rowIndex = top; rowIndex <= bottom; rowIndex++) {
						ICell cell = schTable.getRow(rowIndex).getCell("id");
						if (!idList.contains(cell.getValue())) {
							idList.add(cell.getValue());
						}
					}
				}
				String[] listId = null;
				if (idList != null && idList.size() > 0) {
					Iterator iterat = idList.iterator();
					listId = new String[idList.size()];
					int index = 0;
					while (iterat.hasNext()) {
						listId[index] = (String) iterat.next();
						index++;
					}
				}
				ObjectUuidPK[] pk = new ObjectUuidPK[listId.length];
				String complete = "";
				for (int i = 0; i < listId.length; i++) {
					pk[i] = new ObjectUuidPK(listId[i]);
					/*DeptTaskProgressReportInfo reportInfo = DeptTaskProgressReportFactory.getRemoteInstance().getDeptTaskProgressReportInfo(pk[i]);
					if (reportInfo != null) {
						complete = reportInfo.getCompletePrecent().toString();
						if (complete.equals("100")) {
							FDCSQLBuilder builder = new FDCSQLBuilder();
							if (editData.getRelatedTask() != null) {
								builder.appendSql("update T_SCH_FDCScheduleTask set FactualEndDate = '' where FID='"
										+ editData.getRelatedTask().getId().toString() + "'");//{ts'" + "" + "'}
								builder.executeUpdate();
							}
							realEndDate.setValue(null);
						}
					}*/
				}
				DeptTaskProgressReportFactory.getRemoteInstance().delete(pk);
			}
			fillProgressTable();
			int rowCount = schTable.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				IRow row = schTable.getRow(i);
				// String id = row.getCell("id").getValue().toString();
				String version = row.getCell("progressEdition").getValue().toString();
				if (version.equals("1")) {
					return;
				} else {
					reverseData();
				}
			}
			if (rowCount == 0) {
				this.realStartDate.setValue(null);
				reverseData();
			}
		}
	}

	private void reverseData() throws ParseException {
		try {
		
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.appendSql("update T_SCH_DeptTaskProgressReport set FProgressEdition=1 where fid=");
				builder.appendSql("(select fid from T_SCH_DeptTaskProgressReport where freportdate=");
				builder.appendSql("(select max(freportdate) from T_SCH_DeptTaskProgressReport where frelatetaskid='" + editData.getId().toString()
						+ "'))");
				builder.executeUpdate();
				FDCSQLBuilder builder1 = new FDCSQLBuilder();
				builder1
					.appendSql("(select fcompletePrecent as complete ,FRealStartDate as startDate from T_SCH_DeptTaskProgressReport where  frelatetaskid='"
					+ editData.getId().toString() + "' and fprogressEdition='1')");
			IRowSet rs = builder1.executeQuery();
			
			String complete = "";
			if (rs != null) {
				try {
					while (rs.next()) {
						complete = rs.getString("complete");
						if (rs.getString("startDate") != null) {
							String start = rs.getString("startDate");
							SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
							Date startDate = df.parse(start);
							this.realStartDate.setValue(startDate);
						} else {
							this.realStartDate.setValue(null);
						}
						this.txtCompletePrecent.setText(complete);
						this.realEndDate.setValue(null);
						this.intendEndDate.setValue(this.pkPlanEndDate.getValue());
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} 
			DeptMonthlyScheduleTaskInfo taskInfo = editData;
			String tid = "";
			if (taskInfo.getId() != null) {
				tid = taskInfo.getId().toString();
			}
			
			if (schTable.getRowCount() == 0) {
				Map upTask = new HashMap();
				AdminOrgUnitInfo adminDept = (AdminOrgUnitInfo) txtAdminDept.getValue();
				String scheduleMonth = pkScheduleMonth.getText();
				String yearStr = scheduleMonth.substring(0, scheduleMonth.indexOf("-"));
				String monthStr = scheduleMonth.substring(scheduleMonth.indexOf("-") + 1);
				String taskName = this.txtTaskName.getText();
				int upMonth = new Integer(monthStr).intValue() - 1;
				int upYear = new Integer(yearStr).intValue();
				if (upMonth == 0) {
					upMonth = 12;
					upYear = upYear - 1;
				}
				String upMonthStr = new Integer(upMonth).toString();
				String upYearStr = new Integer(upYear).toString();
				DeptMonthlyScheduleCollection deptMonthlyScheduleTasks = getDeptMonthlyScheduleTasks(adminDept, upYearStr, upMonthStr);
				if (deptMonthlyScheduleTasks.size() > 0) {
						DeptMonthlyScheduleInfo deptMonthlyScheduleInfo = deptMonthlyScheduleTasks.get(0);
					if (deptMonthlyScheduleInfo.getTasks().size() > 0) {
						for (int i = 0; i < deptMonthlyScheduleInfo.getTasks().size(); i++) {
							DeptMonthlyScheduleTaskInfo deptMonthlyScheduleTaskInfo = deptMonthlyScheduleInfo.getTasks().get(i);
							upTask.put(deptMonthlyScheduleTaskInfo.getTaskName(), deptMonthlyScheduleTaskInfo.getComplete());
						}
					}
				}
				if (upTask.containsKey(taskName)) {
					complete = upTask.get(taskName).toString();
					this.txtCompletePrecent.setText(complete);
					if (!complete.equals("100")) {
						this.realEndDate.setValue(null);
						this.intendEndDate.setValue(this.pkPlanEndDate.getValue());
					}
				} else {
					this.txtCompletePrecent.setText("");
					this.realEndDate.setValue(null);
					this.intendEndDate.setValue(this.pkPlanEndDate.getValue());
				}

			}
			FDCSQLBuilder builderTask = new FDCSQLBuilder();
			builderTask.appendSql("update T_SCH_DeptMonthlyScheduleTask set FComplete = '" + complete + "' where FID='" + tid + "'");
			builderTask.executeUpdate();
			builder.clear();
			/*if (editData.getRelatedTask() != null) {
				builder.appendSql("update T_SCH_FDCScheduleTask set FComplete = '" + complete + "' where FID='"
						+ editData.getRelatedTask().getId().toString() + "'");
				builder.executeUpdate();
			}*/
				
				
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		
	}

	private DeptMonthlyScheduleCollection getDeptMonthlyScheduleTasks(AdminOrgUnitInfo adminDept, String year, String month) throws BOSException {

		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("tasks.*"));
		EntityViewInfo view = new EntityViewInfo();
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("adminDept.id", adminDept.getId(), CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("year", year, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("month", month, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED.getValue(), CompareType.EQUALS));
		view.setFilter(filter);
		DeptMonthlyScheduleCollection taskCollection = DeptMonthlyScheduleFactory.getRemoteInstance().getDeptMonthlyScheduleCollection(
				view);
		return taskCollection;
	}
	protected boolean isRemove() {
		int d = FDCMsgBox.showConfirm2(this, "是否删除所选进度汇报？");
		if (d == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param e
	 * @throws Exception
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
	}
	
	public void initUIToolBarLayout() {
		super.initUIToolBarLayout();
	}
	
	public void loadFields() {
		super.loadFields();
		this.txtAdminDept.setValue(getUIContext().get("adminDept"));
		this.pkScheduleMonth.setValue(getUIContext().get("scheduleMonth"));
		this.pkScheduleMonth.setDatePattern("yyyy-MM");
		fillProgressTable();
	}

	/**
	 * 获取进度汇报分录数据
	 */
	private void fillProgressTable() {
		String taskId = editData.get("id").toString();
		schTable.removeRows();
		// 查询数据
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("reportor.*");
		sic.add("reportDate");
		sic.add("completePrecent");
		sic.add("description");
		sic.add("progressEdition");
		view.setSelector(sic);
		// 根据任务ID过滤条件
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("relateTask.id", taskId, CompareType.INCLUDE));
		view.setFilter(filter);
		try {
			setTableValue(view);
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
	}
	/**
	 * 将进度汇报分录填充到table中
	 * 
	 * @param view
	 * @throws BOSException
	 */
	private void setTableValue(EntityViewInfo view) throws BOSException {
		DeptTaskProgressReportCollection col = DeptTaskProgressReportFactory.getRemoteInstance().getDeptTaskProgressReportCollection(view);
		if (col != null && col.size() > 0) {
			this.schTable.checkParsed();
			this.schTable.refresh();
			for (int i = 0; i < col.size(); i++) {
				DeptTaskProgressReportInfo prInfo = col.get(i);
				IRow row = this.schTable.addRow();
				row.getCell("id").setValue(prInfo.getId().toString());
				row.getCell("reportor").setValue(prInfo.getReportor());
				row.getCell("reportDate").setValue(prInfo.getReportDate());
				row.getCell("completePrecent").setValue(prInfo.getCompletePrecent());
				row.getCell("finishDescirption").setValue(prInfo.getDescription());
				boolean edition = prInfo.isProgressEdition();
				if (edition) {
					row.getCell("progressEdition").setValue("1");
				} else {
					row.getCell("progressEdition").setValue("0");
				}
				
			}
		}
	}
	
	public void writeAfterReport(DeptTaskProgressReportInfo info) {
		if (info != null && info.getId() != null) {
			IRow addRow = schTable.addRow();
			addRow.getCell("id").setValue(info.getId().toString());
			if (info.getReportor() != null) {
				addRow.getCell("reportor").setValue(info.getReportor());
			}
			addRow.getCell("reportDate").setValue(info.getReportDate());
			addRow.getCell("completePrecent").setValue(info.getCompletePrecent());
			addRow.getCell("finishDescirption").setValue(info.getDescription());
			realStartDate.setValue(info.getRealStartDate());
			realEndDate.setValue(info.getRealEndDate());
			boolean edition = info.isProgressEdition();
			if (edition) {
				addRow.getCell("progressEdition").setValue("1");
			} else {
				addRow.getCell("progressEdition").setValue("0");
			}
		}
		 
	}
	
	
	/**
	 * 
	 */
	protected void attachListeners() {
	}

	/**
	 * 
	 */
	protected void detachListeners() {
	}
	
	/**
	 * @return
	 */
	protected KDTable getDetailTable() {
		return null;
	}

	/**
	 * @return
	 */
	protected KDTextField getNumberCtrl() {
		return null;
	}

	/**
	 * @return
	 */
	protected IObjectValue createNewData() {
		return null;
	}
	
	/**
	 * @return
	 */
	public boolean destroyWindow() {
		Object obj = null;
		obj = getUIContext().get("Owner2");
		if (obj != null && obj instanceof DeptMonthlyTaskExecListUI) {
			DeptMonthlyTaskExecListUI ui = (DeptMonthlyTaskExecListUI) obj;
			ui.execQuery();
			try {
				ui.setScoreZero();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return super.destroyWindow();
		
	}

}