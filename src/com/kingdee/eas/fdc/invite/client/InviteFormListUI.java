/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.invite.InviteFormFactory;
import com.kingdee.eas.fdc.invite.InviteFormInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class InviteFormListUI extends AbstractInviteFormListUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteFormListUI.class);
    
    /**
     * output class constructor
     */
    public InviteFormListUI() throws Exception
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
		return new InviteFormInfo();
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		return InviteFormFactory.getRemoteInstance();
	}

	@Override
	protected String getEditUIName() {
		return InviteFormEditUI.class.getName();
	}

}