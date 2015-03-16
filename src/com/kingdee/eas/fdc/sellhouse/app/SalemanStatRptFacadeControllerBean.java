package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.SQLException;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.jdbc.rowset.IRowSet;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SalemanStatRptFacadeControllerBean extends AbstractSalemanStatRptFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.SalemanStatRptFacadeControllerBean");
    

	//款项类别:  预订金、定金、首期、楼款、按揭款、公积金、补差款
	private static final String  MoneyType="'"+ MoneyTypeEnum.FISRTAMOUNT_VALUE +"','" 
										+ MoneyTypeEnum.HOUSEAMOUNT_VALUE +"','"+MoneyTypeEnum.LOANAMOUNT_VALUE+"','"+
										MoneyTypeEnum.ACCFUNDAMOUNT_VALUE+"','" + MoneyTypeEnum.COMPENSATEAMOUNT_VALUE +"'" ;
	
	//已售房间指房间状态为预定、认购或签约的房间
	private static final String  SellState="'"+RoomSellStateEnum.PREPURCHASE_VALUE +"','"+ RoomSellStateEnum.PURCHASE_VALUE +"','" 
							+ RoomSellStateEnum.SIGN_VALUE+"'";
    protected Map _getSalemanStatData(Context ctx, Map dateMap)throws BOSException, EASBizException
    {
    	Map dataMap=new HashMap();
    	
    	Map purchaseMap=new HashMap();
    	
    	// 统计每个用户建立的客户数量
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select fsalesmanid,muSellPro.FHeadID FHeadID,count(*) cusCount  from T_SHE_FDCCustomer fdcCus").appendSql("\t\n");
		builder.appendSql("left join T_TEN_MarketingUnitSellProject muSellPro on fdcCus.FProjectID = muSellPro.FSellProjectID").appendSql("\t\n");
		builder.appendSql("left join T_SHE_AdapterLog ad on ad.FHeadID=fdcCus.fid").appendSql("\t\n");
		builder.appendSql("where (fdcCus.FSalesmanID=ad.FBeforeSellerID or ad.FBeforeSellerID is null) and  fdcCus.fisForSHE=1").appendSql("\t\n");
		this.appendFilterSql(builder, "fdcCus.fcreatetime",(Date)dateMap.get("beginDate"),(Date)dateMap.get("endDate"));
		builder.appendSql("group by fsalesmanid , muSellPro.FHeadID");
		
		IRowSet cusSet = builder.executeQuery(ctx);
		dataMap.put("cusCountSet", cusSet);
		
		//认购数,认购金额
		builder = new FDCSQLBuilder();
		builder.appendSql("select purchase.FSalesmanId,muSellPro.FHeadID,count(*) totalCount,").appendSql("\t\n");
		builder.appendSql("sum( case when purchase.FContractTotalAmount is null then 0 else purchase.FContractTotalAmount end) contractAmount ").appendSql("\t\n");
		builder.appendSql("from  t_she_purchase purchase ").appendSql("\t\n");
		builder.appendSql("inner join t_SHE_room room on room.FLastPurchaseID=purchase.fid").appendSql("\t\n");
		builder.appendSql("left join T_TEN_MarketingUnitSellProject muSellPro on purchase.FSellProjectID = muSellPro.FSellProjectID").appendSql("\t\n");
		builder.appendSql("where  room.FIsForSHE=1 ");
		if((Date)dateMap.get("beginDate")!=null || (Date)dateMap.get("endDate")!=null){
			this.appendFilterSql(builder, "purchase.FToPurchaseDate",(Date)dateMap.get("beginDate"),(Date)dateMap.get("endDate"));
			builder.appendSql("\t\n");
			builder.appendSql(" and  (purchase.FToSignDate is null) ").appendSql("\t\n");
//			this.appendNotFilterSql(builder, "(purchase.FToSignDate",(Date)dateMap.get("beginDate"),(Date)dateMap.get("endDate"));
//			builder.appendSql(")").appendSql("\t\n");
		}
		//显示全部签约数
		if((Date)dateMap.get("beginDate")==null && (Date)dateMap.get("endDate")==null){
			builder.appendSql(" and  (purchase.FToPurchaseDate is not null)  and  (purchase.FToSignDate is null)").appendSql("\t\n");
		}
		builder.appendSql("and purchase.FPurchaseState not in('NoPayBlankOut','ManualBlankOut','AdjustBlankOut')").appendSql("\t\n");
		builder.appendSql("and").appendSql("\t\n");
		builder.appendSql("purchase.fid not in(").appendSql("\t\n");
		builder.appendSql("select  FPurchaseID from T_SHE_QuitRoom quit where fstate='4AUDITTED' ").appendSql("\t\n");
		this.appendFilterSql(builder, "fauditTime",(Date)dateMap.get("beginDate"),(Date)dateMap.get("endDate"));
		builder.appendSql("union ").appendSql("\t\n");
		builder.appendSql("select convert(varchar,FoldPurchaseID)  from T_SHE_ChangeRoom   where fstate='4AUDITTED' ").appendSql("\t\n");
		this.appendFilterSql(builder, "fauditTime",(Date)dateMap.get("beginDate"),(Date)dateMap.get("endDate"));
		builder.appendSql(")").appendSql("\t\n");
		builder.appendSql("group by purchase.FSalesmanId,muSellPro.FHeadID").appendSql("\t\n");
		
		cusSet=builder.executeQuery(ctx);
		purchaseMap.put("allDate", cusSet);
		
		dataMap.put("signSet", purchaseMap);
//		//查询有效的认购单
//		builder.appendSql("select purchase.FSalesmanId,muSellPro.FHeadID,count(*) totalCount, ").appendSql("\t\n");
//		builder.appendSql("sum( case when purchase.FContractTotalAmount is null then 0 else purchase.FContractTotalAmount end) contractAmount ").appendSql("\t\n");
//		builder.appendSql("from  t_she_purchase purchase ").appendSql("\t\n");
//		builder.appendSql("inner join t_SHE_room room on room.FLastPurchaseID=purchase.fid").appendSql("\t\n");
//		builder.appendSql("left join T_TEN_MarketingUnitSellProject muSellPro on purchase.FSellProjectID = muSellPro.FSellProjectID").appendSql("\t\n");
//		builder.appendSql("where ROOM.fSellState in (").appendSql(SellState).appendSql(")and  room.FIsForSHE=1 ").appendSql("\t\n"); 
//		this.appendFilterSql(builder, "purchase.FPurchaseDate", (Date)dateMap.get("beginDate"),(Date)dateMap.get("endDate"));
//		builder.appendSql("group by purchase.FSalesmanId,muSellPro.FHeadID").appendSql("\t\n");
//		
//		cusSet=builder.executeQuery(ctx);
//		purchaseMap.put("allDate", cusSet);
//		
//		//1：退房单的认购单对应的认购或销售时间在指定范围内的做 + 操作
//		//   换房单的原认购单对应的认购或销售时间在指定范围内的做 + 操作
//		builder = new FDCSQLBuilder();
//		builder.appendSql("select purchase.FSalesmanId,muSellPro.FHeadID,count(*) totalCount,sum(purchase.FContractTotalAmount)  contractAmount	").appendSql("\t\n");
//		builder.appendSql("from  t_she_purchase purchase").appendSql("\t\n");
//		builder.appendSql("inner join t_SHE_room room   on room.FLastPurchaseID=purchase.fid").appendSql("\t\n");
//		builder.appendSql("inner join T_SHE_QuitRoom quit on  purchase.fid=quit.FPurchaseID").appendSql("\t\n");
//		builder.appendSql("left join T_TEN_MarketingUnitSellProject muSellPro on purchase.FSellProjectID = muSellPro.FSellProjectID").appendSql("\t\n");
//		builder.appendSql("where ROOM.fSellState in (").appendSql(SellState).appendSql(")and  room.FIsForSHE=1 ").appendSql("\t\n"); 
//		this.appendFilterSql(builder, "purchase.FToPurchaseDate",(Date)dateMap.get("beginDate"),(Date)dateMap.get("endDate"));
//		builder.appendSql("\t\n");
//		this.appendNotFilterSql(builder, "(purchase.FToSignDate",(Date)dateMap.get("beginDate"),(Date)dateMap.get("endDate"));
//		builder.appendSql("group by purchase.FSalesmanId,muSellPro.FHeadID").appendSql("\t\n");
//		builder.appendSql("union all").appendSql("\t\n");
//		builder.appendSql("select purchase.FSalesmanId,muSellPro.FHeadID,count(*) totalCount,sum(purchase.FContractTotalAmount)  contractAmount").appendSql("\t\n");
//		builder.appendSql("from  t_she_purchase purchase").appendSql("\t\n");
//		builder.appendSql("inner join T_SHE_ChangeRoom change on change.FoldPurchaseID=purchase.fid").appendSql("\t\n");
//		builder.appendSql("inner join t_SHE_room room on room.FLastPurchaseID=change.FoldPurchaseID").appendSql("\t\n");
//		builder.appendSql("left join T_TEN_MarketingUnitSellProject muSellPro on purchase.FSellProjectID = muSellPro.FSellProjectID").appendSql("\t\n");
//		builder.appendSql("where ROOM.fSellState in (").appendSql(SellState).appendSql(")and  room.FIsForSHE=1 ").appendSql("\t\n"); 
//		this.appendFilterSql(builder, "purchase.FToPurchaseDate",(Date)dateMap.get("beginDate"),(Date)dateMap.get("endDate"));
//		builder.appendSql("\t\n");
//		this.appendNotFilterSql(builder, "(purchase.FToSignDate",(Date)dateMap.get("beginDate"),(Date)dateMap.get("endDate"));
//		builder.appendSql("group by purchase.FSalesmanId,muSellPro.FHeadID").appendSql("\t\n");
//		cusSet=builder.executeQuery(ctx);
//		purchaseMap.put("addDate", cusSet);
//		//2.退房日期在指定范围内的做 - 操作 FQuitDate
//		//换房日期在指定范围内的做 - 操作 FChangeDate
//		builder = new FDCSQLBuilder();
//		builder.appendSql("select purchase.FSalesmanId,muSellPro.FHeadID,count(*) totalCount,sum(purchase.FContractTotalAmount)  contractAmount").appendSql("\t\n");
//		builder.appendSql("from  t_she_purchase purchase").appendSql("\t\n");
//		builder.appendSql("inner join T_SHE_QuitRoom quit on  purchase.fid=quit.FPurchaseID").appendSql("\t\n");
//		builder.appendSql("inner join t_SHE_room room on  room.FLastPurchaseID=purchase.fid").appendSql("\t\n");
//		builder.appendSql("left join T_TEN_MarketingUnitSellProject muSellPro on purchase.FSellProjectID = muSellPro.FSellProjectID").appendSql("\t\n");
//		builder.appendSql("where ROOM.fSellState in (").appendSql(SellState).appendSql(")and  room.FIsForSHE=1 ").appendSql("\t\n"); 
//		this.appendFilterSql(builder, "quit.FQuitDate",(Date)dateMap.get("beginDate"),(Date)dateMap.get("endDate"));
//		builder.appendSql("group by purchase.FSalesmanId,muSellPro.FHeadID").appendSql("\t\n");
//		builder.appendSql("union all").appendSql("\t\n");
//		builder.appendSql("select purchase.FSalesmanId,muSellPro.FHeadID,count(*) totalCount,sum(purchase.FContractTotalAmount)  contractAmount").appendSql("\t\n");
//		builder.appendSql("from  t_she_purchase purchase").appendSql("\t\n");
//		builder.appendSql("inner join T_SHE_ChangeRoom change on change.FoldPurchaseID=purchase.fid").appendSql("\t\n");
//		builder.appendSql("inner join t_SHE_room room on  room.FLastPurchaseID=purchase.fid").appendSql("\t\n");
//		builder.appendSql("left join T_TEN_MarketingUnitSellProject muSellPro on purchase.FSellProjectID = muSellPro.FSellProjectID").appendSql("\t\n");
//		builder.appendSql("where ROOM.fSellState in (").appendSql(SellState).appendSql(")and  room.FIsForSHE=1 ").appendSql("\t\n"); 
//		this.appendFilterSql(builder, "change.FChangeDate",(Date)dateMap.get("beginDate"),(Date)dateMap.get("endDate"));
//		builder.appendSql("group by purchase.FSalesmanId,muSellPro.FHeadID").appendSql("\t\n");
//		
//		cusSet = builder.executeQuery(ctx);
//		purchaseMap.put("muitDate", cusSet);
//		dataMap.put("signSet", purchaseMap);
		
		//合同数，合同金额
		builder = new FDCSQLBuilder();
		builder.appendSql("select purchase.FSalesmanId,muSellPro.FHeadID,count(*) totalCount,sum(FContractTotalAmount) contractAmount from  t_she_purchase purchase").appendSql("\t\n");
		builder.appendSql("inner join t_SHE_room room on room.FLastPurchaseID=purchase.fid").appendSql("\t\n");
		builder.appendSql("left join T_TEN_MarketingUnitSellProject muSellPro on purchase.FSellProjectID = muSellPro.FSellProjectID").appendSql("\t\n");
		builder.appendSql("where  room.FIsForSHE=1 ").appendSql("\t\n");
		//显示全部签约数
		if((Date)dateMap.get("beginDate")==null && (Date)dateMap.get("endDate")==null){
			builder.appendSql(" and  purchase.FToSignDate is not null").appendSql("\t\n");
		}
		this.appendFilterSql(builder, "purchase.FToSignDate",(Date)dateMap.get("beginDate"),(Date)dateMap.get("endDate"));
		builder.appendSql("and purchase.FPurchaseState not in('NoPayBlankOut','ManualBlankOut','AdjustBlankOut')").appendSql("\t\n");
		builder.appendSql("and").appendSql("\t\n");
		builder.appendSql("purchase.fid not in(").appendSql("\t\n");
		builder.appendSql("select  FPurchaseID from T_SHE_QuitRoom  where fstate='4AUDITTED' ").appendSql("\t\n");
		this.appendFilterSql(builder, "fauditTime",(Date)dateMap.get("beginDate"),(Date)dateMap.get("endDate"));
		builder.appendSql("union all ").appendSql("\t\n");
		builder.appendSql("select convert(varchar,FoldPurchaseID)  from T_SHE_ChangeRoom   where fstate='4AUDITTED' ").appendSql("\t\n");
		this.appendFilterSql(builder, "fauditTime",(Date)dateMap.get("beginDate"),(Date)dateMap.get("endDate"));
		builder.appendSql(")").appendSql("\t\n");
		builder.appendSql("group by purchase.FSalesmanId,muSellPro.FHeadID").appendSql("\t\n");

		
		cusSet = builder.executeQuery(ctx);
		dataMap.put("constractSet", cusSet);
		
		
		Map  amountMap=new HashMap();
		try {
			//回款额
			builder = new FDCSQLBuilder();
			builder.appendSql("select purchase.FSalesmanId,muSellPro.FHeadID,sum(billEntry.FRevAmount) sumRevAmount from	t_bdc_fdcReceivingBillEntry billEntry").appendSql("\t\n");
			builder.appendSql("left join t_bdc_fdcReceivingBill   fdc on billEntry.FheadID=fdc.fid ").appendSql("\t\n");
			builder.appendSql("left join t_she_room room on fdc.froomid=room.fid").appendSql("\t\n");
			builder.appendSql("left join T_SHE_Purchase as purchase on room.FLastPurchaseID= purchase.fid").appendSql("\t\n");
			builder.appendSql("left join T_TEN_MarketingUnitSellProject muSellPro on purchase.FSellProjectID = muSellPro.FSellProjectID ").appendSql("\t\n");
			builder.appendSql("left join t_she_moneyDefine money on billEntry.FMoneyDefineId=money.fid ").appendSql("\t\n");
			builder.appendSql("where fdc.FPurchaseObjID=purchase.fid ").appendSql("\t\n");
			builder.appendSql("and money.FMoneyType in ( ").appendSql(this.MoneyType).appendSql(")").appendSql("\t\n");
			builder.appendSql("and room.FIsForSHE = 1").appendSql("\t\n");
			this.appendFilterSql(builder, "fdc.FBizDate",(Date)dateMap.get("beginDate"),(Date)dateMap.get("endDate"));
			builder.appendSql("group by purchase.FSalesmanId,muSellPro.FHeadID").appendSql("\t\n");
			IRowSet termReceiveSet = builder.executeQuery(ctx);
			while (termReceiveSet.next()) {
				String buildingId = termReceiveSet.getString("FSalesmanid");
				String muSellProId = termReceiveSet.getString("FHeadID");
				BigDecimal sumRevAmount = termReceiveSet.getBigDecimal("sumRevAmount");//收款额
				amountMap.put(muSellProId+buildingId, sumRevAmount);
			}
			//定金的回款额
			builder = new FDCSQLBuilder();
			builder.appendSql("select purchase.FSalesmanId,muSellPro.FHeadID,sum(billEntry.FRevAmount) sumRevAmount from	t_bdc_fdcReceivingBillEntry billEntry").appendSql("\t\n");
			builder.appendSql("left join t_bdc_fdcReceivingBill   fdc on billEntry.FheadID=fdc.fid ").appendSql("\t\n");
			builder.appendSql("left join t_she_room room on fdc.froomid=room.fid").appendSql("\t\n");
			builder.appendSql("left join T_SHE_Purchase as purchase on room.FLastPurchaseID= purchase.fid").appendSql("\t\n");
			builder.appendSql("left join T_TEN_MarketingUnitSellProject muSellPro on purchase.FSellProjectID = muSellPro.FSellProjectID ").appendSql("\t\n");
			builder.appendSql("left join t_she_moneyDefine money on billEntry.FMoneyDefineId=money.fid ").appendSql("\t\n");
			builder.appendSql("where money.FMoneyType in ('PurchaseAmount')");
			builder.appendSql("and room.FIsForSHE = 1 and purchase.FisEarnestInHouseAmount=0").appendSql("\t\n");
			this.appendFilterSql(builder, "purchase.FBizDate",(Date)dateMap.get("beginDate"),(Date)dateMap.get("endDate"));
			builder.appendSql("group by purchase.FSalesmanId,muSellPro.FHeadID").appendSql("\t\n");
			termReceiveSet = builder.executeQuery(ctx);
			while (termReceiveSet.next()) {
				String buildingId = termReceiveSet.getString("FSalesmanid");
				String muSellProId = termReceiveSet.getString("FHeadID");
				BigDecimal sumRevAmount = termReceiveSet.getBigDecimal("sumRevAmount");//收款额
				String ollId=muSellProId+buildingId;
				if(amountMap.containsKey(ollId)){
					amountMap.put(ollId, ((BigDecimal)amountMap.get(ollId)).add(sumRevAmount));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dataMap.put("revSet", amountMap);
		
        return dataMap;
    }
    
    public void appendFilterSql(FDCSQLBuilder builder, String proName,Date beginDate,Date endDate ) {
		if (beginDate != null) {
			builder.appendSql(" and " + proName + ">= ? ");
			builder.addParam(FDCDateHelper.getSqlDate(beginDate));
		}
		if (endDate != null) {
			builder.appendSql(" and " + proName + "< ? ");
			builder.addParam(FDCDateHelper.getSqlDate(endDate));
		}
	}
	/**
	 * 不在过滤范围之内的时间
	 * @param builder
	 * @param proName
	 */
	public void appendNotFilterSql(FDCSQLBuilder builder, String proName,Date beginDate,Date endDate ) {
		if (beginDate != null) {
			
			builder.appendSql("(" + proName + "<= ? )");
			builder.addParam(FDCDateHelper.getSqlDate(beginDate));
		}
		if (endDate != null) {
			builder.appendSql(" or " + proName + "> ? ))");
			builder.addParam(FDCDateHelper.getSqlDate(endDate));
		}
	}
}