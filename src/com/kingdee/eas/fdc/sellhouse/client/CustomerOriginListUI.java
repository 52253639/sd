/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.CustomerOriginFactory;
import com.kingdee.eas.fdc.sellhouse.CustomerOriginInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class CustomerOriginListUI extends AbstractCustomerOriginListUI
{
    private static final Logger logger = CoreUIObject.getLogger(CustomerOriginListUI.class);
    
    /**
     * output class constructor
     */
    public CustomerOriginListUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		this.actionQuery.setVisible(false);
		
		removeBizMenu();
		
		super.onLoad();
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}	
	}
    
    private void removeBizMenu() {
		this.menuBar.remove(menuBiz);
		this.toolBar.remove(btnCancelCancel);
        this.toolBar.remove(btnCancel);
	}
	protected FDCDataBaseInfo getBaseDataInfo() {
		return new CustomerOriginInfo();
	}
	protected String getEditUIName() {
		return CustomerOriginEditUI.class.getName();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return CustomerOriginFactory.getRemoteInstance();
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		FDCCustomerHelper.checkHasRelatedByFDCCustomer("customerOrigin.id", getSelectedIdValues());
		super.actionRemove_actionPerformed(e);
	}
}