package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.kingdee.bos.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.jdbc.rowset.IRowSet;

import java.util.HashMap;
import java.util.Map;

public class SellStatRptFacadeControllerBean extends AbstractSellStatRptFacadeControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.SellStatRptFacadeControllerBean");
	
	//签约
	private  static final String ISSIGN="sign";

	protected Map _getSellStatRptData(Context ctx, Map map)
			throws BOSException, EASBizException {
		Map rptMap = new HashMap();
		try {
			// 获取总套数、总面积、总均价、总标准价
			Map areaMap = getAllAmount(ctx, map);

			// 获取保留套数、保留面积、保留标准总价
			Map keepMap = getKeepAmount(ctx, map);

			// 获取撤盘套数、撤盘面积、撤盘标准总价
			Map quitMap = getQuitAmount(ctx, map);

			// 获取销售套数，销售面积 ，底价总价，标准总价，优惠额、成交总价
			Map sellMap = getSellAmount(ctx, map);

			// 获取应收补差金额
			Map sellMap1 = getSellAmount1(ctx, map);

			// 获取实收补差金额
			Map sellMap2 = getSellAmount2(ctx, map);

			// 获取签约合同总价、签约合同套数、签约合同面积、签约合同成交总价
			Map signMap = getSignAmount(ctx, map);

			// 获取销售未签约套数、销售未签约面积、销售未签约合同总价、销售未签约成交总价
			Map unsignMap = getUnsignAmount(ctx, map);

			// 获取定金类回款
			Map purMap = getPurchaseAmount(ctx, map);

			// 获取销售欠款
			Map purMap1 = getPurchaseAmount1(ctx, map);

			// 获取非定金类回款
			Map purMap2 = getPurchaseAmount2(ctx, map);

			// 获取合同总价
			Map conMap = getContractAmount(ctx, map);

			// 获取 销售回款
			IRowSet recSet = (IRowSet)getReceiveAmount(ctx, map);

			rptMap.put("areaMap", areaMap);
			rptMap.put("keepMap", keepMap);
			rptMap.put("quitMap", quitMap);
			rptMap.put("sellMap", sellMap);
			rptMap.put("sellMap1", sellMap1);
			rptMap.put("sellMap2", sellMap2);
			rptMap.put("signMap", signMap);
			rptMap.put("unsignMap", unsignMap);
			rptMap.put("purMap", purMap);
			rptMap.put("purMap1", purMap1);
			rptMap.put("purMap2", purMap2);
			rptMap.put("conMap", conMap);
			rptMap.put("recSet", recSet);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rptMap;
	}

	/**
	 * 获取总套数、总面积、总均价、总标准价
	 */
	private Map getAllAmount(Context ctx, Map map) throws BOSException,
			SQLException {

		Map areaMap1 = new HashMap();

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select FBuildingID buildingId, count(*) termAllCount, sum( case when "
						+ map.get("checkArea")
						+ " is null then 0 else "
						+ map.get("checkArea")
						+ " end) termAllArea, sum( case when FStandardTotalAmount is null then 0 else FStandardTotalAmount end) termAllPrice from T_SHE_Room room ");
		// builder.appendSql(" where FIsForSHE=1 ");

		Boolean includeAttach = (Boolean) (map.get("IncludeAttach")); // 是否包含附属房产
		if (!includeAttach.booleanValue()) {
			//builder.appendSql(" and room.FHouseProperty != '"+HousePropertyEnum
			// .ATTACHMENT_VALUE+"' ");
			builder.appendSql(" where room.FHouseProperty = '"
					+ HousePropertyEnum.NOATTACHMENT_VALUE + "' ");
		}

		builder.appendSql(" group by FBuildingID ");
		IRowSet termKeepSet = builder.executeQuery(ctx);

		while (termKeepSet.next()) {
			String buildingId = termKeepSet.getString("buildingId");
			Map areaMap = new HashMap();

			areaMap.put("termAllCount", new Integer(termKeepSet
					.getInt("termAllCount")));

			BigDecimal termAllArea = termKeepSet.getBigDecimal("termAllArea");
			if (termAllArea == null) {
				termAllArea = FDCHelper.ZERO;
			}
			areaMap.put("termAllArea", termAllArea);

			BigDecimal termAllPrice = termKeepSet.getBigDecimal("termAllPrice");
			if (termAllPrice == null) {
				termAllPrice = FDCHelper.ZERO;
			}
			areaMap.put("termAllPrice", termAllPrice);

			areaMap1.put(buildingId, areaMap);
		}

		return areaMap1;
	}

	/**
	 * 获取保留套数、保留面积、保留标准总价
	 */
	private Map getKeepAmount(Context ctx, Map map) throws BOSException,
			SQLException {

		Map keepMap1 = new HashMap();

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select FBuildingID, count(*) termKeepCount, sum( case when "
						+ map.get("checkArea")
						+ " is null then 0 else "
						+ map.get("checkArea")
						+ " end) termKeepArea," 
						+ " sum(case when FStandardTotalAmount is null then 0 else FStandardTotalAmount end) termKeepStandardTotalAmount " 
						+ " from T_SHE_Room room "
						+ " inner join T_SHE_RoomKeepDownBill keep on room.FLastKeepDownBillID=keep.FID ");
		builder.appendSql(" where keep.FBizDate is not null ");

		Boolean includeAttach = (Boolean) (map.get("IncludeAttach")); // 是否包含附属房产
		if (!includeAttach.booleanValue()) {
			builder.appendSql(" and room.FHouseProperty = '"
					+ HousePropertyEnum.NOATTACHMENT_VALUE + "' ");
		}

		builder.appendSql(" AND FIsForSHE=1 ");
		builder.appendSql(" group by FBuildingID ");
		IRowSet termKeepSet = builder.executeQuery(ctx);
		while (termKeepSet.next()) {

			Map keepMap = new HashMap();

			String buidlingId = termKeepSet.getString("FBuildingID");

			keepMap.put("termKeepCount", termKeepSet.getInt("termKeepCount")+ "");

			BigDecimal termKeepArea = termKeepSet.getBigDecimal("termKeepArea");
			if (termKeepArea == null) {
				termKeepArea = FDCHelper.ZERO;
			}
			keepMap.put("termKeepArea", termKeepArea);

			BigDecimal termKeepStandardTotalAmount = termKeepSet
					.getBigDecimal("termKeepStandardTotalAmount");
			if (termKeepStandardTotalAmount == null) {
				termKeepStandardTotalAmount = FDCHelper.ZERO;
			}
			keepMap.put("termKeepStandardTotalAmount",
					termKeepStandardTotalAmount);

			keepMap1.put(buidlingId, keepMap);
		}
		return keepMap1;
	}

	/**
	 * 获取撤盘套数、撤盘面积、撤盘标准总价
	 */
	private Map getQuitAmount(Context ctx, Map map) throws BOSException,
			SQLException {

		Map quitMap1 = new HashMap();

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select room.FBuildingID FBuildingID, count(room.FID) termQuitCount, sum( case when room."
						+ map.get("checkArea")
						+ " is null then 0 else room."
						+ map.get("checkArea")
						+ " end) termQuitArea," 
						+ " sum( case when room.FStandardTotalAmount is null then 0 else room.FStandardTotalAmount end) termQuitStandardTotalAmount "
						+ " from T_SHE_SellOrderRoomEntry quit "
						+ " inner join T_SHE_Room room on quit.FRoomID=room.FID ");
		builder.appendSql(" where FQuitOrderDate is not null");

		Boolean includeAttach = (Boolean) (map.get("IncludeAttach")); // 是否包含附属房产
		if (!includeAttach.booleanValue()) {
			builder.appendSql(" and room.FHouseProperty = '"
					+ HousePropertyEnum.NOATTACHMENT_VALUE + "' ");
		}

		builder.appendSql("  AND  room.FIsForSHE=1 ");
		builder.appendSql(" group by FBuildingID ");
		IRowSet termQuitSet = builder.executeQuery(ctx);
		while (termQuitSet.next()) {

			Map quitMap = new HashMap();

			String buidlingId = termQuitSet.getString("FBuildingID");

			quitMap.put("termQuitCount", termQuitSet.getInt("termQuitCount")
					+ "");

			BigDecimal termQuitArea = termQuitSet.getBigDecimal("termQuitArea");
			if (termQuitArea == null) {
				termQuitArea = FDCHelper.ZERO;
			}
			quitMap.put("termQuitArea", termQuitArea);

			BigDecimal termQuitStandardTotalAmount = termQuitSet
					.getBigDecimal("termQuitStandardTotalAmount");
			if (termQuitStandardTotalAmount == null) {
				termQuitStandardTotalAmount = FDCHelper.ZERO;
			}
			quitMap.put("termQuitStandardTotalAmount",
					termQuitStandardTotalAmount);

			quitMap1.put(buidlingId, quitMap);
		}
		return quitMap1;
	}

	/**
	 * 获取 销售套数，销售面积 ，底价总价，标准总价，成交总价
	 * */
	private Map getSellAmount(Context ctx, Map map) throws BOSException,
			SQLException {

		Map sellMap1 = new HashMap();

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
//		String area = map.get("checkArea").toString();
		
		builder.appendSql("select FBuildingId,sum(totalCount) totalCount,sum(termSumBaseAmount) termSumBaseAmount,sum(mergeAmount) mergeAmount,").appendSql("\t\n");
		builder.appendSql("sum(buildingArea) buildingArea ,sum(dealTotalAmount)  dealTotalAmount,sum(standardTotalAmount) standardTotalAmount from (").appendSql("\t\n");
		builder.appendSql("select room.FBuildingId FBuildingId,count(*) totalCount,sum( " 
				+ " (case when room.FIsCalByRoomArea=0 " 
				+ " then (case when room.FBuildingArea is null then 0 else room.FBuildingArea end )").appendSql("\t\n");
		builder.appendSql(" else (case when room.FRoomArea is null then 0 else room.FRoomArea end)").appendSql("\t\n");
		builder.appendSql("end) * (case when room.FIsCalByRoomArea=0 then (case when room.FBaseBuildingPrice is null then 0 else room.FBaseBuildingPrice end) " 
				+ " else (case when room.FBaseRoomPrice is null then 0 else room.FBaseRoomPrice end) end)) termSumBaseAmount,").appendSql("\t\n");
//		if(area.indexOf("Building") != -1){
//			builder.appendSql("end) * (case when room.FBaseBuildingPrice is null then 0 else room.FBaseBuildingPrice end)) termSumBaseAmount,").appendSql("\t\n");
//		}else{
//			builder.appendSql("end) * (case when room.FBaseRoomPrice is null then 0 else room.FBaseRoomPrice end)) termSumBaseAmount,").appendSql("\t\n");
//		}
		builder.appendSql("sum( case when purchase.FSellType='PreSell' then room.FBuildingArea else").appendSql("\t\n");
		builder.appendSql("case when  purchase.FSellType='LocaleSell'  then room.FActualBuildingArea  else 0 end  end) buildingArea,").appendSql("\t\n");
		builder.appendSql("sum( case when room.FStandardTotalAmount is null then 0 else room.FStandardTotalAmount end) standardTotalAmount ,").appendSql("\t\n");
		builder.appendSql("sum( case when purchase.FDealAmount is null then room.FStandardTotalAmount else purchase.FDealAmount end) dealTotalAmount, ").appendSql("\t\n");
		builder.appendSql("sum(0) mergeAmount").appendSql("\t\n");
		builder.appendSql("from T_SHE_Purchase purchase left join T_SHE_Room room on purchase.froomid = room.fid ").appendSql("\t\n");
//		builder.appendSql("left join T_SHE_PurchaseRoomAttachEntry roomAttach on purchase.FID = roomAttach.FHeadID").appendSql("\t\n");
		builder.appendSql("where room.FIsForSHE=1   ").appendSql("\t\n");
		//and  purchase.fid= room.FLastPurchaseID
		fillterSQL(builder,map);
		
		builder.appendSql(" group by FBuildingId ");
		
		Boolean includeAttach = (Boolean) (map.get("IncludeAttach")); // 是否包含附属房产
		if (includeAttach.booleanValue()) {
			builder.appendSql("union ").appendSql("\t\n");
			builder.appendSql("select room.FBuildingId FBuildingId,count(*) totalCount,sum( (case when room.FIsCalByRoomArea=0 "
					+ " then (case when room.FBuildingArea is null then 0 else room.FBuildingArea end )").appendSql("\t\n");
			builder.appendSql(" else (case when room.FRoomArea is null then 0 else room.FRoomArea end)").appendSql("\t\n");
			builder.appendSql("end) * (case when room.FIsCalByRoomArea=0 then (case when room.FBaseBuildingPrice is null then 0 else room.FBaseBuildingPrice end) " 
					+ " else (case when room.FBaseRoomPrice is null then 0 else room.FBaseRoomPrice end) end)) termSumBaseAmount,").appendSql("\t\n");
			builder.appendSql("sum( case when purchase.FSellType='PreSell' then room.FBuildingArea else").appendSql("\t\n");
			builder.appendSql("case when  purchase.FSellType='LocaleSell'  then room.FActualBuildingArea  else 0 end  end) buildingArea,").appendSql("\t\n");
			builder.appendSql("sum( case when room.FStandardTotalAmount is null then 0 else room.FStandardTotalAmount end) standardTotalAmount ,").appendSql("\t\n");
			builder.appendSql("sum(0) dealTotalAmount, ").appendSql("\t\n");
			builder.appendSql("sum( case when attach.FMergeAmount is null then 0 else attach.FMergeAmount end) mergeAmount ").appendSql("\t\n");
			
			builder.appendSql(" from t_she_purchase purchase").appendSql("\t\n");
			builder.appendSql(" left join T_SHE_PurchaseRoomAttachEntry attach on attach.fheadid = purchase.fid").appendSql("\t\n");
			builder.appendSql("left join T_SHE_RoomAttachmentEntry roomAttach on attach.FAttachmentEntryID = roomAttach.fid").appendSql("\t\n");
			builder.appendSql("left join t_she_room ROOM on roomAttach.froomid = ROOM.fid").appendSql("\t\n");
			builder.appendSql("where room.FIsForSHE=1").appendSql("\t\n");
			
			fillterSQL(builder,map);
			builder.appendSql(" group by FBuildingId ");
		}
		builder.appendSql(")ffff").appendSql("\t\n");
		builder.appendSql(" group by ffff.FBuildingId ");
		
		IRowSet termSellSet = builder.executeQuery(ctx);
		while (termSellSet.next()) {

			Map sellMap = new HashMap();

			String buidlingId = termSellSet.getString("FBuildingId");

			sellMap.put("termSumCount", new Integer(termSellSet.getInt("totalCount")));

			sellMap.put("termSumArea", termSellSet.getBigDecimal("buildingArea"));

			BigDecimal termSellSumAmount = termSellSet.getBigDecimal("standardTotalAmount");
			BigDecimal dealTotalAmount = termSellSet.getBigDecimal("dealTotalAmount");
			BigDecimal termSumBaseAmount = termSellSet.getBigDecimal("termSumBaseAmount");
//			BigDecimal mergeAmount = termSellSet.getBigDecimal("mergeAmount");//附属房产并入金额
			
			if (includeAttach.booleanValue()) {
//				termSellSumAmount = termSellSumAmount.add(mergeAmount);
			////2010-07-02 michael  
				// 勾选附属房产时，成交总价也不用计入，只有合同总价需计附属房产
				//		dealTotalAmount = dealTotalAmount.add(mergeAmount);  //
			////end  
//				termSumBaseAmount = termSumBaseAmount.add(mergeAmount);
			}

			sellMap.put("termSumAmount", termSellSumAmount);

			sellMap.put("termDealTotalAmount", dealTotalAmount);

			sellMap.put("termSumBaseAmount", termSumBaseAmount);

			sellMap1.put(buidlingId, sellMap);
		}
		
		return sellMap1;
	}
	
	//应收补差金额
	private Map getSellAmount1(Context ctx, Map map) throws BOSException,
			SQLException {

		Map sellMap1 = new HashMap();

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		// 补差金额
		builder.appendSql("select room.FBuildingId FBuildingId,sum( case when roomArea.FCompensateAmount is null then 0 else roomArea.FCompensateAmount end) areaCompensateAmount "
//				+ " from T_SHE_AreaCompensateRevList revList" 
				+ " from T_SHE_RoomAreaCompensate roomArea  "
				+ " left join T_SHE_Room room on roomArea.FRoomID = room.FID "
				+ "LEFT OUTER JOIN T_SHE_Purchase AS PURCHASE ON ROOM.FID = PURCHASE.FRoomId "
				+ " where room.FIsForSHE=1 and roomArea.FCompensateState in ('COMAUDITTED','COMRECEIVED') ");
		
		fillterSQL(builder, map);

		Boolean includeAttach = (Boolean) (map.get("IncludeAttach")); // 是否包含附属房产
//		Boolean preIntoSale = (Boolean) (map.get("PreIntoSale")); // 预定业务进入销售统计
//		Boolean showAll = (Boolean) (map.get("ShowAll")); // 是否显示全部
		if (!includeAttach.booleanValue()) {
			builder.appendSql(" and room.FHouseProperty = '"
					+ HousePropertyEnum.NOATTACHMENT_VALUE + "' ");
		}

		// 根据过滤界面的日期过滤
//		if (!showAll.booleanValue()) {//业务日期
//				builder.appendSql(" and roomArea.FCompensateDate >= {ts '"
//						+ map.get("beginDate")
//						+ "'} and roomArea.FCompensateDate < {ts '"
//						+ map.get("endDate") + "'} ");
//		} else {
//			//业务日期
//			builder.appendSql(" and roomArea.FBookedDate is not null ");
//		}

		builder.appendSql(" group by FBuildingId ");

		IRowSet areaCompensateAmountSet = builder.executeQuery(ctx);

		while (areaCompensateAmountSet.next()) {

			Map sellMap = new HashMap();

			String buidlingId = areaCompensateAmountSet
					.getString("FBuildingId");

			BigDecimal termAreaCompensateAmount = areaCompensateAmountSet
					.getBigDecimal("areaCompensateAmount");
			if (termAreaCompensateAmount == null) {
				termAreaCompensateAmount = FDCHelper.ZERO;
			}
			sellMap.put("termAreaCompensateAmount", termAreaCompensateAmount);

			sellMap1.put(buidlingId, sellMap);
		}
		return sellMap1;
	}
	
	//实收补差金额
	private Map getSellAmount2(Context ctx, Map map) throws BOSException,
			SQLException {

		Map sellMap1 = new HashMap();

		FDCSQLBuilder builder3 = new FDCSQLBuilder(ctx);
		// 得到补差款
		builder3.clear();
		
		// 补差金额
		builder3.appendSql("select room.FBuildingID FBuildingID,").appendSql("\t\n");
		builder3.appendSql(" sum( case when revList.FActRevAmount is null then 0 else revList.FActRevAmount end) revAmount, ").appendSql("\t\n");
		builder3.appendSql(" sum( case when revList.FHasTransferredAmount is null then 0 else revList.FHasTransferredAmount end) ferAmount, ").appendSql("\t\n");
		builder3.appendSql(" sum( case when revList.FHasRefundmentAmount is null then 0 else revList.FHasRefundmentAmount end) refAmount, ").appendSql("\t\n");
		builder3.appendSql(" sum( case when revList.FHasAdjustedAmount is null then 0 else revList.FHasAdjustedAmount end) adjAmount ").appendSql("\t\n");
		builder3.appendSql(" from T_SHE_AreaCompensateRevList revList").appendSql("\t\n"); 
		builder3.appendSql(" left join T_SHE_RoomAreaCompensate roomArea on revList.FHeadID = roomArea.FID ").appendSql("\t\n");
		builder3.appendSql(" left join T_SHE_Room room on roomArea.FRoomID = room.FID ").appendSql("\t\n");
		builder3.appendSql(" LEFT OUTER JOIN T_SHE_Purchase AS PURCHASE ON ROOM.FID = PURCHASE.FRoomId \n");
		builder3.appendSql(" where room.FIsForSHE=1 ").appendSql("\t\n");;
		
		fillterSQL(builder3, map);
		
		builder3.appendSql(" group by FBuildingID ");
		
//		builder3.appendSql("SELECT  room.FBuildingID FBuildingID ,case when FDCEntry.FRevAmount is null then 0 else  FDCEntry.FRevAmount  end revAmount \n");
//
//		builder3.appendSql("FROM T_BDC_FDCReceivingBillEntry AS FDCEntry \n");
//		builder3.appendSql("LEFT OUTER JOIN T_BDC_FDCReceivingBill AS FDC ON FDCEntry.FHeadID = FDC.FID \n"); 
//		
//		builder3.appendSql("inner JOIN T_SHE_Room AS ROOM ON FDC.FRoomID = ROOM.FID \n"); 
//		builder3.appendSql("LEFT OUTER JOIN T_SHE_Purchase AS PURCHASE ON ROOM.FID = PURCHASE.FRoomId \n"); 
//		builder3.appendSql("LEFT OUTER JOIN T_SHE_MoneyDefine AS MoneyDefine ON FDCEntry.FMoneyDefineID = MoneyDefine.FID \n");
//		builder3.appendSql("where  MoneyDefine.FMoneyType='"+MoneyTypeEnum.COMPENSATEAMOUNT_VALUE+"' \n");
		
		IRowSet termbckSet = builder3.executeQuery(ctx);

		while (termbckSet.next()) {

			Map sellMap = new HashMap();

			String buidlingId = termbckSet.getString("FBuildingID");

			BigDecimal YSamount1 = termbckSet.getBigDecimal("revAmount"); // 已收金额
			BigDecimal YZamount1 = termbckSet.getBigDecimal("ferAmount"); // 已转金额
			BigDecimal YQamount1 = termbckSet.getBigDecimal("refAmount"); // 已退金额
			BigDecimal YTamount1 = termbckSet.getBigDecimal("adjAmount"); // 已调金额

			BigDecimal SSamount = YSamount1.subtract(YZamount1).subtract(YQamount1).subtract(YTamount1);
//			BigDecimal SSamount  = YSamount1 ;
			sellMap.put("termAreaComAmount", SSamount);

			sellMap1.put(buidlingId, sellMap);
		}
		return sellMap1;
	}

	/**
	 * 获取 签约合同套数、签约合同面积、签约合同总价、签约合同成交总价
	 * */
	private Map getSignAmount(Context ctx, Map map) throws BOSException,
			SQLException {

		Map signMap1 = new HashMap();

		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);


		Boolean includeAttach = (Boolean) (map.get("IncludeAttach")); // 是否包含附属房产
		
		builder.appendSql("select FBuildingId,sum(termSignCount) termSignCount,sum(termSignArea) termSignArea,sum(termSignTotalAmount) termSignTotalAmount,").appendSql("\t\n");
		builder.appendSql("sum(termSignDealTotalAmount) termSignDealTotalAmount,sum(mergeAmount) mergeAmount from (").appendSql("\t\n");
		builder.appendSql("select room.FBuildingId FBuildingId,count(room.FID) termSignCount,").appendSql("\t\n");
//		builder.appendSql("sum( case when room.FSaleArea is null then 0 else room.FSaleArea end) termSignArea,").appendSql("\t\n");
		builder.appendSql("sum( case when purchase.FSellType='PreSell' then room.FBuildingArea else").appendSql("\t\n");
		builder.appendSql("case when  purchase.FSellType='LocaleSell'  then room.FActualBuildingArea  else 0 end  end) termSignArea,").appendSql("\t\n");
		if (includeAttach.booleanValue()) {
			builder.appendSql("sum( case when purchase.FContractTotalAmount is null then 0 else purchase.FContractTotalAmount end) termSignTotalAmount,").appendSql("\t\n");
		}else{
			builder.appendSql("sum( case when purchase.FContractTotalAmount is null then 0 else purchase.FContractTotalAmount end)" 
					+ " - sum( case when purchase.FAttachmentAmount is null then 0 else purchase.FAttachmentAmount end) termSignTotalAmount,").appendSql("\t\n");
		}
		builder.appendSql("sum( case when purchase.FDealAmount is null then 0 else purchase.FDealAmount end) termSignDealTotalAmount, ").appendSql("\t\n");
		builder.appendSql("sum(0) mergeAmount").appendSql("\t\n");
		builder.appendSql("from T_SHE_Purchase purchase left join T_SHE_Room room on purchase.FRoomID = room.FID ").appendSql("\t\n");
		builder.appendSql("where room.FIsForSHE=1 and purchase.FToSignDate is not null ").appendSql("\t\n");
		
		fillterSQL(builder,map,ISSIGN);
		
		builder.appendSql(" group by FBuildingId ");
		
		if (includeAttach.booleanValue()) {
			builder.appendSql("union ").appendSql("\t\n");
			builder.appendSql("select room.FBuildingId FBuildingId,count(room.FID) termSignCount,").appendSql("\t\n");
			builder.appendSql("sum( case when purchase.FSellType='PreSell' then room.FBuildingArea else").appendSql("\t\n");
			builder.appendSql("case when  purchase.FSellType='LocaleSell'  then room.FActualBuildingArea  else 0 end  end) termSignArea,").appendSql("\t\n");
//			builder.appendSql("sum( case when purchase.FContractTotalAmount is null then 0 else purchase.FContractTotalAmount end) termSignTotalAmount,").appendSql("\t\n");
//			builder.appendSql("sum( case when purchase.FDealAmount is null then 0 else purchase.FDealAmount end) termSignDealTotalAmount, ").appendSql("\t\n");
			builder.appendSql("sum(0) termSignTotalAmount,sum(0) termSignDealTotalAmount,").appendSql("\t\n");
			builder.appendSql("sum( case when attach.FMergeAmount is null then 0 else attach.FMergeAmount end) mergeAmount ").appendSql("\t\n");
			builder.appendSql("from T_SHE_PurchaseRoomAttachEntry attach").appendSql("\t\n");
			builder.appendSql("left join T_SHE_RoomAttachmentEntry roomAttach on attach.FAttachmentEntryID = roomAttach.fid").appendSql("\t\n");
			builder.appendSql("left join t_she_purchase purchase on attach.fheadid = purchase.fid").appendSql("\t\n");
			builder.appendSql("left join t_she_room ROOM on roomAttach.froomid = ROOM.fid").appendSql("\t\n");
			builder.appendSql("where room.FIsForSHE=1 and purchase.FToSignDate is not null ").appendSql("\t\n");
			
			fillterSQL(builder, map, ISSIGN);
			
			builder.appendSql(" group by FBuildingId ");
		}
		builder.appendSql(")ffff").appendSql("\t\n");
		builder.appendSql(" group by ffff.FBuildingId ");
		

		IRowSet termSignSet = builder.executeQuery(ctx);
		while (termSignSet.next()) {

			Map signMap = new HashMap();

			String buidlingId = termSignSet.getString("FBuildingId");

			signMap.put("termSignCount", new Integer(termSignSet.getInt("termSignCount")));
	
			signMap.put("termSignArea", termSignSet.getBigDecimal("termSignArea"));
			
			BigDecimal termSignTotalAmount = termSignSet.getBigDecimal("termSignTotalAmount");
			BigDecimal termSignDealTotalAmount = termSignSet.getBigDecimal("termSignDealTotalAmount");
			BigDecimal mergeAmount = termSignSet.getBigDecimal("mergeAmount");//附属房产并入金额
			
			if (includeAttach.booleanValue()) {
				termSignDealTotalAmount = termSignDealTotalAmount.add(mergeAmount);
			}

			signMap.put("termSignTotalAmount", termSignTotalAmount);

			signMap.put("termSignDealTotalAmount", termSignDealTotalAmount);

			signMap1.put(buidlingId, signMap);
		}
		return signMap1;
	}

	/**
	 * 获取销售未签约套数、销售未签约面积、销售未签约合同总价、销售未签约成交总价
	 */
	private Map getUnsignAmount(Context ctx, Map map) throws BOSException,
			SQLException {

		Map unsignMap1 = new HashMap();

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		
		Boolean includeAttach = (Boolean) (map.get("IncludeAttach")); // 是否包含附属房产
		
		builder.appendSql("select FBuildingId,sum(termUnsignCount) termUnsignCount,sum(termUnsignArea) termUnsignArea,sum(termUnsignStandardTotalAmount) termUnsignStandardTotalAmount,").appendSql("\t\n");
		builder.appendSql("sum(termUnsignDealTotalAmount) termUnsignDealTotalAmount,sum(mergeAmount) mergeAmount from (").appendSql("\t\n");
		builder.appendSql("select room.FBuildingId FBuildingId,count(room.FID) termUnsignCount,").appendSql("\t\n");
		builder.appendSql("sum( case when purchase.FSellType='PreSell' then room.FBuildingArea else").appendSql("\t\n");
		builder.appendSql("case when  purchase.FSellType='LocaleSell'  then room.FActualBuildingArea  else 0 end  end) termUnsignArea,").appendSql("\t\n");
		if (includeAttach.booleanValue()) {
			builder.appendSql("sum( case when purchase.FContractTotalAmount is null then purchase.FStandardTotalAmount else purchase.FContractTotalAmount end) termUnsignStandardTotalAmount,").appendSql("\t\n");
		}else{
			builder.appendSql("sum( case when purchase.FContractTotalAmount is null then purchase.FStandardTotalAmount else purchase.FContractTotalAmount end)" 
					+ " - sum( case when purchase.FAttachmentAmount is null then 0 else purchase.FAttachmentAmount end) termUnsignStandardTotalAmount,").appendSql("\t\n");
		}
		builder.appendSql("sum( case when purchase.FDealAmount is null then purchase.FStandardTotalAmount else purchase.FDealAmount end) termUnsignDealTotalAmount, ").appendSql("\t\n");
		builder.appendSql("sum(0) mergeAmount").appendSql("\t\n");
		builder.appendSql("from T_SHE_Purchase purchase left join T_SHE_Room room on purchase.FRoomID = room.FID ").appendSql("\t\n");
//		builder.appendSql("where room.FIsForSHE=1 and  purchase.FToSignDate is  null").appendSql("\t\n");
		builder.appendSql("where room.FIsForSHE=1 ").appendSql("\t\n");
		
		fillterSQL(builder,map,"unSign");
		
		builder.appendSql(" group by FBuildingId ");
		
		if (includeAttach.booleanValue()) {
			builder.appendSql("union ").appendSql("\t\n");
			builder.appendSql("select room.FBuildingId FBuildingId,count(room.FID) termUnsignCount,").appendSql("\t\n");
			builder.appendSql("sum( case when purchase.FSellType='PreSell' then room.FBuildingArea else").appendSql("\t\n");
			builder.appendSql("case when  purchase.FSellType='LocaleSell'  then room.FActualBuildingArea  else 0 end  end) termUnsignArea,").appendSql("\t\n");
			builder.appendSql("sum(0) termUnsignStandardTotalAmount,sum(0) termUnsignDealTotalAmount, ").appendSql("\t\n");
			builder.appendSql("sum( case when attach.FMergeAmount is null then 0 else attach.FMergeAmount end) mergeAmount ").appendSql("\t\n");
			builder.appendSql("from T_SHE_PurchaseRoomAttachEntry attach").appendSql("\t\n");
			builder.appendSql("left join T_SHE_RoomAttachmentEntry roomAttach on attach.FAttachmentEntryID = roomAttach.fid").appendSql("\t\n");
			builder.appendSql("left join t_she_purchase purchase on attach.fheadid = purchase.fid").appendSql("\t\n");
			builder.appendSql("left join t_she_room ROOM on roomAttach.froomid = ROOM.fid").appendSql("\t\n");
//			builder.appendSql("where room.FIsForSHE=1 and purchase.FToSignDate is null ").appendSql("\t\n");
			builder.appendSql("where room.FIsForSHE=1 ").appendSql("\t\n");
			
			fillterSQL(builder,map,"unSign");
			builder.appendSql(" group by FBuildingId ");
		}
		builder.appendSql(")ffff").appendSql("\t\n");
		builder.appendSql(" group by ffff.FBuildingId ");
		
		
		IRowSet termUnsignSet = builder.executeQuery(ctx);
		while (termUnsignSet.next()) {

			Map unsignMap = new HashMap();

			String buidlingId = termUnsignSet.getString("FBuildingId");

			unsignMap.put("termUnsignCount", new Integer(termUnsignSet.getInt("termUnsignCount")));

			BigDecimal termUnsignArea = termUnsignSet.getBigDecimal("termUnsignArea");
			unsignMap.put("termUnsignArea", termUnsignArea);

			BigDecimal termUnsignStandardTotalAmount = termUnsignSet.getBigDecimal("termUnsignStandardTotalAmount");
			BigDecimal termUnsignDealTotalAmount = termUnsignSet.getBigDecimal("termUnsignDealTotalAmount");
			BigDecimal mergeAmount = termUnsignSet.getBigDecimal("mergeAmount");//附属房产并入金额
			if (includeAttach.booleanValue()) {
				termUnsignDealTotalAmount = termUnsignDealTotalAmount.add(mergeAmount);
			}
			unsignMap.put("termUnsignStandardTotalAmount",termUnsignStandardTotalAmount);
			
			unsignMap.put("termUnsignDealTotalAmount",termUnsignDealTotalAmount);

			unsignMap1.put(buidlingId, unsignMap);
		}
		return unsignMap1;
	}

	/**
	 * 获取定金类回款
	 */
	private Map getPurchaseAmount(Context ctx, Map map) throws BOSException,
			SQLException {

		Map purMap1 = new HashMap();

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		// 查出定金
		builder.clear();
		builder.appendSql(" select room.FBuildingID FBuildingID,sum( case when payList.FActRevAmount is null then 0 else payList.FActRevAmount end) YSamount," 
						+ " sum( case when payList.FHasTransferredAmount is null then 0 else payList.FHasTransferredAmount end) YZamount," 
						+ " sum( case when payList.FHasRefundmentAmount is null then 0 else payList.FHasRefundmentAmount end) YQamount," 
						+ " sum( case when payList.FHasAdjustedAmount is null then 0 else payList.FHasAdjustedAmount end) YTamount"
						+ " from t_she_purchase purchase "
						+ " left join t_she_room room on room.fid = purchase.FRoomID "
						+ " left join T_SHE_PurchasePayListEntry payList on purchase.fid = payList.FHeadID "
						+ " left join t_she_moneydefine moneyDefine on payList.FMoneyDefineID = moneyDefine.fid "
						+ " where room.FIsForSHE=1 AND (moneyDefine.FMoneyType= '"+MoneyTypeEnum.EARNESTMONEY_VALUE+"') ");
//						+ " where room.FIsForSHE=1 AND  moneyDefine.FMoneyType= '"+MoneyTypeEnum.EARNESTMONEY_VALUE+"' and purchase.FisEarnestInHouseAmount=1 ");

		Boolean includeAttach = (Boolean) (map.get("IncludeAttach")); // 是否包含附属房产
		if (!includeAttach.booleanValue()) {
			builder.appendSql(" and room.FHouseProperty = '"
					+ HousePropertyEnum.NOATTACHMENT_VALUE + "' ");
		}
		
		fillterSQL(builder,map);

		builder.appendSql(" group by FBuildingID ");
		IRowSet termDepositSet = builder.executeQuery(ctx);
		while (termDepositSet.next()) {

			Map purMap = new HashMap();

			String buidlingId = termDepositSet.getString("FBuildingID");

			BigDecimal YSamount = termDepositSet.getBigDecimal("YSamount"); // 已收金额
			BigDecimal YZamount = termDepositSet.getBigDecimal("YZamount"); // 已转金额
			BigDecimal YQamount = termDepositSet.getBigDecimal("YQamount"); // 已退金额
			BigDecimal YTamount = termDepositSet.getBigDecimal("YTamount"); // 已调金额
			if (YSamount == null) {
				YSamount = FDCHelper.ZERO;
			}
			if (YZamount == null) {
				YZamount = FDCHelper.ZERO;
			}
			if (YQamount == null) {
				YQamount = FDCHelper.ZERO;
			}
			if (YTamount == null) {
				YTamount = FDCHelper.ZERO;
			}
			BigDecimal SSamount = YSamount.subtract(YZamount)
					.subtract(YQamount).subtract(YTamount);

			if (SSamount == null) {
				SSamount = FDCHelper.ZERO;
			}
			purMap.put("termPurchaseAmount", SSamount);

			purMap1.put(buidlingId, purMap);
		}
		return purMap1;
	}

	/**
	 * 获取销售欠款
	 */
	private Map getPurchaseAmount1(Context ctx, Map map) throws BOSException,
			SQLException {

		Map purMap1 = new HashMap();
		Map purMap2 = new HashMap();
		Map purMap3 = new HashMap();
		
		FDCSQLBuilder builder3 = new FDCSQLBuilder(ctx);
		// 得到补差款
		builder3.clear();
		
		// 补差金额
		builder3.appendSql("select room.FBuildingID FBuildingID,").appendSql("\t\n");
		builder3.appendSql(" sum( case when revList.FActRevAmount is null then 0 else revList.FActRevAmount end) revAmount, ").appendSql("\t\n");
		builder3.appendSql(" sum( case when revList.FHasTransferredAmount is null then 0 else revList.FHasTransferredAmount end) ferAmount, ").appendSql("\t\n");
		builder3.appendSql(" sum( case when revList.FHasRefundmentAmount is null then 0 else revList.FHasRefundmentAmount end) refAmount, ").appendSql("\t\n");
		builder3.appendSql(" sum( case when revList.FHasAdjustedAmount is null then 0 else revList.FHasAdjustedAmount end) adjAmount ").appendSql("\t\n");
		builder3.appendSql(" from T_SHE_AreaCompensateRevList revList").appendSql("\t\n"); 
		builder3.appendSql(" left join T_SHE_RoomAreaCompensate roomArea on revList.FHeadID = roomArea.FID ").appendSql("\t\n");
		builder3.appendSql(" left join T_SHE_Room room on roomArea.FRoomID = room.FID ").appendSql("\t\n");
		builder3.appendSql(" where room.FIsForSHE=1 ").appendSql("\t\n");;

		Boolean includeAttach = (Boolean) (map.get("IncludeAttach")); // 是否包含附属房产
		Boolean showAll = (Boolean) (map.get("ShowAll")); // 是否显示全部
//		if (!includeAttach.booleanValue()) {
//			builder3.appendSql(" and room.FHouseProperty = '"
//					+ HousePropertyEnum.NOATTACHMENT_VALUE + "' ");
//		}

		// 根据过滤界面的日期过滤
//		if (!showAll.booleanValue()) {//业务日期
////			builder3.appendSql(" and roomArea.FCompensateDate >= {ts '"+ map.get("beginDate")+ "'} and roomArea.FCompensateDate < {ts '"+ map.get("endDate") + "'} ");
//		} else {
//			//业务日期
//			builder3.appendSql(" and roomArea.FBookedDate is not null ");
//			
//		}
		
		builder3.appendSql(" group by FBuildingID ");
		IRowSet termbckSet = builder3.executeQuery(ctx);

		while (termbckSet.next()) {

			String buidlingId = termbckSet.getString("FBuildingID");

			BigDecimal YSamount1 = termbckSet.getBigDecimal("revAmount"); // 已收金额
			BigDecimal YZamount1 = termbckSet.getBigDecimal("ferAmount"); // 已转金额
			BigDecimal YQamount1 = termbckSet.getBigDecimal("refAmount"); // 已退金额
			BigDecimal YTamount1 = termbckSet.getBigDecimal("adjAmount"); // 已调金额

			BigDecimal SSamount = YSamount1.subtract(YZamount1).subtract(YQamount1).subtract(YTamount1);

			purMap2.put(buidlingId, SSamount);

		}
		FDCSQLBuilder builder2 = new FDCSQLBuilder(ctx);
		// 查出定金
		builder2.clear();
		builder2.appendSql(" select room.FBuildingID FBuildingID,sum( case when payList.FActRevAmount is null then 0 else payList.FActRevAmount end) YSamount," 
						+ " sum( case when payList.FHasTransferredAmount is null then 0 else payList.FHasTransferredAmount end) YZamount," 
						+ " sum( case when payList.FHasRefundmentAmount is null then 0 else payList.FHasRefundmentAmount end) YQamount," 
						+ " sum( case when payList.FHasAdjustedAmount is null then 0 else payList.FHasAdjustedAmount end) YTamount"
						+ " from t_she_purchase purchase "
						+ " left join t_she_room room on room.fid = purchase.FRoomID "
						+ " left join T_SHE_PurchasePayListEntry payList on purchase.fid = payList.FHeadID "
						+ " left join t_she_moneydefine moneyDefine on payList.FMoneyDefineID = moneyDefine.fid "
//						+ " where room.FIsForSHE=1 AND (moneyDefine.FMoneyType= '"+MoneyTypeEnum.EARNESTMONEY_VALUE+"') ");
						+ " where room.FIsForSHE=1 AND  moneyDefine.FMoneyType= '"+MoneyTypeEnum.EARNESTMONEY_VALUE+"' and purchase.FisEarnestInHouseAmount=1 ");

		if (!includeAttach.booleanValue()) {
			builder2.appendSql(" and room.FHouseProperty = '"
					+ HousePropertyEnum.NOATTACHMENT_VALUE + "' ");
		}
		
		fillterSQL(builder2,map);

		builder2.appendSql(" group by FBuildingID ");
		IRowSet termDepositSet = builder2.executeQuery(ctx);
		while (termDepositSet.next()) {

			String buidlingId = termDepositSet.getString("FBuildingID");

			BigDecimal YSamount = termDepositSet.getBigDecimal("YSamount"); // 已收金额
			BigDecimal YZamount = termDepositSet.getBigDecimal("YZamount"); // 已转金额
			BigDecimal YQamount = termDepositSet.getBigDecimal("YQamount"); // 已退金额
			BigDecimal YTamount = termDepositSet.getBigDecimal("YTamount"); // 已调金额
			
			BigDecimal SSamount = YSamount.subtract(YZamount).subtract(YQamount).subtract(YTamount);

			if (SSamount == null) {
				SSamount = FDCHelper.ZERO;
			}
			purMap3.put(buidlingId, SSamount);
		}
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		// 查出非定金
		builder.clear();
		builder.appendSql(" select room.FBuildingID FBuildingID,sum( case when payList.FActRevAmount is null then 0 else payList.FActRevAmount end) YSamount,").appendSql("\t\n"); 
		builder.appendSql(" sum( case when payList.FHasTransferredAmount is null then 0 else payList.FHasTransferredAmount end) YZamount,").appendSql("\t\n"); 
		builder.appendSql(" sum( case when payList.FHasRefundmentAmount is null then 0 else payList.FHasRefundmentAmount end) YQamount,").appendSql("\t\n"); 
		builder.appendSql(" sum( case when payList.FHasAdjustedAmount is null then 0 else payList.FHasAdjustedAmount end) YTamount ").appendSql("\t\n");
		builder.appendSql(" from t_she_purchase purchase ").appendSql("\t\n");
		builder.appendSql(" left join t_she_room room on room.fid = purchase.FRoomID ").appendSql("\t\n");
		builder.appendSql(" left join T_SHE_PurchasePayListEntry payList on purchase.fid = payList.FHeadID ").appendSql("\t\n");
		builder.appendSql(" left join T_SHE_MoneyDefine moneyDefine on moneyDefine.fid = payList.FMoneyDefineID ").appendSql("\t\n");
		builder.appendSql(" where room.FIsForSHE=1 and moneyDefine.FMoneyType in ").appendSql("\t\n"); 
		builder.appendSql(" ('"+MoneyTypeEnum.FISRTAMOUNT_VALUE+"','"+MoneyTypeEnum.HOUSEAMOUNT_VALUE+"','"+MoneyTypeEnum.LOANAMOUNT_VALUE+"','"+MoneyTypeEnum.ACCFUNDAMOUNT_VALUE+"')").appendSql("\t\n");;


		fillterSQL(builder,map);

		builder.appendSql(" group by FBuildingID ");
		IRowSet termAreageAmountSet = builder.executeQuery(ctx);

		while (termAreageAmountSet.next()) {

			Map purMap = new HashMap();

			String buidlingId = termAreageAmountSet.getString("FBuildingID");

			BigDecimal YSamount = termAreageAmountSet.getBigDecimal("YSamount"); // 已收金额
			BigDecimal YZamount = termAreageAmountSet.getBigDecimal("YZamount"); // 已转金额
			BigDecimal YQamount = termAreageAmountSet.getBigDecimal("YQamount"); // 已退金额
			BigDecimal YTamount = termAreageAmountSet.getBigDecimal("YTamount"); // 已调金额
//			BigDecimal HTZJamount = termAreageAmountSet.getBigDecimal("conTotalAmount"); // 合同总价

			BigDecimal SSamount = YSamount.subtract(YZamount).subtract(YQamount).subtract(YTamount);

//			SSamount = HTZJamount.subtract(SSamount);
			
			BigDecimal bck = (BigDecimal) purMap2.get(buidlingId);
			if (bck == null) {
				bck = FDCHelper.ZERO;
			}
			BigDecimal dj = (BigDecimal) purMap3.get(buidlingId);
			if (dj == null) {
				dj = FDCHelper.ZERO;
			}

			purMap.put("termAreageAmount", SSamount.add(bck).add(dj));

			purMap1.put(buidlingId, purMap);
		}
		return purMap1;
	}

	/**
	 * 获取非定金类回款
	 */
	private Map getPurchaseAmount2(Context ctx, Map map) throws BOSException,
			SQLException {

		Map purMap1 = new HashMap();
		Map purMap2 = new HashMap();
		Boolean preIntoSale = (Boolean) (map.get("PreIntoSale")); // 预定业务进入销售统计

		/*
		 * 非定金类回款的取值
		 */
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		FDCSQLBuilder builder3 = new FDCSQLBuilder(ctx);
		// 得到补差款
		builder3.clear();
		
		// 补差金额
		builder3.appendSql("select room.FBuildingID FBuildingID,").appendSql("\t\n");
		builder3.appendSql(" sum( case when revList.FActRevAmount is null then 0 else revList.FActRevAmount end) revAmount, ").appendSql("\t\n");
		builder3.appendSql(" sum( case when revList.FHasTransferredAmount is null then 0 else revList.FHasTransferredAmount end) ferAmount, ").appendSql("\t\n");
		builder3.appendSql(" sum( case when revList.FHasRefundmentAmount is null then 0 else revList.FHasRefundmentAmount end) refAmount, ").appendSql("\t\n");
		builder3.appendSql(" sum( case when revList.FHasAdjustedAmount is null then 0 else revList.FHasAdjustedAmount end) adjAmount ").appendSql("\t\n");
		builder3.appendSql(" from T_SHE_AreaCompensateRevList revList").appendSql("\t\n"); 
		builder3.appendSql(" left join T_SHE_RoomAreaCompensate roomArea on revList.FHeadID = roomArea.FID ").appendSql("\t\n");
		builder3.appendSql(" left join T_SHE_Room room on roomArea.FRoomID = room.FID ").appendSql("\t\n");
		builder3.appendSql(" where room.FIsForSHE=1 ").appendSql("\t\n");;

		Boolean includeAttach = (Boolean) (map.get("IncludeAttach")); // 是否包含附属房产
		Boolean showAll = (Boolean) (map.get("ShowAll")); // 是否显示全部
		if (!includeAttach.booleanValue()) {
			builder3.appendSql(" and room.FHouseProperty = '"
					+ HousePropertyEnum.NOATTACHMENT_VALUE + "' ");
		}

//		// 根据过滤界面的日期过滤
//		if (!showAll.booleanValue()) {//业务日期
//			builder3.appendSql(" and roomArea.FCompensateDate >= {ts '"+ map.get("beginDate")+ "'} and roomArea.FCompensateDate < {ts '"+ map.get("endDate") + "'} ");
//		} else {
//			//业务日期
//			builder3.appendSql(" and roomArea.FCompensateDate is not null ");
//		}
		
		builder3.appendSql(" group by FBuildingID ");
		IRowSet termUnpurchaseAmountSet1 = builder3.executeQuery(ctx);

		while (termUnpurchaseAmountSet1.next()) {

			String buidlingId = termUnpurchaseAmountSet1
					.getString("FBuildingID");

			BigDecimal YSamount1 = termUnpurchaseAmountSet1.getBigDecimal("revAmount"); // 已收金额
			BigDecimal YZamount1 = termUnpurchaseAmountSet1.getBigDecimal("ferAmount"); // 已转金额
			BigDecimal YQamount1 = termUnpurchaseAmountSet1.getBigDecimal("refAmount"); // 已退金额
			BigDecimal YTamount1 = termUnpurchaseAmountSet1.getBigDecimal("adjAmount"); // 已调金额

			BigDecimal SSamount = YSamount1.subtract(YZamount1).subtract(YQamount1).subtract(YTamount1);

			purMap2.put(buidlingId, SSamount);

		}
		// 查出非定金类回款
		builder.clear();
		builder.appendSql(" select room.FBuildingID FBuildingID,").appendSql("\t\n"); 
		builder.appendSql(" sum( case when payList.FActRevAmount is null then 0 else payList.FActRevAmount end) YSamount,").appendSql("\t\n"); 
		builder.appendSql(" sum( case when payList.FHasTransferredAmount is null then 0 else payList.FHasTransferredAmount end) YZamount,").appendSql("\t\n"); 
		builder.appendSql(" sum( case when payList.FHasRefundmentAmount is null then 0 else payList.FHasRefundmentAmount end) YQamount,").appendSql("\t\n"); 
		builder.appendSql(" sum( case when payList.FHasAdjustedAmount is null then 0 else payList.FHasAdjustedAmount end) YTamount").appendSql("\t\n");
		builder.appendSql(" from t_she_purchase purchase ").appendSql("\t\n");
		builder.appendSql(" left join t_she_room room on room.fid = purchase.FRoomID ").appendSql("\t\n");
		builder.appendSql(" left join T_SHE_PurchasePayListEntry payList on purchase.fid = payList.FHeadID ").appendSql("\t\n");
		builder.appendSql(" left join t_she_moneydefine moneyDefine on payList.FMoneyDefineID = moneyDefine.fid ").appendSql("\t\n");
		builder.appendSql(" where room.FIsForSHE=1").appendSql("\t\n");
		if(preIntoSale.booleanValue()){
			builder.appendSql(" AND moneyDefine.FMoneyType in( '"+MoneyTypeEnum.FISRTAMOUNT_VALUE+"','"+MoneyTypeEnum.HOUSEAMOUNT_VALUE+"','"+MoneyTypeEnum.LOANAMOUNT_VALUE+"','"+MoneyTypeEnum.ACCFUNDAMOUNT_VALUE+"','"+MoneyTypeEnum.PRECONCERTMONEY_VALUE+"') ").appendSql("\t\n");;
		}else{
			builder.appendSql(" AND moneyDefine.FMoneyType in( '"+MoneyTypeEnum.FISRTAMOUNT_VALUE+"','"+MoneyTypeEnum.HOUSEAMOUNT_VALUE+"','"+MoneyTypeEnum.LOANAMOUNT_VALUE+"','"+MoneyTypeEnum.ACCFUNDAMOUNT_VALUE+"') ").appendSql("\t\n");;
		}
		
		if (!includeAttach.booleanValue()) {
			builder.appendSql(" and room.FHouseProperty = '"
					+ HousePropertyEnum.NOATTACHMENT_VALUE + "' ");
		}


		fillterSQL(builder,map);

		builder.appendSql(" group by FBuildingID ");
		IRowSet termUnpurchaseAmountSet = builder.executeQuery(ctx);

		while (termUnpurchaseAmountSet.next()) {

			Map purMap = new HashMap();

			String buidlingId = termUnpurchaseAmountSet.getString("FBuildingID");

			BigDecimal YSamount = termUnpurchaseAmountSet.getBigDecimal("YSamount"); // 已收金额
			BigDecimal YZamount = termUnpurchaseAmountSet.getBigDecimal("YZamount"); // 已转金额
			BigDecimal YQamount = termUnpurchaseAmountSet.getBigDecimal("YQamount"); // 已退金额
			BigDecimal YTamount = termUnpurchaseAmountSet.getBigDecimal("YTamount"); // 已调金额

			BigDecimal SSamount = (YSamount.subtract(YZamount).subtract(YQamount).subtract(YTamount));

			BigDecimal bck = (BigDecimal) purMap2.get(buidlingId);
			if (bck == null) {
				bck = FDCHelper.ZERO;
			}
			purMap.put("termUnpurchaseAmount", SSamount.add(bck));

			purMap1.put(buidlingId, purMap);
		}
		return purMap1;
	}

	/**
	 * 获取 合同总价
	 * */
	private Map getContractAmount(Context ctx, Map map) throws BOSException,
			SQLException {

		Map conMap1 = new HashMap();
		Boolean includeAttach = (Boolean) (map.get("IncludeAttach")); // 是否包含附属房产

		// 合同总价
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select room.fbuildingid FBuildingId, " ).appendSql("\t\n");
		if (!includeAttach.booleanValue()) {
			builder.appendSql(" sum( case when purchase.FContractTotalAmount is null then purchase.FStandardTotalAmount else purchase.FContractTotalAmount end) - " 
					+ " sum( case when purchase.FAttachmentAmount is null then 0 else purchase.FAttachmentAmount end) termContractTotalAmount " ).appendSql("\t\n");
		}else{
			builder.appendSql(" sum( case when purchase.FContractTotalAmount is null then 0 else purchase.FContractTotalAmount end) termContractTotalAmount " ).appendSql("\t\n");
		}
//		builder.appendSql(" sum( case when purchase.FContractTotalAmount is null then 0 else purchase.FContractTotalAmount end) termContractTotalAmount " ).appendSql("\t\n"); 
		builder.appendSql(" from t_she_purchase purchase " ).appendSql("\t\n");
		builder.appendSql(" left join t_she_room room on purchase.FRoomID=room.fid " ).appendSql("\t\n");;

		builder.appendSql(" where room.FIsForSHE=1 " ).appendSql("\t\n");
		

		fillterSQL(builder,map);

		if (!includeAttach.booleanValue()) {
			builder.appendSql(" and room.FHouseProperty = '"
					+ HousePropertyEnum.NOATTACHMENT_VALUE + "' ");
		}
		builder.appendSql(" group by FBuildingId");
		

		IRowSet termContractSet = builder.executeQuery(ctx);
		while (termContractSet.next()) {

			Map conMap = new HashMap();

			String buidlingId = termContractSet.getString("FBuildingId");

			BigDecimal termContractTotalAmount = termContractSet
					.getBigDecimal("termContractTotalAmount");
			if (termContractTotalAmount == null) {
				termContractTotalAmount = FDCHelper.ZERO;
			}
			conMap.put("termContractTotalAmount", termContractTotalAmount);

			conMap1.put(buidlingId, conMap);
		}
		return conMap1;
	}

	/**
	 * 获取 销售回款
	 * */
	private IRowSet getReceiveAmount(Context ctx, Map map) throws BOSException,
			SQLException {

		Boolean preIntoSale = (Boolean) (map.get("PreIntoSale")); // 预定业务进入销售统计

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		// 收款单款项类型改到分录后对报表的修改 zhicheng_jin
//		builder
//				.appendSql("select fbuildingid FBuildingId,sum( case when pay.FActRevAmount is null then 0 else pay.FActRevAmount end) revAmount," 
//						+ " sum( case when pay.FHasTransferredAmount is null then 0 else pay.FHasTransferredAmount end) trsAmount," 
//						+ " sum( case when pay.FHasRefundmentAmount is null then 0 else pay.FHasRefundmentAmount end) refAmount," 
//						+ " sum( case when pay.FHasAdjustedAmount is null then 0 else pay.FHasAdjustedAmount end) adjAmount "
//						+ " from T_SHE_Purchase purchase "
//						+ " left join T_SHE_PurchasePayListEntry pay on purchase.FID=pay.FHeadID "
//						+ " left join T_SHE_Room room on purchase.FRoomID=room.FID "
////						+ " case when purchase.FisEarnestInHouseAmount==1 then where " 
////						+ " moneyDefine.FMoneyType in('"+MoneyTypeEnum.EARNESTMONEY_VALUE+"','"+MoneyTypeEnum.FISRTAMOUNT_VALUE+"','"+MoneyTypeEnum.HOUSEAMOUNT_VALUE+"') "
////					    + " else where " 
////					    + " moneyDefine.FMoneyType in('"+MoneyTypeEnum.FISRTAMOUNT_VALUE+"','"+MoneyTypeEnum.HOUSEAMOUNT_VALUE+"') " 
////					    + " end "
//						+ " where room.FIsForSHE=1 ");
		
		////2010-07-02 michael 销售回款  已收-已退-已转-已调  从收款单上取
		builder.appendSql("SELECT BUILDING.FID FBuildingId,moneyDefine.FMoneyType moneyType,\n");
		////   定金不隶属于房款 ,  预收款转  不算到销售回款内  
//		builder.appendSql(" Sum( \n");
//		
//		builder.appendSql(" case when  purchase.FisEarnestInHouseAmount=0 and MoneyDefine.FMoneyType='PurchaseAmount' then 0 else  \n");
//		
//		builder.appendSql("case when FDCEntry.FRevAmount is null then 0 else  \n");
//		builder.appendSql(" 	case when  MoneyDefine.FMoneyType='PreconcertMoney' and ROOM.fSellState!='PrePurchase' then 0 else \n");
//		builder.appendSql("	   		case when ROOM.fSellState='PrePurchase' and MoneyDefine.FMoneyType='PreconcertMoney' and purchase.FPurchaseState='PurchaseAudit' then 0 \n");
//		builder.appendSql("	   		else FDCEntry.FRevAmount end \n");
//		builder.appendSql("     end \n");
//		builder.appendSql(" end \n");
//		builder.appendSql("end \n");
//		builder.appendSql("	) AS revAmount \n");
//
//		builder.appendSql("FROM T_BDC_FDCReceivingBillEntry AS FDCEntry \n");
//		builder.appendSql("LEFT OUTER JOIN T_BDC_FDCReceivingBill AS FDC ON FDCEntry.FHeadID = FDC.FID \n"); 
//		
//		builder.appendSql("inner JOIN T_SHE_Room AS room ON FDC.FRoomID = room.FID \n"); 
//		builder.appendSql("LEFT OUTER JOIN T_SHE_Purchase AS purchase ON ROOM.FID = purchase.FRoomId \n"); 		
//		builder.appendSql("LEFT OUTER JOIN  T_SHE_Building AS BUILDING	ON ROOM.FBuildingID = BUILDING.FID \n");
//		builder.appendSql("LEFT OUTER JOIN T_SHE_MoneyDefine AS MoneyDefine ON FDCEntry.FMoneyDefineID = MoneyDefine.FID \n");
		///end 
		
		//old  2010-07-02 michael
		builder.appendSql(" sum(pay.FActRevAmount) revAmount,").appendSql("\t\n");
		builder.appendSql(" sum(pay.FHasTransferredAmount) trsAmount, sum(pay.FHasRefundmentAmount) refAmount,").appendSql("\t\n"); 
		builder.appendSql(" sum(pay.FHasAdjustedAmount) adjAmount ").appendSql("\t\n");
		builder.appendSql(" from T_SHE_Purchase purchase ").appendSql("\t\n");
		builder.appendSql(" left join T_SHE_PurchasePayListEntry pay on pay.FHeadID=purchase.FID ").appendSql("\t\n");
		builder.appendSql(" left join T_SHE_Room room on purchase.FRoomID=room.FID ").appendSql("\t\n");
		builder.appendSql(" left join T_SHE_Building AS BUILDING ON ROOM.FBuildingID = BUILDING.FID \n");
		builder.appendSql(" left join T_SHE_PurchaseElsePayListEntry elsepayList on elsepayList.fheadid = purchase.fid ").appendSql("\t\n");
		builder.appendSql(" left join t_she_moneydefine moneyDefine on pay.FMoneyDefineID = moneyDefine.fid ").appendSql("\t\n");
		//end old 
		if(preIntoSale.booleanValue()){
			builder.appendSql(" where room.FIsForSHE=1 ").appendSql("\t\n");
			builder.appendSql(" and room.fsellstate in ('PrePurchase', 'Purchase', 'Sign') \n");
		}else{
			builder.appendSql(" where room.FIsForSHE=1  and moneyDefine.FMoneyType not in ('"+MoneyTypeEnum.PRECONCERTMONEY_VALUE+"') ").appendSql("\t\n");
			builder.appendSql("\n and room.fsellstate in ('Purchase', 'Sign') \n");
		}

		//// 付款明细  款项类别 2010-07-02 michael
//		builder.appendSql(" and moneyDefine.FMoneyType in ('"
//				+ MoneyTypeEnum.PRECONCERTMONEY_VALUE + "','"
//				+ MoneyTypeEnum.EARNESTMONEY_VALUE + "','"
//				+ MoneyTypeEnum.FISRTAMOUNT_VALUE + "','"
////				+ MoneyTypeEnum.COMPENSATEAMOUNT_VALUE + "','"
//				+ MoneyTypeEnum.LOANAMOUNT_VALUE + "','"
//				+ MoneyTypeEnum.ACCFUNDAMOUNT_VALUE + "','"
//				+ MoneyTypeEnum.REFUNDMENT_VALUE + "') ");
		////end
		
//		Boolean includeAttach = (Boolean) (map.get("IncludeAttach")); // 是否包含附属房产
//		if (!includeAttach.booleanValue()) {
//			builder.appendSql(" and room.FHouseProperty != '"
//					+ HousePropertyEnum.NOATTACHMENT_VALUE + "' ");
//		}
//		
		fillterSQL(builder,map);

		//2010-07-02 michael 
		builder.appendSql(" GROUP BY BUILDING.FID,MoneyDefine.FMoneyType \n");	
		//end
		IRowSet termReceiveSet = builder.executeQuery(ctx);
		return termReceiveSet;
	}
	
	/**
	 * 房间状态为签约通用界面
	 * @param builder sql类
	 * @param map 参数
	 * @param sign 是否为签约
	 */
	private void fillterSQL(FDCSQLBuilder builder, Map map,String sign){
		Boolean preIntoSale = (Boolean) (map.get("PreIntoSale")); // 预定业务进入销售统计
		Boolean showAll = (Boolean) (map.get("ShowAll")); // 是否显示全部

		builder.appendSql(" and purchase.FPurchaseState not in('"
				+ PurchaseStateEnum.NOPAYBLANKOUT_VALUE + "','"
				+ PurchaseStateEnum.MANUALBLANKOUT_VALUE + "','"
				+ PurchaseStateEnum.ADJUSTBLANKOUT_VALUE + "') ");

		// 查询退房和换房记录
		builder.appendSql(" and purchase.fid not in( ");
		// 查询退房单
		builder
				.appendSql(" select quitRoom.FPurchaseID  as FPurchaseID from t_she_quitRoom quitRoom where ");
		// 根据过滤界面的日期过滤退房单
		if (!showAll.booleanValue()) {
			builder.appendSql(" quitRoom.FAuditTime >= {ts '"
					+ map.get("beginDate")
					+ "'} and quitRoom.FAuditTime < {ts '" + map.get("endDate")
					+ "'} ");
//			if (preIntoSale.booleanValue()) {// 转销售日期
//				builder
//						.appendSql(" and quitRoom.FAuditTime >= purchase.FToSaleDate ");
//			} else {// 转认购日期
//				builder
//						.appendSql(" and quitRoom.FAuditTime >= purchase.FToPurchaseDate ");
//			}
		} else {
			builder.appendSql(" quitRoom.FAuditTime is not null  ");
//			if (preIntoSale.booleanValue()) {// 转销售日期
//				builder.appendSql(" and quitRoom.FAuditTime >= purchase.FToSaleDate ");
//			} else {// 转认购日期
//				builder.appendSql(" and quitRoom.FAuditTime >= purchase.FToPurchaseDate ");
//			}
		}
		builder.appendSql(" and quitRoom.fstate='"
				+ FDCBillStateEnum.AUDITTED_VALUE + "' ");
		builder.appendSql(" union ");
		// 查询换房单
		builder
				.appendSql(" select changRoom.FOldPurchaseID  as FPurchaseID from t_she_ChangeRoom changRoom where ");
		// 根据过滤界面的日期过滤换房单
		if (!showAll.booleanValue()) {
			builder.appendSql(" changRoom.FAuditTime >= {ts '"
					+ map.get("beginDate")
					+ "'} and changRoom.FAuditTime < {ts '"
					+ map.get("endDate") + "'} ");
//			if (preIntoSale.booleanValue()) {// 转销售日期
//				builder.appendSql(" and changRoom.FAuditTime >= purchase.FToSaleDate ");
//			} else {// 转认购日期
//				builder.appendSql(" and changRoom.FAuditTime >= purchase.FToPurchaseDate ");
//			}
		} else {
			builder.appendSql(" changRoom.FAuditTime is not null ");
//			if (preIntoSale.booleanValue()) {// 转销售日期
//				builder.appendSql(" and changRoom.FAuditTime >= purchase.FToSaleDate ");
//			} else {// 转认购日期
//				builder.appendSql(" and changRoom.FAuditTime >= purchase.FToPurchaseDate ");
//			}
		}
		builder.appendSql(" and changRoom.fstate='"
				+ FDCBillStateEnum.AUDITTED_VALUE + "' ");
		builder.appendSql(" ) ");
		if(ISSIGN.equals(sign)){
			if(map.get("beginDate")!=null && map.get("endDate")!=null){
				builder.appendSql("  and purchase.FToSignDate >= {ts '"
						+ map.get("beginDate")
						+ "'} and purchase.FToSignDate < {ts '"
						+ map.get("endDate") + "'}  ");
			}else{
				builder.appendSql(" and purchase.FToSignDate is not null");
			}
		}else{
			if(map.get("beginDate")!=null && map.get("endDate")!=null){
				builder.appendSql(" and (purchase.FToSignDate is null or (purchase.FToSignDate is not null and purchase.FToSignDate < {ts '"
						+ map.get("beginDate")
						+ "'} or purchase.FToSignDate >= {ts '"
						+ map.get("endDate") + "'} )) ");
			}else{
				builder.appendSql(" and purchase.FToSignDate is null");
			}
		}
		// 根据过滤界面时间条件过滤数据
		if (!showAll.booleanValue()) {
			if (preIntoSale.booleanValue()) {// 转销售日期
				builder.appendSql(" and purchase.FToSaleDate >= {ts '"
						+ map.get("beginDate")
						+ "'} and purchase.FToSaleDate < {ts '"
						+ map.get("endDate") + "'} ");
			} else {// 转认购日期
				builder.appendSql("  and purchase.FToPurchaseDate >= {ts '"
						+ map.get("beginDate")
						+ "'} and purchase.FToPurchaseDate < {ts '"
						+ map.get("endDate") + "'}  ");
			}
		} else {
			if (preIntoSale.booleanValue()) {// 转销售日期
				builder.appendSql("  and purchase.FToSaleDate is not null ");
			} else {// 转认购日期
				builder.appendSql(" and purchase.FToPurchaseDate is not null ");
			}
		}
	}
	
	private void fillterSQL(FDCSQLBuilder builder, Map map){
		
		Boolean preIntoSale = (Boolean) (map.get("PreIntoSale")); // 预定业务进入销售统计
		Boolean showAll = (Boolean) (map.get("ShowAll")); // 是否显示全部

		builder.appendSql(" and purchase.FPurchaseState not in('"
				+ PurchaseStateEnum.NOPAYBLANKOUT_VALUE + "','"
				+ PurchaseStateEnum.MANUALBLANKOUT_VALUE + "','"
				+ PurchaseStateEnum.ADJUSTBLANKOUT_VALUE + "') ");

		// 查询退房和换房记录
		builder.appendSql(" and purchase.fid not in( ");
		// 查询退房单
		builder
				.appendSql(" select quitRoom.FPurchaseID  as FPurchaseID from t_she_quitRoom quitRoom where ");
		// 根据过滤界面的日期过滤退房单
		if (!showAll.booleanValue()) {
			builder.appendSql(" quitRoom.FAuditTime >= {ts '"
					+ map.get("beginDate")
					+ "'} and quitRoom.FAuditTime < {ts '" + map.get("endDate")
					+ "'} ");
//			if (preIntoSale.booleanValue()) {// 转销售日期
//				builder.appendSql(" and quitRoom.FAuditTime >= purchase.FToSaleDate ");
//			} else {// 转认购日期
//				builder
//						.appendSql(" and quitRoom.FAuditTime >= purchase.FToPurchaseDate ");
//			}
		} else {
			builder.appendSql(" quitRoom.FAuditTime is not null  ");
//			if (preIntoSale.booleanValue()) {// 转销售日期
//				builder.appendSql(" and quitRoom.FAuditTime >= purchase.FToSaleDate ");
//			} else {// 转认购日期
//				builder.appendSql(" and quitRoom.FAuditTime >= purchase.FToPurchaseDate ");
//			}
		}
		builder.appendSql(" and quitRoom.fstate='"
				+ FDCBillStateEnum.AUDITTED_VALUE + "' ");
		builder.appendSql(" union ");
		// 查询换房单
		builder
				.appendSql(" select changRoom.FOldPurchaseID  as FPurchaseID from t_she_ChangeRoom changRoom where ");
		// 根据过滤界面的日期过滤换房单
		if (!showAll.booleanValue()) {
			builder.appendSql(" changRoom.FAuditTime >= {ts '"
					+ map.get("beginDate")
					+ "'} and changRoom.FAuditTime < {ts '"
					+ map.get("endDate") + "'} ");
//			if (preIntoSale.booleanValue()) {// 转销售日期
//				builder.appendSql(" and changRoom.FAuditTime >= purchase.FToSaleDate ");
//			} else {// 转认购日期
//				builder.appendSql(" and changRoom.FAuditTime >= purchase.FToPurchaseDate ");
//			}
		} else {
			builder.appendSql(" changRoom.FAuditTime is not null ");
//			if (preIntoSale.booleanValue()) {// 转销售日期
//				builder.appendSql(" and changRoom.FAuditTime >= purchase.FToSaleDate ");
//			} else {// 转认购日期
//				builder.appendSql(" and changRoom.FAuditTime >= purchase.FToPurchaseDate ");
//			}
		}
		builder.appendSql(" and changRoom.fstate='"
				+ FDCBillStateEnum.AUDITTED_VALUE + "' ");
		builder.appendSql(" ) ");

		// 根据过滤界面时间条件过滤数据
		if (!showAll.booleanValue()) {
			if (preIntoSale.booleanValue()) {// 转销售日期
				builder.appendSql(" and purchase.FToSaleDate >= {ts '"
						+ map.get("beginDate")
						+ "'} and purchase.FToSaleDate < {ts '"
						+ map.get("endDate") + "'} ");
			} else {// 转认购日期
				builder.appendSql("  and purchase.FToPurchaseDate >= {ts '"
						+ map.get("beginDate")
						+ "'} and purchase.FToPurchaseDate < {ts '"
						+ map.get("endDate") + "'}  ");
			}
		} else {
			if (preIntoSale.booleanValue()) {// 转销售日期
				builder.appendSql("  and purchase.FToSaleDate is not null ");
			} else {// 转认购日期
				builder.appendSql(" and purchase.FToPurchaseDate is not null ");
			}
		}
	}
}