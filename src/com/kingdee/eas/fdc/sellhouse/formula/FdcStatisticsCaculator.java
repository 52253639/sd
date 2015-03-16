package com.kingdee.eas.fdc.sellhouse.formula;

import java.math.BigDecimal;
import java.sql.SQLException;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.IMethodBatchQuery;
import com.kingdee.bos.ctrl.excel.model.struct.Parameter;
import com.kingdee.bos.ctrl.excel.model.util.SortedParameterArray;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;

import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;

import com.kingdee.eas.fdc.sellhouse.BuildingCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.StatisticRoomTypeEnum;

import com.kingdee.eas.fi.gl.common.toolkit.sqlbuilder.SqlBuilder;
import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;
import com.kingdee.eas.fi.newrpt.formula.IReportPropertyAdapter;
import com.kingdee.eas.fm.common.FMConstants;

import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 房地产售楼统计函数
 * @author laiquan_luo
 *
 */
public class FdcStatisticsCaculator implements ICalculator, IMethodBatchQuery
{
	private Context ServerCtx = null;

	private ICalculateContextProvider context;


	String type; 
	String sellProjectNumber; 
	String buildingSimapleName;
	String buildingPropertyNumber;
	String productTypeNumber;
	String orderState;
	Boolean accessorialProperty;
	Boolean preBiz;
	String begingDate1;
	String endDate1;



	private BuildingCollection getBuildingColl(String sellProjectNumber,String buildingSimapleName) throws BOSException
	{
		BuildingCollection buildingColl = null;
		
		if (buildingSimapleName != null && buildingSimapleName.trim().length() > 0)
		{
			Set nameSet = new HashSet();
			String tempName[] = buildingSimapleName.split(";");
			for (int i = 0; i < tempName.length; i++)
			{
				nameSet.add(tempName[i].trim());
			}
			EntityViewInfo nameEvi = new EntityViewInfo();
			FilterInfo nameFilter = new FilterInfo();
			nameFilter.getFilterItems().add(new FilterItemInfo("simpleName", nameSet,CompareType.INCLUDE));
			nameEvi.getSelector().add("id");
			nameEvi.setFilter(nameFilter);

			buildingColl = BuildingFactory.getLocalInstance(ServerCtx).getBuildingCollection(nameEvi);
		}
		else
		{
			Set numberSet = new HashSet();
			String tempNumber[] = sellProjectNumber.split(";");
			for(int i = 0; i < tempNumber.length; i ++)
			{
				numberSet.add(tempNumber[i].trim());
			}
			
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			
			filter.getFilterItems().add(new FilterItemInfo("sellProject.number",numberSet,CompareType.INCLUDE));
			
			view.getSelector().add("id");
			
			buildingColl = BuildingFactory.getLocalInstance(ServerCtx).getBuildingCollection(view);
		}
		return buildingColl;
	}
	
	private BuildingPropertyCollection getBuildingProColl(String buildingPropertyNumber) throws BOSException
	{
		BuildingPropertyCollection buildingProColl = null;
		
		Set numberSet = new HashSet();

		if (buildingPropertyNumber != null	&& buildingPropertyNumber.trim().length() > 0)
		{
			buildingPropertyNumber = buildingPropertyNumber.replace('.', '!');

			String tempNumber[] = buildingPropertyNumber.split(";");
			for (int i = 0; i < tempNumber.length; i++)
			{
				numberSet.add(tempNumber[i].trim());
			}
		}
		else
		{
			return buildingProColl;
		}
		
		EntityViewInfo numberEvi = new EntityViewInfo();
		FilterInfo numberFilter = new FilterInfo();
		numberFilter.getFilterItems().add(new FilterItemInfo("longNumber", numberSet,CompareType.INCLUDE));
		numberEvi.getSelector().add("id");
		numberEvi.setFilter(numberFilter);

		buildingProColl = BuildingPropertyFactory.getLocalInstance(ServerCtx).getBuildingPropertyCollection(numberEvi);
		
		return buildingProColl;
	}
	
	private ProductTypeCollection getProTypeColl(String productTypeNumber) throws BOSException
	{
		ProductTypeCollection proTypeColl = null;
		
		Set numberSet = new HashSet();
		String tempNumber[] = productTypeNumber.split(";");
		
		if (productTypeNumber != null && productTypeNumber.trim().length() > 0)
		{
			for (int i = 0; i < tempNumber.length; i++)
			{
				numberSet.add(tempNumber[i].trim());
			}
		} 
		else
		{
			return proTypeColl;
		}
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("number",numberSet,CompareType.INCLUDE));
		view.getSelector().add("id");
		
		proTypeColl = ProductTypeFactory.getLocalInstance(ServerCtx).getProductTypeCollection(view);
		
		return proTypeColl;
		
	}
	
	
	/**
	 * @param type						统计维度参数
	 * @param sellProjectNumber			项目参数不能为空
	 * @param buildingSimapleName
	 * @param buildingPropertyNumber
	 * @param productTypeNumber
	 * @param orderState
	 * @param accessorialProperty
	 * @param preBiz
	 * @param begingDate1
	 * @param endDate1
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws SQLException
	 */
	public BigDecimal fdc_statistic(
			String type, 
			String sellProjectNumber, 
			String buildingSimapleName,
			String buildingPropertyNumber,
			String productTypeNumber,
			String orderState, 
			Boolean accessorialProperty,
			Boolean preBiz,
			String begingDate1,
			String endDate1)
	throws BOSException, EASBizException, SQLException
	{
		if (type == null || type.trim().length() < 1)
		{
			throw new BOSException("统计维度参数不能为空！");
		}
		if (sellProjectNumber == null || sellProjectNumber.trim().length() < 1)
		{
			throw new BOSException("项目参数不能为空！");
		}


		BuildingPropertyCollection buildingPropertyCollection = this.getBuildingProColl(buildingPropertyNumber);
		
		String tempProperty = "";
		if (buildingPropertyCollection != null)
		{
			for (int i = 0; i < buildingPropertyCollection.size(); i++)
			{
				tempProperty += buildingPropertyCollection.get(i).getId().toString()+ ";";
			}
		}


		BuildingCollection buildingCollection = this.getBuildingColl(sellProjectNumber,buildingSimapleName);
		String tempSimpleName = "";
		if (buildingCollection != null)
		{
			for (int i = 0; i < buildingCollection.size(); i++)
			{
				tempSimpleName += buildingCollection.get(i).getId().toString()+ ";";
			}
		}
		
		ProductTypeCollection proTypeColl = this.getProTypeColl(productTypeNumber);
		String tempProductTypeNumber = "";
		if(proTypeColl != null)
		{
			for(int i = 0; i < proTypeColl.size(); i ++)
			{
				tempProductTypeNumber += proTypeColl.get(i).getId().toString()+";";
			}
		}
		
		
		

		String building1 = tempSimpleName;

		String buildingProperty1 = tempProperty;

		// 格式转换
		String[] buildingProperty = buildingProperty1 == null
				|| buildingProperty1.trim().length() < 1 ? null
				: buildingProperty1.split(";");
		String[] building = building1 == null || building1.trim().length() < 1 ? null
				: building1.split(";");
		
		String [] productType = tempProductTypeNumber == null || tempProductTypeNumber.trim().length() < 1 ? null
				:tempProductTypeNumber.split(";");
		

		BigDecimal result = FDCHelper.ZERO;

		FDCSQLBuilder sql = new FDCSQLBuilder();
		StringBuffer termSql = new StringBuffer();

		String termBuilding = "";

		String termBuildingProperty = "";

		String termBeginDate = null;

		String termEndDate = null;

		
		

		String unDealSql = "";
		String dealSql = "";
		
		//预定业务计入统计
		if (preBiz != null)
		{
			if (preBiz.booleanValue())
			{
				// 房间未售判断
				unDealSql = " where (r.FSellState!='"
						+ RoomSellStateEnum.PURCHASE_VALUE
						+ "' and r.FSellState!='"
						+ RoomSellStateEnum.SIGN_VALUE
						+ "' and r.FSellState!='"
						+ RoomSellStateEnum.PREPURCHASE_VALUE + "')";

				// 房间已售判断
				dealSql = " where (r.FSellState='"
						+ RoomSellStateEnum.PURCHASE_VALUE
						+ "' or r.FSellState='" + RoomSellStateEnum.SIGN_VALUE
						+ "' or r.FSellState='"
						+ RoomSellStateEnum.PREPURCHASE_VALUE + "')";
			} else
			{
				// 房间未售判断
				unDealSql = " where (r.FSellState!='"
						+ RoomSellStateEnum.PURCHASE_VALUE
						+ "' and r.FSellState!='"
						+ RoomSellStateEnum.SIGN_VALUE + "')";

				// 房间已售判断
				dealSql = " where (r.FSellState='"
						+ RoomSellStateEnum.PURCHASE_VALUE
						+ "' or r.FSellState='" + RoomSellStateEnum.SIGN_VALUE
						+ "')";
			}
		}
		else
		{
			unDealSql = "where 1 = 1";
			dealSql = "where 1 = 1";
		}
		
	
		if(FDCHelper.isEmpty(begingDate1)) {
			if(type.equalsIgnoreCase(StatisticRoomTypeEnum.ARREARAGESUM_VALUE) || type.equalsIgnoreCase(StatisticRoomTypeEnum.TOTALBASETOTALPRICE_VALUE)
					|| type.equalsIgnoreCase(StatisticRoomTypeEnum.TOTALBUILDINGAREA_VALUE) || type.equalsIgnoreCase(StatisticRoomTypeEnum.TOTALROOMAREA_VALUE)
					|| type.equalsIgnoreCase(StatisticRoomTypeEnum.TOTALSTANDARDTOTALPRICE_VALUE) || type.equalsIgnoreCase(StatisticRoomTypeEnum.TOTALSUM_VALUE)) {			
				begingDate1 = null;
			}else{
				Date reportBeginDate = (Date) this.context.getReportAdapter().getReportProperty(IReportPropertyAdapter.Report_StartDate);
				begingDate1 = FMConstants.FORMAT_DAY.format(reportBeginDate);				
			}
		}
		if(FDCHelper.isEmpty(endDate1)) {
			if(type.equalsIgnoreCase(StatisticRoomTypeEnum.ARREARAGESUM_VALUE) || type.equalsIgnoreCase(StatisticRoomTypeEnum.TOTALBASETOTALPRICE_VALUE)
					|| type.equalsIgnoreCase(StatisticRoomTypeEnum.TOTALBUILDINGAREA_VALUE) || type.equalsIgnoreCase(StatisticRoomTypeEnum.TOTALROOMAREA_VALUE)
					|| type.equalsIgnoreCase(StatisticRoomTypeEnum.TOTALSTANDARDTOTALPRICE_VALUE) || type.equalsIgnoreCase(StatisticRoomTypeEnum.TOTALSUM_VALUE)) {		
				endDate1 = null;
			}else{
				Date reportEndDate = (Date) this.context.getReportAdapter().getReportProperty(IReportPropertyAdapter.Report_EndDate);
				endDate1 = FMConstants.FORMAT_DAY.format(reportEndDate);				
			}
		}
		
		termBeginDate = begingDate1;
		termEndDate = endDate1;

		
//		 判断楼栋
		if (building != null)
		{
			String temp = new String();
			for (int i = 0; i < building.length; i++)
			{
				temp += "'" + building[i].trim() + "'" + ",";
			}
			termBuilding = temp.trim().length() < 1 ? "" : temp.substring(temp.indexOf("'") + 1, temp.lastIndexOf("'"));
			termSql.append(" and r.FBuildingID in('" + termBuilding + "') ");
		}
		
		// 判断建筑性质
		if (buildingProperty != null)
		{
			String temp = "";
			for (int i = 0; i < buildingProperty.length; i++)
			{
				temp += "'" + buildingProperty[i].trim() + "'" + ",";
			}
			termBuildingProperty = temp.trim().length() < 1 ? "" : temp.substring(temp.indexOf("'") + 1, temp.lastIndexOf("'"));
			termSql.append(" and r.FBuildingPropertyID in('"+ termBuildingProperty + "') ");
		}
		
		//判断产品类型
		if(productType != null)
		{
			String temp = "";
			for(int i = 0; i < productType.length; i ++)
			{
				temp += "'" + productType[i].trim() + "'" +",";
			}
			String t = temp.trim().length() < 1 ? "" :temp.substring(temp.indexOf("'") + 1, temp.lastIndexOf("'"));
			termSql.append(" and r.FProductTypeID in ('"+ t +"')");
			
		}
		
		// 判断 是否推盘
		if (orderState != null && orderState.trim().length() > 0)
		{
			if (orderState.equalsIgnoreCase("yes"))
			{
				termSql.append(" and r.FSellOrderID is not null");
			} else if (orderState.equalsIgnoreCase("no"))
			{
				termSql.append(" and r.FSellOrderID is null");
			}
		}
		
		if(!accessorialProperty.booleanValue())
		{
			termSql.append(" and r.FHouseProperty != 'Attachment'");
		}
		
		
		if (preBiz != null)
		{
			if (preBiz.booleanValue())
			{
				// 开始时间
				if (termBeginDate != null)
				{
					termSql.append(" and r.FToSaleDate>=to_date('"
							+ termBeginDate + "')");
				}
				// 结束时间
				if (termEndDate != null)
				{
					termSql
							.append(" and to_date(to_char(r.FToSaleDate,'yyyy-mm-dd'))<=to_date('"
									+ termEndDate + "')");
				}
			} else
			{
				// 开始时间
				if (termBeginDate != null)
				{
					termSql.append(" and r.FToPurchaseDate>=to_date('"	+ termBeginDate + "')");
				}
				// 结束时间
				if (termEndDate != null)
				{
					termSql.append(" and to_date(to_char(r.FToPurchaseDate,'yyyy-mm-dd'))<=to_date('"+ termEndDate + "')");
				}
			}
		}
		else
		{
			// 开始时间
			if (termBeginDate != null)
			{
				termSql.append(" and r.FToPurchaseDate>=to_date('"	+ termBeginDate + "')");
			}
			// 结束时间
			if (termEndDate != null)
			{
				termSql.append(" and to_date(to_char(r.FToPurchaseDate,'yyyy-mm-dd'))<=to_date('"+ termEndDate + "')");
			}
		}
		

		// 判断查询维度
		String requestType = type;
		// 总套数
		if (StatisticRoomTypeEnum.TOTALSUM_VALUE.equalsIgnoreCase(type))
		{
			sql.appendSql(" select count(*) as amount from  t_she_room as r ");
			sql.appendSql(" where 1=1 ");
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("amount");
			}
		}
		// 总建筑面积
		else if (StatisticRoomTypeEnum.TOTALBUILDINGAREA_VALUE.equalsIgnoreCase(type))
		{
			sql.appendSql(" select sum(r.FBuildingArea) as totalBuildingArea from t_she_room as r ");
			sql.appendSql(" where 1=1 ");
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);
			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("totalBuildingArea");
			}
		}
		// 总套内面积
		else if (StatisticRoomTypeEnum.TOTALROOMAREA_VALUE.equalsIgnoreCase(type))
		{
			sql.appendSql(" select sum(r.FRoomArea) as totalRoomArea from t_she_room as r");
			sql.appendSql(" where 1=1 ");
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);
			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("totalRoomArea");
			}
		}
		// 总标准总价
		else if (StatisticRoomTypeEnum.TOTALSTANDARDTOTALPRICE_VALUE.equalsIgnoreCase(type))
		{
			sql.appendSql(" select sum(r.FStandardTotalAmount) as totalStandardTotalPrice from t_she_room as r ");
			sql.appendSql(" where 1=1 ");
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);
			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("totalStandardTotalPrice");
			}
		}
		// 总底价总价
		else if (StatisticRoomTypeEnum.TOTALBASETOTALPRICE_VALUE.equalsIgnoreCase(type))
		{
			sql.appendSql(" select sum(r.FBaseBuildingPrice * r.FBuildingArea) as totalBaseTotalPrice from t_she_room as r ");
			sql.appendSql(" where 1=1 ");
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);
			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("totalBaseTotalPrice");
			}
		}
		
		
		// 未售套数
		else if (StatisticRoomTypeEnum.UNSELLSUM_VALUE.equalsIgnoreCase(type))
		{
			sql.appendSql(" select count(*) as unSellSum from t_she_room as r ");
			sql.appendSql(unDealSql);
			
			sql.appendSql(termSql.toString());
			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("unSellSum");
			}
		}
		// 未售建筑面积
		else if (StatisticRoomTypeEnum.UNSELLBUILDINGAREA_VALUE.equalsIgnoreCase(type))
		{
			sql.appendSql(" select sum(r.FBuildingArea) as unSellBuildingArea from t_she_room as r ");
			sql.appendSql(unDealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("unSellBuildingArea");
			}
		}
		// 未售套内套内面积
		else if (StatisticRoomTypeEnum.UNSELLROOMAREA_VALUE.equalsIgnoreCase(type))
		{
			sql.appendSql(" select sum(r.FRoomArea) as unSellRoomArea from t_she_room as r ");
			sql.appendSql(unDealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("unSellRoomArea");
			}
		}
		// 未售标准总价
		else if (StatisticRoomTypeEnum.UNSELLSTANDARDTOTALPRICE_VALUE.equalsIgnoreCase(type))
		{
			sql.appendSql(" select sum(r.FStandardTotalAmount) as unSellStandardTotalPrice from t_she_room as r");
			sql.appendSql(unDealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("unSellStandardTotalPrice");
			}
		}
		// 未售底价总价
		else if (StatisticRoomTypeEnum.UNSELLBASETOTALPRICE_VALUE.equalsIgnoreCase(type))
		{
			sql.appendSql(" select sum(r.FBaseBuildingPrice * r.FBuildingArea) as unSellBaseTotalPrice from t_she_room as r");
			sql.appendSql(unDealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("unSellBaseTotalPrice");
			}
		}
		
		
		
		// 已售套数
		else if (StatisticRoomTypeEnum.SELLSUM_VALUE.equalsIgnoreCase(type))
		{
			sql.appendSql(" select count(*) as sellSum from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("sellSum");
			}
		}
		// 已售建筑面积
		else if (StatisticRoomTypeEnum.SELLBUILDINGAREA_VALUE.equalsIgnoreCase(type))
		{
			sql.appendSql(" select sum(r.FBuildingArea) as sellBuildingArea from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("sellBuildingArea");
			}
		}
		// 已售套内面积
		else if (StatisticRoomTypeEnum.SELLROOMAREA_VALUE.equalsIgnoreCase(type))
		{
			sql.appendSql(" select sum(r.FRoomArea) as sellRoomArea from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("sellRoomArea");
			}
		}
		
		// 补差建筑面积
		else if (StatisticRoomTypeEnum.COMPENSATEBUILDINGAREA_VALUE.equalsIgnoreCase(type))
		{
			sql.appendSql(" select sum(r.FActualBuildingArea-r.FBuildingArea) as compensateBuildingArea from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("compensateBuildingArea");
			}
		}

		// 补差套内面积
		else if (StatisticRoomTypeEnum.COMPENSATEROOMAREA_VALUE.equalsIgnoreCase(type))
		{
			sql.appendSql(" select sum(r.FActualRoomArea-r.FRoomArea) as compensateRoomArea from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("compensateRoomArea");
			}
		}
//		 已售底价总价
		else if (StatisticRoomTypeEnum.SELLBASETOTALPRICE_VALUE.equalsIgnoreCase(type))
		{
			sql.appendSql(" select sum(r.FBaseBuildingPrice * r.FBuildingArea) as sellBaseTotalPrice from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("sellBaseTotalPrice");
			}
		}
		// 已售标准总价
		else if (StatisticRoomTypeEnum.SELLSTANDARDTOTALPRICE_VALUE.equalsIgnoreCase(type))
		{
			sql.appendSql(" select sum(r.FStandardTotalAmount) as sellStandardTotalPrice from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("sellStandardTotalPrice");
			}
		}
		// 已售成交总价
		else if (StatisticRoomTypeEnum.SELLDEALTOTALPRICE_VALUE.equalsIgnoreCase(type))
		{
			sql.appendSql(" select sum(r.FDealTotalAmount) as sellDealTotalPrice from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("sellDealTotalPrice");
			}
		}
		//已售合同总价
		else if(StatisticRoomTypeEnum.SELLCONTRACTTOTALPRICE_VALUE.equalsIgnoreCase(type))
		{
			sql.appendSql(" select sum(p.FContractTotalAmount) as sellContractTotalPrice ");
			sql.appendSql(" from t_she_room as r inner join t_she_purchase as p on r.FLastPurchaseID = p.FID ");
			
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());
			
			IRowSet rowSet = sql.executeQuery(ServerCtx);
			while(rowSet.next())
			{
				result = rowSet.getBigDecimal("sellContractTotalPrice");
			}
			
		}
		//已售补差额
		else if(StatisticRoomTypeEnum.SELLCOMPENSATEPRICE_VALUE.equalsIgnoreCase(type))
		{
			sql.appendSql(" select sum(r.FAreaCompensateAmount) as sellCompensatePrice from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("sellCompensatePrice");
			}
			
		}
		// 已售销售总价
		else if (StatisticRoomTypeEnum.SELLSALETOTALPRICE_VALUE.equalsIgnoreCase(type))
		{
			sql.appendSql(" select sum(pur.FContractTotalAmount) contractTotalAmount from t_she_room r inner join t_she_purchase pur on r.flastpurchaseid=pur.fid ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			BigDecimal contractTotalAmount = FDCHelper.ZERO;
			while (rowSet.next())
			{
				contractTotalAmount = rowSet.getBigDecimal("contractTotalAmount");
			}
			if(contractTotalAmount == null)
				contractTotalAmount = FDCHelper.ZERO;
			
			
			sql = new FDCSQLBuilder();
			
			sql.appendSql(" select sum(r.FAreaCompensateAmount) as sellCompensatePrice from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet1 = sql.executeQuery(ServerCtx);

			BigDecimal sellCompensatePrice = FDCHelper.ZERO;
			while (rowSet1.next())
			{
				sellCompensatePrice = rowSet1.getBigDecimal("sellCompensatePrice");
			}
			if(sellCompensatePrice == null)
				sellCompensatePrice = FDCHelper.ZERO;
			
			result = contractTotalAmount.add(sellCompensatePrice);
		}
		//已售回款
		else if(StatisticRoomTypeEnum.SELLGATHERING_VALUE.equalsIgnoreCase(type))
		{       
			sql.appendSql(" select sum(cas.famount) sumRevAmount from t_cas_receivingbill as cas ");
		    sql.appendSql(" left join t_she_fdcreceivebill as fdc on cas.FFdcReceiveBillId = fdc.Fid ");
		    sql.appendSql(" left join t_she_room as r on fdc.FroomId = r.fid ");
		    sql.appendSql(" left join t_she_moneyDefine money on  fdc.FMoneyDefineId=money.fid ");
		        
		    sql.appendSql(dealSql);
		    sql.appendSql(termSql.toString());
		    sql.appendSql(" and money.FSysType = 'SalehouseSys' ");

		    IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("sumRevAmount");
			}
		}
		//累计欠款
		else if(StatisticRoomTypeEnum.ARREARAGESUM_VALUE.equalsIgnoreCase(type))
		{
			sql.appendSql(" select sum(pur.FContractTotalAmount) contractTotalAmount from t_she_room r inner join t_she_purchase pur on r.flastpurchaseid=pur.fid ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			BigDecimal contractTotalAmount = FDCHelper.ZERO;
			while (rowSet.next())
			{
				contractTotalAmount = rowSet.getBigDecimal("contractTotalAmount");
			}
			if(contractTotalAmount == null)
				contractTotalAmount = FDCHelper.ZERO;
			
			
			sql = new FDCSQLBuilder();
			
			sql.appendSql(" select sum(r.FAreaCompensateAmount) as sellCompensatePrice from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet1 = sql.executeQuery(ServerCtx);

			BigDecimal sellCompensatePrice = FDCHelper.ZERO;
			while (rowSet1.next())
			{
				sellCompensatePrice = rowSet1.getBigDecimal("sellCompensatePrice");
			}
			if(sellCompensatePrice == null)
				sellCompensatePrice = FDCHelper.ZERO;
			
			BigDecimal sellSaleTotalPrice = contractTotalAmount.add(sellCompensatePrice);
			
			
			sql = new FDCSQLBuilder();
			
		     
			sql.appendSql(" select sum(cas.famount) sumRevAmount from t_cas_receivingbill as cas ");
		    sql.appendSql(" left join t_she_fdcreceivebill as fdc on cas.FFdcReceiveBillId = fdc.Fid ");
		    sql.appendSql(" left join t_she_room as r on fdc.FroomId = r.fid ");
		    sql.appendSql(" left join t_she_moneyDefine money on  fdc.FMoneyDefineId=money.fid ");
		        
		    sql.appendSql(dealSql);
		    sql.appendSql(termSql.toString());
		    sql.appendSql(" and money.FSysType = 'SalehouseSys' ");
		    sql.appendSql(" and money.FMoneyType in ('PurchaseAmount','HouseAmount','FisrtAmount','CompensateAmount','LoanAmount','AccFundAmount') ");

		    IRowSet tempSet = sql.executeQuery(ServerCtx);

		    BigDecimal temp = FDCHelper.ZERO;
		    
			while (tempSet.next())
			{
				temp = tempSet.getBigDecimal("sumRevAmount");
			}
			
			if(sellSaleTotalPrice == null)
				sellSaleTotalPrice = FDCHelper.ZERO;
			
			if(temp == null)
				temp = FDCHelper.ZERO;
			
			result = sellSaleTotalPrice.subtract(temp);
			
			
		}

		
		return result;
	}


	public Context getServerCtx()
	{
		return ServerCtx;
	}

	public void setServerCtx(Context serverCtx)
	{
		ServerCtx = serverCtx;
	}

	public void initCalculateContext(ICalculateContextProvider context)
	{
		this.context = context;
		ServerCtx = this.context.getServerContext();

	}
	

	public boolean batchQuery(Map methods)
	{
		SortedParameterArray params = (SortedParameterArray) methods.get("fdc_statistic");
		BigDecimal temp = FDCHelper.ZERO;
		if (params != null)
		{
			for(int i=0;i<params.size();i++)
			{
			Parameter param = params.getParameter(i);
			Object[] obj = param.getArgs();

			
			type =(String) ((Variant) obj[0]).getValue(); 
			sellProjectNumber = (String) ((Variant) obj[1]).getValue();
			buildingSimapleName = (String) ((Variant) obj[2]).getValue();
			buildingPropertyNumber = (String) ((Variant) obj[3]).getValue();
			productTypeNumber = (String) ((Variant) obj[4]).getValue();
			orderState = (String) ((Variant) obj[5]).getValue();
			accessorialProperty = (Boolean)((Variant) obj[6]).getValue();
			preBiz = (Boolean) ((Variant) obj[7]).getValue();
			begingDate1 = (String) ((Variant) obj[8]).getValue();
			endDate1 = (String) ((Variant) obj[9]).getValue();
		
			try
			{
			     temp =  this.fdc_statistic(type,sellProjectNumber,buildingSimapleName,buildingPropertyNumber,
			    		 productTypeNumber,orderState,accessorialProperty, preBiz,begingDate1,endDate1);
			     if(temp!=null)
			        params.getParameter(i).setValue(temp);
			     else
			    	 params.getParameter(i).setValue(FDCHelper.ZERO);
			} catch (EASBizException e)
			{
				e.printStackTrace();
				return false;
			} catch (BOSException e)
			{
				e.printStackTrace();
				return false;
			} catch (SQLException e)
			{
				e.printStackTrace();
				return false;
			}
		}
			
		}
		

		return true;
	}

}
