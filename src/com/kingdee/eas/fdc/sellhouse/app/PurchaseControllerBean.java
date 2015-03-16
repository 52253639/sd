package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo;
import com.kingdee.eas.fdc.sellhouse.FunctionSetting;
import com.kingdee.eas.fdc.sellhouse.GenFdcTrasfBillFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurDistillCommisionEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurDistillCommisionEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurDistillCommisionEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiptTypeStateEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractCollection;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.eas.fdc.sellhouse.SellHouseException;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseStateEnum;
import com.kingdee.eas.fdc.tenancy.MarketingUnitFactory;
import com.kingdee.eas.fdc.tenancy.MarketingUnitInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberCollection;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberFactory;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitSellProjectCollection;
import com.kingdee.eas.fdc.tenancy.MarketingUnitSellProjectFactory;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class PurchaseControllerBean extends AbstractPurchaseControllerBean {
	private static final String AUDITED = "1";
	private static final String UNAUDITED = "0";
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.PurchaseControllerBean");
	//引用售楼设置参数 控制 是否预定金转为房款 eric_wang 2010.08.12,取消使用此参数 2010.09.08
//	private RoomDisplaySetting setting = null;
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		PurchaseInfo purchase = PurchaseFactory.getLocalInstance(ctx).getPurchaseInfo(pk);
		RoomInfo info  = RoomFactory.getLocalInstance(ctx).getRoomInfo(new ObjectUuidPK(purchase.getRoom().getId().toString()));
		SheRoomStateFactory.setRoomOnShowState(ctx, info, purchase, false, false);
		super._delete(ctx, pk);
	}
	//根据房间的所属项目查询出项目下的“认购业务以实际收款为准”参数是否启动
	public boolean isEnabledIsActGathering(Context ctx,PurchaseInfo purInfo){
		//by zgy  增加参数判断
		RoomDisplaySetting setting1 = new RoomDisplaySetting(ctx);
		HashMap functionSetMap = (HashMap)setting1.getFunctionSetMap();
		FunctionSetting funcSet = (FunctionSetting)functionSetMap.get(purInfo.getSellProject().getId().toString());
		/**
		 * 当拿不到这个参数的时候funcSet是为空的，一定要做非空的判断
		 * new update by renliang at 2011-3-17
		 */
		
		if(funcSet!=null && funcSet.getIsActGathering()!=null){
			return funcSet.getIsActGathering().booleanValue();
		}
		return false;
	}
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {		
		PurchaseInfo purchase = (PurchaseInfo) model;
		if (purchase.getPayType() == null) {
			throw new SellHouseException(SellHouseException.NOPAYTYPE);
		}
		PurchaseCustomerInfoCollection customerInfos = purchase
				.getCustomerInfo();
		for (int i = 0; i < customerInfos.size(); i++) {
			PurchaseCustomerInfoInfo customerInfoInfo = customerInfos.get(i);
			FDCCustomerInfo customer = customerInfoInfo.getCustomer();
			if (customer != null) {
				//---这里只更新认购时可能改动的信息，避免使用update(model)
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("postalcode");
				sels.add("phone");
				sels.add("certificateName");
				sels.add("certificateNumber");
				sels.add("mailAddress");
				sels.add("description");
				
				/***
				 * 反写性别 by xiaoao_liu
				 * at 2010-9-19
				 */
				sels.add("sex");
				FDCCustomerFactory.getLocalInstance(ctx).updatePartial(customer, sels);
//				FDCCustomerFactory.getLocalInstance(ctx).update(new ObjectUuidPK(customer.getId()), customer);
				//-------
				FDCCustomerFactory.getLocalInstance(ctx).addToSysCustomer(customer.getId().toString());
			}
		}
		SincerityPurchaseInfo sin = (SincerityPurchaseInfo) purchase
				.getSincerityPurchase();
		if (sin != null) {
			if (!sin.getSincerityState().equals(
					SincerityPurchaseStateEnum.ToPurchase)) {
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("sincerityState");
				sin.setSincerityState(SincerityPurchaseStateEnum.ToPurchase);
				SincerityPurchaseFactory.getLocalInstance(ctx).updatePartial(
						sin, sels);
				RoomInfo room = sin.getRoom();
				if(room!=null)
				{
					SincerityPurchaseCollection sincerColl = SincerityPurchaseFactory.getLocalInstance(ctx).getSincerityPurchaseCollection("where room.id='"+room.getId().toString()+"' and " +
							" sincerityState='ArrangeNum' ");
					if(sincerColl!=null && sincerColl.size()>0)
					{
						for(int a=0;a<sincerColl.size();a++)
						{
							SincerityPurchaseInfo info = sincerColl.get(a);
							info.setSincerityState(SincerityPurchaseStateEnum.Invalid);
							SelectorItemCollection sec = new SelectorItemCollection();
							sec.add("sincerityState");
							SincerityPurchaseFactory.getLocalInstance(ctx).updatePartial(
									info, sec);
						}
						
					}
				}
			}
		}
		//由purchaseState来描述单据状态
		purchase.setPurchaseState(PurchaseStateEnum.PurchaseApply);
		IObjectPK objectPK = super._submit(ctx, purchase);
		if (purchase.getRoom().getSellState().equals(
				RoomSellStateEnum.PrePurchase) || purchase.getRoom().getSellState().equals(
						RoomSellStateEnum.Purchase)) {
			// 更新房间最新价格 增加了认购状态的也去更新
			RoomStateFactory.setRoomSellState(ctx,
					null, purchase.getId().toString());
		}
		//反写转认购日期，预定金转定金   by zgy 
		if(!isEnabledIsActGathering(ctx,purchase)){
			RoomInfo room = purchase.getRoom();
			SheRoomStateFactory.setRoomPurState(ctx, room, purchase, null);
			SelectorItemCollection purUpdateSel = new SelectorItemCollection();
			purchase.setToPurchaseDate(purchase.getPurchaseDate());
			purUpdateSel.add("toPurchaseDate");
			SelectorItemCollection roomUpdateSel = new SelectorItemCollection();
			room.setToPurchaseDate(purchase.getPurchaseDate());
			roomUpdateSel.add("toPurchaseDate");
			RoomFactory.getLocalInstance(ctx).updatePartial(room, roomUpdateSel);
			PurchaseFactory.getLocalInstance(ctx).updatePartial(purchase, purUpdateSel);
			//预定金转定金
			PurchasePayListEntryCollection entrys = PurchasePayListEntryFactory
					.getLocalInstance(ctx).getPurchasePayListEntryCollection(
							"select *,moneyDefine.moneyType where head.id='"+purchase.getId()+"' order by seq");
			purchase.getPayListEntry().clear();
			purchase.getPayListEntry().addCollection(entrys);				
			boolean isStillHasPrecont = false;  //是否有未转移的预订金
			for (int i = 0; i < entrys.size(); i++) {
				PurchasePayListEntryInfo entry = entrys.get(i);
				if(entry.getAllRemainAmount().compareTo(new BigDecimal(0))>0){
					if(entry.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.PreconcertMoney)){ 
						isStillHasPrecont = true;
						break;
					}
				}
			}
					//把收的预订金转移到应收明细中去，多余的设置为可退金额
			if (isStillHasPrecont) {
				counteractPurchasePlan(ctx, purchase);
			}
			
			//如果第一批收齐，反写房间状态
			PurchasePayListEntryInfo firstEntryInfo = null;
			BigDecimal allRemainAmount = new BigDecimal(0);	//总的剩余金额 
			PurchasePayListEntryCollection payListColl = purchase.getPayListEntry(); 
			CRMHelper.sortCollection(payListColl, "seq", true);
			for(int i=0;i<payListColl.size();i++) {
				PurchasePayListEntryInfo entryInfo = payListColl.get(i); 
				if(firstEntryInfo==null && !entryInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.PreconcertMoney))  {
					firstEntryInfo = entryInfo;
				}
				allRemainAmount = allRemainAmount.add(entryInfo.getAllRemainAmount());
			}
			if(firstEntryInfo!=null) {
				boolean isFirstFinish = false;
				if(firstEntryInfo.getActRevAmount().compareTo(new BigDecimal(0))>0) {
					if(firstEntryInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.EarnestMoney)) { //若是定金则比较应收和实收
						if(firstEntryInfo.getActRevAmount().compareTo(firstEntryInfo.getAppAmount())>=0) 
							isFirstFinish = true;
					}else if(firstEntryInfo.getAllRemainAmount().compareTo(firstEntryInfo.getAppAmount())>=0){
						isFirstFinish = true;
					}
				}
				if(isFirstFinish){ //第一笔款收完时 变认购			
					String sql = "update T_SHE_Room set FReceiptTypeState ='FirstRe' where fid = '"+room.getId().toString()+"'";
					DbUtil.execute(ctx, sql);
				}else{  //第一笔款未收完   变预订
					if(allRemainAmount.compareTo(new BigDecimal(0))>0) {	//还有剩余收款
						String sql = "update T_SHE_Room set FReceiptTypeState = null where fid = '"+room.getId().toString()+"'";
						DbUtil.execute(ctx, sql);
					}else{	
						String sql = "update T_SHE_Room set FReceiptTypeState = null where fid = '"+room.getId().toString()+"'";
						DbUtil.execute(ctx, sql);
					}
				}
			}
		}	
		return objectPK;
	}

	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		PurchaseInfo purchase = (PurchaseInfo) model;
		PurchaseCustomerInfoCollection customerInfos = purchase
				.getCustomerInfo();
		for (int i = 0; i < customerInfos.size(); i++) {
			PurchaseCustomerInfoInfo customerInfoInfo = customerInfos.get(i);
			FDCCustomerInfo customer = customerInfoInfo.getCustomer();
			if (customer != null) {
				FDCCustomerFactory.getLocalInstance(ctx).update(
						new ObjectUuidPK(customer.getId()), customer);
				FDCCustomerFactory.getLocalInstance(ctx).addToSysCustomer(customer.getId().toString());
			}
		}
		SincerityPurchaseInfo sin = (SincerityPurchaseInfo) purchase
				.getSincerityPurchase();
		if (sin != null) {
			if (!sin.getSincerityState().equals(
					SincerityPurchaseStateEnum.ToPurchase)) {				
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("sincerityState");
				sin.setSincerityState(SincerityPurchaseStateEnum.ToPurchase);
				SincerityPurchaseFactory.getLocalInstance(ctx).updatePartial(
						sin, sels);
				RoomInfo room = sin.getRoom();
				if(room!=null)
				{
					SincerityPurchaseCollection sincerColl = SincerityPurchaseFactory.getLocalInstance(ctx).getSincerityPurchaseCollection("where room.id='"+room.getId().toString()+"' and " +
							" sincerityState='ArrangeNum' ");
					if(sincerColl!=null && sincerColl.size()>0)
					{
						for(int a=0;a<sincerColl.size();a++)
						{
							SincerityPurchaseInfo info = sincerColl.get(a);
							info.setSincerityState(SincerityPurchaseStateEnum.Invalid);
							SelectorItemCollection sec = new SelectorItemCollection();
							sec.add("sincerityState");
							SincerityPurchaseFactory.getLocalInstance(ctx).updatePartial(
									info, sec);
						}
						
					}
				}
			}
		}
		IObjectPK object = super._save(ctx, purchase);
		if (purchase.getRoom().getSellState().equals(
				RoomSellStateEnum.PrePurchase)) {
			RoomStateFactory.setRoomSellState(ctx,
					null, purchase.getId().toString());
		}
		purchase.setPurchaseState(PurchaseStateEnum.PrePurchaseApply);
//		//认购单改造，当销楼参数认购业务以实际收款为准设置为不勾选，对于认购单业务操作如下：
//		//房间“预定”状态：房间关联有效的预定单后，房间状态即为“预定”；
//		//说明：有效的预定单是指单据状态为“预定申请、预定复核”的预定单；
//		//这里引用属性SourceFunction存放初始化参数状态，单据生成后，后对单据的维护操作都建立此参数上
		String papr = purchase.getSourceFunction();
		if(papr!=null&&papr.equals("false")){
			SheRoomStateFactory.setRoomPrePurState(ctx, purchase.getRoom(), purchase, null);
		}
		return object;

	}
	
	/**
	 * 实际未调用，预定调用的上面各函数
	 * */
	protected void _save(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		PurchaseInfo purchase = (PurchaseInfo) model;
		purchase.setPurchaseState(PurchaseStateEnum.PrePurchaseApply);
		super._save(ctx, pk, model);
	}

	protected boolean isUseName() {
		return false;
	}

	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("isAfterAudit");
		sels.add("room.*");
		sels.add("payListEntry.*");
		sels.add("payListEntry.moneyDefine.*");
		
		PurchaseInfo purchase = (PurchaseInfo) this.getValue(ctx,new ObjectUuidPK(billId), sels);
		if(!purchase.getPurchaseState().equals(PurchaseStateEnum.PurchaseAudit))	{
			throw new ContractException(new NumericExceptionSubItem("100","该单据不是认购审批状态，不能反审批！"));
		}
		
		RoomSignContractCollection signs = RoomSignContractFactory.getLocalInstance(ctx)
				.getRoomSignContractCollection("where purchase.id ='"+purchase.getId()+"' and state!='"+FDCBillStateEnum.INVALID_VALUE+"'");
		if (signs.size() > 0)	throw new ContractException(new NumericExceptionSubItem("100","已经有签约单,不能反审批！"));
	
		if(PurchaseChangeFactory.getLocalInstance(ctx).exists("where purchase.id = '"+purchase.getId()+"'"))
			throw new ContractException(new NumericExceptionSubItem("100","该认购单已存在对应的变更单，不能反审批！"));
		
/*		以前是有此限制的，现在先不限制
 		if(PurchasePayListEntryFactory.getLocalInstance(ctx).exists("where head.id='"+purchase.getId()+"' and actRevAmount >0 "))
			throw new ContractException(new NumericExceptionSubItem("100","该认购单对应的应收明细已进行过实际收款，不能进行反审批操作！"));
		if(PurchaseElsePayListEntryFactory.getLocalInstance(ctx).exists("where head.id='"+purchase.getId()+"' and actRevAmount >0 "))
			throw new ContractException(new NumericExceptionSubItem("100","该认购单对应的其它应收已进行过实际收款，不能进行反审批操作！"));

*		现在要放开了，反审批后会控制一些字段不能修改 xin_wang 2010.09.21
 */
 		if(PurchasePayListEntryFactory.getLocalInstance(ctx).exists("where head.id='"+purchase.getId()+"' and actRevAmount >0 ")
 				||PurchaseElsePayListEntryFactory.getLocalInstance(ctx).exists("where head.id='"+purchase.getId()+"' and actRevAmount >0 ")){
 			//update认购单上的，记录此认购单是否曾经审批字段为true
 			SelectorItemCollection coll = new SelectorItemCollection();
 			coll.add("isAfterAudit");
 			purchase.setIsAfterAudit(true);
 			_updatePartial(ctx, purchase, coll);
 		}
			
		
			

		//更新认购单的认购状态
		purchase.setPurchaseState(PurchaseStateEnum.PurchaseApply);
		SelectorItemCollection purUpdateSels = new SelectorItemCollection();
		purUpdateSels.add("purchaseState");
		_updatePartial(ctx, purchase, purUpdateSels);

		
		//---主要为了实现已审批的认购单的客户资料不允许修改,具体请结合 _audit() 中的注释来看  zhicheng_jin
		String setAudited = "update T_SHE_PurchaseCustomerInfo set FNumber=? where FHeadID=?";
		Object[] params = new Object[]{UNAUDITED, billId.toString()};
		DbUtil.execute(ctx, setAudited, params);
		
		super._unAudit(ctx, billId);
	}
	
	
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
//		setting = new RoomDisplaySetting(ctx);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("room.*");
		PurchaseInfo purchase = (PurchaseInfo) this.getValue(ctx,new ObjectUuidPK(billId), sels);
		
		if(!PurchaseStateEnum.PurchaseAuditing.equals(purchase.getPurchaseState())){
			if(!PurchaseStateEnum.PurchaseApply.equals(purchase.getPurchaseState())){
				throw new ContractException(new NumericExceptionSubItem("100","只有认购申请的单据才能被认购审批！"));
			}
		}
		
		//加入参数控制<是否自动转款> eric_wang 2010.08.12 ，取消此参数控制 2010.09.08
//		if(setting.getIsPreToOtherMoneyMap()!=null&&setting.getIsPreToOtherMoneyMap().get("isPreToOtherMoney")!=null&&(((Boolean)setting.getIsPreToOtherMoneyMap().get("isPreToOtherMoney")).booleanValue())){
				PurchasePayListEntryCollection entrys = PurchasePayListEntryFactory
				.getLocalInstance(ctx).getPurchasePayListEntryCollection(
						"select *,moneyDefine.moneyType where head.id='"+purchase.getId()+"' order by seq");
				purchase.getPayListEntry().clear();
				purchase.getPayListEntry().addCollection(entrys);
				
				boolean isHasRevMoney = false;		//有收钱
				boolean isStillHasPrecont = false;  //是否有未转移的预订金
				for (int i = 0; i < entrys.size(); i++) {
					PurchasePayListEntryInfo entry = entrys.get(i);
					if(entry.getAllRemainAmount().compareTo(new BigDecimal(0))>0){
						isHasRevMoney = true;
						if(entry.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.PreconcertMoney)){ 
							isStillHasPrecont = true;
							break;
						}
					}
				}
				//把收的预订金转移到应收明细中去，多余的设置为可退金额
				if (isStillHasPrecont) {
					counteractPurchasePlan(ctx, purchase);
				}
			
				//付清第一笔款时，反写房间的认购认购状态
				//增加了预订金的明细， 因而第一笔款应为 第一个非预订金的款项
				boolean isFullEast = false;
				PurchasePayListEntryInfo firstEntry = null;
				for(int i=0;i<entrys.size();i++) {
					if(!entrys.get(i).getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.PreconcertMoney)) { 
						firstEntry = entrys.get(i);
						break;
					}
				}
				if(firstEntry!=null) {
					BigDecimal apAmount = firstEntry.getApAmount();
					if (firstEntry.getAllRemainAmount().compareTo(apAmount) >= 0)		isFullEast = true;
				}
				RoomSellStateEnum sellState = purchase.getRoom().getSellState();
				if (isFullEast) {   //第一笔款收起时，房间状态由待售/预订 --> 认购
					if(sellState.equals(RoomSellStateEnum.OnShow) || sellState.equals(RoomSellStateEnum.PrePurchase)) {
						SheRoomStateFactory.setRoomPurState(ctx, purchase.getRoom(), purchase, null);
					}
				}else if(isHasRevMoney) { //只要有收钱  房间状态由待售 --> 预订
					if(sellState.equals(RoomSellStateEnum.OnShow))	{ 
						SheRoomStateFactory.setRoomPrePurState(ctx, purchase.getRoom(), purchase, null);
					}			
				}
//		}
		
		//更新认购单的认购状态
		purchase.setPurchaseState(PurchaseStateEnum.PurchaseAudit);
		SelectorItemCollection purUpateSels = new SelectorItemCollection();
		purUpateSels.add("purchaseState");
		_updatePartial(ctx, purchase, purUpateSels);
		
		//-------认购审批后置认购客户的标志位,如果为已审核的客户，不可修改，反审核时反操作
		String setAudited = "update T_SHE_PurchaseCustomerInfo set FNumber=? where FHeadID=?";
		Object[] params = new Object[]{AUDITED, billId.toString()};
		DbUtil.execute(ctx, setAudited, params);
		//-------------------
		
		super._audit(ctx, billId);
	}


	/**
	 * 把收的预订金转移到应收明细中去，多余的设置为可退金额
	 * TODO 把之前的预订金收款做转款操作
	 */
	private void counteractPurchasePlan(Context ctx, PurchaseInfo purchase)	throws BOSException, EASBizException {
		Date lastPreRevDate = null; //预订金的最后一次收款日期
		CoreBaseCollection coreBaseColl = new CoreBaseCollection();
		PurchasePayListEntryCollection preEntryColl = new PurchasePayListEntryCollection(); //预订定金明细
		PurchasePayListEntryCollection payEntryColl = new PurchasePayListEntryCollection(); //应收的明细
		
		for(int i=0;i<purchase.getPayListEntry().size();i++) {
			PurchasePayListEntryInfo eyInfo = purchase.getPayListEntry().get(i);
			MoneyDefineInfo moDeInfo = eyInfo.getMoneyDefine();  //款项类型为预订金类型
			if(moDeInfo!=null && moDeInfo.getMoneyType().equals(MoneyTypeEnum.PreconcertMoney)) {	
				preEntryColl.add(eyInfo);
				if(eyInfo.getActRevDate()!=null && (lastPreRevDate==null || eyInfo.getActRevDate().after(lastPreRevDate)))
					lastPreRevDate = eyInfo.getActRevDate();
			}
			else payEntryColl.add(eyInfo);
			coreBaseColl.add(eyInfo);
		}
		if(lastPreRevDate==null) lastPreRevDate = CRMHelper.getServerDate2(ctx); 

		if(preEntryColl.size()==0 || payEntryColl.size()==0) return;	//如果没有预订金明细或应收明细这不用做转款操作了
		
		//利用已存在的收款单来复制一张新的
		FDCReceivingBillInfo fdcRevBillInfo = GenFdcTrasfBillFactory.genTrasfBill(ctx, preEntryColl.get(0).getId().toString()); 
		if(fdcRevBillInfo==null) return;
		
		for(int i=0;i<preEntryColl.size();i++) { //转移所有的预订金到应收明细上
			PurchasePayListEntryInfo preEntryInfo = preEntryColl.get(i);
			BigDecimal preLeftAmount =  preEntryInfo.getAllRemainAmount(); //剩余金额
			if(preLeftAmount.compareTo(FDCHelper.ZERO)<=0) continue;

			for(int j=0;j<payEntryColl.size();j++) {
				PurchasePayListEntryInfo payEntryInfo = payEntryColl.get(j);
				BigDecimal payAppAmount = payEntryInfo.getAppAmount();
				BigDecimal unPayAmount = payAppAmount.subtract(payEntryInfo.getAllRemainAmount());  //未付金额
				if(unPayAmount.compareTo(FDCHelper.ZERO)<=0) continue;  
				
				//转款： 原明细oldEntry -x金额 ,新明细newEntry +x金额
				if(preLeftAmount.compareTo(unPayAmount)>=0) {
					preEntryInfo.setHasTransferredAmount(preEntryInfo.getHasTransferredAmount().add(unPayAmount));
					payEntryInfo.setActRevAmount(payEntryInfo.getActRevAmount().add(unPayAmount));
					preLeftAmount = preLeftAmount.subtract(unPayAmount);
					
					GenFdcTrasfBillFactory.setTrasfEntry(ctx,fdcRevBillInfo,unPayAmount,null
										,payEntryInfo.getMoneyDefine(),null,payEntryInfo.getId().toString(),RevListTypeEnum.purchaseRev
										,preEntryInfo.getMoneyDefine(),preEntryInfo.getId().toString(),RevListTypeEnum.purchaseRev);
					
				}else{
					preEntryInfo.setHasTransferredAmount(preEntryInfo.getHasTransferredAmount().add(preLeftAmount));
					payEntryInfo.setActRevAmount(payEntryInfo.getActRevAmount().add(preLeftAmount));
					
					GenFdcTrasfBillFactory.setTrasfEntry(ctx,fdcRevBillInfo,preLeftAmount,null
											,payEntryInfo.getMoneyDefine(),null,payEntryInfo.getId().toString(),RevListTypeEnum.purchaseRev
											,preEntryInfo.getMoneyDefine(),preEntryInfo.getId().toString(),RevListTypeEnum.purchaseRev);
					
					preLeftAmount = new BigDecimal(0);
				}
				//payEntryInfo.setActRevDate(lastPreRevDate);
				
				if(preLeftAmount.compareTo(new BigDecimal(0))<=0) break;
			}
			
			if(preLeftAmount.compareTo(new BigDecimal(0))>0) {	//把应收明细全部冲抵完后还有剩余的，设置为可退金额
				preEntryInfo.setLimitAmount(preEntryInfo.getLimitAmount().add(preLeftAmount));
				preEntryInfo.setIsRemainCanRefundment(true);
			}
		}
				
		GenFdcTrasfBillFactory.submitNewTrasfBill(ctx, fdcRevBillInfo, "com.kingdee.eas.fdc.sellhouse.app.SheRevNoHandle");
	}

	protected void checkBill(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		PurchaseInfo billInfo = ((PurchaseInfo) model);
		PurchaseStateEnum purchaseState = billInfo.getPurchaseState();
		if (purchaseState.equals(
				PurchaseStateEnum.ChangeRoomBlankOut)
				|| purchaseState.equals(
						PurchaseStateEnum.QuitRoomBlankOut)
				|| purchaseState.equals(
						PurchaseStateEnum.NoPayBlankOut)
				|| purchaseState.equals(
						PurchaseStateEnum.ManualBlankOut)
				|| purchaseState.equals(
						PurchaseStateEnum.AdjustBlankOut)) {
			return;
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("number", billInfo.getNumber()));
		
		filter.getFilterItems().add(
				new FilterItemInfo("purchaseState",
						PurchaseStateEnum.ChangeRoomBlankOut, CompareType.NOTEQUALS));
		
		filter.getFilterItems().add(
				new FilterItemInfo("purchaseState",
						PurchaseStateEnum.QuitRoomBlankOut, CompareType.NOTEQUALS));
		
		filter.getFilterItems().add(
				new FilterItemInfo("purchaseState",
						PurchaseStateEnum.NoPayBlankOut, CompareType.NOTEQUALS));
		
		filter.getFilterItems().add(
				new FilterItemInfo("purchaseState",
						PurchaseStateEnum.AdjustBlankOut, CompareType.NOTEQUALS));
		
		filter.getFilterItems().add(
				new FilterItemInfo("purchaseState",
						PurchaseStateEnum.ManualBlankOut, CompareType.NOTEQUALS));
		
		filter.getFilterItems()
				.add(
						new FilterItemInfo("orgUnit.id", billInfo.getOrgUnit()
								.getId()));
		
		if (billInfo.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", billInfo.getId().toString(),
							CompareType.NOTEQUALS));
		}

		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.NUMBER_DUP);
		}
	}

	// 设置组织
	protected void setPropsForBill(Context ctx, FDCBillInfo fDCBillInfo)
			throws EASBizException, BOSException {
		if (fDCBillInfo.getOrgUnit() == null) {
			FullOrgUnitInfo orgUnit = ContextUtil.getCurrentSaleUnit(ctx)
					.castToFullOrgUnitInfo();
			fDCBillInfo.setOrgUnit(orgUnit);
		}
	}

	protected String getCurrentOrgId(Context ctx) {
		SaleOrgUnitInfo org = ContextUtil.getCurrentSaleUnit(ctx);
		String curOrgId = org.getId().toString();
		return curOrgId;
	}

	
	/**
	 * 预定复核
	 * */
	protected void _checkPrePurchase(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException {
		for(int i=0; i<pks.length; i++){
			PurchaseInfo purchase = (PurchaseInfo) getValue(ctx, pks[i]);
			purchase.setPurchaseState(PurchaseStateEnum.PrePurchaseCheck);
			purchase.setPrePurchaseAuditor(ContextUtil.getCurrentUserInfo(ctx));
			purchase.setPrePurchaseAuditDate(new Date());
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("purchaseState");
			sels.add("prePurchaseAuditor");
			sels.add("prePurchaseAuditDate");
			_updatePartial(ctx, purchase, sels);
		}
	}

	/**
	 * 预定反复核
	 * */
	protected void _uncheckPrePurchase(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException {
		for(int i=0; i<pks.length; i++){
			PurchaseInfo purchase = (PurchaseInfo) getValue(ctx, pks[i]);
			purchase.setPurchaseState(PurchaseStateEnum.PrePurchaseApply);
			purchase.setPrePurchaseAuditor(ContextUtil.getCurrentUserInfo(ctx));
			purchase.setPrePurchaseAuditDate(new Date());
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("purchaseState");
			sels.add("prePurchaseAuditor");
			sels.add("prePurchaseAuditDate");
			_updatePartial(ctx, purchase, sels);
		}
	}

	protected void _setAudittingStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		PurchaseInfo billInfo = new PurchaseInfo();
		billInfo.setId(billId);
		billInfo.setPurchaseState(PurchaseStateEnum.PurchaseAuditing);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("purchaseState");
		_updatePartial(ctx, billInfo, selector);
		
		super._setAudittingStatus(ctx, billId);
	}
	/**
	 * 原来没有实现此方法，不知道为什么！
	 * by renliang at 2010-11-17
	 */
	protected void _setSubmitStatus(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		PurchaseInfo billInfo = new PurchaseInfo();
		billInfo.setId(billId);
		billInfo.setPurchaseState(PurchaseStateEnum.PurchaseApply);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("purchaseState");
		_updatePartial(ctx, billInfo, selector);
		
		super._setSubmitStatus(ctx, billId);
	}
	
	protected void _purDistillUpdate(Context ctx) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("salesman.*");
		sel.add("sellProject");
		sel.add("room.sellState");
		sel.add("distillCommisionEntry.*");
		filterInfo.getFilterItems().add(new FilterItemInfo("state", "4AUDITTED"));
		view.setSelector(sel);
		view.setFilter(filterInfo);
		//找出已审批状态的认购单 
		PurchaseCollection purColl = PurchaseFactory.getLocalInstance(ctx).getPurchaseCollection(view);
		for(int j=0;j<purColl.size();j++)
		{
			PurchaseInfo purInfo = purColl.get(j);
			RoomInfo roomInfo = purInfo.getRoom();
			//认购或者签约状态的房间才处理
			if(RoomSellStateEnum.Purchase.equals(roomInfo.getSellState()) ||
					RoomSellStateEnum.Sign.equals(roomInfo.getSellState()))
			{
				PurDistillCommisionEntryCollection purComColl = purInfo.getDistillCommisionEntry();
				if(purInfo.getSalesman()==null)
				{
					continue;
				}
				if(purInfo.getSellProject()==null)
				{
					continue;
				}
				UserInfo salesMan = purInfo.getSalesman();
				SellProjectInfo project = purInfo.getSellProject();
				//同一个营销人员在同一个项目下只有一种职能，也就是同一个人在同一个项目里每一个职能都只能在一个营销单元里
				MarketingUnitInfo marketInfo = getMarketingUnit(ctx,salesMan,project,new Date(),true);
				int i=0;					
				//取得营销人员的提佣比例
				CoreBaseCollection purCommisionColl = new CoreBaseCollection(); 
				//为空的话说明以前没有分录，需要处理
				if(purComColl.size()>0)
				{				
					//当佣金分录有值的时候把以前的全部删除掉重新加一次，保证可重复执行
					//就是后来不管什么时候增加了负责人提佣，点升级一样会起作用
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("head.id",purInfo.getId().toString()));
					try {
						PurDistillCommisionEntryFactory.getLocalInstance(ctx).delete(filter);
					} catch (EASBizException e) {
						e.printStackTrace();
					}
				}
				//数据升级的时候肯定会有找不到营销单元的情况，所以有多少正确的就升多少
				if(marketInfo==null)
				{
					continue;
				}
				//能找到营销单元就把对应的佣金分录加上
				setPurDisCommisionEntry(ctx, marketInfo, salesMan, purCommisionColl, purInfo, i);				
				
				//找出当前认购单对应的收款单，如果收款单已审批并且佣金分录为空的话，把认购单上的佣金分录复制过去
				//佣金分录不为空的话，可能也需要处理下，当原来没有添加负责人提佣且收款单已审批的时候，收款单提佣
				//分录只会增加营销顾问的提佣分录，但后来又添加了负责人且执行了升级操作，没有把负责人的提佣分录复制
				//过去，在这处理下
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().
				add(new FilterItemInfo("purchase.id",purInfo.getId().toString()));
				ev.setFilter(filter);
				
				ev.getSelector().add("*");
				ev.getSelector().add("distillCommisionEntry.*");
				FDCReceiveBillCollection fdcRecColl = FDCReceiveBillFactory.getLocalInstance(ctx).getFDCReceiveBillCollection(ev);
				for(int k=0;k<fdcRecColl.size();k++)
				{
					FDCReceiveBillInfo recInfo = fdcRecColl.get(k);
					EntityViewInfo ev2 = new EntityViewInfo();
					FilterInfo filter2 = new FilterInfo();
					filter2.getFilterItems().
					add(new FilterItemInfo("fdcreceivebill.id",recInfo.getId().toString()));
					filter2.getFilterItems().
					add(new FilterItemInfo("billStatus","12"));
					filter2.getFilterItems().
					add(new FilterItemInfo("billStatus","14"));
					filter2.setMaskString("#0 and (#1 or #2)");
					ev2.setFilter(filter2);						
/*			
 * 新收款单无此功能。。。。。		
 * 			ReceivingBillCollection receivingBillColl = ReceivingBillFactory.getLocalInstance(ctx).getReceivingBillCollection(ev2);
				
					//异常情况全都跳过，有多少升级多少
					if(receivingBillColl.size()==0)
					{
						continue;
					}
					ReceivingBillInfo receivingBillInfo =receivingBillColl.get(0);
					if(BillStatusEnum.AUDITED.equals(receivingBillInfo.getBillStatus()) ||
						BillStatusEnum.RECED.equals(receivingBillInfo.getBillStatus()))
					{
						ReceiveDistillCommisionEntryCollection recComColl = recInfo.getDistillCommisionEntry();
						if(recComColl.size()>0)
						{
							FilterInfo filter3 = new FilterInfo();
							filter3.getFilterItems().add(
									new FilterItemInfo("head.id",recInfo.getId().toString()));
							try {
								ReceiveDistillCommisionEntryFactory.getLocalInstance(ctx).delete(filter3);
							} catch (EASBizException e) {
								e.printStackTrace();
							}						
						}							
						setRecDisCommisionEntry(ctx, recInfo, marketInfo, purInfo, recComColl);						
					}		*/			
				}
			}
		}
	}
	
	//根据认购单提佣分录复制到收款单提佣分录
	private static void setRecDisCommisionEntry(Context ctx,FDCReceiveBillInfo recInfo,MarketingUnitInfo marketInfo,PurchaseInfo purInfo,ReceiveDistillCommisionEntryCollection recComColl) throws BOSException
	{
		PurchaseInfo purchaseInfo = recInfo.getPurchase();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("distillCommisionEntry.*");
		try {
			purInfo = PurchaseFactory.getLocalInstance(ctx).getPurchaseInfo(new ObjectUuidPK(purchaseInfo.getId()), sels);
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		PurDistillCommisionEntryCollection purComEntryColl = purInfo.getDistillCommisionEntry();
		if(purComEntryColl.size()>0)
		{
			CoreBaseCollection recComEntryColl = new CoreBaseCollection();
			PurDistillCommisionEntryCollection purchaseComColl = purInfo.getDistillCommisionEntry();
			//ReceiveDistillCommisionEntryCollection recComColl = new ReceiveDistillCommisionEntryCollection();
			for(int m=0;m<purchaseComColl.size();m++)
			{
				PurDistillCommisionEntryInfo purComInfo = purchaseComColl.get(m);
				ReceiveDistillCommisionEntryInfo recComInfo = new ReceiveDistillCommisionEntryInfo();
				recComInfo.setSharePercent(purComInfo.getSharePercent());
				recComInfo.setTakePercentage(purComInfo.getTakePercentage());
				recComInfo.setDistillCommisionLevel(purComInfo.getDistillCommisionLevel());
				recComInfo.setIsAchieveCommision(purComInfo.isIsAchieveCommision());
				recComInfo.setUser(purComInfo.getUser());
				if(purComInfo.getDistillCommisionLevel()==0 && purComInfo.getMarketUnit()==null)
				{
					recComInfo.setMarketUnit(marketInfo);
				}else
				{
					recComInfo.setMarketUnit(purComInfo.getMarketUnit());
				}							
				recComInfo.setHead(recInfo);
				recComEntryColl.add(recComInfo);
			}
			try {
				ReceiveDistillCommisionEntryFactory.getLocalInstance(ctx).addnew(recComEntryColl);
			} catch (EASBizException e) {
				e.printStackTrace();
			}
		}
	}
	
	//增加认购单提佣分录
	private static void setPurDisCommisionEntry(Context ctx,MarketingUnitInfo marketInfo,UserInfo salesMan,CoreBaseCollection purCommisionColl,PurchaseInfo purInfo,int i) throws BOSException
	{
		PurDistillCommisionEntryInfo purCommisionInfo = new PurDistillCommisionEntryInfo();
		purCommisionInfo.setUser(salesMan);
		//比例暂时为0.根据另外的取数规则来取
		purCommisionInfo.setSharePercent(new BigDecimal(0));
		//分单比例根据营销单元的设置来取
		purCommisionInfo.setTakePercentage(new BigDecimal(100));
		//提佣级次，根据该营销单元的上级营销单元个数来定
		purCommisionInfo.setDistillCommisionLevel(i);
		//间接提佣分组,业务员业绩行此处为空,负责人行存为所在营销单元,
		//业务员也不为空了，把所在营销单元写上去 2009-11-09
		purCommisionInfo.setMarketUnit(marketInfo);
		//是否完成提佣,默认全为false
		purCommisionInfo.setIsAchieveCommision(false);
		purCommisionInfo.setHead(purInfo);
		purCommisionColl.add(purCommisionInfo);
		//在营销单元里的负责人并且是否计提为真，并且在职的负责人的提佣比例也写入分录，如果该负责人
		//已经离职，那么查看他的离职日期是否在转认购日期之前，如果是则不需要写入，否则也需要写入分录中
		List dutyList = getDutyPerson(ctx,marketInfo,new Date());
		if(dutyList.size()>0)
		{
			i++;
		}
		for(int k=0;k<dutyList.size();k++)
		{
			PurDistillCommisionEntryInfo purCommisionInfo2 = new PurDistillCommisionEntryInfo();
			MarketingUnitMemberInfo markMemberInfo = (MarketingUnitMemberInfo)dutyList.get(k);
			purCommisionInfo2.setUser(markMemberInfo.getMember());
			purCommisionInfo2.setSharePercent(new BigDecimal(0));
			purCommisionInfo2.setTakePercentage(markMemberInfo.getTakePercentage());
			purCommisionInfo2.setDistillCommisionLevel(i);
			purCommisionInfo2.setMarketUnit(markMemberInfo.getHead());
			purCommisionInfo2.setIsAchieveCommision(false);
			purCommisionInfo2.setHead(purInfo);
			purCommisionColl.add(purCommisionInfo2);
		}
		try {
			//如果该营销单元的上级营销单元不为空，那么找到上级营销单元有间接提佣属性的负责人						
			setDistillCommision(ctx,purCommisionColl,marketInfo,i,purInfo,new Date());	
			PurDistillCommisionEntryFactory.getLocalInstance(ctx).addnew(purCommisionColl);
		} catch (EASBizException e) {
			e.printStackTrace();
		}
	}
	
	//同一个营销人员在同一个项目下只有一种职能，也就是同一个人在同一个项目里每一个职能都只能在一个营销单元里
	private static MarketingUnitInfo getMarketingUnit(Context ctx,UserInfo salesMan,SellProjectInfo project,Date toPurchaseDate,boolean isDate) throws BOSException
	{	
		Set marketIDSet = new HashSet();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();	
		view.getSelector().add("head.*");
		filter.getFilterItems().
		add(new FilterItemInfo("member.id",salesMan.getId().toString()));
		//		filter.getFilterItems()
		//		.add(new FilterItemInfo("sellProject.sellProject.id", project.getId().toString()));
		filter.getFilterItems()
		.add(new FilterItemInfo("isSellFunction",Boolean.TRUE));
		if(isDate)
		{
			//上岗日期在转认购日期之前并且没有离职的职员
			filter.getFilterItems()
			.add(new FilterItemInfo("accessionDate",toPurchaseDate,CompareType.LESS_EQUALS));
			filter.getFilterItems()
			.add(new FilterItemInfo("dimissionDate",null));
			filter.getFilterItems().
			add(new FilterItemInfo("dimissionDate",toPurchaseDate,CompareType.GREATER_EQUALS));
			filter.setMaskString("#0 and #1 and #2 and (#3 or #4)");
		}
		
		view.setFilter(filter);

		MarketingUnitMemberCollection marketMemberColl = MarketingUnitMemberFactory.getLocalInstance(ctx).getMarketingUnitMemberCollection(view);
		for(int i=0;i<marketMemberColl.size();i++)
		{
			MarketingUnitInfo marketInfo = marketMemberColl.get(i).getHead();
			marketIDSet.add(marketInfo.getId().toString());
		}

		EntityViewInfo view2 = new EntityViewInfo();
		FilterInfo filter2 = new FilterInfo();
		view2.getSelector().add("head.*");
		view2.getSelector().add("head.member.*");
		filter2.getFilterItems().
		add(new FilterItemInfo("head.id",marketIDSet,CompareType.INCLUDE));
		filter2.getFilterItems()
		.add(new FilterItemInfo("sellProject.id", project.getId().toString()));
		view2.setFilter(filter2);
		MarketingUnitSellProjectCollection marketProjectColl = MarketingUnitSellProjectFactory.getLocalInstance(ctx).getMarketingUnitSellProjectCollection(view2);
		//		MarketingUnitCollection marketColl=MarketingUnitFactory.getLocalInstance(ctx).getMarketingUnitCollection(view);
		MarketingUnitInfo marketInfo = null;
		if(marketProjectColl.size()!=0)
		{
			marketInfo = new MarketingUnitInfo();
			marketInfo = marketProjectColl.get(0).getHead();
		}
		return marketInfo;
	}
	
	//找出营销单元里是否计提属性为true的负责人列表
	private static List getDutyPerson(Context ctx,MarketingUnitInfo marketInfo,Date toPurchaseDate) throws BOSException
	{
		List dutyList = new ArrayList();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.getSelector().add("*");
		view.getSelector().add("member.*");
		view.getSelector().add("head.*");
		filter.getFilterItems().
		add(new FilterItemInfo("head.id",marketInfo.getId().toString()));
		filter.getFilterItems().
		add(new FilterItemInfo("isDuty",Boolean.TRUE));//是否是负责人
		filter.getFilterItems().
		add(new FilterItemInfo("isSharePercent",Boolean.TRUE));//是否计提
		filter.getFilterItems()
		.add(new FilterItemInfo("accessionDate",toPurchaseDate,CompareType.LESS_EQUALS));//上岗日期在转认购日期之前
		filter.getFilterItems().
		add(new FilterItemInfo("dimissionDate",null));//离职日期为空，说明还在职或者是离职日期晚于转认购日期
		filter.getFilterItems().
		add(new FilterItemInfo("dimissionDate",toPurchaseDate,CompareType.GREATER_EQUALS));
		filter.setMaskString("#0 and #1 and #2 and #3 and (#4 or #5)");
		view.setFilter(filter);

		MarketingUnitMemberCollection markMemberColl = MarketingUnitMemberFactory.
		getLocalInstance(ctx).getMarketingUnitMemberCollection(view);
		for(int i=0;i<markMemberColl.size();i++)
		{
			MarketingUnitMemberInfo markMemberInfo = markMemberColl.get(i);
			dutyList.add(markMemberInfo);
		}
		return dutyList;
	}
	
	//如果当前营销存在上级营销单元，并且拥有间接提佣的负责人存在，那么把该负责人写入到分单分录
	private static void setDistillCommision(Context ctx,CoreBaseCollection purCommisionColl,MarketingUnitInfo marketInfo,int i,PurchaseInfo purchaseInfo,Date toPurchaseDate) throws BOSException, EASBizException
	{
		if(marketInfo.getParent()==null)
		{
			return;
		}else
		{
			MarketingUnitInfo marketUnitInfo = marketInfo.getParent();
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("member.*");
			marketUnitInfo = MarketingUnitFactory.getLocalInstance(ctx).getMarketingUnitInfo(new ObjectUuidPK(marketUnitInfo.getId()), sels);
			List dutyList = getDutyPerson(ctx, marketUnitInfo,toPurchaseDate);
			if(dutyList.size()>0)
			{
				i++;
			}
			for(int k=0;k<dutyList.size();k++)
			{
				PurDistillCommisionEntryInfo purCommisionInfo2 = new PurDistillCommisionEntryInfo();
				MarketingUnitMemberInfo markMemberInfo = (MarketingUnitMemberInfo)dutyList.get(k);
				purCommisionInfo2.setUser(markMemberInfo.getMember());
				purCommisionInfo2.setSharePercent(new BigDecimal(0));
				purCommisionInfo2.setTakePercentage(markMemberInfo.getTakePercentage());
				purCommisionInfo2.setDistillCommisionLevel(i);
				purCommisionInfo2.setMarketUnit(markMemberInfo.getHead());
				purCommisionInfo2.setIsAchieveCommision(false);
				purCommisionInfo2.setHead(purchaseInfo);
				purCommisionColl.add(purCommisionInfo2);
			}
			setDistillCommision(ctx,purCommisionColl,marketUnitInfo,i,purchaseInfo,toPurchaseDate);
		}


	}
	
	protected void _submitPrePurchase(Context ctx, IObjectValue model) throws BOSException
	{
		// TODO 自动生成方法存根
		
	}

	protected void _auditPrePurchase(Context ctx, BOSUuid billId) throws BOSException
	{
		IObjectPK [] pk =  new ObjectUuidPK[1];
		pk[0] =  new ObjectUuidPK(billId);
		
		try
		{
			PurchaseFactory.getLocalInstance(ctx).checkPrePurchase(pk);
		} catch (EASBizException e)
		{
			throw new BOSException(e);
		}		
	}

	protected void _receiveBill(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		
	}

	protected void _purAddMarket(Context ctx) throws BOSException,
			EASBizException {	
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("salesman");
		sel.add("sellProject");
		sel.add("marketUnit");
		view.setSelector(sel);
		//找出所有的认购单 
		PurchaseCollection purColl = PurchaseFactory.getLocalInstance(ctx).getPurchaseCollection(view);
		for(int i=0;i<purColl.size();i++)
		{
			PurchaseInfo purInfo = purColl.get(i);
			if(purInfo.getMarketUnit()==null)
			{
				if(purInfo.getSalesman()==null)
				{
					continue;
				}
				if(purInfo.getSellProject()==null)
				{
					continue;
				}
				MarketingUnitInfo marketInfo = getMarketingUnit(ctx,purInfo.getSalesman(),purInfo.getSellProject(),new Date(),false);
				if(marketInfo==null)
				{
					continue;
				}
				purInfo.setMarketUnit(marketInfo);
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("marketUnit");
				PurchaseFactory.getLocalInstance(ctx).updatePartial(purInfo, sels);
			}
		}
	}
	
	/**
	 * 直接签约时，认购单提交方法
	 */
	protected void _immediacySave(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		PurchaseInfo purchase = (PurchaseInfo) model;
		if (purchase.getId() == null
				|| !this._exists(ctx, new ObjectUuidPK(purchase.getId()))
				|| purchase.getNumber() == null
				|| purchase.getNumber().length() == 0) {
			handleIntermitNumber(ctx, purchase);
		}
		if (purchase.getPayType() == null) {
			throw new SellHouseException(SellHouseException.NOPAYTYPE);
		}
		PurchaseCustomerInfoCollection customerInfos = purchase
				.getCustomerInfo();
		for (int i = 0; i < customerInfos.size(); i++) {
			PurchaseCustomerInfoInfo customerInfoInfo = customerInfos.get(i);
			FDCCustomerInfo customer = customerInfoInfo.getCustomer();
			if (customer != null) {
				//---这里只更新认购时可能改动的信息，避免使用update(model)
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("postalcode");
				sels.add("phone");
				sels.add("certificateName");
				sels.add("certificateNumber");
				sels.add("mailAddress");
				sels.add("description");
				
				/***
				 * 反写性别 by xiaoao_liu
				 * at 2010-9-19
				 */
				sels.add("sex");
				FDCCustomerFactory.getLocalInstance(ctx).updatePartial(customer, sels);
//				FDCCustomerFactory.getLocalInstance(ctx).update(new ObjectUuidPK(customer.getId()), customer);
				//-------
				FDCCustomerFactory.getLocalInstance(ctx).addToSysCustomer(customer.getId().toString());
			}
		}
		SincerityPurchaseInfo sin = (SincerityPurchaseInfo) purchase
				.getSincerityPurchase();
		if (sin != null) {
			if (!sin.getSincerityState().equals(
					SincerityPurchaseStateEnum.ToPurchase)) {
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("sincerityState");
				sin.setSincerityState(SincerityPurchaseStateEnum.ToPurchase);
				SincerityPurchaseFactory.getLocalInstance(ctx).updatePartial(
						sin, sels);
				RoomInfo room = sin.getRoom();
				if(room!=null)
				{
					SincerityPurchaseCollection sincerColl = SincerityPurchaseFactory.getLocalInstance(ctx).getSincerityPurchaseCollection("where room.id='"+room.getId().toString()+"' and " +
							" sincerityState='ArrangeNum' ");
					if(sincerColl!=null && sincerColl.size()>0)
					{
						for(int a=0;a<sincerColl.size();a++)
						{
							SincerityPurchaseInfo info = sincerColl.get(a);
							info.setSincerityState(SincerityPurchaseStateEnum.Invalid);
							SelectorItemCollection sec = new SelectorItemCollection();
							sec.add("sincerityState");
							SincerityPurchaseFactory.getLocalInstance(ctx).updatePartial(
									info, sec);
						}
						
					}
				}
			}
		}
		//由purchaseState来描述单据状态
		purchase.setPurchaseState(PurchaseStateEnum.PurchaseApply);
		EntityViewInfo purView=new EntityViewInfo();
		FilterInfo purFilter=new FilterInfo();
		purFilter.getFilterItems().add(new FilterItemInfo("id",purchase.getId().toString()));
		purView.setFilter(purFilter);
		PurchaseCollection purColl = PurchaseFactory.getLocalInstance(ctx)
			.getPurchaseCollection(purView);
		if(purColl!=null && purColl.size()>0){
			PurchaseFactory.getLocalInstance(ctx)
					.update(new ObjectUuidPK(purchase.getId().toString()), purchase);
		}else{
			PurchaseFactory.getLocalInstance(ctx).addnew(purchase);
		}
		
		if (purchase.getRoom().getSellState().equals(
				RoomSellStateEnum.PrePurchase)) {
			// 更新房间最新价格
			RoomStateFactory.setRoomSellState(ctx,
					null, purchase.getId().toString());
		}
		//反写转认购日期，预定金转定金   by zgy 
		if(!isEnabledIsActGathering(ctx,purchase)){
			RoomInfo room = purchase.getRoom();
			SheRoomStateFactory.setRoomPurState(ctx, room, purchase, null);
			SelectorItemCollection purUpdateSel = new SelectorItemCollection();
			purchase.setToPurchaseDate(purchase.getPurchaseDate());
			purUpdateSel.add("toPurchaseDate");
			SelectorItemCollection roomUpdateSel = new SelectorItemCollection();
			room.setToPurchaseDate(purchase.getPurchaseDate());
			roomUpdateSel.add("toPurchaseDate");
			RoomFactory.getLocalInstance(ctx).updatePartial(room, roomUpdateSel);
			PurchaseFactory.getLocalInstance(ctx).updatePartial(purchase, purUpdateSel);
			//预定金转定金
			PurchasePayListEntryCollection entrys = PurchasePayListEntryFactory
					.getLocalInstance(ctx).getPurchasePayListEntryCollection(
							"select *,moneyDefine.moneyType where head.id='"+purchase.getId()+"' order by seq");
			purchase.getPayListEntry().clear();
			purchase.getPayListEntry().addCollection(entrys);				
			boolean isStillHasPrecont = false;  //是否有未转移的预订金
			for (int i = 0; i < entrys.size(); i++) {
				PurchasePayListEntryInfo entry = entrys.get(i);
				if(entry.getAllRemainAmount().compareTo(new BigDecimal(0))>0){
					if(entry.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.PreconcertMoney)){ 
						isStillHasPrecont = true;
						break;
					}
				}
			}
					//把收的预订金转移到应收明细中去，多余的设置为可退金额
			if (isStillHasPrecont) {
				counteractPurchasePlan(ctx, purchase);
			}
			
			//如果第一批收齐，反写房间状态
			PurchasePayListEntryInfo firstEntryInfo = null;
			BigDecimal allRemainAmount = new BigDecimal(0);	//总的剩余金额 
			PurchasePayListEntryCollection payListColl = purchase.getPayListEntry(); 
			CRMHelper.sortCollection(payListColl, "seq", true);
			for(int i=0;i<payListColl.size();i++) {
				PurchasePayListEntryInfo entryInfo = payListColl.get(i); 
				if(firstEntryInfo==null && !entryInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.PreconcertMoney))  {
					firstEntryInfo = entryInfo;
				}
				allRemainAmount = allRemainAmount.add(entryInfo.getAllRemainAmount());
			}
			if(firstEntryInfo!=null) {
				boolean isFirstFinish = false;
				if(firstEntryInfo.getActRevAmount().compareTo(new BigDecimal(0))>0) {
					if(firstEntryInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.EarnestMoney)) { //若是定金则比较应收和实收
						if(firstEntryInfo.getActRevAmount().compareTo(firstEntryInfo.getAppAmount())>=0) 
							isFirstFinish = true;
					}else if(firstEntryInfo.getAllRemainAmount().compareTo(firstEntryInfo.getAppAmount())>=0){
						isFirstFinish = true;
					}
				}
				if(isFirstFinish){ //第一笔款收完时 变认购			
					String sql = "update T_SHE_Room set FReceiptTypeState ='FirstRe' where fid = '"+room.getId().toString()+"'";
					DbUtil.execute(ctx, sql);
				}else{  //第一笔款未收完   变预订
					if(allRemainAmount.compareTo(new BigDecimal(0))>0) {	//还有剩余收款
						String sql = "update T_SHE_Room set FReceiptTypeState = null where fid = '"+room.getId().toString()+"'";
						DbUtil.execute(ctx, sql);
					}else{	
						String sql = "update T_SHE_Room set FReceiptTypeState = null where fid = '"+room.getId().toString()+"'";
						DbUtil.execute(ctx, sql);
					}
				}
			}
		}	
	}
}