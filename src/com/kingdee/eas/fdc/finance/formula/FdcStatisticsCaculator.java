package com.kingdee.eas.fdc.finance.formula;

import java.math.BigDecimal;
import java.sql.SQLException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.IMethodBatchQuery;
import com.kingdee.bos.ctrl.excel.model.struct.Parameter;
import com.kingdee.bos.ctrl.excel.model.util.SortedParameterArray;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;

import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;

import com.kingdee.eas.fdc.sellhouse.BuildingCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;

import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;

import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 房地产售楼统计函数
 * @author laiquan_luo
 *
 */
public class FdcStatisticsCaculator implements ICalculator, IMethodBatchQuery
{
	private Context ServerCtx = null;



	private String type = null;
	private String buildingProperty = null;
	private String building = null;
	private String orderState = null;
	private String begingDate = null;
	private String endDate = null;




	public BigDecimal fdc_statistic(String type, String buildingPropertyNumber,
			String buildingSimapleName, String orderState, String begingDate1,
			String endDate1) throws BOSException, EASBizException, SQLException
	{
		if (type == null || type.trim().length() < 1)
			throw new BOSException("统计维度参数不能为空！");
		if (buildingSimapleName == null
				|| buildingSimapleName.trim().length() < 1)
		{
			throw new BOSException("楼栋参数不能为空！");
		}
		// 取得ID
		Set numberSet = new HashSet();

		if (buildingPropertyNumber != null
				&& buildingPropertyNumber.toString().trim().length() > 0)
			buildingPropertyNumber = buildingPropertyNumber.replace('.', '!');

		String tempNumber[] = buildingPropertyNumber.split(";");
		for (int i = 0; i < tempNumber.length; i++)
		{
			numberSet.add(tempNumber[i].trim());
		}

		EntityViewInfo numberEvi = new EntityViewInfo();
		FilterInfo numberFilter = new FilterInfo();
		numberFilter.getFilterItems()
				.add(
						new FilterItemInfo("longNumber", numberSet,
								CompareType.INCLUDE));
		numberEvi.getSelector().add("id");
		numberEvi.setFilter(numberFilter);

		BuildingPropertyCollection buildingPropertyCollection = BuildingPropertyFactory
				.getLocalInstance(ServerCtx).getBuildingPropertyCollection(
						numberEvi);
		String tempProperty = "";
		if (buildingPropertyCollection != null)
		{
			for (int i = 0; i < buildingPropertyCollection.size(); i++)
			{
				tempProperty += buildingPropertyCollection.get(i).getId()
						.toString()
						+ ";";
			}
		}

		Set nameSet = new HashSet();
		String tempName[] = buildingSimapleName.split(";");
		for (int i = 0; i < tempName.length; i++)
		{
			nameSet.add(tempName[i].trim());
		}
		EntityViewInfo nameEvi = new EntityViewInfo();
		FilterInfo nameFilter = new FilterInfo();
		nameFilter.getFilterItems().add(
				new FilterItemInfo("simpleName", nameSet, CompareType.INCLUDE));
		nameEvi.getSelector().add("id");
		nameEvi.setFilter(nameFilter);

		BuildingCollection buildingCollection = BuildingFactory
				.getLocalInstance(ServerCtx).getBuildingCollection(nameEvi);
		String tempSimpleName = "";
		if (buildingCollection != null)
		{
			for (int i = 0; i < buildingCollection.size(); i++)
			{
				tempSimpleName += buildingCollection.get(i).getId().toString()
						+ ";";
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

		BigDecimal result = FDCHelper.ZERO;

		FDCSQLBuilder sql = new FDCSQLBuilder();
		StringBuffer termSql = new StringBuffer();

		String termBuilding = "";

		String termBuildingProperty = "";

		String termBeginDate = null;

		String termEndDate = null;

		// 房间未成交判断
		String unDealSql = " where (r.FSellState!='"
				+ RoomSellStateEnum.PURCHASE_VALUE + "' and r.FSellState!='"
				+ RoomSellStateEnum.SIGN_VALUE + "' and r.FSellState!='"
				+ RoomSellStateEnum.PREPURCHASE_VALUE + "')";

		// 房间成交判断
		String dealSql = " where (r.FSellState='"
				+ RoomSellStateEnum.PURCHASE_VALUE + "' or r.FSellState='"
				+ RoomSellStateEnum.SIGN_VALUE + "' or r.FSellState='"
				+ RoomSellStateEnum.PREPURCHASE_VALUE + "')";

		termBeginDate = begingDate1 == null || begingDate1.trim().length() < 1 ? null
				: begingDate1;
		termEndDate = endDate1 == null || endDate1.trim().length() < 1 ? null
				: endDate1;

		// DateTimeUtils.

		// 判断建筑性质
		if (buildingProperty != null)
		{
			String temp = new String();
			for (int i = 0; i < buildingProperty.length; i++)
			{
				temp += "'" + buildingProperty[i].trim() + "'" + ",";
			}
			termBuildingProperty = temp.trim().length() < 1 ? "" : temp
					.substring(temp.indexOf("'") + 1, temp.lastIndexOf("'"));

			// termSql.append(" inner join T_SHE_BuildingProperty as b on
			// r.FBuildingPropertyID=b.FID ");
			termSql.append(" and r.FBuildingPropertyID in('"
					+ termBuildingProperty + "') ");
		}
		// 判断楼栋
		if (building != null)
		{
			String temp = new String();
			for (int i = 0; i < building.length; i++)
			{
				temp += "'" + building[i].trim() + "'" + ",";
			}
			termBuilding = temp.trim().length() < 1 ? "" : temp.substring(temp
					.indexOf("'") + 1, temp.lastIndexOf("'"));

			termSql.append(" and r.FBuildingID in('" + termBuilding + "') ");
		}

		if (termBeginDate != null)
		{
			// termSql.append(" and
			// to_date(to_char(r.FToPurchaseDate,'yyyy-mm-dd'))>='"+termBeginDate+"'");
			// termBeginDate=DateTimeUtils.truncateDate(termBeginDate);
			termSql.append(" and r.FToPurchaseDate>=to_date('" + termBeginDate
					+ "')");

			// termSql.
			// sql.
		}

		if (termEndDate != null)
		{
			termSql
					.append(" and to_date(to_char(r.FToPurchaseDate,'yyyy-mm-dd'))<=to_date('"
							+ termEndDate + "')");
			// termSql.append(" and r.FToPurchaseDate<='"+termEndDate+"'");

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

		// 判断查询维度
		String requestType = type;
		// 总套数
		if (requestType.equalsIgnoreCase("totalRoomAmount"))
		{
			sql.appendSql(" select count(*) as amount from  t_she_room as r ");
			sql.appendSql(" where 1=1 ");
			sql.appendSql(termSql.toString());

			// IRowSet rowSet = sql.executeQuery();
			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("amount");
			}
		}
		// 总建筑面积
		else if (requestType.equalsIgnoreCase("totalBuildingArea"))
		{
			sql
					.appendSql(" select sum(r.FBuildingArea) as totalBuildingArea from t_she_room as r ");
			sql.appendSql(" where 1=1 ");
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);
			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("totalBuildingArea");
			}
		}
		// 总套内面积
		else if (requestType.equalsIgnoreCase("totalRoomArea"))
		{
			sql
					.appendSql(" select sum(r.FRoomArea) as totalRoomArea from t_she_room as r");
			sql.appendSql(" where 1=1 ");
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);
			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("totalRoomArea");
			}
		}
		// 总标准总价
		else if (requestType.equalsIgnoreCase("totalStandardTotalPrice"))
		{
			sql
					.appendSql(" select sum(r.FStandardTotalAmount) as totalStandardTotalPrice from t_she_room as r ");
			sql.appendSql(" where 1=1 ");
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);
			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("totalStandardTotalPrice");
			}
		}
		// 未成交套数
		else if (requestType.equalsIgnoreCase("unDealRoomAmount"))
		{
			sql
					.appendSql(" select count(*) as unDealAmount from t_she_room as r ");
			sql.appendSql(unDealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("unDealAmount");
			}
		}
		// 未成交建筑面积
		else if (requestType.equalsIgnoreCase("unDealBuildingArea"))
		{
			sql
					.appendSql(" select sum(r.FBuildingArea) as unDealBuildingArea from t_she_room as r ");
			sql.appendSql(unDealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("unDealBuildingArea");
			}
		}
		// 未成交套内面积
		else if (requestType.equalsIgnoreCase("unDealRoomArea"))
		{
			sql
					.appendSql(" select sum(r.FRoomArea) as unDealRoomArea from t_she_room as r ");
			sql.appendSql(unDealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("unDealRoomArea");
			}
		}
		// 未售标准总价
		else if (requestType.equalsIgnoreCase("unDealStandardTotalPrice"))
		{
			sql
					.appendSql(" select sum(r.FStandardTotalAmount) as unDealStandardTotalPrice from t_she_room as r");
			sql.appendSql(unDealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("unDealStandardTotalPrice");
			}
		}
		// 成交套数
		else if (requestType.equalsIgnoreCase("dealRoomAmount"))
		{
			sql
					.appendSql(" select count(*) as dealRoomAmount from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("dealRoomAmount");
			}
		}
		// 成交建筑面积
		else if (requestType.equalsIgnoreCase("dealBuildingArea"))
		{
			sql
					.appendSql(" select sum(r.FBuildingArea) as dealBuildingArea from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("dealBuildingArea");
			}
		}
		// 成交套内面积
		else if (requestType.equalsIgnoreCase("dealRoomArea"))
		{
			sql
					.appendSql(" select sum(r.FRoomArea) as dealRoomArea from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("dealRoomArea");
			}
		}
		// 成交标准总价
		else if (requestType.equalsIgnoreCase("dealStandardTotalPrice"))
		{
			sql
					.appendSql(" select sum(r.FStandardTotalAmount) as dealStandardTotalPrice from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("dealStandardTotalPrice");
			}
		}
		// 成交总价
		else if (requestType.equalsIgnoreCase("totalDealTotalPrice"))
		{
			sql
					.appendSql(" select sum(r.FDealTotalAmount) as totalDealTotalPrice from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("totalDealTotalPrice");
			}
		}
		// 总销售额
		else if (requestType.equalsIgnoreCase("totalSellAmount"))
		{
			sql
					.appendSql(" select sum(r.FSellAmount) as totalSellAmount from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("totalSellAmount");
			}
		}
		// 总面积补差款
		else if (requestType.equalsIgnoreCase("totalAreaCompensateAmount"))
		{
			sql
					.appendSql(" select sum(r.FAreaCompensateAmount) as totalAreaCompensateAmount from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("totalAreaCompensateAmount");
			}
		}
		// 总建筑补差面积
		else if (requestType.equalsIgnoreCase("totalBuildingCompensateArea"))
		{
			sql
					.appendSql(" select sum(r.FActualBuildingArea-r.FBuildingArea) as totalBuildingCompensateArea from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("totalBuildingCompensateArea");
			}
		}

		// 总套内补差面积
		else if (requestType.equalsIgnoreCase("totalRoomCompensateArea"))
		{
			sql
					.appendSql(" select sum(r.FActualRoomArea-r.FRoomArea) as totalRoomCompensateArea from t_she_room as r ");
			sql.appendSql(dealSql);
			sql.appendSql(termSql.toString());

			IRowSet rowSet = sql.executeQuery(ServerCtx);

			while (rowSet.next())
			{
				result = rowSet.getBigDecimal("totalRoomCompensateArea");
			}
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

	}

	public boolean batchQuery(Map methods)
	{
		SortedParameterArray params = (SortedParameterArray) methods
				.get("fdc_statistic");
		if (params != null)
		{
			Parameter param = params.getParameter(0);
			Object[] obj = param.getArgs();

			type = (String) ((Variant) obj[0]).getValue();
			buildingProperty = (String) ((Variant) obj[1]).getValue();
			building = (String) ((Variant) obj[2]).getValue();
			orderState = (String) ((Variant) obj[3]).getValue();
			begingDate = (String) ((Variant) obj[4]).getValue();
			endDate = (String) ((Variant) obj[5]).getValue();
		}

		
			try
			{
				this.fdc_statistic(type,buildingProperty,building,orderState,begingDate,endDate);
			} catch (EASBizException e)
			{
				// TODO 自动生成 catch 块
				e.printStackTrace();
			} catch (BOSException e)
			{
				// TODO 自动生成 catch 块
				e.printStackTrace();
			} catch (SQLException e)
			{
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
		

		return false;
	}

}
