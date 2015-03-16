package com.kingdee.eas.fdc.finance;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum;
import com.kingdee.eas.fdc.basedata.CostSplitBillTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;

public class FDCBudgetAcctHelper {
	/**
	 * 取一个项目下的所有已申请金额做为报表的已付款金额
	 * @param prjId 项目ID
	 * @param period 期间ID
	 * @return　以科目＋合同为key,申请金额累计为value的Map
	 * @throws BOSException
	 */
	public static Map getToPeriodRequestedAmt(Context ctx,String prjId,FDCBudgetPeriodInfo period)throws BOSException{
		Map requestedMap=new HashMap();
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select fcostaccountid,acctPay.fcontractid as fcontractid,sum(acctPay.famount) requestAmt from T_FNC_PayRequestAcctPay acctPay ");
		builder.appendSql("inner join T_CON_PayRequestBill payReq on payReq.fid=acctPay.fpayRequestBillId ");
		builder.appendSql("inner join T_FNC_FDCBudgetPeriod period on period.fid=acctPay.fPeriodId ");
		builder.appendSql("where acctPay.fprojectId=? and payReq.fcurProjectId=? and payReq.fstate=? and ((period.fyear=? and period.fmonth<=?) or (period.fyear<?)) ");
		builder.appendSql("group by fcostaccountid,acctPay.fcontractid");
		builder.addParam(prjId);
		builder.addParam(prjId);
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		builder.addParam(new Integer(period.getYear()));
		builder.addParam(new Integer(period.getMonth()));
		builder.addParam(new Integer(period.getYear()));
		IRowSet rowSet=builder.executeQuery();
		try {
			while (rowSet.next()) {
				requestedMap.put(rowSet.getString("fcostaccountid")+rowSet.getString("fcontractid"), FDCHelper.toBigDecimal(rowSet.getBigDecimal("requestAmt")));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return requestedMap;
	}
	
	/**
	 * 取一个项目下的本年度已申请金额做为报表的已付款金额
	 * @param prjId 项目ID
	 * @param period 期间ID
	 * @return　以科目＋合同为key,申请金额累计为value的Map
	 * @throws BOSException
	 */
	public static Map getYearRequestedAmt(Context ctx,String prjId,FDCBudgetPeriodInfo period)throws BOSException{
		Map requestedMap=new HashMap();
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select fcostaccountid,acctPay.fcontractid as fcontractid,sum(acctPay.famount) requestAmt from T_FNC_PayRequestAcctPay acctPay ");
		builder.appendSql("inner join T_CON_PayRequestBill payReq on payReq.fid=acctPay.fpayRequestBillId ");
		builder.appendSql("inner join T_FNC_FDCBudgetPeriod period on period.fid=acctPay.fPeriodId ");
		builder.appendSql("where acctPay.fprojectId=? and payReq.fcurProjectId=? and payReq.fstate=? and period.fyear=? and period.fmonth<=? ");
		builder.appendSql("group by fcostaccountid,acctPay.fcontractid");
		builder.addParam(prjId);
		builder.addParam(prjId);
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		builder.addParam(new Integer(period.getYear()));
		builder.addParam(new Integer(period.getMonth()));
		IRowSet rowSet=builder.executeQuery();
		try {
			while (rowSet.next()) {
				requestedMap.put(rowSet.getString("fcostaccountid")+rowSet.getString("fcontractid"), FDCHelper.toBigDecimal(rowSet.getBigDecimal("requestAmt")));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return requestedMap;
	}
	
	/**
	 * 截止上年末累计发生-付款
	 * @param prjId 项目ID
	 * @param period 期间ID
	 * @return　以科目＋合同为key,申请金额累计为value的Map
	 * @throws BOSException
	 */
	public static Map getLastYearRequestedAmt(Context ctx,String prjId,FDCBudgetPeriodInfo period)throws BOSException{
		Map requestedMap=new HashMap();
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select fcostaccountid,acctPay.fcontractid as fcontractid,sum(acctPay.famount) requestAmt from T_FNC_PayRequestAcctPay acctPay ");
		builder.appendSql("inner join T_CON_PayRequestBill payReq on payReq.fid=acctPay.fpayRequestBillId ");
		builder.appendSql("inner join T_FNC_FDCBudgetPeriod period on period.fid=acctPay.fPeriodId ");
		builder.appendSql("where acctPay.fprojectId=? and payReq.fcurProjectId=? and payReq.fstate=? and period.fyear<?");
		builder.appendSql("group by fcostaccountid,acctPay.fcontractid");
		builder.addParam(prjId);
		builder.addParam(prjId);
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		builder.addParam(new Integer(period.getYear()));
		IRowSet rowSet=builder.executeQuery();
		try {
			while (rowSet.next()) {
				requestedMap.put(rowSet.getString("fcostaccountid")+rowSet.getString("fcontractid"), FDCHelper.toBigDecimal(rowSet.getBigDecimal("requestAmt")));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return requestedMap;
	}
	
	/**
	 * 取一个项目下的当期已申请金额做为报表的已付款金额
	 * @param prjId 项目ID
	 * @param period 期间ID
	 * @return　以科目＋合同为key,申请金额为value的Map
	 * @throws BOSException
	 */
	public static Map getCurPeriodRequestedAmt(Context ctx,String prjId,FDCBudgetPeriodInfo period)throws BOSException{
		Map requestedMap=new HashMap();
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select fcostaccountid,acctPay.fcontractid as fcontractid,sum(acctPay.famount) requestAmt from T_FNC_PayRequestAcctPay acctPay ");
		builder.appendSql("inner join T_FDC_CostAccount acct on acct.fid=acctPay.fcostaccountid ");
		builder.appendSql("inner join T_Con_PayRequestbill payReq on payReq.fid=acctPay.fpayrequestbillid ");
		builder.appendSql("inner join T_FNC_FDCBudgetPeriod period on period.fid=acctPay.fPeriodId ");
		builder.appendSql("where acct.fcurProject=? and payReq.fstate=? and period.fyear=? and period.fmonth=? ");
		builder.appendSql("group by fcostaccountid,acctPay.fcontractid");
		builder.addParam(prjId);
//		builder.addParam(prjId);
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		builder.addParam(new Integer(period.getYear()));
		builder.addParam(new Integer(period.getMonth()));
		IRowSet rowSet=builder.executeQuery();
		try {
			while (rowSet.next()) {
				requestedMap.put(rowSet.getString("fcostaccountid")+rowSet.getString("fcontractid"), FDCHelper.toBigDecimal(rowSet.getBigDecimal("requestAmt")));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return requestedMap;
	}
	

	/**累计发生-成本：当期的已实现产值,如果是服务器所在月或者之后则取最新的数据
	 * @param ctx
	 * @param prjId
	 * @param period
	 * @return
	 * @throws BOSException 
	 */
	public static Map getToPeriodCost(Context ctx,String prjId,FDCBudgetPeriodInfo period) throws BOSException{
		Map map=new HashMap();
		Date serverDate=new Date();
		Date date=period.toDate();
		if(date.after(serverDate)||(date.getYear()==serverDate.getYear()&&date.getMonth()==serverDate.getMonth())){
			//取最新的已实现产值
			try {
				return getNewRealizedValue(ctx, prjId);
			} catch (Exception e) {
				throw new BOSException(e);
			}
		}else{
			date=getMaxSnapShotDate(ctx, prjId, FDCDateHelper.getFirstYearDate(1900), FDCDateHelper.getLastDayOfMonth2(date));
		}
		map=getCost(prjId, date, ctx);
		return map;
	}

	/**本年实际累计-成本：当前期的已实现产值-上年的已实现产值（去年１２月份的）,待签订行无数据
	 * @param ctx
	 * @param prjId
	 * @param period
	 * @return
	 * @throws BOSException 
	 */
	public static Map getYearCost(Context ctx,String prjId,FDCBudgetPeriodInfo period) throws BOSException{
		Map toPeriodMap=getToPeriodCost(ctx, prjId, period);
		Map map = getLastYearCost(ctx, prjId, period);
		return FDCHelper.getDifMap(toPeriodMap, map);
	}

	/**
	 * 截止到去年的成本
	 * @param ctx
	 * @param prjId
	 * @param period
	 * @return
	 * @throws BOSException
	 */
	public static Map getLastYearCost(Context ctx, String prjId, FDCBudgetPeriodInfo period) throws BOSException {
		Map map=new HashMap();
		//去年最新成本
		Date date=getMaxSnapShotDate(ctx, prjId, FDCDateHelper.getFirstYearDate(1900), FDCDateHelper.getLastYearDate(period.getYear()-1));
		if(date==null){
			return map;
		}
		map=getCost(prjId, date,ctx);
		return map;
	}
	
	public static Date getMaxSnapShotDate(Context ctx, String prjId, Date from,Date to) throws BOSException {
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select max(FSnapShotDate) lstYearDate from T_AIM_DynCostSnapShot where FProjectId=? and FSnapShotDate>=? and FSnapShotDate<=? and FSavedType=?");
		builder.addParam(prjId);
		builder.addParam(from);
		builder.addParam(to);
		builder.addParam(CostMonthlySaveTypeEnum.AUTOSAVE_VALUE);
		IRowSet rowSet=builder.executeQuery();
		if (rowSet.size() == 1) {
			try {
				rowSet.next();
				return rowSet.getDate("lstYearDate");
			} catch (SQLException e) {
				throw new BOSException(e);
			}
		}
		return null;
	}
	/**
	 * 成本取数
	 * @param prjId
	 * @param snapShotDate
	 * @param ctx
	 * @return
	 * @throws BOSException
	 */
	private static Map getCost(String prjId, Date snapShotDate,Context ctx) throws BOSException {
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		Map map =new HashMap();
		builder.appendSql("select fcostaccountid,FRealizedValue from T_AIM_DynCostSnapShot where FProjectId=? and FSnapShotDate=? and FSavedType=?");
		builder.addParam(prjId);
		
		if (snapShotDate == null) 
			//有的工程项目在T_AIM_DynCostSnapShot没有记录，即snapShotDate为null，此时故意传入一个无效的日期1900-01-01
			builder.addParam(FDCDateHelper.getFirstYearDate(1900));
		else
			builder.addParam(snapShotDate);
		
		builder.addParam(CostMonthlySaveTypeEnum.AUTOSAVE_VALUE);
		IRowSet rowSet=builder.executeQuery();
		try {
			while (rowSet.next()) {
				String costAccountId = rowSet.getString("fcostaccountid");
				BigDecimal cost=rowSet.getBigDecimal("FRealizedValue");
				map.put(costAccountId, cost);
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		return map;
	}
	/**
	 * 当期成本：本月的已实现产值-上月的已实现产值
	 * @param ctx
	 * @param prjId
	 * @param period
	 * @return
	 * @throws BOSException 
	 */
	public static Map getCurPeriodCost(Context ctx,String prjId,FDCBudgetPeriodInfo period) throws BOSException{
		Map toPeriodMap=getToPeriodCost(ctx, prjId, period);
		Map map = getLastPeriodCost(ctx, prjId, period);
		return FDCHelper.getDifMap(toPeriodMap, map);
	}

	/**
	 * 上期成本,取上期或之前期的数据
	 * @param ctx
	 * @param prjId
	 * @param period
	 * @return
	 * @throws BOSException
	 */
	public static Map getLastPeriodCost(Context ctx, String prjId, FDCBudgetPeriodInfo period) throws BOSException {
		Map map=new HashMap();
		//上个月
		Date date=period.toDate();
		date=FDCDateHelper.getPreMonthMaxDate(date);
		date=getMaxSnapShotDate(ctx, prjId, FDCDateHelper.getFirstYearDate(1900), date);
		map=getCost(prjId, date, ctx);
		return map;
	}
	private static Map getNewRealizedValue(Context ctx,String prjId) throws Exception{
		Map map=new HashMap();
		//使用一个sql，一次取数
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select FCostAccountId,sum(entry.FAmount) amount from T_AIM_CostSplitEntry entry  \n");
		builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n");
		builder.appendSql("inner join T_CON_ContractSettlementBill settle on head.FCostBillID=settle.FID  \n");
		builder.appendSql("where FCostBillType='SETTLEMENTSPLIT' and head.fcontrolUnitId in (select fcontrolUnitId from T_FDC_CurProject where fid=entry.fobjectid) and head.FIsInvalid=0 And  entry.fobjectid=? and  ((settle.FIsSettled=1 and settle.FIsFinalSettle=1) or (settle.FIsSettled=0 and settle.FIsFinalSettle=0))  \n");
		builder.appendSql("group by fcostaccountid\n");
//		builder.addParam(prjId);
		builder.addParam(prjId);
		IRowSet rowSet=builder.executeQuery();
		while(rowSet.next()){
			map.put(rowSet.getString("fcostAccountid"), rowSet.getBigDecimal("amount"));
		}
		return map;
	}
	/**
	 * 合同下所有变更指令单拆到此科目的合计金额 by Cassiel_peng  2008-9-2
	 */
	public static Map getAmtWithContractChange(Context ctx,String prjId) throws BOSException,SQLException{
		//TODO 1.变更上有合同不需要再关联合同 2.根据合同分组了可以直接以合同+科目为key 其它几个方法类似存在此问题 by hpw 2010.08.11
		Map conChangeSplitAmtMap=new HashMap();
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select con.fid fcontractbillid,FCostAccountId,sum(entry.FAmount) amount \n");
		builder.appendSql("from T_AIM_CostSplitEntry entry   \n ");
		builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n ");
		builder.appendSql("inner join T_CON_ContractChangeBill change on head.FCostBillID=change.FID \n ");
		builder.appendSql("inner join T_CON_ContractBill con on change.FContractBillID=con.FID   \n ");
		builder.appendSql("where FCostBillType='CNTRCHANGESPLIT' and head.FIsInvalid=0 And  entry.fobjectid=?  \n ");
		builder.appendSql(" and entry.fcostaccountid in (select fid from T_fdc_costAccount where fcurProject=? and fisleaf=1) \n ");
		builder.appendSql("group by con.fid,entry.fcostaccountid \n ");
		builder.addParam(prjId);
		builder.addParam(prjId);
		IRowSet rowSet=builder.executeQuery();
		while(rowSet.next()){
			String costaccountId=rowSet.getString("Fcostaccountid");
			String contractId=rowSet.getString("fcontractbillid");
			if(conChangeSplitAmtMap.containsKey(costaccountId)){
				Map contractMap = (Map)conChangeSplitAmtMap.get(costaccountId);// Key:合同Id     Value:Id为Key的合同的结算拆分
				contractMap.put(contractId, rowSet.getBigDecimal("amount"));
				conChangeSplitAmtMap.put(costaccountId, contractMap);
			}else{
				Map contractMap=new HashMap();
				contractMap.put(contractId, rowSet.getBigDecimal("amount"));
				conChangeSplitAmtMap.put(costaccountId,contractMap);
			}
		}
		return conChangeSplitAmtMap;
	}
	/**
	 * 合同未最终结算，则取合同下所有结算单拆分到科目的合计金额；
	 * 已最终结算，则取最终结算单拆分到科目的金额  by Cassiel_peng
	 */
	public static Map getPayedAmtWithSettlement(Context ctx,String prjId) throws BOSException,SQLException{
		Map settlementSplitAmtMap=new HashMap();// Key:成本科目Id     Value:某一个成本科目下相关合同的信息 的Map-  
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select FCostAccountId,sum(entry.FAmount) amount,settle.fcontractbillid fcontractbillid from T_AIM_CostSplitEntry entry  \n");
		builder.appendSql("inner join T_AIM_CostSplit head on entry.FParentId=head.FId  \n");
		builder.appendSql("inner join T_CON_ContractSettlementBill settle on head.FCostBillID=settle.FID  \n");
		builder.appendSql("where FCostBillType='SETTLEMENTSPLIT' and head.fcontrolUnitId in (select fcontrolUnitId from T_FDC_CurProject where fid=entry.fobjectid) and head.FIsInvalid=0 And  entry.fobjectid=? and  ((settle.FIsSettled=1 and settle.FIsFinalSettle=1) or (settle.FIsSettled=0 and settle.FIsFinalSettle=0))  \n");
		builder.appendSql("group by fcostaccountid,fcontractbillid\n");//按成本科目与合同科目组合分组
		builder.addParam(prjId);
		IRowSet rowSet=builder.executeQuery();
		while(rowSet.next()){
			String costaccountId=rowSet.getString("Fcostaccountid");
			String contractId=rowSet.getString("fcontractbillid");
			if(settlementSplitAmtMap.containsKey(costaccountId)){
				Map contractMap = (Map)settlementSplitAmtMap.get(costaccountId);// Key:合同Id     Value:Id为Key的合同的结算拆分
				contractMap.put(contractId, rowSet.getBigDecimal("amount"));
				settlementSplitAmtMap.put(costaccountId, contractMap);
			}else{
				Map contractMap=new HashMap();
				contractMap.put(contractId, rowSet.getBigDecimal("amount"));
				settlementSplitAmtMap.put(costaccountId,contractMap);
			}
		}
		return settlementSplitAmtMap;
	}
	
	/**
	 * 已实现成本
	 */
	public static Map getPayedAmtWithCost(Context ctx,String prjId) throws BOSException,SQLException{
		Map paymentSplitAmtMap=new HashMap();// Key:成本科目Id     Value:某一个成本科目下相关合同的信息 的Map-  
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select fcontractbillid,Fcostaccountid,sum(famount) as amount from ( \n ");
		
		//合同，无文本合同
		builder.appendSql("select pay.fcontractbillid as fcontractbillid,costSplitEntry.Fcostaccountid,sum(costsplitentry.famount) as famount \n ");
		builder.appendSql("from T_AIM_CostSplit costsplit \n ");
		builder.appendSql("inner join T_AIM_CostSplitEntry costSplitEntry on costSplitEntry.fparentid=costsplit.fid \n  ");
		builder.appendSql("inner join T_FNC_PaymentSplit split on split.fid= costsplit.fsplitbillid and split.fisworkloadbill=0 \n ");
		builder.appendSql("inner join T_CAS_PaymentBill pay on pay.fid=split.fpaymentbillid \n");
		builder.appendSql("where costsplitEntry.fobjectid=? and costsplitEntry.Fisproduct=0 and \n ");
		builder.appendSql("costsplit.fisinvalid=0 and costsplit.fcostbilltype=? \n ");
		builder.appendSql("and costsplit.fcontrolunitid in (select fcontrolunitid from T_fdc_curProject where fid=?) \n  ");
		builder.appendSql("group by pay.fcontractbillid,costSplitEntry.Fcostaccountid \n");
		builder.addParam(prjId);
		builder.addParam(CostSplitBillTypeEnum.PAYMENTSPLIT_VALUE);
		builder.addParam(prjId);
		
		//工程量
		builder.appendSql(" union all \n");
		builder.appendSql("select split.fcontractbillid as fcontractbillid,costSplitEntry.Fcostaccountid,sum(costsplitentry.famount) as famount \n ");
		builder.appendSql("from T_AIM_CostSplit costsplit \n ");
		builder.appendSql("inner join T_AIM_CostSplitEntry costSplitEntry on costSplitEntry.fparentid=costsplit.fid \n  ");
		builder.appendSql("inner join T_FNC_PaymentSplit split on split.fid= costsplit.fsplitbillid and split.fisworkloadbill=1 \n ");
		builder.appendSql("where costsplitEntry.fobjectid=? and costsplitEntry.Fisproduct=0 and \n ");
		builder.appendSql("costsplit.fisinvalid=0 and costsplit.fcostbilltype=? \n ");
		builder.appendSql("and costsplit.fcontrolunitid in (select fcontrolunitid from T_fdc_curProject where fid=?) \n  ");
		builder.appendSql("group by split.fcontractbillid,costSplitEntry.Fcostaccountid \n");
		builder.addParam(prjId);
		builder.addParam(CostSplitBillTypeEnum.PAYMENTSPLIT_VALUE);
		builder.addParam(prjId);
		builder.appendSql(")t group by fcontractbillid,fcostaccountid \n");
		IRowSet rowSet=builder.executeQuery();
		
		while(rowSet.next()){
			String costaccountId=rowSet.getString("Fcostaccountid");
			String contractId=rowSet.getString("fcontractbillid");
			if(paymentSplitAmtMap.containsKey(costaccountId)){
				Map contractMap=(Map)paymentSplitAmtMap.get(costaccountId);
				contractMap.put(contractId, rowSet.getBigDecimal("amount"));
				paymentSplitAmtMap.put(costaccountId, contractMap);
			}else{
				Map contractMap=new HashMap();// Key:合同Id     Value:合同Id为Key的付款拆分
				contractMap.put(contractId, rowSet.getBigDecimal("amount"));
				paymentSplitAmtMap.put(costaccountId,contractMap);
			}
		}
		return paymentSplitAmtMap;
	}
	/**
	 * 取得付款拆分的已付款数据 by Cassiel_peng
	 */
	public static Map getPayedAmtWithPayment(Context ctx,String prjId) throws BOSException,SQLException{
		Map paymentSplitAmtMap=new HashMap();// Key:成本科目Id     Value:某一个成本科目下相关合同的信息 的Map-  
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select payment.fcontractbillid,costSplitEntry.Fcostaccountid,sum(costsplitentry.fpaidamount) as amount \n ");
		builder.appendSql("from T_AIM_CostSplit costsplit \n ");
		builder.appendSql("inner join T_AIM_CostSplitEntry costSplitEntry on costSplitEntry.fparentid=costsplit.fid \n  ");
		builder.appendSql("inner join T_CAS_Paymentbill payment on payment.fid= costsplit.Fcostbillid  \n ");
		builder.appendSql("where costsplitEntry.fobjectid=? and costsplitEntry.Fisproduct=0 and \n ");
		builder.appendSql("costsplit.fisinvalid=0 and costsplit.fcostbilltype=? \n ");
		builder.appendSql("and costsplit.fcontrolunitid in (select fcontrolunitid from T_fdc_curProject where fid=?) \n  ");
		builder.appendSql("group by payment.fcontractbillid,costSplitEntry.Fcostaccountid \n");
		builder.addParam(prjId);
		builder.addParam(CostSplitBillTypeEnum.PAYMENTSPLIT_VALUE);
		builder.addParam(prjId);
		IRowSet rowSet=builder.executeQuery();
		while(rowSet.next()){
			String costaccountId=rowSet.getString("Fcostaccountid");
			String contractId=rowSet.getString("fcontractbillid");
			if(paymentSplitAmtMap.containsKey(costaccountId)){
				Map contractMap=(Map)paymentSplitAmtMap.get(costaccountId);
				contractMap.put(contractId, rowSet.getBigDecimal("amount"));
				paymentSplitAmtMap.put(costaccountId, contractMap);
			}else{
				Map contractMap=new HashMap();// Key:合同Id     Value:合同Id为Key的付款拆分
				contractMap.put(contractId, rowSet.getBigDecimal("amount"));
				paymentSplitAmtMap.put(costaccountId,contractMap);
			}
		}
		return paymentSplitAmtMap;
	}
	/**
	 * 取得关联了付款计划的付款拆分的已付款数据
	 * map-->key:acctId+contractId	value:bigdecimal
	 * @param ctx
	 * @param prjId
	 * @return
	 * @throws SQLException 
	 * @throws BOSException 
	 */
	public static Map getPayedAmt(Context ctx,String prjId) throws BOSException, SQLException{
		Map map=new HashMap();
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		//取归属付款金额，应该使用costsplitentry.fpaidamount字段（costsplitentry.famount为归属成本金额）by kevin_yang  2009-06-17 
		builder.appendSql("select payment.fcontractbillid,costSplitEntry.Fcostaccountid,sum(costsplitentry.fpaidamount) as amount \n ");
		builder.appendSql("from T_AIM_CostSplit costsplit \n ");
		builder.appendSql("inner join T_AIM_CostSplitEntry costSplitEntry on costSplitEntry.fparentid=costsplit.fid \n  ");
		builder.appendSql("inner join T_CAS_Paymentbill payment on payment.fid= costsplit.Fcostbillid  \n ");
		//没有关联付款计划(T_FNC_PayRequestAcctPay)的数据是不需要显示出来的 by ling_peng 2009-6-17
		//builder.appendSql("inner join T_FNC_PayRequestAcctPay payActPay on payActPay.FPayRequestBillId=payment.ffdcPayReqID \n");
		builder.appendSql("where costsplitEntry.fobjectid=? and costsplitEntry.Fisproduct=0 and \n ");
		builder.appendSql("payment.fbillstatus >=12 and costsplit.fisinvalid=0 and \n ");
		builder.appendSql("costsplit.fcostbilltype=? and costsplit.fcontrolunitid in (select fcontrolunitid from T_fdc_curProject where fid=?) \n  ");
		builder.appendSql("and exists (select 1 from T_FNC_PayRequestAcctPay where fpayRequestBillid=payment.ffdcPayReqID)\n  ");
		builder.appendSql("group by payment.fcontractbillid,costSplitEntry.Fcostaccountid \n");
		builder.addParam(prjId);
		builder.addParam(CostSplitBillTypeEnum.PAYMENTSPLIT_VALUE);
		builder.addParam(prjId);
		IRowSet rowSet=builder.executeQuery();
		while(rowSet.next()){
			String contractId=rowSet.getString("fcontractbillid");
			String costaccountId=rowSet.getString("Fcostaccountid");
			//在此处设置的 Map 的 key值和在他处使用时候设置的key没有保持一致，导致取数有误。by Cassiel_peng 2009-6-16 
			//map.put(contractId+costaccountId, rowSet.getBigDecimal("amount"));
			map.put(costaccountId+contractId, rowSet.getBigDecimal("amount"));
		}
		return map;
	}
}
