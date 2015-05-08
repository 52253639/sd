package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDRadioButton;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUtil;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.pm.ProjectFillBillEntryCollection;
import com.kingdee.eas.fdc.pm.ProjectFillBillEntryInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.TaskLoadEntryCollection;
import com.kingdee.eas.fdc.schedule.TaskLoadEntryInfo;
import com.kingdee.eas.fdc.schedule.TaskWorkloadLogInfo;
import com.kingdee.eas.fdc.schedule.WorkAmountEntry;
import com.kingdee.eas.fdc.schedule.WorkAmountEntryCollection;
import com.kingdee.eas.fdc.schedule.WorkAmountEntryInfo;
import com.kingdee.eas.util.SysUtil;

public class WorkloadLogPanelHelper {
	private final TaskExtProPanelHelper taskExtPropHelper;
	private KDTable table;
	private KDFormattedTextField txtWorkLoad;
	private KDFormattedTextField txtCompletePercent;
	private TaskWorkloadLogInfo workLoadInfo;
	private KDWorkButton btnAuditResult;
	/**
	 * 完工工程量的确认是否严格控制
	 */
	private boolean isFillBillControlStrict;
	private boolean isBaseOnTask;
	public WorkloadLogPanelHelper(TaskExtProPanelHelper helper) {
		this.taskExtPropHelper = helper;
		this.table = helper.getFDCSCHTaskPropUI().tblWorkload;
		txtWorkLoad = helper.getFDCSCHTaskPropUI().txtPlanWorkload;
		txtCompletePercent = helper.getFDCSCHTaskPropUI().txtCompletePercent;
		btnAuditResult = helper.getFDCSCHTaskPropUI().btnWorkLoadView;
	}
	public void load() throws EASBizException, BOSException{
		Map paramItem = null;
		if(paramItem==null){
			paramItem = FDCUtils.getDefaultFDCParam(null,SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
		}
		if(paramItem.get(FDCConstants.FDCSCH_PARAM_ISFILLBILLCONTROLSTRICT) !=null){
			isFillBillControlStrict = (Boolean.valueOf(paramItem.get(FDCConstants.FDCSCH_PARAM_ISFILLBILLCONTROLSTRICT).toString()).booleanValue());
		}
		if(paramItem.get(FDCConstants.FDCSCH_PARAM_BASEONTASK) != null){
			isBaseOnTask = Boolean.valueOf(paramItem.get(FDCConstants.FDCSCH_PARAM_BASEONTASK).toString()).booleanValue();
		}
		table.checkParsed();
		txtWorkLoad.setHorizontalAlignment(KDFormattedTextField.RIGHT);
		workLoadInfo = (TaskWorkloadLogInfo) taskExtPropHelper.getExtProperties().get("workloadLog");
		if(taskExtPropHelper.getExtProperties().get("workloadLog") == null){
			workLoadInfo = new TaskWorkloadLogInfo();
		}
		txtWorkLoad.setMaximumValue(FDCHelper.MAX_VALUE);
		txtWorkLoad.setMinimumValue(FDCHelper.MIN_VALUE);
		txtWorkLoad.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtWorkLoad.setValue(workLoadInfo.getPlanWorkload());
		
		FDCClientHelper.formatKDFormattedTextField(new KDFormattedTextField[]{txtCompletePercent,txtWorkLoad});
		if(!isFillBillControlStrict && !isBaseOnTask){
			TaskLoadEntryCollection taskLoadCol = (TaskLoadEntryCollection) taskExtPropHelper.getExtProperties().get("taskLoadEntrys");
			initTab2(taskLoadCol);
			if(table.getRowCount()>0){
				BigDecimal sum = FDCHelper.ZERO;
				for(int i=0;i<table.getRowCount();i++){
					if("已确认".equals(table.getCell(i, "state").getValue().toString())){
						sum = FDCHelper.add(sum, FDCHelper.toBigDecimal(table.getCell(i, "completePercent").getValue()));
					}
				}
				if(sum==null){
					sum=FDCHelper.ZERO;
				}
				txtCompletePercent.setValue(sum);
			}
		}else if(isBaseOnTask){
			this.btnAuditResult.setVisible(false);
			WorkAmountEntryCollection workAmountCols = (WorkAmountEntryCollection) taskExtPropHelper.getExtProperties().get("workamountEntrys");
//			initTable();
			initTabByTask(workAmountCols);
			if(table.getRowCount()>0){
				BigDecimal sum = FDCHelper.ZERO;
				for(int i=0;i<table.getRowCount();i++){
					if("已审批".equals(table.getCell(i, "state").getValue().toString())){
						sum = FDCHelper.add(sum, FDCHelper.toBigDecimal(table.getCell(i, "completePercent").getValue()));
					}
				}
				if(sum==null){
					sum=FDCHelper.ZERO;
				}
				txtCompletePercent.setValue(sum);
			}
		}else{
			ProjectFillBillEntryCollection workloadLogEntrys = 
				(ProjectFillBillEntryCollection) taskExtPropHelper.getExtProperties().get("workloadLogEntrys");
			initTab1(workloadLogEntrys);
			if(table.getRowCount()>0){
				BigDecimal sum = FDCHelper.ZERO;
				for(int i=0;i<table.getRowCount();i++){
					sum = FDCHelper.add(sum, FDCHelper.toBigDecimal(table.getCell(i, "completePercent").getValue()));
				}
				if(sum==null){
					sum=FDCHelper.ZERO;
				}
				txtCompletePercent.setValue(sum);
			}
		}
		if(!taskExtPropHelper.getTaskInfo().isIsLeaf()){
			txtCompletePercent.setValue(taskExtPropHelper.getTaskInfo().getComplete());
		}
		btnAuditResult.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int actRowIdx = table.getSelectManager().getActiveRowIndex();
				checkSelected(actRowIdx);
				String itemID;
				if(table.getCell(actRowIdx, "id").getValue() != null){
					itemID = table.getCell(actRowIdx, "id").getValue().toString();
				}else return;
				MultiApproveUtil.showApproveHis(BOSUuid.read(itemID),
						com.kingdee.eas.base.uiframe.client.UIModelDialogFactory.class.getName(),
						taskExtPropHelper.getFDCSCHTaskPropUI());
			}
		});
	}
	
	public Map store(){
		Map map = new HashMap();
		workLoadInfo.setCompletePercent(txtCompletePercent.getBigDecimalValue());
		workLoadInfo.setPlanWorkload(txtWorkLoad.getBigDecimalValue());
		map.put("workloadLog", workLoadInfo);
		return map;
	}
	
	private void verify(){
		
	}
	private void initTab1(ProjectFillBillEntryCollection entrys){
		table.getColumn("completePercent").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
//		table.getColumn("completePercent").getStyleAttributes().setNumberFormat("0.00%");
		if(entrys == null) return;
		for(int i=0;i<entrys.size();i++){
			ProjectFillBillEntryInfo entry = entrys.get(i);
			IRow row = table.addRow();
			row.getCell("id").setValue(entry.getId());
			row.getCell("state").setValue(entry.getBill().getState());
			row.getCell("createTime").setValue(entry.getBill().getCreateTime());
			if(entry.getBill().getCreator() != null){
				row.getCell("creator").setValue(entry.getBill().getCreator().getName());
			}
			row.getCell("auditTime").setValue(entry.getBill().getAuditTime());
			if(entry.getBill().getAuditor() != null){
				row.getCell("auditor").setValue(entry.getBill().getAuditor().getName());
			}
			if(entry.getDescription() != null){
				row.getCell("creatorMemo").setValue(entry.getDescription());
			}
			row.getCell("completeAmt").setValue(entry.getQty());
			row.getCell("completePercent").setValue(entry.getPercent());
			row.getCell("completeDate").setValue(entry.getCompleteDate());
		}
		table.setEnabled(false);
	}
    private void initTabByTask(WorkAmountEntryCollection entrys){
    	table.getColumn("completePercent").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	if(entrys == null) return;
    	WorkAmountEntryInfo entry  = null;
    	for(int i=0;i<entrys.size();i++){
    		entry = entrys.get(i);
    		IRow row = table.addRow();
			row.getCell("state").setValue(entry.getParent().getState());
			row.getCell("createTime").setValue(entry.getParent().getBizDate());
			if(entry.getParent().getCreator() != null){
				row.getCell("creator").setValue(entry.getParent().getCreator().getName());
			}
			row.getCell("auditTime").setValue(entry.getParent().getAuditTime());
			if(entry.getParent().getAuditor() != null){
				row.getCell("auditor").setValue(entry.getParent().getAuditor().getName());
			}
			row.getCell("completeAmt").setValue(entry.getConfirmAmount());
			row.getCell("completePercent").setValue(entry.getConfirmPercent());
			if(entry.getCompleteDate() != null)
			row.getCell("completeDate").setValue(entry.getCompleteDate());
			if(entry.getRemark()!=null)
			row.getCell("creatorMemo").setValue(entry.getRemark());
    	}
    }
	
	
	private void initTab2(TaskLoadEntryCollection entrys){
		table.getColumn("completePercent").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		if(entrys == null) return;
		for(int i=0;i<entrys.size();i++){
			TaskLoadEntryInfo entry = entrys.get(i);
			IRow row = table.addRow();
			row.getCell("id").setValue(entry.getId());
			if(entry.isIsConfirm()){
				row.getCell("state").setValue("已确认");
			}else{
				row.getCell("state").setValue("未确认");
			}
			row.getCell("createTime").setValue(entry.getCreateDate());
			if(entry.getCreator() != null){
				row.getCell("creator").setValue(entry.getCreator().getName());
			}
			row.getCell("auditTime").setValue(entry.getConfirmDate());
			if(entry.getConfirmer() != null){
				row.getCell("auditor").setValue(entry.getConfirmer().getName());
			}
			if(entry.getDesc() != null){
				row.getCell("creatorMemo").setValue(entry.getDesc());
			}
			row.getCell("completeAmt").setValue(entry.getLoad());
			row.getCell("completePercent").setValue(entry.getPercent());
			row.getCell("completeDate").setValue(entry.getCompleteDate());
		}
		table.setEnabled(false);
	}
	
	public void setEditStatus() {
		txtCompletePercent.setEnabled(false);
		txtWorkLoad.setEnabled(true);
		table.getStyleAttributes().setLocked(true);
	}
	public void setViewStatus() {
		txtCompletePercent.setEnabled(false);
		txtWorkLoad.setEnabled(false);
		table.getStyleAttributes().setLocked(true);
	}
	public void setExecutingStatus() {
		if(table.getRowCount()<1){
			FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) taskExtPropHelper.getFDCSCHTaskPropUI().getSelectedTask().getScheduleTaskInfo();
			UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
			if(task.getAdminPerson()!=null&&currentUserInfo!=null&&task.getAdminPerson().getId().equals(currentUserInfo.getPerson().getId())){
				txtCompletePercent.setEnabled(true);
			}
		}else{
			txtCompletePercent.setEnabled(false);
		}
		txtWorkLoad.setEnabled(false);
		table.getStyleAttributes().setLocked(true);
	}
	private void checkSelected(int actRowIdx) {
		if(actRowIdx < 0){
			FDCMsgBox.showError("请先选中行！");
			SysUtil.abort();
		}
	}
}
