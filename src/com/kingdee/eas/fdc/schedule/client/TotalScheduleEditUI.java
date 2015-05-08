/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.REAutoRememberFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.framework.client.KDTaskStatePanel;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有 描述： 项目中进度计划
 * 
 * @author 杜红明
 * @version EAS7.0
 * @createDate 2011-9-28
 * @see
 */
public class TotalScheduleEditUI extends AbstractTotalScheduleEditUI {
	private static final Logger logger = CoreUIObject.getLogger(TotalScheduleEditUI.class);

	private static final String STATUS_CRISIS = "STATUS_CRISIS";

	/**
	 * output class constructor
	 */
	public TotalScheduleEditUI() throws Exception {
		super();
	}


	public void onLoad() throws Exception {
		super.onLoad();
		btnLocate.setEnabled(true);
		getCurScheduleRelateSpecail();
		addStatePanel();
		btnExportProject.setIcon(EASResource.getIcon("imgTbtn_output"));
		btnExportProject.setText("导出到project");
		this.btnCopy.setVisible(false);
	}
	
	
	private  void addStatePanel(){
		JComponent statePanel = null;
		if(getStatePanel() != null){
			statePanel = getStatePanel();
		}else{
			statePanel = new KDTaskStatePanel();
		}
		statePanel.setVisible(true);
		this.paySubway.add(statePanel);
		this.paySubway.setVisible(true);
	}
	/**
	 * 
	 * 描述：提供旭辉二次开发的接口
	 * @return
	 * 创建人：yuanjun_lan
	 * 创建时间：2012-2-23
	 */
	public JComponent getStatePanel(){
		return null;		
	}
	

	private void getCurScheduleRelateSpecail() throws Exception {
		String rememberProjectID = null;
		if (prmtCurproject.getValue() == null) {
			haveNoRememberProject();
			return;
		} else {
			rememberProjectID = getRememberProjectID();
		}
		if (rememberProjectID == null) {
			haveNoRememberProject();
			return;
		} else {
			// 查询最新的主项任务
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			FDCScheduleCollection mainSchedules = getMainSchedule(rememberProjectID, view, filter);
			if (FDCHelper.isEmpty(mainSchedules) || mainSchedules.size() < 1) {
				haveNoRememberProject();
				return;
			} else {
				loadSpecailTaskInMainTask(filter, mainSchedules);
				loadData2Gantt(editData);
			}
		}
	}

	private String getRememberProjectID() throws BOSException {
		String rememberProjectID;
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		rememberProjectID = REAutoRememberFactory.getRemoteInstance().getValue(user.getId().toString(), orgUnit.getId().toString(),
				"MainScheduleProject");
		return rememberProjectID;
	}

	/*
	 * 性能优化，重写父类方法，父类方法调用load2Gantt(this.editData)
	 * 因为在onload时load2Gantt(editData)一次，在加载关联专项计划后getCurScheduleRelateSpecail()，又load2Gantt(editData)一次
	 * 两次都比较耗时，去掉父类的调用 by Owen_wen 2014-01-02 
	 */
	protected void loadData() throws Exception {
		innerLoadData();
		setOprtState(getOprtState());
		ganttProject.setEventsEnabled(true);
	}

	private void haveNoRememberProject() throws BOSException, EASBizException, Exception {
		FDCScheduleInfo info = FDCScheduleFactory.getRemoteInstance()
				.getScheduleInfo(isMainSchedule(), false, null, null, null);
		editData = info;
		loadData2Gantt(editData);
	}
	
	protected void loadData2Gantt(FDCScheduleInfo editData) throws Exception {
		// 按时完成：如果当前任务的完成进度=100，且实际完成日期<=计划完成日期时。
		// 延时完成：如果当前任务的完成进度=100，且实际完成日期>计划完成日期时。
		// 延时未完成：如果当前任务的完成进度<>100，且实际完成日期>计划完成日期时。
		// 待确认任务，进度为100%，但未经过进度评价确认（如果不启用主项任务评价控制参数则无此状态）
		// editData.getTaskEntrys().get(0).put("state", new Integer(11));
		super.loadData2Gantt(editData);
		// getScheduleGanttProject().removeToolbar();
		// TableCellEditor editor =
		// getScheduleGanttProject().getGanttTreeTable()
		// .getColumn(1).getCellEditor();
		// getScheduleGanttProject().getGanttTreeTable().getColumn(1).
		// setCellEditor(editor);
	}


	private void loadSpecailTaskInMainTask(FilterInfo filter, FDCScheduleCollection mainSchedules) throws BOSException, SQLException {
		FDCScheduleInfo mainSchedule = mainSchedules.get(0);

		// 获取到了获取到主项任务关联的专项计划ID、专项任务的longNumber
		FDCSQLBuilder builder = new FDCSQLBuilder();
		Map map = new HashMap();
		if (mainSchedule != null) {
			putSpecialScheduleData(map, mainSchedules, builder);
		}
		/** 获得相关联的专项任务的任务 **/
		filter.getFilterItems().clear();
		filter = getSpecialScheduleFilter(filter, map);
		if (filter.getFilterItems().size() != 0) {
			EntityViewInfo specailTaskView = new EntityViewInfo();
			SelectorItemCollection selector = specailTaskView.getSelector();
			selector = getSpecialRalateSelector(selector);
			specailTaskView.setFilter(filter);
			// 获得主项关联的相关专项任务以及下级任务集合
			FDCScheduleTaskCollection lowerLevelSpcailSchedule = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(
					specailTaskView);
			/** 嵌入相应专项任务到关联主项关联相关专项任务 **/
			putDependSpecailTask(lowerLevelSpcailSchedule);
		}
	}


	private FDCScheduleCollection getMainSchedule(String rememberProjectID, EntityViewInfo view, FilterInfo filter) throws BOSException {
		SelectorItemCollection selectors = view.getSelector();
		view.getSelector().add("id");
		view.getSelector().add("taskEntrys.id");
		selectors.add("taskEntrys.isLeaf");
		selectors.add("taskEntrys.level");
		selectors.add("taskEntrys.longNumber");
		selectors.add("taskEntrys.number");
		selectors.add("taskEntrys.name");
		filter.getFilterItems().add(new FilterItemInfo("project.id", rememberProjectID));
		filter.getFilterItems().add(new FilterItemInfo("isLatestver", "1"));
		filter.getFilterItems().add(new FilterItemInfo("projectSpecial", null));
		view.setFilter(filter);
		FDCScheduleCollection mainSchedules = FDCScheduleFactory.getRemoteInstance().getFDCScheduleCollection(view);
		return mainSchedules;
	}


	private Map putSpecialScheduleData(Map map, FDCScheduleCollection mainSchedules, FDCSQLBuilder builder) throws BOSException, SQLException {
		builder.appendSql("select schedule.fid as fid,task.flongnumber as flongnumber ");
		builder.appendSql("from t_sch_fdcscheduletask as task inner join t_sch_fdcschedule schedule on schedule.fid=task.fscheduleid ");
		builder.appendSql("where schedule.fislatestver=1 and task.fdependmaintaskid in ");
		builder.appendSql("(select fsrcid from t_sch_fdcscheduletask where fscheduleid=?)");
		builder.addParam(mainSchedules.get(0).getId().toString());
		IRowSet rs = builder.executeQuery();
		Set set = null;
		while (rs.next()) {
			String key = rs.getString("fid");
			String value = rs.getString("flongnumber");
			if (!map.containsKey(key)) {
				set = new HashSet();
			}
			set.add(value);
			map.put(key, set);
		}
		return map;
	}

	/**
	 * 
	 * @description 嵌入相应专项任务到关联主项关联相关专项任务
	 * @author 杜红明
	 * @createDate 2011-9-28
	 * @param lowerLevelSpcailSchedule
	 *            相关专项及专项的子项任务
	 * @version EAS7.0
	 * @see
	 */
	private void putDependSpecailTask(FDCScheduleTaskCollection lowerLevelSpcailSchedule) {
		FDCScheduleTaskCollection taskEntrys = editData.getTaskEntrys();
		if (lowerLevelSpcailSchedule != null && null != taskEntrys) {
			for (int i = 0; i < taskEntrys.size(); i++) {
				FDCScheduleTaskInfo scheduleTaskInfo = taskEntrys.get(i);
				for (int j = 0; j < lowerLevelSpcailSchedule.size(); j++) {
					FDCScheduleTaskInfo scheduleTaskInfo2 = lowerLevelSpcailSchedule.get(j);
					if (null != scheduleTaskInfo2.getDependMainTaskID()
							&& scheduleTaskInfo.getId().toString().equals(scheduleTaskInfo2.getDependMainTaskID().getId().toString())) {
						scheduleTaskInfo2.setLongNumber(scheduleTaskInfo.getLongNumber() + "!" + "121");
						scheduleTaskInfo2.setLevel(scheduleTaskInfo.getLevel() + 1);
						scheduleTaskInfo2.setParent(scheduleTaskInfo);
						taskEntrys.add(scheduleTaskInfo2);
						editData.getTaskEntrys().addCollection(lowerLevelSpcailSchedule);
					}
				}
			}
		}
		
		String rsPath = "com.kingdee.eas.fdc.schedule.ScheduleResource";
		// 勾
		String achieve = EASResource.getString(rsPath, "achieve");
		// 圈
		String pending = EASResource.getString(rsPath, "pending");
		// 红
		Color red = new Color(245, 0, 0);
		// 绿
		Color green = new Color(10, 220, 10);
		// 橙
		Color orange = new Color(220, 180, 0);
	}

	private SelectorItemCollection getSpecialRalateSelector(SelectorItemCollection selector) {
		selector.add("id");
		selector.add("name");
		selector.add("number");
		selector.add("longNumber");
		selector.add("level");
		selector.add("isLeaf");
		selector.add("duration");
		selector.add("schedule.id");
		selector.add("parent.*");
		selector.add("srcID");
		selector.add("dependMainTaskID.*");
		return selector;
	}

	private FilterInfo getSpecialScheduleFilter(FilterInfo filter, Map map) throws BOSException {
		int i = 1;
		for (Iterator it = map.keySet().iterator(); it.hasNext();) {
			String fid = (String) it.next();
			filter.getFilterItems().add(new FilterItemInfo("schedule", fid));

			Set longNumbers = (Set) map.get(fid);
			int j = 1;
			for (Iterator longNumber = longNumbers.iterator(); longNumber.hasNext();) {
				String number = (String) longNumber.next();
				filter.getFilterItems().add(new FilterItemInfo("longNumber", number + "%", CompareType.LIKE));

				if (j != longNumbers.size()) {
					filter.mergeFilter(filter, "or");
				}
				j++;
			}
			if (i != map.size()) {
				filter.mergeFilter(filter, "or");
			}
			i++;
		}
		return filter;
	}

	/**
	 * @description
	 * @author 杜红明
	 * @createDate 2011-9-27
	 * @version EAS7.0
	 * @see
	 */
	protected void prmtCurproject_dataChanged(DataChangeEvent e) throws Exception {
		CurProjectInfo curProject = (CurProjectInfo) e.getNewValue();
		Map param = new HashMap();
		param.put("isFormTotalUI", "true");
		FDCScheduleInfo mainSchedule = FDCScheduleFactory.getRemoteInstance()
				.getScheduleInfo(isMainSchedule(), false, curProject, null,
						param);
		FDCScheduleCollection mainSchedules = new FDCScheduleCollection();
		mainSchedules.add(mainSchedule);
		loadSpecailTaskInMainTask(new FilterInfo(), mainSchedules);
		loadData2Gantt(mainSchedule);
		editData = mainSchedule;
		setOprtState(STATUS_VIEW);
	}
	
	/**
	 * @description
	 * @author 杜红明
	 * @createDate 2011-9-28
	 * @version EAS7.0
	 * @see
	 */
	protected String getUIName() {
		return TotalScheduleEditUI.class.getName();
	}


}