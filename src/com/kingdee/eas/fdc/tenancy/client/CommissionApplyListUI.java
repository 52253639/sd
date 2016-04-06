/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basecrm.client.FDCReceivingBillEditUI;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.tenancy.ICommissionApply;
import com.kingdee.eas.fdc.tenancy.CommissionApplyFactory;
import com.kingdee.eas.fdc.tenancy.CommissionApplyInfo;
import com.kingdee.eas.fdc.tenancy.IntentionCustomerCollection;
import com.kingdee.eas.fdc.tenancy.IntentionCustomerFactory;
import com.kingdee.eas.fdc.tenancy.IntentionCustomerInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillCollection;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.WeiChatFacadeFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CommissionApplyListUI extends AbstractCommissionApplyListUI
{
    private static final Logger logger = CoreUIObject.getLogger(CommissionApplyListUI.class);
    public CommissionApplyListUI() throws Exception
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
		
		this.actionAttachment.setVisible(false);
		
		this.tblCus.checkParsed();
		this.tblCus.setEnabled(false);
		this.tblCus.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		getcus();
    }
    
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
    	
		FilterInfo filter = new FilterInfo();
		int rowIndex = this.tblCus.getSelectManager().getActiveRowIndex();
		String id="'null'";
		if(rowIndex>=0){
			IRow row = this.tblCus.getRow(rowIndex);
			id = (String) row.getCell("id").getValue();
		}
		filter.getFilterItems().add(new FilterItemInfo("intentionCustomer.id",id));
		
		viewInfo = (EntityViewInfo) this.mainQuery.clone();
		if (viewInfo.getFilter() != null)
		{
			try {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
		{
			viewInfo.setFilter(filter);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
    protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		int rowIndex = this.tblCus.getSelectManager().getActiveRowIndex();
		if(e.getActionCommand().equals("com.kingdee.eas.framework.client.AbstractListUI$ActionAddNew")){
			if(rowIndex>=0){
				IRow row = this.tblCus.getRow(rowIndex);
				String id = (String) row.getCell("id").getValue();
		    	SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("*");
				sels.add("project.*");
				sels.add("broker.*");
				try {
					IntentionCustomerInfo  info = IntentionCustomerFactory.getRemoteInstance().getIntentionCustomerInfo(new ObjectUuidPK(id),sels);
					uiContext.put("cus",info);
				} catch (EASBizException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (BOSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else{
				FDCMsgBox.showWarning(this,"请选择行！");
				this.abort();
			}
		}
		super.prepareUIContext(uiContext, e);
	}

    protected void initWorkButton() {
		super.initWorkButton();
		
        this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
        this.menuItemAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
       
        this.btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
        this.menuItemUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
        
        this.btnPay.setIcon(EASResource.getIcon("imgTbtn_payment"));
	}
    private void getcus(){
    	this.tblCus.removeRows();
    	
    	EntityViewInfo vi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED));
		vi.setFilter(filter);
		SorterItemCollection sort=new SorterItemCollection();
		sort.add(new SorterItemInfo("createTime"));
		vi.setSorter(sort);
		SelectorItemCollection sels =new SelectorItemCollection();
		sels.add("*");
		sels.add("broker.*");
		sels.add("project.*");
		sels.add("creator.*");
		sels.add("auditor.*");
		sels.add("saleMan.*");
		vi.setSelector(sels);
		try {
			IntentionCustomerCollection tencol = IntentionCustomerFactory.getRemoteInstance().getIntentionCustomerCollection(vi);
			for(int i=0;i<tencol.size();i++){
				IRow row=this.tblCus.addRow();
				IntentionCustomerInfo info=tencol.get(i);
				row.getCell("id").setValue(info.getId().toString());
				row.getCell("name").setValue(info.getName());
				row.getCell("state").setValue(info.getState().getAlias());
				row.getCell("phone").setValue(info.getPhone());
				if(info.getSex()!=null)row.getCell("sex").setValue(info.getSex().getAlias());
				row.getCell("city").setValue(info.getCity());
				if(info.getProject()!=null)row.getCell("project.name").setValue(info.getProject().getName());
				row.getCell("amount").setValue(info.getAmount());
				row.getCell("payedAmount").setValue(info.getPayedAmount());
				row.getCell("infoAmount").setValue(info.getInfoAmount());
				row.getCell("payedInfoAmount").setValue(info.getPayedInfoAmount());
				if(info.getBroker()!=null)row.getCell("broker.name").setValue(info.getBroker().getName());
				row.getCell("bizDate").setValue(info.getBizDate());
				if(info.getAuditor()!=null)row.getCell("auditor.name").setValue(info.getAuditor().getName());
				row.getCell("auditTime").setValue(info.getAuditTime());
				row.getCell("isPayed").setValue(info.isIsPayed());
				row.getCell("contactName").setValue(info.getContactName());
				if(info.getSaleMan()!=null)row.getCell("saleMan.name").setValue(info.getSaleMan().getName());
				
			}
		} catch (BOSException ee) {
			ee.printStackTrace();
		}
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
    	return ((CommissionApplyInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels)).getState();
    }
    protected String getEditUIName() {
		return CommissionApplyEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws BOSException {
		return CommissionApplyFactory.getRemoteInstance();
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
			((ICommissionApply)getBizInterface()).audit(BOSUuid.read(id.get(i).toString()));
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
			((ICommissionApply)getBizInterface()).unAudit(BOSUuid.read(id.get(i).toString()));
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
	protected void tblCus_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			IRow row = this.tblCus.getRow(e.getRowIndex());
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", row.getCell("id").getValue().toString());
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(IntentionCustomerEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}
	protected void tblCus_tableSelectChanged(KDTSelectEvent e) throws Exception {
		this.refresh(null);
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	public boolean isIgnoreRowCount() {
		return false;
	}
	protected void fetchInitData() throws Exception {
    }
	public void actionPay_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	SelectorItemCollection sel=new SelectorItemCollection();
    	sel.add("intentionCustomer.*");
    	sel.add("*");
    	CommissionApplyInfo info=CommissionApplyFactory.getRemoteInstance().getCommissionApplyInfo(new ObjectUuidPK(id),sel);
    	if (MsgBox.showConfirm2New(this, "是否支付"+info.getIntentionCustomer().getName()+"佣金"+info.getAmount()+"元?") == MsgBox.YES) {
    		CommissionApplyFactory.getRemoteInstance().pay(BOSUuid.read(id));
        	FDCClientUtils.showOprtOK(this);
        	getcus();
    		this.refresh(null);
    	}
	}
	
}