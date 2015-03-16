/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.marketbase.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierFileTypInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class MarketSupplierFileTypListUI extends AbstractMarketSupplierFileTypListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketSupplierFileTypListUI.class);
    
    /**
     * output class constructor
     */
    public MarketSupplierFileTypListUI() throws Exception
    {
        super();
    }
    protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}


    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierFileTypFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierFileTypInfo objectValue = new com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierFileTypInfo();
		
        return objectValue;
    }

	protected FDCDataBaseInfo getBaseDataInfo() {
		return new MarketSupplierFileTypInfo();
	}

}