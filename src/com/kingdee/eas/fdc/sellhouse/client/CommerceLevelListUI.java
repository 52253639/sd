/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceLevelFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceLevelInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CommerceLevelListUI extends AbstractCommerceLevelListUI
{
    private static final Logger logger = CoreUIObject.getLogger(CommerceLevelListUI.class);
    
    /**
     * output class constructor
     */
    public CommerceLevelListUI() throws Exception
    {
        super();
    }


    
    

	public void onLoad() throws Exception {
		super.onLoad();
		
		this.actionQuery.setVisible(false);
		
		this.menuBar.remove(menuBiz);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.toolBar.remove(btnPrint);
		this.toolBar.remove(btnPrintPreview);		
		this.toolBar.remove(btnCancelCancel);
        this.toolBar.remove(btnCancel);
        
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID))
		{
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
        
	
	}





	protected FDCDataBaseInfo getBaseDataInfo() {
		return new CommerceLevelInfo();
	}

	protected String getEditUIName() {
		return CommerceLevelEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CommerceLevelFactory.getRemoteInstance();
	}



	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {

		super.actionEdit_actionPerformed(e);
	}
	
	

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String idStr = this.getSelectedKeyValue();
		if(idStr==null) return;
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("commerceLevel.id", idStr));
		if (CommerceChanceFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo("已经被商机管理调用,禁止删除!");
			return;
		}
		
		super.actionRemove_actionPerformed(e);
	}
	
	
	
	

}