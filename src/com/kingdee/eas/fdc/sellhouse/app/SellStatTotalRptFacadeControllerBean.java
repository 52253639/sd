package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.RefundmentMoneyTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.jdbc.rowset.IRowSet;

public class SellStatTotalRptFacadeControllerBean extends AbstractSellStatTotalRptFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.SellStatTotalRptFacadeControllerBean");
    protected Map _getSellStatData(Context ctx, Map paramMap)throws BOSException, EASBizException
    {
    	Map result = new HashMap();
    	try {
    		//获取套数面积标准总价信息
    		Map dataMap = getTotalRptData(ctx, paramMap);
    		//获取合同总价成交总价优惠总价信息
			Map amountMap = getAmount(ctx, paramMap);
			//获取已签约房间补差款信息
			Map compentMap = getCompentAmount(ctx);
			//获取应收已收款项信息
			Map moneyMap = getMoneyMap(ctx, paramMap);
			result.put("roomData", dataMap);
			result.put("contracData", amountMap);
			result.put("compentAmount", compentMap);
			result.put("moneyData", moneyMap);
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
        return result;
    }
    
    /**
	 * 取出房间各销售状态的套数、面积、均价和标准总价
	 */
    private Map getTotalRptData(Context ctx, Map paramMap) throws BOSException, SQLException
    {
    	Map dataMap = new HashMap();
    	Map allRoomMap = new HashMap();
    	Map waitRoomMap = new HashMap();
    	Map noOrderMap = new HashMap();
    	Map orderRoomMap = new HashMap();
    	Map orderNoSellMap = new HashMap();
    	Map keepRoomMap = new HashMap();
    	Map preRoomMap = new HashMap();
    	Map purchaseMap = new HashMap();
    	Map signRoomMap = new HashMap();
    	Map sellRoomMap = new HashMap();
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select build.fid buildingID,room.fsellstate sellState,room.FIsForSHE isforSHE,count(room.fid) count, \n");
    	builder.appendSql("sum(room.fbuildingarea) buildArea, \n");
    	builder.appendSql("sum(room.froomarea) roomArea, \n");
    	builder.appendSql("sum(room.factualbuildingarea) actBuildArea, \n");
    	builder.appendSql("sum(room.factualroomarea) actRoomArea, \n");
    	builder.appendSql("sum(room.FStandardTotalAmount) totalAmount \n");
    	builder.appendSql("from t_she_room room \n");
    	builder.appendSql("left join t_she_building build on room.fbuildingid = build.fid ");
    	
    	/**
    	 * 是否包含附属房产
    	 */
    	Boolean includeAttach = (Boolean)(paramMap.get("IncludeAttachment"));
    	if(includeAttach.booleanValue())
    	{
    		builder.appendSql("\n where room.fhouseproperty in ('Attachment','NoAttachment') \n");
    	}
    	else
    	{
    		builder.appendSql("\n where room.fhouseproperty in ('NoAttachment') \n");
    	}
    	
    	builder.appendSql("group by build.fid,room.fsellstate,room.FIsForSHE \n");
    	builder.appendSql("order by room.fsellstate asc \n");
    	
        logger.info(builder.getTestSql());
    	
        Boolean includeOrder = (Boolean)paramMap.get("IncludeOrder");
        /**
    	 * 面积类型
    	 */
    	Boolean isBuildArea = (Boolean)paramMap.get("BuildArea");
    	Boolean isPreArea = (Boolean)paramMap.get("PreArea");
    	
    	IRowSet rowSet = builder.executeQuery(ctx);
    	while(rowSet.next())
    	{
    		Object[] allRoom = new Object[5];
        	Object[] waitRoom = new Object[5];
        	Object[] noOrder = new Object[5];
        	Object[] orderRoom = new Object[5];
        	Object[] orderNoSell = new Object[5];
        	Object[] keepRoom = new Object[5];
        	Object[] preRoom = new Object[5];
        	Object[] purchase = new Object[5];
        	Object[] signRoom = new Object[5];
        	Object[] sellRoom = new Object[5];
    		String buildID = rowSet.getString("buildingID");
    		String sellState = rowSet.getString("sellState");
    		boolean isforSHE = rowSet.getBoolean("isforSHE");
    		BigDecimal area = (BigDecimal)getArea(isBuildArea, isPreArea, rowSet);
    		area = area==null?new BigDecimal(0):area;
    		int count = rowSet.getInt("count");
    		BigDecimal totalAmount = rowSet.getBigDecimal("totalAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("totalAmount");
    		if(sellState!=null)
    		{
    			//总房源      			
    			allRoomMap = setDataMap(allRoomMap, allRoom, buildID, count, area, totalAmount,false);
    			//待售
    			if(RoomSellStateEnum.ONSHOW_VALUE.equals(sellState))
        		{
        			waitRoomMap = setDataMap(waitRoomMap, waitRoom, buildID, count, area, totalAmount, false);
        			//已推盘
        			orderRoomMap = setDataMap(orderRoomMap, orderRoom, buildID, count, area, totalAmount,false);       			
        			//已推盘未售   			
        			orderNoSellMap = setDataMap(orderNoSellMap, orderNoSell, buildID, count, area, totalAmount,false);
        		//未推盘
        		}else if(RoomSellStateEnum.INIT_VALUE.equals(sellState))
        		{
        			//非售楼房间不算未推盘房间，只是加在总房源和未售房源中
        			if(isforSHE)
        			{
        				//未推盘
            			noOrderMap = setDataMap(noOrderMap, noOrder, buildID, count, area, totalAmount, true);
        			}       			
        			//未售
        			waitRoomMap = setDataMap(waitRoomMap, waitRoom, buildID, count, area, totalAmount, false);
        		}else if(RoomSellStateEnum.KEEPDOWN_VALUE.equals(sellState))
        		{
        			keepRoomMap = setDataMap(keepRoomMap, keepRoom, buildID, count, area, totalAmount, false);
        			//已推盘
        			orderRoomMap = setDataMap(orderRoomMap, orderRoom, buildID, count, area, totalAmount,false);       			
        			//已推盘未售
        			orderNoSellMap = setDataMap(orderNoSellMap, orderNoSell, buildID, count, area, totalAmount,false);
        			//未售
        			waitRoomMap = setDataMap(waitRoomMap, waitRoom, buildID, count, area, totalAmount, false);
        			//预定
        		}else if(RoomSellStateEnum.PREPURCHASE_VALUE.equals(sellState))
        		{
//        			preRoomMap = setDataMap(preRoomMap, preRoom, buildID, count, area, totalAmount, false);
        			//已推盘      			
        			orderRoomMap = setDataMap(orderRoomMap, orderRoom, buildID, count, area, totalAmount,false);
        			
        			//已推盘未售要考虑预定是否计入销售统计
        			if(!includeOrder.booleanValue())
        			{       				
        				orderNoSellMap = setDataMap(orderNoSellMap, orderNoSell, buildID, count, area, totalAmount,false);
        				//未售
            			waitRoomMap = setDataMap(waitRoomMap, waitRoom, buildID, count, area, totalAmount, false);
        			}else
        			{
        				preRoomMap = setDataMap(preRoomMap, preRoom, buildID, count, area, totalAmount, false);
        				//那么预定算已售房源
        				sellRoomMap = setDataMap(sellRoomMap, sellRoom, buildID, count, area, totalAmount,false);
        			}
        		//认购
        		}else if(RoomSellStateEnum.PURCHASE_VALUE.equals(sellState))
        		{
        			purchaseMap = setDataMap(purchaseMap, purchase, buildID, count, area, totalAmount, false);
        			//已推盘
        			orderRoomMap = setDataMap(orderRoomMap, orderRoom, buildID, count, area, totalAmount,false);
        			
        			//认购也算已算房源
        			sellRoomMap = setDataMap(sellRoomMap, sellRoom, buildID, count, area, totalAmount,false);
        		//签约
        		}else if(RoomSellStateEnum.SIGN_VALUE.equals(sellState))
        		{
        			signRoomMap = setDataMap(signRoomMap, signRoom, buildID, count, area, totalAmount, false);
        			//已推盘
        			orderRoomMap = setDataMap(orderRoomMap, orderRoom, buildID, count, area, totalAmount,false);
        			
        			//签约也算已售房源
        			sellRoomMap = setDataMap(sellRoomMap, sellRoom, buildID, count, area, totalAmount,false);
        		}
    		}   		   		
    	}
    	dataMap.put("waitRoom", waitRoomMap);
    	dataMap.put("noOrder", noOrderMap);
    	dataMap.put("orderRoom", orderRoomMap);
    	dataMap.put("orderNoSell", orderNoSellMap);
    	dataMap.put("keepRoom", keepRoomMap);
    	dataMap.put("preRoom", preRoomMap);
    	dataMap.put("purchase", purchaseMap);
    	dataMap.put("signRoom", signRoomMap);
    	//已售房源
    	dataMap.put("sellRoom", sellRoomMap);
    	dataMap.put("allRoom", allRoomMap);
    	return dataMap;
    }
    
    //设置套数面积和标准总价分组信息
    private  Map setDataMap(Map dataMap,Object[] obj,String buildID,int count,BigDecimal area,BigDecimal totalAmount,boolean boo)
    {
    	if(boo)
    	{
    		obj[0] = new Integer(count);
    		obj[1] = area;
    		obj[2] = totalAmount;
    		dataMap.put(buildID, obj);
    	}else
    	{
    		if(dataMap.get(buildID)==null)
        	{
        		obj[0] = new Integer(count);
        		obj[1] = area;
        		obj[2] = totalAmount;
        		dataMap.put(buildID, obj);
        	}else
        	{
        		obj = (Object[])dataMap.get(buildID);
        		obj[0] = new Integer(((Integer)obj[0]).intValue()+count);
    			BigDecimal allRoomArea = obj[1]==null?new BigDecimal(0):(BigDecimal)obj[1];
    			obj[1] = allRoomArea.add(area);
    			BigDecimal allAmount = obj[2]==null?new BigDecimal(0):(BigDecimal)obj[2];
    			obj[2] = allAmount.add(totalAmount);
    			dataMap.put(buildID, obj);
        	}
    	}   	
    	return dataMap;
    }
    
    /**
	 * 找出认购签约状态的优惠总价成交总价和合同总价
	 * 预定的房间看是否计入销售统计
     * @throws BOSException 
     * @throws SQLException 
	 */
    private Map getAmount(Context ctx, Map paramMap) throws BOSException, SQLException
    {   	
    	Map dataMap = new HashMap();
    	
    	//认购
    	Map purchaseMap = new HashMap();
    	//签约
    	Map signMap = new HashMap();
    	//已售
    	Map sellRoomMap = new HashMap();
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select build.fid buildingID,room.fsellstate sellState, \n");
    	builder.appendSql("sum(room.fbuildingarea) buildArea, \n");
    	builder.appendSql("sum(room.froomarea) roomArea, \n");
    	builder.appendSql("sum(room.factualbuildingarea) actBuildArea, \n");
    	builder.appendSql("sum(room.factualroomarea) actRoomArea, \n");
    	builder.appendSql("sum(room.FStandardTotalAmount) totalAmount, \n");
    	builder.appendSql("sum(room.FDealTotalAmount) dealAmount, \n");
    	builder.appendSql("sum(pur.FContractTotalAmount) contactTotalAmount, \n");
    	builder.appendSql("sum(room.fbuildingarea*room.FBaseBuildingPrice) baseBuildAmount, \n");
    	builder.appendSql("sum(room.factualbuildingarea*room.FBaseBuildingPrice) baseActBuildAmount, \n");
    	builder.appendSql("sum(room.factualroomarea*room.FBaseRoomPrice) baseActRoomAmount, \n");
    	builder.appendSql("sum(room.froomarea*room.FBaseRoomPrice) baseRoomAmount \n");
    	builder.appendSql("from t_she_room room \n");
    	builder.appendSql("left join t_she_building build on room.fbuildingid = build.fid ");
    	builder.appendSql("left join t_she_purchase pur on room.flastpurchaseid = pur.fid ");
//    	builder.appendSql("left join T_SHE_RoomAttachmentEntry attach on attach.froomid = room.fid ");
    	
    	/**
    	 * 是否包含附属房产
    	 */
    	Boolean includeAttach = (Boolean)(paramMap.get("IncludeAttachment"));
    	if(includeAttach.booleanValue())
    	{
    		builder.appendSql("\n where room.fhouseproperty in ('Attachment','NoAttachment') \n");
    	}
    	else
    	{
    		builder.appendSql("\n where room.fhouseproperty in ('NoAttachment') \n");
    	}
    	
    	Boolean includeOrder = (Boolean)paramMap.get("IncludeOrder");
    	if(includeOrder.booleanValue())
    	{
    		builder.appendSql("\n and room.fsellstate in ('PrePurchase', 'Purchase', 'Sign') \n");
    	}
    	else
    	{
    		builder.appendSql("\n and room.fsellstate in ('Purchase', 'Sign') \n");
    	}
    	
    	builder.appendSql("group by build.fid,room.fsellstate  \n");
    	builder.appendSql("order by room.fsellstate asc \n");
    	
    	logger.info(builder.getTestSql());
     	
         /**
     	 * 面积类型
     	 */
     	Boolean isBuildArea = (Boolean)paramMap.get("BuildArea");
     	Boolean isPreArea = (Boolean)paramMap.get("PreArea");
     	
     	IRowSet rowSet = builder.executeQuery(ctx);
     	while(rowSet.next())
     	{
     		Object[] purchase = new Object[5];
     		Object[] sign = new Object[5];
     		Object[] sellRoom = new Object[5];
     		String buildID = rowSet.getString("buildingID");
    		String sellState = rowSet.getString("sellState");
    		BigDecimal area = (BigDecimal)getArea(isBuildArea, isPreArea, rowSet);
    		area = area==null?new BigDecimal(0):area;
    		//标准总价
    		BigDecimal totalAmount = rowSet.getBigDecimal("totalAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("totalAmount");
    		//成交总价
    		BigDecimal dealAmount = rowSet.getBigDecimal("dealAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("dealAmount");
    		//合同总价
    		BigDecimal contactTotalAmount = rowSet.getBigDecimal("contactTotalAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("contactTotalAmount");
    		BigDecimal youhuiAmount = totalAmount.subtract(dealAmount);
    		//底价总价
    		BigDecimal baseAmount = (BigDecimal)getBaseAmount(isBuildArea, isPreArea, rowSet);
    		baseAmount = baseAmount==null?new BigDecimal(0):baseAmount;
    		if(sellState!=null)
    		{
    			if(RoomSellStateEnum.SIGN_VALUE.equals(sellState))
    			{
    				setContracDataMap(signMap, sign, buildID, contactTotalAmount,dealAmount,youhuiAmount,baseAmount,area, true);  				
    				//签约也是已售
    				setContracDataMap(sellRoomMap, sellRoom, buildID, contactTotalAmount,dealAmount,youhuiAmount,baseAmount,area, false);
    			}else if(RoomSellStateEnum.PURCHASE_VALUE.equals(sellState))
    			{  				
    				setContracDataMap(purchaseMap, purchase, buildID, contactTotalAmount,dealAmount, youhuiAmount,baseAmount,area, true);   				
    				//认购也是已售
    				setContracDataMap(sellRoomMap, sellRoom, buildID, contactTotalAmount,dealAmount, youhuiAmount,baseAmount,area, false);
    			}else if(RoomSellStateEnum.PREPURCHASE_VALUE.equals(sellState))
    			{
    				if(includeOrder.booleanValue())
    				{
    					//如果预定计入销售统计预定也是已售
        				setContracDataMap(sellRoomMap, sellRoom, buildID,contactTotalAmount,dealAmount,youhuiAmount,baseAmount,area, false);
    				}
    			}
    		}   		   		
     	}
     	dataMap.put("purchaseAmount", purchaseMap);
     	dataMap.put("signAmount", signMap);
     	dataMap.put("sellRoomAmount", sellRoomMap);
    	return dataMap;
    }
    
    //设置合同总价成交总价和优惠总价分组信息
    private  Map setContracDataMap(Map dataMap,Object[] obj,String buildID,BigDecimal contactTotalAmount,BigDecimal dealAmount,BigDecimal youhuiAmount,BigDecimal baseAmount,BigDecimal area,boolean boo)
    {
    	if(boo)
    	{
    		obj[0] = contactTotalAmount;
    		obj[1] = dealAmount;
    		obj[2] = youhuiAmount;
    		obj[3] = area;
    		obj[4] = baseAmount;
    		dataMap.put(buildID, obj);
    	}else
    	{
    		if(dataMap.get(buildID)==null)
        	{
        		obj[0] = contactTotalAmount;
        		obj[1] = dealAmount;
        		obj[2] = youhuiAmount;
        		obj[3] = area;
        		obj[4] = baseAmount;
        		dataMap.put(buildID, obj);
        	}else
        	{
        		obj = (Object[])dataMap.get(buildID);
    			BigDecimal contactAmount = obj[0]==null?new BigDecimal(0):(BigDecimal)obj[0];
    			obj[0] = contactAmount.add(contactTotalAmount);
    			BigDecimal alldealAmount = obj[1]==null?new BigDecimal(0):(BigDecimal)obj[1];
    			obj[1] = alldealAmount.add(dealAmount);
    			BigDecimal youhuiAllAmount = obj[2]==null?new BigDecimal(0):(BigDecimal)obj[2];
    			obj[2] = youhuiAllAmount.add(youhuiAmount);
    			BigDecimal allRoomArea = obj[3]==null?new BigDecimal(0):(BigDecimal)obj[3];
    			obj[3] = allRoomArea.add(area);
    			BigDecimal allBaseAmount = obj[4]==null?new BigDecimal(0):(BigDecimal)obj[4];
    			obj[4] = allBaseAmount.add(baseAmount);
    			dataMap.put(buildID, obj);
        	}
    	}   	
    	return dataMap;
    }
    
    //应收已收款项分组信息
    private Map setMoneyMap(Map map,Object[] obj,String buildID,BigDecimal appAmount,BigDecimal actAmount)
    {
    	if(map.get(buildID)==null)
    	{
    		obj[0] = appAmount;
    		obj[1] = actAmount;
    		map.put(buildID, obj);
    	}else
    	{
    		obj = (Object[])map.get(buildID);
			BigDecimal allappAmount = obj[0]==null?new BigDecimal(0):(BigDecimal)obj[0];
			obj[0] = allappAmount.add(appAmount);
			BigDecimal allactAmount = obj[1]==null?new BigDecimal(0):(BigDecimal)obj[1];
			obj[1] = allactAmount.add(actAmount);
			map.put(buildID, obj);
    	}
    	return map;
    }
    
    //应收已收款项统计
    private Map getMoneyMap(Context ctx, Map paramMap) throws BOSException, SQLException
    {  
    	Map resultMap = new HashMap();
    	//结转定金
    	Map jiezhuanpreMoneyMap = new HashMap();
    	//定金
    	Map preMoneyMap = new HashMap();
    	//非定金
    	Map noPreMoneyMap = new HashMap();
    	//按揭
    	Map LoanAmountMap = new HashMap();
    	//公积金
    	Map AccFundAmountMap = new HashMap();
    	//非房款
    	Map noRoomMoneyMap = new HashMap();
    	//回款
    	Map huikuanMap = new HashMap();
    	Boolean includeOrder = (Boolean)paramMap.get("IncludeOrder");
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select buildid,moneyType,appamount,actamount,transAmount,refundAmount,justAmount from( \n");
    	//认购单收款明细
    	builder.appendSql("select build.fid buildid,moneydefine.FMoneyType moneyType, \n");
    	builder.appendSql("sum(paylist.fappamount) appamount, \n");
    	builder.appendSql("sum(paylist.factrevamount) actamount, \n");
    	builder.appendSql("sum(paylist.Fhastransferredamount) transAmount, \n");
    	builder.appendSql("sum(paylist.Fhasrefundmentamount) refundAmount, \n");
    	builder.appendSql("sum(paylist.Fhasadjustedamount) justAmount \n");
    	builder.appendSql("from t_she_room room \n");
    	builder.appendSql("left join t_she_building build on room.fbuildingid = build.fid \n");
    	builder.appendSql("left join t_she_purchase pur on room.flastpurchaseid = pur.fid \n");
    	builder.appendSql("left join t_she_purchasepaylistentry paylist on pur.fid = paylist.fheadid \n");
    	builder.appendSql("left join t_she_moneydefine moneydefine on paylist.fmoneydefineid = moneydefine.fid \n");
    	builder.appendSql("where moneydefine.FSysType = 'SalehouseSys' and (paylist.fappamount is not null or paylist.factrevamount is not null) \n");
    	if(includeOrder.booleanValue())
    	{
    		builder.appendSql("and room.fsellstate in ('PrePurchase', 'Purchase', 'Sign') \n");
    	}
    	else
    	{
    		builder.appendSql("and room.fsellstate in ('Purchase', 'Sign') \n");
    	}
    	builder.appendSql("group by build.fid,moneydefine.FMoneyType \n");
    	
    	builder.appendSql("union \n");
    	
    	//认购单其他应收明细
    	builder.appendSql("select build.fid buildid,moneydefine.FMoneyType moneyType, \n");
    	builder.appendSql("sum(paylist.fappamount) appamount, \n");
    	builder.appendSql("sum(paylist.factrevamount) actamount, \n");
    	builder.appendSql("sum(paylist.Fhastransferredamount) transAmount, \n");
    	builder.appendSql("sum(paylist.Fhasrefundmentamount) refundAmount, \n");
    	builder.appendSql("sum(paylist.Fhasadjustedamount) justAmount \n");
    	builder.appendSql("from t_she_room room \n");
    	builder.appendSql("left join t_she_building build on room.fbuildingid = build.fid \n");
    	builder.appendSql("left join t_she_purchase pur on room.flastpurchaseid = pur.fid \n");
    	builder.appendSql("left join T_SHE_PurchaseElsePayListEntry paylist on pur.fid = paylist.fheadid \n");
    	builder.appendSql("left join t_she_moneydefine moneydefine on paylist.fmoneydefineid = moneydefine.fid \n");
    	builder.appendSql("where moneydefine.FSysType = 'SalehouseSys' and (paylist.fappamount is not null or paylist.factrevamount is not null) \n");
    	if(includeOrder.booleanValue())
    	{
    		builder.appendSql("and room.fsellstate in ('PrePurchase', 'Purchase', 'Sign') \n");
    	}
    	else
    	{
    		builder.appendSql("and room.fsellstate in ('Purchase', 'Sign') \n");
    	}
    	builder.appendSql("group by build.fid,moneydefine.FMoneyType \n");
    	builder.appendSql(") result ");
    	builder.appendSql("order by moneyType");
    	
    	logger.info(builder.getTestSql());
    	
    	IRowSet rowSet = builder.executeQuery(ctx);
     	while(rowSet.next())
     	{
     		//结转定金
     		Object[] jiezhuanpreMoney = new Object[4];
     		//定金类款项
     		Object[] preMoney = new Object[4];
     		//非定金类款项
     		Object[] noPreMoney = new Object[4];
     		//按揭类款项
     		Object[] LoanAmount = new Object[4];
     		//公积金类款项
     		Object[] AccFundAmount = new Object[4];
     		//非房款
     		Object[] noRoomMoney = new Object[4];
     		//回款合计
     		Object[] huikuanMoney = new Object[3];
     		String buildID = rowSet.getString("buildid");
     		String moneyType = rowSet.getString("moneyType");
     		//应收金额
     		BigDecimal appamount = rowSet.getBigDecimal("appamount")==null?new BigDecimal(0):rowSet.getBigDecimal("appamount");
     		//实收金额
     		BigDecimal revamount = rowSet.getBigDecimal("actamount")==null?new BigDecimal(0):rowSet.getBigDecimal("actamount");
     		//已转金额
     		BigDecimal transAmount = rowSet.getBigDecimal("transAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("transAmount");
     		//已退金额
     		BigDecimal refundAmount = rowSet.getBigDecimal("refundAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("refundAmount");
     		//已调金额
     		BigDecimal justAmount = rowSet.getBigDecimal("justAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("justAmount");
     		//已经金额=实收金额-已转金额-已退金额-已调金额
     		BigDecimal actamount = revamount.subtract(transAmount).subtract(refundAmount).subtract(justAmount);
     		//预定金,定金(定金类款项)
     		if(MoneyTypeEnum.PRECONCERTMONEY_VALUE.equals(moneyType) || MoneyTypeEnum.EARNESTMONEY_VALUE.equals(moneyType))
     		{   	
     			//结转定金
     			if(MoneyTypeEnum.EARNESTMONEY_VALUE.equals(moneyType))
     			{
     				setMoneyMap(jiezhuanpreMoneyMap, jiezhuanpreMoney, buildID, appamount, transAmount);
     			}    			
     			//定金类款项
     			setMoneyMap(preMoneyMap, preMoney, buildID, appamount, actamount);
     			setMoneyMap(huikuanMap, huikuanMoney, buildID, appamount, actamount);
     		// 首期,楼款(非定金类款项)
     		}else if(MoneyTypeEnum.FISRTAMOUNT_VALUE.equals(moneyType) || MoneyTypeEnum.HOUSEAMOUNT_VALUE.equals(moneyType))
     		{
     			setMoneyMap(noPreMoneyMap, noPreMoney, buildID, appamount, actamount);
     			setMoneyMap(huikuanMap, huikuanMoney, buildID, appamount, actamount);
     		//按揭
     		}else if(MoneyTypeEnum.LOANAMOUNT_VALUE.equals(moneyType))
     		{
     			//按揭类款项
     			setMoneyMap(LoanAmountMap, LoanAmount, buildID, appamount, actamount);
     			setMoneyMap(huikuanMap, huikuanMoney, buildID, appamount, actamount);
     		//公积金
     		}else if(MoneyTypeEnum.ACCFUNDAMOUNT_VALUE.equals(moneyType))
     		{
     			//公积金类款项
     			setMoneyMap(AccFundAmountMap, AccFundAmount, buildID, appamount, actamount);
     			setMoneyMap(huikuanMap, huikuanMoney, buildID, appamount, actamount);
     		//装修款,滞纳金,手续费,代收费用,其他类型(非房款类款项)
     		}else if(MoneyTypeEnum.FITMENTAMOUNT_VALUE.equals(moneyType) || MoneyTypeEnum.LATEFEE_VALUE.equals(moneyType) 
     				|| MoneyTypeEnum.COMMISSIONCHARGE_VALUE.equals(moneyType) || MoneyTypeEnum.REPLACEFEE_VALUE.equals(moneyType)
     				|| MoneyTypeEnum.ELSEAMOUNT_VALUE.equals(moneyType))
     		{
     			setMoneyMap(noRoomMoneyMap, noRoomMoney, buildID, appamount, actamount);  
     			setMoneyMap(huikuanMap, huikuanMoney, buildID, appamount, actamount);
     		}
     	}
     	resultMap.put("jiezhuanMoney",jiezhuanpreMoneyMap);
     	resultMap.put("preMoney",preMoneyMap);
     	resultMap.put("noPreMoney",noPreMoneyMap);
     	resultMap.put("LoanAmount",LoanAmountMap);
     	resultMap.put("AccFundAmount",AccFundAmountMap);
     	resultMap.put("noRoomMoney", noRoomMoneyMap);
     	resultMap.put("huikuanMoney",huikuanMap);
    	return resultMap;
    }
    
    
    /**
	 * 已签约房间的补差金额
	 */
    private Map getCompentAmount(Context ctx) throws SQLException, BOSException
    {
    	Map compentMap = new HashMap();
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select build.fid buildingID,room.fsellstate sellState, \n");
    	builder.appendSql("sum(com.FCompensateAmount) compentAmount, sum(comRevList.FActRevAmount) compentRevAmount, \n");
    	builder.appendSql("sum(comRevList.Fhastransferredamount) transAmount, \n");
    	builder.appendSql("sum(comRevList.Fhasrefundmentamount) refundAmount, \n");
    	builder.appendSql("sum(comRevList.Fhasadjustedamount) justAmount \n");
    	builder.appendSql("from t_she_room room ");
    	builder.appendSql("left join t_she_building build on room.fbuildingid = build.fid \n");
    	builder.appendSql("left join T_SHE_RoomAreaCompensate com on room.fid = com.froomid \n");
    	builder.appendSql("left join T_SHE_AreaCompensateRevList comRevList on com.fid = comRevList.fheadid  \n");
    	builder.appendSql("where com.fstate ='4AUDITTED' \n");
    	builder.appendSql("group by build.fid,room.fsellstate   \n");
    	builder.appendSql("order by room.fsellstate asc  \n");
    	
    	logger.info(builder.getTestSql());    	
    	
    	IRowSet rowSet = builder.executeQuery(ctx);
    	while(rowSet.next())
    	{
    		Object[] compent = new Object[3];
    		String buildID = rowSet.getString("buildingID");
    		
    		//退款款项类型
//    		String refundMoneyType = rowSet.getString("refundMoneyType");
    		//应收补差金额
    		BigDecimal compentAmount = rowSet.getBigDecimal("compentAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("compentAmount");
    		//如果退款类型为应收退款则补差金额为正，如退款类型为应退退款则补差金额为负
//    		if(RefundmentMoneyTypeEnum.APPREFUNDMENT_VALUE.equals(refundMoneyType))
//    		{
//    			compentAmount = compentAmount.negate();
//    		}
    		//实收补差金额
     		BigDecimal revamount = rowSet.getBigDecimal("compentRevAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("compentRevAmount");
     		//已转补差金额
     		BigDecimal transAmount = rowSet.getBigDecimal("transAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("transAmount");
     		//已退补差金额
     		BigDecimal refundAmount = rowSet.getBigDecimal("refundAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("refundAmount");
     		//已调补差金额
     		BigDecimal justAmount = rowSet.getBigDecimal("justAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("justAmount");
     		//已经金额=实收金额-已转金额-已退金额-已调金额
     		BigDecimal compentRevAmount = revamount.subtract(transAmount).subtract(refundAmount).subtract(justAmount);
    		//已收补差金额
//    		BigDecimal compentRevAmount = rowSet.getBigDecimal("compentRevAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("compentRevAmount");
     		if(compentMap.get(buildID)==null)
     		{
     			compent[0] = compentAmount;
        		compent[1] = compentRevAmount;
        		compentMap.put(buildID, compent);
     		}else
     		{
     			compent = (Object[])compentMap.get(buildID);
     			BigDecimal amount = (BigDecimal)compent[0];
     			amount = amount==null?new BigDecimal(0):amount;
     			amount = amount.add(compentAmount);
     			compent[0] = amount;
     			BigDecimal revAmount = (BigDecimal)compent[1];
     			revAmount = revAmount==null?new BigDecimal(0):revAmount;
     			revAmount = revAmount.add(compentRevAmount);
     			compent[1] = revAmount;
        		compentMap.put(buildID, compent);
     		}
    		
    	}
    	return compentMap;
    }
    
    /**
	 * 根据选择的面积类型来取底价总价
	 */
    private Object getBaseAmount(Boolean isBuildArea,Boolean isPreArea,IRowSet rowSet) throws SQLException
    {
    	//底价总价
		//如果选的建筑面积和预测面积，则底价总价取建筑预测底价总价
		if(isBuildArea.booleanValue() && isPreArea.booleanValue())
		{
			return rowSet.getBigDecimal("baseBuildAmount");
			//如果选的建筑面积和实测面积，则底价总价取建筑实测底价总价
		}else if(isBuildArea.booleanValue() && !isPreArea.booleanValue())
		{
			return  rowSet.getBigDecimal("baseActBuildAmount");
			//如果选的套内面积和实测面积，，则底价总价取套内实测底价总价
		}else if(!isBuildArea.booleanValue() && !isPreArea.booleanValue())
		{
			return  rowSet.getBigDecimal("baseActRoomAmount");
			//如果选的套内面积和预测面积，则底价总价取套内预测底价总价
		}else
		{
			return rowSet.getBigDecimal("baseRoomAmount");
		}
    }
    
    /**
	 * 根据选择的面积类型来取面积值
	 */
    private Object getArea(Boolean isBuildArea,Boolean isPreArea,IRowSet rowSet) throws SQLException
    {
    	//总面积
		//如果选的建筑面积和预测面积，则总面积去预测建筑面积
		if(isBuildArea.booleanValue() && isPreArea.booleanValue())
		{
			return rowSet.getBigDecimal("buildArea");
			//如果选的建筑面积和实测面积，则总面积去实测建筑面积
		}else if(isBuildArea.booleanValue() && !isPreArea.booleanValue())
		{
			return  rowSet.getBigDecimal("actBuildArea");
			//如果选的套内面积和实测面积，则总面积去实测套内面积
		}else if(!isBuildArea.booleanValue() && !isPreArea.booleanValue())
		{
			return  rowSet.getBigDecimal("actRoomArea");
			//如果选的套内面积和预测面积，则总面积去预测套内面积
		}else
		{
			return rowSet.getBigDecimal("roomArea");
		}
    }
}