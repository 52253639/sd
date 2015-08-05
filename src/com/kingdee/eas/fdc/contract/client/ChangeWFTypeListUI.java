/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.contract.ChangeWFTypeFactory;
import com.kingdee.eas.fdc.contract.ChangeWFTypeInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class ChangeWFTypeListUI extends AbstractChangeWFTypeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ChangeWFTypeListUI.class);
    public ChangeWFTypeListUI() throws Exception
    {
        super();
    }
    protected FDCDataBaseInfo getBaseDataInfo() {
		return new ChangeWFTypeInfo();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return ChangeWFTypeFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return ChangeWFTypeEditUI.class.getName();
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}
	protected boolean isSystemDefaultData(int activeRowIndex){
		return false;
    }

}