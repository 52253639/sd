/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerOriginFactory;
import com.kingdee.eas.fdc.sellhouse.CustomerOriginInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class CustomerOriginEditUI extends AbstractCustomerOriginEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(CustomerOriginEditUI.class);
    
    /**
     * output class constructor
     */
    public CustomerOriginEditUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
		this.contDescription.setBoundLabelText("±¸×¢");
		this.actionRemove.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionCopy.setVisible(false);
		
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}	
	}
    
	protected IObjectValue createNewData() {
		CustomerOriginInfo value = new CustomerOriginInfo();
		return value;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CustomerOriginFactory.getRemoteInstance();
	}

	protected FDCDataBaseInfo getEditData() {
		return editData;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return kDBizMultiLangBox1;
	}
}