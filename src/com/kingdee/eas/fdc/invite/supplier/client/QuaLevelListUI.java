/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.invite.supplier.LevelSetUpFactory;
import com.kingdee.eas.fdc.invite.supplier.LevelSetUpInfo;
import com.kingdee.eas.fdc.invite.supplier.QuaLevelFactory;
import com.kingdee.eas.fdc.invite.supplier.QuaLevelInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class QuaLevelListUI extends AbstractQuaLevelListUI
{
    private static final Logger logger = CoreUIObject.getLogger(QuaLevelListUI.class);
    public QuaLevelListUI() throws Exception
    {
        super();
    }
    protected FDCDataBaseInfo getBaseDataInfo() {
		return new QuaLevelInfo();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return QuaLevelFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return QuaLevelEditUI.class.getName();
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}

}