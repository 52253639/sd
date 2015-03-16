package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.datasource.DSParam;
import com.kingdee.bos.ctrl.kdf.expr.Variant;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.eas.fdc.sellhouse.BizTimeEnum;
import com.kingdee.eas.fdc.sellhouse.LoanCalculateTypeEnum;
import com.kingdee.eas.fdc.sellhouse.LoanDataEntryCollection;
import com.kingdee.eas.fdc.sellhouse.LoanDataEntryInfo;
import com.kingdee.eas.fdc.sellhouse.LoanDataInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RateExpression;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;

public class SimulantPrintDataProvider extends FDCBillDataProvider {
	private static Logger log = Logger
			.getLogger(SimulantPrintDataProvider.class);

	static public final String[] columnName = new String[] { "payDate",
			"payName", "amount", "currency" };

	private BigDecimal eareatMoney = FDCHelper.ZERO;
	private BigDecimal contractAmount = FDCHelper.ZERO;
	private BigDecimal countAmount = FDCHelper.ZERO;
	private IMetaDataPK qpk = null;
	private String id = null;
	private HashMap map = null;

	public SimulantPrintDataProvider(String id, IMetaDataPK qpk) {
		super(id, qpk);
		this.id = id;
		this.qpk = qpk;
	}

	public SimulantPrintDataProvider(String id, IMetaDataPK qpk, HashMap map) {
		 super(id, qpk);
		this.id = id;
		this.qpk = qpk;
		this.map = map;
		contractAmount = map.get("contractAmount") == null ? FDCHelper.ZERO
				: (BigDecimal) map.get("contractAmount");
	}

	public IRowSet execute(BOSQueryDataSource ds) {
		Variant paramVal = null;
		ArrayList ps = ds.getParams();
		IRowSet iRowSet = null;

		if (ps.size() > 0) {
			DSParam param = (DSParam) ps.get(0);
			paramVal = param.getValue();
		}
		if ("MAINBILL".equals(ds.getID().toUpperCase()))// 假设主数据源名称为mainbill
		{
			// 返加主数据源的结果集
			iRowSet = getMainBillRowSet(ds);
		} else if ("PAYLIST".equals(ds.getID().toUpperCase())) {
			return getOtherSubRowSet(ds);
		}
		// 如果套打的元数据大于2个执行下面的函数
		else {
			return getOtherSubRowSet2(ds);
		}
		return iRowSet;
	}

	// 查询主元数据
	public IRowSet getMainBillRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		try {
			IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(qpk);
			exec.option().isAutoTranslateEnum = true;
			EntityViewInfo ev = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("id", id, CompareType.EQUALS));
			ev.setFilter(filter);
			exec.setObjectView(ev);
			// System.out.println(exec.getSQL());
			iRowSet = exec.executeQuery();
			SHEPayTypeInfo payTypeInfo = map.get("payType") == null ? null
					: (SHEPayTypeInfo) map.get("payType");
			LoanDataInfo loanDataInfo = map.get("loanData") == null ? null
					: (LoanDataInfo) map.get("loanData");
			String agioDes = (String) map.get("agioDes");
			if (iRowSet.next()) {
				if (payTypeInfo == null) {
					iRowSet.updateString("payTypeName", null);
				} else {
					iRowSet.updateString("payTypeName", payTypeInfo.getName());
				}
				if (loanDataInfo == null) {
					iRowSet.updateString("loanDataName", null);
				} else {
					iRowSet
							.updateString("loanDataName", loanDataInfo
									.getName());
				}
				iRowSet.updateString("agioDes", agioDes);
				iRowSet.updateBigDecimal("attachAmount", map
						.get("attachAmount") == null ? FDCHelper.ZERO
						: (BigDecimal) map.get("attachAmount"));
				iRowSet.updateBigDecimal("fitmentAmount", map
						.get("fitmentAmount") == null ? FDCHelper.ZERO
						: (BigDecimal) map.get("fitmentAmount"));
				iRowSet.updateBigDecimal("areaCompensateAmount", map
						.get("areaComAmount") == null ? FDCHelper.ZERO
						: (BigDecimal) map.get("areaComAmount"));
				iRowSet.updateBigDecimal("contractAmount", map
						.get("contractAmount") == null ? FDCHelper.ZERO
						: (BigDecimal) map.get("contractAmount"));
			}
			iRowSet.beforeFirst();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}

		return iRowSet;
	}

	//传入付款明细元数据的的PK
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds) {
		IRowSet iRowSet = null;
		SHEPayTypeInfo payTypeInfo = map.get("payType") == null ? null
				: (SHEPayTypeInfo) map.get("payType");
		try {
			IQueryExecutor exec = QueryExecutorFactory
					.getRemoteInstance(new MetaDataPK(
							"com.kingdee.eas.fdc.sellhouse.app.SimulantPayListQuery"));
			exec.option().isAutoTranslateEnum = true;
			EntityViewInfo ev = new EntityViewInfo();
			ev.getSorter().add(new SorterItemInfo("seq"));
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(
					new FilterItemInfo("head.id", payTypeInfo.getId()
							.toString(), CompareType.EQUALS));
			ev.setFilter(filter);
			exec.setObjectView(ev);
			// System.out.println(exec.getSQL());
			iRowSet = exec.executeQuery();
			iRowSet.beforeFirst();
		} catch (BOSException e) {
			log.error(e);
			e.printStackTrace();
		} catch (SQLException e) {
			log.error(e);
			e.printStackTrace();
		}
		return iRowSet;
	}
	
	private DynamicRowSet getRowSet()
	{
		int colCount = columnName.length;
		DynamicRowSet drs = null;
		try {
			drs = new DynamicRowSet(colCount);
			for (int i = 0; i < colCount; i++) {
				ColInfo ci = new ColInfo();
				ci.colType = Types.VARCHAR;
				ci.columnName = columnName[i];
				ci.nullable = 1;
				drs.setColInfo(i + 1, ci);
			}
			drs.beforeFirst();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return drs;
	}
	
	//付款计划表元数据
	public IRowSet getOtherSubRowSet2(BOSQueryDataSource ds) {
		DynamicRowSet drs = this.getRowSet();
		SHEPayTypeInfo payTypeInfo = map.get("payType") == null ? null
				: (SHEPayTypeInfo) map.get("payType");
			
		//付款明细分录
		PayListEntryCollection coll = getPayListCollection(payTypeInfo.getId().toString());		
		for (int i = 0; i < coll.size(); i++) {
			PayListEntryInfo payInfo = (PayListEntryInfo) coll.get(i);
			try {
				BigDecimal amount = this.getAmount(payInfo, coll.size(), i);
				Date curDate = getDate(payInfo);
				String payDate = FDCDateHelper.DateToString(curDate);
				drs.moveToInsertRow();
				drs.updateString("payDate", payDate);
				drs.updateString("payName", payInfo.getMoneyDefine().getName());
				drs.updateBigDecimal("amount", amount);
				drs.updateString("currency", payInfo.getCurrency().getName());
				drs.insertRow();
				if(MoneyTypeEnum.LoanAmount.equals(payInfo.getMoneyDefine().getMoneyType())
						|| MoneyTypeEnum.AccFundAmount.equals(payInfo.getMoneyDefine().getMoneyType()))
				{			
					LoanDataInfo loanDataInfo = map.get("loanData") == null ? null
							: (LoanDataInfo) map.get("loanData");

					BigDecimal loanInterestRate = this.getLoanInterestRate(loanDataInfo, payTypeInfo, amount);
					//分期
					int term = map.get("term") == null ? 0 : ((Integer) map
							.get("term")).intValue();
					//分期间隔
					int monthInterval = map.get("monthInterval") == null ? 0 : ((Integer) map
							.get("monthInterval")).intValue();
					for (int j = 0; j < term; j++) {
						LoanCalculateTypeEnum loanCalculate = null;
						if(MoneyTypeEnum.LoanAmount.equals(payInfo.getMoneyDefine().getMoneyType()))
						{
							loanCalculate = payTypeInfo.getLoanCalculateType();
						}else
						{
							loanCalculate = payTypeInfo.getAccumulationCalculateType();
						}
						BigDecimal termAmount = FDCHelper.ZERO;
						if (loanCalculate
								.equals(LoanCalculateTypeEnum.DescendingAmount)) {
							termAmount = RateExpression.getAmountPrincipal(
									amount, term, loanInterestRate, j);
							countAmount = countAmount.add(termAmount);
						} else {
							termAmount = RateExpression.getAmountAccrual(
									amount, term, loanInterestRate);
							countAmount = countAmount.add(termAmount);
						}
						
						Date mondyNameDate = this.getPayDate(curDate, j, monthInterval);
						drs.moveToInsertRow();
						drs.updateString("payDate",FDCDateHelper.DateToString(mondyNameDate));
						drs.updateString("payName", payInfo.getMoneyDefine()
								.getName()
								+ (j + 1) + "期");
						drs.updateBigDecimal("amount", termAmount);
						drs.updateString("currency", payInfo.getCurrency()
								.getName());
						drs.insertRow();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			drs.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return drs;
	}
	
	//付款明细分录
	private PayListEntryCollection getPayListCollection(String payTypeID)
	{
		EntityViewInfo ev = new EntityViewInfo();
		ev.getSorter().add(new SorterItemInfo("seq"));
		ev.getSelector().add("*");
		ev.getSelector().add("moneyDefine.*");
		ev.getSelector().add("currency.name");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("head.id", payTypeID,CompareType.EQUALS));
		ev.setFilter(filter);
		PayListEntryCollection coll = null;
		try {
			coll = PayListEntryFactory.getRemoteInstance()
					.getPayListEntryCollection(ev);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return coll;
	}
	
	//计算付款金额
	private BigDecimal getAmount(PayListEntryInfo payInfo,int payListSize,int i)
	{
		BigDecimal amount = FDCHelper.ZERO;

		if (payInfo.getValue() != null) {
			amount = payInfo.getValue();
		} else {
			BigDecimal proportion = payInfo.getProportion();
			amount = map.get("contractAmount") == null ? FDCHelper.ZERO
					: (BigDecimal) map.get("contractAmount");
			amount = amount.multiply(proportion).divide(
					new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
		}
		MoneyDefineInfo moneyDefine = payInfo.getMoneyDefine();
		if (moneyDefine.getMoneyType().equals(
				MoneyTypeEnum.EarnestMoney)) {
			eareatMoney = eareatMoney.add(amount);
		} else {
			amount = amount.subtract(eareatMoney);
			eareatMoney = FDCHelper.ZERO;
			if (amount.compareTo(FDCHelper.ZERO) < 0) {
				amount = FDCHelper.ZERO;
			}
		}

		if (amount.compareTo(contractAmount) > 0) {
			amount = contractAmount;
			contractAmount = FDCHelper.ZERO;
		} else {
			contractAmount = contractAmount.subtract(amount);
		}
		if (i ==payListSize - 1) {
			amount = amount.add(contractAmount);
		}
		return amount;
	}
	
	//付款明细日期
	private Date getDate(PayListEntryInfo payInfo)
	{
		Date curDate = new Date();
		if (payInfo.getBizTime().equals(BizTimeEnum.AppTime)) {
			curDate = payInfo.getAppDate();
		} else {
			curDate = new Date();
			int monthLimit = payInfo.getMonthLimit();
			int dayLimit = payInfo.getDayLimit();
			Calendar cal = Calendar.getInstance();
			cal.setTime(curDate);
			cal.add(Calendar.MONTH, monthLimit);
			cal.add(Calendar.DATE, dayLimit);
			curDate = cal.getTime();
		}
		return curDate;
	}
	
	//贷款利率
	private BigDecimal getLoanInterestRate(LoanDataInfo loanDataInfo,SHEPayTypeInfo payTypeInfo,BigDecimal amount )
	{
		// 贷款年限
		int yearCount = loanDataInfo.getLoanFixedYear();
		// 贷款利率
		BigDecimal loanInterestRate = FDCHelper.ZERO;
		LoanDataEntryCollection loanColl = (LoanDataEntryCollection) loanDataInfo
				.getEntrys();
		int min = 0;
		if (loanColl.size() > 0) {
			int fixedYear = loanColl.get(0).getFixedYear();
			min = Math.abs(yearCount - fixedYear);
			loanInterestRate = loanColl.get(0).getInterestRate();
		}
		for (int k = 0; k < loanColl.size(); k++) {
			LoanDataEntryInfo loanInfo = loanColl.get(k);
			int fixedYear = loanInfo.getFixedYear();
			if (yearCount == fixedYear) {
				loanInterestRate = loanInfo.getInterestRate();
				break;
			} else if (min > Math.abs(yearCount - fixedYear)) {
				min = Math.abs(yearCount - fixedYear);
				loanInterestRate = loanInfo.getInterestRate();
			}
		}
		return loanInterestRate;
	}
	
	//付款计划日期
	private Date getPayDate(Date curDate,int j,int monthInterval)
	{
		Calendar cal = Calendar.getInstance();
		if (j == 0) {
			cal.setTime(curDate);
		} else {

			SimpleDateFormat simpleDate = new SimpleDateFormat(
					"yyyy-MM-dd");
			Date date = null;
			try {
				date = simpleDate.parse(FDCDateHelper.DateToString(curDate));
			} catch (Exception e) {
			}
			cal.setTime(date);
			cal.add(Calendar.MONTH, monthInterval * j);
		}
		return cal.getTime();
	}
}
