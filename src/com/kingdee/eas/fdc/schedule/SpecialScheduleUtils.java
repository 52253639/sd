package com.kingdee.eas.fdc.schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;

public class SpecialScheduleUtils {
	
	public static void reBuilderSpecialSchedule(Context ctx, CurProjectInfo curProject, ProjectSpecialInfo special, FDCScheduleInfo schedule)
			throws BOSException {
		Map mScheduleMap = getMainScheduleMap(ctx, curProject.getId().toString(), special.getId().toString());
		if (mScheduleMap.isEmpty()) {
			return;
		}
		FDCScheduleTaskCollection newTaskCols = new FDCScheduleTaskCollection();
		FDCScheduleTaskInfo taskInfo = null;
		Map alreadyAddSchedule = new HashMap();
		FDCScheduleTaskInfo tempTask = null;
		FDCScheduleTaskCollection taskCols = schedule.getTaskEntrys();

		for (int i = 0; i < taskCols.size(); i++) {
			taskInfo = taskCols.get(i);
			if (taskInfo.getDependMainTaskID() != null) {
				String mainScheduleId = taskInfo.getDependMainTaskID().getSrcID().toString();
				if (mScheduleMap.get(mainScheduleId) != null) {
					tempTask = (FDCScheduleTaskInfo) mScheduleMap.get(mainScheduleId);
					tempTask.setIsLeaf(false);
					tempTask.setLevel(1);
					if (!alreadyAddSchedule.containsKey(mainScheduleId)) {
						newTaskCols.add(tempTask);
					}
					if (RESchTaskTypeEnum.MILESTONE.equals(tempTask.getTaskType())) {
						tempTask.setTaskType(RESchTaskTypeEnum.KEY);
					}
					taskInfo.setBoundStart(tempTask.getStart());
					taskInfo.setBoundEnd(tempTask.getEnd());
					taskInfo.setParent(tempTask);
					taskInfo.setLevel(2);
					alreadyAddSchedule.put(mainScheduleId, mainScheduleId);
					newTaskCols.add(taskInfo);
					// TODO
					i += recursiveChildren(taskCols, taskInfo, newTaskCols, i);
					continue;
				}
			}
			newTaskCols.add(taskInfo);
		}

		FDCScheduleTaskCollection mainTaskCol = new FDCScheduleTaskCollection();
		Set scheduleSet = mScheduleMap.entrySet();
		for (Iterator it = scheduleSet.iterator(); it.hasNext();) {
			Entry entry = (Entry) it.next();
			if (!alreadyAddSchedule.containsKey(entry.getKey())) {
				tempTask = (FDCScheduleTaskInfo) entry.getValue();
				if (RESchTaskTypeEnum.MILESTONE.equals(tempTask.getTaskType())) {
					tempTask.setTaskType(RESchTaskTypeEnum.KEY);
				}
				mainTaskCol.add(tempTask);
			}
		}
		List mainTaskList = sortMainTask(mainTaskCol);
		for (int i = 0; i < mainTaskList.size(); ++i) {
			newTaskCols.add((FDCScheduleTaskInfo) mainTaskList.get(i));
		}
		schedule.getTaskEntrys().clear();
		schedule.getTaskEntrys().addCollection(newTaskCols);
		for (int i = 0; i < newTaskCols.size(); ++i) {
			newTaskCols.get(i).setSeq(i + 1);
		}
	}

	private static Map getMainScheduleMap(Context ctx, String projectId, String specialId) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		view.setSelector(ScheduleHelper.getSelector());
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("schedule.project.id", projectId));
		filter.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("belongToSpecial.id", specialId));
		SorterItemCollection sorts = new SorterItemCollection();
		SorterItemInfo sortItem = new SorterItemInfo("longnumber");
		sortItem.setSortType(SortType.ASCEND);
		sorts.add(sortItem);
		view.setSorter(sorts);
		view.setFilter(filter);
		
		FDCScheduleTaskCollection cols = null;
		if (ctx == null)
			cols = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
		else
			cols = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
		Map mScheduleMap = new HashMap();
		FDCScheduleTaskInfo taskInfo = null;
		for (int i = 0; i < cols.size(); i++) {
			taskInfo = cols.get(i);
			taskInfo.put("isInSpecial", true);
			taskInfo.getDependEntrys().clear();
			mScheduleMap.put(taskInfo.getSrcID().toString(), taskInfo);
		}
		// srcid->task
		return mScheduleMap;
	}

	private static int recursiveChildren(FDCScheduleTaskCollection taskCols, FDCScheduleTaskInfo parentTask, FDCScheduleTaskCollection newTaskCols, int index) {
		int childrenCount = 0;
		for (int i = index + 1; i < taskCols.size(); ++i) {
			FDCScheduleTaskInfo task = taskCols.get(i);
			FDCScheduleTaskInfo tempParent = task.getParent();
			if (tempParent != null && tempParent.getId().toString().equals(parentTask.getId().toString())) {
				task.setLevel(parentTask.getLevel() + 1);
				task.setBoundStart(parentTask.getBoundStart());
				task.setBoundEnd(parentTask.getBoundEnd());
				newTaskCols.add(task);
				childrenCount++;
				childrenCount += recursiveChildren(taskCols, task, newTaskCols, i);
			}
		}
		return 0;
	}

	private static List sortMainTask(FDCScheduleTaskCollection mainTaskCol) {
		List ret = new ArrayList();
		for (int i = 0; i < mainTaskCol.size(); ++i) {
			ret.add(mainTaskCol.get(i));
		}
		Collections.sort(ret, new Comparator() {
			public int compare(Object o1, Object o2) {
				FDCScheduleTaskInfo src = (FDCScheduleTaskInfo) o1;
				FDCScheduleTaskInfo target = (FDCScheduleTaskInfo) o2;
				return src.getLongNumber().compareTo(target.getLongNumber());
			}
		});
		return ret;
	}

}

