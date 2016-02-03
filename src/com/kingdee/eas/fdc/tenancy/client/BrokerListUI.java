/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.tenancy.BrokerFactory;
import com.kingdee.eas.fdc.tenancy.BrokerInfo;
import com.kingdee.eas.fdc.tenancy.IntentionCustomerInfo;
import com.kingdee.eas.fdc.tenancy.WeiChatFacade;
import com.kingdee.eas.fdc.tenancy.WeiChatFacadeFactory;
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
	protected String getEditUIModal()
	{
		return UIFactoryName.MODEL;
	}
    protected String getEditUIName() {
		return BrokerEditUI.class.getName();
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
		this.actionRemove.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.btnCancel.setVisible(false);
		this.btnCancelCancel.setVisible(false);
	}
}