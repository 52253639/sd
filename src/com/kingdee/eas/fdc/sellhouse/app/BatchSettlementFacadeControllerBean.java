//package com.kingdee.eas.fdc.sellhouse.app;
//
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//import org.apache.log4j.Logger;
//
//import com.kingdee.bos.BOSException;
//import com.kingdee.bos.Context;
//import com.kingdee.bos.dao.IObjectCollection;
//import com.kingdee.bos.dao.IObjectPK;
//import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
//import com.kingdee.bos.metadata.entity.FilterInfo;
//import com.kingdee.bos.metadata.entity.FilterItemInfo;
//import com.kingdee.bos.metadata.entity.SelectorItemCollection;
//import com.kingdee.bos.metadata.entity.SorterItemCollection;
//import com.kingdee.bos.metadata.entity.SorterItemInfo;
//import com.kingdee.bos.metadata.query.util.CompareType;
//import com.kingdee.bos.util.BOSUuid;
//import com.kingdee.eas.basedata.assistant.CurrencyFactory;
//import com.kingdee.eas.basedata.assistant.CurrencyInfo;
//import com.kingdee.eas.basedata.master.account.AccountViewInfo;
//import com.kingdee.eas.basedata.master.auxacct.AsstActTypeCollection;
//import com.kingdee.eas.basedata.master.auxacct.AsstActTypeFactory;
//import com.kingdee.eas.basedata.master.auxacct.AsstActTypeInfo;
//import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
//import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
//import com.kingdee.eas.common.EASBizException;
//import com.kingdee.eas.common.client.SysContext;
//import com.kingdee.eas.fdc.basedata.FDCHelper;
//import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
//import com.kingdee.eas.fdc.propertymgmt.PPMARBaseCollection;
//import com.kingdee.eas.fdc.propertymgmt.PPMARBaseInfo;
//import com.kingdee.eas.fdc.propertymgmt.PPMGeneralARFactory;
//import com.kingdee.eas.fdc.propertymgmt.PPMGeneralARInfo;
//import com.kingdee.eas.fdc.propertymgmt.PPMMeasureARFactory;
//import com.kingdee.eas.fdc.propertymgmt.PPMMeasureARInfo;
//import com.kingdee.eas.fdc.propertymgmt.PPMTemporaryFactory;
//import com.kingdee.eas.fdc.propertymgmt.PPMTemporaryInfo;
//import com.kingdee.eas.fdc.sellhouse.CustomerEntryCollection;
//import com.kingdee.eas.fdc.sellhouse.CustomerEntryFactory;
//import com.kingdee.eas.fdc.sellhouse.CustomerEntryInfo;
//import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
//import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillCollection;
//import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryCollection;
//import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryFactory;
//import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryInfo;
//import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillFactory;
//import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo;
//import com.kingdee.eas.fdc.sellhouse.GatheringEnum;
//import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
//import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastCollection;
//import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastFactory;
//import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastInfo;
//import com.kingdee.eas.fdc.sellhouse.PayQuomodoEnum;
//import com.kingdee.eas.fdc.sellhouse.ReceiveBillTypeEnum;
//import com.kingdee.eas.fdc.sellhouse.RoomInfo;
//import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
//import com.kingdee.eas.fi.cas.BillStatusEnum;
//import com.kingdee.eas.fi.cas.ReceivingBillEntryInfo;
//import com.kingdee.eas.fi.cas.ReceivingBillFactory;
//import com.kingdee.eas.fi.cas.ReceivingBillInfo;
//import com.kingdee.eas.fi.cas.SettlementStatusEnum;
//import com.kingdee.eas.fi.cas.SourceTypeEnum;
//import com.kingdee.eas.framework.CoreBaseCollection;
//import com.kingdee.eas.util.app.ContextUtil;
//import com.kingdee.util.UuidException;
//
//public class BatchSettlementFacadeControllerBean extends	AbstractBatchSettlementFacadeControllerBean
//{
//	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.BatchSettlementFacadeControllerBean");
//	
//	
//	//缓存款项 科目 对应表
//	private Map moneyAccountMapping = null;
//	//初始化款项 科目 对应表
//	private void initMoneyAccountMapping(Context ctx) throws BOSException
//	{
//		moneyAccountMapping = new HashMap();
//
//		CompanyOrgUnitInfo company = ContextUtil.getCurrentFIUnit(ctx);
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		view.setFilter(filter);
//		if (company != null)
//		{
//			filter.getFilterItems().add(
//					new FilterItemInfo("fullOrgUnit.id", company.getId()
//							.toString()));
//		} else
//		{
//			filter.getFilterItems().add(
//					new FilterItemInfo("fullOrgUnit.id", "idnull"));
//		}
//
//		view.getSelector().add("accountView.*");
//		view.getSelector().add("moneyDefine.id");
//		view.getSelector().add("isChanged");
//		view.getSelector().add("*");
//
//		MoneySubjectContrastCollection moneySubjects = MoneySubjectContrastFactory.getLocalInstance(ctx).getMoneySubjectContrastCollection(view);
//		for (int i = 0; i < moneySubjects.size(); i++)
//		{
//			MoneySubjectContrastInfo moneySubject = moneySubjects.get(i);
//			MoneyDefineInfo moneyDefine = moneySubject.getMoneyDefine();
//			if (moneyDefine != null)
//			{
//				moneyAccountMapping.put(moneyDefine.getId().toString(),	moneySubject);
//			}
//		}
//	}
//
//	/**
//	 * 根据客户去得到 房地产 收款单的 ID 集合
//	 * @param customer
//	 * @return
//	 * @throws BOSException 
//	 */
//	private Set getFdcRecBillId(Context ctx,CustomerInfo customer) throws BOSException
//	{
//		Set set = new HashSet();
//		
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		view.setFilter(filter);
//		
//		view.getSelector().add("head.id");
//		filter.getFilterItems().add( new FilterItemInfo("customer.id",customer.getId().toString()));
//		
//	    CustomerEntryCollection coll = CustomerEntryFactory.getLocalInstance(ctx).getCustomerEntryCollection(view);
//	    
//	    for(int i = 0; i < coll.size(); i ++)
//	    {
//	    	set.add(coll.get(i).getHead().getId().toString());
//	    }
//		return set;
//	}
//	
//	private boolean isInclude(Context ctx, FDCReceiveBillEntryInfo entryInfo, PPMARBaseInfo baseInfo) throws BOSException
//	{
//		boolean debug = false;
//		RoomInfo room = baseInfo.getRoom();
//		CustomerInfo customer = baseInfo.getCustomer().getSysCustomer();
//		Set fdcRecIdSet = this.getFdcRecBillId(ctx, customer);
//		
//		if(fdcRecIdSet == null || fdcRecIdSet.size() < 1)
//			return debug;
//		
//		
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		view.setFilter(filter);
//		
//		view.getSelector().add("id");
//		
//		filter.getFilterItems().add(new FilterItemInfo("room",room.getId().toString()));
//		filter.getFilterItems().add(new FilterItemInfo("id",fdcRecIdSet,CompareType.INCLUDE));
//		
//		FDCReceiveBillCollection coll = FDCReceiveBillFactory.getLocalInstance(ctx).getFDCReceiveBillCollection(view);
//		//借用下同一个变量
//		fdcRecIdSet.clear();
//		
//		for(int i = 0; i < coll.size(); i ++)
//		{
//			fdcRecIdSet.add(coll.get(i).getId().toString());
//		}
//		String id = entryInfo.getReceivingBill().getFdcReceiveBill().getId().toString();
//		
//		debug = fdcRecIdSet.contains(id);
//		
//		return debug;
//	}
//	
//	
//	protected void _generateRecBil(Context ctx, IObjectCollection recBillList,IObjectCollection payList,Date recDate) throws BOSException, EASBizException
//	{
//		if(recBillList == null || payList == null)
//		{
//			logger.error("批量结转传来的参数为NULL");
//			return;
//		}
//		//预收款类型的收款分录
//		FDCReceiveBillEntryCollection fdcEntryColl = null;
//		
//		//有用到红冲的预收款收款分录
//		FDCReceiveBillEntryCollection usedFdcEntryColl = new FDCReceiveBillEntryCollection();
//		
//		//收款了的付款明细
//		PPMARBaseCollection marBaseColl = new PPMARBaseCollection();
//		
//		//组装一个收款的收款分录
//		FDCReceiveBillEntryCollection fdcRecEntryColl = new FDCReceiveBillEntryCollection();
//		
//		//所有预收款分录的可红冲金额
//	//	BigDecimal canCounteractAmount = FDCHelper.ZERO;
//		
//		
//		this.initMoneyAccountMapping(ctx);
//	
//		//传过来的  预收款 分录的 集合
//		if(recBillList instanceof CoreBaseCollection)
//		{
//			Set recIdSet = new HashSet();
//			for(int i = 0; i < recBillList.size(); i ++)
//			{
//				recIdSet.add(((FDCReceiveBillEntryInfo)recBillList.getObject(i)).getId().toString());
//			}
//			if(recIdSet.size() < 1)
//			{
//				logger.error("收款单ID集合为空！");
//				return;
//			}
//			EntityViewInfo view = new EntityViewInfo();
//			FilterInfo filter = new FilterInfo();
//			view.setFilter(filter);
//			
//			view.getSelector().add("*");
//			
//			view.getSelector().add("settlementType.id");
//			view.getSelector().add("settlementType.name");
//			view.getSelector().add("settlementType.number");
//			
//			
//			view.getSelector().add("account.id");
//			view.getSelector().add("account.name");
//			view.getSelector().add("account.number");
//			
//			view.getSelector().add("receivingBill.id");
//			view.getSelector().add("receivingBill.name");
//			view.getSelector().add("receivingBill.number");
//			view.getSelector().add("receivingBill.amount");
//			
//			
//			
//			view.getSelector().add("revAccountBank.id");
//			view.getSelector().add("revAccountBank.name");
//			view.getSelector().add("revAccountBank.number");
//			
//			
//			view.getSelector().add("moneyDefine.*");
//			view.getSelector().add("oppSubject.id");
//			view.getSelector().add("oppSubject.name");
//			view.getSelector().add("oppSubject.number");
//			view.getSelector().add("receivingBill.fdcReceiveBill.*");
//			view.getSelector().add("receivingBill.fdcReceiveBill.customerEntrys.*");
//			view.getSelector().add("receivingBill.fdcReceiveBill.moneyDefine.*");
//			view.getSelector().add("receivingBill.fdcReceiveBill.customerEntrys.customer.id");
//			view.getSelector().add("receivingBill.fdcReceiveBill.customerEntrys.customer.name");
//			view.getSelector().add("receivingBill.fdcReceiveBill.customerEntrys.customer.number");
//			view.getSelector().add("receivingBill.fdcReceiveBill.sellProject.id");
//			view.getSelector().add("receivingBill.fdcReceiveBill.sellProject.number");
//			view.getSelector().add("receivingBill.fdcReceiveBill.sellProject.name");
//			
//			SorterItemInfo sortItemInfo = new SorterItemInfo("receivingBill.bizDate");
//			SorterItemCollection sorColl = new SorterItemCollection();
//			
//			sorColl.add(sortItemInfo);
//			
//			view.setSorter(sorColl);
//			
//			
//			filter.getFilterItems().add(new FilterItemInfo("id",recIdSet,CompareType.INCLUDE));
//			
//			fdcEntryColl = FDCReceiveBillEntryFactory.getLocalInstance(ctx).getFDCReceiveBillEntryCollection(view);
//			
//			/*for(int i = 0; i < fdcEntryColl.size(); i ++)
//			{
//				BigDecimal amount = fdcEntryColl.get(i).getCanCounteractAmount();
//				if(amount == null)
//					amount = FDCHelper.ZERO;
//				
//				canCounteractAmount = canCounteractAmount.add(amount);
//			}*/
//		}
//		
//		if(payList instanceof CoreBaseCollection)
//		{
//			for(int i = 0; i < payList.size(); i ++)
//			{
//				PPMARBaseInfo info = ((PPMARBaseInfo)payList.getObject(i));
//				info = this.getPPMARBaseInfo(ctx, info);
//				
//				
//				BigDecimal arAmount = info.getArAmout();
//				if(arAmount == null)
//					arAmount = FDCHelper.ZERO;
//				
//				BigDecimal derateAmount = info.getDerateAmount();
//				if(derateAmount == null)
//					derateAmount = FDCHelper.ZERO;
//				
//				BigDecimal payedAmount = info.getPayedAmount();
//				if(payedAmount == null)
//					payedAmount = FDCHelper.ZERO;
//				
//				BigDecimal amount = arAmount.subtract(derateAmount).subtract(payedAmount);
//				if(amount == null)
//					amount = FDCHelper.ZERO;
//				
//				if(amount.compareTo(FDCHelper.ZERO) <= 0)
//					continue;
//				
//				//每一个应付明细 去被 预收款 分录 循环红冲，冲完为止; 只有同房间，同客户的预收款才可以去红冲 相同的 应收
//				for(int j = 0; j < fdcEntryColl.size(); j ++)
//				{
//					if(!this.isInclude(ctx,fdcEntryColl.get(j), info))
//						continue;
//					
//					
//					if(amount.compareTo(FDCHelper.ZERO) <= 0)
//						break;
//					
//					BigDecimal canCounteractAmount = fdcEntryColl.get(j).getCanCounteractAmount();
//					
//					if(canCounteractAmount.compareTo(FDCHelper.ZERO) <= 0)
//						continue;
//					
//				
//					if(canCounteractAmount.compareTo(amount) >= 0)
//					{
//						//获取自动生成红冲单的ID
//						IObjectPK settlementID = this.minusCanCounteractAmount(ctx, fdcEntryColl.get(j), amount,recDate);
//						
//						this.writePPMarBaseInfo(ctx, info, amount,fdcRecEntryColl,fdcEntryColl.get(j),recDate, settlementID);
//						amount = FDCHelper.ZERO;
//					}
//					else
//					{
//						//获取自动生成红冲单的ID
//						IObjectPK settlementID = this.minusCanCounteractAmount(ctx, fdcEntryColl.get(j), canCounteractAmount,recDate);
//						
//						//fdcRecEntryColl 是留作以后用，现在还没用到
//						this.writePPMarBaseInfo(ctx, info, canCounteractAmount,fdcRecEntryColl,fdcEntryColl.get(j),recDate, settlementID);
//						
//						amount = amount.subtract(canCounteractAmount);
//					}
//				}
//			}
//		}
//	}
//	
//	//取得详细的 PPMARBaseInfo 对象
//	private PPMARBaseInfo getPPMARBaseInfo(Context ctx, PPMARBaseInfo info) throws EASBizException, BOSException
//	{
//		PPMARBaseInfo pp = null;
//		String id = info.getId().toString();
//		
//		SelectorItemCollection selColl = new SelectorItemCollection();
//		selColl.add("room.id");
//		selColl.add("room.name");
//		selColl.add("customer.*");
//		
//		selColl.add("customer.sysCustomer.*");
//		selColl.add("chargeItem.*");
//		selColl.add("chargeItem.moneyType.*");
//		selColl.add("*");
//		
//		if(info instanceof PPMGeneralARInfo)
//		{
//			pp = PPMGeneralARFactory.getLocalInstance(ctx).getPPMGeneralARInfo(new ObjectUuidPK(BOSUuid.read(id)), selColl);
//		}
//		else if(info instanceof PPMMeasureARInfo)
//		{
//			pp = PPMMeasureARFactory.getLocalInstance(ctx).getPPMMeasureARInfo(new ObjectUuidPK(BOSUuid.read(id)),selColl);
//		}
//		else if(info instanceof PPMTemporaryInfo)
//		{
//			pp = PPMTemporaryFactory.getLocalInstance(ctx).getPPMTemporaryInfo(new ObjectUuidPK(BOSUuid.read(id)), selColl);
//		}
//		
//		return pp;
//	}
//	
//	
//	//反写付款明细的分录
//	private void writePPMarBaseInfo(Context ctx, PPMARBaseInfo info,BigDecimal amount,FDCReceiveBillEntryCollection entryColl,FDCReceiveBillEntryInfo entryInfo,Date recDate ,
//			IObjectPK settlementID) throws EASBizException, BOSException
//	{
//		FDCReceiveBillEntryInfo entry = new FDCReceiveBillEntryInfo();
//
//		BigDecimal payedAmount = info.getPayedAmount();
//		if (payedAmount == null)
//			payedAmount = FDCHelper.ZERO;
//
//		payedAmount = payedAmount.add(amount);
//
//		info.setPayedAmount(payedAmount);
//		
//		SelectorItemCollection selColl = new SelectorItemCollection();
//		selColl.add("payedAmount");
//
//		if (info instanceof PPMGeneralARInfo)
//		{
//			PPMGeneralARFactory.getLocalInstance(ctx).updatePartial(info,	selColl);
//		}
//		else if(info instanceof PPMMeasureARInfo)
//		{
//			PPMMeasureARFactory.getLocalInstance(ctx).updatePartial(info, selColl);
//		}
//		else if(info instanceof PPMTemporaryInfo)
//		{
//			PPMTemporaryFactory.getLocalInstance(ctx).updatePartial(info, selColl);
//		}
//		
//		//每一个反写 物业 应收的时候，都去生成一张  收款单
//		RoomInfo room = info.getRoom();
//		FDCCustomerInfo fdcCustomer = info.getCustomer();
//		SellProjectInfo sellProject = info.getSellproject();
//		
//		entry.setPayListId(info.getId().toString());
//		MoneyDefineInfo chargeItem = info.getChargeItem();
//		entry.setMoneyDefine(chargeItem);
//		entry.setSettlementType(entryInfo.getSettlementType());
//		entry.setAccount(entryInfo.getOppSubject());
//		entry.setFCounteractId(entryInfo.getId() == null ? "":entryInfo.getId().toString());
//		entry.setAmount(amount);
//		MoneySubjectContrastInfo subject = (MoneySubjectContrastInfo)this.moneyAccountMapping.get(chargeItem.getId().toString());
//		if(subject!=null)
//			entry.setOppSubject(subject.getAccountView());
//		
//		FDCReceiveBillEntryCollection fdcEntryColl = new FDCReceiveBillEntryCollection();
//		fdcEntryColl.add(entry);
//		
//		
//		CustomerEntryInfo customerEntryInfo = new CustomerEntryInfo();
//		customerEntryInfo.setCustomer(fdcCustomer.getSysCustomer());
//		
//		CustomerEntryCollection customerColl = new CustomerEntryCollection();
//		customerColl.add(customerEntryInfo);
//		
//		/**
//		 * 生成一张结转单
//		 */
//		ReceivingBillInfo receivingBillInfo = this.generateRecBill(ctx, GatheringEnum.SaleRoom, 
//				MoneySysTypeEnum.ManageSys, ReceiveBillTypeEnum.gathering, fdcEntryColl, room, customerColl,sellProject,recDate);
//		
//		FDCReceiveBillInfo fdcRecBillInfo = receivingBillInfo.getFdcReceiveBill();
//		
//		//生成的结转单中的settlementBill记录对应生成红冲单
//		ReceivingBillInfo  tmpSetmentBill = new ReceivingBillInfo();
//		tmpSetmentBill.setId(BOSUuid.read(settlementID.toString()));
//		
//		fdcRecBillInfo.setSettlementBill(tmpSetmentBill);
//		
//		FDCReceiveBillFactory.getLocalInstance(ctx).submit(fdcRecBillInfo);
//		ReceivingBillFactory.getLocalInstance(ctx).submit(receivingBillInfo);
//	}
//	
//	
//	/**
//	 * 反写收款分录的可红冲金额
//	 * 返回红冲单的ID
//	 * @param entryInfo
//	 * @throws BOSException 
//	 * @throws EASBizException 
//	 */
//	private IObjectPK minusCanCounteractAmount(Context ctx, FDCReceiveBillEntryInfo entryInfo,BigDecimal amount,Date recDate) throws EASBizException, BOSException
//	{
//		FDCReceiveBillEntryInfo info = entryInfo;
//		BigDecimal canCounteractAmount = info.getCanCounteractAmount();
//		if(canCounteractAmount == null)
//			canCounteractAmount = FDCHelper.ZERO;
//		canCounteractAmount = canCounteractAmount.subtract(amount);
//		
//		info.setCanCounteractAmount(canCounteractAmount);
//		
//		BigDecimal counteractAmount = info.getCounteractAmount();
//		if(counteractAmount == null)
//			counteractAmount = FDCHelper.ZERO;
//		counteractAmount = counteractAmount.add(amount);
//		
//		info.setCounteractAmount(counteractAmount);
//		
//		SelectorItemCollection selColl = new SelectorItemCollection();
//		selColl.add("counteractAmount");
//		selColl.add("canCounteractAmount");
//		
//		FDCReceiveBillEntryFactory.getLocalInstance(ctx).updatePartial(info, selColl);
//		
//		//每一次反写 预收款 收款 分录的时候，都去 生成一张结转单 
//		
//		FDCReceiveBillEntryInfo billEntry = (FDCReceiveBillEntryInfo) info.clone();
//		billEntry.setId(null);
//		billEntry.setAmount(FDCHelper.ZERO.subtract(amount));
//		
//		FDCReceiveBillEntryCollection fdcEntryColl = new FDCReceiveBillEntryCollection();
//		fdcEntryColl.add(billEntry);
//		
//		RoomInfo room = info.getReceivingBill().getFdcReceiveBill().getRoom();
//		CustomerEntryCollection customerColl = info.getReceivingBill().getFdcReceiveBill().getCustomerEntrys();
//		if(customerColl == null || customerColl.size() < 1)
//		{
//			String id = info.getReceivingBill().getFdcReceiveBill().getId().toString();
//			
//			EntityViewInfo view = new EntityViewInfo();
//			FilterInfo filter = new FilterInfo();
//			filter.getFilterItems().add(new FilterItemInfo("head",id));
//			view.setFilter(filter);
//			
//			view.getSelector().add("*");
//			view.getSelector().add("customer.*");
//			
//			customerColl = CustomerEntryFactory.getLocalInstance(ctx).getCustomerEntryCollection(view);
//		}
//		
//		
//		CustomerEntryCollection customer = new CustomerEntryCollection();
//		
//		for(int i = 0; i < customerColl.size(); i ++)
//		{
//			CustomerEntryInfo customerEntryInfo = new CustomerEntryInfo();
//			customerEntryInfo.setCustomer(customerColl.get(i).getCustomer());
//			customer.add(customerEntryInfo);
//		}
//		
//		/**
//		 * 生成一张红冲单
//		 */
//		
//		
//		SellProjectInfo sellProject = info.getReceivingBill().getFdcReceiveBill().getSellProject();
//		
//		ReceivingBillInfo receivingBillInfo = this.generateRecBill(ctx, GatheringEnum.SaleRoom, 
//				MoneySysTypeEnum.ManageSys, ReceiveBillTypeEnum.settlement, fdcEntryColl, room, customer,sellProject,recDate);
//		
//		FDCReceiveBillInfo fdcRecBillInfo = receivingBillInfo.getFdcReceiveBill();
//		
//		
//		FDCReceiveBillFactory.getLocalInstance(ctx).submit(fdcRecBillInfo);
//		IObjectPK pk = ReceivingBillFactory.getLocalInstance(ctx).submit(receivingBillInfo);
//		
//		return pk ;
//		
//	}
//	
//
//	
//	
//	
//	private ReceivingBillInfo generateRecBill(Context ctx,	GatheringEnum gatherObj, MoneySysTypeEnum sysType,
//			ReceiveBillTypeEnum revBillType,FDCReceiveBillEntryCollection fdcEntryColl, RoomInfo roomInfo,CustomerEntryCollection customerColl,SellProjectInfo sellProject,Date recDate) throws BOSException, EASBizException
//	{
//		ReceivingBillInfo receivingBillInfo = null;
//		try
//		{
//			receivingBillInfo = this.createBaseRecBillInfo(ctx, gatherObj,recDate);
//
//			FDCReceiveBillInfo fdcRevBillInfo = receivingBillInfo.getFdcReceiveBill();
//			fdcRevBillInfo.setRoom(roomInfo);
//			if(customerColl.size()>0) {
//				CustomerInfo custInfo = customerColl.get(0).getCustomer();
//				receivingBillInfo.setPayerID(custInfo.getId().toString());
//				receivingBillInfo.setPayerName(custInfo.getName());
//				receivingBillInfo.setPayerNumber(custInfo.getNumber());
//			}
//			
//			fdcRevBillInfo.getCustomerEntrys().addCollection(customerColl);			
//			
//			fdcRevBillInfo.setMoneySysType(sysType);
//			fdcRevBillInfo.setBillTypeEnum(revBillType); // 收款单类型 :收款 退款 结转,转款
//			receivingBillInfo.getFdcReceiveBillEntry().addCollection(fdcEntryColl);
//
//			BigDecimal totalAmount = new BigDecimal(0);
//			if (fdcEntryColl.size() > 0)
//			{ // 如果不设置receivingBillInfo.setRecBillType的属性，则该收款单生成凭证时会报无法反写的错
//				MoneyDefineInfo monDeInfo = fdcEntryColl.get(0).getMoneyDefine();
//				if (monDeInfo.getRevBillType() != null)
//					receivingBillInfo.setRecBillType(monDeInfo.getRevBillType());
//				for (int i = 0; i < fdcEntryColl.size(); i++)
//				{
//					FDCReceiveBillEntryInfo fdcBillEntryInfo = fdcEntryColl.get(i);
//					totalAmount = totalAmount.add(fdcBillEntryInfo.getAmount());
//				}
//			}
//			receivingBillInfo.setAmount(totalAmount);
//			receivingBillInfo.setLocalAmt(totalAmount);
//
//			if (receivingBillInfo.getEntries().isEmpty())
//			{ // 收款单财务分录
//				ReceivingBillEntryInfo entry = new ReceivingBillEntryInfo();
//				entry.setAmount(totalAmount);
//				entry.setActualAmt(totalAmount);
//				receivingBillInfo.getEntries().add(entry);
//			} else
//			{
//				ReceivingBillEntryInfo entry = receivingBillInfo.getEntries().get(0);
//				entry.setAmount(totalAmount);
//				entry.setActualAmt(totalAmount);
//			}
//
//			// 房地产收款单中的系统客户分录
//			fdcRevBillInfo.setSellProject(sellProject);
//			
//		} catch (BOSException ex)
//		{
//			throw ex;
//		} catch (EASBizException ex)
//		{
//			throw ex;
//		}
//
//		return receivingBillInfo;
//
//	}
//	
//	private AccountViewInfo getAccountView(Context ctx,MoneyDefineInfo money)
//	{
//		AccountViewInfo account = null;
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		
//		CompanyOrgUnitInfo currentCompany = ContextUtil.getCurrentFIUnit(ctx);
//		
//		view.getSelector().add("accountView.*");
//		view.getSelector().add("*");
//		
//		filter.getFilterItems().add(new FilterItemInfo("moneyDefine",money.getId().toString()));
//		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",currentCompany));
//		
//		return account;
//	}
//
//	// 创建最基础的财务收款对象
//	private ReceivingBillInfo createBaseRecBillInfo(Context ctx,GatheringEnum gatherObj,Date recDate) throws EASBizException, BOSException,	UuidException
//	{
//		ReceivingBillInfo receivingBillInfo = new ReceivingBillInfo();
//
//		CompanyOrgUnitInfo currentCompany = ContextUtil.getCurrentFIUnit(ctx);
//		CurrencyInfo baseCurrency = CurrencyFactory.getLocalInstance(ctx)
//		.getCurrencyInfo(new ObjectUuidPK(BOSUuid.read(currentCompany.getBaseCurrency().getId().toString())));
//
//		receivingBillInfo.setCurrency(baseCurrency);
//		receivingBillInfo.setExchangeRate(FDCHelper.ONE);
//
//		receivingBillInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
//		receivingBillInfo.setCreateTime(new Timestamp(new Date().getTime()));
//
//		receivingBillInfo.setIsInitializeBill(false);
//		receivingBillInfo.setSettlementStatus(SettlementStatusEnum.UNSUBMIT);
//		if(recDate != null)
//		{
//			receivingBillInfo.setBizDate(recDate);
//		}
//		else
//		{
//			receivingBillInfo.setBizDate(new Date());
//		}
//
//		receivingBillInfo.setPayerType(getCustomerType(ctx)); // 往来户类型
//		receivingBillInfo.setSourceType(SourceTypeEnum.FDC); // 来源类型（房地产成本管理）
//		receivingBillInfo.setSourceSysType(SourceTypeEnum.FDC);
//
//		receivingBillInfo.setBillStatus(BillStatusEnum.RECED); // 已收款状态 --
//																// 后台处理的，
//																// 因而直接认为已收款
//
//		FDCReceiveBillInfo fdcReceiveBillInfo = new FDCReceiveBillInfo(); // 关联的房地产收款单
//		fdcReceiveBillInfo.setId(BOSUuid.create(fdcReceiveBillInfo.getBOSType()));
//
//		fdcReceiveBillInfo.setPayQuomodo(PayQuomodoEnum.CASH); // 付款方式
//
//		fdcReceiveBillInfo.setGathering(gatherObj); // 暂定只是针对房间收款 -- 待改
//		receivingBillInfo.setFdcReceiveBill(fdcReceiveBillInfo);
//
//		return receivingBillInfo;
//	}
//
//	private AsstActTypeInfo getCustomerType(Context ctx) throws BOSException
//	{
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(
//				new FilterItemInfo("realtionDataObject", "T_BD_Customer",CompareType.EQUALS));
//		EntityViewInfo view = new EntityViewInfo();
//		view.setFilter(filter);
//		AsstActTypeCollection asstActTypeCollection = AsstActTypeFactory.getLocalInstance(ctx).getAsstActTypeCollection(view);
//		if (asstActTypeCollection != null && asstActTypeCollection.size() == 1)
//		{
//			return asstActTypeCollection.get(0);
//		}
//		return null;
//	}
//}