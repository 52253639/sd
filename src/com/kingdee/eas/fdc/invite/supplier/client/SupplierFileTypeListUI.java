/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class SupplierFileTypeListUI extends AbstractSupplierFileTypeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierFileTypeListUI.class);
    public SupplierFileTypeListUI() throws Exception
    {
        super();
    }
	protected FDCDataBaseInfo getBaseDataInfo() {
		return new SupplierFileTypeInfo();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return SupplierFileTypeFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return SupplierFileTypeEditUI.class.getName();
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}
}