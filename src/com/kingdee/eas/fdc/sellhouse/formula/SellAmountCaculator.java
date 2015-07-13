package com.kingdee.eas.fdc.sellhouse.formula;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.IMethodBatchQuery;
import com.kingdee.bos.ctrl.excel.model.struct.Parameter;
import com.kingdee.bos.ctrl.excel.model.util.SortedParameterArray;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.finance.formula.FDCBudgetAcctCaculatorHelper;
import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;
import com.kingdee.eas.fi.newrpt.formula.IReportPropertyAdapter;
import com.kingdee.eas.ma.budget.BgPeriodInfo;
import com.kingdee.eas.ma.budget.BgRptReportPropertyAdapter;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

public class SellAmountCaculator implements ICalculator, IMethodBatchQuery{

	private Context ServerCtx = null;
	private ICalculateContextProvider context;
	private String companyNumber = null ;
	private String sellProject = null ;
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
		SortedParameterArray params = (SortedParameterArray) map.get("fdc_sellAmount");
		
		IReportPropertyAdapter adapter = context.getReportAdapter();
		
		String _currOrgUnitNumber = (String) adapter.getReportProperty(BgRptReportPropertyAdapter.BG_ORG_NUMBER);
		String _currPeriodNumber = (String) adapter.getReportProperty(BgRptReportPropertyAdapter.BG_PERIOD_NUMBER);
		
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				
				Parameter param = params.getParameter(i);
				Object[] obj = param.getArgs();
				
				companyNumber = (String) ((Variant) obj[0]).getValue();
				sellProject = (String) ((Variant) obj[1]).getValue();
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
					BigDecimal amount = this.fdc_sellAmount(
							companyNumber, sellProject, budgetPeriod);
					params.getParameter(i).setValue(amount);
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
	public BigDecimal fdc_sellAmount(String companyNumber,String sellProject, String budgetPeriod) throws EASBizException, BOSException, SQLException {
		BgPeriodInfo period = FDCBudgetAcctCaculatorHelper.getBgPeriod(ServerCtx, budgetPeriod);
		FDCSQLBuilder _builder = new FDCSQLBuilder(ServerCtx);
		String spStr=null;
		if(sellProject!=null&&!"".equals(sellProject)){
			if(sellProject.indexOf(";")>0){
				for(int i=0;i<sellProject.split(";").length;i++){
					if(i==0){
						spStr=spStr+"'"+sellProject.split(";")[i]+"'";
					}else{
						spStr=spStr+",'"+sellProject.split(";")[i]+"'";
					}
				}
			}else{
				spStr="'"+sellProject+"'";
			}
		}
		_builder.appendSql(" select sum(sign.fcontractTotalAmount) amount from t_she_signManage sign left join t_org_baseUnit org on org.fid=sign.forgUnitId left join t_she_sellProject sp on sp.fid=sign.fsellProjectId");
		_builder.appendSql(" where sign.fbizState in('ChangeNameAuditing','QuitRoomAuditing','ChangeRoomAuditing','SignApple','SignAudit')");
    	_builder.appendSql(" and NOT EXISTS (select tt.fnewId from t_she_changeManage tt where tt.fstate in('2SUBMITTED','3AUDITTING') and sign.fid=tt.fnewId )");
    	_builder.appendSql(" and year(sign.fbusAdscriptionDate)="+period.getYear()+" and month(sign.fbusAdscriptionDate)="+period.getMonth());
		_builder.appendSql(" and org.fnumber='"+companyNumber+"'");
		if(spStr!=null){
			_builder.appendSql(" and sp.fnumber in("+spStr+")");
		}
    	IRowSet rowSet = _builder.executeQuery();
    	BigDecimal amount=FDCHelper.ZERO;
    	while(rowSet.next()) {
    		amount=rowSet.getBigDecimal("amount");
    	}
		return amount;
	}
}
