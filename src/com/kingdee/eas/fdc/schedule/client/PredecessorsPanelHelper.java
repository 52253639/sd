package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComponent;

import net.sourceforge.ganttproject.task.dependency.TaskDependency;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyCollectionMutator;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyConstraint;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyException;
import net.sourceforge.ganttproject.task.dependency.constraint.FinishStartConstraintImpl;

import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.fdc.schedule.framework.ScheduleBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.parser.ScheduleParserHelper;
import com.kingdee.eas.util.SysUtil;

/**
 * dependee 后置任务 dependant 前置任务
 * 
 * @author xiaohong_shi
 * 
 */
public class PredecessorsPanelHelper implements ITaskPanelHelper {
	private final FDCScheduleTaskPropertiesUI taskPropertiesUI;
	private final TaskDependencyCollectionMutator myMutator;
	private final KDTask myTask;
	private final KDTable table;

	public PredecessorsPanelHelper(FDCScheduleTaskPropertiesUI ui) {
		taskPropertiesUI = ui;
		this.myTask = ui.getSelectedTask();
		this.myMutator = this.myTask.getManager().getDependencyCollection().createMutator();
		table = ui.predecessorsTable;
		initTable();
	}

	private void initTable() {
		getTable().checkParsed();
		// 添加，删除按钮
		ItemAction addNewAction = new ItemAction() {
			public void actionPerformed(ActionEvent e) {
				actionAddNew_Performed(e);
			}
		};
		ItemAction deleteAction = new ItemAction() {
			public void actionPerformed(ActionEvent e) {
				actionDelete_Performed(e);
			}
		};
		JButton btnAddNew = taskPropertiesUI.contPredecessorPanel.add(addNewAction);
		btnAddNew.setText("新增");
		btnAddNew.setIcon(SCHClientHelper.ICON_ADDLINE);
		JButton btnDelete = taskPropertiesUI.contPredecessorPanel.add(deleteAction);
		btnDelete.setText("删除");
		btnDelete.setIcon(SCHClientHelper.ICON_REMOVELINE);
		taskPropertiesUI.contPredecessorPanel.setVisibleOfExpandButton(true);
		taskPropertiesUI.contPredecessorPanel.setEnableActive(false);
		// WBSNumber F7
		KDBizPromptBox f7 = new KDBizPromptBox();
		f7.setDisplayFormat("$number$");
		f7.setEditFormat("$number$");
		f7.setCommitFormat("$number$");
		Set filterIdSet = new HashSet();
		// 把自己及下级排除
		filterIdSet.add(myTask.getScheduleTaskInfo().getId().toString());
		ScheduleBaseInfo info = myTask.getScheduleTaskInfo().getScheduleBase();
		if (!myTask.getScheduleTaskInfo().isIsLeaf()) {
			String mySelfLongNumber = myTask.getScheduleTaskInfo().getLongNumber();
			for (Iterator iter = info.getScheduleTasks().iterator(); iter.hasNext();) {
				ScheduleTaskBaseInfo task = (ScheduleTaskBaseInfo) iter.next();
				if (task.getLongNumber() != null && task.getLongNumber().indexOf(mySelfLongNumber + "!") > -1) {
					filterIdSet.add(task.getId().toString());
				}
			}
		}
		final F7ScheduleTaskPromptBox selectorBox = new F7ScheduleTaskPromptBox(taskPropertiesUI, info, filterIdSet, null);
		f7.setSelector(selectorBox);
		// f7.setSelector(s);
		ObjectValueRender renderer = new ObjectValueRender();
		renderer.setFormat(new BizDataFormat("$number$"));
		getTable().getColumn("number").setEditor(new KDTDefaultCellEditor(f7));
		getTable().getColumn("number").setRenderer(renderer);
		getTable().getColumn("number").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		getTable().getColumn("name").getStyleAttributes().setLocked(true);
		f7.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				Set filterIdSet = new HashSet();
				// 把自己排除
				filterIdSet.add(myTask.getScheduleTaskInfo().getId().toString());
				// 把已选则的前置任务排除
				for (int i = 0; i < table.getRowCount(); i++) {
					if (table.getCell(i, "number").getValue() != null) {
						FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) table.getCell(i, "number").getValue();
						filterIdSet.add(taskInfo.getId().toString());
					}
				}
				ScheduleBaseInfo info = myTask.getScheduleTaskInfo().getScheduleBase();
				if (!myTask.getScheduleTaskInfo().isIsLeaf()) {
					String mySelfLongNumber = myTask.getScheduleTaskInfo().getLongNumber();
					for (Iterator iter = info.getScheduleTasks().iterator(); iter.hasNext();) {
						ScheduleTaskBaseInfo task = (ScheduleTaskBaseInfo) iter.next();
						if (task.getLongNumber() != null && task.getLongNumber().indexOf(mySelfLongNumber + "!") > -1) {
							filterIdSet.add(task.getId().toString());
						}
					}
				}

				for (int i = 0; i < getTable().getRowCount(); i++) {
					IRow row = getTable().getRow(i);
					FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) row.getCell("number").getUserObject();
					if (task != null) {
						filterIdSet.add(task.getId().toString());
					}
				}
				F7ScheduleTaskUI f7ScheduleTaskUI = selectorBox.getF7ScheduleTaskUI();
				if (f7ScheduleTaskUI != null) {
					f7ScheduleTaskUI.setFilterTask(filterIdSet);
				}
			}
		});

		// 任务名称

		// 搭接关系
		KDComboBox linkBox = new KDComboBox();
		linkBox.addItems(TaskLinkTypeEnum.getEnumList().toArray());
		getTable().getColumn("linkType").setEditor(new KDTDefaultCellEditor(linkBox));
		getTable().getColumn("linkType").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		// 搭接时间
		KDFormattedTextField linkTimes = new KDFormattedTextField();
		linkTimes.setDataType(KDFormattedTextField.INTEGER_TYPE);
		linkTimes.setPrecision(0);
		linkTimes.setRemoveingZeroInDispaly(true);
		linkTimes.setRemoveingZeroInEdit(true);
		getTable().getColumn("linkTimes").setEditor(new KDTDefaultCellEditor(linkTimes));
		getTable().getColumn("linkTimes").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		getTable().addKDTEditListener(new KDTEditAdapter() {
			public void editStopped(KDTEditEvent e) {
				table_editStopped(e);
			}
		});
	}

	protected void actionDelete_Performed(ActionEvent e) {
		IRow row = FDCTableHelper.getSelectedRow(getTable());
		if (row == null) {
			FDCMsgBox.showInfo("请先选择行");
			SysUtil.abort();
		}
		if (row.getUserObject() != null) {
			TaskDependency dependency = (TaskDependency) row.getUserObject();
			dependency.delete();
		}
		getTable().removeRow(row.getRowIndex());
	}

	protected void actionAddNew_Performed(ActionEvent e) {
		IRow row = getTable().addRow();
		row.getCell("linkTimes").setValue(FDCHelper.ZERO);
		row.getCell("linkType").setValue(TaskLinkTypeEnum.FINISH_START);
	}

	public void load() {
		getTable().removeRows();
		TaskDependency[] dependencies = myTask.getDependenciesAsDependant().toArray();
		for (int i = 0; i < dependencies.length; i++) {
			TaskDependency dependency = dependencies[i];
			IRow row = getTable().addRow();
			row.setUserObject(dependency);
			KDTask dependee = (KDTask) dependency.getDependee();// 前置任务
			FDCScheduleTaskInfo taskInfo = (FDCScheduleTaskInfo) dependee.getScheduleTaskInfo();
			row.getCell("number").setValue(taskInfo);
			row.getCell("name").setValue(taskInfo.getName());
			row.getCell("linkType").setValue(ScheduleParserHelper.getTaskLinkTypeByConstraint(dependency.getConstraint()));
			// int difference = 0;
			// TaskDependencyConstraint linkType = dependency.getConstraint();
			// if(linkType instanceof FinishStartConstraintImpl){
			// Date preEndDate =
			// ScheduleParserHelper.parseGanttCalendarToDate(dependency
			// .getDependee().getEnd());
			// Date nextStartDate =
			// ScheduleParserHelper.parseGanttCalendarToDate
			// (dependency.getDependant().getStart());
			// difference =
			// Integer.parseInt(ScheduleCalendarHelper.getEffectTimes
			// (preEndDate,
			// nextStartDate,taskInfo.getSchedule().getCalendar()).toString()) -
			// 2;
			// System.out.println("PPP: diff+"+difference+"; "+preEndDate.
			// toLocaleString
			// ()+" : "+nextStartDate.toLocaleString()+"; "+dependency
			// .getDifference());
			// }else if (linkType instanceof StartStartConstraintImpl){
			// Date preStartDate =
			// ScheduleParserHelper.parseGanttCalendarToDate(
			// dependency.getDependee().getStart());
			// Date nextStartDate =
			// ScheduleParserHelper.parseGanttCalendarToDate
			// (dependency.getDependant().getStart());
			// difference =
			// Integer.parseInt(ScheduleCalendarHelper.getEffectTimes
			// (preStartDate,
			// nextStartDate,taskInfo.getSchedule().getCalendar()).toString())
			// -1;
			// System.out.println("PPP: diff+"+difference+"; "+preStartDate.
			// toLocaleString
			// ()+" : "+nextStartDate.toLocaleString()+"; "+dependency
			// .getDifference());
			// }else if(linkType instanceof FinishFinishConstraintImpl){
			// Date preEndDate =
			// ScheduleParserHelper.parseGanttCalendarToDate(dependency
			// .getDependee().getEnd());
			// Date nextEndDate =
			// ScheduleParserHelper.parseGanttCalendarToDate(dependency
			// .getDependant().getEnd());
			// int tempInt =
			// Integer.parseInt(ScheduleCalendarHelper.getEffectTimes
			// (preEndDate,
			// nextEndDate,taskInfo.getSchedule().getCalendar()).toString());
			// if(tempInt < 0){
			// difference = tempInt + 1;
			// }else{
			// difference = tempInt - 1;
			// }
			// System.out.println("PPP: diff+"+difference+"; "+preEndDate.
			// toLocaleString
			// ()+" : "+nextEndDate.toLocaleString()+"; "+dependency
			// .getDifference());
			// }

			row.getCell("linkTimes").setValue(new Integer(dependency.getDifference()));
		}
	}

	/**
	 * 将结果更新到Info 的depend分录
	 */
	public void store() {
		// EditUI保存的时候统一生成
		/*
		 * FDCScheduleTaskInfo
		 * taskInfo=(FDCScheduleTaskInfo)this.myTask.getScheduleTaskInfo();
		 * taskInfo.getDependEntrys().clear(); TaskDependency[] depend =
		 * myTask.getDependenciesAsDependee().toArray(); //TODO H 取后置节点 这里可能会有差异
		 * for(int i=0;i<depend.length;i++){ FDCScheduleTaskDependInfo item=new
		 * FDCScheduleTaskDependInfo(); TaskDependency taskDependency =
		 * depend[i]; item.setId(BOSUuid.create(item.getBOSType()));
		 * item.setTask(taskInfo);
		 * item.setDependTask((FDCScheduleTaskInfo)((KDTask
		 * )taskDependency.getDependee()).getScheduleTaskInfo());
		 * item.setType(ScheduleParserHelper
		 * .getTaskLinkTypeByConstraint(taskDependency.getConstraint()));
		 * taskInfo.getDependEntrys().add(item); }
		 */
	}

	private void verify() {
		// 每一行必须录入task
		for (int i = 0; i < getTable().getRowCount(); i++) {
			if (getTable().getRow(i).getUserObject() == null) {
				FDCMsgBox.showConfirm2(taskPropertiesUI, "前置任务不能为空！");
				SysUtil.abort();
			}
		}
	}

	protected void table_editStopped(KDTEditEvent e) {
		IRow row = getTable().getRow(e.getRowIndex());
		TaskDependency dependency = (TaskDependency) row.getUserObject();

		if (getTable().getColumnIndex("number") == e.getColIndex()) {
			// 删除旧的，新建新的
			// 关系处理
			try {
				if (dependency != null) {
					dependency.delete();
				}
				FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) e.getValue();
				if (task != null) {
					KDTask newTask = taskPropertiesUI.getScheduleGanttProject().getKDTaskById(task.getId().toString());
					dependency = createDependency(dependency, newTask);
					row.setUserObject(dependency);
					row.getCell("name").setValue(task.getName());
				}
			} catch (TaskDependencyException e1) {
				taskPropertiesUI.handUIException(e1);
			}
		}
		if (getTable().getColumnIndex("linkType") == e.getColIndex()) {
			if (dependency != null) {
				dependency.setConstraint(ScheduleParserHelper.getTaskDependencyConstraintByLinkType((TaskLinkTypeEnum) e.getValue()));
			}
		}
		if (getTable().getColumnIndex("linkTimes") == e.getColIndex()) {
			try {
				if (dependency != null) {
					dependency.delete();
				}
				FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) getTable().getCell(e.getRowIndex(), "number").getValue();
				if (task != null) {
					KDTask newTask = taskPropertiesUI.getScheduleGanttProject().getKDTaskById(task.getId().toString());
					dependency = createDependency(dependency, newTask);
					row.setUserObject(dependency);
					int intValue = 0;
					int oldVallue = 0;
					if (e.getValue() != null) {
						if (e.getValue() instanceof BigDecimal) {
							intValue = ((BigDecimal) e.getValue()).intValue();
						} else if (e.getValue() instanceof Integer) {
							intValue = ((Integer) e.getValue()).intValue();
						}
					}
					if (e.getOldValue() != null) {
						if (e.getOldValue() instanceof BigDecimal) {
							oldVallue = ((BigDecimal) e.getOldValue()).intValue();
						} else if (e.getOldValue() instanceof Integer) {
							oldVallue = ((Integer) e.getOldValue()).intValue();
						}
					}
					int dif = intValue - oldVallue;
					for (int i = 0; i < getTable().getRowCount(); i++) {
						if (i == e.getRowIndex())
							continue;
						if (getTable().getCell(i, "linkTimes").getValue() != null) {
							int newValue = Integer.parseInt(getTable().getCell(i, "linkTimes").getValue().toString()) + dif;
							getTable().getCell(i, "linkTimes").setValue(new Integer(newValue));
						}
					}
					dependency.setDifference(intValue);
					// row.getCell("name").setValue(task.getName());
				}
			} catch (TaskDependencyException e1) {
				taskPropertiesUI.handUIException(e1);
			}
			// if(dependency!=null){
			// int intValue = 0;
			// if(e.getValue()!=null){
			// intValue = ((Integer)e.getValue()).intValue();
			// }
			// dependency.setDifference(intValue);
			// }
		}

	}

	private KDTable getTable() {
		return table;
	}

	/**
	 * create a new dependency
	 * 
	 * @param newTask
	 * @return
	 * @throws TaskDependencyException
	 */
	private TaskDependency createDependency(TaskDependency dependency, KDTask newTask) throws TaskDependencyException {
		TaskDependencyConstraint constraint = null;
		if (dependency != null) {
			constraint = dependency.getConstraint();
		} else {
			constraint = new FinishStartConstraintImpl();
		}

		TaskDependency dep = myMutator.createDependency(myTask, newTask, constraint);
		if (dependency != null) {
			dep.setDifference(dependency.getDifference());
		}
		return dep;
	}

	public void commit() {
		if (taskPropertiesUI.isExecuting() || taskPropertiesUI.getOprtState().equals(OprtState.VIEW)) {
			return;
		}
		this.verify();
		this.myMutator.commit();
		this.store();
	}

	public void setEditUIStatus() {
		// TODO Auto-generated method stub

	}

	public void setExecutingUIStatus() {
		this.getTable().getStyleAttributes().setLocked(true);
		Object[] buttons = taskPropertiesUI.contPredecessorPanel.getButtons();
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i] instanceof JComponent) {
				((JComponent) buttons[i]).setEnabled(false);
			}
		}
	}

	public void setViewUIStatus() {
		this.getTable().getStyleAttributes().setLocked(true);
		Object[] buttons = taskPropertiesUI.contPredecessorPanel.getButtons();
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i] instanceof JComponent) {
				((JComponent) buttons[i]).setEnabled(false);
			}
		}
	}

}
