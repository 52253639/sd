/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.tenancy.IIntentionCustomer;
import com.kingdee.eas.fdc.tenancy.IntentionCustomerFactory;
import com.kingdee.eas.fdc.tenancy.IntentionCustomerInfo;
import com.kingdee.eas.fdc.tenancy.client.IntentionCustomerEditUI;
import com.kingdee.eas.fdc.tenancy.client.IntentionCustomerListUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class IntentionCustomerListUI extends AbstractIntentionCustomerListUI
{
    private static final Logger logger = CoreUIObject.getLogger(IntentionCustomerListUI.class);
    public IntentionCustomerListUI() throws Exception
    {
        super();
    }
    protected static final String CANTEDIT = "cantEdit";
    protected static final String CANTREMOVE = "cantRemove";
    public void onLoad() throws Exception
	{
    	
    	actionQuery.setEnabled(false);
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		super.onLoad();
		
		initControl();
		
		actionQuery.setEnabled(true);
		
	}
    protected void initControl(){
    	this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
    	this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);

		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);

		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);

		this.actionTraceUp.setVisible(false);
		this.actionTraceDown.setVisible(false);
		
		this.actionAddNew.setVisible(false);
		this.actionAttachment.setVisible(false);
    }
    protected void initWorkButton() {
		super.initWorkButton();
		
        this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
        this.menuItemAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
       
        this.btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
        this.menuItemUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
	}

	protected void refresh(ActionEvent e) throws Exception
	{
		this.tblMain.removeRows();
	}
    protected String getEditUIModal()
	{
		return UIFactoryName.MODEL;
	}
    public FDCBillStateEnum getBizState(String id) throws EASBizException, BOSException, Exception{
    	if(id==null) return null;
    	SelectorItemCollection sels =new SelectorItemCollection();
    	sels.add("state");
    	return ((IntentionCustomerInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels)).getState();
    }
    protected String getEditUIName() {
		return IntentionCustomerEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws BOSException {
		return IntentionCustomerFactory.getRemoteInstance();
	}
    /**
     * 审批
     */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	
			if (!FDCBillStateEnum.SUBMITTED.equals(getBizState(id.get(i).toString()))) {
				FDCMsgBox.showWarning("单据不是提交状态，不能进行审批操作！");
				return;
			}
			((IIntentionCustomer)getBizInterface()).audit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	/**
	 * 反审批
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	
			if (!FDCBillStateEnum.AUDITTED.equals(getBizState(id.get(i).toString()))) {
				FDCMsgBox.showWarning("单据不是审批状态，不能进行反审批操作！");
				return;
			}
			((IIntentionCustomer)getBizInterface()).unAudit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
    protected void checkBeforeEditOrRemove(String warning,String id) throws EASBizException, BOSException, Exception {
    	//检查是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, id);
		
		FDCBillStateEnum bizState = getBizState(id);
		
		if (!FDCBillStateEnum.SUBMITTED.equals(bizState)&&!FDCBillStateEnum.SAVED.equals(bizState)) {
			if(warning.equals(CANTEDIT)){
				FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行修改操作！");
				SysUtil.abort();
			}else{
				FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行删除操作！");
				SysUtil.abort();
			}
		}
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	
    	checkBeforeEditOrRemove(CANTEDIT,id);
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
	    	checkBeforeEditOrRemove(CANTREMOVE,id.get(i).toString());
		}
		super.actionRemove_actionPerformed(e);
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	public boolean isIgnoreRowCount() {
		return false;
	}
	protected void fetchInitData() throws Exception {
    }

}