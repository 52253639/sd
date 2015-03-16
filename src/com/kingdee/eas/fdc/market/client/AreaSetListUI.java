/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.market.AreaSetFactory;
import com.kingdee.eas.fdc.market.AreaSetInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class AreaSetListUI extends AbstractAreaSetListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AreaSetListUI.class);
    
    SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
    
    /**
     * output class constructor
     */
    public AreaSetListUI() throws Exception
    {
        super();
    }

	public void onLoad() throws Exception {
		super.onLoad();
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID)){
			actionAddNew.setEnabled(false);
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
		}
	}

	/**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	protected FDCDataBaseInfo getBaseDataInfo() {
		return new AreaSetInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return AreaSetFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return com.kingdee.eas.fdc.market.client.AreaSetEditUI.class.getName();
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}
}