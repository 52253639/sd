/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BankPaymentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChangeNewStatusEnum;
import com.kingdee.eas.fdc.sellhouse.IBaseTransaction;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewCollection;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewFactory;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class SignManageListUI extends AbstractSignManageListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SignManageListUI.class);
    
    public SignManageListUI() throws Exception
    {
        super();
    }
	protected void afterTableFillData(KDTDataRequestEvent e) {
		super.afterTableFillData(e);
		CRMClientHelper.getFootRow(tblMain, new String[]{"bulidingArea","actBulidingArea","roomArea","actRoomArea","strdTotalAmount","dealTotalAmount","sellAmount","attachmentAmount","fitmentTotalAmount"});
	}
    protected void initControl() {
		super.initControl();
		CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"lastAgio","bulidingArea","actBulidingArea","strdBuildingPrice","roomArea","actRoomArea","strdRoomPrice","strdTotalAmount","dealBuildPrice","dealRoomArea","dealTotalAmount","sellAmount","attachmentAmount","fitmentTotalAmount"});
		FDCHelper.formatTableDate(getBillListTable(), "pur.bizDate");
		FDCHelper.formatTableDate(getBillListTable(), "pur.busAdscriptionDate");
//		FDCHelper.formatTableDate(getBillListTable(), "onRecordDate");
		FDCHelper.formatTableDate(getBillListTable(), "changeDate");
		if (!isSaleHouseOrg){
			this.actionWebMark.setEnabled(false);
			this.actionUnOnRecord.setEnabled(false);
			this.actionOnRecord.setEnabled(false);
		}
    }
	public void actionRelatePrePurchase_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		SignManageInfo info=SignManageFactory.getRemoteInstance().getSignManageInfo(new ObjectUuidPK(id));
		if(info.getSrcId()!=null){
			ObjectUuidPK pk=new ObjectUuidPK(info.getSrcId());
			IObjectValue objectValue=DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(),pk);
			if(objectValue instanceof PurchaseManageInfo){
				if(((PurchaseManageInfo)objectValue).getSrcId()!=null){
					
					ObjectUuidPK srcpk=new ObjectUuidPK(((PurchaseManageInfo)objectValue).getSrcId());
					IObjectValue srcobjectValue=DynamicObjectFactory.getRemoteInstance().getValue(srcpk.getObjectType(),srcpk);
					
					if(srcobjectValue instanceof PrePurchaseManageInfo){
						UIContext uiContext = new UIContext(this);
						uiContext.put("ID", ((PurchaseManageInfo)objectValue).getSrcId());
				        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
				        IUIWindow uiWindow = uiFactory.create(PrePurchaseManageEditUI.class.getName(), uiContext,null,OprtState.VIEW);
				        uiWindow.show();
				        return;
					}
				}
			}else if(objectValue instanceof PrePurchaseManageInfo){
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID", info.getSrcId());
		        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		        IUIWindow uiWindow = uiFactory.create(PrePurchaseManageEditUI.class.getName(), uiContext,null,OprtState.VIEW);
		        uiWindow.show();
		        return;
			}
		}
		FDCMsgBox.showWarning(this,"无关联预定单据！");
	}

	public void actionRelatePurchase_actionPerformed(ActionEvent e) throws Exception
    {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		SignManageInfo info=SignManageFactory.getRemoteInstance().getSignManageInfo(new ObjectUuidPK(id));
		if(info.getSrcId()!=null){
			ObjectUuidPK pk=new ObjectUuidPK(info.getSrcId());
			IObjectValue objectValue=DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(),pk);
			if(objectValue instanceof PurchaseManageInfo){
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID", info.getSrcId());
		        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		        IUIWindow uiWindow = uiFactory.create(PurchaseManageEditUI.class.getName(), uiContext,null,OprtState.VIEW);
		        uiWindow.show();
		        return;
			}
		}
		FDCMsgBox.showWarning(this,"无关联认购单据！");
    }


	public void actionOnRecord_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	
			SelectorItemCollection sels =new SelectorItemCollection();
	    	sels.add("bizState");
	    	sels.add("isOnRecord");
	    	SignManageInfo sign=SignManageFactory.getRemoteInstance().getSignManageInfo(new ObjectUuidPK(id.get(i).toString()));
	    	
	    	if(sign.isIsOnRecord()){
	    		FDCMsgBox.showWarning(this, "该单据已经网签！");
				return;
	    	}
			if (!getBizStateAuditEnum().equals(sign.getBizState())) {
				FDCMsgBox.showWarning("该单据不是审批状态，不能进行网签操作！");
				return;
			}
			SignManageFactory.getRemoteInstance().onRecord(BOSUuid.read(id.get(i).toString()),null,null); 
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}

	public void actionUnOnRecord_actionPerformed(ActionEvent e) throws Exception
	{
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	
			SelectorItemCollection sels =new SelectorItemCollection();
	    	sels.add("bizState");
	    	sels.add("isOnRecord");
	    	SignManageInfo sign=SignManageFactory.getRemoteInstance().getSignManageInfo(new ObjectUuidPK(id.get(i).toString()));
	    	
	    	if(!sign.isIsOnRecord()){
	    		FDCMsgBox.showWarning(this, "只有已备案的单据才可以进行取消备案操作！");
				return;
	    	}
			if (!getBizStateAuditEnum().equals(sign.getBizState())) {
				FDCMsgBox.showWarning("该单据不是审批状态，不能进行取消备案操作！");
				return;
			}
			SignManageFactory.getRemoteInstance().unOnRecord(BOSUuid.read(id.get(i).toString())); 
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
    public void actionWebMark_actionPerformed(ActionEvent e) throws Exception {
//		checkSelected();
//		UIContext uiContext = new UIContext(this);
//		uiContext.put(UIContext.ID, null);
//		uiContext.put(UIContext.OWNER, this);
//		uiContext.put("sellProjID", sellProject.getId().toString());
//		
//		ICell cell = tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex()).getCell("room.number");
//		String roomNumber = cell.getValue().toString();
//		uiContext.put("roomNumber", roomNumber);
		
//		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(WebMarkPutInEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
//		uiWindow.show();
	}

    protected String getEditUIName() {
		return SignManageEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws BOSException {
		return SignManageFactory.getRemoteInstance();
	}

	 protected void initWorkButton() {
		super.initWorkButton();
		
        this.btnRelatePurchase.setIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
        this.btnRelatePrePurchase.setIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
        this.btnWebMark.setIcon(EASResource.getIcon("imgTbtn_subjectsetting"));
        this.btnOnRecord.setIcon(EASResource.getIcon("imgTbtn_declarecollect"));
        this.btnUnOnRecord.setIcon(EASResource.getIcon("imgTbtn_declarecollect"));
        this.menuItemWebMark.setIcon(EASResource.getIcon("imgTbtn_subjectsetting"));
        this.menuItemOnRecord.setIcon(EASResource.getIcon("imgTbtn_declarecollect"));
        this.menuItemUnOnRecord.setIcon(EASResource.getIcon("imgTbtn_declarecollect"));
	       
        this.actionWebMark.setVisible(false);
        this.menuItemOnRecord.setText("网签");
        this.menuItemUnOnRecord.setText("取消网签");
        this.btnOnRecord.setText("网签");
        this.btnUnOnRecord.setText("取消网签");
	}

	protected TransactionStateEnum getBizStateAuditEnum() {
		return TransactionStateEnum.SIGNAUDIT;
	}

	protected TransactionStateEnum getBizStateInvalidEnum() {
		return TransactionStateEnum.SIGNNULLIFY;
	}

	protected TransactionStateEnum getBizStateSubmitEnum() {
		return TransactionStateEnum.SIGNAPPLE;
	}
	protected TransactionStateEnum getBizStateSavedEnum() {
		return TransactionStateEnum.SIGNSAVED;
	}
	protected void editCheck(String id) throws EASBizException, BOSException {
//		if(checkIsHasBankPayment(id)){
//			FDCMsgBox.showWarning(this,"签约单已经产生银行放款单业务，不能进行修改操作！");
//			SysUtil.abort();
//		}
	}
	private boolean checkIsHasBankPayment(String id) throws EASBizException, BOSException{
		if(id==null){
			return false;
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("signManager.id", id));
		return BankPaymentEntryFactory.getRemoteInstance().exists(filter);
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
		sels.add("signCustomerEntry.*");
		sels.add("signCustomerEntry.customer.*");
		sels.add("signCustomerEntry.certificate.*");
		sels.add("signPayListEntry.*");
		sels.add("signPayListEntry.moneyDefine.*");
		SignManageInfo info =SignManageFactory.getRemoteInstance().getSignManageInfo(new ObjectUuidPK(id),sels);
									
		if(!(TransactionStateEnum.SIGNAPPLE.equals(info.getBizState())||
				TransactionStateEnum.SIGNAUDIT.equals(info.getBizState()))){
			FDCMsgBox.showWarning(this,"该单据业务状态不能进行收款操作！");
			SysUtil.abort();
		}
		Object[] custObjs=new Object[info.getSignCustomerEntry().size()];
		for(int i=0;i<info.getSignCustomerEntry().size();i++){
			custObjs[i]=info.getSignCustomerEntry().get(i).getCustomer();
		}
		Set tranEntryIdSet = new HashSet();
		SignPayListEntryCollection signPayListColl = info.getSignPayListEntry();
		for (int i = 0; i < signPayListColl.size(); i++) {
			SignPayListEntryInfo signPayEntryInfo = signPayListColl.get(i);
			if(signPayEntryInfo.getTanPayListEntryId()!=null)
				tranEntryIdSet.add(signPayEntryInfo.getTanPayListEntryId().toString());
		}		
		CRMClientHelper.openTheGatherRevBillWindow(this, null, info.getSellProject(),info ,custObjs,tranEntryIdSet);
	}
	
	protected String whoAmI() {
		return IAMSIGN;
	}
}