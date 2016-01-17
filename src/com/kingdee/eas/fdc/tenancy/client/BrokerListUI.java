/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.tenancy.BrokerFactory;
import com.kingdee.eas.fdc.tenancy.BrokerInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class BrokerListUI extends AbstractBrokerListUI
{
    private static final Logger logger = CoreUIObject.getLogger(BrokerListUI.class);
    public BrokerListUI() throws Exception
    {
        super();
    }
    protected FDCDataBaseInfo getBaseDataInfo() {
		return new BrokerInfo();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return BrokerFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return null;
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
	public void onShow() throws Exception {
		super.onShow();
		this.actionView.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.btnCancel.setVisible(false);
		this.btnCancelCancel.setVisible(false);
	}
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		
	}
	

}