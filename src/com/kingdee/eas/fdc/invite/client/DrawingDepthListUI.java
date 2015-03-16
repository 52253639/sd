/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.invite.DrawingDepthFactory;
import com.kingdee.eas.fdc.invite.DrawingDepthInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class DrawingDepthListUI extends AbstractDrawingDepthListUI
{
    private static final Logger logger = CoreUIObject.getLogger(DrawingDepthListUI.class);
    
    /**
     * output class constructor
     */
    public DrawingDepthListUI() throws Exception
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

	@Override
	protected FDCDataBaseInfo getBaseDataInfo() {
		return new DrawingDepthInfo();
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		return DrawingDepthFactory.getRemoteInstance();
	}

	@Override
	protected String getEditUIName() {
		return DrawingDepthEditUI.class.getName();
	}

    protected boolean isIgnoreCUFilter() {
		return true;
	}
    
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}
    
}