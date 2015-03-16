package com.kingdee.eas.fdc.finance;

import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.FDCBudgetCtrlStrategy.FDCBudgetParam;

public class FDCBudgetNotCtrlStrategy extends FDCBudgetCtrlStrategy {

	protected FDCBudgetNotCtrlStrategy(Context ctx, FDCBudgetParam param) {
		super(ctx, param);
		// TODO Auto-generated constructor stub
	}

	protected Map invokeAddNewBudgetCtrl(PayRequestBillInfo payReqInfo, FDCBudgetPeriodInfo period) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		return null;
	}

	protected Map invokeAuditBudgetCtrl(PayRequestBillInfo payReqInfo, FDCBudgetPeriodInfo period) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		return null;
	}

	protected Map invokeDeleteBudgetCtrl(PayRequestBillInfo payReqInfo, FDCBudgetPeriodInfo period) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		return null;
	}

	protected Map invokeSubmitBudgetCtrl(PayRequestBillInfo payReqInfo, FDCBudgetPeriodInfo period) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		return null;
	}

	protected Map invokeUnAuditBudgetCtrl(PayRequestBillInfo payReqInfo, FDCBudgetPeriodInfo period) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		return null;
	}

}
