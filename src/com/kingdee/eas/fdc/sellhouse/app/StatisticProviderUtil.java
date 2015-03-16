package com.kingdee.eas.fdc.sellhouse.app;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.finance.ContractPayPlanCollection;
import com.kingdee.eas.fdc.finance.ContractPayPlanFactory;
import com.kingdee.eas.fdc.finance.IContractPayPlan;
import com.kingdee.eas.fdc.sellhouse.BuildingCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.StatisticRoomTypeEnum;
import com.kingdee.eas.fdc.sellhouse.app.BuildingControllerBean;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.IPaymentBill;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.ma.budget.BgPeriodFactory;
import com.kingdee.eas.ma.budget.BgPeriodInfo;
import com.kingdee.eas.ma.budget.IBgPeriod;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

public class StatisticProviderUtil {

    private Context ServerCtx = null;
    
	/** 房地产合同付款计划接口 */
	private IContractPayPlan iContractPayPlan;

	/** 房地产合同接口 */
	private IContractBill iContractBill;

	/** 房地产付款单接口 */
	private IPaymentBill iPaymentBill;

	private IContractPayPlan getIContractPayPlan() throws BOSException {
		return iContractPayPlan != null ? iContractPayPlan : (iContractPayPlan = (ServerCtx != null ? ContractPayPlanFactory.getLocalInstance(ServerCtx)
				: ContractPayPlanFactory.getRemoteInstance()));
	}

	private IContractBill getIContractBill() throws BOSException {
		return iContractBill != null ? iContractBill : (iContractBill = (ServerCtx != null ? ContractBillFactory.getLocalInstance(ServerCtx)
				: ContractBillFactory.getRemoteInstance()));
	}

	private IPaymentBill getIPaymentBill() throws BOSException {
		return iPaymentBill != null ? iPaymentBill : (iPaymentBill = (ServerCtx != null ? PaymentBillFactory.getLocalInstance(ServerCtx) : PaymentBillFactory
				.getRemoteInstance()));
	}

	private IBgPeriod getBgPeriod() throws BOSException {
		return ServerCtx == null ? BgPeriodFactory.getRemoteInstance() : BgPeriodFactory.getLocalInstance(ServerCtx);
	}

	/**
	 * 房地产资金计划取数公式_计划数
	 * 
	 * @param fdcProject
	 * @param planPeriod
	 * @return BigDecimal
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public  BigDecimal fdc_plan(String xx, String fdcProject, String planPeriod) throws BOSException, EASBizException {

		IBgPeriod iBgPeriod = getBgPeriod();

		BgPeriodInfo info = null;

		BigDecimal sum = FDCHelper.ZERO;

		String oql = "SELECT * WHERE number = '" + planPeriod + "'";

		info = iBgPeriod.getBgPeriodInfo(oql);

		// 获取合同
		EntityViewInfo evi = new EntityViewInfo();

		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(new FilterItemInfo("curProject.longNumber", fdcProject.replace('.', '!')));

		evi.getSelector().add("*");

		evi.setFilter(filter);

		ContractBillCollection contractBillCollection = getIContractBill().getContractBillCollection(evi);

		Set set = new HashSet();

		for (int j = 0; j < contractBillCollection.size(); j++) {
			set.add(contractBillCollection.get(j).getId().toString());
		}

		if (set.size() < 1)
			return sum;

		// 获取合同计划
		evi = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("payDate", info.getBeginDate(), CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("payDate", info.getEndDate(), CompareType.LESS));
		filter.getFilterItems().add(new FilterItemInfo("contractId", set, CompareType.INCLUDE));
		evi.setFilter(filter);
		evi.getSelector().add(new SelectorItemInfo("*"));
		ContractPayPlanCollection cppc = getIContractPayPlan().getContractPayPlanCollection(evi);

		if (cppc != null) {
			for (int j = 0; j < cppc.size(); j++) {
				sum = sum.add(cppc.get(j).getPayAmount());
			}
			return sum;
		}

		return sum;
	}
	public BigDecimal fdc_statistic(String type,String buildingPropertyNumber,String buildingSimapleName,String orderState,String begingDate1,String endDate1) throws BOSException, EASBizException, SQLException
	{
		if(type==null||type.trim().length()<1)
			throw new BOSException("统计维度参数不能为空！");
		if(buildingSimapleName == null || buildingSimapleName.trim().length()<1)
		{
			throw new BOSException("楼栋参数不能为空！");
		}
		//取得ID
		Set numberSet = new HashSet();
		
		if(buildingPropertyNumber!=null&&buildingPropertyNumber.toString().trim().length()>0)
			buildingPropertyNumber = buildingPropertyNumber.replace('.','!');
		
		String tempNumber[] = buildingPropertyNumber.split(";");
		for(int i=0;i<tempNumber.length;i++)
		{
			numberSet.add(tempNumber[i].trim());
		}
		
		EntityViewInfo numberEvi = new EntityViewInfo();
		FilterInfo numberFilter = new FilterInfo();
		numberFilter.getFilterItems().add(new FilterItemInfo("longNumber",numberSet,CompareType.INCLUDE));
		numberEvi.getSelector().add("id");
		numberEvi.setFilter(numberFilter);
		
		BuildingPropertyCollection buildingPropertyCollection = BuildingPropertyFactory.getLocalInstance(ServerCtx).getBuildingPropertyCollection(numberEvi);
		String tempProperty="";
		if(buildingPropertyCollection!=null)
		{
		for(int i=0;i<buildingPropertyCollection.size();i++)
		{
			tempProperty += buildingPropertyCollection.get(i).getId().toString()+";";
		}
		}
		
		
		Set nameSet = new HashSet();
		String tempName[] = buildingSimapleName.split(";");
		for(int i=0;i<tempName.length;i++)
		{
			nameSet.add(tempName[i].trim());
		}
		EntityViewInfo nameEvi = new EntityViewInfo();
		FilterInfo nameFilter = new FilterInfo();
		nameFilter.getFilterItems().add(new FilterItemInfo("simpleName",nameSet,CompareType.INCLUDE));
		nameEvi.getSelector().add("id");
		nameEvi.setFilter(nameFilter);
		
		BuildingCollection buildingCollection = BuildingFactory.getLocalInstance(ServerCtx).getBuildingCollection(nameEvi);
		String tempSimpleName = "";
		if(buildingCollection!=null)
		{
			for( int i=0;i<buildingCollection.size();i++)
			{
				tempSimpleName += buildingCollection.get(i).getId().toString()+";";
			}
		}
		
		
		
		
		String building1 = tempSimpleName;
		
		String buildingProperty1 = tempProperty;
		
		
		//格式转换
		String [] buildingProperty = buildingProperty1==null||buildingProperty1.trim().length()<1? null:buildingProperty1.split(";"); 
		String [] building = building1==null||building1.trim().length()<1? null:building1.split(";");
		
		BigDecimal result= FDCHelper.ZERO;
		
		FDCSQLBuilder sql = new FDCSQLBuilder();
		StringBuffer termSql = new StringBuffer();
		
		String termBuilding = "";
		
		String termBuildingProperty = "";
		
		String termBeginDate = null;
		
		String termEndDate = null;
		
		//房间未成交判断
		String unDealSql =" where (r.FSellState!='"+RoomSellStateEnum.PURCHASE_VALUE+"' and r.FSellState!='"+RoomSellStateEnum.SIGN_VALUE+"' and r.FSellState!='"+RoomSellStateEnum.PREPURCHASE_VALUE+"')";
		
//		房间成交判断
		String dealSql =" where (r.FSellState='"+RoomSellStateEnum.PURCHASE_VALUE+"' or r.FSellState='"+RoomSellStateEnum.SIGN_VALUE+"' or r.FSellState='"+RoomSellStateEnum.PREPURCHASE_VALUE+"')";
		
	    termBeginDate = begingDate1==null||begingDate1.trim().length()<1? null:begingDate1;
	    termEndDate = endDate1==null||endDate1.trim().length()<1? null:endDate1;
		
		//DateTimeUtils.

		  //判断建筑性质
		if(buildingProperty!=null)
		{
			String temp = new String();
			for(int i=0;i<buildingProperty.length;i++)
			{
				temp += "'"+buildingProperty[i].trim()+"'"+",";
			}
			termBuildingProperty = temp.trim().length()<1? "":temp.substring(temp.indexOf("'")+1,temp.lastIndexOf("'"));
		    
			//termSql.append(" inner join T_SHE_BuildingProperty as b on r.FBuildingPropertyID=b.FID ");
			termSql.append(" and r.FBuildingPropertyID in('"+termBuildingProperty+"') ");
		}
		//判断楼栋
		if(building!=null)
		{
			String temp = new String();
			for(int i=0;i<building.length;i++)
			{
				temp += "'"+building[i].trim()+"'"+",";
			}
			termBuilding = temp.trim().length()<1? "":temp.substring(temp.indexOf("'")+1,temp.lastIndexOf("'"));
		    
			termSql.append(" and r.FBuildingID in('"+termBuilding+"') ");
		}
	 
		if(termBeginDate!=null)
		{
			//termSql.append(" and to_date(to_char(r.FToPurchaseDate,'yyyy-mm-dd'))>='"+termBeginDate+"'");
			//termBeginDate=DateTimeUtils.truncateDate(termBeginDate);
			termSql.append(" and r.FToPurchaseDate>=to_date('"+termBeginDate+"')");
			
//			termSql.
			//sql.
		}
		
		if(termEndDate!=null)
		{
			termSql.append(" and to_date(to_char(r.FToPurchaseDate,'yyyy-mm-dd'))<=to_date('"+termEndDate+"')");
			//termSql.append(" and r.FToPurchaseDate<='"+termEndDate+"'");
		
		}
		//判断 是否推盘
		if(orderState!=null&&orderState.trim().length()>0)
		{
			if(orderState.equalsIgnoreCase("yes"))
			{
				termSql.append(" and r.FSellOrderID is not null");
			}
			else if(orderState.equalsIgnoreCase("no"))
			{
				termSql.append(" and r.FSellOrderID is null");
			}
		}
		
		//判断查询维度
		String requestType = type;
		//总套数
		if(requestType.equalsIgnoreCase("totalRoomAmount"))
		{
			sql.appendSql(" select count(*) as amount from  t_she_room as r ");
			sql.appendSql(" where 1=1 ");
			sql.appendSql(termSql.toString());
			
			//IRowSet rowSet =  sql.executeQuery();
			IRowSet rowSet = sql.executeQuery(ServerCtx);
			
			while(rowSet.next())
			{
				result=rowSet.getBigDecimal("amount");
			}
		}
		//总建筑面积
		else if(requestType.equalsIgnoreCase("totalBuildingArea"))
		{
			sql.appendSql(" select sum(r.FBuildingArea) as totalBuildingArea from t_she_room as r ");
			sql.appendSql(" where 1=1 ");
			sql.appendSql(termSql.toString());
			
			IRowSet rowSet = sql.executeQuery(ServerCtx);
			while(rowSet.next())
			{
				result = rowSet.getBigDecimal("totalBuildingArea");
			}
		}
		//总套内面积
		else if(requestType.equalsIgnoreCase("totalRoomArea"))
		{
			sql.appendSql(" select sum(r.FRoomArea) as totalRoomArea from t_she_room as r");
			sql.appendSql(" where 1=1 ");
			sql.appendSql(termSql.toString());
			
			IRowSet rowSet = sql.executeQuery(ServerCtx);
			while(rowSet.next())
			{
				result = rowSet.getBigDecimal("totalRoomArea");
			}
		}
		//总标准总价
		else if(requestType.equalsIgnoreCase("totalStandardTotalPrice"))
		{
			sql.appendSql(" select sum(r.FStandardTotalAmount) as totalStandardTotalPrice from t_she_room as r ");
			sql.appendSql(" where 1=1 ");
			sql.appendSql(termSql.toString());
			
			IRowSet rowSet = sql.executeQuery(ServerCtx);
			while(rowSet.next())
			{
				result = rowSet.getBigDecimal("totalStandardTotalPrice");
			}
		}
		//未成交套数
		else if(requestType.equalsIgnoreCase("unDealRoomAmount"))
		{
			sql.appendSql(" select count(*) as unDealAmount from t_she_room as r ");
			sql.appendSql(unDealSql);
			sql.appendSql(termSql.toString());
			
			IRowSet rowSet = sql.executeQuery(ServerCtx);
			
			while(rowSet.next())
			{
				result = rowSet.getBigDecimal("unDealAmount");
			}
		}
		//未成交建筑面积
		else if(requestType.equalsIgnoreCase("unDealBuildingArea"))
		{
			sql.appendSql(" select sum(r.FBuildingArea) as unDealBuildingArea from t_she_room as r ");
			sql.appendSql(unDealSql);
			sql.appendSql(termSql.toString());
			
			IRowSet rowSet = sql.executeQuery(ServerCtx);
			
			while(rowSet.next())
			{
				result = rowSet.getBigDecimal("unDealBuildingArea");
			}
		}
		//未成交套内面积
		else if(requestType.equalsIgnoreCase("unDealRoomArea"))
		{
			sql.appendSql(" select sum(r.FRoomArea) as unDealRoomArea from t_she_room as r ");
			sql.appendSql(unDealSql);
			sql.appendSql(termSql.toString());
			
			IRowSet rowSet = sql.executeQuery(ServerCtx);
			
			while(rowSet.next())
			{
				result = rowSet.getBigDecimal("unDealRoomArea");
			}
		}
		//未售标准总价
		else if(requestType.equalsIgnoreCase("unDealStandardTotalPrice"))
		{
			sql.appendSql(" select sum(r.FStandardTotalAmount) as unDealStandardTotalPrice from t_she_room as r");
			sql.appendSql(unDealSql);
			sql.appendSql(termSql.toString());
			
			IRowSet rowSet = sql.executeQuery(ServerCtx);
			
			while(rowSet.next())
			{
				result = rowSet.getBigDecimal("unDealStandardTotalPrice");
			}
		}
		//成交套数
		else if(requestType.equalsIgnoreCase("dealRoomAmount"))
		{
			sql.appendSql(" select count(*) as dealRoomAmount from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());
			
			IRowSet rowSet = sql.executeQuery(ServerCtx);
			
			while(rowSet.next())
			{
				result = rowSet.getBigDecimal("dealRoomAmount");
			}
		}
		//成交建筑面积
		else if(requestType.equalsIgnoreCase("dealBuildingArea"))
		{
			sql.appendSql(" select sum(r.FBuildingArea) as dealBuildingArea from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());
			
			IRowSet rowSet = sql.executeQuery(ServerCtx);
			
			while(rowSet.next())
			{
				result = rowSet.getBigDecimal("dealBuildingArea");
			}
		}
		//成交套内面积
		else if(requestType.equalsIgnoreCase("dealRoomArea"))
		{
			sql.appendSql(" select sum(r.FRoomArea) as dealRoomArea from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());
			
			IRowSet rowSet = sql.executeQuery(ServerCtx);
			
			while(rowSet.next())
			{
				result = rowSet.getBigDecimal("dealRoomArea");
			}
		}
		//成交标准总价
		else if(requestType.equalsIgnoreCase("dealStandardTotalPrice"))
		{
			sql.appendSql(" select sum(r.FStandardTotalAmount) as dealStandardTotalPrice from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());
			
			IRowSet rowSet = sql.executeQuery(ServerCtx);
			
			while(rowSet.next())
			{
				result = rowSet.getBigDecimal("dealStandardTotalPrice");
			}
		}
		//成交总价
		else if(requestType.equalsIgnoreCase("totalDealTotalPrice"))
		{
			sql.appendSql(" select sum(r.FDealTotalAmount) as totalDealTotalPrice from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());
			
			IRowSet rowSet = sql.executeQuery(ServerCtx);
			
			while(rowSet.next())
			{
				result = rowSet.getBigDecimal("totalDealTotalPrice");
			}
		}
		//总销售额
		else if(requestType.equalsIgnoreCase("totalSellAmount"))
		{
			sql.appendSql(" select sum(r.FSellAmount) as totalSellAmount from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());
			
			IRowSet rowSet = sql.executeQuery(ServerCtx);
			
			while(rowSet.next())
			{
				result = rowSet.getBigDecimal("totalSellAmount");
			}
		}
		//总面积补差款
		else if(requestType.equalsIgnoreCase("totalAreaCompensateAmount"))
		{
			sql.appendSql(" select sum(r.FAreaCompensateAmount) as totalAreaCompensateAmount from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());
			
			IRowSet rowSet = sql.executeQuery(ServerCtx);
			
			while(rowSet.next())
			{
				result =  rowSet.getBigDecimal("totalAreaCompensateAmount");
			}
		}
		//总建筑补差面积
		else if(requestType.equalsIgnoreCase("totalBuildingCompensateArea"))
		{
			sql.appendSql(" select sum(r.FActualBuildingArea-r.FBuildingArea) as totalBuildingCompensateArea from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());
			
			IRowSet rowSet = sql.executeQuery(ServerCtx);
			
			while(rowSet.next())
			{
				result = rowSet.getBigDecimal("totalBuildingCompensateArea");
			}
		}
		
		//总套内补差面积
		else if(requestType.equalsIgnoreCase("totalRoomCompensateArea"))
		{
			sql.appendSql(" select sum(r.FActualRoomArea-r.FRoomArea) as totalRoomCompensateArea from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());
			
			IRowSet rowSet = sql.executeQuery(ServerCtx);
			
			while(rowSet.next())
			{
				result = rowSet.getBigDecimal("totalRoomCompensateArea");
			}
		}
		
		return result;
	}
	/**
	 * 房地产资金计划取数公式_实际数
	 * 
	 * @param orgUnit
	 * @param fdcProject
	 * @param paymentType
	 * @param periodBegin
	 * @param periodEnd
	 * @return BigDecimal
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public BigDecimal fdc_real(String xx, String fdcProject, String paymentType, String periodBegin, String periodEnd) throws BOSException, EASBizException {

		// 变量定义
		IBgPeriod iBgPeriod = getBgPeriod();

		BgPeriodInfo info;
		Date endDate, beginDate;
		EntityViewInfo evi;
		FilterInfo filter;
		BigDecimal sum = FDCHelper.ZERO;
		//
		// 取得开始期间
		String oql = "SELECT * WHERE number = '" + periodBegin + "'";
		info = iBgPeriod.getBgPeriodInfo(oql);
		beginDate = info.getBeginDate();

		// 取得结束期间
		oql = "SELECT * WHERE number = '" + periodEnd + "'";
		info = iBgPeriod.getBgPeriodInfo(oql);

		endDate = info.getEndDate();

		evi = new EntityViewInfo();
		filter = new FilterInfo();
		String ln = fdcProject.replace('.', '!');
		filter.getFilterItems().add(new FilterItemInfo("curProject.longNumber", ln));

		evi.getSelector().add("*");
		evi.setFilter(filter);
		ContractBillCollection contractBillCollection = getIContractBill().getContractBillCollection(evi);
		Set set = new HashSet();

		for (int j = 0; j < contractBillCollection.size(); j++) {
			set.add(contractBillCollection.get(j).getId().toString());
		}
		if (set.size() < 1)
			return sum;

		// 获取付款单
		evi = new EntityViewInfo();
		filter = new FilterInfo();

		filter.getFilterItems().add(new FilterItemInfo("billStatus", BillStatusEnum.PAYED));
		filter.getFilterItems().add(new FilterItemInfo("payDate", beginDate, CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("payDate", endDate, CompareType.LESS));
		filter.getFilterItems().add(new FilterItemInfo("fdcPayType.number", paymentType));
		filter.getFilterItems().add(new FilterItemInfo("contractBillId", set, CompareType.INCLUDE));
		evi.setFilter(filter);
		evi.getSelector().add(new SelectorItemInfo("*"));
		PaymentBillCollection pbc = getIPaymentBill().getPaymentBillCollection(evi);
		if (pbc != null) {
			for (int j = 0; j < pbc.size(); j++) {
				sum = sum.add(pbc.get(j).getActPayAmt());
			}
		}
		return sum;
	}

	public Context getServerCtx() {
		return ServerCtx;
	}

	public void setServerCtx(Context serverCtx) {
		ServerCtx = serverCtx;
	}
}

