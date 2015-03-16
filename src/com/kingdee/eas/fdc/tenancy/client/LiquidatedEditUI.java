/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.math.BigDecimal;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.DateEnum;
import com.kingdee.eas.fdc.tenancy.ILiquidated;
import com.kingdee.eas.fdc.tenancy.LiquidatedFactory;
import com.kingdee.eas.fdc.tenancy.LiquidatedInfo;
import com.kingdee.eas.fdc.tenancy.MoneyEnum;
import com.kingdee.eas.fdc.tenancy.OccurreStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class LiquidatedEditUI extends AbstractLiquidatedEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(LiquidatedEditUI.class);
    private SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    /**
     * output class constructor
     */
    public LiquidatedEditUI() throws Exception
    {
        super();
    }

    protected IObjectValue createNewData() {
		LiquidatedInfo objectValue = new LiquidatedInfo();
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		objectValue.setSellProject(sellProject);
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
        objectValue.setRateDate(DateEnum.DAY);
        objectValue.setReliefDate(DateEnum.DAY);
        objectValue.setStandardDate(DateEnum.DAY);
        objectValue.setOccurred(OccurreStateEnum.PRIOR);
        objectValue.setBit(MoneyEnum.YUAN);
        return objectValue;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return LiquidatedFactory.getRemoteInstance();
	}
	public void onLoad() throws Exception {
		 
		this.txtStandard.setPrecision(0);
		this.txtStandard.setRemoveingZeroInDispaly(false);
		this.txtStandard.setRemoveingZeroInEdit(false);
		this.txtStandard.setNegatived(false);
		this.txtStandard.setHorizontalAlignment(JTextField.RIGHT);
		this.txtStandard.setSupportedEmpty(true);

		this.txtRelief.setPrecision(0);
		this.txtRelief.setRemoveingZeroInDispaly(false);
		this.txtRelief.setRemoveingZeroInEdit(false);
		this.txtRelief.setNegatived(false);
		this.txtRelief.setHorizontalAlignment(JTextField.RIGHT);
		this.txtRelief.setSupportedEmpty(true);
		
		this.txtRate.setRemoveingZeroInDispaly(false);
		this.txtRate.setRemoveingZeroInEdit(false);
		this.txtRate.setNegatived(false);
		this.txtRate.setHorizontalAlignment(2);
		this.txtRate.setHorizontalAlignment(JTextField.RIGHT);
		this.txtRate.setSupportedEmpty(true);
		
    	actionSave.setVisible(false);
    	actionCopy.setVisible(false);
    	actionFirst.setVisible(false);
    	actionLast.setVisible(false);
    	actionPre.setVisible(false);
    	actionNext.setVisible(false);
    	super.onLoad();
    	setBtnStatus();
    	FDCClientHelper.addSqlMenu(this, this.menuEdit);
		if (!saleOrg.isIsBizUnit())
		{
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionCancel.setVisible(false);
			this.actionCancelCancel.setVisible(false);
		}
    	txtName.setRequired(true);
    	txtNumber.setRequired(true);
    	txtSimpleName.setRequired(true);
    	pkEffectDate.setRequired(true);
    	txtRate.setRequired(true);
    	txtRelief.setRequired(true);
    	prmtMoneyDefine.setRequired(true);
    	txtStandard.setRequired(true);
    	txtName.setMaxLength(50);
    	txtNumber.setMaxLength(50);
    	txtSimpleName.setMaxLength(50);
    	txtDescription.setMaxLength(255);
    	
    	this.btnPrint.setVisible(false);
    	this.btnPrintPreview.setVisible(false);
    	
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.TENANCYSYS_VALUE));
		view.setFilter(filter);
		prmtMoneyDefine.setEntityViewInfo(view);
		
    }
	public void loadFields() {
		super.loadFields();
		SHEHelper.setNumberEnabled(editData,getOprtState(),txtNumber);
	}
	private void setBtnStatus() {
		if (STATUS_ADDNEW.equals(getOprtState())) {// 新增状态
			this.btnCancelCancel.setVisible(false);// 启用按钮不可见
			this.btnCancel.setVisible(false);// 禁用按钮不可见
		} else if (STATUS_EDIT.equals(getOprtState())) {// 修改状态
			if (this.editData.isIsEnabled()) {// 如果当前为启用状态
				this.btnCancel.setVisible(true);// 禁用按钮可用
				this.btnCancel.setEnabled(true);// 禁用按钮可用
				this.btnCancelCancel.setVisible(false);// 启用按钮不可见
			} else {// 如果当前为禁用状态
				this.btnCancelCancel.setVisible(true);// 启用按钮可见
				this.btnCancelCancel.setEnabled(true);// 启用按钮可用
				this.btnCancel.setVisible(false);// 禁用按钮不可见
			}
		} else if (STATUS_VIEW.equals(getOprtState())) {// 查看状态
			if (this.editData.isIsEnabled()) {// 如果当前为启用状态
				this.btnCancel.setVisible(true);// 禁用按钮可用
				this.btnCancel.setEnabled(true);// 禁用按钮可用
				this.btnCancelCancel.setVisible(false);// 启用按钮不可见
			} else {// 如果当前为禁用状态
				this.btnCancelCancel.setVisible(true);// 启用按钮可见
				this.btnCancelCancel.setEnabled(true);// 启用按钮可用
				this.btnCancel.setEnabled(false);// 禁用按钮不可见
			}
		}
	}
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData!=null&&this.editData.getId()!=null){
			ILiquidated iLiquidated = LiquidatedFactory.getRemoteInstance();
			FDCDataBaseInfo model = LiquidatedFactory.getRemoteInstance().getFDCDataBaseInfo(new ObjectUuidPK(editData.getId()));
			if(iLiquidated.disEnabled(new ObjectUuidPK(editData.getId()),model)){
				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "DisEnabled_OK"));
				setDataObject(getValue(new ObjectUuidPK(editData.getId())));
		        loadFields();
				setSave(true);
		        setSaved(true);
				this.btnCancelCancel.setVisible(true);
				this.btnCancelCancel.setEnabled(true);
				this.btnCancel.setVisible(false);
				//this.chkIsEnabled.setSelected(false);
			}
		}
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData!=null&&this.editData.getId()!=null){
			ILiquidated iLiquidated = LiquidatedFactory.getRemoteInstance();
			FDCDataBaseInfo model = LiquidatedFactory.getRemoteInstance().getFDCDataBaseInfo(new ObjectUuidPK(editData.getId()));
			if(iLiquidated.enabled(new ObjectUuidPK(editData.getId()),model)){
				this.showResultMessage(EASResource.getString(FDCBaseDataClientUtils.FDCBASEDATA_RESOURCE, "Enabled_OK"));
				setDataObject(getValue(new ObjectUuidPK(editData.getId())));
		        loadFields();
				setSave(true);
		        setSaved(true);
				this.btnCancel.setVisible(true);
				this.btnCancel.setEnabled(true);
				this.btnCancelCancel.setVisible(false);
			}
		}
	}
	protected void showResultMessage(String message) {
		setMessageText(message);
		showMessage();
	}

	protected void txtRate_dataChanged(DataChangeEvent e) throws Exception {
		if(e.getNewValue()!=null&&((BigDecimal)e.getNewValue()).compareTo(new BigDecimal(100))==1){
			txtRate.setValue(e.getOldValue());
		}
		super.txtRate_dataChanged(e);
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		
		if(prmtMoneyDefine.getValue()==null){
			MsgBox.showInfo("款项名称必须录入！");
			abort();
		}
		
		if (this.txtNumber.isEditable()) {
			if (StringUtils.isEmpty(this.txtNumber.getText())) {
				MsgBox.showInfo("方案编码必须录入！");
				abort();
			}
		}
		if (StringUtils.isEmpty(this.txtName.getText())) {
			MsgBox.showInfo("方案名称必须录入！");
			abort();
		}
		if (StringUtils.isEmpty(this.txtSimpleName.getText())) {
			MsgBox.showInfo("方案简称必须录入！");
			abort();
		}
		if(pkEffectDate.getValue()==null){
			MsgBox.showInfo("生效日期必须录入！");
			abort();
		}
		if(StringUtils.isEmpty(this.txtRate.getText())){
			MsgBox.showInfo("违约金比例必须录入！");
			abort();
		}
		if(this.txtRate.getBigDecimalValue().compareTo(FDCHelper.ZERO) < 0||this.txtRate.getBigDecimalValue().compareTo(FDCHelper.ZERO) == 0){
			MsgBox.showInfo("违约金比例必须大于0！");
			abort();
		}
//		if(StringUtils.isEmpty(this.txtRelief.getText())){
//			MsgBox.showInfo("违约金减免必须录入！");
//			abort();
//		}
//		if(this.txtRelief.getIntegerValue()<=0){
//			MsgBox.showInfo("违约金减免必须大于0！");
//			abort();
//		}
//		if(StringUtils.isEmpty(this.txtStandard.getText())){
//			MsgBox.showInfo("违约金计算标准必须录入！");
//			abort();
//		}
//		if(this.txtStandard.getIntegerValue()<=0){
//			MsgBox.showInfo("违约金计算标准必须必须大于0！");
//			abort();
//		}
		if(this.txtDescription.getText().length()>255) {
			MsgBox.showInfo("备注长度不能超过255!");
			abort();
		}
	}
	public void actionSubmit_actionPerformed(ActionEvent arg0) throws Exception {
		super.actionSubmit_actionPerformed(arg0);
		this.storeFields();
		this.initOldData(this.editData);
	}
}