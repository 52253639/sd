/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChangeNewStatusEnum;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageCollection;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class PrePurchaseManageListUI extends AbstractPrePurchaseManageListUI
{
    private static final Logger logger = CoreUIObject.getLogger(PrePurchaseManageListUI.class);
    public PrePurchaseManageListUI() throws Exception
    {
        super();
    }
    public void actionReceiveBill_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("room.*");
		sels.add("sellProject.*");
		sels.add("prePurchaseCustomerEntry.*");
		sels.add("prePurchaseCustomerEntry.customer.*");
		sels.add("prePurchaseCustomerEntry.certificate.*");
		sels.add("prePurchasePayListEntry.*");
		sels.add("prePurchasePayListEntry.moneyDefine.*");
		PrePurchaseManageInfo info =PrePurchaseManageFactory.getRemoteInstance().getPrePurchaseManageInfo(new ObjectUuidPK(id),sels);
		if(!(TransactionStateEnum.PREAPPLE.equals(info.getBizState())||
				TransactionStateEnum.PREAUDIT.equals(info.getBizState()))){
			FDCMsgBox.showWarning(this,"该单据业务状态不能进行收款操作！");
			SysUtil.abort();
		}
		Object[] custObjs=new Object[info.getPrePurchaseCustomerEntry().size()];
		for(int i=0;i<info.getPrePurchaseCustomerEntry().size();i++){
			custObjs[i]=info.getPrePurchaseCustomerEntry().get(i).getCustomer();
		}
		
		Set tranEntryIdSet = new HashSet();
		PrePurchasePayListEntryCollection signPayListColl = info.getPrePurchasePayListEntry();
		for (int i = 0; i < signPayListColl.size(); i++) {
			PrePurchasePayListEntryInfo signPayEntryInfo = signPayListColl.get(i);
			if(signPayEntryInfo.getTanPayListEntryId()!=null)
				tranEntryIdSet.add(signPayEntryInfo.getTanPayListEntryId().toString());
		}	
		CRMClientHelper.openTheGatherRevBillWindow(this, null, info.getSellProject(), info ,custObjs,tranEntryIdSet);
	}
	protected ICoreBase getBizInterface() throws BOSException {
		return PrePurchaseManageFactory.getRemoteInstance();
	}
	
	protected String getEditUIName() {
		return PrePurchaseManageEditUI.class.getName();
	}
	/**
	 * 为新增、编辑、查看准备UIContext(当前项目)
	 */
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		SellProjectInfo project=null;
		if(node!=null && node.getUserObject() instanceof SellProjectInfo){
			project = (SellProjectInfo)node.getUserObject();
		}
		ItemAction act = getActionFromActionEvent(e);
		if (act.equals(actionAddNew)) {
			uiContext.put("project", project);
		}
	}

	protected TransactionStateEnum getBizStateAuditEnum() {
		return TransactionStateEnum.PREAUDIT;
	}

	protected TransactionStateEnum getBizStateSubmitEnum() {
		return TransactionStateEnum.PREAPPLE;
	}
	
	protected TransactionStateEnum getBizStateInvalidEnum() {
		return TransactionStateEnum.PRENULLIFY;
	}
	protected TransactionStateEnum getBizStateSavedEnum() {
		return TransactionStateEnum.PRESAVED;
	}
	public void actionToPurchase_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	SelectorItemCollection sels = super.getSelectors();
		sels.add("bizState");
		PrePurchaseManageInfo info=PrePurchaseManageFactory.getRemoteInstance().getPrePurchaseManageInfo(new ObjectUuidPK(id),sels);
		com.kingdee.eas.fdc.sellhouse.SHEManageHelper.toTransactionBill(info.getId(), info.getBizState(), this, PurchaseManageEditUI.class.getName());
		this.refresh(null);
	}
	public void actionToSign_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
    	String id = (String) row.getCell(this.getKeyFieldName()).getValue();
    	SelectorItemCollection sels = super.getSelectors();
		sels.add("bizState");
		PrePurchaseManageInfo info=PrePurchaseManageFactory.getRemoteInstance().getPrePurchaseManageInfo(new ObjectUuidPK(id),sels);
		com.kingdee.eas.fdc.sellhouse.SHEManageHelper.toTransactionBill(info.getId(), info.getBizState(), this, SignManageEditUI.class.getName());
		this.refresh(null);
	}
	
	protected void initControl() {
		super.initControl();
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"preAmount","bulidingArea","strdBuildingPrice","roomArea","strdRoomPrice","strdTotalAmount","attachmentAmount","fitmentTotalAmount"});
		this.actionMultiSubmit.setVisible(false);
		this.actionImport.setVisible(false);
		if (!isSaleHouseOrg){
			this.actionToPurchase.setEnabled(false);
			this.actionToSign.setEnabled(false);
		}else{
			this.actionToPurchase.setEnabled(true);
			this.actionToSign.setEnabled(true);
		}
	}
	protected String whoAmI() {
		return IAMPREPURCHASE;
	}

	public void actionRelatePurchase_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		PrePurchaseManageInfo info=PrePurchaseManageFactory.getRemoteInstance().getPrePurchaseManageInfo(new ObjectUuidPK(id));
	
		if(info!=null&&info.getId()!=null){
			//查取认购单
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("srcId",info.getId()));
			view.setFilter(filter);
			PurchaseManageCollection  purchaseColl= PurchaseManageFactory.getRemoteInstance().getPurchaseManageCollection(view);
			if(purchaseColl.get(0)!=null){
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID", purchaseColl.get(0).getId().toString());
		        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		        IUIWindow uiWindow = uiFactory.create(PurchaseManageEditUI.class.getName(), uiContext,null,OprtState.VIEW);
		        uiWindow.show();
		        return;
			}
		}
		FDCMsgBox.showWarning("无关联认购单据！");
	}

	public void actionRelateSign_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		PrePurchaseManageInfo info=PrePurchaseManageFactory.getRemoteInstance().getPrePurchaseManageInfo(new ObjectUuidPK(id));
		
		if(info!=null&&info.getId()!=null){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("srcId",info.getId()));
			view.setFilter(filter);
			SignManageCollection  signColl= SignManageFactory.getRemoteInstance().getSignManageCollection(view);
			if(signColl.get(0)!=null){
				UIContext uiContext = new UIContext(this);
			 	uiContext.put("ID", signColl.get(0).getId().toString());
			 	IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		        IUIWindow uiWindow = uiFactory.create(SignManageEditUI.class.getName(), uiContext,null,OprtState.VIEW);
		        uiWindow.show();
		        return;
			}else{
				PurchaseManageCollection  purchaseColl= PurchaseManageFactory.getRemoteInstance().getPurchaseManageCollection(view);
				if(purchaseColl.get(0)!=null){
					String purchaseId = purchaseColl.get(0).getId().toString();
					filter.getFilterItems().clear();
					filter.getFilterItems().add(new FilterItemInfo("srcId",purchaseId));
					SignManageCollection  tosignColl= SignManageFactory.getRemoteInstance().getSignManageCollection(view);
					if(tosignColl.get(0)!=null){
					 	UIContext uiContext = new UIContext(this);
					 	uiContext.put("ID", tosignColl.get(0).getId().toString());
					 	IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
				        IUIWindow uiWindow = uiFactory.create(SignManageEditUI.class.getName(), uiContext,null,OprtState.VIEW);
				        uiWindow.show();
				        return;
					}
				}	
			}
		}
		FDCMsgBox.showWarning("无关联签约单据！");
	}
	 protected void initWorkButton() {
		super.initWorkButton();
        this.btnRelatePurchase.setIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
        this.btnRelateSign.setIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
	}
	protected void afterTableFillData(KDTDataRequestEvent e) {
		 super.afterTableFillData(e);
		 CRMClientHelper.getFootRow(tblMain, new String[]{"preAmount","bulidingArea","roomArea","strdTotalAmount","attachmentAmount","fitmentTotalAmount"});
	}
}