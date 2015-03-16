/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.OrgType;
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
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AfPreEnum;
import com.kingdee.eas.fdc.sellhouse.AgioBillFactory;
import com.kingdee.eas.fdc.sellhouse.AgioBillInfo;
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
import com.kingdee.eas.fdc.sellhouse.ChangeCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceChangeNewStatusEnum;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.IPrePurchaseManage;
import com.kingdee.eas.fdc.sellhouse.LoanPreEnum;
import com.kingdee.eas.fdc.sellhouse.MarketUnitControlFactory;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberFactory;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitMemberInfo;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitSellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitSellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefine;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseAgioEntry;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseAgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseRoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseRoomAttachmentEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ProjectProductTypeSet;
import com.kingdee.eas.fdc.sellhouse.ProjectSet;
import com.kingdee.eas.fdc.sellhouse.PurAgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurRoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurSaleManEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEParamConstant;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeBizTimeEnum;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PrePurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignAgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageCollection;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo;
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
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;
import com.kingdee.eas.fdc.sellhouse.AgioParam;
/**
 * output class name
 */
public class PrePurchaseManageEditUI extends AbstractPrePurchaseManageEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(PrePurchaseManageEditUI.class);
    protected MoneyDefineInfo preMoneyDefine=SHEManageHelper.getPreMoneyDefine();
    protected BigDecimal preAmount =null;
    public static final String KEY_SUBMITTED_DATA = "submittedData";
	public static final String KEY_DESTORY_WINDOW = "destoryWindowAfterSubmit";
    /**
     * output class constructor
     */
    public PrePurchaseManageEditUI() throws Exception
    {
        super();
    }
/**
 * @author xiaominWang
 */
    public void onLoad() throws Exception {
    	super.onLoad();
//    	initTblPayList();
//    	if(this.oprtState.equals(OprtState.ADDNEW)){
//    		//TODO xiaominWang
//        	this.tblPayList.addRow();
////        	MoneyDefineInfo mdInfo=new MoneyDefineInfo();
////        	mdInfo.setMoneyType(MoneyTypeEnum.PreconcertMoney);
//        	this.tblPayList.getRow(0).getCell("moneyName").setValue(preMoneyDefine);
//        	this.tblPayList.getRow(0).getCell("appAmount").setValue(preMoneyDefine.getDescription());
//        	this.tblPayList.getRow(0).getCell("currency").setValue(preMoneyDefine.getDisplayName());
//         	this.tblPayList.getRow(0).getCell("des").setValue(preMoneyDefine.getDescription());
////        	this.tblPayList.getRow(0).getCell("appAmount").setValue(preMoneyDefine.getDescription());
////        	this.tblPayList.getRow(0).getCell("appAmount").setValue(preMoneyDefine.getDescription());
////        	PrePurchasePayListEntryInfo pppleInfo=new PrePurchasePayListEntryInfo();
////        	pppleInfo.setMoneyDefine(preMoneyDefine);
////        	pppleInfo.getMoneyDefine().setMoneyType(MoneyTypeEnum.PreconcertMoney);
////        	pppleInfo.setAppAmount(new BigDecimal(10000));
////        	this.tblPayList.getRow(0).setUserObject(pppleInfo);
//        	//____________________________
//    	}
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

    
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		storeCommon();
		storeBizReview();
		storePayList();
		storeAttachmentEntry();
        super.storeFields();
        setTxtFormateNumerValue();
    }
    protected IObjectValue createNewData() {
    	
    	
    	PrePurchaseManageInfo info=new PrePurchaseManageInfo();
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
		
		SellProjectInfo sellproject=(SellProjectInfo) this.getUIContext().get("sellProject");
		info.setSellProject(sellproject);
		
		RoomInfo room = (RoomInfo) this.getUIContext().get("room");
		try {
			if(room!=null){
				verifyAddNewRoom(room,info.isIsBasePriceSell());
			}
		} catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		info.setRoom(room);
		setCRMFunction(info);
		
		if(this.getUIContext().get("srcId")!=null){
			info.setSrcId(BOSUuid.read(this.getUIContext().get("srcId").toString()));
			ObjectUuidPK srcpk=new ObjectUuidPK(info.getSrcId());
			try {
				IObjectValue objectValue=DynamicObjectFactory.getRemoteInstance().getValue(srcpk.getObjectType(),srcpk,SHEManageHelper.getBizSelectors(srcpk.getObjectType()));
				info.getPrePurchaseCustomerEntry().clear();
				info.getPrePurchasePayListEntry().clear();
				
				if(objectValue instanceof SincerityPurchaseInfo){
					SincerityPurchaseInfo sincerityPurchaseInfo=(SincerityPurchaseInfo)objectValue;
					SincerityPurchaseCustomerEntryCollection customerCol=sincerityPurchaseInfo.getCustomer();
					SinPurSaleMansEntryCollection saleCol=sincerityPurchaseInfo.getSaleMansEntry();
					
					for(int i=0;i<customerCol.size();i++){
						PrePurchaseCustomerEntryInfo entry=new PrePurchaseCustomerEntryInfo();
						SHEManageHelper.setCustomerListEntry(entry,customerCol.get(i));
						
						info.getPrePurchaseCustomerEntry().add(entry);
					}
					info.setSellProject(sincerityPurchaseInfo.getSellProject());
					info.setCustomerNames(sincerityPurchaseInfo.getCustomerNames());
					info.setCustomerPhone(sincerityPurchaseInfo.getCustomerPhone());
					
//					SincerReceiveEntryCollection payCol=sincerityPurchaseInfo.getSincerPriceEntrys();
//					CRMHelper.sortCollection(payCol, "seq", true);
//					for(int i=0;i<payCol.size();i++){
//						PrePurchasePayListEntryInfo entry=new PrePurchasePayListEntryInfo();
//						SHEManageHelper.setPayListEntry(null,entry,payCol.get(i));
//						info.getPrePurchasePayListEntry().add(entry);
//					}
					info.setNewCommerceChance(sincerityPurchaseInfo.getNewCommerceChance());
					info.setTransactionID(sincerityPurchaseInfo.getTransactionID());
					
					for(int i=0;i<saleCol.size();i++){
						PrePurchaseSaleManEntryInfo entry=new PrePurchaseSaleManEntryInfo();
						SHEManageHelper.setSaleManEntry(entry,saleCol.get(i));
						
						info.getPrePurchaseSaleManEntry().add(entry);
					}
					info.setSaleManNames(sincerityPurchaseInfo.getSaleManStr());
				}else if(objectValue instanceof ChooseRoomInfo){
					if(((ChooseRoomInfo)objectValue).getSalesMan()!=null){
						PrePurchaseSaleManEntryInfo entry=new PrePurchaseSaleManEntryInfo();
						entry.setUser(((ChooseRoomInfo)objectValue).getSalesMan());
						info.getPrePurchaseSaleManEntry().add(entry);
						info.setSaleManNames(((ChooseRoomInfo)objectValue).getSalesMan().getName());
					}
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
				info.getPrePurchaseCustomerEntry().add((PrePurchaseCustomerEntryInfo)customer.get(i));
			}
			info.setCustomerNames((String)this.getUIContext().get("customerNames"));
			info.setCustomerPhone((String)this.getUIContext().get("customerPhone"));
		}
		if(this.getUIContext().get("changeSaleMan")!=null){
			List saleMan=(ArrayList)this.getUIContext().get("changeSaleMan");
			for(int i=0;i<saleMan.size();i++){
				info.getPrePurchaseSaleManEntry().add((PrePurchaseSaleManEntryInfo)saleMan.get(i));
			}
			info.setSaleManNames((String)this.getUIContext().get("saleManNames"));
		}
//		if(info.getPrePurchaseSaleManEntry().size()==0){
//			PrePurchaseSaleManEntryInfo entry=new PrePurchaseSaleManEntryInfo();
//			entry.setUser(SysContext.getSysContext().getCurrentUserInfo());
//			info.getPrePurchaseSaleManEntry().add(entry);
//			info.setSaleManNames(SysContext.getSysContext().getCurrentUserInfo().getName());
//		}
		return info;
	}
    protected IObjectValue createNewDetailData(KDTable table) {
		return super.createNewDetailData(table);
	}

	protected IObjectValue createNewApData() {
		return new PrePurchaseRoomAttachmentEntryInfo();
	}

	protected IObjectValue createNewPayListData() {
		return new PrePurchasePayListEntryInfo();
	}
	protected ICoreBase getBizInterface() throws BOSException {
		return PrePurchaseManageFactory.getRemoteInstance();
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
		
		sels.add("prePurchasePayListEntry.*");
		sels.add("prePurchasePayListEntry.moneyDefine.*");
		sels.add("prePurchasePayListEntry.currency.*");
		
		sels.add("prePurchaseCustomerEntry.*");
		sels.add("prePurchaseCustomerEntry.customer.*");
		sels.add("prePurchaseCustomerEntry.certificate.*");
		sels.add("prePurchaseCustomerEntry.customer.mainCustomer.*");
		
		sels.add("prePurchaseAgioEntry.*");
		sels.add("prePurchaseAgioEntry.agio.*");
		
		sels.add("prePurchaseRoomAttachmentEntry.*");
		sels.add("prePurchaseRoomAttachmentEntry.room.*");
		sels.add("prePurchaseRoomAttachmentEntry.prePurchaseAgioEntry.*");
		sels.add("prePurchaseRoomAttachmentEntry.prePurchaseAgioEntry.agio.*");
		
		sels.add("prePurchaseSaleManEntry.*");
		sels.add("prePurchaseSaleManEntry.user.*");
		sels.add("prePurchaseSaleManEntry.user.group.name");
		sels.add("prePurchaseSaleManEntry.user.CU.name");
	
		sels.add("room.building.productType.*");
		return sels;
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
		loadPayList(editData);//TODO
		loadAttachmentEntry(editData);
		loadBizReview();
		
		if(STATUS_ADDNEW.equals(this.getOprtState())&&((BaseTransactionInfo)editData).getRoom()!=null){
			if(srcInfo!=null&&!(srcInfo instanceof PrePurchaseManageInfo)&&!(srcInfo instanceof PurchaseManageInfo)){
				updateRoomInfo();
			}else if(srcInfo==null){
				updateRoomInfo();
			}
		}
		if(STATUS_ADDNEW.equals(this.oprtState)){
			addPreMoneyDefine(editData.getRoom(),editData.getSellProject());
		}
		if(STATUS_ADDNEW.equals(this.getOprtState())&&editData.getRoom()!=null){
			if(SellTypeEnum.PlanningSell.equals(editData.getRoom().getSellType())&&editData.getRoom().isIsPlan()){
				this.comboSellType.setSelectedItem(SellTypeEnum.PlanningSell);
			}else if(SellTypeEnum.PreSell.equals(editData.getRoom().getSellType())&&editData.getRoom().isIsPre()){
				this.comboSellType.setSelectedItem(SellTypeEnum.PreSell);
			}else if(SellTypeEnum.LocaleSell.equals(editData.getRoom().getSellType())&&editData.getRoom().isIsActualAreaAudited()){
				this.comboSellType.setSelectedItem(SellTypeEnum.LocaleSell);
			}else if(editData.getRoom().isIsPlan()){
				this.comboSellType.setSelectedItem(SellTypeEnum.PlanningSell);
			}else if(editData.getRoom().isIsPre()){
				this.comboSellType.setSelectedItem(SellTypeEnum.PreSell);
			}else if(editData.getRoom().isIsActualAreaAudited()){
				this.comboSellType.setSelectedItem(SellTypeEnum.LocaleSell);
			}else{
				setSellTypeNull("房间没有面积复核！");
			}
//			if(srcInfo==null||srcInfo instanceof SincerityPurchaseInfo){
				this.updateRoomArea();
				this.updateRoomInfo();
				this.updataRoomContractAndDealAmount();
//			}
		}
		attachListeners();
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
					 sellType,valuationType,isFitmentToContract,roomArea,buildingArea,roomPrice,buildingPrice,standardAmount,fitmentAmount, attachmentAmount, null ,SpecialAgioEnum.DaZhe, payTypeAgio,payTypeName);
			
			BigDecimal amount=purParam.getDealTotalAmount().subtract(this.txtDealTotalAmount.getBigDecimalValue());
			amount=SHEManageHelper.setScale(digit, toIntegerType,amount);
			if(!isHasAgio){
				if(!purParam.getDealTotalAmount().equals(this.txtDealTotalAmount.getBigDecimalValue())){
					PrePurchaseAgioEntryInfo entryInfo = new PrePurchaseAgioEntryInfo();
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
					 sellType,valuationType,isFitmentToContract,roomArea,buildingArea,roomPrice,buildingPrice,standardAmount,fitmentAmount, attachmentAmount, null ,SpecialAgioEnum.DaZhe, payTypeAgio,payTypeName);
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
	protected void tblAttachProperty_editStopped(KDTEditEvent e) throws Exception {
		IRow row = this.tblAttachProperty.getRow(e.getRowIndex());
		PrePurchaseAgioEntryCollection signCol=((PrePurchaseRoomAttachmentEntryInfo)row.getUserObject()).getPrePurchaseAgioEntry();
		
		if(e.getColIndex() == tblAttachProperty.getColumnIndex(AP_ROOMNUMBER)){
			if(e.getValue()!=null){
				RoomInfo room = (RoomInfo)row.getCell(AP_ROOMNUMBER).getValue();
				verifyAddNewAttachRoom(room,row);
				updateAttachRoomInfo(room,row);
				setEntryAgio(row,signCol,room);
				
				this.updateAmount();
			}else{
				clearTblAttach(row);
			}
		}
		if(e.getColIndex() == tblAttachProperty.getColumnIndex(AP_AGIO)){
			if(e.getValue()==null){
				signCol.clear();
			}else if(!e.getValue().equals(e.getOldValue())){
				Object[] obj=(Object[]) e.getValue();
				signCol.clear();
				for (int i = 0; i < obj.length; i++) {
					PrePurchaseAgioEntryInfo entryInfo = (PrePurchaseAgioEntryInfo)obj[i];
					signCol.add(entryInfo);
				}
				setEntryAgio(row,signCol,(RoomInfo)row.getCell(AP_ROOMNUMBER).getValue());
				
				this.updateAmount();
			}
		}
		if(e.getColIndex() == tblAttachProperty.getColumnIndex(AP_ISMERGETOCONTRACT)||e.getColIndex()==tblAttachProperty.getColumnIndex(AP_MERGEAMOUNT)){
			updateAmount();
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
			
			setEntryAgio(row,new PrePurchaseAgioEntryCollection(),room);
		}
	}
	protected void updateAmount(){
		isEditDealAmount=false;
		super.updateAmount();
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
				 sellType,valuationType,isFitmentToContract,roomArea,buildingArea,roomPrice,buildingPrice,standardAmount,fitmentAmount, attachmentAmount, null ,SpecialAgioEnum.DaZhe, payTypeAgio,payTypeName);
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
			List toAddRowEntry = SHEManageHelper.updatePayListByPayType((SHEPayTypeInfo) this.f7PayType.getValue(), this.editData.isIsEarnestInHouseAmount(), this.tblPayList,fittment , roomAttach, preMoneyDefine, 
					this.txtContractTotalAmount.getBigDecimalValue(), this.txtDealTotalAmount.getBigDecimalValue(),this.txtStandardTotalAmount.getBigDecimalValue()
					, this.txtBuildingArea.getBigDecimalValue(), this.txtRoomArea.getBigDecimalValue(),(RoomInfo) this.f7Room.getValue(), digit, priceToIntegerType,(Date)this.pkBizDate.getValue(),SHEPayTypeBizTimeEnum.PREREGISTER,isAddFittment,isAddRoomAttach);
			if ( this.f7PayType.getValue() != null) {
				int rowCount=0;
				for (int i = 0; i < toAddRowEntry.size(); i++) {
					IRow row =null;
					if(i==0){
						row = this.tblPayList.addRow(0);
					}else{
						row = this.tblPayList.addRow(rowCount+1);
					}
					PrePurchasePayListEntryInfo entry=new PrePurchasePayListEntryInfo();
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
	protected void loadAttachmentEntry(PrePurchaseManageInfo info){
		PrePurchaseRoomAttachmentEntryCollection entrys = info.getPrePurchaseRoomAttachmentEntry();
		this.tblAttachProperty.removeRows();
		for(int i=0;i<entrys.size();i++) {
			PrePurchaseRoomAttachmentEntryInfo entry = entrys.get(i);
			IRow row = this.tblAttachProperty.addRow();
			row.setUserObject(entry);
			row.getCell(AP_ROOMNUMBER).setValue(entry.getRoom());
			row.getCell(AP_BUILDINGAREA).setValue(entry.getBuildingArea());
			row.getCell(AP_ROOMAREA).setValue(entry.getRoomArea());
			row.getCell(AP_STANDARDTOTALAMOUNT).setValue(entry.getStandardTotalAmount());
			row.getCell(AP_BUILDINGPRICE).setValue(entry.getBuildingPrice());
			row.getCell(AP_ROOMPRICE).setValue(entry.getRoomPrice());
			row.getCell(AP_AGIO).setValue(entry.getPrePurchaseAgioEntry().toArray());
			PrePurchaseAgioEntryCollection signCol=((PrePurchaseRoomAttachmentEntryInfo)row.getUserObject()).getPrePurchaseAgioEntry();
			try {
				setEntryAgio(row,signCol,entry.getRoom());
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
	private void addPayListEntryRow(TranPayListEntryInfo entry) {
		IRow row = this.tblPayList.addRow();
		row.setUserObject(entry);
		row.getCell(PL_MONEYNAME).setValue(entry.getMoneyDefine());
		row.getCell(PL_APPDATE).setValue(entry.getAppDate());
		row.getCell(PL_APPAMOUNT).setValue(entry.getAppAmount());
		row.getCell(PL_CURRENCY).setValue(entry.getCurrency());
		row.getCell(PL_DES).setValue(entry.getDescription());
		
		BigDecimal actAmount = FDCHelper.ZERO;
		
		if(TransactionStateEnum.PREAPPLE.equals(editData.getBizState())||TransactionStateEnum.PREAUDIT.equals(editData.getBizState())){
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
		for(int i=0;i<editData.getPrePurchaseAgioEntry().size();i++)
			agioEntryColl.add(editData.getPrePurchaseAgioEntry().get(i));
		this.txtAgio.setUserObject(agioEntryColl);
		currAgioParam.setAgios(agioEntryColl);
		//TODO  判断登录人员，赋“置业顾问”初始值
		this.saleMan.clear();
		
		for(int i=0;i<editData.getPrePurchaseSaleManEntry().size();i++){//his
			this.saleMan.add(editData.getPrePurchaseSaleManEntry().get(i));
		}
		this.f7Seller.setValue(this.saleMan.toArray());//
//		authentication();//TODO  如果是营销成员，则“置业顾问”不可编辑(修改)
	}
	protected void loadCustomerEntry(PrePurchaseManageInfo info){
		PrePurchaseCustomerEntryCollection customerCol=info.getPrePurchaseCustomerEntry();
		customer=new ArrayList();
		for(int i=0;i<customerCol.size();i++){
			this.customer.add(customerCol.get(i));
		}
		SHEManageHelper.loadCustomer(labelCustomer1, labelCustomer2, labelCustomer3,labelCustomer4,labelCustomer5, customer, txtPhoneNumber, info);
	}
	protected void loadPayList(PrePurchaseManageInfo info) {
		PrePurchasePayListEntryCollection payEntrys = info.getPrePurchasePayListEntry();
		CRMHelper.sortCollection(payEntrys, "appDate", true);
		this.tblPayList.removeRows();
		for(int i=0;i<payEntrys.size();i++) {
			PrePurchasePayListEntryInfo entry = payEntrys.get(i);
			addPayListEntryRow(entry);
		}
	}
	protected void setEntryAgio(IRow row,PrePurchaseAgioEntryCollection signCol,RoomInfo room) throws UIException{
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
		for (int i = 0; i < signCol.size(); i++) {
			PrePurchaseAgioEntryInfo entryInfo = signCol.get(i);
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
			
		if(signCol.size()>0){
			PurchaseParam purParam = SHEManageHelper.getPurchaseAgioParam(currEntryAgioParam, room, 
					room.getSellType(), false, null, null, null ,null, null,null);
			row.getCell(AP_MERGEAMOUNT).setValue(purParam.getDealTotalAmount());
		}else{
			row.getCell(AP_MERGEAMOUNT).setValue(row.getCell(AP_STANDARDTOTALAMOUNT).getValue());
		}
	}
	protected void storeBizReview() {
		
	}

	protected void storeCommon() {
		AgioEntryCollection agioEntrys = (AgioEntryCollection)this.txtAgio.getUserObject();
		editData.getPrePurchaseAgioEntry().clear();
		for (int i = 0; i < agioEntrys.size(); i++) {
			PrePurchaseAgioEntryInfo entryInfo = (PrePurchaseAgioEntryInfo)agioEntrys.get(i);
			editData.getPrePurchaseAgioEntry().add(entryInfo);
		}
		
		editData.getPrePurchaseSaleManEntry().clear();
		String saleManNames="";
		for(int i=0;i<this.saleMan.size();i++){
			if(i==this.saleMan.size()-1){
				saleManNames=saleManNames+((TranSaleManEntryInfo)this.saleMan.get(i)).getUser().getName();
			}else{
				saleManNames=saleManNames+((TranSaleManEntryInfo)this.saleMan.get(i)).getUser().getName()+";";
			}
			PrePurchaseSaleManEntryInfo entry=new PrePurchaseSaleManEntryInfo();
			SHEManageHelper.setSaleManEntry(entry,(TranSaleManEntryInfo)this.saleMan.get(i));
			editData.getPrePurchaseSaleManEntry().add(entry);
		}
		editData.setSaleManNames(saleManNames);
	}
	private void setCustomerEntry(PrePurchaseManageInfo sign,List customerList){
		String customerNames="";
		String customerPhone="";
		String recommendeds="";
		PrePurchaseSaleManEntryInfo entry=new PrePurchaseSaleManEntryInfo();
		for(int i=0;i<customerList.size();i++){
			SHECustomerInfo sheCI = new SHECustomerInfo();
			PrePurchaseCustomerEntryInfo info =new PrePurchaseCustomerEntryInfo();
			TranCustomerEntryInfo tranInfo=(TranCustomerEntryInfo)customerList.get(i);
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
			sign.getPrePurchaseCustomerEntry().add(info);
			entry.setUser(info.getCustomer().getPropertyConsultant());
		}
		sign.setCustomerNames(customerNames);
		sign.setCustomerPhone(customerPhone);
		txtRecommended.setText(recommendeds);
		
		if(this.saleMan.size()==0&&entry.getUser()!=null){
			this.saleMan.add(entry);
			this.f7Seller.setValue(this.saleMan.toArray());
		}
	}
	protected void storeCustomerEntry() {
		editData.getPrePurchaseCustomerEntry().clear();
		setCustomerEntry(editData,this.customer);
	}
	protected void storePayList() {
		PrePurchaseManageInfo info = this.editData;
		info.getPrePurchasePayListEntry().clear();
		for (int i = 0; i < this.tblPayList.getRowCount(); i++) {
			IRow row = this.tblPayList.getRow(i);
			PrePurchasePayListEntryInfo entry = (PrePurchasePayListEntryInfo) row.getUserObject();
			entry.setSeq(i);
			MoneyDefineInfo moneyName = (MoneyDefineInfo) row.getCell(PL_MONEYNAME).getValue();
			entry.setMoneyDefine(moneyName);
			entry.setAppDate((Date) row.getCell(PL_APPDATE).getValue());
			entry.setCurrency((CurrencyInfo) row.getCell(PL_CURRENCY).getValue());
			entry.setAppAmount((BigDecimal) row.getCell(PL_APPAMOUNT).getValue());
			entry.setDescription((String) row.getCell(PL_DES).getValue());
			
			info.getPrePurchasePayListEntry().add(entry);
		}
	}
	protected void storeAttachmentEntry(){
		String name="附属房产:";
		PrePurchaseManageInfo info = this.editData;
		info.getPrePurchaseRoomAttachmentEntry().clear();
		for (int i = 0; i < this.tblAttachProperty.getRowCount(); i++) {
			IRow row = this.tblAttachProperty.getRow(i);
			PrePurchaseRoomAttachmentEntryInfo entry = (PrePurchaseRoomAttachmentEntryInfo) row.getUserObject();
			entry.setRoom((RoomInfo)row.getCell(AP_ROOMNUMBER).getValue());
			if(entry.getRoom()!=null) name=name+entry.getRoom()+";";
			entry.setIsAttachcmentToContract(((Boolean)row.getCell(AP_ISMERGETOCONTRACT).getValue()).booleanValue());
			entry.setMergeAmount((BigDecimal)row.getCell(AP_MERGEAMOUNT).getValue());
			entry.setStandardTotalAmount((BigDecimal)row.getCell(AP_STANDARDTOTALAMOUNT).getValue());
			entry.setBuildingPrice(SHEManageHelper.setScale(priceDigit, priceToIntegerType,(BigDecimal)row.getCell(AP_BUILDINGPRICE).getValue()));
			entry.setRoomPrice(SHEManageHelper.setScale(priceDigit, priceToIntegerType,(BigDecimal)row.getCell(AP_ROOMPRICE).getValue()));
			
			entry.setAgioDes(getAgioDes(entry.getPrePurchaseAgioEntry().toArray()));
			
			entry.setBuildingArea((BigDecimal)row.getCell(AP_BUILDINGAREA).getValue());
			entry.setRoomArea((BigDecimal)row.getCell(AP_ROOMAREA).getValue());
			
			info.getPrePurchaseRoomAttachmentEntry().add(entry);
		}
		if(!name.equals("附属房产:"))
			this.txtDes.setText(name);
	}
	public void actionReceiveBill_actionPerformed(ActionEvent e) throws Exception {
		if(!(TransactionStateEnum.PREAPPLE.equals(editData.getBizState())||
				TransactionStateEnum.PREAUDIT.equals(editData.getBizState()))){
			FDCMsgBox.showWarning(this,"该单据业务状态不能进行收款操作！");
			SysUtil.abort();
		}
		Object[] custObjs=new Object[editData.getPrePurchaseCustomerEntry().size()];
		for(int i=0;i<editData.getPrePurchaseCustomerEntry().size();i++){
			custObjs[i]=editData.getPrePurchaseCustomerEntry().get(i).getCustomer();
		}
		
		Set tranEntryIdSet = new HashSet();
		PrePurchasePayListEntryCollection signPayListColl = this.editData.getPrePurchasePayListEntry();
		for (int i = 0; i < signPayListColl.size(); i++) {
			PrePurchasePayListEntryInfo signPayEntryInfo = signPayListColl.get(i);
			if(signPayEntryInfo.getTanPayListEntryId()!=null)
				tranEntryIdSet.add(signPayEntryInfo.getTanPayListEntryId().toString());
		}	
		CRMClientHelper.openTheGatherRevBillWindow(this, null, editData.getSellProject(),editData ,custObjs,tranEntryIdSet);
		this.loadReceiveBill();
		this.updateActRevAmount((BaseTransactionInfo)editData);
	}
	public void actionRaletionPurchase_actionPerformed(ActionEvent e) throws Exception {
		if(editData!=null&&editData.getId()!=null){
			//查取认购单
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("srcId",editData.getId()));
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
	
	public void actionRaletionSign_actionPerformed(ActionEvent e) throws Exception {
		if(editData!=null&&editData.getId()!=null){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("srcId",editData.getId()));
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
	protected boolean veriftExistsSameRoomBill(RoomInfo room) throws EASBizException, BOSException{
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId()));
		filter.getFilterItems().add(new FilterItemInfo("bizState", TransactionStateEnum.PREAPPLE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("bizState", TransactionStateEnum.PREAUDIT_VALUE));
		
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
		
		if(veriftExistsSameRoomBill(room)){
			setRoomNull("房间已经存在预定单据！");
		}
		if(editData!=null&&((BaseTransactionInfo)editData).getSrcId()==null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("sellState");
			RoomSellStateEnum sellState=RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(room.getId()),sic).getSellState();
			if(!RoomSellStateEnum.OnShow.equals(sellState)&&!RoomSellStateEnum.PrePurchase.equals(sellState)&&!RoomSellStateEnum.SincerPurchase.equals(sellState)){
				setRoomNull("房间已经存在其他业务单据！");
			}
		}
	}
	protected boolean isExitPreMoneyDefine(){
		for(int i=0;i<this.tblPayList.getRowCount();i++){
			IRow row=this.tblPayList.getRow(i);
			if(this.preMoneyDefine.equals(row.getCell(PL_MONEYNAME).getValue())){
				return true;
			}
		}
		return false;
	}
	
	protected void f7Room_dataChanged(DataChangeEvent e) throws Exception {
		super.f7Room_dataChanged(e);
		if(!isExitPreMoneyDefine()){
			addPreMoneyDefine((RoomInfo)this.f7Room.getValue(),editData.getSellProject());
		}
	}
	private void addPreMoneyDefine(RoomInfo room,SellProjectInfo sellProject){
		if(room == null)return;
		if(sellProject == null)return;
//		RoomDisplaySetting set = new RoomDisplaySetting(null,SHEParamConstant.PROJECT_PRODUCTTYPE_SET_MAP);
		Map projectProductTypeSet = RoomDisplaySetting.getNewProjectProductTypeSet(null,sellProject.getId().toString(),room.getBuilding().getProductType()!=null?room.getBuilding().getProductType().getId().toString():null);
		if(projectProductTypeSet!=null){
			BigDecimal preAmount=(BigDecimal)projectProductTypeSet.get(SHEParamConstant.T1_PRE_PURCHASE_STANDARD);
			if(!(this.txtPreAmount.getBigDecimalValue()!=null&&this.txtPreAmount.getBigDecimalValue().compareTo(FDCHelper.ZERO)>0)){
				this.txtPreAmount.setValue(preAmount);
			}
			//在没有付款方案的时候，则在付款明细增加一条款项名称为预定金的记录
			if(this.f7PayType.getValue()==null){
				PrePurchasePayListEntryInfo info = new PrePurchasePayListEntryInfo();
				info.setAppDate(new Date());
				info.setCurrency(currency);
				info.setAppAmount(preAmount);
				info.setMoneyDefine(preMoneyDefine);
				addPayListEntryRow(info);
			}
		}
	}
	protected void initControl() {
		super.initControl();
		this.txtRecommended.setEnabled(false);
	}
	protected void initWorkButton() {
		super.initWorkButton();
        this.btnRelationPurchase.setIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
        this.btnRelationSign.setIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
	}
	public void actionCustomerSelect_actionPerformed(ActionEvent e) throws Exception {
		customerSelect(this,this.customer,editData.getSellProject(),false);
		storeCustomerEntry();
		loadCustomerEntry(this.editData);
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
		return "/bim/fdc/sellhouse/PrePurchaseManage";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.PrePurchaseManagePrintQuery");
		
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
		PrePurchaseManagePrintDataProvider data = new PrePurchaseManagePrintDataProvider(
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
		PrePurchaseManagePrintDataProvider data = new PrePurchaseManagePrintDataProvider(
				editData.getString("id"), getTDQueryPK(),value);
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}
	
	protected void verfiyOther(){
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
}