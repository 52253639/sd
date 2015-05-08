package com.kingdee.eas.fdc.schedule.framework.parser;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import net.sourceforge.ganttproject.GanttCalendar;
import net.sourceforge.ganttproject.GanttTaskRelationship;
import net.sourceforge.ganttproject.calendar.GPCalendarActivity;
import net.sourceforge.ganttproject.calendar.ScheduleCalendarImpl;
import net.sourceforge.ganttproject.shape.ShapePaint;
import net.sourceforge.ganttproject.task.TaskLength;
import net.sourceforge.ganttproject.task.TaskManager;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyConstraint;
import net.sourceforge.ganttproject.task.dependency.constraint.FinishFinishConstraintImpl;
import net.sourceforge.ganttproject.task.dependency.constraint.FinishStartConstraintImpl;
import net.sourceforge.ganttproject.task.dependency.constraint.StartStartConstraintImpl;
import net.sourceforge.ganttproject.util.ColorConvertion;

import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;

public class ScheduleParserHelper {
	private ScheduleParserHelper(){};
    public static Color parseStringToColor(String value) {
    	if(FDCHelper.isEmpty(value)){
    		return null;
    	}
    	return ColorConvertion.determineColor(value);
    }
    
    public static String parseColorToString(Color color){
    	if(color==null){
    		return "";
    	}
    	return ColorConvertion.getColor(color);
    }
    public static GanttCalendar parseDateToGanttCalendar(Date date){
    	if(date==null){
    		return null;
    	}
    	return new GanttCalendar(date);
    }
    
    public static Date parseGanttCalendarToDate(GanttCalendar calendar){
    	if(calendar==null){
    		return null;
    	}
    	return calendar.getTime();
    }
    
    /**
     * 将形状字符串转化成对象
     * @param shape
     * @param foreground
     * @param background
     * @return
     */
    public static ShapePaint getShape(String shape, Color foreground, Color background) {
		if (shape == null) {
			return null;
		}
		java.util.StringTokenizer st1 = new java.util.StringTokenizer(shape, ",");
		int[] array = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		String token = "";
		int count = 0;
		while (st1.hasMoreTokens()) {
			token = st1.nextToken();
			array[count] = (new Integer(token)).intValue();
			count++;
		}
		return new ShapePaint(4, 4, array, foreground, background);
	}
    
    /**
     * 取得形状的字符串表示
     * @param shape
     * @return
     */
    public static String getShapeString(ShapePaint shape){
    	if(shape!=null){
    		return shape.getArray();
    	}
    	return null;
    }
    /**
     * 将形状字符串转化成对象，使用Task 的颜色做背景
     * @param shape
     * @param taskColor
     * @return
     */
    public static ShapePaint getShape(String shape,Color taskColor){
    	return getShape(shape, Color.white, taskColor);
    }
    public static int parseBigDecimalToInt(BigDecimal dec){
    	if(dec!=null){
    		return dec.intValue();
    	}
    	return 0;
    }
    
    public static TaskDependencyConstraint getTaskDependencyConstraintByLinkType(TaskLinkTypeEnum linkType){
//    	if(linkType==TaskLinkTypeEnum.START_FINISH){
//    		return new StartFinishConstraintImpl();
//    	}
    	if(linkType==TaskLinkTypeEnum.FINISH_FINISH){
    		return new FinishFinishConstraintImpl();
    	}
    	if(linkType==TaskLinkTypeEnum.START_START){
    		return new StartStartConstraintImpl();
    	}
    	if(linkType==TaskLinkTypeEnum.FINISH_START){
    		return new FinishStartConstraintImpl();
    	}
    	return new FinishStartConstraintImpl();
    }

    public static int getTaskLinkTypeInt(TaskLinkTypeEnum linkType){
//    	if(linkType==TaskLinkTypeEnum.START_FINISH){
//    		return GanttTaskRelationship.SF;
//    	}
    	if(linkType==TaskLinkTypeEnum.FINISH_FINISH){
    		return GanttTaskRelationship.FF;
    	}
    	if(linkType==TaskLinkTypeEnum.START_START){
    		return GanttTaskRelationship.SS;
    	}
    	if(linkType==TaskLinkTypeEnum.FINISH_START){
    		return GanttTaskRelationship.FS;
    	}
    	return GanttTaskRelationship.FS;
    }
    
    public static TaskLinkTypeEnum getTaskLinkTypeByConstraint(TaskDependencyConstraint constraint){
//    	if(constraint instanceof StartFinishConstraintImpl){
//    		return TaskLinkTypeEnum.START_FINISH;
//    	}
    	if(constraint instanceof FinishFinishConstraintImpl){
    		return TaskLinkTypeEnum.FINISH_FINISH;
    	}
    	if(constraint instanceof StartStartConstraintImpl){
    		return TaskLinkTypeEnum.START_START;
    	}
    	if(constraint instanceof FinishStartConstraintImpl){
    		return TaskLinkTypeEnum.FINISH_START;
    	}
    	return TaskLinkTypeEnum.FINISH_START;
    }
    
    public static TaskLinkTypeEnum getTaskLinkTypeByInt(int ganttTaskRelationshipType){
    	switch (ganttTaskRelationshipType) {
//			case GanttTaskRelationship.SF:
//				return TaskLinkTypeEnum.START_FINISH;
			case GanttTaskRelationship.FF:return TaskLinkTypeEnum.FINISH_FINISH;
			case GanttTaskRelationship.SS:return TaskLinkTypeEnum.START_START;
			case GanttTaskRelationship.FS:return TaskLinkTypeEnum.FINISH_START;
			default:
				return TaskLinkTypeEnum.FINISH_START;
		}
    }

	/**
	 * 根据task所对应的日历，计算有效工期<br>
	 * 
	 * @param task
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long getDuration(KDTask task, Date startDate, Date endDate) {
		TaskManager taskManager = task.getManager();
		// GPCalendar calendar = taskManager.getCalendar();
		ScheduleCalendarImpl calendar = task.getCalendar();
		return getDuration(task, startDate, endDate, taskManager, calendar);
	}
	
	private static long getDuration(KDTask task, Date startDate, Date endDate,
			TaskManager taskManager, ScheduleCalendarImpl calendar) {
		long length = 0;
		List activities = calendar.getActivities(startDate, endDate);
		for (int i = 0; i < activities.size(); i++) {
			GPCalendarActivity nextCalendarActivity = (GPCalendarActivity) activities
					.get(i);
			if (nextCalendarActivity.isWorkingTime()) {
				TaskLength myDuration = taskManager.createLength(task
						.getDuration().getTimeUnit(), nextCalendarActivity
						.getStart(), nextCalendarActivity.getEnd());
				length = length + myDuration.getLength();
			}
		}
		return length;
	}
}
