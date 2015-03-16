/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationTypeFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class SupplierEvaluationTypeListUI extends AbstractSupplierEvaluationTypeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierEvaluationTypeListUI.class);
    public SupplierEvaluationTypeListUI() throws Exception
    {
        super();
    }
    protected FDCDataBaseInfo getBaseDataInfo() {
		return new SupplierEvaluationTypeInfo();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return SupplierEvaluationTypeFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return SupplierEvaluationTypeEditUI.class.getName();
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}

}