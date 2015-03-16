/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.HopedUnitPriceFactory;
import com.kingdee.eas.fdc.sellhouse.HopedUnitPriceInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class HopedUnitPriceListUI extends AbstractHopedUnitPriceListUI
{
    private static final Logger logger = CoreUIObject.getLogger(HopedUnitPriceListUI.class);
    
    /**
     * output class constructor
     */
    public HopedUnitPriceListUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		this.actionQuery.setVisible(false);
		
		removeBizMenu();
		
		super.onLoad();
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
	}
    
    private void removeBizMenu() {
		this.menuBar.remove(menuBiz);
		this.toolBar.remove(btnCancelCancel);
        this.toolBar.remove(btnCancel);
	}
	protected FDCDataBaseInfo getBaseDataInfo() {
		return new HopedUnitPriceInfo();
	}
	protected String getEditUIName() {
		return HopedUnitPriceEditUI.class.getName();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return HopedUnitPriceFactory.getRemoteInstance();
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
//		FDCCustomerHelper.checkHasRelatedByFDCCustomer("hopedUnitPrice.id", getSelectedIdValues());
		super.actionRemove_actionPerformed(e);
	}
}