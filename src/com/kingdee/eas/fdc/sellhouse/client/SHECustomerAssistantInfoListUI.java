/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class SHECustomerAssistantInfoListUI extends AbstractSHECustomerAssistantInfoListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SHECustomerAssistantInfoListUI.class);
    
    /**
     * output class constructor
     */
    public SHECustomerAssistantInfoListUI() throws Exception
    {
        super();
        actionLocate.setVisible(false);
		actionQueryScheme.setVisible(true);
		actionQuery.setVisible(false);
		actionCancel.setVisible(true);
		actionCancelCancel.setVisible(true);
    }

	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getEditUIName() {
		// TODO Auto-generated method stub
		return null;
	}

    

}