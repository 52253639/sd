/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.marketbase.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarktQuaLevelInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class MarktQuaLevelListUI extends AbstractMarktQuaLevelListUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarktQuaLevelListUI.class);
    
    /**
     * output class constructor
     */
    public MarktQuaLevelListUI() throws Exception
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
        return com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarktQuaLevelFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarktQuaLevelInfo objectValue = new com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarktQuaLevelInfo();
		
        return objectValue;
    }

	protected FDCDataBaseInfo getBaseDataInfo() {
		return new MarktQuaLevelInfo();
	}

}