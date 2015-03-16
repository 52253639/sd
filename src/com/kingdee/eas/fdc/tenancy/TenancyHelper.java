package com.kingdee.eas.fdc.tenancy;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.DigitEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SHEComHelper;
import com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum;
import com.kingdee.eas.fdc.tenancy.client.TENReceivingBillEditUI;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.jdbc.rowset.IRowSet;


/**
 * 用来提供租赁客户端和服务器端公用的函数
 * */
public class TenancyHelper {
	
	private static final Logger logger = CoreUIObject.getLogger(TenancyHelper.class);
	
	/**
	 * 根据租赁合同判断是否存在非保存状态的退租单
	 * */
	public static boolean existQuitTenBillByTenBill(IQuitTenancy iface, String tenId, String exceptQuitBillId) throws EASBizException, BOSException{
		if(tenId == null){
			return false;
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancyBill.id", tenId));
		//TODO 如果退租单增加了作废等操作,这里的查询也要加上 !=已作废  并且还要判断是否审批,注意退租单审批操作对原合同的影响
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED, CompareType.NOTEQUALS));
		if(exceptQuitBillId != null){
			filter.getFilterItems().add(new FilterItemInfo("id", exceptQuitBillId, CompareType.NOTEQUALS));
		}
		return iface.exists(filter);
	}
	
	/**
	 * 根据合同ID，查找是否存在提交或审批中的减免单
	 * @throws BOSException 
	 * @throws EASBizException 
	 * */
	public static boolean existRentRemissionByTenBill(IRentRemission iface, String tenId) throws EASBizException, BOSException {
		if(tenId == null) return false;
		FilterInfo rentRemissionFilter = new FilterInfo();
		rentRemissionFilter.getFilterItems().add(new FilterItemInfo("tenancy.id", tenId));
		rentRemissionFilter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED));
		rentRemissionFilter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTING));
		rentRemissionFilter.setMaskString("#0 and (#1 or #2)");
		return iface.exists(rentRemissionFilter);
	}  
	
	/**
	 * 根据合同ID，查找是否存在提交或审批中的变更单
	 * @throws BOSException 
	 * @throws EASBizException 
	 * */
	public static boolean existTenancyModificationByTenBill(ITenancyModification iface, String tenId) throws EASBizException, BOSException {
		FilterInfo tenChangeFilter = new FilterInfo();
		tenChangeFilter.getFilterItems().add(new FilterItemInfo("tenancy.id", tenId));
		tenChangeFilter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED));
		tenChangeFilter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTING));
		tenChangeFilter.setMaskString("#0 and (#1 or #2)");
		return iface.exists(tenChangeFilter);
	}
	
	/**
	 * 根据原合同ID,找到转,续,更名的目标合同ID
	 * @throws BOSException 
	 * */
	public static String getTargetTenIdBySrcTenancyId(String srcTenId) throws BOSException{
		return getTargetTenIdBySrcTenancyId(TenancyBillFactory.getRemoteInstance(), srcTenId);
	}
	
	/**
	 * 根据原合同ID,找到转,续,更名的目标合同ID
	 * @throws BOSException 
	 * */
	public static String getTargetTenIdBySrcTenancyId(Context ctx, String srcTenId) throws BOSException{
		return getTargetTenIdBySrcTenancyId(TenancyBillFactory.getLocalInstance(ctx), srcTenId);
	}
	
	/**
	 * 根据原合同ID,找到转,续,更名的目标合同ID
	 * @throws BOSException 
	 * */
	public static String getTargetTenIdBySrcTenancyId(ITenancyBill iface, String srcTenId) throws BOSException{
		if(srcTenId == null){
			return null;
		}
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("oldTenancyBill.id", srcTenId));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState", TenancyBillStateEnum.Saved, CompareType.NOTEQUALS));
		//如果目标合同曾经审批,则不用再加条件;否则目标合同不能为作废状态
		filter.getFilterItems().add(new FilterItemInfo("auditor", null, CompareType.NOTEQUALS));
		
		filter.getFilterItems().add(new FilterItemInfo("auditor", null, CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("tenancyState", TenancyBillStateEnum.BlankOut, CompareType.NOTEQUALS));
		
		filter.setMaskString("#0 and #1 and (#2 or (#3 and #4))");
		
		view.setFilter(filter);
		TenancyBillCollection tens = iface.getTenancyBillCollection(view);
		
		if(tens.isEmpty()){
			return null;
		}
		
		if(tens.size() > 1){
			logger.error("逻辑错误,应该最多只会有1条的呀..");
		}
		
		return tens.get(0).getId().toString();
	}
	
	/**
	 * 客户端获得完整的租赁合同,包含房间分录及付款明细
	 * */
	public static TenancyBillInfo getTenancyBillInfo(String id) throws EASBizException, BOSException{
		return getTenancyBillInfo(TenancyBillFactory.getRemoteInstance(), id);
	}
	
	/**
	 * 服务器端获得完整的租赁合同,包含房间分录及付款明细
	 * */
	public static TenancyBillInfo getTenancyBillInfo(Context ctx, String id) throws EASBizException, BOSException{
		return getTenancyBillInfo(TenancyBillFactory.getLocalInstance(ctx), id);
	}
	
	/**
	 * 根据seq对租赁房间的收款明细排序
	 * @param ten
	 * */
	public static void sortPayListOfTenancy(TenancyBillInfo ten){
		if(ten == null){
			return;
		}
		sortPayList(ten.getTenancyRoomList());
		sortPayList(ten.getTenAttachResourceList());
	}

	private static void sortPayList(IObjectCollection tenRooms) {
		for(int i=0; i<tenRooms.size(); i++){
			ITenancyEntryInfo tenRoom = (ITenancyEntryInfo) tenRooms.getObject(i);
			
			IObjectCollection payList = tenRoom.getPayList();
			
			Object[] toSortData = payList.toArray();
			Arrays.sort(toSortData, new Comparator(){
				public int compare(Object arg0, Object arg1) {
					ITenancyPayListInfo pay0 = (ITenancyPayListInfo)arg0;
					ITenancyPayListInfo pay1 = (ITenancyPayListInfo)arg1;
					if(pay0 == null  ||  pay1 == null){
						return 0;
					}
					return pay0.getSeq() - pay1.getSeq();
				}
			});
			payList.clear();
			for(int j=0; j<toSortData.length; j++){
				payList.addObject((IObjectValue) toSortData[j]);
			}
		}
	}
	
	/**
	 * 根据递增日期对新递增明细排序
	 * @param ten
	 * */
	public static void sortNewIncRentEntryCollectionByIncDate(NewIncRentEntryCollection incNewCol){
		if(incNewCol == null){
			return;
		}		
		Object[] toSortData = incNewCol.toArray();
		Arrays.sort(toSortData, new Comparator(){
			public int compare(Object arg0, Object arg1) {
				NewIncRentEntryInfo pay0 = (NewIncRentEntryInfo)arg0;
				NewIncRentEntryInfo pay1 = (NewIncRentEntryInfo)arg1;
				int result = 0;
				if(pay0 == null  ||  pay1 == null){
					return 0;
				}
				if(pay0.getIncreaseDate().before(pay1.getIncreaseDate())){
					result=1;
				}
				return result;
			}				
		});
		incNewCol.clear();
		for(int j=0; j<toSortData.length; j++){
			incNewCol.add((NewIncRentEntryInfo) toSortData[j]);
		}
	}
	
	public static SincerObligateInfo getSincerObligateInfo(String sincerID) throws EASBizException, BOSException
	{
		SelectorItemCollection sels = new SelectorItemCollection();
		
		sels.add("*");
		sels.add("sinRoomList.*");
		sels.add("sinRoomList.room.name");
		sels.add("sinRoomList.room.floor");
		sels.add("sinRoomList.room.isForPPM");
		sels.add("sinRoomList.room.number");
		
		sels.add("sinRoomList.room.*");
		sels.add("sinRoomList.room.building.name");
		sels.add("sinRoomList.room.building.number");
		sels.add("sinRoomList.room.building.sellProject.name");
		sels.add("sinRoomList.room.building.sellProject.number");
		
		sels.add("sinCustomerList.*");
		sels.add("sinCustomerList.fdcCustomer.*");
		sels.add("payListEntrys.*");
		sels.add("payListEntrys.moneyDefine.*");
		return SincerObligateFactory.getRemoteInstance().getSincerObligateInfo(new ObjectUuidPK(sincerID), sels);
	}
	
	/**
	 * 获得完整的租赁合同,包含房间分录及付款明细
	 * 参考TenancyBillEditUI的getSelectors实现
	 * @throws BOSException 
	 * @throws EASBizException 
	 * */
	public static TenancyBillInfo getTenancyBillInfo(ITenancyBill iface, String id) throws EASBizException, BOSException{
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add(new SelectorItemInfo("fixedDateFromMonth"));
		sels.add(new SelectorItemInfo("firstLeaseType"));
		sels.add(new SelectorItemInfo("leaseTime"));
		sels.add(new SelectorItemInfo("firstLeaseEndDate"));
		sels.add(new SelectorItemInfo("isFreeContract"));
		sels.add(new SelectorItemInfo("number"));
		sels.add(new SelectorItemInfo("createTime"));
		sels.add(new SelectorItemInfo("description"));
		sels.add(new SelectorItemInfo("startDate"));
		sels.add(new SelectorItemInfo("endDate"));
		sels.add(new SelectorItemInfo("tenancyName"));
		sels.add(new SelectorItemInfo("deliveryRoomDate"));
		sels.add(new SelectorItemInfo("standardTotalRent"));
		sels.add(new SelectorItemInfo("depositAmount"));
		sels.add(new SelectorItemInfo("payeeBank.*")); 
		sels.add(new SelectorItemInfo("specialClause"));
		sels.add(new SelectorItemInfo("firstPayRent"));
        sels.add(new SelectorItemInfo("tenancyType"));
        sels.add(new SelectorItemInfo("changeDes"));
        sels.add(new SelectorItemInfo("tenancyAdviser.*"));
        sels.add(new SelectorItemInfo("leaseTime"));
        sels.add(new SelectorItemInfo("freeDays"));
        sels.add(new SelectorItemInfo("remainDepositAmount"));
        sels.add(new SelectorItemInfo("leaseCount"));
        sels.add(new SelectorItemInfo("creator.*"));
        sels.add(new SelectorItemInfo("oldTenancyBill.*"));
        sels.add(new SelectorItemInfo("agency.*"));
        sels.add(new SelectorItemInfo("dealTotalRent"));
        sels.add(new SelectorItemInfo("chargeDateType"));
        sels.add(new SelectorItemInfo("chargeOffsetDays"));
        sels.add(new SelectorItemInfo("lateFeeAmount"));
        sels.add(new SelectorItemInfo("isFreeDaysInLease"));
        sels.add(new SelectorItemInfo("flagAtTerm"));
        sels.add(new SelectorItemInfo("tenancyDate"));
        sels.add(new SelectorItemInfo("deductAmount"));
        sels.add(new SelectorItemInfo("tenRoomsDes"));
        sels.add(new SelectorItemInfo("tenAttachesDes"));
        sels.add(new SelectorItemInfo("tenCustomerDes"));        
        sels.add(new SelectorItemInfo("name"));
        sels.add(new SelectorItemInfo("rentCountType"));
        sels.add(new SelectorItemInfo("isAutoToInteger"));
        sels.add(new SelectorItemInfo("toIntegerType"));
        sels.add(new SelectorItemInfo("digit"));
        sels.add(new SelectorItemInfo("rentStartType"));
        sels.add(new SelectorItemInfo("startDateLimit"));
        sels.add(new SelectorItemInfo("daysPerYear"));
        sels.add(new SelectorItemInfo("sellProject.*"));
        sels.add(new SelectorItemInfo("secondRevDate"));
        sels.add(new SelectorItemInfo("fristRevDate"));
        sels.add("orgUnit");
		sels.add("orgUnit.*");
		sels.add("firstLeaseEndDate");
        
        sels.add("tenancyState");

		sels.add("increasedRents.*");
		sels.add("rentFrees.*");
		
		sels.add("tenancyRoomList.*");
		sels.add("tenancyRoomList.room.floor");
		sels.add("tenancyRoomList.room.isForPPM");
		sels.add("tenancyRoomList.room.name");
		sels.add("tenancyRoomList.room.number");
		
		sels.add("tenancyRoomList.room.building.name");
		sels.add("tenancyRoomList.room.building.number");
		sels.add("tenancyRoomList.room.building.sellProject.name");
		sels.add("tenancyRoomList.room.building.sellProject.number");
		sels.add("tenancyRoomList.roomPayList.*");
		sels.add("tenancyRoomList.roomPayList.currency.name");
		sels.add("tenancyRoomList.roomPayList.currency.number");
		
		sels.add("tenancyRoomList.roomPayList.moneyDefine.name");
		sels.add("tenancyRoomList.roomPayList.moneyDefine.number");
		sels.add("tenancyRoomList.roomPayList.moneyDefine.moneyType");
		sels.add("tenancyRoomList.roomPayList.moneyDefine.sysType");
		sels.add("tenancyRoomList.roomPayList.moneyDefine.isEnabled");
		
		sels.add("tenancyRoomList.dealAmounts.*");
		sels.add("tenancyRoomList.dealAmounts.moneyDefine.name");
		sels.add("tenancyRoomList.dealAmounts.moneyDefine.number");
		sels.add("tenancyRoomList.dealAmounts.moneyDefine.moneyType");
		sels.add("tenancyRoomList.dealAmounts.moneyDefine.sysType");
		sels.add("tenancyRoomList.dealAmounts.moneyDefine.isEnabled");
		
		sels.add("tenAttachResourceList.*");
		sels.add("tenAttachResourceList.attachResource.*");
		sels.add("tenAttachResourceList.attachResource.attachType");
		sels.add("tenAttachResourceList.attachResource.name");
		sels.add("tenAttachResourceList.attachResource.number");
		sels.add("tenAttachResourceList.attachResource.description");
		
		sels.add("tenAttachResourceList.attachResource.sellProject.name");
		sels.add("tenAttachResourceList.attachResource.subarea.name");
		sels.add("tenAttachResourceList.attachResource.building.name");
		sels.add("tenAttachResourceList.attachResPayList.*");
		sels.add("tenAttachResourceList.attachResPayList.currency.number");
		sels.add("tenAttachResourceList.attachResPayList.currency.name");
		
		sels.add("tenAttachResourceList.attachResPayList.moneyDefine.name");
		sels.add("tenAttachResourceList.attachResPayList.moneyDefine.number");
		sels.add("tenAttachResourceList.attachResPayList.moneyDefine.moneyType");
		sels.add("tenAttachResourceList.attachResPayList.moneyDefine.sysType");
		sels.add("tenAttachResourceList.attachResPayList.moneyDefine.isEnabled");
		
		sels.add("tenAttachResourceList.dealAmounts.*");
		sels.add("tenAttachResourceList.dealAmounts.moneyDefine.name");
		sels.add("tenAttachResourceList.dealAmounts.moneyDefine.number");
		sels.add("tenAttachResourceList.dealAmounts.moneyDefine.moneyType");
		sels.add("tenAttachResourceList.dealAmounts.moneyDefine.sysType");
		sels.add("tenAttachResourceList.dealAmounts.moneyDefine.isEnabled");
		
		sels.add("tenancyRoomList.equipments.*");
		sels.add("tenancyRoomList.equipments.dev.*");
		sels.add("tenancyRoomList.tenRoomCharges.*");
		sels.add("tenancyRoomList.tenRoomCharges.chargeStandard.*");
		sels.add("tenancyRoomList.tenRoomCharges.chargeItem.name");

		sels.add("tenCustomerList.*");
		sels.add("tenCustomerList.fdcCustomer.name");
		sels.add("tenCustomerList.fdcCustomer.number");
		sels.add("tenCustomerList.fdcCustomer.postalcode");
		sels.add("tenCustomerList.fdcCustomer.phone");
		sels.add("tenCustomerList.fdcCustomer.certificateName");
		sels.add("tenCustomerList.fdcCustomer.certificateNumber");
		sels.add("tenCustomerList.fdcCustomer.mailAddress");
		sels.add("tenCustomerList.fdcCustomer.description");
		sels.add("tenCustomerList.fdcCustomer.sysCustomer.*");
		
		sels.add("otherPayList.*");
		sels.add("otherPayList.currency.name");
		sels.add("otherPayList.currency.number");
		sels.add("otherPayList.moneyDefine.name");
		sels.add("otherPayList.moneyDefine.number");
		
		//add by yangfan
		sels.add("tenLiquidated.*");
		sels.add("tenLiquidated.moneyDefine.*");
		sels.add("tenLiquidated.liquidated.*");
		sels.add("tenLiquidated.liquidated.name");
		sels.add("tenLiquidated.liquidated.number");
		sels.add("rate");
		sels.add("isAccountLiquidated");
		sels.add("isMDLiquidated");
		sels.add("rateDate");
		sels.add("relief");
		sels.add("reliefDate");
		sels.add("standard");
		sels.add("standardDate");
		sels.add("bit");
		sels.add("occurred");
		
		TenancyBillInfo ten = iface.getTenancyBillInfo(new ObjectUuidPK(id), sels);
		sortPayListOfTenancy(ten);
		return ten;
	}
	
	//多态方法，暂保留
	public static BigDecimal getRentBetweenDate(MoneyDefineInfo moneyDefine, Date startDate, Date endDate, IObjectCollection dealAmounts, RentFreeEntryCollection rentFrees) {
		return TenancyHelper.getRentBetweenDate(moneyDefine, startDate, endDate, dealAmounts, rentFrees, null, 0);
	}
	
	/**
	 * 对于分段定价，获得2个日期之间的某种款项金额
	 * @param startDate
	 * @param endDate
	 * @param daysPerYear 
	 * @param rentCountType 
	 * @param rentType
	 * @param rent
	 * */
	public static BigDecimal getRentBetweenDate(MoneyDefineInfo moneyDefine, Date startDate, Date endDate, IObjectCollection dealAmounts, RentFreeEntryCollection rentFrees, RentCountTypeEnum rentCountType, int daysPerYear) {
		if (moneyDefine == null) {
			return null;
		}

		BigDecimal res = FDCHelper.ZERO;
		
		//TODO 要处理，只针对租金有这个区别
		if(rentCountType == null  ||  rentCountType.equals(RentCountTypeEnum.ActDateCount)){
			res = getRentByActDate(moneyDefine, startDate, endDate, dealAmounts, rentFrees);
		}else{//如果是月均算法.如下处理
			List monthes = getMonthList(startDate, endDate);
			//首先检查是否是完整月.
			Date[] lastMonth = (Date[]) monthes.get(monthes.size() - 1);
			//最后一个月的起始日期往后推1个月的时间和结束日期不相同,则表明lastMonth不是完整月
			if(!addCalendar(lastMonth[0], Calendar.MONTH, 1).equals(addCalendar(lastMonth[1], Calendar.DATE, 1))){
				res = getRentByActDate(moneyDefine, startDate, endDate, dealAmounts, rentFrees);
				/*
				for (int i = 0; i < dealAmounts.size(); i++) {
					IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmounts.getObject(i);
					if (dealAmount.getMoneyDefine() != null  &&  dealAmount.getMoneyDefine().getId().equals(moneyDefine.getId())) {
						RentTypeEnum rentType = dealAmount.getRentType();
						BigDecimal amount = dealAmount.getPriceAmount();
						
						if(!rentType.equals(RentTypeEnum.RentByDay)){
							Log.error("目前月均算法只支持按日定租的.");
							continue;
						}
						
						Date[] tmpDates = getWrapDates(new Date[]{startDate, endDate}, new Date[]{dealAmount.getStartDate(), dealAmount.getEndDate()});
						if(tmpDates == null){
							continue;
						}
						
						int daysOfThis = getDistantDays(tmpDates[0], tmpDates[1]);
						
						//根据免租分录计算该时间范围内的免租天数
						int freeDays = 0;
						for(int j=0; j<rentFrees.size(); j++){
							RentFreeEntryInfo rentFree = rentFrees.get(j);
							Date[] dealFreeDates = getWrapDates(tmpDates, new Date[]{rentFree.getFreeStartDate(), rentFree.getFreeEndDate()});
							if(dealFreeDates == null){
								continue;
							}
							freeDays = freeDays + getDistantDays(dealFreeDates[0], dealFreeDates[1]);
						}
						BigDecimal stepAmount = amount.multiply(int2BigDecimal(daysOfThis - freeDays)).divide(int2BigDecimal(daysOfThis), BigDecimal.ROUND_HALF_UP, 8);
//						BigDecimal stepAmount = getRentBetweenDate(tmpDates[0], tmpDates[1], rentType, amount, freeDays);
						if (stepAmount != null) {
							res = res.add(stepAmount);
						}
					}
				}
				*/
			}else{
				boolean wholeMonth = true;
				//对于完整月的期,再看有没有被递增日或者免租日截断
				//即检查该期的日期段是否存在于 >=2个递增分录或者存在免租分录		
				int wrapNum = 0;
				IDealAmountInfo currentDealAmount = null;
				for (int i = 0; i < dealAmounts.size(); i++) {
					IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmounts.getObject(i);
					if (dealAmount.getMoneyDefine() != null  &&  dealAmount.getMoneyDefine().getId().equals(moneyDefine.getId())) {
						Date[] tmpDates = getWrapDates(new Date[]{startDate, endDate}, new Date[]{dealAmount.getStartDate(), dealAmount.getEndDate()});
						if(tmpDates != null){
							wrapNum++;
							currentDealAmount = dealAmount;
						}
					}
				}
				if(wrapNum > 1){
					wholeMonth = false;
				}
				
				if(wholeMonth){
					for(int j=0; j<rentFrees.size(); j++){
						RentFreeEntryInfo rentFree = rentFrees.get(j);
						Date[] dealFreeDates = getWrapDates(new Date[]{startDate, endDate}, new Date[]{rentFree.getFreeStartDate(), rentFree.getFreeEndDate()});
						if(dealFreeDates == null){
							continue;
						}else{
							wholeMonth = false;
						}
					}
				}
				RentTypeEnum rentType = currentDealAmount.getRentType();
				//现在改成可以不定租就签合同，以前写的代码到处都可能没值，造成空指针
				if(rentType==null)
				{
					rentType = RentTypeEnum.RentByDay;
				}
				//如果是完整月，则根据月租*月数计算
				if(wholeMonth && (RentTypeEnum.RentByDay.equals(rentType) || RentTypeEnum.RentByWeek.equals(rentType) || 
				    RentTypeEnum.RentByQuarter.equals(rentType) || RentTypeEnum.RentByYear.equals(rentType))){
										
					BigDecimal amount = currentDealAmount.getAmount();
					if(amount==null)
					{
						amount = new BigDecimal(0);
					}
//					if(!rentType.equals(RentTypeEnum.RentByDay)){
//						logger.error("目前月均算法只支持按日定租的.");
//						//...
//					}
					if(RentTypeEnum.RentByDay.equals(rentType))
					{
						res = int2BigDecimal(monthes.size()).multiply(amount).multiply(int2BigDecimal(daysPerYear)).divide(int2BigDecimal(12), BigDecimal.ROUND_HALF_UP);
					}else if(RentTypeEnum.RentByWeek.equals(rentType))
					{
						//月均按周定价
						res = int2BigDecimal(monthes.size()).multiply(amount).divide(int2BigDecimal(7),BigDecimal.ROUND_HALF_UP).multiply(int2BigDecimal(daysPerYear)).divide(int2BigDecimal(12), BigDecimal.ROUND_HALF_UP);
					}else if(RentTypeEnum.RentByQuarter.equals(rentType))
					{
						//月均按季定价
						res = int2BigDecimal(monthes.size()).multiply(amount).divide(int2BigDecimal(3),BigDecimal.ROUND_HALF_UP);
					}else if(RentTypeEnum.RentByYear.equals(rentType))
					{
						//月均按年定价
						res = int2BigDecimal(monthes.size()).multiply(amount).divide(int2BigDecimal(12),BigDecimal.ROUND_HALF_UP);
					}
				}
				else{//如果不是完整月，根据实际天数计算
					res = getRentByActDate(moneyDefine, startDate, endDate, dealAmounts, rentFrees);
				}
			}
		}
		return res.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

//	private static BigDecimal getRentByActDate(MoneyDefineInfo moneyDefine, Date startDate, Date endDate, IObjectCollection dealAmounts, RentFreeEntryCollection rentFrees) {
//		BigDecimal res = FDCHelper.ZERO;
//		for (int i = 0; i < dealAmounts.size(); i++) {
//			IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmounts.getObject(i);
//			if (dealAmount.getMoneyDefine() != null  &&  dealAmount.getMoneyDefine().getId().equals(moneyDefine.getId())) {
//				RentTypeEnum rentType = dealAmount.getRentType();
//				BigDecimal amount = dealAmount.getAmount();
//				amount = amount==null?new BigDecimal(0):amount;
//
//				Date[] tmpDates = getWrapDates(new Date[]{startDate, endDate}, new Date[]{dealAmount.getStartDate(), dealAmount.getEndDate()});
//				if(tmpDates == null){
//					continue;
//				}
//				
//				//根据免租分录计算该时间范围内的免租天数
//				int freeDays = 0;
//				for(int j=0; j<rentFrees.size(); j++){
//					RentFreeEntryInfo rentFree = rentFrees.get(j);
//					Date[] dealFreeDates = getWrapDates(tmpDates, new Date[]{rentFree.getFreeStartDate(), rentFree.getFreeEndDate()});
//					if(dealFreeDates == null){
//						continue;
//					}
//					freeDays = freeDays + TenancyHelper.getDistantDays(dealFreeDates[0], dealFreeDates[1]);
//				}
//				BigDecimal stepAmount = getRentBetweenDate(tmpDates[0], tmpDates[1], rentType, amount, freeDays);
//				if (stepAmount != null) {
//					res = res.add(stepAmount);
//				}
//			}
//		}
//		return res;
//	}
	/**
	 * 描述：新加了免租类型之后的处理  by zhendui_ai
	 * @param moneyDefine
	 * @param startDate
	 * @param endDate
	 * @param dealAmounts
	 * @param rentFrees
	 * @return
	 */
	private static BigDecimal getRentByActDate(MoneyDefineInfo moneyDefine, Date startDate, Date endDate, IObjectCollection dealAmounts, RentFreeEntryCollection rentFrees) {
		BigDecimal res = FDCHelper.ZERO;
		for (int i = 0; i < dealAmounts.size(); i++) {
			IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmounts.getObject(i);
			if (dealAmount.getMoneyDefine() != null  &&  dealAmount.getMoneyDefine().getId().equals(moneyDefine.getId())) {
				RentTypeEnum rentType = dealAmount.getRentType();
				BigDecimal amount = dealAmount.getAmount();
				amount = amount==null?new BigDecimal(0):amount;

				Date[] tmpDates = getWrapDates(new Date[]{startDate, endDate}, new Date[]{dealAmount.getStartDate(), dealAmount.getEndDate()});
				if(tmpDates == null){
					continue;
				}
				
					//根据免租分录中的（免租类型）计算该时间范围内的租金免租天数  by zhendui_ai
					int freeDays = 0;
					for(int j=0; j<rentFrees.size(); j++){
						RentFreeEntryInfo rentFree = rentFrees.get(j);
						Date[] dealFreeDates = getWrapDates(tmpDates, new Date[]{rentFree.getFreeStartDate(), rentFree.getFreeEndDate()});
						if(dealFreeDates == null){
							continue;
						}
						freeDays = freeDays + TenancyHelper.getDistantDays(dealFreeDates[0], dealFreeDates[1],rentFree.getFreeTenancyType(),moneyDefine);
					}
					BigDecimal stepAmount = getRentBetweenDate(tmpDates[0], tmpDates[1], rentType, amount, freeDays);
					if (stepAmount != null) {
						res = res.add(stepAmount);
					}
				
				
			}
		}
		return res;
	}
	
	/**
	 * 根据租价获得2个日期之间的租金
	 * @param startDate
	 * @param endDate
	 * @param rentType
	 * @param rent
	 * */
	public static BigDecimal getRentBetweenDate(Date startDate, Date endDate, RentTypeEnum rentType, BigDecimal rent){
		return getRentBetweenDate(startDate, endDate, rentType, rent, 0);
	}
	
	/**
	 * 根据租价获得2个日期之间的租金
	 * 注：freeDays必须不大于租赁天数
	 * @param startDate
	 * @param endDate
	 * @param rentType
	 * @param rent
	 * @param freeDays
	 * 			免租天数
	 * */
	public static BigDecimal getRentBetweenDate(Date startDate, Date endDate, RentTypeEnum rentType, BigDecimal rent, int freeDays) {
		
		int days = getDistantDays(startDate, endDate);
		if (RentTypeEnum.RentByDay.equals(rentType)) {// 如果是按天计算租金,直接将计租天数*租金
			return rent.multiply(int2BigDecimal(days - freeDays));
		}
		
		if(freeDays > days){

			return FDCHelper.ZERO;
			//--这里曾经出现过这种情况,在改租调整金额时出现
			//throw new RuntimeException("计算租金时候免租天数大于计租天数,系统错误");
		}
		
		// 获得当前租期需要计算租金的日期
		List time = null;
		Date toPayStartDate = addCalendar(startDate, Calendar.DATE, freeDays);// 计租的起始日期(该租期的起始日期+免租天数)

		if (RentTypeEnum.RentByMonth.equals(rentType)) {
			time = getMonthList(toPayStartDate, endDate);
		} else if (RentTypeEnum.RentByWeek.equals(rentType)) {
			time = getWeekList(toPayStartDate, endDate);
		} else if (RentTypeEnum.RentByYear.equals(rentType)) {
			time = getYearList(toPayStartDate, endDate);
		} else if (RentTypeEnum.RentByQuarter.equals(rentType)) {
			time = getQuarterList(toPayStartDate, endDate);
		} else {
			// 暂只实现按年,按月,按季度,按周其他方式暂未实现
			return null;
		}

		if (time == null || time.isEmpty()) {
			return null;
		}

		Date[] endTime = (Date[]) time.get(time.size() - 1);

		int dealLastTimeDays = getDistantDays(endTime[0], endTime[1]); //最后一个时间单位里计租的天数

		int lastTimeDays = 0;// 最后一个时间单位的总天数
		if (RentTypeEnum.RentByMonth.equals(rentType)) {
			lastTimeDays = getMonthDays(endTime[0]);
		} else if (RentTypeEnum.RentByWeek.equals(rentType)) {
			lastTimeDays = 7;
		} else if (RentTypeEnum.RentByYear.equals(rentType)) {
			lastTimeDays = getYearDays(endTime[0]);
		} else if (RentTypeEnum.RentByQuarter.equals(rentType)) {
			lastTimeDays = getQuarterDays(endTime[0]);
		} else {
			//不支持的租金类型
			return null;
		}

		// 最后一个时间单位的租金(执行天数/总天数)
		BigDecimal lastMonthRent = int2BigDecimal(dealLastTimeDays).multiply(rent).divide(int2BigDecimal(lastTimeDays), 2, BigDecimal.ROUND_HALF_UP);

		// 前几个时间单位的租金 + 最后一个时间单位的租金
		return rent.multiply(int2BigDecimal(time.size() - 1)).add(lastMonthRent);
	}	
	
	/**
	 * 获得该日期所在年的总天数
	 * */
	public static int getYearDays(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		return (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0) ? 366 : 365);
	}
	
	/**
	 * 获得该日期所在季度的总天数
	 * */
	public static int getQuarterDays(Date date) {
		int days = getMonthDays(date);
		for(int i=0; i<2; i++){
			date = addCalendar(date, Calendar.MONTH, 1);
			days = days + getMonthDays(date);
		}
		return days;
	}
	
	/**
	 * 获得该日期与之后1个月之间的天数
	 * */
	public static int getMonthDays(Date date){
		Date endDate = addCalendar(date, Calendar.MONTH, 1);
		endDate = addCalendar(endDate, Calendar.DATE, -1);
		return getDistantDays(date, endDate);
	}
	
	private static final byte WEEK = 1;
	private static final byte MONTH = 2;
	private static final byte YEAR = 3;
	private static final byte QUARTER = 4;
	
	private static List getTimeList(Date startDate, Date endDate, byte field) {
		startDate = FDCDateHelper.getDayBegin(startDate);
		endDate = FDCDateHelper.getDayBegin(endDate);
		List list = new ArrayList();
		if (startDate.after(endDate)) {
			return list;
		}
		
		if(startDate.compareTo(endDate) == 0){
			list.add(new Date[] { startDate, endDate });
			return list;
		}
		
		Date tmpDate = startDate;
		while (!tmpDate.after(endDate)) {
			Date tStartDate = tmpDate;
			if(field == WEEK){
				tmpDate = addCalendar(tmpDate, Calendar.DATE, 7);
			}else if(field == MONTH){
				tmpDate = addCalendar(tmpDate, Calendar.MONTH, 1);
			}else if(field == YEAR){
				tmpDate = addCalendar(tmpDate, Calendar.YEAR, 1);
			}else if(field == QUARTER){
				tmpDate = addCalendar(tmpDate, Calendar.MONTH, 3);
			}else{
				return list;
			}
			
			if (!tmpDate.after(endDate)) {
				//租赁的实际业务天数计算和日期的表现不一样，需要以实际业务为准
				//如租期为1-1到2-1号.代码中显示的是1-1 00:00:00 到 2-1 00:00:00;而业务实际需要表达的是1-1 00:00:00 到 2-1 23:59:59
				list.add(new Date[] { tStartDate, addCalendar(tmpDate, Calendar.DATE, -1) });
			} else {
				list.add(new Date[] { tStartDate, endDate });
			}
		}
		return list;
	}
	
	/**
	 * 获得2个日期间的周集合
	 * 注：startDate,endDate应忽略时分秒
	 * @return List<Date[]> Date[]长度为2,Date[0]表示周起始日期,Date[1]表示周结束日期
	 * */
	public static List getWeekList(Date startDate, Date endDate) {
		return getTimeList(startDate, endDate, WEEK);
	}
	
	public static List getQuarterList(Date startDate, Date endDate) {
		return getTimeList(startDate, endDate, QUARTER);
	}
	
	/**
	 * 获得2个日期间的月份集合
	 * 注：startDate,endDate应忽略时分秒
	 * @return List<Date[]> Date[]长度为2,Date[0]表示月起始日期,Date[1]表示月结束日期
	 * */
	public static List getMonthList(Date startDate, Date endDate) {
		return getTimeList(startDate, endDate, MONTH);
	}
	
	/**
	 * 获得2个日期间的年集合
	 * 注：startDate,endDate应忽略时分秒
	 * @return List<Date[]> Date[]长度为2,Date[0]表示年起始日期,Date[1]表示年结束日期
	 * */
	public static List getYearList(Date startDate, Date endDate) {
		return getTimeList(startDate, endDate, YEAR);
	}
	
	/**
	 * 获取2个日期间的天数,供租赁合同使用
	 * 注：2008-01-01至2008-01-01间天数为0;2008-01-01至2008-01-02间天数为1
	 * 还是按照实际业务对日期进行处理  zhicheng_jin  modify 
	 * */
	public static int getDistantDays(Date beginDate, Date endDate){
//		return FDCDateHelper.getDiffDays(beginDate, endDate) - 1;
		return FDCDateHelper.getDiffDays(beginDate, endDate);
	}
	
	/**
	 * 获取2个日期间的天数,供租赁合同使用
	 * 注：2008-01-01至2008-01-01间天数为0;2008-01-01至2008-01-02间天数为1
	 * 还是按照实际业务对日期进行处理  (需要根据免租类型和款项定义来计算免租的天数)
	 * 新增  “免租类型”  后的处理方式     by zhendui_ai
	 * */
	public static int getDistantDays(Date beginDate, Date endDate,FreeTenancyTypeEnum freeTenancy,MoneyDefineInfo moneyDefine){
		if(MoneyTypeEnum.RentAmount.equals(moneyDefine.getMoneyType())){
			if(FreeTenancyTypeEnum.FreeTenAndMoney.equals(freeTenancy) || FreeTenancyTypeEnum.FreeTenNotMoney.equals(freeTenancy)){
				return FDCDateHelper.getDiffDays(beginDate, endDate);
			}else{
				return 0;
			}
		}else if(MoneyTypeEnum.PeriodicityAmount.equals(moneyDefine.getMoneyType())){
			if(FreeTenancyTypeEnum.FreeTenAndMoney.equals(freeTenancy) || FreeTenancyTypeEnum.FreeMoneyNotTen.equals(freeTenancy)){
				return FDCDateHelper.getDiffDays(beginDate, endDate);
			}else{
				return 0;
			}
		}else{
			return 0;
		}
	}
	
	public static Date addCalendar(Date srcDate, int field, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(srcDate);
		cal.add(field, amount);
		return cal.getTime();
	}
	
	 public static BigDecimal int2BigDecimal(int i){
		 return new BigDecimal(Integer.toString(i));
	 }
	 
	 /**
	  * 根据2个日期范围，返回其重叠日期的起始和结束日期,没有重叠时返回null<br>
	  * Date[]长度为2，表示起始日期和结束日期。
	  * */
	 public static Date[] getWrapDates(Date[] dates1, Date[] dates2){
		 if(dates1 == null  ||  dates2 == null){
			 return null;
		 }
		 if(dates1[0] == null  ||  dates1[1] == null  ||  
				 dates2[0] == null  ||  dates2[1] == null){
			 return null;
		 }
		 
		 if(dates1[0].after(dates1[1]) || dates2[0].after(dates2[1])){
			 logger.error("起始结束日期都弄反了.笨死.");
			 return null;
		 }
		 
		 Date tmpS = dates1[0];
		 Date tmpE = dates1[1];
		 
		 if(!dates2[1].before(tmpS) && !dates2[1].after(tmpE)){
			 tmpE = dates2[1];
			 if(dates2[0].after(tmpS)){
				 tmpS = dates2[0];
			 }
		 }else if(!dates2[0].before(tmpS) && !dates2[0].after(tmpE)){
			 tmpS = dates2[0];
			 if(dates2[1].before(tmpE)){
				 tmpE = dates2[1];
			 }
		 }else if(dates2[0].before(tmpS) && dates2[1].after(tmpE)){
			 
		 }else{
			 return null;
		 }
		 
		 return new Date[]{tmpS, tmpE};
	 }
	 
	 public static void main(String[] args) {
		Date[] d1 = new Date[]{FDCDateHelper.stringToDate("2009-01-15"), FDCDateHelper.stringToDate("2009-2-15")};
		Date[] d2 = new Date[]{FDCDateHelper.stringToDate("2009-2-1"), FDCDateHelper.stringToDate("2009-02-30")};
		Date[] res = getWrapDates(d1, d2);
		System.out.println(d1[0].toLocaleString() + "  " + d1[1].toLocaleString());
		System.out.println(d2[0].toLocaleString() + "  " + d2[1].toLocaleString());
		System.out.println(res[0].toLocaleString() + "  " + res[1].toLocaleString());
	 }
	 
	 /**
     * 调整剩余押金和付款明细的应付金额,并更新可退金额. TODO 后面的扣款金额应该会针对房间设置.故这里未考虑改租,退租时的扣款金额
     * 对于改租,更名和退租,根据操作日期,需要更新原合同的应付金额,并将多交的实付金额加到房间押金上;续租不存在应付日期的改动,所以原合同应付不会变动
     * @param ctx
     * @param bizDate 改租或者续租的起始日期
     * @param tenBill
     * @param oldTenBill
     * @param isExcuteToDB
     * 			金额调整是否更新到数据库.考虑到改租等合同提交时,需要对原合同是否足够结算进行验证,而不会将调整的金额入库(审批时候才会),提供该参数
     * */
    public static void adjustDepositeAndPayList(Context ctx, Date bizDate, TenancyBillInfo oldTenBill, boolean isExecuteToDB) throws EASBizException, BOSException{
    	TenancyRoomEntryCollection oldTenRooms = oldTenBill.getTenancyRoomList();
    	TenAttachResourceEntryCollection oldTenAttachs = oldTenBill.getTenAttachResourceList();
    	
    	SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("remainDepositAmount");
    	
		BigDecimal oldTenRemainDepositAmount = FDCHelper.ZERO;
		for(int i=0; i<oldTenRooms.size(); i++){
			TenancyRoomEntryInfo oldTenRoom = oldTenRooms.get(i);
			RentTypeEnum rentType = oldTenRoom.getDealRentType();
			//用来记录该房间的所有可结转的金额。即实收押金+多收的租金
			BigDecimal oldRoomRemainDepositAmount = FDCHelper.ZERO;
			
			TenancyRoomPayListEntryCollection tenRoomPayList = oldTenRoom.getRoomPayList();
			
//			int interruptSeq = -1;//表示改租,转名时被变动日期截断的那期收款明细的序列号
			for(int j=0; j<tenRoomPayList.size(); j++){
				TenancyRoomPayListEntryInfo tenRoomPay = tenRoomPayList.get(j);
				
				Date payStartDate = tenRoomPay.getStartDate();
				Date payEndDate = tenRoomPay.getEndDate();

				BigDecimal actPayAmount = tenRoomPay.getActAmount();
				if(actPayAmount == null) actPayAmount = FDCHelper.ZERO;
				//原应付金额
				BigDecimal srcAppPayAmount = tenRoomPay.getAppAmount();
				if(srcAppPayAmount == null) srcAppPayAmount = FDCHelper.ZERO;
				MoneyDefineInfo moneyName = tenRoomPay.getMoneyDefine();
				
				//如果是押金，则设置押金的可退金额为押金实收金额
				if(moneyName != null  &&  MoneyTypeEnum.DepositAmount.equals(moneyName.getMoneyType())){
					BigDecimal canRefund = tenRoomPay.getCanRefundmentAmount();
					if(actPayAmount!=null){
//					if(actPayAmount.compareTo(FDCHelper.ZERO) != 0){
						if(canRefund == null  ||  canRefund.compareTo(FDCHelper.ZERO) == 0){
							oldRoomRemainDepositAmount = oldRoomRemainDepositAmount.add(actPayAmount);
//							//如果是转名或者转租合同时，如果新合同开始时间在原合同首期，
//							//那么款项为押金类型的金额不改但是日期需要更改为新合同的起始日期
//							if(payStartDate.before(bizDate) && !payEndDate.before(bizDate))
//							{
//								tenRoomPay.setEndDate(FDCDateHelper.getBeforeDay(bizDate));
//							}							
							tenRoomPay.setLimitAmount(actPayAmount);	//TODO  Temp modify by Jeegan***************
							if(isExecuteToDB)updateAppAmountAndCanRefundmentAmount(ctx, tenRoomPay);
						}
					}
					continue;
				}
				
				//如果是变更日期后的付款明细,应付更新为0,将实付的租金全部加到押金上面
				if(!payStartDate.before(bizDate)){
//					if(actPayAmount != null){
					if(actPayAmount != null  &&  actPayAmount.compareTo(FDCHelper.ZERO) != 0){
//						actPayAmount = CRMHelper.getBigDecimal(actPayAmount);
						oldRoomRemainDepositAmount = oldRoomRemainDepositAmount.add(actPayAmount);
						
						BigDecimal canRefund = tenRoomPay.getCanRefundmentAmount();
						if(canRefund == null){
							canRefund = FDCHelper.ZERO;
						}
						
						BigDecimal tarAppPayAmount = FDCHelper.ZERO;//该期的应付金额将要变为0
						//应付金额的变化金额，用来作为可退金额的变化金额。同时需要控制，0<=可退金额<=实付金额
						canRefund = canRefund.add(srcAppPayAmount.subtract(tarAppPayAmount));
						if(canRefund.compareTo(FDCHelper.ZERO) < 0){
							canRefund = FDCHelper.ZERO;
						}else if(canRefund.compareTo(actPayAmount) > 0){
							canRefund = actPayAmount;
						}
						
						tenRoomPay.setLimitAmount(canRefund);	//TODO  Temp modify by Jeegan***************
						//TODO 是否需要更新收款单上的可退金额.
					}
					tenRoomPay.setAppAmount(FDCHelper.ZERO);
					if(isExecuteToDB)updateAppAmountAndCanRefundmentAmount(ctx, tenRoomPay);
				}else if(payEndDate.before(bizDate)){
					//在变更日期之前的完整付款明细,应付不涉及修改;但可能因为租金减免导致该付款明细多收了钱，所以需要更新其可退金额
					if(actPayAmount.compareTo(srcAppPayAmount) > 0){
						oldRoomRemainDepositAmount = oldRoomRemainDepositAmount.add(actPayAmount.subtract(srcAppPayAmount));
						tenRoomPay.setLimitAmount(actPayAmount.subtract(srcAppPayAmount));	//TODO  Temp modify by Jeegan***************
					}
					if(isExecuteToDB)updateAppAmountAndCanRefundmentAmount(ctx, tenRoomPay);
				}else if(payStartDate.before(bizDate) && !payEndDate.before(bizDate)){
					
					//被截断的那一期，只对租金的应付进行修改
					if(moneyName == null  ||  (!MoneyTypeEnum.RentAmount.equals(moneyName.getMoneyType()) && !MoneyTypeEnum.PeriodicityAmount.equals(moneyName.getMoneyType()))){
						continue;
					}
					int interruptLeaseSeq = tenRoomPay.getLeaseSeq();
					
					BigDecimal totalBornAppPayAmount = FDCHelper.ZERO;
					BigDecimal totalCusSettedAppPayAmount = FDCHelper.ZERO;
					BigDecimal totalActPayAmount = FDCHelper.ZERO;
					
					/*
					 * 原来是可以将2期的应付合并到1期中，但改租时2期仍要按照标准应付计算,
					 * 所以需要保证应付金额和标准计算的应付金额相等，可能出现下面的情况
					 * 原应实付为 100/80,10/0 (1期2期的标准应付都为55,但将2期的应付调到了1期)，
					 * 变更后调整为100/80,-40/0 (变更后标准应付为60，而第一期标准应付为100，且只能修改变更这一期的应付，所以2期应付更新为了-40)
					 * 
					 * 现在应付明细不让手动录入，也不存在说将第2期的应付调到1期的情况。所以也不用再通过总标准应付来计算变更这一期的应付金额
					 * 
					for(int m=0; m<tenRoomPayList.size(); m++){
						TenancyRoomPayListEntryInfo tmpRoomPay = tenRoomPayList.get(m);
						if(tmpRoomPay.getLeaseSeq() == interruptLeaseSeq){
							break;
						}
						
						BigDecimal bornAppPayAmount = null;//getRentBetweenDate(tmpRoomPay.getStartDate(), tmpRoomPay.getEndDate(), oldTenRoom.getDealRentType(), oldTenRoom.getDealRoomRent(), tmpRoomPay.getFreeDays());
						if(bornAppPayAmount == null) bornAppPayAmount = FDCHelper.ZERO;
						totalBornAppPayAmount = totalBornAppPayAmount.add(bornAppPayAmount);
						
						BigDecimal cusSettedAppPayAmount = tmpRoomPay.getAppAmount();
						if(cusSettedAppPayAmount == null) cusSettedAppPayAmount = FDCHelper.ZERO;
						totalCusSettedAppPayAmount = totalCusSettedAppPayAmount.add(cusSettedAppPayAmount);
						
						BigDecimal tmpActPayAmount = tmpRoomPay.getActAmount();
						if(tmpActPayAmount == null) tmpActPayAmount = FDCHelper.ZERO;
						totalActPayAmount = totalActPayAmount.add(tmpActPayAmount);
					}
					
					//改租这期的免租天数，不进行分摊到整个租期,直接用来计算该期的标准应付租金;改租日的前1天作为当前租期的结束日期计算租金
					totalBornAppPayAmount = totalBornAppPayAmount.add(getRentBetweenDate(tenRoomPay.getStartDate(), addCalendar(bizDate, Calendar.DATE, -1), oldTenRoom.getDealRentType(), oldTenRoom.getDealRoomRent(), tenRoomPay.getFreeDays()));
					if(actPayAmount != null) totalActPayAmount = totalActPayAmount.add(actPayAmount);
					
					//TODO注意这里,需要控制该100/80的明细不能再进行收款.暂未控制
					BigDecimal tarAppAmount = totalBornAppPayAmount.subtract(totalCusSettedAppPayAmount);
					tenRoomPay.setAppAmount(tarAppAmount);
					*/
					
					//应付金额为原合同当前期应付金额乘以改租天数占原期间天数的比例
					int dealLastTimeDays = getDistantDays(tenRoomPay.getStartDate(), tenRoomPay.getEndDate());
					int days= getDistantDays(tenRoomPay.getStartDate(),bizDate);
//					int days= getDistantDays(tenRoomPay.getStartDate(),FDCDateHelper.getBeforeDay(bizDate));
					BigDecimal chgLeaseAppAmount = srcAppPayAmount.multiply(new BigDecimal(days)).divide(new BigDecimal(dealLastTimeDays), 2,BigDecimal.ROUND_HALF_UP);
					//变更这一期的应付金额
//					BigDecimal chgLeaseAppAmount = getRentBetweenDate(moneyName, tenRoomPay.getStartDate(), addCalendar(bizDate, Calendar.DATE, -1), oldTenRoom.getDealAmounts(), oldTenBill.getRentFrees());
					if(chgLeaseAppAmount == null){
						chgLeaseAppAmount = FDCHelper.ZERO;
					}
					//如果有减免金额(即以前进行过减免),还得加上减免金额,但需要控制,减去减免金额后应该>=0
					if(tenRoomPay.getRemissionAmount() != null){
						chgLeaseAppAmount = chgLeaseAppAmount.subtract(tenRoomPay.getRemissionAmount());
						if(chgLeaseAppAmount.compareTo(FDCHelper.ZERO) < 0){
							chgLeaseAppAmount = FDCHelper.ZERO;
						}
					}
//					tenRoomPay.setEndDate(FDCDateHelper.getBeforeDay(bizDate));
					tenRoomPay.setAppAmount(chgLeaseAppAmount);
					
					BigDecimal canRefund = tenRoomPay.getCanRefundmentAmount();
					if(canRefund == null){
						canRefund = FDCHelper.ZERO;
					}
					//应付金额的变化金额，用来作为可退金额的变化金额。同时需要控制，0<=可退金额<=实付金额
					canRefund = canRefund.add(srcAppPayAmount.subtract(chgLeaseAppAmount));
					if(canRefund.compareTo(FDCHelper.ZERO) < 0){
						canRefund = FDCHelper.ZERO;
					}else if(canRefund.compareTo(actPayAmount) > 0){
						canRefund = actPayAmount;
					}
					
					tenRoomPay.setLimitAmount(canRefund);   //TODO  Temp modify by Jeegan***************
					//TODO 是否需要更新收款单上的可退金额.
					
					//累计实付金额比调整后的应付金额大时,才将多收的钱加到剩余押金上
					if(actPayAmount.compareTo(chgLeaseAppAmount) > 0){
						oldRoomRemainDepositAmount = oldRoomRemainDepositAmount.add(actPayAmount.subtract(chgLeaseAppAmount));
					}
					
//					if(totalActPayAmount.compareTo(totalBornAppPayAmount) > 0){
//						oldRoomRemainDepositAmount = oldRoomRemainDepositAmount.add(totalActPayAmount.subtract(totalBornAppPayAmount));
//					}
					
					if(isExecuteToDB)updateAppAmountAndCanRefundmentAmount(ctx, tenRoomPay);
					/*					
					//计算要扣掉的应付金额,也就是变动日期到该租期结束日期之间的租金金额
					reducedAppPayAmount = TenancyHelper.getRentBetweenDate(bizDate, payEndDate, oldTenRoom.getDealRentType(), oldTenRoom.getDealRoomRent());
					
					if(appPayAmount == null)appPayAmount = FDCHelper.ZERO;
					
					BigDecimal tmp = appPayAmount.subtract(reducedAppPayAmount);//计算原应付金额与扣掉的金额的差
					
					if(tmp.compareTo(FDCHelper.ZERO) >= 0){//该期原应付金额大于等于要扣掉的金额
						tenRoomPay.setAppAmount(tmp);
						reducedAppPayAmount = FDCHelper.ZERO;
					}else{//该期原应付金额不够扣掉的金额
						tenRoomPay.setAppAmount(FDCHelper.ZERO);
						reducedAppPayAmount = reducedAppPayAmount.subtract(appPayAmount);
					}
					
					if(actPayAmount != null  &&  actPayAmount.compareTo(FDCHelper.ZERO) != 0){
						//实付金额比调整后的应付金额大时,才将多收的钱加到剩余押金上
						if(actPayAmount.compareTo(tenRoomPay.getAppAmount()) > 0){
							oldRoomRemainDepositAmount = oldRoomRemainDepositAmount.add(actPayAmount.subtract(tenRoomPay.getAppAmount()));
						}
					}
					
					if(isExecuteToDB)updateAppAmount(ctx, tenRoomPay);
					*/
				}else{
					logger.warn("脏数据或者逻辑错误.tenRoomPay ID:" + tenRoomPay.getId().toString());
					throw new BOSException("脏数据或者逻辑错误.");
				}
			}
			
			/*
			//被截断那期原应付金额时不够扣除的应付金额时,应该在被截断租期前面的租期中继续扣除
			for(int j=interruptSeq - 1; j>0 && reducedAppPayAmount != null && reducedAppPayAmount.compareTo(FDCHelper.ZERO) > 0; j--){
				TenancyRoomPayListEntryInfo tenRoomPayOfSeq = getTenRoomPayOfSeq(tenRoomPayList, j);
				if(tenRoomPayOfSeq == null){
					logger.warn("脏数据或者逻辑错误.tenRoom ID:" + oldTenRoom.getId().toString());
					continue;
				}
				BigDecimal appAmount = tenRoomPayOfSeq.getAppAmount();
				BigDecimal actAmount = tenRoomPayOfSeq.getActAmount();
				
				if(appAmount == null)appAmount = FDCHelper.ZERO;
				
				BigDecimal tmp = appAmount.subtract(reducedAppPayAmount);//计算原应付金额与扣掉的金额的差
				if(tmp.compareTo(FDCHelper.ZERO) >= 0){
					tenRoomPayOfSeq.setAppAmount(tmp);
					reducedAppPayAmount = FDCHelper.ZERO;
				}else{
					tenRoomPayOfSeq.setAppAmount(FDCHelper.ZERO);
					reducedAppPayAmount = reducedAppPayAmount.subtract(appAmount);
				}
				if(actAmount != null  &&  actAmount.compareTo(FDCHelper.ZERO) != 0){
					//实付金额比调整后的应付金额大时,才将多收的钱加到剩余押金上
					if(actAmount.compareTo(tenRoomPayOfSeq.getAppAmount()) > 0){
						oldRoomRemainDepositAmount = oldRoomRemainDepositAmount.add(actAmount.subtract(tenRoomPayOfSeq.getAppAmount()));
					}
				}
				
				if(isExecuteToDB)updateAppAmount(ctx, tenRoomPayOfSeq);
			}
			
			if(reducedAppPayAmount != null  &&  reducedAppPayAmount.compareTo(FDCHelper.ZERO) > 0){
				throw new BOSException("付款计划偏向后期收款,现系统暂不支持.");//原有前面几期的所有应付之和仍然不够扣除的应付金额,说明原本应付设置的偏重与后期收款,目前情况没法处理了.抛出异常吧. TODO
			}
			*/
			
			//更新原租赁房间的剩余押金
			if(oldTenRoom.getRemainDepositAmount() == null  ||  oldRoomRemainDepositAmount.compareTo(oldTenRoom.getRemainDepositAmount()) != 0){
				oldTenRoom.setRemainDepositAmount(oldRoomRemainDepositAmount);
				if(isExecuteToDB)TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(oldTenRoom, sels);
			}
			oldTenRemainDepositAmount = oldTenRemainDepositAmount.add(oldRoomRemainDepositAmount);
		}
		
		
		for(int i=0; i<oldTenAttachs.size(); i++){
			TenAttachResourceEntryInfo oldTenAttach = oldTenAttachs.get(i);
			
			//用来记录该配套资源的所有可结转的金额。即实收押金+多收的租金
			BigDecimal oldAttachRemainDepositAmount = FDCHelper.ZERO;
			
			TenAttachResourcePayListEntryCollection tenAttachPayList = oldTenAttach.getAttachResPayList();
			for(int j=0; j<tenAttachPayList.size(); j++){
				TenAttachResourcePayListEntryInfo tenAttachPay = tenAttachPayList.get(j);
				
				Date payStartDate = tenAttachPay.getStartDate();
				Date payEndDate = tenAttachPay.getEndDate();
				
				BigDecimal actPayAmount = tenAttachPay.getActAmount();
				if(actPayAmount == null) actPayAmount = FDCHelper.ZERO;
				//原应付金额
				BigDecimal srcAppPayAmount = tenAttachPay.getAppAmount();
				if(srcAppPayAmount == null) srcAppPayAmount = FDCHelper.ZERO;
				MoneyDefineInfo moneyName = tenAttachPay.getMoneyDefine();
				
				//如果是押金，则设置押金的可退金额为押金实收金额
				if(moneyName != null  &&  MoneyTypeEnum.DepositAmount.equals(moneyName.getMoneyType())){
					BigDecimal canRefund = tenAttachPay.getCanRefundmentAmount();
					if(actPayAmount.compareTo(FDCHelper.ZERO) != 0){
						if(canRefund == null  ||  canRefund.compareTo(FDCHelper.ZERO) == 0){
							oldAttachRemainDepositAmount = oldAttachRemainDepositAmount.add(actPayAmount);
							tenAttachPay.setCanRefundmentAmount(actPayAmount);
							if(isExecuteToDB)updateAppAmountAndCanRefundmentAmount(ctx, tenAttachPay);
						}
					}
					continue;
				}
				
				//如果是变更日期后的付款明细,应付更新为0,将实付的租金全部加到押金上面
				if(!payStartDate.before(bizDate)){
					if(actPayAmount != null  &&  actPayAmount.compareTo(FDCHelper.ZERO) != 0){
						oldAttachRemainDepositAmount = oldAttachRemainDepositAmount.add(actPayAmount);
						BigDecimal canRefund = tenAttachPay.getCanRefundmentAmount();
						if(canRefund == null){
							canRefund = FDCHelper.ZERO;
						}
						
						BigDecimal tarAppPayAmount = FDCHelper.ZERO;//该期的应付金额将要变为0
						//应付金额的变化金额，用来作为可退金额的变化金额。同时需要控制，0<=可退金额<=实付金额
						canRefund = canRefund.add(srcAppPayAmount.subtract(tarAppPayAmount));
						if(canRefund.compareTo(FDCHelper.ZERO) < 0){
							canRefund = FDCHelper.ZERO;
						}else if(canRefund.compareTo(actPayAmount) > 0){
							canRefund = actPayAmount;
						}
						
						tenAttachPay.setCanRefundmentAmount(canRefund);
						//TODO 是否需要更新收款单上的可退金额.
					}
					tenAttachPay.setAppAmount(FDCHelper.ZERO);
					if(isExecuteToDB)updateAppAmountAndCanRefundmentAmount(ctx, tenAttachPay);
				}else if(payEndDate.before(bizDate)){
					//在变更日期之前的完整付款明细,应付不涉及修改;但可能因为租金减免导致该付款明细多收了钱，所以需要更新其可退金额
					if(actPayAmount.compareTo(srcAppPayAmount) > 0){
						oldAttachRemainDepositAmount = oldAttachRemainDepositAmount.add(actPayAmount.subtract(srcAppPayAmount));
						tenAttachPay.setCanRefundmentAmount(actPayAmount.subtract(srcAppPayAmount));
					}
					if(isExecuteToDB)updateAppAmountAndCanRefundmentAmount(ctx, tenAttachPay);
				}else if(payStartDate.before(bizDate) && !payEndDate.before(bizDate)){
					//被截断的那一期，只对租金的应付进行修改
					if(moneyName == null  ||  !MoneyTypeEnum.RentAmount.equals(moneyName.getMoneyType())){
						continue;
					}
					int interruptLeaseSeq = tenAttachPay.getLeaseSeq();
					
					BigDecimal totalBornAppPayAmount = FDCHelper.ZERO;
					BigDecimal totalCusSettedAppPayAmount = FDCHelper.ZERO;
					BigDecimal totalActPayAmount = FDCHelper.ZERO;
					
					//变更这一期的应付金额
					BigDecimal chgLeaseAppAmount = getRentBetweenDate(moneyName, tenAttachPay.getStartDate(), addCalendar(bizDate, Calendar.DATE, -1), oldTenAttach.getDealAmounts(), oldTenBill.getRentFrees());
					if(chgLeaseAppAmount == null){
						chgLeaseAppAmount = FDCHelper.ZERO;
					}
					//如果有减免金额(即以前进行过减免),还得加上减免金额,但需要控制,减去减免金额后应该>=0
					if(tenAttachPay.getRemissionAmount() != null){
						chgLeaseAppAmount = chgLeaseAppAmount.subtract(tenAttachPay.getRemissionAmount());
						if(chgLeaseAppAmount.compareTo(FDCHelper.ZERO) < 0){
							chgLeaseAppAmount = FDCHelper.ZERO;
						}
					}
					tenAttachPay.setAppAmount(chgLeaseAppAmount);
					
					BigDecimal canRefund = tenAttachPay.getCanRefundmentAmount();
					if(canRefund == null){
						canRefund = FDCHelper.ZERO;
					}
					//应付金额的变化金额，用来作为可退金额的变化金额。同时需要控制，0<=可退金额<=实付金额
					canRefund = canRefund.add(srcAppPayAmount.subtract(chgLeaseAppAmount));
					if(canRefund.compareTo(FDCHelper.ZERO) < 0){
						canRefund = FDCHelper.ZERO;
					}else if(canRefund.compareTo(actPayAmount) > 0){
						canRefund = actPayAmount;
					}
					
					tenAttachPay.setCanRefundmentAmount(canRefund);
					//TODO 是否需要更新收款单上的可退金额.
					
					//累计实付金额比调整后的应付金额大时,才将多收的钱加到剩余押金上
					if(actPayAmount.compareTo(chgLeaseAppAmount) > 0){
						oldAttachRemainDepositAmount = oldAttachRemainDepositAmount.add(actPayAmount.subtract(chgLeaseAppAmount));
					}
					
					if(isExecuteToDB)updateAppAmountAndCanRefundmentAmount(ctx, tenAttachPay);
				}else{
					logger.warn("脏数据或者逻辑错误.tenRoomPay ID:" + tenAttachPay.getId().toString());
					throw new BOSException("脏数据或者逻辑错误.");
				}
			}

			//更新原租赁配套资源的剩余押金 TODO 这个字段不维护了。
//			if(oldTenAttach.getRemainDepositAmount() == null  ||  oldRoomRemainDepositAmount.compareTo(oldTenAttach.getRemainDepositAmount()) != 0){
//				oldTenAttach.setRemainDepositAmount(oldRoomRemainDepositAmount);
//				if(isExecuteToDB)TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(oldTenAttach, sels);
//			}
			oldTenRemainDepositAmount = oldTenRemainDepositAmount.add(oldAttachRemainDepositAmount);
		}

		if(oldTenBill.getRemainDepositAmount() == null  ||  oldTenRemainDepositAmount.compareTo(oldTenBill.getRemainDepositAmount()) != 0){
			oldTenBill.setRemainDepositAmount(oldTenRemainDepositAmount);
			if(isExecuteToDB)TenancyBillFactory.getLocalInstance(ctx).updatePartial(oldTenBill, sels);
		}
    }
    
    private static void updateAppAmountAndCanRefundmentAmount(Context ctx, TenAttachResourcePayListEntryInfo tenAttachPay) throws EASBizException, BOSException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("appAmount");
		sels.add("canRefundmentAmount");
		TenAttachResourceEntryFactory.getLocalInstance(ctx).updatePartial(tenAttachPay, sels);
	}

	/**
     * 更新应付金额字段和可退金额字段
     * */
	private static void updateAppAmountAndCanRefundmentAmount(Context ctx, TenancyRoomPayListEntryInfo tenRoomPay) throws BOSException, EASBizException {
		SelectorItemCollection sels = new SelectorItemCollection();
//		sels.add("endDate");
		sels.add("appAmount");
		sels.add("canRefundmentAmount");
		TenancyRoomPayListEntryFactory.getLocalInstance(ctx).updatePartial(tenRoomPay, sels);
	}
    
    /**
     * 根据序号获得付款明细集合中对应的明细记录
     * */
    private static TenancyRoomPayListEntryInfo getTenRoomPayOfSeq(TenancyRoomPayListEntryCollection tenRoomPayList, int seq) {
    	for(int i=0; i<tenRoomPayList.size(); i++){
			TenancyRoomPayListEntryInfo tenRoomPay = tenRoomPayList.get(i);
			if(tenRoomPay.getSeq() == seq){
				return tenRoomPay;
			}
		}
    	return null;
	}
    
    /**
     * 原合同剩余押金减去扣款,并分摊到各个房间的剩余押金上
     * TODO 对于改租更名,执行该操作之前有检查剩余押金是否够结转;但退租审批时调用该操作,如果剩余押金不够结帐时仍要进行退租操作,是否要将负的押金设到原租赁房间的剩余押金上
     * @param ctx
     * @param deductAmount
     * 			扣除的金额
     * @param oldTenBill
     * 			原合同
     * @throws BOSException 
     * @throws EASBizException 
     * @deprecated 目前未被调用
     * */
    public static void deductAmountOfRemainDepositAmount(Context ctx, BigDecimal deductAmount, TenancyBillInfo oldTenBill) throws EASBizException, BOSException {
    	if(deductAmount == null  ||  deductAmount.compareTo(FDCHelper.ZERO) == 0){
    		return;
    	}
    	
    	BigDecimal tenRemainDepositAmount = oldTenBill.getRemainDepositAmount();
    	if(tenRemainDepositAmount == null  ||  tenRemainDepositAmount.compareTo(deductAmount) < 0){
    		throw new TenancyException(TenancyException.DEPOSIT_NOT_ENOUGH);
    	}
    	
    	SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("remainDepositAmount");
		
		BigDecimal remainDeductAmount = deductAmount;
    	TenancyRoomEntryCollection oldTenRooms = oldTenBill.getTenancyRoomList();
    	//TODO 合同押金扣款分摊到各房间押金上的策略待定,目前只是按顺序扣除
    	for(int i=0; i<oldTenRooms.size() && remainDeductAmount.compareTo(FDCHelper.ZERO) > 0; i++){
    		TenancyRoomEntryInfo oldTenRoom = oldTenRooms.get(i);
    		BigDecimal oldRoomRemainDepositAmount = oldTenRoom.getRemainDepositAmount();
    		
    		if(oldRoomRemainDepositAmount != null  &&  oldRoomRemainDepositAmount.compareTo(FDCHelper.ZERO) != 0){
    			if(remainDeductAmount.compareTo(oldRoomRemainDepositAmount) > 0){
    				remainDeductAmount = remainDeductAmount.subtract(oldRoomRemainDepositAmount);
    				oldRoomRemainDepositAmount = FDCHelper.ZERO;
    			}else{
    				oldRoomRemainDepositAmount = oldRoomRemainDepositAmount.subtract(remainDeductAmount);
    				remainDeductAmount = FDCHelper.ZERO;
    			}
    			oldTenRoom.setRemainDepositAmount(oldRoomRemainDepositAmount);
    			TenancyRoomEntryFactory.getLocalInstance(ctx).updatePartial(oldTenRoom, sels);
    		}
    	}
    	
    	oldTenBill.setRemainDepositAmount(tenRemainDepositAmount.subtract(deductAmount));
    	TenancyBillFactory.getLocalInstance(ctx).updatePartial(oldTenBill, sels);
	}
    
    /**
     * 把存在单元格对象的值取出来分解，用于出租统计报表和出租汇总表
     * */
    public static Object[] getCellValue(Map map,int tenCount,BigDecimal tenArea)
	{
    	int count ;
    	BigDecimal area = new BigDecimal(0);
    	if(map.get(TenancyReportParameter.REPORT_CELL_COUNT)!=null)
    	{
    		 count = ((Integer)map.get(TenancyReportParameter.REPORT_CELL_COUNT)).intValue();
    	}else
    	{
    		count = 0;
    	}
		
		tenCount = tenCount+count;
		if(map.get(TenancyReportParameter.REPORT_CELL_AREA)!=null)
		{
			area = (BigDecimal)map.get(TenancyReportParameter.REPORT_CELL_AREA);
		}
		
		tenArea = tenArea.add(area);
		return new Object[]{new Integer(tenCount),tenArea};
	}
    
    /**
     * 在设置套数的时候把面积也同时显示，用于出租统计报表和出租汇总表
     * */
    public static String setCellValue(Integer tenCount,BigDecimal tenArea,Map map)
    {
    	tenArea = tenArea==null?new BigDecimal(0):tenArea;
    	if(tenCount.equals(new Integer(0)) || tenArea.equals(new BigDecimal(0)))
    	{
    		return null;
    	}else
    	{
    		StringBuffer strBuffer = new StringBuffer();
        	strBuffer.append(tenCount).append(TenancyReportParameter.REPORT_CELL_JOIN).
    		append(tenArea.divide(new BigDecimal(1), 2,BigDecimal.ROUND_HALF_UP)).append(TenancyReportParameter.REPORT_CELL_UNIT);
        	map.put(TenancyReportParameter.REPORT_CELL_COUNT, tenCount);
        	map.put(TenancyReportParameter.REPORT_CELL_AREA, tenArea);
        	return strBuffer.toString();	
    	}	
    } 
    
    //查找一个合同房间的已付金额
    public static BigDecimal getRecAmount(String roomid,String tenancyBillID) throws BOSException, SQLException
    {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select sum(fdcEntry.famount) sumRent from t_she_fdcreceivebillentry fdcEntry ");
		builder.appendSql(" left join t_cas_receivingbill cas on cas.fid=fdcentry.FReceivingBillID ");
		builder.appendSql(" left join T_SHE_FDCReceiveBill fdc on cas.FFDCReceivebillid=fdc.fid ");
		builder.appendSql(" left join t_she_room room on fdc.froomid=room.fid ");
		builder.appendSql(" left join t_ten_tenancybill tenbill on fdc.FTenancyContractID = tenbill.fid ");
		builder.appendSql(" left join t_she_moneyDefine money on fdcentry.FMoneyDefineId=money.fid ");
		builder.appendSql(" where  tenbill.fid ='" + tenancyBillID + "' and room.fid ='" + roomid + "'");
		builder.appendSql(" and (money.FMoneyType ='RentAmount' or money.FMoneyType ='DepositAmount') and money.FSysType ='TenancySys'");

		IRowSet set = null;
		BigDecimal sumRent = new BigDecimal(0);
		set = SQLExecutorFactory.getRemoteInstance(builder.getSql()).executeSQL();
		while (set.next()) {
			sumRent = set.getBigDecimal("sumRent");
		}
		return sumRent;
	}
    
  //查找一个合同配套资源的已付金额
    public static BigDecimal getRecAttachAmount(String attachid,String tenancyBillID) throws BOSException, SQLException
    {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select sum(fdcEntry.famount) sumRent from t_she_fdcreceivebillentry fdcEntry ");
		builder.appendSql(" left join t_cas_receivingbill cas on cas.fid=fdcentry.FReceivingBillID ");
		builder.appendSql(" left join T_SHE_FDCReceiveBill fdc on cas.FFDCReceivebillid=fdc.fid ");
		builder.appendSql(" left join t_ten_TenAttachResourceEntry tenEntry on fdc.ftenAttachID=tenEntry.fid ");
		builder.appendSql(" left join t_ten_attachResource attach on tenEntry.FAttachResourceID=attach.fid ");
		builder.appendSql(" left join t_ten_tenancybill tenbill on fdc.FTenancyContractID = tenbill.fid ");
		builder.appendSql(" left join t_she_moneyDefine money on fdcentry.FMoneyDefineId=money.fid ");
		builder.appendSql(" where  tenbill.fid ='" + tenancyBillID + "' and attach.fid ='" + attachid + "'");
		builder.appendSql(" and (money.FMoneyType ='RentAmount' or money.FMoneyType ='DepositAmount') and money.FSysType ='TenancySys'");

		IRowSet set = null;
		BigDecimal sumRent = new BigDecimal(0);
		set = SQLExecutorFactory.getRemoteInstance(builder.getSql()).executeSQL();
		while (set.next()) {
			sumRent = set.getBigDecimal("sumRent");
		}
		return sumRent;
	}
    
	/**
	 * 获得租期集合
	 * */
	public static List getLeaseList(Date startDate, Date endDate, FirstLeaseTypeEnum firstLeaseType, Date firstLeaseEndDate, int leaseTime) {
		if (startDate == null || endDate == null) {
			return null;
		}
		startDate = FDCDateHelper.getDayBegin(startDate);
		endDate = FDCDateHelper.getDayBegin(endDate);

		List leaseList = new ArrayList();
		
		if (FirstLeaseTypeEnum.UserDefined.equals(firstLeaseType)) {
			if (firstLeaseEndDate != null && !firstLeaseEndDate.before(startDate)) {
				firstLeaseEndDate = FDCDateHelper.getDayBegin(firstLeaseEndDate);
				Date[] firstLease = new Date[] { startDate, firstLeaseEndDate };
				List tmpMonthList = new ArrayList();
				tmpMonthList.add(firstLease);

				leaseList.add(tmpMonthList);
				startDate = TenancyHelper.addCalendar(firstLeaseEndDate, Calendar.DATE, 1);
			}
		}
		List monthList = TenancyHelper.getMonthList(startDate, endDate);

		if (monthList == null || monthList.isEmpty()) {
			return leaseList;
		}

		// 这种情况其实不可能出现
		if (leaseTime <= 0) {
			return leaseList;
		}
		leaseList.addAll(TenancyHelper.getLeaseList(monthList, leaseTime));
		return leaseList;
	}
    
	public static List getLeaseList(List monthList, int leaseTime){
		if(leaseTime <= 0){
			return null;
		}
		List leaseList = new ArrayList();
		int monthSize = monthList.size();
		for(int i=0; i<monthSize; i = i+leaseTime){
			int tmp = i + leaseTime;
			
			List monthListPerLease = new ArrayList();
			for(int j=i; j<tmp && j<monthSize; j++){
				Date[] month = (Date[]) monthList.get(j);
				monthListPerLease.add(month);
			}
			leaseList.add(monthListPerLease);
		}
		return leaseList;
	}
	
    /**
	 * 根据租期和租赁房间价格,将各房间付款明细注入到租赁房间中
	 * @param tenEntrys 可租赁的集合 <ITenancyEntryInfo>
	 * @param leaseList
	 *            租期集合
	 * @param rentFrees
	 *            免租分录集合
	 * @param digit 
	 * @param toIntegerType 
	 * @param isToInteger 
	 * @param daysPerYear 
	 * @param rentCountType 
	 * @throws BOSException
     * @throws IllegalAccessException 
     * @throws InstantiationException 
	 * */
	public static void fillTenEntryPayList(IObjectCollection tenEntrys, Class payColClazz, Class tenPayClazz, 
			MoneyDefineInfo depositMoney, MoneyDefineInfo rentMoney, 
			List leaseList, RentFreeEntryCollection rentFrees, RentCountTypeEnum rentCountType, int daysPerYear, 
			boolean isToInteger, ToIntegerTypeEnum toIntegerType, DigitEnum digit, boolean isToIntegerFee, ToIntegerTypeEnum toIntegerTypeFee, DigitEnum digitFee,ChargeDateTypeEnum chargeDateType, int chargeOffsetDays
			,Date pkTenancyDate,Date dPickFromMonth,int spinLeaseTime) throws BOSException, InstantiationException, IllegalAccessException {
		if (tenEntrys == null  ||  tenEntrys.isEmpty()) {
			return;
		}
		
		//
		for (int i = 0; i < tenEntrys.size(); i++) {
			ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) tenEntrys.getObject(i);
			
			IObjectCollection dealAmounts = tenEntry.getDealAmounts_();
			IObjectCollection payList = tenEntry.getPayList();
			
			IObjectCollection newPayList = (IObjectCollection) payColClazz.newInstance();
			// 统计一个房间不包含押金的所有租金总合,提交时各付款明细的总合应和该值相等. 
			BigDecimal oneEntryTotalRent = FDCHelper.ZERO;
			int seq = 0;
			for (int j = 0; j < leaseList.size(); j++) {
				List monthes = (List) leaseList.get(j);// 月份集合

				Date[] firstMonth = (Date[]) monthes.get(0);// 该租期的首月
				Date[] lastMonth = (Date[]) monthes.get(monthes.size() - 1);// 该租期的最后1月
				
				// -------计算该房间该租期的租金---
				BigDecimal leaseRent = getRentOfThisLease(monthes, rentMoney, dealAmounts, rentFrees, rentCountType, daysPerYear);
				//取整
				leaseRent = SHEComHelper.getAmountAfterToInteger(leaseRent, isToInteger, toIntegerType, digit);
				// ---------------------
				
				if (leaseRent == null)
					leaseRent = FDCHelper.ZERO;
				
				int freeDaysOfThisLease = 0;// 该租期免租的天数
				oneEntryTotalRent = oneEntryTotalRent.add(leaseRent);
				
				if (j == 0) {// 如果是第一租期,可能会有多条 押金 和 首期租金，我们把押金记录存在成交租金分录可以从那里取
					ITenancyPayListInfo depositPay = null;
					ITenancyPayListInfo firstPay = null;
					for(int m=0;m<dealAmounts.size();m++)
					{
						IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmounts.getObject(m);
						if(MoneyTypeEnum.DepositAmount.equals(dealAmount.getMoneyDefine().getMoneyType()))
						{				
							depositPay = (ITenancyPayListInfo) tenPayClazz.newInstance();// 押金
							depositPay.setTenEntry(tenEntry);
							setRoomPayParam(depositPay, dealAmount.getMoneyDefine(), dealAmount.getAmount(), j + 1, seq++,
									firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays,pkTenancyDate,dPickFromMonth,spinLeaseTime,j);
							newPayList.addObject(depositPay);
						}
					}
					firstPay = (ITenancyPayListInfo) tenPayClazz.newInstance();
					firstPay.setTenEntry(tenEntry);
//					if (payList.size() > 1) {// 如果原本就有付款明细,则押金和首期租金肯定同时存在
//						// 这种处理方式主要是为了保留原有付款明细记录的ID值,使得最终提交认租单时,
//						// 对该条付款明细记录执行的是update操作
//						depositPay = (ITenancyPayListInfo) payList.getObject(0);// 押金
//						firstPay = (ITenancyPayListInfo) payList.getObject(1);// 首期租金
//					} else if(payList.size() == 1){
//						depositPay = (ITenancyPayListInfo) payList.getObject(0);// 押金
//						firstPay = (ITenancyPayListInfo) tenPayClazz.newInstance();
//						firstPay.setTenEntry(tenEntry);
//					} else{
//						depositPay = (ITenancyPayListInfo) tenPayClazz.newInstance();// 押金
//						firstPay = (ITenancyPayListInfo) tenPayClazz.newInstance();
//						depositPay.setTenEntry(tenEntry);
//						firstPay.setTenEntry(tenEntry);
//					}

					// if (!hasPayoff(roomDepositPay)) {//默认押金为该房间的成交租金
//					setRoomPayParam(roomDepositPay, getDepositMoneyDefine(), getRentPerLease(tenRoomEntry.getDealRentType(), tenRoomEntry.getDealRoomRent(), this.spinLeaseTime.getIntegerVlaue().intValue()), j + 1, seq++,
//							firstMonth[0], lastMonth[1], freeDaysOfThisLease);
//					setRoomPayParam(depositPay, depositMoney, tenEntry.getDepositAmount(), j + 1, seq++,
//							firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays);
//					 }
					// if (!hasPayoff(roomFirstPay)) {
					setRoomPayParam(firstPay, rentMoney, leaseRent, j + 1, seq++, firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays,pkTenancyDate,dPickFromMonth,spinLeaseTime,j);
					// }
					
					newPayList.addObject(firstPay);
				} else {
					ITenancyPayListInfo payEntry = null;
					if (j < payList.size() - 1) {
						payEntry = (ITenancyPayListInfo) payList.getObject(j + 1);
					} else {
						payEntry = (ITenancyPayListInfo) tenPayClazz.newInstance();
						payEntry.setTenEntry(tenEntry);
					}
					// 如果已经付清该明细,不修改该条付款明细
					// if (!hasPayoff(roomPayEntry)) {
					setRoomPayParam(payEntry, rentMoney, leaseRent, j + 1, seq++, firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays,null,dPickFromMonth,spinLeaseTime,j);
					// }
					newPayList.addObject(payEntry);
				}
				
				//如果存在周期性费用，则需要增加周期性费用的应收明细
				MoneyDefineInfo money = new MoneyDefineInfo();
				for(int k=0; k<dealAmounts.size(); k++){
					IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmounts.getObject(k);
					if(MoneyTypeEnum.PeriodicityAmount.equals(dealAmount.getMoneyDefine().getMoneyType())  
							&&  dealAmount.getAmount() != null 
							//&&  dealAmount.getAmount().compareTo(FDCHelper.ZERO) > 0 除掉这个判断，不然会造成生成的应收明细错位 xin_wang 2010.11.22
							){
						if(dealAmount.getMoneyDefine().equals(money))
						{
							continue;
						}else
						{
							money = dealAmount.getMoneyDefine();
						}
						ITenancyPayListInfo pPay = (ITenancyPayListInfo) tenPayClazz.newInstance();
						BigDecimal amount = getRentOfThisLease(monthes, dealAmount.getMoneyDefine(), dealAmounts, rentFrees, rentCountType, daysPerYear);
						//取整 eric_wang 2010.08.25
						amount = SHEComHelper.getAmountAfterToInteger(amount, isToIntegerFee, toIntegerTypeFee, digitFee);
//						BigDecimal amount = TenancyHelper.getRentBetweenDate(firstMonth[0], lastMonth[1], dealAmount.getRentType(), dealAmount.getAmount());
						//add by pu_zhang 2010-10-20
						if(j==0){// 如果是第一租期，填写租赁合同日期
							setRoomPayParam(pPay, dealAmount.getMoneyDefine(), amount, j + 1, seq++, firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays,pkTenancyDate,dPickFromMonth,spinLeaseTime,j);
						}else{// 如果是非第一租期，租赁合同日期为空
							setRoomPayParam(pPay, dealAmount.getMoneyDefine(), amount, j + 1, seq++, firstMonth[0], lastMonth[1], freeDaysOfThisLease, chargeDateType, chargeOffsetDays,null,dPickFromMonth,spinLeaseTime,j);
						}
						newPayList.addObject(pPay);	
					}
				}
			}
			payList.clear();
			payList.addObjectCollection(newPayList);
			tenEntry.setTotalRent(oneEntryTotalRent);
		}
	}
	
	/**
	 * 设置付款明细的部分属性
	 * @param roomPayEntry
	 * @param leaseRent 租金
	 * @param leaseSeq
	 * @param seq
	 * @param startDate
	 * @param endDate
	 * @param freeDaysOfThisLease
	 * @param Date pkTenancyDate  如果是第一期设置的时候租赁合同的业务日期（应收日期为租赁合同的业务日期），其它期为空（应收日期按照正常去走）
	 * @throws BOSException
	 * */
	private static void setRoomPayParam(ITenancyPayListInfo payEntry, MoneyDefineInfo moneyDefine, BigDecimal leaseRent, 
			int leaseSeq, int seq, Date startDate, Date endDate, int freeDaysOfThisLease, ChargeDateTypeEnum chargeDateType, int chargeOffsetDays,Date pkTenancyDate,Date dPickFromMonth,int spinLeaseTime,int j) throws BOSException {
		// roomPayEntry.setCurrency(currency);
		// roomPayEntry.setMoneyDefine(moneyDefine);
		payEntry.setAppAmount(leaseRent);
		payEntry.setLeaseSeq(leaseSeq);
		payEntry.setSeq(seq);
		payEntry.setStartDate(startDate);
		payEntry.setEndDate(endDate);
		//该字段现在无用了。
//		roomPayEntry.setFreeDays(freeDaysOfThisLease);

		payEntry.setMoneyDefine(moneyDefine);

		// 设置应收日期
		Date appPayDate = null;
		if(pkTenancyDate!=null){//第一期设置的时候租赁合同的业务日期（应收日期为租赁合同的业务日期）
			appPayDate=pkTenancyDate;
		}else{//非第一期,按照偏移去设置应收日期
			if (ChargeDateTypeEnum.BeforeStartDate.equals(chargeDateType)) {
				appPayDate = TenancyHelper.addCalendar(startDate, Calendar.DATE, -chargeOffsetDays);
			} else if (ChargeDateTypeEnum.AfterStartDate.equals(chargeDateType)) {
				appPayDate = TenancyHelper.addCalendar(startDate, Calendar.DATE, chargeOffsetDays);
			} else if (ChargeDateTypeEnum.BeforeEndDate.equals(chargeDateType)) {
				appPayDate = TenancyHelper.addCalendar(endDate, Calendar.DATE, -chargeOffsetDays);
			} else if (ChargeDateTypeEnum.AfterEndDate.equals(chargeDateType)) {
				appPayDate = TenancyHelper.addCalendar(endDate, Calendar.DATE, chargeOffsetDays);
			} else if (ChargeDateTypeEnum.FixedDate.equals(chargeDateType)&&dPickFromMonth!=null) {
				appPayDate = TenancyHelper.addCalendar(dPickFromMonth, Calendar.MONTH, spinLeaseTime*(j-1));
			} else {
				appPayDate = startDate;
			}
		}
		payEntry.setAppPayDate(appPayDate);
	}
	
	private static BigDecimal getRentOfThisLease(List monthes, MoneyDefineInfo rentMoney, IObjectCollection dealAmounts, RentFreeEntryCollection rentFrees, RentCountTypeEnum rentCountType, int daysPerYear) throws BOSException {
		Date[] firstMonth = (Date[]) monthes.get(0);
		Date[] lastMonth = (Date[]) monthes.get(monthes.size() - 1);
		return TenancyHelper.getRentBetweenDate(rentMoney, firstMonth[0], lastMonth[1], dealAmounts, rentFrees, rentCountType, daysPerYear);
	}
    
	/**
	 * 重置成交租金分录,并将成交租金分录注入到ITenancyEntry的分录中
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * */
	public static boolean reSetRentSetInfo(IObjectCollection tenEntrys,Map roomDisMap,IncreasedRentEntryCollection increasedRents, Date startDate, Date endDate, 
			MoneyDefineCollection periodicityMoneys, Class dealAmountColClazz, Class dealAmountClazz, MoneyDefineInfo rentMoney, 
			boolean isDynamicStartDate,Map mapPrice,Map mapRent,String status,boolean isFirstEdit,String oldState) throws BOSException, InstantiationException, IllegalAccessException {
		if(isDynamicStartDate  &&  startDate == null){
			//根据房间分录生成一条对应的成交租金分录
			for(int i=0; i<tenEntrys.size(); i++){
				ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) tenEntrys.getObject(i);
				RentTypeEnum currentRentType = tenEntry.getDealRentType();
				BigDecimal currentRent = tenEntry.getDealRent();
				TenancyModeEnum tenancyModel = tenEntry.getTenancyModel();
				BigDecimal area = tenEntry.getBuildingArea();
				
				//改  by zhendui_ai
//				if(TenancyModeEnum.RoomAreaMode.equals(tenancyModel)){
//					area = tenEntry.getRoomArea();
//				}
				if(area == null) area = FDCHelper.ZERO;
				
				IObjectCollection dealAmounts = (IObjectCollection) dealAmountColClazz.newInstance();
				IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmountClazz.newInstance();
				dealAmount.setTenancyEntry(tenEntry);
				dealAmount.setIsHandwork(false);
				dealAmount.setMoneyDefine(rentMoney);
				dealAmount.setStartDate(null);
				dealAmount.setEndDate(null);
				dealAmount.setRentType(currentRentType);
				dealAmount.setAmount(currentRent);
				if(currentRent != null){
					if(area != null  &&  area.compareTo(FDCHelper.ZERO) != 0){
						BigDecimal rentPrice = currentRent.divide(area, 2, BigDecimal.ROUND_HALF_UP);
						dealAmount.setPriceAmount(rentPrice);
					}
				}
				dealAmounts.addObject(dealAmount);
				tenEntry.getDealAmounts_().clear();
				tenEntry.getDealAmounts_().addObjectCollection(dealAmounts);
			}
		}else{
			//封装递增列表分录集合
			if (startDate == null  ||  endDate == null) {
				return false;
			}			
			startDate = FDCDateHelper.getDayBegin(startDate);
			endDate = FDCDateHelper.getDayBegin(endDate);
			
			//重新设置租金分录.
			Map tmp = parseIncreasedDate(startDate, endDate, increasedRents);
			//周期性费用递增
			Map perodictityMap = new HashMap();
			if(tmp == null){
				return false;
			}
			
			boolean boo = false;
			//根据房间分录和定价时间段生成租赁房间的成交租金分录
			for(int i=0; i<tenEntrys.size(); i++){
				ITenancyEntryInfo tenEntry = (ITenancyEntryInfo) tenEntrys.getObject(i);
				RentTypeEnum currentRentType = tenEntry.getDealRentType();
				BigDecimal currentRent = null;
				BigDecimal delaRentPrice = null;
				TenancyModeEnum tenancyModel = tenEntry.getTenancyModel();
				BigDecimal area = tenEntry.getBuildingArea();
				//改  by zhendui_ai
//				if(TenancyModeEnum.RoomAreaMode.equals(tenancyModel)){
//					area = tenEntry.getRoomArea();
//				}
				if(area == null) area = FDCHelper.ZERO;
				
				IObjectCollection dealAmounts = (IObjectCollection) dealAmountColClazz.newInstance();
				//当多个房间的时候如果马上isFirstEdit变为true的话。后面的房间就不在执行这句，后面房间的押金就取不出来
				if("VIEW".equals(status) || ("EDIT".equals(status) && !isFirstEdit && !"ADDNEW".equals(oldState))){					
					boo = true;
					for(int m=0;m<tenEntrys.size();m++)
					{
						TenancyRoomEntryInfo tenRoom = (TenancyRoomEntryInfo)tenEntrys.getObject(m);
						
						if(tenRoom==null){
							
							break;
						}else if(tenRoom.getId()==null || "".equalsIgnoreCase(String.valueOf(tenRoom.getId()))){
							break;
						}
						
						if(((TenancyRoomEntryInfo)tenEntry).getId()!=null&&tenEntry.getStrId().equals(tenRoom.getId().toString()))
						{
							dealAmounts.addObjectCollection(tenRoom.getDealAmounts());
						}
						
					}
				}else
				{
					//把租金设置的押金记录生成成交租金分录记录
					TenancyRoomEntryInfo tenRoom = (TenancyRoomEntryInfo)tenEntrys.getObject(i);
					DealAmountEntryCollection deals= tenRoom.getDealAmounts();
				
					
					DealAmountEntryCollection rentDealAount = new DealAmountEntryCollection();
					for(Iterator it = deals.iterator();it.hasNext();){
						DealAmountEntryInfo dealInfo = (DealAmountEntryInfo)it.next();
						if(MoneyTypeEnum.RentAmount.equals(dealInfo.getMoneyDefine().getMoneyType())){
							rentDealAount.add(dealInfo);
						}
					}
					
					if(roomDisMap.get(tenRoom.getRoom().getId().toString())!=null)
					{
						Map map = (Map)roomDisMap.get(tenRoom.getRoom().getId().toString());
						for(Iterator itor = map.keySet().iterator(); itor.hasNext();)
						{
							MoneyDefineInfo moneyInfo = (MoneyDefineInfo)itor.next();
							BigDecimal disAmount = (BigDecimal)map.get(moneyInfo);
							IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmountClazz.newInstance();
							dealAmount.setMoneyDefine(moneyInfo);
							dealAmount.setAmount(disAmount);
							dealAmounts.addObject(dealAmount);
						}
					}
					int z =0;
					for(Iterator itor = tmp.keySet().iterator(); itor.hasNext(); ){
						Date[] dates = (Date[]) itor.next();
						IncreasedRentEntryInfo increased = (IncreasedRentEntryInfo) tmp.get(dates);
						
						boolean isHandwork = true;
//						boolean isHand =false;
						if(increased == null){
							currentRent = tenEntry.getDealRent();
							delaRentPrice = tenEntry.getDealRentPrice();
							z++;
//							isHand = true;
						}else{
							if(IncreaseStyleEnum.rentAndPeriod.equals(increased.getIncreaseStyle()))
							{
								perodictityMap.put(dates, increased);
							}
							IncreasedRentTypeEnum increasedType = increased.getIncreaseType();
							BigDecimal value = increased.getValue();
							if(value == null){//手输的情况下该值为0
								value = FDCHelper.ZERO;
							}
							if(currentRent == null)
							{
								currentRent = FDCHelper.ZERO;
							}							
							if(IncreasedRentTypeEnum.Percent.equals(increasedType)){
								if(TenancyModeEnum.TenancyRentModel.equals(tenancyModel))
								{
									currentRent = currentRent.multiply(FDCHelper.ONE_HUNDRED.add(value)).divide(FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
									if(area != null  &&  area.compareTo(FDCHelper.ZERO) != 0){
										delaRentPrice = currentRent.divide(area, 2, BigDecimal.ROUND_HALF_UP);
									}									
								}else
								{
//									delaRentPrice = delaRentPrice.multiply(FDCHelper.ONE_HUNDRED.add(value)).divide(FDCHelper.ONE_HUNDRED);
									delaRentPrice = FDCHelper.divide(FDCHelper.multiply(delaRentPrice, FDCHelper.add(FDCHelper.ONE_HUNDRED, value)), FDCHelper.ONE_HUNDRED);
									currentRent = delaRentPrice.multiply(area).divide(FDCHelper.ONE, 2, BigDecimal.ROUND_HALF_UP);
								}
								
								isHandwork = false;
							}else if(IncreasedRentTypeEnum.totalRent.equals(increasedType)){
//								if(){//如果租金统一设置  xin_wang 2010.12.29
//									
//								}else{
									currentRent = currentRent.add(value);
									if(area != null  &&  area.compareTo(FDCHelper.ZERO) != 0){
										delaRentPrice = currentRent.divide(area, 2, BigDecimal.ROUND_HALF_UP);
//									}	
								}
								isHandwork = false;
							}else if(IncreasedRentTypeEnum.rentPrice.equals(increasedType)){
								delaRentPrice = delaRentPrice.add(value);
								currentRent = delaRentPrice.multiply(area);
//								currentRent = currentRent.add(value.multiply(area));
//								delaRentPrice = currentRent.divide(area, 2, BigDecimal.ROUND_HALF_UP);
								isHandwork = false;
							}else if(IncreasedRentTypeEnum.Handwork.equals(increasedType)){
								//手工填写的，默认租金为原租金
								//这个逻辑还有待测试  比较别扭！ xin_wang 2010.12.29
								if(rentDealAount.size()>0){
									if(rentDealAount.getObject(z)!=null){
										delaRentPrice =	((DealAmountEntryInfo)rentDealAount.getObject(z)).getPriceAmount();
										currentRent   =	((DealAmountEntryInfo)rentDealAount.getObject(z)).getAmount();
									}
									z++;
								}
							}
						}
						
						IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmountClazz.newInstance();
						dealAmount.setTenancyEntry(tenEntry);
						dealAmount.setIsHandwork(isHandwork);
						dealAmount.setMoneyDefine(rentMoney);
						dealAmount.setStartDate(dates[0]);
						dealAmount.setEndDate(dates[1]);
						dealAmount.setRentType(currentRentType);
						dealAmount.setAmount(currentRent);
						dealAmount.setPriceAmount(delaRentPrice);
//						if(currentRent != null){
//							if(area != null  &&  area.compareTo(FDCHelper.ZERO) != 0){
//								BigDecimal rentPrice = currentRent.divide(area, 2, BigDecimal.ROUND_HALF_UP);
//								dealAmount.setPriceAmount(rentPrice);
//							}
//						}
						
						dealAmounts.addObject(dealAmount);
					}
					
					if(perodictityMap.size()>0)
					{
						String moneyName = null;
						int m =0;
						for(int j=0; j<periodicityMoneys.size(); j++){
							
							MoneyDefineInfo money = (MoneyDefineInfo)periodicityMoneys.get(j);
							String name = money.getName();
							BigDecimal rent = new BigDecimal(0);
							BigDecimal rentPrice = new BigDecimal(0);
							//周期性费用递增列表。因为多条递增分录可能周期性费用和租金都递增也可能只递增租金所以在这不能
							//直接用上面租金递增，在这需要重新取下值
							Map tmp2 = parseIncreasedDate2(startDate, endDate, increasedRents);
							for(Iterator itor = tmp2.keySet().iterator(); itor.hasNext(); ){
								//因为递增会把时间分段，当有多个周期性费用递增时用时间无法和保存时的时间一致
								//所以用款项类型加上第几行第几列来判断才是唯一的
								Date[] dates = (Date[]) itor.next();					
//								String str = getDateDes(name,dates[0],dates[1]);								
								if(!name.equals(moneyName))
								{
									m=0;
								}else
								{
									m++;
								}
								
								if(TenancyModeEnum.TenancyRentModel.equals(tenancyModel))
								{
									moneyName = name;
									StringBuffer nameBuff = new StringBuffer(name);
									nameBuff.append(i);			
									nameBuff.append(m);	
									if(mapRent.get(nameBuff.toString())!=null)
									{
										rent = (BigDecimal)mapRent.get(nameBuff.toString());
										rentPrice = rent.divide(area,2,BigDecimal.ROUND_HALF_UP);
									}
								}else
								{
									moneyName = name;
									moneyName = moneyName+"price";
									StringBuffer nameBuff = new StringBuffer(moneyName);
									nameBuff.append(i);			
									nameBuff.append(m);	
									rentPrice = (BigDecimal)mapRent.get(nameBuff.toString());
									rentPrice = rentPrice==null?FDCHelper.ZERO:rentPrice;
									rent = rentPrice.multiply(area);
								}
								
								boolean isHandwork = true;
								IncreasedRentEntryInfo increased = (IncreasedRentEntryInfo) tmp2.get(dates);
								
								if(increased == null){
									currentRent = rent;
									delaRentPrice = rentPrice;
								}
								else{						
									IncreasedRentTypeEnum increasedType = increased.getIncreaseType();
									BigDecimal value = increased.getValue();
									if(value == null){//手输的情况下该值为0
										value = FDCHelper.ZERO;
									}
									
									if(IncreasedRentTypeEnum.Percent.equals(increasedType)){
										if(TenancyModeEnum.TenancyRentModel.equals(tenancyModel))
										{
											currentRent = currentRent.multiply(FDCHelper.ONE_HUNDRED.add(value)).divide(FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
											delaRentPrice = currentRent.divide(area, 2, BigDecimal.ROUND_HALF_UP);
										}else
										{
//											rentPrice = rentPrice.multiply(FDCHelper.ONE_HUNDRED.add(value)).divide(FDCHelper.ONE_HUNDRED); 以下是由于构建失败，兰远军代为修改
											delaRentPrice = FDCHelper.divide(FDCHelper.multiply(delaRentPrice, FDCHelper.add(FDCHelper.ONE_HUNDRED, value)), FDCHelper.ONE_HUNDRED);
											currentRent = delaRentPrice.multiply(area).divide(FDCHelper.ONE, 2, BigDecimal.ROUND_HALF_UP);
										}
//										currentRent = currentRent.multiply(FDCHelper.ONE_HUNDRED.add(value)).divide(FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
										isHandwork = false;
									}else if(IncreasedRentTypeEnum.totalRent.equals(increasedType)){
										currentRent = currentRent.add(value);
										delaRentPrice = currentRent.divide(area, 2, BigDecimal.ROUND_HALF_UP);
										isHandwork = false;
									}else if(IncreasedRentTypeEnum.rentPrice.equals(increasedType)){
										delaRentPrice = delaRentPrice.add(value);
										currentRent = delaRentPrice.multiply(area);
//										currentRent = currentRent.add(value.multiply(area));
//										delaRentPrice = currentRent.divide(area, 2, BigDecimal.ROUND_HALF_UP);
										isHandwork = false;
									}else if(IncreasedRentTypeEnum.Handwork.equals(increasedType)){
										//手工填写的，默认租金为原租金						
									}
								}
								IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmountClazz.newInstance();
								dealAmount.setIsHandwork(isHandwork);
								dealAmount.setTenancyEntry(tenEntry);
								dealAmount.setIsHandwork(isHandwork);
								dealAmount.setMoneyDefine(periodicityMoneys.get(j));
								dealAmount.setStartDate(dates[0]);
								dealAmount.setEndDate(dates[1]);
								dealAmount.setRentType(currentRentType);
								dealAmount.setAmount(currentRent);
								dealAmount.setPriceAmount(delaRentPrice);
//								if(currentRent != null){
//									if(area != null  &&  area.compareTo(FDCHelper.ZERO) != 0){
//										BigDecimal rentPrice2 = currentRent.divide(area, 2, BigDecimal.ROUND_HALF_UP);
//										dealAmount.setPriceAmount(rentPrice2);
//									}
//								}
								dealAmounts.addObject(dealAmount);
							}					
						}
					}else
					{
						String moneyName = null;
						int m =0;
						for(int j=0; j<periodicityMoneys.size(); j++){
							IDealAmountInfo dealAmount = (IDealAmountInfo) dealAmountClazz.newInstance();
							MoneyDefineInfo money = (MoneyDefineInfo)periodicityMoneys.get(j);
							String name = money.getName();
							//String str = getDateDes(name,startDate,endDate);
							StringBuffer nameBuff = new StringBuffer(name);
							if(!name.equals(moneyName))
							{
								m=0;
							}else
							{
								m++;
							}
							moneyName = name;
							nameBuff.append(i);
							nameBuff.append(m);
							if(mapRent.get(nameBuff.toString())!=null)
							{
								currentRent = (BigDecimal)mapRent.get(nameBuff.toString());
							}else
							{
								currentRent = new BigDecimal(0);
							}
							dealAmount.setTenancyEntry(tenEntry);
							dealAmount.setIsHandwork(true);
							dealAmount.setMoneyDefine(periodicityMoneys.get(j));
							dealAmount.setStartDate(startDate);
							dealAmount.setEndDate(endDate);
							dealAmount.setRentType(currentRentType);
							dealAmount.setAmount(currentRent);
							if(currentRent != null){
								if(area != null  &&  area.compareTo(FDCHelper.ZERO) != 0){
									BigDecimal rentPrice = currentRent.divide(area, 2, BigDecimal.ROUND_HALF_UP);
									dealAmount.setPriceAmount(rentPrice);
								}
							}
							dealAmounts.addObject(dealAmount);
						}
					}
				}
												
				tenEntry.getDealAmounts_().clear();
				tenEntry.getDealAmounts_().addObjectCollection(dealAmounts);
			}
			if(boo)
			{
				isFirstEdit = true;
			}
		}
		return isFirstEdit;
	}
	
	private static String getDateDes(String moneyName, Date startDate, Date endDate) {
		String dateDes = null;
		if(startDate == null  ||  endDate == null){
			return null;
		}
		if(startDate.equals(endDate)){
			dateDes = FDCDateHelper.DateToString(startDate);
		}else{
			dateDes = FDCDateHelper.DateToString(startDate) + "至" + FDCDateHelper.DateToString(endDate);
		}
		return moneyName + "(" + dateDes + ")";
	}
	
	/**
	 * 根据起始日期,结束日期以及递增日期.以Map形式将时间分段.
	 * 时间分段为key,每个段内的递增信息为value.无递增方案的value为null
	 * */
	private static Map parseIncreasedDate(Date startDate, Date endDate, IncreasedRentEntryCollection increasedRents) {
		if (startDate == null  ||  endDate == null) {
			return null;
		}
		
		Date firstPriceSetDate = FDCDateHelper.getDayBegin(startDate);
		Date lastPriceSetDate = FDCDateHelper.getDayBegin(endDate);
		
		Map increadeDates = new TreeMap();
		for (int i = 0; i < increasedRents.size(); i++) {
			IncreasedRentEntryInfo increasedRent = increasedRents.get(i);
			
			//上面已经判断过，increasedRent.getIncreaseDate()肯定不为空
			Date increaseDate = FDCDateHelper.getDayBegin(increasedRent.getIncreaseDate());
			
			if(increaseDate.before(firstPriceSetDate) || increaseDate.after(lastPriceSetDate)){
				//递增日期不在租赁日期范围内，舍去该数据。 TODO 是否需要提示数据不对
				continue;
			}
			increadeDates.put(increaseDate, increasedRent);
		}
		
		//将定价期分块，分别表示各个时间段的租价
		Map tmp = new LinkedHashMap();
		if(increadeDates.isEmpty()){
			tmp.put(new Date[]{firstPriceSetDate, lastPriceSetDate}, null);
		}else{
			Set keySet = increadeDates.keySet();
			boolean containsStartDate = keySet.contains(firstPriceSetDate);
			int count = 0;
			Date tmpLastDate = firstPriceSetDate;
			IncreasedRentEntryInfo tmpLastIncreasedRent = null; 
			for(Iterator itor = keySet.iterator(); itor.hasNext(); ){
				Date date = (Date) itor.next();
				
				Date tmpEndDate = TenancyHelper.addCalendar(date, Calendar.DATE, -1);
				if(!containsStartDate  &&  count == 0){
					tmp.put(new Date[]{firstPriceSetDate, tmpEndDate}, null);
				}else{
					tmp.put(new Date[]{tmpLastDate, tmpEndDate}, tmpLastIncreasedRent);
				}
				
				tmpLastIncreasedRent = (IncreasedRentEntryInfo) increadeDates.get(date);
				tmpLastDate = date;
				count++;
			}
			tmp.put(new Date[]{tmpLastDate, lastPriceSetDate}, tmpLastIncreasedRent);
		}
		return tmp;
	}
	
	
	/**
	 * 根据起始日期,结束日期以及递增日期.以Map形式将时间分段.
	 * 时间分段为key,每个段内的递增信息为value.无递增方案的value为null
	 * */
	private static Map parseIncreasedDate2(Date startDate, Date endDate, IncreasedRentEntryCollection increasedRents) {
		if (startDate == null  ||  endDate == null) {
			return null;
		}
		
		Date firstPriceSetDate = FDCDateHelper.getDayBegin(startDate);
		Date lastPriceSetDate = FDCDateHelper.getDayBegin(endDate);
		
		Map increadeDates = new TreeMap();
		for (int i = 0; i < increasedRents.size(); i++) {
			IncreasedRentEntryInfo increasedRent = increasedRents.get(i);
			if(IncreaseStyleEnum.rentAndPeriod.equals(increasedRent.getIncreaseStyle()))
			{
				//上面已经判断过，increasedRent.getIncreaseDate()肯定不为空
				Date increaseDate = FDCDateHelper.getDayBegin(increasedRent.getIncreaseDate());
				
				if(increaseDate.before(firstPriceSetDate) || increaseDate.after(lastPriceSetDate)){
					//递增日期不在租赁日期范围内，舍去该数据。 TODO 是否需要提示数据不对
					continue;
				}
				increadeDates.put(increaseDate, increasedRent);
			}			
		}
		
		//将定价期分块，分别表示各个时间段的租价
		Map tmp = new LinkedHashMap();
		if(increadeDates.isEmpty()){
			tmp.put(new Date[]{firstPriceSetDate, lastPriceSetDate}, null);
		}else{
			Set keySet = increadeDates.keySet();
			boolean containsStartDate = keySet.contains(firstPriceSetDate);
			int count = 0;
			Date tmpLastDate = firstPriceSetDate;
			IncreasedRentEntryInfo tmpLastIncreasedRent = null; 
			for(Iterator itor = keySet.iterator(); itor.hasNext(); ){
				Date date = (Date) itor.next();
				
				Date tmpEndDate = TenancyHelper.addCalendar(date, Calendar.DATE, -1);
				if(!containsStartDate  &&  count == 0){
					tmp.put(new Date[]{firstPriceSetDate, tmpEndDate}, null);
				}else{
					tmp.put(new Date[]{tmpLastDate, tmpEndDate}, tmpLastIncreasedRent);
				}
				
				tmpLastIncreasedRent = (IncreasedRentEntryInfo) increadeDates.get(date);
				tmpLastDate = date;
				count++;
			}
			tmp.put(new Date[]{tmpLastDate, lastPriceSetDate}, tmpLastIncreasedRent);
		}
		return tmp;
	}
	
	/**
	 * 将收款记录展示在table中 目前供销控表和认购单的实付页签调用
	 * 修改了表格的合并逻辑 以及增加了合同编码 以及 收款单状态 字段 eirc_wang 2010.08.24
	 * */
	public static void fillActPayTable(KDTable table, FDCReceivingBillEntryCollection fdcRevEntrycoll, Class revEditUIClazz)  {
		if (table == null) {
			return;
		}
		if(revEditUIClazz == null) revEditUIClazz = TENReceivingBillEditUI.class;
		initActPayTable(table, revEditUIClazz);
		int rowCount = 0;
		String oldId ="";
		
		IRow row =null;
		int continueCount  =0;
		//处理每张收款单明细
		for (int j = 0; j < fdcRevEntrycoll.size(); j++) {
			
			FDCReceivingBillEntryInfo fdcRevEntry = fdcRevEntrycoll.get(j);
			
			if(RevBizTypeEnum.manage.equals(fdcRevEntry.getHead().getRevBizType())){
				continueCount++;
				continue;
			}else{
				row = table.addRow();
			}
			if(oldId.equals(fdcRevEntry.getHead().getId().toString())){
				//记录进来了几次
				rowCount++;
				row.setUserObject(fdcRevEntry);
				//单据状态
				if(row.getCell("state")!=null){
					row.getCell("state").setValue(fdcRevEntry.getHead().getBillStatus());
				}
				if(row.getCell("contractNumber")!=null){
						row.getCell("contractNumber").setValue(fdcRevEntry.getHead().getTenancyObj().getNumber());
				}
				
				row.getCell("date").setValue(fdcRevEntry.getHead().getBizDate());
				row.getCell("currency").setValue(fdcRevEntry.getHead().getCurrency());			
				FDCReceivingBillInfo fdcReceivingInfo =  fdcRevEntry.getHead();
				
				row.getCell("customer").setValue(fdcReceivingInfo.getCustomer().getName());
				row.getCell("receiver").setValue(fdcRevEntry.getHead().getCreator().getName());
				row.getCell("moneyName").setValue(fdcRevEntry.getMoneyDefine() == null ? null : fdcRevEntry.getMoneyDefine().getName());
				row.getCell("amount").setValue(fdcRevEntry.getRevAmount());
				//总行数 等于 分录数-跳出的次数时 就不能在下面做合并行了
				if(table.getRowCount()==(fdcRevEntrycoll.size()-continueCount)){
					table.getMergeManager().mergeBlock(row.getRowIndex()-rowCount, 0, row.getRowIndex(), 0);
					table.getMergeManager().mergeBlock(row.getRowIndex()-rowCount, 1, row.getRowIndex(), 1);
					table.getMergeManager().mergeBlock(row.getRowIndex()-rowCount, 4, row.getRowIndex(), 4);
					table.getMergeManager().mergeBlock(row.getRowIndex()-rowCount, 5, row.getRowIndex(), 5);
					table.getMergeManager().mergeBlock(row.getRowIndex()-rowCount, 6, row.getRowIndex(), 6);
					table.getMergeManager().mergeBlock(row.getRowIndex()-rowCount, 7, row.getRowIndex(), 7);
				}
				
			}else{
				//rowCount>0出现了同一张收款单的明细  需要合并
				if(rowCount>0){
					table.getMergeManager().mergeBlock(row.getRowIndex()-rowCount-1, 0, row.getRowIndex()-1, 0);
					table.getMergeManager().mergeBlock(row.getRowIndex()-rowCount-1, 1, row.getRowIndex()-1, 1);
					table.getMergeManager().mergeBlock(row.getRowIndex()-rowCount-1, 4, row.getRowIndex()-1, 4);
					table.getMergeManager().mergeBlock(row.getRowIndex()-rowCount-1, 5, row.getRowIndex()-1, 5);
					table.getMergeManager().mergeBlock(row.getRowIndex()-rowCount-1, 6, row.getRowIndex()-1, 6);
					table.getMergeManager().mergeBlock(row.getRowIndex()-rowCount-1, 7, row.getRowIndex()-1, 7);
				}
				rowCount =0;
				row.setUserObject(fdcRevEntry);
				//单据状态
				if(row.getCell("state")!=null){
					row.getCell("state").setValue(fdcRevEntry.getHead().getBillStatus());
				}
				if(row.getCell("contractNumber")!=null){
						row.getCell("contractNumber").setValue(fdcRevEntry.getHead().getTenancyObj().getNumber());				
				}
				
				row.getCell("date").setValue(fdcRevEntry.getHead().getBizDate());
				row.getCell("currency").setValue(fdcRevEntry.getHead().getCurrency());			
				FDCReceivingBillInfo fdcReceivingInfo =  fdcRevEntry.getHead();
				
				row.getCell("customer").setValue(fdcReceivingInfo.getCustomer().getName());
				row.getCell("receiver").setValue(fdcRevEntry.getHead().getCreator().getName());
				row.getCell("moneyName").setValue(fdcRevEntry.getMoneyDefine() == null ? null : fdcRevEntry.getMoneyDefine().getName());
				row.getCell("amount").setValue(fdcRevEntry.getRevAmount());
				//变旧ID
				oldId=fdcRevEntry.getHead().getId().toString();
			}
			
			
		}
//		KDTSortManager sortManager =null;
//		sortManager =table.getSortMange();
//		sortManager.sort(0);
	
	}
	
	private static void initActPayTable(final KDTable table, final Class revEditUIClazz) {
		table.getStyleAttributes().setLocked(true);
		table.removeColumns();
		table.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		IColumn column = table.addColumn();
		//如果进来的是租赁系统，即加上 合同编号，收款单的状态字段
		if(TENReceivingBillEditUI.class.equals(revEditUIClazz)){
			column.setKey("state");
			column = table.addColumn();
		}
		
		column.setKey("date");
		String formatString = "yyyy-MM-dd";
		column.getStyleAttributes().setNumberFormat(formatString);
		column = table.addColumn();
		column.setKey("moneyName");
		column = table.addColumn();
		column.setKey("amount");
		column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		column = table.addColumn();
		column.setKey("customer");
		column = table.addColumn();
		column.setKey("currency");
		column = table.addColumn();
		column.setKey("receiver");
		if(TENReceivingBillEditUI.class.equals(revEditUIClazz)){
			column = table.addColumn();
			column.setKey("contractNumber");
		}

		// column = table.addColumn();
		// column.setKey("salesman");
		IRow headRow = table.addHeadRow();
		headRow.getCell("state").setValue("状态");
		headRow.getCell("date").setValue("收款日期");
		headRow.getCell("moneyName").setValue("款项名称");
		headRow.getCell("amount").setValue("金额");
		headRow.getCell("currency").setValue("币别");
		headRow.getCell("customer").setValue("客户");
		headRow.getCell("receiver").setValue("制单人");
		headRow.getCell("contractNumber").setValue("合同编号");
		// headRow.getCell("salesman").setValue("销售顾问");

		table.addKDTMouseListener(new KDTMouseListener() {
			public void tableClicked(KDTMouseEvent e) {
				try {
					if (e.getButton() == 1 && e.getClickCount() == 2) {
						int rowIndex = e.getRowIndex();
						if (rowIndex == -1) {
							return;
						}
						IRow row = table.getRow(rowIndex);
						FDCReceivingBillEntryInfo fdcRevEntry = (FDCReceivingBillEntryInfo) row.getUserObject();
						UIContext uiContext = new UIContext(this);
						uiContext.put("ID", fdcRevEntry.getHead().getId());
						IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(revEditUIClazz.getName(), uiContext, null, "VIEW");
						uiWindow.show();
					}
				} catch (Exception exc) {
					ExceptionHandler.handle(exc);
				} finally {
				}
			}
		});
	}
	
	//根据收款业务类型和收款单据类型确定合同状态
	public static Set getQueryBillStates(RevBizTypeEnum bizType,RevBillTypeEnum revBillType) {
		Set tempSet = new HashSet();
		if(RevBizTypeEnum.tenancy.equals(bizType))
		{
			//退款和收款，所取得合同不一样 	
			if(RevBillTypeEnum.refundment.equals(revBillType))
			{
				//终止和作废合同
				tempSet.add(TenancyBillStateEnum.BLANKOUT_VALUE);
				tempSet.add(TenancyBillStateEnum.EXPIRATION_VALUE);
			}
			else if(revBillType == null ||RevBillTypeEnum.gathering.equals(revBillType))
			{
				tempSet.add(TenancyBillStateEnum.AUDITED_VALUE);
//				tempSet.add(TenancyBillStateEnum.DEPOSITREVED_VALUE);
				tempSet.add(TenancyBillStateEnum.PARTEXECUTED_VALUE);
				tempSet.add(TenancyBillStateEnum.EXECUTING_VALUE);
				tempSet.add(TenancyBillStateEnum.EXPIRATION_VALUE);
				
				tempSet.add(TenancyBillStateEnum.CONTINUETENANCYING_VALUE);
				tempSet.add(TenancyBillStateEnum.REJIGGERTENANCYING_VALUE);
				tempSet.add(TenancyBillStateEnum.CHANGENAMING_VALUE);
				tempSet.add(TenancyBillStateEnum.QUITTENANCYING_VALUE);
			}
		}else if(RevBizTypeEnum.obligate.equals(bizType))
		{
			if(RevBillTypeEnum.refundment.equals(revBillType))
			{
				//取消预留和作废以及已正式认租的预留单(已正式认租的预留单既可以转也可以退)
				tempSet.add(BizStateEnum.INVALID_VALUE);
				tempSet.add(BizStateEnum.CANCEL_VALUE);
				tempSet.add(BizStateEnum.TENANCY_VALUE);
				tempSet.add(BizStateEnum.LEASE_VALUE);
			}else if(revBillType == null ||RevBillTypeEnum.gathering.equals(revBillType))
			{
				//已审批和已预留的预留单才能收款
				tempSet.add(BizStateEnum.AUDITTED_VALUE);
				tempSet.add(BizStateEnum.SINCEROBLIGATED_VALUE);
			}
		}		
		return tempSet;
	}
	
	/**
	 * 获取该房间下可以进行收款操作的合同
	 * @param room
	 * @param billStateSet 合同的状态
	 * @return
	 * @throws BOSException
	 */
	public static TenancyBillCollection getValidTenancyContractByRoomAndState(RoomInfo room, Set billStateSet) throws BOSException
	{
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("tenancy.id");
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("room",room.getId().toString()));

		if(billStateSet != null && billStateSet.size() > 0)
		{
			filter.getFilterItems().add(new FilterItemInfo("tenancy.tenancyState",billStateSet,CompareType.INCLUDE));
		}
		TenancyRoomEntryCollection tenancyRoomEntryColl = TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryCollection(view);
		
		Set tenIds = new HashSet();
		for(int i =0; i < tenancyRoomEntryColl.size(); i ++)
		{
			tenIds.add(tenancyRoomEntryColl.get(i).getTenancy().getId().toString());
		}
		
		EntityViewInfo tenView = new EntityViewInfo();
		FilterInfo tenFilter = new FilterInfo();
		tenView.setFilter(tenFilter);
		tenFilter.getFilterItems().add(new FilterItemInfo("id", tenIds, CompareType.INCLUDE));
		tenView.getSelector().add("name");
		tenView.getSelector().add("number");
		tenView.getSelector().add("tenancyAdviser.name");
		tenView.getSelector().add("tenancyAdviser.number");
		tenView.getSelector().add("tenCustomerList.sysCustomer.name");
		tenView.getSelector().add("tenCustomerList.fdcCustomer.name");
		tenView.getSelector().add("tenCustomerList.fdcCustomer.number");
		tenView.getSelector().add("tenCustomerList.fdcCustomer.sysCustomer.name");
		tenView.getSelector().add("tenancyRoomList");
		SorterItemCollection coll = new SorterItemCollection();
		SorterItemInfo sorter = new SorterItemInfo("tenancyDate");
		sorter.setSortType(SortType.DESCEND);
		coll.add(sorter);
		tenView.setSorter(coll);
		return TenancyBillFactory.getRemoteInstance().getTenancyBillCollection(tenView);
	}
	
	/**
	 * 获取该房间下可以进行收款操作的预留单
	 * @param room
	 * @param billStateSet 合同的状态
	 * @return
	 * @throws BOSException
	 */
	public static SincerObligateCollection getValidObligateByRoomAndState(RoomInfo room, Set billStateSet) throws BOSException
	{
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("head.id");
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("room",room.getId().toString()));
		if(billStateSet != null && billStateSet.size() > 0)
		{
			filter.getFilterItems().add(new FilterItemInfo("head.bizState",billStateSet,CompareType.INCLUDE));
		}
		SinObligateRoomsEntryCollection sinRoomEntryColl = SinObligateRoomsEntryFactory.getRemoteInstance().getSinObligateRoomsEntryCollection(view);
		Set sinIds = new HashSet();
		for(int i =0; i < sinRoomEntryColl.size(); i ++)
		{
			sinIds.add(sinRoomEntryColl.get(i).getHead().getId().toString());
		}
		
		EntityViewInfo sinView = new EntityViewInfo();
		FilterInfo tenFilter = new FilterInfo();
		sinView.setFilter(tenFilter);
		tenFilter.getFilterItems().add(new FilterItemInfo("id", sinIds, CompareType.INCLUDE));
		sinView.getSelector().add("*");
		sinView.getSelector().add("sinCustomerList.fdcCustomer.*");
		sinView.getSelector().add("sinCustomerList.fdcCustomer.sysCustomer.name");
		sinView.getSelector().add("sinRoomList");
		
		return SincerObligateFactory.getRemoteInstance().getSincerObligateCollection(sinView);
	}
}
