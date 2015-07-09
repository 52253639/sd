package com.kingdee.eas.fdc.contract.client;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.multiapprove.MultiApproveCollection;
import com.kingdee.eas.base.multiapprove.MultiApproveFactory;
import com.kingdee.eas.base.multiapprove.MultiApproveInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.DeductTypeCollection;
import com.kingdee.eas.fdc.basedata.DeductTypeFactory;
import com.kingdee.eas.fdc.basedata.DeductTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillEntryCollection;
import com.kingdee.eas.fdc.contract.ContractBillEntryFactory;
import com.kingdee.eas.fdc.contract.ContractBillEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.FeeTypeInfo;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;
import com.kingdee.util.UuidException;

public class PayRequestBillRowsetProvider extends FDCBillDataProvider {
	private static final Logger logger = Logger.getLogger(PayRequestBillRowsetProvider.class);

	static public final String[] col = new String[] { "curProject.name",
			"curProject.number", "useDepartment.name", "useDepartment.number",
			"contractNo", "bookedDate", "bizDate", "number", "payDate",
			"period.periodNumber", "period.number", "paymentType.name",
			"paymentType.number", "isDifferPlace", "settlementType.name",
			"settlementType.number", "recBank", "description", "supplier.name",
			"realSupplier.name", "recAccount", "usage", "currency.name",
			"currency.number", "exchangeRate", "attachment", "originalAmount",
			"paymentProportion", "amount", "grtAmount", "completePrjAmt",
			"capitalAmount", "moneyDesc", "urgentDegree", "isPay",
			"createTime", "creator.name", "creator.number", "auditTime",
			"auditor.number", "auditor.name", "contractName", "contractPrice",
			"latestPrice", "addProjectAmt", "changeAmt", "payedAmt",
			"payPartAMatlAmt", "projectPriceInContract", "scheduleAmt",
			"curPlannedPayment", "curBackPay", "paymentPlan", "curReqPercent",
			"allReqPercent", "imageSchedule", "curPaid", "prjAllReqAmt",
			"addPrjAllReqAmt", "payPartAMatlAllReqAmt", "lstPrjAllReqAmt",
			"lstAddPrjAllReqAmt", "lstAMatlAllReqAmt", "contractId",
			"hasPayoff", "lstPrjAllPaidAmt", "lstAddPrjAllPaidAmt",
			"lstAMatlAllPaidAmt", "costAmount", "settleAmt", "payTimes",
			"lstGuerdonPaidAmt", "lstGuerdonReqAmt", "guerdonAmt",
			"allGuerdonReqAmt", "allGuerdonPaidAmt", "lstCompensationPaidAmt",
			"lstCompensationReqAmt", "compensationAmt",
			"allCompensationReqAmt", "allCompensationPaidAmt", "prjAllPaidAmt",
			"deductType", "lstDeductPaidAmt", "lstDeductReqAmt", "deductAmt",
			"allDeductReqAmt", "allDeductPaidAmt", "restAmount",
			"lstRealPaidAmt", "allPartAMatlAllPaidAmt", "lstDeductSumPaidAmt",
			"lstDeductSumReqAmt", "deductSumAmt", "allDeductSumReqAmt",
			"allDeductSumPaidAmt",
			"lstRealReqAmt",
			"allRealReqAmt",
			"allRealPaidAmt",
			"auditResult",
			"orgName",
			"oldProjNumber",
			"paidDataAmount",
			"projNameWithoutOrg",
			"paidDetail",
			
			"id",
			"contractBillAmount",//合同原币金额
			"contractBillType",//合同类型
			"totalSettlePrice",//已实现产值
			"allCompletePrjAmt",
			"LstPrjAllPaidAmtAndthis",
			"latestPriceSub",
			// 长城:付款申请单套打时支持获取以下字段的原币  by Cassiel_peng  2009-10-9
			"contractOriPrice",//合同造价原币
			"changeOriAmt",//变更签证原币
			"latestOriPrice",//最新造价原币
			"grtOriAmount",//保修金原币
			"lstPrjAllPaidOriAmt",//上期合同内累计实付原币
			"lstPrjAllReqOriAmt",//合同内工程款上期累计申请原币
			"settleOriAmt",//结算金额本币
			"projectPriceInContractOri",//合同内工程款原币（本期发生）
			"guerdonOriAmt",//奖励本期发生原币
			"compensationOriAmt",//违约本期发生原币
			"deductOriAmt",//扣款本期发生原币
			"payPartAMatlOriAmt",//甲供材本期发生原币
			"thisTimeReqLocalAmt",//本期申请本币金额,
			"process",//形象进度描述
			"invoiceNumber", //发票号     
			"invoiceDate", //开票日  
			"invoiceAmt", //本次发票金额      Added by Owen_wen 2010-09-24
			"allInvoiceAmt", //累计发票金额
			"conGrtAmount",//合同保修金
			"conGrtOriAmount",//合同保修金原币
			"conGrtRate",//合同保修金比例
			"applier",
			"costedDept",
			"costedCompany",
			"payBillType",
			"payContentType",
			"requestAmount",
			"bgItem.number",
			"bgItem.name",
			"accountView.number",
			"accountView.name",
			"accountView.longNumber",
			"accountView.longName",
			"agentCompany.name",
			"payNo",
	};

	public static String printStringHelper(Object o) {
		if (o == null)
			return "";
		else if(o instanceof BigDecimal){
			if(FDCHelper.ZERO.compareTo(FDCHelper.toBigDecimal(o))==0)
				return "";
			else
				return String.valueOf(((BigDecimal)o).setScale(2,BigDecimal.ROUND_HALF_UP));
		}
			return String.valueOf(o);
	}

	public static String printStringHelper(boolean o) {
		return o ? "是" : "否";
	}

	private PayRequestBillInfo fdcBill = null;
	private CurProjectInfo curProjectInfo = null;
	private HashMap bindCellMap =null;
	private ContractBillInfo contractBill = null;
	
	public PayRequestBillRowsetProvider(PayRequestBillInfo editData,HashMap bindCellMap,CurProjectInfo curProjectInfo,ContractBillInfo contractBill) {
		super(editData.getId().toString(),null);
		fdcBill = editData;
		this.bindCellMap = bindCellMap;
		this.curProjectInfo = curProjectInfo;
		this.contractBill = contractBill;
		initAllCompletePrjAmt();//初始化时一次性赋值
	}
	private BigDecimal getContractOriAmt(String contractId) {
		//合同造价
		BigDecimal contractOriAmt=FDCHelper.ZERO;
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("originalAmount");
		
		try {
			ContractBillInfo contractBill = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
			contractOriAmt=contractBill.getOriginalAmount();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (UuidException e) {
			e.printStackTrace();
		}
		return contractOriAmt;
	}
	
	private BigDecimal getChangeOriAmt(String contractId){

		BigDecimal conChangeOriAmt=FDCHelper.ZERO;
		EntityViewInfo view=new EntityViewInfo();
		view.getSelector().add("hasSettled");
		view.getSelector().add("oriBalanceAmount");
		view.getSelector().add("originalAmount");
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractBill.id",contractId));
		view.setFilter(filter);
		ContractChangeBillCollection changeBillColl;
		try {
			changeBillColl = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillCollection(view);
			ContractChangeBillInfo billInfo;
			//变更金额累计=未结算变更+已结算变更
			for (Iterator iter = changeBillColl.iterator(); iter.hasNext();)
			{
				billInfo = (ContractChangeBillInfo) iter.next();
				if(billInfo.isHasSettled()){
					conChangeOriAmt = conChangeOriAmt.add(FDCHelper.toBigDecimal(billInfo.getOriBalanceAmount()));
				}else{
					conChangeOriAmt = conChangeOriAmt.add(FDCHelper.toBigDecimal(billInfo.getOriginalAmount()));
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return conChangeOriAmt;
	}
	
	private BigDecimal getGuerdonOriAmt(String contractId){
		BigDecimal guerdonBillOriAmt=FDCHelper.ZERO;
		EntityViewInfo view=new EntityViewInfo();
		view.getSelector().add("originalAmount");
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("payRequestBill.id",fdcBill.getId().toString()));
		/**
		 * 应该取本次申请原币金额，非某一合同下累计申请的原币金额 by Cassiel_peng 2009-10-21
		 */
		/*filter.getFilterItems().add(new FilterItemInfo("contract.id",contractId));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED));
		filter.getFilterItems().add(new FilterItemInfo("isGuerdoned",Boolean.TRUE));*/
		view.setFilter(filter);
		try {
//			GuerdonBillCollection guerdonBillColl = GuerdonBillFactory.getRemoteInstance().getGuerdonBillCollection(view);
			GuerdonOfPayReqBillCollection guerdonBillColl = GuerdonOfPayReqBillFactory.getRemoteInstance().getGuerdonOfPayReqBillCollection(view);
			for (Iterator iter = guerdonBillColl.iterator(); iter.hasNext();)
			{
				GuerdonOfPayReqBillInfo guerdonBill = (GuerdonOfPayReqBillInfo) iter.next();
				guerdonBillOriAmt = guerdonBillOriAmt.add(FDCHelper.toBigDecimal(guerdonBill.getOriginalAmount()));
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return guerdonBillOriAmt;
	}
	private BigDecimal getCompensationOriAmt(String contractId){
		BigDecimal compensationBillOriAmt=FDCHelper.ZERO;
		EntityViewInfo view=new EntityViewInfo();
		view.getSelector().add("originalAmount");
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("payRequestBill.id",fdcBill.getId().toString()));
		/**
		 * 应该取本次申请原币金额，非某一合同下累计申请的原币金额 by Cassiel_peng 2009-10-21
		 */
		/*filter.getFilterItems().add(new FilterItemInfo("contract.id",contractId));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED));
		filter.getFilterItems().add(new FilterItemInfo("isCompensated",Boolean.TRUE));*/
		view.setFilter(filter);
		try {
			CompensationOfPayReqBillCollection compenstionColl = CompensationOfPayReqBillFactory.getRemoteInstance().getCompensationOfPayReqBillCollection(view);
//			CompensationBillCollection compenstionColl = CompensationBillFactory.getRemoteInstance().getCompensationBillCollection(view);
			for (Iterator iter = compenstionColl.iterator(); iter.hasNext();)
			{
				CompensationOfPayReqBillInfo billInfo = (CompensationOfPayReqBillInfo) iter.next();
				compensationBillOriAmt = compensationBillOriAmt.add(FDCHelper.toBigDecimal(billInfo.getOriginalAmount()));
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return compensationBillOriAmt;
	}
	private BigDecimal getDeductOriAmt(String contractId) {
		BigDecimal DeductOfPayReqBillOriAmt = FDCHelper.ZERO;
		EntityViewInfo view=new EntityViewInfo();
		view.getSelector().add("originalAmount");
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("payRequestBill.id",fdcBill.getId().toString()));
		view.setFilter(filter);
		try {
			DeductOfPayReqBillCollection duductOfPayReqBillColl = DeductOfPayReqBillFactory.getRemoteInstance().getDeductOfPayReqBillCollection(view);
			for (Iterator iter = duductOfPayReqBillColl.iterator(); iter.hasNext();)
			{
				DeductOfPayReqBillInfo billInfo = (DeductOfPayReqBillInfo) iter.next();
				DeductOfPayReqBillOriAmt = DeductOfPayReqBillOriAmt.add(FDCHelper.toBigDecimal(billInfo.getOriginalAmount()));
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return DeductOfPayReqBillOriAmt;
	}
	private void insert(DynamicRowSet drs,DeductOfPayReqBillInfo info,Map deductTypeSum) throws Exception{
	
		BigDecimal sumLstDeductPaid = (BigDecimal)deductTypeSum.get("sumLstDeductPaid");
		BigDecimal sumLstDeductReq =  (BigDecimal)deductTypeSum.get("sumLstDeductReq");
		BigDecimal sumDeduct =  (BigDecimal)deductTypeSum.get("sumDeduct");
		BigDecimal sumAllDeductReq =  (BigDecimal)deductTypeSum.get("sumAllDeductReq");
		BigDecimal sumAllDeductPaid = (BigDecimal)deductTypeSum.get("sumAllDeductPaid");
		
		// 在此把数据传递给实现类，drs.updateString(key,value) key
		// 指的是套打模板中定义的字段编码，Value指的是当前单据的属性值
		String orgName=((CurProjectInfo)fdcBill.getCurProject()).getFullOrgUnit().getName();
		String curProjectName = curProjectInfo.getDisplayName();		
		//取出的数据要求只取项目名称
		//2008-07-22
		String projNameWithoutOrg = curProjectName.replace('_', '\\');
		curProjectName = orgName+"\\"+curProjectName.replace('_', '\\');
		
		drs.updateString("invoiceNumber", printStringHelper(fdcBill.getInvoiceNumber()));//发票号
		drs.updateString("invoiceDate", printStringHelper(fdcBill.getInvoiceDate()));//开票日
		drs.updateString("invoiceAmt", printStringHelper(fdcBill.getInvoiceAmt()));//本次发票金额     Added by Owen_wen 2010-09-24
		drs.updateString("allInvoiceAmt", printStringHelper(fdcBill.getAllInvoiceAmt()));//累计发票金额
		
		drs.updateString("orgName",orgName);
		
		drs.updateString("curProject.name", curProjectName);
		drs.updateString("projNameWithoutOrg", projNameWithoutOrg);
		drs.updateString("curProject.number", fdcBill.getCurProject()
				.getNumber());
		drs.updateString("useDepartment.name", fdcBill.getUseDepartment()
				.getName());
		drs.updateString("useDepartment.number", fdcBill.getUseDepartment()
				.getNumber());
		drs.updateString("contractNo", printStringHelper(fdcBill
				.getContractNo()));
		drs.updateString("bookedDate", printStringHelper(fdcBill
				.getBookedDate()));// 业务日期
		drs.updateString("number", printStringHelper(fdcBill.getNumber()));
		drs
				.updateString("payDate", printStringHelper(fdcBill
						.getPayDate()));
		drs.updateString("period.periodNumber", printStringHelper(fdcBill
				.getPeriod()));
		drs.updateString("period.number", fdcBill.getPeriod()==null?"":String.valueOf(fdcBill
				.getPeriod().getNumber()));
		drs.updateString("paymentType.name",
				fdcBill.getPaymentType() == null ? "" : fdcBill
						.getPaymentType().getName());
		drs.updateString("paymentType.number",
				fdcBill.getPaymentType() == null ? "" : fdcBill
						.getPaymentType().getNumber());
		drs.updateString("isDifferPlace", printStringHelper(fdcBill
				.getIsDifferPlace()));
		drs.updateString("settlementType.name",
				fdcBill.getSettlementType() == null ? "" : fdcBill
						.getSettlementType().getName());
		drs.updateString("settlementType.number", fdcBill
				.getSettlementType() == null ? "" : fdcBill
				.getSettlementType().getNumber());
		drs
				.updateString("recBank", printStringHelper(fdcBill
						.getRecBank()));
		drs.updateString("description", printStringHelper(fdcBill
				.getDescription()));
		drs.updateString("supplier.name",
				fdcBill.getSupplier() == null ? "" : fdcBill.getSupplier()
						.getName());
		drs.updateString("realSupplier.name",
				fdcBill.getRealSupplier() == null ? "" : fdcBill
						.getRealSupplier().getName());
		drs.updateString("recAccount", fdcBill.getRecAccount());
		drs.updateString("usage", fdcBill.getUsage());
		drs.updateString("currency.name",
				fdcBill.getCurrency() == null ? "" : fdcBill.getCurrency()
						.getName());
		drs.updateString("currency.number",
				fdcBill.getCurrency() == null ? "" : fdcBill.getCurrency()
						.getNumber());
		drs.updateString("exchangeRate", String.valueOf(fdcBill
				.getExchangeRate()));// 
		drs.updateString("attachment", String.valueOf(fdcBill
				.getAttachment()));
		drs.updateString("originalAmount", printStringHelper(fdcBill
				.getOriginalAmount()));
		drs.updateString("paymentProportion", printStringHelper(fdcBill
				.getPaymentProportion())+"%");
		drs.updateString("amount", printStringHelper(fdcBill.getAmount()));
		drs.updateString("grtAmount", printStringHelper(fdcBill
				.getGrtAmount()));
		//合同保修金额
		if(contractBill!=null){
			drs.updateString("conGrtAmount", printStringHelper(contractBill.getGrtAmount()));
			drs.updateString("conGrtOriAmount", printStringHelper(FDCHelper.divide(FDCHelper.toBigDecimal(contractBill.getGrtAmount()), contractBill.getExRate(),4,BigDecimal.ROUND_HALF_UP)));
			drs.updateString("conGrtRate", printStringHelper(contractBill.getGrtRate()));
		}
		drs.updateString("completePrjAmt", fdcBill.getBoolean("isCompletePrjAmtVisible")?printStringHelper(fdcBill
				.getCompletePrjAmt()):"");
		drs.updateString("capitalAmount", printStringHelper(fdcBill
				.getCapitalAmount()));
		drs.updateString("moneyDesc", printStringHelper(fdcBill
				.getMoneyDesc()));
		drs.updateString("urgentDegree", printStringHelper(fdcBill
				.getUrgentDegree()));
		drs.updateString("isPay", printStringHelper(fdcBill.isIsPay()));
		drs.updateString("createTime", printStringHelper(fdcBill
				.getCreateTime()));
		drs.updateString("creator.name", fdcBill.getCreator().getName());
		drs
				.updateString("creator.number", fdcBill.getCreator()
						.getNumber());
		drs.updateString("auditTime", printStringHelper(fdcBill
				.getAuditTime()));
		drs.updateString("auditor.number",
				fdcBill.getAuditor() == null ? "" : fdcBill.getAuditor()
						.getNumber());
		drs.updateString("auditor.name", fdcBill.getAuditor() == null ? ""
				: fdcBill.getAuditor().getName());
		drs.updateString("contractName", printStringHelper(fdcBill
				.getContractName()));
		drs.updateString("contractPrice", printStringHelper(fdcBill
				.getContractPrice()));
		String contractId = fdcBill.getContractId();
		BigDecimal exRate = getConExRate(contractId);
		//如果合同是原币，最新造价为原币 如果合同为外币，则取外币  ----- 此逻辑现作废，因为已经有专门的字段用来表示最新造价的原币  by Cassiel_peng 2009-10-9
		if(exRate.compareTo(FDCHelper.ONE)==0 ||exRate.compareTo(FDCHelper.ZERO)==0){
			drs.updateString("latestPrice", printStringHelper(FDCHelper.toBigDecimal(fdcBill
					.getLatestPrice())));
		}
//			else{
//			drs.updateString("latestPrice", printStringHelper(FDCHelper.toBigDecimal(fdcBill
//					.getLatestPrice()).divide(exRate,4,BigDecimal.ROUND_HALF_UP)));
//		}

		drs.updateString("addProjectAmt", printStringHelper(fdcBill
				.getAddProjectAmt()));
		drs.updateString("changeAmt", printStringHelper(fdcBill
				.getChangeAmt()));
		drs.updateString("payedAmt", printStringHelper(fdcBill
				.getPayedAmt()));
		drs.updateString("payPartAMatlAmt", printStringHelper(fdcBill
				.getPayPartAMatlAmt()));
		drs.updateString("projectPriceInContract",
				printStringHelper(fdcBill.getProjectPriceInContract()));
		drs.updateString("scheduleAmt", printStringHelper(fdcBill
				.getScheduleAmt()));
		drs.updateString("curPlannedPayment", printStringHelper(fdcBill
				.getCurPlannedPayment()));
		drs.updateString("curBackPay", printStringHelper(fdcBill
				.getCurBackPay()));
		drs.updateString("paymentPlan", printStringHelper(fdcBill
				.getPaymentPlan()));
		// 付款次数
		drs.updateString("payTimes", String.valueOf(fdcBill.getPayTimes()));
		
		drs.updateString("prjAllReqAmt", printStringHelper(fdcBill
				.getPrjAllReqAmt()));
		drs.updateString("addPrjAllReqAmt", printStringHelper(fdcBill
				.getAddPrjAllReqAmt()));
		drs.updateString("payPartAMatlAllReqAmt", printStringHelper(fdcBill
				.getPayPartAMatlAllReqAmt()));
		drs.updateString("lstPrjAllReqAmt", printStringHelper(fdcBill
				.getLstPrjAllReqAmt()));
		drs.updateString("lstAddPrjAllReqAmt", printStringHelper(fdcBill
				.getLstAddPrjAllReqAmt()));
		drs.updateString("lstAMatlAllReqAmt", printStringHelper(fdcBill
				.getLstAMatlAllReqAmt()));
		drs.updateString("contractId", fdcBill.getContractId());
		drs.updateString("hasPayoff", printStringHelper(fdcBill
				.isHasPayoff()));
		drs.updateString("lstPrjAllPaidAmt", printStringHelper(fdcBill
				.getLstPrjAllPaidAmt()));
		drs.updateString("lstAddPrjAllPaidAmt", printStringHelper(fdcBill
				.getLstAddPrjAllPaidAmt()));
		drs.updateString("lstAMatlAllPaidAmt", printStringHelper(fdcBill
				.getLstAMatlAllPaidAmt()));
		drs.updateString("costAmount", fdcBill.getBoolean("isCompletePrjAmtVisible")?printStringHelper(fdcBill
				.getCompletePrjAmt()):"");
		drs.updateString("settleAmt", printStringHelper(fdcBill
				.getSettleAmt()));
		// 合同内付款 截至本期累计实付 = 截至上期累计累计实付 
		BigDecimal prjAllPaidAmt = (fdcBill.getLstPrjAllPaidAmt()!=null?fdcBill.getLstPrjAllPaidAmt():FDCHelper.ZERO);
		drs.updateString("prjAllPaidAmt", printStringHelper(prjAllPaidAmt));			
		
		// 奖励 截至上期累计实付
		BigDecimal lstGuerdonPaidAmt = (BigDecimal) ( (ICell)( bindCellMap.get("lstGuerdonPaidAmt"))).getValue();				
		drs.updateString("lstGuerdonPaidAmt",printStringHelper(lstGuerdonPaidAmt));
		// 奖励 截至上期累计申请
		BigDecimal lstGuerdonReqAmt = (BigDecimal) ( (ICell)( bindCellMap.get("lstGuerdonReqAmt"))).getValue();
		drs.updateString("lstGuerdonReqAmt", printStringHelper(lstGuerdonReqAmt));
		// 奖励 本期发生				
		BigDecimal guerdonAmt = (BigDecimal)((ICell)( bindCellMap.get("guerdonAmt"))).getValue();
//			fdcBill.get("guerdonAmt")
		guerdonAmt = guerdonAmt!= null?guerdonAmt: FDCHelper.ZERO;
		drs.updateString("guerdonAmt", printStringHelper(guerdonAmt));
		// 奖励 截至本期累计申请 = 奖励 截至上期累计申请 + 奖励 本期发生
		// 奖励 截至本期累计实付 = 奖励 截至上期累计实付 
		BigDecimal allGuerdonReqAmt = guerdonAmt.add(lstGuerdonReqAmt!= null ? lstGuerdonReqAmt : FDCHelper.ZERO);
		BigDecimal allGuerdonPaidAmt = lstGuerdonPaidAmt!= null ?lstGuerdonPaidAmt: FDCHelper.ZERO;
		drs.updateString("allGuerdonReqAmt",printStringHelper(allGuerdonReqAmt));
		drs.updateString("allGuerdonPaidAmt",printStringHelper(allGuerdonPaidAmt));
		
		// 违约 截至上期累计实付
		BigDecimal lstCompensationPaidAmt = (BigDecimal) ( (ICell)( bindCellMap.get("lstCompensationPaidAmt"))).getValue();
		drs.updateString("lstCompensationPaidAmt",printStringHelper(lstCompensationPaidAmt));
		
		// 违约 截至上期累计申请
		BigDecimal lstCompensationReqAmt = (BigDecimal) ( (ICell)( bindCellMap.get("lstCompensationReqAmt"))).getValue();
		drs.updateString("lstCompensationReqAmt", printStringHelper(lstCompensationReqAmt));
		// 违约 本期发生
		BigDecimal compensationAmt = (BigDecimal) ( (ICell)( bindCellMap.get("compensationAmt"))).getValue();
		compensationAmt = compensationAmt != null? compensationAmt: FDCHelper.ZERO;
		drs.updateString("compensationAmt", printStringHelper(compensationAmt));
		// 违约 截至本期累计申请 = 违约 截至上期累计申请 + 违约 本期发生
		// 违约 截至本期累计实付 = 违约 截至上期累计实付 				
		BigDecimal allCompensationReqAmt = compensationAmt.add(lstCompensationReqAmt!= null 
												? lstCompensationReqAmt: FDCHelper.ZERO);
		BigDecimal allCompensationPaidAmt = lstCompensationPaidAmt != null 
												? lstCompensationPaidAmt: FDCHelper.ZERO;
		drs.updateString("allCompensationReqAmt",	printStringHelper(allCompensationReqAmt));
		drs.updateString("allCompensationPaidAmt",printStringHelper(allCompensationPaidAmt));
		
		//已实现产值，累计结算
		
		drs.updateString("totalSettlePrice",printStringHelper(fdcBill.getTotalSettlePrice()));
		
		//扣款项
		//扣款类型				
		if(info!=null){
			drs.updateString("deductType",info==null?"":info.getDeductType().getName());
			BigDecimal lstDeductPaidAmt = info.getAllPaidAmt()==null?FDCHelper.ZERO:info.getAllPaidAmt();
			BigDecimal lstDeductReqAmt  = info.getAllReqAmt()==null?FDCHelper.ZERO:info.getAllReqAmt();
			BigDecimal deductAmt        = info.getAmount()==null?FDCHelper.ZERO:info.getAmount();
			BigDecimal allDeductReqAmt  = lstDeductReqAmt.add(deductAmt);
			BigDecimal allDeductPaidAmt  = lstDeductPaidAmt;
			
			drs.updateString("lstDeductPaidAmt",printStringHelper(lstDeductPaidAmt));
			drs.updateString("lstDeductReqAmt",printStringHelper(lstDeductReqAmt));
			drs.updateString("deductAmt",printStringHelper(deductAmt));
			drs.updateString("allDeductReqAmt",printStringHelper(allDeductReqAmt));
			drs.updateString("allDeductPaidAmt",	printStringHelper(allDeductPaidAmt));
			
			
			//扣款小计
			sumLstDeductPaid = sumLstDeductPaid.add(lstDeductPaidAmt);
			sumLstDeductReq = sumLstDeductReq.add(lstDeductReqAmt);
			sumDeduct = sumDeduct.add(deductAmt);
			sumAllDeductReq = sumAllDeductReq.add(allDeductReqAmt);
			sumAllDeductPaid = sumAllDeductPaid.add(allDeductPaidAmt);
			
			deductTypeSum.put("sumLstDeductPaid",sumLstDeductPaid);
			deductTypeSum.put("sumLstDeductReq",sumLstDeductReq);
			deductTypeSum.put("sumDeduct",sumDeduct);
			deductTypeSum.put("sumAllDeductReq",sumAllDeductReq);
			deductTypeSum.put("sumAllDeductPaid",sumAllDeductPaid);
		}
		
//		if(i==c.size()-1){
			drs.updateString("lstDeductSumPaidAmt",printStringHelper(sumLstDeductPaid));
			drs.updateString("lstDeductSumReqAmt",printStringHelper(sumLstDeductReq));
			drs.updateString("deductSumAmt",printStringHelper(sumDeduct));
			drs.updateString("allDeductSumReqAmt",printStringHelper(sumAllDeductReq));
			drs.updateString("allDeductSumPaidAmt",printStringHelper(sumAllDeductPaid));					
//		}	
		
		//甲供材 截至本期累计实付 = 甲供材 截至上期累计实付 
		BigDecimal allPartAMatlAllPaidAmt = (fdcBill.getLstAMatlAllPaidAmt()!=null?fdcBill
				.getLstAMatlAllPaidAmt():FDCHelper.ZERO);
		// 甲供材 截至上期累计实付
				//.add(fdcBill.getPayPartAMatlAmt()!=null?fdcBill.getPayPartAMatlAmt()
						//:FDCHelper.ZERO);// 甲供材 本期发生
		drs.updateString("allPartAMatlAllPaidAmt",printStringHelper(allPartAMatlAllPaidAmt));
		
		//实付款 截至上期累计实付 = 合同工程款 + 奖励 - 违约金 - 扣款小计 - 甲供材
		BigDecimal lstRealPaidAmt = fdcBill.getLstPrjAllPaidAmt()!=null?fdcBill.getLstPrjAllPaidAmt():FDCHelper.ZERO;
		BigDecimal tempLstRealPaidAmt = lstRealPaidAmt.add(FDCHelper.toBigDecimal(lstGuerdonPaidAmt))//奖励
		.subtract(FDCHelper.toBigDecimal(lstCompensationPaidAmt))//违约
		.subtract(FDCHelper.toBigDecimal(sumLstDeductPaid))//扣款
		.subtract(fdcBill.getLstAMatlAllPaidAmt() != null ? fdcBill
						.getLstAMatlAllPaidAmt() : FDCHelper.ZERO);//甲供材
		drs.updateString("lstRealPaidAmt", printStringHelper(tempLstRealPaidAmt));
		
		//实付款 截至上期累计申请 = 合同工程款 + 奖励 - 违约金 - 扣款小计 - 甲供材
		BigDecimal lstRealReqAmt = fdcBill.getLstPrjAllReqAmt()!=null?fdcBill.getLstPrjAllReqAmt():FDCHelper.ZERO;
		BigDecimal tempLstRealReqAmt = lstRealReqAmt.add(FDCHelper.toBigDecimal(lstGuerdonReqAmt))//奖励
		.subtract(FDCHelper.toBigDecimal(lstCompensationReqAmt))//违约
		.subtract(FDCHelper.toBigDecimal(sumLstDeductReq))//扣款
		.subtract(fdcBill.getLstAMatlAllReqAmt() != null ? fdcBill
						.getLstAMatlAllReqAmt() : FDCHelper.ZERO);//甲供材
		drs.updateString("lstRealReqAmt", printStringHelper(tempLstRealReqAmt));
		
		
		//实付款 本期发生 = 合同工程款 + 奖励 - 违约金 - 扣款小计 - 甲供材
		BigDecimal projectPriceInContract = fdcBill.getProjectPriceInContract()!=null?fdcBill.getProjectPriceInContract():FDCHelper.ZERO;
//		BigDecimal tempCurPaid = fdcBill.getCurPaid()!= null ? fdcBill.getCurPaid(): FDCHelper.ZERO;
		BigDecimal tempCurPaid = projectPriceInContract.add(guerdonAmt)//奖励
		.subtract(compensationAmt)//违约
		.subtract(sumDeduct)//扣款
		.subtract(fdcBill.getPayPartAMatlAmt() != null ? fdcBill
						.getPayPartAMatlAmt() : FDCHelper.ZERO);//甲供材
		drs.updateString("curPaid", printStringHelper(tempCurPaid));
		// ///
		
		
		//实付款 截至本期累计申请 = 合同工程款 + 奖励 - 违约金 - 扣款小计 - 甲供材
		BigDecimal prjAllReqAmt = fdcBill.getPrjAllReqAmt()!=null?fdcBill.getPrjAllReqAmt():FDCHelper.ZERO;
		BigDecimal allRealReqAmt = prjAllReqAmt.add(allGuerdonReqAmt)//奖励
				.subtract(	allCompensationReqAmt)//违约
				.subtract(sumAllDeductReq)//扣款
				.subtract(fdcBill.getPayPartAMatlAllReqAmt() != null ? fdcBill
								.getPayPartAMatlAllReqAmt()
								: FDCHelper.ZERO);//甲供材
		drs.updateString("allRealReqAmt",printStringHelper(allRealReqAmt));
		
		// 实付款 截至本期累计实付 = 合同工程款 + 奖励 - 违约金 - 扣款小计 - 甲供材
		BigDecimal lstPrjAllPaidAmt =  FDCHelper.toBigDecimal(fdcBill.getLstPrjAllPaidAmt());
		BigDecimal allRealPaidAmt = lstPrjAllPaidAmt
				.add(allGuerdonPaidAmt)//奖励
				.subtract(allCompensationPaidAmt)//违约
				.subtract(sumAllDeductPaid)//扣款
				.subtract(allPartAMatlAllPaidAmt);//甲供材
		drs.updateString("allRealPaidAmt",printStringHelper(allRealPaidAmt));
		
		// 余款 = 最新造价 - 合同内付款 截至本期累计实付
		BigDecimal restAmount = (fdcBill
		.getLatestPrice() == null ? FDCHelper.ZERO : fdcBill.getLatestPrice())				
		.subtract(prjAllPaidAmt);//合同内付款 截至本期累计实付
		
		drs.updateString("restAmount",printStringHelper(restAmount));
		// 本次申请% = 实付款 本期发生 / 最新造价
		BigDecimal tempCurReqPercent = fdcBill.getLatestPrice() == null
				|| fdcBill.getLatestPrice().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO
				: tempCurPaid.divide(fdcBill.getLatestPrice(), 4,
						BigDecimal.ROUND_HALF_UP);
//		BigDecimal tempCurReqPercent = fdcBill.getCurReqPercent()!=null?
//				fdcBill.getCurReqPercent():FDCHelper.ZERO;
		drs.updateString("curReqPercent",
				printStringHelper((tempCurReqPercent.multiply(FDCConstants.ONE_HUNDRED)).setScale(2)));
		
		// 累计申请% = 实付款 截至本期累计申请 / 最新造价
		BigDecimal tempAllCurReqPercent = fdcBill.getLatestPrice() == null
				|| fdcBill.getLatestPrice().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO
				: allRealReqAmt.divide(fdcBill.getLatestPrice(), 4,
						BigDecimal.ROUND_HALF_UP);
		
//		BigDecimal tempAllCurReqPercent = fdcBill.getAllReqPercent()!=null?
//				fdcBill.getAllReqPercent():FDCHelper.ZERO;
		drs.updateString("allReqPercent",
				printStringHelper((tempAllCurReqPercent.multiply(FDCConstants.ONE_HUNDRED)).setScale(2)));
		// ///
		drs.updateString("imageSchedule", printStringHelper(fdcBill
				.getImageSchedule()));
		drs.updateString("allCompletePrjAmt", printStringHelper(fdcBill.get("allCompletePrjAmt")));
		// 合同内工程款截止上期实付+本期申请
		BigDecimal LstPrjAllPaidAmtAndthis = FDCHelper.add(fdcBill.getLstPrjAllPaidAmt(),fdcBill.getProjectPriceInContract());
		drs.updateString("LstPrjAllPaidAmtAndthis",printStringHelper(LstPrjAllPaidAmtAndthis));
		// 合同最新造价-合同内工程款截止上期实付-本期申请
		BigDecimal latestPriceSub = FDCHelper.subtract(fdcBill.getLatestPrice(),FDCHelper.add(fdcBill.getLstPrjAllPaidAmt(),fdcBill.getProjectPriceInContract()));
		drs.updateString("latestPriceSub",printStringHelper(latestPriceSub));
		
		// 付款申请单套打时支持获取以下数据的原币  by Cassiel_peng  2009-10-9
		// 合同造价原币
		drs.updateString("contractOriPrice", printStringHelper(getContractOriAmt(contractId)));
		// 变更签证金额原币
		drs.updateString("changeOriAmt", printStringHelper(getChangeOriAmt(contractId)));
		// 最新造价
		drs.updateString("latestOriPrice", printStringHelper(FDCHelper.divide(FDCHelper.toBigDecimal(fdcBill.getLatestPrice()), exRate,4,BigDecimal.ROUND_HALF_UP)));
		// 保修金金额
		drs.updateString("grtOriAmount", printStringHelper(FDCHelper.divide(FDCHelper.toBigDecimal(fdcBill.getGrtAmount()), fdcBill.getExchangeRate(),4,BigDecimal.ROUND_HALF_UP)));
		// 上期合同内累计实付 
		drs.updateString("lstPrjAllPaidOriAmt", printStringHelper(FDCHelper.divide(FDCHelper.toBigDecimal(fdcBill.getLstPrjAllPaidAmt()), fdcBill.getExchangeRate(),4,BigDecimal.ROUND_HALF_UP)));
		// 合同内工程款上期累计申请
		drs.updateString("lstPrjAllReqOriAmt", printStringHelper(FDCHelper.divide(FDCHelper.toBigDecimal(fdcBill.getLstPrjAllReqAmt()), fdcBill.getExchangeRate(),4,BigDecimal.ROUND_HALF_UP)));
		// 结算金额
		drs.updateString("settleOriAmt", printStringHelper(FDCHelper.divide(FDCHelper.toBigDecimal(fdcBill.getSettleAmt()), fdcBill.getExchangeRate(),4,BigDecimal.ROUND_HALF_UP)));
		// 合同内工程款（本期发生）
		drs.updateString("projectPriceInContractOri", printStringHelper(fdcBill.getProjectPriceInContractOri()));
		// 奖励本期发生
		drs.updateString("guerdonOriAmt", printStringHelper(getGuerdonOriAmt(contractId)));
		// 违约本期发生
		drs.updateString("compensationOriAmt", printStringHelper(getCompensationOriAmt(contractId)));
		// 扣款本期发生
		drs.updateString("deductOriAmt", printStringHelper(FDCHelper.toBigDecimal(getDeductOriAmt(contractId))));
		// 甲供材本期发生
		drs.updateString("payPartAMatlOriAmt", printStringHelper(FDCHelper.divide(FDCHelper.toBigDecimal(fdcBill.getPayPartAMatlAmt()), fdcBill.getExchangeRate(),4,BigDecimal.ROUND_HALF_UP)));
		// 本期申请本币金额    直接取得 amount 字段的值，还不知道对不对呢！！！
		drs.updateString("thisTimeReqLocalAmt", printStringHelper(FDCHelper.toBigDecimal(fdcBill.getAmount())));
		drs.updateString("process", fdcBill.getProcess());
		drs.updateString("id",fdcBill.getId().toString());
	}
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds){
		/**
		 * 付款申请单套打增加数据源： 归属财务核算对象、归属成本科目代码、归属成本科目、归属金额、直接归属。
		 * by jian_wen 2009.12.18
		 */
		if(ds.getID().toUpperCase().startsWith("SPLITENTRYPRINTVIEWQUERY")) 
        {   
			IRowSet iRowSet = null;
	        try {
	        	IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK ("com.kingdee.eas.fdc.contract.app.SplitEntryPrintViewQuery"));
	            exec.option().isAutoTranslateEnum= true;
	            EntityViewInfo ev = new EntityViewInfo();
	            FilterInfo filter = new FilterInfo();
	            filter.getFilterItems().add(new FilterItemInfo("parent.contractBill.id", contractBill.getId().toString(), CompareType.EQUALS));
	            ev.setFilter(filter);            
	            exec.setObjectView(ev);
	            //System.out.println(exec.getSQL());
	            iRowSet = exec.executeQuery();	
	            iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}
	        
			return iRowSet;
			
        } else if (ds.getID().toUpperCase().startsWith("CONTRACTBILLENTRYPRINT")){
        	IRowSet iRowSet = null;
	        try {
	        	IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK ("com.kingdee.eas.fdc.contract.app.ContractBillEntryPrintQuery"));
	            exec.option().isAutoTranslateEnum= true;
	            EntityViewInfo ev = new EntityViewInfo();
	            FilterInfo filter = new FilterInfo();
	            filter.getFilterItems().add(new FilterItemInfo("parent.id", contractBill.getId().toString(), CompareType.EQUALS));
	            ev.setFilter(filter);            
	            exec.setObjectView(ev);
	            //System.out.println(exec.getSQL());
	            iRowSet = exec.executeQuery();	
	            iRowSet.beforeFirst();
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
	        
			return iRowSet;
        }else if (ds.getID().toUpperCase().startsWith("CONTRACTPAYITEM")) {
        	IRowSet iRowSet = null;
	        try {
	        	IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK ("com.kingdee.eas.fdc.contract.app.ContractPayItemPrintQuery"));
	            exec.option().isAutoTranslateEnum= true;
	            EntityViewInfo ev = new EntityViewInfo();
	            FilterInfo filter = new FilterInfo();
	            filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractBill.getId().toString(), CompareType.EQUALS));
	            ev.setFilter(filter);            
	            exec.setObjectView(ev);
	            iRowSet = exec.executeQuery();	
	            iRowSet.beforeFirst();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return iRowSet;
        }else if (ds.getID().equalsIgnoreCase("ContractPayItemPrintQuery")) {
			// 合同付款事项
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK(
						"com.kingdee.eas.fdc.contract.app.ContractPayItemPrintQuery"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("contractbill.id", contractBill.getId().toString()));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}

			return iRowSet;
	} else if (ds.getID().equalsIgnoreCase("ContractBailPrintQuery")) {
			// 合同履约保证金及返还部分
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory
						.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fdc.contract.app.ContractBailPrintQuery"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("id", contractBill.getBail().getId().toString()));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}

			return iRowSet;
		} else if (ds.getID().equalsIgnoreCase("ContractBillQueryForPrint")) {
			// 合同详细信息
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory
						.getRemoteInstance(new MetaDataPK(
								"com.kingdee.eas.fdc.contract.app.ContractBillQueryForPrint"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", fdcBill.getContractId()));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}

			return iRowSet;
		}else if(ds.getID().equalsIgnoreCase("ContractBillQuery")) {
			// 合同详细信息
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory
						.getRemoteInstance(new MetaDataPK(
								"com.kingdee.eas.fdc.contract.app.ContractBillQuery"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", fdcBill.getContractId()));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}

			return iRowSet;
		}else if(ds.getID().equalsIgnoreCase("ContractWithoutTextPrintQuery")) {
			// 无文本合同详细信息
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory
						.getRemoteInstance(new MetaDataPK(
								"com.kingdee.eas.fdc.contract.app.ContractWithoutTextPrintQuery"));
				
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("id", fdcBill.getContractId()));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}

			return iRowSet;
		} else if (ds.getID().equalsIgnoreCase("DeductOfPayReqBillPrintQuery")) {
			// 该申请单对应的扣款分录信息
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory
						.getRemoteInstance(new MetaDataPK(
								"com.kingdee.eas.fdc.contract.app.DeductOfPayReqBillPrintQuery"));
				
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(
						new FilterItemInfo("payRequestBill.id", fdcBill.getId()));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}
			return iRowSet;
		}else if(ds.getID().equalsIgnoreCase("BGENTRY")){
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fdc.contract.app.PayRequestBillBgEntryPrintQuery"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("head.id", fdcBill.getId()));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}
		}else if (ds.getID().equalsIgnoreCase("AttachmentQuery")) {
			// 合同履约保证金及返还部分
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory
						.getRemoteInstance(new MetaDataPK("com.kingdee.eas.fdc.basedata.app.AttachmentQuery"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("boAttchAsso.boID",billId));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}

			return iRowSet;
		}
		return getMainBillRowSet(ds);
	}
	public IRowSet getMainBillRowSet(BOSQueryDataSource ds) {
		// TODO 自动生成方法存根
		int colCount = col.length;
		DynamicRowSet drs = null;
		try {
			drs = new DynamicRowSet(colCount);
			
			for (int i = 0; i < colCount; i++) {
				ColInfo ci = new ColInfo();
				ci.colType = Types.VARCHAR;
				ci.columnName = col[i];
				ci.nullable = 1;
				drs.setColInfo(i + 1, ci);
			}
			

			drs.beforeFirst();
			
			
			
			String payDetails = "";
			
			if(fdcBill.getId()!=null){
				//获得累计实付 转换成String字符串显示
				String contractNo = fdcBill.getContractNo().toString();
				Timestamp createTime = fdcBill.getCreateTime();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				view.setFilter(filter);
				filter.appendFilterItem("contractNo", contractNo);
				filter.getFilterItems().add(
						new FilterItemInfo("createTime", createTime,
								CompareType.LESS));
				view.getSelector().add("entrys.paymentBill.id");
				PayRequestBillCollection c;
				Set ids = new HashSet();
				try {
					c = PayRequestBillFactory.getRemoteInstance()
							.getPayRequestBillCollection(view);
					if(c.size()>0){
						for(Iterator it=c.iterator();it.hasNext();){
							PayRequestBillInfo payRequestInfo = (PayRequestBillInfo)it.next();
							if(payRequestInfo.getEntrys().size()>0){
								for(int i=0;i<payRequestInfo.getEntrys().size();i++){
									ids.add(payRequestInfo.getEntrys().get(i).getPaymentBill().getId());
								}
							}
						}
					}
				} catch (BOSException e) {
					// TODO 自动生成 catch 块
					e.printStackTrace();
				}
				filter = new FilterInfo();

				if(ids.size()>0)
					filter.getFilterItems().add(new FilterItemInfo("id",ids,CompareType.INCLUDE));
				else
					filter.getFilterItems().add(
							new FilterItemInfo("id", null));
				filter.getFilterItems().add(
						new FilterItemInfo("billStatus", new Integer(BillStatusEnum.PAYED_VALUE)));
				
				//filter.appendFilterItem("contractNo",contractNo);
				
				Map map = new HashMap();
				EntityViewInfo ev = new EntityViewInfo();
				ev.getSelector().add("contractNo");
				ev.getSelector().add("feeType.name");
				ev.getSelector().add("amount");
				ev.setFilter(filter);
				PaymentBillCollection coll = PaymentBillFactory
					.getRemoteInstance().getPaymentBillCollection(ev);
				for(int i =0;i < coll.size();i++){
					PaymentBillInfo info = coll.get(i);
					FeeTypeInfo feeType = null;
					String feeTypeName = "";
					if(info.getObjectValue("feeType") == null){
						feeTypeName = "nulltype";
					}
					else if(info.getObjectValue("feeType") instanceof FeeTypeInfo){
						feeType = (FeeTypeInfo)info.getObjectValue("feeType");
						feeTypeName = feeType.getName();
					}
					
					BigDecimal amount = info.getBigDecimal("amount");
					if(map.containsKey(feeTypeName)){
						BigDecimal amt = (BigDecimal)map.get(feeTypeName);
						map.put(feeTypeName,amount.add(amt));
					}else{
						map.put(feeTypeName,amount);
					}
			
				}
				Set set = map.keySet();
				for(Iterator it=set.iterator();it.hasNext();){
					String key = (String)it.next();
					if(key.equals("nulltype"))
						payDetails +=""+map.get(key)+"\r\n";
					else
						payDetails +=key+":"+map.get(key)+"\r\n";
				}
			}

			
			drs.beforeFirst();
			//取已经付款的日期和金额
			String paidDataAmount = "";
			if(fdcBill.getId()!=null){
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				FilterItemCollection items = filter.getFilterItems();
				items.add(new FilterItemInfo("contractBillId", fdcBill.getContractId()));
				view.setFilter(filter);
				SorterItemInfo sorterItemInfo = new SorterItemInfo("createTime");
				sorterItemInfo.setSortType(SortType.ASCEND);
				view.getSorter().add(sorterItemInfo);
				view.getSelector().add("payDate");
				view.getSelector().add("localAmt");
				PaymentBillCollection col = PaymentBillFactory.getRemoteInstance().getPaymentBillCollection(view);
				for(int i=0;i<col.size();i++){
					PaymentBillInfo info = col.get(i);
					String payDate = printStringHelper(info.getPayDate());
					String Amt = printStringHelper(info.getLocalAmt());
					paidDataAmount += payDate+":"+Amt+"\r\n";
				}
			}
			//取原合同编码
			String oldProjNumber = "";
			if(fdcBill.getId()!=null){
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				FilterItemCollection items = filter.getFilterItems();
				items.add(new FilterItemInfo("parent", fdcBill.getContractId()));
				//陆家嘴，硬编码
				items.add(new FilterItemInfo("detail", "%原合同编码%" ,CompareType.LIKE));
				view.setFilter(filter);
//				SorterItemInfo sorterItemInfo = new SorterItemInfo("createTime");
//				sorterItemInfo.setSortType(SortType.ASCEND);
//				view.getSorter().add(sorterItemInfo);
				view.getSelector().add("content");				
				ContractBillEntryCollection col = ContractBillEntryFactory.getRemoteInstance().getContractBillEntryCollection(view);
				for(int i=0;i<col.size();i++){
					ContractBillEntryInfo info = col.get(i);
					String temp = printStringHelper(info.getContent());
					oldProjNumber += temp+"\r\n";
				}
			}
			
			//取审批意见 auditResult
			String auditResult = "";
			if(fdcBill.getId()!=null){
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				FilterItemCollection items = filter.getFilterItems();
				items.add(new FilterItemInfo("billId", fdcBill.getId().toString()));
				view.setFilter(filter);
				SorterItemInfo sorterItemInfo = new SorterItemInfo("createTime");
				sorterItemInfo.setSortType(SortType.ASCEND);
				view.getSorter().add(sorterItemInfo);
				view.getSelector().add("opinion");
				view.getSelector().add("creator.person.name");
				MultiApproveCollection col = MultiApproveFactory.getRemoteInstance().getMultiApproveCollection(view);
				for(int i=0;i<col.size();i++){
					MultiApproveInfo info = col.get(i);
					String option = info.getOpinion();
					String person = info.getCreator().getPersonId().getName();
					auditResult += person+":"+option+"\r\n";
				}
			}
			
			//取扣款类型
			DeductOfPayReqBillCollection c = null;
			DeductOfPayReqBillInfo info = null;
			
			if(fdcBill.getId()!=null){
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				FilterItemCollection items = filter.getFilterItems();
				items.add(new FilterItemInfo("payRequestBill.id", fdcBill.getId().toString()));
				view.setFilter(filter);
				SorterItemInfo sorterItemInfo = new SorterItemInfo("deductType.number");
				sorterItemInfo.setSortType(SortType.ASCEND);
				view.getSorter().add(sorterItemInfo);
				view.getSelector().add("deductType.number");
				view.getSelector().add("deductType.name");
				view.getSelector().add("*");
				c = DeductOfPayReqBillFactory.getRemoteInstance().getDeductOfPayReqBillCollection(view);
			}
			
			//取合同信息
			
			String contractBillAmount = "";
			String contractBillType   = "";
			if(fdcBill.getId()!=null){
				// 修复 R100707-082一张付款申请单套打时取不到数据。 By Owen_wen 2010-7-27
				// 文本合同和无文本合同的付款申请单共用EditUI，所以套打时要分别处理
				boolean isCon = FDCUtils.isContractBill(null, fdcBill.getContractId().toString());
				if(isCon){
					ContractBillInfo contract = ContractBillFactory.getRemoteInstance().getContractBillInfo("select amount,contractType.name where id='"+fdcBill.getContractId().toString()+"'");
					contractBillAmount = printStringHelper(contract.getAmount());
					contractBillType   = contract.getContractType().getName();
				}else{
					ContractWithoutTextInfo contract = ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextInfo("select amount,contractType.name where id='"+fdcBill.getContractId().toString()+"'");
					contractBillAmount = printStringHelper(contract.getAmount());
					contractBillType   = contract.getContractType().getName();
				}
			}
			
			
			Map deductTypeSum = new HashMap();
			deductTypeSum.put("sumLstDeductPaid",FDCHelper.ZERO);
			deductTypeSum.put("sumLstDeductReq",FDCHelper.ZERO);
			deductTypeSum.put("sumDeduct",FDCHelper.ZERO);
			deductTypeSum.put("sumAllDeductReq",FDCHelper.ZERO);
			deductTypeSum.put("sumAllDeductPaid",FDCHelper.ZERO);
			
			//TODO 处理没有循环的打印，将每个drs的值都设置为最后一个
			String lstRealPaidAmt = "";
			/*
			 * R110726-0274:套打-房地产的付款申请单套打默认打印5份  by zhiyuan_tang
			 * 我实在忍不住要吐槽一下：天堂有路你不走，不知道为什么要把申请单的套打做成这个鬼样子，放着好好的Query不用，
			 * 非要自己手动取数一个字段一个字段的更新，纠结死我了。。
			 */
			if(c==null || c.size()==0){
				DeductTypeCollection typeCol  = null;
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				FilterItemCollection items = filter.getFilterItems();
				items.add(new FilterItemInfo("isEnabled", Boolean.TRUE));
				view.setFilter(filter);
				typeCol  = DeductTypeFactory.getRemoteInstance().getDeductTypeCollection(view);
				
				for(int i=0;i<typeCol.size();i++){
					DeductTypeInfo deductTypeInfo = typeCol.get(i);
					drs.moveToInsertRow();
					insert( drs,null,deductTypeSum );
					drs.updateString("deductType",deductTypeInfo==null?"":deductTypeInfo.getName());
				}
				//审批意见
				drs.updateString("auditResult",auditResult);
				//已付日期金额
				drs.updateString("paidDataAmount",paidDataAmount);
				//原合同编码
				drs.updateString("oldProjNumber",oldProjNumber);
				drs.updateString("paidDetail",payDetails);
				
				drs.updateString("contractBillAmount",contractBillAmount);
				drs.updateString("contractBillType",contractBillType);
				
				if(fdcBill.getApplier()!=null){
					drs.updateString("applier", fdcBill.getApplier().getName());
				}
				if(fdcBill.getCostedDept()!=null){
					drs.updateString("costedDept", fdcBill.getCostedDept().getName());
				}
				if(fdcBill.getCostedCompany()!=null){
					drs.updateString("costedCompany", fdcBill.getCostedCompany().getName());
				}
				if(fdcBill.getPayBillType()!=null){
					drs.updateString("payBillType", fdcBill.getPayBillType().getName());
				}
				if(fdcBill.getPayContentType()!=null){
					drs.updateString("payContentType", fdcBill.getPayContentType().getName());
				}
				if(fdcBill.getBgEntry().size()>0){
					drs.updateString("requestAmount", printStringHelper(fdcBill.getBgEntry().get(0).getRequestAmount()));
					if(fdcBill.getBgEntry().get(0).getBgItem()!=null){
						drs.updateString("bgItem.number", fdcBill.getBgEntry().get(0).getBgItem().getNumber());
						drs.updateString("bgItem.name", fdcBill.getBgEntry().get(0).getBgItem().getName());
					}
					if(fdcBill.getBgEntry().get(0).getAccountView()!=null){
						drs.updateString("accountView.number", fdcBill.getBgEntry().get(0).getAccountView().getNumber());
						drs.updateString("accountView.name", fdcBill.getBgEntry().get(0).getAccountView().getName());
						drs.updateString("accountView.longNumber", fdcBill.getBgEntry().get(0).getAccountView().getLongNumber());
						drs.updateString("accountView.longName", fdcBill.getBgEntry().get(0).getAccountView().getLongName());
					}
				}
				String agentPayCompany="";
//				FDCSQLBuilder _builder = new FDCSQLBuilder();
//				_builder.appendSql(" select distinct company.fname_l2 name from T_CAS_PaymentBill pay left join T_ORG_Company company on pay.FAgentPayCompanyID=company.fid ");
//				_builder.appendSql(" where pay.fbillstatus=15 and company.fid is not null and pay.FFdcPayReqID='"+fdcBill.getId().toString()+"'");
//				IRowSet rowSet = _builder.executeQuery();
//				if(rowSet.size()==1){
//					while (rowSet.next()) {
//						agentPayCompany=rowSet.getString("name");
//					}
//				}else if(rowSet.size()>1){
//					while (rowSet.next()) {
//						agentPayCompany=rowSet.getString("name")+","+agentPayCompany;
//					}
//					agentPayCompany=agentPayCompany.substring(0,agentPayCompany.lastIndexOf(","));
//				}
				drs.updateString("agentCompany.name", agentPayCompany);
				
				String payNo="";
				FDCSQLBuilder _builder = new FDCSQLBuilder();
				_builder.appendSql(" select pay.fnumber number from T_CAS_PaymentBill pay ");
				_builder.appendSql(" where pay.FFdcPayReqID='"+fdcBill.getId().toString()+"' order by pay.fcreateTime");
				IRowSet rowSet = _builder.executeQuery();
				while (rowSet.next()) {
					payNo=rowSet.getString("number");
				}
				drs.updateString("payNo", payNo);
				
				drs.insertRow();
				
			}else{
				
				for(int i=0;i<c.size();i++){
					info = c.get(i);
					
					drs.moveToInsertRow();
					insert( drs,info ,deductTypeSum);
				}
				
				//审批意见
				drs.updateString("auditResult",auditResult);
				//已付日期金额
				drs.updateString("paidDataAmount",paidDataAmount);
				//原合同编码
				drs.updateString("oldProjNumber",oldProjNumber);
				drs.updateString("paidDetail",payDetails);
				
				drs.updateString("contractBillAmount",contractBillAmount);
				drs.updateString("contractBillType",contractBillType);
				
				if(fdcBill.getApplier()!=null){
					drs.updateString("applier", fdcBill.getApplier().getName());
				}
				if(fdcBill.getCostedDept()!=null){
					drs.updateString("costedDept", fdcBill.getCostedDept().getName());
				}
				if(fdcBill.getCostedCompany()!=null){
					drs.updateString("costedCompany", fdcBill.getCostedCompany().getName());
				}
				if(fdcBill.getPayBillType()!=null){
					drs.updateString("payBillType", fdcBill.getPayBillType().getName());
				}
				if(fdcBill.getPayContentType()!=null){
					drs.updateString("payContentType", fdcBill.getPayContentType().getName());
				}
				if(fdcBill.getBgEntry().size()>0){
					drs.updateString("requestAmount", printStringHelper(fdcBill.getBgEntry().get(0).getRequestAmount()));
					if(fdcBill.getBgEntry().get(0).getBgItem()!=null){
						drs.updateString("bgItem.number", fdcBill.getBgEntry().get(0).getBgItem().getNumber());
						drs.updateString("bgItem.name", fdcBill.getBgEntry().get(0).getBgItem().getName());
					}
					if(fdcBill.getBgEntry().get(0).getAccountView()!=null){
						drs.updateString("accountView.number", fdcBill.getBgEntry().get(0).getAccountView().getNumber());
						drs.updateString("accountView.name", fdcBill.getBgEntry().get(0).getAccountView().getName());
						drs.updateString("accountView.longNumber", fdcBill.getBgEntry().get(0).getAccountView().getLongNumber());
						drs.updateString("accountView.longName", fdcBill.getBgEntry().get(0).getAccountView().getLongName());
					}
				}
				String agentPayCompany="";
//				FDCSQLBuilder _builder = new FDCSQLBuilder();
//				_builder.appendSql(" select distinct company.fname_l2 name from T_CAS_PaymentBill pay left join T_ORG_Company company on pay.FAgentPayCompanyID=company.fid ");
//				_builder.appendSql(" where pay.fbillstatus=15 and company.fid is not null and pay.FFdcPayReqID='"+fdcBill.getId().toString()+"'");
//				IRowSet rowSet = _builder.executeQuery();
//				if(rowSet.size()==1){
//					while (rowSet.next()) {
//						agentPayCompany=rowSet.getString("name");
//					}
//				}else if(rowSet.size()>1){
//					while (rowSet.next()) {
//						agentPayCompany=rowSet.getString("name")+","+agentPayCompany;
//					}
//					agentPayCompany=agentPayCompany.substring(0,agentPayCompany.lastIndexOf(","));
//				}
				drs.updateString("agentCompany.name", agentPayCompany);
				
				
				String payNo="";
				FDCSQLBuilder _builder = new FDCSQLBuilder();
				_builder.appendSql(" select pay.fnumber number from T_CAS_PaymentBill pay ");
				_builder.appendSql(" where pay.FFdcPayReqID='"+fdcBill.getId().toString()+"' order by pay.fcreateTime");
				IRowSet rowSet = _builder.executeQuery();
				while (rowSet.next()) {
					payNo=rowSet.getString("number");
				}
				drs.updateString("payNo", payNo);
				
				lstRealPaidAmt = drs.getString("lstRealPaidAmt");
				drs.insertRow();
			}			
			drs.beforeFirst();
			
			while(drs.next()){
				drs.updateString("lstRealPaidAmt",lstRealPaidAmt);
			}
			drs.beforeFirst();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

		return drs;
	}

	//获取对应合同的汇率
	public BigDecimal getConExRate(String contractId){
		ContractBillInfo info = null;
		BigDecimal exRate = FDCHelper.ONE;
		try {
			info =ContractBillFactory
				.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractId)));
			exRate = info.getExRate();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (UuidException e) {
			e.printStackTrace();
		}
		return exRate;
	}
	/**
	 * 累计完工工程量取界面上“累计完工工程量”的金额。（当界面上显示本期完工工程量时，此数据源才显示数据，否则，为空 ）
	 * @return
	 */
	private void initAllCompletePrjAmt(){
		if(!fdcBill.getBoolean("isCompletePrjAmtVisible")){
			fdcBill.put("allCompletePrjAmt", "");
			return;
		}
//		BigDecimal allCompleteProjAmt = FDCHelper.ZERO;
//		String paymentType = fdcBill.getPaymentType().getPayType().getId().toString();
//    	String progressID = TypeInfo.progressID;
//    	if(!paymentType.equals(progressID)){
//    		allCompleteProjAmt =FDCHelper.toBigDecimal(contractBill.getSettleAmt(),2);
//    		fdcBill.put("allCompletePrjAmt", allCompleteProjAmt);
//    		return;
//    	}
//    	
//    	EntityViewInfo view = new EntityViewInfo();
//    	view.getSelector().add("completePrjAmt");
//    	view.getSelector().add("state");
//    	FilterInfo filter = new FilterInfo();
//    	filter.getFilterItems().add(new FilterItemInfo("contractId", fdcBill.getContractId()));
//    	filter.getFilterItems().add(new FilterItemInfo("paymentType.payType.id", progressID));
//    	filter.getFilterItems().add(new FilterItemInfo("createTime", fdcBill.getCreateTime(), CompareType.LESS_EQUALS));
//    	view.setFilter(filter);
//    	PayRequestBillCollection payReqColl = null;
//		try {
//			payReqColl = PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection(view);
//		} catch (BOSException e) {
//			// TODO 自动生成 catch 块
//			e.printStackTrace();
//		}
//    	
//    	if(payReqColl != null){
//    		for(int i=0;i<payReqColl.size();i++){
//    			PayRequestBillInfo info = payReqColl.get(i);
//    			allCompleteProjAmt = allCompleteProjAmt.add(FDCHelper.toBigDecimal(info.getCompletePrjAmt()));
//    		}
//    	}
//    	allCompleteProjAmt = FDCHelper.toBigDecimal(allCompleteProjAmt, 2);
    	fdcBill.put("allCompletePrjAmt", fdcBill.get("allCompletePrjAmt"));
	}
}
