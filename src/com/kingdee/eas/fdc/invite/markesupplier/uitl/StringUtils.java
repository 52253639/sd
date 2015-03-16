package com.kingdee.eas.fdc.invite.markesupplier.uitl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * 常用的字符串操作函数。
 *
 * @see StreamBuffer
 * @author Taolei, Penglu
 * @version 1.0, 2000/08/15
 */
public class StringUtils
{
	/**
	 * 分割字符串。
	 * @param line      字符串
	 * @param seperator 分隔符
	 */
	public static String[] split(String line, int seperator)
	{
		if (line == null)
			return null;
		line = line.trim();
		if (line.length() == 0)
			return null;
		java.util.Vector v = new java.util.Vector();
		int i = 0, j;
		while ((j = line.indexOf(seperator, i)) >= 0)
		{
			v.addElement(line.substring(i, j).trim());
			i = j + 1;
		}
		v.addElement(line.substring(i).trim());
		int size = v.size();
		String[] ps = new String[size];
		if (size > 0)
			v.copyInto(ps);
		return ps;
	}

	/**
	 * 分割字符串。
	 * @param line      字符串
	 * @param seperator 分隔字符串
	 */
	public static String[] split(String line, String seperator)
	{
		if (line == null)
			return null;
		line = line.trim();
		if (line.length() == 0)
			return new String[] { "" }; // Modify by Ricky 2003.10.30
		java.util.Vector v = new java.util.Vector();
		int i = 0, j;
		while ((j = line.indexOf(seperator, i)) >= 0)
		{
			v.addElement(line.substring(i, j).trim());
			i = j + seperator.length();
		}
		v.addElement(line.substring(i).trim());
		int size = v.size();
		String[] ps = new String[size];
		if (size > 0)
			v.copyInto(ps);
		return ps;
	}

	/**
	 * 返回字符串，以固定长度格式表示长整数，这里的长度为8。
	 * @see StringUtils#fixNumber(long,int)
	 */
	public static String fixNumber(long n)
	{
		return fixNumber(n, 8);
	}

	/**
	 * 返回字符串，以固定长度格式表示长整数。
	 */
	public static String fixNumber(long n, int len)
	{
		char[] b = new char[len];
		for (int i = 0; i < len; i++)
			b[i] = '0';
		return new java.text.DecimalFormat(new String(b)).format(n);
	}

	/**
	 * 返回字符串，以固定长度格式表示整数，这里的长度为4。
	 * @see StringUtils#fixNumber(int,int)
	 */
	public static String fixNumber(int n)
	{
		return fixNumber((long) n, 4);
	}

	/**
	 * 返回字符串，以固定长度格式表示整数。
	 */
	public static String fixNumber(int n, int len)
	{
		return fixNumber((long) n, len);
	}

	/**
	 * 判断字符串是否为 null 或为空字符串。
	 */
	public static final boolean isEmpty(String s)
	{
		return (s == null || s.trim().length() == 0);
	}

	
	/**
	 * 判断两字符串是否严格相等（包括字符串为 null 为空字符串的情况）。
	 */
	public static boolean equals(String s1, String s2)
	{
		if (isEmpty(s1))
			return isEmpty(s2);
		return s1.equals(s2);

	}

	/**
	 * 判断两字符串是否相等（不区分大小写），包括字符串为 null 为空字符串的情况。
	 */
	public static boolean equalsIgnoreCase(String s1, String s2)
	{
		if (isEmpty(s1))
			return isEmpty(s2);
		return s1.equalsIgnoreCase(s2);
	}

	public static String numStr4Java(String s, char c, char c1)
	{
		if (s == null)
			return null;
		StringBuffer stringbuffer = new StringBuffer();
		for (int i = 0; i < s.length(); i++)
		{
			char c2 = s.charAt(i);
			if (c2 != c)
				if (c2 == c1)
					stringbuffer.append('.');
				else
					stringbuffer.append(c2);
		}

		return stringbuffer.toString();
	}

	public static String takeCommasFromNumStr(String s)
	{
		if (s == null)
			return null;
		if (s.indexOf(44) == -1)
			return s;
		else
			return numStr4Java(s, ',', '.');
	}

	/**
	 * 将 String 枚举转换成 String 数组
	 */
	public static String[] enumeration2StringArray(
		Enumeration enumeration,
		int i)
	{
		int j = 0;
		String as[] = new String[i];
		while (enumeration.hasMoreElements())
			as[j++] = (String) enumeration.nextElement();
		return as;
	}

	/**
	 * 将 char（双字节）数组转换成 byte 数组
	 */
	public static byte[] chars2bytes(char ac[])
	{
		byte abyte0[] = new byte[ac.length * 2];
		int i = 0;
		for (int j = 0; j < ac.length; j++)
		{
			char c = ac[j];
			char c1 = ac[j];
			abyte0[i++] = (byte) (c >> 8);
			abyte0[i++] = (byte) c1;
		}

		return abyte0;
	}

	/**
	 * 将 byte 数组转换成 char（双字节）数组
	 */
	public static char[] bytes2chars(byte abyte0[]) throws Exception
	{
		if (abyte0.length % 2 != 0)
			throw new Exception("Cannot convert an odd number of bytes");
		char ac[] = new char[abyte0.length / 2];
		int i = 0;
		for (int j = 0; j < ac.length; j++)
		{
			byte byte0 = abyte0[i++];
			byte byte1 = abyte0[i++];
			ac[j] = (char) (byte0 << 8 & 0xff00 | byte1 & 0xff);
		}

		return ac;
	}

	/**
	 * 在字符串后添加空格使之长度为指定长度
	 * @param int 指定长度
	 */
	public static String padStringWidth(String s, int i)
	{
		StringBuffer stringbuffer = null;
		if (s != null)
		{
			stringbuffer = new StringBuffer(s);
			stringbuffer.setLength(i);
			int j = s.length();
			for (int l = j; l < i; l++)
				stringbuffer.setCharAt(l, ' ');

		} else
		{
			stringbuffer = new StringBuffer(i);
			stringbuffer.setLength(i);
			for (int k = 0; k < i; k++)
				stringbuffer.setCharAt(k, ' ');

		}
		return stringbuffer.toString();
	}

	/**
	 * @see padStringWidth(String, int)
	 */
	public static String padStringWidth(int i, int j)
	{
		return padStringWidth(String.valueOf(i), j);
	}

	/**
	 * @see padStringWidth(String, int)
	 */
	public static String padStringWidth(float f, int i)
	{
		return padStringWidth(String.valueOf(f), i);
	}

	/**
	 * @see padStringWidth(String, int)
	 */
	public static String padStringWidth(long l, int i)
	{
		return padStringWidth(String.valueOf(l), i);
	}

	/**
	 * @see padStringWidth(String, int)
	 */
	public static String padStringWidth(double d, int i)
	{
		return padStringWidth(String.valueOf(d), i);
	}

	/**
	 * 将指定 long 值转化为16进制字符串格式
	 */
	public static String toHexString(long x, int chars)
	{
		char[] buf = new char[chars];
		for (int charPos = chars; --charPos >= 0;)
		{
			buf[charPos] = hexDigits[(int) (x & 0x0f)];
			x >>>= 4;
		}
		return new String(buf);
	}

	private static final char[] hexDigits =
		{
			'0',
			'1',
			'2',
			'3',
			'4',
			'5',
			'6',
			'7',
			'8',
			'9',
			'a',
			'b',
			'c',
			'd',
			'e',
			'f' };

	/**
	 * 转化 GB2312 编码的字符串至 Unicode 编码
	 */
	public static String GBToUnicode(String str)
	{
		try
		{
			return new String(str.getBytes("8859_1"), "GB2312");
		} catch (java.io.UnsupportedEncodingException e)
		{
			return null;
		}
	}

	/**
	 * 转化 Unicode 编码的字符串至 GB2312 编码
	 */
	public static String unicodeToGB(String str)
	{
		if (str == null || str.length() == 0)
			return str;

		try
		{
			return new String(str.getBytes("GB2312"), "8859_1");
		} catch (UnsupportedEncodingException e)
		{
			return null;
		}
	}

	/**
	 * 如果 obj 为 null，返回空字符串；否则返回 obj.toString()。
	 */
	public static String cnulls(Object obj)
	{
		return cnulls(obj, "");
	}

	/**
	 * 如果 obj 为 null，返回 defaultValue；否则返回 obj.toString()。
	 */
	public static String cnulls(Object obj, String defaultValue)
	{
		if (obj == null)
			return defaultValue;
		else
			return obj.toString();
	}

	/**
	 * 如果 str 为 null，返回defaultValue；否则返回 str。
	 */
	public static String cnulls(String str, String defaultValue)
	{
		if (str == null)
			return defaultValue;
		else
			return str;
	}

	/**
	 * 如果 str 为 null，返回空字符串；否则返回 str。
	 */
	public static String cnulls(String str)
	{
		if (str == null)
			return "";
		else
			return str;
	}

	/**
	 * 格式化 cy 为 “￥123,456.00”格式。
	 */
	public static String formatCurrency(double cy)
	{
		return NumberFormat.getCurrencyInstance().format(cy);
	}

	/**
	 * 格式化double值为“123,456.00”。
	 * @param int - 指定格式化的小数位数。
	 */
	public static String formatNumber(double d, int n)
	{
		String pattern = "#,##0";
		if (n > 0)
		{
			pattern += ".";
			for (int i = 0; i < n; i++)
				pattern += "0";
		}
		return new DecimalFormat(pattern).format(d);
	}

	public static int compareToIgnoreCase(String str1, String str2)
	{
		return (str1.toLowerCase().compareTo(str2.toLowerCase()));
	}

	public static boolean endsWithIgnoreCase(String str, String suffix)
	{
		return (str.toLowerCase().endsWith(suffix.toLowerCase()));
	}

	public static int indexOfIgnoreCase(String str, int find)
	{
		return (str.toLowerCase().indexOf(Character.toLowerCase((char) find)));
	}

	public static int indexOfIgnoreCase(String str, int find, int start)
	{
		return (
			str.toLowerCase().indexOf(
				Character.toLowerCase((char) find),
				start));
	}

	public static int indexOfIgnoreCase(String str, String find)
	{
		return (str.toLowerCase().indexOf(find.toLowerCase()));
	}

	public static int indexOfIgnoreCase(String str, String find, int start)
	{
		return (str.toLowerCase().indexOf(find.toLowerCase(), start));
	}

	public static int lastIndexOfIgnoreCase(String str, int find)
	{
		return (
			str.toLowerCase().lastIndexOf(Character.toLowerCase((char) find)));
	}

	public static int lastIndexOfIgnoreCase(String str, int find, int start)
	{
		return (
			str.toLowerCase().lastIndexOf(
				Character.toLowerCase((char) find),
				start));
	}

	public static int lastIndexOfIgnoreCase(String str, String find)
	{
		return (str.toLowerCase().lastIndexOf(find.toLowerCase()));
	}

	public static int lastIndexOfIgnoreCase(String str, String find, int start)
	{
		return (str.toLowerCase().lastIndexOf(find.toLowerCase(), start));
	}

	public static int occurencesOf(String str, char ch)
	{
		char[] s;
		int count;

		s = new char[str.length()];
		str.getChars(0, s.length, s, 0);
		count = 0;

		for (int i = 0; i < s.length; i++)
		{
			if (s[i] == ch)
			{
				count++;
			}
		}

		return (count);
	}

	public static int occurencesOfIgnoreCase(String str, char ch)
	{
		return (occurencesOf(str.toLowerCase(), Character.toLowerCase(ch)));
	}

	/**
	 * @param str
	 * @param oldChars
	 * @param newChars
	 * @return
	 */
	public static final String replace(
		String str,
		String oldChars,
		String newChars)
	{

		int len;
		int pos;
		int lastPos;

		len = newChars.length();
		pos = str.indexOf(oldChars);
		lastPos = pos;

		while (pos > -1)
		{
			String firstPart;
			String lastPart;

			firstPart = str.substring(0, pos);
			lastPart = str.substring(pos + oldChars.length(), str.length());
			str = firstPart + newChars + lastPart;
			lastPos = pos + len;
			pos = str.indexOf(oldChars, lastPos);
		}

		return (str);
	}

	public static String replaceIgnoreCase(
		String str,
		String oldChars,
		String newChars)
	{
		String lowerStr;
		int len;
		int pos;
		int lastPos;

		lowerStr = str.toLowerCase();
		len = newChars.length();
		pos = lowerStr.indexOf(oldChars);
		lastPos = pos;

		while (pos > -1)
		{
			String firstPart;
			String lastPart;

			firstPart = str.substring(0, pos);
			lastPart = str.substring(pos + oldChars.length(), str.length());
			str = firstPart + newChars + lastPart;
			lastPos = pos + len;
			
			// 由于lastPos是pos加上newChars的长度，因此，这一句应该改为：
			// 感谢 linhh 发现这一bug
			// pl 2005-4-4
			//pos = lowerStr.indexOf(oldChars, lastPos);
			
			pos = str.indexOf(oldChars, lastPos);
		}

		return (str);
	}

	public static boolean startsWithIgnoreCase(String str, String prefix)
	{
		return (str.toLowerCase().startsWith(prefix.toLowerCase()));
	}

	public static boolean startsWithIgnoreCase(
		String str,
		String prefix,
		int start)
	{
		return (str.toLowerCase().startsWith(prefix.toLowerCase(), start));
	}

	public static String stackToString(Throwable e)
	{
		try
		{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return "------\r\n" + sw.toString() + "------\r\n";
		} catch (Exception e2)
		{
			return "(bad stack2string) " + e.getMessage();
		}
	}

	public static String stackToString(Exception e)
	{
		try
		{
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return "------\r\n" + sw.toString() + "------\r\n";
		} catch (Exception e2)
		{
			return "(bad stack2string) " + e.getMessage();
		}
	}

	/**
	 * Returns a string of hexadecimal digits from a byte array. Each
	 * byte is converted to 2 hex symbols.
	 */
	public static String byteArrayToString(byte[] ba)
	{
		int length = ba.length;
		char[] buf = new char[length * 2];
		for (int i = 0, j = 0, k; i < length;)
		{
			k = ba[i++];
			buf[j++] = HEX_DIGITS[(k >>> 4) & 0x0F];
			buf[j++] = HEX_DIGITS[k & 0x0F];
		}
		return new String(buf);
	}

	private static final char[] HEX_DIGITS =
		{
			'0',
			'1',
			'2',
			'3',
			'4',
			'5',
			'6',
			'7',
			'8',
			'9',
			'A',
			'B',
			'C',
			'D',
			'E',
			'F' };

	public static byte[] hexFromString(String hex)
	{
		int len = hex.length();
		byte[] buf = new byte[((len + 1) / 2)];

		int i = 0, j = 0;
		if ((len % 2) == 1)
			buf[j++] = (byte) fromDigit(hex.charAt(i++));

		while (i < len)
		{
			buf[j++] =
				(byte) ((fromDigit(hex.charAt(i++)) << 4)
					| fromDigit(hex.charAt(i++)));
		}
		return buf;
	}

	/**
	 * Returns the number from 0 to 15 corresponding to the hex digit <i>ch</i>.
	 */
	public static int fromDigit(char ch)
	{
		if (ch >= '0' && ch <= '9')
			return ch - '0';
		if (ch >= 'A' && ch <= 'F')
			return ch - 'A' + 10;
		if (ch >= 'a' && ch <= 'f')
			return ch - 'a' + 10;

		throw new IllegalArgumentException("invalid hex digit '" + ch + "'");
	}

	/**
	 * 将输入String转换为不含XML特殊标记的String
	 */
	public static String escapeXMLstr(String input)
	{
		if (input == null)
			return null;

		StringBuffer output = new StringBuffer("");
		int len = input.length();
		for (int i = 0; i < len; i++)
		{
			char ch = input.charAt(i);
			if (ch == '&')
				output.append("&amp;");
			else if (ch == '<')
				output.append("&lt;");
			else if (ch == '>')
				output.append("&gt;");
			else if (ch == '\'')
				output.append("&apos;");
			else if (ch == '"')
				output.append("&quot;");
			else
				output.append(ch);
		}

		return output.toString();
	}

	/**
	 * 将输入String转换为不含HTML特殊标记的String
	 */
	public static String escapeHTMLstr(String input)
	{
		if (input == null)
			return null;

		StringBuffer output = new StringBuffer("");
		int len = input.length();
		for (int i = 0; i < len; i++)
		{
			char ch = input.charAt(i);
			if (ch == '&')
				output.append("&amp;");
			else if (ch == '<')
				output.append("&lt;");
			else if (ch == '>')
				output.append("&gt;");
			else if (ch == '"')
				output.append("&quot;");
			else
				output.append(ch);
		}

		return output.toString();
	}

	public static String headCharUpperCase(String str)
	{
		if (str == null || "".equals(str))
			return str;
		String headChar = str.substring(0, 1).toUpperCase();
		if (str.length() == 1)
			return headChar;
		return headChar + str.substring(1, str.length());
	}

	public static String headCharLowerCase(String str)
	{
		if (str == null || "".equals(str))
			return str;
		String headChar = str.substring(0, 1).toLowerCase();
		if (str.length() == 1)
			return headChar;
		return headChar + str.substring(1, str.length());
	}

	/**
	 * 将一个实现序列化接口的Object转换成以base64编码的String.
	 * @param obj
	 * @return
	 */
	public static String objToString(Object obj)
	{
		try
		{
			ByteArrayOutputStream byteOS = new ByteArrayOutputStream(2048);
			ObjectOutputStream objectOS = new ObjectOutputStream(byteOS);
			objectOS.writeObject(obj);
			objectOS.flush();
			ByteArrayInputStream byteIN =
				new ByteArrayInputStream(byteOS.toByteArray());
			ByteArrayOutputStream base64OS = new ByteArrayOutputStream(2048);
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			encoder.encodeBuffer(byteIN, base64OS);
			base64OS.flush();
			String base64 = new String(base64OS.toByteArray());
			return base64;
		} catch (Exception ex)
		{
			return null;
		}
	}
	/**
	 * 将一个以base64编码的String转换成一个Object.
	 * @param obj
	 * @return
	 */
	public static Object strToObject(String str)
	{
		try
		{
			ByteArrayInputStream byteIN =
				new ByteArrayInputStream(str.getBytes());
			ByteArrayOutputStream base64OS = new ByteArrayOutputStream(2048);
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			decoder.decodeBuffer(byteIN, base64OS);
			base64OS.flush();
			ObjectInputStream objectIN =
				new ObjectInputStream(
					new ByteArrayInputStream(base64OS.toByteArray()));
			Object obj = objectIN.readObject();
			return obj;
		} catch (Exception ex)
		{
			return null;
		}
	}

	public static String left(String str, int n)
	{
		return str.substring(str.length() - n);
	}

	public static String right(String str, int n)
	{
		return str.substring(str.length() - n, str.length());
	}

	private final static String[] JAVA_KEYWORDS =
		{
			"assert",
			"abstract",
			"continue",
			"for",
			"new",
			"switch",
			"boolean",
			"default",
			"goto",
			"null",
			"synchronized",
			"break",
			"do",
			"if",
			"package",
			"this",
			"byte",
			"double",
			"implements",
			"private",
			"threadsafe",
			"byvalue",
			"else",
			"import",
			"protected",
			"throw",
			"case",
			"extends",
			"instanceof",
			"public",
			"transient",
			"catch",
			"false",
			"int",
			"return",
			"true",
			"char",
			"final",
			"interface",
			"short",
			"try",
			"class",
			"finally",
			"long",
			"static",
			"void",
			"const",
			"float",
			"native",
			"super",
			"while",
			"volatile",
			"strictfp" };

	/**
	 * 是否合法的Java标识符
	 * @param s
	 * @return
	 */
	public static boolean isJavaIdentifier(String s)
	{
		if (StringUtils.isEmpty(s))
			return false;
		else
		{
			char[] chars = s.toCharArray();
			for (int i = 0; i < chars.length; i++)
			{
				if (!Character.isJavaIdentifierPart(chars[i])
					|| (i == 0 && !Character.isJavaIdentifierStart(chars[i])))
				{
					return false;
				}
			}
			if (Arrays.asList(JAVA_KEYWORDS).contains(s))
				return false;
		}
		return true;
	}

	/**
	 * 是否合法的Java Class Name 
	 * @param name
	 * @return
	 */
	public static boolean isJavaClassName(String name)
	{
		if (StringUtils.isEmpty(name))
			return true;
		if (name.startsWith(" ")
			|| name.startsWith(".")
			|| name.endsWith(" ")
			|| name.endsWith("."))
		{
			return false;
		} else
		{
			String[] pks = StringUtils.split(name, ".");
			for (int i = 0; i < pks.length; i++)
			{
				if (!StringUtils.isJavaIdentifier(pks[i]))
				{
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 是否合法的KSQL标识符的一部分
	 * @param s
	 * @return
	 */
	public static boolean isKsqlIdentifierPart(String s)
	{
		if (!StringUtils.isEmpty(s))
		{
			char[] chars = s.toCharArray();
			for (int i = 0; i < chars.length; i++)
				if (!Character.isJavaIdentifierPart(chars[i]))
					return false;
		}

		return true;
	}

	/**
	 * 是否合法的KSQL标识符
	 * @param s
	 * @return
	 */
	public static boolean isKsqlIdentifier(String s)
	{
		if (StringUtils.isEmpty(s))
			return false;
		else
		{
			char[] chars = s.toCharArray();
			for (int i = 0; i < chars.length; i++)
			{
				if (!Character.isJavaIdentifierPart(chars[i])
					|| (i == 0 && !Character.isJavaIdentifierStart(chars[i]))
					|| (i == 0 && chars[i] == '$'))
				{
					return false;
				}
			}
			// todo			
			//			if (KeyWord.instance().isKeyWord(s))
			//				return false;
		}
		return true;
	}

	/**
	 * 是否合法的文件名称
	 * @param s
	 * @return
	 */
	public static boolean isFileName(String name)
	{
		if (StringUtils.isEmpty(name)
			|| name.indexOf('<') > -1
			|| name.indexOf('*') > -1
			|| name.indexOf('/') > -1
			|| name.indexOf(':') > -1
			|| name.indexOf('?') > -1
			|| name.indexOf('\\') > -1
			|| name.indexOf('\"') > -1)
			return false;

		return true;
	}

	/**
	 * 数组转换为字符串
	 * @param objArray 数组
	 * @param operator 分隔符
	 * @return 字符串
	 */
	public static String arrayToString(Object[] objArray, String operator)
	{

		if (objArray != null && objArray.length > 0)
		{

			StringBuffer sb = new StringBuffer();

			for (int i = 0; i < objArray.length; i++)
			{
				sb.append(objArray[i] + operator);
			}

			if (operator != null && operator.length() > 0)
			{

				if (sb.indexOf(operator) == 0)
				{ //以operator开头
					sb.delete(0, operator.length());
				} else if (
					sb.lastIndexOf(operator)
						== (sb.length() - operator.length()))
				{
					//以operator结尾
					sb.delete(sb.length() - operator.length(), sb.length());
				}
			}

			return sb.toString();
		}

		return null;
	}

	/**
	 * 字符串数组添加
	 * @param array 原数组
	 * @param appendObj 附加对象
	 * @return 附加后的数组
	 */
	public static String[] stringArrayAppend(String[] array, String appendObj)
	{

		if (array == null)
		{
			array = new String[0];
		}

		String[] newArray = new String[array.length + 1];

		System.arraycopy(array, 0, newArray, 0, array.length);

		newArray[newArray.length - 1] = appendObj;

		return newArray;
	}

	/**
	 * Trim
	 * @param str
	 * @return
	 */
	public static String trim(String str)
	{
		return str != null ? str.trim() : null;
	}

	//add by wanx
	//2004-07-30
	/**
	 * 给指定的字符串两边加上括号
	 * @param str
	 * @return
	 * String
	 */
	public static String addBracket(String str)
	{
		if (isEmpty(str))
		{
			return str;
		}
		return "(" + str + ")";
	}

	/**
	 * 得出缩写词。
	 * 
	 * @param txt 使用首字母大写来分隔的英文单词词组，形如：ExpenseReqBill
	 * @param len 预期的缩写词长度
	 * @author pl
	 */
	public static String abbreviate(String txt, int len)
	{
		String result = "";
		
		String[] str = splitByUpperCaseChar(txt);
		if (str.length<len) {
			String abb = metaphone(str[0], len - str.length + 1);
            // 处理metaphone算法得出0长度字符串的场景
            if (abb.length()==0) {
                abb = str[0].substring(0, len - str.length + 1).toUpperCase();
            } 
            // end pl

            abb = abb.substring(0, 1) + abb.substring(1).toLowerCase();		
			result += abb;
			for (int i=1; i<str.length; i++)
				result += str[i].charAt(0);
		}
		else
		{			
			for (int i=0; i<len; i++)
				result += str[i].charAt(0);
		}
		
		return result;
	}
	
	/**
	 * Five values in the English language 
	 */
	private static String VOWELS = "AEIOU" ;

	/**
	 * Variable used in Metaphone algorithm
	 */
	private static String FRONTV = "EIY"   ;

	/**
	 * Variable used in Metaphone algorithm
	 */
	private static String VARSON = "CSPTG" ;

	/**
	 * 得出metaphone码。使用变形metaphone算法。
	 * 
	 * Limitations: Input format is expected to be a single ASCII word
	 * with only characters in the A - Z, a - z range, no punctuation or numbers.
	 *
	 * @param txt String to find the metaphone code for
	 * @return A metaphone code corresponding to the String supplied
	 */
	private static String metaphone(String txt, int maxCodeLen) {
		if ((txt == null) || (txt.length() == 0)) {
			return "" ;
		}
		
		if (maxCodeLen <= 0)
			return "";
		else if (maxCodeLen > txt.length())
			return txt;
		
		// single character is itself
		if (txt.length() == 1) {
			return txt.toUpperCase() ;
		}
      
		boolean hard = false ;
		char[] inwd = txt.toUpperCase().toCharArray() ;
      
		StringBuffer local = new StringBuffer(); // manipulate
		StringBuffer code = new StringBuffer() ; //   output
		// handle initial 2 characters exceptions
		switch(inwd[0]) {
		case 'K' : 
		case 'G' : 
		case 'P' : /* looking for KN, etc*/
			if (inwd[1] == 'N') {
				local.append(inwd, 1, inwd.length - 1);
			} else {
				local.append(inwd);
			}
			break;
		case 'A': /* looking for AE */
			if (inwd[1] == 'E') {
				local.append(inwd, 1, inwd.length - 1);
			} else {
				local.append(inwd);
			}
			break;
		case 'W' : /* looking for WR or WH */
			if (inwd[1] == 'R') {   // WR -> R
				local.append(inwd, 1, inwd.length - 1); 
				break ;
			}
			if (inwd[1] == 'H') {
				local.append(inwd, 1, inwd.length - 1);
				local.setCharAt(0, 'W'); // WH -> W
			} else {
				local.append(inwd);
			}
			break;
		case 'X' : /* initial X becomes S */
			inwd[0] = 'S';
			local.append(inwd);
			break ;
		default :
			local.append(inwd);
		} // now local has working string with initials fixed

		int wdsz = local.length();
		int n = 0 ;

		while ((code.length() < maxCodeLen) && 
			   (n < wdsz) ) { // max code size of 4 works well
			char symb = local.charAt(n) ;
			// remove duplicate letters except C
			if ((symb != 'C') && (isPreviousChar( local, n, symb )) ) {
				n++ ;
			} else { // not dup
				switch(symb) {
				case 'A' : case 'E' : case 'I' : case 'O' : case 'U' :
					if (n == 0) { 
						code.append(symb);
					}
					break ; // only use vowel if leading char
				case 'B' :
					if ( isPreviousChar(local, n, 'M') && 
						 isLastChar(wdsz, n) ) { // B is silent if word ends in MB
						break;
					}
					code.append(symb);
					break;
				case 'C' : // lots of C special cases
					/* discard if SCI, SCE or SCY */
					if ( isPreviousChar(local, n, 'S') && 
						 !isLastChar(wdsz, n) && 
						 (FRONTV.indexOf(local.charAt(n + 1)) >= 0) ) { 
						break;
					}
					if (regionMatch(local, n, "CIA")) { // "CIA" -> X
						code.append('X'); 
						break;
					}
					if (!isLastChar(wdsz, n) && 
						(FRONTV.indexOf(local.charAt(n + 1)) >= 0)) {
						code.append('S');
						break; // CI,CE,CY -> S
					}
					if (isPreviousChar(local, n, 'S') &&
						isNextChar(local, n, 'H') ) { // SCH->sk
						code.append('K') ; 
						break ;
					}
					if (isNextChar(local, n, 'H')) { // detect CH
						if ((n == 0) && 
							(wdsz >= 3) && 
							isVowel(local,2) ) { // CH consonant -> K consonant
							code.append('K');
						} else { 
							code.append('X'); // CHvowel -> X
						}
					} else { 
						code.append('C');
					}
					break ;
				case 'D' :
					if (!isLastChar(wdsz, n + 1) && 
						isNextChar(local, n, 'G') && 
						(FRONTV.indexOf(local.charAt(n + 2)) >= 0)) { // DGE DGI DGY -> J 
						code.append('J'); n += 2 ;
					} else { 
						code.append('D');
					}
					break ;
				case 'G' : // GH silent at end or before consonant
					if (isLastChar(wdsz, n + 1) && 
						isNextChar(local, n, 'H')) {
						break;
					}
					if (!isLastChar(wdsz, n + 1) &&  
						isNextChar(local,n,'H') && 
						!isVowel(local,n+2)) {
						break;
					}
					if ((n > 0) && 
						( regionMatch(local, n, "GN") ||
						  regionMatch(local, n, "GNED") ) ) {
						break; // silent G
					}
					if (isPreviousChar(local, n, 'G')) {
						hard = true ;
					} else {
						hard = false ;
					}
					if (!isLastChar(wdsz, n) && 
						(FRONTV.indexOf(local.charAt(n + 1)) >= 0) && 
						(!hard)) {
						code.append('J');
					} else {
						code.append('G');
					}
					break ;
				case 'H':
					if (isLastChar(wdsz, n)) {
						break ; // terminal H
					}
					if ((n > 0) && 
						(VARSON.indexOf(local.charAt(n - 1)) >= 0)) {
						break;
					}
					if (isVowel(local,n+1)) {
						code.append('H'); // Hvowel
					}
					break;
				//数字优先 modify by ljc 2006-04-11 begin 
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
				//数字优先 modify by ljc 2006-04-11 end 					
				case 'F': 
				case 'J' : 
				case 'L' :
				case 'M': 
				case 'N' : 
				case 'R' :
					code.append(symb); 
					break;
				case 'K' :
					if (n > 0) { // not initial
						if (!isPreviousChar(local, n, 'C')) {
							code.append(symb);
						}
					} else {
						code.append(symb); // initial K
					}
					break ;
				case 'P' :
//					if (isNextChar(local,n,'H')) {
//						// PH -> F
//						code.append('F');
//					}
//					else {
//						code.append(symb);
//					}
//					break ;
					code.append(symb);					
					break ;
				case 'Q' :
					code.append('Q');
					break;
				case 'S' :
					if (regionMatch(local,n,"SH") || 
						regionMatch(local,n,"SIO") || 
						regionMatch(local,n,"SIA")) {
						code.append('X');
					} else {
						code.append('S');
					}
					break;
				case 'T' :
					if (regionMatch(local,n,"TIA") || 
						regionMatch(local,n,"TIO")) {
						code.append('X'); 
						break;
					}
					if (regionMatch(local,n,"TCH")) {
						// Silent if in "TCH"
						break;
					}
					// substitute numeral 0 for TH (resembles theta after all)
					if (regionMatch(local,n,"TH")) {
						//code.append('0');
					} else {
						code.append('T');
					}
					break ;
				case 'V' :
					code.append('V'); break ;
				case 'W' : case 'Y' : // silent if not followed by vowel
					if (!isLastChar(wdsz,n) && 
						isVowel(local,n+1)) {
						code.append(symb);
					}
					break ;
				case 'X' :
					//code.append('K'); code.append('S');
					code.append('X');
					break;
				case 'Z' :
					code.append('Z'); break ;
				} // end switch
				n++ ;
			} // end else from symb != 'C'
			if (code.length() > maxCodeLen) { 
				code.setLength(maxCodeLen); 
			}
		}
		return code.toString();
	}

	private static String[] splitByUpperCaseChar(String line)
	{
		if (line == null)
			return null;
		line = line.trim();
		if (line.length() == 0)
			return new String[] { "" };
			
		java.util.Vector v = new java.util.Vector();
		int pos = 0;
		for (int i=0; i<line.length(); i++)
		{
			char ch = line.charAt(i);
			//处理数字优先 modify by ljc
			if (ch>='A' && ch<='Z' || ch>='0' && ch<='9')
			{
				String str = line.substring(pos, i).trim();
				if (str.length()>0)  
					v.addElement(str);
				pos = i;
			}
		}
		v.addElement(line.substring(pos).trim());
		
		int size = v.size();
		String[] words = new String[size];
		if (size > 0)
			v.copyInto(words);
		return words;
	}
	
	private static final boolean isVowel(StringBuffer string, int index) {
		return (VOWELS.indexOf(string.charAt(index)) >= 0);
	}

	private static boolean isPreviousChar(StringBuffer string, int index, char c) {
		boolean matches = false;
		if( index > 0 &&
			index < string.length() ) {
			matches = string.charAt(index - 1) == c;
		}
		return matches;
	}

	private static boolean isNextChar(StringBuffer string, int index, char c) {
		boolean matches = false;
		if( index >= 0 &&
			index < string.length() - 1 ) {
			matches = string.charAt(index + 1) == c;
		}
		return matches;
	}

	private static boolean regionMatch(StringBuffer string, int index, String test) {
		boolean matches = false;
		if( index >= 0 &&
			(index + test.length() - 1) < string.length() ) {
			String substring = string.substring( index, index + test.length());
			matches = substring.equals( test );
		}
		return matches;
	}

	private static final boolean isLastChar(int wdsz, int n) {
		return n + 1 == wdsz;
	}
}