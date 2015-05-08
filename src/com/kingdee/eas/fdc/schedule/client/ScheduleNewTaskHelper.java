package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustPrefixTaskInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.ScheduleException;
import com.kingdee.eas.fdc.schedule.ScheduleNewTaskEntryInfo;
import com.kingdee.eas.fdc.schedule.WBSCodeRuleFactory;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.ext.ScheduleGanttProject;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

public class ScheduleNewTaskHelper {
	private final KDTable table;
	private final ScheduleAdjustReqBillEditUI ui;
	private final ScheduleCalendarInfo calendarInfo;
	public ScheduleNewTaskHelper(ScheduleAdjustReqBillEditUI ui) {
		this.ui = ui;
		table = ui.getNewTaskTable();
		calendarInfo=ui.getBaseScheduleInfo().getCalendar();
		init();
		
		for(int i=0;i<table.getRowCount();i++){
			IRow row=table.getRow(i);
			loadRow(row);
		}
	}

	private KDTable getTable() {
		return table;
	}

	void addRow(ActionEvent e) {
		if(OprtState.VIEW.equals(ui.getOprtState())){
			return;
		}
		ScheduleNewTaskEntryInfo entry=new ScheduleNewTaskEntryInfo();
		entry.setStart(new Date());
		BigDecimal effectTimes = FDCHelper.ONE;
		entry.setEnd(ScheduleCalendarHelper.getEndDate(entry.getStart(), effectTimes, calendarInfo));
		entry.setId(BOSUuid.create(entry.getBOSType()));
		IRow row=table.addRow();
		row.getCell("startDate").setValue(entry.getStart());
		row.getCell("endDate").setValue(entry.getEnd());
		row.getCell("effectTimes").setValue(effectTimes);
		row.setUserObject(entry);
	}

	void deleteRow(ActionEvent e) {
		//update taskList
		int[] selectedRows = KDTableUtil.getSelectedRows(table);
		for(int i=selectedRows.length-1;i>=0;i--){
			IRow row=table.getRow(selectedRows[i]);
			ScheduleNewTaskEntryInfo entry=(ScheduleNewTaskEntryInfo)row.getUserObject();
			if(entry!=null){
				ui.getTaskList().deleteItem(entry);
			}
			table.removeRow(row.getRowIndex());
		}
		ui.setDataChanged();
	}

	void property(ActionEvent e){
		IRow row=FDCTableHelper.getSelectedRow(table);
		if(row==null){
			FDCMsgBox.showWarning(ui, "请选择行!");
			return;
		}
		ScheduleNewTaskEntryInfo entry=(ScheduleNewTaskEntryInfo)row.getUserObject();
		entry.put("ScheduleCalendarInfo",calendarInfo);
		FDCScheduleTaskInfo task=new FDCScheduleTaskInfo();
		task.putAll(entry);
		task.put("ScheduleNewTaskEntryInfo", entry);
		task.put("projectId",ui.getUIContext().get("projectId"));
		FDCWBSInfo wbs=new FDCWBSInfo();
		//每次打开都new一个WBSID导致之前保存的属性无法带出来
		if(entry == null || entry.getWbs() == null || entry.getWbs().getId() == null){
			wbs.setId(BOSUuid.create(wbs.getBOSType()));
		}else{
			wbs = entry.getWbs();
		}
		task.setWbs(wbs);
		if(row.getCell("effectTimes") != null && row.getCell("effectTimes").getValue() != null){
			if(row.getCell("effectTimes").getValue() instanceof BigDecimal){
				task.setDuration(((BigDecimal)row.getCell("effectTimes").getValue()).intValue());
			}else if(row.getCell("effectTimes").getValue() instanceof Integer){
				task.setDuration(((Integer)row.getCell("effectTimes").getValue()).intValue());	
			}
		}
		KDTask selectTask=new KDTask(getGanttProject().getTaskManager(),task);
		if(e!=null&&e.getActionCommand()!=null&&e.getActionCommand().equals("dependTask")){
			selectTask=(KDTask)FDCScheduleTaskPropertiesUI.showUI(ui, selectTask, ui.getOprtState(),1);
		}else{
			selectTask=(KDTask)FDCScheduleTaskPropertiesUI.showUI(ui, selectTask, ui.getOprtState());
		}
		
		
		task = (FDCScheduleTaskInfo)selectTask.getScheduleTaskInfo();
		entry=(ScheduleNewTaskEntryInfo)task.remove("ScheduleNewTaskEntryInfo");
		entry.remove("ScheduleCalendarInfo");
		entry.putAll(task);
		loadRow(row);
	}
	private void init(){
		initTable();
		ui.contNewTask.setEnableActive(false);
		ui.contNewTask.add(actionAddRow);
		ui.contNewTask.add(actionDeleteRow);
		ui.contNewTask.add(actionProperty);
		
	}
	private void initTable() {
		table.checkParsed();
		// set editor
		KDBizPromptBox prmt = new KDBizPromptBox();
		prmt.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7ScheduleTaskQuery");
		prmt.setRequired(true);
		prmt.setDisplayFormat("$name$");
		prmt.setEditFormat("$number$");
		prmt.setCommitFormat("$number$");
		prmt.setEditable(false);
		EntityViewInfo view=new EntityViewInfo();
		view.setFilter(new FilterInfo());
		view.getFilter().appendFilterItem("project.id", ui.getUIContext().get("projectId").toString());
		//应该只能调整审批及执行中的任务
		Set set=new HashSet();
		set.add(ScheduleStateEnum.AUDITTED_VALUE);
		set.add(ScheduleStateEnum.EXETING_VALUE);
		view.getFilter().getFilterItems().add(new FilterItemInfo("schedule.state", set,CompareType.INCLUDE));
		prmt.setEntityViewInfo(view);
		SelectorItemCollection selector=new SelectorItemCollection();
		selector.add("*");
		selector.add("wbs.id");
		selector.add("wbs.level");
		selector.add("wbs.longNumber");
		selector.add("wbs.number");
		selector.add("adminDept.id");
		selector.add("adminDept.number");
		selector.add("adminDept.name");
		
		selector.add("respDept.id");
		selector.add("respDept.number");
		selector.add("respDept.name");
		
		selector.add("adminPerson.id");
		selector.add("adminPerson.number");
		selector.add("adminPerson.name");
		
		selector.add("administrator.id");
		selector.add("administrator.number");
		selector.add("administrator.name");
		
		selector.add("noter.id");
		selector.add("noter.number");
		selector.add("noter.name");
		
		selector.add("manager.id");
		selector.add("manager.number");
		selector.add("manager.name");
		prmt.setSelectorCollection(selector);
		table.getColumn("parentTask").setEditor(new KDTDefaultCellEditor(prmt));
		
		KDTextField tf=new KDTextField();
		tf.setMaxLength(80);
		table.getColumn("name").setEditor(new KDTDefaultCellEditor(tf));
		
		KDDatePicker pkDate=new KDDatePicker();
		pkDate.setSupportedEmpty(false);
		table.getColumn("startDate").setEditor(new KDTDefaultCellEditor(pkDate));
		pkDate=new KDDatePicker();
		pkDate.setSupportedEmpty(false);
		table.getColumn("endDate").setEditor(new KDTDefaultCellEditor(pkDate));
		
		KDFormattedTextField kf=new KDFormattedTextField();
		kf.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		kf.setPrecision(0);
		kf.setRemoveingZeroInDispaly(true);
		kf.setRemoveingZeroInEdit(true);
		kf.setHorizontalAlignment(KDFormattedTextField.RIGHT);
		kf.setMinimumValue(FDCHelper.ONE);
		kf.setMaximumValue(FDCHelper.MAX_VALUE);
		table.getColumn("effectTimes").setEditor(new KDTDefaultCellEditor(kf));
		
		prmt=new KDBizPromptBox();
		prmt.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7FullOrgUnitQuery");
		prmt.setRequired(true);
		prmt.setDisplayFormat("$name$");
		prmt.setEditFormat("$number$");
		prmt.setCommitFormat("$number$");
		table.getColumn("adminDept").setEditor(new KDTDefaultCellEditor(prmt));
		String cuId = SysContext.getSysContext().getCurrentFIUnit().getId().toString();
		ScheduleClientHelper.setRespDept((KDBizPromptBox)new KDTDefaultCellEditor(prmt).getComponent(), ui,cuId);
		
		prmt=new KDBizPromptBox();
		prmt.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7PersonQuery");
		prmt.setRequired(true);
		prmt.setDisplayFormat("$name$");
		prmt.setEditFormat("$number$");
		prmt.setCommitFormat("$number$");
		table.getColumn("adminPerson").setEditor(new KDTDefaultCellEditor(prmt));
		
		table.addKDTEditListener(new KDTEditAdapter() {

			public void editStopped(KDTEditEvent e) {
				table_editStopped(e);
			}

		});

		table.addKDTMouseListener(new KDTMouseListener() {
			public void tableClicked(KDTMouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
					if(e.getColIndex()==table.getColumnIndex("dependTask")){
						ActionEvent event=new ActionEvent(table,1,"dependTask");
						ScheduleNewTaskHelper.this.property(event);
					}/*else{
						ScheduleNewTaskHelper.this.property(null);
					}*/
				}
			}
		});
		// set column
		table.getColumn("number").getStyleAttributes().setLocked(true);
		table.getColumn("number").getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
		table.getColumn("parentTask").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		table.getColumn("name").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		table.getColumn("startDate").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		table.getColumn("endDate").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		table.getColumn("effectTimes").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		table.getColumn("adminDept").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		table.getColumn("adminPerson").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		table.getColumn("dependTask").getStyleAttributes().setLocked(true);
	}

	protected void table_editStopped(KDTEditEvent e) {
		if(e.getValue()==null&&e.getOldValue()==null){
			return;
		}
		if(e.getValue()!=null&&e.getValue().equals(e.getOldValue())){
			return;
		}
		ui.setDataChanged();
		IRow row = getTable().getRow(e.getRowIndex());
		if ("parentTask".equals(getTable().getColumnKey(e.getColIndex()))) {
			FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) e.getValue();
			ScheduleNewTaskEntryInfo entry = (ScheduleNewTaskEntryInfo)row.getUserObject();
			if(task==null){
				row.getCell("number").setValue(null);
				entry.setNumber(null);
				//delete 
				ui.getTaskList().deleteItem(entry);
				return;
			}
			if(e.getOldValue()!=null){
				ui.getTaskList().deleteItem(entry);
			}
			entry.setParentTask(task);
			entry.setAdminDept(task.getAdminDept());
			entry.setRespDept(task.getAdminDept());
			entry.setAdminPerson(task.getAdminPerson());
			entry.setAdministrator(task.getAdministrator());
			entry.setNoter(task.getNoter());
			entry.setManager(task.getManager());
			
//			fillRow(task, row);
//			row.getCell("startDate").setValue(task.getStart());
			if(task!=null){
				try {
					//新增的任务开始时间跟结束时间一样
					entry.setStart(task.getStart());
					entry.setEnd(task.getStart());
					String number=getNewWBSNumber(task.getWbs());
					String longNumber = task.getWbs().getLongNumber()+"!"+number;
					number = longNumber.replace('!', '.');
					entry.setNumber(number);
					entry.setLongNumber(longNumber);
					entry.setLevel(task.getWbs().getLevel()+1);
					entry.setIsLeaf(true);
					entry.setEffectTimes(FDCHelper.ONE);
					loadRow(row);
					FDCWBSInfo wbs=new FDCWBSInfo();
					wbs.setId(BOSUuid.create(wbs.getBOSType()));
					entry.setWbs(wbs);
				} catch (Exception exc) {
					ui.handUIException(exc);
				}
			}
			ui.getTaskList().addNewItem(task!=null?task.getId().toString():null, entry);
/*			KDTEditEvent e1=new KDTEditEvent(e.getSource(), null, task.getStart(), e.getRowIndex(), getTable().getColumnIndex("startDate"), false, e.getType());
			table_editStopped(e1);*/
		}
		
//		ScheduleAdjustTaskEntryInfo entry=(ScheduleAdjustTaskEntryInfo)row.getUserObject();
		if ("startDate".equals(getTable().getColumnKey(e.getColIndex()))) {
			//设置结束时间
			Date startDate=(Date)e.getValue();
			if(calendarInfo.isWeekTime(startDate)){
				e.setCancel(true);
				FDCMsgBox.showWarning(ui, "当前选择的为休息日，自动跳到下一工作日");
				Date newDate = ScheduleCalendarHelper.getNextWorkDay(startDate, calendarInfo);
				row.getCell("startDate").setValue(newDate);
				startDate=newDate;
//				return;
			}
			BigDecimal adjustEffectTimes=FDCHelper.toBigDecimal(row.getCell("effectTimes").getValue());
			Date endDate=ScheduleCalendarHelper.getEndDate(startDate, adjustEffectTimes, calendarInfo);
			row.getCell("endDate").setValue(endDate);
		}
		if ("endDate".equals(getTable().getColumnKey(e.getColIndex()))) {
			//设置工期
			Date endDate=(Date)e.getValue();
			if(calendarInfo.isWeekTime(endDate)){
				e.setCancel(true);
				FDCMsgBox.showWarning(ui, "当前选择的为休息日，自动跳到下一工作日");
				Date newDate = ScheduleCalendarHelper.getNextWorkDay(endDate, calendarInfo);
				row.getCell("endDate").setValue(newDate);
				endDate=newDate;
//				return;
			}
			Date startDate=(Date)row.getCell("startDate").getValue();
			if(startDate!=null&&startDate.compareTo(endDate)>=0){
				FDCMsgBox.showWarning(ui, "结束时间必须大于开始时间!");
				row.getCell("endDate").setValue(e.getOldValue());
				e.setCancel(true);
//				SysUtil.abort();
			}else{
				BigDecimal adjustEffectTimes=ScheduleCalendarHelper.getEffectTimes(startDate, endDate, calendarInfo);
				row.getCell("effectTimes").setValue(adjustEffectTimes);
			}
		}
		if ("effectTimes".equals(getTable().getColumnKey(e.getColIndex()))) {
			//设置结束时间
			BigDecimal adjustEffectTimes=FDCHelper.toBigDecimal(e.getValue());
			Date startDate=(Date)row.getCell("startDate").getValue();
			Date endDate=ScheduleCalendarHelper.getEndDate(startDate, adjustEffectTimes, calendarInfo);
			row.getCell("endDate").setValue(endDate);
		}
		
		//重算搭接时间
		ScheduleNewTaskEntryInfo entry = (ScheduleNewTaskEntryInfo)row.getUserObject();
		if(entry!=null){
			entry.reCalcPrefixTaskLinkTimes(calendarInfo);
		}
	}

	protected void loadRow(IRow row){
		ScheduleNewTaskEntryInfo entry=(ScheduleNewTaskEntryInfo)row.getUserObject();
		row.getCell("number").setValue(entry.getNumber());
		row.getCell("name").setValue(entry.getName());
		row.getCell("startDate").setValue(entry.getStart());
		row.getCell("endDate").setValue(entry.getEnd());
		row.getCell("effectTimes").setValue(entry.getEffectTimes());
//		row.getCell("adminDept").setValue(entry.getAdminDept()!=null?entry.getAdminDept().getName():null);
		row.getCell("adminDept").setValue(entry.getRespDept()!=null?entry.getRespDept().getName():null);
		row.getCell("adminPerson").setValue(entry.getAdminPerson()!=null?entry.getAdminPerson().getName():null);
		String prefixTask=null;
		if(entry.getPrefixEntrys()!=null){
			for(int i=0;i<entry.getPrefixEntrys().size();i++){
				ScheduleAdjustPrefixTaskInfo scheduleAdjustPrefixTaskInfo = entry.getPrefixEntrys().get(i);
				FDCScheduleTaskInfo task = scheduleAdjustPrefixTaskInfo.getPrefixTask();
				if(task==null){
					continue;
				}
				if(prefixTask==null){
					prefixTask=task.getName();
				}else{
					prefixTask=prefixTask+";"+task.getName();
				}
			}
		}
		row.getCell("dependTask").setValue(prefixTask);
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
	
	ItemAction actionProperty=new ItemAction(){
		{
			putValue(ItemAction.NAME, "属性");
			putValue(ItemAction.SHORT_DESCRIPTION, "属性");
			putValue(ItemAction.SMALL_ICON, EASResource.getIcon("imgTbtn_modifyattribute"));
		}
		public void actionPerformed(ActionEvent e) {
			property(e);
		}
	};
	
	protected void setOptState(String oprtState){
		if(oprtState!=null&&oprtState.equals(OprtState.EDIT)){
			actionAddRow.setEnabled(true);
			actionDeleteRow.setEnabled(true);
			actionProperty.setEnabled(true);
		}else{
			actionAddRow.setEnabled(false);
			actionDeleteRow.setEnabled(false);
			actionProperty.setEnabled(false);
		}
		
	}

	private Map wbsNumberMap=new HashMap();
	private String getNewWBSNumber(FDCWBSInfo parentWbs) throws ScheduleException, BOSException{
		String number=(String)wbsNumberMap.get(parentWbs.getId());
		if (number == null) {
			number = WBSCodeRuleFactory.getRemoteInstance().getNewNumber(parentWbs.getLevel() + 1, parentWbs.getId().toString(), ui.getUIContext().get("projectId").toString());
		}else{
			//+1
			char [] numberChar=number.toCharArray();
			int newNnumber=Integer.parseInt(number)+1;
			char [] newNumberChar=String.valueOf(newNnumber).toCharArray();
			if(newNumberChar.length>numberChar.length){
				//超过最大编号
				FDCMsgBox.showWarning(ui, "超过WBS最大编号");
				SysUtil.abort();
			}
			
			//替换
			for(int i=1;i<=newNumberChar.length;i++){
				numberChar[numberChar.length-i]=newNumberChar[newNumberChar.length-i];
			}
			number=String.valueOf(numberChar);
		}
		wbsNumberMap.put(parentWbs.getId(),number);
		return number;
	}
	
	public static ScheduleGanttProject getGanttProject(){
		return ScheduleGanttProject.getScheduleGanttProjectSingleForOther();
	}

}
