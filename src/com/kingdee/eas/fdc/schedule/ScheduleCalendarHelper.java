package com.kingdee.eas.fdc.schedule;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;

/**
 * 进度计算帮助类
 * 
 * @author xiaohong_shi
 * 
 */
public class ScheduleCalendarHelper {

	/**
	 * 计算有效工期 使用与Gantt一样的方法计算有效工期，这样的一个好处是可以有效的避免休息日的重叠
	 * 
	 * @param startDate
	 * @param endDate
	 * @param calendarInfo
	 * @return
	 */
	public static BigDecimal getEffectTimes(Date startDate, Date endDate,
			ScheduleCalendarInfo calendarInfo) {
		if (startDate == null || endDate == null) {
			return FDCHelper.ZERO;
		}
		GregorianCalendar startCalendar = getCalendar(startDate);
		GregorianCalendar endCalendar = getCalendar(endDate);
		boolean isNeg = false;// 是否相反
		if (compareTo(startCalendar, endCalendar) > 0) {
			GregorianCalendar tempDate = startCalendar;
			startCalendar = endCalendar;
			endCalendar = tempDate;
			isNeg = true;
		}
		int day = 0;
		while (compareTo(startCalendar, endCalendar) <= 0) {
			// 此处算法有误。如周四-周五，计算出的时间为1天，周三-周四为2天；正确的算法应都算出位2天。 modify by
			// zhiqiao_yang. at 2010-07-25
			if (calendarInfo != null && !calendarInfo.isWeekTime(startCalendar)) {
				day++;
			}
			startCalendar.add(Calendar.DATE, 1);
		}
		if (isNeg) {
			day = day * -1;
		}
		return new BigDecimal(String.valueOf(day));
	}

	/**
	 * 计算自然工期 当天到当天算做一天
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static BigDecimal getNatureTimes(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return FDCHelper.ZERO;
		}
		GregorianCalendar startCalendar = getCalendar(startDate);
		GregorianCalendar endCalendar = getCalendar(endDate);
		boolean isNeg = false;// 是否相反
		if (compareTo(startCalendar, endCalendar) > 0) {
			GregorianCalendar tempDate = startCalendar;
			startCalendar = endCalendar;
			endCalendar = tempDate;
			isNeg = true;
		}
		long diff = (endCalendar.getTimeInMillis() - startCalendar
				.getTimeInMillis())
				/ (1000 * 60 * 60 * 24);
		// 包含当天
		diff = diff + 1;
		if (isNeg) {
			diff = diff * -1;
		}
		return new BigDecimal(String.valueOf(diff));
	}

	private static GregorianCalendar getCalendar(Date date) {
		if (date == null) {
			throw new NullPointerException("date cann't be null");
		}
		GregorianCalendar cal = new GregorianCalendar(date.getYear() + 1900,
				date.getMonth(), date.getDate());
		// cal.setTime(date);
		return cal;
	}

	/**
	 * 1.4 的Calendar 没有compareTo 方法
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareTo(Calendar date1, Calendar date2) {
		return (int) ((date1.getTimeInMillis() - date2.getTimeInMillis()) / (1000 * 24 * 60 * 60));
	}

	/**
	 * 得到持续有效工期后的结束时间
	 * 
	 * @param startDate
	 * @param effectTimes
	 * @param calendarInfo
	 * @return
	 */
	public static Date getEndDate(Date startDate, BigDecimal effectTimes,
			ScheduleCalendarInfo calendarInfo) {
		GregorianCalendar startCalendar = getCalendar(startDate);
		int length = effectTimes.intValue();// 先全部做成Int 以后应该会有半天的
		boolean isNeg = length<0?true:false;
		// 从1开始，因为1表示开始时间与结束时间一样
		if(isNeg){
			for (int i = 0; i < Math.abs(length); i++) {
				startCalendar.add(Calendar.DATE, -1);
				if (calendarInfo.isWeekTime(startCalendar)) {
					// startCalendar.add(Calendar.DATE, 1);
					// 本次不计
					i--;
				}
			}
		}else{
			for (int i = 0; i < length; i++) {
				startCalendar.add(Calendar.DATE, 1);
				if (calendarInfo.isWeekTime(startCalendar)) {
					// startCalendar.add(Calendar.DATE, 1);
					// 本次不计
					i--;
				}
			}
		}
		
		return startCalendar.getTime();
	}

	/**
	 * 取得时间按搭接差异的下一天,用于计算任务关系的
	 * 
	 * @param startDate
	 * @param effectTimes
	 * @param calendarInfo
	 * @return
	 */
	public static Date getDiffDate(Date date, BigDecimal diff) {
		GregorianCalendar calendar = getCalendar(date);
		int length = diff.intValue();
		// 从1开始，因为1表示开始时间与结束时间一样
		calendar.add(Calendar.DATE, length);
		return calendar.getTime();
	}

	/**
	 * 得到持续有效工期后的开始时间
	 * 
	 * @param endDate
	 * @param effectTimes
	 * @param calendarInfo
	 * @return
	 */
	public static Date getStartDate(Date endDate, BigDecimal effectTimes,
			ScheduleCalendarInfo calendarInfo) {
		GregorianCalendar endCalendar = getCalendar(endDate);
		int length = effectTimes.intValue();// 先全部做成Int 以后应该会有半天的
		// 从1开始，因为1表示开始时间与结束时间一样
		for (int i = 1; i < length; i++) {
			endCalendar.add(Calendar.DATE, -1);
			if (calendarInfo.isWeekTime(endCalendar)) {
				// startCalendar.add(Calendar.DATE, 1);
				// 本次不计
				length++;
			}
		}
		return endCalendar.getTime();
	}

	/**
	 * 得到下一工作日
	 * 
	 * @param date
	 * @param calendarInfo
	 * @return
	 */
	public static Date getNextWorkDay(Date date,
			ScheduleCalendarInfo calendarInfo) {
		Calendar cal = getCalendar(date);
		while (true) {
			cal.add(Calendar.DATE, 1);
			if (!calendarInfo.isWeekTime(cal)) {
				break;
			}
		}
		return cal.getTime();
	}

	/**
	 * 得到上一工作日
	 * 
	 * @param date
	 * @param calendarInfo
	 * @return
	 */
	public static Date getPreWorkDay(Date date,
			ScheduleCalendarInfo calendarInfo) {
		Calendar cal = getCalendar(date);
		while (true) {
			cal.add(Calendar.DATE, -1);
			if (!calendarInfo.isWeekTime(cal)) {
				break;
			}
		}
		return cal.getTime();
	}

	/**
	 * 取得最近的工作日
	 * <p>
	 * 往后递增
	 * 
	 * @param date
	 * @param calendarInfo
	 * @return
	 */
	public static Date getClosestWorkDay(Date date,
			ScheduleCalendarInfo calendarInfo) {
		Calendar cal = getCalendar(date);
		if (!calendarInfo.isWeekTime(cal)) {
			return cal.getTime();
		} else {
			return getNextWorkDay(date, calendarInfo);
		}
	}

}
