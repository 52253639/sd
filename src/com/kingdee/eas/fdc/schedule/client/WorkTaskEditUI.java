/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.IFormatter;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientCtrler;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.WorkTaskFactory;
import com.kingdee.eas.fdc.schedule.WorkTaskInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class WorkTaskEditUI extends AbstractWorkTaskEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(WorkTaskEditUI.class);

	/**
	 * output class constructor
	 */
	public WorkTaskEditUI() throws Exception {
		super();
	}

    public void setOprtState(String oprtType) {
    	super.setOprtState(oprtType);
    	setTitle();
    }
    
	private void setTitle() {
		FDCBaseDataClientUtils.setupUITitle(this, "工作任务");
	}
	
	protected IObjectValue createNewData() {
		WorkTaskInfo info = new WorkTaskInfo();
		if (getUIContext().get(UIContext.PARENTNODE) != null) {
			WorkTaskInfo parentInfo = (WorkTaskInfo) getUIContext().get(
					UIContext.PARENTNODE);
			info.setLevel(parentInfo.getLevel() + 1);
		}
		else
			info.setLevel(1);
		info.setIsEnabled(true);	 
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return WorkTaskFactory.getRemoteInstance();
	}
	protected void verifyInput(ActionEvent e) throws Exception {
		Number estimateDays = txtEstimateDays.getNumberValue();
		if(estimateDays == null || estimateDays.intValue()<1){
			FDCMsgBox.showWarning("任务的预估工期必须大于等于1");
			SysUtil.abort();
		}
    }
	public void onLoad() throws Exception { 
		this.txtLevel.setEnabled(false);
		super.onLoad();
		this.actionAddNew.setEnabled(true);
		prmtParent.setDisplayFormatter(new IFormatter() {
			public void applyPattern(String pattern) {
			}

			public String valueToString(Object o) {
				if (o != null && o instanceof WorkTaskInfo) {
					return ((WorkTaskInfo) o).getLongNumber().replace('!', '.');
				}
				if (o != null && o instanceof String) {
					return o.toString().replace('!', '.');
				}
				return null;
			}
		});
		 
	 

		if (OprtState.VIEW.equals(getOprtState())) {
			if (editData.isIsEnabled()) {
				actionEdit.setEnabled(false);
				btnEdit.setEnabled(false);
			} else {
				btnEdit.setEnabled(true);
				actionEdit.setEnabled(true);
			}

			actionRemove.setEnabled(false);
			actionCancel.setEnabled(false);
			actionCancelCancel.setEnabled(false);
			

		} else if (OprtState.EDIT.equals(getOprtState())) {			 
			getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_MODIFY);
			if (FDCSCHClientHelper.isSystemDefaultData(editData)) {
				MsgBox.showError(EASResource.getString(
						FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE,
						"operate_SysData"));
				return;
			}
		}else if(OprtState.ADDNEW.equals(getOprtState())){
			txtEstimateDays.setValue(FDCHelper.ONE);
		}
		txtName.setMaxLength(80);
		txtNumber.setMaxLength(80);
		txtDescription.setMaxLength(255);
		txtLevel.setMaximumValue(FDCHelper.MAX_VALUE);
		txtLevel.setMinimumValue(new BigDecimal(0));		 
		txtEstimateDays.setMaximumValue(FDCHelper.MAX_VALUE);
		txtEstimateDays.setMinimumValue(FDCHelper.ONE);
		txtLevel.setPrecision(0);
		txtLevel.setHorizontalAlignment(KDFormattedTextField.RIGHT);
		txtLevel.setEnabled(false);		
		txtEstimateDays.setHorizontalAlignment(KDFormattedTextField.RIGHT);
		if (chkIsEstDaysLocked.isSelected()) {
			txtEstimateDays.setEnabled(false);
		} else {
			txtEstimateDays.setEnabled(true);
		}
		chkIsEstDaysLocked.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkIsEstDaysLocked.isSelected()) {
					txtEstimateDays.setEnabled(false);
				} else {
					txtEstimateDays.setEnabled(true);
				}
			}
		});
		btnRemove.setVisible(false);
		if (this.prmtParent.getData() != null) {
			String temp = this.prmtParent.getData().toString();
			temp = temp.replace('!', '.');
			this.prmtParent.setData(temp);
		}

	}

	protected void initWorkButton() {
		super.initWorkButton();
		actionCopy.setVisible(false);
		actionSave.setVisible(false);
		actionCancel.setVisible(false);
		actionCancelCancel.setVisible(false);
		actionFirst.setVisible(false);
		actionLast.setVisible(false);
		actionNext.setVisible(false);
		actionPre.setVisible(false);
		actionPrint.setVisible(false);
		actionPrintPreview.setVisible(false);
		
		
	}
 
	 
	// 新增操作
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		btnCancel.setVisible(false);
		btnCancelCancel.setVisible(false); 
		this.txtEstimateDays.setEnabled(true);		

		getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_ADDNEW);
		super.actionAddNew_actionPerformed(e);
		this.txtLevel.setEnabled(false);	
	}

	// 修改操作
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		this.txtLevel.setEnabled(false);
		if (FDCSCHClientHelper.isSystemDefaultData(editData)) {
			MsgBox.showError(EASResource.getString(
					FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE,
					"operate_SysData"));
			return;
		}
		if (editData.isIsEnabled()) {
			FDCMsgBox.showError("已启用工作任务禁止修改！");
			SysUtil.abort();
		}
		
		getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_MODIFY);
		super.actionEdit_actionPerformed(e);
	}

	// 删除操作
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if (FDCSCHClientHelper.isSystemDefaultData(editData)) {
			MsgBox.showError(EASResource.getString(
					FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE,
					"operate_SysData"));
			return;
		}
		getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_DELETE);
		super.actionRemove_actionPerformed(e);
	}

	// 禁用操作
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		if (editData.getId() != null) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems()
					.add(
							new FilterItemInfo("parent.id", editData.getId()
									.toString()));
			filter.getFilterItems().add(
					new FilterItemInfo("isEnabled", Boolean.TRUE));
			if (WorkTaskFactory.getRemoteInstance().exists(filter)) {
				FDCMsgBox.showError("有下级未禁用，请先禁用下级！");
				SysUtil.abort();
			}
		}
		setIsEnable(false);
	}

	// 启用操作
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception {
		if (editData.getParent().isIsEnabled()) {
			setIsEnable(true);
		} else {
			FDCMsgBox.showError("上级未启用，请先启用上级！");
			SysUtil.abort();
		}
	}

	protected void setIsEnable(boolean flag) throws Exception {
		getCtrler().checkPermission(FDCBaseDataClientCtrler.ACTION_MODIFY);
		if (!flag)
			if (FDCSCHClientHelper.isSystemDefaultData(editData)) {
				MsgBox.showError(EASResource.getString(
						FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE,
						"operate_SysData"));
				return;
			}
		editData.setIsEnabled(flag);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("isEnabled"));
		String message = null;
		if (flag) {
			getBizInterface().updatePartial(editData, sic);
			message = EASResource.getString(
					FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK");
		} else {
			getBizInterface().updatePartial(editData, sic);
			message = EASResource.getString(
					FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE,
					"DisEnabled_OK");
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

	private FDCBaseDataClientCtrler ctrler = null;

	protected FDCBaseDataClientCtrler getCtrler() {
		if(ctrler==null){
			try {
				ctrler=new FDCBaseDataClientCtrler(this,getBizInterface());
			} catch (Exception e) {
				this.handUIExceptionAndAbort(e);
			}
		}
		return ctrler;
	}

	protected void doBeforeSubmit(ActionEvent e) throws Exception {
		super.doBeforeSubmit(e);
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
		WorkTaskFactory.getRemoteInstance().checkNameDup(editData);
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("isEnabled ");
		return sic;
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		if (editData.isIsEnabled()) {
			actionEdit.setEnabled(false);
			btnEdit.setEnabled(false);
		} else {
			btnEdit.setEnabled(true);
			actionEdit.setEnabled(true);
		}
	}
}