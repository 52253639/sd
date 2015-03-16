package com.kingdee.eas.fdc.finance.client;

public final class PaymentBillContants {

	//PaymentBillListUI中付款单需要显示的列名
	public static final String COL_ID = "id";
	public static final String COL_STATE = "billStatus";
    public static final String COL_NUMBER = "number";
    public static final String COL_REQNUM = "payreqbillnum";
    public static final String COL_CURRENCY = "currency";   
    public static final String COL_AMOUNT = "amount";
    public static final String COL_EXCHANGERATE = "exchangeRate"; 
    public static final String COL_LOCALAMT = "localAmt"; 
    public static final String COL_PAYDATE = "payDate";
    public static final String COL_PAYEENAME = "payeeName";
    public static final String COL_ACTPAYEENAME = "actPayeeName";
    public static final String COL_CREATER = "creator.name";
    public static final String COL_CREATETIME = "createTime";
    public static final String COL_SUMMARY = "summary";
    public static final String COL_PAYEEBANK = "payeeBank";
    public static final String COL_PAYEEACCOUNTBANK = "payeeAccountBank";
    public static final String COL_AUDITOR = "auditor.name";
    public static final String COL_AUDITDATE = "auditDate";
    public static final String COL_ISRESPITE = "isRespite"; 

    //PaymentBillEditUI分录内要绑定的对象的常量名
    public static final String CONTRACTNAME="contractName";
    public static final String CONTRACTPRICE="contractPrice";   
    public static final String IMAGESCHEDULE="imageSchedule";
    public static final String SCHEDULEAMT="scheduleAmt";
    public static final String LATESTPRICE="latestPrice";
    public static final String ADDPROJECTAMT="addProjectAmt";
    public static final String ADDPROJECTAMTORI="addProjectOriAmt";
    public static final String PROJECTPRICEINCONTRACT="projectPriceInContract";
    public static final String PROJECTPRICEINCONTRACTORI="projectPriceInContractOri";
    public static final String CHANGEAMT="changeAmt";
    public static final String PAYPARTAMATLAMT="payPartAMatlAmt";
    public static final String PAYPARTAMATLAMTORI="payPartAMatlOriAmt";
    public static final String PAYTIMES="payTimes";
    public static final String SETTLEAMT="settleAmt";
    public static final String URGENTDEGREE="urgentDegree";
    public static final String CAPITALAMOUNT="capitalAmount";
    public static final String PAYEDAMT="payedAmt";
    /**原币*/
    public static final String CURPAID="curPaid";
    /**本币*/
    public static final String CURPAIDLOCAL="curpaidlocal";
    public static final String PRJALLREQAMT="prjAllReqAmt";
    public static final String ADDPRJALLREQAMT="addPrjAllReqAmt";	
    public static final String PAYPARTAMATLALLREQAMT="payPartAMatlAllReqAmt";    
    //public static final String LSTPRJALLREQAMT="lstPrjAllReqAmt";
    //public static final String LSTADDPRJALLREQAMT="lstAddPrjAllReqAmt";	
    //public static final String LSTAMATLALLREQAMT="lstAMatlAllReqAmt";   
    public static final String CURPLANNEDPAYMENT="curPlannedPayment";
    public static final String CURBACKPAY="curBackPay";
    public static final String PAYMENTPLAN="paymentPlan";
    public static final String CURREQPERCENT="curReqPercent";
    public static final String ALLREQPERCENT="allReqPercent";
    public static final String CURPROJECT="curProject";
    
    /**
     * 合同内工程款上期累计申请
     */
    public static final String LSTPRJALLREQAMT="lstPrjAllReqAmt";
    /**
     * 增加工程款上期累计申请
     */
    public static final String LSTADDPRJALLREQAMT="lstAddPrjAllReqAmt";
    /**
     * 甲供材上期累计申请
     */
    public static final String LSTAMATLALLREQAMT="lstAMatlAllReqAmt";
    /**
     * 合同内工程款上期累计实付
     */
    public static final String LSTPRJALLPAIDAMT="LstPrjAllPaidAmt";
    /**
     * 增加工程款上期累计实付
     */
    public static final String LSTADDPRJALLPAIDAMT="LstAddPrjAllPaidAmt";
    /**
     * 甲供材上期累计实付
     */
    public static final String LSTAMATLALLPAIDAMT="LstAMatlAllPaidAmt";
    
    /** 预付款截至上期累计实付 */
	public static final String LSTADVANCEALLPAID = "lstAdvanceAllPaid";
	
	/** 预付款-截至上期累计申请 */
	public static final String LSTADVANCEALLREQ = "lstAdvanceAllReq";
	
	/** 预付款-原币 */
	public static final String ADVANCE = "advance";
	
	/** 预付款-本币 */
	public static final String LOCALADVANCE = "locAdvance";
	
	/** 预付款-截至本期累计申请 */
	public static final String ADVANCEALLREQ = "advanceAllReq";
	
	/** 预付款-截至本期累计实付 */
	public static final String ADVANCEALLPAID = "advanceAllPaid";
	
}
