/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有<p>		 	
 * 描述：		                              
 *		
 * @author		车忠伟
 * @version		EAS7.0		
 * @createDate	2011-8-29
 * @see						
 */
package com.kingdee.eas.fdc.schedule.framework;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.kingdee.bos.Context;
import com.kingdee.eas.base.usermonitor.UserMonitorFactory;
import com.kingdee.eas.common.EASBizException;



/**
 * 描述: 日期处理工具，用于对日期的获取、比较、计算
 * 
 * @author daij  date:2006-11-27   修改时间: 2010-03-12 run_zheng<p> 
 * @version EAS5.2.0
 */
public final class DateUtils {
	
	public static final int MILLIS_OF_DAY = 86400000;
	
	public static final String TIME_FORMAT_STR = "HH:mm:ss";
	public static final String SIMPLE_FORMAT_STR = "yyyy-MM-dd";
	public static final String DETAIL_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
	public static final String ALLFIELD_FORMAT_STR = "yyyy-MM-dd HH:mm:ss.SS";
	//简单日期格式化工具
	public static final SimpleDateFormat SIMPLE_FORMAT = new SimpleDateFormat(SIMPLE_FORMAT_STR);
	public static final SimpleDateFormat DETAIL_FORMAT = new SimpleDateFormat(DETAIL_FORMAT_STR);

////////////////////////////////////////Date相关的方法//////////////////////////////////////////////
	/**
	 * 描述：将Date加N个月，多使用于失效日期处理
	 * EXAMPLE: date = 2006-11-29  dateAddMonth(date, 1); date = 2006-12-29
	 * 
	 * @param Date 如果为Null，则返回当前日期加上N个月.
	 * @param months  正数为Date加上months个月，负数为Date减去months个月
	 * @return java.util.Date 
	 * @author:daij  
	 * 创建时间：2006-11-29  修改时间: 2010-03-12 run_zheng<p> 
	 */	
	public static Date addMonth(Date date,int months){
		return fromDefNow(date).addMonth(months).date();
	}
	
	/**
	 * 描述：将Date加N天
	 * Example: date = 2006-11-29  dateAddDay(date, 1); date = 2006-11-30
	 * 
	 * @param Date 如果为Null，则返回当前日期加上N天.
	 * @param days  正数为Date加上days个月，负数为Date减去days个月
	 * @return java.util.Date   修改时间: 2010-03-12 run_zheng<p> 
	 */	
	public static Date addDay(Date date,int days){
		return fromDefNow(date).addDay(days).date();
	}
	
	/**
	 * 描述：增加日期对应属性的值。
	 * @param date  源日期
	 * @param field 增长的属性，  如:Calendar.DATE,具体参考Calendar
	 * @param amount 增长量
	 * @return 增长后的日期
	 * @author tjw
	 * 创建时间：2009-09-14  修改时间: 2010-03-12 run_zheng<p> 
	 */
	public static Date addDateField(Date date,int field,int amount){
		if(date == null) date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(field, amount);
		return (c.getTime());
	}
	

	
	/**
	 * 描述：某天的开始时间，yyyy-MM-dd 00:00:00.000
	 * 		date为空，默认为当前日期
	 * @param date java.util.Date 源日期
	 * @return 目标日期
	 * @author:daij
	 * 创建时间：2006-11-27  修改时间: 2010-03-12 run_zheng<p> 
	 */
	public static Date startOfDay(Date date){
		return fromDefNow(date).startOfDay().date();
	}
	
	/**
	 *描述：某天的结束时间，yyyy-MM-dd 23:59:59.999
	 *		date为空，默认为当前日期
	 * @param date java.util.Date 源日期
	 * @return 目标日期
	 */
	public static Date endOfDay(Date date){
		return fromDefNow(date).endOfDay().date();
	}
	
	/**
	 *描述：以某天为基准的所在月第一天的开始时间，yyyy-MM-01 00:00:00.000
	 *		date为空，默认为当前日期
	 * @param date 源日期
	 * @return  目标日期
	 */
	public static Date startOfMonth(Date date){
		return fromDefNow(date).startOfMonth().date();
	}
	
	/**
	 *描述： 以某天为基准的所在月第一天的结束时间，yyyy-MM-XX 23:59:59.999，
	 * 		 其中XX为所在月的最后一天。date为空，默认为当前日期
	 * @param date 源日期
	 * @return	目标日期
	 */
	public static Date endOfMonth(Date date){
		return fromDefNow(date).endOfMonth().date();
	}
	
	/**
	 * 描述：返回精确到天的日期,显示格式为yyyy-MM-dd的日期
	 * 		date为空，默认为当前日期
	 * @param date java.util.Date 源日期
	 * @return String 按yyyy-MM-dd格式化后的日期.
	 * @author:daij
	 * 创建时间：2009-8-8 修改时间: 2010-03-12 run_zheng<p> 
	 */
	public static String simpleDateString(Date date) {
		return fromDefNow(date).startOfDay().simpleString();
	}
	/**
	 * 描述：返回精确到天的日期,显示格式为yyyy-MM-dd HH:mm:ss的日期
	 * 		date为空，默认为当前日期
	 * @param date java.util.Date 源日期
	 * @return String 按yyyy-MM-dd HH:mm:ss格式化后的日期.
	 * @author:tjw
	 * 创建时间：2010-6-23 
	 */
	public static String detailDateString(Date date){
		SimpleDateFormat formater = new SimpleDateFormat(DETAIL_FORMAT_STR);
		return formater.format(date);
	}
    
    /**
	 * 获取应用服务器当前时间，支持客户端和服务端调用
     * @author:hongguang_wang
	 * @param ctx
	 *            服务端上下文，客户端调用时请传入NULL
	 * @return Date
	 * @throws EASBizException
	 */
	public static Date serverDate(Context ctx) throws EASBizException {
		Date serverDate = null;
		if (ctx == null) {
			serverDate = UserMonitorFactory.getRemoteInstance().getCurrentTime();
		} else {
			serverDate = UserMonitorFactory.getLocalInstance(ctx).getCurrentTime();
		}
		return serverDate;
	}
	
	/**
	 * 获得当前服务器日期,不带时间,格式为yyyy-MM-dd
	 * @return
	 * @throws EASBizException
	 * @throws ParseException
	 */
	public static Date getServerDate(Context ctx) throws EASBizException, ParseException{
		Date serverDate = null;
		if (ctx == null) {
			serverDate = UserMonitorFactory.getRemoteInstance().getCurrentTime();
		} else {
			serverDate = UserMonitorFactory.getLocalInstance(ctx).getCurrentTime();
		}		
		
		Date curDate = DateUtils.parseSimpleString(DateUtils.simpleDateString(serverDate));
		return curDate;
	}
	
///////////////////////////////////////////Timestamp相关的方法/////////////////////////////////////    
	/**
	 * 描述：将Date转换为Timestamp
	 * 注意：当date值为NULL时，可返回当前日期的Timestamp
	 * 
	 * @param date 源日期
	 * @return Timestamp 目标类型的日期
	 * @author:daij
	 * 创建时间：2006-11-29  修改时间: 2010-03-12 run_zheng<p> 
	 */
	public static Timestamp dateToTimestamp(Date date){
		return new Timestamp((date == null? new Date(): date).getTime());
		//return fromDefNow(date).timestamp();
	}
	
	/**
	 * 描述：将Timestamp转换为Date.
	 * 注意：当Timestamp值为NULL时，可返回当前日期的Date
	 * 
	 * @param time 源日期Timestamp值
	 * @return java.util.Date  目标类型的日期
	 * @author:daij
	 * 创建时间：2006-11-29 修改时间: 2010-03-12 run_zheng<p> 
	 */
	public static Date timestampToDate(Timestamp time){
		return new Date(time.getTime());
		//return fromDefNow(time).date();
	}
    
    /**
     * 描述：获取当前精确的时间戳
     * @return java.sql.Timestamp
     * @author:daij
     * 创建时间：2007-4-19 修改时间: 2010-03-12 run_zheng<p> 
     */
    public static Timestamp nowTimestamp(){
    	return new Timestamp((new Date()).getTime());
    	//return fromNow().timestamp(); 
    }
    
    /**
     * 
     * 描述：返回参数date时间戳，且时间处理为（时，分，秒：0, 0, 0 3）NULL作为NOW处理
     * @return Timestamp
     * @author:daij
     * 创建时间：2006-11-27 修改时间: 2010-03-12 run_zheng<p> 
     */
    public static Timestamp timestampStartOfDay(Date date){
    	return fromDefNow(date).startOfDay().timestamp();
    }
	
    /**
     * 描述：返回参数date时间戳，且时间处理为（时，分，秒 处理为：23, 59, 59）NULL作为NOW处理
     * @return Timestamp
     * @author:daij
     * 创建时间：2006-11-27 修改时间: 2010-03-12 run_zheng<p> 
     */
    public static Timestamp timestampEndOfDay(Date date){
    	return fromDefNow(date).endOfDay().timestamp();
    }
    
    /**
     * 描述：以某天为基准的所在月第一天的开始时间，yyyy-MM-01 00:00:00.000
     * 		date为空，默认为当前日期
     * @param date 源日期
     * @return 目标Timestamp对象
     */
    public static Timestamp timestampStartOfMonth(Date date){
    	return fromDefNow(date).startOfMonth().timestamp();
    }
    
    /**
     * 描述：以某天为基准的所在月第一天的结束时间，yyyy-MM-XX 23:59:59.999，
	 * 		 其中XX为所在月的最后一天。date为空，默认为当前日期
     * @param date 源日期
     * @return 目标Timestamp对象
     */
    public static Timestamp timestampEndOfMonth(Date date){
    	return fromDefNow(date).endOfMonth().timestamp();
    }
	
	/**
	 * 描述：某个日期往前或者后推几天. 
	 * 		date为空，默认为当前日期
	 * @param amount 前或后推几天， 比如:amount = 1 则为明天， -1则为前天，以此类推
	 * @return Timestamp
	 * @author:daij
	 * 创建时间：2009-7-18 修改时间: 2010-03-12 run_zheng<p> 
	 */
	public static Timestamp timestampAddDay(Date date, int amount){
		return fromDefNow(date).addDay(amount).timestamp();
	}
	
	/**
	 * 描述：某个日期的月往前或者后推几月. 
	 * 		date为空，默认为当前日期
	 * @param amount 前或后推几月， 比如:amount = 1 则为下个月， -1则为上个月，以此类推
	 * @return Timestamp
	 * @author:daij
	 * 创建时间：2009-7-18 <p>
	 */
	public static Timestamp timestampAddMonth(Date date, int amount){
		return fromDefNow(date).addMonth(amount).timestamp();
	}

	/**
	 * 获取应用服务器当前时间，支持客户端和服务端调用
     * @author:hongguang_wang
	 * @param ctx
	 *            服务端上下文，客户端调用时请传入NULL
	 * @return Timestamp
	 * @throws EASBizException
	 */
	public static Timestamp serverTimestamp(Context ctx) throws EASBizException {
		return from(serverDate(ctx)).timestamp();
	}
/////////////////////////////////////////////////日期比较、判断和计算/////////////////////////////////	
	/**
	 * 开始日期部分偏移后，与结束日期比较结果值
	 * @param offsetPart 偏移部分 Calendar.YEAR, Calendar.MONTH, Calendar.DATE,
	 *          Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, Calendar.MILLISECOND
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param offset 偏移值
	 * @author tjw
	 * @return int 
     * 		-1  startDate < endDate
     * 		0   startDate = endDate
     * 	    1   startDate > endDate
	 */
	public static int offset(int offsetPart,Date startDate,Date endDate,int offset){		
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		
		switch (offsetPart) {
		case Calendar.YEAR:
			startCalendar.add(Calendar.YEAR, offset);
			break;
		case Calendar.MONTH:
			startCalendar.add(Calendar.MONTH, offset);
			break;
		case Calendar.DATE:
			startCalendar.add(Calendar.DATE, offset);
			break;
		case Calendar.HOUR_OF_DAY:
			startCalendar.add(Calendar.HOUR_OF_DAY, offset);
			break;
		case Calendar.MINUTE:
			startCalendar.add(Calendar.MINUTE, offset);
			break;
		case Calendar.SECOND:
			startCalendar.add(Calendar.SECOND, offset);
			break;
		case Calendar.MILLISECOND:
			startCalendar.add(Calendar.MILLISECOND, offset);
			break;
		}
		long result = endDate.getTime() - startCalendar.getTimeInMillis();
		return result > 0 ? -1 : result < 0 ? 1 : 0;
	}
	
    /**
     * 计算两个日期之间相差的天数
     * 		计算的返回值为date2 - date1 的天数
     * @author danny_du
     * @param date1
     * @param date2
     * @return
     */
    public static int diffdates(Date date1, Date date2) {
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		return (int) ((time2 - time1) / MILLIS_OF_DAY);
	} 
    /**
     * 描述：两个日期精确到天比较.
     * 注意：按天比较不考虑时,分，秒，毫秒.
     * 
     * @param dateLeft 可为NULL作为NOW处理
     * @param dateRight 可为NULL作为NOW处理
     * @return int 
     * 		-1  dateLeft < dateRight
     * 		0   dateLeft = dateRight
     * 	    1   dateLeft > dateRight
     * @author:daij
     * 创建时间：2006-11-27  修改时间: 2010-03-12 run_zheng<p> 
     */
    public static int compareUpToDay(Date dateLeft, Date dateRight) {
       long result =  fromDefNow(dateLeft).startOfDay().milliseconds() 
       					- fromDefNow(dateRight).startOfDay().milliseconds();
       return result > 0 ? 1 : result < 0 ? -1 : 0;
    }
    
    /**
     * 描述：日期Date与当前日期NOW比较
     * @param date 待比较的日期，可为NULL作为NOW处理
     * @return int 
     * 		-1  NOW < dateRight
     * 		0   NOW = dateRight
     * 	    1   NOW > dateRight 
     * @author:daij
     * 创建时间：2006-11-27 修改时间: 2010-03-12 run_zheng<p> 
     */
    public static int compareNowUpToDay(Date date){
    	return compareUpToDay(null,date);
    }
    /**
     * 描述：两个日期比较.
     * 
     * @param left 可为NULL作为NOW处理
     * @param right 可为NULL作为NOW处理
     * @return int 
     * 		-1  dateLeft < dateRight
     * 		0   dateLeft = dateRight
     * 	    1   dateLeft > dateRight
     * 创建时间: 2010-03-12 run_zheng<p> 
     */
    public static int compareDate(Date left, Date right){
    	long result = fromDefNow(left).milliseconds() - fromDefNow(right).milliseconds();
    	return result > 0 ? 1 : result < 0 ? -1 : 0;
    }
    
    public static int compareTime(Date left, Date right){
    	long result = fromDefNow(left).time().getTime() - fromDefNow(right).time().getTime();
    	return result > 0 ? 1 : result < 0 ? -1 : 0;
    }
    /**
	 * 计算此日期是一周中的哪一天.返回依次用 Calendar.MONDAY ,Calendar.TUESDAY 判断。
	 * @param date
	 * @return
	 */
	 public static int dayOfWeek(Date date) {
		return getDateField(date, Calendar.DAY_OF_WEEK);
	} 
	 
	 public static int getDateField(Date date, int field){
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTime(date);
		 return calendar.get(field);
	 }
	 
	 public static long getDaysBetween(Date left, Date right){
		 long leftL = DateUtils.from(left).startOfDay().milliseconds();
		 long rightL = DateUtils.from(right).startOfDay().milliseconds();
		 long result = leftL - rightL;
		 return result / MILLIS_OF_DAY;
	 }
   //////////////////////////////////////////有效日期相关////////////////////////////////////////
    /**
     * 描述： 判断某个日期是否落在某个日期范围内,对日期范围不做时分秒处理
     * @param date  某个日期
     * @param dateLeft 日期范围的左值 
     * @param dateRight 日期范围的右值
     * @return 落在范围内返回true，否则返回否
     */
    public static boolean isBetweenDate(Date date, Date dateLeft, Date dateRight){
    	return date.getTime() < dateLeft.getTime()? false : 
    				date.getTime() > dateRight.getTime() ? false : true;
    }
    
    /**
     * 描述： 判断某个日期是否是某个日期范围内有效，
     * 		对日期范围的左端将时分秒设置为00：00：00.000 
     * 		对日期范围的有段将时分秒设置为23：59：59.999
     * @param date  某个日期
     * @param dateLeft 日期范围的左值 
     * @param dateRight 日期范围的右值
     * @return 落在范围内返回true，否则返回否
     */
    public static boolean isEffetiveDay(Date date,Date dateLeft, Date dateRight){
    	dateLeft = from(dateLeft).startOfDay().date();
    	dateRight = from(dateRight).endOfDay().date();
    	return isBetweenDate(date, dateLeft, dateRight	);
    }
    
    /**
     * 描述：获取判断日期落在有效日期范围内的sql字符串， 注意时间精确到天。
     * 		判断的规则：日期的结束时间 （yyyy-MM-dd 23:59:59）大于等于有效范围的左端
     * 					日期的开始时间 （yyyy-MM-dd 00:00:00）小于等于有效范围的右端
     *      该规则不会造成日期落在日期范围两段的情况漏判断。
     * @param date     日期
     * @param effectiveDateField  生效日期的字段名
     * @param expireDateField     失效日期的字段名
     * @return
     */
    public static StringBuffer effectiveDaySql(Date date, String effectiveDateField, String expireDateField){
		return effectiveCommonSql(expireDateField, from(date).startOfDay().formatString(DETAIL_FORMAT_STR), 
					effectiveDateField, from(date).endOfDay().formatString(DETAIL_FORMAT_STR)
    			);
    }
    
    public static StringBuffer effectiveDaySql(String field, Date left, Date right){
    	return effectiveCommonSql(field, from(left).startOfDay().formatString(DETAIL_FORMAT_STR), 
    			field, from(right).endOfDay().formatString(DETAIL_FORMAT_STR)
    			);
    }
    
    public static StringBuffer effectiveDayRangeSql(Date left, Date right, String leftField, String rightField){
    	StringBuffer sb = new StringBuffer();
    	sb.append("( ")
    	  .append(effectiveDaySql(left, leftField, rightField))
    	  .append("  or ")
    	  .append(effectiveDaySql(right, leftField, rightField))
    	  .append(" or ")
    	  .append(effectiveDaySql(leftField, left, right))
    	  .append(" )");
    	return sb;
    }
    /**
     * 描述：获取判断日期落在有效日期范围内的sql字符串, 注意时间是精确到时分秒。
     * 		判断的规则：日期的结束时间 （yyyy-MM-dd hh:mm:ss）大于等于有效范围的左端
     * 					日期的开始时间 （yyyy-MM-dd hh:mm:ss）小于等于有效范围的右端
     *      该规则不会造成日期落在日期范围两段的情况漏判断。
     * @param date     日期
     * @param effectiveDateField  生效日期的字段名
     * @param expireDateField     失效日期的字段名
     * @return
     */
    public static StringBuffer effectiveTimeSql(Date date, String effectiveDateField, String expireDateField){
    	return effectiveCommonSql(expireDateField, from(date).formatString(DETAIL_FORMAT_STR),
    			effectiveDateField, from(date).formatString(DETAIL_FORMAT_STR));
    }
    
    public static StringBuffer effectiveTimeSql(String field, Date left, Date right){
    	return effectiveCommonSql(field, from(left).formatString(DETAIL_FORMAT_STR), 
    			field, from(right).formatString(DETAIL_FORMAT_STR) );
    }
    
    public static StringBuffer effectiveTimeRangeSql(Date left, Date right, String leftField, String rightField){
    	StringBuffer sb = new StringBuffer();
    	sb.append("( ")
    	  .append(effectiveTimeSql(left, leftField, rightField))
    	  .append("  or ")
    	  .append(effectiveTimeSql(right, leftField, rightField))
    	  .append(" or ")
    	  .append(effectiveTimeSql(leftField, left, right))
    	  .append(" )");
    	return sb;
    }
    
    private static StringBuffer effectiveCommonSql(String leftField,  String leftDateStr, String rightField, String rightDateStr){
    	StringBuffer sb = new StringBuffer();
    	sb.append(" (")
  	  	  .append(leftField).append(" >= {ts'")
  	  	  .append(leftDateStr)
  	  	  .append("'} ")
  	  	  .append("and ").append(rightField)
  	  	  .append(" <={ts'")
  	  	  .append(rightDateStr)
  	  	  .append("'} )");
    	return sb;
    }
	
//////////////////////////////////////解析字符串成日期////////////////////////////////////////	
	 /**
	  * 利用传入的模式匹配字符串，构建Date对象
	 * @param str    目标字符串
	 * @param format 模式字符串
	 * @return       返回Date对象
	 * @throws ParseException  解析失败的时候抛出异常
	 */
	public static Date parse(String str, String format) throws ParseException{
		 SimpleDateFormat simple = new SimpleDateFormat(format);
		 return simple.parse(str);
	 }
	 /**
	  * 利用yyyy-MM-dd模式串分析str字符串
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Date parseSimpleString(String str) throws ParseException{
		 return parse(str, SIMPLE_FORMAT_STR);
	 }
	 /**
	 * 利用yyyy-MM-dd HH:mm:ss模式串分析str字符串
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDetailString(String str) throws ParseException{
		 return parse(str, DETAIL_FORMAT_STR);
	 }
	 
/////////////////////////////////////Date伙伴类相关/////////////////////////////////////
	 /**
		 * 获取Date的伙伴类对象
		 * 使用方法如：DateUtil.from(DATE).addMonth(1).startOfDay().get()
		 *           获得下个月同一天的开始时间Date对象，比如现在是2010-03-11 10:49:33.23
		 * 建议在多次设置的情况下使用该API
		 * @param date 初始化日期
		 * @return
		 */
		public static DateObject from(Date date){
			return new DateObject().from(date);
		}
		/**
		 * 获取指定日期的伙伴类对象
		 * @param time
		 * @return
		 */
		public static DateObject from(Timestamp time){
			return new DateObject().from(time);
		}
		/**
		 * 获取指定日期的伙伴类对象
		 * @param time
		 * @return
		 */
		public static DateObject from(long time){
			return new DateObject().fromMillis(time);
		}
		
		/**
		 * 获取指定日期的伙伴类对象，如果为空，使用当前日期
		 * @param time
		 * @return
		 */
		public static DateObject fromDefNow(Timestamp time){
			return time == null? fromNow() : from(time);
		}
		/**
		 * 获取指定日期的伙伴类对象，如果为空，使用当前日期
		 * @param date
		 * @return
		 */
		public static DateObject fromDefNow(Date date){
			return date == null? fromNow() : from(date);
		}
		
		/**
		 * 获取当前日期的伙伴类对象
		 * @return
		 */
		public static DateObject fromNow(){
			return new DateObject();
		}
		
		
		/**
		 * Date的伙伴类
		 * 0、利用from(),fromTimestamp(),fromMillis()从Date,Timestamp,long类型创建伙伴对象
		 * 1、利用year(),month(),day(),hour(),minute(),second()获取对应日期对应的年月日时分秒
		 * 2、利用year(X),month(X),day(X),hour(X),minute(X),second(X)设置对应日期的年月日时分秒
		 * 3、利用addYear(X),addMonth(X),addDay(X),addHour(X),addMinute(X),addSecond(X)
		 *           增加对应日期的年月日时分秒（负数往前推，正数往后推）
		 * 4、利用cutTail/millisecond(X)/millisecond() 截掉后面的毫秒/设置/获取秒后面的毫秒数
		 * 5、利用startOfDay/endOfDay/startOfMonth/endOfMonth
		 * 			分别用于获取一天的开始/结束和一个月的开始/结束的日期
		 * 6、利用reset方法重置伙伴类的日期
		 * 7、利用daysOfMonth后去对应日期所在月的天数
		 * 8、利用date/timestamp/milliseconds分别用于获取Date/Timestamp日期对象,或详细的毫秒数
		 * 9、利用simpleString/detailString/toString/timeString/formatString(String)
		 * 			分别用于获取——（yyyy-MM-dd）/(yyyy-MM-dd HH:mm:ss)
		 * 			/（yyyy-MM-dd HH:mm:ss.SS）/（HH:mm:ss）/指定格式的——日期字符串
		 * 注意：1、不能多线程操作该类的对象，否则结果不可预料。
		 *       2、month方法设置/得到的月份是从1开始，Calendar提供的API是从0开始，使用时必须注意点
		 * @author run_zheng
		 */
		public static class DateObject {
			private static final int MAX_NANOS = 999999999;
			private static final int MAX_HOUR = 23;
			private static final int MAX_MIN_SEC = 59;
			private static final int MAX_MILLIS = 999;
			private Calendar calendar =  Calendar.getInstance();
			private Date date = null;
		//	private int nanos = 0; //纳秒
			//其他包无法创建该类的实例
			DateObject(){}
			
			public DateObject from(Date date) {
				calendar.setTime(date);
				this.date  = date;
		//		this.nanos = 0;
				return this;
			}
			
			public DateObject fromTimestamp(Timestamp time){
				calendar.setTimeInMillis(time.getTime());
			//	nanos = time.getNanos();
				return this;
			}
			
			public DateObject fromMillis(long time) {
				calendar.setTimeInMillis(time);
				return this;
			}
			
			public DateObject year(int year) {
				calendar.set(Calendar.YEAR, year);
				return this;
			}
			public DateObject month(int month) {
				calendar.set(Calendar.MONTH, month - 1);
				return this;
			}
			public DateObject day(int day) {
				calendar.set(Calendar.DAY_OF_MONTH, day);
				return this;
			}
			public DateObject hour(int hour) {
				calendar.set(Calendar.HOUR_OF_DAY, hour);
				return this;
			}
			public DateObject minute(int minute) {
				calendar.set(Calendar.MINUTE, minute);
				return this;
			}
			public DateObject second(int second) {
				calendar.set(Calendar.SECOND, second);
				return this;
			}
			
			public DateObject addYear(int year) {
				calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year);
				return this;
			}
			public DateObject addMonth(int month) {
				calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + month);
				return this;
			}
			public DateObject addDay(int day) {
				calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + day);
				return this;
			}
			public DateObject addHour(int hour) {
				calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
				return this;
			}
			public DateObject addMinute(int minute) {
				calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minute);
				return this;
			}
			public DateObject addSecond(int second) {
				calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + second);
				return this;
			}
			
			public DateObject addMillisecond(int millisecond){
				calendar.set(Calendar.MILLISECOND, calendar.get(Calendar.MILLISECOND) + millisecond);
				return this;
			}
			
			public int year() {
				return calendar.get(Calendar.YEAR);
			}
			public int month() {
				return calendar.get(Calendar.MONTH) + 1;
			}
			public int day() {
				return calendar.get(Calendar.DAY_OF_MONTH);
			}
			public int hour() {
				return calendar.get(Calendar.HOUR_OF_DAY);
			}
			public int minute() {
				return calendar.get(Calendar.MINUTE);
			}
			public int second() {
				return calendar.get(Calendar.SECOND);
			}
			
			public DateObject cutTail(){
				calendar.set(Calendar.MILLISECOND, 0);
			//	nanos(0);
				return this;
			}
			public DateObject millisecond(int tail){
				calendar.set(Calendar.MILLISECOND, tail);
				return this;
			}
			public int millisecond(){
				return calendar.get(Calendar.MILLISECOND);
			}
			
			/*public int nanos(){
				return nanos;
			}
			
			public DateAccompany nanos(int nanos){
				this.nanos = nanos;
				return this;
			}*/
			
			public DateObject startOfDay(){
				this.hour(0).minute(0).second(0).cutTail();
				return this;
			}
			public DateObject endOfDay(){
				this.hour(MAX_HOUR).minute(MAX_MIN_SEC)
					.second(MAX_MIN_SEC).millisecond(MAX_MILLIS); //.nanos(MAX_NANOS);
				return this;
			}
			
			public DateObject startOfMonth(){
				this.day(1).startOfDay();
				return this;
			}
			
			public DateObject endOfMonth(){
				this.day(this.daysOfMonth()).endOfDay();
				return this;
			}
			
			public int daysOfMonth(){
				return this.calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			
			public DateObject reset(){
				if(date == null)
					calendar = Calendar.getInstance();
				else calendar.setTime(date);
				return this;
			}
			
			public DateObject reset(Date date){
				calendar.setTime(date);
				return this;
			}
			public DateObject reset(DateObject ac){
				calendar.setTime(ac.date());
				return this;
			}
			
			public Date date(){
				return calendar.getTime();
			}
			
			public Timestamp timestamp(){
				Timestamp time = new Timestamp(calendar.getTimeInMillis());
			//	time.setNanos(this.nanos);
				return time;
			}
			
			public Time time(){
				this.year(1970).month(1).day(1);
				Time time = new Time(calendar.getTimeInMillis());
				return time;
			}
			public long milliseconds(){
				return calendar.getTimeInMillis();
			}
			
			public String simpleString(){
				return DateUtils.SIMPLE_FORMAT.format(date());
			}
			
			public String detailString(){
				return DateUtils.DETAIL_FORMAT.format(date());
			}
			
			public String formatString(String format){
				SimpleDateFormat formater = new SimpleDateFormat(format);
				return formater.format(date());
			}
			
			public String timeString(){
				return formatString(TIME_FORMAT_STR);
			}
			
			public String toString(){
				return formatString(ALLFIELD_FORMAT_STR);
			}
		}
		
		/**
	     * 
	     * 描述：两个日期精确到天比较.
	     * 注意：按天比较不考虑时,分，秒，毫秒.
	     * 
	     * @param dateLeft 可为NULL作为NOW处理
	     * @param dateRight 可为NULL作为NOW处理
	     * @return int 
	     * 		-1  dateLeft < dateRight
	     * 		0   dateLeft = dateRight
	     * 	    1   dateLeft > dateRight
	     * 
	     * @throws ParseException
	     * @author:junwu_tang
	     * 创建时间：2006-11-27 <p>
	     */
	    public static int compareByDate(Date dateLeft, Date dateRight) throws ParseException{
	        
	        //如果没有传入日期左，则认为与当前日期比较.
	        Date ld = simpleDate(dateLeft);
	        
	        //如果没有传入日期右，则认为与当前日期比较.        
	        Date sd = simpleDate(dateRight);
	        
	    	return ld.compareTo(sd);
	    }
	    
	    /**
		 * 
		 * 描述：返回精确到天的日期,显示格式为yyyy-MM-dd的日期
		 * 
		 * @param date java.util.Date 
		 * @return java.util.Date 按yyyy-MM-dd格式化后的日期.
		 * @throws ParseException
		 * @author:junwu_tang
		 * 创建时间：2006-11-27 <p>
		 */
		public static Date simpleDate(Date date) throws ParseException{
			Date d = date==null ? date : new Date();
			return SIMPLE_FORMAT.parse(SIMPLE_FORMAT.format(d));
		}
}

