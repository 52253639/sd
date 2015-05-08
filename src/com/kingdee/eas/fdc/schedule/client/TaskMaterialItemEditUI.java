/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.TaskMaterialItemEntryFactory;
import com.kingdee.eas.fdc.schedule.TaskMaterialItemEntryInfo;
import com.kingdee.eas.fdc.schedule.TaskMaterialPlan;
import com.kingdee.eas.fdc.schedule.TaskMaterialPlanInfo;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class TaskMaterialItemEditUI extends AbstractTaskMaterialItemEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(TaskMaterialItemEditUI.class);
   
    public TaskMaterialItemEditUI() throws Exception  {
        super();
    }
    public void storeFields() {
        super.storeFields();
    }
    public void actionSave_actionPerformed(ActionEvent e) throws Exception {
    	verify();
    	checkNameDup();
    	if(entityTaskMaterialItemEntry == null)
    		entityTaskMaterialItemEntry = new TaskMaterialItemEntryInfo();
    	entityTaskMaterialItemEntry.setTitle(txtTitle.getText());
    	entityTaskMaterialItemEntry.setHappenTime((Date)pkHappenTime.getValue());
    	entityTaskMaterialItemEntry.setDescription(txtDescription.getText());
    	entityTaskMaterialItemEntry.setAppraise(txtAppraise.getBigDecimalValue());
    	if(entityTaskMaterialItemEntry.getParent() == null){
    		TaskMaterialPlanInfo planParent = new TaskMaterialPlanInfo();
    		planParent.setId(BOSUuid.read(getUIContext().get("materialPlanID").toString()));
    		entityTaskMaterialItemEntry.setParent(planParent);
    	}
    	entityTaskMaterialItemEntry.setState(FDCBillStateEnum.SAVED);
    	IObjectPK pk = TaskMaterialItemEntryFactory.getRemoteInstance().save(entityTaskMaterialItemEntry);
    	if(entityTaskMaterialItemEntry.getId() == null){
    		entityTaskMaterialItemEntry.setId(BOSUuid.read(pk.toString()));
    	}
    	FDCMsgBox.showInfo("保存成功！");
    }
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	verify();
    	checkNameDup();
    	if(entityTaskMaterialItemEntry == null)
    		entityTaskMaterialItemEntry = new TaskMaterialItemEntryInfo();
    	entityTaskMaterialItemEntry.setTitle(txtTitle.getText());
    	entityTaskMaterialItemEntry.setHappenTime((Date)pkHappenTime.getValue());
    	entityTaskMaterialItemEntry.setDescription(txtDescription.getText());
    	entityTaskMaterialItemEntry.setAppraise(txtAppraise.getBigDecimalValue());
    	entityTaskMaterialItemEntry.setState(FDCBillStateEnum.SUBMITTED);
    	if(entityTaskMaterialItemEntry.getParent() == null){
    		TaskMaterialPlanInfo planParent = new TaskMaterialPlanInfo();
    		planParent.setId(BOSUuid.read(getUIContext().get("materialPlanID").toString()));
    		entityTaskMaterialItemEntry.setParent(planParent);
    	}
    	IObjectPK pk = TaskMaterialItemEntryFactory.getRemoteInstance().submit(entityTaskMaterialItemEntry);
    	if(entityTaskMaterialItemEntry.getId() == null){
    		entityTaskMaterialItemEntry.setId(BOSUuid.read(pk.toString()));
    	}
    	FDCMsgBox.showInfo("提交成功！");
    }
    public void onLoad() throws Exception {
    	super.onLoad();
    	txtTitle.setMaxLength(80);
    	txtDescription.setMaxLength(255);
    	txtAppraise.setMaximumValue(new BigDecimal("10"));
    	txtAppraise.setMinimumValue(new BigDecimal("0"));
    	txtAppraise.setHorizontalAlignment(KDFormattedTextField.RIGHT);
    	txtAppraise.setPrecision(4);
    	txtAppraise.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
    	if(getUIContext().get("id")!=null){
    		entityTaskMaterialItemEntry = TaskMaterialItemEntryFactory.getRemoteInstance().
    				getTaskMaterialItemEntryInfo("	where id='"+getUIContext().get("id")+"'");
    		txtTitle.setText(entityTaskMaterialItemEntry.getTitle());
    		txtDescription.setText(entityTaskMaterialItemEntry.getDescription());
    		txtAppraise.setValue(entityTaskMaterialItemEntry.getAppraise());
    		pkHappenTime.setValue(entityTaskMaterialItemEntry.getHappenTime());
    	}
    	else	
    		entityTaskMaterialItemEntry = new TaskMaterialItemEntryInfo();
    	if(entityTaskMaterialItemEntry.getState() != null
    			&& FDCBillStateEnum.SUBMITTED.equals(entityTaskMaterialItemEntry.getState())){
    		actionSave.setEnabled(false);
    		actionSubmit.setEnabled(true);
    	}else {
    		actionSave.setEnabled(true);
    		actionSubmit.setEnabled(true);
    	}
    }
    public void loadFields() {
    	super.loadFields();
    }
    private void verify(){
    	if(txtTitle.getText() == null||txtTitle.getText().trim().length()<1){
    		FDCMsgBox.showError("标题不能为空！");
    		SysUtil.abort();
    	}
    	if(pkHappenTime.getValue() == null){
    		FDCMsgBox.showError("发生时间不能为空！");
    		SysUtil.abort();
    	}
//    	if(txtAppraise.getValue() == null){
//    		FDCMsgBox.showError("评分不能为空！");
//    		SysUtil.abort();
//    	}
    }
    protected void disposeUIWindow() {
    	if(OprtState.VIEW.equals(getOprtState()));
    	boolean isModify = false;
		if (entityTaskMaterialItemEntry == null
				&& (!this.txtTitle.getText().equals("") || !this.txtDescription.getText().equals("") || this.pkHappenTime
						.getValue() != null)) {
			isModify = true;
		} else {
			if (!this.txtTitle.getText().equals(entityTaskMaterialItemEntry.getTitle() == null ? "" : entityTaskMaterialItemEntry.getTitle())) {
				isModify = true;
			}
			if (!this.txtDescription.getText().equals(
					entityTaskMaterialItemEntry.getDescription() == null ? "" : entityTaskMaterialItemEntry.getDescription())) {
				isModify = true;
			}
			if (entityTaskMaterialItemEntry.getAppraise() != null && 
					! entityTaskMaterialItemEntry.getAppraise().equals(txtAppraise.getBigDecimalValue())) {
				isModify = true;
			}
			if (entityTaskMaterialItemEntry.getHappenTime() != null && this.pkHappenTime.getValue() == null) {
				isModify = true;
			} else if (entityTaskMaterialItemEntry.getHappenTime() == null && this.pkHappenTime.getValue() != null) {
				isModify = true;
			} else if (entityTaskMaterialItemEntry.getHappenTime() != null && this.pkHappenTime.getValue() != null) {
				if (((Date) this.pkHappenTime.getValue()).getTime() != entityTaskMaterialItemEntry.getHappenTime().getTime()) {
					isModify = true;
				}
			}
		}
		if (isModify) {
			int rtn = FDCMsgBox.showConfirm3("数据有修改，是否保存？");
			if (rtn == FDCMsgBox.CANCEL) {
				SysUtil.abort();
			} else if (rtn == FDCMsgBox.YES) {
				if (this.actionSave.isEnabled()) {
					try {
						actionSave_actionPerformed(null);
					} catch (com.kingdee.eas.util.AbortException e) {
						SysUtil.abort();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					try {
						actionSubmit_actionPerformed(null);
					} catch (com.kingdee.eas.util.AbortException e) {
						SysUtil.abort();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		super.disposeUIWindow();
		if (entityTaskMaterialItemEntry != null && entityTaskMaterialItemEntry.getId() != null) {
			if (getUIContext().get(UIContext.OWNER) instanceof FDCScheduleTaskPropertiesUI) {
				FDCScheduleTaskPropertiesUI mainPanelHelper = (FDCScheduleTaskPropertiesUI) getUIContext().get(UIContext.OWNER);
				try {
					mainPanelHelper.loadMaterial();
				} catch (BOSException e) {
					this.handleException(e);
				}
			}
		}
    }
    
    private void checkNameDup() throws EASBizException, BOSException{
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("title",txtTitle.getText().trim()));
    	filter.getFilterItems().add(new FilterItemInfo("parent",getUIContext().get("materialPlanID").toString()));
    	if(entityTaskMaterialItemEntry.getId() != null){
    		filter.getFilterItems().add(new FilterItemInfo("id",entityTaskMaterialItemEntry.getId().toString(),CompareType.NOTEQUALS));
    	}
    	if(TaskMaterialItemEntryFactory.getRemoteInstance().exists(filter)){
    		FDCMsgBox.showError("标题不能重复！");
    		SysUtil.abort();
    	}
    }
}