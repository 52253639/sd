package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.text.NumberFormatter;

import com.kingdee.bos.ctrl.common.util.StringUtil;
import com.kingdee.bos.ctrl.swing.DecimalFormatEx;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.NumberFormatterEx;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.ctrl.swing.util.CtrlFormatUtilities;

public class FDCFormattedTextField extends KDFormattedTextField {

	public FDCFormattedTextField() {
	}

	public FDCFormattedTextField(AbstractFormatterFactory factory) {
		super(factory);
	}

	public FDCFormattedTextField(Object value) {
		super(value);
	}

	public FDCFormattedTextField(int dataType) {
		super(dataType);
	}

	//重载创建编辑formatter，修改formatter的实例为FDCNumberFormatterEx
	protected NumberFormatter createEditFormatter(int newDataType) {
		DecimalFormatEx sysFormat = CtrlFormatUtilities.getEditNumberFormat();
		DecimalFormatEx format = null;
		if (sysFormat == null)
			format = new DecimalFormatEx();
		else
			format = new DecimalFormatEx(sysFormat.toPattern());

		FDCNumberFormatterEx formatter = new FDCNumberFormatterEx();

		// 让DecimalFormatEx能够控制编辑状态, by 苏志钦, 20060810
		format.setFormattedTextField(this);
		// 0 精度
		formatter.setDecimalPrecision(getPrecision());
		format.setMaximumFractionDigits(getPrecision());

		// 1 舍入原则
		formatter.setRoundingMode(this.getRoundingMode());
		
		// 2 千份位表示
		format.setGroupingUsed(false);

		// 是否百分数 （目前不支持编辑状态时采用百分数表示）
		// 3是否支持空值
		formatter.setSupportedEmpty(isSupportedEmpty());

		// 4 验证规则
		if (getDataVerifierType() == INPUTING_VERIFIER) {
			formatter.setAllowsInvalid(false);
			formatter.setSupportedEmpty(false);
			format.setMinimumFractionDigits(getPrecision());
		} else {
			formatter.setAllowsInvalid(true);
		}

		// 5 数据异常处理

		// 6 是否去零处理
		formatter.setRemoveingZero(isRemoveingZeroInEdit());
		if (!isRemoveingZeroInEdit()) {
			format.setMinimumFractionDigits(getPrecision());
		}

		// 7 最大值设置
		formatter.setMaximum((Comparable) getMaximumValue());

		// 8 最小值设置
		formatter.setMinimum((Comparable) getMinimumValue());

		// 9 每次有效输入时,是否进行提交.
		formatter.setCommitsOnValidEdit(isCommitsOnValidEdit());

		// 10 是否支持空值
		formatter.setSupportedEmpty(isSupportedEmpty());

		exConfigEditFormat(format);

		formatter.setFormat(format);
		return formatter;
	}

	//扩展NumberFormatterEx类，主要是为了重写stringToValue方法。原有的方法没有读取舍入方式
	public class FDCNumberFormatterEx extends NumberFormatterEx {
		/**
		 * 是否清零处理。
		 */
		private boolean isRemoveingZero = false;

		/** 精度 */
		private int precision = 0;

		/** 四舍五入 */
		private int roundingMode = BigDecimal.ROUND_HALF_UP;

		/** 是否支持空值 */
		private boolean isSupportedEmpty = false;

		private KDFormattedTextField ftf = null;

		public FDCNumberFormatterEx() {
			super();
		}

		public FDCNumberFormatterEx(NumberFormat format) {
			super(format);
		}

		public Object stringToValue(String text) throws ParseException {
			ParseException pEx = null;
			try {
				String newValue = text;

				char decimalSeparator = ((DecimalFormatEx) getFormat())
						.getDecimalFormatSymbols().getDecimalSeparator();// 小数点
				if (isRemoveingZero() && text != null && text.length() > 0
						&& text.indexOf(String.valueOf(decimalSeparator)) != -1) {
					newValue = new String(this.removeTrailZero(new StringBuffer(text)));// 使用国际化了的方法
				}

				// added by 苏志钦 2006-01-17, 用于判断当使用输入验证时，直接截掉字符串，不需要做四舍五入
				if (ftf != null
						&& ftf instanceof KDFormattedTextField
						&& ftf.getDataVerifierType() == KDFormattedTextField.INPUTING_VERIFIER) {
					newValue = repairingStrByPrecision(newValue,
							getDecimalPrecision());
				}

				Object obj = stringToValue2(newValue);
				try {
					return new BigDecimal(obj.toString()).setScale(
							getDecimalPrecision(), this.getRoundingMode());
				} catch (Exception e) {
					throw new ParseException("can not new a BigDecimal", 0);
				}
			} catch (ParseException ex) {
				pEx = ex;
			}
			// 若支持null时，则不扔出异常，以null值赋值。
			if (isSupportedEmpty() && text != null && text.trim().length() == 0) {
				return null;
			} else {
				// 否则扔出异常，一是阻止null，而是让用户捕获异常。
				throw pEx;
			}
		}

		/**
		 * 依照精度给字符串进行有效位的补充. 若字符串带有百分数，则不进行处理。比如：源2.123 精度是5 ，结果是2.12300
		 * 
		 * @param str
		 *            源字符串。
		 * @param precision
		 *            精度值。
		 * @return 符合有效位的字符串。
		 */
		private String repairingStrByPrecision(String str, int precision) {
			char decimalSeparator = ((DecimalFormatEx) getFormat())
					.getDecimalFormatSymbols().getDecimalSeparator();// 小数点
			char zero = ((DecimalFormatEx) getFormat())
					.getDecimalFormatSymbols().getZeroDigit();// 零
			if (str != null && str.length() > 0
					&& str.indexOf(String.valueOf(decimalSeparator)) != -1
					&& !StringUtil.isPercentStr(str)) {
				StringBuffer sb = new StringBuffer(20);
				// 精度只能在 0 - 10 之间。
				precision = precision < CtrlCommonConstant.PRECISION_MIN ? CtrlCommonConstant.PRECISION_MIN
						: precision;
				// 注释掉下面的操作，因为对BigDecimal的情况，KDFormattedTextField没有对其精度做限制，
				// 此处也不应做限制，否则会出现value与text不一致的情况
				// precision = precision > CtrlCommonConstant.PRECISION_MAX
				// ? CtrlCommonConstant.PRECISION_MAX
				// : precision;

				int dotIndex = str
						.lastIndexOf(String.valueOf(decimalSeparator));
				// 小数点前一部分,比如是 234.056 的 234
				String preStr = str.substring(0, dotIndex);

				// 小数点后一部分,比如是 234.056 的 056
				String nextStr = str.substring(dotIndex + 1);

				int nextStrLen = nextStr.length();

				if (nextStrLen == precision) {
					// 1 字符串的有效位 刚好等于精度范围
					return str;
				} else if (nextStrLen > precision) {
					// 字符串的有效位 大于 精度范围，应该截取多余的字母
					nextStr = nextStr.substring(0, precision);
					if (nextStr.length() > 0)
						str = sb.append(preStr).append(decimalSeparator)
								.append(nextStr).toString();
					else
						str = sb.append(preStr).toString();
				} else {
					// 字符串的有效位 小于 精度范围，应该补充零
					int interval = precision - nextStrLen;
					sb.append(str);
					for (int i = 0; i < interval; i++) {
						sb.append(zero);
					}
					str = sb.toString();
				}
			}
			return str;
		}

		private Object convertValueToValueClass2(Object value, Class valueClass) {
			if (valueClass != null && (value instanceof Number)) {
				if (valueClass == Integer.class) {
					return new Integer(((Number) value).intValue());
				} else if (valueClass == Long.class) {
					return new Long(((Number) value).longValue());
				} else if (valueClass == Float.class) {
					return new Float(((Number) value).floatValue());
				} else if (valueClass == Double.class) {
					return new Double(((Number) value).doubleValue());
				} else if (valueClass == Byte.class) {
					return new Byte(((Number) value).byteValue());
				} else if (valueClass == Short.class) {
					return new Short(((Number) value).shortValue());
				}
			}
			return value;
		}

		/**
		 * 移除结尾多余的零
		 * 
		 * @param sb
		 * @return
		 */
		private StringBuffer removeTrailZero(StringBuffer sb) {
			int dotIndex = sb.indexOf(".");
			if (dotIndex != -1) {// 当有小数点时
				int index = findNotZeroDigitLastIndex(sb, dotIndex);
				if (index != -1) // 小数点后找到非零数字字符
				{
					sb = sb.delete(index + 1, sb.length());
				} else
				// 小数点后找不到非零数字字符
				{
					sb = sb.delete(dotIndex, sb.length());
				}
			}
			return sb;
		}

		/**
		 * 查找小数点后最后一个不为零的数字位置
		 * 
		 * @param sb
		 * @param dotIndex
		 * @return -1 代表找不到非零的数字字符
		 */
		private int findNotZeroDigitLastIndex(StringBuffer sb, int dotIndex) {
			int lastIndex = -1;
			for (int i = dotIndex + 1; i < sb.length(); i++) {
				if (isNotZeroDigits(sb.charAt(i))) {
					lastIndex = i;
				}
			}
			return lastIndex;
		}

		/**
		 * 检验字符是否为非零的数字字符
		 * 
		 * @param c
		 * @return
		 */
		private boolean isNotZeroDigits(char c) {
			if (c == '1' || c == '2' || c == '3' || c == '4' || c == '5'
					|| c == '6' || c == '7' || c == '8' || c == '9') {
				return true;
			}
			return false;
		}

		/**
		 * 是否清零处理
		 */
		public boolean isRemoveingZero() {
			return isRemoveingZero;
		}

		/**
		 * 设置是否清零处理
		 */
		public void setRemoveingZero(boolean newValue) {
			isRemoveingZero = newValue;
		}

		/**
		 * 精度
		 */
		public void setDecimalPrecision(int newValue) {
			precision = newValue;
		}

		/** 精度 */
		public int getDecimalPrecision() {
			return precision;
		}

		/** 舍入方式 */
		public void setRoundingMode(int newValue) {
			roundingMode = newValue;
		}

		/** 舍入方式 */
		public int getRoundingMode() {
			return roundingMode;
		}

		/***/
		public void setSupportedEmpty(boolean newValue) {
			isSupportedEmpty = newValue;
		}

		/***/
		public boolean isSupportedEmpty() {
			return isSupportedEmpty;
		}
	}

}
