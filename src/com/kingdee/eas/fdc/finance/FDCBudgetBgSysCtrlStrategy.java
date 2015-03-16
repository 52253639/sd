package com.kingdee.eas.fdc.finance;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.BgCtrlPayRequestBillHandler;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillException;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.ma.budget.BgBudgetFacadeFactory;
import com.kingdee.eas.ma.budget.BgControlFacadeFactory;
import com.kingdee.eas.ma.budget.BgCtrlResultCollection;
import com.kingdee.eas.ma.budget.BgCtrlResultInfo;
import com.kingdee.eas.ma.budget.IBgBudgetFacade;
import com.kingdee.eas.ma.budget.IBgControlFacade;
import com.kingdee.jdbc.rowset.IRowSet;

public class FDCBudgetBgSysCtrlStrategy extends FDCBudgetCtrlStrategy{

	protected FDCBudgetBgSysCtrlStrategy(Context ctx, FDCBudgetParam param) {
		super(ctx, param);
	}

	protected Map invokeAddNewBudgetCtrl(PayRequestBillInfo payReqInfo, FDCBudgetPeriodInfo period) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		return null;
	}

	protected Map invokeAuditBudgetCtrl(PayRequestBillInfo payReqInfo, FDCBudgetPeriodInfo period) throws BOSException, EASBizException {
		//扣减预算
		boolean useWorkflow = FDCUtils.isRunningWorkflow(getCtx(), payReqInfo.getId().toString());
        if (!useWorkflow) {
            IBgControlFacade bgControlFacade = BgControlFacadeFactory.getLocalInstance(getCtx());
            IBgBudgetFacade iBgBudget = BgBudgetFacadeFactory.getLocalInstance(getCtx());

            boolean isPass = false;
            isPass = iBgBudget.getAllowAccessNoWF(FDCConstants.PayRequestBill);
            if (isPass) {
                // 5.1.1暂时屏蔽
                bgControlFacade.bgAuditAllowAccess(payReqInfo.getId(), FDCConstants.PayRequestBill, BgCtrlPayRequestBillHandler.BGHANDLER);
            } else {
                isPass = bgControlFacade.bgAudit(payReqInfo.getId().toString(),  FDCConstants.PayRequestBill, BgCtrlPayRequestBillHandler.BGHANDLER);
            }
            // 根据isPass判断是否抛异常
            if (!isPass) {
                throw new PayRequestBillException(PayRequestBillException.BEFOREBGBAL);
            }
        }
        String payReqId=payReqInfo.getId().toString();
		PayRequestAcctPayFactory.getLocalInstance(getCtx()).audit(payReqId);
        return null;
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

	protected Map invokeSubmitBudgetCtrl(PayRequestBillInfo payReqInfo, FDCBudgetPeriodInfo period) throws BOSException, EASBizException {
		Map retMap=getRetMap();
		StringBuffer buffer = new StringBuffer("");
		IBgControlFacade iCtrl = BgControlFacadeFactory.getLocalInstance(getCtx());
		BgCtrlResultCollection bgCtrlResultCollection = iCtrl.getBudget(
				FDCConstants.PayRequestBill, null, payReqInfo);

		if (bgCtrlResultCollection != null) {
			for (int j = 0, count = bgCtrlResultCollection.size(); j < count; j++) {
				BgCtrlResultInfo bgCtrlResultInfo = bgCtrlResultCollection
						.get(j);

				BigDecimal balance = bgCtrlResultInfo.getBalance();
				if ((balance != null && bgCtrlResultInfo.getReqAmount() != null
						&& balance.compareTo(bgCtrlResultInfo
								.getReqAmount()) < 0) || bgCtrlResultInfo.getReqAmount() == null) {
					buffer.append(
							bgCtrlResultInfo.getItemName() + "("
									+ bgCtrlResultInfo.getOrgUnitName()
									+ ")").append("余额不足");
				}
			}
			
		}
		if (buffer.length() > 0) {
			setPass(retMap, false);
			setNoPassDetail(retMap, buffer.toString());
		}else{
			setPass(retMap, true);
		}
		return retMap;
	}

	protected Map invokeUnAuditBudgetCtrl(PayRequestBillInfo payReqInfo, FDCBudgetPeriodInfo period) throws BOSException, EASBizException {
        // 20050511 吴志敏提供预算接口
		IBgControlFacade iBgCtrl = BgControlFacadeFactory.getLocalInstance(getCtx());
		iBgCtrl.cancelRequestBudget(payReqInfo.getId().toString());

        return null;
	}

}
