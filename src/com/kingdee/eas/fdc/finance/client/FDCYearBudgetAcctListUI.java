/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.finance.FDCYearBudgetAcctFactory;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class FDCYearBudgetAcctListUI extends AbstractFDCYearBudgetAcctListUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCYearBudgetAcctListUI.class);
    
    /**
     * output class constructor
     */
    public FDCYearBudgetAcctListUI() throws Exception
    {
        super();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output tblMain_tableSelectChanged method
     */
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
    {
        super.tblMain_tableSelectChanged(e);
    }


	protected ICoreBase getRemoteInterface() throws BOSException {
		return FDCYearBudgetAcctFactory.getRemoteInstance();
	}
	
	protected String getEditUIName() {
		return FDCYearBudgetAcctEditUI.class.getName();
	}
	public void onLoad() throws Exception {
		super.onLoad();
		spMonth.setVisible(false);
		
		//版本号保留一位
		tblMain.getColumn("verNumber").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(1));
		actionRecension.setVisible(actionEdit.isVisible());
		actionRecension.setEnabled(actionEdit.isEnabled());
	}
	
}