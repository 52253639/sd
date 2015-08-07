package net.sourceforge.ganttproject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.eas.fdc.schedule.framework.ext.ScheduleGanttProject;

public class Sllistenner implements ChangeListener {
	private KDDatePicker pkJumpDate;
	private ScheduleGanttProject project;

	public Sllistenner(KDDatePicker pkJumpDate, ScheduleGanttProject ganttProejct) {
		this.pkJumpDate = pkJumpDate;
		this.project = ganttProejct;
	}

	public void stateChanged(ChangeEvent e) {
		Date start = project.getTaskManager().getProjectStart();
		Calendar temp = Calendar.getInstance();
		temp.setTime(start);
		JSlider js = (JSlider) e.getSource();
		int value = js.getValue();
		temp.add(Calendar.DATE, value);
		project.getScrollingManager().scrollLeft(temp.getTime());
		pkJumpDate.setValue(temp.getTime(), false);
		js.setToolTipText(DateFormat.getDateInstance(3).format(temp.getTime()));

	}

}