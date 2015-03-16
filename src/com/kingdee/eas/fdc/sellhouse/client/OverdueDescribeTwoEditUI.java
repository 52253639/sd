/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.HashSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.OverdueCauseTypeEnum;
import com.kingdee.eas.fdc.sellhouse.OverdueDescribeFactory;
import com.kingdee.eas.fdc.sellhouse.OverdueDescribeInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class OverdueDescribeTwoEditUI extends AbstractOverdueDescribeTwoEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(OverdueDescribeTwoEditUI.class);
    
    /**
     * output class constructor
     */
    public OverdueDescribeTwoEditUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
		this.actionRemoveLine.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionAddLine.setVisible(false);
		if (STATUS_ADDNEW.equals(this.oprtState)) {
			if (( this.getUIContext().get("tranID") != null) && ( this.getUIContext().get("tranID") != "")) {
				 String tranID =   (String) this.getUIContext().get("tranID");
				 this.txtTransOviewId.setText(tranID);
			}
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("type", OverdueCauseTypeEnum.DD_VALUE));
		view.setFilter(filter);
		this.prmtOverdueSort.setEntityViewInfo(view);
		
		this.pkLatestDate.setRequired(true);
	}
	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.pkLatestDate);
		super.verifyInput(e);
	}
	public void loadFields() {
		super.loadFields();
	}

	public void onShow() throws Exception {
		super.onShow();
	}
	/**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	protected void attachListeners() {
		
	}
	protected void detachListeners() {
		
	}
	protected IObjectValue createNewData() {
		return new OverdueDescribeInfo() ;
	
	}
	protected ICoreBase getBizInterface() throws Exception {
		return OverdueDescribeFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return null;
	}
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

}