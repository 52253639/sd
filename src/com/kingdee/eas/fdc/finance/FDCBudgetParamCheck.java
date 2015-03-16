package com.kingdee.eas.fdc.finance;

import java.sql.SQLException;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.base.param.IParamCheck;
import com.kingdee.eas.base.param.IParamSetModify;
import com.kingdee.eas.base.param.ParamInfo;
import com.kingdee.eas.base.param.ParamItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusException;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusFactory;
import com.kingdee.eas.fdc.finance.FDCBudgetCtrlStrategy.FDCBudgetParam;
import com.kingdee.eas.fdc.finance.app.ProjectPeriodStatusUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class FDCBudgetParamCheck implements IParamCheck, IParamSetModify {

	public void check(Context ctx, ParamItemInfo model) throws BOSException, EASBizException {

		String orgunitid = null;
		if (model.getOrgUnitID() != null) {
			orgunitid = model.getOrgUnitID().getId().toString();
		}

		try {
			checkParamUpdate(ctx, model, model.getKeyID(), orgunitid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
	}

	public boolean setParamModify(Context ctx, ParamInfo paramInfo) throws BOSException, EASBizException {

		return true;
	}

	/**
	 * FDC参数校验
	 * 
	 * @param ctx
	 * @param param
	 *            参数对象
	 * @param orgUnitId
	 *            组织
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws SQLException
	 */
	private void checkParamUpdate(Context ctx, ParamItemInfo model, ParamInfo param, String orgUnit) throws BOSException, EASBizException, SQLException {
		String number = param.getNumber();
		FDCBudgetParam params=FDCBudgetParam.getInstance(ctx, orgUnit);
		//			 			
		if (number.equals(FDCConstants.FDC_PARAM_BUDGET_BGSYSCTRPAY)) {
			//预算系统控制
			if(params.isContractPlanCtrl()){
				throw new FDCBudgetAcctException(FDCBudgetAcctException.CONPARAM);
			}
			if(params.isAcctPlanCtrl()){
				throw new FDCBudgetAcctException(FDCBudgetAcctException.ACCTPARAM);
			}
		}
		if (number.equals(FDCConstants.FDC_PARAM_BUDGET_CONTRACTCTRPAY)) {
			//合同控制
			if(params.isBgSysCtrl()){
				throw new FDCBudgetAcctException(FDCBudgetAcctException.BGSYSPARAM);
			}
			if(params.isAcctPlanCtrl()){
				throw new FDCBudgetAcctException(FDCBudgetAcctException.ACCTPARAM);
			}
		}
		if (number.equals(FDCConstants.FDC_PARAM_BUDGET_COSTACCTCTRPAY)) {
			//成本科目控制
			if(params.isContractPlanCtrl()){
				throw new FDCBudgetAcctException(FDCBudgetAcctException.CONPARAM);
			}
			if(params.isBgSysCtrl()){
				throw new FDCBudgetAcctException(FDCBudgetAcctException.BGSYSPARAM);
			}
		}
	}
}
