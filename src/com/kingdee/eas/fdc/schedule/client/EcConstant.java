package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.kingdee.eas.fi.gl.GlUtils;

public class EcConstant {

	//组织常量
	public static final String CORP_CU = "00000000-0000-0000-0000-000000000000CCE7AED4";
	public static final String SYS_CU = "11111111-1111-1111-1111-111111111111CCE7AED4";
	
	//资源文件常量
	public static final String EcResource = "com.kingdee.eas.ec.basedata.client.EcResource";
	
	//精度常量
//	public static final int SCALE_QTY = 2;
//	public static final int SCALE_PRICE = 2;
//	public static final int SCALE_AMOUNT = 2;
//	public static final int SCALE_Rate = 2;
	
	public static final int MAX_LENGTH_TXT = 80;
	
	//数字常量
	public static final BigDecimal ZERO = new BigDecimal("0.0");

	/** BigDecimal Value : 1 */
	public static final BigDecimal ONE = new BigDecimal("1.0");

	/** BigDecimal Value : -1 */
	public static final BigDecimal _ONE = new BigDecimal("-1");

	/** BigDecimal Value : 10 */
	public static final BigDecimal TEN = new BigDecimal("10");

	/** BigDecimal Value : -10 */
	public static final BigDecimal _TEN = new BigDecimal("-10");

	/** BigDecimal Value : 100 百 */
	public static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

	/** BigDecimal Value : -100 百 */
	public static final BigDecimal _ONE_HUNDRED = new BigDecimal("-100");

	/** BigDecimal Value : 1000 千 */
	public static final BigDecimal ONE_THOUSAND = new BigDecimal("1000");

	/** BigDecimal Value : -1000 千 */
	public static final BigDecimal _ONE_THOUSAND = new BigDecimal("-1000");

	/** BigDecimal Value : 10000 万 */
	public static final BigDecimal TEN_THOUSAND = new BigDecimal("10000");

	/** BigDecimal Value : -10000 万 */
	public static final BigDecimal _TEN_THOUSAND = new BigDecimal("-10000");

	/** BigDecimal Value : 100000000 亿 */
	public static final BigDecimal ONE_HUNDRED_MILLION = new BigDecimal(
			"100000000");

	/** BigDecimal Value : -100000000 亿 */
	public static final BigDecimal _ONE_HUNDRED_MILLION = new BigDecimal(
			"-100000000");

	// public static final BigDecimal MAX_VALUE = new
	// BigDecimal("999999999999999999.9999999999");
	// public static final BigDecimal MIN_VALUE = new
	// BigDecimal("-999999999999999999.9999999999");

	// 最大明细数（1兆），最小明细数（负1兆），和总帐一致，所以要生成凭证的字段必须不能大于此数，（13位）
	public static final BigDecimal MAX_VALUE = GlUtils.maxBigDecimal.divide(
			ONE_HUNDRED, BigDecimal.ROUND_HALF_UP); // 1000000000000

	public static final BigDecimal MIN_VALUE = GlUtils.minBigDecimal.divide(
			ONE_HUNDRED, BigDecimal.ROUND_HALF_UP); // -1000000000000

	// 最大合计数（100兆），最小合计数（负100兆）MAX_VALUE/MIN_VALUE * 100 （15位）
	public final static BigDecimal MAX_TOTAL_VALUE = MAX_VALUE
			.multiply(ONE_HUNDRED);

	public final static BigDecimal MIN_TOTAL_VALUE = MIN_VALUE
			.multiply(ONE_HUNDRED);
	
//	 最大合计数（1000兆），最小合计数（负1000兆）MAX_VALUE/MIN_VALUE * 1000 （16位）
	public final static BigDecimal MAX_TOTAL_VALUE2 = MAX_VALUE
			.multiply(ONE_THOUSAND);

	public final static BigDecimal MIN_TOTAL_VALUE2 = MIN_VALUE
			.multiply(ONE_THOUSAND);

	public final static Color KDTABLE_TOTAL_BG_COLOR = new Color(0xF6F6B6);

	public final static Color KDTABLE_SUBTOTAL_BG_COLOR = new Color(0xF5F5E6);

	// 格式化精度设置
	public final static String KDTABLE_NUMBER_FTM = "%-.2n";

	// public final static String kdTablePercentFtm = "%{0.00%}f";
	public final static String KDTABLE_PERCENT_FTM = "%r{0.00}p";

	public final static String KDTABLE_DATE_FMT = "yyyy-MM-dd";
	public final static String KDTABLE_DATETIME_FMT = "yyyy-MM-dd HH:mm:ss";

	public final static String ACTUAL_DIGIT_FMT = "#,##0.###########";

	public static final String strDataFormat = "#,##0.00;-#,##0.00";

	/**
	 * KDFormattedTextField使用的最大最小值。目前数据库定义的金额字段为18位，控件采用16位显示
	 * 关于数据精度的设计规范金额字段统一使用 (19,4)
	 */
	public static final BigDecimal MAX_DECIMAL = new BigDecimal(
			"999999999999999.9999");

	public static final BigDecimal MIN_DECIMAL = new BigDecimal(
			"-999999999999999.9999");

	public static final DateFormat FORMAT_DAY = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static final DateFormat FORMAT_TIME = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static final DateFormat FORMAT_MONTH = new SimpleDateFormat(
			"yyyy-MM");
	
	
	//初始化数据标志
	public static final String EC_INIT_ORGUNIT = "orgUnitId";
	public static final String EC_INIT_CURRENCY = "baseCurrencyInfo";
	public static final String EC_INIT_COMPANY= "company";
	public static final String EC_INIT_CURRENTPERIOD= "currentPeriod";
	
	//参数设置常量
	/**基础资料 EC1 **/
	//会计期间类型
	public static final String EC101_PERIODTYPE = "EC101_PeriodType";
	public static final String EC102_PERIODTYPE = "EC102_PeriodType";
	
	//精度
	public static final String EC102_AMOUT = "EC102_AMOUT";
	public static final String EC103_QTY = "EC103_QTY";
	public static final String EC104_PRICE = "EC104_PRICE";
	
	/**进度 EC20**/
	//是否启用施工任务书
	public static final String EC201_TASTBILL = "EC201_TASTBILL";
	//工程归集需要加参数控制：按是否包含上月算或者按填报天数多数在哪个月算
	public static final String EC202_FILL = "EC202_FILL";
	//总体进度计划工程量来源
	public static final String EC203_COUNT = "EC203_COUNT";
	
	/**合同 EC30**/
	//合同最终结算单新增时判断变动类型为"暂定变更"的清单项已清零
	public static final String EC301_LSTSETTLE = "EC301_LSTSETTLE";
	//合同结算付款是否走发票流程
	public static final String EC301_INVOICE_NEED="EC301_INVOICE_NEED";
	/**供应商 EC40**/
	//成为合格供应商合格最低等级值
	public static final String EC401_SUPPLIER_LIMIT="EC401_SUPPLIER_LIMIT";
	public static final String EC402_SUPPLIER_ESTIMATERESULT="EC402_SUPPLIER_ESTIMATERESULT";
	
	/**招标 EC50**/
	
	/**成本 EC60**/
	//是否启用成本系统
	public static final String EC0601_COSTSTART = "EC0601_COSTSTART";
	//初始化单据是否严格控制
	public static final String EC0602_STRICTINIT = "EC0602_STRICTINIT";	
	//是否使用指标库资源
	public static final String EC0603_ISUSECOSTINDEX = "EC0603_ISUSECOSTINDEX";
	//期末理论成本单价来源
	public static final String EC0604_COSTPRICESOURCE = "EC0604_COSTPRICESOURCE";
	//成本科目是否可以录入负数
	public static final String EC0605_COSTACCOUNTCANNEGATIVE = "EC0605_COSTACCOUNTCANNEGATIVE";
	
	//错误提示最大数量
	public static final int MAX_ERRTIP=100;
}
