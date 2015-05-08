package com.kingdee.eas.fdc.schedule.client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.datasource.DSParam;
import com.kingdee.bos.ctrl.kdf.expr.Variant;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.framework.ScheduleDependHelper;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 进度计划套打数据源
 * <p>
 * 对前置任务，业务类型等做特殊处理
 * 
 * @author emanon
 * 
 */
public class FDCScheduleDataProvider extends FDCBillDataProvider {

	// 套打中，任务的前置任务，是否显示全名称
	boolean isDetail = false;

	public FDCScheduleDataProvider(String billId, IMetaDataPK mainQuery,
			boolean isShowDetailDependInPrint) {
		super(billId, mainQuery);
		this.isDetail = isShowDetailDependInPrint;
	}

	public IRowSet execute(BOSQueryDataSource ds) {
		Variant paramVal = null;
		ArrayList ps = ds.getParams();
		IRowSet iRowSet = null;
		if (ps.size() > 0) {
			DSParam param = (DSParam) ps.get(0);
			paramVal = param.getValue();
		}

		String dsID = ds.getID().toUpperCase();
		// 主数据FDCScheduleTDQuery
		if ("FDCSCHEDULETDQUERY".equals(dsID)) {
			// 返加主数据源的结果集
			iRowSet = getMainBillRowSet(ds);
		} else if (dsID.startsWith("AUDITINFO")) {
			// 返回参数值为paramVal的从数据源1的结果集
			return getAuditInfoRowSet(ds);
		} else {
			return getOtherSubRowSet(ds);
		}
		return iRowSet;
	}

	/**
	 * 默认主数据源为FDCScheduleTDQuery
	 * <p>
	 * 由于业务类型、前置任务等字段需要把分录合并成一个字段，这里对结果集再进行处理<br>
	 * 如果主数据源不是FDCScheduleTDQuery，无法保证包含这些要处理的字段，所以写死<br>
	 */
	public IRowSet getMainBillRowSet(BOSQueryDataSource ds) {
		IRowSet MRowSet = super.getMainBillRowSet(ds);
		if (MRowSet == null || MRowSet.size() < 1) {
			return MRowSet;
		}
		try {
			// 取得所以任务ID
			Set ids = new HashSet();
			while (MRowSet.next()) {
				ids.add(MRowSet.getString("taskEntrys.id"));
			}
			MRowSet.beforeFirst();
			if (ids.size() < 1) {
				return MRowSet;
			}
			// 查找这些任务的前置任务及业务类型
			FDCScheduleTaskCollection taskCol = getTaskDetails(ids);
			// 将前置任务和业务类型转换成字符串形式，用逗号分隔
			if (taskCol != null && taskCol.size() > 0) {
				Map depMap = new HashMap();
				Map bizMap = new HashMap();
				for (int i = 0; i < taskCol.size(); i++) {
					FDCScheduleTaskInfo taskInfo = taskCol.get(i);
					// 处理前置任务字符串
					handleDepStr(depMap, taskInfo);
					// 处理业务类型字符串
					handleBizStr(bizMap, taskInfo);
				}
				// 将字符串更新到结果集
				updateRS(MRowSet, depMap, bizMap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return MRowSet;
	}

	/**
	 * 查找套打所必须的信息
	 * <p>
	 * 
	 * @param ids
	 * @return
	 * @throws BOSException
	 */
	private FDCScheduleTaskCollection getTaskDetails(Set ids)
			throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("id");
		sic.add("dependEntrys.id");
		sic.add("dependEntrys.type");
		sic.add("dependEntrys.difference");
		sic.add("dependEntrys.task.id");
		sic.add("dependEntrys.task.seq");
		sic.add("dependEntrys.task.name");
		sic.add("dependEntrys.dependTask.id");
		sic.add("dependEntrys.dependTask.seq");
		sic.add("dependEntrys.dependTask.name");
		sic.add("bizType.id");
		sic.add("bizType.bizType.id");
		sic.add("bizType.bizType.name");
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", ids, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSorter().add(new SorterItemInfo("longNumber"));
		FDCScheduleTaskCollection taskCol = FDCScheduleTaskFactory
				.getRemoteInstance().getFDCScheduleTaskCollection(view);
		return taskCol;
	}

	/**
	 * 处理前置任务字符串
	 * 
	 * @param depMap
	 * @param taskInfo
	 */
	private void handleDepStr(Map depMap, FDCScheduleTaskInfo taskInfo) {
		FDCScheduleTaskDependCollection depCol = taskInfo.getDependEntrys();
		if (depCol != null && depCol.size() > 0) {
			for (int j = 0; j < depCol.size(); j++) {
				FDCScheduleTaskDependInfo depInfo = depCol.get(j);
				String depID = depInfo.getDependTask().getId().toString();
				String dep = isDetail ? depInfo.getTask().getName() : String
						.valueOf(depInfo.getTask().getSeq());
				String type = ScheduleDependHelper.getSimpleName(depInfo
						.getType());
				int diff = depInfo.getDifference();
				String strDep = (String) depMap.get(depID);
				if (FDCHelper.isEmpty(strDep)) {
					strDep = ScheduleDependHelper.getDependStr(dep, type, diff);
				} else {
					strDep = strDep
							+ ","
							+ ScheduleDependHelper
									.getDependStr(dep, type, diff);
				}
				depMap.put(depID, strDep);
			}
		}
	}

	/**
	 * 处理业务类型字符串
	 * 
	 * @param bizMap
	 * @param taskInfo
	 */
	private void handleBizStr(Map bizMap, FDCScheduleTaskInfo taskInfo) {
		ScheduleTaskBizTypeCollection bizCol = taskInfo.getBizType();
		if (bizCol != null && bizCol.size() > 0) {
			String taskID = taskInfo.getId().toString();
			for (int j = 0; j < bizCol.size(); j++) {
				String bizName = bizCol.get(j).getBizType().getName();
				String strBiz = (String) bizMap.get(taskID);
				if (FDCHelper.isEmpty(strBiz)) {
					strBiz = bizName;
				} else {
					strBiz = strBiz + "," + bizName;
				}
				bizMap.put(taskID, strBiz);
			}
		}
	}

	/**
	 * 将字符串更新到结果集
	 * 
	 * @param MRowSet
	 * @param depMap
	 * @param bizMap
	 * @throws SQLException
	 */
	private void updateRS(IRowSet MRowSet, Map depMap, Map bizMap)
			throws SQLException {
		if (depMap.size() > 0 || bizMap.size() > 0) {
			while (MRowSet.next()) {
				String taskID = MRowSet.getString("taskEntrys.id");
				MRowSet.updateString("taskEntrys.dependTasks", (String) depMap
						.get(taskID));
				MRowSet.updateString("taskEntrys.bizType", (String) bizMap
						.get(taskID));
			}
			MRowSet.beforeFirst();
		}
	}

}
