package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.TableManagerFacadeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DeductTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillConfirmEntryCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillConfirmEntryFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillEntryFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.finance.FDCPaymentBillFactory;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.report.util.DBUtil;
import com.kingdee.eas.framework.report.util.SqlParams;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.db.SQLUtils;

public class PaymentFacadeControllerBean extends AbstractPaymentFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.PaymentFacadeControllerBean");
	private boolean add;
//	在fetchPayData()里需要用到合同ID集合，为了不至于再循环遍历一次 contractBills 抽取为全局变量 by Cassiel_peng 
	private Set contractIdSet=new HashSet();
    protected Map _fetchInitData(Context ctx, Map paramMap)throws BOSException, EASBizException
    {
    	
		Map initMap = new HashMap();// FDCbillf.getLocalInstance(ctx).fetchInitData(ctx,paramMap);	
		String comId = null;
		String id = null;
		String fdcPayReqID = null;
		PaymentBillInfo billInfo = null;
		PaymentBillInfo lastBillInfo = null;
		boolean isExist = false;
		
		if(paramMap.get("ID")==null && paramMap.get("srcBillID")==null){
			return initMap;			
		}else 
		if(paramMap.get("ID")==null &&  paramMap.get("srcBillID")!=null){
			//return initMap;
			fdcPayReqID = paramMap.get("srcBillID").toString();
			
		}else{
			id = paramMap.get("ID").toString();			
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("contractBillId");		
			selector.add("fdcPayReqID");
			selector.add("createTime");
			selector.add("curProject.id");
			
			 billInfo = (PaymentBillInfo) PaymentBillFactory.getLocalInstance(ctx).getValue(new ObjectUuidPK(id),selector);
			 
				//付款申请单
			fdcPayReqID  = billInfo.getFdcPayReqID();
			if(fdcPayReqID==null){
				return null;
			}
		}
		//是否已存在付款单
		EntityViewInfo existView = new EntityViewInfo();
		existView.getSelector().add("createTime");
		FilterInfo existFilter = new FilterInfo();
		existFilter.getFilterItems().add(
				new FilterItemInfo("fdcPayReqID", fdcPayReqID));
		if (paramMap.get("createTime") != null) {
			existFilter.getFilterItems().add(
					new FilterItemInfo("createTime", billInfo.getCreateTime(), CompareType.LESS));
			//
		}else if(billInfo!=null&&billInfo.getCreateTime()!=null){
			existFilter.getFilterItems().add(
					new FilterItemInfo("createTime", billInfo.getCreateTime(), CompareType.LESS));
		}
		isExist = PaymentBillFactory.getLocalInstance(ctx).exists(existFilter);
		if (isExist) {
			initMap.put("isExist", Boolean.valueOf(true));
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			view.getSelector().add("createTime");
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("fdcPayReqID", fdcPayReqID));
			SorterItemInfo sort = new SorterItemInfo("createTime");
			sort.setSortType(SortType.DESCEND);
			view.getSorter().add(sort);
			lastBillInfo = PaymentBillFactory.getLocalInstance(ctx).getPaymentBillCollection(view).get(0);
		}
			
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("paymentType.number");
		sic.add("paymentType.name");
		sic.add("paymentType.id");
		sic.add("paymentType.payType.id");
		sic.add("completePrjAmt");
		sic.add("urgentDegree");
		sic.add("paymentProportion");
		sic.add("originalAmount");
		sic.add("amount");
		sic.add("payPartAMatlAmt");
		sic.add("payPartAMatlOriAmt");
		sic.add("createTime");
		sic.add("projectPriceInContract");
		sic.add("projectPriceInContractOri");
		sic.add("addProjectAmt");
		sic.add("curProject.id");
		sic.add("bookedDate");
		sic.add("isPay");
		sic.add("contractId");
		sic.add("isDifferPlace");
		sic.add("usage");
		sic.add("description");	
		sic.add("moneyDesc");	
		sic.add("attachment");	
		sic.add("supplier.id");
		sic.add("supplier.number");
		sic.add("supplier.name");
		sic.add("realSupplier.name");
		sic.add("prjPayEntry.*");
		sic.add("invoiceNumber");
		sic.add("invoiceAmt");
		sic.add("allInvoiceAmt");
		sic.add("invoiceDate");
		sic.add("contractPrice");
		
		sic.add(new SelectorItemInfo("period.number"));
		sic.add(new SelectorItemInfo("period.periodNumber"));
		sic.add(new SelectorItemInfo("period.periodYear"));
		
		// 计划项目
		sic.add("fdcDepConPayPlan.id");
		sic.add("fdcDepConPayPlan.name");
		sic.add("fdcDepConPayPlan.number");
		sic.add("newContractPayPlan.id");
		sic.add("newContractPayPlan.name");
		sic.add("newContractPayPlan.number");
		sic.add("bizDate");
		PayRequestBillInfo info = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(
				new ObjectUuidPK(BOSUuid.read(fdcPayReqID)),sic);
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.id",fdcPayReqID));
		view.setFilter(filter);
		PayRequestBillEntryCollection payReqEntry = PayRequestBillEntryFactory.getLocalInstance(ctx).getPayRequestBillEntryCollection(view);
		info.put("entrys",payReqEntry);
		info.put("prjPayEntry", info.getPrjPayEntry());
		initMap.put("PayRequestBillInfo",info);
		
		//合同单据
		String contractBillId = (String)paramMap.get("contractBillId");		
		if(contractBillId==null && billInfo!=null){			
			contractBillId = billInfo.getContractBillId();		
		}if(contractBillId==null && fdcPayReqID!=null){			
			contractBillId = info.getContractId();		
		}		
		
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("number");
		selector.add("name");
		selector.add("amount");
		selector.add("curProject.name");
		selector.add("curProject.number");
//		selector.add("curProject.longNumber");
//		selector.add("curProject.displayName");
//		selector.add("curProject.parent.id");
//		selector.add("curProject.fullOrgUnit.name");
		selector.add("curProject.CU.name");
		selector.add("curProject.CU.number");
//		selector.add("respDept.num");
//		selector.add("partB.*");
		if ((BOSUuid.read(contractBillId).getType()).equals(new ContractBillInfo().getBOSType())) {
			selector.add("addPrjAmtPaid");
			selector.add("prjPriceInConPaid");
			selector.add("paidPartAMatlAmt");
			ContractBillInfo contractBill = ContractBillFactory.getLocalInstance(ctx).
			getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractBillId)),selector);
			initMap.put(FDCConstants.FDC_INIT_CONTRACT,contractBill);
		}else{
			// 计划项目 added by masb 2010/09/17
			selector.add("fdcDepConPayPlanEntry.id");
			selector.add("fdcDepConPayPlanEntry.entrys1.id");
			selector.add("fdcDepConPayPlanEntry.entrys1.planPayAmount");
			selector.add("fdcDepConPayPlanEntry.entrys1.month");
			selector.add("fdcDepConPayPlan.id");
			selector.add("fdcDepConPayPlanMonth.*");
			ContractWithoutTextInfo contractBill = ContractWithoutTextFactory.getLocalInstance(ctx).
			getContractWithoutTextInfo(new ObjectUuidPK(BOSUuid.read(contractBillId)),selector);
			initMap.put(FDCConstants.FDC_INIT_CONTRACT,contractBill);
		}

		
		//工程项目
		selector = new SelectorItemCollection();
		selector.add("number");
		selector.add("name");
		selector.add("longNumber");
		selector.add("displayName");
		selector.add("parent.id");
		selector.add("fullOrgUnit.name");
		selector.add("CU.name");
		selector.add("CU.number");
		selector.add("costCenter");
		String projectId = info.getCurProject().getId().toString();
		CurProjectInfo curProjectInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(projectId),selector);		
		initMap.put(FDCConstants.FDC_INIT_PROJECT,curProjectInfo);
		
		comId = curProjectInfo.getFullOrgUnit().getId().toString();
		//财务组织
		CompanyOrgUnitInfo company =GlUtils.getCurrentCompany(ctx,comId,null,false);		
		initMap.put(FDCConstants.FDC_INIT_COMPANY,company);
		
		//本位币
		initMap.put(FDCConstants.FDC_INIT_CURRENCY,company.getBaseCurrency());
		
		//工程项目对应的组织
		String orgUnitId = null;
		if(curProjectInfo!=null &&  curProjectInfo.getCostCenter()!=null){
			orgUnitId = curProjectInfo.getCostCenter().getId().toString();
		}else{
			orgUnitId = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
		}
		FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getLocalInstance(ctx)
			.getFullOrgUnitInfo(new ObjectUuidPK(orgUnitId));
		//获得当前组织		
		initMap.put(FDCConstants.FDC_INIT_ORGUNIT,orgUnitInfo);
		

		
		//上次累计申请
		view = new EntityViewInfo();
		filter = new FilterInfo();
		FilterItemCollection items = filter.getFilterItems();
		items.add(new FilterItemInfo("createTime", info.getCreateTime(), CompareType.LESS_EQUALS));
		items.add(new FilterItemInfo("contractId",contractBillId));
		view.setFilter(filter);
		SorterItemInfo sorterItemInfo = new SorterItemInfo("createTime");
		sorterItemInfo.setSortType(SortType.DESCEND);
		view.getSorter().add(sorterItemInfo);
		view.getSelector().add("createTime");
		view.getSelector().add("prjAllReqAmt");
		view.getSelector().add("addPrjAllReqAmt");
		view.getSelector().add("payPartAMatlAllReqAmt");
		view.getSelector().add("lstAMatlAllPaidAmt");
		PayRequestBillCollection c1 = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection(view);
		initMap.put("PayRequestBillCollection",c1);
		
		//设置付款次数为合同的付款次数
		EntityViewInfo payview = new EntityViewInfo();
		 filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBillId", contractBillId));
		filter.getFilterItems().add(
				new FilterItemInfo("billStatus", BillStatusEnum.PAYED_VALUE
						+ ""));
//		if(lastBillInfo!=null&&lastBillInfo.getCreateTime()!=null){
//			filter.getFilterItems().add(
//					new FilterItemInfo("createTime", lastBillInfo.getCreateTime(),
//							CompareType.LESS));
//		}else{
//			filter.getFilterItems().add(
//					new FilterItemInfo("createTime", info.getCreateTime(),
//							CompareType.LESS));
//		}
		payview.setFilter(filter);
		payview.getSelector().add("amount");
		payview.getSelector().add("fdcPayReqID");
		PaymentBillCollection c2 = PaymentBillFactory.getLocalInstance(ctx).getPaymentBillCollection(payview);
		initMap.put("PaymentBillCollection",c2);
		
		//本申请单已付金额
		payview = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractBillId",contractBillId));
		filter.getFilterItems().add(
				new FilterItemInfo("fdcPayReqID", fdcPayReqID));
		filter.getFilterItems().add(
				new FilterItemInfo("billStatus", BillStatusEnum.PAYED_VALUE+ ""));
//		if(lastBillInfo!=null&&lastBillInfo.getCreateTime()!=null){
//			filter.getFilterItems().add(
//					new FilterItemInfo("createTime", lastBillInfo.getCreateTime(),
//							CompareType.LESS));
//		}else{
//			filter.getFilterItems().add(
//					new FilterItemInfo("createTime", info.getCreateTime(),
//							CompareType.LESS));
//		}
//		filter.getFilterItems().add(new FilterItemInfo("createTime", info.getCreateTime(),CompareType.LESS));

		payview.setFilter(filter);
		PaymentBillCollection c3 = PaymentBillFactory.getLocalInstance(ctx).getPaymentBillCollection(payview);
		initMap.put("PaymentBillCollection_hasPaid",c3);
				
		//是否已经有过其他付款单
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems()
				.add(new FilterItemInfo("parent", fdcPayReqID));
		filter.getFilterItems().add(
				new FilterItemInfo("paymentBill", id));
		view.setFilter(filter);
		PayRequestBillEntryCollection c4 = PayRequestBillEntryFactory.getLocalInstance(ctx).getPayRequestBillEntryCollection(view);
		initMap.put("PayRequestBillCollection_hasPaid",c4);
		
		//
		EntityViewInfo viewtemp = new EntityViewInfo();
		FilterInfo filtertemp = new FilterInfo();
		filtertemp.getFilterItems().add(new FilterItemInfo("parent", fdcPayReqID));			
		viewtemp.setFilter(filtertemp);
		
		PayRequestBillEntryCollection c5  = PayRequestBillEntryFactory
			.getLocalInstance(ctx).getPayRequestBillEntryCollection(viewtemp);
		initMap.put("PayRequestBillCollection_c5",c5);
		
		//奖励(本期)
		EntityViewInfo viewGe = new EntityViewInfo();
		FilterInfo filterGe = new FilterInfo();

		FilterItemCollection itemsGe = filterGe.getFilterItems();
		itemsGe.add(new FilterItemInfo("payRequestBill.id", fdcPayReqID));
		viewGe.setFilter(filterGe);
		viewGe.getSelector().add("amount");
		viewGe.getSelector().add("originalAmount");
		GuerdonOfPayReqBillCollection c = GuerdonOfPayReqBillFactory
			.getLocalInstance(ctx).getGuerdonOfPayReqBillCollection(viewGe);
		
		initMap.put("GuerdonOfPayReqBillCollection",c);
		
		//应包括本期数据
		//合同内申请的奖励(包括本次) 
		EntityViewInfo viewGeDe = new EntityViewInfo();
		FilterInfo filterGeDe = new FilterInfo();
		FilterItemCollection itemsGeDe = filterGeDe.getFilterItems();

		itemsGeDe.add(new FilterItemInfo("payRequestBill.contractId",
				contractBillId));
		itemsGeDe.add(new FilterItemInfo("payRequestBill.createTime",
				info.getCreateTime(), CompareType.LESS_EQUALS));
		viewGeDe.setFilter(filterGeDe);
		GuerdonOfPayReqBillCollection deGePay = GuerdonOfPayReqBillFactory
		.getLocalInstance(ctx).getGuerdonOfPayReqBillCollection(viewGeDe);
		initMap.put("GuerdonOfPayReqBillCollection_deGePay",deGePay);
		
		//合同内已付的奖励
		EntityViewInfo viewGePaid = new EntityViewInfo();
		FilterInfo filterGePaid = new FilterInfo();

		FilterItemCollection itemsGePaid = filterGePaid.getFilterItems();
		itemsGePaid.add(new FilterItemInfo("payRequestBill.contractId",
				contractBillId));
		itemsGePaid.add(new FilterItemInfo("hasPaid", Boolean.TRUE));
		itemsGePaid.add(new FilterItemInfo("payRequestBill.createTime",
				info.getCreateTime(), CompareType.LESS_EQUALS));
		viewGePaid.setFilter(filterGePaid);
		
		GuerdonOfPayReqBillCollection dePay = GuerdonOfPayReqBillFactory
		.getLocalInstance(ctx).getGuerdonOfPayReqBillCollection(
				viewGePaid);
		initMap.put("GuerdonOfPayReqBillCollection_dePay",dePay);
		
		//当前付款申请单已付的奖励
		EntityViewInfo viewGePay = new EntityViewInfo();
		FilterInfo filterGePay = new FilterInfo();
		FilterItemCollection itemsGePay = filterGePay.getFilterItems();
		itemsGePay.add(new FilterItemInfo("payRequestBill.id",	fdcPayReqID));
		itemsGePay.add(new FilterItemInfo("hasPaid", Boolean.TRUE));
		itemsGePay.add(new FilterItemInfo("paymentBill.id",id));
		itemsGePay.add(new FilterItemInfo("payRequestBill.createTime", info.getCreateTime(), CompareType.EQUALS));
		viewGePay.setFilter(filterGePay);
		GuerdonOfPayReqBillCollection getemp = GuerdonOfPayReqBillFactory
		.getLocalInstance(ctx).getGuerdonOfPayReqBillCollection(
				viewGePay);
		initMap.put("GuerdonOfPayReqBillCollection_getemp",getemp);
		
		//显示违约金(不包括本期) ---2009-2-6 应包括本期 时间比较应用LESS_EQUALS 用等于是为了显示本期的 by hpw 2009-07-18
		EntityViewInfo myview = new EntityViewInfo();
		FilterInfo myfilter = new FilterInfo();
		myfilter.getFilterItems().add(new FilterItemInfo("payRequestBill.contractId", contractBillId));
		// TODO 再加一个时间过滤
		Timestamp createTime = info.getCreateTime();
		myfilter.getFilterItems().add(
					new FilterItemInfo("payRequestBill.createTime",
							createTime, CompareType.LESS_EQUALS));
		myview.setFilter(myfilter);
		myview.getSelector().add("amount");
		myview.getSelector().add("originalAmount");
		myview.getSelector().add("payRequestBill.id");
		myview.getSelector().add("hasPaid");
		myview.getSelector().add("paymentBill.id");
		CompensationOfPayReqBillCollection comCol = CompensationOfPayReqBillFactory.getLocalInstance(ctx)
					.getCompensationOfPayReqBillCollection(myview);
		initMap.put("CompensationOfPayReqBillCollection",comCol);
		
		//扣款(本期)
		EntityViewInfo viewDePay = new EntityViewInfo();
		FilterInfo filterDePay = new FilterInfo();
		FilterItemCollection itemsDePay = filterDePay.getFilterItems();
		itemsDePay.add(new FilterItemInfo("payRequestBill.id",
				fdcPayReqID));
		itemsDePay.add(new FilterItemInfo("deductType", DeductTypeInfo.partAMaterialType, CompareType.NOTEQUALS));
		viewDePay.setFilter(filterDePay);
		SorterItemInfo sorterItem= new SorterItemInfo("deductType.number");
		sorterItemInfo.setSortType(SortType.ASCEND);
		viewDePay.getSorter().add(sorterItem);
		viewDePay.getSelector().add("deductType.number");
		viewDePay.getSelector().add("deductType.name");
		viewDePay.getSelector().add("*");
		DeductOfPayReqBillCollection deduCol = DeductOfPayReqBillFactory
		.getLocalInstance(ctx).getDeductOfPayReqBillCollection(
				viewDePay);
		initMap.put("DeductOfPayReqBillCollection",deduCol);
		
		DeductOfPayReqBillInfo deductInfo = null;
		int rows = deduCol.size();
		for (int i = 0; i < rows; i++) {
			deductInfo = deduCol.get(i);
			
			//合同内申请的扣款项(包括本期 by hpw)
			EntityViewInfo viewDe = new EntityViewInfo();
			FilterInfo filterDe = new FilterInfo();
			FilterItemCollection itemsDe = filterDe.getFilterItems();
			itemsDe.add(new FilterItemInfo("payRequestBill.contractId",contractBillId));
			itemsDe.add(new FilterItemInfo("deductType.number", deductInfo
						.getDeductType().getNumber()));
			itemsDe.add(new FilterItemInfo("payRequestBill.createTime",
						info.getCreateTime(), CompareType.LESS_EQUALS));
			viewDe.setFilter(filterDe);
			DeductOfPayReqBillCollection deduPay = DeductOfPayReqBillFactory
			.getLocalInstance(ctx).getDeductOfPayReqBillCollection(viewDe);
			
			deductInfo.put("DeductOfPayReqBillCollection_deduPay",deduPay);
			
			//合同内已付
			EntityViewInfo viewPaid = new EntityViewInfo();
			FilterInfo filterPaid = new FilterInfo();
			FilterItemCollection itemsPaid = filterPaid
					.getFilterItems();
			itemsPaid.add(new FilterItemInfo(
					"payRequestBill.contractId", contractBillId));
			itemsPaid.add(new FilterItemInfo("hasPaid", Boolean.TRUE));
			itemsPaid.add(new FilterItemInfo("deductType.number", deductInfo
					.getDeductType().getNumber()));
			itemsPaid.add(new FilterItemInfo(
					"payRequestBill.createTime", info
							.getCreateTime(), CompareType.LESS_EQUALS));
			viewPaid.setFilter(filterPaid);
			
			DeductOfPayReqBillCollection deduPay2 = DeductOfPayReqBillFactory
			.getLocalInstance(ctx).getDeductOfPayReqBillCollection(viewPaid);
			deductInfo.put("DeductOfPayReqBillCollection_deduPay2",deduPay2);
			
			//已付
			EntityViewInfo viewtempPay = new EntityViewInfo();
			FilterInfo filtertempPay = new FilterInfo();
			FilterItemCollection itemstempPay = filtertempPay
					.getFilterItems();
			itemstempPay.add(new FilterItemInfo("payRequestBill.id",
					fdcPayReqID));
			itemstempPay
					.add(new FilterItemInfo("hasPaid", Boolean.TRUE));
			itemstempPay.add(new FilterItemInfo("paymentBill.id",
					id));
			itemstempPay.add(new FilterItemInfo("deductType.id", deductInfo
					.getDeductType().getId()));
			viewtempPay.setFilter(filtertempPay);
			
			DeductOfPayReqBillCollection ctemp = DeductOfPayReqBillFactory
			.getLocalInstance(ctx).getDeductOfPayReqBillCollection(viewtempPay);
			deductInfo.put("DeductOfPayReqBillCollection_dctemp",ctemp);
			
			
		}
		
		//检查是否是新增的付款单
		EntityViewInfo paymentview = new EntityViewInfo();
		FilterInfo paymentfilter = new FilterInfo();
		paymentview.setFilter(paymentfilter);
		paymentfilter.getFilterItems().add(new FilterItemInfo("id", id));
		PaymentBillCollection paymentColl = PaymentBillFactory.getLocalInstance(ctx).getPaymentBillCollection(paymentview);
		initMap.put("PaymentBillCollection_paymentColl",paymentColl);
		
		//变更签证
		filter = new FilterInfo();
		filter.getFilterItems().add(
						new FilterItemInfo("contractBill.id",contractBillId));
		filter.getFilterItems().add(
						new FilterItemInfo("state",
								FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(
						new FilterItemInfo("state",
								FDCBillStateEnum.VISA_VALUE));
		filter.getFilterItems().add(
						new FilterItemInfo("state",
								FDCBillStateEnum.ANNOUNCE_VALUE));
		filter.setMaskString("#0 and (#1 or #2 or #3)");
				// 变更签证金额
		view = new EntityViewInfo();
		view.setFilter(filter);
		view.getSelector().add("amount");
		ContractChangeBillCollection collection = ContractChangeBillFactory
		.getLocalInstance(ctx)	.getContractChangeBillCollection(view);
		
		initMap.put("ContractChangeBillCollection",collection);
		
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		view.getSelector().add("*");
		view.getSelector().add("confirmBill.*");
		view.getSelector().add("confirmBill.mainContractBill.id");
		view.getSelector().add("confirmBill.mainContractBill.number");
		view.getSelector().add("confirmBill.mainContractBill.name");
		view.getSelector().add("confirmBill.materialContractBill.id");
		view.getSelector().add("confirmBill.materialContractBill.number");
		view.getSelector().add("confirmBill.materialContractBill.name");
		filter.getFilterItems().add(new FilterItemInfo("parent", fdcPayReqID));
		view.setFilter(filter);
		PayRequestBillConfirmEntryCollection mcBills = PayRequestBillConfirmEntryFactory.getLocalInstance(ctx).getPayRequestBillConfirmEntryCollection(view);
		
		initMap.put("PayRequestBillConfirmEntryCollection",mcBills);
		
		//发票金额
		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
		builder.appendSql("select sum(FInvoiceAmt) FAllInvoiceAmt from T_FNC_FDCPaymentBill where FPaymentBillId in ( ");
		builder.appendSql("	select fid from T_CAS_PaymentBill where FFdcPayReqID =  ?)");
		builder.addParam(fdcPayReqID);
		IRowSet rowSet=builder.executeQuery();
		try {
			while(rowSet.next()){
				initMap.put("FDCPaymentAllInvoicAmt", rowSet.getBigDecimal("FAllInvoiceAmt"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
        return initMap;
    }

	//获取计划执行请款数据
    protected Map _fetchPayPlanData(Context ctx, Map param) throws BOSException, EASBizException {
		
    	Map data = new HashMap();
    	Set set = new HashSet();
    	int dataType = ((Integer)param.get("dateType")).intValue(); 
    	int yearFrom = ((Integer)param.get("yearFrom")).intValue(); 
    	int yearTo = ((Integer)param.get("yearTo")).intValue(); 
    	int monthFrom = ((Integer)param.get("monthFrom")).intValue(); 
    	int monthTo = ((Integer)param.get("monthTo")).intValue(); 
    	set = (Set)param.get("projectIds");
    	if(set==null || set.size()==0){
    		return new HashMap();
    	}
    	Date beginDate = FDCUtils.getBeginQueryDate( dataType, yearFrom, monthFrom);
    	Date endDate = FDCUtils.getEndQueryDate( dataType, yearTo, monthTo);
    	StringBuffer temp = new StringBuffer("('null_null','"); //要处理set可能为空的情况 Added By Owen_wen 2010-12-20
    	for(Iterator it = set.iterator();it.hasNext();){
    		temp.append(it.next().toString());
    		temp.append("','");
    	}
   		String ids = temp.substring(0,temp.length()-2);    		
    	
    	ids = ids + ")";
//    	String prjId = (String)param.get("projectID");
    	String currencyId = (String)param.get("currencyId");
    	
		//获取一个临时表
		String tempTable = getTempTableName("FDC_payplan", ctx);
    	
		try {
			//合同
			ContractBillCollection contractBills = getContractColl(ctx,ids,currencyId,beginDate,endDate,tempTable);
			data.put("ContractBillCollection",contractBills);
			
			//执行情况
			Map result = fetchExecution( ctx, ids, beginDate, endDate, tempTable,dataType);
			data.put("fetchExecution",result);
			
			//付款计划执行表中增加三列:未付款、累计付款比例、已结算	 by Cassiel_peng 2009-11-16
			data.put("payData", fetchPayData(ctx,contractIdSet));
		}  catch (SQLException e) {		
			e.printStackTrace();
			throw new BOSException(e);
		} 	
    	
    	return data;
	}
   
    /**
     * 执行情况
     * @param desc
     * @param ctx
     * @return
     * @throws BOSException
     */
    protected Map  fetchExecution(Context ctx,String prgId,Date begin,Date end,String tempTable,int dataType) throws BOSException, SQLException,EASBizException {
    	Map data = new HashMap();
		String sql = "select a.FContractId,a.FPayDate,a.FPayAmount,a.FPAYEDAMT  from T_FNC_ContractPayPlan a" +
		//去掉内连接临时表查询 其会导致数据重复
		//		" inner join "+tempTable+" c on (a.FContractId =c.FContractId )" +
		" where FCurProjectID in "+prgId+"  and FPayDate>=? and FPayDate<=?";
		
		IRowSet rs = DbUtil.executeQuery(ctx,sql,new Object[]{new java.sql.Date(begin.getTime()),new java.sql.Date(end.getTime())});
		while(rs!=null && rs.next()){
			String contractId = rs.getString("FContractId");
			BigDecimal payAmt = rs.getBigDecimal("FPayAmount");
			//数据库中FPayedAmt为0
//			BigDecimal paidAmt = rs.getBigDecimal("FPayedAmt");
			Date payDate = rs.getDate("FPayDate");
			Calendar cal = Calendar.getInstance();
			cal.setTime(payDate);
			//计算当月已付金额
			BigDecimal paidAmt = getPaymentAmount(ctx,contractId,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH));
			if(payAmt==null){
				payAmt = FDCConstants.ZERO;
			}
			if(paidAmt==null){
				paidAmt = FDCConstants.ZERO;
			}
			
			//合同数据
			Map contract = null;
			if(!data.containsKey(contractId)){
				contract = new HashMap();
			}else{
				contract = (Map)data.get(contractId);					
			}
			data.put(contractId,contract);
			
			//每一个月份的金额数据
			String key = getKey(dataType,payDate );
			if((key!=null)&&(!contract.containsKey(key))){
				Map amtMap = new HashMap();
				
				amtMap.put("FPayAmount",payAmt);
				amtMap.put("FPayedAmt",paidAmt);
				
				contract.put(key,amtMap);
			}else{
				Map amtMap = (Map)contract.get(key);
				
				BigDecimal FPayAmount = (BigDecimal)amtMap.get("FPayAmount");
				BigDecimal FPayedAmt = (BigDecimal)amtMap.get("FPayedAmt");
				
				amtMap.put("FPayAmount",payAmt.add(FPayAmount));
				amtMap.put("FPayedAmt",paidAmt.add(FPayedAmt));
				
				contract.put(key,amtMap);
			}			
		}
		
		return data;
    }
    
    //根据查询的类型，获取不同的key值
    protected String getKey(int dataType,Date payDate ){
    	String key = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(payDate);
		//取数
		if(dataType==1){
			key = ""+cal.get(Calendar.YEAR)+ (cal.get(Calendar.MONTH)+1);
		}else if(dataType==2){
			key = ""+cal.get(Calendar.YEAR)+ (cal.get(Calendar.MONTH)/3+1);
		}else if(dataType==3){
			key = ""+cal.get(Calendar.YEAR);
		}
		
		return key;
    }
    //计算当月已付金额
    protected static BigDecimal getPaymentAmount(Context ctx,String contractId, int year,
			int month) throws BOSException{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, 1);
		Date beginDate = DateTimeUtils.truncateDate(cal.getTime());
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		Date endDate = DateTimeUtils.truncateDate(cal.getTime());
		return getPaymentAmount(ctx,contractId, beginDate, endDate);
    }
    
    /**
     *   计算当月已付金额
     *   @Modified By Owen_wen 由于Bug提单PBG065661 与 需求提单R100524-069 有冲突，经过与赵常旭、周鹏和王芬讨论后，
     *   改回用付款日期PayDate做为统计字段。
     */
    protected static BigDecimal getPaymentAmount(Context ctx,String contractId, Date beginDate,Date endDate) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("amount");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("contractBillId", contractId));
		
		if (beginDate != null) {
			filter.getFilterItems().add(new FilterItemInfo("payDate", beginDate, CompareType.GREATER_EQUALS));
		}
		filter.getFilterItems().add(new FilterItemInfo("payDate", endDate, CompareType.LESS));
		filter.getFilterItems().add(new FilterItemInfo("billStatus", new Integer(BillStatusEnum.PAYED_VALUE)));
		PaymentBillCollection payRequestBillCollection = PaymentBillFactory.getLocalInstance(ctx).getPaymentBillCollection(view);
		BigDecimal amount = null;
		for (int i = 0; i < payRequestBillCollection.size(); i++) {
			PaymentBillInfo info = payRequestBillCollection.get(i);
			if (amount == null) {
				amount = FDCHelper.ZERO;
			}
			amount = amount.add(info.getAmount());
		}
		return amount;
    }
	protected String getTempTableName(String desc,Context ctx)	throws BOSException {		
		return TableManagerFacadeFactory.getLocalInstance(ctx).getTableName(desc);		
	}
	
	protected int executeCreateAsSelectInto(String sql,SqlParams parameters, Context ctx)throws BOSException {
		Connection con = this.getConnection(ctx);
		try{
			return DBUtil.executeSelectInto(sql,parameters,con);
		} catch (SQLException e) {
			e.setNextException(new SQLException("SQL: " +sql));
			logger.error("Execute select into failed.("+sql+")",e);
			throw new SQLDataException(e);
		} finally {	
			SQLUtils.cleanup(con);
		}
	}
	 /**
	 * 付款计划执行表中增加三列							<p>
	 * 未付款:合同最新造价-该合同的累计已付款			    <p>
	 * 累计付款比例:该合同的累计已付款/合同最新造价*100%	<p>
	 * 已结算:合同是否已最终结算							<p>
	 * by Cassiel_peng   2009-11-16
	 * @throws BOSException 
	 */
    protected Map fetchPayData(Context ctx,Set contractIdSet) throws BOSException{
    	Map  payDataMap = new HashMap();
    	if(contractIdSet!=null&&contractIdSet.size()>0){
    		FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
    		builder.appendParam("select FContractBillId, sum(FActualPayAmount) amount from t_cas_paymentbill where FContractBillId  \n ",contractIdSet.toArray());
    		builder.appendSql(" and FbillStatus =? \n ");
    		builder.addParam(new Integer(BillStatusEnum.PAYED_VALUE));
    		builder.appendSql(" group by FContractBillId ");
    		IRowSet rowSet=builder.executeQuery();
    		try {
    			while(rowSet.next()){
    				payDataMap.put(rowSet.getString("FContractBillId"), rowSet.getBigDecimal("amount"));//合同ID为键
    			}
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}
		return payDataMap;
    }
	
	//合同
	private ContractBillCollection getContractColl(Context ctx,String prgId,String curId,Date begin,Date end,String tempTable)
			throws BOSException, SQLException,EASBizException {
			
		String sql = "select FContractId into "+tempTable+" from T_FNC_ContractPayPlan " +
			" where FCurProjectID in  " +prgId +
			(curId!=null?" and  FCurrencyId=? ":"" )+
			" and FPayDate>=? and FPayDate<=?";
		
		SqlParams sp = new SqlParams();
		//sp.addString(prgId);
		if(curId!=null){
			sp.addString(curId);
		}
		sp.addDate(new java.sql.Date(begin.getTime()));
		sp.addDate(new java.sql.Date(end.getTime()));
		//生成临时表
		executeCreateAsSelectInto( sql, sp,  ctx);		

		
		String selectSql = "select a.fid id,a.Fnumber conNumber,a.Fname conName,a.FAmount amount,a.Fsettleamt settleamt ,a.Fhassettled hassettled," +
				"b.Fname_l2 bName,b.fid bId,d.fname_l2 dName" +
				//2008-8-27日，杨文  增加取原币金额
				" ,a.FOriginalAmount oriAmount"+
				//2008-8-29日 杨文 增加工程项目名称
				" ,p.Fid curprojectId,p.FDisplayName_l2 curprojectName"+
				" From T_Con_ContractBill a " +
				" inner join T_BD_Currency b on b.fid=a.FCurrencyID " +
				" inner join "+tempTable+" c on a.fid=c.FContractId  " +
				" inner join T_FDC_CurProject p on a.FCurProjectId=p.Fid "+
				" inner join T_FDC_ContractType d on d.fid=a.FContractTypeID " +
						"ORDER BY a.fnumber";

		IRowSet rs = DbUtil.executeQuery(ctx,selectSql);
		
		ContractBillCollection contractBills = new ContractBillCollection();
		if(contractIdSet==null){
			contractIdSet=new HashSet();
		}
		while(rs!=null && rs.next()){
			ContractBillInfo bill= new ContractBillInfo();
			//在fetchPayData()里需要用到合同ID集合，为了不至于再循环遍历一次 contractBills 抽取为全局变量 by Cassiel_peng 
			String contractId = rs.getString("id");
			contractIdSet.add(contractId);
			
			bill.setId(BOSUuid.read(contractId));
			bill.setNumber(rs.getString("conNumber"));
			bill.setName(rs.getString("conName"));
			bill.setAmount(rs.getBigDecimal("amount"));
			bill.setOriginalAmount(rs.getBigDecimal("oriAmount"));
			//取工程项目名称
			CurProjectInfo proInfo = new CurProjectInfo();
			String curProjectId = rs.getString("curprojectId");
			proInfo.setId(BOSUuid.read(curProjectId));
			proInfo.setDisplayName(rs.getString("curprojectName"));
			bill.setCurProject(proInfo);
			boolean hasSettle = rs.getBoolean("hassettled");
			//取最新造价
			if(hasSettle){
				bill.setSettleAmt(rs.getBigDecimal("settleamt"));
			}else{
				bill.setSettleAmt(FDCUtils.getContractLastPrice(ctx,contractId,true));
			}
			//付款计划执行表中增加了此列取数：已结算 by Cassiel_peng
			bill.setHasSettled(hasSettle);					
			
			CurrencyInfo cur = new CurrencyInfo();
			cur.setName(rs.getString("bName"));
			cur.setId(BOSUuid.read(rs.getString("bId")));
			
			ContractTypeInfo cType = new ContractTypeInfo();
			cType.setName(rs.getString("dName"));
			
			bill.setCurrency(cur);	
			bill.setContractType(cType);
			contractBills.add(bill);					
		}
		
		
		return contractBills;
	}
}