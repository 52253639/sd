/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.HopedFloorFactory;
import com.kingdee.eas.fdc.sellhouse.HopedFloorInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class HopedFloorListUI extends AbstractHopedFloorListUI
{
    private static final Logger logger = CoreUIObject.getLogger(HopedFloorListUI.class);
    
    /**
     * output class constructor
     */
    public HopedFloorListUI() throws Exception
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
		return new HopedFloorInfo();
	}
	protected String getEditUIName() {
		return HopedFloorEditUI.class.getName();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return HopedFloorFactory.getRemoteInstance();
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
//		FDCCustomerHelper.checkHasRelatedByFDCCustomer("hopedFloor.id", getSelectedIdValues());
		super.actionRemove_actionPerformed(e);
	}
}