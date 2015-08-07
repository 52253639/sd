package net.sourceforge.ganttproject.time.gregorian;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.sourceforge.ganttproject.language.GanttLanguage;
import net.sourceforge.ganttproject.language.GanttLanguage.Event;
import net.sourceforge.ganttproject.time.TextFormatter;
import net.sourceforge.ganttproject.time.TimeUnitText;

public class WeekTextFormatter extends CachingTextFormatter implements
		TextFormatter {
	private Calendar myCalendar;

	SimpleDateFormat weekFormat_yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");

	SimpleDateFormat weekFormat_MMdd = new SimpleDateFormat("MM-dd");

	SimpleDateFormat weekFormat_dd = new SimpleDateFormat("dd");

	public static int format_yyyyMMdd = 1;

	private int disFormat = -1;

	WeekTextFormatter(String formatString) {
		myCalendar = (Calendar) Calendar.getInstance().clone();
	}

	public WeekTextFormatter(int myDisFormat) {
		disFormat = myDisFormat;
	}

	protected TimeUnitText createTimeUnitText(Date startDate) {
		String shortText = "";
		String middleText = "";
		String longText = "";
		if (myCalendar == null) {
			myCalendar = (Calendar) Calendar.getInstance().clone();
		}
		myCalendar.setTime(startDate);
		myCalendar.setMinimalDaysInFirstWeek(7);
		if (disFormat == -1) {
			Integer weekNo = new Integer(myCalendar.get(Calendar.WEEK_OF_YEAR));
			shortText = MessageFormat.format("{0}", new Object[] { weekNo });
			middleText = MessageFormat.format(GanttLanguage.getInstance()
					.getText("week")
					+ " {0}", new Object[] { weekNo });
		} else if (disFormat == format_yyyyMMdd) {
			shortText = weekFormat_dd.format(startDate);
			middleText = weekFormat_MMdd.format(startDate);
			longText = weekFormat_yyyyMMdd.format(startDate);
		}
		return new TimeUnitText(longText, middleText, shortText);
	}

	public void languageChanged(Event event) {
		super.languageChanged(event);
		myCalendar = (Calendar) Calendar.getInstance().clone();
	}

}


