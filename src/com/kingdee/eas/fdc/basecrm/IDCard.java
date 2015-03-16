package com.kingdee.eas.fdc.basecrm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.kingdee.eas.fdc.sellhouse.SexEnum;

public class IDCard {

	private static String CERTIFICATE_EIGHTEEN = "X";

	/**
	 * 构造
	 */
	public IDCard() {

	}

	/*********************************** 身份证验证开始 ****************************************/
	/**
	 * 身份证号码验证 1、号码的结构 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码，
	 * 八位数字出生日期码，三位数字顺序码和一位数字校验码。 2、地址码(前六位数）
	 * 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。 3、出生日期码（第七位至十四位）
	 * 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。 4、顺序码（第十五位至十七位）
	 * 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号， 顺序码的奇数分配给男性，偶数分配给女性。 5、校验码（第十八位数）
	 * （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和
	 * Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4
	 * 2 （2）计算模 Y = mod(S, 11) （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0
	 * X 9 8 7 6 5 4 3 2
	 */

	public static boolean CardValidate(String IDStr) throws ParseException {
		if (IDCardValidate(IDStr).equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 功能：身份证的有效验证
	 * 
	 * @param IDStr
	 *            身份证号
	 * @return 有效：返回"" 无效：返回String信息
	 * @throws ParseException
	 */
	public static String IDCardValidate(String IDStr) throws ParseException {
		IDStr = IDStr.trim();
		String errorInfo = "";// 记录错误信息
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",
				"3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
				"9", "10", "5", "8", "4", "2" };
		String Ai = "";
		// ================ 号码的长度 15位或18位 ================
		if (IDStr.trim().length() != 15 && IDStr.trim().length() != 18) {
			errorInfo = "身份证号码长度应该为15位或18位。";
			return errorInfo;
		}

		// =======================(end)========================

		// ================ 数字 除最后以为都为数字 ================
		// new update by renliang at 2011-3-5
		if (IDStr.trim().length() == 18) {
			String eighteen = IDStr.substring(17, 18).toUpperCase();
			if (!isNumeric(eighteen)) {
				if (!CERTIFICATE_EIGHTEEN.equals(eighteen)) {
					errorInfo = "身份证18位号码最后一位为字母的话，一定为X，不能为其他的！";
					return errorInfo;
				}
			}
		}
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}

		if (!isNumeric(Ai)) {
			errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
			return errorInfo;
		}

		// =======================(end)========================

		// ================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 月份
		if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
			errorInfo = "身份证生日无效。";
			return errorInfo;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
				|| (gc.getTime().getTime() - s.parse(
						strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
			errorInfo = "身份证生日不在有效范围。";
			return errorInfo;
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			errorInfo = "身份证月份无效";
			return errorInfo;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			errorInfo = "身份证日期无效";
			return errorInfo;
		}

		// new add by renliang
		errorInfo = checkYear(IDStr);

		if (!"".equals(errorInfo)) {
			return errorInfo;
		}
		// =====================(end)=====================

		// ================ 地区码时候有效 ================
		Hashtable h = GetAreaCode();
		if (h.get(Ai.substring(0, 2)) == null) {
			errorInfo = "身份证地区编码错误。";
			return errorInfo;
		}
		// ==============================================

		// ================ 判断最后一位的值 ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi
					+ Integer.parseInt(String.valueOf(Ai.charAt(i)))
					* Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		// new update by renliang at 2011-3-15
		if ("x".equals(strVerifyCode)) {
			strVerifyCode = strVerifyCode.toUpperCase();
		}
		Ai = Ai + strVerifyCode;

		if (IDStr.length() == 18) {
			String eTemp=IDStr.substring(17,18);
			if ("x".equals(eTemp)) {
				eTemp = eTemp.toUpperCase();
			}
			IDStr = IDStr.substring(0,17)+eTemp;
			if (Ai.equals(IDStr) == false) {
				errorInfo = "身份证无效，不是合法的身份证号码";
				return errorInfo;
			}
		} else {
			return "";
		}
		// =====================(end)=====================
		return "";
	}

	/**
	 * 功能：设置地区编码
	 * 
	 * @return Hashtable 对象
	 */
	private static Hashtable GetAreaCode() {
		Hashtable hashtable = new Hashtable();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	/**
	 * 功能：判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 功能：判断字符串是否为日期格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDate(String strDate) {
		Pattern pattern = Pattern
				.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		String IDCardNum = "610112820318501";
		System.out.println(IDCard.checkYear(IDCardNum));
	}

	/*********************************** 身份证验证结束 ****************************************/
	/**
	 * 功能：把15位身份证转换成18位
	 * 
	 * @param id
	 * @return newid or id
	 */
	public static final String getIDCard_18bit(String id) {
		// 若是15位，则转换成18位；否则直接返回ID
		if (15 == id.length()) {
			final int[] W = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4,
					2, 1 };
			final String[] A = { "1", "0", "X", "9", "8", "7", "6", "5", "4",
					"3", "2" };
			int i, j, s = 0;
			String newid;
			newid = id;
			newid = newid.substring(0, 6) + "19"
					+ newid.substring(6, id.length());
			for (i = 0; i < newid.length(); i++) {

				j = Integer.parseInt(newid.substring(i, i + 1)) * W[i];
				s = s + j;
			}
			s = s % 11;
			newid = newid + A[s];

			return newid;
		} else {
			return id;
		}

	}

	/**
	 * 从身份证获取出生日期
	 * 
	 * @param cardNumber
	 *            已经校验合法的身份证号
	 * @return Strig YYYY-MM-DD 出生日期
	 */
	public static String getBirthDateFromCard(String cardNumber) {
		String card = cardNumber.trim();
		String year;
		String month;
		String day;
		if (card.length() == 18) { // 处理18位身份证
			year = card.substring(6, 10);
			month = card.substring(10, 12);
			day = card.substring(12, 14);
		} else { // 处理非18位身份证
			year = card.substring(6, 8);
			month = card.substring(8, 10);
			day = card.substring(10, 12);
			year = "19" + year;
		}
		if (month.length() == 1) {
			month = "0" + month; // 补足两位
		}
		if (day.length() == 1) {
			day = "0" + day; // 补足两位
		}
		return year + "-" + month + "-" + day;
	}

	/**
	 * 从身份证获取性别
	 * 
	 * @param cardNumber
	 *            已经校验合法的身份证号
	 * @return String Sex 性别
	 */
	public static String getSexFromCard(String cardNumber) {
		String inputStr = cardNumber.toString();
		int sex;
		if (inputStr.length() == 18) {
			sex = inputStr.charAt(16);
			if (sex % 2 == 0) {
				return SexEnum.WOMENFOLK_VALUE;
			} else {
				return SexEnum.MANKIND_VALUE;
			}
		} else {
			sex = inputStr.charAt(14);
			if (sex % 2 == 0) {
				return SexEnum.WOMENFOLK_VALUE;
			} else {
				return SexEnum.MANKIND_VALUE;
			}
		}
	}

	/**
	 * 从身份证获取出生日期
	 * 
	 * @param cardNumber
	 *            已经校验合法的身份证号
	 * @return Strig YYYY-MM-DD 出生日期
	 */
	public static java.sql.Date getBirthFromCard(String cardNumber) {
		String birthString = getBirthDateFromCard(cardNumber);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date birthDate = new java.sql.Date(new Date().getTime());
		// 将字符串转换为Date
		try {
			Date date = df.parse(birthString);
			birthDate = new java.sql.Date(date.getTime());
		} catch (ParseException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return birthDate;

	}

	/**
	 * 从身份证上取得地区
	 * 
	 * @param cardNumber
	 * @return
	 */
	public static String getAreaFromCard(String cardNumber) {
		String areaCode = "";
		Hashtable h = GetAreaCode();
		if (h.get(cardNumber.substring(0, 2)) != null) {
			areaCode = h.get(cardNumber.substring(0, 2)).toString();
		}
		return areaCode;
	}

	/**
	 * 根据身份证计算周岁
	 */
	public static int getAge(String IDCardNum) {
		Calendar cal1 = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		// 如果身份证15位则将其转化为18位
		if (IDCardNum.length() == 15)
			IDCardNum = getIDCard_18bit(IDCardNum);
		cal1.set(Integer.parseInt(IDCardNum.substring(6, 10)), Integer
				.parseInt(IDCardNum.substring(10, 12)), Integer
				.parseInt(IDCardNum.substring(12, 14)));
		return getYearDiff(today, cal1);
	}

	public static int getYearDiff(Calendar cal, Calendar cal1) {
		int m = (cal.get(cal.MONTH)) - (cal1.get(cal1.MONTH));
		int y = (cal.get(cal.YEAR)) - (cal1.get(cal1.YEAR));
		return (y * 12 + m) / 12;
	}

	/**
	 * 判断年份是否是闰年
	 * 
	 * @param year
	 * @return
	 */
	private static boolean isLeapYear(int year) {
		if ((year % 400 == 0) || (year % 4 == 0) && (year % 100 != 0)) {
			return true;
		} else {
			return false;
		}
	}

	private static String checkYear(String idCard) {
		String result = "";

		if (idCard.trim().length() == 15) {
			int seven_eight = Integer.parseInt(idCard.substring(6, 8));
			int nine_ten = Integer.parseInt(idCard.substring(8, 10));
			int ele_twe = Integer.parseInt(idCard.substring(10, 12));

			if (seven_eight > 99 || seven_eight < 0) {
				result = "身份证年份不正确！";
				return result;
			}
			if (nine_ten > 12 || nine_ten < 1) {
				result = "身份证月份不正确！";
				return result;
			}
			if (nine_ten == 2) {
				if (isLeapYear(Integer.parseInt("19" + idCard.substring(6, 8)))) {
					if (ele_twe > 29 || ele_twe < 1) {
						result = "身份证天数不正确！";
						return result;
					}
				} else {
					if (ele_twe > 28 || ele_twe < 1) {
						result = "身份证天数不正确！";
						return result;
					}
				}
			} else {
				if (ele_twe > 31 || ele_twe < 1) {
					result = "身份证天数不正确！";
					return result;
				}
			}
		} else if (idCard.trim().length() == 18) {
			int seven_eight = Integer.parseInt(idCard.substring(6, 10));
			int nine_ten = Integer.parseInt(idCard.substring(10, 12));
			int ele_twe = Integer.parseInt(idCard.substring(12, 14));

			if (seven_eight > 2099 || seven_eight < 1800) {
				result = "身份证年份不正确！";
				return result;
			}
			if (nine_ten > 12 || nine_ten < 1) {
				result = "身份证月份不正确！";
				return result;
			}
			if (nine_ten == 2) {
				if (isLeapYear(Integer.parseInt(idCard.substring(6, 10)))) {
					if (ele_twe > 29 || ele_twe < 1) {
						result = "身份证天数不正确！";
						return result;
					}
				} else {
					if (ele_twe > 28 || ele_twe < 1) {
						result = "身份证天数不正确！";
						return result;
					}
				}
			} else {
				if (ele_twe > 31 || ele_twe < 1) {
					result = "身份证天数不正确！";
					return result;
				}
			}
		}
		return result;
	}

}
