/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.sellhouse.OverdueDescribeFactory;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class OverdueDescribeTwoListUI extends AbstractOverdueDescribeTwoListUI
{
    private static final Logger logger = CoreUIObject.getLogger(OverdueDescribeTwoListUI.class);
    
    /**
     * output class constructor
     */
    public OverdueDescribeTwoListUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception 
    {
    	super.onLoad();
    	if (( this.getUIContext().get("tranID") != null) && ( this.getUIContext().get("tranID") != "")) {
    		String tranID =   (String) this.getUIContext().get("tranID");
    		EntityViewInfo view = new EntityViewInfo();
    		FilterInfo  filter = new FilterInfo();
    		filter.getFilterItems().add(new FilterItemInfo("transOviewId",tranID));
    		view.setFilter(filter);
    		mainQuery=view;
		}
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    protected String getEditUIName() {
    	return OverdueDescribeTwoEditUI.class.getName();
    }
    protected ICoreBase getBizInterface() throws Exception {
    	return OverdueDescribeFactory.getRemoteInstance();
    }

}