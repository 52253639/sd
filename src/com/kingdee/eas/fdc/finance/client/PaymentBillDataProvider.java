package com.kingdee.eas.fdc.finance.client;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;

public class PaymentBillDataProvider extends FDCBillDataProvider {
	static public final String[] col = new String[] { "id", "bookedDate",
			"number", "useDepartment.name", "period", "payDate", "dayaccount",
			"feeType.name", "recProvince", "recCity",
			"payerAccountBank.bankAccountNumber", "payeeType.name",
			"isDifferPlace", "payerBank.name", "payee", "actFdcPayeeName.name",
			"payerAccount.name", "payeeBank", "currency.name", "bizType.name",
			"payeeAccountBank", "exchangeRate", "isEmergency", "amount",
			"accessoryAmt", "localAmt", "capitalAmount", "paymentProportion",
			"description", "completePrjAmt", "usage", "fpItem.name", "conceit",
			"summary", "settlementType.name", "settlementNumber",
			"payeeArea.name", "creator.number", "creator.name", "createTime",
			"auditor.number", "auditor.name", "auditDate", "accountant.number",
			"accountant.name", "cashier.number", "cashier.name",
			"projNameWithoutOrg", "curProject.displayName", "contractPrice",
			"contractName", "latestPrice", "changeAmt", "settleAmt",
			"payedAmt", "payTimes", "lstPrjAllPaidAmt", "lstPrjAllReqAmt",
			"projectPriceInContract", "prjAllReqAmt", "prjAllPaidAmt",
			"lstGuerdonPaidAmt", "lstGuerdonReqAmt", "guerdonAmt",
			"allGuerdonReqAmt", "allGuerdonPaidAmt", "lstCompensationPaidAmt",
			"lstCompensationReqAmt", "compensationAmt",
			"allCompensationReqAmt", "allCompensationPaidAmt", "deductType",
			"lstDeductPaidAmt", "lstDeductReqAmt", "deductAmt",
			"allDeductReqAmt", "allDeductPaidAmt", "lstAMatlAllPaidAmt",
			"lstAMatlAllReqAmt", "payPartAMatlAmt", "payPartAMatlAllReqAmt",
			"allPartAMatlAllPaidAmt", "lstRealPaidAmt", "lstRealReqAmt",
			"curPaid", "allRealReqAmt", "allRealPaidAmt", "balance",
			"lstDeductSumPaidAmt", "lstDeductSumReqAmt", "deductSumAmt",
			"allDeductSumReqAmt", "allDeductSumPaidAmt", "curPlannedPayment",
			"curBackPay", "curReqPercent", "allReqPercent", "imageSchedule", "contractNo"};
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
	

	private PaymentBillInfo fdcBill = null;
	private PayRequestBillInfo payReqInfo = null;
	private CurProjectInfo curProjectInfo = null;
	private HashMap bindCellMap =null;
	public PaymentBillDataProvider(PaymentBillInfo editData,HashMap bindCellMap,CurProjectInfo curProjectInfo,IMetaDataPK mainQuery) {
		super(editData.getId().toString(), mainQuery);
		fdcBill = editData;
		payReqInfo = (PayRequestBillInfo)editData.get("payReqInfo");
		this.bindCellMap = bindCellMap;
		this.curProjectInfo = curProjectInfo;
	}
	
	private void insert(IRowSet drs,DeductOfPayReqBillInfo info,Map deductTypeSum) throws Exception{
		
		/**************************************************头部start**************************************************/
		//合同编号 
		drs.updateString("contractNo", fdcBill.getContractNo());
		//申请日期
		drs.updateString("bookedDate",printStringHelper(payReqInfo.getBookedDate()));
		//付款单编码
		drs.updateString("number",fdcBill.getNumber());
		//用款部门
		drs.updateString("useDepartment.name",fdcBill.getUseDepartment()!=null?fdcBill.getUseDepartment().getName():null);
		//申请期间
		if(payReqInfo.getPeriod()!=null)
		drs.updateString("period",payReqInfo.getPeriod().getPeriodYear()+"年"+payReqInfo.getPeriod().getPeriodNumber()+"期");
		//付款日期
		drs.updateString("payDate",printStringHelper(fdcBill.getPayDate()));
		//日记账余额
		drs.updateString("dayaccount",printStringHelper(fdcBill.getDayaccount()));
		//收付类别
		drs.updateString("feeType.name",fdcBill.getFeeType()!=null?fdcBill.getFeeType().getName():null);
		//收款方省
		drs.updateString("recProvince",fdcBill.getRecProvince());
		//收款方市/县
		drs.updateString("recCity",fdcBill.getRecCity());
		//付款账号
		drs.updateString("payerAccountBank.bankAccountNumber",fdcBill.getPayerAccountBank()!=null?fdcBill.getPayerAccountBank().getBankAccountNumber():null);
		//收款人类型
		drs.updateString("payeeType.name",fdcBill.getPayeeType()!=null?fdcBill.getPayeeType().getName():null);
		//同城异地
		drs.updateString("isDifferPlace",printStringHelper(fdcBill.getIsDifferPlace()));
		//付款银行
		drs.updateString("payerBank.name",fdcBill.getPayerBank()!=null?fdcBill.getPayerBank().getName():null);
		//收款人名称
		drs.updateString("payee",fdcBill.getPayeeName());
		//实际收款单位全称
		drs.updateString("actFdcPayeeName.name",fdcBill.getActFdcPayeeName()!=null?fdcBill.getActFdcPayeeName().getName():null);
		//付款科目
		drs.updateString("payerAccount.name",fdcBill.getPayerAccount()!=null?fdcBill.getPayerAccount().getName():null);
		//收款银行
		drs.updateString("payeeBank",fdcBill.getPayeeBank());
		//币别
		drs.updateString("currency.name",fdcBill.getCurrency()!=null?fdcBill.getCurrency().getName():null);
		//业务种类
		drs.updateString("bizType.name",fdcBill.getBizType()!=null?fdcBill.getBizType().getName():null);
		//收款账户
		drs.updateString("payeeAccountBank",fdcBill.getPayeeAccountBank());
		//汇率
		drs.updateString("exchangeRate",printStringHelper(fdcBill.getExchangeRate()));
		//是否加急
		drs.updateString("isEmergency",printStringHelper(fdcBill.getIsEmergency()));
		//原币金额
		drs.updateString("amount",printStringHelper(fdcBill.getAmount()));
		//附件数
		drs.updateString("accessoryAmt",String.valueOf(fdcBill.getAccessoryAmt()));
		//本币金额
		drs.updateString("localAmt",printStringHelper(fdcBill.getLocalAmt()));
		//大写金额
		drs.updateString("capitalAmount",fdcBill.getCapitalAmount());
		//进度款比例(%)
		drs.updateString("paymentProportion",printStringHelper(payReqInfo.getPaymentProportion()));
		//摘要
		drs.updateString("description",fdcBill.getDescription());
		//本期成本金额
		drs.updateString("completePrjAmt",printStringHelper(payReqInfo.getCompletePrjAmt()));
		//用途
		drs.updateString("usage",fdcBill.getUsage());
		//计划项目
		drs.updateString("fpItem.name",fdcBill.getFpItem()!=null?fdcBill.getFpItem().getName():null);
		//打回意见
		drs.updateString("conceit",fdcBill.getConceit());
		//备注
		drs.updateString("summary",fdcBill.getSummary());
		//结算方式
		drs.updateString("settlementType.name",fdcBill.getSettlementType()!=null?fdcBill.getSettlementType().getName():null);
		//结算号
		drs.updateString("settlementNumber",fdcBill.getSettlementNumber());
		//制单人
		drs.updateString("creator.number",fdcBill.getCreator()!=null?fdcBill.getCreator().getNumber():null);
		//制单人
		drs.updateString("creator.name",fdcBill.getCreator()!=null?fdcBill.getCreator().getName():null);
		//制单时间
		drs.updateString("createTime",printStringHelper(fdcBill.getCreateTime()));
		//审核人
		drs.updateString("auditor.number",fdcBill.getAuditor()!=null?fdcBill.getAuditor().getNumber():null);
		//审核人
		drs.updateString("auditor.name",fdcBill.getAuditor()!=null?fdcBill.getAuditor().getName():null);
		//审核日期
		drs.updateString("auditDate",printStringHelper(fdcBill.getAuditDate()));
		//会计编号
		drs.updateString("accountant.number",fdcBill.getAccountant()!=null?fdcBill.getAccountant().getNumber():null);
		//会计名称
		drs.updateString("accountant.name",fdcBill.getAccountant()!=null?fdcBill.getAccountant().getName():null);
		//出纳编号
		drs.updateString("cashier.number",fdcBill.getCashier()!=null?fdcBill.getCashier().getNumber():null);
		//出纳名称
		drs.updateString("cashier.number",fdcBill.getCashier()!=null?fdcBill.getCashier().getNumber():null);
		/**************************************************头部start**************************************************/
		
		
		
		/**************************************************工程付款情况表start**************************************************/
		BigDecimal sumLstDeductPaid = (BigDecimal)deductTypeSum.get("sumLstDeductPaid");
		BigDecimal sumLstDeductReq =  (BigDecimal)deductTypeSum.get("sumLstDeductReq");
		BigDecimal sumDeduct =  (BigDecimal)deductTypeSum.get("sumDeduct");
		BigDecimal sumAllDeductReq =  (BigDecimal)deductTypeSum.get("sumAllDeductReq");
		BigDecimal sumAllDeductPaid = (BigDecimal)deductTypeSum.get("sumAllDeductPaid");
		
//		 在此把数据传递给实现类，drs.updateString(key,value) key
		// 指的是套打模板中定义的字段编码，Value指的是当前单据的属性值
		String orgName=((CurProjectInfo)fdcBill.getCurProject()).getFullOrgUnit().getName();
		String curProjectName = curProjectInfo.getDisplayName();		
		//取出的数据要求只取项目名称
		//2008-07-22
		String projNameWithoutOrg = curProjectName.replace('_', '\\');
		curProjectName = orgName+"\\"+curProjectName.replace('_', '\\');
		drs.updateString("curProject.displayName", curProjectName);
		drs.updateString("projNameWithoutOrg", projNameWithoutOrg);
		drs.updateString("contractName",payReqInfo.getContractName());
		drs.updateString("contractPrice", printStringHelper(payReqInfo.getContractPrice()));
		drs.updateString("latestPrice",printStringHelper(fdcBill.getLatestPrice()));
		drs.updateString("changeAmt",printStringHelper(fdcBill.getChangeAmt()));
		drs.updateString("settleAmt",printStringHelper(fdcBill.getSettleAmt()));
		drs.updateString("payedAmt", printStringHelper(fdcBill.getPayedAmt()));
		drs.updateString("payTimes", String.valueOf(fdcBill.getPayTimes()));
		
		drs.updateString("lstPrjAllPaidAmt", printStringHelper(fdcBill
				.getLstPrjAllPaidAmt()));
		drs.updateString("lstPrjAllReqAmt", printStringHelper(fdcBill
				.getLstPrjAllReqAmt()));
		drs.updateString("projectPriceInContract",
				printStringHelper(fdcBill.getProjectPriceInContract()));
		drs.updateString("prjAllReqAmt", printStringHelper(fdcBill
				.getPrjAllReqAmt()));
		//合同内付款 截至本期累计实付 = 截至上期累计累计实付 +本期
		BigDecimal prjAllPaidAmt = (fdcBill.getLstPrjAllPaidAmt()!=null?fdcBill.getLstPrjAllPaidAmt():FDCHelper.ZERO);
		prjAllPaidAmt = FDCHelper.add(prjAllPaidAmt,fdcBill.getProjectPriceInContract());
		drs.updateString("prjAllPaidAmt", printStringHelper(prjAllPaidAmt));
		// 奖励 截至上期累计实付
		BigDecimal lstGuerdonPaidAmt = FDCHelper.toBigDecimal(((ICell)( bindCellMap.get("lstGuerdonPaidAmt"))).getValue());				
		drs.updateString("lstGuerdonPaidAmt",printStringHelper(lstGuerdonPaidAmt));
		// 奖励 截至上期累计申请
		BigDecimal lstGuerdonReqAmt = FDCHelper.toBigDecimal(( (ICell)( bindCellMap.get("lstGuerdonReqAmt"))).getValue());
		drs.updateString("lstGuerdonReqAmt", printStringHelper(lstGuerdonReqAmt));
		// 奖励 本期发生				
		BigDecimal guerdonAmt = FDCHelper.toBigDecimal(((ICell)( bindCellMap.get("guerdonAmt"))).getValue());
		guerdonAmt = guerdonAmt!= null?guerdonAmt: FDCHelper.ZERO;
		drs.updateString("guerdonAmt", printStringHelper(guerdonAmt));
		// 奖励 截至本期累计申请 = 奖励 截至上期累计申请 + 奖励 本期发生
		// 奖励 截至本期累计实付 = 奖励 截至上期累计实付 + 本期
		BigDecimal allGuerdonReqAmt = guerdonAmt.add(lstGuerdonReqAmt!= null ? lstGuerdonReqAmt : FDCHelper.ZERO);
		BigDecimal allGuerdonPaidAmt = guerdonAmt.add(lstGuerdonPaidAmt!= null ?lstGuerdonPaidAmt: FDCHelper.ZERO);
		drs.updateString("allGuerdonReqAmt",printStringHelper(allGuerdonReqAmt));
		drs.updateString("allGuerdonPaidAmt",printStringHelper(allGuerdonPaidAmt));
		
		// 违约 截至上期累计实付
		BigDecimal lstCompensationPaidAmt = FDCHelper.toBigDecimal(( (ICell)( bindCellMap.get("lstCompensationPaidAmt"))).getValue());
		drs.updateString("lstCompensationPaidAmt",printStringHelper(lstCompensationPaidAmt));
		
		// 违约 截至上期累计申请
		BigDecimal lstCompensationReqAmt = FDCHelper.toBigDecimal(( (ICell)( bindCellMap.get("lstCompensationReqAmt"))).getValue());
		drs.updateString("lstCompensationReqAmt", printStringHelper(lstCompensationReqAmt));
		// 违约 本期发生
		BigDecimal compensationAmt = FDCHelper.toBigDecimal(( (ICell)( bindCellMap.get("compensationAmt"))).getValue());
		compensationAmt = compensationAmt != null? compensationAmt: FDCHelper.ZERO;
		drs.updateString("compensationAmt", printStringHelper(compensationAmt));
		// 违约 截至本期累计申请 = 违约 截至上期累计申请 + 违约 本期发生
		// 违约 截至本期累计实付 = 违约 截至上期累计实付 +本期				
		BigDecimal allCompensationReqAmt = compensationAmt.add(lstCompensationReqAmt!= null 
												? lstCompensationReqAmt: FDCHelper.ZERO);
		BigDecimal allCompensationPaidAmt = compensationAmt.add(lstCompensationPaidAmt != null 
												? lstCompensationPaidAmt: FDCHelper.ZERO);
		drs.updateString("allCompensationReqAmt",	printStringHelper(allCompensationReqAmt));
		drs.updateString("allCompensationPaidAmt",printStringHelper(allCompensationPaidAmt));
		
		//扣款项
		//扣款类型				
		if(info!=null){
			drs.updateString("deductType",info==null?"":info.getDeductType().getName());
			BigDecimal lstDeductPaidAmt = info.getAllPaidAmt()==null?FDCHelper.ZERO:info.getAllPaidAmt();
			BigDecimal lstDeductReqAmt  = info.getAllReqAmt()==null?FDCHelper.ZERO:info.getAllReqAmt();
			BigDecimal deductAmt        = info.getAmount()==null?FDCHelper.ZERO:info.getAmount();
			BigDecimal allDeductReqAmt  = lstDeductReqAmt.add(deductAmt);
			BigDecimal allDeductPaidAmt  = lstDeductPaidAmt.add(deductAmt);
			
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
		
		drs.updateString("lstDeductSumPaidAmt",printStringHelper(sumLstDeductPaid));
		drs.updateString("lstDeductSumReqAmt",printStringHelper(sumLstDeductReq));
		drs.updateString("deductSumAmt",printStringHelper(sumDeduct));
		drs.updateString("allDeductSumReqAmt",printStringHelper(sumAllDeductReq));
		drs.updateString("allDeductSumPaidAmt",printStringHelper(sumAllDeductPaid));					
		
		//甲供 截至上期累计申请
		drs.updateString("lstAMatlAllPaidAmt",printStringHelper(fdcBill.getLstAMatlAllPaidAmt()));
		//甲供 截至上期累计申请
		drs.updateString("lstAMatlAllReqAmt",printStringHelper(fdcBill.getLstAMatlAllReqAmt()));
		//甲供 本期发生
		drs.updateString("payPartAMatlAmt",printStringHelper(fdcBill.getPayPartAMatlAmt()));
//		BigDecimal AMatlAllReqAmt = FDCHelper.add(fdcBill.getLstAMatlAllReqAmt(),fdcBill.getPayPartAMatlAmt());
		//甲供 截至本期累计申请
		drs.updateString("payPartAMatlAllReqAmt",printStringHelper(fdcBill.getPayPartAMatlAllReqAmt()));
		//甲供材 截至本期累计实付 = 甲供材 截至上期累计实付 +本期
		BigDecimal allPartAMatlAllPaidAmt = (fdcBill.getLstAMatlAllPaidAmt()!=null?fdcBill
				.getLstAMatlAllPaidAmt():FDCHelper.ZERO);
		// 甲供材 截至上期累计实付
				//.add(fdcBill.getPayPartAMatlAmt()!=null?fdcBill.getPayPartAMatlAmt()
						//:FDCHelper.ZERO);// 甲供材 本期发生
		allPartAMatlAllPaidAmt = FDCHelper.add(allPartAMatlAllPaidAmt,fdcBill.getPayPartAMatlAmt());
		drs.updateString("allPartAMatlAllPaidAmt",printStringHelper(allPartAMatlAllPaidAmt));
		
		//实付款 本期发生 = 合同工程款 + 奖励 - 违约金 - 扣款小计 - 甲供材
		BigDecimal projectPriceInContract = fdcBill.getProjectPriceInContract()!=null?fdcBill.getProjectPriceInContract():FDCHelper.ZERO;
//		BigDecimal tempCurPaid = fdcBill.getCurPaid()!= null ? fdcBill.getCurPaid(): FDCHelper.ZERO;
		BigDecimal tempCurPaid = projectPriceInContract.add(guerdonAmt)//奖励
		.subtract(compensationAmt)//违约
		.subtract(sumDeduct)//扣款
		.subtract(fdcBill.getPayPartAMatlAmt() != null ? fdcBill
						.getPayPartAMatlAmt() : FDCHelper.ZERO);//甲供材
		drs.updateString("curPaid", printStringHelper(tempCurPaid));
		
		//实付款 截至上期累计实付 = 合同工程款 + 奖励 - 违约金 - 扣款小计 - 甲供材
		BigDecimal lstRealPaidAmt = FDCHelper.toBigDecimal(fdcBill.getLstPrjAllPaidAmt());
		lstRealPaidAmt = lstRealPaidAmt.add(lstGuerdonPaidAmt)//奖励
				.subtract(lstCompensationPaidAmt)//违约
				.subtract(sumLstDeductPaid)//扣款
				.subtract(fdcBill.getLstAMatlAllPaidAmt() != null ? fdcBill
								.getLstAMatlAllPaidAmt()
								: FDCHelper.ZERO);//甲供材
		drs.updateString("lstRealPaidAmt",printStringHelper(lstRealPaidAmt));
		
		//实付款 截至上期累计申请 = 合同工程款 + 奖励 - 违约金 - 扣款小计 - 甲供材
		BigDecimal lstRealReqAmt = FDCHelper.toBigDecimal(fdcBill.getLstPrjAllReqAmt());
		lstRealReqAmt = lstRealReqAmt.add(lstGuerdonReqAmt)//奖励
				.subtract(lstCompensationReqAmt)//违约
				.subtract(sumLstDeductReq)//扣款
				.subtract(fdcBill.getLstAMatlAllReqAmt() != null ? fdcBill
								.getLstAMatlAllReqAmt()
								: FDCHelper.ZERO);//甲供材
		drs.updateString("lstRealReqAmt",printStringHelper(lstRealReqAmt));
		
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
//		BigDecimal prjAllReqAmt =  fdcBill.getPrjAllReqAmt();
		BigDecimal allRealPaidAmt = prjAllReqAmt
				.add(allGuerdonPaidAmt)//奖励
				.subtract(allCompensationPaidAmt)//违约
				.subtract(sumAllDeductPaid)//扣款
				.subtract(allPartAMatlAllPaidAmt);//甲供材
		drs.updateString("allRealPaidAmt",printStringHelper(allRealPaidAmt));
		
		// 余款 = 最新造价 - 合同内付款 截至本期累计实付
		BigDecimal balance = (fdcBill
		.getLatestPrice() == null ? FDCHelper.ZERO : fdcBill.getLatestPrice())				
		.subtract(prjAllPaidAmt);//合同内付款 截至本期累计实付
		
		drs.updateString("balance",printStringHelper(balance));
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
		
		drs.updateString("allReqPercent",
				printStringHelper((tempAllCurReqPercent.multiply(FDCConstants.ONE_HUNDRED)).setScale(2)));
		// ///
		drs.updateString("imageSchedule", printStringHelper(fdcBill
				.getImageSchedule()));
		/**************************************************工程付款情况表end**************************************************/
	}

	public IRowSet getMainBillRowSet(BOSQueryDataSource ds) {
		DynamicRowSet drs = null;
		try {
			drs = new DynamicRowSet(col.length);
			for (int i = 0; i < col.length; i++) {
				ColInfo ci = new ColInfo();
				ci.colType = Types.VARCHAR;
				ci.columnName = col[i];
				ci.nullable = 1;
				drs.setColInfo(i + 1, ci);
			}
			drs.beforeFirst();
			
			//取扣款类型
			DeductOfPayReqBillCollection c = null;
			DeductOfPayReqBillInfo info = null;
			
			if(fdcBill.getId()!=null){
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				FilterItemCollection items = filter.getFilterItems();
				items.add(new FilterItemInfo("payRequestBill.id", payReqInfo.getId().toString()));
				view.setFilter(filter);
				SorterItemInfo sorterItemInfo = new SorterItemInfo("deductType.number");
				sorterItemInfo.setSortType(SortType.ASCEND);
				view.getSorter().add(sorterItemInfo);
				view.getSelector().add("deductType.number");
				view.getSelector().add("deductType.name");
				view.getSelector().add("*");
				c = DeductOfPayReqBillFactory.getRemoteInstance().getDeductOfPayReqBillCollection(view);
			}
			if(c!=null){
				Map deductTypeSum = new HashMap();
				deductTypeSum.put("sumLstDeductPaid",FDCHelper.ZERO);
				deductTypeSum.put("sumLstDeductReq",FDCHelper.ZERO);
				deductTypeSum.put("sumDeduct",FDCHelper.ZERO);
				deductTypeSum.put("sumAllDeductReq",FDCHelper.ZERO);
				deductTypeSum.put("sumAllDeductPaid",FDCHelper.ZERO);
				for(int i=0;i<c.size();i++){
					info = c.get(i);
					drs.moveToInsertRow();
					insert( drs,info ,deductTypeSum);
					drs.insertRow();
				}
			}
			drs.beforeFirst();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return drs;
	}


}
