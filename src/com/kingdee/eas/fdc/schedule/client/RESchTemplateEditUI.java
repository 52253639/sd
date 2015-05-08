/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.kdf.table.CellTreeNode;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.NodeClickListener;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.util.backport.Collections;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.schedule.FDCScheduleConstant;
import com.kingdee.eas.fdc.schedule.GTNBizTypeCollection;
import com.kingdee.eas.fdc.schedule.GTNBizTypeInfo;
import com.kingdee.eas.fdc.schedule.GlobalTaskNodeCollection;
import com.kingdee.eas.fdc.schedule.GlobalTaskNodeInfo;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.fdc.schedule.RESchTemplateCatagoryInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskPredecessorCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskPredecessorInfo;
import com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTemplateTypeEnum;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.fdc.schedule.client.RESchMSProjectReader.IRESchCalendar;
import com.kingdee.eas.fdc.schedule.client.RESchMSProjectReader.IRESchTaskCreator;
import com.kingdee.eas.fdc.schedule.client.RESchMSProjectReader.IRESchTaskPredecessorCreator;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarFactory;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.util.IRESchTask;
import com.kingdee.eas.fdc.schedule.framework.util.IRESchTaskPredecessor;
import com.kingdee.eas.fdc.schedule.framework.util.KDProjectWriter;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.client.AdvMsgBox;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 
 * @(#)						
 * 版权：		金蝶国际软件集团有限公司版权所有 描述： 计划管理任务列表编辑界面
 * 
 * @author 罗小龙
 * @version EAS7.0
 * @createDate 2011-08-04
 * @see
 */
public class RESchTemplateEditUI extends AbstractRESchTemplateEditUI {
	// 缺省版本号
	private static final long serialVersionUID = 1L;

	// 日志
	private static final Logger logger = CoreUIObject
			.getLogger(RESchTemplateEditUI.class);

	// 编码
	private Map preTasksMap = new HashMap();
	private Set needProcessRow = new HashSet();
	private RESchTemplateCatagoryInfo catagory = null;
	private ScheduleTemplateTypeEnum type = null;
	private RESchTemplateTaskInfo parent = null;
	/**
	 * 上一次选择导入的Project文件，以便作为再次导入的默认文件
	 */
	private static String preProjectFileName = System.getProperty("user.home");
	
	public boolean destroyWindow() {
//		return super.destroyWindow();
		disposeUIWindow();
		return true;
	}

	/**
	 * output class constructor
	 */
	public RESchTemplateEditUI() throws Exception {
		super();
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (editData.getState().equals(FDCBillStateEnum.AUDITTING) || editData.getState().equals(FDCBillStateEnum.AUDITTED)) {
			FDCMsgBox.showError("当前单据状态为" + editData.getState().getAlias() + ",不能进行操作！");
			abort();
		}
		if (!editData.getOrgUnit().getId().equals(SysContext.getSysContext().getCurrentFIUnit().getId())) {
			FDCMsgBox.showError("不能维护 " + editData.getOrgUnit().getName() + " 的模板！");
			abort();
		}
		super.actionEdit_actionPerformed(e);
		this.btnSubmit.setEnabled(false);
		this.btnSave.setEnabled(true);
		this.btnEdit.setEnabled(false);
		this.btnImportPlan.setEnabled(true);
		this.btnImportProject.setEnabled(true);
		this.btnExportProject.setEnabled(true);
		this.btnAddTask.setEnabled(true);
		this.btnAddChildTask.setEnabled(true);
		this.btnRemoveTask.setEnabled(true);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.tblTask.checkParsed();
		this.btnAddTask.setIcon(EASResource.getIcon("imgTbtn_addline"));
		this.btnRemoveTask.setIcon(EASResource.getIcon("imgTbtn_deleteline"));
		this.btnAddChildTask.setIcon(EASResource.getIcon("imgTbtn_insert"));

		this.btnSave.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.btnSubmit.setIcon(EASResource.getIcon("imgTbtn_submit"));
		
		this.btnImportPlan.setIcon(EASResource.getIcon("imgTbtn_upenumnew"));
		this.btnImportProject.setIcon(EASResource.getIcon("imgTbtn_checklose"));
		this.btnExportProject.setIcon(EASResource.getIcon("imgTbtn_checkprofit"));
		this.btnSetStdDuration.setIcon(EASResource.getIcon("imgTbtn_inputmetadata"));
		
//		if (editData.getTemplateType().equals(ScheduleTemplateTypeEnum.OtherPlanTemplate)) {
//			this.btnImportPlan.setVisible(true);
//		}
		
		this.btnSetStdDuration.setEnabled(true);
		this.contTask.addButton(this.btnAddTask);
		//增加子任务
		this.contTask.addButton(this.btnAddChildTask);
		this.contTask.addButton(this.btnRemoveTask);

		this.tblTask.getColumn("taskNumber").getStyleAttributes().setNumberFormat("0#");
		
		if (OprtState.ADDNEW.equals(getOprtState()) || OprtState.EDIT.equals(getOprtState())) {
			this.btnImportPlan.setEnabled(true);
			this.btnImportProject.setEnabled(true);
			this.btnExportProject.setEnabled(true);
			this.btnAddTask.setEnabled(true);
			this.btnAddChildTask.setEnabled(true);
			this.btnRemoveTask.setEnabled(true);
		} else {
			if (SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit()) {
				this.btnAddNew.setEnabled(true);
				this.btnEdit.setEnabled(true);
//				this.btnSubmit.setEnabled(false);
//				this.btnSave.setEnabled(true);
//				this.btnEdit.setEnabled(false);
			}
			this.btnImportPlan.setEnabled(false);
			this.btnImportProject.setEnabled(false);
			this.btnExportProject.setEnabled(true);
			this.btnAddTask.setEnabled(false);
			this.btnAddChildTask.setEnabled(false);
			this.btnRemoveTask.setEnabled(false);
		}
		
		
		FDCTableHelper.disableDelete(tblTask);
		
		if (editData.getTemplateType() != null) {
			if (editData.getTemplateType().equals(ScheduleTemplateTypeEnum.MainPlanTemplate)) {
				this.setUITitle("主项计划模板编制");
			} else {
				this.setUITitle("专项计划模板编制");
				this.actionSetStdDuration.setVisible(false);
			}
		}
		
		if (getUIContext().get("fromSchedule") != null) {
			this.actionAddNew.setVisible(false);
			this.actionEdit.setVisible(false);
			this.actionSave.setVisible(false);
			this.actionSubmit.setVisible(false);
			this.actionImportProject.setVisible(false);
			this.actionImportTemplate.setVisible(false);
			this.getUIMenuBar().setVisible(false);
		}
		this.catagory = editData.getCatagory();
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		this.catagory = editData.getCatagory();
		this.type = editData.getTemplateType();
		super.actionAddNew_actionPerformed(e);
		
		this.btnImportPlan.setEnabled(true);
		this.btnImportProject.setEnabled(true);
		this.btnExportProject.setEnabled(true);
		this.btnAddTask.setEnabled(true);
		this.btnAddChildTask.setEnabled(true);
		this.btnRemoveTask.setEnabled(true);
		this.txtNumber.setMaxLength(4);
	}

	public void onShow() throws Exception {
		super.onShow();
		this.btnSubmit.setEnabled(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.tblTask.getColumn("taskName").getStyleAttributes().setLocked(false);
		/* 设置名称的最大长度 */
		this.txtName.setMaxLength(100);
		this.txtNumber.setMaxLength(4);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		int taskCount = tblTask.getRowCount();
		editData.setLongNumber(editData.getNumber());
		
		RESchTemplateTaskCollection entries = new RESchTemplateTaskCollection();
		RESchTemplateTaskInfo task = null;
		for (int i = 0; i < taskCount; i++) {
           task = (RESchTemplateTaskInfo) tblTask.getRow(i).getUserObject();
           task.setTemplate(editData);
			// task.setId(BOSUuid.create(task.getBOSType()));
           task.setSeq(i);
			entries.add(task);
		}

		if (editData.getEntry() != null) {
			editData.getEntry().clear();
		}
		editData.getEntry().addCollection(entries);
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
	}
	
	
	protected void handleOldData() {
		if (!(getOprtState() == STATUS_VIEW)) {
			FDCUIWeightWorker.getInstance().addWork(new IFDCWork() {
				public void run() {
					storeFields();
					initOldData(editData);
				}
			});
		}
	}
	public boolean isModify() {
		return super.isModify();
	}
	
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		/* modified by zhaoqin on 2014/06/24 */
		Pattern tDouble = Pattern.compile("([0-9]{1,4})");
		if (!tDouble.matcher(this.txtNumber.getText().trim()).matches()) {
			FDCMsgBox.showInfo("计划模板编码只允许录入不超过4个的数字！");
		  	return ;	
		}
		
		super.actionSave_actionPerformed(e);
		this.btnSubmit.setEnabled(true);
		this.btnEdit.setEnabled(true);
		this.btnSave.setEnabled(false);
    	/*Pattern tDouble = Pattern.compile("([0-9]{1,4})");
    	if (!tDouble.matcher(this.txtNumber.getText().trim()).matches()) {
			FDCMsgBox.showInfo("计划模板编码只允许录入不超过4个的数字！");
		  	return ;	
		}*/
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if (this.tblTask.getRowCount() == 0) {
			FDCMsgBox.showError("当前模板任务为空，不能提交！");
			abort();
		}
//    	Pattern tDouble = Pattern.compile("([0-9]{1,4})");
//		if (!tDouble.matcher(this.txtNumber.getText().trim()).matches()) {
//			FDCMsgBox.showInfo("计划模板编码只允许录入不超过4个的数字！");
//		  	return ;	
//		}
		this.editData.setState(FDCBillStateEnum.SUBMITTED);
		RESchTemplateTaskInfo currTask = null;
		ScheduleBizTypeInfo bizType = null;
		StringBuffer errMsg = new StringBuffer();
		int err = 0;
		for (int i = 0; i < tblTask.getRowCount(); i++) {
			currTask = (RESchTemplateTaskInfo) tblTask.getRow(i).getUserObject();
			for (int j = 0; j < currTask.getBusinessType().size(); j++) {
				if (currTask.getBusinessType().get(j).getBizType().getId().toString().equals(FDCScheduleConstant.ACHIEVEMANAGER)) {
					if (currTask.getAchievementType() == null) {
						err++;
						errMsg.append("模板任务列表中第");
						errMsg.append(i + 1);
						errMsg.append("行的阶段性成果不能为空！\n");
					}
				}
			}
		}

		if (err > 0) {
			FDCMsgBox.showDetailAndOK(this, "提交时发生如下错误，请修改后再提交！", errMsg.toString(), AdvMsgBox.ERROR_MESSAGE);
			abort();
		}
		this.catagory = editData.getCatagory();
		this.type = editData.getTemplateType();
//		super.actionSave_actionPerformed(e);
		if (editData.getTemplateType().equals(ScheduleTemplateTypeEnum.MainPlanTemplate)){
			verifyIncludeGlobalTask();
		}
		super.actionSubmit_actionPerformed(e);
		this.btnEdit.setEnabled(true);
		this.btnSave.setEnabled(false);
		this.btnSubmit.setEnabled(false);
		handleOldData();
	}

	private void verifyIncludeGlobalTask() {
		GlobalTaskNodeCollection cols = FDCScheduleBaseHelper.getGlobalTaskNode();
		Map newMap = new HashMap();
		for (int i = 0; i < cols.size(); i++) {
			GlobalTaskNodeInfo taskNode = cols.get(i);
//			newMap.put(taskNode.getId().toString(), taskNode);
			//name控制
			newMap.put(taskNode.getName().toString(), taskNode);
		}
		if (newMap.isEmpty()) {
			return;
		}
	    RESchTemplateTaskCollection tasks = editData.getEntry();
	    RESchTemplateTaskInfo task = null;
	    int size = tasks.size();
		for (int i = 0; i < size; i++) {
             task = tasks.get(i);
             //name控制
             if(newMap.containsKey(task.getName().toString())){
 				newMap.remove(task.getName().toString());
             }
//			if (StringUtils.isEmpty(task.getSrcGroupNode())) {
//				continue;
//			} else {
//				newMap.remove(task.getSrcGroupNode());
//			}
		}
		
		if (!newMap.isEmpty()) {
			StringBuffer errMsg = new StringBuffer();
			Set set = newMap.entrySet();
			for (Iterator it = set.iterator(); it.hasNext();) {
				errMsg.append(((GlobalTaskNodeInfo) ((Entry) it.next()).getValue()).getName());
				errMsg.append("\n");
			}
//			FDCMsgBox.showDetailAndOK(this, "当前模板任务中未包含以下集团管控节点!", errMsg.toString(), MsgBox.ICONERROR);
			int  flag = FDCMsgBox.showConfirm3a(this, "当前模板任务中未包含以下集团管控节点!是否继续提交", errMsg.toString());
			if(flag != 0){				
				abort();
			}
//			abort();
		}
		newMap.clear();
	}
	private void verifyIncludeGlobalTask(RESchTemplateTaskCollection tasks) {
		GlobalTaskNodeCollection cols = FDCScheduleBaseHelper.getGlobalTaskNode();
		Map newMap = new HashMap();
		for (int i = 0; i < cols.size(); i++) {
			GlobalTaskNodeInfo taskNode = cols.get(i);
			newMap.put(taskNode.getName().toString(), taskNode);
		}
		if (newMap.isEmpty()) {
			return;
		}
		RESchTemplateTaskInfo task = null;
		GlobalTaskNodeInfo nodeInfo = null;
		int size = tasks.size();
		for (int i = 0; i < size; i++) {
			task = tasks.get(i);
//			if (newMap.containsKey(task.getName())) {
//				nodeInfo = (GlobalTaskNodeInfo) newMap.get(task.getName());
//				task.setSrcGroupNode(nodeInfo.getId().toString());
//				newMap.remove(task.getName());
//			}
            if(newMap.containsKey(task.getName().toString())){
 				newMap.remove(task.getName().toString());
             }
		}

		if (!newMap.isEmpty()) {
			StringBuffer errMsg = new StringBuffer();
			Set set = newMap.entrySet();
			for (Iterator it = set.iterator(); it.hasNext();) {
				errMsg.append(((GlobalTaskNodeInfo) ((Entry) it.next()).getValue()).getName());
				errMsg.append("\n");
			}

//			FDCMsgBox.showDetailAndOK(this, "当前project文件中未包含以下集团管控节点!是否继续提交", errMsg.toString(), MsgBox.ICONERROR);
			int  flag = FDCMsgBox.showConfirm3a(this, "当前project文件中未包含以下集团管控节点!是否继续提交", errMsg.toString());
			if(flag != 0){				
				abort();
			}
		}
		newMap.clear();
	}


	protected FDCTreeBaseDataInfo getEditData() {
		return editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return txtName;
	}

	protected IObjectValue createNewData() {
		RESchTemplateInfo info = new RESchTemplateInfo();
		info.setName(null);
		info.setNumber(null);
		info.setId(BOSUuid.create(info.getBOSType()));
		info.setOrgUnit(SysContext.getSysContext().getCurrentFIUnit().castToFullOrgUnitInfo());
		if (getUIContext().get("catagory") != null) {
			RESchTemplateCatagoryInfo catagory = (RESchTemplateCatagoryInfo) getUIContext().get("catagory");
			info.setCatagory(catagory);
			if (catagory.getTemplateType() != null) {
				info.setTemplateType(catagory.getTemplateType());
			}
		} else {
			info.setCatagory(catagory);
			
			Object ownerObj = getUIContext().get("Owner");
			if (null != ownerObj && ownerObj instanceof FDCScheduleBaseEditUI) {
				if (ownerObj instanceof MainScheduleEditUI) {
					info.setTemplateType(ScheduleTemplateTypeEnum.MainPlanTemplate);
				} else {
					info.setTemplateType(ScheduleTemplateTypeEnum.OtherPlanTemplate);
				}
			}
			/* modified by zhaoqin on 2014/06/26 */
			if(null == info.getTemplateType()) {
				info.setTemplateType(this.catagory.getTemplateType());
			}
		}
		
		// 自动带入集团管控节点的ABC类任务
		if (ScheduleTemplateTypeEnum.MainPlanTemplate.equals(info.getTemplateType())) {
			GlobalTaskNodeCollection cols = FDCScheduleBaseHelper.getGlobalTaskNode();
			RESchTemplateTaskCollection tasks = generateTaskByGlobalTask(cols);
			
			info.getEntry().addCollection(tasks);
		}
		return info;
	}

	private RESchTemplateTaskCollection generateTaskByGlobalTask(GlobalTaskNodeCollection cols) {
		RESchTemplateTaskCollection tasks = new RESchTemplateTaskCollection();
		GlobalTaskNodeInfo taskNode = null;
		for (int i = 0; i < cols.size(); i++) {
			taskNode = cols.get(i);
			RESchTemplateTaskInfo task = new RESchTemplateTaskInfo();
			task.setSrcGroupNode(taskNode.getId().toString());
			task.setId(BOSUuid.create(task.getBOSType()));
			task.setName(taskNode.getName());
			task.setNumber(taskNode.getNumber());
			task.setLongNumber(taskNode.getNumber());
			task.setLevel(1);
			task.setIsLeaf(true);
			task.setAchievementType(taskNode.getAchType());
			task.setTaskType(RESchTaskTypeEnum.MILESTONE);
			/*TODO 金地分支的三个属性，后面要去掉
			 * task.setIsBuildingNode(taskNode.isIsBuidingPlan());
			task.setIsOperationNode(taskNode.isIsOperationPlan());
			task.setTaskType(taskNode.getTaskType());*/
			GTNBizTypeCollection biztypes = taskNode.getBizType();
			GTNBizTypeInfo bizType = null;
			for (Iterator it = biztypes.iterator(); it.hasNext();) {
				bizType = (GTNBizTypeInfo) it.next();
				RESchTemplateTaskBizTypeInfo taskBiz = new RESchTemplateTaskBizTypeInfo();
				taskBiz.setParent(task);
				taskBiz.setId(BOSUuid.create(taskBiz.getBOSType()));
				taskBiz.setBizType(bizType.getBizType());
				task.getBusinessType().add(taskBiz);
			}
			tasks.add(task);
		}
		// TODO Auto-generated method stub
		return tasks;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RESchTemplateFactory.getRemoteInstance();
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	public void actionAddTask_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = tblTask.getSelectManager().getActiveRowIndex();
		RESchTemplateTaskInfo parent = null;
		UIContext uiContext = new UIContext();
//		if (rowIndex > -1) {
//			 parent = (RESchTemplateTaskInfo) tblTask.getRow(rowIndex).getUserObject();
//			 uiContext.put(UIContext.PARENTNODE, parent);
//			 uiContext.put("parent", parent);
//		}
		uiContext.put("fromUI", this);
		uiContext.put("tasks", getExistTask());
		uiContext.put("template", editData);
		IUIWindow addTaskDialog = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RESchTemplateTaskEditUI.class.getName(), uiContext, null,
				OprtState.ADDNEW);
		addTaskDialog.show();
	}
	
	public void actionAddChildTask_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = tblTask.getSelectManager().getActiveRowIndex();
		RESchTemplateTaskInfo parent = null;
		UIContext uiContext = new UIContext();
		if (rowIndex > -1) {
			 parent = (RESchTemplateTaskInfo) tblTask.getRow(rowIndex).getUserObject();
			 uiContext.put(UIContext.PARENTNODE, parent);
			 uiContext.put("parent", parent);
		}
		uiContext.put("fromUI", this);
		uiContext.put("tasks", getExistTask());
		uiContext.put("template", editData);
		IUIWindow addTaskDialog = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RESchTemplateTaskEditUI.class.getName(), uiContext, null,
				OprtState.ADDNEW);
		addTaskDialog.show();
	}
	/**
	 * 从新增界面传回模板任务，然后更新任务列表 问题点：由于要显示前置任务，seq如何更新的问题
	 * 
	 * @param task
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public void refreshTable(RESchTemplateTaskInfo task) throws IllegalArgumentException, IllegalAccessException {
		needProcessRow.clear();
		if (task == null) {
			return;
		}
		this.tblTask.checkParsed();
		RESchTemplateTaskInfo currTask = null;
		RESchTemplateTaskCollection alreadyCols = getExistTask();
		// 对现有任务进行修改
		if (alreadyCols.contains(task.getId())) {
			RESchTemplateTaskInfo tempTask = null;
			tempTask = alreadyCols.get(task.getId());
			tempTask = task;
		} else {// 新增任务，需要确认插入点
			int insertIndex = alreadyCols.size() - 1;
			for (int i = 0; i < alreadyCols.size(); i++) {
				currTask = alreadyCols.get(i);
				if (task.getParent() != null && task.getParent().getId().equals(currTask.getId())) {
					currTask.setIsLeaf(false);
					insertIndex = i;
				}
				if (task.getParent() != null && currTask.getParent() != null && task.getParent().getId().equals(currTask.getParent().getId())) {
					insertIndex = i;
				}
			}
			// alreadyCols.add(task);
			alreadyCols.insertObject(insertIndex, task);
		}
		if (task.getParent() != null) {
			RESchTemplateTaskInfo pTask = task.getParent();
			for (int i = 0; i < alreadyCols.size(); i++) {
				currTask = alreadyCols.get(i);
				if (currTask.getId().equals(pTask.getId())) {
					currTask.setIsLeaf(false);
					if (currTask.getReferenceDay() < task.getReferenceDay()) {
						currTask.setReferenceDay(task.getReferenceDay());
					}
				}
			}
		}
		// int size = alreadyCols.size();
		// this.tblTask.removeRows(false);
		// for (int i = 0; i < size; i++) {
		// IRow row = tblTask.addRow();
		// currTask = alreadyCols.get(i);
		// fillDataToTable(row, currTask);
		// }
		refreshTable(alreadyCols);
		 updateUI();
	}
	
	public void refreshTable(RESchTemplateTaskCollection alreadyCols) {
		this.tblTask.checkParsed();
		int size = alreadyCols.size();
		this.tblTask.removeRows(false);
		needProcessRow.clear();
		for (int i = 0; i < size; i++) {
			IRow row = tblTask.addRow();
			RESchTemplateTaskInfo currTask = alreadyCols.get(i);
			fillDataToTable(row, currTask);
		}
		if (!needProcessRow.isEmpty()) {
			for (Iterator it = needProcessRow.iterator(); it.hasNext();) {
				int j = Integer.parseInt(it.next() + "");
				if (tblTask.getRow(j) != null) {
					RESchTemplateTaskInfo task = (RESchTemplateTaskInfo) tblTask.getRow(j).getUserObject();
					if (task != null && task.getPredecessors() != null && !task.getPredecessors().isEmpty())
						processPredecssor(task.getPredecessors(), tblTask.getRow(j));
				}
			}
		}
	}
	
	public void refreshTable(RESchTemplateTaskCollection alreadyCols, boolean isRemoveOld) {
		this.tblTask.checkParsed();
		needProcessRow.clear();
		RESchTemplateTaskCollection totalCols = null;
		if (!isRemoveOld) {
			totalCols = getExistTask();
			totalCols.addCollection(alreadyCols);
		} else {
			totalCols = alreadyCols;
		}
		refreshTable(totalCols);
		// int size = alreadyCols.size();
		// this.tblTask.removeRows(false);
		// for (int i = 0; i < size; i++) {
		// IRow row = tblTask.addRow();
		// RESchTemplateTaskInfo currTask = alreadyCols.get(i);
		// fillDataToTable(row, currTask);
		// }
		//		
		// if (!needProcessRow.isEmpty()) {
		// for (Iterator it = needProcessRow.iterator(); it.hasNext();) {
		// int i = Integer.parseInt(it.next() + "");
		// RESchTemplateTaskInfo task = (RESchTemplateTaskInfo) tblTask.getUserObject();
		// if (task != null && task.getPredecessors() != null && !task.getPredecessors().isEmpty())
		// processPredecssor(task.getPredecessors(), tblTask.getRow(i));
		// }
		// }
	}

	private void fillDataToTable(IRow row, RESchTemplateTaskInfo task) {
		row.getCell("id").setValue(task.getId() == null ? null : task.getId());
		row.getCell("taskNumber").setValue(task.getNumber() == null ? "" : task.getNumber());
		row.getCell("level").setValue(task.getLevel());
		row.getCell("isLeaf").setValue(task.isIsLeaf());
		String value = task.getName() == null ? "" : task.getName();
		CellTreeNode treeNode = new CellTreeNode();
		treeNode.setValue(value);
		treeNode.setTreeLevel(task.getLevel());
		treeNode.setHasChildren(!task.isIsLeaf());
		treeNode.setCollapse(false);
		treeNode.addClickListener(new NodeClickListener() {
			public void doClick(CellTreeNode celltreenode, ICell icell, int i) {
				tblTask.revalidate();
			}
		});
		row.getCell("taskName").setValue(treeNode);
		RESchTemplateTaskPredecessorCollection preTasks = task.getPredecessors();
		if (preTasks.size() > 0) {
			processPredecssor(preTasks, row);
		}
		task.setSeq(row.getRowIndex() + 1);
		row.setUserObject(task);
		if (task.getRESchBusinessType() != null) {
			RESchTemplateTaskBizTypeCollection bizTypes = task.getRESchBusinessType();
			StringBuffer bizTypeDesc = new StringBuffer();
			RESchTemplateTaskBizTypeInfo bizType = null;
			int bizTypeSize = bizTypes.size();
			for (int i = 0; i < bizTypeSize; i++) {
				bizType = bizTypes.get(i);
				if (bizType.getBizType() != null && bizType.getBizType().getName() != null) {
					bizTypeDesc.append(bizType.getBizType().getName());
					if (i < bizTypeSize - 1) {
						bizTypeDesc.append(";");
					}
				}
			}
			row.getCell("bizType").setValue(bizTypeDesc.toString());
		}
		if (!task.isIsLeaf()) {
			row.getStyleAttributes().setBackground(new Color(210, 227, 202));
		}
		row.getCell("taskType").setValue(task.getTaskType() == null ? null : task.getTaskType());
		row.getCell("duration").setValue(task.getReferenceDay());
		preTasksMap.put(task.getId().toString(), task.getSeq());
		row.getCell("taskName").getStyleAttributes().setLocked(false);
	}

	private void processPredecssor(RESchTemplateTaskPredecessorCollection preTasks, IRow row) {
		int size = preTasks.size();
		RESchTemplateTaskPredecessorInfo preTask = null;
		StringBuffer desc = new StringBuffer();
		for (int i = 0; i < size; i++) {
            preTask = preTasks.get(i);
			TaskLinkTypeEnum type = preTask.getPredecessorType();
			int diff = preTask.getDifferenceDay();
			RESchTemplateTaskInfo task = preTask.getPredecessorTask();
			if (preTasksMap.containsKey(task.getId().toString())) {
				int seq = Integer.parseInt(preTasksMap.get(task.getId().toString()) + "");
				desc.append(seq);
				if (type.equals(TaskLinkTypeEnum.FINISH_FINISH)) {
					desc.append("FF");
				} else if (type.equals(TaskLinkTypeEnum.FINISH_START)) {
					if (diff == 0) {
						if (i < size - 1) {
							desc.append(",");
						}
						continue;
					}
					desc.append("FS");
					 
				} else if (type.equals(TaskLinkTypeEnum.START_START)) {
					desc.append("SS");
				}
				if (diff > 0) {
					desc.append("+");
					desc.append(diff);
				} else if (diff < 0) {
					desc.append(diff);
				}
			} else {
				needProcessRow.add(row.getRowIndex());
			}
           if (i < size - 1) {
        	   desc.append(",");
           }
		}
		
		if(desc.length()>0){
			row.getCell("preTask").setValue(desc.toString());
		}

	}

	public void actionRemoveTask_actionPerformed(ActionEvent e) throws Exception {
		ArrayList alist = this.tblTask.getSelectManager().getBlocks();
		int top = -1;
		int bottom = -1;
		/* modified by zhaoqin on 2014/06/23 */
		//if(alist != null){
		if(alist != null && alist.size() > 0){
			top = ((KDTSelectBlock)alist.get(0)).getTop();
			bottom = ((KDTSelectBlock)alist.get(0)).getBottom();
		}
		if( top != -1 && bottom != -1){
			int rowIndex = top;
			if (rowIndex < 0) {
				FDCMsgBox.showError("请选择相应的模板任务");
				abort();
			}
			
			int result = FDCMsgBox.showConfirm3("是否确认删除？");
			if (AdvMsgBox.NO_OPTION == result || AdvMsgBox.CANCEL_OPTION == result) {
				abort();
			}
			
			/* modified by zhaoqin for R20141120-0178 on 2014/12/29 start */
			//for(int j=0,count= bottom - top + 1 ; j < count; j++ ){
			for(int j = bottom; j >= top; j--){
				//RESchTemplateTaskInfo info = (RESchTemplateTaskInfo) this.tblTask.getRow(rowIndex).getUserObject();
				RESchTemplateTaskInfo info = (RESchTemplateTaskInfo) this.tblTask.getRow(j).getUserObject();
				/* modified by zhaoqin for R20141120-0178 on 2014/12/29 end */

				//TODO 不可以删除。。。
//				if (editData.getTemplateType().equals(ScheduleTemplateTypeEnum.MainPlanTemplate) && !StringUtils.isEmpty(info.getSrcGroupNode())) {
//					GlobalTaskNodeCollection cols = FDCScheduleBaseHelper.getGlobalTaskNode();
//					if (cols.contains(BOSUuid.read(info.getSrcGroupNode()))) {
//						FDCMsgBox.showError("当前任务为集团控制节点，不允许删除!");
//						abort();
//					}
//				}
				this.tblTask.removeRow(rowIndex);
				String ownId = null;
				if (!info.isIsLeaf()) {
					ownId = info.getId().toString();
				}
				String id = null;
				if (info.getParent() != null) {
					id = info.getParent().getId().toString();
				}
				if (!StringUtils.isEmpty(id)) {
					updateParent(id);
				}
				if (!StringUtils.isEmpty(ownId)) {
					List list = new ArrayList();
					removeSubTask(list, ownId);
					Collections.sort(list);
					logger.error(list);
					Collections.reverse(list);
					logger.error(list);
					for (int i = 0; i < list.size(); i++) {
						this.tblTask.removeRow(Integer.parseInt(list.get(i) + ""));
					}
					
				}
				removeDependTask(info.getId());
				
				refreshPrePredecssor();
				refreshTable(getExistTask());
			}
		}
//		int rowIndex = this.tblTask.getSelectManager().getActiveRowIndex();
//		if (rowIndex < 0) {
//			FDCMsgBox.showError("请选择相应的模板任务");
//			abort();
//		}
//		
//		int result = FDCMsgBox.showConfirm3("是否确认删除？");
//		if (AdvMsgBox.NO_OPTION == result || AdvMsgBox.CANCEL_OPTION == result) {
//			abort();
//		}
//		
//		RESchTemplateTaskInfo info = (RESchTemplateTaskInfo) this.tblTask.getRow(rowIndex).getUserObject();
//		if (editData.getTemplateType().equals(ScheduleTemplateTypeEnum.MainPlanTemplate) && !StringUtils.isEmpty(info.getSrcGroupNode())) {
//			GlobalTaskNodeCollection cols = FDCScheduleBaseHelper.getGlobalTaskNode();
//			if (cols.contains(BOSUuid.read(info.getSrcGroupNode()))) {
//				FDCMsgBox.showError("当前任务为集团控制节点，不允许删除!");
//				abort();
//			}
//		}
//		this.tblTask.removeRow(rowIndex);
//		String ownId = null;
//		if (!info.isIsLeaf()) {
//			ownId = info.getId().toString();
//		}
//		String id = null;
//		if (info.getParent() != null) {
//			id = info.getParent().getId().toString();
//		}
//		if (!StringUtils.isEmpty(id)) {
//			updateParent(id);
//		}
//		if (!StringUtils.isEmpty(ownId)) {
//			List list = new ArrayList();
//			removeSubTask(list, ownId);
//			Collections.sort(list);
//			logger.error(list);
//			Collections.reverse(list);
//			logger.error(list);
//			for (int i = 0; i < list.size(); i++) {
//				this.tblTask.removeRow(Integer.parseInt(list.get(i) + ""));
//			}
//			
//		}
//		removeDependTask(info.getId());
//		
//		refreshPrePredecssor();
//		refreshTable(getExistTask());
	}

	private void removeDependTask(BOSUuid id) {
		int rowCount = tblTask.getRowCount();
		RESchTemplateTaskInfo task = null;
		IRow row = null;
		RESchTemplateTaskPredecessorCollection cols = null;
		RESchTemplateTaskPredecessorInfo dependInfo = null;
		for (int i = 0; i < rowCount; i++) {
			row = tblTask.getRow(i);
			task = (RESchTemplateTaskInfo) row.getUserObject();
			if (task.getPredecessors() == null || task.getPredecessors().isEmpty()) {
				continue;
			}
			cols = task.getPredecessors();
			for (int j = cols.size(); j > 0; j--) {
				dependInfo = cols.get(j - 1);
				if (dependInfo.getPredecessorTask().getId().equals(id)) {
                    cols.remove(dependInfo);
				}
			}
		}

	}

	/**
	 * 更新上级任务的isLeaf属性,循环全表，并计数，先记录
	 * @param id
	 */
	private void updateParent(String id) {
		int rowCount = tblTask.getRowCount();
		int subCount = 0;
		int rowIndex = -1;
		RESchTemplateTaskInfo currTask = null;
		for (int i = 0; i < rowCount; i++) {
			currTask = (RESchTemplateTaskInfo) tblTask.getRow(i).getUserObject();
			if (currTask.getId().toString().equals(id)) {
				rowIndex = i;
			}
			if (currTask.getParent() != null) {
				if (currTask.getParent().getId().toString().equals(id)) {
					subCount++;
				}
			}
		}
		
		if (rowIndex != -1 && subCount == 0) {
			RESchTemplateTaskInfo task = (RESchTemplateTaskInfo) tblTask.getRow(rowIndex).getUserObject();
			task.setIsLeaf(true);
		}
	}

	/**
	 * 重算前置任务描述
	 * 
	 */
	private void refreshPrePredecssor() {

	}
	
	private RESchTemplateTaskCollection getExistTask() {
		RESchTemplateTaskCollection tasks = new RESchTemplateTaskCollection();
		RESchTemplateTaskPredecessorCollection preTaskCollection = null;
		int rowCount = tblTask.getRowCount();
		RESchTemplateTaskInfo task = null;
		for (int i = 0; i < rowCount; i++) {
			task = (RESchTemplateTaskInfo) this.tblTask.getRow(i).getUserObject();
			tasks.add(task);
			if (task.getPredecessors() != null) {
				preTaskCollection = task.getPredecessors();
				for (int j = 0; j < preTaskCollection.size(); j++) {
                   
				}
			}
		}
		return tasks;
	}
	/**
	 * 删除任务时如果有子任务，一并把子任务删除
	 * @param rowIndex
	 */
	private List removeSubTask(List removeList, String id) {
		int rowCount = tblTask.getRowCount();
		RESchTemplateTaskInfo currTask = null;
		for (int i = 0; i < rowCount; i++) {
			currTask = (RESchTemplateTaskInfo) tblTask.getRow(i).getUserObject();
			if (currTask.getParent() != null) {
				if (currTask.getParent().getId().toString().equals(id)) {
					removeList.add(i);
					if (!currTask.isIsLeaf()) {
						removeSubTask(removeList, currTask.getId().toString());
					}
				}
			}
		}
		
		
      return removeList;
	}

	protected void tblTask_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 2 && e.getButton() == KDTMouseEvent.BUTTON1) {
			int rowIndex = tblTask.getSelectManager().getActiveRowIndex();
			IRow row = this.tblTask.getRow(rowIndex);
			UIContext uiContext = new UIContext();
			RESchTemplateTaskInfo task = (RESchTemplateTaskInfo) row.getUserObject();
			task.setTemplate(editData);
			uiContext.put("id", task.getId().toString());
			uiContext.put("fromUI", this);
			uiContext.put(UIContext.PARENTNODE, task.getParent());
			uiContext.put("taskInfo", task);
			uiContext.put("tasks", getExistTask());
			IUIWindow dlg = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RESchTemplateTaskEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			dlg.show();
		}
	}
	
	/**
	 * 自动生成编码
	 * */
	private String getNewNumber() throws BOSException, SQLException{
		FDCSQLBuilder sql = new FDCSQLBuilder();
		sql.appendSql(" select top 1 fnumber ");
		sql.appendSql(" from T_SCH_RESchTemplate ");
		sql.appendSql(" where FCATAGORYID=? ");
		if(this.catagory !=null && this.catagory.getId().toString()!=null){
			sql.addParam(this.catagory.getId().toString());
		}else if(editData.getCatagory()!=null && editData.getCatagory().getId().toString()!=null){
			sql.addParam(editData.getCatagory().getId().toString());
		}else{
			sql.addParam(" ");
		}
		sql.appendSql(" ORDER BY fnumber desc ");

		IRowSet rs = sql.executeQuery();
		String currMaxNumber = null;
		while (rs.next()) {
			currMaxNumber= rs.getString("fnumber");
		}
		if (StringUtils.isEmpty(currMaxNumber)) {
			return "0001";
		}
		String currNumber = null;
        currNumber = String.valueOf(Integer.parseInt(currMaxNumber) + 1);
        if (Integer.parseInt(currMaxNumber) < 9) {
			currNumber = "000" + currNumber;
		}
		return currNumber;
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("name");
		sic.add("number");
		sic.add("longNumber");
		
		/* modified by zhaoqin on 2014/06/26 */
		sic.add("catagory.id");
		sic.add("catagory.name");
		sic.add("catagory.number");
		sic.add("catagory.templateType");
		
		sic.add("orgUnit.id");
		sic.add("orgUnit.name");
		sic.add("orgUnit.number");
		sic.add("state");
		sic.add("templateType");
		sic.add("entry.id");
		sic.add("entry.name");
		sic.add("entry.tasktype");
		sic.add("entry.number");
		sic.add("entry.longNumber");
		sic.add("entry.referenceday");
		sic.add("entry.isLeaf");
		sic.add("entry.level");
		sic.add("entry.srcGroupNode");
		sic.add("entry.description");		
		sic.add("entry.parent.id");
		sic.add("entry.parent.name");
		sic.add("entry.parent.number");
		sic.add("entry.parent.level");
		sic.add("entry.parent.isLeaf");
		sic.add("entry.parent.longNumber"); /* modified by zhaoqin for R140504-0265 on 2014/06/11 */
		sic.add("entry.standardTask.id");
		sic.add("entry.standardTask.name");
		sic.add("entry.standardTask.number");

		sic.add("entry.achievementType.id");
		sic.add("entry.achievementType.name");
		sic.add("entry.achievementType.number");

		sic.add("entry.businessType.id");
		sic.add("entry.businessType.bizType.id");
		sic.add("entry.businessType.bizType.number");
		sic.add("entry.businessType.bizType.name");

		sic.add("entry.predecessors.id");
		sic.add("entry.predecessors.predecessorTask.id");
		sic.add("entry.predecessors.predecessorTask.name");
		sic.add("entry.predecessors.predecessorTask.number");
		sic.add("entry.predecessors.differenceDay");
		sic.add("entry.predecessors.predecessorType");
		sic.add(new SelectorItemInfo("entry.adminPerson.id"));
		sic.add(new SelectorItemInfo("entry.adminPerson.name"));
		sic.add(new SelectorItemInfo("entry.adminPerson.number"));
		sic.add(new SelectorItemInfo("entry.adminDept.id"));
		sic.add(new SelectorItemInfo("entry.adminDept.name"));
		sic.add(new SelectorItemInfo("entry.adminDept.number"));

		return sic;
	}
	
	public void loadFields() {
		super.loadFields();
		if (editData.getEntry() != null) {
			RESchTemplateTaskCollection cols = editData.getEntry();
			refreshTable(cols);

		}
		/* 为本级编码赋默认值  */
		if (getOprtState().equals(OprtState.ADDNEW)) {
			try {
				this.txtNumber.setText(getNewNumber());
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		needProcessRow.clear();
		preTasksMap.clear();
	}
	
	public String getDefaultTreeNumber(String parentId){
		String number = "";
		/* 根据父节点ID，获得树节点同级编码的最大值 */
		if(!"".equals(parentId.trim())){
			EntityViewInfo view = new EntityViewInfo();
	    	FilterInfo filter = new FilterInfo();
	    	filter.appendFilterItem("catagory", parentId);
	    	view.setFilter(filter);
	    	RESchTemplateCollection templateCollection;
			try {
				templateCollection = RESchTemplateFactory.getRemoteInstance().getRESchTemplateCollection(view);
				//临时变量，取树节点同级编码最大值
	        	int numberInt = 0;
	        	for(int temp = 0; temp < templateCollection.size(); temp ++){
	        		RESchTemplateInfo schTemplateInfo = (RESchTemplateInfo)templateCollection.get(temp);
	        		  Pattern tDouble = Pattern.compile("([0-9]{1,20})");
	        		  if (null != schTemplateInfo.getNumber() && tDouble.matcher(schTemplateInfo.getNumber()).matches()) {
	        			  if(numberInt < Integer.parseInt(schTemplateInfo.getNumber().toString())){
	  	        			numberInt = Integer.parseInt(schTemplateInfo.getNumber().toString());
	  	        		}
	        		  }
	        	}
	        	number = String.valueOf(numberInt == 0 ? 1 : (numberInt + 1)) + "";
	        	if(String.valueOf(number).length() == 1){
					number = "000" + number; 
				}else if(String.valueOf(number).length() == 2){
					number = "00" + number;
				}else if(String.valueOf(number).length() == 3){
					number = "0" + number;
				}
			} catch (BOSException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return number;
	}
	public void actionImportProject_actionPerformed(ActionEvent e) throws Exception {
		int result = FDCMsgBox.showConfirm3("此操作将删除当前模板下的所有任务，是否继续？");
		if (AdvMsgBox.NO_OPTION == result || AdvMsgBox.CANCEL_OPTION == result) {
			abort();
		}
		/*
		 * 执行导入project操作
		 */
		JFileChooser chooser = new JFileChooser(new File("c:\\"));
		/* 设置过虑器 */
		chooser.setFileFilter(new FilterType());
		/* 弹出对话框 */
		chooser.showDialog(null, "导入Project");
		/* 判断是否选中文件 */
		if (chooser.getSelectedFile() == null) {
			return;
		}
		/* 获得选取文件路径 */
		String filePath = chooser.getSelectedFile().getPath();
		File file = new File(filePath);

		/* 取数据 */
		List projectList = RESchMSProjectReader.pasreMSProject(file, new IRESchTaskCreator() {
			public IRESchTask createSchTask() {
				return (IRESchTask) new RESchTemplateTaskInfo();
			}
		}, new IRESchTaskPredecessorCreator() {
			public IRESchTaskPredecessor createSchTaskPredecessor() {
				return new RESchTemplateTaskPredecessorInfo();
			}
		}, new IRESchCalendar() {
			public ScheduleCalendarInfo getSchedule() {
				try {
					return ScheduleCalendarFactory.getRemoteInstance().getDefaultCal(null);
				} catch (BOSException e) {
					handUIException(e);
				} catch (EASBizException e) {
					handUIException(e);
				}
				return new ScheduleCalendarInfo();
			}
		});

		/* 存入以本级任务id为键，本级任务为值。以便后面构建层次结构 */
		Map projectMap = new HashMap();
		for (int k = 0; k < projectList.size(); k++) {
			if (null != projectList.get(k)) {
				RESchTemplateTaskInfo schTemplateTaskInfo = (RESchTemplateTaskInfo) projectList.get(k);
				if (null != schTemplateTaskInfo.getMsProjectId()) {
					projectMap.put(schTemplateTaskInfo.getMsProjectId().toString(), schTemplateTaskInfo);
				}
			}
		}

		/* 存入模板任务数据，并设置层次结构 */
		RESchTemplateTaskCollection coreBaseCollection = new RESchTemplateTaskCollection();
		for (int k = 0; k < projectList.size(); k++) {
			if (null != projectList.get(k)) {
				RESchTemplateTaskInfo schTemplateTaskInfo = (RESchTemplateTaskInfo) projectList.get(k);
				schTemplateTaskInfo.setTemplate(editData);
				Object projectObj = projectMap
						.get(String.valueOf(null == schTemplateTaskInfo.getMsParentPrjId() ? "" : schTemplateTaskInfo.getMsParentPrjId()));
				if (null != projectObj && !"".equals(projectObj.toString().trim())) {
					if (projectObj instanceof RESchTemplateTaskInfo) {
						RESchTemplateTaskInfo parent =(RESchTemplateTaskInfo) projectObj;
						schTemplateTaskInfo.setParent(parent);
						schTemplateTaskInfo.setLongNumber(parent.getLongNumber() + "!" + schTemplateTaskInfo.getNumber());
					}
				} else {
					schTemplateTaskInfo.setLongNumber(schTemplateTaskInfo.getNumber());
				}
				schTemplateTaskInfo.setSeq(k + 1);
				coreBaseCollection.add(schTemplateTaskInfo);
			}
		}
	    if (editData.getTemplateType().equals(ScheduleTemplateTypeEnum.MainPlanTemplate))
			verifyIncludeGlobalTask(coreBaseCollection);
		refreshTable(coreBaseCollection);
		// RESchTemplateTaskFactory.getRemoteInstance().importTasks(coreBaseCollection, currentTemplateTaskCollection);
	}
	

	public void actionExportProject_actionPerformed(ActionEvent e) throws Exception {
		if (this.tblTask.getRowCount()==0 ) {
			FDCMsgBox.showWarning(this, "没有可供导出的任务");
			return;
		}
		KDFileChooser chooser = new KDFileChooser(new File(preProjectFileName));
		chooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".xml");
			}

			public String getDescription() {
				return "XML 格式(*.xml)";
			}
		});
		String defaultName = editData.getName();
		if (FDCHelper.isEmpty(defaultName)) {
			defaultName = editData.getName();
		}
		chooser.setSelectedFile(new File(preProjectFileName + defaultName + ".xml"));
		if (chooser.showSaveDialog(this) == KDFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			KDProjectWriter projectWriter = new KDProjectWriter(this.editData.getEntry(),null);
			projectWriter.write(file);
		}
	}
	
	public void actionImportTemplate_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext();
		uiContext.put("fromUI", this);
		uiContext.put("alreadySelect", getExistTask());
		UIFactory.createUIFactory().create(F7RESchTemplateUI.class.getName(), uiContext, null, OprtState.VIEW).show();
	}

	public void actionSetStdDuration_actionPerformed(ActionEvent e) throws Exception {
		if (editData.getId() == null) {
			FDCMsgBox.showError("请先保存任务，然后再进行标准工期设置!");
			abort();
		}
		String dlgState = OprtState.ADDNEW;
		if (editData.getState().equals(FDCBillStateEnum.AUDITTED)) {
			dlgState = OprtState.VIEW;
		}
		UIContext uiContext = new UIContext();
		uiContext.put("template", editData);
		uiContext.put("tasks", getGlobalTaskNode());
		UIFactory.createUIFactory().create(StandardDurationSetUpEditUI.class.getName(), uiContext, null, dlgState).show();
	}
	
	protected void showSubmitSuccess() {
		setMessageText(getClassAlise() + " " + EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Submit_OK"));
		setNextMessageText(getClassAlise() + " " + EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Edit"));
		setShowMessagePolicy(SHOW_MESSAGE_DEFAULT);
		setIsShowTextOnly(false);
		showMessage();
	}

	/** 主要是为了修改保存后的的提示 **/
	protected void showSaveSuccess() {
		setMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Save_OK"));
		setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Edit"));
		setShowMessagePolicy(0);
		setIsShowTextOnly(false);
		showMessage();
	}
	
	Map<String, RESchTemplateTaskInfo> getGlobalTaskNode() {
		RESchTemplateTaskCollection tasks = getExistTask();
		Map<String, RESchTemplateTaskInfo> taskMap = new HashMap<String, RESchTemplateTaskInfo>();
		
		RESchTemplateTaskInfo currTask = null;
		for (int i = 0; i < tasks.size(); i++) {
			currTask = tasks.get(i);
		/* TODO 这里是金地的东西，需要后续拿掉
			 * 	if (currTask.getSrcGroupNode() != null
						&& (currTask.getSrcGroupNode().equals(FDCScheduleConstant.GETLAND) || currTask.getSrcGroupNode().equals(FDCScheduleConstant.STARTBUILDING)
							|| currTask.getSrcGroupNode().equals(FDCScheduleConstant.STARTSALES)
							|| currTask.getSrcGroupNode().equals(
								FDCScheduleConstant.ENDBUILDING))) {
						taskMap.put(currTask.getSrcGroupNode(), currTask);
				} */
		}
       return taskMap;
	}

}