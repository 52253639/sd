package com.kingdee.eas.fdc.invite.client;

import java.math.BigDecimal;
import java.util.HashMap;

import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.eas.fdc.basedata.FDCHelper;

/**
 *
 * @author 肖飙彪
 * @version 功能描述：进行简单的excel公式计算
 */
public class SimpleExcelCalculateUtil {
	private static HashMap signMap = new HashMap();
	static {
		signMap.put("+", "");
		signMap.put("-", "");
		signMap.put("*", "");
		signMap.put("/", "");
		signMap.put("(", "");
		signMap.put(")", "");
		signMap.put("[", "");
		signMap.put("]", "");
		signMap.put("{", "");
		signMap.put("}", "");
		signMap.put(":", "");
		signMap.put("SUM", "");
	}

	/**
	 * 功能描述：根据sheet计算变量的值
	 *
	 * @param variable
	 * @param sheet
	 * @return
	 * @throws Exception
	 */
	private static BigDecimal variableAnalyse(String variable, Sheet sheet)
			throws Exception {
		if (variable == null) {
			throw new Exception("参数variable为空！");
		}
		BigDecimal decimal = new BigDecimal("0");
		String rowString = "";
		String colString = "";
		variable = variable.trim();
		while (variable.length() > 0) {
			char temp = variable.substring(0, 1).toUpperCase().charAt(0);
			if (temp >= '0' && temp <= '9') {
				rowString += temp;
			} else if (temp >= 'A' && temp <= 'Z') {
				colString += temp;
			}
			variable = variable.substring(1);
		}
		int row = 0;
		int col = 0;
		row = Integer.parseInt(rowString) - 1;
		while (colString.length() > 0) {
			char temp = colString.substring(0, 1).toUpperCase().charAt(0);
			col += (temp - 'A') * Math.pow(26, colString.length() - 1);
			colString = colString.substring(1);
		}
		if (sheet == null) {
			throw new Exception("参数sheet为空！");
		}
		Variant variant = sheet.getCell(row, col, true).getValue();
		if (variant.getValue() != null) {
			try {
				decimal = new BigDecimal(variant.getValue().toString());
			} catch (Exception e) {
				throw new Exception("单元格[" + variable + "]值["
						+ variant.getValue().toString() + "]不是数字！");
			}
		}
		return decimal;
	}

	/**
	 * 功能描述：表达式分析，返回公式中变量
	 *
	 * @param exprise
	 * @return
	 * @throws Exception
	 */
	private static HashMap expriseAnalyse(String exprise) {
		HashMap map = new HashMap();
		String variable = "";
		while (exprise.length() > 0) {
			String temp = exprise.substring(0, 1);
			if (!signMap.containsKey(temp)) {
				variable += temp;
			} else {
				variable = variable.trim();
				if (!variable.equals("")) {
					if (!signMap.containsKey(variable)) {
						map.put(variable, "");
					}
					variable = "";
				}
			}
			exprise = exprise.substring(1);
		}
		if (!variable.equals("")) {
			map.put(variable, "");
		}
		return map;
	}

	/**
	 * 功能描述：计算所有变量值，并存入map
	 *
	 * @param map
	 * @param sheet
	 * @throws Exception
	 */
	private static void fillVariableMapValue(HashMap map, Sheet sheet)
			throws Exception {
		Object[] variable = map.keySet().toArray();
		for (int i = 0; i < variable.length; i++) {
			map.put(variable[i], variableAnalyse((String) variable[i], sheet));
		}
	}

	/**
	 * 功能描述：计算表达式
	 *
	 * @param exprise
	 * @param sheet
	 * @return
	 * @throws Exception
	 */
	public static BigDecimal calExprise(String exprise, Sheet sheet)
			throws Exception {
		if (exprise.startsWith("=")) {
			exprise = exprise.substring(1);
		}
		BigDecimal decimal = null;//new BigDecimal("0");
		HashMap map = expriseAnalyse(exprise);
		fillVariableMapValue(map, sheet);
		decimal = calExprise(exprise, map);
		return decimal;
	}

	/**
	 * 功能描述：计算表达式
	 *
	 * @param exprise
	 * @param map
	 * @return
	 */
	public static BigDecimal calExprise(String exprise, HashMap map) {
		if (exprise.startsWith("=")) {
			exprise = exprise.substring(1);
		}
		BigDecimal decimal = null;//new BigDecimal("0");
		Object[] variable = map.keySet().toArray();
		for (int i = 0; i < variable.length; i++) {
			String var = (String) variable[i];
			exprise = exprise.replaceAll(var, ((BigDecimal) map.get(var))
					.toString());
		}
		decimal = new BigDecimal(FDCHelper.getScriptResult(exprise).toString());
		return decimal;
	}

	public static void main(String[] args) {
		try {
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}
}
