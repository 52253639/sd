/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.invite.InviteContractFrameFactory;
import com.kingdee.eas.fdc.invite.InviteContractFrameInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class InviteContractFrameListUI extends AbstractInviteContractFrameListUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteContractFrameListUI.class);
    
    /**
     * output class constructor
     */
    public InviteContractFrameListUI() throws Exception
    {
        super();
    }

    @Override
	protected FDCDataBaseInfo getBaseDataInfo() {
		return new InviteContractFrameInfo();
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		return InviteContractFrameFactory.getRemoteInstance();
	}

	@Override
	protected String getEditUIName() {
		return InviteContractFrameEditUI.class.getName();
	}


}