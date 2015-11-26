/*
 * @(#)FDCContants.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.basedata;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.kingdee.eas.fi.gl.GlUtils;

/**
 * 
 * 描述:房地产常量类
 * 
 * @author liupd date:2006-10-10
 *         <p>
 * @version EAS5.1.3
 */
public class FDCConstants {
	


	/** 供应商 F7 Query */
	public static final String SUPPLIER_F7_QUERY = "com.kingdee.eas.basedata.master.cssp.app.F7SupplierQueryWithDefaultStandard";

	/** 目标成本比指标ID */
	public static final String AIM_COST_PERCENT_ID = "qHQt0wEMEADgAAaOoKgTuzW0boA=";

	/** 建筑面积指标id */
	public static final String BUILD_AREA_ID = "qHQt0wEMEADgAAaBwKgTuzW0boA=";

	/** 可售面积 指标ID */
	public static final String SALE_AREA_ID = "qHQt0wEMEADgAAaHwKgTuzW0boA=";
	
	/** 设计变更 */
	public static final String CHANGE_TYPE_DESIGN = "FQsluAENEADgAAr2wKgTu6wXT3w=";
	
	/** 现场签证 */
	public static final String CHANGE_TYPE_SIGN = "FQsluAENEADgAAr5wKgTu6wXT3w=";
	
	/** 其他调整 */
	public static final String CHANGE_TYPE_OTHER = "FQsluAENEADgAArwwKgTu6wXT3w=";
	
	//语言常量
	/** Locale - L1 */
	public static final Locale L1 = new Locale("L1");
	/** Locale - L2 */
	public static final Locale L2 = new Locale("L2");
	/** Locale - L3 */
	public static final Locale L3 = new Locale("L3");
	
	//int 常量
	//默认币别精度
	public static final int DEFAULT_CURRENCY_PREC = 2;
	
	public  static final int  DEFAULT_RATE_PRECISION = 5;
	
    public  static final int  ONE_SECOND = 1000;
    
    public static final int  ONE_MINUTE = 60*ONE_SECOND;
    
    public static final int  ONE_HOUR   = 60*ONE_MINUTE;
    
    public static final long ONE_DAY    = 24*ONE_HOUR;
    
    public static final long ONE_WEEK   = 7*ONE_DAY;
    
    final static public int MAX_YEAR = 2999;

	final public static Integer TRUE = new Integer(1);
	
	final public static Integer FALSE = new Integer(0);
	
	public static final int  MsgMaxLength=2000;
	
	/** 综合本位币ID */
	public static final String GENERAL_LOCAL_CURRENCY  = "11111111-1111-1111-1111-111111111111DEB58FDC";
	/** 综合报告ID */
	public static final String GENERAL_REPORT_CURRENCY = "22222222-2222-2222-2222-222222222222DEB58FDC";

	public static final String datafilter="datafilter";    

	//BigDecimal 常量
    final static public BigDecimal FIRST_VERSION = new BigDecimal("0.9");
    
    final static public BigDecimal CURR_VERSION = new BigDecimal("0");
    
	public static final BigDecimal B0 = new BigDecimal("0");

	public static final BigDecimal B100 = new BigDecimal("100");
	
	public static final BigDecimal ZERO = new BigDecimal("0");
	
    public  static final Date MAX_DATE = new Date(1099,11,30);

    /** BigDecimal Value : 1 */
	public static final BigDecimal ONE = new BigDecimal("1");
	
	/** BigDecimal Value : -1 */
	public static final BigDecimal _ONE = new BigDecimal("-1");
	
	/** BigDecimal Value : 10 */
	public static final BigDecimal 	TEN = new BigDecimal("10");
	
	/** BigDecimal Value : -10 */
	public static final BigDecimal 	_TEN = new BigDecimal("-10");
	
	/** BigDecimal Value : 100 百 */
	public static final BigDecimal 	ONE_HUNDRED  = new BigDecimal("100");
	
	/** BigDecimal Value : -100 百 */
	public static final BigDecimal 	_ONE_HUNDRED  = new BigDecimal("-100");
	
	/** BigDecimal Value : 1000 千 */
	public static final BigDecimal 	 ONE_THOUSAND = new BigDecimal("1000");
	
	/** BigDecimal Value : -1000 千 */
	public static final BigDecimal 	 _ONE_THOUSAND = new BigDecimal("-1000");
	
	/** BigDecimal Value : 10000 万 */
	public static final BigDecimal 	TEN_THOUSAND = new BigDecimal("10000");
	
	/** BigDecimal Value : -10000 万 */
	public static final BigDecimal 	_TEN_THOUSAND = new BigDecimal("-10000");
	
	/** BigDecimal Value : 100000000 亿 */
	public static final BigDecimal 	ONE_HUNDRED_MILLION  = new BigDecimal("100000000");
	
	/** BigDecimal Value : -100000000 亿 */
	public static final BigDecimal 	_ONE_HUNDRED_MILLION  = new BigDecimal("-100000000");
	
	
	//最大明细数（1兆），最小明细数（负1兆），和总帐一致，所以要生成凭证的字段必须不能大于此数，（13位）
	public static final BigDecimal MAX_VALUE = GlUtils.maxBigDecimal;	//1000000000000
	
	public static final BigDecimal MIN_VALUE = GlUtils.minBigDecimal; //-1000000000000
	
	//最大合计数（100兆），最小合计数（负100兆）MAX_VALUE/MIN_VALUE * 100 （15位）
	public final static BigDecimal MAX_TOTAL_VALUE = MAX_VALUE.multiply(ONE_HUNDRED);
	
	public final static BigDecimal MIN_TOTAL_VALUE  = MIN_VALUE.multiply(ONE_HUNDRED);

	public static final String TIME_FORMAT = "yyyy-mm-dd hh:mm:ss";
	
    public static final DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
    
    public static final DateFormat FORMAT_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final DateFormat FORMAT_MONTH = new SimpleDateFormat("yyyy-MM");


    public static final DateFormat FORMAT_QUARTER = new DateFormat(){
        public  StringBuffer format(Date date, StringBuffer toAppendTo,
            FieldPosition fieldPosition)
	    {
	        Calendar cal = new GregorianCalendar();
	        cal.setTime(date);
	        int year;
	        int quarter;
	        StringBuffer sb = new StringBuffer(8);
	        year=cal.get(Calendar.YEAR);
	        quarter=cal.get(Calendar.MONTH)/3+1;
	        
	        return  sb.append(year).append(" - ").append(quarter);
	    }
	
	    public Date parse(String source, ParsePosition pos) {
	        // TODO 自动生成方法存根
	        return null;
	    }
    };
    public static final DateFormat FORMAT_YEAR = new SimpleDateFormat("yyyy");
	public  static final String UI_ISCOPY = "isCopy";
	public static final String EXT_ISDATATYPE = "isDataType";
	public static final String EXT_ISACTIONDATE = "isActionDate";
	public static final String EXT_ISFILTER = "isCommonQueryFilter";
	public  static final int INT_ZERO = 0;
	
	
	public static final int DEFAULT_ROUND = BigDecimal.ROUND_HALF_UP;
	public static final String FM_CUSTOMER_KEY = "fmcustomer";
	public static final int PRE_2 = 2;
	 
	//参数常量 Param Constant  命名规则参考FDC001_STARTMG		
	/*******1 基础资料下的参数****FDC101**/	
	//指标管理是否显示面积指标　by sxhong 2008-02-58 16:26:41
	public static final String FDC_PARAM_PROJECTINDEX = "FDC006_PROJECTINDEX";
	//报表是否显示成本合计行
	public static final String FDC_PARAM_TOTALCOST = "FDC007_TOTALCOST";	
	//成本报表统计包括非成本类科目　by yx 2008-03-24
	public static final String FDC_PARAM_INCLUDENOCOSTACCOUNT = "FDC008_INCLUDENOCOSTACCOUNT";
	
	//全项目动态成本表中的“已实现成本”字段隐藏　by yx 2008-11-03
	public static final String FDC_PARAM_HIDEREALIZEDCOST = "FDC009_HIDEREALIZEDCOST";
	//责任成本
	public static final String FDC_PARAM_COSTACCOUNTSMANDATE = "FDC010_CostAccountsMandate";
	
	//工作流审批信息打印时，碰到不同意，不同意之前的数据都不打印
	public static final String FDC_PARAM_WFISPASSISFALSENOTPRINT = "FDC011_WFisPassIsFalseNotPrint";
	
	/**
	 * 工作流审批信息打印时，碰到重复的审批人，只打印最后一次审批 
	 */
	public static final String FDC_PARAM_WFIsDuplicateNotPrint = "FDC012_WFIsDuplicateNotPrint"; //added by owen_wen 2010-10-12
	
	/**
	 * 工程项目树及序时簿列表是否按照开发顺序排序
	 */
	public static final String FDC_PARAM_PROJECTTREESORTBYSORTNO = "FDC013_PROJECTTREESORTBYSORTNO";
	
	
	/*******2 合同管理下的参数***FDC201***/
	//是否启用预算
	public static final String FDC_PARAM_STARTMG = "FDC001_STARTMG";
	//责任人可以选择全集团人员
	public static final String FDC_PARAM_SELECTPERSON = "FDC002_SELECTPERSON";
	//合同可进行多次结算
	public static final String FDC_PARAM_MORESETTER = "FDC004_MORESETTER";
	/**
	 * 合同可进行多次结算下，合同执行情况表中完工未付款是否按“累计完工工程量-已付款”公式计算
	 */
	public static final String FDC_PARAM_MORESETTER_ALLNOTPAID = "FDC005_MORESETTER_ALLNOTPAID";
	//集团参数：再次归档时更新合同编号
	public static final String FDC_PARAM_UPDATECONTRACTNO = "FDC007_UPDATECONTRACTNO";
	//合同审批前进行拆分
	public static final String FDC_PARAM_SPLITBFAUDIT = "FDC015_SPLITBFAUDIT";
	//付款单提交时检查付款单已经拆分完成
	public static final String FDC_PARAM_CHECKPAYMENTALLSPLIT = "FDC016_CHECKPAYMENTALLSPLIT";
	//付款拆分科目的金额受对应科目已拆分成本金额的限制
	public static final String FDC_PARAM_LIMITCOST = "FDC017_LIMITCOST";
	//合同变更、合同结算的拆分是否自动引用合同拆分的成本科目、拆分比例
	public static final String FDC_PARAM_IMPORTCONSPLIT = "FDC018_IMPORTCONSPLIT";
	//工程量，付款拆分是否允许使用按比例拆分
	public static final String FDC_PARAM_SPLITBASEONPROP ="FDC019_SPLITBASEONPROP";
	//补充合同可以选择提交状态的主合同
	public static final String FDC_PARAM_SELECTSUPPLY = "FDC080_SELECTSUPPLY";
	
	
	//付款申请单付款金额不允许超过可付款额度
	public static final String FDC_PARAM_OUTPAYAMOUNT = "FDC071_OUTPAYAMOUNT";
	//申请单进度款付款比例自动为100%
	public static final String FDC_PARAM_PAYPROGRESS = "FDC072_PAYPROGRESS";
	//付款申请单审批前必须录入合同付款计划
	public static final String FDC_PARAM_CONPAYPLAN = "FDC073_CONPAYPLAN";
	//经办人维护合同
	public static final String FDC_PARAM_CREATORATTACHMENT = "FDC211_CREATORATTACHMENT";
	//审批后提示进行拆分
	public static final String FDC_PARAM_SPLITAFTERAUDIT = "FDC212_SPLITAFTERAUDIT";
	//跨项目拆分的工程项目也可进行合同结算及请款
	public static final String FDC_PARAM_CROSSPROJECTSPLIT = "FDC215_CROSSPROJECTSPLIT";
	//未结算合同的实付款大于已实现产值时是否严格控制  by jian_wen 2009.12.15
	public static final String FDC_PARAM_ISCONTROLPAYMENT="FDC225_ISCONTROLPAYMENT"; 
	
	// 工程项目同步到基础资料中的项目时是否显示组织编码
	public static final String FDC_PARAM_ISSHOWORGTOPROJECTNUM = "FDC1001_ISShowOrgToProjectNum";
	
	/**
	 * 合同结算时是否要求变更必须结算完毕
	 */
	public static final String FDC_PARAM_CONTRACTCHANGESETTLEMENTMUSTCOMPLETE = "FDC226_CONTRACTCHANGESETTLEMENTMUSTCOMPLETE";
	
	/**
	 * 显示合同执行情况表的计划列
	 */
	public static final String FDC_PARAM_CONTRACTEXEC = "FDC074_CONTRACTEXEC";
	/**
	 * 结算拆分生成凭证的参数
	 */
	public static final String FDC_PARAM_SETTLEMENTCOSTSPLIT = "FDC075_SETTLEMENTCOSTSPLIT";
	//合同费用项目
	public static final String FDC_PARAM_CHARGETYPE = "FDC075_CHARGETYPE";
	//付款单提交时，是否检查合同未完全拆分
	public static final String FDC_PARAM_CHECKALLSPLIT = "FDC078_CHECKALLSPLIT";
	//付款单生成凭证时检查付款单是否已拆分
	public static final String FDC_PARAM_CHECKPAYMENTSPLIT = "FDC079_CHECKPAYMENTSPLIT";
	//审批和反审批必须同一个用户
	public static final String FDC_PARAM_AUDITORMUSTBETHESAMEUSER = "FDC011_AUDITORMUSTBETHESAMEUSER";
	//设计变更单“提出方”及“施工单位”是否必填 
	public static final String FDC_PARAM_ISREQUIREDFORASKANDCONSTRCTION="FDC219_ISREQUIREDFORASKANDCONSTRCTION";
	//是否启用变更下发  
	public static final String FDC_PARAM_ALLOWDISPATCH="FDC220_ALLOWDISPATCH";
	//变更指令单是否自动改为工程签证单  
	public static final String FDC_PARAM_AUTOCHANGETOPROJECTVISA="FDC221_AUTOCHANGETOPROJECTVISA";
	//指令单是否必须在审批单审批后才能生成
	public static final String FDC_PARAM_GENERATEAFTERAUDIT = "FDC222_GENERATEAFTERAUDIT";
	//付款申请单可以直接付保修金，而不需要付结算款
	public static final String FDC_PARAM_KEEPBEFORESETTLEMENT = "FDC600_KEEPBEFORESETTLEMENT";
	
	/*******3 付款管理下的参数****FDC301**/
	//启用成本财务一体化--->启用成本月结
	public static final String FDC_PARAM_INCORPORATION= "FDC003_INCORPORATION";
	//启用成本财务一体化
	public static final String FDC_PARAM_FINACIAL = "FDC010_FINACIAL";
	//财务帐务以发票金额为准计开发成本
	public static final String FDC_PARAM_SIMPLEINVOICE = "FDC023_SIMPLEINVOICE";
	//以发票金额为准计开发成本时，凭证记录应付账款的冲抵过程
	public static final String FDC_PARAM_INVOICEOFFSET = "FDC024_INVOICEOFFSET";
	//启用简单模式成本财务一体化
	public static final String FDC_PARAM_SIMPLEFINACIAL = "FDC025_SIMPLEFINACIAL";
	//产品结算单期间采用工程项目成本期间还是财务期间,是采用成本期间,否采用财务期间
	public static final String FDC_PARAM_USECOSTORFINANCE = "FDC014_USECOSTORFINANCE";
	//付款申请单收款银行和收款账号为必录项
	public static final String FDC_PARAM_BANKREQURE = "FDC301_BANKREQURE";
	//付款单审批后可修改付款账户\付款科目\付款日期\金额
	public static final String FDC_PARAM_MODIFYACOUNT = "FDC302_MODIFY";
	//房地产付款单强制不进入出纳系统
	public static final String FDC_PARAM_NOTENTERCAS = "FDC303_NOTENTERCAS";
	//增加参数“根据工程项目对应的成本中心查看付款计划”
	public static final String FDC_PARAM_VIEWPLAN= "FDC304_VIEWPLAN";
	//付款单制单人是付款申请单制单人/审批人
	public static final String FDC_PARAM_PAYMENTCREATOR = "FDC090_PAYMENTCREATOR";
	/**甲供材的付款单生成扣款单*/
	public static final String FDC_PARAM_CREATEPARTADEDUCT= "FDC305_CREATEPARTADEDUCT";
	public static final String FDC_PARAM_PARTA_MAINCONTRACT="FDC330_PARTA_MAINCONTRACT";
	
	//启用成本财务一体化--->启用成本反月结
	public static final String FDC_PARAM_UNINCORPORATION = "FDC004_UNINCORPORATION";
	//简单财务成本一体化处理扣款、违约、奖励
	public static final String FDC_PARAM_SIMPLEFINACIALEXTEND = "FDC026_SIMPLEFINACIALEXTEND";

	//进行严格预算控制
	public static final String FDC_PARAM_BUDGET_CTRLCOSTACCOUNT= "FDC314_CTRLCOSTACCOUNT";
	//进行严格预算控制
	public static final String FDC_PARAM_BUDGET_STRICTCTRL= "FDC310_STRICTCTRL";
	//合同计划控制当月付款
	public static final String FDC_PARAM_BUDGET_CONTRACTCTRPAY= "FDC311_CONTRACTCTRPAY";
	//成本科目付款计划控制当月付款
	public static final String FDC_PARAM_BUDGET_COSTACCTCTRPAY= "FDC312_COSTACCTCTRPAY";
	//通过预算系统控制当月付款
	public static final String FDC_PARAM_BUDGET_BGSYSCTRPAY= "FDC313_BGSYSCTRPAY";
	//预算计划表显示成本列
	public static final String FDC_PARAM_BUDGET_DISPLAYCOST= "FDC309_DISPLAYCOST";
	//合同月度付款计划是否有部门合同付款计划直接生成
	public static final String FDC_PARAM_DEPCONPAYPLAN="FDC310_DEPCONPAYPLAN";
	//已实现产值为0时的控制
	public static final String FDC_PARAM_REALIZEDZEROCTRL= "FDC315_REALIZEDZEROCTRL";
	/*
	 *by Cassiel_peng
	 */
	//付款计划差异计算(项目计划执行中的本月计划执行情况的计算采用何种计算方式)
	public static final String FDC_PARAM_BUDGET_PLANDIF= "FDC316_PLANDIF";
	//项目计划执行表是否显示付款单上实际付款拆分金额及对应差异列
	public static final String FDC_PARAM_BUDGET_ACTDIF="FDC316_ACTDIF";
	//房地产业务系统附件管理是否启用笔迹留痕管理
	public static final String FDC_PARAM_WRITEMARK="FDC213_WRITEMARK";
	//是否允许拆分提交状态的单据
	public static final String FDC_PARAM_SPLITSUBMIT="FDC214_SPLITSUBMITBILL";
	//审批状态的单据可以上传附件
	public static final String FDC_PARAM_UPLOADAUDITEDBILL="FDC216_UPLOADAUDITEDBILL";
	//合同实际付款情况依据付款单而定                  此参数已作废  by cassiel_peng  2009-12-05
	public static final String FDC_PARAM_BASEONPAYMENTBILL="FDC217_BASEONPAYMENTBILL";
	//启用变更结算工作流审批
	public static final String FDC_PARAM_CHANGESETTAUDIT="FDC218_CHANGESETTAUDIT";
	//调整凭证模式
	public static final String FDC_PARAM_ADJUSTVOURCHER= "FDC019_ADJUSTVOURCHER";
	//工程量确认流程与付款流程分离
	public static final String FDC_PARAM_SEPARATEFROMPAYMENT = "FDC317_SEPARATEFROMPAYMENT";
	//允许审批后可增加合同的正文
	public static final String FDC_PARAM_ADDCONTENTAUDITED="FDC223_ADDCONTENTAUDITED";
	//结算单上保修金比例是否保留6位小数
	public static final String FDC_PARAM_FDC224_KEEP6FORGUARANTERATE="FDC224_KEEP6FORGUARANTERATE";
	//项目月度计划申请表显示合同经济条款及形象进度
	public static final String FDC_PARAM_FDC322_DISECOITEMPROCESS="FDC322_DISECOITEMPROCESS";
	/**
	 * 合同结算时，汇率是否取合同签订时的汇率
	 */
	public static final String FDC_PARAM_FDC323_SELECTEXECHANGERATE = "FDC323_SELECTEXECHANGERATE"; // added by Owen_wen 2010-10-09
	/**
	 * 合同是否上传正文控制付款申请单新增。
	 * added by Owen_wen 2010-11-19
	 */
	public static final String FDC_PARAM_FDC324_NEWPAYREQWITHCONTRACTATT = "FDC324_NEWPAYREQWITHCONTRACTATT";
	
	/**
	 * 合同、变更拆分审批后方可被后续拆分引用，后续拆分包括：结算拆分、付款拆分、工程量拆分。
	 * added by Owen_wen 2010-11-29
	 */
	public static final String FDC_PARAM_FDC5014_NEXTSPLITISBASEONPREAUDITED = "FDC5014_NEXTSPLITISBASEONPREAUDITED";
	//累计付款超过合同最新造价约定比例严格控制
	public static final String FDC_PARAM_ALLPAIDMORETHANCONPRICE="FDC444_ALLPAIDMORETHANCONPRICE";
	//启用甘特图的调整申请模式
	public static final String FDC_PARAM_ADJUSTBYGRANT="FDCSCH005_ADJUSTBYGRANT";
	//责任人是否按当前用户所在组织进行过滤
	public static final String FDC_PARAM_FILTERRESPPERSON="FDCSCH006_FILTERRESPPERSON";
	//任务工程量填报是否只能按责任人填报
	public static final String FDC_PARAM_FILLBYRESPPERSON="FDCSCH007_FILLBYRESPPERSON";
	//计划部门、责任部门可选全集团组织
	public static final String FDC_PARAM_CHOOSEALLORG="FDCSCH008_CHOOSEALLORG";
	//合同提交审批时是否必须与计划任务进行关联 2010-08-09
	public static final String FDC_PARAM_RELATEDTASK="FDC802_RELATEDTASK";
	
	
	
	
	//合同完工工程量取进度系统工程量填报数据
	public static final String FDC_PARAM_PROJECTFILLBILL = "FDC068_FROMPROJECTFILL";
	/**
	 *复杂模式财务成本一体化支持发票处理 
	 */
	public static final String FDC_PARAM_INVOICEMRG = "FDC318_INVOICEMGR";

	// 付款申请单及无文本合同发票号、发票金额必录
	public static final String FDC_PARAM_INVOICEREQUIRED = "FDC319_INVOICEREQUIRED";
	// 付款申请单,结算单根据审批时间设置期间值
	public static final String FDC_PARAM_RESETPERIOD = "FDC320_RESETPERIOD";
	// 是否允许付款申请单累计发票金额大于合同最新造价
	public static final String FDC_PARAM_OVERRUNCONPRICE = "FDC321_OVERRUNCONPRICE";
	
	
	/*******4 自定义参数界面控制的参数****FDC401******/
	//拆分生成凭证中间转换科目
	public static final String FDC_PARAM_ACCOUNTVIEW = "FDC005_ACCOUNTVIEW";
	//生成凭证的凭证类型
	public static final String FDC_PARAM_VOUCHERTYPE = "FDC012_VOUCHERTYPE";
	//项目下合同总金额不能超过目标成本金额:项目合同签约总金额超过项目
	public static final String FDC_PARAM_OUTCOST = "FDC013_OUTCOST";
	public static final String FDC_PARAM_CONTROLTYPE = "FDC013_CONTROLTYPE";
	public static final String FDC_PARAM_CONSETTTYPE = "FDC591_CONSETTTYPE";
	public static final String FDC_PARAM_COSTCENTERCONSTRATE = "FDC592_COSTCENTERCONSTRATE";
	
	/*******5 目标成本下的参数***FDC501***/	
	//待发生成本预测数据通过待发生成本预测单进行处理，待发生成本预测功能只允许查询
	public static final String FDC_PARAM_COSTMEASURE = "FDC011_COSTMEASURE";	
	//成本科目预测控制功能参数
	public static final String FDC_PARAM_ACCTBUDGET = "FDC5001001_ACCTBUDGET";
	//目标成本查询是否可以录入
	public static final String FDC_PARAM_AIMCOSTINPUT = "FDC5001002_AIMCOSTINPUT";
	//测算调整系数
	public static final String FDC_PARAM_MEASUREADJUST = "FDC5001002_MEASUREADJUST";
	public static final String FDC_PARAM_MEASUREQUALITY = "FDC5001003_MEASUREQUALITY";
	//使用自定义指标
	public static final String FDC_PARAM_USECOSTOMINDEX = "FDC5001004_USECOSTOMINDEX";
	//目标成本与调整记录是否允许直接录入
	public static final String FDC_PARAM_AIMCOSTADJUSTINPUT = "FDC5001005_AIMCOSTADJUSTINPUT";
	//允许删除待发生成本的调整记录
	public static final String FDC_PARAM_AIMCOSTADJUSTDELETE = "FDC5001009_AIMCOSTADJUSTDELETE";
	//目标成本测算时，各产品分摊是否按集团规定的指标进行分摊
	public static final String FDC_PARAM_MEASUREINDEX = "FDC5001010_MEASUREINDEX";
	//--参数：允许科目总和为负的结算拆分
	public static final String FDC_PARAM_NOSETTLEMENTSPLITACCTCTRL = "FDC5001100_NOSETTLEMENTSPLITACCT";
	//参数：允许科目总和为负的变更拆分
	public static final String FDC_PARAM_NOCHANGESPLITACCTCTRL = "FDC5001200_NOCHANGESPLITACCT";
	//参数：收入测算产品可售面积之和不能超过规划指标表可售面积
	public static final String FDC_PARAM_LIMITSELLAREA ="FDC5001300_LIMITSELLAREA";
	//合同是否可进行量价拆分
	public static final String FDC_PARAM_MEASURESPLIT = "FDC010_MEASURESPLIT";
	//参数：目标成本修订审批之后才影响动态成本
	public static final String FDC_PARAM_AIMCOSTAUDIT = "FDC5001006_AIMCOSTAUDIT";
	// 可研成本测算及目标成本测算时，规划指标表上：人行道及广场计算在“绿化用地合计”内
	public static final String FDC_PARAM_PLANINDEXLOGIC = "FDC5001007_PLANINDXLOGIC";
	// 可研成本测算、目标成本测算上项目建筑单方计算，目标成本测算导出项目建筑面积指标都使用参与分摊的产品建筑面积之和，而不是总建筑面积
	public static final String FDC_PARAM_BUILDPARTLOGIC = "FDC5001008_BUILDPARTLOGIC";	
	
    public static final String FDC_PARAM_MEASURECOSTISCONTAINDEVPROJECT ="FDC588_MEASURECOSTCONTAINDEVPROJECT";
	
    public static final String FDC_PARAM_SETTLEMENTOVERCONTRACTAMOUNT = "FDC589_SETTLEMENTOVERCONTRACTAMOUNT";
   
    public static final String FDC_PARAM_ADVANCEPAYMENTNUMBER = "FDC590_ADVANCEPAYMENTNUMBER";
    
    public static final String FDC_PARAM_CANMODIFYCONTRACTNUMBER ="FDC601_CANMODIFYNUMBER";
    
    public static final String FDC_PARAM_INCLUDECHANGEAUDIT ="FDC800_INCLUDECHANGEAUDIT";
    
    public static final String FDC_PARAM_ISOPENPAYMENTEDITUI = "FDC801_ISOPENPAYMENTEDITUI";
    
    //成本测算与收入测算是否联用
    public static final String FDC_PARAM_ISINCOMEJOINCOST = "FDC5013_ISINCOMEJOINCOST";
    
	/*******5 进度下的***FDC501***/
	//启用项目计划任务授权
	public static final String FDC_PARAM_PLANMANDATE = "FDC088_PlanMandate";
	public static final String FDC_PARAM_SCHUDLEEXECUSEVIRTUALMODE = "FDCSCH009_EXECUSEVIRTUALMODE";
	
	//资源常量
	public static final String FinanceResource = "com.kingdee.eas.fdc.finance.client.FinanceResource";
	public static final String VoucherResource = "com.kingdee.eas.fi.gl.client.VoucherEditResource";
	
	
	//预算控制实体
	//付款申请单
	static public final String PayRequestBill = "com.kingdee.eas.fdc.contract.app.PayRequestBill";
	
	//预算控制合同费用项目
	//合同
	public static final String ContractBill = "com.kingdee.eas.fdc.contract.app.ContractBill";
	//合同
	public static final String ContractWithoutText = "com.kingdee.eas.fdc.contract.app.ContractWithoutText";
	
	//初始化数据标志
	public static final String FDC_INIT_ORGUNIT = "orgUnitId";
	public static final String FDC_INIT_CURRENCY = "baseCurrencyInfo";
	public static final String FDC_INIT_COMPANY= "company";
	public static final String FDC_INIT_CONTRACT = "contractBillInfo";
	
	public static final String FDC_INIT_PROJECT = "curProject";
	public static final String FDC_INIT_PERIOD = "curPeriod";
	public static final String FDC_INIT_DATE = "bookedDate";	
	
	public static final String FDC_INIT_ISFREEZE = "isFreeze";
	public static final String FDC_INIT_BOOKEDPERIOD = "canBookedPeriod";
	
	/*
	 *如下几个常量用于做付款凭证分录的类型 
	 */
	public static final String FDC_INIT_PROJECTPRICEINCONTRACT = "projectPriceInContract";
	public static final String FDC_INIT_ADVANCE = "advance";
	public static final String FDC_INIT_COMPENSATION = "compensation";
	public static final String FDC_INIT_GUERDON = "guerdon";
	public static final String FDC_INIT_INVOICE = "invoice";
	/**万科一体化需求,暂屏蔽奖励*/
	public static final boolean INVALID_GUERDON = true;
	
	/*******6 招投标下面的***FDC601***/
	/**
	 * 采购招投标
	 */
	/**招标文件合成、供应商资格预审、确定评标专家、确定评标模板一块审批**/
	public static final String INVITE_SUPPLIER_EXPERT_TEMPLATE = "m0+mbCEzSQm6Deqwm/ltVKiB8+c=";
	public static final String FDC_PARAM_INVITE_SUPPLIER_EXPERT_TEMPLATE = "FDC601_INVITE_TOGETHERAUDIT";
	//采购申请单物料必须来源于甲供物资清单
	public static final String FDC_PARAM_INVITE_PURCHASEBOMMUSTPARTA="FDC602_INVITE_PURCHASEBOMMUSTPARTA";
	//供应商必须来源于房地产的供应商管理生成的标准供应商
	public static final String FDC_PARAM_INVITE_SUPPLIER_FROM_FDCSUPPLIER = "FDC603_INVITE_SUPPLIERFROMFDC";
	/****供应商参数 ***********/
	public static final String FDC_PARAM_SUPPLIER_LIMIT="FDC_SUPPLIER_LIMIT";
	
	/*******7 投资测算下面的参数 **** FDC701****/
	//成本费用测算中测算汇总表的单方是否可录入
	public static final String MEASURE_COSTMEASURE = "DjH0Xv3XTi6aP4tP3klLCqiB8+c=";
	public static final String FDC_PARAM_MEASURE_COSTMEASURE = "FDC701_MEASURE_COSTMEASURE";

	
	/**进度管理下的FDCSCH开头，一般放到ScheduleParamHelper中，因为关系到付款的东西，所以放到basedata中**/
	/**
	 * 是否基于合同填报工程量
	 */
	public static final String FDCSCH_PARAM_ISFILLBILLCONTROLSTRICT = "FDCSCH003";
	public static final String FDCSCH_PARAM_BASEONTASK = "FDCSCH04_BASEONTASK";
	/**
	 * 合同签约金额超过与之关联的合约规划金额时是否严格控制
	 * */
	public static final String FDC_PARAM_CONTRACT_PROGRAM_AMOUNT = "ContractProgramAmountControlMode";
	//历史合同关联合约规划
	public static final String FDC_PARAM_OLDCONTRACT_PROGRAM = "FDC900_CON_OLDCONTRACTRTOPROGRAMMING";
	
}
