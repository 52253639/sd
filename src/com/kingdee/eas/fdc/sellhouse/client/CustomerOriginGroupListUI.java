/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.CustomerOriginFactory;
import com.kingdee.eas.fdc.sellhouse.CustomerOriginGroupFactory;
import com.kingdee.eas.fdc.sellhouse.CustomerOriginGroupInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CustomerOriginGroupListUI extends AbstractCustomerOriginGroupListUI
{
    private static final Logger logger = CoreUIObject.getLogger(CustomerOriginGroupListUI.class);
    
    /**
     * output class constructor
     */
    public CustomerOriginGroupListUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
		this.actionQuery.setVisible(false);
		this.menuBar.remove(menuBiz);
		this.toolBar.remove(btnCancelCancel);
        this.toolBar.remove(btnCancel);
		super.onLoad();
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}	
	}
	protected FDCDataBaseInfo getBaseDataInfo() {
		return new CustomerOriginGroupInfo();
	}
	protected String getEditUIName() {
		return CustomerOriginGroupEditUI.class.getName();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return CustomerOriginGroupFactory.getRemoteInstance();
	}
	 
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("group.id", FDCHelper.getSetByList(getSelectedIdValues()), CompareType.INCLUDE));
		if(CustomerOriginFactory.getRemoteInstance().exists(filter)){
			MsgBox.showInfo("已被客户来源关联，不可删除");
			  this.abort();
		}
		super.actionRemove_actionPerformed(e);
	}
}