package com.kingdee.eas.fdc.sellhouse.app;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.jdbc.rowset.IRowSet;

public class ArrearageQueryFacadeControllerBean extends AbstractArrearageQueryFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.ArrearageQueryFacadeControllerBean");
    
    private static final String  AFMortgagedState="'"+AFMortgagedStateEnum.TRANSACTING_VALUE+"','"+AFMortgagedStateEnum.TRANSACTED_VALUE+"'";
    
    private static final String PurchaseState="'"+PurchaseStateEnum.CHANGEROOMBLANKOUT_VALUE+"', '"+PurchaseStateEnum.QUITROOMBLANKOUT_VALUE	+"','"+PurchaseStateEnum.ADJUSTBLANKOUT_VALUE +"','"
	                    		+PurchaseStateEnum.NOPAYBLANKOUT_VALUE+"','"+PurchaseStateEnum.MANUALBLANKOUT_VALUE+"'";
    
    protected Map _getArrearageDate(Context ctx, Map map)throws BOSException, EASBizException
    {
    	Map allMap = new HashMap();
    	try {
    		allMap = (Map)getExecuteSqlString(ctx,map);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return allMap;
    }
    private Map getExecuteSqlString(Context ctx, Map mapFilter) throws BOSException, SQLException{
    	Map allMap = new HashMap();
    	
    	Map filterMap = (Map)mapFilter.get("filter");
		StringBuffer sonFilterSql = (StringBuffer) mapFilter.get("sqlStr");

//		Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());	

		String RoomSellStatuStr=null;
		if(filterMap.get("RoomSellStatuStr")!=null)  {
			RoomSellStatuStr = (String)filterMap.get("RoomSellStatuStr");	//房间销售状态值
			if(RoomSellStatuStr!=null && !RoomSellStatuStr.equals("")){
				RoomSellStatuStr="'"+RoomSellStatuStr.replaceAll("<>", "','")+"'";
				sonFilterSql.append(" and ROOM.FSellState in ("+RoomSellStatuStr+") ");
			}
		}
		DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
		int prePurchase = 0;
		//业务日期
		if(RoomSellStatuStr!=null && filterMap.get("purBeginDate")!=null && filterMap.get("purEndDate")!=null){
			String[] sellstate = RoomSellStatuStr.split(",");
			sonFilterSql.append(" and (");
			for(int i = 0;i < sellstate.length;i++){
				String sellsta = sellstate[i].substring(1,sellstate[i].length()-1);
				if(sellstate.length > 1 && i != 0 && filterMap.get("purBeginDate")!=null && filterMap.get("purEndDate")!=null){
					sonFilterSql.append(" or ");
				}
				if(filterMap.get("purBeginDate")!=null && filterMap.get("purEndDate")!=null)  {
					Date purBeginDate = new Date(((Timestamp)filterMap.get("purBeginDate")).getTime());
					Date purEndDate = new Date(((Timestamp)filterMap.get("purEndDate")).getTime());
					if(sellsta.equals(RoomSellStateEnum.PURCHASE_VALUE)){
						sonFilterSql.append(" (PURCHASE.FPurchaseDate >= {ts'"+FORMAT_DAY.format(purBeginDate)+"'} and PURCHASE.FPurchaseDate < {ts'"+FORMAT_DAY.format(purEndDate)+"'}) ");
					}
					else if(sellsta.equals(RoomSellStateEnum.PREPURCHASE_VALUE)){
						sonFilterSql.append(" (PURCHASE.FPrePurchaseDate >= {ts'"+FORMAT_DAY.format(purBeginDate)+"'} and PURCHASE.FPrePurchaseDate < {ts'"+FORMAT_DAY.format(purEndDate)+"'}) ");
						sonFilterSql.append(" or (PURCHASE.FPurchaseDate >= {ts'"+FORMAT_DAY.format(purBeginDate)+"'} and PURCHASE.FPurchaseDate < {ts'"+FORMAT_DAY.format(purEndDate)+"'}) ");
					}
					else if(sellsta.equals(RoomSellStateEnum.SIGN_VALUE)){
						sonFilterSql.append(" (PURCHASE.FToSignDate >= {ts'"+FORMAT_DAY.format(purBeginDate)+"'} and PURCHASE.FToSignDate < {ts'"+FORMAT_DAY.format(purEndDate)+"'}) ");
					}
				}
//				if(filterMap.get("purEndDate")!=null){
//					Date purEndDate = new Date(((Timestamp)filterMap.get("purEndDate")).getTime());
//					if(sellsta.equals(RoomSellStateEnum.PURCHASE_VALUE)){
//						sonFilterSql.append("and PURCHASE.FPurchaseDate < {ts'"+FORMAT_DAY.format(purEndDate)+"'}) ");
//					}
//					else if(sellsta.equals(RoomSellStateEnum.PREPURCHASE_VALUE)){
//						sonFilterSql.append("and PURCHASE.FPrePurchaseDate < {ts'"+FORMAT_DAY.format(purEndDate)+"'}) ");
//					}
//					else if(sellsta.equals(RoomSellStateEnum.SIGN_VALUE)){
//						sonFilterSql.append("and PURCHASE.FToSignDate < {ts'"+FORMAT_DAY.format(purEndDate)+"'}) ");
//					}
//				}
			}
			sonFilterSql.append(") ");
		}
		if(filterMap.get("dpAppBeginDate")!=null) {
			Date dpAppBeginDate = new Date(((Timestamp)filterMap.get("dpAppBeginDate")).getTime());
			sonFilterSql.append("and PAYLISTENTRY.FAppDate >= {ts'"+FORMAT_DAY.format(dpAppBeginDate)+"'} ");
		}
		if(filterMap.get("dpAppEndDate")!=null)	 {
			Date dpAppEndDate = new Date(((Timestamp)filterMap.get("dpAppEndDate")).getTime());
			sonFilterSql.append("and PAYLISTENTRY.FAppDate < {ts'"+FORMAT_DAY.format(dpAppEndDate)+"'} ");
		}
		if(filterMap.get("PurIdStr")!=null)   {
			String PurIdStr = (String)filterMap.get("PurIdStr");	//认购单的id集合
			sonFilterSql.append("and PURCHASE.FID in ("+PurIdStr+") ");
		}
		if(filterMap.get("MoneyTypeIdStr")!=null)  {
			String MoneyTypeIdStr = (String)filterMap.get("MoneyTypeIdStr");	//款项类型的id集合
			MoneyTypeIdStr="'"+MoneyTypeIdStr.replaceAll("<>", "','")+"'";
			sonFilterSql.append("and MONEYDEFINE.FID in ("+MoneyTypeIdStr+") ");
		}else{
			return null;		//确保一定选择了款项类型
		}

		StringBuffer fatherFilterSql = new StringBuffer();
		if(filterMap.get("checkYuQiArage")!=null) {   //逾期欠款 = 欠款>0 and 逾期时间 > 0天 (逾期欠款,已经到期尚未付清)
			boolean checkYuQiArage = ((Integer)filterMap.get("checkYuQiArage")).intValue()>0?true:false;
			if(checkYuQiArage) {
				int areageDay = 0;
				if(filterMap.get("textYuQiDayNum")!=null)	{
					areageDay = ((Integer)filterMap.get("textYuQiDayNum")).intValue();
				}
				fatherFilterSql.append("or (ARREARAGEQUERY.ARREARAGEAMOUNT > 0 and DATEDIFF(dd,ARREARAGEQUERY.PAYLISTENTRYAPDATE,ARREARAGEQUERY.ARREARAGETIME) >"+areageDay+") ");
			}
		}
		if(filterMap.get("checkUnYuQiArage")!=null)  {   //未逾期欠款,没有到应付日期的欠款
			boolean checkUnYuQiArage = ((Integer)filterMap.get("checkUnYuQiArage")).intValue()>0?true:false;
			if(checkUnYuQiArage) {
				fatherFilterSql.append("or (ARREARAGEQUERY.ARREARAGEAMOUNT > 0 and DATEDIFF(dd,ARREARAGEQUERY.PAYLISTENTRYAPDATE,ARREARAGEQUERY.ARREARAGETIME)<=0 ) ");
			}
		}
		if(filterMap.get("checkYuQiPay")!=null) {   //逾期付清,曾经逾期现在已经付清的.
			boolean checkYuQiPay = ((Integer)filterMap.get("checkYuQiPay")).intValue()>0?true:false;
			if(checkYuQiPay) {
				int areageDay = 0;
				if(filterMap.get("textYuQiDayNum")!=null)	{
					areageDay = ((Integer)filterMap.get("textYuQiDayNum")).intValue();
				}
				fatherFilterSql.append("or (ARREARAGEQUERY.ARREARAGEAMOUNT <= 0 and DATEDIFF(dd,ARREARAGEQUERY.PAYLISTENTRYAPDATE,ARREARAGEQUERY.ARREARAGETIME)>"+areageDay+") ");
			}
		}
		if(filterMap.get("checkPayOnTime")!=null)  {  //按时付清,正常付款
			boolean checkPayOnTime = ((Integer)filterMap.get("checkPayOnTime")).intValue()>0?true:false;
			if(checkPayOnTime) {
				fatherFilterSql.append("or (ARREARAGEQUERY.ARREARAGEAMOUNT <= 0 and DATEDIFF(dd,ARREARAGEQUERY.PAYLISTENTRYAPDATE,ARREARAGEQUERY.ARREARAGETIME)<=0 ) ");
			}			
		}
		if(fatherFilterSql.toString().equals("")) return null;   //确保一定选择了欠款类型			
		
		
		
		//MoenyType_Num = "moneyTypeNum";   //款项类型种类 ，存数字  0全部，1应收，2其它部分
		int textYuQiDayNum = 0;
		if(filterMap.get("moneyTypeNum")!=null)  textYuQiDayNum = ((Integer)filterMap.get("moneyTypeNum")).intValue();
		
		StringBuffer unionBuilder = new StringBuffer();   //
		
		unionBuilder.append("select ARREARAGEQUERY.*,DATEDIFF(dd,ARREARAGEQUERY.PAYLISTENTRYAPDATE,ARREARAGEQUERY.ARREARAGETIME) arrearageDay from (");
		if(filterMap.get("checkYuQiArage")!=null || filterMap.get("checkUnYuQiArage")!=null) { //取值当前服务器日期
			chackSQL(true,false,sonFilterSql,textYuQiDayNum,unionBuilder);
		}
		if(filterMap.get("checkYuQiPay")!=null || filterMap.get("checkPayOnTime")!=null){ //取值实收日期
			if(filterMap.get("checkYuQiArage")!=null || filterMap.get("checkUnYuQiArage")!=null) {
				unionBuilder.append("\t\n").append(" union ").append("\t\n");
			}
			chackSQL(false,true,sonFilterSql,textYuQiDayNum,unionBuilder);
		}
		unionBuilder.append(") ARREARAGEQUERY ");
		unionBuilder.append("WHERE " + fatherFilterSql.toString().replaceFirst("or", ""));
		unionBuilder.append("ORDER BY SELLPROJECTNAME ASC,SUBAREANAME ASC,BUILDINGNAME ASC,ROOMUNIT ASC,ROOMNUMBER ASC,PAYLISTENTRYSEQ ASC");

		FDCSQLBuilder builderUnion = new FDCSQLBuilder(ctx);
		if(!unionBuilder.equals("")){
			builderUnion.appendSql(unionBuilder.toString());
		}
		IRowSet unionSet = builderUnion.executeQuery(ctx);
		//查出按揭银行放到MAP里
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.clear();
		builder.appendSql("SELECT ROOM.FID ROOMID, MONEYDEFINE.FName_l2 MONEYNAME, BANK.FName_l2 LOANBANK  from   T_SHE_RoomLoan  ROOMLOAN").appendSql("\t\n");
		builder.appendSql("LEFT JOIN T_SHE_Purchase PURCHASE ON  ROOMLOAN.FPurchaseID=PURCHASE.FID").appendSql("\t\n");
		builder.appendSql("LEFT OUTER JOIN T_SHE_Room ROOM ON ROOMLOAN.FRoomID = ROOM.FID").appendSql("\t\n");
		builder.appendSql("LEFT JOIN T_SHE_LoanData LOANDATA ON ROOMLOAN.FLoanDataID = LOANDATA.FID").appendSql("\t\n");
		builder.appendSql("LEFT JOIN T_SHE_MoneyDefine MONEYDEFINE ON ROOMLOAN.FMmType=MONEYDEFINE.FiD").appendSql("\t\n");
		builder.appendSql("LEFT JOIN  T_BD_Bank BANK ON LOANDATA.FBankID = BANK.FID").appendSql("\t\n");
		builder.appendSql("LEFT OUTER JOIN T_SHE_Building BUILDING ON ROOM.FBuildingID = BUILDING.FID").appendSql("\t\n");
		builder.appendSql("LEFT OUTER JOIN T_SHE_SellProject SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID").appendSql("\t\n");
		builder.appendSql("LEFT OUTER JOIN T_SHE_Subarea SUBAREA ON BUILDING.FSubareaID = SUBAREA.FID").appendSql("\t\n");
		builder.appendSql("LEFT OUTER JOIN T_SHE_BuildingUnit BuildingUnit ON ROOM.FBuildUnitID = BuildingUnit.FID ").appendSql("\t\n");
		builder.appendSql("WHERE FAFMortgagedState in(").appendSql(AFMortgagedState).appendSql(")").appendSql("\t\n");
		builder.appendSql("and ROOMLOAN.fid is not null").appendSql("\t\n");
		builder.appendSql("AND PURCHASE.FPurchaseState NOT IN (").appendSql(PurchaseState).appendSql(")").appendSql("\t\n");
		IRowSet rowSet = builder.executeQuery(ctx);
		
		//电话号码
		FDCSQLBuilder builder2 = new FDCSQLBuilder(ctx);
		builder2
		.appendSql(" select purchase.fid ID,room.fid roomid,customer.FPhone fphone,customer.FTel ftel,customer.FOfficeTel fofficeTel,customer.FPhone2 otherphone " 
				+ " from t_she_room room "
    			+ " LEFT OUTER JOIN T_SHE_BuildingUnit AS BUILDUNIT ON ROOM.FBuildUnitID = BUILDUNIT.FID"
				+ " left join t_she_purchase purchase on room.FLastPurchaseID = purchase.fid "
				+ " left join T_SHE_PurchaseCustomerInfo  purCustomer on purchase.fid = purCustomer.fheadid "
				+ " left join t_she_fdccustomer customer on purCustomer.FCustomerID = customer.fid "
				+ " WHERE room.FIsForSHE=1 ");
		builder2.appendSql(" and (room.fSellState = '" + RoomSellStateEnum.PURCHASE_VALUE + "' or room.fSellState = '" + RoomSellStateEnum.SIGN_VALUE + "' or room.fSellState = '" + RoomSellStateEnum.PREPURCHASE_VALUE + "') and  room.FHouseProperty = '"+HousePropertyEnum.NOATTACHMENT_VALUE+"' ");
    	
		IRowSet phoneSet = builder2.executeQuery(ctx);
		
		allMap.put("map", rowSet);
		allMap.put("unionSet", unionSet);
		allMap.put("phoneSet", phoneSet);
		
		return allMap;
	}
    
    private StringBuffer chackSQL(boolean nowTime,boolean revTime,StringBuffer sonFilterSql,int textYuQiDayNum,StringBuffer unionBuilder){
    	
    	StringBuffer shouldSql = new StringBuffer();
		StringBuffer elseSql = new StringBuffer();
		shouldSql.append("SELECT PURCHASE.FID ID, ROOM.FID ROOMID, SELLPROJECT.FName_l2 SELLPROJECTNAME, SUBAREA.FName_l2 SUBAREANAME,  ");
		shouldSql.append("BUILDING.FName_l2 BUILDINGNAME, BuildingUnit.FName_l2 ROOMUNIT, ROOM.FDisplayName ROOMNUMBER,"); 
		shouldSql.append("PURCHASE.FCustomerNames CUSTOMERNAMES,PURCHASE.FCustomerPhones CUSTOMERPHONES, ROOMMODEL.FName_l2 ROOMMODELNAME, PRODUCTTYPE.FName_l2 PRODUCTTYPENAME,"); 
		shouldSql.append("MONEYDEFINE.FName_l2 MONEYDEFINENAME, PAYLISTENTRY.FAppAmount PAYLISTENTRYAPAMOUNT, PAYLISTENTRY.FAppDate PAYLISTENTRYAPDATE,"); 
		shouldSql.append(" CASE WHEN (PAYLISTENTRY.FActRevAmount IS NULL) THEN 0 ELSE PAYLISTENTRY.FActRevAmount END " 
				+ " - CASE WHEN (PAYLISTENTRY.FHasTransferredAmount IS NULL) THEN 0 ELSE PAYLISTENTRY.FHasTransferredAmount END " 
				+ " - CASE WHEN (PAYLISTENTRY.FHasRefundmentAmount IS NULL) THEN 0 ELSE PAYLISTENTRY.FHasRefundmentAmount END " 
				+ " PAYLISTENTRYACTPAYAMOUNT, PAYLISTENTRY.FActRevDate PAYLISTENTRYACTPAYDATE,"); 
		shouldSql.append("(PAYLISTENTRY.FAppAmount -  (CASE WHEN (PAYLISTENTRY.FActRevAmount IS NULL) THEN 0 ELSE PAYLISTENTRY.FActRevAmount END " 
				+ " - CASE WHEN (PAYLISTENTRY.FHasTransferredAmount IS NULL) THEN 0 ELSE PAYLISTENTRY.FHasTransferredAmount END " 
				+ " - CASE WHEN (PAYLISTENTRY.FHasRefundmentAmount IS NULL) THEN 0 ELSE PAYLISTENTRY.FHasRefundmentAmount END )) ARREARAGEAMOUNT,");
		shouldSql.append("(CASE WHEN MONEYDEFINE.FMoneyType in ('"+MoneyTypeEnum.LOANAMOUNT_VALUE+"','"+MoneyTypeEnum.ACCFUNDAMOUNT_VALUE+"') THEN (PAYLISTENTRY.FAppAmount " 
				+ " -  CASE WHEN (PAYLISTENTRY.FActRevAmount IS NULL) THEN 0 ELSE PAYLISTENTRY.FActRevAmount END) ELSE 0 END) LOANARAGEAMOUNT,"); //增加按揭类的欠款
//		shouldSql.append("(CASE  WHEN (PAYLISTENTRY.FActRevDate IS NULL) or THEN NOW() ELSE PAYLISTENTRY.FActRevDate END ) ARREARAGETIME,");   //实付日期，若还未付，则默认当前时间
		if(nowTime) {   //逾期欠款 = 欠款>0 and 逾期时间 > 0天 (逾期欠款,已经到期尚未付清)、未逾期欠款,没有到应付日期的欠款
			shouldSql.append(" NOW() ARREARAGETIME,");   //取当前时间
		}else if(revTime) {   //逾期付清,曾经逾期现在已经付清的、按时付清,正常付款
//			shouldSql.append("(CASE  WHEN (PAYLISTENTRY.FActRevDate IS NULL) THEN NOW() ELSE PAYLISTENTRY.FActRevDate END ) ARREARAGETIME,");   //实付日期，若还未付，则默认当前时间
			shouldSql.append("PAYLISTENTRY.FActRevDate ARREARAGETIME,");   //实付日期，若还未付，则默认当前时间
		}
		shouldSql.append("PURCHASE.FPurchaseDate ToSignDate, SALESMAN.FName_l2 SALESMANNAME, ROOM.FSellState ROOMSELLSTATE, PAYLISTENTRY.FSeq PAYLISTENTRYSEQ ");
		shouldSql.append("FROM T_SHE_Room ROOM ");
		shouldSql.append("LEFT OUTER JOIN T_SHE_Purchase PURCHASE ON PURCHASE.FID = ROOM.FLastPurchaseID ");
		shouldSql.append("LEFT OUTER JOIN T_SHE_PurchasePayListEntry PAYLISTENTRY ON PURCHASE.FID = PAYLISTENTRY.FHeadID ");
		shouldSql.append("LEFT OUTER JOIN T_PM_User SALESMAN ON PURCHASE.FSalesmanID = SALESMAN.FID ");
		shouldSql.append("LEFT OUTER JOIN T_SHE_MoneyDefine MONEYDEFINE ON PAYLISTENTRY.FMoneyDefineID = MONEYDEFINE.FID ");
		shouldSql.append("LEFT OUTER JOIN T_SHE_RoomModel ROOMMODEL ON ROOM.FRoomModelID = ROOMMODEL.FID ");
		shouldSql.append("LEFT OUTER JOIN T_SHE_Building BUILDING ON ROOM.FBuildingID = BUILDING.FID ");
		shouldSql.append("LEFT OUTER JOIN T_FDC_ProductType PRODUCTTYPE ON ROOM.FProductTypeID = PRODUCTTYPE.FID ");
		shouldSql.append("LEFT OUTER JOIN T_SHE_SellProject SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID ");
		shouldSql.append("LEFT OUTER JOIN T_SHE_Subarea SUBAREA ON BUILDING.FSubareaID = SUBAREA.FID ");
		shouldSql.append("LEFT OUTER JOIN T_SHE_BuildingUnit BuildingUnit ON ROOM.FBuildUnitID = BuildingUnit.FID ");
		shouldSql.append("WHERE (PAYLISTENTRY.FAppDate IS NOT NULL) and ROOM.FHouseProperty = '" + HousePropertyEnum.NOATTACHMENT_VALUE + "' ");
		//排除认购状态为 ChangeRoomBlankOut 换房作废；QuitRoomBlankOut 退房作废；NoPayBlankOut 未付款作废； ManualBlankOut 手工作废;调整作废
		shouldSql.append(" AND PURCHASE.FPurchaseState NOT IN ('"+PurchaseStateEnum.CHANGEROOMBLANKOUT_VALUE+"', '"+PurchaseStateEnum.QUITROOMBLANKOUT_VALUE +"','"+PurchaseStateEnum.ADJUSTBLANKOUT_VALUE +"','"
							+PurchaseStateEnum.NOPAYBLANKOUT_VALUE+"','"+PurchaseStateEnum.MANUALBLANKOUT_VALUE+"') ");
		shouldSql.append(sonFilterSql.toString());
		
		elseSql.append("SELECT PURCHASE.FID ID, ROOM.FID ROOMID, SELLPROJECT.FName_l2 SELLPROJECTNAME, SUBAREA.FName_l2 SUBAREANAME, ");
		elseSql.append("BUILDING.FName_l2 BUILDINGNAME, BuildingUnit.Fname_l2 ROOMUNIT, ROOM.FDisplayName ROOMNUMBER, ");
		elseSql.append("PURCHASE.FCustomerNames CUSTOMERNAMES,PURCHASE.FCustomerPhones CUSTOMERPHONES, ROOMMODEL.FName_l2 ROOMMODELNAME, PRODUCTTYPE.FName_l2 PRODUCTTYPENAME,"); 
		elseSql.append("MONEYDEFINE.FName_l2 MONEYDEFINENAME, PAYLISTENTRY.FAppAmount PAYLISTENTRYAPAMOUNT, PAYLISTENTRY.FAppDate PAYLISTENTRYAPDATE,"); 
		elseSql.append("PAYLISTENTRY.FActRevAmount PAYLISTENTRYACTPAYAMOUNT, PAYLISTENTRY.FActRevDate PAYLISTENTRYACTPAYDATE, ");
		elseSql.append("(PAYLISTENTRY.FAppAmount - CASE WHEN (PAYLISTENTRY.FActRevAmount IS NULL) THEN 0 ELSE PAYLISTENTRY.FActRevAmount END) ARREARAGEAMOUNT, ");
		elseSql.append("0 LOANARAGEAMOUNT, "); //增加按揭类的欠款
//		elseSql.append("(CASE  WHEN (PAYLISTENTRY.FActRevDate IS NULL) THEN NOW() ELSE PAYLISTENTRY.FActRevDate END) ARREARAGETIME,");   //实付日期，若还未付，则默认当前时间
		if(nowTime) {   //逾期欠款 = 欠款>0 and 逾期时间 > 0天 (逾期欠款,已经到期尚未付清)、未逾期欠款,没有到应付日期的欠款
			elseSql.append(" NOW() ARREARAGETIME,");   //取当前时间
		}else if(revTime) {   //逾期付清,曾经逾期现在已经付清的、按时付清,正常付款
			elseSql.append("PAYLISTENTRY.FActRevDate ARREARAGETIME,");   //实付日期，若还未付，则默认当前时间
//			elseSql.append("(CASE  WHEN (PAYLISTENTRY.FActRevDate IS NULL) THEN NOW() ELSE PAYLISTENTRY.FActRevDate END ) ARREARAGETIME,");   //实付日期，若还未付，则默认当前时间
		}
		elseSql.append("PURCHASE.FPurchaseDate ToSignDate,SALESMAN.FName_l2 SALESMANNAME, ROOM.FSellState ROOMSELLSTATE, (100 + PAYLISTENTRY.FSeq) PAYLISTENTRYSEQ ");
		elseSql.append("FROM T_SHE_Room ROOM ");
		elseSql.append("LEFT OUTER JOIN T_SHE_Purchase PURCHASE ON PURCHASE.FID = ROOM.FLastPurchaseID ");
		elseSql.append("LEFT OUTER JOIN T_SHE_PurchaseElsePayListEntry PAYLISTENTRY ON PURCHASE.FID = PAYLISTENTRY.FHeadID ");
		elseSql.append("LEFT OUTER JOIN T_PM_User SALESMAN ON PURCHASE.FSalesmanID = SALESMAN.FID ");
		elseSql.append("LEFT OUTER JOIN T_SHE_MoneyDefine MONEYDEFINE ON PAYLISTENTRY.FMoneyDefineID = MONEYDEFINE.FID ");
		elseSql.append("LEFT OUTER JOIN T_SHE_RoomModel ROOMMODEL ON ROOM.FRoomModelID = ROOMMODEL.FID ");
		elseSql.append("LEFT OUTER JOIN T_SHE_Building BUILDING ON ROOM.FBuildingID = BUILDING.FID ");
		elseSql.append("LEFT OUTER JOIN T_FDC_ProductType PRODUCTTYPE ON ROOM.FProductTypeID = PRODUCTTYPE.FID ");
		elseSql.append("LEFT OUTER JOIN T_SHE_SellProject SELLPROJECT ON BUILDING.FSellProjectID = SELLPROJECT.FID ");
		elseSql.append("LEFT OUTER JOIN T_SHE_Subarea SUBAREA ON BUILDING.FSubareaID = SUBAREA.FID ");
		elseSql.append("LEFT OUTER JOIN T_SHE_BuildingUnit BuildingUnit ON ROOM.FBuildUnitID = BuildingUnit.FID ");		
		elseSql.append("WHERE (PAYLISTENTRY.FAppDate IS NOT NULL) and ROOM.FHouseProperty = '" + HousePropertyEnum.NOATTACHMENT_VALUE + "' ");
		//排除认购状态为 ChangeRoomBlankOut 换房作废；QuitRoomBlankOut 退房作废；NoPayBlankOut 未付款作废； ManualBlankOut 手工作废；调整作废
		elseSql.append(" AND PURCHASE.FPurchaseState NOT IN ('"+PurchaseStateEnum.CHANGEROOMBLANKOUT_VALUE+"', '"+PurchaseStateEnum.QUITROOMBLANKOUT_VALUE	+"','"+PurchaseStateEnum.ADJUSTBLANKOUT_VALUE +"','"
							+PurchaseStateEnum.NOPAYBLANKOUT_VALUE+"','"+PurchaseStateEnum.MANUALBLANKOUT_VALUE+"') ");
		elseSql.append(sonFilterSql.toString());
		
		if(textYuQiDayNum==0) 
			unionBuilder.append(shouldSql.toString()+" union "+elseSql.toString());
		else if(textYuQiDayNum==1)
			unionBuilder.append(shouldSql.toString());
		else if(textYuQiDayNum==2)
			unionBuilder.append(elseSql.toString());
    	
    	return null;
    } 
}