/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.CertificateCategoryEnum;
import com.kingdee.eas.fdc.sellhouse.CertificateFactory;
import com.kingdee.eas.fdc.sellhouse.CertificateInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class CertificateEditUI extends AbstractCertificateEditUI
{
	
	private Map certifacateMap = new HashMap() {
		{
			put("001", "001");
			put("002", "002");
			put("003", "003");
			put("004", "004");
			put("005", "005");
			put("006", "006");
		}
	};
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -8092032755165568389L;
	private static final Logger logger = CoreUIObject.getLogger(CertificateEditUI.class);
    
    /**
     * output class constructor
     */
    public CertificateEditUI() throws Exception
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
    
	protected ICoreBase getBizInterface() throws Exception {
		return CertificateFactory.getRemoteInstance();
	}

	protected IObjectValue createNewData() {
		CertificateInfo info =  new CertificateInfo();
		info.setCustomerType(CustomerTypeEnum.PersonalCustomer);
		return info;
	}

	protected FDCDataBaseInfo getEditData() {
		// TODO Auto-generated method stub
		return this.editData;
	}
	
	public SelectorItemCollection getSelectors() {
		 SelectorItemCollection su  = super.getSelectors();
		 su.add(new SelectorItemInfo("CU.id"));
		 return su;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		
		return null;
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCancel.setEnabled(false);
		this.actionCancelCancel.setEnabled(false);
		
		if(STATUS_VIEW.equals(getOprtState())){
			this.setUITitle("证件名称-查看");
		}else if(STATUS_EDIT.equals(getOprtState())){
			this.setUITitle("证件名称-修改");
			///this.txtNumber.setEditable(false);
		}
	}

	protected KDTextField getNumberCtrl() {
		
		return this.txtNumber;
	}
	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.txtNumber);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtName);
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData.getId()!=null && this.editData.getNumber()!=null){
			if(!certifacateMap.containsKey(this.editData.getNumber().toString())){
				try {
					
					CertificateFactory.getRemoteInstance().isDeleteCertificate(
							this.editData.getId().toString());
				} catch (BOSException ex) {
					logger.error(ex.getMessage());
					FDCMsgBox.showWarning(this, "单据已经被引用，不能删除!");
					SysUtil.abort();
				}
			}
		}
		super.actionRemove_actionPerformed(e);
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
	
		if(this.editData.getId()!=null && this.editData.getNumber()!=null){
			if(!certifacateMap.containsKey(this.editData.getNumber().toString())){
				try {
					
					CertificateFactory.getRemoteInstance().isDeleteCertificate(
							this.editData.getId().toString());
				} catch (BOSException ex) {
					logger.error(ex.getMessage());
					FDCMsgBox.showWarning(this, "单据已经被引用，不能删除!");
					SysUtil.abort();
				}
			}
		}
		
		super.actionEdit_actionPerformed(e);
		
		if(STATUS_EDIT.equals(getOprtState())){
			this.setUITitle("证件名称-修改");
			///this.txtNumber.setEditable(false);
		}
	}

}