package com.kingdee.eas.fdc.finance;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetAcctItemTypeEnum;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class FDCBudgetAcctCtrlStrategy extends FDCBudgetCtrlStrategy{

	protected FDCBudgetAcctCtrlStrategy(Context ctx, FDCBudgetParam param) {
		super(ctx, param);
	}

	protected Map invokeAddNewBudgetCtrl(PayRequestBillInfo payReqInfo, FDCBudgetPeriodInfo period) throws BOSException, EASBizException {
		String contractId=payReqInfo.getContractId();
		Map retMap=getRetMap();
		boolean isExistContractPlan=checkExistContractPlan(contractId, period);
		retMap.put(FDCBudgetConstants.RETKEY_EXISTCONTRACTPLAN, Boolean.valueOf(isExistContractPlan));
		if(!isExistContractPlan){
			boolean isNeedAssociateCon=checkNeedAssociateUnSettledPlan(contractId, period);
			retMap.put(FDCBudgetConstants.RETKEY_ASSOCIATIONCONTRACT, Boolean.valueOf(isNeedAssociateCon));
		}
		return retMap;
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
	
	/**
	 * 是否是新增合同或者没有做计划的合同,这样的合同可以进行待签订合同的关联
	 * @param contractId
	 * @param period
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public boolean checkNeedAssociateUnSettledPlan(String contractId,FDCBudgetPeriodInfo period) throws BOSException, EASBizException{
		if(true){
			return true;
		}
		FDCSQLBuilder builder=new FDCSQLBuilder(getCtx());
		builder.appendSql("select 1 from T_FNC_FDCMonthBudgetAcctEntry entry ");
		builder.appendSql("inner join T_FNC_FDCMonthBudgetAcct head on head.fid=entry.fparentid ");
		builder.appendSql("inner join T_FNC_FDCBudgetPeriod period on period.fid=head.ffdcperiodid");
		builder.appendSql("where fcontractbillId=? and period.fyear=? and f");
		Date date = period.toDate();
		Date lastDate=FDCDateHelper.getLastDayOfMonth(date);
		Date fistDate=FDCDateHelper.getFirstDayOfMonth(date);
		builder.addParam(contractId);
		builder.addParam(fistDate);
		builder.addParam(lastDate);
		return builder.isExist();
	}
	protected Map invokeSubmitBudgetCtrl(PayRequestBillInfo payReqInfo, FDCBudgetPeriodInfo period) throws BOSException, EASBizException {
		Map retMap=getRetMap();
		String contractId=payReqInfo.getContractId();
		PayRequestAcctPayCollection acctPays = payReqInfo.getAcctPays();
		BigDecimal payReaAmt = FDCHelper.toBigDecimal(payReqInfo.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP);
		if(FDCHelper.toBigDecimal(payReaAmt).signum()!=0&&(acctPays!=null&&acctPays.size()==0)){
			throw new FDCBudgetAcctException(FDCBudgetAcctException.NOACCTPAY);
		}
		BigDecimal tmpSum=FDCHelper.ZERO;
		Map requestMap=new HashMap();
		Map acctNameMap=new HashMap();
		
		//update by renliang
		if(acctPays!=null && acctPays.size()>0){
			for (Iterator iter = acctPays.iterator(); iter.hasNext();) {
				PayRequestAcctPayInfo info = (PayRequestAcctPayInfo) iter.next();
				BigDecimal reqAmt = FDCHelper.toBigDecimal(info.getAmount());
				requestMap.put(info.getCostAccount().getId().toString(), reqAmt);
				tmpSum=tmpSum.add(reqAmt);
				acctNameMap.put(info.getCostAccount().getId().toString(), info.getCostAccount().getName());
			}
			
		}
		
		if(tmpSum.compareTo(payReaAmt)!=0){
			throw new FDCBudgetAcctException(FDCBudgetAcctException.ACCTPAYNOTEQUEALREQAMT);
		}
		
		Map balanceMap=getBalance(contractId, period);
		Map difMap=FDCHelper.getDifMap(balanceMap, requestMap);
		Map noPassDetailMap=new HashMap();
		for(Iterator iter=difMap.keySet().iterator();iter.hasNext();){
			String key=(String)iter.next();
			if(FDCHelper.toBigDecimal(difMap.get(key)).signum()<0){
				noPassDetailMap.put(key, difMap.get(key));
			}
		}
		if(noPassDetailMap.size()>0){
			Map detailMap=new HashMap();
			setPass(retMap, false);
			for(Iterator iter=noPassDetailMap.keySet().iterator();iter.hasNext();){
				String key=(String)iter.next();
				Map map=new HashMap();
				map.put(FDCBudgetConstants.RETKEY_BALANCEAMT,balanceMap.get(key));
				map.put(FDCBudgetConstants.RETKEY_REQAMT,requestMap.get(key));
				map.put(FDCBudgetConstants.RETKEY_ACCT, acctNameMap.get(key));
				detailMap.put(key, map);
			}
			setNoPassDetail(retMap, detailMap);
		}else{
			setPass(retMap, true);
		}
		return retMap;
	}
	
	private Map getBalance(String contractId,FDCBudgetPeriodInfo period)throws BOSException{
		Map planMap=getPlanAmt(contractId, period);
		Map requestedMap=getRequestedAmt(contractId, period);
		Map balanceMap=FDCHelper.getDifMap(planMap, requestedMap);
		return balanceMap;
	}
	
	private Map getPlanAmt(String contractId,FDCBudgetPeriodInfo period)throws BOSException{
		Map planMap=new HashMap();
		FDCSQLBuilder builder=new FDCSQLBuilder(getCtx());
		builder.appendSql("select fcostaccountid, sum(entry.famount) planAmt from T_FNC_FDCMonthBudgetAcctEntry entry ");
		builder.appendSql("inner join T_FNC_FDCMonthBudgetAcct head on head.fid=entry.fparentid ");
		builder.appendSql("inner join T_FNC_FDCBudgetPeriod period on period.fid=head.ffdcperiodid ");
		// 取最终版本 by hpw 2009-11-13
		builder.appendSql("where fcontractbillId=? and period.fyear=? and fmonth=? and head.fstate=? and fitemType<>? and head.fislatestver=1 ");
		builder.appendSql("group by fcostAccountid");
		builder.addParam(contractId);
		builder.addParam(new Integer(period.getYear()));
		builder.addParam(new Integer(period.getMonth()));
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		builder.addParam(FDCBudgetAcctItemTypeEnum.UNSETTLEDCONTRACT_VALUE);
		IRowSet rowSet=builder.executeQuery();
		try{
			while(rowSet.next()){
				planMap.put(rowSet.getString("fcostaccountid"), FDCHelper.toBigDecimal(rowSet.getBigDecimal("planAmt")));
			}
		}catch(SQLException e){
			throw new BOSException(e);
		}
		log(planMap.toString());
		return planMap;
	}
	
	private Map getRequestedAmt(String contractId,FDCBudgetPeriodInfo period)throws BOSException{
		Map requestedMap=new HashMap();
		FDCSQLBuilder builder=new FDCSQLBuilder(getCtx());
		builder.appendSql("select fcostaccountid,sum(acctPay.famount) requestAmt from T_FNC_PayRequestAcctPay acctPay ");
		builder.appendSql("inner join T_CON_PayRequestBill payReq on payReq.fid=acctPay.fpayRequestBillId ");
		builder.appendSql("inner join T_FNC_FDCBudgetPeriod period on period.fid=acctPay.fPeriodId ");
		builder.appendSql("where acctPay.fcontractId=? and payReq.fcontractId=? and period.fyear=? and period.fmonth=? and payReq.fstate=? ");
		builder.appendSql("group by fcostaccountid");
		builder.addParam(contractId);
		builder.addParam(contractId);
		builder.addParam(new Integer(period.getYear()));
		builder.addParam(new Integer(period.getMonth()));
		builder.addParam(FDCBillStateEnum.AUDITTED_VALUE);
		IRowSet rowSet=builder.executeQuery();
		try {
			while (rowSet.next()) {
				requestedMap.put(rowSet.getString("fcostaccountid"), FDCHelper.toBigDecimal(rowSet.getBigDecimal("requestAmt")));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		log(requestedMap.toString());
		return requestedMap;
	}
	
	protected Map invokeAuditBudgetCtrl(PayRequestBillInfo payReqInfo, FDCBudgetPeriodInfo period) throws BOSException, EASBizException {
		Map retMap = invokeSubmitBudgetCtrl(payReqInfo, period);
		
		if(retMap!=null){
			Boolean isPass=(Boolean)retMap.get(FDCBudgetConstants.RETKEY_PASS);
			if(isPass==null||!isPass.booleanValue()){
				String error="预算余额不足";
				String errorDetail="";
				Map map=(Map)retMap.get(FDCBudgetConstants.RETKEY_NOPASSDETAIL);
				StringBuffer sb=new StringBuffer();
				sb.append("        成本科目");
				sb.append("       ");
				sb.append("余额");
				sb.append("       ");
				sb.append("申请额");
				sb.append("\n");
				for(Iterator iter=map.keySet().iterator();iter.hasNext();){
					Map subMap=(Map)map.get(iter.next());
					String str = (String)subMap.get(FDCBudgetConstants.RETKEY_ACCT);
					sb.append(str);
					if(str.length()>"成本科目".length()*2){
						sb.append("       ");
					}else if(str.length()>"成本科目".length()){
						sb.append("       ");
					}else{
						sb.append("       ");
					}
					BigDecimal tmpAmt = (BigDecimal)subMap.get(FDCBudgetConstants.RETKEY_BALANCEAMT);
					sb.append(tmpAmt==null?"":FDCHelper.toBigDecimal(tmpAmt, 2).toString());
					sb.append("       ");
					tmpAmt=(BigDecimal)subMap.get(FDCBudgetConstants.RETKEY_REQAMT);
					sb.append(tmpAmt==null?"":FDCHelper.toBigDecimal(tmpAmt, 2).toString());
					sb.append("\n");
				}
				errorDetail=sb.toString();
				if(getFDCBudgetParam().isStrictCtrl()){
					throw new EASBizException(new NumericExceptionSubItem("100",error+": \n"+errorDetail));
				}

			}
			
		}
		String payReqId=payReqInfo.getId().toString();
		PayRequestAcctPayFactory.getLocalInstance(getCtx()).audit(payReqId);
		return retMap;
	}

	protected Map invokeDeleteBudgetCtrl(PayRequestBillInfo payReqInfo, FDCBudgetPeriodInfo period) throws BOSException, EASBizException {
		IObjectPK[] arrayPK=(IObjectPK[])payReqInfo.get("arrayPK");
		String contractId = (String)payReqInfo.get("contractId");
		if(period==null){
			period = (FDCBudgetPeriodInfo)payReqInfo.get("period");
		}
		FDCSQLBuilder builder=new FDCSQLBuilder(getCtx());
		if(period.getId()==null){
	    	builder.appendSql("select fid from T_FNC_FDCBudgetPeriod where fyear=? and fmonth=? and fisYear=?");
	    	builder.addParam(new Integer(period.getYear()));
	    	builder.addParam(new Integer(period.getMonth()));
	    	builder.addParam(Boolean.FALSE);
	    	IRowSet rowSet=builder.executeQuery();
	    	try {
				if (rowSet.size() > 0) {
					rowSet.next();
					period.setId(BOSUuid.read(rowSet.getString("fid")));
				}
			}catch(SQLException e){
	    		throw new BOSException(e);
	    	}
		}
		if(arrayPK==null||arrayPK.length==0||contractId==null){
			return null;
		}
		String pks[]=new String[arrayPK.length];
		for (int i = 0; i < arrayPK.length; i++) {
			IObjectPK objectPK = arrayPK[i];
			pks[i]=objectPK.toString();
			
		}
		
		Set acctIds = new HashSet();
		builder.clear();
		builder.appendSql("select fcostaccountid from T_FNC_PayRequestAcctPay where ");
		builder.appendParam("fpayRequestBillId", pks);
		IRowSet rs = builder.executeQuery();
		try{
			if(rs!=null&&rs.size()>0){
				while(rs.next()){
					acctIds.add(rs.getString("fcostaccountid"));
				}
			}
		}catch(Exception e){
			throw new BOSException(e);
		}
		builder.clear();
		builder.appendSql("delete from T_FNC_PayRequestAcctPay where ");
		builder.appendParam("fpayRequestBillId", pks);
		builder.execute();
		
		if(acctIds.size()==0){
			return null;
		}
		//处理最后一版本数据
		//1.删除单据待签定条目
		builder.clear();
		builder.appendSql("delete from t_fnc_fdcmonthbudgetacctitem where fentryid in ( ");
		builder.appendSql("	select fid from t_fnc_fdcmonthbudgetacctentry where fisadd=1 and fcontractbillid=? \n");
		builder.addParam(contractId);
		builder.appendSql(" and fparentid in (select fid from t_fnc_fdcmonthbudgetacct where ffdcperiodid=? and fislatestver=1) and \n");
		builder.addParam(period.getId().toString());
		builder.appendParam("fcostaccountid", acctIds.toArray());
		builder.appendSql(" and not exists (select 1 from t_fnc_payrequestacctpay where FContractId =?) \n");
		builder.appendSql(" )");
		builder.addParam(contractId);
		builder.execute();
		
		//2.删除单据待签定分录(关联待签定时生成的)
		builder.clear();
		builder.appendSql("delete from t_fnc_fdcmonthbudgetacctentry where fisadd=1 and fcontractbillid=? and fparentid in (select fid from t_fnc_fdcmonthbudgetacct where ffdcperiodid=? and fislatestver=1) and \n");
		builder.addParam(contractId);
		builder.addParam(period.getId().toString());
		builder.appendParam("fcostaccountid", acctIds.toArray());
		builder.appendSql(" and not exists (select 1 from t_fnc_payrequestacctpay where FContractId =?) \n");
		builder.addParam(contractId);
		builder.execute();
		
		//--3.更新关联的待签定记录状态
		builder.clear();
		builder.appendSql("update t_fnc_fdcmonthbudgetacctentry set fcontractbillid=null, fdesc=null where fcontractbillid=? and fitemtype='9UNSETTLEDCONTRACT' and fparentid in (select fid from t_fnc_fdcmonthbudgetacct where ffdcperiodid=? and fislatestver=1 ) and \n");
		builder.addParam(contractId);
		builder.addParam(period.getId().toString());
		builder.appendParam("fcostaccountid", acctIds.toArray());
		builder.appendSql(" and not exists (select 1 from t_fnc_payrequestacctpay where FContractId =? ) \n");
		builder.addParam(contractId);
		builder.execute();
		return null;
	}

	protected Map invokeUnAuditBudgetCtrl(PayRequestBillInfo payReqInfo, FDCBudgetPeriodInfo period) throws BOSException, EASBizException {
		return null;
	}
}
