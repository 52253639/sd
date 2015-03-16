/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierBusinessModeFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierBusinessModeInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class SupplierBusinessModeListUI extends AbstractSupplierBusinessModeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierBusinessModeListUI.class);
    public SupplierBusinessModeListUI() throws Exception
    {
        super();
    }
    protected FDCDataBaseInfo getBaseDataInfo() {
		return new SupplierBusinessModeInfo();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return SupplierBusinessModeFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return SupplierBusinessModeEditUI.class.getName();
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}

}