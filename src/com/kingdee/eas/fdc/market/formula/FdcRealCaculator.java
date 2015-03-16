package com.kingdee.eas.fdc.market.formula;
/*
 * @(#)FdcRealCaculator.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */


import java.math.BigDecimal;
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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.IContractBill;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.IPaymentBill;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;
import com.kingdee.eas.ma.budget.BgPeriodFactory;
import com.kingdee.eas.ma.budget.BgPeriodInfo;
import com.kingdee.eas.ma.budget.IBgPeriod;

/**
 * 描述:
 * 
 * @author wangweidong date:2008-6-19
 *         <p>
 * @version EAS6.0
 */
public class FdcRealCaculator implements ICalculator, IMethodBatchQuery {

	private ICalculateContextProvider context;

	private Context serverCtx = null;
	
	/** 房地产合同接口 */
	private IContractBill iContractBill;

	/** 房地产付款单接口 */
	private IPaymentBill iPaymentBill;

	//工程项目编码
	private String fdcProjectNumber;
	

	
	//期间编码
	private String periodBeginNumber;
	private String periodEndNumber;

	public FdcRealCaculator() {

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

		SortedParameterArray params = (SortedParameterArray)methods.get("fdc_visitrate");
		if(params!=null){
			for(int i=0;i<params.size();i++)
			{
			Parameter param = params.getParameter(i);
			Object[] obj = param.getArgs();
			
			fdcProjectNumber = (String)((Variant)obj[1]).getValue();
//			paymentTypeNumber = (String)((Variant)obj[2]).getValue();
			periodBeginNumber = (String)((Variant)obj[2]).getValue();
			periodEndNumber = (String)((Variant)obj[3]).getValue();
		
		
		try {
			BigDecimal amount = fdc_real(null, fdcProjectNumber,periodBeginNumber,periodEndNumber);
			params.getParameter(i).setValue(amount);
		} catch (EASBizException e) {
			e.printStackTrace();
			return false ;
		} catch (BOSException e) {
			e.printStackTrace();
			return false ;
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
	

	private IContractBill getIContractBill() throws BOSException {
		return iContractBill != null ? iContractBill : (iContractBill = (serverCtx != null ? ContractBillFactory.getLocalInstance(serverCtx)
				: ContractBillFactory.getRemoteInstance()));
	}

	private IPaymentBill getIPaymentBill() throws BOSException {
		return iPaymentBill != null ? iPaymentBill : (iPaymentBill = (serverCtx != null ? PaymentBillFactory.getLocalInstance(serverCtx) : PaymentBillFactory
				.getRemoteInstance()));
	}

	private IBgPeriod getBgPeriod() throws BOSException {
		return serverCtx == null ? BgPeriodFactory.getRemoteInstance() : BgPeriodFactory.getLocalInstance(serverCtx);
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
	 */
	public BigDecimal fdc_real(String xx, String fdcProject, String periodBegin, String periodEnd) throws BOSException, EASBizException {

		// 变量定义
		IBgPeriod iBgPeriod = getBgPeriod();

		BgPeriodInfo info;
		Date endDate, beginDate;
		EntityViewInfo evi;
		FilterInfo filter;
		BigDecimal sum = FDCHelper.ZERO;
		//
		// 取得开始期间
		String oql = "SELECT * WHERE number = '" + periodBegin + "'";
		info = iBgPeriod.getBgPeriodInfo(oql);
		beginDate = info.getBeginDate();

		// 取得结束期间
		oql = "SELECT * WHERE number = '" + periodEnd + "'";
		info = iBgPeriod.getBgPeriodInfo(oql);

		endDate = info.getEndDate();

		evi = new EntityViewInfo();
		filter = new FilterInfo();
		String ln = fdcProject.replace('.', '!');
		filter.getFilterItems().add(new FilterItemInfo("curProject.longNumber", ln));

		evi.getSelector().add("*");
		evi.setFilter(filter);
		ContractBillCollection contractBillCollection = getIContractBill().getContractBillCollection(evi);
		Set set = new HashSet();

		for (int j = 0; j < contractBillCollection.size(); j++) {
			set.add(contractBillCollection.get(j).getId().toString());
		}
		if (set.size() < 1)
			return sum;

		// 获取付款单
		evi = new EntityViewInfo();
		filter = new FilterInfo();

		filter.getFilterItems().add(new FilterItemInfo("billStatus", BillStatusEnum.PAYED));
		filter.getFilterItems().add(new FilterItemInfo("payDate", beginDate, CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("payDate", endDate, CompareType.LESS));
//		filter.getFilterItems().add(new FilterItemInfo("fdcPayType.number", paymentType));
		filter.getFilterItems().add(new FilterItemInfo("contractBillId", set, CompareType.INCLUDE));
		evi.setFilter(filter);
		evi.getSelector().add(new SelectorItemInfo("*"));
		PaymentBillCollection pbc = getIPaymentBill().getPaymentBillCollection(evi);
		if (pbc != null) {
			for (int j = 0; j < pbc.size(); j++) {
				sum = sum.add(pbc.get(j).getActPayAmt());
			}
		}
		return sum;
	}

}
