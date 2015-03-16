package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.jdbc.rowset.IRowSet;

public class MonthSellReportFacadeControllerBean extends AbstractMonthSellReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.MonthSellReportFacadeControllerBean");
    
    private Map rowByIdsMap = null;
    
    private Set comanyIdSet = null;
    private Set sellProIdSet = null;
    private Set proOrBuildIdSet = null;
    
    private boolean isAuditDate = false;
    
    
    protected Map _getAllData(Context ctx, Map params)throws BOSException, EASBizException
    {
    	Map resultMap = new HashMap();
    	
    	Boolean temp = (Boolean)params.get("isAuditDate");
    	if(temp!=null){
    		isAuditDate = temp.booleanValue();
    	}
    	
		boolean isIncludeAttach = params.get("isIncludeAttach")==null?false:(((Integer)params.get("isIncludeAttach")).intValue()>0?true:false);
		boolean showByProductType = params.get("showByProductType")==null?false:(((Integer)params.get("showByProductType")).intValue()>0?true:false);
		boolean showByBuilding = params.get("showByBuilding")==null?false:(((Integer)params.get("showByBuilding")).intValue()>0?true:false);
		
		SaleOrgUnitInfo saleOrg = (SaleOrgUnitInfo)params.get("saleOrg");
		Set buildingIds = (Set)params.get("allBuildingIds");
		String buildid =null;
		if(buildingIds!=null){
	    	Iterator iter = (Iterator)buildingIds.iterator();
	    	
	    	while(iter.hasNext()){
	    		if("".equals(buildid) || buildid == null){
	    			buildid = "'"+(String)iter.next()+"'";
	    		}else{
	    			buildid = buildid+",'"+(String)iter.next()+"'";
	    		}
	    	}
		}
		boolean chkIsShowSpecial =true ;//= params.get("chkIsShowSpecial")==null?false:(((Integer)params.get("chkIsShowSpecial")).intValue()>0?true:false);

		String showByType = "PRODUCTTYPE.FID";
		if(showByBuilding)	 {
			showByType = "BUILDING.FID";
		}
		//SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		String baseFilter="1=1 ";
		//baseFilter+= " and "+showByType+"='ZYuMhVM9Tz6Cze+eRjw/k+EgPpc='";
		
		
		if(buildid!=null){
			baseFilter+=" and ROOM.FBuildingID in ( "+buildid+" )";
		}else if(saleOrg!=null){
			baseFilter = " (ORGUNIT.FNumber = '"+saleOrg.getNumber().trim()+"' or ORGUNIT.FLongNumber like '"+saleOrg.getLongNumber().trim()+"!%' or ORGUNIT.FLongNumber like '%!"+saleOrg.getLongNumber().trim()+"!%') ";
		}
		/*if(saleOrg!=null){
			 baseFilter = " (ORGUNIT.FNumber = '"+saleOrg.getNumber().trim()+"' or ORGUNIT.FLongNumber like '"+saleOrg.getLongNumber().trim()+"!%' or ORGUNIT.FLongNumber like '%!"+saleOrg.getLongNumber().trim()+"!%') ";
		}*/
		//baseFilter += " and ROOM.FIsForSHE = 1 ";	
		
		String baseFilter2=baseFilter;
		if(!isIncludeAttach) baseFilter += " and Room.FHouseProperty != '"+ HousePropertyEnum.ATTACHMENT_VALUE +"'";
		
		try {
			//Table1  查询出：  总套数、预售建筑面积、实测建筑面积    
			intTable1(baseFilter,showByType,ctx);
			//-- 已售套数、已售面积、应收合同总价  、 应收（退 ）面积差
			intTable2(baseFilter2,showByType,"all",params,ctx);  //累计
			intTable2(baseFilter2,showByType,"month",params,ctx);  //按月
			intTable2(baseFilter2,showByType,"year",params,ctx);  //按年
			//销售回款     //按揭款[回款]   //已收（退 ）面积差
			intTable3(baseFilter2,showByType,"all",params,ctx);	//累计      
			intTable3(baseFilter2,showByType,"month",params,ctx);	//按月
			intTable3(baseFilter2,showByType,"year",params,ctx);	//按年
		
			//欠收房款
			intTable4(baseFilter2,showByType,"all",params,ctx);   //累计    
			intTable4(baseFilter2,showByType,"month",params,ctx); //按月
			intTable4(baseFilter2,showByType,"year",params,ctx);  //按年
			
			if(chkIsShowSpecial) {   //特殊业务处理
				this.dealForQuitRoom(baseFilter, showByType, "month",params,ctx);
				this.dealForQuitRoom(baseFilter, showByType, "year",params,ctx);
				
				//this.dealForAdjustRoom(baseFilter, showByType, "month");
				//this.dealForAdjustRoom(baseFilter, showByType, "year");
				
				this.dealForPurchaseChange(baseFilter, showByType, "month",params,ctx);
				this.dealForPurchaseChange(baseFilter, showByType, "year",params,ctx);
				this.dealForChangeRoom(baseFilter, showByType, "month",params,ctx);
				this.dealForChangeRoom(baseFilter, showByType, "year",params,ctx);
			}			
			
		    Map comanyIdNameMap = getCompayIdNameMap(ctx);
		    Map sellProIdNameMap = getSellProIdNameMap(ctx);
		    Map proOrBuildNameMap = getProductOrBuildIdNameMap(showByBuilding,ctx);
		    Map subAreaMap = getSubAreaIdNameMap(showByBuilding,ctx);
		
		    resultMap.put("comanyIdNameMap", comanyIdNameMap);
			resultMap.put("sellProIdNameMap", sellProIdNameMap);
			resultMap.put("proOrBuildNameMap", proOrBuildNameMap);
			resultMap.put("subAreaMap", subAreaMap);
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		resultMap.put("rowByIdsMap", rowByIdsMap);
		resultMap.put("comanyIdSet", comanyIdSet);
		resultMap.put("sellProIdSet", sellProIdSet);
		resultMap.put("proOrBuildIdSet", proOrBuildIdSet);
		
        return resultMap;
    }
    
    
  //Table1  查询出：  总套数、预售建筑面积、实测建筑面积    
	private void intTable1(String baseFilter,String showByType,Context ctx) throws BOSException, SQLException {	
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);		
		builder.appendSql("SELECT ORGUNIT.FID AS OID, SELLPROJECT.FID AS SID," + showByType + " AS PBID,\n");
		builder.appendSql("Count(ROOM.FID) AS totalRoomcount, \n");
		builder.appendSql("Sum(case when ROOM.FBuildingArea is null then 0 else ROOM.FBuildingArea end) AS buildingArea,\n"); 
		builder.appendSql("Sum(case when ROOM.FActualBuildingArea is null then 0 else ROOM.FActualBuildingArea end) AS actureBuildArea \n"); 
		builder.appendSql("FROM T_SHE_Room AS ROOM \n");
		builder.appendSql("LEFT OUTER JOIN  T_SHE_Building AS BUILDING	ON ROOM.FBuildingID = BUILDING.FID \n"); 
		builder.appendSql("LEFT OUTER JOIN T_FDC_ProductType AS PRODUCTTYPE ON ROOM.FProductTypeID = PRODUCTTYPE.FID \n"); 
		builder.appendSql("INNER JOIN T_SHE_SellProject AS SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID \n"); 
		builder.appendSql("INNER JOIN T_ORG_BaseUnit AS ORGUNIT ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID \n"); 
		builder.appendSql("where \n");
		builder.appendSql(baseFilter);
		builder.appendSql(" GROUP BY ORGUNIT.FID, SELLPROJECT.FID,"+showByType+" \n");
		builder.appendSql(" order by ORGUNIT.FID, SELLPROJECT.FID \n");
		
		rowByIdsMap = new HashMap();
	    comanyIdSet = new HashSet();
	    sellProIdSet = new HashSet();
	    proOrBuildIdSet = new HashSet();
		IRowSet tableSet = builder.executeQuery();
		int index = 0;
		while (tableSet.next()) {
			String OID = tableSet.getString("OID");
			String SID = tableSet.getString("SID");
			String PBID = tableSet.getString("PBID");
			int totalRoomcount = tableSet.getInt("totalRoomcount");
			BigDecimal buildingArea = tableSet.getBigDecimal("buildingArea");
			BigDecimal actureBuildArea = tableSet.getBigDecimal("actureBuildArea");
			
			//IRow row = this.tblMain.addRow();
			Map row= initRowMap(index);
			row.put("OID",OID);
			row.put("SID",SID);
			row.put("PBID",PBID);
			row.put("totalRoomcount",new Integer(totalRoomcount));
			row.put("buildingArea",buildingArea);
			row.put("actureBuildArea",actureBuildArea);
			String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
			rowByIdsMap.put(idStr, row);
			
			comanyIdSet.add(OID);
			sellProIdSet.add(SID);
			proOrBuildIdSet.add(PBID);
			index++;
		}
	}
	//-- 已售套数、已售面积、应收合同总价 、（应收（退 ）面积差 ）  --- （本月或本年的只是多个时间范围)  
	//byType 为all代表累计，month代表按月，year代表按年
	private void intTable2(String baseFilter,String showByType,String byType,Map filterMap,Context ctx) throws BOSException, SQLException {
		if(byType==null || "all month year".indexOf(byType)<0) return;	

		//Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
		Date beginMonthDate = filterMap.get("BeginMonthDate")==null?null:new Date(((Timestamp)filterMap.get("BeginMonthDate")).getTime());
		Date endMonthDate = filterMap.get("EndMonthDate")==null?null:new Date(((Timestamp)filterMap.get("EndMonthDate")).getTime());
		Date beginYearDate = filterMap.get("BeginYearDate")==null?null:new Date(((Timestamp)filterMap.get("BeginYearDate")).getTime());
		Date endYearDate = filterMap.get("EndYearDate")==null?null:new Date(((Timestamp)filterMap.get("EndYearDate")).getTime());
		boolean isPreIntoSale = filterMap.get("isPreIntoSale")==null?false:(((Integer)filterMap.get("isPreIntoSale")).intValue()>0?true:false);	
	
		String dataCompareParam = "PURCHASE.FToSaleDate";
		if(!isPreIntoSale) dataCompareParam = "PURCHASE.FToPurchaseDate";
		
		String[] columnNames = {byType+"RoomCount",byType+"AreaSum",byType+"PriceSum",byType+"CompensateSum"};
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		
		builder.appendSql("SELECT aa.OID, aa.SID, aa.PBID,\n");
		builder.appendSql("sum(aa."+columnNames[0]+") "+columnNames[0]+", \n");
		builder.appendSql("sum(aa."+columnNames[1]+") "+columnNames[1]+",\n");
		builder.appendSql("sum(aa."+columnNames[2]+") "+columnNames[2]+",\n");
		builder.appendSql("sum(aa."+columnNames[3]+") "+columnNames[3]+" from (  \n");

		
		//非附属房产
		builder.appendSql("SELECT ORGUNIT.FID AS OID, SELLPROJECT.FID AS SID," + showByType + " AS PBID,\n");
		builder.appendSql("Count(ROOM.FID) AS "+columnNames[0]+", \n");
		builder.appendSql("Sum(case when ROOM.FSaleArea is null then (case when FSaleArea is null then 0 else FSaleArea end) else ROOM.FSaleArea end) AS "+columnNames[1]+"\n"); 
//FSaleArea
		builder.appendSql(",Sum(case when PURCHASE.FContractTotalAmount is null then 0 else PURCHASE.FContractTotalAmount end) AS "+columnNames[2]+" \n");
		
		builder.appendSql(",Sum(case when areaCompensate.FCompensateAmount is null then 0 else(case when areaCompensate.Fstate!='"+FDCBillStateEnum.AUDITTED_VALUE+"' then 0 else  areaCompensate.FCompensateAmount  end ) end ) AS "+columnNames[3]+" \n");
		
		builder.appendSql("FROM T_SHE_Room AS ROOM \n");
		builder.appendSql("left JOIN T_SHE_Purchase AS PURCHASE ON ROOM.FLastPurchaseID = PURCHASE.FID \n");
		builder.appendSql("left join t_she_RoomAreaCompensate areaCompensate on areaCompensate.FROOMID = ROOM.FID \n");
		builder.appendSql("LEFT OUTER JOIN  T_SHE_Building AS BUILDING	ON ROOM.FBuildingID = BUILDING.FID \n"); 
		builder.appendSql("LEFT OUTER JOIN T_FDC_ProductType AS PRODUCTTYPE ON ROOM.FProductTypeID = PRODUCTTYPE.FID \n"); 
		builder.appendSql("INNER JOIN T_SHE_SellProject AS SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID \n"); 
		builder.appendSql("INNER JOIN T_ORG_BaseUnit AS ORGUNIT ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID \n");
		builder.appendSql("where \n");
		builder.appendSql(baseFilter);
		builder.appendSql(" and Room.FHouseProperty != '"+ HousePropertyEnum.ATTACHMENT_VALUE +"'");
		if(isPreIntoSale) {
			builder.appendSql(" and ROOM.fSellState in ('"+RoomSellStateEnum.PREPURCHASE_VALUE+"','"+RoomSellStateEnum.PURCHASE_VALUE+"','"+RoomSellStateEnum.SIGN_VALUE+"') \n");
		}else{
			builder.appendSql(" and ROOM.fSellState in ('"+RoomSellStateEnum.PURCHASE_VALUE+"','"+RoomSellStateEnum.SIGN_VALUE+"') \n");
		}
		//builder.appendSql(" AND ROOM.FIsForSHE=1 ");
		if(byType.equals("month") || byType.equals("year")) {
			builder.appendSql(" and "+dataCompareParam + ">= ? \n");
			builder.appendSql(" and "+dataCompareParam + "< ? \n");
			if(byType.equals("month")) {
				builder.addParam(FDCDateHelper.getSqlDate(beginMonthDate));
				builder.addParam(FDCDateHelper.getSqlDate(endMonthDate));
			}else if(byType.equals("year")) {
				builder.addParam(FDCDateHelper.getSqlDate(beginYearDate));
				builder.addParam(FDCDateHelper.getSqlDate(endYearDate));
			}
		}
		builder.appendSql(" GROUP BY ORGUNIT.FID, SELLPROJECT.FID,"+showByType+" \n");

		
		///getSimpleSqlWhere(builder,byType,showByType,filterMap);
		
		
		
		//附属房产
		boolean isIncludeAttach = filterMap.get("isIncludeAttach")==null?false:(((Integer)filterMap.get("isIncludeAttach")).intValue()>0?true:false);
		
    	if(isIncludeAttach)
    	{
    		builder.appendSql("union \n");
    		
    		
    		builder.appendSql("SELECT ORGUNIT.FID AS OID, SELLPROJECT.FID AS SID," + showByType + " AS PBID, \n");
    		builder.appendSql("Count(ROOM.FID) AS "+columnNames[0]+", ");
    		builder.appendSql("Sum(case when ROOM.FSaleArea is null then (case when FSaleArea is null then 0 else FSaleArea end) else ROOM.FSaleArea end) AS "+columnNames[1]+","); 
    		builder.appendSql(" 0 AS "+columnNames[2]+" ");
    		builder.appendSql(",0 AS "+columnNames[3]+" ");

    		builder.appendSql("from T_SHE_PurchaseRoomAttachEntry attach \n");
        	builder.appendSql("left join T_SHE_RoomAttachmentEntry roomAttach on attach.FAttachmentEntryID = roomAttach.fid \n");
        	builder.appendSql("left join t_she_purchase purchase on attach.fheadid = purchase.fid \n");
        	builder.appendSql("left join t_she_room ROOM on roomAttach.froomid = ROOM.fid \n");
        	builder.appendSql("LEFT OUTER JOIN  T_SHE_Building AS BUILDING	ON ROOM.FBuildingID = BUILDING.FID \n"); 
        	builder.appendSql("left join t_fdc_producttype PRODUCTTYPE on PRODUCTTYPE.FID = ROOM.FProductTypeID \n");
        	builder.appendSql("left join T_SHE_SellProject AS SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID \n"); 
    		builder.appendSql("left join T_ORG_BaseUnit AS ORGUNIT ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID \n");
        	
        	
    		builder.appendSql("where \n");
    		builder.appendSql(baseFilter);
    		if(isPreIntoSale) {
    			builder.appendSql(" and ROOM.fSellState in ('"+RoomSellStateEnum.PREPURCHASE_VALUE+"','"+RoomSellStateEnum.PURCHASE_VALUE+"','"+RoomSellStateEnum.SIGN_VALUE+"') \n");
    		}else{
    			builder.appendSql(" and ROOM.fSellState in ('"+RoomSellStateEnum.PURCHASE_VALUE+"','"+RoomSellStateEnum.SIGN_VALUE+"') \n");
    		}
    		if(byType.equals("month") || byType.equals("year")) {
    			builder.appendSql(" and "+dataCompareParam + ">= ? \n");
    			builder.appendSql(" and "+dataCompareParam + "< ? \n");
    			if(byType.equals("month")) {
    				builder.addParam(FDCDateHelper.getSqlDate(beginMonthDate));
    				builder.addParam(FDCDateHelper.getSqlDate(endMonthDate));
    			}else if(byType.equals("year")) {
    				builder.addParam(FDCDateHelper.getSqlDate(beginYearDate));
    				builder.addParam(FDCDateHelper.getSqlDate(endYearDate));
    			}
    		}
    		
    		
    		builder.appendSql(" GROUP BY ORGUNIT.FID, SELLPROJECT.FID,"+showByType+" \n");
    	}
    	
    	builder.appendSql(" )aa \n");
    	builder.appendSql("GROUP BY aa.OID, aa.SID, aa.PBID \n");
    	
    	/*String tempbaseFilter = baseFilter;
    	tempbaseFilter+="";
    	if(isPreIntoSale) {
    		tempbaseFilter+= " and ROOM.fSellState in ('"+RoomSellStateEnum.PREPURCHASE_VALUE+"','"+RoomSellStateEnum.PURCHASE_VALUE+"','"+RoomSellStateEnum.SIGN_VALUE+"') \n";
		}else{
			tempbaseFilter+="  and ROOM.fSellState in ('"+RoomSellStateEnum.PURCHASE_VALUE+"','"+RoomSellStateEnum.SIGN_VALUE+"') \n";
		}
    	*/
		
		IRowSet tableSet = builder.executeQuery();

		while (tableSet.next()) {
			String OID = tableSet.getString("OID");
			String SID = tableSet.getString("SID");
			String PBID = tableSet.getString("PBID");
			int roomcount = tableSet.getInt(columnNames[0]);
			BigDecimal areaSum = tableSet.getBigDecimal(columnNames[1]);
			BigDecimal priceSum = tableSet.getBigDecimal(columnNames[2]);
			BigDecimal areaCompensateAmount = tableSet.getBigDecimal(columnNames[3]);
			
			//合同总价 +补差金额
			priceSum =priceSum.add(areaCompensateAmount);
			
			String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
			Map row = (Map)rowByIdsMap.get(idStr);
			if(row!=null)  {
				row.put(columnNames[0],new Integer(roomcount));
				row.put(columnNames[1],areaSum);
				row.put(columnNames[2],priceSum);					
				row.put(columnNames[3],areaCompensateAmount);
			}
		}
		
		//unused 
		//FDCSQLBuilder priceSumbuilder = getPriceSumSql(tempbaseFilter,showByType,byType,filterMap,ctx,"ROOM");
		//IRowSet priceSumtableSet = priceSumbuilder.executeQuery();
		/*while (priceSumtableSet.next()) {
			String OID = priceSumtableSet.getString("OID");
			String SID = priceSumtableSet.getString("SID");
			String PBID = priceSumtableSet.getString("PBID");
			BigDecimal priceSum = priceSumtableSet.getBigDecimal(columnNames[2]);
			
			String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
			Map row = (Map)rowByIdsMap.get(idStr);
			if(row!=null)  {
				//row.get(columnNames[2],priceSum);
				addDecimalValueToCell(row,columnNames[2],priceSum);
			}
		}*/
	}
    
    
    
	//-- 销售回款   、  按揭款[回款] 、已收（退 ）面积差    （本月或本年的只是多个时间范围)  
	//byType 为all代表累计，month代表按月，year代表按年
	private void intTable3(String baseFilter,String showByType,String byType,Map filterMap,Context ctx) throws BOSException, SQLException {
		if(byType==null || "all month year".indexOf(byType)<0) return;

		//Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
		Date beginMonthDate = filterMap.get("BeginMonthDate")==null?null:new Date(((Timestamp)filterMap.get("BeginMonthDate")).getTime());
		Date endMonthDate = filterMap.get("EndMonthDate")==null?null:new Date(((Timestamp)filterMap.get("EndMonthDate")).getTime());
		Date beginYearDate = filterMap.get("BeginYearDate")==null?null:new Date(((Timestamp)filterMap.get("BeginYearDate")).getTime());
		Date endYearDate = filterMap.get("EndYearDate")==null?null:new Date(((Timestamp)filterMap.get("EndYearDate")).getTime());
		boolean isPreIntoSale = filterMap.get("isPreIntoSale")==null?false:(((Integer)filterMap.get("isPreIntoSale")).intValue()>0?true:false);	
			
		
		
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("SELECT ORGUNIT.FID AS OID, SELLPROJECT.FID AS SID," + showByType + " AS PBID,MoneyDefine.FMoneyType,\n");
		////   定金不隶属于房款 ,  预收款转  不算到销售回款内  
		builder.appendSql(" Sum( \n");
		
		builder.appendSql(" case when  PURCHASE.FisEarnestInHouseAmount=0 and MoneyDefine.FMoneyType='PurchaseAmount' then 0 else  \n");
		
		builder.appendSql("case when FDCEntry.FRevAmount is null then 0 else  \n");
		builder.appendSql(" 	case when  MoneyDefine.FMoneyType='PreconcertMoney' and ROOM.fSellState!='PrePurchase' then 0 else \n");
		builder.appendSql("	   		case when ROOM.fSellState='PrePurchase' and MoneyDefine.FMoneyType='PreconcertMoney' and PURCHASE.FPurchaseState='PurchaseAudit' then 0 \n");
		builder.appendSql("	   		else FDCEntry.FRevAmount end \n");
		builder.appendSql("     end \n");
		builder.appendSql(" end \n");
		builder.appendSql("end \n");
		builder.appendSql("	) AS resvSum \n");
		
		//定金
		builder.appendSql(", sum(case when   MoneyDefine.FMoneyType='PurchaseAmount' and FDCEntry.FRevAmount is not null then FDCEntry.FRevAmount else 0 end) as PurchaseAmounts  \n");

		builder.appendSql("FROM T_BDC_FDCReceivingBillEntry AS FDCEntry \n");
		builder.appendSql("LEFT OUTER JOIN T_BDC_FDCReceivingBill AS FDC ON FDCEntry.FHeadID = FDC.FID \n"); 
		
		builder.appendSql("inner JOIN T_SHE_Room AS ROOM ON FDC.FRoomID = ROOM.FID \n"); 
		builder.appendSql("LEFT OUTER JOIN T_SHE_Purchase AS PURCHASE ON ROOM.FID = PURCHASE.FRoomId \n"); 		
		builder.appendSql("LEFT OUTER JOIN  T_SHE_Building AS BUILDING	ON ROOM.FBuildingID = BUILDING.FID \n");
		builder.appendSql("LEFT OUTER JOIN T_FDC_ProductType AS PRODUCTTYPE ON ROOM.FProductTypeID = PRODUCTTYPE.FID \n");
		builder.appendSql("INNER JOIN T_SHE_SellProject AS SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID \n");
		builder.appendSql("INNER JOIN T_ORG_BaseUnit AS ORGUNIT ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID \n");
		builder.appendSql("LEFT OUTER JOIN T_SHE_MoneyDefine AS MoneyDefine ON FDCEntry.FMoneyDefineID = MoneyDefine.FID \n");
		builder.appendSql("where  \n");
		//builder.appendSql("where ROOM.FIsForSHE = 1 ");
		builder.appendSql(baseFilter);
		if(isPreIntoSale) {
			builder.appendSql(" and ROOM.fSellState in ('"+RoomSellStateEnum.PREPURCHASE_VALUE+"','"+RoomSellStateEnum.PURCHASE_VALUE+"','"+RoomSellStateEnum.SIGN_VALUE+"') \n");
		}else{
			builder.appendSql(" and ROOM.fSellState in ('"+RoomSellStateEnum.PURCHASE_VALUE+"','"+RoomSellStateEnum.SIGN_VALUE+"') \n");
		}
		
		builder.appendSql(" and FDC.FPurchaseObjID = PURCHASE.FID \n"); //TODO
		//财务回款 跟房间状态无关 ，跟附属于房产无关 ；跟认购单的状态无关	by jeegan 20090901
		//builder.appendSql(" and PURCHASE.FPurchaseState not in('"+PurchaseStateEnum.CHANGEROOMBLANKOUT_VALUE+"','"+PurchaseStateEnum.QUITROOMBLANKOUT_VALUE+"','"+PurchaseStateEnum.NOPAYBLANKOUT_VALUE+"','"+PurchaseStateEnum.MANUALBLANKOUT_VALUE+"') ");
//		if(isPreIntoSale) {    //
//			builder.appendSql(" and ROOM.fSellState in ('"+RoomSellStateEnum.PREPURCHASE_VALUE+"','"+RoomSellStateEnum.PURCHASE_VALUE+"','"+RoomSellStateEnum.SIGN_VALUE+"')");
//		}else{
//			builder.appendSql(" and ROOM.fSellState in ('"+RoomSellStateEnum.PURCHASE_VALUE+"','"+RoomSellStateEnum.SIGN_VALUE+"')");
//		}
		if(byType.equals("month")) {
			builder.appendSql(" and FDC.FBizDate >= ? \n");
			builder.appendSql(" and FDC.FBizDate < ? \n" );
			builder.addParam(FDCDateHelper.getSqlDate(beginMonthDate));
			builder.addParam(FDCDateHelper.getSqlDate(endMonthDate));
		}else if(byType.equals("year")) {	//按年的sql执行起来很奇怪，居然要查询很久还会执行完，因而换种方式来做 
/*				builder.appendSql(" and CAS.FBizDate >= ? ");
				builder.appendSql(" and CAS.FBizDate < ? " );
				builder.addParam(FDCDateHelper.getSqlDate(beginYearDate));
				builder.addParam(FDCDateHelper.getSqlDate(endYearDate));*/
			DateFormat FORMAT_YEAR = new SimpleDateFormat("yyyy");
			builder.appendSql(" and year(FDC.FBizDate) = "+FORMAT_YEAR.format(beginYearDate)+"  \n");
		}
		
		builder.appendSql(" GROUP BY ORGUNIT.FID, SELLPROJECT.FID,"+showByType+",MoneyDefine.FMoneyType \n");		
	
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			String OID = tableSet.getString("OID");
			String SID = tableSet.getString("SID");
			String PBID = tableSet.getString("PBID");
			BigDecimal resvSum = tableSet.getBigDecimal("resvSum");
			BigDecimal PurchaseAmounts = tableSet.getBigDecimal("PurchaseAmounts");
			String fMoneyType = tableSet.getString("FMoneyType");
			//if(resvSum.compareTo(new BigDecimal(0))==0) continue;
			if(fMoneyType==null || fMoneyType.trim().equals("")) continue;
			if(PurchaseAmounts==null){
				PurchaseAmounts = FDCHelper.ZERO;
			}
			
			String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
			Map row = (Map)rowByIdsMap.get(idStr);			
			if(row==null) continue;
			//(销售回款)		:   预收款(改成了预订金)、定金、楼款、首期、补差款、按揭款、公积金、退款
			String resvMTypeStr = MoneyTypeEnum.PRECONCERTMONEY_VALUE +","+ MoneyTypeEnum.EARNESTMONEY_VALUE +","+ MoneyTypeEnum.HOUSEAMOUNT_VALUE +","
								   + MoneyTypeEnum.FISRTAMOUNT_VALUE +","+ MoneyTypeEnum.COMPENSATEAMOUNT_VALUE +"," + MoneyTypeEnum.LOANAMOUNT_VALUE +","+ MoneyTypeEnum.ACCFUNDAMOUNT_VALUE +","+ MoneyTypeEnum.REFUNDMENT_VALUE;
			//按揭款[回款]   :   按揭款     ///、公积金
			String loanMTypeStr = MoneyTypeEnum.LOANAMOUNT_VALUE ;//+","+ MoneyTypeEnum.ACCFUNDAMOUNT_VALUE;
			//补差款   ：	
			
			///start 2010-06-27
			addDecimalValueToCell(row,byType + "ResvSum",resvSum); //销售回款  含其他付款
			
			/*if(resvMTypeStr.indexOf(fMoneyType)>=0)	 //销售回款	  //用于计算欠收房款	
				addDecimalValueToCell(row,byType + "ResvSum_all",resvSum);*/
			//end
			if(loanMTypeStr.indexOf(fMoneyType)>=0)   //按揭款
				addDecimalValueToCell(row,byType + "LoanSum",resvSum);
			if(fMoneyType.equals(MoneyTypeEnum.COMPENSATEAMOUNT_VALUE))  //已收面积补差
				addDecimalValueToCell(row,byType + "CompensateInSum",resvSum);
			
			if(fMoneyType.equals(MoneyTypeEnum.EARNESTMONEY_VALUE))    	//实收定金
				addDecimalValueToCell(row,byType + "PreconcertMoney",PurchaseAmounts);
			
			if(fMoneyType.equals(MoneyTypeEnum.ACCFUNDAMOUNT_VALUE))    	//公积金
				addDecimalValueToCell(row,byType + "AccFundAmount",resvSum);
			
			
			/*if(byType.equals("all")) {
				if(fMoneyType.equals(MoneyTypeEnum.EARNESTMONEY_VALUE))	//定金
					addDecimalValueToCell(row,"preconcertMoney",resvSum);
			}*/
		}
	}
	
	/**
	 *  用于计算欠收房款
	 * @param baseFilter
	 * @param showByType
	 * @param byType
	 * @param filterMap
	 * @param ctx
	 * @throws BOSException
	 * @throws SQLException
	 */
	private void intTable4(String baseFilter,String showByType,String byType,Map filterMap,Context ctx) throws BOSException, SQLException {
		if(byType==null || "all month year".indexOf(byType)<0) return;

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);	
		boolean isPreIntoSale = filterMap.get("isPreIntoSale")==null?false:(((Integer)filterMap.get("isPreIntoSale")).intValue()>0?true:false);
		
		//应收  过滤付款明细中 日期 < 过滤截至日期的应收款
		builder.appendSql("SELECT ORGUNIT.FID AS OID, SELLPROJECT.FID AS SID," + showByType + " AS PBID \n");
		builder.appendSql(" ,sum( \n");   //定金是否隶属于房款
		builder.appendSql(" case when ( PURCHASE.FisEarnestInHouseAmount=0 and money.FMoneyType='PurchaseAmount') then 0 \n");
		builder.appendSql(" else paylistentry.FappAmount  end) as "+byType+"PriceSum \n");

		builder.appendSql("from  T_SHE_PurchasePayListEntry AS paylistentry \n");
		builder.appendSql("left JOIN  T_SHE_Purchase AS PURCHASE  ON paylistentry.FheadID = PURCHASE.FID \n");
		
		builder.appendSql("left join t_she_moneydefine money on money.FID = paylistentry.FmoneyDefineID  \n");
		builder.appendSql("left join T_she_ROOM as ROOM  ON ROOM.FID = PURCHASE.FROOMID  \n");
		builder.appendSql("LEFT OUTER JOIN  T_SHE_Building AS BUILDING	ON ROOM.FBuildingID = BUILDING.FID  \n");
		builder.appendSql("LEFT OUTER JOIN T_FDC_ProductType AS PRODUCTTYPE ON ROOM.FProductTypeID = PRODUCTTYPE.FID \n");
		builder.appendSql("INNER JOIN T_SHE_SellProject AS SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID  \n");
		builder.appendSql("INNER JOIN T_ORG_BaseUnit AS ORGUNIT ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID \n");
		
		builder.appendSql("where \n");
		builder.appendSql(baseFilter);
		builder.appendSql(" and Room.FHouseProperty != '"+ HousePropertyEnum.ATTACHMENT_VALUE +"'");
		if(isPreIntoSale) {
			builder.appendSql(" and ROOM.fSellState in ('"+RoomSellStateEnum.PREPURCHASE_VALUE+"','"+RoomSellStateEnum.PURCHASE_VALUE+"','"+RoomSellStateEnum.SIGN_VALUE+"') \n");
		}else{
			builder.appendSql(" and ROOM.fSellState in ('"+RoomSellStateEnum.PURCHASE_VALUE+"','"+RoomSellStateEnum.SIGN_VALUE+"') \n");
		}
		
		//应收明细 日期过滤
		Date beginMonthDate = filterMap.get("BeginMonthDate")==null?null:new Date(((Timestamp)filterMap.get("BeginMonthDate")).getTime());
		Date endMonthDate = filterMap.get("EndMonthDate")==null?null:new Date(((Timestamp)filterMap.get("EndMonthDate")).getTime());
		Date beginYearDate = filterMap.get("BeginYearDate")==null?null:new Date(((Timestamp)filterMap.get("BeginYearDate")).getTime());
		Date endYearDate = filterMap.get("EndYearDate")==null?null:new Date(((Timestamp)filterMap.get("EndYearDate")).getTime());
	
		String dataCompareParam = "paylistentry.FappDate ";
		
		
		if(byType.equals("month") || byType.equals("year")) {
			builder.appendSql(" and "+dataCompareParam + "< ? \n");
			if(byType.equals("month")) {
				builder.addParam(FDCDateHelper.getSqlDate(endMonthDate));
			}else if(byType.equals("year")) {
				builder.addParam(FDCDateHelper.getSqlDate(endYearDate));
			}
		}
		
		builder.appendSql("GROUP BY ORGUNIT.FID, SELLPROJECT.FID,"+showByType);
		
		IRowSet tableSet = builder.executeQuery();

		while (tableSet.next()) {
			String OID = tableSet.getString("OID");
			String SID = tableSet.getString("SID");
			String PBID = tableSet.getString("PBID");
			BigDecimal priceSum = tableSet.getBigDecimal(byType+"PriceSum");
			
			String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
			Map row = (Map)rowByIdsMap.get(idStr);
			if(row!=null)  {
				addDecimalValueToCell(row,byType + "Arrearage",priceSum);				
			}
		}
		
		
		//实收
		builder = new FDCSQLBuilder(ctx);
		builder.appendSql("SELECT ORGUNIT.FID AS OID, SELLPROJECT.FID AS SID," + showByType + " AS PBID,MoneyDefine.FMoneyType,\n");
		builder.appendSql(" Sum( \n");
		builder.appendSql(" case when  PURCHASE.FisEarnestInHouseAmount=0 and MoneyDefine.FMoneyType='PurchaseAmount' then 0 else  \n");
		
		builder.appendSql("   case when FDCEntry.FRevAmount is null then 0 else  \n");
		builder.appendSql(" 	case when  MoneyDefine.FMoneyType='PreconcertMoney' and ROOM.fSellState!='PrePurchase' then 0 else \n");
		builder.appendSql("	   		case when ROOM.fSellState='PrePurchase' and MoneyDefine.FMoneyType='PreconcertMoney' and PURCHASE.FPurchaseState='PurchaseAudit' then 0 \n");
		builder.appendSql("	   		else FDCEntry.FRevAmount end \n");
		builder.appendSql("     end \n");
		builder.appendSql("   end \n");
		builder.appendSql("end \n");
		builder.appendSql("	) AS resvSum \n");
		
		builder.appendSql("FROM T_BDC_FDCReceivingBillEntry AS FDCEntry \n");
		builder.appendSql("LEFT OUTER JOIN T_BDC_FDCReceivingBill AS FDC ON FDCEntry.FHeadID = FDC.FID \n"); 
		
		
		builder.appendSql("inner JOIN T_SHE_Room AS ROOM ON FDC.FRoomID = ROOM.FID \n"); 
		builder.appendSql("LEFT OUTER JOIN T_SHE_Purchase AS PURCHASE ON ROOM.FID = PURCHASE.FRoomId \n"); 		
		builder.appendSql("LEFT OUTER JOIN  T_SHE_Building AS BUILDING	ON ROOM.FBuildingID = BUILDING.FID \n");
		builder.appendSql("LEFT OUTER JOIN T_FDC_ProductType AS PRODUCTTYPE ON ROOM.FProductTypeID = PRODUCTTYPE.FID \n");
		builder.appendSql("INNER JOIN T_SHE_SellProject AS SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID \n");
		builder.appendSql("INNER JOIN T_ORG_BaseUnit AS ORGUNIT ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID \n");
		builder.appendSql("LEFT OUTER JOIN T_SHE_MoneyDefine AS MoneyDefine ON FDCEntry.FMoneyDefineID = MoneyDefine.FID \n");
		builder.appendSql("where  \n");
		builder.appendSql(baseFilter);
		if(isPreIntoSale) {
			builder.appendSql(" and ROOM.fSellState in ('"+RoomSellStateEnum.PREPURCHASE_VALUE+"','"+RoomSellStateEnum.PURCHASE_VALUE+"','"+RoomSellStateEnum.SIGN_VALUE+"') \n");
		}else{
			builder.appendSql(" and ROOM.fSellState in ('"+RoomSellStateEnum.PURCHASE_VALUE+"','"+RoomSellStateEnum.SIGN_VALUE+"') \n");
		}
		
		builder.appendSql(" and FDC.FPurchaseObjID = PURCHASE.FID \n"); //TODO
		if(byType.equals("month")) {
			builder.appendSql(" and FDC.FBizDate < ? \n" );
			builder.addParam(FDCDateHelper.getSqlDate(endMonthDate));
		}else if(byType.equals("year")) {	//按年的sql执行起来很奇怪，居然要查询很久还会执行完，因而换种方式来做 
			DateFormat FORMAT_YEAR = new SimpleDateFormat("yyyy");
			builder.appendSql(" and year(FDC.FBizDate) <= "+FORMAT_YEAR.format(beginYearDate)+"  \n");
		}
		
		builder.appendSql(" GROUP BY ORGUNIT.FID, SELLPROJECT.FID,"+showByType+",MoneyDefine.FMoneyType \n");		
	
		 tableSet = builder.executeQuery();
		while (tableSet.next()) {
			String OID = tableSet.getString("OID");
			String SID = tableSet.getString("SID");
			String PBID = tableSet.getString("PBID");
			BigDecimal resvSum = tableSet.getBigDecimal("resvSum");
			String fMoneyType = tableSet.getString("FMoneyType");
			if(resvSum.compareTo(new BigDecimal(0))==0) continue;
			if(fMoneyType==null || fMoneyType.trim().equals("")) continue;
			
			String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
			Map row = (Map)rowByIdsMap.get(idStr);			
			if(row==null) continue;
			//(销售回款)		:   预收款(改成了预订金)、定金、楼款、首期、按揭款、公积金
			String resvMTypeStr = MoneyTypeEnum.PRECONCERTMONEY_VALUE +","+ MoneyTypeEnum.EARNESTMONEY_VALUE +","+ MoneyTypeEnum.HOUSEAMOUNT_VALUE +","
								   + MoneyTypeEnum.FISRTAMOUNT_VALUE +"," + MoneyTypeEnum.LOANAMOUNT_VALUE +","+ MoneyTypeEnum.ACCFUNDAMOUNT_VALUE ;
			
			if(resvMTypeStr.indexOf(fMoneyType)>=0)	 //
				addDecimalValueToCell(row,byType + "Arrearage",resvSum.negate());
			
		}
		
		
	}
	
	
	private void addDecimalValueToCell(Map row,String cellName,BigDecimal addValue) {
		if(addValue==null) return; 
		BigDecimal cellValue = new BigDecimal(0); 
		Object cellObject = row.get(cellName);
		if(cellObject instanceof Integer)
			cellValue = new BigDecimal(((Integer)cellObject).intValue());
		else if(cellObject instanceof BigDecimal)
			cellValue = (BigDecimal)cellObject;

		row.put(cellName,cellValue.add(addValue));
	}
	
	
	
	
	//特殊业务  退房和变更     只对按月和按年的处理 （ 已售套数、已售面积、应收合同总价  、 应收（退 ）面积差）
	//退房 --特殊处理  （ 已售套数、已售面积、应收合同总价  、 应收（退 ）面积差）
	//分两步操作 1：退房单的认购单对应的认购或销售时间在指定范围内的做 + 操作
	//			2: 退房日期在指定范围内的做 - 操作
	private void dealForQuitRoom(String baseFilter,String showByType,String byType,Map filterMap,Context ctx) throws BOSException, SQLException {	
		//Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
		/*Date beginMonthDate = filterMap.get("BeginMonthDate")==null?null:new Date(((Timestamp)filterMap.get("BeginMonthDate")).getTime());
		Date endMonthDate = filterMap.get("EndMonthDate")==null?null:new Date(((Timestamp)filterMap.get("EndMonthDate")).getTime());
		Date beginYearDate = filterMap.get("BeginYearDate")==null?null:new Date(((Timestamp)filterMap.get("BeginYearDate")).getTime());
		Date endYearDate = filterMap.get("EndYearDate")==null?null:new Date(((Timestamp)filterMap.get("EndYearDate")).getTime());*/
		boolean isPreIntoSale = filterMap.get("isPreIntoSale")==null?false:(((Integer)filterMap.get("isPreIntoSale")).intValue()>0?true:false);	
	
		
		String dataCompareParam = "PURCHASE.FToSaleDate";
		if(!isPreIntoSale) dataCompareParam = "PURCHASE.FToPurchaseDate";
		
		String[] columnNames = {byType+"RoomCount",byType+"AreaSum",byType+"PriceSum",byType+"CompensateSum"};
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		FDCSQLBuilder builder2 = new FDCSQLBuilder(ctx);
		
		builder.appendSql("SELECT aa.OID, aa.SID, aa.PBID,\n");
		builder.appendSql("sum(aa."+columnNames[0]+") "+columnNames[0]+", \n");
		builder.appendSql("sum(aa."+columnNames[1]+") "+columnNames[1]+",\n");
		builder.appendSql("sum(aa."+columnNames[2]+") "+columnNames[2]+",\n");
		builder.appendSql("sum(aa."+columnNames[3]+") "+columnNames[3]+" from (  \n");

		
		builder.appendSql("SELECT ORGUNIT.FID AS OID, SELLPROJECT.FID AS SID," + showByType + " AS PBID,\n");
		builder.appendSql("Count(ROOM.FID) AS "+columnNames[0]+", \n");
		builder.appendSql("Sum(case when ROOM.FSaleArea is null then (case when FSaleArea is null then 0 else FSaleArea end) else ROOM.FSaleArea end) AS "+columnNames[1]+",\n"); 
		//builder.appendSql(" 0 AS "+columnNames[2]+" \n");
		builder.appendSql("Sum(case when PURCHASE.FContractTotalAmount is null then 0 else PURCHASE.FContractTotalAmount end) AS "+columnNames[2]+" \n");
		builder.appendSql(",Sum(case when ROOM.FAreaCompensateAmount is null then 0 else ROOM.FAreaCompensateAmount end) AS "+columnNames[3]+" \n");
		builder.appendSql("FROM T_SHE_Room AS ROOM \n");
		builder.appendSql("INNER JOIN T_SHE_QuitRoom AS QUITROOM ON ROOM.FID = QUITROOM.FRoomID \n");
		builder.appendSql("left outer JOIN T_SHE_Purchase AS PURCHASE ON QUITROOM.FPurchaseID = PURCHASE.FID \n");
		builder.appendSql("LEFT OUTER JOIN  T_SHE_Building AS BUILDING	ON ROOM.FBuildingID = BUILDING.FID \n"); 
		builder.appendSql("LEFT OUTER JOIN T_FDC_ProductType AS PRODUCTTYPE ON ROOM.FProductTypeID = PRODUCTTYPE.FID \n"); 
		builder.appendSql("INNER JOIN T_SHE_SellProject AS SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID \n"); 
		builder.appendSql("INNER JOIN T_ORG_BaseUnit AS ORGUNIT ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID \n");
		builder.appendSql("where \n");
		builder.appendSql(baseFilter);
		builder.appendSql(" and ROOM.FHouseProperty != '"+ HousePropertyEnum.ATTACHMENT_VALUE +"'");
		
		builder.appendSql(" and QUITROOM.fstate = '"+FDCBillStateEnum.AUDITTED_VALUE+"' \n");
		if(!isPreIntoSale)
			builder.appendSql(" and PURCHASE.FToPurchaseDate is not null \n");
		
		builder2.appendSql(builder.getSql());
		
		getSimpleSqlWhere(builder,byType,showByType,filterMap,"QUITROOM");
		
		
		//附属房产
		boolean isIncludeAttach = filterMap.get("isIncludeAttach")==null?false:(((Integer)filterMap.get("isIncludeAttach")).intValue()>0?true:false);
		
    	if(isIncludeAttach)
    	{
    		builder.appendSql("union \n");
    		
    		getAttachSql(builder,columnNames,showByType,"QUITROOM");
    		
    		builder.appendSql("where \n");
    		builder.appendSql(baseFilter);
    		
    		builder.appendSql(" and QUITROOM.fstate = '"+FDCBillStateEnum.AUDITTED_VALUE+"' \n");
    		if(!isPreIntoSale)
    			builder.appendSql(" and PURCHASE.FToPurchaseDate is not null \n");
    	/*	if(isPreIntoSale) {
    			builder.appendSql(" and ROOM.fSellState in ('"+RoomSellStateEnum.PREPURCHASE_VALUE+"','"+RoomSellStateEnum.PURCHASE_VALUE+"','"+RoomSellStateEnum.SIGN_VALUE+"') \n");
    		}else{
    			builder.appendSql(" and ROOM.fSellState in ('"+RoomSellStateEnum.PURCHASE_VALUE+"','"+RoomSellStateEnum.SIGN_VALUE+"') \n");
    		}*/
    		
    		getSimpleSqlWhere(builder,byType,showByType,filterMap,"QUITROOM");
    	
    	}
    	
    	builder.appendSql(" )aa \n");
    	builder.appendSql("GROUP BY aa.OID, aa.SID, aa.PBID \n");
		
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			String OID = tableSet.getString("OID");
			String SID = tableSet.getString("SID");
			String PBID = tableSet.getString("PBID");
			int roomcount = tableSet.getInt(columnNames[0]);
			BigDecimal areaSum = tableSet.getBigDecimal(columnNames[1]);
			BigDecimal priceSum = tableSet.getBigDecimal(columnNames[2]);
			BigDecimal areaCompensateAmount = tableSet.getBigDecimal(columnNames[3]);
			
			String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
			Map row = (Map)rowByIdsMap.get(idStr);
			if(row!=null)  {
				addDecimalValueToCell(row,columnNames[0],new BigDecimal(roomcount));
				addDecimalValueToCell(row,columnNames[1],areaSum);
				addDecimalValueToCell(row,columnNames[2],priceSum);
				addDecimalValueToCell(row,columnNames[3],areaCompensateAmount);
			}
		}
		
		//
		 //FDCSQLBuilder priceSumbuilder = getPriceSumSql(baseFilter,showByType,byType,filterMap,ctx,"QUITROOM");
		 //IRowSet priceSumtableSet  = priceSumbuilder.executeQuery();
		
		/* while (priceSumtableSet.next()) {
				String OID = priceSumtableSet.getString("OID");
				String SID = priceSumtableSet.getString("SID");
				String PBID = priceSumtableSet.getString("PBID");
				BigDecimal priceSum = priceSumtableSet.getBigDecimal(columnNames[2]);
				
				String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
				Map row = (Map)rowByIdsMap.get(idStr);
				if(row!=null)  {
					addDecimalValueToCell(row,columnNames[2],priceSum);
				}
			}*/
		 
		//第二步
		
		getSimpleSqlWhere(builder2,byType,showByType,filterMap,"QUITROOM",true);
		if(isIncludeAttach)
    	{
			builder2.appendSql("union \n");
    		getAttachSql(builder2,columnNames,showByType,"QUITROOM");
    		builder2.appendSql("where \n");
    		builder2.appendSql(baseFilter);
    		builder2.appendSql(" and QUITROOM.fstate = '"+FDCBillStateEnum.AUDITTED_VALUE+"' \n");
    		if(!isPreIntoSale)
    			builder2.appendSql(" and PURCHASE.FToPurchaseDate is not null \n");
    		
    		getSimpleSqlWhere(builder2,byType,showByType,filterMap,"QUITROOM",true);
    		
    	}
		
		builder2.appendSql(" )aa \n");
		builder2.appendSql("GROUP BY aa.OID, aa.SID, aa.PBID \n");
	
		
		tableSet = builder2.executeQuery();
		while (tableSet.next()) {
			String OID = tableSet.getString("OID");
			String SID = tableSet.getString("SID");
			String PBID = tableSet.getString("PBID");
			int roomcount = tableSet.getInt(columnNames[0]);
			BigDecimal areaSum = tableSet.getBigDecimal(columnNames[1]);
			BigDecimal priceSum = tableSet.getBigDecimal(columnNames[2]);
			BigDecimal areaCompensateAmount = tableSet.getBigDecimal(columnNames[3]);
			
			String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
			Map row = (Map)rowByIdsMap.get(idStr);
			if(row!=null)  {
				addDecimalValueToCell(row,columnNames[0],new BigDecimal(-roomcount));
				addDecimalValueToCell(row,columnNames[1],areaSum.negate());
				addDecimalValueToCell(row,columnNames[2],priceSum.negate());
				addDecimalValueToCell(row,columnNames[3],areaCompensateAmount.negate());
			}
		}
		
		
		  /*priceSumbuilder = getPriceSumSql(baseFilter,showByType,byType,filterMap,ctx,"QUITROOM",true);
		   priceSumtableSet  = priceSumbuilder.executeQuery();
		   while (priceSumtableSet.next()) {
				String OID = priceSumtableSet.getString("OID");
				String SID = priceSumtableSet.getString("SID");
				String PBID = priceSumtableSet.getString("PBID");
				BigDecimal priceSum = priceSumtableSet.getBigDecimal(columnNames[2]);
				
				String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
				Map row = (Map)rowByIdsMap.get(idStr);
				if(row!=null)  {
					addDecimalValueToCell(row,columnNames[2],priceSum.negate());
				}
			}*/
		
		
	}	
	
	/**
	 * 
	 * @param columnNames
	 * @param showByType
	 * @param billType
	 * @return
	 */
	private void getAttachSql(FDCSQLBuilder builder,String[] columnNames,String showByType,String billType){
		builder.appendSql("SELECT ORGUNIT.FID AS OID, SELLPROJECT.FID AS SID," + showByType + " AS PBID, \n");
		builder.appendSql("Count(ROOM.FID) AS "+columnNames[0]+", ");
		builder.appendSql("Sum(case when ROOM.FSaleArea is null then (case when FSaleArea is null then 0 else FSaleArea end) else ROOM.FSaleArea end) AS "+columnNames[1]+","); 
		builder.appendSql("0 AS "+columnNames[2]+" ");
		builder.appendSql(",Sum(case when ROOM.FAreaCompensateAmount is null then 0 else ROOM.FAreaCompensateAmount end) AS "+columnNames[3]+" ");

		builder.appendSql("from T_SHE_PurchaseRoomAttachEntry attach \n");
    	builder.appendSql("left join T_SHE_RoomAttachmentEntry roomAttach on attach.FAttachmentEntryID = roomAttach.fid \n");
    	builder.appendSql("left join t_she_purchase purchase on attach.fheadid = purchase.fid \n");
    	builder.appendSql("left join t_she_room ROOM on roomAttach.froomid = ROOM.fid \n");
    	if("QUITROOM".equalsIgnoreCase(billType)){
    		builder.appendSql("INNER JOIN T_SHE_QuitRoom AS QUITROOM ON purchase.FID = QUITROOM.FPurchaseID \n");  //退房
    	}else if ("CHANGEROOM".equalsIgnoreCase(billType)){
    		builder.appendSql("INNER JOIN T_SHE_ChangeRoom AS CHANGEROOM ON purchase.FID = CHANGEROOM.FOldPurchaseID \n");  //换房
    	}
    	
    	builder.appendSql("LEFT OUTER JOIN  T_SHE_Building AS BUILDING	ON ROOM.FBuildingID = BUILDING.FID \n"); 
    	builder.appendSql("left join t_fdc_producttype PRODUCTTYPE on PRODUCTTYPE.FID = ROOM.FProductTypeID \n");
    	builder.appendSql("left join T_SHE_SellProject AS SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID \n"); 
		builder.appendSql("left join T_ORG_BaseUnit AS ORGUNIT ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID \n");
    	
		
	}
	private void getSimpleSqlWhere(FDCSQLBuilder builder,String byType,String  showByType,Map filterMap){
		getSimpleSqlWhere(builder,byType,showByType,filterMap,null);
	}
	
	private void getSimpleSqlWhere(FDCSQLBuilder builder,String byType,String  showByType,Map filterMap,String billName){
		getSimpleSqlWhere(builder,byType,showByType,filterMap,billName,false);
	}
	private void getSimpleSqlWhere(FDCSQLBuilder builder,String byType,String  showByType,Map filterMap,String billName,boolean operationInTime){
		Date beginMonthDate = filterMap.get("BeginMonthDate")==null?null:new Date(((Timestamp)filterMap.get("BeginMonthDate")).getTime());
		Date endMonthDate = filterMap.get("EndMonthDate")==null?null:new Date(((Timestamp)filterMap.get("EndMonthDate")).getTime());
		Date beginYearDate = filterMap.get("BeginYearDate")==null?null:new Date(((Timestamp)filterMap.get("BeginYearDate")).getTime());
		Date endYearDate = filterMap.get("EndYearDate")==null?null:new Date(((Timestamp)filterMap.get("EndYearDate")).getTime());
		boolean isPreIntoSale = filterMap.get("isPreIntoSale")==null?false:(((Integer)filterMap.get("isPreIntoSale")).intValue()>0?true:false);	
	
		String dataCompareParam = "PURCHASE.FToSaleDate";
		if(!isPreIntoSale) dataCompareParam = "PURCHASE.FToPurchaseDate";
		
		
		if(byType.equals("month") || byType.equals("year")) {
			if(operationInTime){
				if(this.isAuditDate)
				{
					builder.appendSql(" and "+billName+".FAuditTime >= ? \n");
					builder.appendSql(" and "+billName+".FAuditTime < ? \n");
				}
				else
				{
					if(!"CHANGEROOM".equals(billName)){
						builder.appendSql(" and "+billName+".FQuitDate >= ? \n");
						builder.appendSql(" and "+billName+".FQuitDate < ? \n");
					}else{
						builder.appendSql(" and "+billName+".FChangeDate >= ? \n");
						builder.appendSql(" and "+billName+".FChangeDate < ? \n");
					}
				}
			}else{
				builder.appendSql(" and "+dataCompareParam + ">= ? \n");
				builder.appendSql(" and "+dataCompareParam + "< ? \n");
			}
			if(byType.equals("month")) {
				builder.addParam(FDCDateHelper.getSqlDate(beginMonthDate));
				builder.addParam(FDCDateHelper.getSqlDate(endMonthDate));
			}else if(byType.equals("year")) {
				builder.addParam(FDCDateHelper.getSqlDate(beginYearDate));
				builder.addParam(FDCDateHelper.getSqlDate(endYearDate));
			}
		}		
		//builder.appendSql(" AND ROOM.FIsForSHE=1 ");
		builder.appendSql(" GROUP BY ORGUNIT.FID, SELLPROJECT.FID,"+showByType+" \n");
		
	}
	
	
	/**
	 * Unused
	 * @param baseFilter
	 * @param showByType
	 * @param byType
	 * @param filterMap
	 * @throws BOSException
	 * @throws SQLException
	 */
	//调整--和退房一样处理  by Pope
	private void dealForAdjustRoom(String baseFilter,String showByType,String byType,Map filterMap,Context ctx) throws BOSException, SQLException {	
		
	}	
	
	//换房单——和退房一样处理
	private void dealForChangeRoom(String baseFilter,String showByType,String byType,Map filterMap,Context ctx) throws BOSException, SQLException {	
		//Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
		Date beginMonthDate = filterMap.get("BeginMonthDate")==null?null:new Date(((Timestamp)filterMap.get("BeginMonthDate")).getTime());
		Date endMonthDate = filterMap.get("EndMonthDate")==null?null:new Date(((Timestamp)filterMap.get("EndMonthDate")).getTime());
		Date beginYearDate = filterMap.get("BeginYearDate")==null?null:new Date(((Timestamp)filterMap.get("BeginYearDate")).getTime());
		Date endYearDate = filterMap.get("EndYearDate")==null?null:new Date(((Timestamp)filterMap.get("EndYearDate")).getTime());
		boolean isPreIntoSale = filterMap.get("isPreIntoSale")==null?false:(((Integer)filterMap.get("isPreIntoSale")).intValue()>0?true:false);
		
		String dataCompareParam = "PURCHASE.FToSaleDate";
		if(!isPreIntoSale) dataCompareParam = "PURCHASE.FToPurchaseDate";
		
		String[] columnNames = {byType+"RoomCount",byType+"AreaSum",byType+"PriceSum",byType+"CompensateSum"};
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		FDCSQLBuilder builder2 = new FDCSQLBuilder(ctx);
		
		builder.appendSql("SELECT aa.OID, aa.SID, aa.PBID,\n");
		builder.appendSql("sum(aa."+columnNames[0]+") "+columnNames[0]+", \n");
		builder.appendSql("sum(aa."+columnNames[1]+") "+columnNames[1]+",\n");
		builder.appendSql("sum(aa."+columnNames[2]+") "+columnNames[2]+",\n");
		builder.appendSql("sum(aa."+columnNames[3]+") "+columnNames[3]+" from (  \n");
		
		
		builder.appendSql("SELECT ORGUNIT.FID AS OID, SELLPROJECT.FID AS SID," + showByType + " AS PBID,\n");
		builder.appendSql("Count(ROOM.FID) AS "+columnNames[0]+", \n");
		builder.appendSql("Sum(case when ROOM.FSaleArea is null then (case when FSaleArea is null then 0 else FSaleArea end) else ROOM.FSaleArea end) AS "+columnNames[1]+",\n"); 
		//builder.appendSql("0 AS "+columnNames[2]+" \n");
		builder.appendSql("Sum(case when PURCHASE.FContractTotalAmount is null then 0 else PURCHASE.FContractTotalAmount end) AS "+columnNames[2]+" \n");
		builder.appendSql(",Sum(case when ROOM.FAreaCompensateAmount is null then 0 else ROOM.FAreaCompensateAmount end) AS "+columnNames[3]+" \n");
		builder.appendSql("FROM T_SHE_Room AS ROOM \n");
		builder.appendSql("INNER JOIN T_SHE_ChangeRoom AS CHANGEROOM ON ROOM.FID = CHANGEROOM.FOLDRoomID \n");
		builder.appendSql("left outer JOIN T_SHE_Purchase AS PURCHASE ON CHANGEROOM.FOldPurchaseID = PURCHASE.FID \n");
		builder.appendSql("LEFT OUTER JOIN  T_SHE_Building AS BUILDING	ON ROOM.FBuildingID = BUILDING.FID \n"); 
		builder.appendSql("LEFT OUTER JOIN T_FDC_ProductType AS PRODUCTTYPE ON ROOM.FProductTypeID = PRODUCTTYPE.FID \n"); 
		builder.appendSql("INNER JOIN T_SHE_SellProject AS SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID \n"); 
		builder.appendSql("INNER JOIN T_ORG_BaseUnit AS ORGUNIT ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID \n");
		builder.appendSql("where \n");
		builder.appendSql(baseFilter);
		builder.appendSql(" and ROOM.FHouseProperty != '"+ HousePropertyEnum.ATTACHMENT_VALUE +"'");
		
		builder.appendSql(" and CHANGEROOM.fstate = '"+FDCBillStateEnum.AUDITTED_VALUE+"' \n");
		if(!isPreIntoSale)
			builder.appendSql(" and PURCHASE.FToPurchaseDate is not null \n");
		
		builder2.appendSql(builder.getSql());
		
		
		getSimpleSqlWhere(builder,byType,showByType,filterMap);
		
		//附属房产
		boolean isIncludeAttach = filterMap.get("isIncludeAttach")==null?false:(((Integer)filterMap.get("isIncludeAttach")).intValue()>0?true:false);
		
    	if(isIncludeAttach)
    	{
    		builder.appendSql("union \n");

    		getAttachSql(builder,columnNames,showByType,"CHANGEROOM");
    		
    		builder.appendSql("where \n");
    		builder.appendSql(baseFilter);
    		builder.appendSql(" and CHANGEROOM.fstate = '"+FDCBillStateEnum.AUDITTED_VALUE+"' \n");
    		if(!isPreIntoSale)
    			builder.appendSql(" and PURCHASE.FToPurchaseDate is not null \n");
    		
    		getSimpleSqlWhere(builder,byType,showByType,filterMap,"CHANGEROOM");
    		
    	}
		
		
		builder.appendSql(" )aa \n");
		builder.appendSql("GROUP BY aa.OID, aa.SID, aa.PBID \n");
		
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			String OID = tableSet.getString("OID");
			String SID = tableSet.getString("SID");
			String PBID = tableSet.getString("PBID");
			int roomcount = tableSet.getInt(columnNames[0]);
			BigDecimal areaSum = tableSet.getBigDecimal(columnNames[1]);
			BigDecimal priceSum = tableSet.getBigDecimal(columnNames[2]);
			BigDecimal areaCompensateAmount = tableSet.getBigDecimal(columnNames[3]);
			
			String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
			Map row = (Map)rowByIdsMap.get(idStr);
			if(row!=null)  {
				addDecimalValueToCell(row,columnNames[0],new BigDecimal(roomcount));
				addDecimalValueToCell(row,columnNames[1],areaSum);
				addDecimalValueToCell(row,columnNames[2],priceSum);
				addDecimalValueToCell(row,columnNames[3],areaCompensateAmount);
			}
		}
		
		
		/* FDCSQLBuilder priceSumbuilder = getPriceSumSql(baseFilter,showByType,byType,filterMap,ctx,"CHANGEROOM");
		 IRowSet priceSumtableSet  = priceSumbuilder.executeQuery();
		 while (priceSumtableSet.next()) {
				String OID = priceSumtableSet.getString("OID");
				String SID = priceSumtableSet.getString("SID");
				String PBID = priceSumtableSet.getString("PBID");
				BigDecimal priceSum = priceSumtableSet.getBigDecimal(columnNames[2]);
				
				String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
				Map row = (Map)rowByIdsMap.get(idStr);
				if(row!=null)  {
					addDecimalValueToCell(row,columnNames[2],priceSum);
				}
			}*/
		 
		 
		//第二步
		
		getSimpleSqlWhere(builder2,byType,showByType,filterMap,"CHANGEROOM",true);
		
		if(isIncludeAttach)
    	{
			builder2.appendSql("union \n");
    		getAttachSql(builder2,columnNames,showByType,"CHANGEROOM");
    		builder2.appendSql("where \n");
    		builder2.appendSql(baseFilter);
    		builder2.appendSql(" and CHANGEROOM.fstate = '"+FDCBillStateEnum.AUDITTED_VALUE+"' \n");
    		if(!isPreIntoSale)
    			builder2.appendSql(" and PURCHASE.FToPurchaseDate is not null \n");
    		
    		getSimpleSqlWhere(builder2,byType,showByType,filterMap,"CHANGEROOM",true);
    		
    	}
		
		builder2.appendSql(" )aa \n");
		builder2.appendSql("GROUP BY aa.OID, aa.SID, aa.PBID \n");
		
		
		tableSet = builder2.executeQuery();
		while (tableSet.next()) {
			String OID = tableSet.getString("OID");
			String SID = tableSet.getString("SID");
			String PBID = tableSet.getString("PBID");
			int roomcount = tableSet.getInt(columnNames[0]);
			BigDecimal areaSum = tableSet.getBigDecimal(columnNames[1]);
			BigDecimal priceSum = tableSet.getBigDecimal(columnNames[2]);
			BigDecimal areaCompensateAmount = tableSet.getBigDecimal(columnNames[3]);
			
			String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
			Map row = (Map)rowByIdsMap.get(idStr);
			if(row!=null)  {
				addDecimalValueToCell(row,columnNames[0],new BigDecimal(-roomcount));
				addDecimalValueToCell(row,columnNames[1],areaSum.negate());
				addDecimalValueToCell(row,columnNames[2],priceSum.negate());
				addDecimalValueToCell(row,columnNames[3],areaCompensateAmount.negate());
			}
		}
		
		
		 /* priceSumbuilder = getPriceSumSql(baseFilter,showByType,byType,filterMap,ctx,"CHANGEROOM");
		  priceSumtableSet  = priceSumbuilder.executeQuery();
		 while (priceSumtableSet.next()) {
				String OID = priceSumtableSet.getString("OID");
				String SID = priceSumtableSet.getString("SID");
				String PBID = priceSumtableSet.getString("PBID");
				BigDecimal priceSum = priceSumtableSet.getBigDecimal(columnNames[2]);
				
				String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
				Map row = (Map)rowByIdsMap.get(idStr);
				if(row!=null)  {
					addDecimalValueToCell(row,columnNames[2],priceSum.negate());
				}
			}*/
	}
	
	//变更单--特殊处理   （ 应收合同总价 ，好像只对这个有影响 ）
	private void dealForPurchaseChange(String baseFilter,String showByType,String byType,Map filterMap,Context ctx) throws BOSException, SQLException {	
		//Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
		Date beginMonthDate = filterMap.get("BeginMonthDate")==null?null:new Date(((Timestamp)filterMap.get("BeginMonthDate")).getTime());
		Date endMonthDate = filterMap.get("EndMonthDate")==null?null:new Date(((Timestamp)filterMap.get("EndMonthDate")).getTime());
		Date beginYearDate = filterMap.get("BeginYearDate")==null?null:new Date(((Timestamp)filterMap.get("BeginYearDate")).getTime());
		Date endYearDate = filterMap.get("EndYearDate")==null?null:new Date(((Timestamp)filterMap.get("EndYearDate")).getTime());
		boolean isPreIntoSale = filterMap.get("isPreIntoSale")==null?false:(((Integer)filterMap.get("isPreIntoSale")).intValue()>0?true:false);	
	
		String dataCompareParam = "PURCHASE.FToSaleDate";
		if(!isPreIntoSale) dataCompareParam = "PURCHASE.FToPurchaseDate";
		
		String columnNames = byType+"PriceSum";
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		FDCSQLBuilder builder2 = new FDCSQLBuilder(ctx);
		builder.appendSql("SELECT ORGUNIT.FID AS OID, SELLPROJECT.FID AS SID," + showByType + " AS PBID,\n");
		builder.appendSql("Sum(PurchaseChange.FNewContractAmount - PurchaseChange.FOldContractAmount) AS "+columnNames+" \n");
		builder.appendSql("FROM T_SHE_Room AS ROOM \n");		
		builder.appendSql("INNER JOIN T_SHE_Purchase AS PURCHASE ON ROOM.FID = PURCHASE.FRoomID \n");
		builder.appendSql("INNER JOIN T_SHE_PurchaseChange AS PurchaseChange ON PURCHASE.FID = PurchaseChange.FPurchaseID \n");
		builder.appendSql("LEFT OUTER JOIN  T_SHE_Building AS BUILDING	ON ROOM.FBuildingID = BUILDING.FID \n"); 
		builder.appendSql("LEFT OUTER JOIN T_FDC_ProductType AS PRODUCTTYPE ON ROOM.FProductTypeID = PRODUCTTYPE.FID \n"); 
		builder.appendSql("INNER JOIN T_SHE_SellProject AS SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID \n"); 
		builder.appendSql("INNER JOIN T_ORG_BaseUnit AS ORGUNIT ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID \n");
		builder.appendSql("where \n");
		builder.appendSql(baseFilter);
		builder.appendSql(" and PurchaseChange.fstate = '"+FDCBillStateEnum.AUDITTED_VALUE+"' \n");
		if(!isPreIntoSale)
			builder.appendSql(" and PURCHASE.FToPurchaseDate is not null \n");
		
		builder2.appendSql(builder.getSql());
		if(byType.equals("month") || byType.equals("year")) {
			builder.appendSql(" and "+dataCompareParam + ">= ? \n");
			builder.appendSql(" and "+dataCompareParam + "< ? \n" );
			if(byType.equals("month")) {
				builder.addParam(FDCDateHelper.getSqlDate(beginMonthDate));
				builder.addParam(FDCDateHelper.getSqlDate(endMonthDate));
			}else if(byType.equals("year")) {
				builder.addParam(FDCDateHelper.getSqlDate(beginYearDate));
				builder.addParam(FDCDateHelper.getSqlDate(endYearDate));
			}
		}
		//builder.appendSql(" AND ROOM.FIsForSHE=1 ");
		builder.appendSql(" GROUP BY ORGUNIT.FID, SELLPROJECT.FID,"+showByType);		
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			String OID = tableSet.getString("OID");
			String SID = tableSet.getString("SID");
			String PBID = tableSet.getString("PBID");
			BigDecimal priceSum = tableSet.getBigDecimal(columnNames);
			
			String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
			Map row = (Map)rowByIdsMap.get(idStr);
			if(row!=null)  {
				addDecimalValueToCell(row,columnNames,priceSum.negate());
			}
		}
		
		//第二步
		if(byType.equals("month") || byType.equals("year")) {
			
			if(this.isAuditDate)
			{
				builder2.appendSql(" and PurchaseChange.FAuditTime >= ? \n");
				builder2.appendSql(" and PurchaseChange.FAuditTime < ? \n" );
			}
			else
			{
			builder2.appendSql(" and PurchaseChange.FChangeDate >= ? \n");
			builder2.appendSql(" and PurchaseChange.FChangeDate < ? \n" );
			}
			if(byType.equals("month")) {
				builder2.addParam(FDCDateHelper.getSqlDate(beginMonthDate));
				builder2.addParam(FDCDateHelper.getSqlDate(endMonthDate));
			}else if(byType.equals("year")) {
				builder2.addParam(FDCDateHelper.getSqlDate(beginYearDate));
				builder2.addParam(FDCDateHelper.getSqlDate(endYearDate));
			}
		}
		builder2.appendSql(" GROUP BY ORGUNIT.FID, SELLPROJECT.FID,"+showByType+" \n");
		tableSet = builder2.executeQuery();
		while (tableSet.next()) {
			String OID = tableSet.getString("OID");
			String SID = tableSet.getString("SID");
			String PBID = tableSet.getString("PBID");
			BigDecimal priceSum = tableSet.getBigDecimal(columnNames);
			
			String idStr = (OID==null?"":OID) + (SID==null?"":SID) + (PBID==null?"":PBID);
			Map row = (Map)rowByIdsMap.get(idStr);
			if(row!=null)  {
				addDecimalValueToCell(row,columnNames,priceSum);
			}
		}
		
	}
	private FDCSQLBuilder getPriceSumSql(String baseFilter,String showByType,String byType,Map filterMap,Context ctx ,String type ){
		return getPriceSumSql(baseFilter,showByType,byType,filterMap,ctx,type,false);
	}
	
	private FDCSQLBuilder getPriceSumSql(String baseFilter,String showByType,String byType,Map filterMap,Context ctx ,String type,boolean operationInTime ){
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("SELECT ORGUNIT.FID AS OID, SELLPROJECT.FID AS SID," + showByType + " AS PBID \n");
		builder.appendSql(" ,sum( \n");   //定金是否隶属于房款
		builder.appendSql(" case when ( PURCHASE.FisEarnestInHouseAmount=0 and money.FMoneyType='PurchaseAmount') then 0 \n");
		builder.appendSql(" else paylistentry.FappAmount  end) as "+byType+"PriceSum \n");

		builder.appendSql("from  T_SHE_PurchasePayListEntry AS paylistentry \n");
		builder.appendSql("left JOIN  T_SHE_Purchase AS PURCHASE  ON paylistentry.FheadID = PURCHASE.FID \n");
		
		builder.appendSql("left join t_she_moneydefine money on money.FID = paylistentry.FmoneyDefineID  \n");
		if("ROOM".equalsIgnoreCase(type)){
			builder.appendSql("left join T_she_ROOM as ROOM  ON ROOM.FLastPurchaseID = PURCHASE.FID  \n");
		}else{
			builder.appendSql("left join T_she_ROOM as ROOM  ON ROOM.FID = PURCHASE.FROOMID  \n");
		}
		builder.appendSql("LEFT OUTER JOIN  T_SHE_Building AS BUILDING	ON ROOM.FBuildingID = BUILDING.FID  \n");
		builder.appendSql("LEFT OUTER JOIN T_FDC_ProductType AS PRODUCTTYPE ON ROOM.FProductTypeID = PRODUCTTYPE.FID \n");
		builder.appendSql("INNER JOIN T_SHE_SellProject AS SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID  \n");
		builder.appendSql("INNER JOIN T_ORG_BaseUnit AS ORGUNIT ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID \n");
		builder.appendSql("where  PURCHASE.FID in (\n");
		
		//builder.appendSql(getPurchaseIds(baseFilter,type).toString());
		getPurchaseIds(builder,baseFilter,type,showByType,byType,filterMap,operationInTime);
		
		builder.appendSql(")  \n");
	//	builder.appendSql("and ");
		//getSimpleSqlWhere(builder, byType, showByType, filterMap);
		
		
		//应收明细 日期过滤
		Date beginMonthDate = filterMap.get("BeginMonthDate")==null?null:new Date(((Timestamp)filterMap.get("BeginMonthDate")).getTime());
		Date endMonthDate = filterMap.get("EndMonthDate")==null?null:new Date(((Timestamp)filterMap.get("EndMonthDate")).getTime());
		Date beginYearDate = filterMap.get("BeginYearDate")==null?null:new Date(((Timestamp)filterMap.get("BeginYearDate")).getTime());
		Date endYearDate = filterMap.get("EndYearDate")==null?null:new Date(((Timestamp)filterMap.get("EndYearDate")).getTime());
	
		String dataCompareParam = "paylistentry.FappDate ";
		
		
		if(byType.equals("month") || byType.equals("year")) {
			builder.appendSql(" and "+dataCompareParam + ">= ? \n");
			builder.appendSql(" and "+dataCompareParam + "< ? \n");
	
			if(byType.equals("month")) {
				builder.addParam(FDCDateHelper.getSqlDate(beginMonthDate));
				builder.addParam(FDCDateHelper.getSqlDate(endMonthDate));
			}else if(byType.equals("year")) {
				builder.addParam(FDCDateHelper.getSqlDate(beginYearDate));
				builder.addParam(FDCDateHelper.getSqlDate(endYearDate));
			}
		}	
		
		//
		
		builder.appendSql("GROUP BY ORGUNIT.FID, SELLPROJECT.FID,"+showByType);
		
		builder.appendSql("\n");
		
		return builder;
	}
	
	
	private void getPurchaseIds(FDCSQLBuilder builder,String baseFilter,String billType,String showByType,String byType,Map filterMap,boolean operationInTime ){
		builder.appendSql("SELECT PURCHASE.FID \n");
		builder.appendSql("FROM T_SHE_Room AS ROOM \n");
		if("QUITROOM".equalsIgnoreCase(billType)){
			builder.appendSql("INNER JOIN T_SHE_QuitRoom AS QUITROOM ON ROOM.FID = QUITROOM.FRoomID  \n");
			builder.appendSql("left outer JOIN T_SHE_Purchase AS PURCHASE ON QUITROOM.FPurchaseID = PURCHASE.FID \n");
		}else if("CHANGEROOM".equalsIgnoreCase(billType)){
			builder.appendSql("INNER JOIN T_SHE_ChangeRoom AS CHANGEROOM ON ROOM.FID = CHANGEROOM.FOLDRoomID \n");
			builder.appendSql("left outer JOIN T_SHE_Purchase AS PURCHASE ON CHANGEROOM.FOldPurchaseID = PURCHASE.FID \n");
		}else{
			builder.appendSql("left JOIN T_SHE_Purchase AS PURCHASE ON ROOM.FLastPurchaseID = PURCHASE.FID \n");
		}
		
		builder.appendSql(" where \n");
		builder.appendSql(baseFilter);
		
		
		getSimpleSqlWhere(builder, byType, showByType, filterMap,billType,operationInTime);
		builder.replaceSql("GROUP BY ORGUNIT.FID, SELLPROJECT.FID,"+showByType, "");
		//builder.getSql().replace(new StringBuffer(" GROUP BY ORGUNIT.FID, SELLPROJECT.FID,"+showByType), new StringBuffer(""));
	}
	
	
	
	private Map getProductOrBuildIdNameMap(boolean showByBuilding,Context ctx) throws SQLException, BOSException {
		Map idNameMap = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		if(showByBuilding)
			builder.appendSql("select fid,fname_l2 from T_SHE_Building \n");
		else
			builder.appendSql("select fid,fname_l2 from T_FDC_ProductType \n");
		if(proOrBuildIdSet!=null && proOrBuildIdSet.size()>0) {
			builder.appendSql(" where \n");
			builder.appendParam("fid",proOrBuildIdSet.toArray());
		}else{
			return idNameMap;
		}
		
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			String fid = tableSet.getString("fid");
			String name = tableSet.getString("fname_l2");
			idNameMap.put(fid, name);
		}		
		return idNameMap;
	}
	
	private Map getSubAreaIdNameMap(boolean showByBuilding,Context ctx) throws SQLException, BOSException {
		Map idNameMap = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		if(showByBuilding){
			builder.appendSql("select BUILDING.FID fid,subArea.Fname_l2 fname_l2 from T_SHE_Building AS BUILDING \n");
			builder.appendSql("LEFT OUTER JOIN T_she_Subarea AS subArea ON subArea.FID = BUILDING.FSubareaID \n");
		/*else
			builder.appendSql("select fid,fname_l2 from T_FDC_ProductType ");*/
			if(proOrBuildIdSet!=null && proOrBuildIdSet.size()>0) {
				builder.appendSql(" where \n");
				builder.appendParam("BUILDING.fid",proOrBuildIdSet.toArray());
			}
		}else{
			return idNameMap;
		}
		
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			String fid = tableSet.getString("fid");
			String name = tableSet.getString("fname_l2");
			idNameMap.put(fid, name);
		}		
		return idNameMap;
	}
	
	private Map getSellProIdNameMap(Context ctx) throws SQLException, BOSException {
		Map idNameMap = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select fid,fname_l2 from T_SHE_SellProject \n");
		if(sellProIdSet!=null && sellProIdSet.size()>0) {
			builder.appendSql(" where \n");
			builder.appendParam("fid",sellProIdSet.toArray());		
		}else{
			return idNameMap;
		}
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			String fid = tableSet.getString("fid");
			String name = tableSet.getString("fname_l2");
			idNameMap.put(fid, name);
		}		
		return idNameMap;
	}
	
	private Map getCompayIdNameMap(Context ctx) throws SQLException, BOSException {
		Map idNameMap = new HashMap();
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select fid,fname_l2 from T_ORG_BaseUnit \n");
		if(comanyIdSet!=null && comanyIdSet.size()>0) {
			builder.appendSql(" where \n");
			builder.appendParam("fid",comanyIdSet.toArray());		
		}else{
			return idNameMap;
		}
		IRowSet tableSet = builder.executeQuery();
		while (tableSet.next()) {
			String fid = tableSet.getString("fid");
			String name = tableSet.getString("fname_l2");
			idNameMap.put(fid, name);
		}		
		return idNameMap;
	}
	
	private Map initRowMap(int rowindex){
		Map row = new HashMap();
		row.put("rowindex", new Integer(rowindex));
		row.put("OID","");
		row.put("SID","");
		row.put("PBID","");
		row.put("totalRoomcount",new Integer(0));
		row.put("buildingArea",new BigDecimal(0));
		row.put("actureBuildArea",new BigDecimal(0));
		
		String[] columnNamesAll = {"all"+"RoomCount","all"+"AreaSum","all"+"PriceSum","all"+"CompensateSum","all"+"PreconcertMoney","all"+"Arrearage","all"+"AccFundAmount"};
		String[] columnNamesMonth = {"month"+"RoomCount","month"+"AreaSum","month"+"PriceSum","month"+"CompensateSum","month"+"PreconcertMoney","month"+"Arrearage","month"+"AccFundAmount"};
		String[] columnNamesYear = {"year"+"RoomCount","year"+"AreaSum","year"+"PriceSum","year"+"CompensateSum","year"+"PreconcertMoney","year"+"Arrearage","year"+"AccFundAmount"};
		
		String[] sums = {"all"+"ResvSum","all"+"LoanSum","all"+"CompensateInSum","all"+"ResvSum_all"};
		String[] sumsmonth = {"month"+"ResvSum","month"+"LoanSum","month"+"CompensateInSum","month"+"ResvSum_all"};
		String[] sumsyear = {"year"+"ResvSum","year"+"LoanSum","year"+"CompensateInSum","year"+"ResvSum_all"};
		
		row.put(columnNamesAll[0], new Integer(0));
		row.put(columnNamesAll[1], new BigDecimal(0.00));
		row.put(columnNamesAll[2], new BigDecimal(0.00));
		row.put(columnNamesAll[3], new BigDecimal(0.00));
		row.put(columnNamesAll[4], new BigDecimal(0.00));
		row.put(columnNamesAll[5], new BigDecimal(0.00));
		row.put(columnNamesAll[6], new BigDecimal(0.00));
		
		row.put(columnNamesMonth[0], new Integer(0));
		row.put(columnNamesMonth[1], new BigDecimal(0.00));
		row.put(columnNamesMonth[2], new BigDecimal(0.00));
		row.put(columnNamesMonth[3], new BigDecimal(0.00));
		row.put(columnNamesMonth[4], new BigDecimal(0.00));
		row.put(columnNamesMonth[5], new BigDecimal(0.00));
		row.put(columnNamesMonth[6], new BigDecimal(0.00));
		
		row.put(columnNamesYear[0], new Integer(0));
		row.put(columnNamesYear[1], new BigDecimal(0.00));
		row.put(columnNamesYear[2], new BigDecimal(0.00));
		row.put(columnNamesYear[3], new BigDecimal(0.00));
		row.put(columnNamesYear[4], new BigDecimal(0.00));
		row.put(columnNamesYear[5], new BigDecimal(0.00));
		row.put(columnNamesYear[6], new BigDecimal(0.00));
		
		row.put(sums[0], new BigDecimal(0.00));
		row.put(sums[1], new BigDecimal(0.00));
		row.put(sums[2], new BigDecimal(0.00));
		
		row.put(sums[3], new BigDecimal(0.00));
	
		row.put(sumsmonth[0], new BigDecimal(0.00));
		row.put(sumsmonth[1], new BigDecimal(0.00));
		row.put(sumsmonth[2], new BigDecimal(0.00));
		
		row.put(sumsmonth[3], new BigDecimal(0.00));
		
		row.put(sumsyear[0], new BigDecimal(0.00));
		row.put(sumsyear[1], new BigDecimal(0.00));
		row.put(sumsyear[2], new BigDecimal(0.00));
		
		row.put(sumsyear[3], new BigDecimal(0.00));
		
		//row.put("preconcertMoney", new BigDecimal(0));
		//row.put("allArrearage", new BigDecimal(0));
		
		return row;
		
	}

}