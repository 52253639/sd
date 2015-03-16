package com.kingdee.eas.fdc.sellhouse.app;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.tenancy.MarketingUnitFactory;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberCollection;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberFactory;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;

public class GatheringDetailFacadeControllerBean extends AbstractGatheringDetailFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.GatheringDetailFacadeControllerBean");
    protected Map _getAllData(Context ctx, Map param)throws BOSException, EASBizException
    {
    	Map map = new HashMap();
    	
    	//非转出
    	StringBuffer sb = getSqlHead(false);
    	sb.append(getSqlWhere(ctx,param));
    	sb.append(" order by customer.FID,room.FID,entry.FSettlementTypeID,entry.FRevAccountBankID,bill.FRevBillType \n");
    	//sb.append(" union \n");
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql(sb.toString());
    	IRowSet rowSet = builder.executeQuery(ctx);
    	logger.info(builder.getTestSql());
    	
    	map.put("map", rowSet);
    	
    	//转出
    	sb = getSqlHead(true);
    	sb.append(getSqlWhere(ctx,param));
    	sb.append("group by trans.FID \n ");
    	sb.append("order by customerID,roomID,settlementTypeID,RevAccountBankID,RevBillType \n");
    	builder = new FDCSQLBuilder(ctx);
    	builder.appendSql(sb.toString());
    	IRowSet rowSet2 = builder.executeQuery(ctx);
    	logger.info(builder.getTestSql());
    	
    	map.put("map2", rowSet2);
    	
    	
        return map;
    }
    
    private StringBuffer getSqlHead(boolean transfer){
    	StringBuffer sb = new StringBuffer();

    	if(transfer){
	    	sb.append(" select max(customer.FID) customerID ,max(room.FID) roomID, \n");
	    	sb.append("	 max(customer.Fname_l2) customerName,max(room.Fname_l2) roomName,max(SELLPROJECT.Fname_l2) sellProjectName,max(subarea.Fname_l2) subAreaName,\n");
	    	sb.append("	 max(building.Fname_l2) buidingName ,max(buildunit.Fname_l2) buildUnitName,max(room.FDisplayName) roomNumber, \n");
	    	sb.append("	 max(entry.FmoneyDefineId)  moneyDefineId, max(moneydefine.Fname_l2) moneydefineName,max(moneydefine.FMoneyType)  MoneyType, \n");
	    	sb.append("	 avg(trans.FAmount) revAmount , \n");
	    	sb.append("	 max(entry.FSettlementTypeID) settlementTypeID,max(settlementType.Fname_l2) settlementTypeName,max(settlementType.Fnumber) settlementTypeNumber,\n");
	    	sb.append("	 max(entry.FRevAccountBankID) RevAccountBankID ,max(accountbank.Fname_l2) accountbankName ,max(accountbank.FBankAccountNumber) accountbankNumber, \n");
	    	sb.append(" trans.FID as transferID , \n");
	    	sb.append("	 max(bill.FBillStatus) billStatus,max(bill.FrevBillType) revBillType \n");
    	}else{
	    	sb.append("select customer.FID customerID ,room.FID roomID, \n");
	    	sb.append("customer.Fname_l2 customerName,room.Fname_l2 roomName,SELLPROJECT.Fname_l2 sellProjectName,subarea.Fname_l2 subAreaName,\n");
	    	sb.append("building.Fname_l2 buidingName ,buildunit.Fname_l2 buildUnitName,room.FDisplayName roomNumber, \n");
	    	
	    	sb.append("entry.FmoneyDefineId  moneyDefineId, moneydefine.Fname_l2 moneydefineName,moneydefine.FMoneyType  MoneyType, \n");
	    	sb.append("entry.FrevAmount revAmount , \n");
	    	
	    	sb.append(" entry.FSettlementTypeID settlementTypeID,settlementType.Fname_l2 settlementTypeName,settlementType.Fnumber settlementTypeNumber,\n");
	    	sb.append(" entry.FRevAccountBankID RevAccountBankID  ,accountbank.Fname_l2 accountbankName ,accountbank.FBankAccountNumber accountbankNumber, \n");
	    	sb.append(" null as transferID , \n");
	    	sb.append("bill.FBillStatus billStatus,bill.FrevBillType revBillType \n");
    	}
    	if(transfer){
	    	sb.append(" from T_BDC_TransferSourceEntry trans\n");
	    	sb.append(" left join t_bdc_fdcreceivingbillentry entry on trans.Ffromrevlistid=entry.FrevlistID  and trans.Ffromrevlisttype=entry.FrevlistType \n");
    	}else{
    		sb.append(" from t_bdc_fdcreceivingbillentry  entry  \n");
    	}
    	sb.append("inner join t_bdc_fdcreceivingbill bill  on bill.FID =entry.FHeadID \n");

    	sb.append("left join  t_bd_customer customer on customer.FID= bill.FcustomerID \n");
    	sb.append("left join  T_SHE_FDCCustomer fdccustomer on customer.FID= fdccustomer.FSysCustomerID \n"); ///
    	sb.append(" left join t_she_room room on bill.FroomID = room.FID \n");
    	sb.append(" left join t_she_purchase purchase on  purchase.FID =bill.FpurchaseObjId \n");

    	sb.append("LEFT OUTER JOIN T_SHE_SellProject AS SELLPROJECT  \n");
    	sb.append("ON bill.FSellProjectID = SELLPROJECT.FID  \n");

    	sb.append("LEFT OUTER JOIN T_SHE_Building building \n");
    	sb.append("ON room.FBuildingID = building.FID  \n");
    	sb.append("LEFT OUTER JOIN T_SHE_BuildingUnit AS buildunit \n");
    	sb.append("ON room.FBuildUnitID = buildunit.FID  \n");
    	sb.append("LEFT OUTER JOIN T_SHE_Subarea AS subarea \n");
    	sb.append("ON building.FSubareaID = subarea.FID  \n");
    	
    	sb.append("LEFT OUTER JOIN T_BDC_TransferSourceEntry AS transfer \n");
    	sb.append("ON  entry.FID = transfer.FHeadId  \n");
    	
    	 
		sb.append(" left join t_she_moneyDefine moneydefine on entry.FMoneyDefineID = moneydefine.FID\n");
		sb.append(" left join T_BD_SettlementType settlementType on settlementType.FID = entry.FSettlementTypeID \n");
		sb.append(" left join T_BD_AccountBanks accountbank on accountbank.FID = entry.FRevAccountBankID \n");
		
		
		return sb;
    }
    
    
    
    
    private StringBuffer getSqlWhere(Context ctx,Map params){
    	StringBuffer sb = new StringBuffer();
// moneydefine.FMoneyType='PreMoney' and 
    	sb.append("  where customer.FID is not null \n");
    	String customerstr = getCustomerSqlStr(ctx);
    	if(customerstr!=null && customerstr.length()>0){
    		sb.append(" and fdccustomer.FID in ("+customerstr+" )");
    	}
    	sb.append("  and bill.FrevBillType in ('gathering','refundment','transfer') \n");
		
		boolean includehistory =false;
		if((Boolean)params.get("includeHistoey")!=null){
			includehistory = ((Boolean)params.get("includeHistoey")).booleanValue();
		}
		
		
		if(!includehistory){
			sb.append(" and (( bill.FpurchaseObjId is not null \n");
			sb.append(" and room.FLastPurchaseID =bill.FpurchaseObjId ) or (bill.FSincerityObjID is not null and moneyDefine.FmoneyType ='"+MoneyTypeEnum.PREMONEY_VALUE+"') )  \n");
			sb.append(" and room.FsellState  in ('"
					+ RoomSellStateEnum.PREPURCHASE_VALUE + "','"
					+ RoomSellStateEnum.PURCHASE_VALUE + "','"
					+ RoomSellStateEnum.SIGN_VALUE + "')  \n");
			//sb.append(" ) or (bill.FSincerityObjID is not null and room.FLastPurchaseID =purchase.FId and   purchase.FSincerityPurchaseID =bill.FSincerityObjID  and room.FsellState ='"+RoomSellStateEnum.PREPURCHASE_VALUE+"' ))\n");
			//sb.append(" ) or (bill.FSincerityObjID is not null and room.FLastPurchaseID =purchase.FId and   purchase.FSincerityPurchaseID =bill.FSincerityObjID  and room.FsellState ='"+RoomSellStateEnum.PREPURCHASE_VALUE+"' ))\n");
		}
		
		Date from = (Date)params.get("dateFrom");
    	Date to = (Date)params.get("dateTo");
    	
    	String buildingIds = (String)params.get("allBuildingIds");
    	String sellProjectIds = (String)params.get("allSellProjectIds");
		
    	
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	sb.append(" and bill.FbizDate>={TS'"+sdf.format(from)+"'}");
    	sb.append(" and bill.FbizDate<{TS'"+sdf.format(to)+"'}");
    	
         if( sellProjectIds != null && sellProjectIds.length()>0){
    		sb.append("and SELLPROJECT.FID in ("+sellProjectIds+")");
    	}else{
	    	if(buildingIds!=null && buildingIds.length()>0){
	    		sb.append(" and room.FBuildingID in ( "+buildingIds+" ) ");
	    	}
    	}
    	int  unitNumber = ((Integer)params.get("unitNumber")).intValue();
    	if(unitNumber != 0){
    		sb.append(" and buildunit.Fseq = "+unitNumber+" ");
    	}
    	
    	Set set = (Set)params.get("moneydefine");
		if(set!=null && set.size()>0){ 
			sb.append(" and moneyDefine.FmoneyType in ("+allMoneydefine(set)+")"); ///至少选一种类别
			sb.append(" and moneyDefine.FsysType ='"+MoneySysTypeEnum.SALEHOUSESYS_VALUE+"'"); 
			//sb.append(" and moneyDefine.FmoneyType in ('"+MoneyTypeEnum.PREMONEY_VALUE+"','"+MoneyTypeEnum.EARNESTMONEY_VALUE+"')");
		}else{
			sb.append(" and 1>2");
		}
		//TODO 测试 505
		//sb.append(" and room.Fnumber='505' and customer.Fname_l2='流流' ");
		//sb.append(" and room.Fnumber='205' and customer.Fname_l2='王四' ");
		//
		//sb.append(" order by customer.FID,room.FID,entry.FSettlementTypeID,entry.FRevAccountBankID,bill.FRevBillType \n");
		
		
		return sb;
    }
    

    
    private String allMoneydefine(Set set){
    	StringBuffer sb = new StringBuffer();
    	if(set!=null && set.size()>0){
    		Iterator  it = set.iterator();
    		while(it.hasNext()){
    			String value = (String)it.next();
    			sb.append(",'"+value+"'");
    		}
    		sb.delete(0, 1);
    	}
    	
    	return sb.toString();
    }
   
    
	public  String getPermitShareCustomerIdStr(Context  ctx) {
		String userId = "null";
		
		UserInfo userInfo = ContextUtil.getCurrentUserInfo(ctx);
		
		if(userInfo!=null)	userId = userInfo.getId().toString();
		//T_SHE_ShareSeller	FHeadID	FSellerID	FMarketingUnitID FOrgUnitID
		String idSqlString = "select fheadId from T_SHE_ShareSeller where FSellerID = '"+userId+"'";
		String muIdStr = "";
		try {
			Set muIdSet = getMuIdSetBySaleMan(ctx);
			if(muIdSet.size()>0)
				muIdStr = getStringFromSet(muIdSet);
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		if(!muIdStr.equals(""))
			idSqlString += " union select fheadId from T_SHE_ShareSeller where FMarketingUnitID in ("+muIdStr+")  ";
		SaleOrgUnitInfo saleUnitInfo = SysContext.getSysContext().getCurrentSaleUnit();
		if(saleUnitInfo!=null && saleUnitInfo.isIsBizUnit()) {
			idSqlString += " union select fheadId from T_SHE_ShareSeller where FOrgUnitID ='"+saleUnitInfo.getId().toString()+"'  ";
		}
		return idSqlString;
	}
	
	/**
	 * 获得指定营销顾问所能看到的营销单元
	 */
	private static Set getMuIdSetBySaleMan(Context  ctx) throws BOSException , SQLException {		
		SaleOrgUnitInfo saleOrgUnit = ContextUtil.getCurrentSaleUnit(ctx);
		UserInfo userInfo = ContextUtil.getCurrentUserInfo(ctx);
		MarketingUnitMemberCollection muMemColl = MarketingUnitMemberFactory.getLocalInstance(ctx).getMarketingUnitMemberCollection(
				"select id where (head.orgUnit.number = '"+ saleOrgUnit.getNumber() +"' and head.orgUnit.longNumber like '"+saleOrgUnit.getLongNumber()+"!%') " +
						"and member.id = '"+userInfo.getId().toString()+"' ");
		Set idSet = new HashSet();
		for(int i=0;i<muMemColl.size();i++)
			idSet.add(muMemColl.get(i).getHead().getId().toString());
		return idSet;
	}
	
	
	protected  String getStringFromSet(Set srcSet){
		String retStr = "";
		if(srcSet==null || srcSet.size()==0) return retStr;
		Iterator iter = srcSet.iterator();
		while(iter.hasNext()){
			Object obj = iter.next();
			if(obj instanceof String) retStr += ",'" + (String)obj + "'";
		}
		if(!retStr.equals("")) retStr = retStr.replaceFirst(",", "");
		return retStr;
	}	
	
	private String getCustomerSqlStr(Context  ctx){
		StringBuffer sb = new StringBuffer();
    		
    		UserInfo userInfo = ContextUtil.getCurrentUserInfo(ctx);
    		try {
    		sb.append("SELECT \n"); 

    		sb.append("DISTINCT FDCCUSTOMER.FID AS ID \n"); 
    		sb.append("FROM T_SHE_FDCCustomer AS FDCCUSTOMER \n"); 
    		sb.append("LEFT OUTER JOIN T_PM_User AS SALESMAN \n"); 
    		sb.append("ON FDCCUSTOMER.FSalesmanID = SALESMAN.FID \n"); 

    		sb.append("WHERE FDCCUSTOMER.FisEnabled = 1 \n"); 
    		sb.append("AND FDCCUSTOMER.FisForSHE = 1 \n"); 
				sb.append(" AND (SALESMAN.fid IN ("+MarketingUnitFactory.getLocalInstance(ctx).getPermitSaleManIdSql(userInfo)+" )\n");
			
    		sb.append("OR \n");
    		sb.append("FDCCUSTOMER.Fid  in ("+getPermitShareCustomerIdStr(ctx)+")\n");
    		sb.append(" )\n");
    		} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		return sb.toString();
	}
	
	
	
	
	
}