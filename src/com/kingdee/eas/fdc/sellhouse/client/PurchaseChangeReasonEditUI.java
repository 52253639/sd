/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeReason;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeReasonFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeReasonInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class PurchaseChangeReasonEditUI extends AbstractPurchaseChangeReasonEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(PurchaseChangeReasonEditUI.class);
    
    /**
     * output class constructor
     */
    public PurchaseChangeReasonEditUI() throws Exception
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

	protected FDCDataBaseInfo getEditData() {
		return this.editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return this.kDMultiLangName;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected IObjectValue createNewData() {
		return new PurchaseChangeReasonInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PurchaseChangeReasonFactory.getRemoteInstance();
	}
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	String idStr = this.editData.getId().toString();
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
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID))
		{
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
	
		this.txtNumber.setMaxLength(80);
		this.kDMultiLangName.setMaxLength(80);
		this.txtDescription.setMaxLength(255);
    }
}