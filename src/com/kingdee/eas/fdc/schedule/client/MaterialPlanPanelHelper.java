package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.monitor.client.BasicWorkFlowMonitorPanel;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUtil;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.TaskMaterialItemEntryCollection;
import com.kingdee.eas.fdc.schedule.TaskMaterialItemEntryFactory;
import com.kingdee.eas.fdc.schedule.TaskMaterialItemEntryInfo;
import com.kingdee.eas.fdc.schedule.TaskMaterialPlanCollection;
import com.kingdee.eas.fdc.schedule.TaskMaterialPlanFactory;
import com.kingdee.eas.fdc.schedule.TaskMaterialPlanInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

public class MaterialPlanPanelHelper {
	private final TaskExtProPanelHelper taskExtPropHelper; 
	private TaskMaterialPlanCollection materialPlanCol;
	private KDTable tblMaterialPlan;
	private KDTable tblMaterialItem;
	private KDWorkButton btnPlanAddLine;
	private KDWorkButton btnPlanEditLine;
	private KDWorkButton btnPlanDelLine;
	private KDWorkButton btnItemAddLine;
	private KDWorkButton btnItemEditLine;
	private KDWorkButton btnItemDelLine;
	private KDWorkButton btnItemAudit;
	private KDWorkButton btnItemUnaudit;
	private KDWorkButton btnItemAuditResult;
	private KDWorkButton btnItemWFGraph;
	public MaterialPlanPanelHelper(TaskExtProPanelHelper helper) {
		this.taskExtPropHelper = helper;
		this.tblMaterialPlan = helper.getFDCSCHTaskPropUI().tableSafePlan;
		this.tblMaterialItem = helper.getFDCSCHTaskPropUI().tableSafe;
		this.btnPlanAddLine = helper.getFDCSCHTaskPropUI().btnSafePlanAdd;
		this.btnPlanEditLine = helper.getFDCSCHTaskPropUI().btnSafePlanEdit;
		this.btnPlanDelLine = helper.getFDCSCHTaskPropUI().btnSafePlanDel;
		this.btnItemAddLine = helper.getFDCSCHTaskPropUI().btnSafeAddline;
		this.btnItemEditLine = helper.getFDCSCHTaskPropUI().btnSafeEditLine;
		this.btnItemDelLine = helper.getFDCSCHTaskPropUI().btnSafeDeleteline;
		this.btnItemAudit = helper.getFDCSCHTaskPropUI().btnAudit;
		this.btnItemUnaudit = helper.getFDCSCHTaskPropUI().btnUnAudit;
		this.btnItemAuditResult = helper.getFDCSCHTaskPropUI().btnAuditResult;
		this.btnItemWFGraph = helper.getFDCSCHTaskPropUI().btnWorkFlow;
	}
	public void load(){
		tblMaterialPlan.checkParsed();
		tblMaterialItem.checkParsed();
		tblMaterialPlan.addKDTSelectListener(new KDTSelectListener(){
			public void tableSelectChanged(KDTSelectEvent e) {
				int actPlanRowIdx = tblMaterialPlan.getSelectManager().getActiveRowIndex();
				IRow row = null;
				if(actPlanRowIdx < 0) return;
				row = tblMaterialPlan.getRow(actPlanRowIdx);
				if(row.getUserObject() != null){
					TaskMaterialItemEntryCollection itemInfos = (TaskMaterialItemEntryCollection) row.getUserObject();
					fillItemTable(itemInfos);
				}
			}
		});
		materialPlanCol = 
			(TaskMaterialPlanCollection) taskExtPropHelper.getExtProperties().get("materialPlan");
		if( taskExtPropHelper.getExtProperties().get("materialPlan")== null){
			materialPlanCol = new TaskMaterialPlanCollection();
		}
		initTable();
		fillTable();
	}
	/**
	 * 用于添加物资计划之后的重新加载
	 * @param taskExtID
	 */
	public void load(String taskExtID){
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("taskExt.id",taskExtID));
		view.setFilter(filter);
		try {
			materialPlanCol = TaskMaterialPlanFactory.getRemoteInstance().getTaskMaterialPlanCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		fillTable();
	}
	
	
	private void fillTable(){
		tblMaterialPlan.removeRows();
		tblMaterialItem.removeRows();
		for(int i = 0;i<materialPlanCol.size();i++){
			TaskMaterialPlanInfo materialPlanInfo = materialPlanCol.get(i);
			IRow row = tblMaterialPlan.addRow();
			row.getCell("id").setValue(materialPlanInfo.getId());
			row.getCell("date").setValue(materialPlanInfo.getDate());
			row.getCell("description").setValue(materialPlanInfo.getName());
			TaskMaterialItemEntryCollection materialItemCol = materialPlanInfo.getItemEntrys();
			row.setUserObject(materialItemCol);
		}
	}
	
	private void initTable(){
		KDDatePicker date = new KDDatePicker();
		tblMaterialPlan.getColumn("date").setEditor(new KDTDefaultCellEditor(date));
		tblMaterialItem.getColumn("date").setEditor(new KDTDefaultCellEditor(date));
		btnPlanAddLine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				UIContext uiContext = new UIContext();
				uiContext.put(UIContext.OWNER, taskExtPropHelper.getFDCSCHTaskPropUI());
				uiContext.put("taskExt",taskExtPropHelper.getTaskExtInfo());
				uiContext.put("wbsID", taskExtPropHelper.getWBS().getId().toString());
				IUIWindow uiWindow;
				try {
					uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
							TaskMaterialPlanEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
					uiWindow.show();
					uiWindow.getUIObject();
				} catch (UIException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btnPlanEditLine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int actRowIdx = tblMaterialPlan.getSelectManager().getActiveRowIndex();
				checkSelected(actRowIdx);
				String planID;
				if(tblMaterialPlan.getCell(actRowIdx, "id").getValue() != null){
					planID = tblMaterialPlan.getCell(actRowIdx, "id").getValue().toString();
				}else return;
				try {
					checkIsRefered(planID);
				} catch (EASBizException e2) {
					e2.printStackTrace();
				} catch (BOSException e2) {
					e2.printStackTrace();
				}
				UIContext uiContext = new UIContext();
				uiContext.put(UIContext.OWNER, taskExtPropHelper.getFDCSCHTaskPropUI());
				uiContext.put("taskExt",taskExtPropHelper.getTaskExtInfo());
				uiContext.put("wbsID", taskExtPropHelper.getWBS().getId().toString());
				uiContext.put("id", planID);
				IUIWindow uiWindow;
				try {
					uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
							TaskMaterialPlanEditUI.class.getName(), uiContext, null, OprtState.EDIT);
					uiWindow.show();
				} catch (UIException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnPlanDelLine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int actRowIdx = tblMaterialPlan.getSelectManager().getActiveRowIndex();
				checkSelected(actRowIdx);
				String planID;
				if(tblMaterialPlan.getCell(actRowIdx, "id").getValue() != null){
					planID = tblMaterialPlan.getCell(actRowIdx, "id").getValue().toString();
				}else return;
				try {
					checkIsRefered(planID);
					TaskMaterialPlanFactory.getRemoteInstance().delete(new ObjectUuidPK(BOSUuid.read(planID)));
					reload();
				} catch (EASBizException e2) {
					e2.printStackTrace();
				} catch (BOSException e2) {
					e2.printStackTrace();
				}
			}
		});
		btnItemAddLine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				UIContext uiContext = new UIContext();
				uiContext.put(UIContext.OWNER, taskExtPropHelper.getFDCSCHTaskPropUI());
				int actPlanRowIdx = tblMaterialPlan.getSelectManager().getActiveRowIndex();
				if(actPlanRowIdx < 0){
					FDCMsgBox.showError("请先选中相应的物资计划！");
					SysUtil.abort();
				}
				String planID;
				if(tblMaterialPlan.getCell(actPlanRowIdx, "id").getValue() !=null){
					planID = tblMaterialPlan.getCell(actPlanRowIdx, "id").getValue().toString();
				}else return;
				uiContext.put("task",taskExtPropHelper.getTaskInfo());
				uiContext.put("materialPlanID", planID);
				IUIWindow uiWindow;
				try {
					uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
							TaskMaterialItemEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
					uiWindow.show();
				} catch (UIException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnItemEditLine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int actItemRowIdx = tblMaterialItem.getSelectManager().getActiveRowIndex();
				int actPlanRowIdx = tblMaterialPlan.getSelectManager().getActiveRowIndex();
				checkSelected(actItemRowIdx);
				if(FDCBillStateEnum.AUDITTED.equals(tblMaterialItem.getCell(actItemRowIdx, "status").getValue())){
					FDCMsgBox.showError("该记录已审批，不能修改！");
					SysUtil.abort();
				}
					
				String itemID;
				String planID;
				if(actPlanRowIdx < 0 && tblMaterialPlan.getRowCount() >0)
					actPlanRowIdx = 0;
				if(tblMaterialPlan.getCell(actPlanRowIdx, "id").getValue() !=null){
					planID = tblMaterialPlan.getCell(actPlanRowIdx, "id").getValue().toString();
				}else return;
				if(tblMaterialItem.getCell(actItemRowIdx, "id").getValue() != null){
					itemID = tblMaterialItem.getCell(actItemRowIdx, "id").getValue().toString();
				}else return;
				
				UIContext uiContext = new UIContext();
				uiContext.put(UIContext.OWNER, taskExtPropHelper.getFDCSCHTaskPropUI());
				uiContext.put("id", itemID);
				uiContext.put("materialPlanID", planID);
				IUIWindow uiWindow;
				try {
					uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
							TaskMaterialItemEditUI.class.getName(), uiContext, null, OprtState.EDIT);
					uiWindow.show();
				} catch (UIException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnItemDelLine.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int actRowIdx = tblMaterialItem.getSelectManager().getActiveRowIndex();
				int actPlanRowIdx = tblMaterialPlan.getSelectManager().getActiveRowIndex();
				checkSelected(actRowIdx);
				if(FDCBillStateEnum.AUDITTED.equals(tblMaterialItem.getCell(actRowIdx, "status").getValue())){
					FDCMsgBox.showError("该记录已审批，不能修改！");
					SysUtil.abort();
				}
				String itemID;
				String planID;
				if(tblMaterialPlan.getCell(actPlanRowIdx, "id").getValue() != null){
					planID = tblMaterialPlan.getCell(actPlanRowIdx, "id").getValue().toString();
				}else return;
				if(tblMaterialItem.getCell(actRowIdx, "id").getValue() != null){
					itemID = tblMaterialItem.getCell(actRowIdx, "id").getValue().toString();
				}else return;
				try {
					TaskMaterialItemEntryFactory.getRemoteInstance().delete(new ObjectUuidPK(BOSUuid.read(itemID)));
					reloadItem(planID);
				} catch (EASBizException e2) {
					e2.printStackTrace();
				} catch (BOSException e2) {
					e2.printStackTrace();
				}
			}
		});
		btnItemAudit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int actRowIdx = tblMaterialItem.getSelectManager().getActiveRowIndex();
				int actPlanRowIdx = tblMaterialPlan.getSelectManager().getActiveRowIndex();
				checkSelected(actRowIdx);
				String itemID;
				String planID;
				if(tblMaterialPlan.getCell(actPlanRowIdx, "id").getValue() != null){
					planID = tblMaterialPlan.getCell(actPlanRowIdx, "id").getValue().toString();
				}else return;
				if(tblMaterialItem.getCell(actRowIdx, "id").getValue() != null){
					itemID = tblMaterialItem.getCell(actRowIdx, "id").getValue().toString();
				}else return;
				try {
					TaskMaterialItemEntryFactory.getRemoteInstance().audit(new ObjectUuidPK(BOSUuid.read(itemID)));
					reloadItem(planID);
				} catch (Exception e1) {
					e1.printStackTrace();
				} 
			}
		});
		btnItemUnaudit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int actRowIdx = tblMaterialItem.getSelectManager().getActiveRowIndex();
				int actPlanRowIdx = tblMaterialPlan.getSelectManager().getActiveRowIndex();
				checkSelected(actRowIdx);
				String itemID;
				String planID;
				if(tblMaterialPlan.getCell(actPlanRowIdx, "id").getValue() != null){
					planID = tblMaterialPlan.getCell(actPlanRowIdx, "id").getValue().toString();
				}else return;
				if(tblMaterialItem.getCell(actRowIdx, "id").getValue() != null){
					itemID = tblMaterialItem.getCell(actRowIdx, "id").getValue().toString();
				}else return;
				try {
					TaskMaterialItemEntryFactory.getRemoteInstance().unAudit(new ObjectUuidPK(BOSUuid.read(itemID)));
					reloadItem(planID);
				} catch (Exception e1) {
					e1.printStackTrace();
				} 
			}
		});
		btnItemAuditResult.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int actRowIdx = tblMaterialItem.getSelectManager().getActiveRowIndex();
				checkSelected(actRowIdx);
				String itemID;
				if(tblMaterialItem.getCell(actRowIdx, "id").getValue() != null){
					itemID = tblMaterialItem.getCell(actRowIdx, "id").getValue().toString();
				}else return;
				MultiApproveUtil.showApproveHis(BOSUuid.read(itemID),
						com.kingdee.eas.base.uiframe.client.UIModelDialogFactory.class.getName(),
						taskExtPropHelper.getFDCSCHTaskPropUI());
			}
		});
		btnItemWFGraph.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int actRowIdx = tblMaterialItem.getSelectManager().getActiveRowIndex();
				checkSelected(actRowIdx);
				String itemID;
				if(tblMaterialItem.getCell(actRowIdx, "id").getValue() != null){
					itemID = tblMaterialItem.getCell(actRowIdx, "id").getValue().toString();
				}else return;
				IEnactmentService service;
				try {
					service = EnactmentServiceFactory.createRemoteEnactService();
					ProcessInstInfo instInfo = null;
					ProcessInstInfo[] procInsts = service.getProcessInstanceByHoldedObjectId(itemID);
					for (int i = 0, n = procInsts.length; i < n; i++) {
						// modify by gongyin,流程挂起时也显示流程图
						if ("open.running".equals(procInsts[i].getState())
								|| "open.not_running.suspended".equals(procInsts[i].getState())) {
							instInfo = procInsts[i];
						}
					}
					if (instInfo == null) {
						FDCMsgBox.showInfo(EASResource.getString(FrameWorkClientUtils.strResource + "Msg_WFHasNotInstance"));
						// MessageBox("没有正在运行的对应流程实例");
					} else {
						// 显示UI
						UIContext uiContext = new UIContext(this);
						uiContext.put("id", instInfo.getProcInstId());
						String className = BasicWorkFlowMonitorPanel.class.getName();
						IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(className, uiContext);
						uiWindow.show();
					}
				} catch (BOSException e1) {
					e1.printStackTrace();
				}
			}
			});
	}
	private void checkSelected(int actRowIdx) {
		if(actRowIdx < 0){
			FDCMsgBox.showError("请先选中行！");
			SysUtil.abort();
		}
	}
	private void checkIsRefered(String planID) throws BOSException,
			EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id",planID,CompareType.EQUALS));
		if(TaskMaterialItemEntryFactory.getRemoteInstance().exists(filter)){
			FDCMsgBox.showError("该记录已被引用，不能修改或删除！");
			SysUtil.abort();
		}
	}
	
	
	public void setEditStatus() {
		btnPlanAddLine.setEnabled(true);
		btnPlanEditLine.setEnabled(true);
		btnPlanDelLine.setEnabled(true);
		btnItemAddLine.setEnabled(false);
		btnItemEditLine.setEnabled(false);
		btnItemDelLine.setEnabled(false);
		btnItemAudit.setEnabled(false);
		btnItemAuditResult.setEnabled(false);
		btnItemUnaudit.setEnabled(false);
		btnItemWFGraph.setEnabled(false);
		tblMaterialItem.getStyleAttributes().setLocked(true);
		tblMaterialPlan.getStyleAttributes().setLocked(true);
	}
	public void setViewStatus() {
		btnPlanAddLine.setEnabled(false);
		btnPlanEditLine.setEnabled(false);
		btnPlanDelLine.setEnabled(false);
		btnItemAddLine.setEnabled(false);
		btnItemEditLine.setEnabled(false);
		btnItemDelLine.setEnabled(false);
		btnItemAudit.setEnabled(false);
		btnItemAuditResult.setEnabled(false);
		btnItemUnaudit.setEnabled(false);
		btnItemWFGraph.setEnabled(false);
		tblMaterialItem.getStyleAttributes().setLocked(true);
		tblMaterialPlan.getStyleAttributes().setLocked(true);
	}
	public void setExecutingStatus(){
		btnPlanAddLine.setEnabled(false);
		btnPlanEditLine.setEnabled(false);
		btnPlanDelLine.setEnabled(false);
		btnItemAddLine.setEnabled(true);
		btnItemEditLine.setEnabled(true);
		btnItemDelLine.setEnabled(true);
		btnItemAudit.setEnabled(true);
		btnItemAuditResult.setEnabled(true);
		btnItemUnaudit.setEnabled(true);
		btnItemWFGraph.setEnabled(true);
		tblMaterialItem.getStyleAttributes().setLocked(true);
		tblMaterialPlan.getStyleAttributes().setLocked(true);
	}
	public void reload() throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("taskExt.wbs.id",taskExtPropHelper.getWBS().getId().toString()));
		view.setFilter(filter);
		materialPlanCol = TaskMaterialPlanFactory.getRemoteInstance().getTaskMaterialPlanCollection(view);
		fillTable();
	}
	public void reloadItem(String planId) throws BOSException{
		if(planId == null) return;
		EntityViewInfo view = new EntityViewInfo();
		TaskMaterialItemEntryCollection materialItemCol = null;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id",planId));
		view.setFilter(filter);
		materialItemCol = TaskMaterialItemEntryFactory.getRemoteInstance().getTaskMaterialItemEntryCollection(view);
		if(materialItemCol == null) return;
		fillItemTable(materialItemCol);
	}
	private void fillItemTable(TaskMaterialItemEntryCollection itemInfos) {
		tblMaterialItem.removeRows();
		for(int i=0;i<itemInfos.size();i++){
			TaskMaterialItemEntryInfo info = itemInfos.get(i);
			IRow row2 = tblMaterialItem.addRow();
			row2.getCell("status").setValue(info.getState());
			row2.getCell("id").setValue(info.getId());
			row2.getCell("date").setValue(info.getHappenTime());
			row2.getCell("description").setValue(info.getTitle());
		}
	}
}
