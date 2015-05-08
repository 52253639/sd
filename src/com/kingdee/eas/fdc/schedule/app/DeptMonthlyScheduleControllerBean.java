package com.kingdee.eas.fdc.schedule.app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleCollection;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleInfo;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.DeptMonthlyScheduleTaskInfo;
import com.kingdee.eas.framework.CoreBillBaseInfo;

public class DeptMonthlyScheduleControllerBean extends AbstractDeptMonthlyScheduleControllerBean {
	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.schedule.app.DeptMonthlyScheduleControllerBean");

	protected void checkNameDup(Context ctx, FDCBillInfo billInfo) throws BOSException, EASBizException {

	}
	
	
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		DeptMonthlyScheduleInfo info = (DeptMonthlyScheduleInfo) model;
		AdminOrgUnitInfo adminDept = info.getAdminDept();
		int year = info.getYear();
		int month = info.getMonth();

		// FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(new FilterItemInfo("adminDept.id",
		// adminDept.getId().toString()));
		// filter.getFilterItems().add(new FilterItemInfo("year", year));
		// filter.getFilterItems().add(new FilterItemInfo("month", month));
		//		
		// delete(ctx, filter);
		return super._save(ctx, model);
	}
	
	protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		DeptMonthlyScheduleInfo info = (DeptMonthlyScheduleInfo) model;
		AdminOrgUnitInfo adminDept = info.getAdminDept();
		// int year = info.getYear();
		// int month = info.getMonth();

		// FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(new FilterItemInfo("adminDept.id",
		// adminDept.getId().toString()));
		// filter.getFilterItems().add(new FilterItemInfo("year", year));
		// filter.getFilterItems().add(new FilterItemInfo("month", month));
		//
		// delete(ctx, filter);
        info.setState(FDCBillStateEnum.SUBMITTED);
		return super._submit(ctx, info);
		// submitForWF(ctx, model);
	}

	public boolean checkNumberDup(Context arg0, IObjectPK arg1, CoreBillBaseInfo arg2) throws BOSException, EASBizException {
		// update by libing at 2011-11-14 use true take place of false
		return true;
	}

	protected List _getAllTask(Context ctx, int year, int month, String id) throws BOSException {
		Set set = new HashSet();
		EntityViewInfo viewInfo = new EntityViewInfo();
		if (null != id) {
			set.add(id);
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("parent.id", id));
			viewInfo.setFilter(filterInfo);
		}
		AdminOrgUnitCollection adminOrgUnitCollection = AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitCollection(viewInfo);
		for (int k = 0; k < adminOrgUnitCollection.size(); k++) {
			AdminOrgUnitInfo adminOrgUnitInfo = adminOrgUnitCollection.get(k);
			set.add(adminOrgUnitInfo.getId().toString());
		}

		List monthList = new ArrayList();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("tasks.*"));
		view.getSelector().add(new SelectorItemInfo("adminDept.*"));
		view.getSelector().add(new SelectorItemInfo("tasks.schedule.adminDept.parent.*"));
		view.getSelector().add(new SelectorItemInfo("tasks.adminPerson.*"));
		view.getSelector().add(new SelectorItemInfo("tasks.schedule.*"));
		view.getSelector().add(new SelectorItemInfo("tasks.schedule.adminDept.*"));
		view.getSelector().add(new SelectorItemInfo("tasks.relatedTask.*"));
		view.getSelector().add(new SelectorItemInfo("tasks.project.*"));

		FilterInfo filter = new FilterInfo();
		if (null != id) {
			filter.getFilterItems().add(new FilterItemInfo("adminDept.id", set, CompareType.INCLUDE));
		}
		if (year != -1) {
			filter.getFilterItems().add(new FilterItemInfo("year", new Integer(year)));
		}
		if (month != -1) {
			filter.getFilterItems().add(new FilterItemInfo("month", new Integer(month)));
		}
		view.setFilter(filter);
		DeptMonthlyScheduleCollection collection = getDeptMonthlyScheduleCollection(ctx, view);
		if (collection.size() > 0) {
			for (int k = 0; k < collection.size(); k++) {
				DeptMonthlyScheduleInfo projectWeekReportInfo = collection.get(k);
				DeptMonthlyScheduleTaskCollection entrysColl = projectWeekReportInfo.getTasks();

				if (entrysColl.size() > 0) {
					for (int i = 0; i < entrysColl.size(); i++) {
						DeptMonthlyScheduleTaskInfo entryInfo = entrysColl.get(i);
						monthList.add(entryInfo);
					}
				}
			}
		}
		return monthList;
	}

	protected List _getAllTask(Context ctx, String id) throws BOSException {
		Set set = new HashSet();
		EntityViewInfo viewInfo = new EntityViewInfo();
		if (null != id) {
			set.add(id);
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("parent.id", id));
			viewInfo.setFilter(filterInfo);
		}
		AdminOrgUnitCollection adminOrgUnitCollection = AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitCollection(viewInfo);
		for (int k = 0; k < adminOrgUnitCollection.size(); k++) {
			AdminOrgUnitInfo adminOrgUnitInfo = adminOrgUnitCollection.get(k);
			set.add(adminOrgUnitInfo.getId().toString());
		}

		List monthList = new ArrayList();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("tasks.*"));
		view.getSelector().add(new SelectorItemInfo("adminDept.*"));
		view.getSelector().add(new SelectorItemInfo("tasks.adminPerson.*"));
		view.getSelector().add(new SelectorItemInfo("tasks.schedule.*"));
		view.getSelector().add(new SelectorItemInfo("tasks.schedule.adminDept.*"));
		view.getSelector().add(new SelectorItemInfo("tasks.schedule.adminDept.parent.*"));
		view.getSelector().add(new SelectorItemInfo("tasks.relatedTask.*"));
		view.getSelector().add(new SelectorItemInfo("tasks.project.*"));

		if (null != id) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("adminDept.id", set, CompareType.INCLUDE));
			view.setFilter(filter);
		}
		DeptMonthlyScheduleCollection collection = getDeptMonthlyScheduleCollection(ctx, view);
		if (collection.size() > 0) {
			for (int k = 0; k < collection.size(); k++) {
				DeptMonthlyScheduleInfo projectWeekReportInfo = collection.get(k);
				monthList.add(projectWeekReportInfo);
			}
		}
		return monthList;
	}

	protected void _submitForWF(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected IObjectValue _getValue(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		/* TODO 自动生成方法存根 */
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("scheduleMonth"));
		sic.add(new SelectorItemInfo("adminDept.*"));
		sic.add(new SelectorItemInfo("year"));
		sic.add(new SelectorItemInfo("month"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("tasks.taskType"));
		sic.add(new SelectorItemInfo("tasks.taskName"));
		sic.add(new SelectorItemInfo("tasks.finishStandard"));
		sic.add(new SelectorItemInfo("tasks.planFinishDate"));
		sic.add(new SelectorItemInfo("tasks.weightRate"));
		sic.add(new SelectorItemInfo("tasks.taskOrigin"));
		sic.add(new SelectorItemInfo("tasks.projectPeriod"));
		sic.add(new SelectorItemInfo("tasks.planStartDate"));
		sic.add(new SelectorItemInfo("tasks.requiredResource"));
		sic.add(new SelectorItemInfo("tasks.*"));
		// sic.add(new SelectorItemInfo("tasks.number"));
		sic.add(new SelectorItemInfo("tasks.id"));
		sic.add(new SelectorItemInfo("tasks.complete"));
		sic.add(new SelectorItemInfo("adminPerson.id"));
		sic.add(new SelectorItemInfo("adminPerson.name"));
		sic.add(new SelectorItemInfo("adminPerson.number"));
		sic.add(new SelectorItemInfo("tasks.adminPerson.id"));
		sic.add(new SelectorItemInfo("tasks.adminPerson.name"));
		sic.add(new SelectorItemInfo("tasks.adminPerson.number"));
		sic.add(new SelectorItemInfo("tasks.relatedTask.id"));
		sic.add(new SelectorItemInfo("tasks.relatedTask.name"));
		sic.add(new SelectorItemInfo("tasks.relatedTask.number"));
		sic.add(new SelectorItemInfo("tasks.project.*"));
		sic.add(new SelectorItemInfo("tasks.project.id"));
		sic.add(new SelectorItemInfo("tasks.project.name"));
		sic.add(new SelectorItemInfo("tasks.project.number"));
		SorterItemCollection sorter = new SorterItemCollection();
		return super._getValue(ctx, pk, sic, sorter);
	}
	
}