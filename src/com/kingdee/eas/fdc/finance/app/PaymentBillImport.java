package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.AccountBankFactory;
import com.kingdee.eas.basedata.assistant.AccountBankInfo;
import com.kingdee.eas.basedata.assistant.BankInfo;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeAuxFactory;
import com.kingdee.eas.basedata.assistant.ExchangeAuxInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateFactory;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.assistant.ProjectInfo;
import com.kingdee.eas.basedata.assistant.SettlementTypeFactory;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.account.AccountViewFactory;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBaseDataInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.DifPlaceEnum;
import com.kingdee.eas.fi.cas.FdcPayeeTypeEnum;
import com.kingdee.eas.fi.cas.FeeTypeFactory;
import com.kingdee.eas.fi.cas.FeeTypeInfo;
import com.kingdee.eas.fi.cas.IsMergencyEnum;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fm.fpl.FpItemFactory;
import com.kingdee.eas.fm.fpl.FpItemInfo;
import com.kingdee.eas.fm.fs.SettBizTypeFactory;
import com.kingdee.eas.fm.fs.SettBizTypeInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.util.ResourceBase;

/**
 * 
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		付款单引入引出实现类
 *		
 * @author			liangwenjie
 * @version		EAS7.0		
 * @createDate		2011-7-22	 
 * @see
 */
public class PaymentBillImport extends AbstractDataTransmission {

	private static String resource = "com.kingdee.eas.fdc.finance.FDCBudgetAcctResource";
	
	protected ICoreBase getController(Context ctx)
			throws TaskExternalException {
		
		ICoreBase factory = null;
		try {
			factory = PaymentBillFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	/**
	 * 
	 * @description		付款单导入，开发：梁文杰。后期BUG修改：雍定文
	 * @author				雍定文		
	 * @createDate			2011-7-22
	 * @param				hsData
	 * @param				ctx
	 * @return				CoreBaseInfo
	 * @version			EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.IDataTransmission#transmit(java.util.Hashtable, com.kingdee.bos.Context)
	 */
	public CoreBaseInfo transmit(Hashtable hsData, Context ctx) throws TaskExternalException {
		
		PaymentBillInfo info = new PaymentBillInfo();
		
		//取值
		String FCostCenter_longNumber = FDCTransmissionHelper.getFieldValue(hsData, "FCostCenter_longNumber");//组织编码
		String FCurProject_longNumber = FDCTransmissionHelper.getFieldValue(hsData, "FCurProject_longNumber");//*工程项目长编码
		String FAgentPayCompany_name = FDCTransmissionHelper.getFieldValue(hsData, "FAgentPayCompany_name_l2");//代理付款公司
		String FPayBillType_name = FDCTransmissionHelper.getFieldValue(hsData, "FPayBillType_name_l2");//*付款类型
		String FContractNo = FDCTransmissionHelper.getFieldValue(hsData, "FContractNo");//*合同编号
		String FBillStatus = FDCTransmissionHelper.getFieldValue(hsData, "FBillStatus");//单据状态
		String FNumber = FDCTransmissionHelper.getFieldValue(hsData, "FNumber");//*付款单编码
		String payReqNumber = FDCTransmissionHelper.getFieldValue(hsData, "payReqNumber");// *
																							// 付款申请单编码
		
		String FUseDepartment_name = FDCTransmissionHelper.getFieldValue(hsData, "FUseDepartment_name_l2");//*用款部门
		String FBizDate = FDCTransmissionHelper.getFieldValue(hsData, "FBizDate");//*业务日期
		FBizDate = forMatDate(FBizDate);
		
		String FFeeType_name = FDCTransmissionHelper.getFieldValue(hsData, "FFeeType_name_l2");//收付类别
		String FRecProvince = FDCTransmissionHelper.getFieldValue(hsData, "FRecProvince");//收款方省
		String FRecCity = FDCTransmissionHelper.getFieldValue(hsData, "FRecCity");//收款方市县
		String FPayerAccountBank_bankAccountNumber = FDCTransmissionHelper.getFieldValue(hsData, "FPayerAccountBank_bankAccountNumber");//付款帐号
		String FFdcPayeeType = FDCTransmissionHelper.getFieldValue(hsData, "FFdcPayeeType");//收款人类别
		String FIsDifferPlace = FDCTransmissionHelper.getFieldValue(hsData, "FIsDifferPlace");//同城异地
		String FPayerBank_name = FDCTransmissionHelper.getFieldValue(hsData, "FPayerBank_name_l2");//付款银行
		String FPayeeName = FDCTransmissionHelper.getFieldValue(hsData, "FPayeeName");//*收款人名称
		String FActFdcPayeeName_name = FDCTransmissionHelper.getFieldValue(hsData, "FActFdcPayeeName_name_l2");//实际收款单位全称
		String FPayerAccount_name = FDCTransmissionHelper.getFieldValue(hsData, "FPayerAccount_name_l2");//付款科目
		String FPayeeBank = FDCTransmissionHelper.getFieldValue(hsData, "FPayeeBank");//收款银行
		String FCurrency_number = FDCTransmissionHelper.getFieldValue(hsData, "FCurrency_number");//*币别编码
		String FBizType_name = FDCTransmissionHelper.getFieldValue(hsData, "FBizType_name_l2");//业务种类
		String FPayeeAccountBank = FDCTransmissionHelper.getFieldValue(hsData, "FPayeeAccountBank");//收款账户
		String FExchangeRate = FDCTransmissionHelper.getFieldValue(hsData, "FExchangeRate");//汇率
		
		String FIsEmergency = FDCTransmissionHelper.getFieldValue(hsData, "FIsEmergency");//是否加急
		String FAmount = FDCTransmissionHelper.getFieldValue(hsData, "FAmount");//原币金额
		//String FFdcPayReqID = FDCTransmissionHelper.getFieldValue(hsData, "FFdcPayReqID");//付款申请单id--------
		String paymentProportion = FDCTransmissionHelper.getFieldValue(hsData, "paymentProportion");//进度款比例
		String completePrjAmt = FDCTransmissionHelper.getFieldValue(hsData, "completePrjAmt");//本期完工工程量(金额)
		String FDescription = FDCTransmissionHelper.getFieldValue(hsData, "FDescription");//摘要
		String FUsage = FDCTransmissionHelper.getFieldValue(hsData, "FUsage");//用途
		String FFpItem_name = FDCTransmissionHelper.getFieldValue(hsData, "FFpItem_name_l2");//计划项目
		String FConceit = FDCTransmissionHelper.getFieldValue(hsData, "FConceit");//打回意见
		String FSettlementType_name = FDCTransmissionHelper.getFieldValue(hsData, "FSettlementType_name_l2");//结算方式
		String FSettlementNumber = FDCTransmissionHelper.getFieldValue(hsData, "FSettlementNumber");//结算号
		String grtAmount = FDCTransmissionHelper.getFieldValue(hsData, "grtAmount");//保修金金额
		String invoiceDate = FDCTransmissionHelper.getFieldValue(hsData, "invoiceDate");//开票日期
		String InvoiceNumber = FDCTransmissionHelper.getFieldValue(hsData, "InvoiceNumber");//发票号
		String invoiceAmt = FDCTransmissionHelper.getFieldValue(hsData, "invoiceAmt");//发票金额
		String FSummary = FDCTransmissionHelper.getFieldValue(hsData, "FSummary");//备注
		String FProject_name = FDCTransmissionHelper.getFieldValue(hsData, "FProject_name_l2");//项目名称
		String FContractBase_name = FDCTransmissionHelper.getFieldValue(hsData, "FContractBase_name_l2");//合同名称
		String FLatestPrice = FDCTransmissionHelper.getFieldValue(hsData, "FLatestPrice");//最新造价
		String ChangeMoney = FDCTransmissionHelper.getFieldValue(hsData, "ChangeMoney");//变更指令单金额
		String FProjectPriceInContract = FDCTransmissionHelper.getFieldValue(hsData, "FProjectPriceInContract");//*合同内工程款_本期发生（原币）
		String FCurPlannedPayment = FDCTransmissionHelper.getFieldValue(hsData, "FCurPlannedPayment");//本期计划付款
		String FCurBackPay = FDCTransmissionHelper.getFieldValue(hsData, "FCurBackPay");//本期欠付款
		String FCurReqPercent = FDCTransmissionHelper.getFieldValue(hsData, "FCurReqPercent");//本次申请%
		String FAllReqPercent = FDCTransmissionHelper.getFieldValue(hsData, "FAllReqPercent");//累计申请%
		String FImageSchedule = FDCTransmissionHelper.getFieldValue(hsData, "FImageSchedule");//形象进度
		String payreq = FDCTransmissionHelper.getFieldValue(hsData, "payreq");//应付申请
		String sumpayreq = FDCTransmissionHelper.getFieldValue(hsData, "sumpayreq");//累计应付申请
		String FCreator_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FCreator_name_l2");//*制单人编码
		String FCreateTime = FDCTransmissionHelper.getFieldValue(hsData, "FCreateTime");//*制单时间
		FCreateTime = forMatDate(FCreateTime);
		
		String FAuditor_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FAuditor_name_l2");//审核人编码
		String FAuditDate = FDCTransmissionHelper.getFieldValue(hsData, "FAuditDate");//审核日期
		FAuditDate = forMatDate(FAuditDate);
		
		String FCashier_name = FDCTransmissionHelper.getFieldValue(hsData, "FCashier_name_l2");//出纳
		String FAccountant_name = FDCTransmissionHelper.getFieldValue(hsData, "FAccountant_name_l2");//会计
		String award = FDCTransmissionHelper.getFieldValue(hsData, "award");//奖励
		String breakcontract = FDCTransmissionHelper.getFieldValue(hsData, "breakcontract");//违约
		String deduct = FDCTransmissionHelper.getFieldValue(hsData, "deduct");//扣款
		String FPayPartAMatlAmt = FDCTransmissionHelper.getFieldValue(hsData, "FPayPartAMatlAmt");//*甲供材扣款
		
		FDCTransmissionHelper.valueFormat("合同编号", FContractNo, "String", true, 80);
		FDCTransmissionHelper.valueFormat("付款单编码", FNumber, "String", true, 80);
		FDCTransmissionHelper.valueFormat("付款申请单编码", payReqNumber, "String", true, 80);
		//首先判断付款申请单 是否存在
		PayRequestBillInfo prbinfo = null;//付款申请单对象
		EntityViewInfo view = null;
		FilterInfo filter = null;
		
		try {// 拿到付款申请单的对象
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("number",payReqNumber,CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("ContractNo",FContractNo,CompareType.EQUALS));
	        view.setFilter(filter);
			prbinfo = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillCollection(view).get(0);
			if (prbinfo == null) {// 付款单编码对应的付款申请单不存在，请先导入付款申请单
				//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource(ctx
				// , "payreqnumberisnull"));
				FDCTransmissionHelper.isThrow("付款单编码对应的付款申请单不存在，请先导入付款申请单");
			}
			// added by Owen_wen 2011-08-23 考虑到客户的应用场景，历史数据付款申请单一定会有“已审批”状态的
			if (!FDCBillStateEnum.AUDITTED_VALUE.equals(prbinfo.getState().getValue())) {
				FDCTransmissionHelper.isThrow("对应付款申请单未审批，不能导入。");
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		
		//验证格式是否正确  是否为空   长度是否超出
		FDCTransmissionHelper.valueFormat("组织编码", FCostCenter_longNumber, "String", false, 80);
		FDCTransmissionHelper.valueFormat("工程项目长编码", FCurProject_longNumber, "String", true, 80);
		FDCTransmissionHelper.valueFormat("代理付款公司", FAgentPayCompany_name, "String", false, 80);
		FDCTransmissionHelper.valueFormat("付款类型", FPayBillType_name, "String", true, 80);
		FDCTransmissionHelper.valueFormat("单据状态", FBillStatus, "String", false, 80);
		FDCTransmissionHelper.valueFormat("用款部门", FUseDepartment_name, "String", true, 200);
		FDCTransmissionHelper.valueFormat("业务日期", FBizDate, "Date", true, -1);
		FDCTransmissionHelper.valueFormat("收付类别", FFeeType_name, "String", false, 150);
		FDCTransmissionHelper.valueFormat("收款方省", FRecProvince, "String", false, 80);
		FDCTransmissionHelper.valueFormat("收款方市县", FRecCity, "String", false, 150);
		FDCTransmissionHelper.valueFormat("付款帐号", FPayerAccountBank_bankAccountNumber, "String", false, 100);
		FDCTransmissionHelper.valueFormat("收款人类别", FFdcPayeeType, "String", false, 80);
		FDCTransmissionHelper.valueFormat("同城异地", FIsDifferPlace, "String", false, 80);
		FDCTransmissionHelper.valueFormat("付款银行", FPayerBank_name, "String", false, 200);
		FDCTransmissionHelper.valueFormat("收款人名称", FPayeeName, "String", true, 80);
		FDCTransmissionHelper.valueFormat("实际收款单位全称", FActFdcPayeeName_name, "String", false, 200);
		FDCTransmissionHelper.valueFormat("付款科目", FPayerAccount_name, "String", false, 100);
		FDCTransmissionHelper.valueFormat("收款银行", FPayeeBank, "String", false, 90);
		FDCTransmissionHelper.valueFormat("币别编码", FCurrency_number, "String", true, 80);
		FDCTransmissionHelper.valueFormat("业务种类", FBizType_name, "String", false, 80);
		FDCTransmissionHelper.valueFormat("收款账户", FPayeeAccountBank, "String", false, 80);
		FDCTransmissionHelper.bdValueFormat("汇率", FExchangeRate, false, 10,4);
		FDCTransmissionHelper.isBoolean("是否加急", FIsEmergency, false,"是", "否", 80);
		FDCTransmissionHelper.valueFormat("原币金额", FAmount, "Double", false, 80);
		//FDCTransmissionHelper.valueFormat("付款申请单id-----", FFdcPayReqID, "String", false, 80);
		this.ptValueFormat("进度款比例",paymentProportion,false);
		FDCTransmissionHelper.valueFormat("本期完工工程量(金额)", completePrjAmt, "Double", false, 80);
		FDCTransmissionHelper.valueFormat("摘要", FDescription, "String", false, 80);
		FDCTransmissionHelper.valueFormat("用途", FUsage, "String", false, 80);
		FDCTransmissionHelper.valueFormat("计划项目", FFpItem_name, "String", false, 80);
		FDCTransmissionHelper.valueFormat("打回意见", FConceit, "String", false, 80);
		FDCTransmissionHelper.valueFormat("结算方式", FSettlementType_name, "String", false, 80);
		FDCTransmissionHelper.valueFormat("结算号", FSettlementNumber, "String", false, 80);
		FDCTransmissionHelper.valueFormat("保修金金额", grtAmount, "Double", false, -1);
		FDCTransmissionHelper.valueFormat("开票日期", invoiceDate, "Date", false, -1);
		FDCTransmissionHelper.valueFormat("发票号", InvoiceNumber, "String", false, 80);
		FDCTransmissionHelper.valueFormat("发票金额", invoiceAmt, "Double", false, -1);
		FDCTransmissionHelper.valueFormat("备注", FSummary, "String", false, 500);
		FDCTransmissionHelper.valueFormat("项目名称", FProject_name, "String", false, 80);
		FDCTransmissionHelper.valueFormat("合同名称", FContractBase_name, "String", false, 80);
		FDCTransmissionHelper.valueFormat("最新造价", FLatestPrice, "Double", false, 80);
		FDCTransmissionHelper.valueFormat("变更指令单金额", ChangeMoney, "Double", false, 80);
		FDCTransmissionHelper.valueFormat("合同内工程款_本期发生(原币)", FProjectPriceInContract, "Double", true, 80);
		FDCTransmissionHelper.valueFormat("本期计划付款", FCurPlannedPayment, "Double", false, 80);
		FDCTransmissionHelper.valueFormat("本期欠付款", FCurBackPay, "Double", false, 80);
		this.ptValueFormat("本次申请%", FCurReqPercent, false);
		this.ptValueFormat("累计申请%", FAllReqPercent, false);
		this.ptValueFormat("形象进度%", FImageSchedule, false);
		this.ptValueFormat("应付申请%", payreq, false);
		this.ptValueFormat("累计应付申请%", sumpayreq, false);
		FDCTransmissionHelper.valueFormat("制单人编码", FCreator_name_l2, "String", true, 80);
		FDCTransmissionHelper.valueFormat("制单时间", FCreateTime, "Date", true, 80);
		if(FBillStatus.trim().equals("已审批") || FBillStatus.trim().equals("") || FBillStatus==null){//单据状态已经审批和为空  是为必须录项目
			FDCTransmissionHelper.valueFormat("审核人编码", FAuditor_name_l2, "String", true, 80);
			FDCTransmissionHelper.valueFormat("审核日期", FAuditDate, "Date", true, 80);
		}else{
			FDCTransmissionHelper.valueFormat("审核人编码", FAuditor_name_l2, "String", false, 80);
			FDCTransmissionHelper.valueFormat("审核日期", FAuditDate, "Date", false, 80);
			if(!FAuditor_name_l2.equals("")){
//				FDCTransmissionHelper.isThrow("非审批状态的时候，不能填写审核人！");
			}
			if(!FAuditDate.equals("")){
//				FDCTransmissionHelper.isThrow("非审批状态的时候，不能填写审核日期！");
			}
		}
		FDCTransmissionHelper.valueFormat("出纳", FCashier_name, "String", false, 80);
		FDCTransmissionHelper.valueFormat("会计", FAccountant_name, "String", false, 80);
		
		FDCTransmissionHelper.valueFormat("奖励", award, "Double", false, 80);
		FDCTransmissionHelper.valueFormat("违约", breakcontract, "Double", false, 80);
		FDCTransmissionHelper.valueFormat("扣款", deduct, "Double", false, 80);
		FDCTransmissionHelper.valueFormat("甲供材扣款", FPayPartAMatlAmt, "Double", false, 80);
		
	
		//数据库校验

		
		CostCenterOrgUnitInfo ccinfo = null;//成本中心
		CurProjectInfo cpinfo =  null;//工程项目长
		CompanyOrgUnitInfo coinfo =  null;//代理公司
		PaymentTypeInfo ptinfo =  null;//付款类型
		ContractBillInfo cbinfo =  null;//合同
		BillStatusEnum bsenum = null;//单据状态
		PaymentBillInfo pbinfo =  null;//付款单据
		
		AdminOrgUnitInfo aouinfo =  null;//用款部门
		FeeTypeInfo fyinfo =  null;//收付类别
		AccountBankInfo abinfo =  null;//付款帐号
		FdcPayeeTypeEnum fptenum = null;//收款人类别
		DifPlaceEnum dpenum = null;//同城异地
		BankInfo binfo =  null;//银行信息
		SupplierInfo sinfo =  null,/*实际收款单位*/ skrinfo=null;//收款人
		AccountViewInfo avinfo =  null;//付款科目
		CurrencyInfo cinfo =  null;//币别
		SettBizTypeInfo sbtinfo =  null;//业务种类
		ExchangeRateInfo erinfo = null;//汇率
		IsMergencyEnum imenum = null;//是否加急
		FpItemInfo fiinfo =  null;//项目计划
		SettlementTypeInfo syinfo  =  null;//结算方式
		ProjectInfo pinfo =  null;//项目
		ContractBaseDataInfo  cbdinfo =  null;//合同基础资料
		UserInfo uinfo = null, ainfo=null ,cuserinfo=null , accuserinfo=null;//创建人和审核人 出纳 会计
		Timestamp tt = null;//创建时间
		BigDecimal bgm = null;

		  
		try {
			//找出工程项目
			cpinfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(this.getView("longnumber", FCurProject_longNumber.replace('.', '!'))).get(0);
              
			//组织编码
			String ccouid = cpinfo.getCostCenter().getId().toString();//成本中心id
			CostCenterOrgUnitInfo ccouinfo = CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitCollection(this.getView("id", ccouid)).get(0);//成本中心对象
			String longnumber = ccouinfo.getLongNumber();//工程项目对应的成本中心长编码
			if(!FCostCenter_longNumber.trim().equals("") && FCostCenter_longNumber!=null){//填写了组织编码的情况
				if(!FCostCenter_longNumber.trim().replace('.', '!').equals(longnumber)){//填写的组织编码和工程项目对应的成本中心长编码不相等的情况
					//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource
					// (ctx, "costnumberisnull"));// 组织编码在系统中不存在
					FDCTransmissionHelper.isThrow("组织编码在系统中不存在");// 组织编码在系统中不存在
				}else{
					ccinfo = ccouinfo;
				}
			}else{
				ccinfo = ccouinfo;
			}
			
			//判断工程项目长编码是否存在
			if(cpinfo==null){
				FDCTransmissionHelper.isThrow("工程项目在系统中不存在");
				// /FDCTransmissionHelper.isThrow(PaymentBillImport.getResource(
				// ctx, "curprojectisnull"));// 工程项目在系统中不存在
			}
			
			//代理公司
			coinfo = CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitCollection(this.getView("name", FAgentPayCompany_name)).get(0);
			
			//付款类型
			ptinfo = PaymentTypeFactory.getLocalInstance(ctx).getPaymentTypeCollection(this.getView("name", FPayBillType_name)).get(0);
			
			//合同编码
			cbinfo = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(this.getView("number", FContractNo)).get(0);
			if(cbinfo==null){
				//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource(ctx
				// , "contractnumberisnull"));// 合同编码在系统中不存在
				FDCTransmissionHelper.isThrow("合同编码在系统中不存在");
			}
			//查看合同是否已经结算hasSettled
			BigDecimal bdset =  cbinfo.getSettleAmt();//结算价
			if(bdset.compareTo(new BigDecimal(0))==0 && FPayBillType_name.equals("结算款")){//结算价等0  说明没有结算
				FDCTransmissionHelper.isThrow("合同未结算时，不能导入付款类型为“结算款”的付款单!");
			}
			
			//单据状态
			String enums = FBillStatus.trim();
			if (enums.equals("已提交")) {
				enums = "SUBMIT";
			}else if(enums.equals("审批中")){
				enums = "AUDITING";
			}else if(enums.equals("已收款")){
				enums = "RECED";
			}else if(enums.equals("保存")){
				enums = "SAVE";
			}else if(enums.equals("已付款")){
				enums = "PAYED";
			}else if(enums.equals("已审批")){
				enums = "AUDITED";
			}else{
				enums = "AUDITED";
			}
			
			bsenum = BillStatusEnum.getEnum(enums);//单据状态
			if(bsenum==null){
				//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource(ctx
				// , "stateiserror"));// 请按照模板提示填写正确的单据状态
				FDCTransmissionHelper.isThrow("请按照模板提示填写正确的单据状态");// 请按照模板提示填写正确的单据状态
			}
			
			//付款单编码
			view = new EntityViewInfo();
			filter = new FilterInfo();//同一个合同下的  才有付款单编码重复的说法
			filter.getFilterItems().add(new FilterItemInfo("number",FNumber,CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("contractno",cbinfo.getNumber(),CompareType.EQUALS));
			view.setFilter(filter);
			pbinfo = PaymentBillFactory.getLocalInstance(ctx).getPaymentBillCollection(view).get(0);
			if(pbinfo!=null){
				//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource(ctx
				// , "paymentnumberiserror"));// 付款单编码重复
				FDCTransmissionHelper.isThrow("付款单编码重复");// 付款单编码重复
			}
			
			
			//用款部门
			aouinfo = AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitCollection(this.getView("name", FUseDepartment_name)).get(0);
			if(aouinfo==null){
				//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource(ctx
				// , "aorisnull"));// 用款部门在系统中不存在
				FDCTransmissionHelper.isThrow("用款部门在系统中不存在");
			}

			//收付类别
			fyinfo = FeeTypeFactory.getLocalInstance(ctx).getFeeTypeCollection(this.getView("name", FFeeType_name)).get(0);
			
			//付款帐号
			abinfo = AccountBankFactory.getLocalInstance(ctx).getAccountBankCollection(this.getView("number", FPayerAccountBank_bankAccountNumber)).get(0);
			
			//收款人类别
			fptenum = FdcPayeeTypeEnum.getEnum("1OTHER");//FFdcPayeeType
			
			//同城异地
			enums = FIsDifferPlace.trim();
			
			if (enums.equals("异地")) {
				enums = "difPlace";
			}else if(enums.equals("同城")){
				enums = "samePlace";
			}else if(enums.equals("")){
				enums = "samePlace";
			}
			
			dpenum = DifPlaceEnum.getEnum(enums);//同城异地
			if(dpenum==null){
				//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource(ctx
				// , "diffplaceiserror"));// 请按照模板提示填写正确的同城异地
				FDCTransmissionHelper.isThrow("请按照模板提示填写正确的同城异地");
			}
	
			//银行信息
			if(abinfo!=null){
				binfo = abinfo.getBank();//根据选择的付款帐号，自动带出
			}
		    
		    //收款人名称
			skrinfo = SupplierFactory.getLocalInstance(ctx).getSupplierCollection(this.getView("name", FPayeeName)).get(0);
			if(skrinfo==null){
				//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource(ctx
				// , "skrisnull"));// 收款人在系统中不存在
				FDCTransmissionHelper.isThrow("收款人在系统中不存在");
			}
			
		    //实际收款单位全称
		    sinfo = SupplierFactory.getLocalInstance(ctx).getSupplierCollection(this.getView("name", FActFdcPayeeName_name)).get(0);
		    
		    //付款科目
		    avinfo = AccountViewFactory.getLocalInstance(ctx).getAccountViewCollection(this.getView("name", FPayerAccount_name)).get(0);

			//币别
			cinfo = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection(this.getView("number", FCurrency_number)).get(0);
			//币种为空的时候去本位币 
			if(cinfo==null){
				cinfo = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection("where name='人民币'").get(0);
				if (cinfo == null) {// 币别编码在系统中不存在或找不到默认币别，请联系系统管理员!
					//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource
					// (ctx, "bbisnull"));
					FDCTransmissionHelper.isThrow("币别编码在系统中不存在或找不到默认币别，请联系系统管理员!");
				}	
			}
			
			//业务种类
			sbtinfo = SettBizTypeFactory.getLocalInstance(ctx).getSettBizTypeCollection(this.getView("name", FBizType_name)).get(0);

			//汇率
			if(FExchangeRate==null){//汇率为空 默认  币别的汇率
				erinfo = ExchangeRateFactory.getLocalInstance(ctx).getExchangeRateInfo(new ObjectUuidPK(cinfo.getId()));
			}
			CurrencyInfo ci = null;//用于找本位币
			view = new EntityViewInfo();
			filter = new FilterInfo();
			//汇率  如果汇率为空   取当前汇率
			if(FExchangeRate.trim().equals("") || FExchangeRate==null){
				//本位币对象ci        //用户填写的币种-原币对象 cinfo
				ci = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection("where name='人民币'").get(0);
				if(ci==null){//ci是本位币哈   找不到本位币
					//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource
					// (ctx, "bbisnull"));//币别编码在系统中不存在或找不到默认币别，请联系系统管理员!
					// 币别编码在系统中不存在或找不到默认币别，请联系系统管理员!
					FDCTransmissionHelper.isThrow("币别编码在系统中不存在或找不到默认币别，请联系系统管理员!");
				
				}else{//找到本位币的情况
					if(ci.getName().trim().equals(cinfo.getName().trim())){//都是人民币的情况
						bgm = new BigDecimal(1.0000);
					}else{
						filter.getFilterItems().add(new FilterItemInfo("targetCurrency.id",ci.getId().toString(),CompareType.EQUALS));//目标币本位币
						filter.getFilterItems().add(new FilterItemInfo("SourceCurrency.id", cinfo.getId().toString(), CompareType.EQUALS));//用户填写的原币
						filter.setMaskString("#0 and #1");
						view.setFilter(filter);
						ExchangeAuxInfo xinfo = ExchangeAuxFactory.getLocalInstance(ctx).getExchangeAuxCollection(view).get(0);
						if(xinfo==null){
							// FDCTransmissionHelper.isThrow(PaymentBillImport.
							// getResource(ctx, "hlisnull"));//
							// 汇率表中找不到原币和本位币的兑换汇率
							FDCTransmissionHelper.isThrow("汇率表中找不到原币和本位币的兑换汇率");// 汇率表中找不到原币和本位币的兑换汇率
						}else{
							erinfo = ExchangeRateFactory.getLocalInstance(ctx).
							getExchangeRate(new ObjectUuidPK(xinfo.getExchangeTable().getId()),
									new ObjectUuidPK(xinfo.getSourceCurrency().getId()), 
									new ObjectUuidPK(xinfo.getTargetCurrency().getId()), 
									Calendar.getInstance().getTime());
							bgm = erinfo.getConvertRate();
							if(erinfo==null){
								//FDCTransmissionHelper.isThrow(PaymentBillImport
								// .getResource(ctx, "hlisnull"));//
								// 汇率表中找不到原币和本位币的兑换汇率
								FDCTransmissionHelper.isThrow("汇率表中找不到原币和本位币的兑换汇率");// 汇率表中找不到原币和本位币的兑换汇率
							}
						}
					}
				}	
			}else{
				bgm = FDCTransmissionHelper.strToBigDecimal(FExchangeRate);
			}
		
			//是否加急 
			enums = FIsEmergency.trim();
			if(enums.equals("加急")){
				enums = "mergercy";
			} else {
				enums = "normal";
			}
			
			imenum = IsMergencyEnum.getEnum(enums);//是否加急
			if(imenum==null){
				//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource(ctx
				// , "sfjjiserror"));// 请按照模板提示填写正确的是否加急
				FDCTransmissionHelper.isThrow("请按照模板提示填写正确的是否加急");// 请按照模板提示填写正确的是否加急
			}
			
			//项目计划
			fiinfo = FpItemFactory.getLocalInstance(ctx).getFpItemCollection(this.getView("name", FFpItem_name)).get(0);
			
			//结算方式 
			syinfo = SettlementTypeFactory.getLocalInstance(ctx).getSettlementTypeCollection(this.getView("name", FFpItem_name)).get(0);
	
			//项目名称   合同名称       最新造价        变更指令单      金额合同内工程款_本期发生（原币）都由合同编号带出    取得合同信息
			//conbinfo = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection("where number='"+FContractNo+"'").get(0);
			//项目名称  FProject_name
			//pinfo = cbinfo.getCurProject();
			//合同名称
			cbdinfo = cbinfo.getContractBaseData();
			
			//最新造价
			
			//创建人
			uinfo = UserFactory.getLocalInstance(ctx).getUserCollection(this.getView("number", FCreator_name_l2)).get(0);
			if(uinfo==null){
				//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource(ctx
				// , "zdrisnull"));// 制单人编码在系统中不存在
				FDCTransmissionHelper.isThrow("制单人编码在系统中不存在");// 制单人编码在系统中不存在
			}
			
			//创建时间
			tt = Timestamp.valueOf(FCreateTime+" 00:00:00.0");
			
			//审核人编码
			ainfo = UserFactory.getLocalInstance(ctx).getUserCollection(this.getView("number", FAuditor_name_l2)).get(0);
			if(!FAuditor_name_l2.trim().equals("")){//填写了  审批人编码
				if(ainfo==null){
					//FDCTransmissionHelper.isThrow(PaymentBillImport.getResource
					// (ctx, "shrisnull"));// 审核人编码在系统中不存在
					FDCTransmissionHelper.isThrow("审核人编码在系统中不存在");// 审核人编码在系统中不存在
				}
			}
			
			// 出纳
			cuserinfo=UserFactory.getLocalInstance(ctx).getUserCollection(this.getView("name", FCashier_name)).get(0);
			
			// 会计
			accuserinfo=UserFactory.getLocalInstance(ctx).getUserCollection(this.getView("name", FAccountant_name)).get(0);

			BigDecimal payReqMoney = prbinfo.getOriginalAmount();//付款申请单原币金额
			BigDecimal conProMoney = prbinfo.getProjectPriceInContract();//付款申请单的合同内工程款
			BigDecimal proPriceInContract = FDCTransmissionHelper.strToBigDecimal(FProjectPriceInContract);//付款单的合同内工程款
			BigDecimal PayMentMoney = FDCHelper.ZERO;//初始化付款单原币金额为0
			
			//导入的付款单汇总金额  必须要小于或者等于  付款申请单金额
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("FdcPayReqNumber",payReqNumber,CompareType.EQUALS));
			filter.getFilterItems().add(new FilterItemInfo("ContractNo",FContractNo,CompareType.EQUALS));
	        view.setFilter(filter);//付款单集合
	        PaymentBillCollection conn  = PaymentBillFactory.getLocalInstance(ctx).getPaymentBillCollection(view);
	        //判断付款单  在数据库中是否已经存在   如果是第一次导入付款单，那么就要将扣款奖励之类的结算掉，但是如果是第2次或者第2次以上的导入，就只需要结算工程款了
	        int ikm = conn.size();
			
			if(ikm==0){//第一次导入付款申请单对应的   付款单
				//比较付款申请单和付款单的合同内工程款
				if(proPriceInContract.compareTo(conProMoney)>0){///付款单大于付款申请单金额  不能导入
					FDCTransmissionHelper.isThrow("此次导入以后，累计的付款单金额将会大于付款申请单金额！所以此次导入不被允许！");
				}else{
					PayMentMoney = FDCHelper.add(FDCHelper.subtract(payReqMoney, conProMoney), proPriceInContract);
				}
			}else{//第2次  或者第2次以上的导入
				Iterator it = conn.iterator();
				while (it.hasNext()){
					PaymentBillInfo item = (PaymentBillInfo)it.next();
					PayMentMoney = PayMentMoney.add(item.getAmount());//原币金额
				}
				PayMentMoney = proPriceInContract;//2次或者2次以上的导入，合同内工程款就=原币金额
				if(PayMentMoney.add(proPriceInContract).compareTo(payReqMoney)>0){//付款单大于付款申请单金额  不能导入
					FDCTransmissionHelper.isThrow("此次导入以后，累计的付款单金额将会大于付款申请单金额！所以此次导入不被允许！");
				}
			}
			
			
			/**
			 * 	数据校验完成后所需要做的操作如下： 
			 *  设置将该付款申请单未审批修改为审批状态
			 *  完成审批付款申请单之后获取该付款申请单对应的状态为保存的付款单对象
			 */
			info = this.auditPayRequestBill(ctx, prbinfo);
			
			//设置值
//			info.setCostCenter(ccinfo);//组织编码
//			info.setSourceType(SourceTypeEnum.FDC);
//			
//			info.setCurProject(cpinfo);//工程项目长编码
			info.setAgentPayCompany(coinfo);//代理付款公司
			info.setFdcPayType(ptinfo);//付款类型
			info.setBillStatus(bsenum);//单据状态
			
			info.setUseDepartment(aouinfo);//用款部门
			info.setBizDate(FDCTransmissionHelper.strToDate(FBizDate));//业务日期
			info.setFeeType(fyinfo);//收付类别
			info.setRecProvince(FRecProvince);//收款方省
			info.setRecCity(FRecCity);//收款方市县
			info.setPayerAccountBank(abinfo);//付款帐号
			info.setFdcPayeeType(fptenum);//收款人类别
			info.setIsDifferPlace(dpenum);//同城异地
			info.setPayerBank(binfo);//银行信息]
			
			info.setPayeeName(FPayeeName);//收款人名称
			info.setPayeeID(skrinfo.getId().toString());//收款人id
			info.setPayeeNumber(skrinfo.getNumber());//收款人编号
//			
			info.setActFdcPayeeName(sinfo);//实际收款单位全称
			info.setPayerAccount(avinfo);//付款科目
			info.setPayeeBank(FPayeeBank);//收款银行
			info.setCurrency(cinfo);//币别编码
			info.setBizType(sbtinfo);//业务种类
			info.setPayeeAccountBank(FPayeeAccountBank);//收款账户
			info.setExchangeRate(bgm);//汇率
			info.setIsEmergency(imenum);//是否加急

			info.setAmount(PayMentMoney);//原币金额--特殊处理
			info.setLocalAmt(FDCHelper.multiply(PayMentMoney, bgm));//本位币金额--特殊处理
//			info.setCapitalAmount(FDCHelper.transCap(cinfo, PayMentMoney));//大写金额
//			
			info.setDescription(FDescription);//摘要
			info.setUsage(FUsage);//用途
			info.setFpItem(fiinfo);//项目计划
			info.setConceit(FConceit);//打回意见
			info.setSettlementType(syinfo);//结算方式
			info.setSettlementNumber(FSettlementNumber);//结算号

			info.setCreator(uinfo);// 制单人编码
			info.setCreateTime(tt);// 制单时间
			info.setAuditor(ainfo);// 审核人编码
			info.setAuditDate(FDCTransmissionHelper.strToDate(FAuditDate));// 审核日期
			info.setCashier(cuserinfo);// 出纳
			info.setAccountant(accuserinfo);// 会计

			info.setSummary(FSummary);// 备注
//			info.setLatestPrice(FDCTransmissionHelper.strToBigDecimal(FLatestPrice));// 最新造价
//			// 合同内工程款_本期发生(原币 )
//			info.setProjectPriceInContract(FDCTransmissionHelper.strToBigDecimal(FProjectPriceInContract));
//			info.setCurPlannedPayment(FDCTransmissionHelper.strToBigDecimal(FCurPlannedPayment));// 本期计划付款
//			info.setCurBackPay(FDCTransmissionHelper.strToBigDecimal(FCurBackPay));// 本期欠付款
//			info.setCurReqPercent(FDCTransmissionHelper.strToBigDecimal(FCurReqPercent));// 本次申请
//																							// %
//			info.setAllReqPercent(FDCTransmissionHelper.strToBigDecimal(FAllReqPercent));// 累计申请
//																							// %
//			info.setImageSchedule(FDCTransmissionHelper.strToBigDecimal(FImageSchedule));// 形象进度
//
//			info.setAddProjectAmt(FDCTransmissionHelper.strToBigDecimal(FProjectPriceInContract));//工程付款情况表本期发生原币合同内工程款
			
			
			
			// -------------------------------------用户填写以下类容
			// 我们需要到数据库中去覆盖--------------------------------
			// // 进度款比例 不为空 ，代表覆盖数据//
			// if (!StringUtils.isEmpty(paymentProportion)) {
			// prbinfo.setGrtAmount(FDCTransmissionHelper.strToBigDecimal(
			// paymentProportion));
			// }
			//
			// // 本期完工工程量 false 不为空// 代表覆盖数据
			// if (!StringUtils.isEmpty(completePrjAmt)) {
			// prbinfo.setCompletePrjAmt(FDCTransmissionHelper.strToBigDecimal(
			// completePrjAmt));
			// }
			//
			// // 保修金金额 false // 不为空 代表覆盖数据
			// if (!StringUtils.isEmpty(grtAmount)) {
			// prbinfo.setGrtAmount(FDCTransmissionHelper.strToBigDecimal(
			// grtAmount));
			// }
			//
			// // 开票日期 // 不为空 代表覆盖数据
			// if (!StringUtils.isEmpty(invoiceDate)) {
			//prbinfo.setInvoiceDate(FDCTransmissionHelper.strToDate(invoiceDate
			// ));
			// }
			//
			// // 发票号 // 不为空 代表覆盖数据
			// if (!StringUtils.isEmpty(InvoiceNumber)) {
			// prbinfo.setInvoiceNumber(InvoiceNumber);
			// }
			//
			// // 发票金额 // 不为空 代表覆盖数据
			// if (!StringUtils.isEmpty(invoiceAmt)) {
			// prbinfo.setInvoiceAmt(FDCTransmissionHelper.strToBigDecimal(
			// invoiceAmt));
			// }
			//			
			// // "变更指令单金额", ChangeMoney 不为空 代表覆盖数据
			// if (!StringUtils.isEmpty(ChangeMoney)) {
			// prbinfo.setChangeAmt(FDCTransmissionHelper.strToBigDecimal(
			// ChangeMoney));
			// }
			
			// "应付申请", payreq 不为空 代表覆盖数据
			
			// "累计应付申请", sumpayreq 不为空 代表覆盖数据
			
			
	        // "奖励", award 不为空 代表覆盖数据
			// "违约", breakcontract 不为空 代表覆盖数据
			// "扣款", deduct 不为空 代表覆盖数据
			// 甲供材扣款 不为空 代表覆盖数据
			info.setPayPartAMatlAmt(FDCTransmissionHelper.strToBigDecimal(FPayPartAMatlAmt));
			
//			info.setContractNo(FContractNo);//合同编号
//			info.setProject(pinfo);//项目名称
//			info.setContractBase(cbdinfo);//合同名称
//			info.setContractBillId(cbinfo.getId().toString());//合同id
			
			info.setNumber(FNumber);//付款单编码
//			info.setFdcPayReqNumber(prbinfo.getNumber());// 付款申请单编码
//			info.setFdcPayReqID(prbinfo.getId().toString());// 付款申请单
		    
	  		// 奖励        (中间表<T_CON_GuerdonOfPayReqBill>)
			// 违约        (中间表<T_CON_CompensationOfPayReqBill>)
			// 扣款        (中间表<T_CON_DeductOfPayReqBill>)
			// 甲供材扣款  (中间表<T_CON_PartAOfPayReqBill>)
			
			// 将新的付款单对象接收到之后直接进行Update修改
			PaymentBillFactory.getLocalInstance(ctx).update(new ObjectUuidPK(String.valueOf(info.getId())), info);
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		} 
		
		return null;// 因为付款申请单在审批之后付款单对象已经存在所以选择返回null
	}
	
//	/**
//	 * 备注： 此方法已经过时，所以被注释了！
//	 * @description		在保存付款单之后，按条件修改付款申请单的状态为已审批！		
//	 * @author				雍定文		
//	 * @createDate			2011-7-21
//	 * @param				
//	 * @return							
//	 * @version			EAS1.0
//	 * @see					
//	 * (non-Javadoc)
//	 * @see com.kingdee.eas.tools.datatask.AbstractDataTransmission#submit(com.kingdee.eas.framework.CoreBaseInfo, com.kingdee.bos.Context)					
//	 */
//	public void submit(CoreBaseInfo coreBaseInfo, Context context) throws TaskExternalException {
//		
//		super.submit(coreBaseInfo, context);
//		
//		/* 如果付款申请单状态不是已审批时保存一条付款单，则需要在付款单保存之后进行修改付款申请单的状态 */
//		if (coreBaseInfo instanceof PaymentBillInfo) {
//			PaymentBillInfo paymentBillInfo = (PaymentBillInfo) coreBaseInfo;
//			// 得到付款申请单编号！
//			String pbiNumber = paymentBillInfo.getFdcPayReqNumber();
//			// 得到付款申请单ID！
//			String pbiID = String.valueOf(paymentBillInfo.getFdcPayReqID());
//			// 得到合同编码
//			String cbID = String.valueOf(paymentBillInfo.getContractNo());
//			try {
//				/** 查询付款申请单 */
//				FilterInfo filter = new FilterInfo();
//				filter.getFilterItems().add(new FilterItemInfo("number", pbiNumber));
//				filter.getFilterItems().add(new FilterItemInfo("id", pbiID));
//				filter.getFilterItems().add(new FilterItemInfo("contractNo", cbID));
//				EntityViewInfo view = new EntityViewInfo();
//				view.setFilter(filter);
//				PayRequestBillCollection payRequestBillColl = PayRequestBillFactory.getLocalInstance(context).getPayRequestBillCollection(view);
//				if (payRequestBillColl.size() > 0) {
//					PayRequestBillInfo payRequestBillInfo = payRequestBillColl.get(0);
//					payRequestBillInfo.setState(FDCBillStateEnum.AUDITTED); // 设置付款申请单状态为已审批
//					payRequestBillInfo.setAuditor(paymentBillInfo.getCreator()); // 设置审批人
//					payRequestBillInfo.setAuditTime(paymentBillInfo.getCreateTime()); // 设置审批时间
//					/** 执行更改付款申请单的状态为已审批 */
////					PayRequestBillFactory.getLocalInstance(context).update(new ObjectUuidPK(String.valueOf(payRequestBillInfo.getId())), payRequestBillInfo);
//					PayRequestBillFactory.getLocalInstance(context).audit(payRequestBillInfo.getId());
//					payRequestBillInfo.setNumber("");
//				}
//			} catch (BOSException e) {
//				e.printStackTrace();
//			} catch (EASBizException e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
	/**
	 * @description		如果付款申请单未审批，则审批该付款申请单！	
	 * @author				雍定文	
	 * @param				context
	 * @param				payRequestBillInfo
	 * @createDate			2011-7-22
	 * @throws 			TaskExternalException
	 * @void 				PaymentBillInfo
	 * @version			EAS1.0
	 * @see
	 */
	private PaymentBillInfo auditPayRequestBill(Context context, PayRequestBillInfo payRequestBillInfo) throws TaskExternalException {
		
		try {
			/** 执行更改付款申请单的状态为已审批 */
			PayRequestBillFactory.getLocalInstance(context).audit(payRequestBillInfo.getId());
			
			PaymentBillInfo paymentBillInfo = null;
			
			// 查询该付款申请单审批后对应的付款单进行修改设值<理由一个付款申请单只对应一个付款单>
			// 合同编号！
			String contractNo = String.valueOf(payRequestBillInfo.getContractNo());
			// 付款申请单编码！
			String payRequestBillInfo_Number = payRequestBillInfo.getNumber();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractNo", contractNo));
			filter.getFilterItems().add(new FilterItemInfo("FdcPayReqNumber", payRequestBillInfo_Number));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			PaymentBillCollection paymentBillColl = PaymentBillFactory.getLocalInstance(context).getPaymentBillCollection(view);
			if (!(paymentBillColl.size() > 0)) {
				// 貌似永远都执行不到。
				FDCTransmissionHelper.isThrow("付款单数据出现不可估算的错误！请与开发人员联系！");	
			}
			paymentBillInfo = paymentBillColl.get(0);
			
			return paymentBillInfo;
			
		} catch (EASBizException e) {
			FDCTransmissionHelper.isThrow(e.getMessage() + "\n所选择的付款申请单审批失败！不能导入付款单！");	
			// 如果抛出异常则说明审批失败
//			e.printStackTrace();
		} catch (BOSException e) {
			FDCTransmissionHelper.isThrow("所选择的付款申请单审批失败！不能导入付款单！");	
			// 如果抛出异常则说明审批失败
//			e.printStackTrace();
		}
		
		return null;
		
	}
	
	//返回视图
	private EntityViewInfo getView(String sqlcolnum,Object item){
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(sqlcolnum,item,CompareType.EQUALS));
        view.setFilter(filter);
		return view;	
	}
	
	//判断百分数
	private void ptValueFormat(String cName,String value,boolean b) throws TaskExternalException{
		if(null != value && !"".equals(value.trim()) ){
			if(!value.matches("^([1-9]\\d{0,2}\\.\\d{0,2})|(0\\.\\d{0,2})||([1-9]\\d{0,2})||0$")){
				FDCTransmissionHelper.isThrow(cName,"必须是0-100的数字,可以有2位小数" );
    		}
		}else{
			if(b){//为空的情况  但是是必填的字段
				FDCTransmissionHelper.isThrow(cName,"不能为空！");	
			}
		}
	}
	
	private String forMatDate(String str ){
		String strDate = str;
		String[] strlen = str.split("-");
		if(strlen.length==3){
			if(strlen[1].length()==1 && strlen[1].matches("[1-9]")){
				strlen[1]="0"+strlen[1];
			}
			if(strlen[2].length()==1 && strlen[2].matches("[1-9]")){
				strlen[2]="0"+strlen[1];
			}
			strDate = strlen[0]+"-"+strlen[1]+"-"+strlen[2];
		}
		return strDate;
	}
	
	
	/**
	 * 得到资源文件
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
}
