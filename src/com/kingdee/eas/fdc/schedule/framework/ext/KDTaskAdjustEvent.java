package com.kingdee.eas.fdc.schedule.framework.ext;

import java.io.Serializable;
import java.util.Date;
import java.util.EventObject;

import net.sourceforge.ganttproject.task.TaskLength;

public class KDTaskAdjustEvent extends EventObject implements Serializable{
	public static final int CHANGETYPE_STARTDATE=0;
	public static final int CHANGETYPE_ENDDATE=1;
	public static final int CHANGETYPE_DURATION=2;
	private final int changeType;
	private Date startDate=null;
	private Date endDate=null;
	private long duration;
	public KDTaskAdjustEvent(Object source,int changeType) {
		super(source);
		this.changeType=changeType;
	}
	public KDTaskAdjustEvent(Object source,int changeType,Date startDate,Date endDate,long duration) {
		this(source, changeType);
		this.startDate=startDate;
		this.endDate=endDate;
		this.duration=duration;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public int getChangeType() {
		return changeType;
	}
	
	
}
