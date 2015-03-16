/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.invite.InvitePurchaseModeFactory;
import com.kingdee.eas.fdc.invite.InvitePurchaseModeInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class InvitePurchaseModeListUI extends AbstractInvitePurchaseModeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(InvitePurchaseModeListUI.class);
    
    /**
     * output class constructor
     */
    public InvitePurchaseModeListUI() throws Exception
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
		return new InvitePurchaseModeInfo();
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		return InvitePurchaseModeFactory.getRemoteInstance();
	}

	@Override
	protected String getEditUIName() {
		return InvitePurchaseModeEditUI.class.getName();
	}

  

}