/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class SubstituteTransfOutListUI extends AbstractSubstituteTransfOutListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SubstituteTransfOutListUI.class);
    
    /**
     * output class constructor
     */
    public SubstituteTransfOutListUI() throws Exception
    {
        super();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.basecrm.SubstituteTransfOutFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.basecrm.SubstituteTransfOutInfo objectValue = new com.kingdee.eas.fdc.basecrm.SubstituteTransfOutInfo();
		
        return objectValue;
    }

    
    
    
}