package net.sourceforge.ganttproject.time.gregorian;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import net.sourceforge.ganttproject.gui.zoom.ZoomManager;
import net.sourceforge.ganttproject.time.DateFrameable;
import net.sourceforge.ganttproject.time.TextFormatter;
import net.sourceforge.ganttproject.time.TimeUnit;
import net.sourceforge.ganttproject.time.TimeUnitText;

public class DayTextFormatter extends CachingTextFormatter implements TextFormatter {
	/** cache for holding formatted day names * */

	SimpleDateFormat weekFormat = new SimpleDateFormat("d(E)");

	private final HashMap textCache = new HashMap();

	private int disFormat = -1;

	public static int format_EEEE = 1;

	public DayTextFormatter() {

	}

	public DayTextFormatter(int myDisFormat) {
		disFormat = myDisFormat;
	}

	protected TimeUnitText createTimeUnitText(Date adjustedLeft) {
		String format = "";
		if (disFormat == format_EEEE) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(adjustedLeft);
			format = weekFormat.format(adjustedLeft);
			format = format.replaceAll("ÐÇÆÚ", "");
		} else if (disFormat == -1) {
			format = MessageFormat.format("{0}", new Object[] { "" + adjustedLeft.getDate() });
		}
		return new TimeUnitText(format);
	}

	// public TimeUnitText format(TimeUnit timeUnit, Date baseDate) {
	// String result = null;
	// if (timeUnit instanceof DateFrameable) {
	// Date adjustedLeft = ((DateFrameable) timeUnit).adjustLeft(baseDate);
	// result = (String) textCache.get(adjustedLeft);
	// if (result == null) {
	// result = MessageFormat.format("{0}", new Object[] { ""
	// + adjustedLeft.getDate() });
	// textCache.put(adjustedLeft, result);
	// }
	// }
	// return new TimeUnitText(result);
	// }

}
