package com.kingdee.eas.fdc.tenancy.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SubareaFactory;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class RentRatioFacadeControllerBean extends AbstractRentRatioFacadeControllerBean
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4126057598335635111L;
	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.RentRatioFacadeControllerBean");
    /**
     * 功能：获取出租率报表信息
     *      由工程项目，分区，楼栋过滤条件，以楼栋为维度统计已出租面积、可出租面积、出年平均出租率、期末出租率、平均单价信息
     * @author pu_zhang
     * @date  2010-07-17
     * @param Context, Map
     * @throws BOSException
     */
    protected Map _getRentRatioInfo(Context ctx, Map param)throws BOSException
    {

    	
    	String projectID = "";
    	List arearidList = null;
    	List buildingidList = null;
    	 //工程项目
    	if(param.get("projectid")!=null){
    		projectID = String.valueOf(param.get("projectid"));
    	}
    	
    	//分区
    	if(param.get("arearidList")!=null){
    		arearidList =(List)param.get("arearidList");
    	}
    	//楼栋
    	if(param.get("buildingidList")!=null){
    		buildingidList = (List)param.get("buildingidList");
    	}
    	
    	Map result = new HashMap();
    	List lst = new ArrayList();
    	List lstParem = new ArrayList();
    	Map tenancy =null;
		//工程项目，分区，楼栋
    	StringBuffer sqlInf = new StringBuffer();
    	sqlInf.append("select T_building.ID as ID,T_building.saleProjectName as projectName,T_building.subAreaName as area,T_building.buildingName as building, ");
    	sqlInf.append("       T_rentedArea.rentedArea as rentedArea,T_AvalableRentArea.AvalableRentArea as  avalableArea");
    	sqlInf.append(" from   ( ");
    	sqlInf.append("	        select sellProject.fid as projectID,subarea.fid as subareaID,  building.fid as ID,sellProject.fname_l2 as saleProjectName,subarea.fname_l2 as ");
    	sqlInf.append("		           subAreaName, building.fname_l2 as buildingName  ");
    	sqlInf.append("	         from  t_she_building building  ");
    	sqlInf.append("	               left join t_she_sellProject sellProject on building.FSellProjectID =sellProject.fid ");
    	sqlInf.append("	               left join  t_she_subarea subarea on building.FSubareaID = subarea.fid ");
    	sqlInf.append("         ) T_building ");
    	sqlInf.append("	left join (select room.FBuildingID, sum(ROOM.FTenancyArea)  as AvalableRentArea ");
    	sqlInf.append("	  	        from t_she_room room ");
    	sqlInf.append("		       where room.fTenancystate is not null and   room.fTenancystate <>'UNTenancy' group by room.FBuildingID ");
    	sqlInf.append("		      ) T_AvalableRentArea on T_building.ID =T_AvalableRentArea.FBuildingID ");
    	sqlInf.append("	left join (select room.FBuildingID, sum(ROOM.FTenancyArea)  as rentedArea");
    	sqlInf.append("		        from t_she_room room ");
    	sqlInf.append("		       where room.fTenancystate ='NewTenancy' or room.fTenancystate ='ContinueTenancy' or room.fTenancystate ='EnlargeTenancy' ");
    	sqlInf.append("			        group by room.FBuildingID");
    	sqlInf.append("		      ) T_rentedArea  on T_building.ID =T_rentedArea.FBuildingID ");
    	sqlInf.append(" where 1=1 ");
    	
    	//工程项目ID
    	if(!projectID.equalsIgnoreCase("")){
    		lstParem.add(projectID);
    		sqlInf.append(" and T_building.projectID= ? ");
    	}
    	
    	//分区ID
    	if(arearidList!=null && arearidList.size()>0){
    		sqlInf.append(" and T_building.subareaID in ( '##' ");
			for(int k=0; k<arearidList.size(); k++){
				lstParem.add(arearidList.get(k)); 
				sqlInf.append(", ? ");
			}
			sqlInf.append(" )");
    	}
    	
    	//楼栋ID
    	if(buildingidList!=null && buildingidList.size()>0){
    		sqlInf.append("  and T_building.ID in  ( '##' ");
			for(int k=0; k<buildingidList.size(); k++){
				lstParem.add(buildingidList.get(k)); 
				sqlInf.append(", ?");
			}
			sqlInf.append(" )");
    	}
 
    	sqlInf.append(" order by T_building.projectID, T_building.subAreaName, T_building.buildingName ");
    	
    	IRowSet rsRentedInfo= DbUtil.executeQuery(ctx, sqlInf.toString(),lstParem.toArray());
    	
//    	//已租售面积
//    	StringBuffer sqlrentedArea = new StringBuffer();
//    	sqlrentedArea.append("    	select room.FBuildingID, sum(ROOM.FTenancyArea)  as rentedArea");
//    	sqlrentedArea.append("  	    from t_she_room room ");
//    	sqlrentedArea.append("	   where room.fTenancystate is not null and   room.fTenancystate <>'UNTenancy' group by room.FBuildingID"); 
//    	IRowSet rsRentedArea= DbUtil.executeQuery(ctx, sqlrentedArea.toString());
//    	//可租售面积
//    	StringBuffer sqlArea = new StringBuffer();
//    	sqlArea.append("    	select room.FBuildingID, sum(ROOM.FTenancyArea)  as AvalableRentArea");
//    	sqlArea.append("		   from t_she_room room ");
//    	sqlArea.append("		  where room.fTenancystate ='NewTenancy' or room.fTenancystate ='ContinueTenancy' or room.fTenancystate ='EnlargeTenancy'"); 
//    	sqlArea.append("			group by room.FBuildingID");
//    	IRowSet rsAvalableArea= DbUtil.executeQuery(ctx, sqlArea.toString());
    	
    	Date curDate = new Date();
    	Date fistDayOfYear =  FDCDateHelper.getFirstYearDate(curDate) ;
    	String currDateStr =  FDCDateHelper.DateToString(curDate);
    	String fistDayOfYearStr =  FDCDateHelper.DateToString(fistDayOfYear);
    	
    	//年出租面积*出租天数
    	StringBuffer sqlAreaMDay = new StringBuffer();
    	List lstParemArea = new ArrayList();
    	sqlAreaMDay.append("select  room.fbuildingid as ID,sum(room.FTenancyArea*");
    	sqlAreaMDay.append("( case when (TENANCYBILL.fstartdate <= {ts'").append(currDateStr).append(" 00:00:00'})");
    	sqlAreaMDay.append("       then");
    	sqlAreaMDay.append("	       case when (TENANCYBILL.fenddate <={ts'").append(currDateStr).append(" 00:00:00'}) ");
    	sqlAreaMDay.append("     		    then case when (TENANCYBILL.fstartdate <={ts'").append(fistDayOfYearStr).append(" 00:00:00'}) ");
    	sqlAreaMDay.append("		 	              then (datediff(day,{ts'").append(fistDayOfYearStr).append(" 00:00:00'},TENANCYBILL.fenddate)+1) ");
    	sqlAreaMDay.append("		 	              else (datediff(day,TENANCYBILL.fstartdate,TENANCYBILL.fenddate)+1) ");
    	sqlAreaMDay.append("         	         end ");
    	sqlAreaMDay.append("    	        else case when (TENANCYBILL.fstartdate <={ts'").append(fistDayOfYearStr).append(" 00:00:00'}) ");
    	sqlAreaMDay.append("		                  then (datediff(day,{ts'").append(fistDayOfYearStr).append(" 00:00:00'},{ts'").append(currDateStr).append(" 00:00:00'})+1) ");
    	sqlAreaMDay.append("		                  else (datediff(day,TENANCYBILL.fstartdate,{ts'").append(currDateStr).append(" 00:00:00'})+1) ");
    	sqlAreaMDay.append("         	         end ");
    	sqlAreaMDay.append("	       end ");
    	sqlAreaMDay.append("     else ");
    	sqlAreaMDay.append("         0 ");
    	sqlAreaMDay.append(" end ) ) as sumTenAreaPlsDay ");
		sqlAreaMDay.append(" from t_ten_tenancyroomentry tenancyroomentry ");
		sqlAreaMDay.append("left join  T_TEN_TenancyBill TENANCYBILL  on  tenancyroomentry.FTenancyID=TENANCYBILL.fid  ");
		sqlAreaMDay.append("left join  t_she_room room on tenancyroomentry.FRoomID = room.fid ");
		sqlAreaMDay.append(" where TENANCYBILL.fenddate >= {ts'").append(fistDayOfYearStr).append(" 00:00:00'}  and TENANCYBILL.ftenancystate  in('PartExecuted','Executing','ContinueTenancying','RejiggerTenancying','ChangeNaming','TenancyChanging','QuitTenancying','Expiration' ) ");

		
		//楼栋ID
    	if(buildingidList!=null && buildingidList.size()>0){
    		sqlAreaMDay.append(" and room.fbuildingid  in  ( '##' ");
			for(int k=0; k<buildingidList.size(); k++){
				lstParemArea.add(buildingidList.get(k)); 
				sqlAreaMDay.append(", ?");
			}
			sqlAreaMDay.append(" )");
    	}
		
		sqlAreaMDay.append(" group by room.fbuildingid");
		IRowSet rsAreaMDay= DbUtil.executeQuery(ctx, sqlAreaMDay.toString(),lstParemArea.toArray());
		
		//房间总金额
		StringBuffer sqlAreaMAmt = new StringBuffer();
		List lstParemMAmt = new ArrayList();
		sqlAreaMAmt.append("select  room.fbuildingID as ID, sum(ROOM.FTenancyArea*T_roomAmt.roomAmt) as areaPlsTotalAmt ");
		sqlAreaMAmt.append(" from  t_she_room room ");
		sqlAreaMAmt.append(" left join ");
		sqlAreaMAmt.append("(select room.fid  as roomID,sum( ");
		sqlAreaMAmt.append("                                case when datediff(day,TENANCYBILL.FStartDate,TENANCYBILL.FEndDate) < 0   then 0 ");
		sqlAreaMAmt.append("                                     else payListentry.fappamount/(datediff(day,TENANCYBILL.FStartDate,TENANCYBILL.FEndDate)+1) ");
		sqlAreaMAmt.append("                                 end ");
		sqlAreaMAmt.append("                              ) as roomAmt ");
		sqlAreaMAmt.append(" from t_ten_tenancyroompayListEntry payListentry  ");
		sqlAreaMAmt.append("      join T_SHE_MoneyDefine moneydefine on payListentry.fmoneydefineID = moneydefine.fid  ");
		sqlAreaMAmt.append("      join t_ten_tenancyroomentry tenancyroomentry on  payListentry.FTenRoomID = tenancyroomentry.fid ");
		sqlAreaMAmt.append("      join t_she_room room on tenancyroomentry.FRoomID=room.fid ");
//		sqlAreaMAmt.append("      left join T_TEN_TenancyBill TENANCYBILL on room.FLastTenancyID = TENANCYBILL.fid ");
		sqlAreaMAmt.append("      join T_TEN_TenancyBill TENANCYBILL on TENANCYBILL.FID = tenancyroomentry.FTenancyID ");
		sqlAreaMAmt.append("  where  moneydefine.fmoneytype  in ('PeriodicityAmount','RentAmount') ");
		sqlAreaMAmt.append("       and TENANCYBILL.ftenancystate in ('PartExecuted','Executing','ContinueTenancying','RejiggerTenancying','ChangeNaming','TenancyChanging','QuitTenancying' ) ");
		sqlAreaMAmt.append("  group by  room.fid ) T_roomAmt on T_roomAmt.roomID =room.fid ");
		sqlAreaMAmt.append("where 1=1 ");

		//楼栋ID
    	if(buildingidList!=null && buildingidList.size()>0){
    		sqlAreaMAmt.append(" and room.fbuildingID in  ( '##' ");
			for(int k=0; k<buildingidList.size(); k++){
				 lstParemMAmt.add(buildingidList.get(k));
				 sqlAreaMAmt.append(", ?");
			}
			sqlAreaMAmt.append(" )");
    	}
		
		sqlAreaMAmt.append("		group by room.fbuildingID");
		IRowSet rsAreaMAmt= DbUtil.executeQuery(ctx, sqlAreaMAmt.toString(),lstParemMAmt.toArray());
		
		Map  rsAreaMDayMap = new HashMap();
		Map  rsAreaMAmtMap = new HashMap();
		try {
			
			Date currDate = FDCDateHelper.getDayBegin();
			Date firstDay = FDCDateHelper.getFirstYearDate(currDate);
			//包含当天
			BigDecimal dayDiff = new BigDecimal(FDCDateHelper.dateDiff("d", firstDay, currDate)+1);
			
			while(rsAreaMDay.next()){
				BigDecimal sumTenAreaPlsDay = rsAreaMDay.getBigDecimal("sumTenAreaPlsDay")==null?FDCConstants.B0:rsAreaMDay.getBigDecimal("sumTenAreaPlsDay");
				rsAreaMDayMap.put(rsAreaMDay.getString("ID"), sumTenAreaPlsDay);
			}
			
			while(rsAreaMAmt.next()){
				BigDecimal areaPlsTotalAmt =  rsAreaMAmt.getBigDecimal("areaPlsTotalAmt")==null?FDCConstants.B0:rsAreaMAmt.getBigDecimal("areaPlsTotalAmt");
				rsAreaMAmtMap.put(rsAreaMAmt.getString("ID"), areaPlsTotalAmt);
			}
		
		
			while(rsRentedInfo.next()){
				tenancy = new HashMap();
				String ID = rsRentedInfo.getString("ID");
				tenancy.put("ID",ID );
				tenancy.put("projectName",rsRentedInfo.getString("projectName") );
				tenancy.put("area",rsRentedInfo.getString("area") );
				tenancy.put("building",rsRentedInfo.getString("building") );
				BigDecimal rentedArea = rsRentedInfo.getBigDecimal("rentedArea")==null? FDCConstants.B0:rsRentedInfo.getBigDecimal("rentedArea");
				BigDecimal avalableArea = rsRentedInfo.getBigDecimal("avalableArea")==null? FDCConstants.B0:rsRentedInfo.getBigDecimal("avalableArea");
				tenancy.put("rentedArea",rentedArea );
				tenancy.put("avalableArea",avalableArea);
				if(rsRentedInfo.getBigDecimal("avalableArea")==null){
					tenancy.put("endRentRatio", FDCConstants.B0 );
				}
				else{
					tenancy.put("endRentRatio",rentedArea.multiply(FDCConstants.B100).divide(avalableArea, 2, BigDecimal.ROUND_HALF_UP) );
				}
				
				//设置averageRentRatio值
				Set rsAreaMDaySet = rsAreaMDayMap.keySet();
				Iterator rsAreaMDayIter = rsAreaMDaySet.iterator();
				while(rsAreaMDayIter.hasNext()){
					Object vo =rsAreaMDayIter.next();
					if(vo!=null){
						String keyID = (String) vo;
						if(ID.equalsIgnoreCase(keyID)){
							BigDecimal sumTenAreaPlsDay = (BigDecimal)rsAreaMDayMap.get(keyID);
							BigDecimal averageRentRatio = sumTenAreaPlsDay.multiply(FDCConstants.B100).divide(avalableArea.multiply(dayDiff),2, BigDecimal.ROUND_HALF_UP);
							tenancy.put("averageRentRatio",averageRentRatio );
							break;
						}
					}
				}
				if(tenancy.get("averageRentRatio")==null){
					tenancy.put("averageRentRatio", FDCConstants.B0 );
				}

				//设置averagePrice值
				Set rsAreaMAmtSet = rsAreaMAmtMap.keySet();
				Iterator rsAreaMAmtIter = rsAreaMAmtSet.iterator();
				while(rsAreaMAmtIter.hasNext()){
					Object vo =rsAreaMAmtIter.next();
					if(vo!=null){
						String keyID = (String) vo;
						if(ID.equalsIgnoreCase(keyID)){
							BigDecimal areaPlsTotalAmt = (BigDecimal)rsAreaMAmtMap.get(keyID);
							BigDecimal averagePrice = FDCConstants.B0;
							if(rsRentedInfo.getBigDecimal("rentedArea")!=null){
								averagePrice = areaPlsTotalAmt.divide(rentedArea, 2, BigDecimal.ROUND_HALF_UP);
							}
							tenancy.put("averagePrice",averagePrice );
							break;
						}
					}
				}
				if(tenancy.get("averagePrice")==null){
					tenancy.put("averagePrice", FDCConstants.B0 );
				}
				
				lst.add(tenancy);
			}
		} catch (SQLException e) {
			logger.error(e);
		}
    	result.put("rentRatioInfo", lst);
		return result;
    }
    
	protected Map _getBatchFilterInfo(Context ctx, FDCCustomerParams customerParams) throws BOSException {
		Map resultMap = new HashMap();
		try {
			if (customerParams.getString("F7Project") != null) {
				resultMap.put("F7Project", SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(customerParams.getString("F7Project"))));
			}
			//地区id
			if (customerParams.isNotNull("areaIds")) {
				String[] costArray = customerParams.getStringArray("areaIds");
				Object[] oa = new Object[costArray.length];
				for (int i = 0; i < costArray.length; i++) {
						oa[i]=SubareaFactory.getRemoteInstance().getSubareaInfo(new ObjectUuidPK(costArray[i]));
				}
				resultMap.put("areaIds", oa);
			}
			//楼栋id
			if (customerParams.isNotNull("buildingIds")) {
				String[] costArray = customerParams.getStringArray("buildingIds");
				Object[] ba = new Object[costArray.length];
				for (int i = 0; i < costArray.length; i++) {
					ba[i]=BuildingFactory.getRemoteInstance().getBuildingInfo(new ObjectUuidPK(costArray[i]));
				}
				resultMap.put("buildingIds", ba);
			}
			
		} catch (EASBizException e) {
			logger.error(e);
		}

		return null;
	}

	protected Map _getBatchFilterInfo(Context ctx, Map customerParams) throws BOSException {
		Map resultMap = new HashMap();
		try {
			if (customerParams.get("F7Project") != null) {
				String id = (String) customerParams.get("F7Project");
				resultMap.put("F7Project", SellProjectFactory.getLocalInstance(ctx).getSellProjectInfo(new ObjectUuidPK(id)));
			}
			//地区id
			if (customerParams.get("areaIds")!=null) {
				String[] costArray = (String[]) customerParams.get("areaIds");
				Object[] oa = new Object[costArray.length];
				for (int i = 0; i < costArray.length; i++) {
						oa[i]=SubareaFactory.getLocalInstance(ctx).getSubareaInfo(new ObjectUuidPK(costArray[i]));
				}
				resultMap.put("areaIds", oa);
			}
			//楼栋id
			if (customerParams.get("buildingIds")!=null) {
				String[] costArray = (String[]) customerParams.get("buildingIds");
				Object[] ba = new Object[costArray.length];
				for (int i = 0; i < costArray.length; i++) {
					ba[i]=BuildingFactory.getLocalInstance(ctx).getBuildingInfo(new ObjectUuidPK(costArray[i]));
				}
				resultMap.put("buildingIds", ba);
			}
			
		} catch (EASBizException e) {
			logger.error(e);
		}

		return resultMap;
	}
  
}