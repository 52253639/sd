/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class PurchaseWorkFlowListUI extends AbstractPurchaseWorkFlowListUI
{
    private static final Logger logger = CoreUIObject.getLogger(PurchaseWorkFlowListUI.class);
    
    /**
     * output class constructor
     */
    public PurchaseWorkFlowListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    protected ICoreBase getBizInterface() throws Exception {
    	return PurchaseFactory.getRemoteInstance();
    }
    
    protected String getEditUIName() {
    	return PurchaseEditUI.class.getName();
    }

}