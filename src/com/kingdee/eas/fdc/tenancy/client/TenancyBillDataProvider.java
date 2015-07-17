package com.kingdee.eas.fdc.tenancy.client;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryCollection;
import com.kingdee.eas.fdc.tenancy.DealAmountEntryInfo;
import com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class TenancyBillDataProvider extends FDCBillDataProvider {

	private TenancyBillInfo ten;
	public TenancyBillDataProvider(String billId, IMetaDataPK mainQuery) {
		super(billId, mainQuery);
		try {
			ten = TenancyHelper.getTenancyBillInfo(billId);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}

	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds) {
		
		if(("TenancyBillPrintQuery").toUpperCase().equals(ds.getID().toUpperCase())){
			/****
			 * 主单据
			 */
			return this.getMainBillRowSet(ds);
		}
		else if(("TenancyRoomEntryPrintQuery").toUpperCase().equals(ds.getID().toUpperCase())){
			/****
			 * 房间分录
			 */
			return getTenancyRoomEntryRowSet(ds);
		}
		else if(("TenancyCustomerEntryPrintQuery").toUpperCase().equals(ds.getID().toUpperCase())){
			/****
			 * 客户分录
			 */
			return getTenancyCustomerEntryRowSet(ds);
		}
		else if(("IncreasedRentEntryPrintQuery").toUpperCase().equals(ds.getID().toUpperCase())){
			/****
			 * 租金递增分录
			 */
			return getIncreasedRentEntryRowSet(ds);
		}
		else if(("RentFeeEntryPrintQuery").toUpperCase().equals(ds.getID().toUpperCase())){
			/****
			 * 免租分录
			 */
			return getRentFeeEntryRowSet(ds);
		}
		else if(("DealAmountEntryPrintQuery").toUpperCase().equals(ds.getID().toUpperCase())){
			/****
			 * 房间租金分录
			 */
			return getRoomDealAmountEntryRowSet(ds);
		}
		else if(("TENANCYBILL").toUpperCase().equals(ds.getID().toUpperCase())){
			return getEntryRowSet(ds,"id",new MetaDataPK("com.kingdee.eas.fdc.tenancy.app.TenancyBillPrintQuery"));
		}else if (ds.getID().equalsIgnoreCase("AttachmentQuery")) {
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory
						.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fdc.basedata.app.AttachmentQuery"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("boAttchAsso.boID",billId));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return iRowSet;
		}else if (ds.getID().equalsIgnoreCase("TenancyBillTotalPrintQuery")) {
			return getTotalEntryRowSet();
		}
		return getEntryRowSet(ds,"tenancy.id",new MetaDataPK("com.kingdee.eas.fdc.tenancy.app."+ds.getID()));
		
	}
	public IRowSet getMainBillRowSet(BOSQueryDataSource ds) {
		IRowSet rowSet = super.getMainBillRowSet(ds);
		/***
		 * 更新 
		 * 合并房号allRoomNumber
		 * 总建筑面积allBuildArea
		 * 租赁期(年)rentYears
		 * 周期性费用总额circleFee
		 */
		try {
			Map valueMap = getUpdateValues(billId);
			rowSet.beforeFirst();
			while(rowSet.next()){
				
				Date startDate = rowSet.getDate("startDate");
				GregorianCalendar start = new GregorianCalendar();
				start.setTime(startDate);
				Date endDate = rowSet.getDate("endDate");
				GregorianCalendar end = new GregorianCalendar();
				end.setTime(endDate);
				int rentYears = new Long(FDCDateHelper.dateDiff("yyyy", endDate, startDate)).intValue();
				if(end.get(GregorianCalendar.MONTH)>start.get(GregorianCalendar.MONTH)){
					rentYears++;
				}else if(end.get(GregorianCalendar.MONTH)==start.get(GregorianCalendar.MONTH)){
					if(end.get(GregorianCalendar.DATE)>start.get(GregorianCalendar.DATE)){
						rentYears++;
					}
				}
				rowSet.updateBigDecimal("rentYears", FDCHelper.toBigDecimal(new Integer(rentYears)));
				
				
				rowSet.updateString("allRoomNumber", String.valueOf(valueMap.get("allRoomNumber")));
				rowSet.updateBigDecimal("allBuildArea", FDCHelper.toBigDecimal(valueMap.get("allBuildArea")));
				
				rowSet.updateBigDecimal("circleFee", FDCHelper.toBigDecimal(valueMap.get("circleFee")));
			}
			rowSet.beforeFirst();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowSet;
	}
	private IRowSet getTotalEntryRowSet() {
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select t.moneyDefine,isnull(sum(t,appAmount),0) appAmount,isnull(sum(t,actRevAmount),0) actRevAmount from(");
		_builder.appendSql(" select md.fnumber mdNumber,md.fname_l2 moneyDefine,entry.fappAmount appAmount,entry.factRevAmount actRevAmount from T_TEN_TenBillOtherPay entry left join T_SHE_MoneyDefine md on md.fid=entry.fmoneyDefineId");
		_builder.appendSql(" where entry.fheadId='"+billId+"'");
		_builder.appendSql(" uniaon all select md.fnumber mdNumber,md.fname_l2 moneyDefine,entry.fappAmount appAmount,entry.factRevAmount actRevAmount,entry.ftenBillId billId from T_TEN_TenancyRoomPayListEntry entry left join T_SHE_MoneyDefine md on md.fid=entry.fmoneyDefineId");
		_builder.appendSql(" where entry.ftenBillId='"+billId+"'");
		_builder.appendSql(" )t group by t.moneyDefine");
		IRowSet rowSet=null;
		try {
			rowSet = _builder.executeQuery();
			rowSet.beforeFirst();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowSet;
	}
	private Map getUpdateValues(String billId) throws BOSException {
		Map value = new HashMap();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("tenancy.id", billId, CompareType.EQUALS));
		view.getSelector().add("roomLongNum");
		view.getSelector().add("buildingArea");
		TenancyRoomEntryCollection colls = TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryCollection(view);
		StringBuffer allRoomNumber = new StringBuffer();
		BigDecimal allBuildArea = FDCHelper.ZERO;
		for(Iterator it = colls.iterator();it.hasNext();){
			TenancyRoomEntryInfo info = (TenancyRoomEntryInfo)it.next();
			allRoomNumber.append(info.getRoomLongNum());
			allRoomNumber.append("，");
			allBuildArea = FDCHelper.add(allBuildArea, info.getBuildingArea());
		}
				
		value.put("allRoomNumber", allRoomNumber.toString().substring(0, allRoomNumber.toString().length()-1));
		value.put("allBuildArea", allBuildArea);
		
		/*****
		 * 获取总的周期性费用
		 */
		value.put("circleFee", getAllPeriodicityAmount(ten));
		
		return value;
	}

	private IRowSet getRoomDealAmountEntryRowSet(BOSQueryDataSource ds) {
		IRowSet rowSet = getEntryRowSet(ds,"tenancy.id",new MetaDataPK("com.kingdee.eas.fdc.tenancy.app.DealAmountEntryPrintQuery"));
		/****
		 * do some logic function that replace or get data ..
		 */
		return rowSet;
	}
	private IRowSet getTenancyRoomEntryRowSet(BOSQueryDataSource ds){
		IRowSet rowSet = getEntryRowSet(ds,"tenancy.id",new MetaDataPK("com.kingdee.eas.fdc.tenancy.app.TenancyRoomEntryPrintQuery"));
		/****
		 * do some logic function that replace or get data ..
		 */
		return rowSet;
	}
	private IRowSet getTenancyCustomerEntryRowSet(BOSQueryDataSource ds) {
		IRowSet rowSet = getEntryRowSet(ds,"tenancyBill.id",new MetaDataPK("com.kingdee.eas.fdc.tenancy.app.TenancyCustomerEntryPrintQuery"));
		/****
		 * do some logic function that replace or get data ..
		 * updateString("property", value);
		 */
		return rowSet;
	}

	private IRowSet getIncreasedRentEntryRowSet(BOSQueryDataSource ds) {
		IRowSet rowSet = getEntryRowSet(ds,"tenancy.id",new MetaDataPK("com.kingdee.eas.fdc.tenancy.app.IncreasedRentEntryPrintQuery"));
		/****
		 * do some logic function that replace or get data ..
		 */
		return rowSet;
	}

	private IRowSet getRentFeeEntryRowSet(BOSQueryDataSource ds) {
		IRowSet rowSet = getEntryRowSet(ds,"tenancy.id",new MetaDataPK("com.kingdee.eas.fdc.tenancy.app.RentFeeEntryPrintQuery"));
		/****
		 * 更新
		 * 免租日freeDays
		 * 免租月freeMonths
		 * 免租期费用freeFee
		 */
		try {
			rowSet.beforeFirst();
			while(rowSet.next()){
				Date startDate = rowSet.getDate("startDate");
				GregorianCalendar start = new GregorianCalendar();
				start.setTime(startDate);
				Date endDate = rowSet.getDate("endDate");
				GregorianCalendar end = new GregorianCalendar();
				end.setTime(endDate);
				Long freeDays = null;
				int freeMonths = 0;
				if(startDate!=null&&endDate!=null)
				{
					freeDays = new Long(FDCDateHelper.dateDiff("d", endDate, startDate));
					freeMonths = (end.get(GregorianCalendar.YEAR) - start.get(GregorianCalendar.YEAR)) * 12 + (end.get(GregorianCalendar.MONTH) - start.get(GregorianCalendar.MONTH));
					if(end.get(GregorianCalendar.DATE)>start.get(GregorianCalendar.DATE)){
						freeMonths++;
					}
				}
				String entryId = rowSet.getString("id");
				
				rowSet.updateBigDecimal("freeDays", FDCHelper.toBigDecimal(freeDays));
				rowSet.updateBigDecimal("freeMonths", FDCHelper.toBigDecimal(new Integer(freeMonths)));
				rowSet.updateBigDecimal("freeFee", FDCHelper.toBigDecimal(getFreeFee(startDate, endDate, ten)));
			}
			rowSet.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowSet;
	}

	/**
	 * 获得这段免租期的周期性费用总额
	 * 
	 * TODO 这里有个前提，就是周期性费用不能支持递增，一个房间的dealAmount分录中，同一周期性款项的记录最多只能有1条
	 * 未考虑配套资源的周期性费用
	 * */
	private BigDecimal getFreeFee(Date startDate, Date endDate, TenancyBillInfo tenancy) {
		if(tenancy == null){
			return FDCHelper.ZERO;
		}
		BigDecimal tol = FDCHelper.ZERO;
		TenancyRoomEntryCollection tenRooms = tenancy.getTenancyRoomList();
		for(int i=0; i<tenRooms.size(); i++){
			TenancyRoomEntryInfo tenRoom = tenRooms.get(i);
			DealAmountEntryCollection dealAmounts = tenRoom.getDealAmounts();
			for(int j=0; j<dealAmounts.size(); j++){
				DealAmountEntryInfo dealAmount = dealAmounts.get(j);
				MoneyDefineInfo money = dealAmount.getMoneyDefine();
				
				if(money != null  &&  !MoneyTypeEnum.RentAmount.equals(money.getMoneyType())
						&&  !MoneyTypeEnum.RentAmount.equals(money.getMoneyType())){
					BigDecimal am = TenancyHelper.getRentBetweenDate(money, startDate, endDate, dealAmounts, new RentFreeEntryCollection());
					if(am != null){
						tol = tol.add(am);
					}
				}
			}
		}
		return tol;
	}

	/**
	 * 获得所有周期性费用的价格之和
	 * TODO 未考虑配套资源的周期性费用
	 * */
	private BigDecimal getAllPeriodicityAmount(TenancyBillInfo tenancy) {
		if(tenancy == null){
			return FDCHelper.ZERO;
		}
		BigDecimal tol = FDCHelper.ZERO;
		TenancyRoomEntryCollection tenRooms = tenancy.getTenancyRoomList();
		for(int i=0; i<tenRooms.size(); i++){
			TenancyRoomEntryInfo tenRoom = tenRooms.get(i);
			DealAmountEntryCollection dealAmounts = tenRoom.getDealAmounts();
			for(int j=0; j<dealAmounts.size(); j++){
				DealAmountEntryInfo dealAmount = dealAmounts.get(j);
				MoneyDefineInfo money = dealAmount.getMoneyDefine();
				
				if(money != null  &&  !MoneyTypeEnum.RentAmount.equals(money.getMoneyType())
						&&  !MoneyTypeEnum.RentAmount.equals(money.getMoneyType())){
					BigDecimal am = dealAmount.getAmount();
					if(am != null){
						tol = tol.add(am);
					}
				}
			}
		}
		return tol;
	}
	
	public IRowSet getEntryRowSet(BOSQueryDataSource ds,String property,IMetaDataPK entryQuery){
		IRowSet iRowSet = null;
        try {
        	if(property==null)
        		property="tenancy.id";
        	IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(entryQuery);
            exec.option().isAutoTranslateEnum= true;
            EntityViewInfo ev = new EntityViewInfo();
            FilterInfo filter = new FilterInfo();
            filter.getFilterItems().add(new FilterItemInfo(property, billId, CompareType.EQUALS));
            ev.setFilter(filter);            
            exec.setObjectView(ev);
            iRowSet = exec.executeQuery();	
            iRowSet.beforeFirst();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iRowSet;
	}
	

}
