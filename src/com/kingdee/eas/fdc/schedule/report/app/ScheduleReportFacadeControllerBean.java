package com.kingdee.eas.fdc.schedule.report.app;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.ejb.EJBFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.schedule.FDCScheduleConstant;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.fdc.schedule.ScheduleHelper;
import com.kingdee.eas.fdc.schedule.report.ScheduleReportOrgFactory;
import com.kingdee.eas.fdc.schedule.report.ScheduleReportOrgInfo;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.db.SQLUtils;

public class ScheduleReportFacadeControllerBean extends AbstractScheduleReportFacadeControllerBean {

	private static final long serialVersionUID = -501319729693419815L;

	private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.schedule.app.report.ScheduleReportFacadeControllerBean");

	// 小数点控制位数
	private static final int DIVIDE_SCALE = 4;

	/**
	 * 获取除了专项计划外的所有数据信息
	 * @see com.kingdee.eas.fdc.schedule.app.report.AbstractScheduleReportFacadeControllerBean#_getScheduleReportData(com.kingdee.bos.Context, java.util.Map, java.util.Map, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Map<String, String>> _getScheduleReportData(Context ctx, Map orgIds, Map dates, String scheduleType)
			throws BOSException, EASBizException {

		if (orgIds == null) {
			return null;
		}
		Map<String, Map<String, String>> resultDataMap = new HashMap<String, Map<String, String>>();

		Iterator iter = orgIds.entrySet().iterator();
		List<String> listOrg = null;
		Map.Entry<String, Map<String, String>> mapByOrg = null;
		while (iter.hasNext()) {
			// 此处组织或工程ID按照<组织ID,<子节点ID,子节点名称>>
			mapByOrg = (Map.Entry<String, Map<String, String>>) iter.next();
			String typeId = mapByOrg.getKey();
			listOrg = new ArrayList<String>();
			String nodeName = null;
			String nodeLevel = null;
			for (Iterator orgIter = mapByOrg.getValue().entrySet().iterator(); orgIter.hasNext();) {
				Map.Entry<String, String> map = (Map.Entry<String, String>) orgIter.next();
				String orgID = map.getKey();
				listOrg.add(orgID);
				if (nodeName == null) {
					nodeName = mapByOrg.getValue().get(FDCScheduleConstant.CHART_X_AXIS_NAME);
				}
				if(nodeLevel == null){
					nodeLevel = mapByOrg.getValue().get(FDCScheduleConstant.CHART_SELECT_SORTED_ORG);
				}
			}

			// 获取完成日期的条件过滤（开始日期和结束日期）
			Date startDate = (Date) dates.get(FDCScheduleConstant.REPORT_CHART_START_DATE);
			Date endDate = (Date) dates.get(FDCScheduleConstant.REPORT_CHART_END_DATE);

			// 根据scheduleType来区分类型统计（考核节点、主项、专项）
			String timedCompSQL = getAllTableChartSQL(scheduleType, FDCScheduleConstant.REPORT_CHART_TIMMED_COMP, listOrg);
			String fifInCompSQL = getAllTableChartSQL(scheduleType, FDCScheduleConstant.REPORT_CHART_FIF_IN_COMP, listOrg);
			String fifOutCompSQL = getAllTableChartSQL(scheduleType, FDCScheduleConstant.REPORT_CHART_FIF_OUT_COMP, listOrg);
			String unCompSQL = getAllTableChartSQL(scheduleType, FDCScheduleConstant.REPORT_CHART_UNCOMP, listOrg);
			String confirmedCompSQL = getAllTableChartSQL(scheduleType, FDCScheduleConstant.REPORT_CHART_CONFIRMED_COMP, listOrg);
			//TODO 计划完成
			String plannedCompSQL = getAllTableChartSQL(scheduleType, FDCScheduleConstant.REPORT_CHART_PLANNED_COMP, listOrg);

			// 分别为按时完成、延迟15天内完成、延迟15天外完成、未完成、待确认MAP
			Map<String, Map<String, String>> timedCompMap = getEachTableChartData(ctx, timedCompSQL, startDate, endDate,
					FDCScheduleConstant.REPORT_CHART_TIMMED_COMP);
			Map<String, Map<String, String>> fifInCompMap = getEachTableChartData(ctx, fifInCompSQL, startDate, endDate,
					FDCScheduleConstant.REPORT_CHART_FIF_IN_COMP);
			Map<String, Map<String, String>> fifOutCompMap = getEachTableChartData(ctx, fifOutCompSQL, startDate, endDate,
					FDCScheduleConstant.REPORT_CHART_FIF_OUT_COMP);
			Map<String, Map<String, String>> unCompMap = getEachTableChartData(ctx, unCompSQL, startDate, endDate,
					FDCScheduleConstant.REPORT_CHART_UNCOMP);
			Map<String, Map<String, String>> confirmedCompMap = getEachTableChartData(ctx, confirmedCompSQL, startDate, endDate,
					FDCScheduleConstant.REPORT_CHART_CONFIRMED_COMP);
			Map<String, Map<String, String>> plannedCompMap = getEachTableChartData(ctx, plannedCompSQL, startDate, endDate,
					FDCScheduleConstant.REPORT_CHART_PLANNED_COMP);

			List<Map<String, Map<String, String>>> mapList = getChartListMap(timedCompMap, fifInCompMap, fifOutCompMap, unCompMap,
					confirmedCompMap,plannedCompMap );

			List<String> chartTypeList = getTypeList();

			// 获取不同类型的统计数据
			Map<String, String> scheduleDataMap = getOneOrgOrProData(mapList, chartTypeList, ctx);
			scheduleDataMap.put(FDCScheduleConstant.REPORT_CHART_PROJECT_NAME, nodeName);
			scheduleDataMap.put(FDCScheduleConstant.CHART_SELECT_SORTED_ORG, nodeLevel);
			scheduleDataMap.put(FDCScheduleConstant.REPORT_CHART_ID, typeId);

			// 统计计划完成及完成率数据
			getCalculateReportData(scheduleDataMap);

			resultDataMap.put(typeId, scheduleDataMap);
		}

		return resultDataMap;
	}

	/**
	 * 获取专项计划数据信息
	 * @see com.kingdee.eas.fdc.schedule.app.report.AbstractScheduleReportFacadeControllerBean#_getScheduleExpertReportData(com.kingdee.bos.Context, java.util.Date, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Map<String, Map<String, String>> _getScheduleExpertReportData(Context ctx, Map dates, String projectID) throws BOSException {
		
		Object startDateObj = dates.get(FDCScheduleConstant.REPORT_CHART_START_DATE);
		Object endDateObj = dates.get(FDCScheduleConstant.REPORT_CHART_END_DATE);
		
		// 是否根据日期来查询数据
		boolean hasDate = true;
		Date startDate = null;
		Date endDate = null;
		if(startDateObj == null && endDateObj == null){
			hasDate = false;
		}else{
			// 获取完成日期的条件过滤（开始日期和结束日期）
			startDate = (Date) startDateObj;
			endDate = (Date) endDateObj;
		}
//		projectID = "VvdNtwW7Scug3L2ju+Ayivnl6Ss=";

		// 根据scheduleType来区分类型统计（考核节点、主项、专项）
		String timedCompSQL = getExpertPlanCompSQL(FDCScheduleConstant.REPORT_CHART_TIMMED_COMP,hasDate);
		String fifInCompSQL = getExpertPlanCompSQL(FDCScheduleConstant.REPORT_CHART_FIF_IN_COMP,hasDate);
		String fifOutCompSQL = getExpertPlanCompSQL(FDCScheduleConstant.REPORT_CHART_FIF_OUT_COMP,hasDate);
		String unCompSQL = getExpertPlanCompSQL(FDCScheduleConstant.REPORT_CHART_UNCOMP,hasDate);
		String confirmedCompSQL = getExpertPlanCompSQL(FDCScheduleConstant.REPORT_CHART_CONFIRMED_COMP,hasDate);
		//TODO 计划完成
		String plannedCompSQL = getExpertPlanCompSQL(FDCScheduleConstant.REPORT_CHART_PLANNED_COMP, hasDate);
		
		// 分别为按时完成、延迟15天内完成、延迟15天外完成、未完成、待确认MAP
		Map<String, Map<String, String>> timedCompMap = getExpertTableChartData(ctx, timedCompSQL, startDate, endDate,
				FDCScheduleConstant.REPORT_CHART_TIMMED_COMP,projectID,hasDate);
		Map<String, Map<String, String>> fifInCompMap = getExpertTableChartData(ctx, fifInCompSQL, startDate, endDate,
				FDCScheduleConstant.REPORT_CHART_FIF_IN_COMP,projectID,hasDate);
		Map<String, Map<String, String>> fifOutCompMap = getExpertTableChartData(ctx, fifOutCompSQL, startDate, endDate,
				FDCScheduleConstant.REPORT_CHART_FIF_OUT_COMP,projectID,hasDate);
		Map<String, Map<String, String>> unCompMap = getExpertTableChartData(ctx, unCompSQL, startDate, endDate,
				FDCScheduleConstant.REPORT_CHART_UNCOMP,projectID,hasDate);
		Map<String, Map<String, String>> confirmedCompMap = getExpertTableChartData(ctx, confirmedCompSQL, startDate, endDate,
				FDCScheduleConstant.REPORT_CHART_CONFIRMED_COMP,projectID,hasDate);
		Map<String, Map<String, String>> plannedCompMap = getExpertTableChartData(ctx, plannedCompSQL, startDate, endDate,
				FDCScheduleConstant.REPORT_CHART_PLANNED_COMP,projectID,hasDate);
		
		List<Map<String, Map<String, String>>> mapList = getChartListMap(timedCompMap, fifInCompMap, fifOutCompMap, unCompMap,
				confirmedCompMap,plannedCompMap);

		List<String> chartTypeList = getTypeList();

		// 获取不同类型的统计数据
		Map<String, Map<String, String>> scheduleDataMap = getScheduleReportCompData(mapList, chartTypeList, ctx);

		return scheduleDataMap;
	}

	/**
	 * 描述：获取不同类型的统计数据
	 * @param mapList
	 * @param typeList
	 * @return
	 * @throws BOSException 
	 * @Author：adward_huang
	 * @CreateTime：2012-9-19
	 */
	private Map<String, Map<String, String>> getScheduleReportCompData(List<Map<String, Map<String, String>>> mapList,
			List<String> typeList, Context ctx) throws BOSException {

		// 保存所有结果集<id,Map<"timedComp","100">>
		Map<String, Map<String, String>> scheduleDataMap = new HashMap<String, Map<String, String>>();
		int mapSize = mapList.size();

		// 针对工程ID获取工程名的集合
		List<String> scheduleIDList = new ArrayList<String>();

		// 在集合中是否存在key为ID的value
		boolean hasIDInMap = false;

		for (int i = 0; i < mapSize; i++) {

			Map<String, Map<String, String>> map = mapList.get(i);

			if (map != null && !map.isEmpty()) {

				String typeName = typeList.get(i);
				Iterator<Map.Entry<String, Map<String, String>>> iter = map.entrySet().iterator();
				Map<String, String> chartDataMap = null;
				BigDecimal typeCount = new BigDecimal("0");
				while (iter.hasNext()) {
					//				for(Iterator<Map.Entry<String, Map<String, String>>> iter = map.entrySet().iterator();iter.hasNext();){
					Map.Entry<String, Map<String, String>> dataMap = iter.next();
					// 获取进度计划ID
					String id = dataMap.getKey();
					// 获取结果集中map，将计划完成、延迟15天内完成等数据进行封装
					if (scheduleDataMap.containsKey(id)) {
						chartDataMap = scheduleDataMap.get(id);
						hasIDInMap = true;
					} else {
						chartDataMap = new HashMap<String, String>();
						hasIDInMap = false;
					}
					Map<String, String> countMap = dataMap.getValue();
					// 根据进度ID获取进度名称
					if (!scheduleIDList.contains(id)) {
						String projectID = countMap.get(FDCScheduleConstant.REPORT_CHART_PROJECT_ID);
						String projectName;
						if(!FDCScheduleConstant.REPORT_CHART_SCHEDULE_ALL_EXPERT_PLAN_ID.equals(projectID)){
							projectName = getScheduleExpertNameByID(ctx, projectID);
						}else{
							projectName = FDCScheduleConstant.REPORT_CHART_SCHEDULE_ALL_EXPERT_PLAN_NAME;
						}
						scheduleIDList.add(id);
						chartDataMap.put(FDCScheduleConstant.REPORT_CHART_ID, id);
						chartDataMap.put(FDCScheduleConstant.REPORT_CHART_PROJECT_ID, projectID);
						chartDataMap.put(FDCScheduleConstant.REPORT_CHART_PROJECT_NAME, projectName);
					}

					String count = getChartMapValue(countMap, typeName);
					typeCount = typeCount.add(new BigDecimal(count));
					chartDataMap.put(typeName, count);
					if (!hasIDInMap) {
						scheduleDataMap.put(id, chartDataMap);
					}
				}
				Map<String, String> allExpertMap;
				if(scheduleDataMap.containsKey(FDCScheduleConstant.REPORT_CHART_SCHEDULE_ALL_EXPERT_PLAN_ID)){
					allExpertMap = scheduleDataMap.get(FDCScheduleConstant.REPORT_CHART_SCHEDULE_ALL_EXPERT_PLAN_ID);
					allExpertMap.put(typeName, typeCount+"");
				}else{
					allExpertMap = new HashMap<String,String>();
					allExpertMap.put(typeName, typeCount+"");
					allExpertMap.put(FDCScheduleConstant.REPORT_CHART_ID, FDCScheduleConstant.REPORT_CHART_SCHEDULE_ALL_EXPERT_PLAN_ID);
					allExpertMap.put(FDCScheduleConstant.REPORT_CHART_PROJECT_ID, FDCScheduleConstant.REPORT_CHART_SCHEDULE_ALL_EXPERT_PLAN_ID);
					allExpertMap.put(FDCScheduleConstant.REPORT_CHART_PROJECT_NAME, FDCScheduleConstant.REPORT_CHART_SCHEDULE_ALL_EXPERT_PLAN_NAME);
					scheduleDataMap.put(FDCScheduleConstant.REPORT_CHART_SCHEDULE_ALL_EXPERT_PLAN_ID, allExpertMap);
				}
			}
		}

		// 统计计划完成及完成率数据
		getAllScheduleReportData(scheduleDataMap);

		return scheduleDataMap;
	}

	/**
	 * 描述：获取不同类型的统计数据
	 * @param mapList
	 * @param typeList
	 * @return
	 * @throws BOSException 
	 * @Author：adward_huang
	 * @CreateTime：2012-9-19
	 */
	private Map<String, String> getOneOrgOrProData(List<Map<String, Map<String, String>>> mapList, List<String> typeList, Context ctx)
			throws BOSException {

		// 单个组织或项目的不同统计类型的数据集合，如按时完成、计划完成、延迟15天内完成等
		Map<String, String> resultMap = new HashMap<String, String>();
		int mapSize = mapList.size();
		for (int i = 0; i < mapSize; i++) {

			Map<String, Map<String, String>> map = mapList.get(i);
			String typeName = typeList.get(i);
			if (map != null && !map.isEmpty()) {

				BigDecimal typeCount = new BigDecimal("0");
				Iterator<Map.Entry<String, Map<String, String>>> iter = map.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry<String, Map<String, String>> dataMap = iter.next();
					Map<String, String> countMap = dataMap.getValue();

					String count = getChartMapValue(countMap, typeName);
					typeCount = typeCount.add(new BigDecimal(count));
				}
				resultMap.put(typeName, typeCount + "");
			} else {
				resultMap.put(typeName, "0");
			}
		}

		return resultMap;
	}

	/**
	 * 描述：对不同类型的统计数据进行汇总统计及计算完成率
	 * @param scheduleDataMap
	 * @Author：adward_huang
	 * @CreateTime：2012-9-19
	 */
	private void getAllScheduleReportData(Map<String, Map<String, String>> scheduleDataMap) {

		if (scheduleDataMap != null && !scheduleDataMap.isEmpty()) {

			Iterator<Map.Entry<String, Map<String, String>>> iter = scheduleDataMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, Map<String, String>> dataMap = iter.next();

				// 获取结果集中map，将计划完成、延迟15天内完成等数据封装进去
				Map<String, String> chartDataMap = dataMap.getValue();

				String timedCount = getChartMapValue(chartDataMap, FDCScheduleConstant.REPORT_CHART_TIMMED_COMP);
				String fifInCount = getChartMapValue(chartDataMap, FDCScheduleConstant.REPORT_CHART_FIF_IN_COMP);
				String fifOutCount = getChartMapValue(chartDataMap, FDCScheduleConstant.REPORT_CHART_FIF_OUT_COMP);
				String unCount = getChartMapValue(chartDataMap, FDCScheduleConstant.REPORT_CHART_UNCOMP);
				String confirmedCount = getChartMapValue(chartDataMap, FDCScheduleConstant.REPORT_CHART_CONFIRMED_COMP);
				String plannedCompCount = getChartMapValue(chartDataMap,FDCScheduleConstant.REPORT_CHART_PLANNED_COMP);

				BigDecimal timedCountBigDecimal = new BigDecimal(timedCount);
				BigDecimal fifInCountBigDecimal = new BigDecimal(fifInCount);
				BigDecimal fifOutCountBigDecimal = new BigDecimal(fifOutCount);
				BigDecimal unCountBigDecimal = new BigDecimal(unCount);
				BigDecimal plannedCompCountBigDecimal = new BigDecimal(plannedCompCount);

				// 待确认 = 完成数 - 按时完成 - 延迟15天内完成-15天外完成
				BigDecimal confirmedCountBigDecimal = new BigDecimal(confirmedCount).subtract(timedCountBigDecimal).subtract(
						fifInCountBigDecimal).subtract(fifOutCountBigDecimal);

				// 计划完成
				BigDecimal plannedComp = plannedCompCountBigDecimal;
//				= timedCountBigDecimal
//				.add(fifInCountBigDecimal)
//				.add(fifOutCountBigDecimal)
//				.add(unCountBigDecimal)
//				.add(confirmedCountBigDecimal);

				// 按时完成率
				BigDecimal timedCompRate;
				if (timedCountBigDecimal.intValue() != 0 && plannedComp.intValue() != 0) {
					// 按时完成率
					timedCompRate = timedCountBigDecimal.divide(plannedComp, DIVIDE_SCALE, BigDecimal.ROUND_HALF_UP);
				} else {
					timedCompRate = BigDecimal.ZERO;
				}

				// 延迟完成
				BigDecimal allComp = timedCountBigDecimal.add(fifInCountBigDecimal).add(fifOutCountBigDecimal);
				// 延迟完成率
				BigDecimal delayedCompRate;
				if (allComp.intValue() != 0 && plannedComp.intValue() != 0) {
					// 按时完成率
					delayedCompRate = allComp.divide(plannedComp, DIVIDE_SCALE, BigDecimal.ROUND_HALF_UP);
				} else {
					delayedCompRate = BigDecimal.ZERO;
				}

				// 重新计算后的待确认数据
				chartDataMap.put(FDCScheduleConstant.REPORT_CHART_CONFIRMED_COMP, confirmedCountBigDecimal + "");
				chartDataMap.put(FDCScheduleConstant.REPORT_CHART_PLANNED_COMP, plannedComp + "");
				chartDataMap.put(FDCScheduleConstant.REPORT_CHART_TIMED_COMP_RATE, timedCompRate + "");
				chartDataMap.put(FDCScheduleConstant.REPORT_CHART_DELAYED_COMP_RATE, delayedCompRate + "");
			}
		}
	}

	/**
	 * 描述：对不同类型的统计数据进行汇总统计及计算完成率
	 * @param scheduleDataMap
	 * @Author：adward_huang
	 * @CreateTime：2012-9-19
	 */
	private void getCalculateReportData(Map<String, String> orgOrProData) {

		if (orgOrProData != null && !orgOrProData.isEmpty()) {

			BigDecimal timedCountBigDecimal = new BigDecimal(orgOrProData.get(FDCScheduleConstant.REPORT_CHART_TIMMED_COMP));
			BigDecimal fifInCountBigDecimal = new BigDecimal(orgOrProData.get(FDCScheduleConstant.REPORT_CHART_FIF_IN_COMP));
			BigDecimal fifOutCountBigDecimal = new BigDecimal(orgOrProData.get(FDCScheduleConstant.REPORT_CHART_FIF_OUT_COMP));
			BigDecimal unCountBigDecimal = new BigDecimal(orgOrProData.get(FDCScheduleConstant.REPORT_CHART_UNCOMP));
			BigDecimal confirmedCountUnCalculate = new BigDecimal(orgOrProData.get(FDCScheduleConstant.REPORT_CHART_CONFIRMED_COMP));
			BigDecimal plannedCompCountBigDecimal = new BigDecimal(orgOrProData.get(FDCScheduleConstant.REPORT_CHART_PLANNED_COMP));

			// 待确认 = 待确认（未计算） - 按时完成 - 延迟15天内完成-15天外完成
			BigDecimal confirmedCountBigDecimal = confirmedCountUnCalculate.subtract(timedCountBigDecimal).subtract(fifInCountBigDecimal)
					.subtract(fifOutCountBigDecimal);

			// 计划完成
			BigDecimal plannedComp = plannedCompCountBigDecimal;
//			= timedCountBigDecimal
//			.add(fifInCountBigDecimal)
//			.add(fifOutCountBigDecimal)
//			.add(unCountBigDecimal)
//			.add(confirmedCountBigDecimal);

			// 按时完成率
			BigDecimal timedCompRate;
			if (timedCountBigDecimal.intValue() != 0 && plannedComp.intValue() != 0) {
				// 按时完成率
				timedCompRate = timedCountBigDecimal.divide(plannedComp, DIVIDE_SCALE, BigDecimal.ROUND_HALF_UP);
			} else {
				timedCompRate = BigDecimal.ZERO;
			}

			// 延迟完成
			BigDecimal allComp = timedCountBigDecimal.add(fifInCountBigDecimal).add(fifOutCountBigDecimal);
			// 延迟完成率
			BigDecimal delayedCompRate;
			if (allComp.intValue() != 0 && plannedComp.intValue() != 0) {
				// 按时完成率
				delayedCompRate = allComp.divide(plannedComp, DIVIDE_SCALE, BigDecimal.ROUND_HALF_UP);
			} else {
				delayedCompRate = BigDecimal.ZERO;
			}

			// 重新计算后的待确认数据
			orgOrProData.put(FDCScheduleConstant.REPORT_CHART_CONFIRMED_COMP, confirmedCountBigDecimal + "");
			orgOrProData.put(FDCScheduleConstant.REPORT_CHART_PLANNED_COMP, plannedComp + "");
			orgOrProData.put(FDCScheduleConstant.REPORT_CHART_TIMED_COMP_RATE, timedCompRate + "");
			orgOrProData.put(FDCScheduleConstant.REPORT_CHART_DELAYED_COMP_RATE, delayedCompRate + "");
		}
	}

	/*
	 * 根据类型获取Map中值，同时制定默认值
	 */
	private String getChartMapValue(Map<String, String> map, String chartType) {
		String value = "0";
		if (map != null && !map.isEmpty()) {
			value = map.get(chartType);
			if (value == null) {
				value = "0";
				map.put(chartType, value);
			}
		}
		return value;
	}

	/**
	 * 描述：根据每个类别的图形获取相应的数据
	 * @param ctx
	 * @param sql
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws BOSException
	 * @Author：adward_huang
	 * @CreateTime：2012-9-13
	 */
	private Map<String, Map<String, String>> getEachTableChartData(Context ctx, String sql, Date startDate, Date endDate, String chartType)
			throws BOSException {

		//IRowSet rs = DbUtil.executeQuery(ctx, sql, new Date[] { startDate, endDate });

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Map<String, Map<String, String>> scheduleMap = new HashMap<String, Map<String, String>>();
		;
		try {
			conn = EJBFactory.getConnection(ctx);
			pst = conn.prepareStatement(sql);
			pst.setTimestamp(1, new java.sql.Timestamp(getStartDate(startDate)));
			pst.setTimestamp(2, new java.sql.Timestamp(getEndDate(endDate)));
			logger.error("schedulerptdebug-----start");
			logger.error("executeSQL:"+sql);
			logger.error("params-startDate:"+startDate);
			logger.error("params-EndDate:"+endDate);
			logger.error("schedulerptdebug-----end");
			rs = pst.executeQuery();

			while (rs != null && rs.next()) {
				Map<String, String> dataMap = new HashMap<String, String>();
				String id = rs.getString("fid");
				String projectID = rs.getString("fprojectid");
				int dataCount = rs.getInt("dataCount");

				dataMap.put(FDCScheduleConstant.REPORT_CHART_ID, id);
				dataMap.put(FDCScheduleConstant.REPORT_CHART_PROJECT_ID, projectID);
				dataMap.put(chartType, dataCount + "");
				scheduleMap.put(id, dataMap);
			}
		} catch (SQLException e) {
			SQLUtils.cleanup(conn);
			throw new BOSException("Schedule Report SQLException", e);
		} finally {
			SQLUtils.cleanup(rs, pst, conn);
		}

		return scheduleMap;
	}

	/**
	 * 描述：获取专项相应的数据
	 * @param ctx
	 * @param sql
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws BOSException
	 * @Author：adward_huang
	 * @CreateTime：2012-9-13
	 */
	private Map<String, Map<String, String>> getExpertTableChartData(Context ctx, String sql, Date startDate, 
			Date endDate, String chartType,String projectID, boolean flag)
			throws BOSException {

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Map<String, Map<String, String>> scheduleMap = new HashMap<String, Map<String, String>>();
		
		try {
			conn = EJBFactory.getConnection(ctx);
			pst = conn.prepareStatement(sql);
			if(flag){
				pst.setString(1, projectID);
				pst.setTimestamp(2, new java.sql.Timestamp(getStartDate(startDate)));
				pst.setTimestamp(3, new java.sql.Timestamp(getEndDate(endDate)));
			}else{
				pst.setString(1, projectID);
			}
			rs = pst.executeQuery();

			while (rs != null && rs.next()) {
				Map<String, String> dataMap = new HashMap<String, String>();
				String id = rs.getString("fid");
				int dataCount = rs.getInt("dataCount");

				dataMap.put(FDCScheduleConstant.REPORT_CHART_ID, id);
				dataMap.put(FDCScheduleConstant.REPORT_CHART_PROJECT_ID, id);
				dataMap.put(chartType, dataCount + "");
				scheduleMap.put(id, dataMap);
			}
		} catch (SQLException e) {
			SQLUtils.cleanup(conn);
			throw new BOSException("Schedule Export Report SQLException", e);
		} finally {
			SQLUtils.cleanup(rs, pst, conn);
		}

		return scheduleMap;
	}

	/**
	 * 描述：获取主项计划SQL
	 * @param tableType		统计指标项类型
	 * @param orgIDs		组织ID
	 * @param startDate		开始日期
	 * @param endDate		结束日期
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-13
	 */
	@SuppressWarnings("unchecked")
	private String getAllTableChartSQL(String tableType, String chartType, List orgIDs) {

		if (orgIDs == null || orgIDs.isEmpty()) {
			return null;
		}

		StringBuffer sql = new StringBuffer(100);
		sql.append("select sch.fprojectid, sch.fid, count(task.fid) as dataCount from T_SCH_FDCScheduleTask task ").append(
				"left join T_SCH_FDCSchedule sch on task.FScheduleid=sch.fid ").append("where sch.FIsLatestVer=1 and task.fisleaf = 1 ")
				.append("and sch.fprojectid in( ");

		for (int i = 0; i < orgIDs.size(); i++) {
			if (i == orgIDs.size() - 1) {
				sql.append("'").append(orgIDs.get(i)).append("' ");
			} else {
				sql.append("'").append(orgIDs.get(i)).append("', ");
			}
		}

		sql.append(") ");

		// 主项计划
		if (FDCScheduleConstant.REPORT_CHART_SCHEDULE_TYPE_MAIN_PRJ.equals(tableType)) {
			sql.append(" and sch.FProjectSpecialId is null ");

			//专项计划
		} else if (FDCScheduleConstant.REPORT_CHART_SCHEDULE_TYPE_PRO_PRJ.equals(tableType)) {
			sql.append(" and sch.FProjectSpecialId is not null ");

			// 考核节点
		} else if (FDCScheduleConstant.REPORT_CHART_SCHEDULE_TYPE_CHECK.equals(tableType)) {
			sql.append(" and task.fischecknode = 1  and sch.FProjectSpecialId is null ");

			// 里程碑
		} else if (FDCScheduleConstant.REPORT_CHART_SCHEDULE_TYPE_MILESTONE.equals(tableType)) {
			sql.append(" and sch.FProjectSpecialId is null and task.ftasktype = '").append(RESchTaskTypeEnum.MILESTONE_VALUE).append("'");
		}

		sql.append(" and task.FEnd between ? and ? ");

		sql.append(getEachChartSQL(chartType));

		sql.append(" group by sch.fprojectid, sch.fid");

		logger.info("EAS Schedule Chart SQL:" + sql.toString());
		return sql.toString();

	}

	/**
	 * 描述：获取专项计划的SQL
	 * @param chartType
	 * @return
	 * @Author：adward_huang
	 * @CreateTime：2012-9-24
	 */
	private String getExpertPlanCompSQL(String chartType, boolean flag) {

		StringBuffer sql = new StringBuffer(100);
		sql.append("select ps.fid, count(task.fid)as dataCount ")
		   .append("from t_sch_fdcscheduletask task right outer join t_sch_fdcschedule sch  on sch.fid = task.fscheduleid ")
		   .append("left outer join t_fdc_curproject prj on prj.fid = sch.fprojectid ")
		   .append("left outer join t_sch_projectspecial ps on sch.fprojectspecialid = ps.fid ")
		   .append("where sch.fprojectspecialid is not null and sch.FIsLatestVer=1 and task.fisleaf = 1 and prj.fid = ? ");
		if(flag){
			 sql.append("and task.FEnd between ? and ? ");
		}
		sql.append(getEachChartSQL(chartType));
		sql.append("group by ps.fid ");

		return sql.toString();
	}

	/**
	 * 描述：根据每个图形类型获取相应的SQL
	 * @param chartType			图形类别
	 * @param orgIDs			组织ID
	 * @param startDate			开始日期
	 * @param endDate			结束日期
	 * @return					SQL
	 * @Author：adward_huang
	 * @CreateTime：2012-9-13
	 */
	private String getEachChartSQL(String chartType) {
		StringBuffer sql = new StringBuffer(100);

		// 按时完成
		if (FDCScheduleConstant.REPORT_CHART_TIMMED_COMP.equals(chartType)) {
			sql.append("and  task.FComplete=100 ")//and task.FActualEndDate<=task.FEnd 
					.append("and (task.FActualEndDate <= isnull(fcheckdate,FEnd) or datediff(day,isnull(task.FcheckDate,task.FEnd),task.FActualEndDate) = 0) ").append(
		             " and task.fsrcid in (	select fsrcrelatetask from	t_sch_taskevaluationbill where fevaluationresult in (select fid from t_sch_taskevaluation where fevaluationtype='1SCHEDULE' and fevaluationpass = 1 )) ");

		} else if (FDCScheduleConstant.REPORT_CHART_FIF_IN_COMP.equals(chartType)) {// 15天内完成
			sql.append("and  task.FComplete=100  and task.FActualEndDate>isnull(task.fcheckdate,task.FEnd) ").append(
					"and datediff(day,isnull(task.fcheckdate,task.FEnd),task.FActualEndDate) <= 15 ")
					.append("and datediff(day,isnull(task.fcheckdate,task.FEnd),task.FActualEndDate) > 0 ").append(
							"and task.fsrcid in (	select fsrcrelatetask from	t_sch_taskevaluationbill where fevaluationresult in (select fid from t_sch_taskevaluation where fevaluationtype='1SCHEDULE' and fevaluationpass = 1 ))");

		} else if (FDCScheduleConstant.REPORT_CHART_FIF_OUT_COMP.equals(chartType)) {// 15天外完成
			sql.append("and  task.FComplete=100  and task.FActualEndDate>isnull(task.fcheckdate,task.FEnd) ").append(
					"and datediff(day,isnull(task.fcheckdate,task.FEnd),task.FActualEndDate) > 15").append(
					"and task.fsrcid in (	select fsrcrelatetask from	t_sch_taskevaluationbill where fevaluationresult in (select fid from t_sch_taskevaluation where fevaluationtype='1SCHEDULE' and fevaluationpass = 1 )) ");

		} else if (FDCScheduleConstant.REPORT_CHART_CONFIRMED_COMP.equals(chartType)) {// 待确认（SQL所求值还需减去-15天内-15天外-按时完成）
			sql.append("and  task.FComplete=100 ");

		} else if (FDCScheduleConstant.REPORT_CHART_UNCOMP.equals(chartType)) {// 未完成
//			sql.append("and  (task.FComplete is null or task.FComplete<100) ");
			sql.append(" and task.fcomplete <100 and datediff(d,to_date(to_char(isnull(task.fcheckdate,task.fend),'yyyy-MM-dd')),getdate())>0 ");
		}
		//		sql.append("group by sch.fprojectid, sch.fid");

		return sql.toString();
	}

	/**
	 * 描述：根据项目ID获取进度名称
	 * @param ctx
	 * @param id
	 * @return
	 * @throws BOSException
	 * @Author：adward_huang
	 * @CreateTime：2012-9-14
	 */
	@SuppressWarnings("unused")
	private String getScheduleProjectNameByID(Context ctx, String id) throws BOSException {
		//String sql = "select fname_l2 from T_SCH_FDCSchedule where fid = ?";
		String sql = "select fname_l2 from T_FDC_CurProject where fid = ?";
		IRowSet rs = DbUtil.executeQuery(ctx, sql, new Object[] { id });

		try {
			if (rs != null && rs.next()) {
				String projectName = rs.getString("fname_l2");
				return projectName;
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return null;
	}

	/**
	 * 描述：根据专项ID获取专项名称
	 * @param ctx
	 * @param id
	 * @return
	 * @throws BOSException
	 * @Author：adward_huang
	 * @CreateTime：2012-9-14
	 */
	private String getScheduleExpertNameByID(Context ctx, String id) throws BOSException {
		String sql = "select fname_l2 from T_SCH_ProjectSpecial where fid = ?";
		IRowSet rs = DbUtil.executeQuery(ctx, sql, new Object[] { id });

		try {
			if (rs != null && rs.next()) {
				String projectName = rs.getString("fname_l2");
				return projectName;
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return null;
	}
	
	private List<String> getTypeList() {
		List<String> chartTypeList = new ArrayList<String>();
		chartTypeList.add(FDCScheduleConstant.REPORT_CHART_TIMMED_COMP);
		chartTypeList.add(FDCScheduleConstant.REPORT_CHART_FIF_IN_COMP);
		chartTypeList.add(FDCScheduleConstant.REPORT_CHART_FIF_OUT_COMP);
		chartTypeList.add(FDCScheduleConstant.REPORT_CHART_UNCOMP);
		chartTypeList.add(FDCScheduleConstant.REPORT_CHART_CONFIRMED_COMP);
		chartTypeList.add(FDCScheduleConstant.REPORT_CHART_PLANNED_COMP);
		return chartTypeList;
	}

	private List<Map<String, Map<String, String>>> getChartListMap(Map<String, Map<String, String>> timedCompMap,
			Map<String, Map<String, String>> fifInCompMap, Map<String, Map<String, String>> fifOutCompMap,
			Map<String, Map<String, String>> unCompMap, Map<String, Map<String, String>> confirmedCompMap, Map<String, Map<String, String>> plannedCompMap) {
		
		List<Map<String, Map<String, String>>> mapList = new ArrayList<Map<String, Map<String, String>>>();
		mapList.add(timedCompMap);
		mapList.add(fifInCompMap);
		mapList.add(fifOutCompMap);
		mapList.add(unCompMap);
		mapList.add(confirmedCompMap);
		mapList.add(plannedCompMap);		 
		return mapList;
	}

	private long getStartDate(Date startDate) {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		startCalendar.set(Calendar.HOUR, 0);
		startCalendar.set(Calendar.MINUTE, 0);
		startCalendar.set(Calendar.SECOND, 0);
		return startCalendar.getTimeInMillis();
	}

	private long getEndDate(Date endDate) {
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(endDate);
		endCalendar.set(Calendar.HOUR, 23);
		endCalendar.set(Calendar.MINUTE, 59);
		endCalendar.set(Calendar.SECOND, 59);
		return endCalendar.getTimeInMillis();
	}

	@Override
	protected Map _getScheduleTaskInfo(Context ctx, Map paramMap) throws BOSException, EASBizException {
		if(paramMap.isEmpty()){
			throw new RuntimeException("参数传递有误");
		}
		
		Map resultMap = new HashMap();
		
		String id = (String) paramMap.get("currentOrgId");
		Date startDate = (Date) paramMap.get("startDate");
		Date endDate = (Date) paramMap.get("endDate");
		String state = (String) paramMap.get("state");
		String chartType = (String) paramMap.get("chartType");
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		IRowSet rs = null;
		String prjsql = " ";//info.getLongNumber
		String sql= "";
		if(chartType.equals("proPrj")){
			String prjId = (String) paramMap.get("selectedOrgs");
			 prjsql = "'"+prjId+"' ";
			 sql = "select bill.fsrcrelatetask,eva.FEvaluationPass from t_sch_taskevaluationbill bill left outer join t_sch_taskevaluation eva on bill.fevaluationresult = eva.fid where bill.fsrcrelatetask in (select fsrcid from t_sch_fdcscheduletask where fscheduleid in (select fid from t_sch_fdcschedule where fprojectid in ("+prjsql+") and fprojectSpecialid is not null) and fevaluationresult in (select fid from T_SCH_TaskEvaluation where fevaluationtype='1SCHEDULE' and fevaluationpass = 1)) order by bill.flastupdateTime desc";

		}else{
			ScheduleReportOrgInfo info = ScheduleReportOrgFactory.getLocalInstance(ctx).getScheduleReportOrgInfo(new ObjectUuidPK(BOSUuid.read(id)));
			BOSUuid orgID = info.getRelateOrg().getId();
			if(!orgID.getType().toString().equals("F9E5E92B")){
				String orgSql = "select fid,fname_l2,fnumber from t_org_baseunit where fid = ?";
				builder.appendSql(orgSql);
				builder.addParam(orgID.toString());
				rs = builder.executeQuery();
				try {
					while(rs.next()){
						resultMap.put("orgInfo", rs.getString(2));
					}
				} catch (SQLException e) {
					throw new EASBizException(null);
				}
			}
			 prjsql = "select FRelateOrgID from t_sch_schedulereportorg where flongNumber like  '"+info.getLongNumber()+"%'";
			 sql = "select bill.fsrcrelatetask,eva.FEvaluationPass from t_sch_taskevaluationbill bill left outer join t_sch_taskevaluation eva on bill.fevaluationresult = eva.fid where bill.fsrcrelatetask in (select fsrcid from t_sch_fdcscheduletask where fscheduleid in (select fid from t_sch_fdcschedule where fprojectid in ("+prjsql+") and fprojectspecialid is null) and fevaluationresult in (select fid from T_SCH_TaskEvaluation where fevaluationtype='1SCHEDULE' and fevaluationpass = 1)) order by bill.flastupdateTime desc";
		}
		//获取所有评价过的任务
		
		builder.clear();
		builder.appendSql(sql);
		 rs = builder.executeQuery();
		Map<String,Boolean> evMap = new HashMap<String,Boolean>();
		Set<String> evSet = new HashSet<String>();
		String srcRelateTask = null;
		try {
			while(rs.next()){
				srcRelateTask = rs.getString(1);
				Boolean rsPass = rs.getBoolean(2);
				if(!evMap.containsKey(srcRelateTask)){
					evMap.put(srcRelateTask,rsPass);
				}
				if(!evSet.contains(srcRelateTask)){
					evSet.add(srcRelateTask);
				}
			}
		} catch (SQLException e) {
			throw new EASBizException(null);
		}
	
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		String taskIdSql="select task.fid from t_sch_fdcscheduletask task right outer join t_sch_fdcschedule sch on task.fscheduleid = sch.fid " +
				"where task.fend >= {ts'"+FDCDateHelper.formatDate2(startDate)+" 00:00:00'} and fend <= {ts'"+FDCDateHelper.formatDate2(endDate)+" 23:59:59'} and sch.fprojectid in ("+prjsql+") and sch.fislatestver = 1 and fisleaf = 1";
		builder.clear();
		builder.appendSql(taskIdSql);
		
		
		//时间范围
		FilterItemInfo timeLowBound = new FilterItemInfo("start",startDate,CompareType.GREATER_EQUALS);
		FilterItemInfo timeUpBound = new FilterItemInfo("end",FDCDateHelper.getNextDay(endDate),CompareType.LESS);
		filter.getFilterItems().add(timeLowBound);
		filter.getFilterItems().add(timeUpBound);
		
		if (state.equals(FDCScheduleConstant.REPORT_CHART_TIMMED_COMP)) {
			builder.appendSql(" and task.fcomplete =100 and  datediff(d,to_date(to_char(isnull(task.fcheckdate,task.fend),'yyyy-MM-dd')),to_date(to_char(task.factualenddate,'yyyy-MM-dd')))<=0");
			builder.appendFilter("task.FSrcID", evSet, CompareType.INCLUDE);
			
			
			
		} else if (state.equals("fifInComp")) {
			builder.appendSql(" and task.fcomplete =100 and datediff(d,to_date(to_char(isnull(task.fcheckdate,task.fend),'yyyy-MM-dd')),to_date(to_char(task.factualEndDate,'yyyy-MM-dd')))>0 and datediff(d,to_date(to_char(isnull(task.fcheckdate,task.fend),'yyyy-MM-dd')),to_date(to_char(task.factualEndDate,'yyyy-MM-dd')))<=15");
			builder.appendFilter("task.FSrcID", evSet, CompareType.INCLUDE);
			
		} else if (state.equals("fifOutComp")) {
			builder.appendSql(" and task.fcomplete =100 and  datediff(d,to_date(to_char(isnull(task.fcheckdate,task.fend),'yyyy-MM-dd')),to_date(to_char(task.factualEndDate,'yyyy-MM-dd')))>15");
			builder.appendFilter("task.FSrcID", evSet, CompareType.INCLUDE);
			
		}else if(state.equals("confirmComp")){
			builder.appendSql(" and task.fcomplete = 100 ");
			//TODO sqlbuilder的一个bug。不过现在没用，先注释下
			//builder.appendFilterForNotIn("task.FSrcID", evSet, CompareType.NOTINCLUDE);
		}else if(state.equals("unComp")){
			builder.appendSql(" and task.fcomplete <100 and datediff(d,to_date(to_char(isnull(task.fcheckdate,task.fend),'yyyy-MM-dd')),getdate())>0  ");
		}
		//报表类型
		if(chartType.equals(FDCScheduleConstant.REPORT_CHART_SCHEDULE_TYPE_MAIN_PRJ)){//主项计划
               builder.appendSql(" and sch.fprojectspecialid is null");
		}else if(chartType.equals(FDCScheduleConstant.REPORT_CHART_SCHEDULE_TYPE_MILESTONE)){//里程碑
			builder.appendSql(" and sch.fprojectspecialid is null and task.ftasktype='milestone'");		
		}else if(chartType.equals(FDCScheduleConstant.REPORT_CHART_SCHEDULE_TYPE_CHECK)){//考核计划
			builder.appendSql(" and sch.fprojectspecialid is null and task.fischecknode = 1");	;
			
		}else if(chartType.equals(FDCScheduleConstant.REPORT_CHART_SCHEDULE_TYPE_PRO_PRJ)){//专项计划
			if(id.equals("allExpertPlan")){
				builder.appendSql(" and sch.fprojectspecialid is not null");
			}else {
				builder.appendSql(" and sch.fprojectspecialid = ?");
				builder.addParam(id);
			}
			
		}
		
		
		FilterInfo sqlFilter = new FilterInfo();
		sqlFilter.getFilterItems().add(new FilterItemInfo("id",builder.getTestSql(),CompareType.INNER));
		
		view.setFilter(sqlFilter);
		view.setSelector(ScheduleHelper.getRptTaskSelector());
		
		SorterItemCollection sorters = new SorterItemCollection();
		SorterItemInfo si = new SorterItemInfo("schedule.project.longNumber");
	    sorters.add(si);
		view.setSorter(sorters);
		
		FDCScheduleTaskCollection tasks = FDCScheduleTaskFactory.getLocalInstance(ctx).getFDCScheduleTaskCollection(view);
		resultMap.put("taskList", tasks);
		return resultMap;
	}

}