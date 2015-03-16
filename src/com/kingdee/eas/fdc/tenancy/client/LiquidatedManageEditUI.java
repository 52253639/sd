/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.math.BigDecimal;

import javax.swing.Action;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCBaseDataClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.ILiquidated;
import com.kingdee.eas.fdc.tenancy.LiquidatedFactory;
import com.kingdee.eas.fdc.tenancy.LiquidatedInfo;
import com.kingdee.eas.fdc.tenancy.LiquidatedManageFactory;
import com.kingdee.eas.fdc.tenancy.LiquidatedManageInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class LiquidatedManageEditUI extends AbstractLiquidatedManageEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(LiquidatedManageEditUI.class);
    private SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    /**
     * output class constructor
     */
    public LiquidatedManageEditUI() throws Exception
    {
        super();
    }

    protected IObjectValue createNewData() {
		LiquidatedManageInfo objectValue = new LiquidatedManageInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        objectValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
        this.btnGenTenBillOtherPay.setEnabled(false);
        return objectValue;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return LiquidatedManageFactory.getRemoteInstance();
	}
	public void onLoad() throws Exception {
		
		this.txtAmount.setRemoveingZeroInDispaly(false);
		this.txtAmount.setRemoveingZeroInEdit(false);
		this.txtAmount.setNegatived(false);
		this.txtAmount.setHorizontalAlignment(2);
		this.txtAmount.setPrecision(2);
		this.txtAmount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtAmount.setSupportedEmpty(true);
		
		this.txtReliefAmount.setRemoveingZeroInDispaly(false);
		this.txtReliefAmount.setRemoveingZeroInEdit(false);
		this.txtReliefAmount.setNegatived(false);
		this.txtReliefAmount.setHorizontalAlignment(2);
		this.txtReliefAmount.setPrecision(2);
		this.txtReliefAmount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtReliefAmount.setSupportedEmpty(true);
		
		this.txtActAmount.setRemoveingZeroInDispaly(false);
		this.txtActAmount.setRemoveingZeroInEdit(false);
		this.txtActAmount.setHorizontalAlignment(2);
		this.txtActAmount.setPrecision(2);
		this.txtActAmount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtActAmount.setSupportedEmpty(true);
		
		this.actionSave.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
    	super.onLoad();
    	FDCClientHelper.addSqlMenu(this, this.menuEdit);
		
    	txtAmount.setRequired(true);
    	txtDescription.setMaxLength(255);
    	
    	this.actionGenTenBillOtherPay.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_post"));
    
    	if (STATUS_ADDNEW.equals(getOprtState())) {
    		this.btnEdit.setEnabled(false);
    		this.btnGenTenBillOtherPay.setEnabled(false);
    		this.btnRemove.setEnabled(false);
		} else if (STATUS_EDIT.equals(getOprtState())) {
			if (this.editData.isIsGenerate()) {
				this.btnEdit.setEnabled(false);
				this.btnGenTenBillOtherPay.setEnabled(false);
				this.btnRemove.setEnabled(false);
			} else {
				this.btnEdit.setEnabled(false);
				this.btnGenTenBillOtherPay.setEnabled(true);
				this.btnRemove.setEnabled(true);
			}
		} else if (STATUS_VIEW.equals(getOprtState())) {
			if (this.editData.isIsGenerate()) {
				this.btnEdit.setEnabled(false);
				this.btnGenTenBillOtherPay.setEnabled(false);
				this.btnRemove.setEnabled(false);
			} else {
				this.btnEdit.setEnabled(true);
				this.btnGenTenBillOtherPay.setEnabled(true);
				this.btnRemove.setEnabled(true);
			}
		}
    	
    	if (!saleOrg.isIsBizUnit())
		{
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionGenTenBillOtherPay.setEnabled(false);
		}
    	this.cbIsGenerate.setVisible(false);
    	this.btnPrint.setVisible(false);
    	this.btnPrintPreview.setVisible(false);
	}

	
	
	protected void showResultMessage(String message) {
		setMessageText(message);
		showMessage();
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if(StringUtils.isEmpty(this.txtAmount.getText())){
			MsgBox.showInfo("违约金金额必须录入！");
			abort();
		}
		if(this.txtAmount.getBigDecimalValue().compareTo(FDCHelper.ZERO) < 0||this.txtAmount.getBigDecimalValue().compareTo(FDCHelper.ZERO) == 0){
			MsgBox.showInfo("违约金金额必须大于0！");
			abort();
		}
		if(this.txtActAmount.getBigDecimalValue().compareTo(FDCHelper.ZERO) < 0||this.txtActAmount.getBigDecimalValue().compareTo(FDCHelper.ZERO) == 0){
			MsgBox.showInfo("剩余金额必须大于0！");
			abort();
		}
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		this.actionGenTenBillOtherPay.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_post"));
	}

	public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
		super.actionEdit_actionPerformed(arg0);
		this.btnGenTenBillOtherPay.setEnabled(false);
	}

	public void actionSubmit_actionPerformed(ActionEvent arg0) throws Exception {
		super.actionSubmit_actionPerformed(arg0);
		this.btnGenTenBillOtherPay.setEnabled(true);
		this.storeFields();
		this.initOldData(this.editData);
	}

	public void actionGenTenBillOtherPay_actionPerformed(ActionEvent e)
			throws Exception {
		if(editData.getActAmount().compareTo(this.txtActAmount.getBigDecimalValue())!=0){
			MsgBox.showWarning(this, "请先保存后再生产应收！");
			return;
		}
		boolean bool=false;
		MoneyDefineCollection mdCollection=MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection("select * where name ='违约金'");
		if(mdCollection.size()==0){
			MsgBox.showWarning(this, "“营销公共资料-款项类型”中必须定义一条名称和类别都为“违约金”的款项！");
			return;
		}
		bool=LiquidatedManageFactory.getRemoteInstance().genTenBillOtherPay(editData.getId());
		if(bool){
			MsgBox.showInfo(this, "成功生成1条应收费用！");
			super.setOprtState(STATUS_VIEW);
			this.btnEdit.setEnabled(false);
			this.btnGenTenBillOtherPay.setEnabled(false);
			this.btnSave.setEnabled(false);
			this.btnRemove.setEnabled(false);
			
			this.txtAmount.setEnabled(false);
			this.txtActAmount.setEnabled(false);
			this.txtDescription.setEnabled(false);
		}else{
			MsgBox.showInfo(this, "成功生成0条应收费用！");
			this.btnGenTenBillOtherPay.setEnabled(true);
		}
		super.actionGenTenBillOtherPay_actionPerformed(e);
	}

	public void loadFields() {
		super.loadFields();
		if(editData!=null&&editData.isIsGenerate()){
			this.btnGenTenBillOtherPay.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionEdit.setEnabled(false);
		}
	}

	protected void txtReliefAmount_dataChanged(DataChangeEvent e)
			throws Exception {
		if(this.txtReliefAmount.getValue()!=null&&this.txtAmount.getValue()!=null){
			this.txtActAmount.setValue(this.txtAmount.getBigDecimalValue().subtract(this.txtReliefAmount.getBigDecimalValue()));
		}else{
			this.txtActAmount.setValue(this.txtAmount.getValue());
		}
		super.txtReliefAmount_dataChanged(e);
	}

	protected void txtAmount_dataChanged(DataChangeEvent e) throws Exception {
		if(this.txtReliefAmount.getValue()!=null&&this.txtAmount.getValue()!=null){
			this.txtActAmount.setValue(this.txtAmount.getBigDecimalValue().subtract(this.txtReliefAmount.getBigDecimalValue()));
		}else{
			this.txtActAmount.setValue(this.txtAmount.getValue());
		}
		super.txtAmount_dataChanged(e);
	}
	
}