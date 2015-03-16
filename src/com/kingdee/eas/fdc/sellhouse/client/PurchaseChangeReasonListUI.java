/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeReason;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeReasonFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeReasonInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class PurchaseChangeReasonListUI extends AbstractPurchaseChangeReasonListUI
{
    private static final Logger logger = CoreUIObject.getLogger(PurchaseChangeReasonListUI.class);
    
    /**
     * output class constructor
     */
    public PurchaseChangeReasonListUI() throws Exception
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

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

	protected FDCDataBaseInfo getBaseDataInfo() {
		return new PurchaseChangeReasonInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PurchaseChangeReasonFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return PurchaseChangeReasonEditUI.class.getName();
	}
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	String idStr = this.getSelectedKeyValue();
    	if(idStr == null || "".equals(idStr)){
    		return;
    	}
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("purchaseChangeReason.id", idStr));
    	if(PurchaseChangeFactory.getRemoteInstance().exists(filter)){
    		MsgBox.showInfo("已被变更管理调用，禁止删除！");
    		return;
    	}
    	super.actionRemove_actionPerformed(e);
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	this.actionQuery.setVisible(false);
    	this.menuBar.remove(menuBiz);
    	this.actionPrint.setVisible(false);
    	this.actionPrintPreview.setVisible(false);
    	this.toolBar.remove(btnPrint);
    	this.toolBar.remove(btnPrintPreview);
    	this.toolBar.remove(btnCancel);
    	this.toolBar.remove(btnCancelCancel);
    	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    	if(!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)){
    		this.btnAddNew.setEnabled(false);
    		this.btnEdit.setEnabled(false);
    		this.btnRemove.setEnabled(false);
    	}
    }
}