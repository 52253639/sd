package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import net.sourceforge.ganttproject.action.NewTaskAction;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.ormapping.impl.ObjectReader;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.FDCSchedule;
import com.kingdee.eas.fdc.schedule.FDCScheduleCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustTaskEntryInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;

public class ScheduleAdjustTaskHelper {
	private static final String COL_ADMIN_PERSON = "adminPerson";
	private static final String COL_ADMIN_DEPT = "adminDept";
	private static final String COL_EFFECT_TIMES = "effectTimes";
	private static final String COL_END_DATE = "endDate";
	private static final String COL_START_DATE = "startDate";
	private static final String COL_NAME = "name";
	private static final String COL_ADJUST_EFFECT_TIMES = "adjustEffectTimes";
	private static final String COL_ADJUST_END_DATE = "adjustEndDate";
	private static final String COL_ADJUST_START_DATE = "adjustStartDate";
	private static final String COL_NUMBER = "number";
	private final KDTable table;
	private final ScheduleAdjustReqBillEditUI ui;
	private final ScheduleCalendarInfo calendarInfo;
	public ScheduleAdjustTaskHelper(ScheduleAdjustReqBillEditUI ui) {
		this.ui = ui;
		table = ui.getAdjustTaskTable();
		calendarInfo=ui.getBaseScheduleInfo().getCalendar();
		init();
	}

	private KDTable getTable() {
		return table;
	}

	void addRow(ActionEvent e) {
		if(OprtState.VIEW.equals(ui.getOprtState())){
			return;
		}
		ScheduleAdjustTaskEntryInfo entry=new ScheduleAdjustTaskEntryInfo();
		entry.setId(BOSUuid.create(entry.getBOSType()));
		IRow row=table.addRow();
		row.setUserObject(entry);
	}

	void deleteRow(ActionEvent e) {
		//update taskList
		if(OprtState.VIEW.equals(ui.getOprtState())){
			return;
		}
		int[] selectedRows = KDTableUtil.getSelectedRows(table);
		for(int i=selectedRows.length-1;i>=0;i--){
			IRow row=table.getRow(selectedRows[i]);
			ScheduleAdjustTaskEntryInfo entry=(ScheduleAdjustTaskEntryInfo)row.getUserObject();
			if(entry.getTask()!=null){
				ui.getTaskList().updateItem(entry.getTask().getId().toString(), null);
			}
			table.removeRow(row.getRowIndex());
		}
		ui.setDataChanged();
	}

	private void init(){
		initTable();
		ui.contAdjustTask.setEnableActive(false);
//		if(OprtState.VIEW.equals(ui.getOprtState())){
//			actionAddRow.setEnabled(false);
//			actionDeleteRow.setEnabled(false);
//		}
		ui.contAdjustTask.add(actionAddRow);
		ui.contAdjustTask.add(actionDeleteRow);
		
	}
	private void initTable() {
		table.checkParsed();
		// set editor
		KDBizPromptBox prmt = new KDBizPromptBox();
		prmt.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7ScheduleTaskQuery");
		prmt.setRequired(true);
		prmt.setDisplayFormat("$number$");
		prmt.setEditFormat("$number$");
		prmt.setCommitFormat("$number$");
		prmt.setEditable(false);
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("project.id", ui.getUIContext().get("projectId").toString());
//		//应该只能调整审批及执行中的任务
		Set set=new HashSet();
		set.add(ScheduleStateEnum.AUDITTED_VALUE);
		set.add(ScheduleStateEnum.EXETING_VALUE);
		view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.state", set,CompareType.INCLUDE));
		prmt.setEntityViewInfo(view);
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("*");
		selector.add("adminPerson.id");
		selector.add("adminPerson.name");
		selector.add("adminPerson.number");
		selector.add("adminDept.id");
		selector.add("adminDept.name");
		selector.add("adminDept.number");
		selector.add("dependEntrys.type");
		selector.add("dependEntrys.id");
		selector.add("dependEntrys.type");
		selector.add("dependEntrys.difference");
		selector.add("dependEntrys.dependTask.id");
		prmt.setSelectorCollection(selector);
		table.getColumn(COL_NUMBER).setEditor(new KDTDefaultCellEditor(prmt));
    	BizDataFormat bizDataFormat = new BizDataFormat("$number$");
    	ObjectValueRender render=new ObjectValueRender();
    	render.setFormat(bizDataFormat);
		table.getColumn(COL_NUMBER).setRenderer(render);
		
		KDDatePicker pkDate=new KDDatePicker();
		pkDate.setSupportedEmpty(false);
		table.getColumn(COL_ADJUST_START_DATE).setEditor(new KDTDefaultCellEditor(pkDate));
		
		pkDate=new KDDatePicker();
		pkDate.setSupportedEmpty(false);
		table.getColumn(COL_ADJUST_END_DATE).setEditor(new KDTDefaultCellEditor(pkDate));
		
		KDFormattedTextField kf=new KDFormattedTextField();
		kf.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		kf.setHorizontalAlignment(KDFormattedTextField.RIGHT);
		kf.setPrecision(0);
		kf.setRemoveingZeroInDispaly(true);
		kf.setRemoveingZeroInEdit(true);
		kf.setMinimumValue(FDCHelper.ONE);
		kf.setMaximumValue(FDCHelper.MAX_VALUE);
		table.getColumn(COL_ADJUST_EFFECT_TIMES).setEditor(new KDTDefaultCellEditor(kf));
		
		table.addKDTEditListener(new KDTEditAdapter() {

			public void editStopped(KDTEditEvent e) {
				table_editStopped(e);
			}

		});
		// set column
		table.getColumn(COL_NAME).getStyleAttributes().setLocked(true);
		table.getColumn(COL_START_DATE).getStyleAttributes().setLocked(true);
		table.getColumn(COL_END_DATE).getStyleAttributes().setLocked(true);
		table.getColumn(COL_EFFECT_TIMES).getStyleAttributes().setLocked(true);
		table.getColumn(COL_ADMIN_DEPT).getStyleAttributes().setLocked(true);
		table.getColumn(COL_ADMIN_PERSON).getStyleAttributes().setLocked(true);
		table.getColumn(COL_NUMBER).getStyleAttributes().setLocked(false);
		
		table.getColumn(COL_NAME).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		table.getColumn(COL_START_DATE).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		table.getColumn(COL_END_DATE).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		table.getColumn(COL_EFFECT_TIMES).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		table.getColumn(COL_ADMIN_DEPT).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		table.getColumn(COL_ADMIN_PERSON).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		table.getColumn(COL_NUMBER).getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
	}

	
	/**
	 * 
	 * @param taskInfo	变动的任务
	 */
	private void adjustTask(BigDecimal diffEffectTimes){
		int actRowIdx = table.getSelectManager().getActiveRowIndex();
		if(actRowIdx < 0) return;
		IRow row = table.getRow(actRowIdx);
		Set dependTaskIds = new HashSet();
		ScheduleAdjustTaskEntryInfo adjustTaskEntry = (ScheduleAdjustTaskEntryInfo) row.getUserObject(); 
		FDCScheduleTaskDependCollection dependTasks = adjustTaskEntry.getTask().getDependEntrys();
		for(int i=0;i<dependTasks.size();i++){
			dependTaskIds.add(dependTasks.get(i).getDependTask().getId().toString());
		}
		for(int i=0;i<table.getRowCount();i++){
			if(table.getCell(i, COL_NUMBER) != null && table.getCell(i, COL_NUMBER).getValue() != null){
				ScheduleAdjustTaskEntryInfo adjustTaskInfo = (ScheduleAdjustTaskEntryInfo) table.getRow(i).getUserObject();
				FDCScheduleTaskInfo tempTaskInfo = adjustTaskInfo.getTask();
				if(tempTaskInfo.getId().toString().equals(adjustTaskEntry.getTask().getId().toString())){
					continue;
				}else if(dependTaskIds.contains(tempTaskInfo.getId().toString())){
					Date startDate = ScheduleCalendarHelper.getEndDate(adjustTaskEntry.getEndDate(), adjustTaskEntry.getEffectTimes(), calendarInfo);
					Date endDate=ScheduleCalendarHelper.getEndDate(startDate, tempTaskInfo.getEffectTimes(), calendarInfo);
					adjustTaskInfo.setStartDate(startDate);
					adjustTaskInfo.setEndDate(endDate);
					loadRow(table.getRow(i));
				}
			}
		}
	}
	
	protected void table_editStopped(KDTEditEvent e) {
		if(e.getValue()==null){
			return;
		}
		if(e.getValue()==null&&e.getOldValue()==null){
			return;
		}
		if(e.getValue()!=null&&e.getValue().equals(e.getOldValue())){
			return;
		}
		if(COL_ADJUST_START_DATE.equals(getTable().getColumnKey(e.getColIndex()))){
			FDCScheduleTaskInfo adjTask = (FDCScheduleTaskInfo) getTable().getCell(e.getRowIndex(), COL_NUMBER).getValue();
			if(!adjTask.isIsLeaf()
					 && ((Date)e.getValue()).before(adjTask.getStart())){
				getTable().getCell(e.getRowIndex(), e.getColIndex()).setValue(e.getOldValue());
				FDCMsgBox.showWarning("开始时间向前调整时，请从最明细节点开始调整。");
//				SysUtil.abort();
			}
		}
		IRow row = getTable().getRow(e.getRowIndex());
		if (COL_NUMBER.equals(getTable().getColumnKey(e.getColIndex()))) {
			FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) e.getValue();
			FDCScheduleTaskInfo oldTask = (FDCScheduleTaskInfo) e.getOldValue();
			if(oldTask!=null){
				//需要判断，新的和旧的是否一样，如果一样，可以直接放回
				if(task.getId().toString().equals(oldTask.getId().toString())){
					return;
				}
				//如果不一样，需要删除缓存中的旧对象
				else{
					ui.getTaskList().updateItem(oldTask.getId().toString(), null);
				}
			}
			fillRow(task, row);			
		}
		
		if (COL_ADJUST_START_DATE.equals(getTable().getColumnKey(e.getColIndex()))) {
			//设置结束时间
			Date startDate=(Date)e.getValue();
			if(getTable().getCell(e.getRowIndex(), COL_NUMBER).getValue() != null){
				FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) getTable().getCell(e.getRowIndex(), COL_NUMBER).getValue();
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(new FilterInfo());
				view.getFilter().getFilterItems().add(new FilterItemInfo("dependTask.id",task.getId().toString()));
				view.getSelector().add("task.id");
				view.getSelector().add("task.name");
				view.getSelector().add("task.number");
				view.getSelector().add("task.longnumber");
				view.getSelector().add("task.start");
				view.getSelector().add("task.end");
				view.getSelector().add("type");
				try {
					FDCScheduleTaskDependCollection dependees = FDCScheduleTaskDependFactory.getRemoteInstance().getFDCScheduleTaskDependCollection(view);
					for(int i =0;i<dependees.size();i++){
						FDCScheduleTaskDependInfo dependee = dependees.get(i); 
						if(TaskLinkTypeEnum.FINISH_START.equals(dependee.getType())){
							if(dependee.getTask().getEnd().after((Date) e.getValue())){
								getTable().getCell(e.getRowIndex(), COL_ADJUST_START_DATE).setValue(e.getOldValue());
								FDCMsgBox.showError("调整时间不能超过前置任务限制！");
								return;
							}
						}
						if(TaskLinkTypeEnum.START_START.equals(dependee.getType())){
							if(dependee.getTask().getStart().after((Date) e.getValue())){
								getTable().getCell(e.getRowIndex(), COL_ADJUST_START_DATE).setValue(e.getOldValue());
								FDCMsgBox.showError("调整时间不能超过前置任务限制！");
								return;
							}
						}
					}
				} catch (BOSException e1) {
					ExceptionHandler.handle(e1);
				}
			}
			if(calendarInfo.isWeekTime(startDate)){
//				e.setCancel(true);
				FDCMsgBox.showWarning(ui, "当前选择的为休息日，自动跳到下一工作日");
				Date newDate = ScheduleCalendarHelper.getNextWorkDay(startDate, calendarInfo);
				row.getCell(COL_ADJUST_START_DATE).setValue(newDate);
				startDate=newDate;
			}
			BigDecimal adjustEffectTimes=FDCHelper.toBigDecimal(row.getCell(COL_ADJUST_EFFECT_TIMES).getValue());
			Date endDate=ScheduleCalendarHelper.getEndDate(startDate, adjustEffectTimes, calendarInfo);
			row.getCell(COL_ADJUST_END_DATE).setValue(endDate);
		}
		if (COL_ADJUST_END_DATE.equals(getTable().getColumnKey(e.getColIndex()))) {
			//设置工期
			Date endDate=(Date)e.getValue();
			if(calendarInfo.isWeekTime(endDate)){
//				e.setCancel(true);
				FDCMsgBox.showWarning(ui, "当前选择的为休息日，自动跳到下一工作日");
				Date newDate = ScheduleCalendarHelper.getNextWorkDay(endDate, calendarInfo);
				row.getCell(COL_ADJUST_END_DATE).setValue(newDate);
				endDate=newDate;
			}
			
			Date startDate=(Date)row.getCell(COL_ADJUST_START_DATE).getValue();
			if(startDate!=null){
				if(startDate!=null&&endDate.before(startDate)){
					FDCMsgBox.showWarning(ui, "结束时间必须大于开始时间!");
					row.getCell(COL_ADJUST_END_DATE).setValue(e.getOldValue());
//					SysUtil.abort();
				}else{
					BigDecimal adjustEffectTimes=ScheduleCalendarHelper.getEffectTimes(startDate, endDate, calendarInfo);
					row.getCell(COL_ADJUST_EFFECT_TIMES).setValue(adjustEffectTimes);
				}
			}
		}
		if (COL_ADJUST_EFFECT_TIMES.equals(getTable().getColumnKey(e.getColIndex()))) {
			//设置结束时间
			BigDecimal adjustEffectTimes=FDCHelper.toBigDecimal(e.getValue());
			Date startDate=(Date)row.getCell(COL_ADJUST_START_DATE).getValue();
			if(startDate!=null){
				Date endDate=ScheduleCalendarHelper.getEndDate(startDate, adjustEffectTimes, calendarInfo);
				row.getCell(COL_ADJUST_END_DATE).setValue(endDate);
			}
			
			
		}
//		adjustTask(FDCHelper.ONE);
		ui.setDataChanged();
	}

	private void fillRow(FDCScheduleTaskInfo task, IRow row) {
		if(task==null){
			task=new FDCScheduleTaskInfo();
		}
		ScheduleAdjustTaskEntryInfo entry=(ScheduleAdjustTaskEntryInfo)row.getUserObject();
		entry.setTask(task);
		entry.setEffectTimes(task.getEffectTimes());
		entry.setStartDate(task.getStart());
		entry.setEndDate(task.getEnd());
		loadRow(row);
		ui.getTaskList().updateItem(task.getId().toString(),entry);
	}

	private void loadRow(IRow row){
		ScheduleAdjustTaskEntryInfo entry=(ScheduleAdjustTaskEntryInfo)row.getUserObject();
		FDCScheduleTaskInfo task=entry.getTask();
		row.getCell(COL_NAME).setValue(task.getName());
		row.getCell(COL_START_DATE).setValue(task.getStart());
		row.getCell(COL_ADJUST_START_DATE).setValue(entry.getStartDate());
		row.getCell(COL_END_DATE).setValue(task.getEnd());
		row.getCell(COL_ADJUST_END_DATE).setValue(entry.getEndDate());
		row.getCell(COL_EFFECT_TIMES).setValue(task.getEffectTimes());
		row.getCell(COL_ADJUST_EFFECT_TIMES).setValue(entry.getEffectTimes());
		
		row.getCell(COL_ADMIN_DEPT).setValue(task.getAdminDept()!=null?task.getAdminDept().getName():null);
		row.getCell(COL_ADMIN_PERSON).setValue(task.getAdminPerson()!=null?task.getAdminPerson().getName():null);
	}
	
	ItemAction actionAddRow=new ItemAction(){
		{
			putValue(ItemAction.NAME, "新增行");
			putValue(ItemAction.SHORT_DESCRIPTION, "新增行");
			putValue(ItemAction.SMALL_ICON, EASResource.getIcon("imgTbtn_addline"));
		}
		public void actionPerformed(ActionEvent e) {
			addRow(e);
		}
	};
	
	ItemAction actionDeleteRow=new ItemAction(){
		{
			putValue(ItemAction.NAME, "删除行");
			putValue(ItemAction.SHORT_DESCRIPTION, "删除行");
			putValue(ItemAction.SMALL_ICON, EASResource.getIcon("imgTbtn_deleteline"));
		}
		public void actionPerformed(ActionEvent e) {
			deleteRow(e);
		}
	};
	
	protected void setOptState(String oprtState){
		if(oprtState!=null&&oprtState.equals(OprtState.EDIT)){
			actionAddRow.setEnabled(true);
			actionDeleteRow.setEnabled(true);
		}else{
			actionAddRow.setEnabled(false);
			actionDeleteRow.setEnabled(false);
		}
	}
	
}
