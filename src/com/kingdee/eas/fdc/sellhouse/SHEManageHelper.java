package com.kingdee.eas.fdc.sellhouse;

import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyCollection;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.SHERevBillFactory;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AgioParam;
import com.kingdee.eas.fdc.sellhouse.client.ChangeManageEditUI;
import com.kingdee.eas.fdc.sellhouse.client.CustomerRptEditUI;
import com.kingdee.eas.fdc.sellhouse.client.PrePurchaseManageEditUI;
import com.kingdee.eas.fdc.sellhouse.client.PrePurchaseManageListUI;
import com.kingdee.eas.fdc.sellhouse.client.PurchaseManageEditUI;
import com.kingdee.eas.fdc.sellhouse.client.PurchaseManageListUI;
import com.kingdee.eas.fdc.sellhouse.client.PurchaseParam;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.sellhouse.client.SignManageEditUI;
import com.kingdee.eas.fdc.sellhouse.client.SincerityPurchaseChangeNameUI;
import com.kingdee.eas.fdc.sellhouse.client.SincerityPurchaseListUI;
import com.kingdee.eas.fdc.sellhouse.client.TranCustomerSelectUI;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class SHEManageHelper {
	public final static String YUAN="0";
	public final static String JIAO="1";
	public final static String FEN="2";
	public final static String ROUND_DOWN="2";
	public final static String ROUND_UP="1";
	public final static String ROUND_HALF_UP="0";
	public static final int NUMBER_TEXT_FIELD_DATATYPE_BIGDECIMAL = KDFormattedTextField.BIGDECIMAL_TYPE;
	public static final int NUMBERTEXTFIELD_ALIGNMENT = JTextField.RIGHT;
	public static final String FittmentMoneyDefineNum="SYSMONEYDEFINE-02";
	public static final String FittmentMoneyDefineName="装修款";
	public static final String RoomAttachMoneyDefineNum="SYSMONEYDEFINE-01";
	public static final String RoomAttachMoneyDefineName="附属房款";
	public final static String CURRENCY="人民币";
	public final static BOSUuid SINPURMONEYDEFINEID=BOSUuid.read("7bFOykMhScaIcN+lBlSI7LiwqOA=");
	
	public static String PRELIMINARY="预定登记";
	public static String PURCHASE="签认购书";
	public static String SIGN="签约";
	public static String MORTGAGE="按揭办理";
	public static String ACCFUND="公积金办理";
	public static String INTEREST="产权办理";
	public static String JOIN="入伙办理";
	public static String AREACOMPENSATE="面积补差";
	
	/**
	 * 交易单据转预订认购签约
	 * @param id
	 * @param state
	 * @param owner
	 * @param uiName
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void toTransactionBill(BOSUuid id,TransactionStateEnum state,Component owner,String uiName) throws BOSException, EASBizException{
		if(owner==null||id==null||state==null||uiName==null) return;
		if(id==null) return;
		if(state==null) return;
		
		if(uiName.equals(PrePurchaseManageEditUI.class.getName())){
			if(!TransactionStateEnum.BAYNUMBER.equals(state)){
				FDCMsgBox.showWarning(owner,"该单据不是排号状态，不能进行转预定操作！");
				return;
			}
		}else if(uiName.equals(PurchaseManageEditUI.class.getName())){
			if(owner.getClass().getName().equals(SincerityPurchaseListUI.class.getName())&&!TransactionStateEnum.BAYNUMBER.equals(state)){
				FDCMsgBox.showWarning(owner,"该单据不是排号状态，不能进行转认购操作！");
				return;
			}
			if(owner.getClass().getName().equals(PrePurchaseManageListUI.class.getName())&&!TransactionStateEnum.PREAUDIT.equals(state)){
				FDCMsgBox.showWarning(owner,"该单据不是审批状态，不能进行转认购操作！");
				return;
			}
		}else if(uiName.equals(SignManageEditUI.class.getName())){
			if(owner.getClass().getName().equals(SincerityPurchaseListUI.class.getName())&&!TransactionStateEnum.BAYNUMBER.equals(state)){
				FDCMsgBox.showWarning(owner,"该单据不是排号状态，不能进行转签约操作！");
				return;
			}
			if(owner.getClass().getName().equals(PrePurchaseManageListUI.class.getName())&&!TransactionStateEnum.PREAUDIT.equals(state)){
				FDCMsgBox.showWarning(owner,"该单据不是审批状态，不能进行转签约操作！");
				return;
			}
			if(owner.getClass().getName().equals(PurchaseManageListUI.class.getName())&&!TransactionStateEnum.PURAUDIT.equals(state)){
				FDCMsgBox.showWarning(owner,"该单据不是审批状态，不能进行转签约操作！");
				return;
			}
		}
		
		UIContext uiContext = new UIContext(owner);
		
		ObjectUuidPK pk=new ObjectUuidPK(id);
		IObjectValue objectValue=DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(),pk,SHEManageHelper.getBizSelectors(pk.getObjectType()));
		if(((BaseTransactionInfo)objectValue).getRoom()!=null){
			SelectorItemCollection sels =new SelectorItemCollection();
			sels.add("*");
			sels.add("roomModel.*");
			sels.add("building.*");
			sels.add("building.productType.*");
			sels.add("building.sellProject.*");
			BOSUuid roomId=((BaseTransactionInfo)objectValue).getRoom().getId();
			RoomInfo room=RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(roomId),sels);
			uiContext.put("room", room);
			uiContext.put("sellProject", room.getBuilding().getSellProject());
		}
		uiContext.put("srcId", id);
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(uiName, uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}
	/**
	 * 非交易单据转预订认购签约
	 * @param id
	 * @param RoomId
	 * @param owner
	 * @param uiName
	 * @param customer
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void toTransactionBill(BOSUuid id,BOSUuid roomId,Component owner,String uiName,List customer) throws BOSException, EASBizException{
		if(owner==null) return;
		if(id==null) return;
		if(roomId==null) return;
		
		UIContext uiContext = new UIContext(owner);
		
		uiContext.put("srcId", id);
		SelectorItemCollection sels =new SelectorItemCollection();
		sels.add("*");
		sels.add("building.*");
		sels.add("building.productType.*");
		sels.add("building.sellProject.*");
		sels.add("roomModel.*");
		RoomInfo room=RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(roomId),sels);
		uiContext.put("room", room);
		uiContext.put("sellProject", room.getBuilding().getSellProject());
		
		uiContext.put("customer", customer);
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(uiName, uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}
	public static void toTransactionBill(BOSUuid id,SellProjectInfo sellProject,Component owner,String uiName,List customer) throws BOSException, EASBizException{
		if(owner==null) return;
		if(id==null) return;
		if(sellProject==null) return;
		
		UIContext uiContext = new UIContext(owner);
		
		uiContext.put("srcId", id);
		uiContext.put("sellProject",sellProject);
		uiContext.put("customer", customer);
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(uiName, uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}
	
	public static IUIWindow toTransactionBillForCommerceChance(BOSUuid id,SellProjectInfo sellProject,Component owner,String uiName,List customer,CommerceChanceInfo commerce) throws BOSException, EASBizException{
		if(owner==null) return null;
		if(id==null) return null;
		if(sellProject==null) return null;
		
		UIContext uiContext = new UIContext(owner);
		
		//uiContext.put("srcId", id);
		uiContext.put("sellProject",sellProject);
		uiContext.put("customer", customer);
		uiContext.put("commerce", commerce);
		uiContext.put("destoryWindowAfterSubmit", Boolean.TRUE);
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(uiName, uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
		return uiWindow;
	}
	public static void toTransactionBill(BOSUuid roomId,SellProjectInfo sellProject,Component owner,String uiName) throws BOSException, EASBizException{
		if(owner==null) return;
		if(roomId==null) return;
		if(sellProject==null) return;
		
		UIContext uiContext = new UIContext(owner);
		
		SelectorItemCollection sels =new SelectorItemCollection();
		sels.add("*");
		sels.add("building.*");
		sels.add("building.productType.*");
		sels.add("building.sellProject.*");
		sels.add("roomModel.*");
		RoomInfo room=RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(roomId),sels);
		uiContext.put("room", room);
		uiContext.put("sellProject",sellProject);
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(uiName, uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}
	/**
	 * 特殊业务
	 * @param bizType 特殊业务类型
	 * @param id
	 * @param owner
	 * @param isRoom 为true ,id 为房间ID，为false ,id 为房间业务单据Id
	 * @throws BOSException
	 * @throws EASBizException
	 */
	
	public static void toChangeManage(ChangeBizTypeEnum bizType,BOSUuid id,Component owner,boolean isRoom) throws BOSException, EASBizException{
		if(owner==null) return;
		BOSUuid srcId=null;
		IObjectValue objectValue=null;
		if(isRoom){
			if(id==null) return;
			objectValue=getCurTransactionBill(id);
			if(objectValue!=null){
				srcId=((FDCBillInfo)objectValue).getId();
			}
		}else{
			srcId=id;
			ObjectUuidPK pk=new ObjectUuidPK(srcId);
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("room.id");
			sic.add("bizState");
			sic.add("specialAgio");
			sic.add("transactionID");
			objectValue=DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(),pk,sic);
			if(((BaseTransactionInfo)objectValue).getRoom()==null){
				FDCMsgBox.showWarning(owner,"该房间为空，不适合做变更业务！");
				return;
			}
		}
		if(objectValue!=null){
			if(objectValue instanceof SignManageInfo){
				if(bizType.equals(ChangeBizTypeEnum.CHANGENAME)){
					FDCMsgBox.showWarning(owner,"该房间已经签约，不适合做更名业务！");
					return;
				}
				if(bizType.equals(ChangeBizTypeEnum.CHANGEROOM)){
					FDCMsgBox.showWarning(owner,"该房间已经签约，不适合做换房业务！");
					return;
				}
				FilterInfo filter = new FilterInfo();
//				filter.getFilterItems().add(new FilterItemInfo("signManager.id", srcId));
//				if(BankPaymentEntryFactory.getRemoteInstance().exists(filter)){
//					FDCMsgBox.showWarning(owner,"该房间存在银行放款，不适合做变更业务！");
//					return;
//				}
				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("sign.id", srcId));
				if(CompensateRoomListFactory.getRemoteInstance().exists(filter)&&!bizType.equals(ChangeBizTypeEnum.QUITROOM)){
					FDCMsgBox.showWarning(owner,"该房间存在面积补差，不适合做变更业务！");
					return;
				}
				if(((SignManageInfo)objectValue).getSpecialAgio()!=null&&!bizType.equals(ChangeBizTypeEnum.QUITROOM)){
					FDCMsgBox.showWarning(owner,"该房间存在特殊折扣，不适合做变更业务！");
					return;
				}
			}
		}else{
			FDCMsgBox.showWarning(owner,"该房间无业务单据，不适合做变更业务！");
			return;
		}
		TransactionStateEnum state=((BaseTransactionInfo)objectValue).getBizState();
		if(!(TransactionStateEnum.PURAUDIT.equals(state)||TransactionStateEnum.PREAUDIT.equals(state)||TransactionStateEnum.SIGNAUDIT.equals(state))){
			FDCMsgBox.showWarning(owner,"该房间业务单据不是审批状态，不适合做变更业务！");
			return;
		}
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("relateTransId",((BaseTransactionInfo)objectValue).getTransactionID()));
		filter.getFilterItems().add(new FilterItemInfo("isGathered", new Boolean(false)));
		if(SHERevBillFactory.getRemoteInstance().exists(filter)){
			FDCMsgBox.showWarning(owner,"该房间存在未生成出纳汇总单的收付款单，不适合做变更业务！");
			return;
		}
		
		UIContext uiContext = new UIContext(owner);
		uiContext.put("srcId", srcId);
		uiContext.put("bizType", bizType);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				ChangeManageEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}
	
	public static void setRoomAttachmentListEntry(TranRoomAttachmentEntryInfo info,TranRoomAttachmentEntryInfo srcInfo){
		info.setMergeAmount(srcInfo.getMergeAmount());
		info.setIsAttachcmentToContract(srcInfo.isIsAttachcmentToContract());
		info.setRoom(srcInfo.getRoom());
		info.setStandardTotalAmount(srcInfo.getStandardTotalAmount());
		info.setAgioDes(srcInfo.getAgioDes());
		info.setBuildingArea(srcInfo.getBuildingArea());
		info.setRoomArea(srcInfo.getRoomArea());
		info.setBuildingPrice(srcInfo.getBuildingPrice());
		info.setRoomPrice(srcInfo.getRoomPrice());
	}
	public static void setAgioListEntry(AgioEntryInfo info,AgioEntryInfo srcInfo){
		info.setAgio(srcInfo.getAgio());
		info.setPro(srcInfo.getPro());
		info.setAmount(srcInfo.getAmount());
		info.setSeq(srcInfo.getSeq());
		info.setOperate(srcInfo.getOperate());
	}
	public static void setSaleManEntry(TranSaleManEntryInfo info,TranSaleManEntryInfo srcInfo){
		info.setUser(srcInfo.getUser());
		info.setPercent(srcInfo.getPercent());
	}
	public static void setPayListEntry(Context ctx,TranPayListEntryInfo info,TranPayListEntryInfo srcInfo){
		info.setMoneyDefine(srcInfo.getMoneyDefine());
		info.setAppDate(srcInfo.getAppDate());
		info.setCurrency(srcInfo.getCurrency());
		info.setAppAmount(srcInfo.getAppAmount());
		info.setActRevAmount(getActRevAmount(ctx,srcInfo.getTanPayListEntryId()));
		info.setSeq(srcInfo.getSeq());
		info.setTanPayListEntryId(srcInfo.getTanPayListEntryId());
		info.setDescription(srcInfo.getDescription());
	}
	public static void setPayListEntry(TranPayListEntryInfo info,TranPayListEntryInfo srcInfo,boolean isUpdateRevAmount){
		info.setMoneyDefine(srcInfo.getMoneyDefine());
		info.setAppDate(srcInfo.getAppDate());
		info.setCurrency(srcInfo.getCurrency());
		info.setAppAmount(srcInfo.getAppAmount());
		if(isUpdateRevAmount){
			info.setActRevAmount(srcInfo.getActRevAmount());
		}else{
			info.setActRevAmount(FDCHelper.ZERO);
		}
		info.setSeq(srcInfo.getSeq());
		info.setTanPayListEntryId(srcInfo.getTanPayListEntryId());
		info.setDescription(srcInfo.getDescription());
	}
	public static void setTranBusinessOverViewBusinessName(Context ctx,TranBusinessOverViewInfo bo,TranPayListEntryInfo payListEntry) throws EASBizException, BOSException{
		MoneyDefineInfo moneyDefineInfo = payListEntry.getMoneyDefine();
		if(moneyDefineInfo!=null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("name");
			bo.setBusinessName(MoneyDefineFactory.getLocalInstance(ctx).getMoneyDefineInfo(new ObjectUuidPK(moneyDefineInfo.getId())).getName());
		}
//		MoneyTypeEnum moneyType = moneyDefineInfo.getMoneyType();
//		if (MoneyTypeEnum.EarnestMoney.equals(moneyType)) {
//			bo.setBusinessName(BusinessTypeNameEnum.PURCHASEAMOUNT);
//		} else if(MoneyTypeEnum.FisrtAmount.equals(moneyType)){
//			bo.setBusinessName(BusinessTypeNameEnum.FISRTAMOUNT);
//		}else if(MoneyTypeEnum.HouseAmount.equals(moneyType)){
//			bo.setBusinessName(BusinessTypeNameEnum.HOUSEAMOUNT);
//		}else if(MoneyTypeEnum.LoanAmount.equals(moneyType)){
//			bo.setBusinessName(BusinessTypeNameEnum.LOANAMOUNT);
//		}else if(MoneyTypeEnum.AccFundAmount.equals(moneyType)){
//			bo.setBusinessName(BusinessTypeNameEnum.ACCFUNDAMOUNT);
//		}else if(MoneyTypeEnum.ReplaceFee.equals(moneyType)){
//			bo.setBusinessName(BusinessTypeNameEnum.REPLACEFEE);
//		}else if(MoneyTypeEnum.SinPur.equals(moneyType)){
//			bo.setBusinessName(BusinessTypeNameEnum.SINCER);
//		}else if(MoneyTypeEnum.PreconcertMoney.equals(moneyType)){
//			bo.setBusinessName(BusinessTypeNameEnum.PRECONCERTMONEY);
//		}else if(MoneyTypeEnum.Refundment.equals(moneyType)){
//			bo.setBusinessName(BusinessTypeNameEnum.REFUNDMENT);
//		}else if(MoneyTypeEnum.FitmentAmount.equals(moneyType)){
//			bo.setBusinessName(BusinessTypeNameEnum.FITMENTAMOUNT);
//		}else if(MoneyTypeEnum.LateFee.equals(moneyType)){
//			bo.setBusinessName(BusinessTypeNameEnum.LATEFEE);
//		}else if(MoneyTypeEnum.CompensateAmount.equals(moneyType)){
//			bo.setBusinessName(BusinessTypeNameEnum.COMPENSATEAMOUNT);
//		}else if(MoneyTypeEnum.CommissionCharge.equals(moneyType)){
//			bo.setBusinessName(BusinessTypeNameEnum.COMMISSIONCHARGE);
//		}else if(MoneyTypeEnum.ElseAmount.equals(moneyType)){
//			bo.setBusinessName(BusinessTypeNameEnum.ELSEAMOUNT);
//		}
	}
	
	public static void setBizListEntry(Context ctx,BaseTransactionInfo baseInfo,TranBusinessOverViewInfo info,BizListEntryInfo srcInfo) throws BOSException{
		info.setIsFinish(false);
		info.setFinishDate(null);
		info.setActualFinishDate(null);
		info.setDesc(srcInfo.getDescription());
		info.setSeq(srcInfo.getSeq());
		if(baseInfo instanceof PrePurchaseManageInfo){
			if(BizNewFlowEnum.PREREGISTER.equals(srcInfo.getPayTypeBizFlow())){
				info.setBusinessName(SHEManageHelper.PRELIMINARY);
//				info.setBusinessName(BusinessTypeNameEnum.PRELIMINARY);
	    		info.setIsFinish(true);
	    		info.setFinishDate(baseInfo.getBizDate());
	    		info.setActualFinishDate(baseInfo.getBizDate());
			}else if(BizNewFlowEnum.PURCHASE.equals(srcInfo.getPayTypeBizFlow())){
//				info.setBusinessName(BusinessTypeNameEnum.PURCHASE);
				info.setBusinessName(SHEManageHelper.PURCHASE);
			}else if(BizNewFlowEnum.SIGN.equals(srcInfo.getPayTypeBizFlow())){
//				info.setBusinessName(BusinessTypeNameEnum.SIGN);
				info.setBusinessName(SHEManageHelper.SIGN);
			}else if(BizNewFlowEnum.LOAN.equals(srcInfo.getPayTypeBizFlow())){
//				info.setBusinessName(BusinessTypeNameEnum.MORTGAGE);
				info.setBusinessName(SHEManageHelper.MORTGAGE);
			}else if(BizNewFlowEnum.ACCFUND.equals(srcInfo.getPayTypeBizFlow())){
//				info.setBusinessName(BusinessTypeNameEnum.ACCFUND);
				info.setBusinessName(SHEManageHelper.ACCFUND);
			}else if(BizNewFlowEnum.PROPERTY.equals(srcInfo.getPayTypeBizFlow())){
//				info.setBusinessName(BusinessTypeNameEnum.INTEREST);
				info.setBusinessName(SHEManageHelper.INTEREST);
			}else if(BizNewFlowEnum.JOIN.equals(srcInfo.getPayTypeBizFlow())){
//				info.setBusinessName(BusinessTypeNameEnum.JOIN);
				info.setBusinessName(SHEManageHelper.JOIN);
			}else if(BizNewFlowEnum.AREACOMPENSAGTE.equals(srcInfo.getPayTypeBizFlow())){
//				info.setBusinessName(BusinessTypeNameEnum.AREACOMPENSATE);
				info.setBusinessName(SHEManageHelper.AREACOMPENSATE);
			}
    		setBizListOtherEntry(ctx,baseInfo,info,srcInfo);
		}
		if(baseInfo instanceof PurchaseManageInfo){
			if(BizNewFlowEnum.PREREGISTER.equals(srcInfo.getPayTypeBizFlow())){
//				info.setBusinessName(BusinessTypeNameEnum.PRELIMINARY);
				info.setBusinessName(SHEManageHelper.PRELIMINARY);
			}else if(BizNewFlowEnum.PURCHASE.equals(srcInfo.getPayTypeBizFlow())){
//				info.setBusinessName(BusinessTypeNameEnum.PURCHASE);
				info.setBusinessName(SHEManageHelper.PURCHASE);
				info.setIsFinish(true);
	    		info.setFinishDate(baseInfo.getBizDate());
	    		info.setActualFinishDate(baseInfo.getBizDate());
			}else if(BizNewFlowEnum.SIGN.equals(srcInfo.getPayTypeBizFlow())){
//				info.setBusinessName(BusinessTypeNameEnum.SIGN);
				info.setBusinessName(SHEManageHelper.SIGN);
			}else if(BizNewFlowEnum.LOAN.equals(srcInfo.getPayTypeBizFlow())){
//				info.setBusinessName(BusinessTypeNameEnum.MORTGAGE);
				info.setBusinessName(SHEManageHelper.MORTGAGE);
			}else if(BizNewFlowEnum.ACCFUND.equals(srcInfo.getPayTypeBizFlow())){
//				info.setBusinessName(BusinessTypeNameEnum.ACCFUND);
				info.setBusinessName(SHEManageHelper.ACCFUND);
			}else if(BizNewFlowEnum.PROPERTY.equals(srcInfo.getPayTypeBizFlow())){
//				info.setBusinessName(BusinessTypeNameEnum.INTEREST);
				info.setBusinessName(SHEManageHelper.INTEREST);
			}else if(BizNewFlowEnum.JOIN.equals(srcInfo.getPayTypeBizFlow())){
//				info.setBusinessName(BusinessTypeNameEnum.JOIN);
				info.setBusinessName(SHEManageHelper.JOIN);
			}else if(BizNewFlowEnum.AREACOMPENSAGTE.equals(srcInfo.getPayTypeBizFlow())){
//				info.setBusinessName(BusinessTypeNameEnum.AREACOMPENSATE);
				info.setBusinessName(SHEManageHelper.AREACOMPENSATE);
			}
			setBizListOtherEntry(ctx,baseInfo,info,srcInfo);
		}
		if(baseInfo instanceof SignManageInfo){
			if(BizNewFlowEnum.PREREGISTER.equals(srcInfo.getPayTypeBizFlow())){
//				info.setBusinessName(BusinessTypeNameEnum.PRELIMINARY);
				info.setBusinessName(SHEManageHelper.PRELIMINARY);
			}else if(BizNewFlowEnum.PURCHASE.equals(srcInfo.getPayTypeBizFlow())){
//				info.setBusinessName(BusinessTypeNameEnum.PURCHASE);
				info.setBusinessName(SHEManageHelper.PURCHASE);
			}else if(BizNewFlowEnum.SIGN.equals(srcInfo.getPayTypeBizFlow())){
//				info.setBusinessName(BusinessTypeNameEnum.SIGN);
				info.setBusinessName(SHEManageHelper.SIGN);
				info.setIsFinish(true);
	    		info.setFinishDate(baseInfo.getBizDate());
	    		info.setActualFinishDate(baseInfo.getBizDate());
			}else if(BizNewFlowEnum.LOAN.equals(srcInfo.getPayTypeBizFlow())){
//				info.setBusinessName(BusinessTypeNameEnum.MORTGAGE);
				info.setBusinessName(SHEManageHelper.MORTGAGE);
			}else if(BizNewFlowEnum.ACCFUND.equals(srcInfo.getPayTypeBizFlow())){
//				info.setBusinessName(BusinessTypeNameEnum.ACCFUND);
				info.setBusinessName(SHEManageHelper.ACCFUND);
			}else if(BizNewFlowEnum.PROPERTY.equals(srcInfo.getPayTypeBizFlow())){
//				info.setBusinessName(BusinessTypeNameEnum.INTEREST);
				info.setBusinessName(SHEManageHelper.INTEREST);
			}else if(BizNewFlowEnum.JOIN.equals(srcInfo.getPayTypeBizFlow())){
//				info.setBusinessName(BusinessTypeNameEnum.JOIN);
				info.setBusinessName(SHEManageHelper.JOIN);
			}else if(BizNewFlowEnum.AREACOMPENSAGTE.equals(srcInfo.getPayTypeBizFlow())){
//				info.setBusinessName(BusinessTypeNameEnum.AREACOMPENSATE);
				info.setBusinessName(SHEManageHelper.AREACOMPENSATE);
			}
			setBizListOtherEntry(ctx,baseInfo,info,srcInfo);
		}
	}
	
	private static void setBizListOtherEntry(Context ctx,BaseTransactionInfo baseInfo,TranBusinessOverViewInfo info,BizListEntryInfo srcInfo) throws BOSException{
		RoomInfo room = baseInfo.getRoom();
		if(room == null){
			return;
		}
		int monthLimit = srcInfo.getMonthLimit();
		int dayLimit = srcInfo.getDayLimit();
		Date appDate = srcInfo.getAppDate();
		String payTypeBizTime = srcInfo.getPayTypeBizTime();
		if (payTypeBizTime != null && "指定日期".equals(payTypeBizTime)) {
			info.setFinishDate(appDate);
		} else if(payTypeBizTime != null && "预定登记".equals(payTypeBizTime)){
			PrePurchaseManageInfo prePurchaseManage = getBizDatePrePurchaseManage(ctx,room);
			Date date = null;
			if(prePurchaseManage != null){
				date = prePurchaseManage.getBizDate();
			}
			if (date != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.MONTH, monthLimit);
				cal.add(Calendar.DATE, dayLimit);
				date = cal.getTime();
				info.setFinishDate(date);
			}
		} else if(payTypeBizTime != null && "签认购书".equals(payTypeBizTime)){
			PurchaseManageInfo purchaseManage = getBizDatePurchaseManage(ctx,room);
			Date date = null;
			if(purchaseManage != null){
				date = purchaseManage.getBizDate();
			}
			if (date != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.MONTH, monthLimit);
				cal.add(Calendar.DATE, dayLimit);
				date = cal.getTime();
				info.setFinishDate(date);
			}
		}else if(payTypeBizTime != null && "签约".equals(payTypeBizTime)){
			SignManageInfo signManage = getBizDateSignManage(ctx,room);
			Date date = null;
			if(signManage != null){
				date = signManage.getBizDate();
			}
			if (date != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.MONTH, monthLimit);
				cal.add(Calendar.DATE, dayLimit);
				date = cal.getTime();
				info.setFinishDate(date);
			}
		}else if(payTypeBizTime != null && "按揭办理".equals(payTypeBizTime)){
			RoomLoanInfo roomLoan = getRoomLoan(ctx,room);
			Date date = null;
			if(roomLoan != null){
				date = roomLoan.getProcessLoanDate();
			}
			if (date != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.MONTH, monthLimit);
				cal.add(Calendar.DATE, dayLimit);
				date = cal.getTime();
				info.setFinishDate(date);
			}
		}else if(payTypeBizTime != null && "公积金办理".equals(payTypeBizTime)){
			RoomLoanInfo roomLoan = getRoomAccFund(ctx,room);
			Date date = null;
			if(roomLoan != null){
				date = roomLoan.getProcessLoanDate();
			}
			if (date != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.MONTH, monthLimit);
				cal.add(Calendar.DATE, dayLimit);
				date = cal.getTime();
				info.setFinishDate(date);
			}
		}else if(payTypeBizTime != null && "产权办理".equals(payTypeBizTime)){
			RoomPropertyBookInfo propertyBook = getRoomPropertyBook(ctx,room);
			Date date = null;
			if(propertyBook != null){
				date = propertyBook.getTransactDate();
			}
			if (date != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.MONTH, monthLimit);
				cal.add(Calendar.DATE, dayLimit);
				date = cal.getTime();
				info.setFinishDate(date);
			}
		}else if(payTypeBizTime != null && "入伙办理".equals(payTypeBizTime)){
			RoomJoinInfo joinIn = getRoomJoinIn(ctx,room);
			Date date = null;
			if(joinIn != null){
				date = joinIn.getJoinDate();
			}
			if (date != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.MONTH, monthLimit);
				cal.add(Calendar.DATE, dayLimit);
				date = cal.getTime();
				info.setFinishDate(date);
			}
		}else if(payTypeBizTime != null && "面积补差".equals(payTypeBizTime)){
			RoomAreaCompensateInfo areaCompensation = getRoomAreaCompensation(ctx,room);
			Date date = null;
			if(areaCompensation != null){
				date = areaCompensation.getCompensateDate();
			}
			if (date != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.add(Calendar.MONTH, monthLimit);
				cal.add(Calendar.DATE, dayLimit);
				date = cal.getTime();
				info.setFinishDate(date);
			}
		}
	}

	public static RoomJoinInfo getRoomJoinIn(Context ctx,RoomInfo room) {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("joinDate");
		view.getSelector().add("joinEndDate");
		RoomJoinCollection joins = null;
		try {
			joins = RoomJoinFactory.getLocalInstance(ctx).getRoomJoinCollection(view);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		if (joins.size() > 1) {
			MsgBox.showInfo("多个入伙,系统错误!");
			SysUtil.abort();
		}
		if (joins.size() == 0) {
			return null;
		}
		return joins.get(0);
	}

	public static RoomAreaCompensateInfo getRoomAreaCompensation(Context ctx,RoomInfo room) {
		if (room.getRoomCompensateState() == null || room.getRoomCompensateState().equals(RoomCompensateStateEnum.NOCOMPENSATE)) {
			return null;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		view.setFilter(filter);
		view.getSelector().add("compensateDate");
		view.getSelector().add("compensateState");
		view.getSelector().add("compensateAmount");

		RoomAreaCompensateCollection roomAreaCompens = null;
		try {
			roomAreaCompens = RoomAreaCompensateFactory.getLocalInstance(ctx).getRoomAreaCompensateCollection(view);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		if (roomAreaCompens.size() > 1) {
			MsgBox.showInfo("多个补差,系统错误!");
			SysUtil.abort();
		}
		if (roomAreaCompens.size() == 0) {
			return null;
		}
		return roomAreaCompens.get(0);
	}

	public static RoomLoanInfo getRoomLoan(Context ctx,RoomInfo room) {
		if (room.getRoomLoanState() == null || room.getRoomLoanState().equals(RoomLoanStateEnum.NOTLOANED) && room.getRoomACCFundState().equals(RoomACCFundStateEnum.NOTFUND)) {
			return null;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("mmType.moneyType", MoneyTypeEnum.LoanAmount));
		view.setFilter(filter);
		view.getSelector().add("fundProcessDate");
		view.getSelector().add("processLoanDate");

		RoomLoanCollection joins = null;
		try {
			joins = RoomLoanFactory.getLocalInstance(ctx).getRoomLoanCollection(view);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		if (joins.size() == 0) {
			return null;
		}
		return joins.get(0);
	}
	
	
	public static RoomLoanInfo getRoomAccFund(Context ctx,RoomInfo room) {
		if (room.getRoomLoanState() == null || room.getRoomLoanState().equals(RoomLoanStateEnum.NOTLOANED) && room.getRoomACCFundState().equals(RoomACCFundStateEnum.NOTFUND)) {
			return null;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("mmType.moneyType", MoneyTypeEnum.AccFundAmount));
		view.setFilter(filter);
		view.getSelector().add("fundProcessDate");
		view.getSelector().add("processLoanDate");

		RoomLoanCollection joins = null;
		try {
			joins = RoomLoanFactory.getLocalInstance(ctx).getRoomLoanCollection(view);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		if (joins.size() == 0) {
			return null;
		}
		return joins.get(0);
	}

	public static RoomPropertyBookInfo getRoomPropertyBook(Context ctx,RoomInfo room) {
		if (room.getRoomBookState() == null || room.getRoomBookState().equals(RoomBookStateEnum.NOTBOOKED)) {
			return null;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("transactDate");
		RoomPropertyBookCollection joins = null;
		try {
			joins = RoomPropertyBookFactory.getLocalInstance(ctx).getRoomPropertyBookCollection(view);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
			SysUtil.abort();
		}
		if (joins.size() > 1) {
			MsgBox.showInfo("多个产权登记,系统错误!");
			SysUtil.abort();
		}
		if (joins.size() == 0) {
			return null;
		}
		return joins.get(0);
	}

	public static TransactionInfo getTransactionInfo(Context ctx,RoomInfo room) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		view.getSelector().add("tranDate");
		view.getSelector().add("preLink");
		view.getSelector().add("currentLink");
		view.getSelector().add("billId");
		view.getSelector().add("tranBusinessOverView.*");
		view.getSelector().add("customerNames");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("isValid", new Boolean(false)));

		view.setFilter(filter);
		TransactionCollection transactionCol = null;
		if(ctx==null){
			 transactionCol = TransactionFactory.getRemoteInstance().getTransactionCollection(view);
		}else{
			 transactionCol = TransactionFactory.getLocalInstance(ctx).getTransactionCollection(view);
		}
		
		if(transactionCol!=null && !transactionCol.isEmpty()){
			return transactionCol.get(0);
		}
		else{
			return null;
		}
	}
	
	/**
	 * 通过销售服务更新总览中对应的服务
	 * @param transactionInfo - 交易主线
	 * @param serviceType - 销售服务
	 * @param promiseFinishDate - 应完成日期
	 * @param actualFinishDate - 实际完成日期，如果为null，表示未完成
	 * @param isDelete - 是否删除，如果为是，当付款方案的业务明细存在对应的服务时，则重置服务，否则删除服务
	 */
	public static void updateTransactionOverView(Context ctx,RoomInfo room, String serviceType, 
			Date promiseFinishDate, Date actualFinishDate, boolean isDelete){
		try {
			boolean isServiceExist = false;
			TransactionInfo transactionInfo = getTransactionInfo(ctx,room);
			TranBusinessOverViewCollection overViewCol = transactionInfo.getTranBusinessOverView();
			if(overViewCol!=null && !overViewCol.isEmpty()){
				for(int i=0; i<overViewCol.size(); i++){
					TranBusinessOverViewInfo overViewInfo = overViewCol.get(i);
					if(overViewInfo.getType().equals(BusTypeEnum.BUSINESS)
						&& overViewInfo.getBusinessName().equals(serviceType)){
						isServiceExist = true;
						if(isDelete){  //服务单据被置为无效，根据付款方案中的业务明细来处理总览中对应的服务
							if(!isDeleteBiz(ctx,transactionInfo, serviceType)){  //付款方案中定义了业务，所以不删除总览业务
								overViewInfo.setActualFinishDate(null);
								overViewInfo.setIsFinish(false);
							}
							else{  //直接删除总览业务
								overViewCol.removeObject(i);
							}
						}
						else if(actualFinishDate != null){  //更新为完成状态
							overViewInfo.setActualFinishDate(actualFinishDate);
							overViewInfo.setIsFinish(true);
						}
						else{  //恢复为未完成状态
							overViewInfo.setActualFinishDate(null);
							overViewInfo.setIsFinish(false);
						}
						if(ctx==null){
							TransactionFactory.getRemoteInstance()
							.update(new ObjectUuidPK(transactionInfo.getId().toString()), transactionInfo);
						}else{
							TransactionFactory.getLocalInstance(ctx)
							.update(new ObjectUuidPK(transactionInfo.getId().toString()), transactionInfo);
						}
						
						break;
					}
				}
			}
			//总览中没有对应的服务，新增对应的服务
			if(!isServiceExist){
				
				//在前面已经做了处理,这里直接返回
				if(AREACOMPENSATE.equals(serviceType)){
					return;
				}
				TranBusinessOverViewInfo overViewInfo = new TranBusinessOverViewInfo();
				overViewInfo.setBusinessName(serviceType);
				overViewInfo.setType(BusTypeEnum.BUSINESS);
				overViewInfo.setFinishDate(promiseFinishDate);
				if(actualFinishDate != null){
					overViewInfo.setActualFinishDate(actualFinishDate);
					overViewInfo.setIsFinish(true);
				}
				else{
					overViewInfo.setIsFinish(false);
				}
				
				transactionInfo.getTranBusinessOverView().add(overViewInfo);
				if(ctx==null){
					TransactionFactory.getRemoteInstance()
					.update(new ObjectUuidPK(transactionInfo.getId().toString()), transactionInfo);
				
				}else{
					TransactionFactory.getLocalInstance(ctx)
					.update(new ObjectUuidPK(transactionInfo.getId().toString()), transactionInfo);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据交易主线对应单据中付款方案的业务明细，判断是否删除总览中对应的服务
	 * @param transInfo - 交易主线
	 * @param serviceType - 服务类型
	 * @return true - 删除, false - 清空总览服务的完成日期和是否完成 
	 */
	private static boolean isDeleteBiz(Context ctx,TransactionInfo transInfo, String serviceType){
		boolean isDelete = true;
		try{
			String payTypeId = null;
			if(transInfo.getCurrentLink().equals(RoomSellStateEnum.Sign)){  //签约
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add(new SelectorItemInfo("payType.id"));
				
				SignManageInfo signInfo = null;
				
				if(ctx==null){
					signInfo = SignManageFactory.getRemoteInstance()
					.getSignManageInfo(new ObjectUuidPK(transInfo.getBillId()), selector);
				}else{
					signInfo = SignManageFactory.getLocalInstance(ctx)
					.getSignManageInfo(new ObjectUuidPK(transInfo.getBillId()), selector);
			
				}
					
				
				payTypeId = signInfo.getPayType().getId().toString();
			}
			else if(transInfo.getCurrentLink().equals(RoomSellStateEnum.Purchase)){  //认购
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add(new SelectorItemInfo("payType.id"));
				PurchaseManageInfo purchaseInfo = null;
				if(ctx==null){
					purchaseInfo = PurchaseManageFactory.getRemoteInstance()
					.getPurchaseManageInfo(new ObjectUuidPK(transInfo.getBillId()), selector);
				
				}else{
					purchaseInfo = PurchaseManageFactory.getLocalInstance(ctx)
					.getPurchaseManageInfo(new ObjectUuidPK(transInfo.getBillId()), selector);
			
				}
				
				payTypeId = purchaseInfo.getPayType().getId().toString();
			}
			if(payTypeId != null){
				//获取付款方案的业务明细
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add(new SelectorItemInfo("bizLists.*"));
				SHEPayTypeInfo payType = null;
				if(ctx==null){
					payType = SHEPayTypeFactory.getRemoteInstance()
					.getSHEPayTypeInfo(new ObjectUuidPK(payTypeId), selector);
				}else{
					payType = SHEPayTypeFactory.getLocalInstance(ctx)
					.getSHEPayTypeInfo(new ObjectUuidPK(payTypeId), selector);
				}
			
				//遍历付款业务明细，判断是否存在对应的服务
				if(payType.getBizLists()!=null && !payType.getBizLists().isEmpty()){
					for(int i=0; i<payType.getBizLists().size(); i++){
						BizListEntryInfo bizEntry = payType.getBizLists().get(i);
						if(bizEntry.getPayTypeBizFlow().equals(BizNewFlowEnum.LOAN) && serviceType.equals(SHEManageHelper.MORTGAGE)
							|| bizEntry.getPayTypeBizFlow().equals(BizNewFlowEnum.ACCFUND) && serviceType.equals(SHEManageHelper.ACCFUND)
							|| bizEntry.getPayTypeBizFlow().equals(BizNewFlowEnum.PROPERTY) && serviceType.equals(SHEManageHelper.INTEREST)
							|| bizEntry.getPayTypeBizFlow().equals(BizNewFlowEnum.JOIN) && serviceType.equals(SHEManageHelper.JOIN)
							|| bizEntry.getPayTypeBizFlow().equals(BizNewFlowEnum.AREACOMPENSAGTE) && serviceType.equals(SHEManageHelper.AREACOMPENSATE)){
							isDelete = false;
							break;
						}
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return isDelete;
	}
	
	public static void setBaseInfo(BaseTransactionInfo info,BaseTransactionInfo srcInfo,boolean isChangeManage){
		info.setTransactionID(srcInfo.getTransactionID());
		info.setSellProject(srcInfo.getSellProject());
		info.setSellType(srcInfo.getSellType());
		info.setStrdTotalAmount(srcInfo.getStrdTotalAmount());
		info.setBulidingArea(srcInfo.getBulidingArea());
		info.setRoomArea(srcInfo.getRoomArea());
		
		info.setStrdPlanBuildingArea(srcInfo.getStrdPlanBuildingArea());
		info.setStrdActualBuildingArea(srcInfo.getStrdActualBuildingArea());
		info.setStrdPlanRoomArea(srcInfo.getStrdPlanRoomArea());
		info.setStrdActualRoomArea(srcInfo.getStrdActualRoomArea());
		
		info.setAttachmentAmount(srcInfo.getAttachmentAmount());
		info.setStrdBuildingPrice(srcInfo.getStrdBuildingPrice());
		info.setStrdRoomPrice(srcInfo.getStrdRoomPrice());
		info.setFitmentTotalAmount(srcInfo.getFitmentTotalAmount());
		
		info.setFitmentStandard(srcInfo.getFitmentStandard());
		info.setFitmentPrice(srcInfo.getFitmentPrice());
		info.setIsFitmentToContract(srcInfo.isIsFitmentToContract());
		
		info.setPayType(srcInfo.getPayType());
		info.setAgioScheme(srcInfo.getAgioScheme());
		info.setAgioDesc(srcInfo.getAgioDesc());
		info.setLastAgio(srcInfo.getLastAgio());
		info.setDealTotalAmount(srcInfo.getDealTotalAmount());
		info.setContractTotalAmount(srcInfo.getContractTotalAmount());
		info.setValuationType(srcInfo.getValuationType());
		info.setDealBuildPrice(srcInfo.getDealBuildPrice());
		info.setDealRoomPrice(srcInfo.getDealRoomPrice());
		info.setContractBuildPrice(srcInfo.getContractBuildPrice());
		info.setContractRoomPrice(srcInfo.getContractRoomPrice());
//		info.setBusAdscriptionDate(srcInfo.getBusAdscriptionDate());
		
		info.setLoanAmount(srcInfo.getLoanAmount());
		info.setAccFundAmount(srcInfo.getAccFundAmount());
		info.setNewCommerceChance(srcInfo.getNewCommerceChance());
		info.setRecommended(srcInfo.getRecommended());
		
		//参数是针对交易的
		info.setIsEarnestInHouseAmount(srcInfo.isIsEarnestInHouseAmount());
		info.setToIntegerType(srcInfo.getToIntegerType());
		info.setDigit(srcInfo.getDigit());
		info.setIsBasePriceSell(srcInfo.isIsBasePriceSell());
		info.setPriceDigit(srcInfo.getPriceDigit());
		info.setPriceToIntegerType(srcInfo.getPriceToIntegerType());
		
		if(!isChangeManage){
			info.setTackDesc(srcInfo.getTackDesc());
//			info.setDescription(srcInfo.getDescription());
			info.setInsider(srcInfo.getInsider());
			info.setSalesman(srcInfo.getSalesman());
			info.setMarketUnit(srcInfo.getMarketUnit());
			info.setCustomerNames(srcInfo.getCustomerNames());
			info.setCustomerPhone(srcInfo.getCustomerPhone());
		}else{
			info.setBizDate(srcInfo.getBizDate());
		}
	}
	public static void setCustomerListEntry(TranCustomerEntryInfo info,TranCustomerEntryInfo srcInfo){
		info.setCustomer(srcInfo.getCustomer());
		info.setSeq(srcInfo.getSeq());
		info.setName(srcInfo.getName());
		info.setTel(srcInfo.getTel());
		info.setPhone(srcInfo.getPhone());
		info.setAddress(srcInfo.getAddress());
		info.setCertificate(srcInfo.getCertificate());
		info.setCertificateNumber(srcInfo.getCertificateNumber());
		info.setPropertyPercent(srcInfo.getPropertyPercent());
		info.setIsMain(srcInfo.isIsMain());
	}
    public static SelectorItemCollection getBizSelectors(BOSObjectType bosType) {
    	SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add(new SelectorItemInfo("createTime"));
	    sic.add(new SelectorItemInfo("lastUpdateTime"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("lastUpdateUser.*"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("customerPhone"));
        sic.add(new SelectorItemInfo("room.*"));
        sic.add(new SelectorItemInfo("sellType"));
        sic.add(new SelectorItemInfo("room.roomModel.name"));
        sic.add(new SelectorItemInfo("strdTotalAmount"));
        sic.add(new SelectorItemInfo("strdBuildingPrice"));
        sic.add(new SelectorItemInfo("strdRoomPrice"));
        sic.add(new SelectorItemInfo("attachmentAmount"));
        sic.add(new SelectorItemInfo("fitmentTotalAmount"));
        sic.add(new SelectorItemInfo("isFitmentToContract"));
        sic.add(new SelectorItemInfo("fitmentStandard.*"));
        sic.add(new SelectorItemInfo("fitmentPrice"));
        sic.add(new SelectorItemInfo("recommended"));
        sic.add(new SelectorItemInfo("busAdscriptionDate"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizNumber"));
        sic.add(new SelectorItemInfo("valuationType"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("payType.*"));
        sic.add(new SelectorItemInfo("agioScheme.*"));
        sic.add(new SelectorItemInfo("agioDesc"));
        sic.add(new SelectorItemInfo("dealTotalAmount"));
        sic.add(new SelectorItemInfo("dealBuildPrice"));
        sic.add(new SelectorItemInfo("dealRoomPrice"));
        sic.add(new SelectorItemInfo("contractTotalAmount"));
        sic.add(new SelectorItemInfo("contractBuildPrice"));
        sic.add(new SelectorItemInfo("contractRoomPrice"));
        sic.add(new SelectorItemInfo("lastAgio"));
        sic.add(new SelectorItemInfo("insider.*"));
        sic.add(new SelectorItemInfo("accFundAmount"));
        sic.add(new SelectorItemInfo("loanAmount"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("bizState"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		sic.add(new SelectorItemInfo("CU.*"));
		sic.add(new SelectorItemInfo("sellProject.*"));
		sic.add(new SelectorItemInfo("srcId"));
		sic.add(new SelectorItemInfo("isEarnestInHouseAmount"));
		sic.add(new SelectorItemInfo("isAutoToInteger"));
		sic.add(new SelectorItemInfo("isBasePriceSell"));
		sic.add(new SelectorItemInfo("toIntegerType"));
		sic.add(new SelectorItemInfo("digit"));
		sic.add(new SelectorItemInfo("priceToIntegerType"));
		sic.add(new SelectorItemInfo("priceDigit"));
		sic.add(new SelectorItemInfo("isValid"));
		sic.add(new SelectorItemInfo("transactionID"));
		sic.add(new SelectorItemInfo("customerNames"));
		sic.add(new SelectorItemInfo("bulidingArea"));
		sic.add(new SelectorItemInfo("roomArea"));
		sic.add(new SelectorItemInfo("strdPlanBuildingArea"));
		sic.add(new SelectorItemInfo("strdPlanRoomArea"));
		sic.add(new SelectorItemInfo("strdActualBuildingArea"));
		sic.add(new SelectorItemInfo("strdActualRoomArea"));
		sic.add(new SelectorItemInfo("newCommerceChance.id"));
		sic.add(new SelectorItemInfo("newCommerceChance.name"));
		sic.add(new SelectorItemInfo("newCommerceChance.number"));
		sic.add(new SelectorItemInfo("newCommerceChance.customer.*"));
		sic.add(new SelectorItemInfo("saleManNames"));
		sic.add(new SelectorItemInfo("recommended"));
		sic.add(new SelectorItemInfo("room.building.joinInDate"));
	    try {
	    	if(bosType.equals(new PrePurchaseManageInfo().getBOSType())){
	    		sic.add(new SelectorItemInfo("preAmount"));
	    		
	    		sic.add(new SelectorItemInfo("prePurchasePayListEntry.*"));
	    		sic.add(new SelectorItemInfo("prePurchasePayListEntry.moneyDefine.*"));
	    		sic.add(new SelectorItemInfo("prePurchasePayListEntry.currency.*"));
	    		
	    		sic.add(new SelectorItemInfo("prePurchaseCustomerEntry.*"));
	    		sic.add(new SelectorItemInfo("prePurchaseCustomerEntry.customer.*"));
	    		sic.add(new SelectorItemInfo("prePurchaseCustomerEntry.certificate.*"));
	    		sic.add(new SelectorItemInfo("prePurchaseCustomerEntry.customer.mainCustomer.*"));
	    		
	    		sic.add(new SelectorItemInfo("prePurchaseAgioEntry.*"));
	    		sic.add(new SelectorItemInfo("prePurchaseAgioEntry.agio.*"));
	    		
	    		sic.add(new SelectorItemInfo("prePurchaseRoomAttachmentEntry.*"));
	    		sic.add(new SelectorItemInfo("prePurchaseRoomAttachmentEntry.room.*"));
	    		sic.add(new SelectorItemInfo("prePurchaseRoomAttachmentEntry.prePurchaseAgioEntry.*"));
	    		sic.add(new SelectorItemInfo("prePurchaseRoomAttachmentEntry.prePurchaseAgioEntry.agio.*"));
	    		
	    		sic.add(new SelectorItemInfo("prePurchaseSaleManEntry.*"));
	    		sic.add(new SelectorItemInfo("prePurchaseSaleManEntry.user.*"));
	    		sic.add(new SelectorItemInfo("prePurchaseSaleManEntry.user.group.name"));
	    		sic.add(new SelectorItemInfo("prePurchaseSaleManEntry.user.CU.name"));
		    }else if(bosType.equals(new PurchaseManageInfo().getBOSType())){
		    	sic.add(new SelectorItemInfo("planSignDate"));
		        sic.add(new SelectorItemInfo("actualArea"));
		        sic.add(new SelectorItemInfo("preArea"));
		        sic.add(new SelectorItemInfo("planningCompensate"));
		        sic.add(new SelectorItemInfo("cashSalesCompensate"));
		        sic.add(new SelectorItemInfo("planningArea"));
		        sic.add(new SelectorItemInfo("purAmount"));
		        
		        sic.add(new SelectorItemInfo("purPayListEntry.*"));
				sic.add(new SelectorItemInfo("purPayListEntry.moneyDefine.*"));
				sic.add(new SelectorItemInfo("purPayListEntry.currency.*"));
				
				sic.add(new SelectorItemInfo("purCustomerEntry.*"));
				sic.add(new SelectorItemInfo("purCustomerEntry.customer.*"));
				sic.add(new SelectorItemInfo("purCustomerEntry.certificate.*"));
				sic.add(new SelectorItemInfo("purCustomerEntry.customer.mainCustomer.*"));
				
				sic.add(new SelectorItemInfo("purAgioEntry.*"));
				sic.add(new SelectorItemInfo("purAgioEntry.agio.*"));
				
				sic.add(new SelectorItemInfo("purRoomAttachmentEntry.*"));
				sic.add(new SelectorItemInfo("purRoomAttachmentEntry.room.*"));
				sic.add(new SelectorItemInfo("purRoomAttachmentEntry.purAgioEntry.*"));
				sic.add(new SelectorItemInfo("purRoomAttachmentEntry.purAgioEntry.agio.*"));
				
				sic.add(new SelectorItemInfo("purSaleManEntry.user.*"));
				sic.add(new SelectorItemInfo("purSaleManEntry.user.group.name"));
				sic.add(new SelectorItemInfo("purSaleManEntry.user.CU.name"));
				sic.add(new SelectorItemInfo("purSaleManEntry.*"));
	    	}else if(bosType.equals(new SignManageInfo().getBOSType())){
	    		sic.add(new SelectorItemInfo("isWorkRoom"));
	    		
	    		sic.add(new SelectorItemInfo("sellAmount"));
	            sic.add(new SelectorItemInfo("areaCompensate"));
	            sic.add(new SelectorItemInfo("planningCompensate"));
	            sic.add(new SelectorItemInfo("planningArea"));
	            sic.add(new SelectorItemInfo("preArea"));
	            sic.add(new SelectorItemInfo("cashSalesCompensate"));
	            sic.add(new SelectorItemInfo("actualArea"));
	            sic.add(new SelectorItemInfo("joinInDate"));
	            sic.add(new SelectorItemInfo("onRecordDate"));
	            sic.add(new SelectorItemInfo("AcfBank.*"));
	            sic.add(new SelectorItemInfo("LoanBank.*"));
	            sic.add(new SelectorItemInfo("isOnRecord"));
	            sic.add(new SelectorItemInfo("specialAgio.*"));
	            
	    		sic.add(new SelectorItemInfo("signPayListEntry.*"));
	    		sic.add(new SelectorItemInfo("signPayListEntry.moneyDefine.*"));
	    		sic.add(new SelectorItemInfo("signPayListEntry.currency.*"));
	    		
	    		sic.add(new SelectorItemInfo("signCustomerEntry.*"));
	    		sic.add(new SelectorItemInfo("signCustomerEntry.customer.*"));
	    		sic.add(new SelectorItemInfo("signCustomerEntry.certificate.*"));
	    		sic.add(new SelectorItemInfo("signCustomerEntry.customer.mainCustomer.*"));
	    		
	    		sic.add(new SelectorItemInfo("signAgioEntry.*"));
	    		sic.add(new SelectorItemInfo("signAgioEntry.agio.*"));
	    		
	    		sic.add(new SelectorItemInfo("signRoomAttachmentEntry.*"));
	    		sic.add(new SelectorItemInfo("signRoomAttachmentEntry.room.*"));
	    		sic.add(new SelectorItemInfo("signRoomAttachmentEntry.signAgioEntry.*"));
	    		sic.add(new SelectorItemInfo("signRoomAttachmentEntry.signAgioEntry.agio.*"));
	    		
	    		sic.add(new SelectorItemInfo("signSaleManEntry.user.*"));
	    		sic.add(new SelectorItemInfo("signSaleManEntry.user.group.name"));
	    		sic.add(new SelectorItemInfo("signSaleManEntry.user.CU.name"));
	    		sic.add(new SelectorItemInfo("signSaleManEntry.*"));
	    	}else if(bosType.equals(new SincerityPurchaseInfo().getBOSType())){
	    		sic.add(new SelectorItemInfo("isValid"));
	            sic.add(new SelectorItemInfo("creator.*"));
	            sic.add(new SelectorItemInfo("createTime"));
	            sic.add(new SelectorItemInfo("lastUpdateUser.*"));
	            sic.add(new SelectorItemInfo("number"));
	            sic.add(new SelectorItemInfo("bizDate"));
	            sic.add(new SelectorItemInfo("description"));
	            sic.add(new SelectorItemInfo("salesman.*"));
	            sic.add(new SelectorItemInfo("sellProject.*"));
	            sic.add(new SelectorItemInfo("appointmentPeople"));
	            sic.add(new SelectorItemInfo("appointmentPhone"));
	            sic.add(new SelectorItemInfo("invalidationDate"));
	            sic.add(new SelectorItemInfo("lastUpdateTime"));
	            sic.add(new SelectorItemInfo("projectNum"));
	            sic.add(new SelectorItemInfo("room.*"));
	            sic.add(new SelectorItemInfo("sincerPriceEntrys.*"));
	    		sic.add(new SelectorItemInfo("sincerPriceEntrys.moneyDefine.*"));
	    		sic.add(new SelectorItemInfo("bizState"));
	    		sic.add(new SelectorItemInfo("orgUnit.*"));
	    		sic.add(new SelectorItemInfo("CU.*"));
	    		sic.add(new SelectorItemInfo("sellProject.*"));
	    		sic.add(new SelectorItemInfo("srcId"));
	    		sic.add(new SelectorItemInfo("isAutoToInteger"));
	    		sic.add(new SelectorItemInfo("isBasePriceSell"));
	    		sic.add(new SelectorItemInfo("toIntegerType"));
	    		sic.add(new SelectorItemInfo("digit"));
	    		sic.add(new SelectorItemInfo("isValid"));
	    		sic.add(new SelectorItemInfo("transactionID"));
	    		sic.add(new SelectorItemInfo("customer.*"));
	    		sic.add(new SelectorItemInfo("customer.customer.*"));
	    		sic.add(new SelectorItemInfo("customer.certificate.*"));
	    		sic.add(new SelectorItemInfo("newCommerceChance"));
	    		sic.add(new SelectorItemInfo("newCommerceChance.customer.*"));
	    		
	    		sic.add(new SelectorItemInfo("changeRecordEntryTwo.id"));
	    		sic.add(new SelectorItemInfo("changeRecordEntryTwo.name"));
	    		sic.add(new SelectorItemInfo("changeRecordEntryTwo.number"));
	    		sic.add(new SelectorItemInfo("changeRecordEntryTwo.description"));
	    		sic.add(new SelectorItemInfo("changeRecordEntryTwo.oldCustomer.id"));
	    		sic.add(new SelectorItemInfo("changeRecordEntryTwo.oldCustomer.name"));
	    		sic.add(new SelectorItemInfo("changeRecordEntryTwo.oldCustomer.number"));
	    		sic.add(new SelectorItemInfo("changeRecordEntryTwo.newCustomer.id"));
	    		sic.add(new SelectorItemInfo("changeRecordEntryTwo.newCustomer.name"));
	    		sic.add(new SelectorItemInfo("changeRecordEntryTwo.newCustomer.number"));
	    		sic.add(new SelectorItemInfo("changeRecordEntryTwo.remark"));
	    		sic.add(new SelectorItemInfo("changeRecordEntryTwo.changeDate"));
	    		sic.add(new SelectorItemInfo("changeRecordEntryTwo.operator.id"));
	    		sic.add(new SelectorItemInfo("changeRecordEntryTwo.operator.name"));
	    		sic.add(new SelectorItemInfo("changeRecordEntryTwo.operator.number"));
	    		sic.add(new SelectorItemInfo("changeRecordEntryTwo.oldCusStr"));
	    		sic.add(new SelectorItemInfo("changeRecordEntryTwo.newCusStr"));
	    			
	    		sic.add(new SelectorItemInfo("saleMansEntry.user.*"));
	    		sic.add(new SelectorItemInfo("saleMansEntry.user.group.name"));
	    		sic.add(new SelectorItemInfo("saleMansEntry.user.CU.name"));
	    		sic.add(new SelectorItemInfo("saleMansEntry.*"));
	    		sic.add(new SelectorItemInfo("saleManStr"));
	    		
	    		sic.add(new SelectorItemInfo("customerNames"));
	    		sic.add(new SelectorItemInfo("customerPhone"));
	    	}else if(bosType.equals(new ChooseRoomInfo().getBOSType())){
	    		sic = new SelectorItemCollection();
	    		sic.add(new SelectorItemInfo("salesMan.*"));
	    		sic.add(new SelectorItemInfo("room.*"));
	    		sic.add(new SelectorItemInfo("room.building.*"));
	    	}
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	return sic;
     }

	/**
	 * 获取交易最新单据对象
	 * @param roomId
	 * @return
	 * @throws BOSException
	 */
	public static IObjectValue getCurTransactionBill (BOSUuid roomId) throws BOSException{
		if(roomId==null) return null;
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", roomId));
		filter.getFilterItems().add(new FilterItemInfo("isValid", new Boolean(false)));
		view.setFilter(filter);
		
		TransactionCollection col=TransactionFactory.getRemoteInstance().getTransactionCollection(view);
		if(col.size()>0){
			ObjectUuidPK pk=new ObjectUuidPK(col.get(0).getBillId());
			IObjectValue objectValue=DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(),pk,getBizSelectors(pk.getObjectType()));
			return objectValue;
		}
		return null;
	}
    public static void updateRoomState(Context ctx,RoomInfo room,RoomSellStateEnum state) throws EASBizException, BOSException{
		if(room==null) return;
    	SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("sellState");
		RoomInfo changeRoom=new RoomInfo();
		changeRoom.setSellState(state);
		changeRoom.setId(room.getId());
		RoomFactory.getLocalInstance(ctx).updatePartial(changeRoom, sels);
	}
    /**
     * 删除单据时更新交易为历史交易中最新的，并且更新房间状态
     * @param ctx
     * @param transactionId
     * @throws EASBizException
     * @throws BOSException
     */
     public static RoomSellStateEnum toOldTransaction(Context ctx, BOSUuid transactionId) throws EASBizException, BOSException {
    	if(transactionId==null) return null;
    	RoomSellStateEnum roomState=RoomSellStateEnum.OnShow;
    	
    	ObjectUuidPK pk=new ObjectUuidPK(transactionId);
    	TransactionInfo oldTran=null;
    	TransactionInfo info=TransactionFactory.getLocalInstance(ctx).getTransactionInfo(pk);
    	oldTran=(TransactionInfo) info.clone();
    	
		ObjectUuidPK billpk=new ObjectUuidPK(info.getBillId());
		IObjectValue objectValue=DynamicObjectFactory.getLocalInstance(ctx).getValue(billpk.getObjectType(),billpk);
		BaseTransactionInfo baseInfo=(BaseTransactionInfo)objectValue;
		updateCommerceChanceStage(ctx,baseInfo,baseInfo.getId(),info.getCurrentLink(),info.getPreLink(),true);
		
    	if(RoomSellStateEnum.OnShow.equals(info.getPreLink())){
    		
    		if(info.getCurrentLink().equals(RoomSellStateEnum.SincerPurchase)){
    			FilterInfo filter = new  FilterInfo();
        		filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.BAYNUMBER_VALUE));
        		if(info.getRoom()!=null){
        			filter.getFilterItems().add(new FilterItemInfo("room.id",info.getRoom().getId()));
        		}
        		filter.getFilterItems().add(new FilterItemInfo("id",info.getBillId(),CompareType.NOTEQUALS));
        		
        		info.setIsValid(true);
        		
        		if(!SincerityPurchaseFactory.getLocalInstance(ctx).exists(filter)){
        	    	SelectorItemCollection sic = new SelectorItemCollection();
        	    	sic.add("isPush");
        	    	RoomInfo room=RoomFactory.getLocalInstance(ctx).getRoomInfo(new ObjectUuidPK(info.getRoom().getId()),sic);
        			if(room.isIsPush()){
        				updateRoomState(ctx,info.getRoom(),RoomSellStateEnum.OnShow);
        			}else{
        				updateRoomState(ctx,info.getRoom(),RoomSellStateEnum.Init);
        			}
        			
        			TransactionFactory.getLocalInstance(ctx).update(pk, info);
        			return null;
        		}
    		}
    		info.setIsValid(true);
//    		delTransaction(ctx,transactionId);
    		TransactionFactory.getLocalInstance(ctx).update(pk, info);
    		updateRoomState(ctx,info.getRoom(),RoomSellStateEnum.OnShow);
    		return RoomSellStateEnum.OnShow;
    	}else{
    	
    		TranStateHisCollection col=info.getTranStateHis();
    		CRMHelper.sortCollection(col, "seq", true);
    		
    		TranStateHisInfo hisInfo=info.getTranStateHis().get(info.getTranStateHis().size()-1);
    		
    		if(RoomSellStateEnum.SincerPurchase.equals(hisInfo.getCurrentLink())&&hisInfo.getHisRoom()==null&&info.getRoom()!=null){
    			updateRoomState(ctx,info.getRoom(),RoomSellStateEnum.OnShow);
    		}
        	info.setPreLink(hisInfo.getPreLink());
        	info.setCurrentLink(hisInfo.getCurrentLink());
        	info.setBillId(hisInfo.getHisBillID());
        	info.setCustomerNames(hisInfo.getCustomerNames());
        	info.setTranDate(hisInfo.getTranDate());
        	info.setRoom(hisInfo.getHisRoom());
    		info.getTranStateHis().remove(info.getTranStateHis().get(info.getTranStateHis().size()-1));
    		
    		if(RoomSellStateEnum.PrePurchase.equals(info.getCurrentLink())||RoomSellStateEnum.Purchase.equals(info.getCurrentLink())
    				||RoomSellStateEnum.Sign.equals(info.getCurrentLink())||RoomSellStateEnum.SincerPurchase.equals(info.getCurrentLink())){
    			roomState=info.getCurrentLink();
    		}else{
    			roomState=info.getPreLink();
    		}
    		info.getTranBusinessOverView().clear();
    		for(int i=0;i<hisInfo.getTranBusinessOverView().size();i++){
    			TranBusinessOverViewInfo oldInfo=(TranBusinessOverViewInfo) hisInfo.getTranBusinessOverView().get(i).clone();
    			oldInfo.setHisHead(null);
    			
    			if(RoomSellStateEnum.PrePurchase.equals(info.getCurrentLink())||RoomSellStateEnum.Purchase.equals(info.getCurrentLink())
        				||RoomSellStateEnum.Sign.equals(info.getCurrentLink())||RoomSellStateEnum.SincerPurchase.equals(info.getCurrentLink())
        					||RoomSellStateEnum.priceChange.equals(info.getCurrentLink())){
    				oldInfo.setActRevAmount(FDCHelper.ZERO);
    			}
    			oldInfo.setId(hisInfo.getTranBusinessOverView().get(i).getTanPayListEntryId());
    			
    			info.getTranBusinessOverView().add(oldInfo);
    		}
    	}
    	TransactionFactory.getLocalInstance(ctx).update(pk, info);
    	
    	if(RoomSellStateEnum.PrePurchase.equals(info.getCurrentLink())||RoomSellStateEnum.Purchase.equals(info.getCurrentLink())
				||RoomSellStateEnum.Sign.equals(info.getCurrentLink())||RoomSellStateEnum.SincerPurchase.equals(info.getCurrentLink())
					||RoomSellStateEnum.priceChange.equals(info.getCurrentLink())){
        	info=TransactionFactory.getLocalInstance(ctx).getTransactionInfo(pk);
        	SHERevBillFactory.getLocalInstance(ctx).whenTransEntryChange(oldTran, info);
    	}
		updateRoomState(ctx,info.getRoom(),roomState);
		return roomState;
    }
    public static void whenTransEntryChange(Context ctx,BOSUuid transactionId) throws EASBizException, BOSException{
    	ObjectUuidPK pk=new ObjectUuidPK(transactionId);
    	
    	TransactionInfo info=TransactionFactory.getLocalInstance(ctx).getTransactionInfo(pk);
    	TransactionInfo oldTran=(TransactionInfo)info.clone();
    	
    	for(int i=0;i<info.getTranBusinessOverView().size();i++){
    		if(info.getTranBusinessOverView().get(i).getType().equals(BusTypeEnum.PAY)){
    			info.getTranBusinessOverView().get(i).setActRevAmount(FDCHelper.ZERO);
    		}
    	}
    	TransactionFactory.getLocalInstance(ctx).update(pk, info);
    	info=TransactionFactory.getLocalInstance(ctx).getTransactionInfo(pk);
    	
    	SHERevBillFactory.getLocalInstance(ctx).whenTransEntryChange(oldTran, info);
    }
    public static IBaseTransaction getBizInterface(Context ctx,IObjectValue objectValue) throws BOSException{
    	if(objectValue instanceof SincerityPurchaseInfo){
			return SincerityPurchaseFactory.getLocalInstance(ctx);
		}
    	if(objectValue instanceof PrePurchaseManageInfo){
			return PrePurchaseManageFactory.getLocalInstance(ctx);
		}
		if(objectValue instanceof PurchaseManageInfo){
			return PurchaseManageFactory.getLocalInstance(ctx);
		}
		if(objectValue instanceof SignManageInfo){
			return SignManageFactory.getLocalInstance(ctx);
		}
		return null;
	}
    private static void addTranBusinessOverViewPayList(Context ctx, TransactionInfo info,BaseTransactionInfo baseInfo) throws EASBizException, BOSException{
    	if(baseInfo instanceof SincerityPurchaseInfo){
    		for(int i=0;i<((SincerityPurchaseInfo)baseInfo).getSincerPriceEntrys().size();i++){
				TranBusinessOverViewInfo bo=new TranBusinessOverViewInfo();
				BOSUuid boid=BOSUuid.create(bo.getBOSType());
				bo.setId(boid);
				bo.setType(BusTypeEnum.PAY);
				setPayListEntry(bo,((SincerityPurchaseInfo)baseInfo).getSincerPriceEntrys().get(i),false);
				
				bo.setFinishDate(((SincerityPurchaseInfo)baseInfo).getSincerPriceEntrys().get(i).getAppDate());
				bo.setDesc(((SincerityPurchaseInfo)baseInfo).getSincerPriceEntrys().get(i).getDescription());
				
				setTranBusinessOverViewBusinessName(ctx,bo,((SincerityPurchaseInfo)baseInfo).getSincerPriceEntrys().get(i));
				info.getTranBusinessOverView().add(bo);
				
				((SincerityPurchaseInfo)baseInfo).getSincerPriceEntrys().get(i).setTanPayListEntryId(boid);
			}
		}
    	if(baseInfo instanceof PrePurchaseManageInfo){
    		for(int i=0;i<((PrePurchaseManageInfo)baseInfo).getPrePurchasePayListEntry().size();i++){
    			TranBusinessOverViewInfo bo=new TranBusinessOverViewInfo();
    			BOSUuid boid=BOSUuid.create(bo.getBOSType());
    			bo.setId(boid);
    			bo.setType(BusTypeEnum.PAY);
    			setPayListEntry(bo,((PrePurchaseManageInfo)baseInfo).getPrePurchasePayListEntry().get(i),false);
    			
    			bo.setFinishDate(((PrePurchaseManageInfo)baseInfo).getPrePurchasePayListEntry().get(i).getAppDate());
				bo.setDesc(((PrePurchaseManageInfo)baseInfo).getPrePurchasePayListEntry().get(i).getDescription());
				
    			setTranBusinessOverViewBusinessName(ctx,bo,((PrePurchaseManageInfo)baseInfo).getPrePurchasePayListEntry().get(i));
    			info.getTranBusinessOverView().add(bo);
    			
    			((PrePurchaseManageInfo)baseInfo).getPrePurchasePayListEntry().get(i).setTanPayListEntryId(boid);
    		}
		}
		if(baseInfo instanceof PurchaseManageInfo){
			for(int i=0;i<((PurchaseManageInfo)baseInfo).getPurPayListEntry().size();i++){
				TranBusinessOverViewInfo bo=new TranBusinessOverViewInfo();
				BOSUuid boid=BOSUuid.create(bo.getBOSType());
				bo.setId(boid);
				bo.setType(BusTypeEnum.PAY);
				setPayListEntry(bo,((PurchaseManageInfo)baseInfo).getPurPayListEntry().get(i),false);
			
				bo.setFinishDate(((PurchaseManageInfo)baseInfo).getPurPayListEntry().get(i).getAppDate());
				bo.setDesc(((PurchaseManageInfo)baseInfo).getPurPayListEntry().get(i).getDescription());
				
				setTranBusinessOverViewBusinessName(ctx,bo,((PurchaseManageInfo)baseInfo).getPurPayListEntry().get(i));
				info.getTranBusinessOverView().add(bo);
				
				((PurchaseManageInfo)baseInfo).getPurPayListEntry().get(i).setTanPayListEntryId(boid);
			}
		}
		if(baseInfo instanceof SignManageInfo){
			for(int i=0;i<((SignManageInfo)baseInfo).getSignPayListEntry().size();i++){
				TranBusinessOverViewInfo bo=new TranBusinessOverViewInfo();
				BOSUuid boid=BOSUuid.create(bo.getBOSType());
				bo.setId(boid);
				bo.setType(BusTypeEnum.PAY);
				setPayListEntry(bo,((SignManageInfo)baseInfo).getSignPayListEntry().get(i),false);
				
				bo.setFinishDate(((SignManageInfo)baseInfo).getSignPayListEntry().get(i).getAppDate());
				bo.setDesc(((SignManageInfo)baseInfo).getSignPayListEntry().get(i).getDescription());
				
				setTranBusinessOverViewBusinessName(ctx,bo,((SignManageInfo)baseInfo).getSignPayListEntry().get(i));
				info.getTranBusinessOverView().add(bo);
				
				((SignManageInfo)baseInfo).getSignPayListEntry().get(i).setTanPayListEntryId(boid);
			}
		}
    }
    
    private static void addTranBusinessOverViewBizList(Context ctx, TransactionInfo info,BaseTransactionInfo baseInfo) throws BOSException{
    	SHEPayTypeInfo payType = baseInfo.getPayType();
    	if (payType != null && payType.getId() != null) {
    		BizListEntryCollection bizList = BizListEntryFactory.getLocalInstance(ctx).getBizListEntryCollection("select * where head.id='"+payType.getId().toString()+ "'");
			for (int i = 0; i < bizList.size(); i++) {
				TranBusinessOverViewInfo bo=new TranBusinessOverViewInfo();
				BOSUuid boid=BOSUuid.create(bo.getBOSType());
				bo.setId(boid);
				bo.setType(BusTypeEnum.BUSINESS);
				setBizListEntry(ctx,baseInfo,bo,bizList.get(i));
				info.getTranBusinessOverView().add(bo);
			}
		}
    	updateCurrentBizStateToTranBusiness(info,baseInfo);
    }
    private static BOSUuid updatePayList(Context ctx,TransactionInfo info,TranPayListEntryInfo entry) throws EASBizException, BOSException{
    	TranBusinessOverViewInfo bo=null;
    	BOSUuid bosid=null;
    	if(entry.getTanPayListEntryId()==null){
			bo=new TranBusinessOverViewInfo();
			bosid=BOSUuid.create(bo.getBOSType());
			bo.setId(bosid);
			bo.setType(BusTypeEnum.PAY);
			setPayListEntry(bo,entry,false);
			
			bo.setFinishDate(entry.getAppDate());
			bo.setDesc(entry.getDescription());
			
			setTranBusinessOverViewBusinessName(ctx,bo,entry);
			info.getTranBusinessOverView().add(bo);
			
			entry.setTanPayListEntryId(bosid);
		}else{
			bosid=entry.getTanPayListEntryId();
			bo=info.getTranBusinessOverView().get(bosid);
			if(bo==null){
				bo=new TranBusinessOverViewInfo();
				info.getTranBusinessOverView().add(bo);
			}
			bosid=BOSUuid.create(bo.getBOSType());
			bo.setId(bosid);
			bo.setType(BusTypeEnum.PAY);
			setPayListEntry(bo,entry,false);
			
			bo.setFinishDate(entry.getAppDate());
			bo.setDesc(entry.getDescription());
			
			setTranBusinessOverViewBusinessName(ctx,bo,entry);
			
			entry.setTanPayListEntryId(bosid);
		}
    	return bosid;
    }

    /**
     * 转预订，转认购，转签约，特殊业务
     * 转预订，转认购，转签约，特殊业务时候，付款明细分录都需要带入先状态的付款明细的TanPayListEntryId
     * @param ctx
     * @param info
     * @param baseInfo
     * @throws BOSException 
     * @throws EASBizException 
     */
    private static void updateTranBusinessOverViewPayList(Context ctx, TransactionInfo info,BaseTransactionInfo baseInfo) throws EASBizException, BOSException{
    	List removeInfo=new ArrayList();
    	List baseTranID=new ArrayList();
    	if(baseInfo instanceof SincerityPurchaseInfo){
    		SincerReceiveEntryCollection col=((SincerityPurchaseInfo)baseInfo).getSincerPriceEntrys();
    		for(int i=0;i<col.size();i++){
    			baseTranID.add(updatePayList(ctx,info,col.get(i)));
			}
		}
    	if(baseInfo instanceof PrePurchaseManageInfo){
    		PrePurchasePayListEntryCollection col=((PrePurchaseManageInfo)baseInfo).getPrePurchasePayListEntry();
    		for(int i=0;i<col.size();i++){
    			baseTranID.add(updatePayList(ctx,info,col.get(i)));
			}
		}
		if(baseInfo instanceof PurchaseManageInfo){
			PurPayListEntryCollection col=((PurchaseManageInfo)baseInfo).getPurPayListEntry();
    		for(int i=0;i<col.size();i++){
    			baseTranID.add(updatePayList(ctx,info,col.get(i)));
			}
		}
		if(baseInfo instanceof SignManageInfo){
			SignPayListEntryCollection col=((SignManageInfo)baseInfo).getSignPayListEntry();
    		for(int i=0;i<col.size();i++){
    			baseTranID.add(updatePayList(ctx,info,col.get(i)));
			}
		}
		if(info.getTranBusinessOverView().size()>baseTranID.size()){
			for(int i=0;i<info.getTranBusinessOverView().size();i++){
				boolean isRemove=true;
				for(int j=0;j<baseTranID.size();j++){
					if(!info.getTranBusinessOverView().get(i).getType().equals(BusTypeEnum.PAY)|| info.getTranBusinessOverView().get(i).getId().toString().equals(baseTranID.get(j).toString())){
						isRemove=false;
						continue;
					}
				}
				if(isRemove){
					removeInfo.add(info.getTranBusinessOverView().get(i));
				}
			}
		}
		for(int i=0;i<removeInfo.size();i++){
			info.getTranBusinessOverView().remove((TranBusinessOverViewInfo)removeInfo.get(i));
		}
    }
    
    
    
    private static void updateTranBusinessOverViewBizList(Context ctx, TransactionInfo info,BaseTransactionInfo baseInfo)throws BOSException{
    	removeNotFinishedBizList(info);
    	updateCurrentBizStateToTranBusiness(info,baseInfo);
    	updateBizListToTranBusiness(ctx,info,baseInfo);
    }
    
    private  static void removeNotFinishedBizList(TransactionInfo info){
    	List removeInfo=new ArrayList();
    	for(int i=0;i<info.getTranBusinessOverView().size();i++){
    		if(info.getTranBusinessOverView().get(i).getType().equals(BusTypeEnum.BUSINESS) && !info.getTranBusinessOverView().get(i).isIsFinish()){
    			removeInfo.add(info.getTranBusinessOverView().get(i));
    		}
    	}
    	for(int i=0;i<removeInfo.size();i++){
			info.getTranBusinessOverView().remove((TranBusinessOverViewInfo)removeInfo.get(i));
		}
    }
    
    private static void updateCurrentBizStateToTranBusiness(TransactionInfo info,BaseTransactionInfo baseInfo){
    	if(baseInfo instanceof PrePurchaseManageInfo){
    		boolean isExit=false;
    		TranBusinessOverViewInfo tranBusiness = null;
    		for(int i=0;i<info.getTranBusinessOverView().size();i++){
    			tranBusiness = info.getTranBusinessOverView().get(i);
        		if(tranBusiness.getType().equals(BusTypeEnum.BUSINESS) && SHEManageHelper.PRELIMINARY.equals(tranBusiness.getBusinessName())){
        			tranBusiness.setIsFinish(true);
        			tranBusiness.setFinishDate(baseInfo.getBizDate());
        			tranBusiness.setActualFinishDate(baseInfo.getBizDate());
        			isExit = true;
        			break;
        		}
        	}
    		if(!isExit){
    			TranBusinessOverViewInfo bo=new TranBusinessOverViewInfo();
				BOSUuid boid=BOSUuid.create(bo.getBOSType());
				bo.setId(boid);
				bo.setType(BusTypeEnum.BUSINESS);
//				bo.setBusinessName(BusinessTypeNameEnum.PRELIMINARY);
				bo.setBusinessName(SHEManageHelper.PRELIMINARY);
				bo.setDesc(baseInfo.getDescription());
				bo.setIsFinish(true);
	    		bo.setFinishDate(baseInfo.getBizDate());
	    		bo.setActualFinishDate(baseInfo.getBizDate());
	    		info.getTranBusinessOverView().add(bo);
    		}
		}
		if(baseInfo instanceof PurchaseManageInfo){
			boolean isExit=false;
    		TranBusinessOverViewInfo tranBusiness = null;
    		for(int i=0;i<info.getTranBusinessOverView().size();i++){
    			tranBusiness = info.getTranBusinessOverView().get(i);
        		if(tranBusiness.getType().equals(BusTypeEnum.BUSINESS) && SHEManageHelper.PURCHASE.equals(tranBusiness.getBusinessName())){
        			tranBusiness.setIsFinish(true);
        			tranBusiness.setFinishDate(baseInfo.getBizDate());
        			tranBusiness.setActualFinishDate(baseInfo.getBizDate());
        			isExit = true;
        			break;
        		}
        	}
    		if(!isExit){
    			TranBusinessOverViewInfo bo=new TranBusinessOverViewInfo();
				BOSUuid boid=BOSUuid.create(bo.getBOSType());
				bo.setId(boid);
				bo.setType(BusTypeEnum.BUSINESS);
//				bo.setBusinessName(BusinessTypeNameEnum.PURCHASE);
				bo.setBusinessName(SHEManageHelper.PURCHASE);
				bo.setDesc(baseInfo.getDescription());
				bo.setIsFinish(true);
	    		bo.setFinishDate(baseInfo.getBizDate());
	    		bo.setActualFinishDate(baseInfo.getBizDate());
	    		info.getTranBusinessOverView().add(bo);
    		}
		}
		if(baseInfo instanceof SignManageInfo){
			boolean isExit=false;
    		TranBusinessOverViewInfo tranBusiness = null;
    		for(int i=0;i<info.getTranBusinessOverView().size();i++){
    			tranBusiness = info.getTranBusinessOverView().get(i);
        		if(tranBusiness.getType().equals(BusTypeEnum.BUSINESS) && SHEManageHelper.SIGN.equals(tranBusiness.getBusinessName())){
        			tranBusiness.setIsFinish(true);
        			tranBusiness.setFinishDate(baseInfo.getBizDate());
        			tranBusiness.setActualFinishDate(baseInfo.getBizDate());
        			isExit = true;
        			break;
        		}
        	}
    		if(!isExit){
    			TranBusinessOverViewInfo bo=new TranBusinessOverViewInfo();
				BOSUuid boid=BOSUuid.create(bo.getBOSType());
				bo.setId(boid);
				bo.setType(BusTypeEnum.BUSINESS);
//				bo.setBusinessName(BusinessTypeNameEnum.SIGN);
				bo.setBusinessName(SHEManageHelper.SIGN);
				bo.setDesc(baseInfo.getDescription());
				bo.setIsFinish(true);
	    		bo.setFinishDate(baseInfo.getBizDate());
	    		bo.setActualFinishDate(baseInfo.getBizDate());
	    		info.getTranBusinessOverView().add(bo);
    		}
		}
    }
    
    private  static void updateBizListToTranBusiness(Context ctx, TransactionInfo info,BaseTransactionInfo baseInfo) throws BOSException{
    	SHEPayTypeInfo payType = baseInfo.getPayType();
    	if (payType != null && payType.getId() != null) {
    		BizListEntryCollection bizList = BizListEntryFactory.getLocalInstance(ctx).getBizListEntryCollection("select * where head.id='"+payType.getId().toString()+ "'");
			for (int i = 0; i < bizList.size(); i++) {
				updateBizToTranBusiness(ctx,info,baseInfo,bizList.get(i));
			}
		}
    }
    
    private static void updateBizToTranBusiness(Context ctx, TransactionInfo info,BaseTransactionInfo baseInfo,BizListEntryInfo srcInfo) throws BOSException{
    	int monthLimit = srcInfo.getMonthLimit();
		int dayLimit = srcInfo.getDayLimit();
		Date appDate = srcInfo.getAppDate();
		String payTypeBizTime = srcInfo.getPayTypeBizTime();
		BizNewFlowEnum bizFlow = srcInfo.getPayTypeBizFlow();
		TranBusinessOverViewInfo tranBusinessInfo= null;
		boolean isExit = false;
		for (int i = 0; i < info.getTranBusinessOverView().size(); i++) {
			tranBusinessInfo = info.getTranBusinessOverView().get(i);
			if(SHEManageHelper.PRELIMINARY.equals(tranBusinessInfo.getBusinessName()) && BizNewFlowEnum.PREREGISTER.equals(bizFlow)){
				isExit = true;
				break;
			}else if(SHEManageHelper.PURCHASE.equals(tranBusinessInfo.getBusinessName()) && BizNewFlowEnum.PURCHASE.equals(bizFlow)){
				isExit = true;
				break;
			}else if(SHEManageHelper.SIGN.equals(tranBusinessInfo.getBusinessName()) && BizNewFlowEnum.SIGN.equals(bizFlow)){
				isExit = true;
				break;
			}else if(SHEManageHelper.MORTGAGE.equals(tranBusinessInfo.getBusinessName()) && BizNewFlowEnum.LOAN.equals(bizFlow)){
				isExit = true;
				break;
			}else if(SHEManageHelper.ACCFUND.equals(tranBusinessInfo.getBusinessName()) && BizNewFlowEnum.ACCFUND.equals(bizFlow)){
				isExit = true;
				break;
			}else if(SHEManageHelper.INTEREST.equals(tranBusinessInfo.getBusinessName()) && BizNewFlowEnum.PROPERTY.equals(bizFlow)){
				isExit = true;
				break;
			}else if(SHEManageHelper.JOIN.equals(tranBusinessInfo.getBusinessName()) && BizNewFlowEnum.JOIN.equals(bizFlow)){
				isExit = true;
				break;
			}else if(SHEManageHelper.AREACOMPENSATE.equals(tranBusinessInfo.getBusinessName()) && BizNewFlowEnum.AREACOMPENSAGTE.equals(bizFlow)){
				isExit = true;
				break;
			}
		}
		if (!isExit) {
			TranBusinessOverViewInfo bo=new TranBusinessOverViewInfo();
			BOSUuid boid=BOSUuid.create(bo.getBOSType());
			bo.setId(boid);
			bo.setType(BusTypeEnum.BUSINESS);
			setBizListEntry(ctx,baseInfo,bo,srcInfo);
			info.getTranBusinessOverView().add(bo);
		}
		
    }
   
    /**
     * 新增单据时候，新增交易和交易历史(预约排号转约定，转认购,转签约时，需要把交易ID带入单据。当预约，认购，签约新增
     * 时候，如果房间是排号的则不带入交易ID，其他都需要带入交易ID)
     * @param ctx
     * @param transactionId
     * @param roomId
     * @param billId
     * @param currentLink
     * @param tranDate
     * @return
     * @throws EASBizException
     * @throws BOSException
     */
    public static void updateTransaction(Context ctx, BaseTransactionInfo baseInfo,RoomSellStateEnum currentLink,boolean isUpdatePayList) throws EASBizException, BOSException {
    	if(baseInfo==null) return;
    	BOSUuid transactionId=baseInfo.getTransactionID();
		Date tranDate=baseInfo.getBizDate();
		String customerNames=baseInfo.getCustomerNames();
		BOSUuid billId=baseInfo.getId();
    	TransactionInfo info=null;
    	TranStateHisInfo hisInfo=null;
    	RoomSellStateEnum pLink=null;
    	ObjectUuidPK pk=null;
    	boolean isAddNew=false;
    	
    	TransactionInfo oldTran=null;
    	if(transactionId!=null){
    		pk=new ObjectUuidPK(transactionId);
    		info=TransactionFactory.getLocalInstance(ctx).getTransactionInfo(pk);
    		if(!currentLink.equals(info.getCurrentLink())){
    			pLink=info.getCurrentLink();
    			
    			if(RoomSellStateEnum.SincerPurchase.equals(currentLink)&&TransactionStateEnum.WAITTINGFORDEAL.equals(baseInfo.getBizState())){
    				info.setIsValid(false);
    			}
    			
				hisInfo=new TranStateHisInfo();
				hisInfo.setSeq(info.getTranStateHis().size()+1);
				hisInfo.setHisBillID(info.getBillId());
				hisInfo.setCurrentLink(info.getCurrentLink());
				hisInfo.setPreLink(info.getPreLink());
				hisInfo.setTranDate(info.getTranDate());
				hisInfo.setCustomerNames(info.getCustomerNames());
				hisInfo.setHisRoom(info.getRoom());
				
				for(int i=0;i<info.getTranBusinessOverView().size();i++){
					TranBusinessOverViewInfo hisBusInfo=(TranBusinessOverViewInfo)info.getTranBusinessOverView().get(i).clone();
					hisBusInfo.setId(null);
					hisBusInfo.setHead(null);
					hisBusInfo.setTanPayListEntryId(info.getTranBusinessOverView().get(i).getId());
					
					hisInfo.getTranBusinessOverView().add(hisBusInfo);
				}
				info.getTranStateHis().add(hisInfo);
				
    		}else{
    			pLink=info.getPreLink();
    		}
    		oldTran=(TransactionInfo)info.clone();
    		
    		if(isUpdatePayList){
    			updateTranBusinessOverViewPayList(ctx,info,baseInfo);
        		updateTranBusinessOverViewBizList(ctx,info,baseInfo);
    		}
    		updateCommerceChanceStage(ctx,baseInfo,baseInfo.getId(),pLink,currentLink,false);
    	}else{
    		isAddNew=true;
    		info=new TransactionInfo();
			transactionId=BOSUuid.create(info.getBOSType());
			pLink=RoomSellStateEnum.OnShow;
			info.setIsValid(false);
			
			addTranBusinessOverViewPayList(ctx,info,baseInfo);
			addTranBusinessOverViewBizList(ctx,info,baseInfo);
			
			updateCommerceChanceStage(ctx,baseInfo,baseInfo.getId(),pLink,currentLink,false);
    	}
    	info.setId(transactionId);
		info.setPreLink(pLink);
		info.setCurrentLink(currentLink);
		info.setBillId(billId);
		info.setTranDate(tranDate);
		info.setRoom(baseInfo.getRoom());
		
		//增加客户商机
		if(baseInfo.getNewCommerceChance()!=null){
			info.setCommerceChance(baseInfo.getNewCommerceChance());
		}
		info.setCustomerNames(customerNames);
		
		if(isAddNew){
			TransactionFactory.getLocalInstance(ctx).addnew(info);
			baseInfo.setTransactionID(transactionId);
		}else{
			TransactionFactory.getLocalInstance(ctx).update(pk, info);
			
			if(isUpdatePayList){
				info=TransactionFactory.getLocalInstance(ctx).getTransactionInfo(pk);
//				交易业务总览信息变化时，需要重新建立对冲关系
				SHERevBillFactory.getLocalInstance(ctx).whenTransEntryChange(oldTran, info);
			}
		}
		getBizInterface(ctx,baseInfo).update(new ObjectUuidPK(billId), baseInfo);
    }
    
    
    /**
     * 新增或编辑单据时，新增或更新交易业务总览的业务明细和付款明细
     * @param ctx
     * @param billId
     * @param info
     * @param isAddNew
     * @return
     * @throws EASBizException
     * @throws BOSException
     */
    public static void updateTranBusinessOverView(Context ctx,BOSUuid roomId,BOSUuid billId,TransactionInfo info,boolean isAddNew)throws EASBizException, BOSException{
    	RoomInfo room = RoomFactory.getLocalInstance(ctx).getRoomInfo(new ObjectUuidPK(roomId));
    	ObjectUuidPK pk=new ObjectUuidPK(billId);
		IObjectValue objectValue=DynamicObjectFactory.getLocalInstance(ctx).getValue(pk.getObjectType(),pk);
		TranBusinessOverViewCollection tranBusinessCol = null;
		if(objectValue instanceof PrePurchaseManageInfo){
			PrePurchaseManageInfo prePurchase = null;
			PrePurchasePayListEntryCollection entry = null;
			prePurchase = (PrePurchaseManageInfo)objectValue;
			
			if(prePurchase.getPrePurchasePayListEntry().size() == 0){
				return ;
			}else{
				//得到交易的业务总览中已经完成的业务明细和付款明细
				tranBusinessCol = getFinishListEntry(info);
				//将业务单据的付款明细更新到交易的付款明细中
				entry = prePurchase.getPrePurchasePayListEntry();
				PrePurchasePayListEntryInfo prePurPayListEntryInfo = null;
				MoneyDefineInfo purMoneyDefine = null;
				BigDecimal purAppAmount=FDCHelper.ZERO;
				String purDescription = null;
				Date purAppDate = null;
				for(int i = 0;i < entry.size(); i++){
					prePurPayListEntryInfo = entry.get(i);
					purMoneyDefine = prePurPayListEntryInfo.getMoneyDefine();
					purAppAmount = prePurPayListEntryInfo.getAppAmount();
					purAppDate = prePurPayListEntryInfo.getAppDate();
					purDescription = prePurPayListEntryInfo.getDescription();
					//将业务单据的付款明细更新到交易的付款明细中
					updatePayToTranPay(info,tranBusinessCol,purMoneyDefine,purAppAmount,purAppDate,purDescription);
				}
				
				
			}
			SHEPayTypeInfo payType = prePurchase.getPayType();
			Date bizDate =  prePurchase.getBizDate();
			String description = prePurchase.getDescription();
			
			updateBizToTranBiz(ctx,payType,tranBusinessCol,info,room,isAddNew,bizDate,description);
			//如果单据没有付款方案就没有业务明细，只需要将该单据类型加到交易的业务明细中
			//将单据的付款方案的业务明细更新到交易的业务明细中
			
			info.getTranBusinessOverView().clear();
			info.getTranBusinessOverView().addCollection(tranBusinessCol);
		}
		
		if(objectValue instanceof PurchaseManageInfo){
			PurchaseManageInfo purchase = null;
			PurPayListEntryCollection entry = null;
			purchase = (PurchaseManageInfo)objectValue;
			if(purchase.getPurPayListEntry().size() ==0){
				return;
			}else{
				//得到交易的业务总览中已经完成的业务明细和付款明细
				tranBusinessCol = getFinishListEntry(info);
				//将业务单据的付款明细更新到交易的付款明细中
				entry = purchase.getPurPayListEntry();
				PurPayListEntryInfo purPayListEntryInfo = null;
				MoneyDefineInfo purMoneyDefine = null;
				BigDecimal purAppAmount=FDCHelper.ZERO;
				String purDescription = null;
				Date purAppDate = null;
				for(int i = 0;i < entry.size(); i++){
					purPayListEntryInfo = entry.get(i);
					purMoneyDefine = purPayListEntryInfo.getMoneyDefine();
					purAppAmount = purPayListEntryInfo.getAppAmount();
					purAppDate = purPayListEntryInfo.getAppDate();
					purDescription = purPayListEntryInfo.getDescription();
					//将业务单据的付款明细更新到交易的付款明细中
					updatePayToTranPay(info,tranBusinessCol,purMoneyDefine,purAppAmount,purAppDate,purDescription);
				}
				
				
			}
			SHEPayTypeInfo payType = purchase.getPayType();
			Date bizDate =  purchase.getBizDate();
			String description = purchase.getDescription();
			
			updateBizToTranBiz(ctx,payType,tranBusinessCol,info,room,isAddNew,bizDate,description);
			//如果单据没有付款方案就没有业务明细，只需要将该单据类型加到交易的业务明细中
			//将单据的付款方案的业务明细更新到交易的业务明细中
			
			info.getTranBusinessOverView().clear();
			info.getTranBusinessOverView().addCollection(tranBusinessCol);
		}
		if(objectValue instanceof SignManageInfo){
			SignManageInfo signInfo = null;
			SignPayListEntryCollection entry = null;
			signInfo = (SignManageInfo)objectValue;
			if(((SignManageInfo)objectValue).getSignPayListEntry().size() == 0){
				return;
			}else{
				//得到交易的业务总览中已经完成的业务明细和付款明细
				tranBusinessCol = getFinishListEntry(info);
				//将业务单据的付款明细更新到交易的付款明细中
				entry = signInfo.getSignPayListEntry();
				SignPayListEntryInfo signPayListEntryInfo = null;
				MoneyDefineInfo purMoneyDefine = null;
				BigDecimal purAppAmount=FDCHelper.ZERO;
				String purDescription = null;
				Date purAppDate = null;
				for(int i = 0;i < entry.size(); i++){
					signPayListEntryInfo = entry.get(i);
					purMoneyDefine = signPayListEntryInfo.getMoneyDefine();
					purAppAmount = signPayListEntryInfo.getAppAmount();
					purAppDate = signPayListEntryInfo.getAppDate();
					purDescription = signPayListEntryInfo.getDescription();
					//将业务单据的付款明细更新到交易的付款明细中
					updatePayToTranPay(info,tranBusinessCol,purMoneyDefine,purAppAmount,purAppDate,purDescription);
				}
				
				
			}
			SHEPayTypeInfo payType = signInfo.getPayType();
			Date bizDate =  signInfo.getBizDate();
			String description = signInfo.getDescription();
			
			updateBizToTranBiz(ctx,payType,tranBusinessCol,info,room,isAddNew,bizDate,description);
			//如果单据没有付款方案就没有业务明细，只需要将该单据类型加到交易的业务明细中
			//将单据的付款方案的业务明细更新到交易的业务明细中
			
			info.getTranBusinessOverView().clear();
			info.getTranBusinessOverView().addCollection(tranBusinessCol);
		}
    }
    
    /**
     * 得到交易的业务总览中已经完成的业务明细和付款明细
     * @param info
     * @return TranBusinessOverViewCollection
     */
    public static TranBusinessOverViewCollection getFinishListEntry(TransactionInfo info){
    	TranBusinessOverViewCollection tranBusinessCol = null;
    	tranBusinessCol = info.getTranBusinessOverView();
    	TranBusinessOverViewCollection deleteTanBusinessCol = new TranBusinessOverViewCollection();
    	TranBusinessOverViewInfo tranBusinessInfo = null;
    	for (int i = 0; i < tranBusinessCol.size() ; i++) {
			tranBusinessInfo = tranBusinessCol.get(i);
			BusTypeEnum type = tranBusinessInfo.getType();
			boolean isFinish = tranBusinessInfo.isIsFinish();
			BigDecimal actRevAmount=FDCHelper.ZERO;
			actRevAmount = tranBusinessInfo.getActRevAmount();
			//如果是业务明细且没有完成给删除
			if(BusTypeEnum.BUSINESS.equals(type) && isFinish==false){
				deleteTanBusinessCol.add(tranBusinessInfo);
				continue;
			}
			//如果是付款明细且实际收款金额为零给删除
			if(BusTypeEnum.PAY.equals(type) && actRevAmount==FDCHelper.ZERO){
				deleteTanBusinessCol.add(tranBusinessInfo);
			}
		}
    	for(int i=0;i<deleteTanBusinessCol.size();i++){
			//为已经完成的业务明细和付款明细
			tranBusinessCol.remove(deleteTanBusinessCol.get(i));
		}
    	return tranBusinessCol;
    }
    
    /**
     * 将付款方案的付款明细更新到业务总览的付款明细
     * @param info
     * @return tranBusinessCol
     * @param purMoneyDefine
     * @return purAppAmount
     * @param purAppDate
     * @param purDescription
     */
    public static void updatePayToTranPay(TransactionInfo info,TranBusinessOverViewCollection tranBusinessCol,MoneyDefineInfo purMoneyDefine,BigDecimal purAppAmount,Date purAppDate,String purDescription){
    		MoneyTypeEnum purMoneyType = purMoneyDefine.getMoneyType();
    		TranBusinessOverViewInfo tranOldBusinessInfo = null;
    		MoneyDefineInfo tranMoneyDefine = null;
    		MoneyTypeEnum tranMoneyType = null;
			for (int j = 0; j < tranBusinessCol.size(); j++) {
				
				tranOldBusinessInfo = tranBusinessCol.get(j);
				tranMoneyDefine = tranOldBusinessInfo.getMoneyDefine();
				tranMoneyType = tranMoneyDefine.getMoneyType();
				if(purMoneyType.equals(tranMoneyType)){
					tranOldBusinessInfo.setAppAmount(purAppAmount);
				}else{
					TranBusinessOverViewInfo addTranBusinessOverViewInfo = new TranBusinessOverViewInfo();
					addTranBusinessOverViewInfo.setId(BOSUuid.create(addTranBusinessOverViewInfo.getBOSType()));
					addTranBusinessOverViewInfo.setAppDate(purAppDate);
					addTranBusinessOverViewInfo.setBusinessName(tranMoneyDefine.getName());
//					if(purMoneyType.EarnestMoney.equals(purMoneyType)){
//						addTranBusinessOverViewInfo.setBusinessName(BusinessTypeNameEnum.PURCHASEAMOUNT);
//					}else if(purMoneyType.FisrtAmount.equals(purMoneyType)){
//						addTranBusinessOverViewInfo.setBusinessName(BusinessTypeNameEnum.FISRTAMOUNT);
//					}else if(purMoneyType.HouseAmount.equals(purMoneyType)){
//						addTranBusinessOverViewInfo.setBusinessName(BusinessTypeNameEnum.HOUSEAMOUNT);
//					}else if(purMoneyType.LoanAmount.equals(purMoneyType)){
//						addTranBusinessOverViewInfo.setBusinessName(BusinessTypeNameEnum.LOANAMOUNT);
//					}else if(purMoneyType.AccFundAmount.equals(purMoneyType)){
//						addTranBusinessOverViewInfo.setBusinessName(BusinessTypeNameEnum.ACCFUNDAMOUNT);
//					}else if(purMoneyType.ReplaceFee.equals(purMoneyType)){
//						addTranBusinessOverViewInfo.setBusinessName(BusinessTypeNameEnum.REPLACEFEE);
//					}else if(purMoneyType.SinPur.equals(purMoneyType)){
//						addTranBusinessOverViewInfo.setBusinessName(BusinessTypeNameEnum.SINCER);
//					}
					addTranBusinessOverViewInfo.setIsFinish(false);
					addTranBusinessOverViewInfo.setDesc(purDescription);
					addTranBusinessOverViewInfo.setType(BusTypeEnum.PAY);
					addTranBusinessOverViewInfo.setHead(info);
					addTranBusinessOverViewInfo.setMoneyDefine(purMoneyDefine);
					addTranBusinessOverViewInfo.setAppAmount(purAppAmount);
					addTranBusinessOverViewInfo.setAppDate(purAppDate);
					addTranBusinessOverViewInfo.setDesc(purDescription);
					tranBusinessCol.add(addTranBusinessOverViewInfo);
				}
				
			}
    }
    
    /**
     * 将付款方案的业务明细更新到业务总览的业务明细
     * @param payType
     * @return tranBusinessCol
     * @param info
     * @return room
     * @param isAddNew
     * @param bizDate
     * @param description
     * @throws BOSException 
     */
    public static void updateBizToTranBiz(Context ctx,SHEPayTypeInfo payType,TranBusinessOverViewCollection tranBusinessCol,TransactionInfo info,RoomInfo room,boolean isAddNew,Date bizDate,String description) throws BOSException{
    	//如果单据没有付款方案就没有业务明细，只需要将该单据类型加到交易的业务明细中
		//将单据的付款方案的业务明细更新到交易的业务明细中
		if(payType != null){
			//交易业务总览中存在的业务明细
			BizListEntryCollection bizListEntryCol = payType.getBizLists();
			BizListEntryCollection deletebizListEntryCol = new BizListEntryCollection();
			for (int j = 0; j < tranBusinessCol.size(); j++) {
				TranBusinessOverViewInfo tranOldBusinessInfo = null;
				tranOldBusinessInfo = tranBusinessCol.get(j);
				BusTypeEnum busType = tranOldBusinessInfo.getType();
				//过滤掉付款明细
				if(BusTypeEnum.PAY.equals(busType)){
					continue;
				}
//				BusinessTypeNameEnum businessName = tranOldBusinessInfo.getBusinessName();
				String businessName = tranOldBusinessInfo.getBusinessName();
				BizListEntryInfo bizListEntryInfo = null;
				//付款方案中的业务明细
				for (int i = 0; i < bizListEntryCol.size(); i++) {
					bizListEntryInfo = bizListEntryCol.get(i);
					BizNewFlowEnum payTypeBizFlow = bizListEntryInfo.getPayTypeBizFlow();
					//当付款方案的业务明细为预定时，处理交易中各种业务明细
					if(BizNewFlowEnum.PREREGISTER.equals(payTypeBizFlow) && SHEManageHelper.PRELIMINARY.equals(businessName)){
						//表示之前已经完成了预定，交易的业务总览已经存在
						deletebizListEntryCol.add(bizListEntryInfo);
						break;
					}else if(BizNewFlowEnum.PURCHASE.equals(payTypeBizFlow) && SHEManageHelper.PURCHASE.equals(businessName)){
						//表示之前已经完成了认购，交易的业务总览已经存在
						deletebizListEntryCol.add(bizListEntryInfo);
						break;
					}else if(BizNewFlowEnum.SIGN.equals(payTypeBizFlow) && SHEManageHelper.SIGN.equals(businessName)){
						//表示之前已经完成了签约，交易的业务总览已经存在
						deletebizListEntryCol.add(bizListEntryInfo);
						break;
					}else if(BizNewFlowEnum.LOAN.equals(payTypeBizFlow) && SHEManageHelper.MORTGAGE.equals(businessName)){
						//表示之前已经完成了按揭办理，交易的业务总览已经存在
						deletebizListEntryCol.add(bizListEntryInfo);
						break;
					}else if(BizNewFlowEnum.ACCFUND.equals(payTypeBizFlow) && SHEManageHelper.ACCFUND.equals(businessName)){
						//表示之前已经完成了公积金办理，交易的业务总览已经存在
						deletebizListEntryCol.add(bizListEntryInfo);
						break;
					}else if(BizNewFlowEnum.PROPERTY.equals(payTypeBizFlow) && SHEManageHelper.INTEREST.equals(businessName)){
						//表示之前已经完成了产权办理，交易的业务总览已经存在
						deletebizListEntryCol.add(bizListEntryInfo);
						break;
					}else if(BizNewFlowEnum.JOIN.equals(payTypeBizFlow) && SHEManageHelper.JOIN.equals(businessName)){
						//表示之前已经完成了入伙办理，交易的业务总览已经存在
						deletebizListEntryCol.add(bizListEntryInfo);
						break;
					}else if(BizNewFlowEnum.AREACOMPENSAGTE.equals(payTypeBizFlow) && SHEManageHelper.AREACOMPENSATE.equals(businessName)){
						//表示之前已经完成了面积补差，交易的业务总览已经存在
						deletebizListEntryCol.add(bizListEntryInfo);
						break;
					}
						
				}
			}
			for (int i = 0; i < deletebizListEntryCol.size(); i++) {
				bizListEntryCol.remove(deletebizListEntryCol.get(i));
			}
			BizListEntryInfo bizInfo = null;
			int monthLimit;
			int dayLimit;
			Date appDate = null;
			String payTypeBizTime = null;
			BizNewFlowEnum payTypeBizFlow = null;
			for (int i = 0; i < bizListEntryCol.size(); i++) {
				
				bizInfo = bizListEntryCol.get(i);
				monthLimit = bizInfo.getMonthLimit();
				dayLimit = bizInfo.getDayLimit();
				appDate = bizInfo.getAppDate();
				payTypeBizTime = bizInfo.getPayTypeBizTime();
				payTypeBizFlow = bizInfo.getPayTypeBizFlow();
				TranBusinessOverViewInfo tranBusiness= new TranBusinessOverViewInfo();
				tranBusiness.setId(BOSUuid.create(tranBusiness.getBOSType()));
				if(BizNewFlowEnum.PREREGISTER.equals(payTypeBizFlow)){
					tranBusiness.setBusinessName(SHEManageHelper.PRELIMINARY);
				}else if(BizNewFlowEnum.PURCHASE.equals(payTypeBizFlow)){
					tranBusiness.setBusinessName(SHEManageHelper.PURCHASE);
				}else if(BizNewFlowEnum.SIGN.equals(payTypeBizFlow)){
					tranBusiness.setBusinessName(SHEManageHelper.SIGN);
				}else if(BizNewFlowEnum.LOAN.equals(payTypeBizFlow)){
					tranBusiness.setBusinessName(SHEManageHelper.MORTGAGE);
				}else if(BizNewFlowEnum.ACCFUND.equals(payTypeBizFlow)){
					tranBusiness.setBusinessName(SHEManageHelper.ACCFUND);
				}else if(BizNewFlowEnum.PROPERTY.equals(payTypeBizFlow)){
					tranBusiness.setBusinessName(SHEManageHelper.INTEREST);
				}else if(BizNewFlowEnum.JOIN.equals(payTypeBizFlow)){
					tranBusiness.setBusinessName(SHEManageHelper.JOIN);
				}else if(BizNewFlowEnum.AREACOMPENSAGTE.equals(payTypeBizFlow)){
					tranBusiness.setBusinessName(SHEManageHelper.AREACOMPENSATE);
				}
				tranBusiness.setFinishDate(null);
				tranBusiness.setIsFinish(false);
				tranBusiness.setDesc(bizInfo.getDescription());
				tranBusiness.setType(BusTypeEnum.BUSINESS);
				tranBusiness.setHead(info);
				if (payTypeBizTime != null && "指定日期".equals(payTypeBizTime)) {
					tranBusiness.setFinishDate(appDate);
				} else if(payTypeBizTime != null && "预定登记".equals(payTypeBizTime)){
					PrePurchaseManageInfo prePurchaseManage =getBizDatePrePurchaseManage(ctx,room);
					Date date = null;
					if(prePurchaseManage != null){
						date = prePurchaseManage.getBizDate();
					}
					if (date != null) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						cal.add(Calendar.MONTH, monthLimit);
						cal.add(Calendar.DATE, dayLimit);
						date = cal.getTime();
						tranBusiness.setFinishDate(date);
					}
				} else if(payTypeBizTime != null && "签认购书".equals(payTypeBizTime)){
					PurchaseManageInfo purchaseManage = getBizDatePurchaseManage(ctx,room);
					Date date = null;
					if(purchaseManage != null){
						date = purchaseManage.getBizDate();
					}
					if (date != null) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						cal.add(Calendar.MONTH, monthLimit);
						cal.add(Calendar.DATE, dayLimit);
						date = cal.getTime();
						tranBusiness.setFinishDate(date);
					}
					//根据不同的单据判断不同的业务明细
					tranBusiness.setFinishDate(purchaseManage.getBizDate());
					tranBusiness.setIsFinish(true);
				}else if(payTypeBizTime != null && "签约".equals(payTypeBizTime)){
					SignManageInfo signManage = getBizDateSignManage(ctx,room);
					Date date = null;
					if(signManage != null){
						date = signManage.getBizDate();
					}
					if (date != null) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						cal.add(Calendar.MONTH, monthLimit);
						cal.add(Calendar.DATE, dayLimit);
						date = cal.getTime();
						tranBusiness.setFinishDate(date);
					}
				}else if(payTypeBizTime != null && "按揭办理".equals(payTypeBizTime)){
					RoomLoanInfo roomLoan = SHEHelper.getRoomLoan(room);
					Date date = null;
					if(roomLoan != null){
						date = roomLoan.getProcessLoanDate();
					}
					if (date != null) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						cal.add(Calendar.MONTH, monthLimit);
						cal.add(Calendar.DATE, dayLimit);
						date = cal.getTime();
						tranBusiness.setFinishDate(date);
					}
				}else if(payTypeBizTime != null && "产权办理".equals(payTypeBizTime)){
					RoomPropertyBookInfo propertyBook = SHEHelper.getRoomPropertyBook(room);
					Date date = null;
					if(propertyBook != null){
						date = propertyBook.getTransactDate();
					}
					if (date != null) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						cal.add(Calendar.MONTH, monthLimit);
						cal.add(Calendar.DATE, dayLimit);
						date = cal.getTime();
						tranBusiness.setFinishDate(date);
					}
				}else if(payTypeBizTime != null && "入伙办理".equals(payTypeBizTime)){
					RoomJoinInfo joinIn = SHEHelper.getRoomJoinIn(room);
					Date date = null;
					if(joinIn != null){
						date = joinIn.getJoinDate();
					}
					if (date != null) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						cal.add(Calendar.MONTH, monthLimit);
						cal.add(Calendar.DATE, dayLimit);
						date = cal.getTime();
						tranBusiness.setFinishDate(date);
					}
				}else if(payTypeBizTime != null && "面积补差".equals(payTypeBizTime)){
					RoomAreaCompensateInfo areaCompensation = SHEHelper.getRoomAreaCompensation(room);
					Date date = null;
					if(areaCompensation != null){
						date = areaCompensation.getCompensateDate();
					}
					if (date != null) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						cal.add(Calendar.MONTH, monthLimit);
						cal.add(Calendar.DATE, dayLimit);
						date = cal.getTime();
						tranBusiness.setFinishDate(date);
					}
				}
				tranBusinessCol.add(tranBusiness);
			}
		}else if(isAddNew){
			boolean isExist = false;
			TranBusinessOverViewInfo tranOldBusinessInfo = null;
			BusTypeEnum busType = null;
//			BusinessTypeNameEnum businessName = null;
			String businessName = null;
			for (int j = 0; j < tranBusinessCol.size(); j++) {
				tranOldBusinessInfo = tranBusinessCol.get(j);
				busType = tranOldBusinessInfo.getType();
				//过滤掉付款明细
				if(BusTypeEnum.PAY.equals(busType)){
					continue;
				}
				businessName = tranOldBusinessInfo.getBusinessName();
				
				if(SHEManageHelper.PURCHASE.equals(businessName)){
					isExist = true;
					break;
				}
			}
			if(!isExist){
				TranBusinessOverViewInfo tranBusiness= new TranBusinessOverViewInfo();
				tranBusiness.setId(BOSUuid.create(tranBusiness.getBOSType()));
				//根据不同的单据来分别处理
				tranBusiness.setBusinessName(SHEManageHelper.PURCHASE);
				tranBusiness.setFinishDate(bizDate);
				tranBusiness.setActualFinishDate(bizDate);
				tranBusiness.setIsFinish(true);
				tranBusiness.setDesc(description);
				tranBusiness.setType(BusTypeEnum.BUSINESS);
				tranBusiness.setHead(info);
				tranBusinessCol.add(tranBusiness);
			}
		}
    }
    /**
     * 作废交易
     * @param ctx
     * @param transactionId
     * @param valid
     * @throws EASBizException
     * @throws BOSException
     */
    public static void validTransaction(Context ctx,BOSUuid transactionId,boolean valid) throws EASBizException, BOSException {
    	if(transactionId==null) return;
    	TransactionInfo info=new TransactionInfo();
    	info.setIsValid(valid);
    	info.setId(transactionId);
    	
    	SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("isValid");
		
		TransactionFactory.getLocalInstance(ctx).updatePartial(info,sels);
    }
    /**
     * 删除交易
     * @param ctx
     * @param transactionId
     * @throws EASBizException
     * @throws BOSException
     */
    public static void delTransaction(Context ctx,BOSUuid transactionId) throws EASBizException, BOSException{
    	if(transactionId==null) return;
    	TransactionFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(transactionId));
    }
	
	public static BigDecimal getActRevAmount(Context ctx,BOSUuid tanPayListEntryId){
    	if(tanPayListEntryId==null) return FDCHelper.ZERO;
    	try {
    		SelectorItemCollection sel=new SelectorItemCollection();
    		sel.add("actRevAmount");
			if(ctx==null){
				return TranBusinessOverViewFactory.getRemoteInstance().getTranBusinessOverViewInfo(new ObjectUuidPK(tanPayListEntryId),sel).getActRevAmount();
			}else{
				return TranBusinessOverViewFactory.getLocalInstance(ctx).getTranBusinessOverViewInfo(new ObjectUuidPK(tanPayListEntryId),sel).getActRevAmount();
			}
    	} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return FDCHelper.ZERO;
    }
	private static void updateSincerityPurchase(Context ctx,RoomInfo room,boolean isDel) throws BOSException, EASBizException{
		String state=null;
		TransactionStateEnum updateState=null;
		if(isDel){
			state=TransactionStateEnum.WAITTINGFORDEAL_VALUE;
			updateState=TransactionStateEnum.BAYNUMBER;
		}else{
			state=TransactionStateEnum.BAYNUMBER_VALUE;
			updateState=TransactionStateEnum.WAITTINGFORDEAL;
		}
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("bizState");
		
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
		sqlBuilder.appendSql("select fid from T_SHE_NewSincerityPurchase sin where 1=1 ");
		sqlBuilder.appendSql("and sin.froomid = ? ");
		sqlBuilder.addParam(room.getId().toString());
		sqlBuilder.appendSql("and sin.fbizState = ? ");
		sqlBuilder.addParam(state);

		IRowSet rs = sqlBuilder.executeQuery(ctx);
		
		try {
			while(rs.next()){
				SincerityPurchaseInfo info=SincerityPurchaseFactory.getLocalInstance(ctx).getSincerityPurchaseInfo(new ObjectUuidPK(rs.getString("fid")));
				info.setBizState(updateState);
				SincerityPurchaseFactory.getLocalInstance(ctx).updatePartial(info, sel);
				
				if(info.getTransactionID()!=null){
					validTransaction(ctx,info.getTransactionID(),!isDel);
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void updateChooseRoomState(Context ctx,BOSUuid srcId,boolean isDel,RoomSellStateEnum state) throws BOSException, EASBizException{
		if(srcId!=null){
    		ObjectUuidPK pk=new ObjectUuidPK(srcId);
    		IObjectValue objectValue=DynamicObjectFactory.getLocalInstance(ctx).getValue(pk.getObjectType(),pk);
    		SelectorItemCollection selector = new SelectorItemCollection();
    		selector.add("chooseRoomStateEnum");
    		if(isDel){
    			if(objectValue instanceof ChooseRoomInfo){
    				ChooseRoomInfo info=(ChooseRoomInfo)objectValue;
    				info.setChooseRoomStateEnum(ChooseRoomStateEnum.Affirm);
    				ChooseRoomFactory.getLocalInstance(ctx).updatePartial(info, selector);
    			}
    		}else{
    			if(objectValue instanceof ChooseRoomInfo){
    				ChooseRoomInfo info=(ChooseRoomInfo)objectValue;
    				if(state.equals(RoomSellStateEnum.PrePurchase)&&ChooseRoomStateEnum.TransPrePur.equals(info.getChooseRoomStateEnum())){
    					return;
    				}else if(state.equals(RoomSellStateEnum.Purchase)&&ChooseRoomStateEnum.TransPurchase.equals(info.getChooseRoomStateEnum())){
    					return;
    				}else if(state.equals(RoomSellStateEnum.Sign)&&ChooseRoomStateEnum.TransSign.equals(info.getChooseRoomStateEnum())){
    					return;
    				}
    				if(state.equals(RoomSellStateEnum.PrePurchase)){
    					info.setChooseRoomStateEnum(ChooseRoomStateEnum.TransPrePur);
    				}else if(state.equals(RoomSellStateEnum.Purchase)){
    					info.setChooseRoomStateEnum(ChooseRoomStateEnum.TransPurchase);
    				}else if(state.equals(RoomSellStateEnum.Sign)){
    					info.setChooseRoomStateEnum(ChooseRoomStateEnum.TransSign);
    				}
    				ChooseRoomFactory.getLocalInstance(ctx).updatePartial(info, selector);    				
    			}
    		}
		}
	}
	//转交易，作废时，更新付款明细
	public static void updatePayActRevAmountAndBizState(Context ctx,BOSUuid srcId,boolean isDel,TransactionStateEnum state) throws EASBizException, BOSException{
		if(srcId!=null){
    		ObjectUuidPK pk=new ObjectUuidPK(srcId);
    		IObjectValue objectValue=DynamicObjectFactory.getLocalInstance(ctx).getValue(pk.getObjectType(),pk);
    		SelectorItemCollection selector = new SelectorItemCollection();
    		
    		if(isDel){
    			if(objectValue instanceof BaseTransactionInfo){
    				selector.add("bizState");
    				if(objectValue instanceof SincerityPurchaseInfo){
        				state=TransactionStateEnum.BAYNUMBER;
        				SincerityPurchaseInfo info=(SincerityPurchaseInfo)objectValue;
        				if(info.getRoom()!=null){
        					updateSincerityPurchase(ctx,info.getRoom(),isDel);
        				}
        			}
        	    	if(objectValue instanceof PrePurchaseManageInfo){
        	    		state=TransactionStateEnum.PREAUDIT;
        			}
        			if(objectValue instanceof PurchaseManageInfo){
        				state=TransactionStateEnum.PURAUDIT;
        			}
        			if(objectValue instanceof SignManageInfo){
        				state=TransactionStateEnum.SIGNAUDIT;
        			} 
        			((BaseTransactionInfo)objectValue).setBizState(state);
    	    		SHEManageHelper.getBizInterface(ctx, objectValue).updatePartial(((BaseTransactionInfo)objectValue), selector);
    			}
    			if(objectValue instanceof CluesManageInfo){
    				selector.add("cluesStatus");
    				CluesManageInfo info=(CluesManageInfo)objectValue;
    				info.setCluesStatus(CluesStatusEnum.CUSTOMER);
    				CluesManageFactory.getLocalInstance(ctx).updatePartial(info, selector);
    			}
    		}else{
    			if(objectValue instanceof BaseTransactionInfo){
    				selector.add("bizState");
    				
    				if(state.equals(((BaseTransactionInfo)objectValue).getBizState())) return;
	    			if(objectValue instanceof SincerityPurchaseInfo){
	    				selector.add("sincerPriceEntrys.actRevAmount");
	    				
	    				SincerityPurchaseInfo info=(SincerityPurchaseInfo)objectValue;
	    				for(int i=0;i<info.getSincerPriceEntrys().size();i++){
	    					SincerReceiveEntryInfo entry=info.getSincerPriceEntrys().get(i);
							entry.setActRevAmount(getActRevAmount(ctx,entry.getTanPayListEntryId()));
	    				}
	    			}
	    	    	if(objectValue instanceof PrePurchaseManageInfo){
	    	    		selector.add("prePurchasePayListEntry.actRevAmount");
	    				
	    	    		PrePurchaseManageInfo info=(PrePurchaseManageInfo)objectValue;
	    				for(int i=0;i<info.getPrePurchasePayListEntry().size();i++){
	    					PrePurchasePayListEntryInfo entry=info.getPrePurchasePayListEntry().get(i);
							entry.setActRevAmount(getActRevAmount(ctx,entry.getTanPayListEntryId()));
	    				}
	    			}
	    			if(objectValue instanceof PurchaseManageInfo){
	    				selector.add("purPayListEntry.actRevAmount");
	    				
	    				PurchaseManageInfo info=(PurchaseManageInfo)objectValue;
	    				for(int i=0;i<info.getPurPayListEntry().size();i++){
	    					PurPayListEntryInfo entry=info.getPurPayListEntry().get(i);
							entry.setActRevAmount(getActRevAmount(ctx,entry.getTanPayListEntryId()));
	    				}
	    			}
	    			if(objectValue instanceof SignManageInfo){
	    				selector.add("purPayListEntry.actRevAmount");
	    				
	    				SignManageInfo info=(SignManageInfo)objectValue;
	    				for(int i=0;i<info.getSignPayListEntry().size();i++){
	    					SignPayListEntryInfo entry=info.getSignPayListEntry().get(i);
							entry.setActRevAmount(getActRevAmount(ctx,entry.getTanPayListEntryId()));
	    				}
	    			}
	    			((BaseTransactionInfo)objectValue).setBizState(state);
		    		SHEManageHelper.getBizInterface(ctx, objectValue).updatePartial(((BaseTransactionInfo)objectValue), selector);
	    		
		    		if(objectValue instanceof SincerityPurchaseInfo){
		    			SincerityPurchaseInfo info=(SincerityPurchaseInfo)objectValue;
		    			if(info.getRoom()!=null){
	    					updateSincerityPurchase(ctx,info.getRoom(),isDel);
	    				}
		    		}
    			}
    			if(objectValue instanceof CluesManageInfo){
    				selector.add("cluesStatus");
    				CluesManageInfo info=(CluesManageInfo)objectValue;
    				if(state.equals(TransactionStateEnum.TOPRE)){
    					info.setCluesStatus(CluesStatusEnum.PREPURCHASE);
    				}else if(state.equals(TransactionStateEnum.TOPUR)){
    					info.setCluesStatus(CluesStatusEnum.PURCHASE);
    				}else if(state.equals(TransactionStateEnum.TOSIGN)){
    					info.setCluesStatus(CluesStatusEnum.SIGN);
    				}
    				CluesManageFactory.getLocalInstance(ctx).updatePartial(info, selector);
    			}
    		}
    	}
	}
	public static List customerSelect(Component owner,List customer,SellProjectInfo sellProject,boolean isBaseTranAdd,boolean isCanEditMainCustomer) throws EASBizException, BOSException{
		UIContext uiContext = new UIContext(owner);
		uiContext.put(UIContext.INIT_DATAOBJECT, new BaseTransactionInfo());
		
		uiContext.put("customer", customer);
		uiContext.put("sellProject", sellProject);
		if(isBaseTranAdd){
			uiContext.put("isSignManage", "yes");
		}
		if(isCanEditMainCustomer){
			uiContext.put("isCanEditMainCustomer", Boolean.TRUE);
		}else{
			uiContext.put("isCanEditMainCustomer", Boolean.FALSE);
		}
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				TranCustomerSelectUI.class.getName(), uiContext, null, OprtState.EDIT);
		uiWindow.show();
		
		Map cusContext = uiWindow.getUIObject().getUIContext();
		return (List) cusContext.get(TranCustomerSelectUI.SHE_FILTER_CUSTOMER);
	}
	public static void checkSelected(Component owner,KDTable table) {
		if (table.getRowCount() == 0 || table.getSelectManager().size() == 0) {
			FDCMsgBox.showWarning(owner, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			SysUtil.abort();
		}
	}
	public static void openCustomerInformation(Component owner,SHECustomerInfo info) throws UIException {
		if (info== null) return;
		UIContext uiContext = new UIContext(owner);
		uiContext.put("ID", info.getId());
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CustomerRptEditUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}
	public static void loadCustomer(KDLabel labelCustomer1,KDLabel labelCustomer2,KDLabel labelCustomer3,KDLabel labelCustomer4,KDLabel labelCustomer5,List customerentry,KDTextField txtPhoneNumber,BaseTransactionInfo info){
		if(customerentry.size()==0||info==null){
			labelCustomer1.setText(null);
			labelCustomer2.setText(null);
			labelCustomer3.setText(null);
			labelCustomer4.setText(null);
			labelCustomer5.setText(null);
			labelCustomer1.setUserObject(null);
			labelCustomer2.setUserObject(null);
			labelCustomer3.setUserObject(null);
			labelCustomer4.setUserObject(null);
			labelCustomer5.setUserObject(null);
			if(txtPhoneNumber!=null){
				txtPhoneNumber.setText(null);
			}
		}else{
			if(txtPhoneNumber!=null){
				txtPhoneNumber.setText(info.getCustomerPhone());
			}
		}
		if(customerentry.size()==1){
			labelCustomer1.setText(((TranCustomerEntryInfo)customerentry.get(0)).getName());
			labelCustomer2.setText(null);
			labelCustomer3.setText(null);
			labelCustomer4.setText(null);
			labelCustomer5.setText(null);
			labelCustomer1.setUserObject(((TranCustomerEntryInfo)customerentry.get(0)).getCustomer());
			labelCustomer2.setUserObject(null);
			labelCustomer3.setUserObject(null);
			labelCustomer4.setUserObject(null);
			labelCustomer5.setUserObject(null);
		}
		if(customerentry.size()==2){
			labelCustomer1.setText(((TranCustomerEntryInfo)customerentry.get(0)).getName());
			labelCustomer2.setText(((TranCustomerEntryInfo)customerentry.get(1)).getName());
			labelCustomer3.setText(null);
			labelCustomer4.setText(null);
			labelCustomer5.setText(null);
			labelCustomer1.setUserObject(((TranCustomerEntryInfo)customerentry.get(0)).getCustomer());
			labelCustomer2.setUserObject(((TranCustomerEntryInfo)customerentry.get(1)).getCustomer());
			labelCustomer3.setUserObject(null);
			labelCustomer4.setUserObject(null);
			labelCustomer5.setUserObject(null);
		}
		if(customerentry.size()==3){
			labelCustomer1.setText(((TranCustomerEntryInfo)customerentry.get(0)).getName());
			labelCustomer2.setText(((TranCustomerEntryInfo)customerentry.get(1)).getName());
			labelCustomer3.setText(((TranCustomerEntryInfo)customerentry.get(2)).getName());
			labelCustomer4.setText(null);
			labelCustomer5.setText(null);
			labelCustomer1.setUserObject(((TranCustomerEntryInfo)customerentry.get(0)).getCustomer());
			labelCustomer2.setUserObject(((TranCustomerEntryInfo)customerentry.get(1)).getCustomer());
			labelCustomer3.setUserObject(((TranCustomerEntryInfo)customerentry.get(2)).getCustomer());
			labelCustomer4.setUserObject(null);
			labelCustomer5.setUserObject(null);
		}
		if(customerentry.size()==4){
			labelCustomer1.setText(((TranCustomerEntryInfo)customerentry.get(0)).getName());
			labelCustomer2.setText(((TranCustomerEntryInfo)customerentry.get(1)).getName());
			labelCustomer3.setText(((TranCustomerEntryInfo)customerentry.get(2)).getName());
			labelCustomer4.setText(((TranCustomerEntryInfo)customerentry.get(3)).getName());
			labelCustomer5.setText(null);
			labelCustomer1.setUserObject(((TranCustomerEntryInfo)customerentry.get(0)).getCustomer());
			labelCustomer2.setUserObject(((TranCustomerEntryInfo)customerentry.get(1)).getCustomer());
			labelCustomer3.setUserObject(((TranCustomerEntryInfo)customerentry.get(2)).getCustomer());
			labelCustomer4.setUserObject(((TranCustomerEntryInfo)customerentry.get(1)).getCustomer());
			labelCustomer5.setUserObject(null);
		}
		if(customerentry.size()==5){
			labelCustomer1.setText(((TranCustomerEntryInfo)customerentry.get(0)).getName());
			labelCustomer2.setText(((TranCustomerEntryInfo)customerentry.get(1)).getName());
			labelCustomer3.setText(((TranCustomerEntryInfo)customerentry.get(2)).getName());
			labelCustomer4.setText(((TranCustomerEntryInfo)customerentry.get(3)).getName());
			labelCustomer5.setText(((TranCustomerEntryInfo)customerentry.get(4)).getName());
			labelCustomer1.setUserObject(((TranCustomerEntryInfo)customerentry.get(0)).getCustomer());
			labelCustomer2.setUserObject(((TranCustomerEntryInfo)customerentry.get(1)).getCustomer());
			labelCustomer3.setUserObject(((TranCustomerEntryInfo)customerentry.get(2)).getCustomer());
			labelCustomer4.setUserObject(((TranCustomerEntryInfo)customerentry.get(3)).getCustomer());
			labelCustomer5.setUserObject(((TranCustomerEntryInfo)customerentry.get(4)).getCustomer());
		}
	}
	public static MoneyDefineInfo getRoomAttachMoneyDefine(){
		try {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new  FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("moneyType",MoneyTypeEnum.ElseAmount));
			filter.getFilterItems().add(new FilterItemInfo("name",RoomAttachMoneyDefineName));
			
			view.setFilter(filter);
	
			MoneyDefineCollection  coll = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
			if(coll.size()>0){
				return coll.get(0);
			}else{
				MoneyDefineInfo info=new MoneyDefineInfo();
				info.setId(BOSUuid.create(info.getBOSType()));
				info.setName(RoomAttachMoneyDefineName);
				info.setNumber(RoomAttachMoneyDefineNum);
				info.setMoneyType(MoneyTypeEnum.ElseAmount);
				info.setSysType(MoneySysTypeEnum.SalehouseSys);
			
					info.setOrgUnit(CtrlUnitFactory.getRemoteInstance().getCtrlUnitInfo(new ObjectUuidPK(OrgConstants.DEF_CU_ID)).castToFullOrgUnitInfo());
				
				info.setCU(CtrlUnitFactory.getRemoteInstance().getCtrlUnitInfo(new ObjectUuidPK(OrgConstants.DEF_CU_ID)));
				info.setIsGroup(false);
				MoneyDefineFactory.getRemoteInstance().addnew(info);
				return info;
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static MoneyDefineInfo getFittmentMoneyDefine(){
		try {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new  FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("moneyType",MoneyTypeEnum.FitmentAmount));
			filter.getFilterItems().add(new FilterItemInfo("name",FittmentMoneyDefineName));
			view.setFilter(filter);
			MoneyDefineCollection  coll = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
			
			if(coll.size()>0){
				return coll.get(0);
			}else{
				MoneyDefineInfo info=new MoneyDefineInfo();
				info.setId(BOSUuid.create(info.getBOSType()));
				info.setName(FittmentMoneyDefineName);
				info.setNumber(FittmentMoneyDefineNum);
				info.setMoneyType(MoneyTypeEnum.FitmentAmount);
				info.setSysType(MoneySysTypeEnum.SalehouseSys);
				info.setOrgUnit(CtrlUnitFactory.getRemoteInstance().getCtrlUnitInfo(new ObjectUuidPK(OrgConstants.DEF_CU_ID)).castToFullOrgUnitInfo());
				info.setCU(CtrlUnitFactory.getRemoteInstance().getCtrlUnitInfo(new ObjectUuidPK(OrgConstants.DEF_CU_ID)));
				info.setIsGroup(false);
				MoneyDefineFactory.getRemoteInstance().addnew(info);
				return info;
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static MoneyDefineInfo getSinMoneyDefine() throws EASBizException, BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new  FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name","诚意金"));
		view.setFilter(filter);
		MoneyDefineCollection  coll = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
		if(coll.size()>0){
			return coll.get(0);
		} else {
			MoneyDefineInfo info=new MoneyDefineInfo();
			info.setId(BOSUuid.create(info.getBOSType()));
			info.setName("诚意金");
			info.setNumber("SYSMONEYDEFINE-05");
			info.setMoneyType(MoneyTypeEnum.SinPur);
			info.setSysType(MoneySysTypeEnum.SalehouseSys);
			info.setOrgUnit(CtrlUnitFactory.getRemoteInstance().getCtrlUnitInfo(new ObjectUuidPK(OrgConstants.DEF_CU_ID)).castToFullOrgUnitInfo());
			info.setCU(CtrlUnitFactory.getRemoteInstance().getCtrlUnitInfo(new ObjectUuidPK(OrgConstants.DEF_CU_ID)));
			info.setIsGroup(false);
			MoneyDefineFactory.getRemoteInstance().addnew(info);
			return info;
		}
		
	}
	public static MoneyDefineInfo getPreMoneyDefine() throws EASBizException, BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new  FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name","预定金"));
		view.setFilter(filter);
		MoneyDefineCollection  coll = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
		if(coll.size()>0){
			return coll.get(0);
		} else {
			MoneyDefineInfo info=new MoneyDefineInfo();
			info.setId(BOSUuid.create(info.getBOSType()));
			info.setName("预定金");
			info.setNumber("SYSMONEYDEFINE-03");
			info.setMoneyType(MoneyTypeEnum.PreconcertMoney);
			info.setSysType(MoneySysTypeEnum.SalehouseSys);
			info.setOrgUnit(CtrlUnitFactory.getRemoteInstance().getCtrlUnitInfo(new ObjectUuidPK(OrgConstants.DEF_CU_ID)).castToFullOrgUnitInfo());
			info.setCU(CtrlUnitFactory.getRemoteInstance().getCtrlUnitInfo(new ObjectUuidPK(OrgConstants.DEF_CU_ID)));
			info.setIsGroup(false);
			MoneyDefineFactory.getRemoteInstance().addnew(info);
			return info;
		}
		
	}
	public static MoneyDefineInfo getPurMoneyDefine() throws EASBizException, BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new  FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name","定金"));
		view.setFilter(filter);
		MoneyDefineCollection  coll = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
		if(coll.size()>0){
			return coll.get(0);
		} else {
			MoneyDefineInfo info=new MoneyDefineInfo();
			BOSUuid boid=BOSUuid.create(info.getBOSType());
			info.setId(boid);
			info.setName("定金");
			info.setNumber("SYSMONEYDEFINE-04");
			info.setMoneyType(MoneyTypeEnum.EarnestMoney);
			info.setSysType(MoneySysTypeEnum.SalehouseSys);
			info.setOrgUnit(CtrlUnitFactory.getRemoteInstance().getCtrlUnitInfo(new ObjectUuidPK(OrgConstants.DEF_CU_ID)).castToFullOrgUnitInfo());
			info.setCU(CtrlUnitFactory.getRemoteInstance().getCtrlUnitInfo(new ObjectUuidPK(OrgConstants.DEF_CU_ID)));
			info.setIsGroup(false);
			MoneyDefineFactory.getRemoteInstance().addnew(info);
			return info;
		}
		
	}
	
	public static CurrencyInfo getCurrencyInfo(Context ctx) throws EASBizException, BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new  FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name",CURRENCY));
		view.setFilter(filter);
		if(ctx==null){
			CurrencyCollection  col = CurrencyFactory.getRemoteInstance().getCurrencyCollection(view);
			if(col.size()>0){
				return col.get(0);
			}else{
				return null;
			}
		}else{
			CurrencyCollection  col = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection(view);
			if(col.size()>0){
				return col.get(0);
			}else{
				return null;
			}
		}
	}
	public static Integer setRoundingMode(String toIntegerType){
		if(ROUND_DOWN.equals(toIntegerType)){
			return new Integer(BigDecimal.ROUND_DOWN);
		}else if(ROUND_UP.equals(toIntegerType)){
			return new Integer(BigDecimal.ROUND_UP);
		}else if(ROUND_HALF_UP.equals(toIntegerType)){
			return new Integer(BigDecimal.ROUND_HALF_UP);
		}else{
			return null;
		}
	}
	public static Integer setPrecision(String digit){
		if(YUAN.equals(digit)){
			return new Integer(0);
		}else if(JIAO.equals(digit)){
			return new Integer(1);
		}else if(FEN.equals(digit)){
			return new Integer(2);
		}else{
			return null;
		}
	}
	public static boolean RoomSelectParamIsEnable(String buildingId){
//		RoomDisplaySetting set = new RoomDisplaySetting(new String[]{RoomDisplaySetting.CHOOSE_ROOM_SET_MAP});
//    	ChooseRoomSet  chooseRoomSet = set.getChooseRoomSet(buildingId);
    	Map chooseRoomSet =RoomDisplaySetting.getNewChooseRoomSet(null,buildingId);
    	if(chooseRoomSet!=null&&null!=chooseRoomSet.get(SHEParamConstant.T3_IS_ENABLE)){
			if(true ==(((Boolean)chooseRoomSet.get(SHEParamConstant.T3_IS_ENABLE)).booleanValue())){
				FDCMsgBox.showInfo("该楼栋已经启用选房设置，请到选房管理中进行交易!");
				return true;
			}
		}
    	return false;
	}
	public static BigDecimal setScale(int newScale, int roundingMode,BigDecimal value){
		if(value!=null){
			return value.setScale(newScale,roundingMode);
		}
		return value;
	}
	public static HashMap getCRMConstants(BOSUuid orgid){
		HashMap param = new HashMap();
		HashMap value=null; 
		IObjectPK pk=new ObjectUuidPK(orgid);
		param.put(CRMConstants.FDCSHE_PARAM_TOL_TOINTEGER_TYPE, pk);
		param.put(CRMConstants.FDCSHE_PARAM_TOLAMOUNT_BIT, pk);
		param.put(CRMConstants.FDCSHE_PARAM_PRICE_TOINTEGER_TYPE, pk);
		param.put(CRMConstants.FDCSHE_PARAM_PRICE_BIT, pk);
		try {
			value = ParamControlFactory.getRemoteInstance().getParamHashMap(param);
			
			String totalIntegerType=SHEManageHelper.ROUND_HALF_UP;
			String totalDigit=SHEManageHelper.FEN;
			String priceIntegerType=SHEManageHelper.ROUND_HALF_UP;
			String priceDigit=SHEManageHelper.FEN;
			
			if(value==null){
				pk=new ObjectUuidPK(OrgConstants.DEF_CU_ID);
				param.put(CRMConstants.FDCSHE_PARAM_TOL_TOINTEGER_TYPE, pk);
				param.put(CRMConstants.FDCSHE_PARAM_TOLAMOUNT_BIT, pk);
				param.put(CRMConstants.FDCSHE_PARAM_PRICE_TOINTEGER_TYPE, pk);
				param.put(CRMConstants.FDCSHE_PARAM_PRICE_BIT, pk);
				
				value=ParamControlFactory.getRemoteInstance().getParamHashMap(param);
				if(value==null){
					value=new HashMap();
					value.put(CRMConstants.FDCSHE_PARAM_TOL_TOINTEGER_TYPE, totalIntegerType);
					value.put(CRMConstants.FDCSHE_PARAM_TOLAMOUNT_BIT, totalDigit);
					value.put(CRMConstants.FDCSHE_PARAM_PRICE_TOINTEGER_TYPE, priceIntegerType);
					value.put(CRMConstants.FDCSHE_PARAM_PRICE_BIT, priceDigit);
				}
			}
		}catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		return value;
	}
	public static KDTDefaultCellEditor getNumberCellEditor(int precision,int roundingMode) {
		KDFormattedTextField indexValue_TextField = new KDFormattedTextField();
		indexValue_TextField.setName("indexValue_TextField");
		indexValue_TextField.setVisible(true);
		indexValue_TextField.setEditable(true);
		indexValue_TextField.setHorizontalAlignment(NUMBERTEXTFIELD_ALIGNMENT);
		indexValue_TextField.setDataType(NUMBER_TEXT_FIELD_DATATYPE_BIGDECIMAL);
		indexValue_TextField.setMaximumValue(FDCHelper.MAX_VALUE);
		indexValue_TextField.setMinimumValue(FDCHelper.MIN_VALUE);
		indexValue_TextField.setSupportedEmpty(true);
		indexValue_TextField.setPrecision(precision);
		indexValue_TextField.setRoundingMode(roundingMode);
		
		KDTDefaultCellEditor indexValue_CellEditor = new KDTDefaultCellEditor(
				indexValue_TextField);
		indexValue_CellEditor.setClickCountToStart(1);
		
		return indexValue_CellEditor;
	}
	public static KDTDefaultCellEditor getTxtCellEditor(int min,int max, boolean editable) {
		KDTextField textField = new KDTextField();
		textField.setMinLength(min);
		textField.setMaxLength(max);
		textField.setEditable(editable);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		return txtEditor;
	}
	public static void checkCanUnAuditChangeManage(Component owner,String id) throws EASBizException, BOSException{
		SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("transactionID");
    	sic.add("bizType");
    	sic.add("room.*");
    	sic.add("srcRoom.*");
    	sic.add("newId");
    	ChangeManageInfo info=ChangeManageFactory.getRemoteInstance().getChangeManageInfo(new ObjectUuidPK(id),sic);
    	
		TransactionInfo tran=TransactionFactory.getRemoteInstance().getTransactionInfo(new ObjectUuidPK(info.getTransactionID()));
		RoomSellStateEnum roomState=null;
		if(ChangeBizTypeEnum.CHANGEROOM.equals(info.getBizType())){
			roomState=RoomSellStateEnum.changeRoom;			
		}
		if(ChangeBizTypeEnum.QUITROOM.equals(info.getBizType())){
			roomState=RoomSellStateEnum.quitRoom;
		}
		if(ChangeBizTypeEnum.PRICECHANGE.equals(info.getBizType())){
			roomState=RoomSellStateEnum.priceChange;
		}
		if(ChangeBizTypeEnum.CHANGENAME.equals(info.getBizType())){
			roomState=RoomSellStateEnum.ChangeName;
		}
		
		if(RoomSellStateEnum.priceChange.equals(roomState)||RoomSellStateEnum.ChangeName.equals(roomState)){
			if(!tran.getPreLink().equals(roomState)){
				FDCMsgBox.showWarning(owner,"请先反审批其他变更单据！");
				SysUtil.abort();
			}
			if(!tran.getCurrentLink().equals(info.getSrcRoom().getSellState())){
				FDCMsgBox.showWarning(owner,"房间已经发生业务单据，不能进行反审批操作！");
				SysUtil.abort();
			}
			if(!info.getNewId().equals(tran.getBillId())){
				FDCMsgBox.showWarning(owner,"请先反审批其他变更单据！");
				SysUtil.abort();
			}
		}else if(RoomSellStateEnum.changeRoom.equals(roomState)){
			if(!tran.getCurrentLink().equals(roomState)){
				FDCMsgBox.showWarning(owner,"请先反审批其他变更单据！");
				SysUtil.abort();
			}
			if(!tran.getPreLink().equals(info.getRoom().getSellState())){
				FDCMsgBox.showWarning(owner,"新换房间已经发生业务单据，不能进行反审批操作！");
				SysUtil.abort();
			}
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("room.id",info.getRoom().getId()));
			filter.getFilterItems().add(new FilterItemInfo("isValid",Boolean.FALSE));
			filter.getFilterItems().add(new FilterItemInfo("preLink",RoomSellStateEnum.ONSHOW_VALUE));
			
			if(!TransactionFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showWarning(owner,"新换房间已经发生业务单据，不能进行反审批操作！");
				SysUtil.abort();
			}
		}else if(RoomSellStateEnum.quitRoom.equals(roomState)){
			if(!RoomSellStateEnum.OnShow.equals(info.getSrcRoom().getSellState())){
				FDCMsgBox.showWarning(owner,"新换房间已经发生业务单据，不能进行反审批操作！");
				SysUtil.abort();
			}
		}
	}
	public static void checkHasSHERevBill(Context ctx, String billId,String warning) throws EASBizException, BOSException{
    	FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("relateBizBillId", billId));
		if(SHERevBillFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","单据已经产生收款单业务，不能进行"+warning+"操作！"));
		}
    }
	public static void setAreaBySellType(BaseTransactionInfo info,RoomInfo room,SellTypeEnum sellType,KDFormattedTextField txtRoomArea,KDFormattedTextField txtBuildingArea){
		BigDecimal actualBuildingArea=FDCHelper.ZERO;
		BigDecimal planBuildingArea=FDCHelper.ZERO;
		BigDecimal buildingArea=FDCHelper.ZERO;
		BigDecimal actualRoomArea=FDCHelper.ZERO;
		BigDecimal planRoomArea=FDCHelper.ZERO;
		BigDecimal roomArea=FDCHelper.ZERO;
		
//		if(info.getStrdActualBuildingArea()!=null&&info.getStrdActualBuildingArea().compareTo(FDCHelper.ZERO)!=0){
//			actualBuildingArea=info.getStrdActualBuildingArea();
//		}else{
			actualBuildingArea=room.getActualBuildingArea()==null?FDCHelper.ZERO:room.getActualBuildingArea();
			info.setStrdActualBuildingArea(actualBuildingArea);
//		}
//		if(info.getStrdPlanBuildingArea()!=null&&info.getStrdPlanBuildingArea().compareTo(FDCHelper.ZERO)!=0){
//			planBuildingArea=info.getStrdPlanBuildingArea();
//		}else{
			planBuildingArea=room.getPlanBuildingArea()==null?FDCHelper.ZERO:room.getPlanBuildingArea();
			info.setStrdPlanBuildingArea(planBuildingArea);
//		}
//		if(info.getBulidingArea()!=null&&info.getBulidingArea().compareTo(FDCHelper.ZERO)!=0){
//			buildingArea=info.getBulidingArea();
//		}else{
			buildingArea=room.getBuildingArea()==null?FDCHelper.ZERO:room.getBuildingArea();
			info.setBulidingArea(buildingArea);
//		}
//		if(info.getStrdActualRoomArea()!=null&&info.getStrdActualRoomArea().compareTo(FDCHelper.ZERO)!=0){
//			actualRoomArea=info.getStrdActualRoomArea();
//		}else{
			actualRoomArea=room.getActualRoomArea()==null?FDCHelper.ZERO:room.getActualRoomArea();
			info.setStrdActualRoomArea(actualRoomArea);
//		}
//		if(info.getStrdPlanRoomArea()!=null&&info.getStrdPlanRoomArea().compareTo(FDCHelper.ZERO)!=0){
//			planRoomArea=info.getStrdPlanRoomArea();
//		}else{
			planRoomArea=room.getPlanRoomArea()==null?FDCHelper.ZERO:room.getPlanRoomArea();
			info.setStrdPlanRoomArea(planRoomArea);
//		}
//		if(info.getRoomArea()!=null&&info.getRoomArea().compareTo(FDCHelper.ZERO)!=0){
//			roomArea=info.getRoomArea();
//		}else{
			roomArea=room.getRoomArea()==null?FDCHelper.ZERO:room.getRoomArea();
			info.setRoomArea(roomArea);
//		}
		if(SellTypeEnum.LocaleSell.equals(sellType)){	
			txtBuildingArea.setValue(actualBuildingArea);
			txtRoomArea.setValue(actualRoomArea);
		} else if(SellTypeEnum.PreSell.equals(sellType)){
			txtBuildingArea.setValue(buildingArea);
			txtRoomArea.setValue(roomArea);
		}else if(SellTypeEnum.PlanningSell.equals(sellType)){
			txtBuildingArea.setValue(planBuildingArea);
			txtRoomArea.setValue(planRoomArea);
		}else{
			txtBuildingArea.setValue(FDCHelper.ZERO);
			txtRoomArea.setValue(FDCHelper.ZERO);
		}
	}
	
	/**
	 * 预约排号新增诚意金
	 * @author tim_gao
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 */
	public static MoneyDefineInfo getSinPurMoneyDefine() throws EASBizException, BOSException{
		if(MoneyDefineFactory.getRemoteInstance().exists(new ObjectUuidPK(SINPURMONEYDEFINEID))){
			return MoneyDefineFactory.getRemoteInstance().getMoneyDefineInfo(new ObjectUuidPK(SINPURMONEYDEFINEID));
		}else{
			MoneyDefineInfo info=new MoneyDefineInfo();
			info.setId(SINPURMONEYDEFINEID);
			info.setName("诚意金");
			info.setNumber("SYSSINPURMONEYDEFINE-01");
			info.setMoneyType(MoneyTypeEnum.SinPur);
			info.setSysType(MoneySysTypeEnum.SalehouseSys);
			info.setIsGroup(true);
			info.setCU(CtrlUnitFactory.getRemoteInstance().getCtrlUnitInfo(new ObjectUuidPK(OrgConstants.DEF_CU_ID)));
			MoneyDefineFactory.getRemoteInstance().addnew(info);
			return info;
		}
	}
	private static CommerceChanceAssistantInfo getCommerceChanceAssistantInfo(Context ctx,RoomSellStateEnum sellState) throws BOSException, EASBizException{
		String name=null;
		if(RoomSellStateEnum.SincerPurchase.equals(sellState)){
			name="'排号','诚意金'";
		}else if(RoomSellStateEnum.PrePurchase.equals(sellState)){
			name="'预定','小订'";
		}else if(RoomSellStateEnum.Purchase.equals(sellState)){
			name="'认购'";
		}else if(RoomSellStateEnum.Sign.equals(sellState)){
			name="'签约'";
		}else if(RoomSellStateEnum.ChangeName.equals(sellState)){
			name="'更名'";
		}else if(RoomSellStateEnum.quitRoom.equals(sellState)){
			name="'退房'";
		}else{
			return null;
		}
		CommerceChanceAssistantCollection col= CommerceChanceAssistantFactory.getLocalInstance(ctx).getCommerceChanceAssistantCollection("select * from where name in("+name+")");
		if(col.size()>0){
			return col.get(0);
		}else{
			return null;
		}
	}
	private static String getNewBillNumber(Context ctx,IObjectValue objectValue) throws BOSException, EASBizException {
		OrgUnitInfo crrOu = ContextUtil.getCurrentOrgUnit(ctx);
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		boolean isExist = iCodingRuleManager.isExist(objectValue, crrOu.getId().toString());
		if(isExist) {
			return iCodingRuleManager.getNumber(objectValue, crrOu.getId().toString());
		}
		return null;
	}
	/**
	 * 当新增交易单的时候,新增商机,当转交易或商机转交易时更新商机阶段，同时新增跟进
	 * @param baseInfo 当前交易单
	 * @throws EASBizException
	 * @throws BOSException
	 */
	
	/**
	 * 当删除交易单的时候,来更新对应商机的状态
	 * @param billId 当前交易单的id
	 * @throws EASBizException
	 * @throws BOSException
	 */
//	public static void updateCommerceChanceStatus(Context ctx,String billId)
//			throws EASBizException, BOSException {
//		CommerceChanceInfo commerce = null;
//		TransactionInfo info = TransactionFactory
//				.getLocalInstance(ctx)
//				.getTransactionInfo(
//						"select id,preLink,commerceChance.id,commerceChance.name,commerceChance.number where id='"
//								+ billId + "'");
//		if (info != null) {
//			if (info.getPreLink().equals(RoomSellStateEnum.OnShow)) {
//				commerce = info.getCommerceChance();
//			} else if (info.getPreLink() == null) {
//				commerce = info.getCommerceChance();
//			}
//			if (commerce != null) {
//				SelectorItemCollection selector = new SelectorItemCollection();
//				selector.add(new SelectorItemInfo("status"));
//				CommerceChanceInfo model = new CommerceChanceInfo();
//				model.setStatus(CommerceChangeNewStatusEnum.CLOSE);
//				model.setId(commerce.getId());
//				CommerceChanceFactory.getLocalInstance(ctx).updatePartial(model,
//						selector);
//			}
//		}
//	}
	public static SellProjectInfo getParentSellProject(Context ctx,SellProjectInfo sellProject) throws EASBizException, BOSException{
		if(sellProject==null) return null;
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("parent.id");
		if(ctx==null){
			sellProject=SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProject.getId()));
		}else{
			sellProject=SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo(new ObjectUuidPK(sellProject.getId()));
		}
		if(sellProject.getParent()!=null){
			return getParentSellProject(ctx,sellProject.getParent());
		}else{
			return sellProject;
		}
	}
	public static void updateCommerceChanceStage(Context ctx,BaseTransactionInfo baseInfo,BOSUuid srcBillId,RoomSellStateEnum preSellState,RoomSellStateEnum curSellState,boolean isDel)
			throws EASBizException, BOSException {
		TrackEventEnum trackEventEnum=null;
		BOSUuid changeNameCustomerId=null;
		boolean isExist=false;
		
		CommerceChanceAssistantInfo commerceChanceStage=getCommerceChanceAssistantInfo(ctx,curSellState);
		SHECustomerInfo customerInfo = null;
		SHECustomerInfo commerceChanceCustomerInfo=null;
		SellProjectInfo sellProject=getParentSellProject(ctx,baseInfo.getSellProject());
		
		Timestamp now=FDCSQLFacadeFactory.getLocalInstance(ctx).getServerTime();
		UserInfo user=null;
		boolean isChange=false;
		if(RoomSellStateEnum.ChangeName.equals(curSellState)||preSellState.equals(RoomSellStateEnum.ChangeName)){
			if(baseInfo.getNewCommerceChance()!=null){
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("customer.id");
				sic.add("isChange");
				CommerceChanceInfo ccinfo=CommerceChanceFactory.getLocalInstance(ctx).getCommerceChanceInfo(new ObjectUuidPK(baseInfo.getNewCommerceChance().getId()),sic);
				if(ccinfo.getCustomer()!=null){
					commerceChanceCustomerInfo=ccinfo.getCustomer();
					changeNameCustomerId=ccinfo.getCustomer().getId();
					isChange=ccinfo.isIsChange();
				}
			}
		} 
		if(baseInfo instanceof SincerityPurchaseInfo){
			SincerityPurchaseCustomerEntryCollection customerCol =(SincerityPurchaseCustomerEntryCollection)((SincerityPurchaseInfo)baseInfo).getCustomer();
			if(customerCol != null){
				for(int i=0;i<customerCol.size();i++){
					if(customerCol.get(i).isIsMain()){
						customerInfo=customerCol.get(i).getCustomer();
					}
					if(customerCol.get(i).getCustomer().getId().equals(changeNameCustomerId)){
						isExist=true;
					}
				}
			}
			user=((SincerityPurchaseInfo)baseInfo).getSaleMansEntry().get(0).getUser();
		}
		if(baseInfo instanceof PrePurchaseManageInfo){
			PrePurchaseCustomerEntryCollection customerCol = (PrePurchaseCustomerEntryCollection)((PrePurchaseManageInfo)baseInfo).getPrePurchaseCustomerEntry();
			if(customerCol != null){
				for(int i=0;i<customerCol.size();i++){
					if(customerCol.get(i).isIsMain()){
						customerInfo=customerCol.get(i).getCustomer();
					}
					if(customerCol.get(i).getCustomer().getId().equals(changeNameCustomerId)){
						isExist=true;
					}
				}
			} 
			user=((PrePurchaseManageInfo)baseInfo).getPrePurchaseSaleManEntry().get(0).getUser();
		}
		if(baseInfo instanceof PurchaseManageInfo){
			PurCustomerEntryCollection customerCol = (PurCustomerEntryCollection)((PurchaseManageInfo)baseInfo).getPurCustomerEntry();
			if(customerCol != null){
				for(int i=0;i<customerCol.size();i++){
					if(customerCol.get(i).isIsMain()){
						customerInfo=customerCol.get(i).getCustomer();
					}
					if(customerCol.get(i).getCustomer().getId().equals(changeNameCustomerId)){
						isExist=true;
					}
				}
			}
			user=((PurchaseManageInfo)baseInfo).getPurSaleManEntry().get(0).getUser();
		}
		if(baseInfo instanceof SignManageInfo){
			SignCustomerEntryCollection customerCol = (SignCustomerEntryCollection)((SignManageInfo)baseInfo).getSignCustomerEntry();
			if(customerCol != null){
				for(int i=0;i<customerCol.size();i++){
					if(customerCol.get(i).isIsMain()){
						customerInfo=customerCol.get(i).getCustomer();
					}
					if(customerCol.get(i).getCustomer().getId().equals(changeNameCustomerId)){
						isExist=true;
					}
				} 
			}
			user=((SignManageInfo)baseInfo).getSignSaleManEntry().get(0).getUser();
		}
		if(customerInfo != null){
			if(RoomSellStateEnum.ChangeName.equals(curSellState)&&!isExist&&commerceChanceCustomerInfo!=null){
				customerInfo = commerceChanceCustomerInfo;
			}
			if(customerInfo!=null){
				customerInfo=SHECustomerFactory.getLocalInstance(ctx).getSHECustomerInfo("select id,name from where id='"+customerInfo.getId()+"'");
			} 
		}else{ 
			return;
		} 
		if(RoomSellStateEnum.OnShow.equals(curSellState)||isChange){
			if(isDel&&baseInfo.getNewCommerceChance()!=null){
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add(new SelectorItemInfo("status"));
				baseInfo.getNewCommerceChance().setStatus(CommerceChangeNewStatusEnum.CLOSE);
				CommerceChanceFactory.getLocalInstance(ctx).updatePartial(baseInfo.getNewCommerceChance(),selector);
				
				CommerceChanceAssistantInfo oldcommerceChanceStage=getCommerceChanceAssistantInfo(ctx,preSellState);
				
				CommerceChanceTrackCollection col = CommerceChanceTrackFactory.getLocalInstance(ctx).getCommerceChanceTrackCollection("select * from where commerceChanceStage='"+oldcommerceChanceStage.getId()+"' and commerceChance='"+baseInfo.getNewCommerceChance().getId()+"' and description='"+baseInfo.getId()+"'");
				if(col.size()>0){
					CommerceChanceTrackFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(col.get(0).getId()));
				}
				return;
			}
		}
		if(RoomSellStateEnum.SincerPurchase.equals(curSellState)){
			trackEventEnum=TrackEventEnum.SincerPurchase;
		}else if(RoomSellStateEnum.PrePurchase.equals(curSellState)){
			trackEventEnum=TrackEventEnum.PrePurchase;
		}else if(RoomSellStateEnum.Purchase.equals(curSellState)){
			trackEventEnum=TrackEventEnum.Purchase;
		}else if(RoomSellStateEnum.Sign.equals(curSellState)){
			trackEventEnum=TrackEventEnum.Sign;
		}else if(RoomSellStateEnum.ChangeName.equals(curSellState)){
			trackEventEnum=TrackEventEnum.ChangName;
		}else if(RoomSellStateEnum.quitRoom.equals(curSellState)){
			trackEventEnum=TrackEventEnum.QuitRoom;
		}else{
			return;
		}
		CommerceChanceInfo commerce = null;
		 
		if(baseInfo.getNewCommerceChance()==null||(preSellState.equals(RoomSellStateEnum.ChangeName)&&!isExist&&!srcBillId.toString().equals(baseInfo.getId().toString())&&isDel)
				||preSellState.equals(RoomSellStateEnum.ChangeName)&&!isExist&&!isDel){
			commerce = new CommerceChanceInfo();
			BOSUuid boid=BOSUuid.create(commerce.getBOSType());
			commerce.setId(boid);
			commerce.setCU(ContextUtil.getCurrentCtrlUnit(ctx));
			String retNumber = getNewBillNumber(ctx,commerce);
			if(retNumber!=null){
				commerce.setNumber(retNumber);
			}else{
				commerce.setNumber("交易"+baseInfo.getNumber()+curSellState.getAlias()+"产生客户商机");
			}
			if(preSellState.equals(RoomSellStateEnum.ChangeName)&&!isExist){
				commerce.setIsChange(true);
			}
			commerce.setSellProject(sellProject);
			commerce.setSysType(MoneySysTypeEnum.SalehouseSys);
			commerce.setOrgUnit(baseInfo.getOrgUnit());
			commerce.setCreator(baseInfo.getCreator());
			commerce.setCreateTime(now);
			commerce.setEffectiveDate(now);
			commerce.setName(customerInfo.getName()+"客户的销售机会");
			commerce.setIsToPre(false);
			commerce.setIsToPur(false);
			commerce.setIsToSign(false);
			commerce.setCommerceChanceStage(commerceChanceStage);
			commerce.setCommerceSrc(CommerceSrcEnum.TRANSACTION);
			commerce.setStatus(CommerceChangeNewStatusEnum.TRANSACTION);
			commerce.setCustomer(customerInfo);
			commerce.setSaleMan(user);
			baseInfo.setNewCommerceChance(commerce);
			CommerceChanceFactory.getLocalInstance(ctx).addnew(commerce);
		}else{
			if(baseInfo.getNewCommerceChance()!=null){
				commerce = baseInfo.getNewCommerceChance();
				if(!preSellState.equals(RoomSellStateEnum.ChangeName)||!curSellState.equals(RoomSellStateEnum.ChangeName)){
					commerce.setStatus(CommerceChangeNewStatusEnum.TRANSACTION);
					commerce.setCustomer(customerInfo);
					commerce.setCommerceChanceStage(getCommerceChanceAssistantInfo(ctx,curSellState));
				}else{
					if(srcBillId.toString().equals(baseInfo.getId().toString())){
						commerce.setStatus(CommerceChangeNewStatusEnum.TRANSACTION);
					}else{
						commerce.setCustomer(customerInfo);
						commerce.setCommerceChanceStage(getCommerceChanceAssistantInfo(ctx,curSellState));
					}
				}
				
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("status");
				sic.add("customer");
				sic.add("commerceChanceStage");
				CommerceChanceFactory.getLocalInstance(ctx).updatePartial(commerce,sic);
			}
		}
		
		if(!isDel){
			CommerceChanceTrackInfo trackInfo = null;
			CommerceChanceTrackCollection col = CommerceChanceTrackFactory.getLocalInstance(ctx).getCommerceChanceTrackCollection("select * from where commerceChanceStage='"+commerceChanceStage.getId()+"' and commerceChance='"+commerce.getId()+"' and description='"+baseInfo.getId()+"'");
			if(col.size()>0){
				trackInfo=col.get(0);
				trackInfo.setTrackDate(baseInfo.getBusAdscriptionDate());
				trackInfo.setSaleMan(user);
				CommerceChanceTrackFactory.getLocalInstance(ctx).update(new ObjectUuidPK(trackInfo.getId()), trackInfo);
			}else{
				trackInfo=new CommerceChanceTrackInfo();
				BOSUuid boid=BOSUuid.create(trackInfo.getBOSType());
				trackInfo.setId(boid);
				trackInfo.setCU(ContextUtil.getCurrentCtrlUnit(ctx));
				String retNumber = getNewBillNumber(ctx,trackInfo);
				if(retNumber!=null){
					trackInfo.setNumber(retNumber);
				}else{
					trackInfo.setNumber("交易"+baseInfo.getNumber()+curSellState.getAlias()+"产生商机跟进");
				}
				trackInfo.setSellProject(sellProject);
				trackInfo.setOrgUnit(baseInfo.getOrgUnit());
				trackInfo.setCommerceChance(commerce);
				trackInfo.setTrackDate(baseInfo.getBusAdscriptionDate());
				trackInfo.setCommerceChanceStage(commerceChanceStage);
				trackInfo.setDescription(srcBillId.toString());
				trackInfo.setTrackEvent(trackEventEnum);
				trackInfo.setCreator(baseInfo.getCreator());
				trackInfo.setCreateTime(now);
				trackInfo.setSaleMan(user);
				CommerceChanceTrackInfo lastTrackInfo=getLastCommerceChanceTrack(ctx,commerce);
				if(lastTrackInfo!=null){
					trackInfo.setClassify(lastTrackInfo.getClassify());
					trackInfo.setCommerceLevel(lastTrackInfo.getCommerceLevel());
				}
				CommerceChanceTrackFactory.getLocalInstance(ctx).addnew(trackInfo);
			}
		}else{
			if(commerceChanceStage!=null&&commerce!=null){
				CommerceChanceAssistantInfo oldcommerceChanceStage=getCommerceChanceAssistantInfo(ctx,preSellState);
				
				if(oldcommerceChanceStage==null) return;
				CommerceChanceTrackCollection col = CommerceChanceTrackFactory.getLocalInstance(ctx).getCommerceChanceTrackCollection("select * from where commerceChanceStage='"+oldcommerceChanceStage.getId()+"' and commerceChance='"+commerce.getId()+"' and description='"+srcBillId+"'");
				if(col.size()>0){
					CommerceChanceTrackFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(col.get(0).getId()));
				}
			}
		}
	}
	public static boolean isSellProjectHasChild(SellProjectInfo sellProject) throws EASBizException, BOSException{
		if(sellProject==null) return false;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id", sellProject.getId()));
		return SellProjectFactory.getRemoteInstance().exists(filter);
	}
	/**
	 * 非按揭类房款
	 * 
	 * 1、非按揭类房款：包括定金、首期、楼款等。
	 * 2、按揭类房款：包括按揭款、公积金。
	 * 3、代收费用：包括维修基金、契税等。
	 * 4、其他：包括诚意金、预订金、手续费、滞纳金、POS机手续费。
	 * 按揭类放款
	 * @param moneyType
	 * @return
	 */
	public static boolean isUnGoOnRoomMoneyType(MoneyTypeEnum moneyType){
		if(moneyType.equals(MoneyTypeEnum.EarnestMoney)||moneyType.equals(MoneyTypeEnum.FisrtAmount)||
				moneyType.equals(MoneyTypeEnum.HouseAmount)){
			return true;
		}else{
			return false;
		}
	}
	public static boolean isMergerToContractMoneyType(MoneyTypeEnum moneyType,boolean isEarnestInHouseAmount){
    	if(moneyType==null){
    		return false;
    	}else if(moneyType.equals(MoneyTypeEnum.EarnestMoney)){
    		if(isEarnestInHouseAmount){
    			return true;
    		}else{
    			return false;
    		}
    	}else if(moneyType.equals(MoneyTypeEnum.FisrtAmount)){
    		return true;
    	}else if(moneyType.equals(MoneyTypeEnum.HouseAmount)){
    		return true;
    	}else if(moneyType.equals(MoneyTypeEnum.LoanAmount)){
    		return true;
    	}else if(moneyType.equals(MoneyTypeEnum.AccFundAmount)){
    		return true;
    	}else{
    		return false;
    	}
    }
	public static BigDecimal getLoanByPayType(BigDecimal amount,SHEPayTypeInfo payType){
		if(LoanPreEnum.TENTHOUSAND.equals(payType.getNewLoanPre())){
			return amount.divide(new BigDecimal(10000),0,BigDecimal.ROUND_DOWN).multiply(new BigDecimal(10000));
		}
		if(LoanPreEnum.THOUSAND.equals(payType.getNewLoanPre())){
			return amount.divide(new BigDecimal(1000),0,BigDecimal.ROUND_DOWN).multiply(new BigDecimal(1000));
		}
		if(LoanPreEnum.HUNDRED.equals(payType.getNewLoanPre())){
			return amount.divide(new BigDecimal(100),0,BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100));
		}
		if(LoanPreEnum.TEN.equals(payType.getNewLoanPre())){
			return amount.divide(new BigDecimal(10),0,BigDecimal.ROUND_DOWN).multiply(new BigDecimal(10));
		}
		return FDCHelper.ZERO;
	}
	public static BigDecimal getAccFundByPayType(BigDecimal amount,SHEPayTypeInfo payType){
		if(AfPreEnum.TENTHOUSAND.equals(payType.getNewAfPre())){
			return amount.divide(new BigDecimal(10000),0,BigDecimal.ROUND_DOWN).multiply(new BigDecimal(10000));
		}
		if(AfPreEnum.THOUSAND.equals(payType.getNewAfPre())){
			return amount.divide(new BigDecimal(1000),0,BigDecimal.ROUND_DOWN).multiply(new BigDecimal(1000));
		}
		if(AfPreEnum.HUNDRED.equals(payType.getNewAfPre())){
			return amount.divide(new BigDecimal(100),0,BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100));
		}
		if(AfPreEnum.TEN.equals(payType.getNewAfPre())){
			return amount.divide(new BigDecimal(10),0,BigDecimal.ROUND_DOWN).multiply(new BigDecimal(10));
		}
		return FDCHelper.ZERO;
	}
	private static Date getAfterDate(Date date,int month,int day){
		if(date==null) return null;
		if(month==0&&day==0){
			
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, month);
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}
	public static PrePurchaseManageInfo getPrePurchaseManage(Context ctx,RoomInfo room,String tranId,String billId) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.PREAPPLE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.PREAUDIT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.TOPUR_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.TOSIGN_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("transactionID",tranId));
		filter.getFilterItems().add(new FilterItemInfo("id",billId));
//		filter.getFilterItems().add(new FilterItemInfo("transactionID","select fid from T_SHE_Transaction where fisValid=0 and froomId='"+room.getId().toString()+"'",CompareType.INNER));
//		filter.getFilterItems().add(new FilterItemInfo("id","select fbillid from T_SHE_Transaction where fisValid=0 and froomId='"+room.getId().toString()+"'",CompareType.INNER));
		
		filter.setMaskString("(#0 and (#1 or #2 or #3 or #4 ) and #5) or #6");
		view.setFilter(filter);
		view.getSelector().add("bizDate");
		PrePurchaseManageCollection prePurchaseManages =null;
		if(ctx==null){
			prePurchaseManages=PrePurchaseManageFactory.getRemoteInstance().getPrePurchaseManageCollection(view);
		}else{
			prePurchaseManages = PrePurchaseManageFactory.getLocalInstance(ctx).getPrePurchaseManageCollection(view);
		}
		if(prePurchaseManages.size()>0){
			return prePurchaseManages.get(0);
		}else{
			return null;
		}
		
	}
	public static PurchaseManageInfo getPurchaseManage(Context ctx,RoomInfo room,String tranId,String billId) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.PURAPPLE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.PURAUDIT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.TOSIGN_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("transactionID",tranId));
		filter.getFilterItems().add(new FilterItemInfo("id",billId));
//		filter.getFilterItems().add(new FilterItemInfo("transactionID","select fid from T_SHE_Transaction where fisValid=0 and froomId='"+room.getId().toString()+"'",CompareType.INNER));
//		filter.getFilterItems().add(new FilterItemInfo("id","select fbillid from T_SHE_Transaction where fisValid=0 and froomId='"+room.getId().toString()+"'",CompareType.INNER));
		
		filter.setMaskString("(#0 and (#1 or #2 or #3 ) and #4) or #5");
		view.setFilter(filter);
		view.getSelector().add("bizDate");
		PurchaseManageCollection purchaseManages = null;
		if(ctx==null){
			purchaseManages=PurchaseManageFactory.getRemoteInstance().getPurchaseManageCollection(view);
		}else{
			purchaseManages = PurchaseManageFactory.getLocalInstance(ctx).getPurchaseManageCollection(view);
		}
		if (purchaseManages.size() > 0) {
			return purchaseManages.get(0);
		}else{
			return null;
		}
	}
	public static SignManageInfo getSignManage(Context ctx,RoomInfo room,String tranId,String billId) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.SIGNAPPLE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.SIGNAUDIT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("transactionID",tranId));
		filter.getFilterItems().add(new FilterItemInfo("id",billId));
//		filter.getFilterItems().add(new FilterItemInfo("transactionID","select fid from T_SHE_Transaction where fisValid=0 and froomId='"+room.getId().toString()+"'",CompareType.INNER));
//		filter.getFilterItems().add(new FilterItemInfo("id","select fbillid from T_SHE_Transaction where fisValid=0 and froomId='"+room.getId().toString()+"'",CompareType.INNER));
		
		filter.setMaskString("(#0 and (#1 or #2 ) and #3) or #4");
		view.setFilter(filter);
		view.getSelector().add("bizDate");
		SignManageCollection signManages =null;
		if(ctx==null){
			signManages=SignManageFactory.getRemoteInstance().getSignManageCollection(view);
		}else{
			signManages = SignManageFactory.getLocalInstance(ctx).getSignManageCollection(view);
		}
		if (signManages.size() > 0) {
			return signManages.get(0);
		}else{
			return null;
		}
	}
	private static PrePurchaseManageInfo getBizDatePrePurchaseManage(Context ctx,RoomInfo room) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.PREAPPLE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.PREAUDIT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.TOPUR_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.TOSIGN_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.CHANGEPIRCEAUDITING_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("transactionID","select fid from T_SHE_Transaction where fisValid=0 and froomId='"+room.getId().toString()+"'",CompareType.INNER));
		
//		filter.setMaskString("#0 and (#1 or #2 or #3 or #4 or #5) and #6)");
		filter.setMaskString("#0 and (#1 or #2 or #3 or #4 or #5))");
		view.setFilter(filter);
		view.getSelector().add("bizDate");
		PrePurchaseManageCollection prePurchaseManages =null;
		if(ctx==null){
			prePurchaseManages=PrePurchaseManageFactory.getRemoteInstance().getPrePurchaseManageCollection(view);
		}else{
			prePurchaseManages = PrePurchaseManageFactory.getLocalInstance(ctx).getPrePurchaseManageCollection(view);
		}
		if(prePurchaseManages.size()>0){
			return prePurchaseManages.get(0);
		}else{
			return null;
		}
		
	}
	private static PurchaseManageInfo getBizDatePurchaseManage(Context ctx,RoomInfo room) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.PURAPPLE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.PURAUDIT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.TOSIGN_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.CHANGEPIRCEAUDITING_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("transactionID","select fid from T_SHE_Transaction where fisValid=0 and froomId='"+room.getId().toString()+"'",CompareType.INNER));
		
//		filter.setMaskString("#0 and (#1 or #2 or #3 or #4) and #5");
		filter.setMaskString("#0 and (#1 or #2 or #3 or #4)");
		view.setFilter(filter);
		view.getSelector().add("bizDate");
		PurchaseManageCollection purchaseManages = null;
		if(ctx==null){
			purchaseManages=PurchaseManageFactory.getRemoteInstance().getPurchaseManageCollection(view);
		}else{
			purchaseManages = PurchaseManageFactory.getLocalInstance(ctx).getPurchaseManageCollection(view);
		}
		if (purchaseManages.size() > 0) {
			return purchaseManages.get(0);
		}else{
			return null;
		}
	}
	public static SignManageInfo getBizDateSignManage(Context ctx,RoomInfo room) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.SIGNAPPLE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.SIGNAUDIT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.CHANGEPIRCEAUDITING_VALUE));
//		filter.getFilterItems().add(new FilterItemInfo("transactionID","select fid from T_SHE_Transaction where fisValid=0 and froomId='"+room.getId().toString()+"'",CompareType.INNER));
		
//		filter.setMaskString("#0 and (#1 or #2 or #3 ) and #4)");
		filter.setMaskString("#0 and (#1 or #2 or #3 ))");
		view.setFilter(filter);
		view.getSelector().add("bizDate");
		SignManageCollection signManages =null;
		if(ctx==null){
			signManages=SignManageFactory.getRemoteInstance().getSignManageCollection(view);
		}else{
			signManages = SignManageFactory.getLocalInstance(ctx).getSignManageCollection(view);
		}
		if (signManages.size() > 0) {
			return signManages.get(0);
		}else{
			return null;
		}
	}
	private static Date setPayListAppDate(PayListEntryInfo entry,RoomInfo room,Date bizDate,SHEPayTypeBizTimeEnum billState) throws BOSException{
		if(entry == null){
			return null;
		}
		SHEPayTypeBizTimeEnum bizTimeEnum = entry.getPayTypeBizTime();
		Date date = new Date();
		
		int monthLimit = entry.getMonthLimit();
		int dayLimit = entry.getDayLimit();
		
		if(billState.equals(bizTimeEnum)){
			if(bizDate!=null){
				return getAfterDate(bizDate,monthLimit,dayLimit);
			}else{
				return null;
			}
		}else if (SHEPayTypeBizTimeEnum.APPTIME.equals(bizTimeEnum)) {
			return entry.getAppDate();
		} else if(SHEPayTypeBizTimeEnum.PREREGISTER.equals(bizTimeEnum)){
			PrePurchaseManageInfo prePurchaseManage = getBizDatePrePurchaseManage(null,room);
			
			if(prePurchaseManage != null){
				date = prePurchaseManage.getBizDate();
			}
			return getAfterDate(date,monthLimit,dayLimit);
		} else if(SHEPayTypeBizTimeEnum.PURCHASE.equals(bizTimeEnum)){
			PurchaseManageInfo purchaseManage = getBizDatePurchaseManage(null,room);
			if(purchaseManage != null){
				date = purchaseManage.getBizDate();
			}
			return getAfterDate(date,monthLimit,dayLimit);
		}else if(SHEPayTypeBizTimeEnum.SIGN.equals(bizTimeEnum)){
			SignManageInfo signManage = getBizDateSignManage(null,room);
			if(signManage != null){
				date = signManage.getBizDate();
			}
			return getAfterDate(date,monthLimit,dayLimit);
		}else{
			return null;
		}
	}
	public static void updatePayList(BigDecimal contractTotalAmount,KDTable table,boolean isEarnestInHouseAmount){
		BigDecimal addAmount=FDCHelper.ZERO;
		BigDecimal totalAmount= FDCHelper.ZERO;
		
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow entry = table.getRow(i);
			
			MoneyDefineInfo moneyDefine = (MoneyDefineInfo)entry.getCell("moneyName").getValue();
			if(moneyDefine==null) continue;
			if(entry.getCell("appAmount").getValue()==null) continue;
			
			BigDecimal amount=(BigDecimal)entry.getCell("appAmount").getValue();
			
			MoneyTypeEnum moneyDefineType= moneyDefine.getMoneyType();
			
			if(SHEManageHelper.isMergerToContractMoneyType(moneyDefineType,isEarnestInHouseAmount)){
				totalAmount=totalAmount.add(amount);
			}
		}
		addAmount=contractTotalAmount.subtract(totalAmount);
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow entry = table.getRow(i);
			
			MoneyDefineInfo moneyDefine = (MoneyDefineInfo)entry.getCell("moneyName").getValue();
			if(moneyDefine==null) continue;
			if(entry.getCell("appAmount").getValue()==null) continue;
			
			MoneyTypeEnum moneyDefineType= moneyDefine.getMoneyType();
			BigDecimal appAmount=(BigDecimal)entry.getCell("appAmount").getValue();
			if(SHEManageHelper.isMergerToContractMoneyType(moneyDefineType,isEarnestInHouseAmount)&&!moneyDefineType.equals(MoneyTypeEnum.EarnestMoney)
					&&!moneyDefineType.equals(MoneyTypeEnum.LoanAmount)&&!moneyDefineType.equals(MoneyTypeEnum.AccFundAmount)){
				
				if(addAmount.add(appAmount).compareTo(FDCHelper.ZERO)<0){
					entry.getCell("appAmount").setValue(FDCHelper.ZERO);
					addAmount=addAmount.add(appAmount);
				}else{
					entry.getCell("appAmount").setValue(addAmount.add(appAmount));
					addAmount=FDCHelper.ZERO;
				}
				
				BigDecimal apAmount = entry.getCell("appAmount").getValue()==null?FDCHelper.ZERO:(BigDecimal)entry.getCell("appAmount").getValue();			
				BigDecimal actAmount = entry.getCell("actAmount").getValue()==null?FDCHelper.ZERO:(BigDecimal)entry.getCell("actAmount").getValue();			
				if (actAmount.compareTo(FDCHelper.ZERO) != 0) {
					if (actAmount.compareTo(apAmount) >= 0) {
						entry.getCell("state").setValue(new Boolean(true));
					}else{
						entry.getCell("state").setValue(new Boolean(false));
					}
				}else{
					entry.getCell("state").setValue(new Boolean(false));
				}
				entry.getCell("balance").setValue(apAmount.subtract(actAmount).compareTo(FDCHelper.ZERO)<0?FDCHelper.ZERO:apAmount.subtract(actAmount));
			}
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow entry = table.getRow(i);
			
			MoneyDefineInfo moneyDefine = (MoneyDefineInfo)entry.getCell("moneyName").getValue();
			if(moneyDefine==null) continue;
			if(entry.getCell("appAmount").getValue()==null) continue;
			
			MoneyTypeEnum moneyDefineType= moneyDefine.getMoneyType();
			BigDecimal appAmount=(BigDecimal)entry.getCell("appAmount").getValue();
			if(moneyDefineType.equals(MoneyTypeEnum.EarnestMoney)){
				if(addAmount.add(appAmount).compareTo(FDCHelper.ZERO)<0){
					entry.getCell("appAmount").setValue(FDCHelper.ZERO);
				}else{
					entry.getCell("appAmount").setValue(addAmount.add(appAmount));
				}
			}
			
			BigDecimal apAmount = entry.getCell("appAmount").getValue()==null?FDCHelper.ZERO:(BigDecimal)entry.getCell("appAmount").getValue();			
			BigDecimal actAmount = entry.getCell("actAmount").getValue()==null?FDCHelper.ZERO:(BigDecimal)entry.getCell("actAmount").getValue();			
			if (actAmount.compareTo(FDCHelper.ZERO) != 0) {
				if (actAmount.compareTo(apAmount) >= 0) {
					entry.getCell("state").setValue(new Boolean(true));
				}else{
					entry.getCell("state").setValue(new Boolean(false));
				}
			}else{
				entry.getCell("state").setValue(new Boolean(false));
			}
			entry.getCell("balance").setValue(apAmount.subtract(actAmount).compareTo(FDCHelper.ZERO)<0?FDCHelper.ZERO:apAmount.subtract(actAmount));
		}
	}
	public static List updatePayListByPayType(SHEPayTypeInfo payType,boolean isEarnestInHouseAmount,KDTable payList,IRow fittment,IRow roomAttach,MoneyDefineInfo preMoneyDefine,BigDecimal contractTotalAmount
			,BigDecimal dealTotalAmount,BigDecimal standardTotalAmount,BigDecimal buildingArea,BigDecimal roomArea,RoomInfo room,int digit,int toIntegerType,Date bizDate,SHEPayTypeBizTimeEnum billState,boolean isAddFittment,boolean isAddRoomAttach) throws BOSException, EASBizException{
		
			List toDelRowIds = new ArrayList();
			List toAddRowEntry = new ArrayList();
			
			for (int i = 0; i < payList.getRowCount(); i++) {
				if(fittment!=null){
					if(i==fittment.getRowIndex()) continue;
				}
				if(roomAttach!=null){
					if(i==roomAttach.getRowIndex()) continue;
				}
				if(payType==null&&preMoneyDefine!=null&&preMoneyDefine.equals(payList.getRow(i).getCell("moneyName").getValue())){
					continue;
				}
				toDelRowIds.add(new Integer(i));
			}
			for (int i = toDelRowIds.size() - 1; i >= 0; i--) {
				Integer tmp = (Integer) toDelRowIds.get(i);
				payList.removeRow(tmp.intValue());
			}
			if (payType != null) {
				
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("*");
				sic.add("payLists.*");
				sic.add("payLists.moneyDefine.*");
				sic.add("payLists.currency.*");
				sic.add("payLists.collection.*");
				sic.add("payLists.collection.moneyName.*");
				sic.add("payLists.collection.project.*");
				payType=SHEPayTypeFactory.getRemoteInstance().getSHEPayTypeInfo(new ObjectUuidPK(payType.getId()),sic);
				
				BigDecimal addAmount=FDCHelper.ZERO;
				BigDecimal oldAmount=FDCHelper.ZERO;
				BigDecimal remain = contractTotalAmount==null?FDCHelper.ZERO:contractTotalAmount.setScale(digit, toIntegerType);
				BigDecimal totalAmount= contractTotalAmount==null?FDCHelper.ZERO:contractTotalAmount.setScale(digit, toIntegerType);
				
				PayListEntryCollection payListEntry = payType.getPayLists();
				CRMHelper.sortCollection(payListEntry, "seq", true);
				
				for (int i = 0; i < payListEntry.size(); i++) {
					PayListEntryInfo entry = payListEntry.get(i);
					
					Date curDate = setPayListAppDate(entry,room,bizDate,billState);
										
					BigDecimal amount = FDCHelper.ZERO;	
					
					MoneyDefineInfo moneyDefine = entry.getMoneyDefine();
					if((getFittmentMoneyDefine().equals(moneyDefine)&&!isAddFittment)||(getRoomAttachMoneyDefine().equals(moneyDefine)&&!isAddRoomAttach)){
						continue;
					}
					MoneyTypeEnum moneyDefineType= moneyDefine.getMoneyType();
					
					boolean isMergerToContractMoneyType =SHEManageHelper.isMergerToContractMoneyType(moneyDefineType,isEarnestInHouseAmount);
					boolean isUnGoOnRoomMoneyType =SHEManageHelper.isUnGoOnRoomMoneyType(moneyDefineType);
					
					if(moneyDefineType.equals(MoneyTypeEnum.ReplaceFee)&&entry.getCollection()!=null){
							amount=CRMHelper.getSubstituteAmountByCollection(null, entry.getCollection(),dealTotalAmount,standardTotalAmount,buildingArea, roomArea,room);
					}else{
						if (entry.getValue() != null) {
							amount = entry.getValue();
						} else if(entry.getProportion()!=null){
							BigDecimal proportion = entry.getProportion();
							amount = totalAmount.multiply(proportion).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
						}
					}
					if(moneyDefineType.equals(MoneyTypeEnum.LoanAmount)||moneyDefineType.equals(MoneyTypeEnum.AccFundAmount)){
						BigDecimal accFundAmount=FDCHelper.ZERO;
						oldAmount=amount;
						if(moneyDefineType.equals(MoneyTypeEnum.LoanAmount)){
							accFundAmount=SHEManageHelper.getLoanByPayType(amount, payType);
						}else{
							accFundAmount=SHEManageHelper.getAccFundByPayType(amount, payType);
						}
						addAmount=addAmount.add(amount.subtract(accFundAmount));
						amount=	accFundAmount;
						if(addAmount.compareTo(FDCHelper.ZERO)>0){
							for(int j=toAddRowEntry.size()-1;j>0;j--){
								TranPayListEntryInfo tranEntry=(TranPayListEntryInfo) toAddRowEntry.get(j);
								MoneyTypeEnum type=tranEntry.getMoneyDefine().getMoneyType();
								if(SHEManageHelper.isUnGoOnRoomMoneyType(type)){
									tranEntry.setAppAmount(tranEntry.getAppAmount().add(addAmount));
									addAmount=FDCHelper.ZERO;
								}
							}
						}
					}
					if(addAmount.compareTo(FDCHelper.ZERO)>0){
						if(isUnGoOnRoomMoneyType){
							amount=amount.add(addAmount);
							addAmount=FDCHelper.ZERO;
						}
					}
					if(isMergerToContractMoneyType){
						if(moneyDefineType.equals(MoneyTypeEnum.LoanAmount)||moneyDefineType.equals(MoneyTypeEnum.AccFundAmount)){
							if (oldAmount.compareTo(remain) > 0) {
//								amount = remain;
								for(int j=toAddRowEntry.size()-1;j>0;j--){
									TranPayListEntryInfo tranEntry=(TranPayListEntryInfo) toAddRowEntry.get(j);
									MoneyTypeEnum type=tranEntry.getMoneyDefine().getMoneyType();
									if(SHEManageHelper.isUnGoOnRoomMoneyType(type)){
										tranEntry.setAppAmount(tranEntry.getAppAmount().subtract(oldAmount.subtract(remain)));
									}
								}
								remain = FDCHelper.ZERO;
							} else {
								remain = remain.subtract(oldAmount);
							}
						}else{
							if (amount.compareTo(remain) > 0) {
								amount = remain;
								remain = FDCHelper.ZERO;
							} else {
								remain = remain.subtract(amount);
							}
						}
					}
					if (i == payListEntry.size() - 1&&remain.compareTo(FDCHelper.ZERO)>0) {
						if(isUnGoOnRoomMoneyType){
							amount = amount.add(remain);
						}else{
							for(int j=toAddRowEntry.size()-1;j>0;j--){
								TranPayListEntryInfo tranEntry=(TranPayListEntryInfo) toAddRowEntry.get(j);
								MoneyTypeEnum type=tranEntry.getMoneyDefine().getMoneyType();
								if(SHEManageHelper.isUnGoOnRoomMoneyType(type)){
									tranEntry.setAppAmount(tranEntry.getAppAmount().add(remain));
									remain=FDCHelper.ZERO;
								}
							}
						}
					}
					
					TranPayListEntryInfo tranEntry = new TranPayListEntryInfo();
					tranEntry.setAppDate(curDate);
					tranEntry.setAppAmount(amount);
					tranEntry.setMoneyDefine(moneyDefine);
					tranEntry.setCurrency(entry.getCurrency());

					toAddRowEntry.add(tranEntry);
				}
			}
			return toAddRowEntry;
		}
	public static Set getAllSellProjectCollection(Context ctx,SellProjectInfo sellProject) throws BOSException{
		Set id=new HashSet();
		if(sellProject==null ) return id;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id", sellProject.getId()));
		view.setFilter(filter);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		view.setSelector(sic);
		ISellProject iSellProject=null;
		if(ctx==null){
			iSellProject=SellProjectFactory.getRemoteInstance();
		}else{
			iSellProject=SellProjectFactory.getLocalInstance(ctx);
		}
		SellProjectCollection col=iSellProject.getSellProjectCollection(view);
		col.add(sellProject);
		for(int i=0;i<col.size();i++){
			id.add(col.get(i).getId().toString());
		}
		return id;
	}
	public static Set getAllSellProjectCollection(SellProjectInfo sellProject,Set id) throws BOSException{
		if(sellProject==null ) return id;
		id.add(sellProject.getId().toString());
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id", sellProject.getId()));
		view.setFilter(filter);
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		view.setSelector(sic);
		SellProjectCollection col=SellProjectFactory.getRemoteInstance().getSellProjectCollection(view);
		for(int i=0;i<col.size();i++){
			id.add(col.get(i).getId().toString());
			getAllSellProjectCollection(col.get(i),id);
		}
		return id;
	}
	public static Set getAllParentSellProjectCollection(SellProjectInfo sellProject,Set id) throws BOSException, EASBizException{
		if(sellProject==null ) return id;
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("parent.id");
		sellProject=SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProject.getId()),sic);
		
		id.add(sellProject.getId().toString());
		
		if(sellProject.getParent()!=null){
			getAllParentSellProjectCollection(sellProject.getParent(),id);
		}
		return id;
	}
	public static DefaultKingdeeTreeNode findNode(DefaultKingdeeTreeNode node,
			BuildingInfo building, BuildingUnitInfo unit, SellProjectInfo sellProject) {
		if(sellProject!=null) {
			if(node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo sellProInfo = (SellProjectInfo)node.getUserObject();
				if(sellProInfo.getId().toString().equals(sellProject.getId().toString())) {
					return node;
				}
			}			
		}else if(building!=null){
			if (node.getUserObject() instanceof BuildingInfo) {
				BuildingInfo buildingInfo = (BuildingInfo) node.getUserObject();
				if (buildingInfo.getId().toString().equals(building.getId().toString()) && unit == null) {
					return node;
				}
			} else if (node.getUserObject() instanceof BuildingUnitInfo) {
				BuildingInfo buildingInfo = (BuildingInfo) ((DefaultKingdeeTreeNode) node
						.getParent()).getUserObject();
				BuildingUnitInfo aUnit = (BuildingUnitInfo) node.getUserObject();
				if (buildingInfo.getId().toString().equals(building.getId().toString())	&& unit!=null &&
						unit.getId().toString().equals(aUnit.getId().toString())) {
					return node;
				}
			}
		}	
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode findNode = findNode(
					(DefaultKingdeeTreeNode) node.getChildAt(i), building,
					unit,sellProject);
			if (findNode != null) {
				return findNode;
			}

		}
		return null;
	}
	public static void addCommerceChance(Object owner,List customer,List saleman,BaseTransactionInfo info) throws BOSException{
		if(info.getNewCommerceChance()!=null){
			return;
		}else{
			SHECustomerInfo mainCustoemr=null;
			if(customer!=null){
				for(int i=0;i<customer.size();i++){
					if(((TranCustomerEntryInfo)customer.get(i)).isIsMain()){
						mainCustoemr=((TranCustomerEntryInfo)customer.get(i)).getCustomer();
					}
				}
			}
//			Set salemanId=new HashSet();
//			if(saleman!=null){
//				for(int i=0;i<saleman.size();i++){
//					salemanId.add(((TranSaleManEntryInfo)saleman.get(i)).getUser().getId().toString());
//				}
//			}
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			if(mainCustoemr!=null){
				filter.getFilterItems().add(new FilterItemInfo("customer.id", mainCustoemr.getId()));
			}
//			filter.getFilterItems().add(new FilterItemInfo("saleMan.id", salemanId,CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("sysType", MoneySysTypeEnum.SALEHOUSESYS_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("commerceSrc", CommerceSrcEnum.ADDBYHAND_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("status", CommerceChangeNewStatusEnum.ACTIVE_VALUE));
			
			view.setFilter(filter);

			CommerceChanceCollection col=CommerceChanceFactory.getRemoteInstance().getCommerceChanceCollection(view);
			if(col.size()==0){
				return ;
			}else{
				info.setNewCommerceChance(col.get(0));
			}
//			UIContext uiContext = new UIContext(owner); 
//			uiContext.put("filter", filter);
//			uiContext.put("info", info);
//			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CommerceChanceSelectUI.class.getName(), uiContext, null, OprtState.VIEW);
//			uiWindow.show();
		}
	}
	
	public static KDTDefaultCellEditor getMoneyTypeCellEditorForSHE(){
		// 初始化款项名称
		KDBizPromptBox f7Box = new KDBizPromptBox();
		f7Box.setDisplayFormat("$name$");
		f7Box.setCommitFormat("$number$");
		f7Box.setEditFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineQuery");

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		// 这里应该和认购单上的过滤一致
		filter.getFilterItems().add(
				new FilterItemInfo("sysType",
						MoneySysTypeEnum.SALEHOUSESYS_VALUE,
						CompareType.EQUALS));
		Set moneyTypeSet = new HashSet();
		moneyTypeSet.add(MoneyTypeEnum.EARNESTMONEY_VALUE);
		moneyTypeSet.add(MoneyTypeEnum.FISRTAMOUNT_VALUE);
		moneyTypeSet.add(MoneyTypeEnum.HOUSEAMOUNT_VALUE);
		moneyTypeSet.add(MoneyTypeEnum.LOANAMOUNT_VALUE);
		moneyTypeSet.add(MoneyTypeEnum.ACCFUNDAMOUNT_VALUE);
		moneyTypeSet.add(MoneyTypeEnum.REPLACEFEE_VALUE);
		moneyTypeSet.add(MoneyTypeEnum.LATEFEE_VALUE);
		moneyTypeSet.add(MoneyTypeEnum.COMMISSIONCHARGE_VALUE);
		moneyTypeSet.add(MoneyTypeEnum.ELSEAMOUNT_VALUE);
		filter.getFilterItems().add(
				new FilterItemInfo("moneyType",
						moneyTypeSet,
						CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("number", SHEManageHelper.RoomAttachMoneyDefineNum,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("number", SHEManageHelper.FittmentMoneyDefineNum,CompareType.NOTEQUALS));
		f7Box.setEntityViewInfo(view);
		return  new KDTDefaultCellEditor(f7Box);
	}

	/** 首先要计算付款方案的折扣，也就是现在的特殊折扣
	1.底价销售
			合同总价 = 成交总价 + 装修金额 + 附属房款
			成交总价 = 房间套内面积×房间套内销售底价/房间建筑面积×房间建筑销售底价 + （考虑特殊折扣）  + 取整
			合同建筑单价 = 成交建筑单价 = 房间的建筑销售底价
			合同套内单价 = 成交套内单价 = 房间的套内销售底价
			最终折扣 = 特殊折扣

	2.总价反算单价
			合同总价 = 	成交总价 + 装修金额 + 附属房款
			折扣前成交总价 = （房间的标准总价 + （现售实测复核后的差异值）；
			成交总价 = 折扣前成交总价 + （总价的）折扣 + 特殊折扣）  +  取整操作
			合同建筑单价 = 合同总价 / 建筑面积（区分现售或预售）
			合同套内单价 = 合同总价 / 套内面积（区分现售或预售）
			成交建筑单价 = 成交总价 / 建筑面积（区分现售或预售）
			成交套内单价 = 成交总价 / 套内面积（区分现售或预售）
			最终折扣 = 成交总价 / 折扣前成交总价
			
	3.单价反算总价
			折扣前的单价 = 房间的单价（区分按套内或建筑）；
			折扣后的单价 = 折扣前的单价 + （单价的）折扣 + （单价的）特殊折扣  ------可能是套内或建筑
			面积（考虑是套内或建筑）																							------可能是套内或建筑			
			成交总价 = 单价 × 面积				+   取整
			合同总价 = 成交总价 + 装修金额 + 附属房款
			最终折扣 = 折扣后的单价 / 折扣前的单价
	 * @param agioParam
	 * @param roomInfo
	 * @param splType
	 * @param splAgio
	 * @param payTypeName
	 * @param sellType
	 * @param agioEntrys
	 * @param isFitmentToContract
	 * @param fitmentAmount
	 * @param attachmentAmount
	 * @return
	 */
	public static PurchaseParam getPurchaseAgioParam(AgioParam agioParam,RoomInfo roomInfo,SellTypeEnum sellType , 
			boolean isFitmentToContract ,BigDecimal fitmentAmount,BigDecimal attachmentAmount,BigDecimal areaCompensateAmount ,
				SpecialAgioEnum splType,BigDecimal splAgio,String payTypeName)	{
		PurchaseParam purParam = new PurchaseParam();
		if(roomInfo==null) return purParam;
		if(areaCompensateAmount==null) areaCompensateAmount = FDCHelper.ZERO;
		
		if(sellType==null){
			return purParam;
		}
		BigDecimal calRoomArea = FDCHelper.ZERO;		//计算时的套内面积（现售时按实测来算）
		BigDecimal calBuildArea = FDCHelper.ZERO;		//计算时的建筑面积（现售时按实测来算）
		
		if(SellTypeEnum.LocaleSell.equals(sellType)){	
			calRoomArea = roomInfo.getActualRoomArea()==null?FDCHelper.ZERO:roomInfo.getActualRoomArea();
			calBuildArea = roomInfo.getActualBuildingArea()==null?FDCHelper.ZERO:roomInfo.getActualBuildingArea();
		} else if(SellTypeEnum.PreSell.equals(sellType)){
			calRoomArea = roomInfo.getRoomArea()==null?FDCHelper.ZERO:roomInfo.getRoomArea();
			calBuildArea = roomInfo.getBuildingArea()==null?FDCHelper.ZERO:roomInfo.getBuildingArea();
		}else if(SellTypeEnum.PlanningSell.equals(sellType)){
			calRoomArea = roomInfo.getPlanRoomArea()==null?FDCHelper.ZERO:roomInfo.getPlanRoomArea();
			calBuildArea = roomInfo.getPlanBuildingArea()==null?FDCHelper.ZERO:roomInfo.getPlanBuildingArea();
		}
		
		BigDecimal standardAmount = roomInfo.getStandardTotalAmount();	//房间的标准总价
		if (standardAmount == null) standardAmount = FDCHelper.ZERO;	
		
//		if(agioParam.isBasePriceSell()){			//底价销售
//			if(roomInfo.getBaseRoomPrice()==null ||roomInfo.getBaseBuildingPrice()==null) {
//				FDCMsgBox.showWarning("查询不到房间的底价，请检查！");
//				SysUtil.abort();
//			}
//				
//			BigDecimal standTotalAmount = FDCHelper.ZERO;
//			if(roomInfo.isIsCalByRoomArea()){
//				standTotalAmount = calRoomArea.multiply(roomInfo.getBaseRoomPrice());
//			}else{
//				standTotalAmount = calBuildArea.multiply(roomInfo.getBaseBuildingPrice());
//			}			
//			BigDecimal dealTotalAmount = getSpecialAgioAmount(standTotalAmount,splType,splAgio);	//考虑特殊则扣
//			dealTotalAmount = SHEHelper.getAmountAfterToInteger(dealTotalAmount, agioParam.isToInteger(), 	//取整操作
//							agioParam.getToIntegerType(), agioParam.getDigit());
//
//			if(standTotalAmount.compareTo(FDCHelper.ZERO)>0)
//				purParam.setFinalAgio(dealTotalAmount.multiply(new BigDecimal("100")).divide(standTotalAmount,2,BigDecimal.ROUND_HALF_UP));			
//			purParam.setDealTotalAmount(dealTotalAmount);
//			purParam.setContractTotalAmount(dealTotalAmount.add(getFitmentAndAttachMent(isFitmentToContract, fitmentAmount, attachmentAmount)));
//			purParam.setContractBuildPrice(roomInfo.getBaseBuildingPrice());
//			purParam.setContractRoomPrice(roomInfo.getBaseRoomPrice());
//			purParam.setDealBuildPrice(roomInfo.getBaseBuildingPrice());
//			purParam.setDealRoomPrice(roomInfo.getBaseRoomPrice());
//		}else 
			if (PriceAccountTypeEnum.StandSetPrice.equals(agioParam.getPriceAccountType())) {	//总价反算单价
			// 只有实测面积复核后，且销售方式为现售， 存在金额调整的问题(实测 和 预测的差值)
			BigDecimal beforeDealAmount = standardAmount.add(areaCompensateAmount);				

			BigDecimal finalDealAmount = getSpecialAgioAmount(beforeDealAmount, splType, splAgio);
			AgioEntryCollection agioEntryCol = agioParam.getAgios(); 
			for (int i = 0; i < agioEntryCol.size(); i++) {				//按照折扣方案进行打折处理	
				AgioBillInfo aAgio = null;
				BigDecimal amount = FDCHelper.ZERO;	//认购折扣分录's 优惠金额
				BigDecimal pro = FDCHelper.ZERO;		//认购折扣分录's 百分比
				
				AgioEntryInfo agioEntryInfo = agioEntryCol.get(i);
				aAgio = agioEntryInfo.getAgio();
				if (agioEntryInfo.getAmount() != null) amount = agioEntryInfo.getAmount();
				if (agioEntryInfo.getPro() != null) 	pro = agioEntryInfo.getPro();				
				
				if(aAgio!=null){
					if (aAgio.getCalType().equals(AgioCalTypeEnum.DanJia)) {	
						if(roomInfo.isIsCalByRoomArea())
							amount = amount.multiply(calRoomArea);
						else
							amount = amount.multiply(calBuildArea);
						finalDealAmount = finalDealAmount.subtract(amount);
					} else if (aAgio.getCalType().equals(AgioCalTypeEnum.ZongJia)) {
						finalDealAmount = finalDealAmount.subtract(amount);
					} else if (aAgio.getCalType().equals(AgioCalTypeEnum.Dazhe)) {
						finalDealAmount = finalDealAmount.multiply(pro).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
					} else if (aAgio.getCalType().equals(AgioCalTypeEnum.JianDian)) {
						BigDecimal jiandianAmount = finalDealAmount.multiply(pro).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
						finalDealAmount = finalDealAmount.subtract(jiandianAmount);
					}
				}else{
					finalDealAmount = finalDealAmount.subtract(amount);
				}
			}
			if (finalDealAmount.compareTo(FDCHelper.ZERO) < 0) 	finalDealAmount = FDCHelper.ZERO;
//			finalDealAmount = getSpecialAgioAmount(finalDealAmount, splType, splAgio);	//考虑特殊则扣					
			finalDealAmount = SHEHelper.getAmountAfterToInteger(finalDealAmount, agioParam.isToInteger(), 	//取整操作
										agioParam.getToIntegerType(), agioParam.getDigit());
			
			// 主房产金额加上装修金额和附属房款
			BigDecimal contractTotalAmount = finalDealAmount.add(getFitmentAndAttachMent(isFitmentToContract,fitmentAmount,attachmentAmount));
			
			purParam.setDealTotalAmount(finalDealAmount);
			purParam.setContractTotalAmount(contractTotalAmount);
			//添加calBuildArea为null的处理
			if(calBuildArea==null||calBuildArea.compareTo(FDCHelper.ZERO)==0){
				calBuildArea = FDCHelper.ONE;
			}
			purParam.setContractBuildPrice(contractTotalAmount.divide(calBuildArea, 2, BigDecimal.ROUND_HALF_UP));
			purParam.setDealBuildPrice(finalDealAmount.divide(calBuildArea, 2, BigDecimal.ROUND_HALF_UP));
			if(calRoomArea==null||calRoomArea.compareTo(FDCHelper.ZERO)==0){
				calRoomArea = FDCHelper.ONE;
			}
			purParam.setContractRoomPrice(contractTotalAmount.divide(calRoomArea, 2, BigDecimal.ROUND_HALF_UP));
			purParam.setDealRoomPrice(finalDealAmount.divide(calRoomArea, 2, BigDecimal.ROUND_HALF_UP));	
			if (beforeDealAmount.compareTo(FDCHelper.ZERO) != 0) 
				purParam.setFinalAgio(finalDealAmount.multiply(new BigDecimal("100")).divide(beforeDealAmount, 2, BigDecimal.ROUND_HALF_UP));
		} else if(PriceAccountTypeEnum.PriceSetStand.equals(agioParam.getPriceAccountType())){	 //单价反算总价
			BigDecimal beforeDealPrice = FDCHelper.ZERO;	//折扣前的成交单价
			if (roomInfo.isIsCalByRoomArea()) {
				beforeDealPrice = roomInfo.getRoomPrice();
			} else {
				beforeDealPrice = roomInfo.getBuildPrice();
			}				
			
			BigDecimal dealPrice =  getSpecialAgioAmount(beforeDealPrice, splType, splAgio);
			AgioEntryCollection agioEntryColl = agioParam.getAgios();
			
			BigDecimal zongjia=FDCHelper.ZERO;
			for (int i = 0;  i < agioEntryColl.size(); i++) {			//考虑打折
				AgioBillInfo aAgio = null;
				BigDecimal amount = FDCHelper.ZERO;
				BigDecimal pro = FDCHelper.ZERO;
				
				AgioEntryInfo agioEntryInfo = agioEntryColl.get(i);
				aAgio = agioEntryInfo.getAgio();
				if(agioEntryInfo.getAmount() != null)  amount = agioEntryInfo.getAmount();
				if(agioEntryInfo.getPro() != null) pro = agioEntryInfo.getPro();

				// 按照选择折扣的先后顺序算出单价
				if(aAgio!=null){
					if (AgioCalTypeEnum.DanJia.equals(aAgio.getCalType())) {
						dealPrice = dealPrice.subtract(amount);
					} else if (AgioCalTypeEnum.Dazhe.equals(aAgio.getCalType())) {
						dealPrice = dealPrice.multiply(pro).divide(FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
					} else if (AgioCalTypeEnum.JianDian.equals(aAgio.getCalType())) {
						//减点是对最原始的价格减点，打折时可能是折扣再打折算法是不一样的
						BigDecimal jiandianAmount = dealPrice.multiply(pro).divide(FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
						dealPrice = dealPrice.subtract(jiandianAmount);
					} else if (aAgio.getCalType().equals(AgioCalTypeEnum.ZongJia)) {
						zongjia = zongjia.add(amount);
					}
				}else{
					zongjia= zongjia.add(amount);
				}
			}	
			if (dealPrice.compareTo(FDCHelper.ZERO) < 0) 	dealPrice = FDCHelper.ZERO;
//			dealPrice = getSpecialAgioAmount(dealPrice, splType, splAgio);		//考虑特殊折扣
			
//			PBG065863 提出计算方法应该是  取整方式【取整方式（“定价时的单价”*折扣）*面积】此处对单价进行取整 xiaoao_liu
			dealPrice = SHEHelper.getAmountAfterToInteger(dealPrice, agioParam.isToInteger(), 	//取整操作
					agioParam.getToIntegerType(), agioParam.getDigit());
			
			BigDecimal dealAmount = FDCHelper.ZERO;
			if(roomInfo.isIsCalByRoomArea())
				dealAmount = dealPrice.multiply(calRoomArea);
			else
				dealAmount = dealPrice.multiply(calBuildArea);
			
			dealAmount=dealAmount.subtract(zongjia);
			
			dealAmount = SHEHelper.getAmountAfterToInteger(dealAmount, agioParam.isToInteger(), 	//取整操作
									agioParam.getToIntegerType(), agioParam.getDigit());

			// 主房产金额加上装修金额和附属房款
			BigDecimal contractTotalAmount = dealAmount.add(getFitmentAndAttachMent(isFitmentToContract,fitmentAmount,attachmentAmount));
			
			purParam.setDealTotalAmount(dealAmount);
			purParam.setContractTotalAmount(contractTotalAmount);	
			
			//这里应该取0还是1
			if(calBuildArea==null||calBuildArea.compareTo(FDCHelper.ZERO)==0){
				calBuildArea = FDCHelper.ONE;
			}
			if(calRoomArea==null||calRoomArea.compareTo(FDCHelper.ZERO)==0){
				calRoomArea = FDCHelper.ONE;
			}
			purParam.setContractBuildPrice(contractTotalAmount.divide(calBuildArea, 2, BigDecimal.ROUND_HALF_UP));
			purParam.setDealBuildPrice(dealAmount.divide(calBuildArea, 2, BigDecimal.ROUND_HALF_UP));
			purParam.setContractRoomPrice(contractTotalAmount.divide(calRoomArea, 2, BigDecimal.ROUND_HALF_UP));
			purParam.setDealRoomPrice(dealAmount.divide(calRoomArea, 2, BigDecimal.ROUND_HALF_UP));	
			if (beforeDealPrice.compareTo(FDCHelper.ZERO) != 0) 
				purParam.setFinalAgio(dealPrice.multiply(new BigDecimal("100")).divide(beforeDealPrice, 2, BigDecimal.ROUND_HALF_UP));
		}
		
		String agioDesString = SHEHelper.getAgioDes(agioParam.getAgios(),
				splType,splAgio,payTypeName,agioParam.isToInteger(),agioParam.isBasePriceSell(),agioParam.getToIntegerType(), agioParam.getDigit());
		purParam.setAgioDes(agioDesString);
		
		return purParam;
	}
	
	
	public static PurchaseParam getAgioParam(AgioParam agioParam,RoomInfo roomInfo,SellTypeEnum sellType,CalcTypeEnum valuationType,
			boolean isFitmentToContract ,BigDecimal roomArea,BigDecimal buildingArea,BigDecimal roomPrice,BigDecimal buildingPrice,BigDecimal standardAmount,BigDecimal fitmentAmount,BigDecimal attachmentAmount,BigDecimal areaCompensateAmount ,
				SpecialAgioEnum splType,BigDecimal splAgio,String payTypeName)	{
		
		PurchaseParam purParam = new PurchaseParam();
		if(agioParam.getSpecialAgio()!=null){
			AgioEntryCollection agios=new AgioEntryCollection();
			AgioEntryInfo info=new SignAgioEntryInfo();
			try {
				SpecialDiscountEntryCollection col=SpecialDiscountEntryFactory.getRemoteInstance().getSpecialDiscountEntryCollection("select discountAfAmount from where parent.id='"+agioParam.getSpecialAgio().getId().toString()+"' and room.id='"+roomInfo.getId().toString()+"'");
				if(col.size()>0){
					info.setAmount(FDCHelper.subtract(standardAmount, col.get(0).getDiscountAfAmount()));
					agios.add(info);
					agioParam.setAgios(agios);
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		
		if(roomInfo==null||valuationType==null) return purParam;
		if(areaCompensateAmount==null) areaCompensateAmount = FDCHelper.ZERO;
		
		BigDecimal calRoomArea = FDCHelper.ZERO;		//计算时的套内面积（现售时按实测来算）
		BigDecimal calBuildArea = FDCHelper.ZERO;		//计算时的建筑面积（现售时按实测来算）
		
		calRoomArea=roomArea==null?FDCHelper.ZERO:roomArea;
		calBuildArea=buildingArea==null?FDCHelper.ZERO:buildingArea;;
		
		roomPrice=roomPrice==null?FDCHelper.ZERO:roomPrice;
		buildingPrice=buildingPrice==null?FDCHelper.ZERO:buildingPrice;;
		
		if (standardAmount == null) standardAmount = FDCHelper.ZERO;	
		
		if (PriceAccountTypeEnum.StandSetPrice.equals(agioParam.getPriceAccountType())) {	//总价反算单价
			// 只有实测面积复核后，且销售方式为现售， 存在金额调整的问题(实测 和 预测的差值)
			BigDecimal beforeDealAmount = standardAmount.add(areaCompensateAmount);				

			BigDecimal finalDealAmount = getSpecialAgioAmount(beforeDealAmount, splType, splAgio);
			AgioEntryCollection agioEntryCol = agioParam.getAgios(); 
			boolean isAdd=false;
			for (int i = 0; i < agioEntryCol.size(); i++) {				//按照折扣方案进行打折处理	
				AgioBillInfo aAgio = null;
				BigDecimal amount = FDCHelper.ZERO;	//认购折扣分录's 优惠金额
				BigDecimal pro = FDCHelper.ZERO;		//认购折扣分录's 百分比
				
				AgioEntryInfo agioEntryInfo = agioEntryCol.get(i);
				aAgio = agioEntryInfo.getAgio();
				if (agioEntryInfo.getAmount() != null) amount = agioEntryInfo.getAmount();
				if (agioEntryInfo.getPro() != null) 	pro = agioEntryInfo.getPro();				
				
				if(aAgio!=null){
					if (aAgio.getCalType().equals(AgioCalTypeEnum.DanJia)) {	
						if(CalcTypeEnum.PriceByRoomArea.equals(valuationType))
							amount = amount.multiply(calRoomArea);
						else
							amount = amount.multiply(calBuildArea);
						finalDealAmount = finalDealAmount.subtract(amount);
					} else if (aAgio.getCalType().equals(AgioCalTypeEnum.ZongJia)) {
						finalDealAmount = finalDealAmount.subtract(amount);
					} else if (aAgio.getCalType().equals(AgioCalTypeEnum.Dazhe)||aAgio.getCalType().equals(AgioCalTypeEnum.JianDian)) {
						if(aAgio.getCalType().equals(AgioCalTypeEnum.Dazhe)){
							pro=new BigDecimal("100").subtract(pro);
						}
						if(agioEntryInfo.getOperate()!=null&&agioEntryInfo.getOperate().equals(OperateEnum.ADD)){
							if(isAdd){
								continue;
							}
							for(int j=i+1;j<agioEntryCol.size();j++){
								if(agioEntryCol.get(j).getAgio()!=null&&agioEntryCol.get(j).getAgio().getCalType()!=null
										&&(agioEntryCol.get(j).getAgio().getCalType().equals(AgioCalTypeEnum.JianDian)||agioEntryCol.get(j).getAgio().getCalType().equals(AgioCalTypeEnum.Dazhe))&&agioEntryCol.get(j).getOperate()!=null
										&&agioEntryCol.get(j).getOperate().equals(OperateEnum.ADD)&&agioEntryInfo.getPro()!=null){
									if(agioEntryCol.get(j).getAgio().getCalType().equals(AgioCalTypeEnum.JianDian)){
										pro=pro.add(agioEntryCol.get(j).getPro());
									}else{
										pro=pro.add(new BigDecimal("100").subtract(agioEntryCol.get(j).getPro()));
									}
								}
							}
							isAdd=true;
						}
						BigDecimal jiandianAmount = finalDealAmount.multiply(pro).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
						finalDealAmount = finalDealAmount.subtract(jiandianAmount);
					}
				}else{
					finalDealAmount = finalDealAmount.subtract(amount);
				}
			}
			if (finalDealAmount.compareTo(FDCHelper.ZERO) < 0) 	finalDealAmount = FDCHelper.ZERO;
			
			finalDealAmount = getAmountAfterToInteger(finalDealAmount, agioParam.isToInteger(), 	//取整操作
										agioParam.getToIntegerType(), agioParam.getDigit());
			
			// 主房产金额加上装修金额和附属房款
			BigDecimal contractTotalAmount = finalDealAmount.add(getFitmentAndAttachMent(isFitmentToContract,fitmentAmount,attachmentAmount));
			
			purParam.setDealTotalAmount(finalDealAmount);
			purParam.setContractTotalAmount(contractTotalAmount);
			//添加calBuildArea为null的处理
			if(calBuildArea==null||calBuildArea.compareTo(FDCHelper.ZERO)==0){
				calBuildArea = FDCHelper.ONE;
			}
			purParam.setContractBuildPrice(contractTotalAmount.divide(calBuildArea, 2, BigDecimal.ROUND_HALF_UP));
			purParam.setDealBuildPrice(finalDealAmount.divide(calBuildArea, 2, BigDecimal.ROUND_HALF_UP));
			if(calRoomArea==null||calRoomArea.compareTo(FDCHelper.ZERO)==0){
				calRoomArea = FDCHelper.ONE;
			}
			purParam.setContractRoomPrice(contractTotalAmount.divide(calRoomArea, 2, BigDecimal.ROUND_HALF_UP));
			purParam.setDealRoomPrice(finalDealAmount.divide(calRoomArea, 2, BigDecimal.ROUND_HALF_UP));	
			if (beforeDealAmount.compareTo(FDCHelper.ZERO) != 0) 
				purParam.setFinalAgio(finalDealAmount.multiply(new BigDecimal("100")).divide(beforeDealAmount, 2, BigDecimal.ROUND_HALF_UP));
		} else if(PriceAccountTypeEnum.PriceSetStand.equals(agioParam.getPriceAccountType())){	 //单价反算总价
			BigDecimal beforeDealPrice = FDCHelper.ZERO;	//折扣前的成交单价
			
			if(CalcTypeEnum.PriceByRoomArea.equals(valuationType)){
				beforeDealPrice = roomPrice;
			} else {
				beforeDealPrice = buildingPrice;
			}				
			
			BigDecimal dealPrice =  getSpecialAgioAmount(beforeDealPrice, splType, splAgio);
			AgioEntryCollection agioEntryColl = agioParam.getAgios();
			
			BigDecimal zongjia=FDCHelper.ZERO;
			boolean isAdd=false;
			for (int i = 0;  i < agioEntryColl.size(); i++) {			//考虑打折
				AgioBillInfo aAgio = null;
				BigDecimal amount = FDCHelper.ZERO;
				BigDecimal pro = FDCHelper.ZERO;
				
				AgioEntryInfo agioEntryInfo = agioEntryColl.get(i);
				aAgio = agioEntryInfo.getAgio();
				if(agioEntryInfo.getAmount() != null)  amount = agioEntryInfo.getAmount();
				if(agioEntryInfo.getPro() != null) pro = agioEntryInfo.getPro();

				// 按照选择折扣的先后顺序算出单价
				if(aAgio!=null){
					if (AgioCalTypeEnum.DanJia.equals(aAgio.getCalType())) {
						dealPrice = dealPrice.subtract(amount);
					} else if (AgioCalTypeEnum.Dazhe.equals(aAgio.getCalType())||AgioCalTypeEnum.JianDian.equals(aAgio.getCalType())) {
						if(aAgio.getCalType().equals(AgioCalTypeEnum.Dazhe)){
							pro=new BigDecimal("100").subtract(pro);
						}
						if(agioEntryInfo.getOperate()!=null&&agioEntryInfo.getOperate().equals(OperateEnum.ADD)){
							if(isAdd){
								continue;
							}
							for(int j=i+1;j<agioEntryColl.size();j++){
								if(agioEntryColl.get(j).getAgio()!=null&&agioEntryColl.get(j).getAgio().getCalType()!=null
										&&(agioEntryColl.get(j).getAgio().getCalType().equals(AgioCalTypeEnum.JianDian)||agioEntryColl.get(j).getAgio().getCalType().equals(AgioCalTypeEnum.Dazhe))&&agioEntryColl.get(j).getOperate()!=null
										&&agioEntryColl.get(j).getOperate().equals(OperateEnum.ADD)&&agioEntryInfo.getPro()!=null){
									if(agioEntryColl.get(j).getAgio().getCalType().equals(AgioCalTypeEnum.JianDian)){
										pro=pro.add(agioEntryColl.get(j).getPro());
									}else{
										pro=pro.add(new BigDecimal("100").subtract(agioEntryColl.get(j).getPro()));
									}
								}
							}
							isAdd=true;
						}
						BigDecimal jiandianAmount = dealPrice.multiply(pro).divide(FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
						dealPrice = dealPrice.subtract(jiandianAmount);
					}else if (aAgio.getCalType().equals(AgioCalTypeEnum.ZongJia)) {
						zongjia = zongjia.add(amount);
					}
				}else{
					zongjia= zongjia.add(amount);
				}
			}	
			if (dealPrice.compareTo(FDCHelper.ZERO) < 0) 	dealPrice = FDCHelper.ZERO;
//			dealPrice = getSpecialAgioAmount(dealPrice, splType, splAgio);		//考虑特殊折扣
			
//			PBG065863 提出计算方法应该是  取整方式【取整方式（“定价时的单价”*折扣）*面积】此处对单价进行取整 xiaoao_liu
			dealPrice = getAmountAfterToInteger(dealPrice, agioParam.isToInteger(), 	//取整操作
					agioParam.getToIntegerType(), agioParam.getDigit());
			
			BigDecimal dealAmount = FDCHelper.ZERO;
			if(CalcTypeEnum.PriceByRoomArea.equals(valuationType))
				dealAmount = dealPrice.multiply(calRoomArea);
			else
				dealAmount = dealPrice.multiply(calBuildArea);
			
			dealAmount=dealAmount.subtract(zongjia);
			
			dealAmount = getAmountAfterToInteger(dealAmount, agioParam.isToInteger(), 	//取整操作
									agioParam.getToIntegerType(), agioParam.getDigit());

			// 主房产金额加上装修金额和附属房款
			BigDecimal contractTotalAmount = dealAmount.add(getFitmentAndAttachMent(isFitmentToContract,fitmentAmount,attachmentAmount));
			
			purParam.setDealTotalAmount(dealAmount);
			purParam.setContractTotalAmount(contractTotalAmount);	
			
			//这里应该取0还是1
			if(calBuildArea==null||calBuildArea.compareTo(FDCHelper.ZERO)==0){
				calBuildArea = FDCHelper.ONE;
			}
			if(calRoomArea==null||calRoomArea.compareTo(FDCHelper.ZERO)==0){
				calRoomArea = FDCHelper.ONE;
			}
			purParam.setContractBuildPrice(contractTotalAmount.divide(calBuildArea, 2, BigDecimal.ROUND_HALF_UP));
			purParam.setDealBuildPrice(dealAmount.divide(calBuildArea, 2, BigDecimal.ROUND_HALF_UP));
			purParam.setContractRoomPrice(contractTotalAmount.divide(calRoomArea, 2, BigDecimal.ROUND_HALF_UP));
			purParam.setDealRoomPrice(dealAmount.divide(calRoomArea, 2, BigDecimal.ROUND_HALF_UP));	
			if (beforeDealPrice.compareTo(FDCHelper.ZERO) != 0) 
				purParam.setFinalAgio(dealPrice.multiply(new BigDecimal("100")).divide(beforeDealPrice, 2, BigDecimal.ROUND_HALF_UP));
		}
		AgioEntryCollection col=agioParam.getAgios();
		StringBuffer agioDes = new StringBuffer();
		if (payTypeName != null && SpecialAgioEnum.DaZhe.equals(splType)) {
			if (splAgio != null && splAgio.compareTo(new BigDecimal("100")) != 0) {
				BigDecimal teishu = splAgio;
				agioDes.append(payTypeName+"折扣 " + teishu.setScale(2, BigDecimal.ROUND_HALF_UP) + "%;");
			}
		}
		if (col != null) {
			CRMHelper.sortCollection(col, "seq", true);
			for (int i = 0; i < col.size(); i++) {
				if (i != 0) {
					agioDes.append("；");
				}
				AgioEntryInfo entry = (AgioEntryInfo)col.get(i);
				AgioBillInfo aAgio = entry.getAgio();
				if(aAgio!=null){
					agioDes.append(aAgio.getName());
					if (aAgio.getCalType().equals(AgioCalTypeEnum.Dazhe)) {
//						agioDes.append("打折" + entry.getPro().setScale(2, BigDecimal.ROUND_HALF_UP) + "% ");
						agioDes.append("打折");
					} else if (aAgio.getCalType().equals(AgioCalTypeEnum.JianDian)) {
						agioDes.append("减点" + entry.getPro().setScale(2, BigDecimal.ROUND_HALF_UP) + "% ");
					} else if (aAgio.getCalType().equals(AgioCalTypeEnum.ZongJia)) {
						agioDes.append("总价优惠" + entry.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
					} else if (aAgio.getCalType().equals(AgioCalTypeEnum.DanJia)) {
						agioDes.append("单价优惠" + entry.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
					}
				}else{
					agioDes.append("总价优惠" + entry.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
				}
			

				if (i == col.size() - 1) {
					agioDes.append("。");
				}
			}
		}
		if (agioParam.isToInteger()) {
			agioDes.append(agioParam.getDigit().getAlias()).append(agioParam.getToIntegerType().getAlias()).append("自动取整。");
		}
		purParam.setAgioDes(agioDes.toString());
		return purParam;
	}
	private static BigDecimal getFitmentAndAttachMent(boolean isFitmentToContract,BigDecimal fitmentAmount,BigDecimal attachmentAmount){
		BigDecimal retTotalAmount = FDCHelper.ZERO;
		if (isFitmentToContract) 
			if (fitmentAmount != null) retTotalAmount = retTotalAmount.add(fitmentAmount);
		if (attachmentAmount != null) retTotalAmount = retTotalAmount.add(attachmentAmount);
		return retTotalAmount;		
	}
	
	private static BigDecimal getSpecialAgioAmount(BigDecimal currAmount,SpecialAgioEnum splType,BigDecimal splAgio){
		if(currAmount==null) currAmount = FDCHelper.ZERO;
		if(splType!=null && splAgio!=null) {	//考虑特殊则扣
			if(SpecialAgioEnum.DaZhe.equals(splType))
				currAmount = currAmount.multiply(splAgio).divide(new BigDecimal("100.00"),2, BigDecimal.ROUND_HALF_UP);
			else if(SpecialAgioEnum.YouHui.equals(splType))
				currAmount = currAmount.subtract(splAgio);
		}			
		return currAmount;
	}
	public static BigDecimal getAmountAfterToInteger(BigDecimal srcAmount, boolean isToInteger, ToIntegerTypeEnum toIntegerType, DigitEnum digit) {
		return SHEComHelper.getAmountAfterToInteger(srcAmount, isToInteger, toIntegerType, digit);
	}
	public static void expandAllNodesByBuilding(KDTree treeMain,DefaultKingdeeTreeNode node){
		if(node.getUserObject() instanceof BuildingInfo) {
			treeMain.expandAllNodes(false,node);
		}else{
			treeMain.expandAllNodes(true,node);
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			expandAllNodesByBuilding(treeMain,(DefaultKingdeeTreeNode) node.getChildAt(i));
		}
	}
	/**
	 * 删除组织结构中的非财务组织
	 * @param root
	 * @param treeNode
	 */
	public static void deleteNonCompanyNode(DefaultMutableTreeNode root,
			DefaultMutableTreeNode treeNode,boolean isEntryChild) {
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
		List indexList = new ArrayList();
		if (thisNode.getUserObject() != null
				&& thisNode.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo org = (OrgStructureInfo) thisNode.getUserObject();
			if (org != null && org.getUnit() != null
					&& !org.getUnit().isIsCompanyOrgUnit()) {
					indexList.add(root.getIndex(thisNode) + "");
			}
		}
		
		if (indexList.size() > 0) {
			int temp = 0;
			for (int i = 0; i < indexList.size(); i++) {
				temp = Integer.parseInt(indexList.get(i).toString());
				if(temp == -1){
					if(thisNode.getParent()!=null){
						deleteNonCompanyNode((DefaultMutableTreeNode)thisNode.getParent(),thisNode,false);
					}
				}else{
					root.remove(temp);
				}
			}
		}

		if(isEntryChild){
			int childCount = treeNode.getChildCount();
			while (childCount > 0) {
				deleteNonCompanyNode(root, (DefaultMutableTreeNode) treeNode
						.getChildAt(childCount - 1),true);
				childCount--;
			}
		}
	}
	public static CommerceChanceTrackInfo getLastCommerceChanceTrack(Context ctx,CommerceChanceInfo commerce) throws BOSException{
		if(commerce==null) return null;
		CommerceChanceTrackInfo info=null;
		
		SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("commerceLevel.*"));
        sic.add(new SelectorItemInfo("classify.*"));
        sic.add(new SelectorItemInfo("commerceChanceStage.*"));
        sic.add(new SelectorItemInfo("trackDate"));
        sic.add(new SelectorItemInfo("trackContent"));
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("commerceChance.id",commerce.getId()));
		view.setFilter(filter);
		SorterItemCollection sort=new SorterItemCollection();
		SorterItemInfo createTime=new SorterItemInfo("createTime");
		createTime.setSortType(SortType.DESCEND);
		sort.add(createTime);
		view.setSorter(sort);
		view.setSelector(sic);
		
		if(ctx==null){
			CommerceChanceTrackCollection col=CommerceChanceTrackFactory.getRemoteInstance().getCommerceChanceTrackCollection(view);
			if(col.size()>0){
				info= col.get(0);
			}
		}else {
			CommerceChanceTrackCollection col=CommerceChanceTrackFactory.getLocalInstance(ctx).getCommerceChanceTrackCollection(view);
			if(col.size()>0){
				info= col.get(0);
			}
		}
		return info;
	}
	
	public static void handleCodingRule(KDTextField txtnumber,String oprtState,IObjectValue editData,ICoreBase coreBase,String bindingProperty) throws BOSException, CodingRuleException, EASBizException{
		String currentOrgId = FDCClientHelper.getCurrentOrgId();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
		if(!"ADDNEW".equals(oprtState))
			return;
		boolean isExist = true;
		if(!iCodingRuleManager.isExist(editData, currentOrgId)){
			isExist = false;
		}
		if(isExist){
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(editData, currentOrgId);
			if(isAddView){
				getNumberByCodingRule(txtnumber,editData, currentOrgId,coreBase,bindingProperty);
	        } else {
	        	String number = null;
	            if(iCodingRuleManager.isUseIntermitNumber(editData, currentOrgId) && iCodingRuleManager.isUserSelect(editData, currentOrgId)){
	                CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(editData, currentOrgId, null, null);
	                Object object = null;
	                if(iCodingRuleManager.isDHExist(editData, currentOrgId)){
	                    intermilNOF7.show();
	                    object = intermilNOF7.getData();
	                }
	                if(object != null)
	                    number = object.toString();
	            }
	            txtnumber.setText(number);
	        }
			setNumberTextEnabled(txtnumber,editData,currentOrgId);
	    }
	}

	public static void getNumberByCodingRule(KDTextField txtnumber,IObjectValue editData, String orgId,ICoreBase coreBase,String bindingProperty){
	    try
	    {
	        ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
	        if(orgId == null || orgId.trim().length() == 0)
	            orgId = "00000000-0000-0000-0000-000000000000CCE7AED4";
	        if(iCodingRuleManager.isExist(editData, orgId))
	        {
	            String number = "";
	            if(iCodingRuleManager.isUseIntermitNumber(editData, orgId))
	            {
	                if(iCodingRuleManager.isUserSelect(editData, orgId))
	                {
	                    CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(editData, orgId, null, null);
	                    Object object = null;
	                    if(iCodingRuleManager.isDHExist(editData, orgId))
	                    {
	                        intermilNOF7.show();
	                        object = intermilNOF7.getData();
	                    }
	                    if(object != null){
	                    	 number = object.toString();
	                    }else{
	                    	if(bindingProperty != null)
	                    		number = iCodingRuleManager.getNumber(editData, orgId, bindingProperty, "");
	           	         	else
	           	         		number = iCodingRuleManager.getNumber(editData, orgId);
	                    }
	                } else
	                {
	                    number = iCodingRuleManager.readNumber(editData, orgId);
	                }
	            } else
	            {
	                number = prepareNumberForAddnew(iCodingRuleManager, editData, orgId, bindingProperty,coreBase);
	            }
	            txtnumber.setText(number);
	            if(iCodingRuleManager.isModifiable(editData, orgId))
	                setNumberTextEnabled(txtnumber,editData,orgId);
	            return;
	        }
	    }
	    catch(Exception err)
	    {
	        setNumberTextEnabled(txtnumber,editData,orgId);
	    }
	    setNumberTextEnabled(txtnumber,editData,orgId);
	}
	public static String prepareNumberForAddnew(ICodingRuleManager iCodingRuleManager, IObjectValue editData, String orgId, String bindingProperty,ICoreBase coreBase)throws Exception{
		String number = null;
		FilterInfo filter = null;
	     int i = 0;
	     do
	     {
	         if(bindingProperty != null)
	             number = iCodingRuleManager.getNumber(editData, orgId, bindingProperty, "");
	         else
	             number = iCodingRuleManager.getNumber(editData, orgId);
	         filter = new FilterInfo();
	         filter.getFilterItems().add(new FilterItemInfo("number", number));
	         if(editData.get("id") != null)
	             filter.getFilterItems().add(new FilterItemInfo("id", editData.get("id").toString(), CompareType.NOTEQUALS));
	         i++;
	     } while(coreBase.exists(filter) && i < 1000);
	     return number;
	}
	public static void setNumberTextEnabled(KDTextField txtnumber,IObjectValue editData,String orgId){
		boolean isAllowModify = FDCClientUtils.isAllowModifyNumber(editData, orgId);
        txtnumber.setEnabled(isAllowModify);
        txtnumber.setEditable(isAllowModify);
	}
	public static void getProductTypeNodes(KDTree treeMain,DefaultKingdeeTreeNode node) throws BOSException{
		if(node.getUserObject() instanceof SellProjectInfo&&node.isLeaf()) {
			RoomCollection build=RoomFactory.getRemoteInstance().getRoomCollection("select productType.* from where building.sellProject.id='"+((SellProjectInfo)node.getUserObject()).getId()+"'");
			Set pro=new HashSet();
			for(int i=0;i<build.size();i++){
				if(build.get(i).getProductType()!=null&&build.get(i).getProductType().getName()!=null){
					if(!pro.contains(build.get(i).getProductType().getId())){
						pro.add(build.get(i).getProductType().getId());
						DefaultKingdeeTreeNode sonNode = new DefaultKingdeeTreeNode(build.get(i).getProductType());
						sonNode.setCustomIcon(EASResource.getIcon("imgTree_folder_leaf"));	
						node.add(sonNode);
					}
				}
			}
			treeMain.expandAllNodes(false,node);
		}
		for (int i = 0; i < node.getChildCount(); i++) {
			getProductTypeNodes(treeMain,(DefaultKingdeeTreeNode) node.getChildAt(i));
		}
	}
	public static String getStringFromSet(Set srcSet) {
		String retStr = "";
		if (srcSet == null || srcSet.size() == 0)
			return retStr;
		Iterator iter = srcSet.iterator();
		while (iter.hasNext()) {
			Object obj = iter.next();
			if (obj instanceof String)
				retStr += ",'" + (String) obj + "'";
		}
		if (!retStr.equals(""))
			retStr = retStr.replaceFirst(",", "");
		return retStr;
	}
	public static void addSumColoum(IRow sumRow,IRow row,String name[]){
		for(int i=0;i<name.length;i++){
			if(sumRow.getCell(name[i]).getValue()==null){
				sumRow.getCell(name[i]).setValue(FDCHelper.ZERO);
			}
			if(row.getCell(name[i]).getValue() instanceof BigDecimal){
				sumRow.getCell(name[i]).setValue(((BigDecimal)sumRow.getCell(name[i]).getValue()).add((BigDecimal)row.getCell(name[i]).getValue()));
			}else if(row.getCell(name[i]).getValue() instanceof Integer){
				sumRow.getCell(name[i]).setValue(Integer.valueOf(Integer.parseInt(sumRow.getCell(name[i]).getValue().toString()+((Integer)row.getCell(name[i]).getValue()).intValue())));
			}
		}
	}
	public static Set getPermitSaleManSet(SellProjectInfo sellProject,Map permit) throws EASBizException, BOSException{
		UserInfo currentUserInfo = SysContext.getSysContext().getCurrentUserInfo();
		if(sellProject!=null){
			if(permit.containsKey(sellProject.getId().toString())){
				return (Set)permit.get(sellProject.getId().toString());
			}else{
				Set id=NewCommerceHelper.getPermitSaleManIdSet(sellProject, currentUserInfo);
				permit.put(sellProject.getId().toString(), id);
				return id;
			}
		}else{
			return null;
		}
	}
	public static boolean isControl(Context ctx,UserInfo user) throws EASBizException, BOSException{
		if(user==null) return false;
		if(ctx==null){
			OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentSaleUnit();
			if(orgUnit==null) return false;
			return MarketUnitControlFactory.getRemoteInstance().exists("select id from where orgUnit.Id = '"+orgUnit.getId().toString()+"' and controler.id = '"+user.getId().toString()+"'");
		}else{
			OrgUnitInfo orgUnit = ContextUtil.getCurrentOrgUnit(ctx,OrgType.Sale);
			return MarketUnitControlFactory.getLocalInstance(ctx).exists("select id from where orgUnit.Id = '"+orgUnit.getId().toString()+"' and controler.id = '"+user.getId().toString()+"'");
		}
	}
}
