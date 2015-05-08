/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sourceforge.ganttproject.GanttPreviousStateTask;
import net.sourceforge.ganttproject.gui.GanttTabbedPane;
import net.sourceforge.ganttproject.task.Task;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.PlanAdjustReasonInfo;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustGattReqFactory;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustGattReqInfo;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustTypeEnum;
import com.kingdee.eas.fdc.schedule.ScheduleAffectEnum;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.client.IScheduleUIFacade;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.ext.ScheduleGanttProject;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.workflow.DefaultWorkflowUIEnhancement;
import com.kingdee.eas.framework.client.workflow.IWorkflowUIEnhancement;
import com.kingdee.eas.framework.client.workflow.IWorkflowUISupport;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;

/**
 * output class name
 */
public class SchAdjustReqByGrantEditUI extends AbstractSchAdjustReqByGrantEditUI implements IScheduleUIFacade, IWorkflowUISupport {

	private static final Logger logger = CoreUIObject.getLogger(SchAdjustReqByGrantEditUI.class);

	private boolean isVersionCompare = false;
	private ScheduleAdjustGattReqInfo adjustGanttReqCache = null;

	/**
	 * output class constructor
	 */
	public SchAdjustReqByGrantEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		if (editData != null) {
			if (editData.getTaskEntrys() == null || editData.getTaskEntrys().isEmpty()) {
				FDCMsgBox.showWarning("计划的任务不能为空， 请添加任务");
				SysUtil.abort();
			}
			ScheduleAdjustGattReqInfo adjustGanttReq = (ScheduleAdjustGattReqInfo) editData.get("AdjustInfo");
			if (adjustGanttReq != null) {
				adjustGanttReq.setName(this.reqName.getText());
				adjustGanttReq.setNumber(this.reqNumber.getText());
				adjustGanttReq.setHandler(SysContext.getSysContext().getCurrentUserInfo());
				adjustGanttReq.setAdjustDesc(this.reqAdjustDesc.getText());
				// 最开始周老大把它做成了枚举，按照需求应该是F7基础资料的值 by cassiel 2010-06-16
				//adjustGanttReq.setAdjustReason((ScheduleCreateReasonEnum)this.
				// reqAdjustReason.getSelectedItem());
				if (this.prmtAdjustReason.getValue() != null && this.prmtAdjustReason.getValue() instanceof PlanAdjustReasonInfo) {
					adjustGanttReq.setAdjustReason((PlanAdjustReasonInfo) prmtAdjustReason.getValue());
				}
				adjustGanttReq.setAdjustType((ScheduleAdjustTypeEnum) this.reqAdjustType.getSelectedItem());
				adjustGanttReq.setAffect((ScheduleAffectEnum) this.reqAffect.getSelectedItem());
				adjustGanttReq.setReqDate(this.reqTime.getSqlDate());
				adjustGanttReq.setCurProject(editData.getProject());
				Date oldStartDate = editData.getStartDate();
				if (editData.containsKey("myOldStartDate")) {
					oldStartDate = editData.getDate("myOldStartDate");
				}
				Date oldEndDate = editData.getEndDate();
				if (editData.containsKey("myOldEndDate")) {
					oldEndDate = editData.getDate("myOldEndDate");
				}
				editData.setStartDate(null);
				editData.setEndDate(null);
				for (Iterator it = editData.getTaskEntrys().iterator(); it.hasNext();) {
					FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) it.next();
					if (editData.getStartDate() == null || task.getStart().before(editData.getStartDate())) {
						editData.setStartDate(task.getStart());
					}
					if (editData.getEndDate() == null || task.getEnd().after(editData.getEndDate())) {
						editData.setEndDate(task.getEnd());
					}
				}
				if (oldEndDate != null && oldEndDate.after(editData.getEndDate())) {
					adjustGanttReq.setTotalTimes(FDCHelper.add(ScheduleCalendarHelper.getNatureTimes(oldEndDate, editData.getEndDate()),
							FDCHelper.ONE));
				} else {
					adjustGanttReq.setTotalTimes(FDCHelper.subtract(ScheduleCalendarHelper.getNatureTimes(oldEndDate, editData.getEndDate()),
							FDCHelper.ONE));
				}

				// adjustGanttReq.setTotalTimes(this.reqAffectDay.
				// getBigDecimalValue());
			}

		}

		super.storeFields();

	}

	protected FDCScheduleInfo getNewData() throws EASBizException, BOSException {

		Map param = new HashMap();
		CurProjectInfo prj = (CurProjectInfo) getUIContext().get("CurProject");
		param.put("adminDeptId", SysContext.getSysContext().getCurrentOrgUnit().getId().toString());// 归口部门
		param.put("taskTypeId", TaskTypeInfo.TASKTYPE_ADJUST);// 任务专业属性
		param.put("adjustVerID", this.getUIContext().get("adjustVerID"));

		if (this.getUIContext().containsKey("isFromWorkflow")) {
			if (prj == null) {
				ScheduleAdjustGattReqInfo adjustInfo = null;
				if (this.getUIContext().containsKey(UIContext.ID)) {
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("curProject.id");
					sic.add("baseVer.id");
					adjustInfo = ScheduleAdjustGattReqFactory.getRemoteInstance().getScheduleAdjustGattReqInfo(
							new ObjectUuidPK(this.getUIContext().get(UIContext.ID).toString()), sic);
					prj = adjustInfo.getCurProject();
				}
				if (this.getUIContext().get("adjustVerID") == null) {
					param.put("adjustVerID", adjustInfo.getBaseVer().getId().toString());
				}
			}
		}
		if (this.getUIContext().containsKey(UIContext.ID)) {
			param.put("adjustReqId", this.getUIContext().get(UIContext.ID));
		}
		param.put("prjId", prj.getId().toString());// 工程项目
		FDCScheduleInfo info = FDCScheduleFactory.getRemoteInstance().getNewData(param);
		return info;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		// this.contNumber.setVisible(false);
		// this.contName.setVisible(false);
		contCreator.setVisible(false);
		this.prmtCreator.setVisible(false);
		this.prmtAdminPerson.setVisible(false);

		this.reqName.setRequired(true);
		this.reqNumber.setRequired(true);
		initAffectRpt();
		// if(this.getOprtState().equals("VIEW")){
		// this.reqName.setEnabled(false);
		// this.reqNumber.setEnabled(false);
		//			
		// this.reqAdjustDesc.setEnabled(false);
		// this.reqAdjustReason.setEnabled(false);
		// this.reqAdjustType.setEnabled(false);
		// this.reqAffect.setEnabled(false);
		// this.reqTime.setEnabled(false);
		// this.reqAffectDay.setEnabled(false);
		// }
		if (editData.containsKey("AdjustInfo")) {
			ScheduleAdjustGattReqInfo adjustGanttReq = (ScheduleAdjustGattReqInfo) editData.get("AdjustInfo");
			if (adjustGanttReq != null) {
				if (FDCBillStateEnum.SAVED.equals(adjustGanttReq.getState()) || FDCBillStateEnum.SUBMITTED.equals(adjustGanttReq.getState())) {
					this.actionEdit.setEnabled(true);
				} else {
					this.actionEdit.setEnabled(false);
				}
			}
		}
		if (this.getOprtState().equals("EDIT")) {
			this.actionEdit.setEnabled(false);
		}
		this.actionRemove.setVisible(false);

		this.btnVersionCompare.setIcon(EASResource.getIcon("imgTbtn_combinemessage"));
		this.reqAffectDay.setHorizontalAlignment(KDFormattedTextField.RIGHT);
	}

	/**
	 * 保存提交时，设置调整申请单（甘特图模式）的最大任务变动量为：影响分析，有效工期-差值的最大值add by warship at
	 * 2010/07/23
	 */
	protected void setMaxTaskChange() {
		Component tabs[] = this.mainPanel.getComponents();
		BigDecimal default_value = null;
		if (tabs[0] instanceof GanttTabbedPane) {
			final GanttTabbedPane ganttTabbedPane = (GanttTabbedPane) tabs[0];
			KDTable table = (KDTable) ganttTabbedPane.getComponentAt(1);
			int rowCount = table.getRowCount();
			if (rowCount > 0) {
				for (int i = 0; i < rowCount; i++) {
					IRow row = table.getRow(i);
					BigDecimal durationChange = (BigDecimal) row.getCell(10).getValue();
					if (durationChange != null) {
						if (i == 0) {
							default_value = durationChange;
						} else {
							if (durationChange.compareTo(default_value) == 1) {
								default_value = durationChange;
							}
						}
					}
				}
			}
		}
		ScheduleAdjustGattReqInfo adjustGanttReq = (ScheduleAdjustGattReqInfo) editData.get("AdjustInfo");
		if (adjustGanttReq != null && default_value != null) {
			adjustGanttReq.setMaxTaskChange(default_value);
		}
	}

	/**
	 * 初始化影响情况表头add by warship at 2010/07/08
	 */
	protected void initAffectRpt() {
		Component tabs[] = this.mainPanel.getComponents();

		if (tabs[0] instanceof GanttTabbedPane) {
			final GanttTabbedPane ganttTabbedPane = (GanttTabbedPane) tabs[0];
			final KDTable table = new KDTable(11, 2, 0);
			table.getIndexColumn().getStyleAttributes().setHided(true);
			table.setRefresh(false);
			table.getStyleAttributes().setLocked(true);

			IRow headRow0 = table.getHeadRow(0);
			headRow0.getCell(0).setValue("WBS编码");
			headRow0.getCell(1).setValue("任务名称");
			headRow0.getCell(2).setValue("开始时间");
			headRow0.getCell(5).setValue("结束时间");
			headRow0.getCell(8).setValue("有效工期(天)");
			IRow headRow1 = table.getHeadRow(1);
			headRow1.getCell(2).setValue("原开始时间");
			headRow1.getCell(3).setValue("调整后开始时间");
			headRow1.getCell(4).setValue("差值(天)");
			headRow1.getCell(5).setValue("原结束时间");
			headRow1.getCell(6).setValue("调整后结束时间");
			headRow1.getCell(7).setValue("差值(天)");
			headRow1.getCell(8).setValue("原工期");
			headRow1.getCell(9).setValue("调整后工期");
			headRow1.getCell(10).setValue("差值(天)");

			KDTMergeManager mm = table.getHeadMergeManager();
			mm.mergeBlock(0, 0, 1, 0, KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(0, 1, 1, 1, KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(0, 2, 0, 4, KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(0, 5, 0, 7, KDTMergeManager.SPECIFY_MERGE);
			mm.mergeBlock(0, 8, 0, 10, KDTMergeManager.SPECIFY_MERGE);

			ganttTabbedPane.add("影响情况", table);
		}
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FDCScheduleFactory.getRemoteInstance();
	}

	protected void initWorkButton() {
		super.initWorkButton();
		// this.contNumber.setVisible(false);
		// this.contName.setVisible(false);
		contCreator.setVisible(false);
		this.prmtCreator.setVisible(false);
		this.prmtAdminPerson.setVisible(false);

		this.reqName.setRequired(true);
		this.reqNumber.setRequired(true);

		this.reqAffectDay.setEnabled(false);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if (editData.containsKey("AdjustInfo")) {
			ScheduleAdjustGattReqInfo adjustGanttReq = (ScheduleAdjustGattReqInfo) editData.get("AdjustInfo");
			ScheduleAdjustGattReqFactory.getRemoteInstance().delete(new ObjectUuidPK(adjustGanttReq.getId()));
		}
	}

	public void loadFields() {

		super.loadFields();

		if (editData != null) {
			ScheduleAdjustGattReqInfo adjustGanttReq = (ScheduleAdjustGattReqInfo) editData.get("AdjustInfo");
			if (adjustGanttReq != null) {
				this.reqName.setText(adjustGanttReq.getName());
				this.reqNumber.setText(adjustGanttReq.getNumber());
				this.reqHandler.setText(adjustGanttReq.getHandler().getName());
				this.reqAdjustDesc.setText(adjustGanttReq.getAdjustDesc());
				// 最开始周老大把它做成了枚举，按照需求应该是F7基础资料的值 by cassiel 2010-06-16
				// this.reqAdjustReason.setSelectedItem(adjustGanttReq.
				// getAdjustReason());
				this.prmtAdjustReason.setValue(adjustGanttReq.getAdjustReason());
				this.reqAdjustType.setSelectedItem(adjustGanttReq.getAdjustType());
				this.reqAffect.setSelectedItem(adjustGanttReq.getAffect());

				this.reqTime.setValue(adjustGanttReq.getReqDate());
				this.reqAffectDay.setValue(adjustGanttReq.getTotalTimes());
			}
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {

		super.actionEdit_actionPerformed(e);
		this.reqHandler.setEnabled(false);
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		Map param = new HashMap();
		CurProjectInfo prj = (CurProjectInfo) getUIContext().get("CurProject");
		param.put("adminDeptId", SysContext.getSysContext().getCurrentOrgUnit().getId().toString());// 归口部门
		// 在工作流中保存，提交时要处理 add by warship at 2010/07/30
		if (prj == null || prj.getId() == null) {
			param.put("prjId", editData.getProject().getId().toString());// 工程项目
		} else {
			param.put("prjId", prj.getId().toString());// 工程项目
		}
		param.put("taskTypeId", TaskTypeInfo.TASKTYPE_ADJUST);// 任务专业属性
		param.put("adjustVerID", this.getUIContext().get(UIContext.ID));
		editData.put("AdjustParam", param);

		// 提交影响分析，有效工期-差值的最大值add by warship at 2010/07/23
		setMaxTaskChange();
		super.actionSave_actionPerformed(e);
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		Map param = new HashMap();
		CurProjectInfo prj = (CurProjectInfo) getUIContext().get("CurProject");
		param.put("adminDeptId", SysContext.getSysContext().getCurrentOrgUnit().getId().toString());// 归口部门
		if (prj == null || prj.getId() == null) {
			param.put("prjId", editData.getProject().getId().toString());// 工程项目
		} else {
			param.put("prjId", prj.getId().toString());// 工程项目
		}
		param.put("taskTypeId", TaskTypeInfo.TASKTYPE_ADJUST);// 任务专业属性
		param.put("adjustVerID", this.getUIContext().get(UIContext.ID));
		editData.put("AdjustParam", param);
		// 提交影响分析，有效工期-差值的最大值add by warship at 2010/07/23
		setMaxTaskChange();
		/***
		 * R100823-129 为了不启动计划编制的工作流，这里调用actionSave 但是，为了保证状态的正确性，这里设置状态值
		 */
		param.put("isSubmit", Boolean.TRUE);
		super.actionSave_actionPerformed(e);
	}

	public boolean isAdjustSchedule() {
		return true;
	}

	protected void afterOnload() {
		super.afterOnload();
		// this.contNumber.setVisible(false);
		// this.contName.setVisible(false);
		contCreator.setVisible(false);

		// this.txtNumber.setEnabled(false);
		// this.txtNumber.setRequired(false);
		// this.txtName.setRequired(false);
		this.prmtAdminPerson.setRequired(false);
		this.prmtCreator.setRequired(false);

		this.reqHandler.setEnabled(false);

		this.actionProperty.setEnabled(false);
		this.actionProperty.setVisible(false);

	}

	protected void beforeOnload() {
		super.beforeOnload();
	}

	protected IObjectValue createNewData() {
		FDCScheduleInfo info = null;
		;
		try {
			info = getNewData();
			info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		} catch (Exception e) {
			// 并发的时候显示错误会中断线程,先临时保存
			if (tempException == null) {
				tempException = e;
			}
			this.handUIException(e);
			this.abort();
		}
		if (!info.containsKey("AdjustInfo")) {
			ScheduleAdjustGattReqInfo adjustGanttReq = new ScheduleAdjustGattReqInfo();
			adjustGanttReq.setHandler(SysContext.getSysContext().getCurrentUserInfo());
			adjustGanttReq.setCreator(adjustGanttReq.getHandler());
			adjustGanttReq.setReqDate(new Date());
			// 最开始周老大把它做成了枚举，按照需求应该是F7基础资料的值 by cassiel 2010-06-16
			// adjustGanttReq.setAdjustReason(ScheduleCreateReasonEnum.InitVer);
			adjustGanttReq.setAdjustType(ScheduleAdjustTypeEnum.ADDDELNEWTASK);
			adjustGanttReq.setAffect(ScheduleAffectEnum.MainSchedule);
			adjustGanttReq.setCurProject(info.getProject());
			adjustGanttReq.setOrgUnit(info.getProject().getFullOrgUnit());
			info.put("AdjustInfo", adjustGanttReq);
		}

		return info;
	}

	public FDCScheduleInfo getNewTaskScheduleInfo() {
		return super.getNewTaskScheduleInfo();
	}

	protected void getNumberByCodingRule(IObjectValue caller, String orgId) {
		try {
			ICodingRuleManager codingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
			if (orgId == null || orgId.trim().length() == 0) {
				orgId = OrgConstants.DEF_CU_ID;
			}
			if (codingRuleManager.isExist(caller, orgId)) {

				String number = "";
				if (codingRuleManager.isUseIntermitNumber(caller, orgId)) { // 此处的orgId与步骤1
					// ）
					// 的orgId时一致的
					// ，
					// 判断用户是否启用断号支持功能
					if (codingRuleManager.isUserSelect(caller, orgId)) {
						// 启用了断号支持功能,同时启用了用户选择断号功能
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(caller, orgId, null);
						//要判断是否存在断号,是则弹出,否则不弹///////////////////////////////////
						// ///////
						Object object = null;
						if (codingRuleManager.isDHExist(caller, orgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						} else {
							// 如果没有使用用户选择功能,直接getNumber用于保存,为什么不是read?
							// 是因为使用用户选择功能也是get!
							number = codingRuleManager.getNumber(caller, orgId);
						}
					} else {
						// 只启用了断号支持功能，此时只是读取当前最新编码，真正的抢号在保存时
						number = codingRuleManager.readNumber(caller, orgId);
					}
				} else {
					// 没有启用断号支持功能，此时获取了编码规则产生的编码
					String costCenterId = null;
					if (editData instanceof FDCScheduleInfo && ((FDCScheduleInfo) editData).getOrgUnit() != null) {
						costCenterId = ((FDCScheduleInfo) editData).getOrgUnit().getId().toString();
					} else {
						costCenterId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
					}
					number = prepareNumberForAddnew(codingRuleManager, (FDCScheduleInfo) editData, orgId, costCenterId, null);
					// iCodingRuleManager.getNumber(caller, orgId);
				}
				// 把number的值赋予caller中相应的属性，并把TEXT控件的编辑状态设置好。
				prepareNumber(caller, number);
				if (codingRuleManager.isModifiable(caller, orgId)) {
					// 如果启用了用户可修改
					setNumberTextEnabled();
				}
				return;
			}
		} catch (Exception e) {
			handleCodingRuleError(e);
			setNumberTextEnabled();
			e.printStackTrace();
		}
		setNumberTextEnabled();
	}

	public FDCScheduleInfo getSaveSchedule() {
		return super.getSaveSchedule();
	}

	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		boolean isAdjust = BOSUuid.read(pk.toString()).getType().equals(new ScheduleAdjustGattReqInfo().getBOSType());
		if (isAdjust) {

			if (editData != null) {
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("*");
				sic.add("adjustReason.*");
				sic.add("adjustReason.name");
				sic.add("adjustReason.number");
				sic.add("baseVer.*");
				sic.add("newVer.*");
				sic.add("entrys.*");
				sic.add("entrys.wbs.*");
				sic.add("creator.id");
				sic.add("creator.name");
				sic.add("creator.number");
				sic.add("handler.id");
				sic.add("handler.name");
				sic.add("handler.number");
				ScheduleAdjustGattReqInfo adjustInfo = ScheduleAdjustGattReqFactory.getRemoteInstance().getScheduleAdjustGattReqInfo(pk, sic);
				editData.put("AdjustInfo", adjustInfo);
				editData.setBaseVer(adjustInfo.getNewVer());
				return editData;
			} else {
				FDCScheduleInfo schedule = this.getNewData();
				if (schedule.containsKey("AdjustInfo")) {
					ScheduleAdjustGattReqInfo adjustInfo = (ScheduleAdjustGattReqInfo) schedule.get("AdjustInfo");
					if (adjustInfo.getState().equals(FDCBillStateEnum.AUDITTED) || adjustInfo.getState().equals(FDCBillStateEnum.AUDITTING)) {
						schedule.setBoolean("WEAKMODE", false);
					}
				}
				return schedule;
			}
		}
		if (this.editData != null) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("number");

			FDCScheduleInfo info = FDCScheduleFactory.getRemoteInstance().getFDCScheduleInfo(pk, sic);
			if (info.getNumber() != null && info.getNumber().length() > 0) {
				editData.setNumber(info.getNumber());
			}
			return this.editData;
		}
		// 取出来的应该是隔离的完整的任务树
		return FDCScheduleFactory.getRemoteInstance().getScheduleInfo(pk);
	}

	protected void setNumberTextEnabled() {
		if (this.reqNumber != null) {
			String currentOrgId = null;
			if (editData instanceof FDCScheduleInfo && ((FDCScheduleInfo) editData).getOrgUnit() != null) {
				currentOrgId = ((FDCScheduleInfo) editData).getOrgUnit().getId().toString();
			} else {
				OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
				currentOrgId = org.getId().toString();
			}

			if (currentOrgId != null) {
				boolean isAllowModify = true;
				try {
					ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
					if (iCodingRuleManager.isExist(editData.getObjectValue("AdjustInfo"), currentOrgId)) {
						isAllowModify = iCodingRuleManager.isModifiable(editData.getObjectValue("AdjustInfo"), currentOrgId);
					} else {
						isAllowModify = true;
					}
				} catch (Exception e) {
					ExceptionHandler.handle(e);
				}
				this.reqNumber.setEnabled(isAllowModify);
				this.reqNumber.setEditable(isAllowModify);
			}
		}
	}

	protected void handleCodingRule() throws BOSException, CodingRuleException, EASBizException {
		String currentOrgId = "";

		if (editData instanceof FDCScheduleInfo && ((FDCScheduleInfo) editData).getOrgUnit() != null) {
			currentOrgId = ((FDCScheduleInfo) editData).getOrgUnit().getId().toString();
		} else {
			OrgUnitInfo org = SysContext.getSysContext().getCurrentOrgUnit();
			currentOrgId = org.getId().toString();
		}
		ICodingRuleManager codingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		if (!OprtState.ADDNEW.equals(getOprtState())) {
			return;
		}
		boolean isExist = true;
		if (currentOrgId.length() == 0 || !codingRuleManager.isExist(editData.getObjectValue("AdjustInfo"), currentOrgId)) {
			isExist = false;
		}
		if (isExist) {
			// 是否新增显示
			boolean isAddView = codingRuleManager.isAddView(editData.getObjectValue("AdjustInfo"), currentOrgId);
			if (isAddView) { // 新增显示
				getNumberByCodingRule(editData.getObjectValue("AdjustInfo"), currentOrgId);
			} else {
				String number = null;
				if (codingRuleManager.isUseIntermitNumber(editData, currentOrgId) && codingRuleManager.isUserSelect(editData, currentOrgId)) {
					CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(editData.getObjectValue("AdjustInfo"), currentOrgId, null,
							null);
					Object obj = null;
					if (codingRuleManager.isDHExist(editData.getObjectValue("AdjustInfo"), currentOrgId)) {
						intermilNOF7.show();
						obj = intermilNOF7.getData();
					}
					if (obj != null) {
						number = obj.toString();
					}
				}
				this.reqNumber.setText(number);
			}
			setNumberTextEnabled();
		}
	}

	public boolean isSaveAction() {
		return super.isSaveAction();
	}

	public void onShow() throws Exception {
		super.onShow();
		this.menuWorkflow.setVisible(false);
	}

	protected void prepareNumber(IObjectValue caller, String number) {
		this.reqNumber.setText(number);
	}

	protected String prepareNumberForAddnew(ICodingRuleManager codingRuleManager, FDCScheduleInfo info, String orgId, String costerId,
			String bindingProperty) throws Exception {

		String number = null;
		FilterInfo filter = null;
		int i = 0;
		do {
			// 如果编码重复重新取编码

			number = codingRuleManager.getNumber(editData.getObjectValue("AdjustInfo"), orgId);

			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number", number));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID.getValue(), CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", costerId));
			if (info.getId() != null) {
				ScheduleAdjustGattReqInfo adjustInfo = (ScheduleAdjustGattReqInfo) editData.getObjectValue("AdjustInfo");
				if (adjustInfo.getId() != null) {
					filter.getFilterItems().add(new FilterItemInfo("id", adjustInfo.getId().toString(), CompareType.NOTEQUALS));
				}

			}
			i++;
		} while (ScheduleAdjustGattReqFactory.getRemoteInstance().exists(filter) && i < 1000);
		return number;

	}

	public IObjectPK runSave() throws Exception {
		return super.runSave();
	}

	public IObjectPK runSubmit() throws Exception {
		return super.runSubmit();
	}

	public void setSaveAction(boolean isSaveAction) {
		super.setSaveAction(isSaveAction);
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
	}

	protected void verifyInputForSave() throws Exception {
		super.verifyInputForSave();
	}

	protected void verifyInputForSubmit() throws Exception {
		super.verifyInputForSubmit();
	}

	protected void beforeStoreFields(ActionEvent e) throws Exception {
		super.beforeStoreFields(e);
	}

	protected KDTable getDetailTable() {
		return super.getDetailTable();
	}

	public ScheduleGanttProject getScheduleGanttProject() {

		return super.getScheduleGanttProject();
	}

	protected void initGanttProject() {

		super.initGanttProject();
	}

	protected boolean isOnlyView() {

		return super.isOnlyView();
	}

	protected void load2Gantt(ScheduleBaseInfo info) throws Exception {

		super.load2Gantt(info);
	}

	protected void loadData() throws Exception {

		super.loadData();
	}

	protected void resetMenu() {

		super.resetMenu();
	}

	protected void resetToolBar() {

		super.resetToolBar();
	}

	protected void storeTasks() {

		super.storeTasks();
	}

	public void actionVersionCompare_actionPerformed(ActionEvent e) throws Exception {

		if (!editData.containsKey("AdjustInfo")) {
			return;
		}
		ScheduleAdjustGattReqInfo adjustGanttReq = (ScheduleAdjustGattReqInfo) editData.get("AdjustInfo");
		// if(adjustGanttReq.getNewVer()==null){
		// FDCMsgBox.showInfo(this,"请先保存调整单！");
		// return;
		// }
		isVersionCompare = !isVersionCompare;
		if (isVersionCompare) {
			this.btnVersionCompare.setText("隐藏版本对比");
			Task[] tTasks = this.getScheduleGanttProject().getTaskManager().getTasks();
			ArrayList tasks = new ArrayList();
			for (int i = 0; i < tTasks.length; i++) {
				if (tTasks[i] instanceof KDTask) {
					KDTask tTask = (KDTask) tTasks[i];
					if (tTask.getMyOldStartDate() == null)
						continue;
					GanttPreviousStateTask task = new GanttPreviousStateTask(tTask.getTaskID(), tTask.getMyOldStartDate() == null ? tTask.getStart()
							: tTask.getMyOldStartDate(), tTask.getMyOldDuration() == null ? 0 : tTask.getMyOldDuration().intValue(), false, false);
					tasks.add(task);
				}
			}

			this.getScheduleGanttProject().getArea().setPreviousStateTasks(tasks);
			this.getScheduleGanttProject().getArea().repaint();

		} else {
			this.btnVersionCompare.setText("版本对比");
			if (editData.containsKey("AdjustInfo")) {
				if (adjustGanttReq != null) {
					if (FDCBillStateEnum.SAVED.equals(adjustGanttReq.getState()) || FDCBillStateEnum.SUBMITTED.equals(adjustGanttReq.getState())) {
						this.actionEdit.setEnabled(true);
					} else {
						this.actionEdit.setEnabled(false);
					}
				}
			}
			if (this.getOprtState().equals("EDIT")) {
				this.actionEdit.setEnabled(false);
			}
			this.getScheduleGanttProject().getArea().setPreviousStateTasks(null);
			this.getScheduleGanttProject().getArea().repaint();
		}

	}

	public IWorkflowUIEnhancement getWorkflowUIEnhancement() {
		return new DefaultWorkflowUIEnhancement() {
			public List getApporveToolButtons(CoreUIObject uiObject) {
				List toolButtionsList = new ArrayList();
				btnVersionCompare.setVisible(true);
				toolButtionsList.add(btnVersionCompare);
				return toolButtionsList;
			}
		};
	}


}