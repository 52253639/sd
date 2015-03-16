/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.MarketUnitSaleManFactory;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.SHERevBillCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.SHERevBillFactory;
import com.kingdee.eas.fdc.basecrm.SHERevBillInfo;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.AgioSchemeInfo;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.BizFlowEnum;
import com.kingdee.eas.fdc.sellhouse.BizListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BizListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BizListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BizTimeEnum;
import com.kingdee.eas.fdc.sellhouse.CalcTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomInfo;
import com.kingdee.eas.fdc.sellhouse.CollectionFactory;
import com.kingdee.eas.fdc.sellhouse.CollectionInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChangeNewStatusEnum;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.MarketUnitControlFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseAgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseSaleManEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ProjectProductTypeSet;
import com.kingdee.eas.fdc.sellhouse.ProjectSet;
import com.kingdee.eas.fdc.sellhouse.PurAgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurRoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurRoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageCollection;
import com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEParamConstant;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeBizTimeEnum;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SignAgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageCollection;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignRoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SinPurSaleMansEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SincerReceiveEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.SpecialAgioEnum;
import com.kingdee.eas.fdc.sellhouse.TranCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TranPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TranSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.StringUtils;
import com.kingdee.eas.fdc.sellhouse.AgioParam;
/**
 * output class name
 */
public class PurchaseManageEditUI extends AbstractPurchaseManageEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(PurchaseManageEditUI.class);
	protected MoneyDefineInfo purMoneyDefine=SHEManageHelper.getPurMoneyDefine();
	public static final String KEY_SUBMITTED_DATA = "submittedData";
	public static final String KEY_DESTORY_WINDOW = "destoryWindowAfterSubmit";
    /**
     * output class constructor
     */
    public PurchaseManageEditUI() throws Exception
    {
        super();
    }

    /**
     * @author xiaominWang
     */   
    public void onLoad() throws Exception {
    	super.onLoad();
		this.contRecommendPerson.setVisible(false);
		this.txtRecommend.setEnabled(false);
//    	initTblPayList();
	}
//    /**
//     * @author xiaominWang
//     */
//    private void initTblPayList() {
//    	KDBizPromptBox f7Box = new KDBizPromptBox(); 
//    	KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
//		f7Box.setDisplayFormat("$name$");
//		f7Box.setEditFormat("$number$");
//		f7Box.setCommitFormat("$number$");
//		f7Box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		
//		filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.SALEHOUSESYS_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("number", SHEManageHelper.RoomAttachMoneyDefineNum,CompareType.NOTEQUALS));//TODO  ?????
//		filter.getFilterItems().add(new FilterItemInfo("number", SHEManageHelper.FittmentMoneyDefineNum,CompareType.NOTEQUALS));//TODO  ??????
//		
//		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.EARNESTMONEY_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.FISRTAMOUNT_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.HOUSEAMOUNT_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.LOANAMOUNT_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.ACCFUNDAMOUNT_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.ELSEAMOUNT_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.REPLACEFEE_VALUE));
//		
//		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.LATEFEE_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("moneyType", MoneyTypeEnum.COMMISSIONCHARGE_VALUE));
//		filter.setMaskString("#0 and #1 and #2 and (#3 or #4 or #5 or #6 or #7 or #8 or #9 or #10 or #11)");//TODO xiaominwang
//		
//		view.setFilter(filter);
//		f7Box.setEntityViewInfo(view);
//		f7Editor = new KDTDefaultCellEditor(f7Box); 
//		this.tblPayList.getColumn(PL_MONEYNAME).setEditor(f7Editor);
//    }

    public void loadFields() {
		
		detachListeners();
		super.loadFields();
		setSaveActionStatus();

        int idx = idList.getCurrentIndex();
        if (idx >= 0) {
            billIndex = "(" + (idx + 1) + ")";
        } else {
        	billIndex = "";
        }
		refreshUITitle();
		
		setAuditButtonStatus(this.getOprtState());
		
		loadCommon();
		loadCustomerEntry(editData);
		loadReceiveBill();
		loadPayList(editData);
		loadAttachmentEntry(editData);
		loadBizReview();
		
		if(STATUS_ADDNEW.equals(this.oprtState)){
			addPurMoneyDefine(editData.getRoom(),editData.getSellProject());
		}
		
		this.comboSellType.removeItem(SellTypeEnum.PlanningSell);
		if(srcInfo!=null&&SellTypeEnum.LocaleSell.equals(srcInfo.getSellType())){
			this.comboSellType.removeItem(SellTypeEnum.PreSell);
		}
		if(STATUS_ADDNEW.equals(this.getOprtState())&&editData.getRoom()!=null){
			if(!editData.getRoom().isIsPre()){
    			setSellTypeNull("房间没有预售复核！");
    		}else{
    			this.comboSellType.setSelectedItem(SellTypeEnum.PreSell);
    		}
//			if(srcInfo==null||srcInfo instanceof SincerityPurchaseInfo){
				this.updateRoomArea();
				this.updateRoomInfo();
				this.updataRoomContractAndDealAmount();
//			}
		}
		attachListeners();
	}

//    /**
//     * 身份验证，对置业顾问进行设置//TODO xiaominWang
//     */
//    protected void authentication(){
//    	//得到当前登录用户
//    	UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();	
//		String id=currentUserInfo.getId().toString();
//		//当前选中项目
//		 SellProjectInfo spInfo=null;
//		if(null!=this.getUIContext().get("project")){
//			spInfo=(SellProjectInfo)this.getUIContext().get("project");
//		}else{
//			return;
//		}
//		try {
//			String currDate = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()); 
//			OrgUnitInfo orgUnit=SysContext.getSysContext().getCurrentOrgUnit();
//			boolean isPermit = false;
//			isPermit=MarketUnitControlFactory.getRemoteInstance().exists("where orgUnit.id = '"+orgUnit.getId()+"' and controler.id = '"+id+"' ");
//			if(isPermit){
//				this.f7Seller.setEditable(true);
//				this.f7Seller.setValue(currentUserInfo);
//			}else{
//				boolean isDuty=MarketUnitSaleManFactory.getRemoteInstance().exists("where orgUnit.id = '"+orgUnit.getId()+"' and " +
//						"dutyPerson.id = '"+id+"' and member.id = '"+ id +"' " +
//						"and accessionDate <= {ts '"+currDate+"'} and dimissionDate >= {ts '"+currDate+"'} and sellProject = '"+spInfo.getId()+"'");//
//				if(isDuty){
//					this.f7Seller.setEditable(true);
//				}else{
//					this.f7Seller.setEditable(false);
//				}
//				this.f7Seller.setValue(currentUserInfo);
//			}
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//    }
    
	protected void loadCommon() {
		if(SellTypeEnum.LocaleSell.equals(editData.getSellType())){	
			txtBuildingArea.setValue(editData.getStrdActualBuildingArea());
			txtRoomArea.setValue(editData.getStrdActualRoomArea());
		} else if(SellTypeEnum.PreSell.equals(editData.getSellType())){
			txtBuildingArea.setValue(editData.getBulidingArea());
			txtRoomArea.setValue(editData.getRoomArea());
		}else if(SellTypeEnum.PlanningSell.equals(editData.getSellType())){
			txtBuildingArea.setValue(editData.getStrdPlanBuildingArea());
			txtRoomArea.setValue(editData.getStrdPlanRoomArea());
		}else{
			txtBuildingArea.setValue(FDCHelper.ZERO);
			txtRoomArea.setValue(FDCHelper.ZERO);
		}
		
		this.txtFitmentAmount1.setValue(editData.getFitmentTotalAmount());
		
		if(CalcTypeEnum.PriceByBuildingArea.equals(editData.getValuationType())||CalcTypeEnum.PriceByRoomArea.equals(editData.getValuationType())){
			currAgioParam.setPriceAccountType(PriceAccountTypeEnum.PriceSetStand);
		}else{
			currAgioParam.setPriceAccountType(PriceAccountTypeEnum.StandSetPrice);
		}
		currAgioParam.setBasePriceSell(editData.isIsBasePriceSell());
		currAgioParam.setToIntegerType(SHEHelper.DEFAULT_TO_INTEGER_TYPE);
		currAgioParam.setDigit(SHEHelper.DEFAULT_DIGIT);
		AgioEntryCollection agioEntryColl = new AgioEntryCollection(); 
		for(int i=0;i<editData.getPurAgioEntry().size();i++)
			agioEntryColl.add(editData.getPurAgioEntry().get(i));
		this.txtAgio.setUserObject(agioEntryColl);
		currAgioParam.setAgios(agioEntryColl);
		
		this.saleMan.clear();
		
		for(int i=0;i<editData.getPurSaleManEntry().size();i++){
			this.saleMan.add(editData.getPurSaleManEntry().get(i));
		}
		this.f7Seller.setValue(this.saleMan.toArray());
		
		if(editData.getSrcId()!=null){
			ObjectUuidPK srcpk=new ObjectUuidPK(editData.getSrcId());
			try {
				IObjectValue objectValue = DynamicObjectFactory.getRemoteInstance().getValue(srcpk.getObjectType(),srcpk,SHEManageHelper.getBizSelectors(srcpk.getObjectType()));
				if(objectValue instanceof PrePurchaseManageInfo){
					srcInfo=(BaseTransactionInfo)objectValue;
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void loadCustomerEntry(PurchaseManageInfo info){
		this.customer=new ArrayList();
		for(int i=0;i<info.getPurCustomerEntry().size();i++){
			PurCustomerEntryInfo entry=info.getPurCustomerEntry().get(i);
			this.customer.add(entry);
		}
		SHEManageHelper.loadCustomer(labelCustomer1, labelCustomer2, labelCustomer3,labelCustomer4,labelCustomer5, this.customer, txtPhoneNumber, info);
	}

	
	protected void loadPayList(PurchaseManageInfo info) {
		PurPayListEntryCollection payEntrys = info.getPurPayListEntry();
		CRMHelper.sortCollection(payEntrys, "appDate", true);
		this.tblPayList.removeRows();
		for(int i=0;i<payEntrys.size();i++) {
			PurPayListEntryInfo entry = payEntrys.get(i);
			addPayListEntryRow(entry);
		}
	}
	
	protected void loadAttachmentEntry(PurchaseManageInfo info){
		PurRoomAttachmentEntryCollection entrys = info.getPurRoomAttachmentEntry();
		this.tblAttachProperty.removeRows();
		for(int i=0;i<entrys.size();i++) {
			PurRoomAttachmentEntryInfo entry = entrys.get(i);
			IRow row = this.tblAttachProperty.addRow();
			row.setUserObject(entry);
			row.getCell(AP_ROOMNUMBER).setValue(entry.getRoom());
			row.getCell(AP_BUILDINGAREA).setValue(entry.getBuildingArea());
			row.getCell(AP_ROOMAREA).setValue(entry.getRoomArea());
			row.getCell(AP_STANDARDTOTALAMOUNT).setValue(entry.getStandardTotalAmount());
			row.getCell(AP_BUILDINGPRICE).setValue(entry.getBuildingPrice());
			row.getCell(AP_ROOMPRICE).setValue(entry.getRoomPrice());
			
			row.getCell(AP_AGIO).setValue(entry.getPurAgioEntry().toArray());
			PurAgioEntryCollection purchaseCol=((PurRoomAttachmentEntryInfo)row.getUserObject()).getPurAgioEntry();
			try {
				setEntryAgio(row,purchaseCol,entry.getRoom());
			} catch (UIException e) {
				e.printStackTrace();
			}
			if(entry.getRoom()!=null){
				row.getCell(AP_AGIO).getStyleAttributes().setLocked(false);
			}else{
				row.getCell(AP_AGIO).getStyleAttributes().setLocked(true);
			}
			
			row.getCell(AP_ISMERGETOCONTRACT).setValue(new Boolean(entry.isIsAttachcmentToContract()));
			row.getCell(AP_MERGEAMOUNT).setValue(entry.getMergeAmount());
		}
	}
	protected void setEntryAgio(IRow row,PurAgioEntryCollection purchaseCol,RoomInfo room) throws UIException{
		AgioParam currEntryAgioParam=new AgioParam();
		currEntryAgioParam.setBasePriceSell(editData.isIsBasePriceSell());
		currEntryAgioParam.setToIntegerType(SHEHelper.DEFAULT_TO_INTEGER_TYPE);
		currEntryAgioParam.setDigit(SHEHelper.DEFAULT_DIGIT);
		
		if(CalcTypeEnum.PriceByBuildingArea.equals(room.getCalcType())||CalcTypeEnum.PriceByRoomArea.equals(room.getCalcType())){
			currEntryAgioParam.setPriceAccountType(PriceAccountTypeEnum.PriceSetStand);
		}else{
			currEntryAgioParam.setPriceAccountType(PriceAccountTypeEnum.StandSetPrice);
		}
		AgioEntryCollection agioEntrys = currEntryAgioParam.getAgios();
		for (int i = 0; i < purchaseCol.size(); i++) {
			PurAgioEntryInfo entryInfo = purchaseCol.get(i);
			agioEntrys.add(entryInfo);
		}
		KDBizPromptBox f7AgioBox = new KDBizPromptBox();
		f7AgioBox.setEnabledMultiSelection(true);
		f7AgioBox.setSelector(new AgioPromptDialog(this, room.getId().toString(), agioEntrys, currEntryAgioParam, editData,isAgioListCanEdit));
		f7AgioBox.setEditable(false);
		if(row.getCell(AP_AGIO).getValue()!=null){
			f7AgioBox.setDisplayFormat("");
		}else{
			f7AgioBox.setDisplayFormat(null);
		}
		KDTDefaultCellEditor f7AgioEditor = new KDTDefaultCellEditor(f7AgioBox);
		row.getCell(AP_AGIO).setEditor(f7AgioEditor);
			
		if(purchaseCol.size()>0){
			PurchaseParam purParam = SHEManageHelper.getPurchaseAgioParam(currEntryAgioParam, room, 
					room.getSellType(), false, null, null, null ,null, null,null);
			row.getCell(AP_MERGEAMOUNT).setValue(purParam.getDealTotalAmount());
		}else{
			row.getCell(AP_MERGEAMOUNT).setValue(row.getCell(AP_STANDARDTOTALAMOUNT).getValue());
		}
	}
	protected void updateRoomArea(){
		super.updateRoomArea();
    	if(this.comboValuationType.getSelectedItem()!=null&&this.f7Room.getValue()!=null){
    		CalcTypeEnum valuationType=(CalcTypeEnum)this.comboValuationType.getSelectedItem();
    		if(CalcTypeEnum.PriceByBuildingArea.equals(valuationType)||CalcTypeEnum.PriceByTotalAmount.equals(valuationType)){
    			this.txtPlanningArea.setValue(editData.getStrdPlanBuildingArea());
    			this.txtPreArea.setValue(editData.getBulidingArea());
    			this.txtActualArea.setValue(editData.getStrdActualBuildingArea());
    		}
    		if(CalcTypeEnum.PriceByRoomArea.equals(valuationType)){
    			this.txtPlanningArea.setValue(editData.getStrdPlanRoomArea());
    			this.txtPreArea.setValue(editData.getRoomArea());
    			this.txtActualArea.setValue(editData.getStrdActualRoomArea());
    		}
    	}else{
			this.txtPlanningArea.setValue(FDCHelper.ZERO);
			this.txtPreArea.setValue(FDCHelper.ZERO);
			this.txtActualArea.setValue(FDCHelper.ZERO);
			this.txtPlanningCompensate.setValue(FDCHelper.ZERO);
			this.txtCashSalesCompensate.setValue(FDCHelper.ZERO);
		}
	}
	protected void updateRoomInfo(){
		super.updateRoomInfo();
		if(this.f7Room.getValue()!=null){
			RoomInfo room=(RoomInfo)this.f7Room.getValue();
			if(SellTypeEnum.PlanningSell.equals(room.getSellType())){
				this.comboSellType.setSelectedItem(SellTypeEnum.PreSell);
			}
		}
	}
	protected void updateAmount(){
		isEditDealAmount=false;
		super.updateAmount();
		BigDecimal dealPrice=FDCHelper.ZERO;
		if(CalcTypeEnum.PriceByBuildingArea.equals(this.comboValuationType.getSelectedItem())){
			dealPrice=this.txtDealBuildPrice.getBigDecimalValue();
		}
		if(CalcTypeEnum.PriceByRoomArea.equals(this.comboValuationType.getSelectedItem())){
			dealPrice=this.txtDealRoomPrice.getBigDecimalValue();
		}
		BigDecimal preArea=this.txtPreArea.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtPreArea.getBigDecimalValue();
		BigDecimal planningArea=this.txtPlanningArea.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtPlanningArea.getBigDecimalValue();
		BigDecimal actualArea=this.txtActualArea.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtActualArea.getBigDecimalValue();
		
		if(srcInfo!=null){
			if(SellTypeEnum.LocaleSell.equals((SellTypeEnum)this.comboSellType.getSelectedItem())){
				if(SellTypeEnum.PreSell.equals(srcInfo.getSellType())&&srcInfo instanceof PrePurchaseManageInfo){
					this.txtPlanningCompensate.setValue(FDCHelper.ZERO);
					this.txtCashSalesCompensate.setValue((actualArea.subtract(preArea)).multiply(dealPrice));
				}else if(SellTypeEnum.PlanningSell.equals(srcInfo.getSellType())&&srcInfo instanceof PrePurchaseManageInfo){
					this.txtPlanningCompensate.setValue(FDCHelper.ZERO);
					this.txtCashSalesCompensate.setValue((actualArea.subtract(planningArea)).multiply(dealPrice));
				}else{
					this.txtPlanningCompensate.setValue(FDCHelper.ZERO);
					this.txtCashSalesCompensate.setValue(FDCHelper.ZERO);
				}
			}
			if(SellTypeEnum.PreSell.equals((SellTypeEnum)this.comboSellType.getSelectedItem())){
				if(SellTypeEnum.PlanningSell.equals(srcInfo.getSellType())&&srcInfo instanceof PrePurchaseManageInfo){
					this.txtPlanningCompensate.setValue((preArea.subtract(planningArea)).multiply(dealPrice));
					this.txtCashSalesCompensate.setValue(FDCHelper.ZERO);
				}else{
					this.txtPlanningCompensate.setValue(FDCHelper.ZERO);
					this.txtCashSalesCompensate.setValue(FDCHelper.ZERO);
				}
			}
			
		}
		updataRoomContractAndDealAmount();
		updateRoomAttactAndFittmentMD();
		if(this.f7PayType.getValue()!=null){
			updatePayList();
		}
		isEditDealAmount=true;
	}
	protected void updataRoomContractAndDealAmount(){
		RoomInfo roomInfo = (RoomInfo) this.f7Room.getValue();
		if(roomInfo==null) return;
		
		isEditDealAmount=false;
		
		SellTypeEnum sellType = (SellTypeEnum)this.comboSellType.getSelectedItem();
		CalcTypeEnum valuationType = (CalcTypeEnum)this.comboValuationType.getSelectedItem();
		
		BigDecimal roomArea=this.txtRoomArea.getBigDecimalValue();
		BigDecimal buildingArea=this.txtBuildingArea.getBigDecimalValue();
		BigDecimal roomPrice=this.txtRoomPrice.getBigDecimalValue();
		BigDecimal buildingPrice=this.txtBuildingPrice.getBigDecimalValue();
		BigDecimal standardAmount=this.txtStandardTotalAmount.getBigDecimalValue();
		
		
		BigDecimal fitmentAmount = this.txtFitmentAmount.getBigDecimalValue();
		BigDecimal attachmentAmount =getMergeContractAttachmentTotalAmount(true);
		
		BigDecimal cashSalesCompensate=this.txtCashSalesCompensate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtCashSalesCompensate.getBigDecimalValue();
		BigDecimal planningCompensate=this.txtPlanningCompensate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtPlanningCompensate.getBigDecimalValue();
		
		BigDecimal areaCompensateAmount =cashSalesCompensate.add(planningCompensate);
		
		boolean isFitmentToContract = this.chkIsFitmentToContract.isSelected();
		SHEPayTypeInfo payType = (SHEPayTypeInfo) this.f7PayType.getValue();
		String payTypeName = null;
		BigDecimal payTypeAgio = FDCHelper.ONE_HUNDRED;
		if (payType != null) {
			payTypeName = payType.getName(); 
			payTypeAgio = payType.getAgio();
			if(payTypeAgio == null){
				payTypeAgio = FDCHelper.ONE_HUNDRED;
			}
		}
		PurchaseParam purParam = SHEManageHelper.getAgioParam(this.currAgioParam, roomInfo, 
				 sellType,valuationType,isFitmentToContract,roomArea,buildingArea,roomPrice,buildingPrice,standardAmount,fitmentAmount, attachmentAmount, areaCompensateAmount ,SpecialAgioEnum.DaZhe, payTypeAgio,payTypeName);
		if(purParam!=null) {
			this.txtAgioDes.setText(purParam.getAgioDes());
			this.txtAgio.setValue(purParam.getFinalAgio());
			this.txtDealTotalAmount.setValue(purParam.getDealTotalAmount());
			this.txtContractTotalAmount.setValue(purParam.getContractTotalAmount());
			this.txtContractBuildPrice.setValue(purParam.getContractBuildPrice());
			this.txtContractRoomPrice.setValue(purParam.getContractRoomPrice());
			this.txtDealBuildPrice.setValue(purParam.getDealBuildPrice());
			this.txtDealRoomPrice.setValue(purParam.getDealRoomPrice());
		}
		isEditDealAmount=true;
	}
	
	protected void storeCustomerEntry() {
		editData.getPurCustomerEntry().clear();
		setCustomerEntry(editData,this.customer);
	}
	public void storeFields() {
//		已经在选择客户的时候store了
//		storeCustomerEntry();
		storeCommon();
		storePayList();
		storeAttachmentEntry();
		super.storeFields();
		setTxtFormateNumerValue();
	}
	protected void setTxtFormateNumerValue(){
		super.setTxtFormateNumerValue();
		editData.setPlanningCompensate(SHEManageHelper.setScale(digit, toIntegerType,this.txtPlanningCompensate.getBigDecimalValue()));
		editData.setCashSalesCompensate(SHEManageHelper.setScale(digit, toIntegerType,this.txtCashSalesCompensate.getBigDecimalValue()));
	}
	protected void storeCommon() {
		AgioEntryCollection agioEntrys = (AgioEntryCollection)this.txtAgio.getUserObject();
		editData.getPurAgioEntry().clear();
		for (int i = 0; i < agioEntrys.size(); i++) {
			PurAgioEntryInfo entryInfo = (PurAgioEntryInfo)agioEntrys.get(i);
			editData.getPurAgioEntry().add(entryInfo);
		}
		
		String saleManNames="";
		editData.getPurSaleManEntry().clear();
		for(int i=0;i<this.saleMan.size();i++){
			if(i==this.saleMan.size()-1){
				saleManNames=saleManNames+((TranSaleManEntryInfo)this.saleMan.get(i)).getUser().getName();
			}else{
				saleManNames=saleManNames+((TranSaleManEntryInfo)this.saleMan.get(i)).getUser().getName()+";";
			}
			PurSaleManEntryInfo entry=new PurSaleManEntryInfo();
			SHEManageHelper.setSaleManEntry(entry,(TranSaleManEntryInfo)this.saleMan.get(i));
			editData.getPurSaleManEntry().add(entry);
		}
		editData.setSaleManNames(saleManNames);
	}
	
	private void setCustomerEntry(PurchaseManageInfo purchase,List customerList){
		String customerNames="";
		String customerPhone="";
		String recommendeds="";
		PurSaleManEntryInfo entry=new PurSaleManEntryInfo();
		for(int i=0;i<customerList.size();i++){
			SHECustomerInfo sheCI = new SHECustomerInfo();
			PurCustomerEntryInfo info = new PurCustomerEntryInfo();
			TranCustomerEntryInfo tranInfo=(TranCustomerEntryInfo)this.customer.get(i);
			SHEManageHelper.setCustomerListEntry(info, tranInfo);
			//获取客户台帐客户信息
			sheCI = tranInfo.getCustomer();
			String phone="";
			String mob="";
			String tel="";
			String recommended="";
			if(info.getPhone()!=null&&!info.getPhone().trim().equals("")){
				mob=info.getPhone()+"(M)";
			}
			if(info.getTel()!=null&&!info.getTel().trim().equals("")){
				tel=info.getTel()+"(T)";
			}
			if(!mob.equals("")&&!tel.equals("")){
				phone=mob+","+tel;
			}else if(!mob.equals("")){
				phone=mob;
			}else if(!tel.equals("")){
				phone=tel;
			}
			if(sheCI.getRecommended()!=null&&!sheCI.getRecommended().trim().equals("")){
				recommended=sheCI.getRecommended();
			}
			if(i==customerList.size()-1){
				customerNames=customerNames+info.getName();
				customerPhone=customerPhone+phone;
				recommendeds=recommendeds+recommended;
			}else{
				customerNames=customerNames+info.getName()+";";
				customerPhone=customerPhone+phone+";";
				recommendeds=recommendeds+recommended+";";
			}
			purchase.getPurCustomerEntry().add(info);
			entry.setUser(info.getCustomer().getPropertyConsultant());
		}
		purchase.setCustomerNames(customerNames);
		purchase.setCustomerPhone(customerPhone);
		txtRecommend.setText(recommendeds);
		
		if(this.saleMan.size()==0&&entry.getUser()!=null){
			this.saleMan.add(entry);
			this.f7Seller.setValue(this.saleMan.toArray());
		}
	}

	protected void storePayList() {
		PurchaseManageInfo info = this.editData;
		info.setPayType((SHEPayTypeInfo)this.f7PayType.getValue());
		info.setAgioScheme((AgioSchemeInfo)this.f7AgioScheme.getValue());
		info.getPurPayListEntry().clear();
		for (int i = 0; i < this.tblPayList.getRowCount(); i++) {
			IRow row = this.tblPayList.getRow(i);
			PurPayListEntryInfo entry = (PurPayListEntryInfo) row.getUserObject();
			entry.setSeq(i);
			MoneyDefineInfo moneyName = (MoneyDefineInfo) row.getCell(PL_MONEYNAME).getValue();
			entry.setMoneyDefine(moneyName);
			entry.setAppDate((Date) row.getCell(PL_APPDATE).getValue());
			entry.setCurrency((CurrencyInfo) row.getCell(PL_CURRENCY).getValue());
			entry.setAppAmount(SHEManageHelper.setScale(digit, toIntegerType,(BigDecimal) row.getCell(PL_APPAMOUNT).getValue()));
			entry.setDescription((String) row.getCell(PL_DES).getValue());
			info.getPurPayListEntry().add(entry);
		}
	}

	protected void storeAttachmentEntry(){
		String name="附属房产:";
		PurchaseManageInfo info = this.editData;
		info.getPurRoomAttachmentEntry().clear();
		for (int i = 0; i < this.tblAttachProperty.getRowCount(); i++) {
			IRow row = this.tblAttachProperty.getRow(i);
			PurRoomAttachmentEntryInfo entry = (PurRoomAttachmentEntryInfo) row.getUserObject();
			entry.setRoom((RoomInfo)row.getCell(AP_ROOMNUMBER).getValue());
			if(entry.getRoom()!=null) name=name+entry.getRoom()+";";
			entry.setIsAttachcmentToContract(((Boolean)row.getCell(AP_ISMERGETOCONTRACT).getValue()).booleanValue());
			entry.setMergeAmount(SHEManageHelper.setScale(digit, toIntegerType,(BigDecimal)row.getCell(AP_MERGEAMOUNT).getValue()));
			entry.setStandardTotalAmount(SHEManageHelper.setScale(digit, toIntegerType,(BigDecimal)row.getCell(AP_STANDARDTOTALAMOUNT).getValue()));
			entry.setAgioDes(getAgioDes(entry.getPurAgioEntry().toArray()));
			entry.setBuildingPrice(SHEManageHelper.setScale(priceDigit, priceToIntegerType,(BigDecimal)row.getCell(AP_BUILDINGPRICE).getValue()));
			entry.setRoomPrice(SHEManageHelper.setScale(priceDigit, priceToIntegerType,(BigDecimal)row.getCell(AP_ROOMPRICE).getValue()));
			
			entry.setBuildingArea((BigDecimal)row.getCell(AP_BUILDINGAREA).getValue());
			entry.setRoomArea((BigDecimal)row.getCell(AP_ROOMAREA).getValue());
			
			info.getPurRoomAttachmentEntry().add(entry);
		}
		if(!name.equals("附属房产:"))
			this.txtDes.setText(name);
	}
	
	private void setInfoFromSrcInfo(IObjectValue objectValue,PurchaseManageInfo info){
		info.getPurCustomerEntry().clear();
		info.getPurPayListEntry().clear();
		info.getPurAgioEntry().clear();
		info.getPurRoomAttachmentEntry().clear();
		
		if(objectValue instanceof SincerityPurchaseInfo){
			SincerityPurchaseInfo sincerityPurchaseInfo=(SincerityPurchaseInfo)objectValue;
			SincerityPurchaseCustomerEntryCollection customerCol=sincerityPurchaseInfo.getCustomer();
			SinPurSaleMansEntryCollection saleCol=sincerityPurchaseInfo.getSaleMansEntry();
			
			for(int i=0;i<customerCol.size();i++){
				PurCustomerEntryInfo entry=new PurCustomerEntryInfo();
				SHEManageHelper.setCustomerListEntry(entry,customerCol.get(i));
				
				info.getPurCustomerEntry().add(entry);
			}
			info.setSellProject(sincerityPurchaseInfo.getSellProject());
			info.setCustomerNames(sincerityPurchaseInfo.getCustomerNames());
			info.setCustomerPhone(sincerityPurchaseInfo.getCustomerPhone());
			
//			SincerReceiveEntryCollection payCol=sincerityPurchaseInfo.getSincerPriceEntrys();
//			CRMHelper.sortCollection(payCol, "seq", true);
//			for(int i=0;i<payCol.size();i++){
//				PurPayListEntryInfo entry=new PurPayListEntryInfo();
//				SHEManageHelper.setPayListEntry(null,entry,payCol.get(i));
//				info.getPurPayListEntry().add(entry);
//			}
			
			for(int i=0;i<saleCol.size();i++){
				PurSaleManEntryInfo entry=new PurSaleManEntryInfo();
				SHEManageHelper.setSaleManEntry(entry,saleCol.get(i));
				
				info.getPurSaleManEntry().add(entry);
			}
			info.setSaleManNames(sincerityPurchaseInfo.getSaleManStr());
			
			info.setNewCommerceChance(sincerityPurchaseInfo.getNewCommerceChance());
			info.setTransactionID(sincerityPurchaseInfo.getTransactionID());
		}else if(objectValue instanceof PrePurchaseManageInfo){
			PrePurchaseManageInfo prePurchaseManageInfo=(PrePurchaseManageInfo)objectValue;
			SHEManageHelper.setBaseInfo(info,prePurchaseManageInfo,false);
			PrePurchaseCustomerEntryCollection customerCol=prePurchaseManageInfo.getPrePurchaseCustomerEntry();
			for(int i=0;i<customerCol.size();i++){
				PurCustomerEntryInfo entry=new PurCustomerEntryInfo();
				SHEManageHelper.setCustomerListEntry(entry,customerCol.get(i));
				
				info.getPurCustomerEntry().add(entry);
			}
			
			PrePurchasePayListEntryCollection payCol=prePurchaseManageInfo.getPrePurchasePayListEntry();
			CRMHelper.sortCollection(payCol, "seq", true);
			for(int i=0;i<payCol.size();i++){
				PurPayListEntryInfo entry=new PurPayListEntryInfo();
				if(payCol.get(i).getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.PreconcertMoney)){
					continue;
				}
				SHEManageHelper.setPayListEntry(null,entry,payCol.get(i));
				info.getPurPayListEntry().add(entry);
			}
			
			PrePurchaseAgioEntryCollection agioCol=prePurchaseManageInfo.getPrePurchaseAgioEntry();
			for(int i=0;i<agioCol.size();i++){
				PurAgioEntryInfo entry=new PurAgioEntryInfo();
				SHEManageHelper.setAgioListEntry(entry,agioCol.get(i));
				info.getPurAgioEntry().add(entry);
			}
			
			PrePurchaseRoomAttachmentEntryCollection attachCol=prePurchaseManageInfo.getPrePurchaseRoomAttachmentEntry();
			for(int i=0;i<attachCol.size();i++){
				PurRoomAttachmentEntryInfo entry=new PurRoomAttachmentEntryInfo();
				SHEManageHelper.setRoomAttachmentListEntry(entry,attachCol.get(i));
				for(int j=0;j<attachCol.get(i).getPrePurchaseAgioEntry().size();j++){
					PurAgioEntryInfo agioEntry=new PurAgioEntryInfo();
					SHEManageHelper.setAgioListEntry(agioEntry,attachCol.get(i).getPrePurchaseAgioEntry().get(j));
					entry.getPurAgioEntry().add(agioEntry);
				}
				info.getPurRoomAttachmentEntry().add(entry);
			}
			
			PrePurchaseSaleManEntryCollection saleCol=prePurchaseManageInfo.getPrePurchaseSaleManEntry();
			for(int i=0;i<saleCol.size();i++){
				PurSaleManEntryInfo entry=new PurSaleManEntryInfo();
				SHEManageHelper.setSaleManEntry(entry,saleCol.get(i));
				info.getPurSaleManEntry().add(entry);
			}
			info.setSaleManNames(prePurchaseManageInfo.getSaleManNames());
		}else if(objectValue instanceof ChooseRoomInfo){
			if(((ChooseRoomInfo)objectValue).getSalesMan()!=null){
				PurSaleManEntryInfo entry=new PurSaleManEntryInfo();
				entry.setUser(((ChooseRoomInfo)objectValue).getSalesMan());
				info.getPurSaleManEntry().add(entry);
				info.setSaleManNames(((ChooseRoomInfo)objectValue).getSalesMan().getName());
			}
		}
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		return super.createNewDetailData(table);
	}

	protected IObjectValue createNewApData() {
		return new PurRoomAttachmentEntryInfo();
	}

	protected IObjectValue createNewPayListData() {
		return new PurPayListEntryInfo();
	}

	protected IObjectValue createNewData() {
		
		PurchaseManageInfo info=new PurchaseManageInfo();
		info.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		info.setValuationType(CalcTypeEnum.PriceByTotalAmount);
//		info.setSalesman(SysContext.getSysContext().getCurrentUserInfo());
		try {
			info.setBizDate(FDCCommonServerHelper.getServerTimeStamp());
			info.setBusAdscriptionDate(FDCCommonServerHelper.getServerTimeStamp());
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		info.setIsValid(false);
		info.setIsFitmentToContract(false);
		info.setBookedDate(new Date());
		
		SellProjectInfo sellproject=(SellProjectInfo) this.getUIContext().get("sellProject");
		info.setSellProject(sellproject);
		
		RoomInfo room = (RoomInfo) this.getUIContext().get("room");
		try {
			verifyAddNewRoom(room,info.isIsBasePriceSell());
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		info.setRoom(room);
		setCRMFunction(info);
		
		if(this.getUIContext().get("srcId")!=null){
			info.setSrcId(BOSUuid.read(this.getUIContext().get("srcId").toString()));
			ObjectUuidPK srcpk=new ObjectUuidPK(info.getSrcId());
			try {
				IObjectValue objectValue=DynamicObjectFactory.getRemoteInstance().getValue(srcpk.getObjectType(),srcpk,SHEManageHelper.getBizSelectors(srcpk.getObjectType()));
				setInfoFromSrcInfo(objectValue,info);
				
				if(SellTypeEnum.PlanningSell.equals(info.getSellType())){
					info.setSellType(null);
				}
			} catch (BOSException e) {
				logger.error(e.getMessage());
			}
		}
		if(this.getUIContext().get("customer")!=null){
			List customer=(ArrayList)this.getUIContext().get("customer");
			setCustomerList(customer);
			setCustomerEntry(info,this.customer);
		}
		if(this.getUIContext().get("changeRoomCustomer")!=null){
			List customer=(ArrayList)this.getUIContext().get("changeRoomCustomer");
			for(int i=0;i<customer.size();i++){
				info.getPurCustomerEntry().add((PurCustomerEntryInfo)customer.get(i));
			}
			info.setCustomerNames((String)this.getUIContext().get("customerNames"));
			info.setCustomerPhone((String)this.getUIContext().get("customerPhone"));
		}
		if(this.getUIContext().get("changeSaleMan")!=null){
			List saleMan=(ArrayList)this.getUIContext().get("changeSaleMan");
			for(int i=0;i<saleMan.size();i++){
				info.getPurSaleManEntry().add((PurSaleManEntryInfo)saleMan.get(i));
			}
			info.setSaleManNames((String)this.getUIContext().get("saleManNames"));
		}
		if(this.getUIContext().get("commerce")!=null){
			CommerceChanceInfo commerce=(CommerceChanceInfo)this.getUIContext().get("commerce");
			info.setNewCommerceChance(commerce);
		}
//		if(info.getPurSaleManEntry().size()==0){
//			PurSaleManEntryInfo entry=new PurSaleManEntryInfo();
//			entry.setUser(SysContext.getSysContext().getCurrentUserInfo());
//			info.getPurSaleManEntry().add(entry);
//			info.setSaleManNames(SysContext.getSysContext().getCurrentUserInfo().getName());
//		}
		return info;
		
	}
	protected ICoreBase getBizInterface() throws BOSException {
		return PurchaseManageFactory.getRemoteInstance();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("bizState");
		sels.add("state");
		sels.add("orgUnit.*");
		sels.add("CU.*");
		sels.add("sellProject.*");
		sels.add("srcId");
		sels.add("isEarnestInHouseAmount");
		sels.add("isAutoToInteger");
		sels.add("isBasePriceSell");
		sels.add("toIntegerType");
		sels.add("digit");
		sels.add("priceToIntegerType");
		sels.add("priceDigit");
		sels.add("isValid");
		sels.add("transactionID");
		sels.add("customerNames");
		sels.add("bulidingArea");
		sels.add("roomArea");
		sels.add("strdPlanBuildingArea");
		sels.add("strdPlanRoomArea");
		sels.add("strdActualBuildingArea");
		sels.add("strdActualRoomArea");
		sels.add("saleManNames");
		sels.add("newCommerceChance");
		sels.add("newCommerceChance.customer.*");
		sels.add("purAmount");
		
		sels.add("purPayListEntry.*");
		sels.add("purPayListEntry.moneyDefine.*");
		sels.add("purPayListEntry.currency.*");
		
		sels.add("purCustomerEntry.*");
		sels.add("purCustomerEntry.customer.*");
		sels.add("purCustomerEntry.certificate.*");
		sels.add("purCustomerEntry.customer.mainCustomer.*");
		
		sels.add("purAgioEntry.*");
		sels.add("purAgioEntry.agio.*");
		
		sels.add("purRoomAttachmentEntry.*");
		sels.add("purRoomAttachmentEntry.room.*");
		sels.add("purRoomAttachmentEntry.purAgioEntry.*");
		sels.add("purRoomAttachmentEntry.purAgioEntry.agio.*");
		
		sels.add("purSaleManEntry.user.*");
		sels.add("purSaleManEntry.user.group.name");
		sels.add("purSaleManEntry.user.CU.name");
		sels.add("purSaleManEntry.*");
		
		sels.add("room.building.productType.*");
		
		return sels;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	
	protected TransactionStateEnum getBizStateAuditEnum() {
		return TransactionStateEnum.PURAUDIT;
	}

	protected TransactionStateEnum getBizStateInvalidEnum() {
		return TransactionStateEnum.PURNULLIFY;
	}

	protected TransactionStateEnum getBizStateSubmitEnum() {
		return TransactionStateEnum.PURAPPLE;
	}
	
	public void actionReceiveBill_actionPerformed(ActionEvent e) throws Exception {
		if(!(TransactionStateEnum.PURAPPLE.equals(editData.getBizState())||
				TransactionStateEnum.PURAUDIT.equals(editData.getBizState()))){
			FDCMsgBox.showWarning(this,"该单据业务状态不能进行收款操作！");
			SysUtil.abort();
		}
		Object[] custObjs=new Object[editData.getPurCustomerEntry().size()];
		for(int i=0;i<editData.getPurCustomerEntry().size();i++){
			custObjs[i]=editData.getPurCustomerEntry().get(i).getCustomer();
		}
		Set tranEntryIdSet = new HashSet();
		PurPayListEntryCollection signPayListColl = this.editData.getPurPayListEntry();
		for (int i = 0; i < signPayListColl.size(); i++) {
			PurPayListEntryInfo signPayEntryInfo = signPayListColl.get(i);
			if(signPayEntryInfo.getTanPayListEntryId()!=null)
				tranEntryIdSet.add(signPayEntryInfo.getTanPayListEntryId().toString());
		}			
		CRMClientHelper.openTheGatherRevBillWindow(this, null, editData.getSellProject(),editData ,custObjs,tranEntryIdSet);
		this.loadReceiveBill();
		this.updateActRevAmount((BaseTransactionInfo)editData);
	}
	public void actionCustomerSelect_actionPerformed(ActionEvent e) throws Exception {
		customerSelect(this,this.customer,editData.getSellProject(),true);
		this.storeCustomerEntry();
		this.loadCustomerEntry(editData);
	}
	protected boolean isExitPurMoneyDefine(){
		for(int i=0;i<this.tblPayList.getRowCount();i++){
			IRow row=this.tblPayList.getRow(i);
			if(this.purMoneyDefine.equals(row.getCell(PL_MONEYNAME).getValue())){
				return true;
			}
		}
		return false;
	}
	protected void f7Room_dataChanged(DataChangeEvent e) throws Exception {
		super.f7Room_dataChanged(e);
		if(!isExitPurMoneyDefine()){
			addPurMoneyDefine((RoomInfo)this.f7Room.getValue(),editData.getSellProject());
		}
	}
	private void addPurMoneyDefine(RoomInfo room,SellProjectInfo sellProject){
		if(room == null)return;
		if(sellProject == null)return;
		//加上定金,取售楼设置里的定金设置
//		RoomDisplaySetting setProduct = new RoomDisplaySetting(null,SHEParamConstant.PROJECT_PRODUCTTYPE_SET_MAP);
		Map projectProductTypeSet = RoomDisplaySetting.getNewProjectProductTypeSet(null,sellProject.getId().toString(),room.getBuilding().getProductType()!=null?room.getBuilding().getProductType().getId().toString():null);
		if(projectProductTypeSet!=null){
			this.txtPurAmount.setValue(projectProductTypeSet.get(SHEParamConstant.T1_PRE_STANDARD));
			//取售楼设置里的认购单默认签约时限设置约定签约日期
			int day =  ((Integer)projectProductTypeSet.get(SHEParamConstant.T1_TO_SIGN_LIMIT_TIME)).intValue();
			this.pkPlanSignDate.setValue(FDCDateHelper.addDays(new Date(),day));
			//在没有付款方案的时候，则在付款明细增加一条款项名称为定金的记录
			if(this.f7PayType.getValue()==null){
				PurPayListEntryInfo purPayListEntry = new PurPayListEntryInfo();
				purPayListEntry.setAppDate(new Date());
				purPayListEntry.setAppAmount((BigDecimal)projectProductTypeSet.get(SHEParamConstant.T1_PRE_STANDARD));
				purPayListEntry.setCurrency(currency);
				purPayListEntry.setMoneyDefine(this.purMoneyDefine);
				addPayListEntryRow(purPayListEntry);
			}
		}
	}
	protected void initControl() {
		super.initControl();
		this.txtRecommend.setEnabled(false);
		this.txtDes.setMaxLength(255);
	}
	
	protected void setTxtFormate(){
		super.setTxtFormate();
		this.txtPlanningCompensate.setPrecision(digit);
		this.txtPlanningCompensate.setRoundingMode(toIntegerType);
		
		this.txtCashSalesCompensate.setPrecision(digit);
		this.txtCashSalesCompensate.setRoundingMode(toIntegerType);
	}
	protected void initWorkButton() {
		super.initWorkButton();
        this.btnRelateSign.setIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
        this.btnRelatePrePurchase.setIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
        this.menuItemRelatePrePurchase.setIcon(EASResource.getIcon("imgTbtn_subjectsetting"));
        this.menuItemRelateSign.setIcon(EASResource.getIcon("imgTbtn_declarecollect"));
        this.btnRelateSign.setEnabled(true);
        this.btnRelatePrePurchase.setEnabled(true);
        this.menuItemRelatePrePurchase.setEnabled(true);
        this.menuItemRelateSign.setEnabled(true);
	}
	
	protected void verfiyOther(){
		FDCClientVerifyHelper.verifyEmpty(this, this.f7PayType);
		FDCClientVerifyHelper.verifyEmpty(this, this.pkPlanSignDate);
		
		if(((BaseTransactionInfo)editData).isIsBasePriceSell()){
			RoomInfo room = (RoomInfo) this.f7Room.getValue();
			if(room.getBaseStandardPrice()==null){
				FDCMsgBox.showWarning(this,"已启用强制底价控制参数，该房间底价不存在，请检查！");
				SysUtil.abort();
			}
			if(this.txtDealTotalAmount.getBigDecimalValue().setScale(digit, toIntegerType).compareTo(room.getBaseStandardPrice().setScale(digit, toIntegerType))<0){
				FDCMsgBox.showWarning(this,"成交总价不能低于房间底价！("+this.txtDealTotalAmount.getBigDecimalValue().setScale(digit, toIntegerType)+"<"+room.getBaseStandardPrice().setScale(digit, toIntegerType)+")");
				SysUtil.abort();
			}
		}
	}
	protected boolean veriftExistsSameRoomBill(RoomInfo room) throws EASBizException, BOSException{
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId()));
		filter.getFilterItems().add(new FilterItemInfo("bizState", TransactionStateEnum.PURAPPLE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("bizState", TransactionStateEnum.PURAUDIT_VALUE));
		
		if(editData!=null&&editData.getId()!=null){
			filter.getFilterItems().add(new FilterItemInfo("id", editData.getId(),CompareType.NOTEQUALS));
			filter.setMaskString("#0 and (#1 or #2) and #3");
		}else{
			filter.setMaskString("#0 and (#1 or #2)");
		}
		return getBizInterface().exists(filter);
	}
	
	protected void verifyAddNewRoom(RoomInfo room,boolean isIsBasePriceSell) throws BOSException, EASBizException {
		super.verifyAddNewRoom(room,isIsBasePriceSell); 
		if(room==null) return;
		
		if (veriftExistsSameRoomBill(room)) {
			setRoomNull("房间已经存在认购单据！");
		}
		IObjectValue objectValue=SHEManageHelper.getCurTransactionBill(room.getId());
		if(objectValue!=null&&(objectValue instanceof PrePurchaseManageInfo)){
			TransactionStateEnum curState=((BaseTransactionInfo)objectValue).getBizState();
			
			if(!TransactionStateEnum.PREAUDIT.equals(curState)){
				setRoomNull("该房间对应业务单据不是审批状态，不能进行转认购操作！");
			}
			if(editData!=null&&editData.getSrcId()==null){
				srcInfo=(BaseTransactionInfo)objectValue;
				editData.setSrcId(((BaseTransactionInfo)objectValue).getId());
				editData.setRoom(room);
				setInfoFromSrcInfo(objectValue,editData);
				this.loadFields();
			}
		}
		if(editData!=null&&((BaseTransactionInfo)editData).getSrcId()==null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("sellState");
			RoomSellStateEnum sellState=RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(room.getId()),sic).getSellState();
			if(!RoomSellStateEnum.OnShow.equals(sellState)&&!RoomSellStateEnum.Purchase.equals(sellState)&&!RoomSellStateEnum.SincerPurchase.equals(sellState)){
				setRoomNull("房间已经存在其他业务单据！");
			}
		}
	}
	
	protected void txtDealTotalAmount_dataChanged(DataChangeEvent e) throws Exception {
		if(isDealAmountEdit&&isEditDealAmount){
			RoomInfo roomInfo = (RoomInfo) this.f7Room.getValue();
			if(roomInfo==null) return;
			if(e.getOldValue().equals(e.getNewValue())) return;
			boolean isHasAgio=false;
			AgioEntryInfo agioEntryInfo=null;
			AgioEntryCollection agioCol=((AgioEntryCollection)this.txtAgio.getUserObject());
			
			AgioParam oldAgioParam=new AgioParam();
			oldAgioParam.setBasePriceSell(this.currAgioParam.isBasePriceSell());
			oldAgioParam.setDigit(this.currAgioParam.getDigit());
			oldAgioParam.setPriceAccountType(this.currAgioParam.getPriceAccountType());
			oldAgioParam.setToInteger(this.currAgioParam.isToInteger());
			oldAgioParam.setToIntegerType(this.currAgioParam.getToIntegerType());
			
			AgioEntryCollection oldAgioCol=new AgioEntryCollection();
			oldAgioParam.setAgios(oldAgioCol);
			
			for(int i=0;i<agioCol.size();i++){
				if(agioCol.get(i).getAgio()==null){
					isHasAgio=true;
					agioEntryInfo=agioCol.get(i);
				}else{
					oldAgioCol.add(agioCol.get(i));
				}
			}
			SellTypeEnum sellType = (SellTypeEnum)this.comboSellType.getSelectedItem();
			CalcTypeEnum valuationType = (CalcTypeEnum)this.comboValuationType.getSelectedItem();
			
			BigDecimal roomArea=this.txtRoomArea.getBigDecimalValue();
			BigDecimal buildingArea=this.txtBuildingArea.getBigDecimalValue();
			BigDecimal roomPrice=this.txtRoomPrice.getBigDecimalValue();
			BigDecimal buildingPrice=this.txtBuildingPrice.getBigDecimalValue();
			BigDecimal standardAmount=this.txtStandardTotalAmount.getBigDecimalValue();
			
			
			BigDecimal fitmentAmount = this.txtFitmentAmount.getBigDecimalValue();
			BigDecimal attachmentAmount =getMergeContractAttachmentTotalAmount(true);
			
			BigDecimal cashSalesCompensate=this.txtCashSalesCompensate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtCashSalesCompensate.getBigDecimalValue();
			BigDecimal planningCompensate=this.txtPlanningCompensate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtPlanningCompensate.getBigDecimalValue();
			
			BigDecimal areaCompensateAmount =cashSalesCompensate.add(planningCompensate);
			
			boolean isFitmentToContract = this.chkIsFitmentToContract.isSelected();
			SHEPayTypeInfo payType = (SHEPayTypeInfo) this.f7PayType.getValue();
			String payTypeName = null;
			BigDecimal payTypeAgio = FDCHelper.ONE_HUNDRED;
			if (payType != null) {
				payTypeName = payType.getName();
				payTypeAgio = payType.getAgio();
				if(payTypeAgio == null){
					payTypeAgio = FDCHelper.ONE_HUNDRED;
				}
			}
			PurchaseParam purParam = SHEManageHelper.getAgioParam(oldAgioParam, roomInfo, 
					 sellType,valuationType,isFitmentToContract,roomArea,buildingArea,roomPrice,buildingPrice,standardAmount,fitmentAmount, attachmentAmount, areaCompensateAmount ,SpecialAgioEnum.DaZhe, payTypeAgio,payTypeName);
			
			BigDecimal amount=purParam.getDealTotalAmount().subtract(this.txtDealTotalAmount.getBigDecimalValue());
			amount=SHEManageHelper.setScale(digit, toIntegerType,amount);
			if(!isHasAgio){
				if(!purParam.getDealTotalAmount().equals(this.txtDealTotalAmount.getBigDecimalValue())){
					PurAgioEntryInfo entryInfo = new PurAgioEntryInfo();
					entryInfo.setAmount(amount);
					entryInfo.setSeq(this.currAgioParam.getAgios().size()+1);
					this.currAgioParam.getAgios().add(entryInfo);
					this.txtAgio.setUserObject(this.currAgioParam.getAgios());
				}
			}else{
				if(!purParam.getDealTotalAmount().equals(this.txtDealTotalAmount.getBigDecimalValue().add(agioEntryInfo.getAmount()))){
					if(amount.compareTo(FDCHelper.ZERO)==0){
						this.currAgioParam.getAgios().remove(agioEntryInfo);
					}else{
						agioEntryInfo.setAmount(amount);
						agioEntryInfo.setSeq(this.currAgioParam.getAgios().size()+1);
					}
				}
			}
			PurchaseParam afterPurParam = SHEManageHelper.getAgioParam(this.currAgioParam, roomInfo, 
					 sellType,valuationType,isFitmentToContract,roomArea,buildingArea,roomPrice,buildingPrice,standardAmount,fitmentAmount, attachmentAmount, areaCompensateAmount ,SpecialAgioEnum.DaZhe, payTypeAgio,payTypeName);
			if(afterPurParam!=null) {
				this.txtAgioDes.setText(afterPurParam.getAgioDes());
				this.txtAgio.setValue(afterPurParam.getFinalAgio());
				this.txtContractTotalAmount.setValue(afterPurParam.getContractTotalAmount());
				this.txtContractBuildPrice.setValue(afterPurParam.getContractBuildPrice());
				this.txtContractRoomPrice.setValue(afterPurParam.getContractRoomPrice());
				this.txtDealBuildPrice.setValue(afterPurParam.getDealBuildPrice());
				this.txtDealRoomPrice.setValue(afterPurParam.getDealRoomPrice());
			}
			if(this.tblPayList.getRowCount()>0&&FDCMsgBox.showConfirm2(this, "成交总价已改变，是否重新生成付款明细？")== FDCMsgBox.YES){
				this.updatePayList();
			}
    	}
	}
	
	protected void getAttachRoom(RoomInfo room) throws EASBizException, BOSException{
		if(room==null) return;
		RoomAttachmentEntryCollection col=room.getAttachmentEntry();
		this.tblAttachProperty.removeRows();
		for(int i=0;i<col.size();i++){
			RoomInfo attRoom=RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(col.get(i).getRoom().getId()));
			
			verifyAddNewAttachRoom(attRoom,null);
			
			IRow row=this.tblAttachProperty.addRow();
			row.getCell(AP_ISMERGETOCONTRACT).setValue(new Boolean(false));
			row.setUserObject(this.createNewApData());
			
			row.getCell(AP_ROOMNUMBER).setValue(attRoom);
			row.getCell(AP_MERGEAMOUNT).setValue(attRoom.getStandardTotalAmount());
			updateAttachRoomInfo(attRoom,row);
			
			setEntryAgio(row,new PurAgioEntryCollection(),room);
		}
	}
	
	protected void tblAttachProperty_editStopped(KDTEditEvent e) throws Exception {
		IRow row = this.tblAttachProperty.getRow(e.getRowIndex());
		PurAgioEntryCollection purchaseCol=((PurRoomAttachmentEntryInfo)row.getUserObject()).getPurAgioEntry();
		
		if(e.getColIndex() == tblAttachProperty.getColumnIndex(AP_ROOMNUMBER)){
			if(e.getValue()!=null){
				RoomInfo room = (RoomInfo)row.getCell(AP_ROOMNUMBER).getValue();
				
				verifyAddNewAttachRoom(room,row);
				updateAttachRoomInfo(room,row);
				setEntryAgio(row,purchaseCol,room);
				updateAmount();
			}else{
				clearTblAttach(row);
			}
		}
		if(e.getColIndex() == tblAttachProperty.getColumnIndex(AP_AGIO)){
			if(e.getValue()==null){
				purchaseCol.clear();
			}else if(!e.getValue().equals(e.getOldValue())){
				Object[] obj=(Object[]) e.getValue();
				purchaseCol.clear();
				for (int i = 0; i < obj.length; i++) {
					PurAgioEntryInfo entryInfo = (PurAgioEntryInfo)obj[i];
					purchaseCol.add(entryInfo);
				}
				setEntryAgio(row,purchaseCol,(RoomInfo)row.getCell(AP_ROOMNUMBER).getValue());
				updateAmount();
			}
		}
		if(e.getColIndex() == tblAttachProperty.getColumnIndex(AP_ISMERGETOCONTRACT)||e.getColIndex()==tblAttachProperty.getColumnIndex(AP_MERGEAMOUNT)){
			updateAmount();
		}
	}
	protected void updatePayListByPayType() {
		try {
			boolean isAddFittment=true;
			boolean isAddRoomAttach=true;
			if(!this.chkIsFitmentToContract.isSelected()&&this.txtFitmentAmount.getBigDecimalValue()!=null&&this.txtFitmentAmount.getBigDecimalValue().compareTo(FDCHelper.ZERO)>0){
				isAddFittment=false;
			}
			BigDecimal attachmentTotalAmount=getMergeContractAttachmentTotalAmount(false);
			
			if(attachmentTotalAmount.compareTo(FDCHelper.ZERO)>0){
				isAddRoomAttach=false;
			}
			IRow fittment=null;
			IRow roomAttach=null;
			if(!isAddFittment){
				fittment=getFittmentMoneyDefineRow();
			}
			if(!isAddRoomAttach){
				roomAttach=getRoomAttactMoneyDefineRow();
			}
			List toAddRowEntry = SHEManageHelper.updatePayListByPayType((SHEPayTypeInfo) this.f7PayType.getValue(), this.editData.isIsEarnestInHouseAmount(), this.tblPayList, fittment, roomAttach, purMoneyDefine, 
					this.txtContractTotalAmount.getBigDecimalValue(), this.txtDealTotalAmount.getBigDecimalValue(),this.txtStandardTotalAmount.getBigDecimalValue()
					, this.txtBuildingArea.getBigDecimalValue(), this.txtRoomArea.getBigDecimalValue(),(RoomInfo) this.f7Room.getValue(), digit, priceToIntegerType,(Date)this.pkBizDate.getValue(),SHEPayTypeBizTimeEnum.PURCHASE,isAddFittment,isAddRoomAttach);
			if ( this.f7PayType.getValue() != null) {
				int rowCount=0;
				for (int i = 0; i < toAddRowEntry.size(); i++) {
					IRow row =null;
					if(i==0){
						row = this.tblPayList.addRow(0);
					}else{
						row = this.tblPayList.addRow(rowCount+1);
					}
					PurPayListEntryInfo entry=new PurPayListEntryInfo();
					SHEManageHelper.setPayListEntry(entry, (TranPayListEntryInfo) toAddRowEntry.get(i),true);
					row.setUserObject(entry);
					row.getCell(PL_MONEYNAME).setValue(entry.getMoneyDefine());
					row.getCell(PL_APPDATE).setValue(entry.getAppDate());
					row.getCell(PL_APPAMOUNT).setValue(entry.getAppAmount());
					row.getCell(PL_BALANCE).setValue(entry.getAppAmount());
					row.getCell(PL_CURRENCY).setValue(entry.getCurrency());
					row.getCell(PL_STATE).setValue(new Boolean(false));
					
					rowCount=row.getRowIndex();
				}
				updateLoanAndAFAmount();
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
	
	private void addPayListEntryRow(TranPayListEntryInfo entry) {
		IRow row = this.tblPayList.addRow();
		row.setUserObject(entry);
		row.getCell(PL_MONEYNAME).setValue(entry.getMoneyDefine());
		row.getCell(PL_APPDATE).setValue(entry.getAppDate());
		row.getCell(PL_APPAMOUNT).setValue(entry.getAppAmount());
		row.getCell(PL_CURRENCY).setValue(entry.getCurrency());
		row.getCell(PL_DES).setValue(entry.getDescription());
		
		BigDecimal actAmount = FDCHelper.ZERO;
		
		if(TransactionStateEnum.PURAPPLE.equals(editData.getBizState())||TransactionStateEnum.PURAUDIT.equals(editData.getBizState())){
			actAmount=SHEManageHelper.getActRevAmount(null,entry.getTanPayListEntryId());
			row.getCell(PL_ACTAMOUNT).setValue(actAmount);
		}else{
			actAmount=entry.getActRevAmount();
			row.getCell(PL_ACTAMOUNT).setValue(actAmount);
		}
		BigDecimal apAmount = entry.getAppAmount()==null?FDCHelper.ZERO:entry.getAppAmount();
		
		if (actAmount.compareTo(FDCHelper.ZERO) != 0) {
			if (actAmount.compareTo(apAmount) >= 0) {
				row.getCell(PL_STATE).setValue(new Boolean(true));
			}else{
				row.getCell(PL_STATE).setValue(new Boolean(false));
			}
		}else{
			row.getCell(PL_STATE).setValue(new Boolean(false));
		}
		row.getCell(PL_BALANCE).setValue(apAmount.subtract(actAmount).compareTo(FDCHelper.ZERO)<0?FDCHelper.ZERO:apAmount.subtract(actAmount));
	}
	public void actionRelatePrePurchase_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getSrcId()!=null){
			ObjectUuidPK pk=new ObjectUuidPK(editData.getSrcId());
			IObjectValue objectValue=DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(),pk);
			if(objectValue instanceof PrePurchaseManageInfo){
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID", editData.getSrcId());
		        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		        IUIWindow uiWindow = uiFactory.create(PrePurchaseManageEditUI.class.getName(), uiContext,null,OprtState.VIEW);
		        uiWindow.show();
		        return;
			}
		}
		FDCMsgBox.showWarning(this,"无关联预定单据！");
	}

	public void actionRelateSign_actionPerformed(ActionEvent e) throws Exception {
		if(editData!=null&&editData.getId()!=null){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("srcId",editData.getId()));
			view.setFilter(filter);
			SignManageCollection  col= SignManageFactory.getRemoteInstance().getSignManageCollection(view);
			if(col.get(0)!=null){
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID", col.get(0).getId().toString());
		        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		        IUIWindow uiWindow = uiFactory.create(SignManageEditUI.class.getName(), uiContext,null,OprtState.VIEW);
		        uiWindow.show();
		        return;
			}
		}
		FDCMsgBox.showWarning(this,"无关联 签约单据！");
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		if(this.getUIContext()!=null){
			if (Boolean.TRUE.equals(this.getUIContext().get(KEY_DESTORY_WINDOW))) {
				destroyWindow();
			}
			this.getUIContext().put(KEY_SUBMITTED_DATA, Boolean.TRUE);
		}
	}
	
	protected String getTDFileName() {
		return "/bim/fdc/sellhouse/PurchaseManage";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.PurchaseManagePrintQuery");
		
	}
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
			"cantPrint"));
			return;
		}
		HashMap value=new HashMap();
		value.put("roomArea", this.txtRoomArea.getBigDecimalValue());
		value.put("buildingArea", this.txtBuildingArea.getBigDecimalValue());
		BigDecimal purAmount=FDCHelper.ZERO;
		for (int i = 0; i < this.tblPayList.getRowCount(); i++) {
			IRow row = this.tblPayList.getRow(i);
			BigDecimal amount = (BigDecimal)row.getCell(PL_APPAMOUNT).getValue();
			MoneyDefineInfo moneyName = (MoneyDefineInfo) row.getCell(PL_MONEYNAME).getValue();
			if (moneyName != null&&moneyName.getMoneyType().equals(MoneyTypeEnum.EarnestMoney)&&amount!=null) {
				purAmount=purAmount.add(amount);
			} 
		}
		value.put("purAmount", purAmount);
		if(this.customer.size()>0){
			for(int i=0;i<this.customer.size();i++){
				if(this.customer.get(i)!=null&&((TranCustomerEntryInfo)this.customer.get(i)).isIsMain()){
					value.put("mainAddress", ((TranCustomerEntryInfo)this.customer.get(i)).getAddress());
					value.put("mainPhone", ((TranCustomerEntryInfo)this.customer.get(i)).getPhone());
					value.put("mainTel", ((TranCustomerEntryInfo)this.customer.get(i)).getTel());
					value.put("mainPostalCode", ((TranCustomerEntryInfo)this.customer.get(i)).getCustomer().getPostalCode());
				}
			}
		}
		Map projectProductTypeSet = RoomDisplaySetting.getNewProjectProductTypeSet(null,editData.getSellProject().getId().toString(),editData.getRoom().getBuilding().getProductType()!=null?editData.getRoom().getBuilding().getProductType().getId().toString():null);
		if(projectProductTypeSet!=null){
			int day =  ((Integer)projectProductTypeSet.get(SHEParamConstant.T1_TO_SIGN_LIMIT_TIME)).intValue();
			value.put("signLimitDay", Integer.valueOf(day));
		}
		PurchaseManagePrintDataProvider data = new PurchaseManagePrintDataProvider(
				editData.getString("id"), getTDQueryPK(),value);
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
			"cantPrint"));
			return;

		}
		HashMap value=new HashMap();
		value.put("roomArea", this.txtRoomArea.getBigDecimalValue());
		value.put("buildingArea", this.txtBuildingArea.getBigDecimalValue());
		BigDecimal purAmount=FDCHelper.ZERO;
		for (int i = 0; i < this.tblPayList.getRowCount(); i++) {
			IRow row = this.tblPayList.getRow(i);
			BigDecimal amount = (BigDecimal)row.getCell(PL_APPAMOUNT).getValue();
			MoneyDefineInfo moneyName = (MoneyDefineInfo) row.getCell(PL_MONEYNAME).getValue();
			if (moneyName != null&&moneyName.getMoneyType().equals(MoneyTypeEnum.EarnestMoney)&&amount!=null) {
				purAmount=purAmount.add(amount);
			} 
		}
		value.put("purAmount", purAmount);
		if(this.customer.size()>0){
			for(int i=0;i<this.customer.size();i++){
				if(this.customer.get(i)!=null&&((TranCustomerEntryInfo)this.customer.get(i)).isIsMain()){
					value.put("mainAddress", ((TranCustomerEntryInfo)this.customer.get(i)).getAddress());
					value.put("mainPhone", ((TranCustomerEntryInfo)this.customer.get(i)).getPhone());
					value.put("mainTel", ((TranCustomerEntryInfo)this.customer.get(i)).getTel());
					value.put("mainPostalCode", ((TranCustomerEntryInfo)this.customer.get(i)).getCustomer().getPostalCode());
				}
			}
		}
		Map projectProductTypeSet = RoomDisplaySetting.getNewProjectProductTypeSet(null,editData.getSellProject().getId().toString(),editData.getRoom().getBuilding().getProductType()!=null?editData.getRoom().getBuilding().getProductType().getId().toString():null);
		if(projectProductTypeSet!=null){
			int day =  ((Integer)projectProductTypeSet.get(SHEParamConstant.T1_TO_SIGN_LIMIT_TIME)).intValue();
			value.put("signLimitDay", Integer.valueOf(day));
		}
		PurchaseManagePrintDataProvider data = new PurchaseManagePrintDataProvider(
				editData.getString("id"), getTDQueryPK(),value);
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}
}