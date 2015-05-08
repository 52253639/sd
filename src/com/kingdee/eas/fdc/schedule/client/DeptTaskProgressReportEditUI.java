/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.BizEnumValueDTO;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.DeptTaskProgressReportFactory;
import com.kingdee.eas.fdc.schedule.DeptTaskProgressReportInfo;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * @(#)	DeptTaskProgressReportEditUI.java 版权： 金蝶国际软件集团有限公司版权所有
 *                                        描述：部门月度计划执行—进度汇报界面
 * @author 王维强
 * @version EAS7.0
 * @createDate 2011-9-7
 * @see
 */
public class DeptTaskProgressReportEditUI extends AbstractDeptTaskProgressReportEditUI {
	private static final Logger logger = CoreUIObject.getLogger(DeptTaskProgressReportEditUI.class);
	private DeptMonthlyScheduleTaskInfo taskInfo;
	private String comStr = "";
	private String completeStr = "";
	private boolean isSave = false;
	/**
	 * output class constructor
	 */
	public DeptTaskProgressReportEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		
		Object rid = getUIContext().get("rid");
		Boolean isProgress = (Boolean) getUIContext().get("isReport");
		DeptMonthlyScheduleTaskInfo relateTask = null;
		/*
		 * if (isProgress.booleanValue() == true) {builder.appendSql(
		 * "update T_SCH_DeptTaskProgressReport set FProgressEdition='0' where  FRelateTaskID='"
		 * + ((DeptMonthlyScheduleTaskInfo)
		 * getUIContext().get("taskInfo")).getId().toString() + "'"); try {
		 * builder.executeUpdate(); } catch (BOSException e) {
		 * e.printStackTrace(); } }
		 */
		
		super.storeFields();
		if (complete.isSelected()) {
			editData.setIsComplete(true);
			editData.setCompletePrecent(new BigDecimal("100", new MathContext(0)));
		} else {
			editData.setIsComplete(false);
			editData.setRealEndDate(null);
			if (!txtCompletePrecent.getText().equals("")) {
				editData.setCompletePrecent(new BigDecimal(txtCompletePrecent.getText().toString(), new MathContext(0)));
			} else {
				editData.setCompletePrecent(new BigDecimal("0", new MathContext(0)));
			}
		}
		if (selectRow != null) {
			Date planEndDate = (Date) selectRow.getCell("planEndDate").getValue();
			editData.setPlanEndDate(planEndDate);
		} else {
			Date planEndDate = (Date) getUIContext().get("planEndDate");
			editData.setPlanEndDate(planEndDate);
		}
		
		this.editData.setRelateTask(taskInfo);
	}

	private IRow selectRow = null;

	/**
	 * @throws Exception
	 */
	public void onLoad() throws Exception {
		super.onLoad();
		taskInfo = (DeptMonthlyScheduleTaskInfo) getUIContext().get("taskInfo");
		selectRow = (IRow) getUIContext().get("selectRow");
		String name = null;
		String taskType = null;
		comStr = txtCompletePrecent.getText();
		if (getOprtState() != null && getOprtState().equals(OprtState.ADDNEW) && selectRow == null) {
			DeptMonthlyScheduleTaskInfo report = (DeptMonthlyScheduleTaskInfo) getUIContext().get("editData");
			name = report.getTaskName();
			if (report.getTaskType() != null) {
				taskType = report.getTaskType().getAlias();
			}
		}
		if (getOprtState() != null && getOprtState().equals(OprtState.ADDNEW) && selectRow != null) {
			/*DeptMonthlyScheduleTaskInfo report = taskInfo;
			name = report.getTaskName();
			if (report.getTaskType() != null) {
				taskType = report.getTaskType().getAlias();
			}*/
			name = (String) selectRow.getCell("taskName").getValue();
			Object taskTypeTemp = selectRow.getCell("taskType").getValue();
			if (taskTypeTemp instanceof BizEnumValueDTO) {
				taskType = ((BizEnumValueDTO) taskTypeTemp).getAlias();
			} else {
				taskType = ((RESchTaskTypeEnum) taskTypeTemp).getAlias();
			}
			txtCompletePrecent.setValue(selectRow.getCell("completePrecent").getValue());
			
		}
		txtTaskName.setText(name);
		txtTaskType.setText(taskType);
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		if (user != null && user.getPerson() != null) {
			prmtReportor.setValue(user.getPerson());
		} else if (user != null && user.getPerson() == null) {
			prmtReportor.setValue(user);
		}
		this.pkReportDate.setValue(new Date());
		pkReportDate.setDatePattern("yyyy-MM-dd");
		txtTaskName.setEnabled(false);
		txtTaskType.setEnabled(false);
		kDButtonGroup1.add(complete);
		kDButtonGroup1.add(noComplete);
		if (txtCompletePrecent.getText().equals("100")) {
			complete.setSelected(true);
			pkRealEndDate.setValue(selectRow.getCell("realEndDate").getValue());
		}
		complete.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				txtCompletePrecent.setText("100");
				pkRealEndDate.setRequired(true);
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {

			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

		});
		noComplete.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				txtCompletePrecent.setText("0");
				pkRealEndDate.setValue(null);
				pkRealEndDate.setRequired(false);
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseReleased(MouseEvent e) {
			}

		});
		
		txtCompletePrecent.addDataChangeListener(new DataChangeListener() {

			public void dataChanged(DataChangeEvent arg0) {
				String value = txtCompletePrecent.getText().toString();
				int precent = new BigDecimal(value).intValue();
				if (precent > 100 || precent < 0) {
					FDCMsgBox.showError("完成进度在0~100之间！");
					txtCompletePrecent.setText("");
					SysUtil.abort();
				}
				if (txtCompletePrecent.getText().equals("100")) {
					complete.setSelected(true);
					pkRealEndDate.setRequired(true);
				} else {
					noComplete.setSelected(true);
					pkRealEndDate.setRequired(false);
				}
			}
			
		});
	}

	/**
	 * @throws Exception
	 */
	public void onShow() throws Exception {
		super.onShow();
		pkRealEndDate.setEditable(true);
		completeStr = txtCompletePrecent.getText();
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
	 * 
	 */
	protected void initWorkButton() {
		super.initWorkButton();
		// 新增干掉
		btnAddNew.setEnabled(false);
		btnAddNew.setVisible(false);
		// 删除干掉
		btnRemove.setEnabled(false);
		btnRemove.setVisible(false);
		// 复制干掉
		btnCopy.setEnabled(false);
		btnCopy.setVisible(false);
		// 附件管理干掉
		btnAttachment.setEnabled(false);
		btnAttachment.setVisible(false);
		// 前一
		btnPre.setEnabled(false);
		btnPre.setVisible(false);
		// 第一
		btnFirst.setEnabled(false);
		btnFirst.setVisible(false);
		// 后一
		btnNext.setEnabled(false);
		btnNext.setVisible(false);
		// 最后
		btnLast.setEnabled(false);
		btnLast.setVisible(false);
		btnSave.setVisible(false);
		// btnSubmit.setVisible(false);
		btnPrint.setVisible(false);
		btnPrintPreview.setVisible(false);
		btnCancel.setVisible(false);
		btnCancelCancel.setVisible(false);
		btnEdit.setVisible(false);
		pkRealEndDate.setEditable(true);
	}

	/**
	 * @return
	 */
	protected IObjectValue createNewData() {
		DeptTaskProgressReportInfo info = new DeptTaskProgressReportInfo();
		if (taskInfo != null) {
			info.setReportor(taskInfo.getAdminPerson());
		}
		// info.setProgressEdition(true);
		info.setReportDate(new Date());
		if (complete.isSelected()) {
			info.setIsComplete(true);
			pkRealEndDate.setEditable(true);
		} else {
			info.setIsComplete(false);
		}
		return info;
	}

	/**
	 * @param e
	 * @throws Exception
	 */
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		String completePrecentStr = "";
		int precent = 0;
		if (txtCompletePrecent.getText().toString().equals("")) {
			FDCMsgBox.showInfo(this, "完成进度不能为空！");
			SysUtil.abort();
		} else {
			completePrecentStr = txtCompletePrecent.getText().toString();
			precent = new BigDecimal(completePrecentStr).intValue();
			FDCSQLBuilder builder = new FDCSQLBuilder();
			Object rid = getUIContext().get("rid");
			builder.appendSql("select FCompletePrecent as complete from T_SCH_DeptTaskProgressReport where FProgressEdition='1' and FRelateTaskID='"
					+ rid + "'");
			IRowSet rowSet = builder.executeQuery();
			int precent2 = 0;
			String comText = "";
			if (getUIContext().get("comText") != null) {
				comText = getUIContext().get("comText").toString();
			}
			
			if (!completeStr.equals("")) {
				precent2 = new BigDecimal(completeStr).intValue();
			}
			if (!comText.equals("")) {
				precent2 = new BigDecimal(comText).intValue();
			}
			if (rowSet.size() > 0) {
				int complete = 0;
				while (rowSet.next()) {
					complete = new BigDecimal(rowSet.getString("complete").toString()).intValue();
					if (precent < complete) {
						FDCMsgBox.showInfo(this, "当前任务工程量目前完工" + complete + "%,本次进度不能低于上次汇报数！");
						SysUtil.abort();
					}
				}
			} else if (rowSet.size() == 0 && precent2 != 0) {
				if (precent < precent2) {
					FDCMsgBox.showInfo(this, "当前任务工程量目前完工" + precent2 + "%,本次进度不能低于上次汇报数！");
					SysUtil.abort();
				}
			}
			if (precent > 100 || precent < 0) {
				FDCMsgBox.showInfo(this, "完成进度在0~100之间！");
				SysUtil.abort();
			}
		}
		if (noComplete.isSelected() == true && precent == 100) {
			FDCMsgBox.showInfo(this, "未完成任务进度不能为100%！");
			noComplete.setSelected(false);
			complete.setSelected(true);
			SysUtil.abort();
		}
		if (complete.isSelected() == true && precent != 100) {
			FDCMsgBox.showInfo(this, "任务完成进度为100%！");
			 txtCompletePrecent.setText("100");
			SysUtil.abort();
		}
		if (noComplete.isSelected() == true && !pkRealEndDate.getText().toString().equals("")) {
			FDCMsgBox.showInfo(this, "未完成任务完成日期为空！");
			pkRealEndDate.setValue(null);
			SysUtil.abort();
		}
		if (complete.isSelected() == true && pkRealEndDate.getText().toString().equals("")) {
			FDCMsgBox.showInfo(this, "实际完成日期不能为空！");
			SysUtil.abort();
		}
	}

	/**
	 * @return
	 * @throws Exception
	 */
	protected ICoreBase getBizInterface() throws Exception {
		return DeptTaskProgressReportFactory.getRemoteInstance();
	}

	/**
	 * @param arg0
	 * @throws Exception
	 */
	public void actionSubmit_actionPerformed(ActionEvent arg0) throws Exception {
		Object rid = getUIContext().get("rid");
		if (prmtReportor.getValue() != null && prmtReportor.getValue() instanceof PersonInfo) {
			editData.setReportor((PersonInfo) prmtReportor.getValue());
		}
		editData.setProgressEdition(true);
		super.actionSubmit_actionPerformed(arg0);
		/**
		 * 保存成功后手动改数据库 表里面该条进度汇报的汇报人，因为现在的实现没有汇报人 modify by libing
		 */
		if (prmtReportor.getValue() != null && prmtReportor.getValue() instanceof PersonInfo) {
			editData.setReportor((PersonInfo) prmtReportor.getValue());
			DeptTaskProgressReportInfo info = new DeptTaskProgressReportInfo();
			info.setReportor((PersonInfo) prmtReportor.getValue());
			info.setId(editData.getId());
			DeptTaskProgressReportFactory.getRemoteInstance().update(new ObjectUuidPK(editData.getId().toString()), info);
		}
		
		
		isSave = true;
		updateVersion();
		if (rid != null) {
			if (DeptMonthlyScheduleTaskFactory.getRemoteInstance().exists(new ObjectStringPK(rid.toString()))) {
				DeptMonthlyScheduleTaskInfo deptMonthScheduleTask = DeptMonthlyScheduleTaskFactory.getRemoteInstance()
						.getDeptMonthlyScheduleTaskInfo(new ObjectStringPK(rid.toString()));
				Object scheduletTaskId = null;
				try {
					scheduletTaskId = deptMonthScheduleTask.getRelatedTask().getId();
				} catch (NullPointerException e) {
					setOprtState(OprtState.VIEW);
					this.btnSubmit.setEnabled(true);
					setComplete(rid);
					return;
				}
				/*String relateTaskIdStr = scheduletTaskId.toString();
				String precent = txtCompletePrecent.getText().toString();
				Object realStartDate = pkRealStartDate.getValue();
				Object realEndDate = this.pkRealEndDate.getValue();
				builder.appendSql("update T_SCH_FDCScheduleTask set FComplete = '" + precent + "' where FID='" + relateTaskIdStr + "'");
				builder.executeUpdate();
				builder.clear();
				if (realStartDate != null) {
					builder.appendSql("update T_SCH_FDCScheduleTask set FactualStartDate ={ts'" + pkRealStartDate.getText() + "'} where FID='"
							+ relateTaskIdStr + "'");
					builder.executeUpdate();
					builder.clear();
				}
				if (realEndDate != null) {
					builder.appendSql("update T_SCH_FDCScheduleTask set FactualEndDate = {ts'" + pkRealEndDate.getText() + "'} where FID='"
							+ relateTaskIdStr + "'");
					builder.executeUpdate();
					builder.clear();
				}
				
				FDCScheduleTaskInfo value = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskInfo(new ObjectStringPK(relateTaskIdStr));
				Date actualEndDate = null;
				if (value != null) {
					actualEndDate = value.getActualEndDate();
				}
				if (!precent.equals("100") && actualEndDate != null) {
					builder.appendSql("update T_SCH_FDCScheduleTask set FactualEndDate = '' where FID='" + relateTaskIdStr + "'");
					builder.executeUpdate();
				}*/
				}
			
			setComplete(rid);
		} 
		setOprtState(OprtState.VIEW);
		this.btnSubmit.setEnabled(true);
	}

	/**
	 * @describes
	 * @author 王维强
	 * @createDate 2011-11-17
	 */
	private void updateVersion() throws BOSException {
		Object rid = getUIContext().get("rid");
		if (rid == null || editData == null || editData.getId() == null) {
			return;
		}
		FDCSQLBuilder builderUpdateVer = new FDCSQLBuilder();
		builderUpdateVer.appendSql("update T_SCH_DeptTaskProgressReport set FProgressEdition='0' where fid<> '" + editData.getId().toString()
				+ "' and FRelateTaskID='" + rid + "'");
		builderUpdateVer.executeUpdate();
	}

	/**
	 * @describes 反写完成进度
	 * @author 王维强
	 * @createDate 2011-11-9
	 */
	private void setComplete(Object rid) throws BOSException {
		FDCSQLBuilder builderTask = new FDCSQLBuilder();
		String taskId = rid.toString();
		String complete = editData.getCompletePrecent().toString();
		builderTask.appendSql("update T_SCH_DeptMonthlyScheduleTask set FComplete = '" + complete + "' where FID='" + taskId + "'");
		builderTask.executeUpdate();
	}

	/**
	 * @param e
	 * @throws Exception
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		// editData.setProgressEdition(false);
		super.actionSave_actionPerformed(e);
		setOprtState(OprtState.VIEW);

	}
	public boolean validateEidt() {
		BigDecimal completePrecent = null;
		if (editData != null) {
			completePrecent = editData.getCompletePrecent();
			if (completePrecent != null) {
				comStr = completePrecent.toString();
			}
		}
		String text = txtCompletePrecent.getText();
		if (comStr.equals(text)) {
			return false;
		}
		return true;
	}
	/**
	 * @return
	 */
	public boolean destroyWindow() {
		if (!validateEidt() && isSave == false) {
			editData = null;
			return super.destroyWindow();
		}
		Object obj = null;
		obj = getUIContext().get("Owner");
		if (obj != null && obj instanceof DeptMonthlyTaskExecEditUI) {
			Map uiContext = getUIContext();
			DeptMonthlyTaskExecEditUI ui = (DeptMonthlyTaskExecEditUI) obj;
			ui.writeAfterReport(editData);
			try {
				if (editData != null) {
					ui.refreshValue(editData);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			if (obj != null && obj instanceof DeptMonthlyTaskExecListUI) {
				DeptMonthlyTaskExecListUI ui = (DeptMonthlyTaskExecListUI) obj;
				ui.execQuery();
			}
		}
		return super.destroyWindow();
	}

	/**
	 * @return
	 */
	protected KDTable getDetailTable() {
		return new KDTable();
	}

	/**
	 * @return
	 */
	protected KDTextField getNumberCtrl() {
		return this.txtTaskName;
	}

}