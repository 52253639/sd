/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.invite.DeployTypeFactory;
import com.kingdee.eas.fdc.invite.DeployTypeInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class DeployTypeListUI extends AbstractDeployTypeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(DeployTypeListUI.class);
    
    /**
     * output class constructor
     */
    public DeployTypeListUI() throws Exception
    {
        super();
    }

    protected boolean isIgnoreCUFilter() {
		return true;
	}
    
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}
    
	@Override
	protected FDCDataBaseInfo getBaseDataInfo() {
		return new DeployTypeInfo();
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		return DeployTypeFactory.getRemoteInstance();
	}

	@Override
	protected String getEditUIName() {
		return DeployTypeEditUI.class.getName();
	}
}