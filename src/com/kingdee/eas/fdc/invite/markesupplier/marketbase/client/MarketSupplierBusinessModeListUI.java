/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.marketbase.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierBusinessModeInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class MarketSupplierBusinessModeListUI extends AbstractMarketSupplierBusinessModeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketSupplierBusinessModeListUI.class);
    
    /**
     * output class constructor
     */
    public MarketSupplierBusinessModeListUI() throws Exception
    {
        super();
    }


    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierBusinessModeFactory.getRemoteInstance();
    }
    protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}
    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierBusinessModeInfo objectValue = new com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierBusinessModeInfo();
		
        return objectValue;
    }

	protected FDCDataBaseInfo getBaseDataInfo() {
		return new MarketSupplierBusinessModeInfo();
	}

}