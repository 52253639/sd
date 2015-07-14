package com.kingdee.eas.fdc.finance.formula;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.IMethodBatchQuery;
import com.kingdee.bos.ctrl.excel.model.struct.Parameter;
import com.kingdee.bos.ctrl.excel.model.util.SortedParameterArray;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherDateEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherDateEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherFactory;
import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;
import com.kingdee.eas.fi.newrpt.formula.IReportPropertyAdapter;
import com.kingdee.eas.ma.budget.BgPeriodInfo;
import com.kingdee.eas.ma.budget.BgRptReportPropertyAdapter;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

public class FDCOrgUnitMonthPlanGahterCaculator implements ICalculator, IMethodBatchQuery{

	private Context ServerCtx = null;
	private ICalculateContextProvider context;
	private String companyNumber = null ;
	private String bgNumber = null ;
	private String budgetPeriod = null ;
	
	public Context getServerCtx()
	{
		return ServerCtx;
	}
	public void setServerCtx(Context serverCtx)
	{
		ServerCtx = serverCtx;
	}
	public void initCalculateContext(ICalculateContextProvider context)
	{
		this.context = context;
		this.ServerCtx = this.context.getServerContext();
	}
	public boolean batchQuery(Map map) {
		SortedParameterArray params = (SortedParameterArray) map.get("fdc_orgUnitMonthPlanGahter_amount");
		
		IReportPropertyAdapter adapter = context.getReportAdapter();
		
		String _currOrgUnitNumber = (String) adapter.getReportProperty(BgRptReportPropertyAdapter.BG_ORG_NUMBER);
		String _currPeriodNumber = (String) adapter.getReportProperty(BgRptReportPropertyAdapter.BG_PERIOD_NUMBER);
		
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				
				Parameter param = params.getParameter(i);
				Object[] obj = param.getArgs();
				
				companyNumber = (String) ((Variant) obj[0]).getValue();
				bgNumber = (String) ((Variant) obj[1]).getValue();
				budgetPeriod = (String) ((Variant) obj[2]).getValue();
				if (StringUtils.isEmpty(companyNumber)) {
					if(_currOrgUnitNumber==null){
						companyNumber="";
					}else{
						companyNumber = _currOrgUnitNumber;
					}
				}
				if (StringUtils.isEmpty(budgetPeriod)) {
					budgetPeriod = _currPeriodNumber;
				}
				try {
					// 通过计算公式计算，返回结果
					BigDecimal amount = this.fdc_orgUnitMonthPlanGahter_amount(
							companyNumber, bgNumber, budgetPeriod);
					params.getParameter(i).setValue(amount);
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
	public BigDecimal fdc_orgUnitMonthPlanGahter_amount(String companyNumber,String bgNumber, String budgetPeriod) throws EASBizException, BOSException, SQLException {
		BgPeriodInfo period = FDCBudgetAcctCaculatorHelper.getBgPeriod(ServerCtx, budgetPeriod);
		FDCSQLBuilder _builder = new FDCSQLBuilder(ServerCtx);
		_builder.appendSql(" select isnull(sum(t.amount),0) amount from(");
		_builder.appendSql(" select isnull(sum(dateEntry.famount),0) amount from T_FNC_OrgUnitMonthPlanGather bill");
    	_builder.appendSql(" left join T_FNC_OrgUnitMonthPGEntry entry on bill.fid=entry.fheadId left join T_ORG_BaseUnit orgUnit on orgUnit.fid=bill.forgUnitId");
    	_builder.appendSql(" left join T_FNC_ProjectMonthPlanGather gather on gather.fid=entry.fsrcId");
    	_builder.appendSql(" left join T_FNC_ProjectMonthPlanGEntry gatherEntry on gatherEntry.fheadId=gather.fid");
    	_builder.appendSql(" left join T_FNC_ProjectMonthPGDateEntry dateEntry on dateEntry.fheadentryId=gatherEntry.fid left join t_bg_bgitem bgItem on bgItem.fid=dateEntry.fbgItemId");
    	_builder.appendSql(" where bill.fstate='4AUDITTED' and bill.fIsLatest=1 and year(bill.fbizDate)="+period.getYear()+" and month(bill.fbizDate)="+period.getMonth());
    	_builder.appendSql(" and dateEntry.fyear="+period.getYear()+" and dateEntry.fmonth="+period.getMonth());
    	_builder.appendSql(" and orgUnit.fnumber='"+companyNumber+"' and bgItem.fnumber like '"+bgNumber+"%'");
    	
    	_builder.appendSql(" union all select isnull(sum(payEntry.famount-payEntry.factPayAmount),0) amount from T_FNC_OrgUnitMonthPlanGather bill");
    	_builder.appendSql(" left join T_FNC_OrgUnitMonthPGEntry entry on bill.fid=entry.fheadId left join T_ORG_BaseUnit orgUnit on orgUnit.fid=bill.forgUnitId");
    	_builder.appendSql(" left join T_FNC_ProjectMonthPlanGather gather on gather.fid=entry.fsrcId");
    	_builder.appendSql(" left join T_FNC_ProjectMPlanGPayEntry payEntry on payEntry.fheadId=gather.fid left join t_bg_bgitem bgItem on bgItem.fid=payEntry.fbgItemId");
    	_builder.appendSql(" where bill.fstate='4AUDITTED' and bill.fIsLatest=1 and year(bill.fbizDate)="+period.getYear()+" and month(bill.fbizDate)="+period.getMonth());
    	_builder.appendSql(" and orgUnit.fnumber='"+companyNumber+"' and bgItem.fnumber like '"+bgNumber+"%'");
    	
    	_builder.appendSql(" )t");
    	IRowSet rowSet = _builder.executeQuery();
    	BigDecimal amount=FDCHelper.ZERO;
    	while(rowSet.next()) {
    		amount=rowSet.getBigDecimal("amount");
    	}
		return amount;
	}

}
