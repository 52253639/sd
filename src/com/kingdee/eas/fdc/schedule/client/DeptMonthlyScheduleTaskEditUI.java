/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleInfo;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.RESchTaskOriginEnum;
import com.kingdee.eas.fdc.schedule.framework.DateUtils;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class DeptMonthlyScheduleTaskEditUI extends AbstractDeptMonthlyScheduleTaskEditUI {
	private static final Logger logger = CoreUIObject.getLogger(DeptMonthlyScheduleTaskEditUI.class);

	/**
	 * output class constructor
	 */
	public DeptMonthlyScheduleTaskEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {

		super.storeFields();

	}
	/**
	 * output btnAddLine_actionPerformed method
	 */
	protected void btnAddLine_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		// super.btnAddLine_actionPerformed(e);
	}

	/**
	 * 设置工期 根据开始日期和结束日期
	 * 
	 * @description
	 * @author 李建波
	 * @createDate 2011-9-8 void
	 * @version EAS7.0
	 * @see
	 */
	private void calcProjectPeriod() {
		int projectPeriod = 0;
		if (null != planBeginDate.getValue() && null != pkPlanFinishDate.getValue()) {

			projectPeriod = FDCDateHelper.getDiffDays((Date) planBeginDate.getValue(), (Date) pkPlanFinishDate.getValue());
			this.txtProjectPeriod.setText(String.valueOf(projectPeriod));
		}

	}

	/**
	 * 
	 * @description 月度部门编制界面初始化数据
	 * @author 李建波
	 * @createDate 2011-8-17 void
	 * @version EAS7.0
	 * @see
	 */
	private void initFillData() {
		FDCScheduleTaskInfo taskInfo = new FDCScheduleTaskInfo();
		if (null != getUIContext().get("DeptMonthlyScheduleTaskInfo")) {
			editData = (DeptMonthlyScheduleTaskInfo) getUIContext().get("DeptMonthlyScheduleTaskInfo");
		}
		if (null != getUIContext().get("taskInfo")) {
			taskInfo = (FDCScheduleTaskInfo) getUIContext().get("taskInfo");
		}
		DecimalFormat df = new DecimalFormat("0");
		if (editData != null) {
			if (null != editData.getSchedule()) {
				this.txtAdminDept.setText(editData.getSchedule().getAdminDept().toString());
				this.txtPlanMonth.setValue(selDate);
			}
			this.txtTaskName.setText(editData.getTaskName());
			if (null != editData.getTaskOrigin()) {
				this.comboTaskOrigin.setText(editData.getTaskOrigin().toString());
			}
			this.prmtAdminPerson.setValue(editData.getAdminPerson());
			
			this.txtWeightRate.setText(df.format(editData.getWeightRate()));
			if (editData.getProjectPeriod() != null) {
				this.txtProjectPeriod.setText(df.format(editData.getProjectPeriod()));
			}
			if (null != editData.getFinishStandard()) {
				this.txtFinishStand.setText(editData.getFinishStandard().toString());
			}
			// 计划完成时间
			if (null != editData.getPlanFinishDate()) {
				this.pkPlanFinishDate.setValue(editData.getPlanFinishDate());

			}
			// 计划开始时间
			if (null != editData.getPlanFinishDate()) {
				this.planBeginDate.setValue(editData.getPlanStartDate());

			}
			// 如果没有相关任务，则显示任务分录日期
			// if (null != taskInfo.getEnd()) {
			//
			// this.pkPlanEndDate.setValue(taskInfo.getEnd());
			// } else {
			// this.pkPlanEndDate.setValue(editData.getPlanFinishDate());
			// }
			// if (null != taskInfo.getStart()) {
			// this.pkPlanStartDate.setValue(taskInfo.getStart());
			// } else {
			// this.pkPlanStartDate.setValue(editData.getPlanStartDate());
			// }
			if (null != editData.getRequiredResource()) {
				this.txtRequiestesource.setText(editData.getRequiredResource().toString());
			}
			// if (null != editData.getTaskType()) {
			// this.txtTaskType.setText(editData.getTaskType().toString());
			// }
			if (null != editData.getProject()) {
				this.txtProject.setText(editData.getProject().toString());
			}
			if (null != editData.getRelatedTask()) {
				this.prmtRelatedTask.setValue(editData.getRelatedTask());
				FDCScheduleTaskInfo relatetaskinfo = editData.getRelatedTask();
				/**
				 * 查看进来的时候没找到完成进度，在这里查询一次 update by libing
				 */
				try {
					
					if (relatetaskinfo.getId() != null) {
						SelectorItemCollection sel = new SelectorItemCollection();
						sel.add(new SelectorItemInfo("*"));
						sel.add(new SelectorItemInfo("schedule.project.id"));
						sel.add(new SelectorItemInfo("schedule.project.name"));
						relatetaskinfo = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskInfo(
								new ObjectUuidPK(relatetaskinfo.getId().toString()), sel);
					}
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
				
				if (relatetaskinfo.getPlanStart() != null) {
					pkPlanStartDate.setValue(relatetaskinfo.getPlanStart());
				}
				if (relatetaskinfo.getPlanEnd() != null) {
					pkPlanEndDate.setValue(relatetaskinfo.getPlanEnd());
				}
				if (relatetaskinfo.getSchedule() != null && relatetaskinfo.getSchedule().getProject() != null) {
					txtProject.setText(relatetaskinfo.getSchedule().getProject().toString());
				}
				
				/*if (relatetaskinfo.getSchedule() != null && relatetaskinfo.getSchedule().getProject() != null
						&& relatetaskinfo.getSchedule().getProject().getName() != null) {
					txtProject.setText(relatetaskinfo.getSchedule().getProject().getName().toString());
				}*/
				// 完成进度
				if (relatetaskinfo.getComplete() != null) {
					txtFinish.setText(relatetaskinfo.getComplete().toString());
				}
				// 业务类型
				if (relatetaskinfo.getTaskType() != null) {
					txtTaskType.setText(relatetaskinfo.getTaskType().toString());
				}
				
			}
			
		}
		calcProjectPeriod();
	}

	protected boolean isModifySave() {
		// TODO Auto-generated method stub
		return false;
	}

	/*						
		  */
	public boolean isModify() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @description
	 * @author 杜红明
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */

	public void onShow() throws Exception {
		// TODO Auto-generated method stub
		super.onShow();
		// 设置必须录入字段底色为黄色
		this.prmtAdminPerson.setRequired(true);
		this.txtWeightRate.setRequired(true);
		this.txtPlanMonth.setValue(selDate);
		this.txtPlanMonth.setDatePattern("yyyy-MM");
		this.kDPanel3.setEnabled(false);
	}

	/**
	 * @description
	 * @author lijianbo
	 * @createDate 2011-8-16
	 * @version EAS7.0
	 * @see
	 */

	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		if (!(getOprtState().equals(OprtState.ADDNEW))) {
			initFillData();
		}
	
		admininfo = (AdminOrgUnitInfo) getUIContext().get("adinfo");
		selDate = (Date) getUIContext().get("datex");
		
		
		setEnabled();
		// initF7Entry();
		txtProjectPeriod.setPrecision(0);
		if (getOprtState().equals(OprtState.ADDNEW) || getOprtState().equals(OprtState.EDIT)) {
			txtTaskName.setEditable(true);
			txtTaskName.setEnabled(true);
			initDateFromTransinfo();
			setCanEdit();
		}
		check();
		txtWeightRate.setMinimumValue(0);
		txtWeightRate.setMaximumValue(100);
		
		/* modified by zhaoqin for R140105-0033 on 2014/03/19 start */
		txtWeightRate.setPrecision(2);
		/* modified by zhaoqin for R140105-0033 on 2014/03/19 end */
		
		txtTaskName.setRequired(true);
		relateTask = (FDCScheduleTaskInfo) prmtRelatedTask.getValue();
		openRetaskF7();
		txtProjectPeriod.setEditable(false);
		// 责任人
		KDBizPromptBox f7Person = prmtAdminPerson;
		FDCClientUtils.setPersonF7(f7Person, this, null);
		this.txtPlanMonth.setEnabled(false);
		// 主专项引入的不能修改这两个日期
		if (!getOprtState().equals(OprtState.VIEW)
				&& (comboTaskOrigin.getText().toString().equals("主项任务") || comboTaskOrigin.getText().toString().equals("专项任务"))) {
			planBeginDate.setEditable(false);
			planBeginDate.setEnabled(false);
			pkPlanFinishDate.setEditable(false);
			pkPlanFinishDate.setEnabled(false);
		}
		if (!getOprtState().equals(OprtState.VIEW) && comboTaskOrigin.getText().toString().equals("手工录入")) {
			txtTaskName.setEnabled(true);
			txtTaskName.setEditable(true);
		}
	}
	
	private void setCanEdit(){
		// 拿到数据传送对象
		DeptMonthlyScheduleTaskInfo initinfo = (DeptMonthlyScheduleTaskInfo) getUIContext().get("transinfo");
		if(initinfo != null && initinfo.getTaskOrigin() != null){
			if (!(initinfo.getTaskOrigin().toString().equals("手工录入"))) {
				// 任务名称
				txtTaskName.setEditable(false);
				// 计划开始
				planBeginDate.setEditable(false);
				planBeginDate.setEnabled(false);
				// 计划完成日期
				pkPlanFinishDate.setEditable(false);
				pkPlanFinishDate.setEnabled(false);
			}
		}
	}
	
	private void initDateFromTransinfo() {
		// 责任部门
		AdminOrgUnitInfo adinfo = (AdminOrgUnitInfo) getUIContext().get("adinfo");
		if (adinfo != null) {
			txtAdminDept.setText(adinfo.getName().toString());
		}
		
		// 拿到数据传送对象
		DeptMonthlyScheduleTaskInfo initinfo = (DeptMonthlyScheduleTaskInfo) getUIContext().get("transinfo");
		// 加载信息到页面
		if (initinfo != null) {
			// 责任人
			if (initinfo.getAdminPerson() != null) {
				prmtAdminPerson.setValue(initinfo.getAdminPerson());
			}
			// 权重
			if (initinfo.getWeightRate() != null) {
				txtWeightRate.setValue(initinfo.getWeightRate());
			}
			// 任务类别
			if (initinfo.getTaskType() != null) {
				txtTaskType.setText(initinfo.getTaskType().toString());
			}
			// 任务名称
			if (initinfo.getTaskName() != null) {
				txtTaskName.setText(initinfo.getTaskName());
			}
			// 完成标准
			if (initinfo.getFinishStandard() != null) {
				txtFinishStand.setText(initinfo.getFinishStandard());
			}
			// 计划完成日期
			if (initinfo.getPlanFinishDate() != null) {
				pkPlanFinishDate.setValue(initinfo.getPlanFinishDate());
			}
			// 相关任务
			if (initinfo.getRelatedTask() != null) {
				prmtRelatedTask.setValue(initinfo.getRelatedTask());
				//FDCScheduleTaskInfo relatetaskinfo = (FDCScheduleTaskInfo)initinfo.getRelatedTask();
				// 根据相任务远程查询任务任务 WWQ
				IObjectPK pkId = new ObjectUuidPK(initinfo.getRelatedTask().getId().toString());
				FDCScheduleTaskInfo relatetaskinfo=null;
				try {
					SelectorItemCollection sel = new SelectorItemCollection();
					sel.add(new SelectorItemInfo("*"));
					sel.add(new SelectorItemInfo("schedule.project.id"));
					sel.add(new SelectorItemInfo("schedule.project.name"));
					// sel.add(new SelectorItemInfo("project.id"));
					// sel.add(new SelectorItemInfo("project.name"));
					relatetaskinfo = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskInfo(pkId, sel);
					if (relatetaskinfo != null) {
						if (relatetaskinfo.getPlanStart() != null) {
							pkPlanStartDate.setValue(relatetaskinfo.getPlanStart());
						}
						if (relatetaskinfo.getPlanEnd() != null) {
							pkPlanEndDate.setValue(relatetaskinfo.getPlanEnd());
						}
						// 所属项目
						/*
						 * if (relatetaskinfo.getSchedule() != null &&
						 * relatetaskinfo.getSchedule().getProject() != null &&
						 * relatetaskinfo.getSchedule().getProject().getName()
						 * != null) {
						 * txtProject.setText(relatetaskinfo.getSchedule
						 * ().getProject().getName().toString()); }
						 */
						// Object project = getUIContext().get("selectProject");
						if (relatetaskinfo.getSchedule() != null && relatetaskinfo.getSchedule().getProject() != null) {
							txtProject.setText(relatetaskinfo.getSchedule().getProject().toString());
						}
						// 完成进度
						if (relatetaskinfo.getComplete() != null) {
							txtFinish.setText(relatetaskinfo.getComplete().toString());
						}
						// 业务类型
						if (relatetaskinfo.getTaskType() != null) {
							txtTaskType.setText(relatetaskinfo.getTaskType().toString());
						}
					}
				} catch (EASBizException e) {
					// e.printStackTrace();
				} catch (BOSException e) {
					// e.printStackTrace();
				}
			}
			
			// 任务来源
			if (initinfo.getTaskOrigin() != null) {
				comboTaskOrigin.setText(initinfo.getTaskOrigin().toString());
			}
			// 工期
			if (initinfo.getProjectPeriod() != null) {
				txtProjectPeriod.setText(initinfo.getProjectPeriod().toString());
			}
			// 计划开始日期
			if (initinfo.getPlanStartDate() != null) {
				planBeginDate.setValue(initinfo.getPlanStartDate());
			}
			// 需求资源
			if (initinfo.getRequiredResource() != null) {
				txtRequiestesource.setText(initinfo.getRequiredResource().toString());
			}
			
		}
		/**
		 * 计算工期
		 */
		calcProjectPeriod();
	}
	
	
	// 增加个工程项目和日期(为了f7筛选)
	private AdminOrgUnitInfo admininfo;

	private Date selDate;
	
	private FDCScheduleTaskInfo relateTask;
	
	private void openRetaskF7() {
		try {
			prmtRelatedTask.setDisplayFormat("$name$");
			prmtRelatedTask.setEditable(false);
			prmtRelatedTask.setSelector(new DeptMonthlySchedulePromptSelector(admininfo, selDate, relateTask));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化设置按钮不能编辑
	 * 
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-17 void
	 * @version EAS7.0
	 * @see
	 */
	private void setEnabled() {
		// 责任部门
		this.txtAdminDept.setEnabled(false);
		this.txtPlanMonth.setEnabled(false);
		// 任务来源
		this.comboTaskOrigin.setEnabled(false);
		this.txtProject.setEnabled(false);
		this.txtTaskType.setEnabled(false);
		this.pkPlanStartDate.setEnabled(false);
		this.pkPlanEndDate.setEnabled(false);
		this.txtTaskName.setEnabled(false);
	}

	/**
	 * @description
	 * @author 杜红明
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */

	protected void pkPlanFinishDate_dataChanged(DataChangeEvent e) throws Exception {
		// TODO Auto-generated method stub
		calcProjectPeriod();
		super.pkPlanFinishDate_dataChanged(e);
	}

	/**
	 * @description
	 * @author 杜红明
	 * @createDate 2011-9-8
	 * @version EAS7.0
	 * @see
	 */

	protected void planBeginDate_dataChanged(DataChangeEvent e) throws Exception {
		// TODO Auto-generated method stub
		calcProjectPeriod();
		super.planBeginDate_dataChanged(e);
	}

	public void prmtRelatedTask_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
		// write your code here
		Object obj = prmtRelatedTask.getValue();
		if (null != obj) {
			if (obj instanceof Object[]) {
				Object[] objs = (Object[]) obj;
				if (objs.length > 0) {
					FDCScheduleTaskInfo object = (FDCScheduleTaskInfo) objs[0];
					if (object.getEnd() != null)
						this.pkPlanEndDate.setValue(object.getEnd());
					if (object.getStart() != null)
						this.pkPlanStartDate.setValue(object.getStart());
					if (object.getComplete() != null)
						this.txtFinish.setText(object.getComplete().toString());
					if (object.getTaskType() != null)
						this.txtTaskType.setText(object.getTaskType().toString());
					if (object.getSchedule() != null && object.getSchedule().getProject() != null
							&& object.getSchedule().getProject().getName() != null) {
						this.txtProject.setText(object.getSchedule().getProject().getName().toString());
					}
				}
			}
		}
	}

	/**
	 * output menuItemEnterToNextRow_itemStateChanged method
	 */
	protected void menuItemEnterToNextRow_itemStateChanged(java.awt.event.ItemEvent e) throws Exception {
		// super.menuItemEnterToNextRow_itemStateChanged(e);
	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e) throws Exception {
		super.actionCalculator_actionPerformed(e);
	}

	/**
	 * output actionExport_actionPerformed
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExport_actionPerformed(e);
	}

	/**
	 * output actionExportSelected_actionPerformed
	 */
	public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionProcductVal_actionPerformed
	 */
	public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception {
		super.actionProcductVal_actionPerformed(e);
	}

	/**
	 * output actionExportSave_actionPerformed
	 */
	public void actionExportSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportSave_actionPerformed(e);
	}

	/**
	 * output actionExportSelectedSave_actionPerformed
	 */
	public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportSelectedSave_actionPerformed(e);
	}

	/**
	 * output actionKnowStore_actionPerformed
	 */
	public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception {
		super.actionKnowStore_actionPerformed(e);
	}

	/**
	 * output actionAnswer_actionPerformed
	 */
	public void actionAnswer_actionPerformed(ActionEvent e) throws Exception {
		super.actionAnswer_actionPerformed(e);
	}

	/**
	 * output actionRemoteAssist_actionPerformed
	 */
	public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemoteAssist_actionPerformed(e);
	}

	/**
	 * output actionPopupCopy_actionPerformed
	 */
	public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception {
		super.actionPopupCopy_actionPerformed(e);
	}

	/**
	 * output actionHTMLForMail_actionPerformed
	 */
	public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception {
		super.actionHTMLForMail_actionPerformed(e);
	}

	/**
	 * output actionExcelForMail_actionPerformed
	 */
	public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception {
		super.actionExcelForMail_actionPerformed(e);
	}

	/**
	 * output actionHTMLForRpt_actionPerformed
	 */
	public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception {
		super.actionHTMLForRpt_actionPerformed(e);
	}

	/**
	 * output actionExcelForRpt_actionPerformed
	 */
	public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception {
		super.actionExcelForRpt_actionPerformed(e);
	}

	/**
	 * output actionLinkForRpt_actionPerformed
	 */
	public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception {
		super.actionLinkForRpt_actionPerformed(e);
	}

	/**
	 * output actionPopupPaste_actionPerformed
	 */
	public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception {
		super.actionPopupPaste_actionPerformed(e);
	}

	/**
	 * output actionSave_actionPerformed
	 */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
	
	}

	public void verifyData() throws Exception {
		// TODO Auto-generated method stub
		super.verifyData();
	}

	protected void verifyInput(ActionEvent actionevent) throws Exception {
		// 计划月份txtPlanMonth
		if (FDCHelper.isEmpty(txtPlanMonth.getValue())) {
			FDCMsgBox.showInfo("计划月份不能为空");
			abort();
		}
		// 任务名称不能为空
		if (FDCHelper.isEmpty(txtTaskName.getText())) {
			FDCMsgBox.showInfo("任务名称不能为空");
			abort();
		}
		// 责任人 prmtAdminPerson
		if (FDCHelper.isEmpty(prmtAdminPerson.getValue())) {
			FDCMsgBox.showInfo("责任人不能为空");
			abort();
		}
		// 权重txtWeightRate
		if (FDCHelper.isEmpty(txtWeightRate.getText())) {
			FDCMsgBox.showInfo("权重不能为空");
			abort();
		}
		// 计划完成日期pkPlanFinishDate
		if (FDCHelper.isEmpty(pkPlanFinishDate.getValue())) {
			FDCMsgBox.showInfo("计划完成日期不能为空");
			abort();
		}
		if(!FDCHelper.isEmpty(planBeginDate.getValue())){
			if(!FDCHelper.isEmpty(pkPlanFinishDate.getValue())){
				if(DateUtils.compareUpToDay((Date)planBeginDate.getValue(), (Date)pkPlanFinishDate.getValue()) > 0 ){
					FDCMsgBox.showInfo("计划开始日期不能晚于计划完成日期！");
					abort();
				}
			}
		}
		super.verifyInput(actionevent);
		// Object obj = getUIContext().get("Owner");
		// if (obj != null && obj instanceof DeptMonthlyScheduleEditUI) {
		// // 反写到页面
		// DeptMonthlyScheduleEditUI ui = (DeptMonthlyScheduleEditUI)
		// getUIContext().get("Owner");
		// if (getUIContext().get("rowIndex") != null) {
		// String rowIndex = (String) getUIContext().get("rowIndex");
		// int index = Integer.parseInt(rowIndex);
		// if (ui != null) {
		// if (prmtRelatedTask.getValue() != null) {
		// ui.kdtTasks.getRow(index).getCell("relateTask").setValue(
		// prmtRelatedTask.getValue());
		// }
		// if (pkPlanEndDate.getValue() != null) {
		// ui.kdtTasks.getRow(index).getCell("planFinishDate").setValue(
		// pkPlanEndDate.getValue());
		// }
		//
		// if (pkPlanStartDate.getValue() != null) {
		// ui.kdtTasks.getRow(index).getCell("planStartDate").setValue(
		// pkPlanStartDate.getValue());
		// }
		// // 自动带入日期可以修改，新增可以维护
		// if (planBeginDate.getValue() != null) {
		// ui.kdtTasks.getRow(index).getCell("planStartDate").setValue(
		// planBeginDate.getValue());
		// }
		// if (pkPlanFinishDate.getValue() != null) {
		// ui.kdtTasks.getRow(index).getCell("planFinishDate").setValue(
		// pkPlanFinishDate.getValue());
		// }
		// if (txtTaskName.getText() != null) {
		// ui.kdtTasks.getRow(index).getCell("taskName").setValue(txtTaskName.
		// getText());
		// }
		// if (prmtAdminPerson.getValue() != null) {
		// ui.kdtTasks.getRow(index).getCell("adminPerson").setValue(
		// prmtAdminPerson.getValue());
		// }
		// if (txtWeightRate.getText() != null) {
		// ui.kdtTasks.getRow(index).getCell("weightRate").setValue(new
		// BigDecimal(txtWeightRate.getText()));
		// }
		// if (txtFinishStand.getText() != null) {
		// ui.kdtTasks.getRow(index).getCell("finishStandard").setValue(
		// txtFinishStand.getText());
		// }
		// if (txtProjectPeriod.getText() != null) {
		// ui.kdtTasks.getRow(index).getCell("projectPeriod").setValue(
		// txtProjectPeriod.getText());
		// }
		// if (txtRequiestesource.getText() != null) {
		// ui.kdtTasks.getRow(index).getCell("requiredResource").setValue(
		// txtRequiestesource.getText());
		// }
		// ui.setWeightRateTotal();
		//
		// }
		// }
		// }
		
		

	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		Object objui = getUIContext().get("Owner");
		Object schedule = getUIContext().get("par");
		Object objtaskinfo = getUIContext().get("rowIndex2");
		IRow selectRow = (IRow) getUIContext().get("selectRow");
		if (objui != null && objui instanceof DeptMonthlyScheduleEditUI) {
			DeptMonthlyScheduleEditUI ui2 = (DeptMonthlyScheduleEditUI) objui;
			if (schedule != null) {
				if (objtaskinfo != null) {
					// ui2.exeQueryList2((DeptMonthlyScheduleInfo) schedule);
					// 页面上取数据
					DeptMonthlyScheduleTaskInfo tinfo = new DeptMonthlyScheduleTaskInfo();
					if (editData.getId() == null) {
						BOSUuid uuid = BOSUuid.create(tinfo.getBOSType());
						// id
						tinfo.setId(uuid);
						editData.setId(uuid);
					}
					verifyData();
					// 就5个可以编辑 因此只判断5列
					// 责任人
					if (prmtAdminPerson.getValue() != null) {
						tinfo.setAdminPerson((PersonInfo) prmtAdminPerson.getValue());
					}
					// 任务名称
					if (txtTaskName.getText() != null) {
						tinfo.setTaskName(txtTaskName.getText());
					}
					// 计划完成日期
					if (pkPlanFinishDate.getValue() != null) {
						tinfo.setPlanFinishDate((Date) pkPlanFinishDate.getValue());
					}
					// 权重
					if (txtWeightRate.getText() != null) {
						tinfo.setWeightRate(new BigDecimal(txtWeightRate.getText().toString()));
					}
					// 计划开始日期
					if (planBeginDate.getValue() != null) {
						tinfo.setPlanStartDate((Date) planBeginDate.getValue());
					}
					// 相关任务
					if (prmtRelatedTask.getValue() != null) {
						if (prmtRelatedTask.getValue() instanceof Object[]) {
							Object[] ob = (Object[]) prmtRelatedTask.getValue();
							tinfo.setRelatedTask((FDCScheduleTaskInfo) ob[0]);
							editData.setRelatedTask((FDCScheduleTaskInfo) ob[0]);
						}
						if (prmtRelatedTask.getValue() instanceof FDCScheduleTaskInfo) {
							tinfo.setRelatedTask((FDCScheduleTaskInfo) prmtRelatedTask.getValue());
							editData.setRelatedTask((FDCScheduleTaskInfo) prmtRelatedTask.getValue());
						}
					}
					// 完成标准
					if (txtFinishStand.getText() != null) {
						tinfo.setFinishStandard(txtFinishStand.getText());
					}
					// 需求资源
					if (txtRequiestesource.getText() != null) {
						tinfo.setRequiredResource(txtRequiestesource.getText());
					}
					// 任务来源
					if (comboTaskOrigin.getText() != null) {
						if (comboTaskOrigin.getText().equals(RESchTaskOriginEnum.INPUT.toString())) {
							tinfo.setTaskOrigin(RESchTaskOriginEnum.INPUT);
							editData.setTaskOrigin(RESchTaskOriginEnum.INPUT);
						}
						if (comboTaskOrigin.getText().equals(RESchTaskOriginEnum.MAIN.toString())) {
							tinfo.setTaskOrigin(RESchTaskOriginEnum.MAIN);
							editData.setTaskOrigin(RESchTaskOriginEnum.MAIN);
						}
						if (comboTaskOrigin.getText().equals(RESchTaskOriginEnum.SPECIAL.toString())) {
							tinfo.setTaskOrigin(RESchTaskOriginEnum.SPECIAL);
							editData.setTaskOrigin(RESchTaskOriginEnum.SPECIAL);
						}
					}
					ui2.writeBacktoRow(tinfo, new Integer(objtaskinfo.toString()).intValue());
				}
			} else {
				
			}
		}
		
		
		
		Object obj = prmtRelatedTask.getValue();
	
		super.actionSubmit_actionPerformed(e);
		calcProjectPeriod();
		getOprtState();
		 if (null != obj) {

			if (obj instanceof Object[]) {
				Object[] objs = (Object[]) obj;
				if (objs.length > 0) {
					FDCScheduleTaskInfo object = (FDCScheduleTaskInfo) objs[0];
					this.prmtRelatedTask.setValue(objs[0]);
					this.pkPlanEndDate.setValue(object.getEnd());
					this.pkPlanStartDate.setValue(object.getStart());
					/**
					 * chongxinnashuju
					 */
					SelectorItemCollection col = new SelectorItemCollection();
					col.add(new SelectorItemInfo("*"));
					col.add(new SelectorItemInfo("bizType.id"));
					col.add(new SelectorItemInfo("bizType.name"));
					col.add(new SelectorItemInfo("schedule.project.id"));
					col.add(new SelectorItemInfo("schedule.project.name"));
					col.add(new SelectorItemInfo("complete"));
					object = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskInfo(new ObjectUuidPK(object.getId().toString()), col);
					if (object.getTaskType() != null) {
						this.txtTaskType.setText(object.getTaskType().toString());
					}
					if (object.getSchedule() != null && object.getSchedule().getProject() != null
							&& object.getSchedule().getProject().getName() != null) {
						this.txtProject.setText(object.getSchedule().getProject().getName());
					}
					if (object.getComplete() != null) {
						this.txtFinish.setText(object.getComplete().toString());
					}
					// if (object.getSchedule() != null) {
					// if (object.getSchedule().getProjectSpecial() == null) {
					// this.comboTaskOrigin.setText(RESchTaskOriginEnum.MAIN.
					// toString());
					// } else {
					// this.comboTaskOrigin.setText(RESchTaskOriginEnum.SPECIAL.
					// toString());
					// }
					// }
				}
			}
			if (obj instanceof FDCScheduleTaskInfo) {
				FDCScheduleTaskInfo object = (FDCScheduleTaskInfo) obj;
				this.prmtRelatedTask.setValue(object);
				this.pkPlanEndDate.setValue(object.getEnd());
				this.pkPlanStartDate.setValue(object.getStart());
				/**
				 * 重新去拿数据
				 */
				SelectorItemCollection col = new SelectorItemCollection();
				col.add(new SelectorItemInfo("*"));
				col.add(new SelectorItemInfo("bizType.id"));
				col.add(new SelectorItemInfo("bizType.name"));
				col.add(new SelectorItemInfo("schedule.project.id"));
				col.add(new SelectorItemInfo("schedule.project.name"));
				col.add(new SelectorItemInfo("complete"));
				object = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskInfo(new ObjectUuidPK(object.getId().toString()), col);
				if (object.getTaskType() != null) {
					this.txtTaskType.setText(object.getTaskType().toString());
				}
				if (object.getSchedule() != null && object.getSchedule().getProject() != null && object.getSchedule().getProject().getName() != null) {
					this.txtProject.setText(object.getSchedule().getProject().getName());
				}
				if (object.getComplete() != null) {
					this.txtFinish.setText(object.getComplete().toString());
				}
				// if (object.getSchedule() != null) {
				// if (object.getSchedule().getProjectSpecial() == null) {
				// this.comboTaskOrigin.setText(RESchTaskOriginEnum.MAIN.
				// toString());
				// } else {
				// this.comboTaskOrigin.setText(RESchTaskOriginEnum.SPECIAL.
				// toString());
				// }
				// }
			}
		}
		
		
	}

	/**
	 * 
	 * @description 数据校验
	 * @author 李建波
	 * @createDate 2011-8-25 void
	 * @version EAS7.0
	 * @see
	 */
	private void check() {
		Object total = null;
		if (null != getUIContext().get("total")) {
			total = (Object) getUIContext().get("total");
		}
		if (total != null) {
			int temp = new BigDecimal(total.toString()).intValue();
			int newtemp = new BigDecimal(temp).subtract(editData.getWeightRate()).intValue();
			int tempValue = 100 - newtemp;
			txtWeightRate.setMaximumValue(new Integer(tempValue));

			this.txtFinishStand.setMaxLength(200);
			this.txtRequiestesource.setMaxLength(200);
		}
		
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	

	/**
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-16
	 * @version EAS7.0
	 * @see
	 */

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return DeptMonthlyScheduleTaskFactory.getRemoteInstance();
	}

	/**
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-16
	 * @version EAS7.0
	 * @see
	 */

	protected KDTable getDetailTable() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-16
	 * @version EAS7.0
	 * @see
	 */

	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @description
	 * @author lijianbo
	 * @createDate 2011-8-16
	 * @version EAS7.0
	 * @see
	 */

	protected IObjectValue createNewData() {
		DeptMonthlyScheduleTaskInfo info = new DeptMonthlyScheduleTaskInfo();
		if (getUIContext().get("par") != null) {
			Object object = getUIContext().get("par");
			info.setSchedule((DeptMonthlyScheduleInfo) object);
		}
		return info;
	}

	/**
	 * 窗口关闭时刷新列表
	 * 
	 * @description
	 * @author 李建波
	 * @createDate 2011-8-19
	 * @version EAS7.0
	 * @see
	 */
	public boolean destroyWindow() {
		return super.destroyWindow();
		
	}
}