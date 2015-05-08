package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarCollection;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarFactory;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;

public class ScheduleAppHelper {
	
	/** 默认的系统进度日历ID **/
	private static final String DEFALUT_CALENDAR_PK = "L5WgOM5+TDCr/oCKWmYL0IclCXs=";
	/**
	 * 项目日历从数据库取值
	 * 
	 * @param info
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static void setCalendar(Context ctx, FDCScheduleInfo info) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("holidayEntrys.*");
		view.getSelector().add("weekendEntrys.*");
		view.setFilter(new FilterInfo());
		if (info.getProject() != null) {
			view.getFilter().appendFilterItem("objectId", info.getProject().getId().toString());
		} else {
			view.getFilter().appendFilterItem("objectId", null);
		}
		ScheduleCalendarCollection calendars = ScheduleCalendarFactory.getLocalInstance(ctx).getScheduleCalendarCollection(view);
		ScheduleCalendarInfo calendar = null;
		if (calendars.size() < 1) {
			try {
				calendar = ScheduleCalendarFactory.getLocalInstance(ctx).getScheduleCalendarInfo(new ObjectStringPK(DEFALUT_CALENDAR_PK));
			} catch (EASBizException e) {
				e.printStackTrace();
			}
		} else {
			calendar = calendars.get(0);
		}
		info.setCalendar(calendar);
	}
}
