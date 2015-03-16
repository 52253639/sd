package com.kingdee.eas.fdc.finance;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class FDCBudgetConCtrlStrategy extends FDCBudgetCtrlStrategy {

	protected FDCBudgetConCtrlStrategy(Context ctx, FDCBudgetParam param) {
		super(ctx, param);
	}

	protected Map invokeAddNewBudgetCtrl(PayRequestBillInfo payReqInfo,FDCBudgetPeriodInfo period) throws BOSException, EASBizException {
		String contractId=payReqInfo.getContractId();
		Map retMap=getRetMap();
		boolean isExistContractPlan=checkExistContractPlan(contractId, period);
		retMap.put(FDCBudgetConstants.RETKEY_EXISTCONTRACTPLAN, Boolean.valueOf(isExistContractPlan));
		return retMap;
	}

	protected Map invokeSubmitBudgetCtrl(PayRequestBillInfo payReqInfo,FDCBudgetPeriodInfo period) throws BOSException, EASBizException{
		String contractId=payReqInfo.getContractId();
		Map retMap=getRetMap();
		//get balance
		BigDecimal balanceAmt=getBalance(contractId, period);
		log("debug balanceAmt:"+balanceAmt+"\t reqAmt:"+payReqInfo.getAmount());
		if(balanceAmt.signum()<=0){
			retMap.put(FDCBudgetConstants.RETKEY_NOBALANCE, Boolean.TRUE);
			retMap.put(FDCBudgetConstants.RETKEY_PASS, Boolean.FALSE);
		}else{
			if(balanceAmt.compareTo(FDCHelper.toBigDecimal(payReqInfo.getAmount()))>=0){;
				retMap.put(FDCBudgetConstants.RETKEY_PASS, Boolean.TRUE);
			}else{
				retMap.put(FDCBudgetConstants.RETKEY_PASS, Boolean.FALSE);
			}
		}
		retMap.put("balanceAmt", balanceAmt);
		retMap.put("reqAmt", payReqInfo.getAmount());
		
		return retMap;
	}
	
	private BigDecimal getBalance(String contractId,FDCBudgetPeriodInfo period) throws BOSException{
		return getPlanAmt(contractId, period).subtract(getRequestedAmt(contractId, period));
	}
	/**
	 *本期计划数
	 * @param contractId
	 * @param period
	 * @return
	 */
	private BigDecimal getPlanAmt(String contractId,FDCBudgetPeriodInfo period) throws BOSException{
		BigDecimal planAmt=FDCNumberHelper.ZERO;
		FDCSQLBuilder builder=new FDCSQLBuilder(getCtx());
		builder.appendSql("select sum(FPayAmount) planAmt from T_FNC_ContractPayPlan ");
		builder.appendSql("where fcontractid=? and FPayDate>=? and FPayDate<=?");
		Date date = period.toDate();
		Date lastDate=FDCDateHelper.getLastDayOfMonth(date);
		Date fistDate=FDCDateHelper.getFirstDayOfMonth(date);
		builder.addParam(contractId);
		builder.addParam(fistDate);
		builder.addParam(lastDate);
		IRowSet rowSet=builder.executeQuery();
		try{
			if(rowSet.next()){
				planAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("planAmt"));
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		if(getCtx().get("debug")!=null){
			System.out.println("debug planAmt:"+planAmt);
		}
		return planAmt;
	}
	
	/**
	 *本期已请款数 
	 * @param contractId
	 * @param period
	 * @return
	 */
	private BigDecimal getRequestedAmt(String contractId,FDCBudgetPeriodInfo period) throws BOSException{
		BigDecimal payRequestedAmt=FDCNumberHelper.ZERO;
		FDCSQLBuilder builder=new FDCSQLBuilder(getCtx());
		builder.appendSql("select sum(FAmount) requestedAmt from T_CON_PayRequestBill ");
		builder.appendSql("where fcontractid=? and FPayDate>=? and FPayDate<=? and fstate=?");
		Date date = period.toDate();
		Date fistDate=FDCDateHelper.getFirstDayOfMonth(date);
		Date lastDate=FDCDateHelper.getLastDayOfMonth(date);
		builder.addParam(contractId);
		builder.addParam(fistDate);
		builder.addParam(lastDate);
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		IRowSet rowSet=builder.executeQuery();
		try{
			if(rowSet.next()){
				payRequestedAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("requestedAmt"));
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		if(getCtx().get("debug")!=null){
			System.out.println("debug payRequestedAmt:"+payRequestedAmt);
		}
		return payRequestedAmt;
	}
	protected Map invokeAuditBudgetCtrl(PayRequestBillInfo payReqInfo,FDCBudgetPeriodInfo period) throws BOSException, EASBizException{
		Map retMap=invokeSubmitBudgetCtrl(payReqInfo, period);
		if(retMap!=null){
			Boolean isPass=(Boolean)retMap.get(FDCBudgetConstants.RETKEY_PASS);
			if(isPass==null||!isPass.booleanValue()){
				String error="没有合同付款计划或者合同付款计划余额不足";
				String errorDetail="";
				errorDetail="合同付款计划余额:\t"+FDCHelper.toBigDecimal(retMap.get("balanceAmt"),2);
				errorDetail+="\n本次申请金额:\t"+FDCHelper.toBigDecimal(retMap.get("reqAmt"),2);
				if(getFDCBudgetParam().isStrictCtrl()){
					throw new EASBizException(new NumericExceptionSubItem("100",error+": \n"+errorDetail));
				}

			}
			
		}
		return retMap;
	}

	protected Map invokeDeleteBudgetCtrl(PayRequestBillInfo payReqInfo,FDCBudgetPeriodInfo period) throws BOSException, EASBizException{
		IObjectPK[] arrayPK=(IObjectPK[])payReqInfo.get("arrayPK");
		if(arrayPK==null||arrayPK.length==0){
			return null;
		}
		String pks[]=new String[arrayPK.length];
		for (int i = 0; i < arrayPK.length; i++) {
			IObjectPK objectPK = arrayPK[i];
			pks[i]=objectPK.toString();
			
		}
		FDCSQLBuilder builder=new FDCSQLBuilder(getCtx());
		builder.appendSql("delete from T_FNC_PayRequestAcctPay where ");
		builder.appendParam("fpayRequestBillId", pks);
		builder.execute();
		return null;
	}


	protected Map invokeUnAuditBudgetCtrl(PayRequestBillInfo payReqInfo,FDCBudgetPeriodInfo period) throws BOSException, EASBizException{
		return null;
	}
	
	public boolean checkExistContractPlan(String contractId,FDCBudgetPeriodInfo period) throws BOSException, EASBizException{
		FDCSQLBuilder builder=new FDCSQLBuilder(getCtx());
		builder.appendSql("select 1 from T_FNC_ContractPayPlan where fcontractid=? and FPayDate>=? and FPayDate<=?");
		Date date = period.toDate();
		Date lastDate=FDCDateHelper.getLastDayOfMonth(date);
		Date fistDate=FDCDateHelper.getFirstDayOfMonth(date);
		builder.addParam(contractId);
		builder.addParam(fistDate);
		builder.addParam(lastDate);
		return builder.isExist();
	}

}
