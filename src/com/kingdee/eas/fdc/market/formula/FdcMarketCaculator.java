package com.kingdee.eas.fdc.market.formula;

/*
 * @(#)FdcRealCaculator.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.IMethodBatchQuery;
import com.kingdee.bos.ctrl.excel.model.struct.Parameter;
import com.kingdee.bos.ctrl.excel.model.util.SortedParameterArray;
import com.kingdee.bos.dao.query.SQLExecutorFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.IPaymentBill;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.gr.cslrpt.client.report.CslCheckBeforeSaveHelper;
import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;
import com.kingdee.eas.ma.budget.BgPeriodFactory;
import com.kingdee.eas.ma.budget.BgPeriodInfo;
import com.kingdee.eas.ma.budget.IBgPeriod;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.AdvMsgBox;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 描述:
 * 
 * @author
 * <p>
 * @version EAS6.0
 */
public class FdcMarketCaculator implements ICalculator, IMethodBatchQuery {

	private ICalculateContextProvider context;

	private Context serverCtx = null;
	
	private String type = null;
	// 工程项目编码
	private String fdcProjectNumber;

	// 期间编码
	private String periodBeginNumber;

	private String periodEndNumber;

	public FdcMarketCaculator() {

	}

	public void initCalculateContext(ICalculateContextProvider context) {
		this.context = context;
		this.serverCtx = this.context.getServerContext();
	}

	/**
	 * 房地产资金计划取数公式_计划数
	 * 
	 * @param fdcProject
	 * @param planPeriod
	 * @return BigDecimal
	 * @throws BOSException
	 * @throws EASBizException
	 */

	public boolean batchQuery(Map methods) {

		SortedParameterArray params = (SortedParameterArray) methods.get("fdc_market");
		if (params != null) {
			for (int i = 0; i < params.size(); i++) {
				Parameter param = params.getParameter(i);
				Object[] obj = param.getArgs();
				
				type = (String) ((Variant) obj[0]).getValue();
				fdcProjectNumber = (String) ((Variant) obj[1]).getValue();
				periodBeginNumber = (String) ((Variant) obj[2]).getValue();
				periodEndNumber = (String) ((Variant) obj[3]).getValue();

				try {
					BigDecimal amount;
					try {
						amount = fdc_market(type, fdcProjectNumber, periodBeginNumber, periodEndNumber);
						params.getParameter(i).setValue(amount);
					} catch (SQLException e) {
						e.printStackTrace();
					}

				} catch (EASBizException e) {
					e.printStackTrace();
					return false;
				} catch (BOSException e) {
					e.printStackTrace();
					return false;
				}
			}
		}

		return true;
	}

	public Context getServerCtx() {
		return serverCtx;
	}

	public void setServerCtx(Context serverCtx) {
		this.serverCtx = serverCtx;
	}


	/**
	 * 房地产资金计划取数公式_实际数
	 * 
	 * @param orgUnit
	 * @param fdcProject
	 * @param paymentType
	 * @param periodBegin
	 * @param periodEnd
	 * @return BigDecimal
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws SQLException
	 */
	public BigDecimal fdc_market(String type, String fdcProject, String periodBegin, String periodEnd) throws BOSException, EASBizException, SQLException {

		BigDecimal rate = FDCHelper.ZERO;
		BigDecimal money = FDCHelper.ZERO;
		FDCSQLBuilder firstVisitSql = new FDCSQLBuilder();
		FDCSQLBuilder sellNumberSql = new FDCSQLBuilder();
		StringBuffer firstVisitWhere = new StringBuffer();
		StringBuffer sellNumberWhere = new StringBuffer();
		int visitors = 0;
		//
		
		if (type == null || type.trim().length() < 1)
			throw new BOSException("统计维度参数不能为空！");
		if (fdcProject == null || fdcProject.trim().length() < 1)
			throw new BOSException("所选销售项目不能为空！");
		
		if (!periodBegin.equals("") && !periodEnd.equals("")) {
			
			firstVisitWhere.append(" and t1.FCreateTime BETWEEN '" + periodBegin + "' and  '" + periodEnd + "'");
			
			sellNumberWhere.append(" and t1.FCreateTime BETWEEN '" + periodBegin + "' and  '" + periodEnd + "'");
			
		}
		
		else if (!periodBegin.equals("") && periodEnd.equals("")) {
			
			firstVisitWhere.append(" and t1.FCreateTime > '" + periodBegin+ "'");
	
			sellNumberWhere.append(" and t1.FCreateTime > '" + periodBegin + "'");
	
		}
		
		else if (periodBegin.equals("") && !periodEnd.equals("")) {
			
			firstVisitWhere.append(" and t1.FCreateTime < '" + periodEnd + "'");
			
			sellNumberWhere.append(" and t1.FCreateTime < '" + periodEnd + "'");
			
		}
//		 判断查询维度
		String requestType = type;
		// 总套数
		if (requestType.equalsIgnoreCase("visit"))
		{
	
			firstVisitSql.appendSql("select count(*) as Visitors from t_SHE_fdccustomer t1,t_she_receptionType t2 where t1.freceptionTypeid = t2.fid And t2.fName_l2 like '%首次来访%'");
			firstVisitSql.appendSql(firstVisitWhere.toString());
			IRowSet allReceiveSet=firstVisitSql.executeQuery(serverCtx);
	
			// 新来访的客户数ggf
			while (allReceiveSet.next())
			{
				visitors = allReceiveSet.getInt("Visitors");
			}
			
			sellNumberSql.appendSql("select sum(famount) as Amount from T_MAR_MarketManage as t1,T_MAR_MarketManageEntry as t2 ");
			sellNumberSql.appendSql("where t1.fid=t2.fparentid and t1.fsellproject=(select fid from T_SHE_SellProject where fnumber='" + fdcProject + "') ");
			sellNumberSql.appendSql(sellNumberWhere.toString());
			IRowSet allReceiveSet1=sellNumberSql.executeQuery(serverCtx);
			
			while (allReceiveSet1.next())
			{
				money = allReceiveSet1.getBigDecimal("Amount");
			}
	
			if(money!= FDCHelper.ZERO && money != null)
			{
			rate = (new BigDecimal(visitors*10000)).divide(money, BigDecimal.ROUND_HALF_UP);
			}
			
		}
		else if(requestType.equalsIgnoreCase("visitRate")){
			

			StringBuffer firstVisit = new StringBuffer("select count(*) as Visitors from t_SHE_fdccustomer t1,t_she_receptionType t2 where t1.freceptionTypeid = t2.fid And t2.fName_l2 like '%首次来访%'");
			
			StringBuffer sellNumber = new StringBuffer("SELECT count(*) as sellNumber FROM T_SHE_ROOM t1 WHERE FSellState in ('Purchase')");
			
			
			firstVisitSql.appendSql(sellNumber.toString());
			firstVisitSql.appendSql(firstVisitWhere.toString());
			sellNumberSql.appendSql(firstVisit.toString());
			sellNumberSql.appendSql(sellNumberWhere.toString());
			IRowSet receptionSet = firstVisitSql.executeQuery(serverCtx);
			IRowSet sellSet = sellNumberSql.executeQuery(serverCtx);
			
			float firstVisitSum = 0.00f;
			float sellSum = 0.00f;
			
			try {
				while (sellSet.next()&&receptionSet.next()) {
					
					firstVisitSum =(float) sellSet.getFloat("firstVisit");
					sellSum =(float) receptionSet.getFloat("sellNumber");
					
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			if (firstVisitSum!=0&&sellSum!=0){
				rate = new BigDecimal(firstVisitSum/sellSum);
			}

		}
		else if(requestType.equalsIgnoreCase("phone"))
		{
	
			firstVisitSql.appendSql("select count(*) as Visitors from t_SHE_fdccustomer t1,t_she_receptionType t2 where t1.freceptionTypeid = t2.fid And t2.fName_l2 like '%首次来电%'");
			firstVisitSql.appendSql(firstVisitWhere.toString());
			IRowSet allReceiveSet=firstVisitSql.executeQuery(serverCtx);
	
			// 新来电的客户数
			while (allReceiveSet.next())
			{
				visitors = allReceiveSet.getInt("Visitors");
			}
			
			sellNumberSql.appendSql("select sum(famount) as Amount from T_MAR_MarketManage as t1,T_MAR_MarketManageEntry as t2 ");
			sellNumberSql.appendSql("where t1.fid=t2.fparentid and t1.fsellproject=(select fid from T_SHE_SellProject where fnumber='" + fdcProject + "') ");
			sellNumberSql.appendSql(sellNumberWhere.toString());
			IRowSet allReceiveSet1=sellNumberSql.executeQuery(serverCtx);
			
			while (allReceiveSet1.next())
			{
				money = allReceiveSet1.getBigDecimal("Amount");
			}
	
			if(money!= FDCHelper.ZERO && money != null)
			{
			rate = (new BigDecimal(visitors*10000)).divide(money, BigDecimal.ROUND_HALF_UP);
			}

		}
		return rate;
	}


}
