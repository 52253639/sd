/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.sellhouse.OverdueCauseFactory;
import com.kingdee.eas.fdc.sellhouse.OverdueCauseInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class OverdueCauseListUI extends AbstractOverdueCauseListUI
{
    private static final Logger logger = CoreUIObject.getLogger(OverdueCauseListUI.class);
    
    /**
     * output class constructor
     */
    public OverdueCauseListUI() throws Exception
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
		return OverdueCauseFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return OverdueCauseEditUI.class.getName();
	}

	protected FDCDataBaseInfo getBaseDataInfo() {
		return  new OverdueCauseInfo();
	}


}