/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.Map;

import org.apache.log4j.Logger;

import bsh.This;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.service.message.Message;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.CertifacateNameEnum;
import com.kingdee.eas.fdc.sellhouse.CertificateInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomer;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CustomerCardUI extends AbstractCustomerCardUI
{
	
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.btnSave.setVisible(false);
		this.actionSubmit.setVisible(false);
        this.actionRemove.setVisible(false);
        
        this.actionAttachment.setVisible(false);
        this.actionPrint.setVisible(false);
        this.actionPrintPreview.setVisible(false);
        this.actionNext.setVisible(false);
        this.actionFirst.setVisible(false);
        this.actionLast.setVisible(false);
        this.actionPre.setVisible(false);
        this.actionCancel.setVisible(false);
        this.actionCopy.setVisible(false);
        this.actionCancelCancel.setVisible(false);
        FDCCustomerInfo customer = (FDCCustomerInfo)this.getUIContext().get("Customer");
        //企业用户，个人用户的证件类型不同
        if(CustomerTypeEnum.EnterpriceCustomer.equals(customer.getCustomerType())){
        	/*this.CardNameText.removeItem(CertifacateNameEnum.IDCard);
        	this.CardNameText.removeItem(CertifacateNameEnum.Passport);
        	this.CardNameText.removeItem(CertifacateNameEnum.PRC);
        	this.CardNameText.removeItem(CertifacateNameEnum.MilitaryID)*/
        	initNewCertificateName(false);
        }else if(CustomerTypeEnum.PersonalCustomer.equals(customer.getCustomerType())){
        	/*this.CardNameText.removeItem(CertifacateNameEnum.License);
        	this.CardNameText.removeItem(CertifacateNameEnum.Histolysis);
        	this.CardNameText.removeItem(CertifacateNameEnum.Revenuer);
        	this.CardNameText.removeItem(CertifacateNameEnum.Corporative);*/
        	initNewCertificateName(true);
        }
        
		super.onLoad();
	}
	
	/**
	 * 初始化证件名称的过滤条件
	 * @param isPerson
	 */
	private void initNewCertificateName(boolean isPerson) {
		
		this.CardNameText.setEditable(false);
		
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		if(isPerson){
			filterInfo.getFilterItems().add(new FilterItemInfo("customerType", CustomerTypeEnum.PERSONALCUSTOMER_VALUE, CompareType.EQUALS));
		}else{
			filterInfo.getFilterItems().add(new FilterItemInfo("customerType", CustomerTypeEnum.ENTERPRICECUSTOMER_VALUE, CompareType.EQUALS));
		}
		evi.setFilter(filterInfo);
				this.CardNameText.setEntityViewInfo(evi);
				
	}
	protected void btnConfim_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		if (this.CardNumberText.getText() == null || this.CardNumberText.getText().trim().equals("")) {
			MsgBox.showInfo("证件号码不能为空！");
			abort();
		}
		// // put("cardId", this.CardNumberText.getText());
		// this.destroyWindow();
		//Map uiMap = uiWindow.getUIObject().getUIContext();
		FDCCustomerInfo customer = (FDCCustomerInfo)this.getUIContext().get("Customer");
		SelectorItemCollection sc = new SelectorItemCollection();
		
		if(this.CardNameText.getValue()==null){
			FDCMsgBox.showWarning(this,"请先选择证件名称！");
			SysUtil.abort();
		}
		CertificateInfo nameEnum = (CertificateInfo)this.CardNameText.getValue();
		customer.setCertificateNumber(this.CardNumberText.getText());
		customer.setCertificateName(nameEnum);
		sc.add("certificateName.id");
		//sc.add("certificateName.number");
		//sc.add("certificateName.name");
		sc.add("certificateNumber");
		FDCCustomerFactory.getRemoteInstance().updatePartial(customer, sc);
		//CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
		MsgBox.showInfo("用户证件号码更新成功!");
		destroyWindow();
		// this.getUIContext().put("IsUpdate",new Boolean(true));

	}

	private static final Logger logger = CoreUIObject.getLogger(CustomerCardUI.class);
    
    /**
     * output class constructor
     */
    public CustomerCardUI() throws Exception
    {
        super();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	protected IObjectValue createNewData() {
		// TODO Auto-generated method stub
		 FDCCustomerInfo value=new FDCCustomerInfo();
		 return value;
	  
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return FDCCustomerFactory.getRemoteInstance();
	}


	public static void addTheCustomerAuthority(FDCCustomerInfo Customer,Object uiCont){
		if(Customer!=null && (Customer.getCertificateName()==null || Customer.getCertificateNumber()==null || Customer.getCertificateNumber().trim().length() == 0)){
			UIContext uiContext = new UIContext(uiCont); 		
			uiContext.put(UIContext.ID, Customer.getId());
			uiContext.put("Customer", Customer);
			try {
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CustomerCardUI.class.getName(), uiContext, null, OprtState.EDIT);
				uiWindow.show();
			} catch (UIException e) {
				e.printStackTrace();
				SysUtil.abort();
			}
		}
	}
	
	
	
	

}