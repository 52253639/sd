/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.FirstPayProportionFactory;
import com.kingdee.eas.fdc.sellhouse.FirstPayProportionInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class FirstPayProportionListUI extends AbstractFirstPayProportionListUI
{
    private static final Logger logger = CoreUIObject.getLogger(FirstPayProportionListUI.class);
    
    /**
     * output class constructor
     */
    public FirstPayProportionListUI() throws Exception
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
		return new FirstPayProportionInfo();
	}
	protected String getEditUIName() {
		return FirstPayProportionEditUI.class.getName();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return FirstPayProportionFactory.getRemoteInstance();
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
//		FDCCustomerHelper.checkHasRelatedByFDCCustomer("firstPayProportion.id", getSelectedIdValues());
		super.actionRemove_actionPerformed(e);
	}
}