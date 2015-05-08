/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientCtrler;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.TaskTypeFactory;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class TaskTypeEditUI extends AbstractTaskTypeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(TaskTypeEditUI.class);
    
    private TaskTypeInfo parentInfo;
    /**
     * output class constructor
     */
    public TaskTypeEditUI() throws Exception
    {
        super();
    }

    protected IObjectValue createNewData() {
    	TaskTypeInfo  task  = new TaskTypeInfo();
		task.setIsEnabled(true);
    	return task;
		 
		 
	}

    public void setOprtState(String oprtType) {
    	super.setOprtState(oprtType);
    	setTitle();
    }
    
	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, "任务专业属性");
	}
    
	protected ICoreBase getBizInterface() throws Exception {
		return TaskTypeFactory.getRemoteInstance();
	}
	public void onLoad() throws Exception {
		super.onLoad();
		txtName.setMaxLength(80);
		txtNumber.setMaxLength(80);
		txtDescription.setMaxLength(255);
		 
	}
//	新增操作
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		btnCancel.setVisible(false);
		btnCancelCancel.setVisible(false);
		getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_ADDNEW);
//		super.actionAddNew_actionPerformed(e);
		
		
	}
//	修改操作
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		for(int i=0;i<TaskTypeInfo.SYSPREDESIGN.length;i++){
			if(TaskTypeInfo.SYSPREDESIGN[i].equals(editData.getId().toString())){
				FDCMsgBox.showError("系统预设数据，不允许修改！");
				SysUtil.abort();
			}
		}
		if (FDCSCHClientHelper.isSystemDefaultData(editData)) {
			MsgBox.showError(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "operate_SysData"));
			return;
		}
		if(editData.isIsEnabled()){
			FDCMsgBox.showError("已启用工作任务禁止修改！");
			SysUtil.abort();
		}
		
		getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_MODIFY);
//		super.actionEdit_actionPerformed(e);
	}
//	删除操作
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if (FDCSCHClientHelper.isSystemDefaultData(editData)) {
			MsgBox.showError(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "operate_SysData"));
			return;
		}
		getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_DELETE);
//		super.actionRemove_actionPerformed(e);
	}
//	禁用操作
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
//		setIsEnable(false);
	}
//	启用操作
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
//		setIsEnable(true);
	}
	
	protected void setIsEnable(boolean flag) throws Exception {
		getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_MODIFY);
		if (!flag)
			if (FDCSCHClientHelper.isSystemDefaultData(editData)) {
				MsgBox.showError(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "operate_SysData"));
				return;
			}
		editData.setIsEnabled(flag);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("isEnabled"));
		String message = null;
		if (flag) {
			getBizInterface().updatePartial(editData, sic);
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK");
		} else {
			getBizInterface().updatePartial(editData, sic);
			message = EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK");
		}
		setMessageText(message);
		showMessage();

		setDataObject(getValue(new ObjectUuidPK(editData.getId())));
		loadFields();
		setSave(true);
		setSaved(true);

		this.btnCancelCancel.setVisible(!flag);
		this.btnCancelCancel.setEnabled(!flag);
		this.btnCancel.setVisible(flag);
		this.btnCancel.setEnabled(flag);
		// this.chkIsEnabled.setSelected(flag);

	}
	protected void initWorkButton() {
		super.initWorkButton();
		menuBar.setEnabled(false);
		menuBar.setVisible(false);
		actionCopy.setVisible(false);
		actionNext.setVisible(false);
		actionPre.setVisible(false);
		actionPrint.setVisible(false);
		actionPrintPreview.setVisible(false);
		actionFirst.setVisible(false);
		actionLast.setVisible(false);
		actionRemove.setVisible(false);
		actionSave.setVisible(false);
		actionCancel.setVisible(false);
		actionCancelCancel.setVisible(false);
	}
	
	public void loadFields() {
		super.loadFields();
		parentInfo = (TaskTypeInfo) getUIContext().get(UIContext.PARENTNODE);
	}
	private FDCBaseDataClientCtrler ctrler=null;
	protected FDCBaseDataClientCtrler getCtrler(){
		if(ctrler==null){
			try {
				ctrler=new FDCBaseDataClientCtrler(this,getBizInterface());
//				ctrler.setControlType(getControlType());
			} catch (Exception e) {
				this.handUIExceptionAndAbort(e);
			}
		}
		return ctrler;
	}
	 
	public void storeFields() {
		super.storeFields();
	}
	private void checkData() throws Exception
	{
		if (editData.getNumber() == null
				|| editData.getNumber().trim().length() < 1) {
			FDCMsgBox.showError("编码不能为空！");
			SysUtil.abort();
		}
		if (editData.getName() == null
				|| editData.getName().trim().length() < 1) {
			FDCMsgBox.showError("名称不能为空！");
			SysUtil.abort();
		}
		TaskTypeFactory.getRemoteInstance().checkNumberDup(editData);
		TaskTypeFactory.getRemoteInstance().checkNameDup(editData);
	}
	protected void verifyInput(ActionEvent e) throws Exception {
	 	super.verifyInput(e);
	 	checkData();
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("isEnabled");
		sic.add("CU.id");
		return sic;
	}
	
}