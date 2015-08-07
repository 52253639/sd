/*
 * Created on 08.11.2004
 */
package net.sourceforge.ganttproject.time.gregorian;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.time.TimeFrame;
import net.sourceforge.ganttproject.time.TimeUnit;
import net.sourceforge.ganttproject.time.TimeUnitGraph;
import net.sourceforge.ganttproject.time.TimeUnitPair;
import net.sourceforge.ganttproject.time.TimeUnitStack;

/**
 * @author bard
 */
public class GPTimeUnitStack implements TimeUnitStack {
	private TimeUnitGraph ourGraph = new TimeUnitGraph();

	public TimeUnit DAY;

	public TimeUnit WEEK;

	public TimeUnit MONTH;

	public TimeUnit QUARTER;

	public TimeUnit YEAR = null;

	private TimeUnitPair[] myPairs;

	private TimeUnit MONTH_FROM_WEEKS;

	public TimeUnit DAY_AS_BOTTOM_UNIT;

	public TimeUnit WEEK_AS_BOTTOM_UNIT;

	public TimeUnit MONTH_AS_BOTTOM_UNIT;

	/**
	 * 
	 */
	public GPTimeUnitStack(GanttLanguage i18n) {
		TimeUnit atom = ourGraph.createAtomTimeUnit("atom");
		DAY = ourGraph.createDateFrameableTimeUnit("day", atom, 1,
				new FramerImpl(Calendar.DATE));
		DAY.setTextFormatter(new DayTextFormatter());

		DAY_AS_BOTTOM_UNIT = ourGraph.createDateFrameableTimeUnit("week", DAY,
				1, new FramerImpl(Calendar.DAY_OF_WEEK));
		DAY_AS_BOTTOM_UNIT.setTextFormatter(new DayTextFormatter(DayTextFormatter.format_EEEE));

		WEEK = ourGraph.createDateFrameableTimeUnit("week", DAY, 7,
				new WeekFramerImpl());
		WEEK.setTextFormatter(new WeekTextFormatter(
				WeekTextFormatter.format_yyyyMMdd));

		WEEK_AS_BOTTOM_UNIT = ourGraph.createDateFrameableTimeUnit("week", DAY,
				7, new WeekFramerImpl());
		WEEK_AS_BOTTOM_UNIT.setTextFormatter(new WeekTextFormatter("{0}"));

		MONTH = ourGraph.createTimeUnitFunctionOfDate("month", DAY,
				new FramerImpl(Calendar.MONTH));
		MONTH.setTextFormatter(new MonthTextFormatter());

		MONTH_AS_BOTTOM_UNIT = ourGraph.createDateFrameableTimeUnit("month",
				DAY, 30, new FramerImpl(Calendar.MONTH));
		MONTH_AS_BOTTOM_UNIT.setTextFormatter(new MonthTextFormatter());

		YEAR = ourGraph.createTimeUnitFunctionOfDate("year", DAY,
				new FramerImpl(Calendar.YEAR));
		YEAR.setTextFormatter(new YearTextFormatter());

		myPairs = new TimeUnitPair[] {
				new MyTimeUnitPair(WEEK, DAY_AS_BOTTOM_UNIT),
				new MyTimeUnitPair(MONTH, DAY),
				new MyTimeUnitPair(MONTH, DAY),
				new MyTimeUnitPair(MONTH, WEEK),
				new MyTimeUnitPair(MONTH, WEEK),
				new MyTimeUnitPair(MONTH, WEEK),
				new MyTimeUnitPair(MONTH, WEEK),
				new MyTimeUnitPair(MONTH, WEEK),
				new MyTimeUnitPair(YEAR, MONTH_AS_BOTTOM_UNIT),
				new MyTimeUnitPair(YEAR, MONTH_AS_BOTTOM_UNIT),
				new MyTimeUnitPair(YEAR, MONTH_AS_BOTTOM_UNIT) };
	}

	public TimeFrame createTimeFrame(Date baseDate, TimeUnit topUnit,
			TimeUnit bottomUnit) {
		// if (topUnit instanceof TimeUnitFunctionOfDate) {
		// topUnit = ((TimeUnitFunctionOfDate)topUnit).createTimeUnit(baseDate);
		// }
		return new TimeFrameImpl(baseDate, topUnit, bottomUnit);
	}

	public String getName() {
		return "default";
	}

	public TimeUnit getDefaultTimeUnit() {
		return DAY;
	}

	public TimeUnitPair[] getTimeUnitPairs() {
		return myPairs;
	}

	private class MyTimeUnitPair extends TimeUnitPair {
		MyTimeUnitPair(TimeUnit topUnit, TimeUnit bottomUnit) {
			super(topUnit, bottomUnit, GPTimeUnitStack.this);
		}
	}

	public TimeUnit getWeekDayTimeUnit() {
		return DAY_AS_BOTTOM_UNIT;
	}
}
