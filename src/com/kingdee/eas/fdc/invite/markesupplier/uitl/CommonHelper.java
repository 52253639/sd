package com.kingdee.eas.fdc.invite.markesupplier.uitl;

import java.math.BigDecimal;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collection;

import javax.swing.ImageIcon;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.CoreBaseInfo;

public class CommonHelper {
	
	public static final BigDecimal BIGZERO = new BigDecimal("0.0");
    public static final int TRUE = 1;
    public static final int FALSE = 0;
    
    
    /**
	 * 获取文件资源路径（设置按钮自定义图标） 
	 * java.net.URL url = ClassUtil.getResource("com/kingdee/eas/custom/images/search.gif", MaterialInfoBaseEditUI.class);
     * ImageIcon icon = new ImageIcon(url);
     * this.wbtnContract.setIcon(icon);
	 * @return boolean, 返回true,Ascill字符
	 */
    public static URL getResource(final String resourceName,
			final Class callingClass) {
		URL url = null;
		try {
			url = (URL) AccessController.doPrivileged(new PrivilegedAction() {
				public Object run() {
					return Thread.currentThread().getContextClassLoader()
							.getResource(resourceName);
				}
			});
			if (url == null) {
				url = (URL) AccessController
						.doPrivileged(new PrivilegedAction() {
							public Object run() {
								return CommonHelper.class.getClassLoader()
										.getResource(resourceName);
							}
						});

			}
			if (url == null) {
				url = (URL) AccessController
						.doPrivileged(new PrivilegedAction() {
							public Object run() {
								return callingClass.getClassLoader()
										.getResource(resourceName);
							}
						});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	/**
	 * 判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符）
	 * 
	 * @param char c, 需要判断的字符
	 * @return boolean, 返回true,Ascill字符
	 */
    private static boolean isLetter(char c) {
		int k = 0x80;
		return c / k == 0 ? true : false;
	}
	/**
	 *判断是否为空
	 * */
	public static boolean isEmpty(Object param[])
    {
		return param == null || param.length == 0 || isEmpty(param[0]);
    }

    public static boolean isEmpty(String s)
    {
    	return s == null || s.trim().length() == 0;
    }

    public static boolean isEmpty(Object o)
    {
    	if(o instanceof String)
    		return o == null || o.toString().trim().length() == 0;
    	else
    		return o == null;
    }

    public static boolean isEmpty(Collection coll)
    {
    	return coll == null || coll.isEmpty();
    }

    public static boolean isEmpty(AbstractObjectCollection coll)
    {
    	return coll == null || coll.isEmpty();
    }

    public static boolean isZERO(BigDecimal o)
    {
    	return o == null || o.compareTo(FMConstants.ZERO) == 0;
    }

    public static boolean isEmpty(CoreBaseInfo info)
    {
    	return info == null || isEmpty(info.getId());
    }
    /**
	  * 将数字转换为英文大写格式．
	  * @param x
	  * @return　英文大写格式
	  */
	 public static String parse(String x) {
	     int z = x.indexOf("."); // 取小数点位置
	     String lstr = "", rstr = "";
	     if (z > -1) { // 看是否有小数，如果有，则分别取左边和右边
	      lstr = x.substring(0, z);
	      rstr = x.substring(z + 1);
	      } else // 否则就是全部
	      lstr = x;

	     String lstrrev = reverse(lstr); // 对左边的字串取反
	     String[] a = new String[5]; // 定义5个字串变量来存放解析出来的叁位一组的字串

	     switch (lstrrev.length() % 3) {
	      case 1 :
	       lstrrev += "00";
	       break;
	      case 2 :
	       lstrrev += "0";
	       break;
	      }
	     String lm = ""; // 用来存放转换后的整数部分
	     for (int i = 0; i < lstrrev.length() / 3; i++) {
	      a[i] = reverse(lstrrev.substring(3 * i, 3 * i + 3)); // 截取第一个叁位
	      if (!a[i].equals("000")) { // 用来避免这种情况：1000000 = one million thousand only
	       if (i != 0){  
	         
	        lm = transThree(a[i]) + " " + parseMore(String.valueOf(i)) + " " + lm; // 加: thousand、million、billion
	       }
	       else if(Integer.parseInt(a[i]) <  100 && lstrrev.length() > 3){
	               //判断个,十,百三位小于100 且要转换的数字大于1000时要加AND 
	           // 如:1001 应该为:ONE THOUSAND AND ONE ONLY 要加AND 而不是ONE THOUSAND ONE ONLY
	             lm = "AND " + transThree(a[i]); 
	            }else{
	             lm = transThree(a[i]); // 防止i=0时， 在多加两个空格.
	            }
	       } else
	       lm += transThree(a[i]);
	      }

	     String xs = ""; // 用来存放转换后小数部分
	     if (z > -1)
	      xs = "AND " + transTwo(rstr) + " CENT(S) "; // 小数部分存在时转换小数

	     return "SAY USD "+ lm.trim() + " " + xs ;//+ "ONLY";
	     }

	 private static String parseFirst(String s) {
	     String[] a =
	      new String[] { "", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE" };
	     return a[Integer.parseInt(s.substring(s.length() - 1))];
	     }

	 private static String parseTeen(String s) {
	     String[] a =
	      new String[] {
	       "TEN",
	       "ELEVEN",
	       "TWELEVE",
	       "THIRTEEN",
	       "FOURTEEN",
	       "FIFTEEN",
	       "SIXTEEN",
	       "SEVENTEEN",
	       "EIGHTEEN",
	       "NINETEEN" };
	     return a[Integer.parseInt(s) - 10];
	     }

	 private static String parseTen(String s) {
	     String[] a =
	      new String[] {
	       "TEN",
	       "TWENTY",
	       "THIRTY",
	       "FORTY",
	       "FIFTY",
	       "SIXTY",
	       "SEVENTY",
	       "EIGHTY",
	       "NINETY" };
	     return a[Integer.parseInt(s.substring(0, 1)) - 1];
	     }

	//  两位
	 private static String transTwo(String s) {
	     String value = "";
	     // 判断位数
	     if (s.length() > 2)
	      s = s.substring(0, 2);
	     else if (s.length() < 2)
	      s = "0" + s;

	     if (s.startsWith("0")) // 07 - seven 是否小於10
	      value = parseFirst(s);
	     else if (s.startsWith("1")) // 17 seventeen 是否在10和20之间
	      value = parseTeen(s);
	     else if (s.endsWith("0")) // 是否在10与100之间的能被10整除的数
	      value = parseTen(s);
	     else
	      value = parseTen(s) + " " + parseFirst(s);
	     return value;
	     }

	 private static String parseMore(String s) {
	     String[] a = new String[] { "", "THOUSAND", "MILLION", "BILLION" };
	     return a[Integer.parseInt(s)];
	     }

	//  制作叁位的数
	//  s.length = 3
	 private static String transThree(String s) {
	     String value = "";
	     if (s.startsWith("0")) // 是否小於100
	      value = transTwo(s.substring(1));
	     else if (s.substring(1).equals("00")) // 是否被100整除
	      value = parseFirst(s.substring(0, 1)) + " HUNDRED";
	     else
	      value = parseFirst(s.substring(0, 1)) + " HUNDRED AND " + transTwo(s.substring(1));
	     return value;
	     }

	 /**
	  * 将数字进行反转
	  * @param s
	  * @return 反转后的数字
	  */
	 private static String reverse(String s) {
	     char[] aChr = s.toCharArray();
	     StringBuffer tmp = new StringBuffer();
	     for (int i = aChr.length - 1; i >= 0; i--) {
	      tmp.append(aChr[i]);
	      }
	     return tmp.toString();
	 }
	 
	 /** 
		 * --------将数字转换成中文金额的大写形式调用示例------------
		 *  例如 123.45 --> 壹佰贰拾叁元肆角伍分
		 *  TransRMB t2r = new TransRMB();
		 *  String s = t2r.cleanZero(t2r.splitNum(t2r.roundString("")));
		 */  

		
	    /** 
	     * 判断用户输入的数据是否合法，用户只能输入大于零的数字，不能输入其它字符 
	     * @param s String 
	     * @return 如果用户输入数据合法，返回 true，否则返回 false 
	     */  
	    public boolean checkNum(String s) {  
	        // 如果用户输入的数里有非数字字符，则视为非法数据，返回 false  
	        try {  
	            float f = Float.valueOf(s);  
	            // 如果这个数小于零则视为非法数据，返回 false  
	            if(f < 0) {  
	                System.out.println("非法数据，请检查！");  
	                return false;  
	            }else {  
	                return true;  
	            }  
	        } catch (NumberFormatException e) {  
	            System.out.println("非法数据，请检查！");  
	            return false;  
	        }     
	    }  
	      
	    /** 
	     * 把用户输入的数以小数点为界分割开来，并调用 numFormat() 方法 
	     * 进行相应的中文金额大写形式的转换 
	     * 注：传入的这个数应该是经过 roundString() 方法进行了四舍五入操作的 
	     * @param s String 
	     * @return 转换好的中文金额大写形式的字符串 
	     */  
	    public static String splitNum(String s) {  
	        // 如果传入的是空串则继续返回空串  
	        if("".equals(s)) {  
	            return "";  
	        }  
	        // 以小数点为界分割这个字符串  
	        int index = s.indexOf(".");  
	        // 截取并转换这个数的整数部分  
	        String intOnly = s.substring(0, index);  
	        String part1 = numFormat(1, intOnly);  
	        // 截取并转换这个数的小数部分  
	        String smallOnly = s.substring(index + 1);  
	        String part2 = numFormat(2, smallOnly);  
	        // 把转换好了的整数部分和小数部分重新拼凑一个新的字符串  
	        String newS = part1 + part2;  
	        return newS;  
	    }  
	          
	    /** 
	     * 对传入的数进行四舍五入操作 
	     * 并且转换成大写金额
	     * @param s String 从命令行输入的那个数 
	     * @return 四舍五入后的新值 
	     */  
	    public static String roundString(String s) {  
	        // 如果传入的是空串则继续返回空串  
	        if("".equals(s)) {  
	            return "";  
	        }  
	        // 将这个数转换成 double 类型，并对其进行四舍五入操作  
	        double d = Double.parseDouble(s);  
	        // 此操作作用在小数点后两位上  
	        d = (d * 100 + 0.5) / 100;  
	        // 将 d 进行格式化  
	        s = new java.text.DecimalFormat("##0.000").format(d);  
	        // 以小数点为界分割这个字符串  
	        int index = s.indexOf(".");  
	        // 这个数的整数部分  
	        String intOnly = s.substring(0, index);  
	        // 规定数值的最大长度只能到万亿单位，否则返回 "0"  
	        if(intOnly.length() > 13) {  
	            System.out.println("输入数据过大！（整数部分最多13位！）");  
	            return "";  
	        }  
	        // 这个数的小数部分  
	        String smallOnly = s.substring(index + 1);  
	        // 如果小数部分大于两位，只截取小数点后两位  
	        if(smallOnly.length() > 2) {  
	            String roundSmall = smallOnly.substring(0, 2);  
	            // 把整数部分和新截取的小数部分重新拼凑这个字符串  
	            s = intOnly + "." + roundSmall;  
	        }  
	        s = splitNum(s);
	        s = cleanZero(s);
	        return s;  
	    }  
	      
	    /** 
	     * 把传入的数转换为中文金额大写形式 
	     * @param flag int 标志位，1 表示转换整数部分，0 表示转换小数部分 
	     * @param s String 要转换的字符串 
	     * @return 转换好的带单位的中文金额大写形式 
	     */  
	    public static String numFormat(int flag, String s) {  
	        int sLength = s.length();  
	        // 货币大写形式  
	        String bigLetter[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};  
	        // 货币单位  
	        String unit[] = {"元", "拾", "佰", "仟", "万",   
	                // 拾万位到仟万位  
	                "拾", "佰", "仟",  
	                // 亿位到万亿位  
	                "亿", "拾", "佰", "仟", "万"};  
	        String small[] = {"分", "角"};  
	        // 用来存放转换后的新字符串  
	        String newS = "";  
	        // 逐位替换为中文大写形式  
	        for(int i = 0; i < sLength; i ++) {  
	            if(flag == 1) {  
	                // 转换整数部分为中文大写形式（带单位）  
	                newS = newS + bigLetter[s.charAt(i) - 48] + unit[sLength - i - 1];  
	            } else if(flag == 2) {  
	                // 转换小数部分（带单位）  
	                newS = newS + bigLetter[s.charAt(i) - 48] + small[sLength - i - 1];  
	            }  
	        }  
	        return newS;  
	    }  
	      
	    /** 
	     * 把已经转换好的中文金额大写形式加以改进，清理这个字 
	     * 符串里面多余的零，让这个字符串变得更加可观 
	     * 注：传入的这个数应该是经过 splitNum() 方法进行处理，这个字 
	     * 符串应该已经是用中文金额大写形式表示的 
	     * @param s String 已经转换好的字符串 
	     * @return 改进后的字符串 
	     */  
	    public static String cleanZero(String s) {  
	        // 如果传入的是空串则继续返回空串  
	        if("".equals(s)) {  
	            return "";  
	        }  
	        // 如果用户开始输入了很多 0 去掉字符串前面多余的'零'，使其看上去更符合习惯  
	        while(s.charAt(0) == '零') {  
	            // 将字符串中的 "零" 和它对应的单位去掉  
	            s = s.substring(2);  
	            // 如果用户当初输入的时候只输入了 0，则只返回一个 "零"  
	            if(s.length() == 0) {  
	                return "零";  
	            }  
	        }  
	        // 字符串中存在多个'零'在一起的时候只读出一个'零'，并省略多余的单位  
	        /* 由于本人对算法的研究太菜了，只能用4个正则表达式去转换了，各位大虾别介意哈... */  
	        String regex1[] = {"零仟", "零佰", "零拾"};  
	        String regex2[] = {"零亿", "零万", "零元"};  
	        String regex3[] = {"亿", "万", "元"};  
	        String regex4[] = {"零角", "零分"};  
	        // 第一轮转换把 "零仟", 零佰","零拾"等字符串替换成一个"零"  
	        for(int i = 0; i < 3; i ++) {  
	            s = s.replaceAll(regex1[i], "零");  
	        }  
	        // 第二轮转换考虑 "零亿","零万","零元"等情况  
	        // "亿","万","元"这些单位有些情况是不能省的，需要保留下来  
	        for(int i = 0; i < 3; i ++) {  
	            // 当第一轮转换过后有可能有很多个零叠在一起  
	            // 要把很多个重复的零变成一个零  
	            s = s.replaceAll("零零零", "零");  
	            s = s.replaceAll("零零", "零");  
	            s = s.replaceAll(regex2[i], regex3[i]);  
	        }  
	        // 第三轮转换把"零角","零分"字符串省略  
	        for(int i = 0; i < 2; i ++) {  
	            s = s.replaceAll(regex4[i], "");  
	        }  
	        // 当"万"到"亿"之间全部是"零"的时候，忽略"亿万"单位，只保留一个"亿"  
	        s = s.replaceAll("亿万", "亿");  
	        return s;  
	    } 
}
