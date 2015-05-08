/*
 * Created on Mar 10, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.kingdee.eas.fdc.schedule.framework.parser;

import java.awt.Component;
import java.awt.Font;
import java.text.ParseException;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.sourceforge.ganttproject.CustomPropertyDefinition;
import net.sourceforge.ganttproject.GanttTreeTableModel;
import net.sourceforge.ganttproject.task.CustomColumn;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.script.miniscript.exec.Logger;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleConstant;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.client.CustomerGenerateStatePanel;
import com.kingdee.eas.fdc.schedule.client.FDCSCHClientHelper;
import com.kingdee.eas.fdc.schedule.client.ScheduleClientHelper;
import com.kingdee.eas.fdc.schedule.framework.ScheduleBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskPropertyInfo;
import com.kingdee.eas.fdc.schedule.framework.TaskPropertyTypeEnum;
import com.kingdee.eas.fdc.schedule.framework.client.ScheduleCellEditor;
import com.kingdee.eas.fdc.schedule.framework.ext.GanttTreeTableModelExt;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.ext.PropertyTypeEncoder;
import com.kingdee.eas.fdc.schedule.framework.ext.ScheduleGanttProject;
import com.kingdee.eas.fdc.schedule.framework.util.ScheduleTaskPropertyHelper;
import com.kingdee.eas.util.SysUtil;

public class TaskPropertiesHandler extends AbstractKDHandler implements
		IKDHandler {
	// private final CustomColumnsStorage myColumnStorage;
	private final ScheduleGanttProject ganttProject;

	public TaskPropertiesHandler(ScheduleGanttProject ganttProject) {
		this.ganttProject = ganttProject;
	}

	private void loadTaskProperty(ScheduleTaskPropertyInfo property) {
		String name = property.getName();
		String id = property.getPropertyId();
		String valueType = property.getValueType();
		TaskPropertyTypeEnum type = property.getType();
		int displayUI = property.getDisplayUI();
		int mark = ScheduleClientHelper.getUIMark(this.ganttProject
				.getScheduleUIFacede());
		boolean isDisplay = ScheduleTaskPropertyHelper.isIncludeUI(mark, displayUI);
		if (!isDisplay) {
			Logger.warn("unDisplay:" + name);
		}
		if (type == TaskPropertyTypeEnum.CUSTOM) {
			CustomColumn cc;
			// 没有默认值的列
			String valueStr = null;
			CustomPropertyDefinition stubDefinition = PropertyTypeEncoder
					.decodeTypeAndDefaultValue(valueType, valueStr);
			cc = new CustomColumn(name, stubDefinition.getType(),
					stubDefinition.getDefaultValue());
			cc.setId(id);

			this.ganttProject.getCustomColumnsStorage().addCustomColumn(cc);
			resetColumnEditor(property);
		}
	}

	public void handle() {
		ScheduleBaseInfo scheduleBaseInfo = getScheduleBaseInfo();
		// 先清空所有已存在列
		ganttProject.getTree().getTable().setColumnModel(
				new DefaultTableColumnModel());
		for (Iterator iter = scheduleBaseInfo.getTaskPropertys().iterator(); iter
				.hasNext();) {
			ScheduleTaskPropertyInfo property = (ScheduleTaskPropertyInfo) iter
					.next();
			loadTaskProperty(property);
			// 重新设置default 列 jtable列的控件
			// System.out.println(property.getName());
		}
		// 设置开始日期、结束日期
		ScheduleTaskPropertyInfo prop = new ScheduleTaskPropertyInfo();
		prop.setName(GanttTreeTableModel.strColBegDate);
		prop.setValueType("date");
		resetColumnEditor(prop);
		prop.setName(GanttTreeTableModel.strColEndDate);
		resetColumnEditor(prop);
		// ganttProject.getTree().getTreeTable().initDisplay();
		// ganttProject.getTree().getTreeTable().setColumnHorizontalAlignment(
		// GanttTreeTableModelExt.strColPrefixNode, SwingConstants.LEFT);
	}

	/**
	 * 重新设置列的编辑器
	 * <p>
	 * 顺便设置表现器<br>
	 * 状态列展示图标<br>
	 * 名称列颜色根据任务类别改变，由于是tree中的renderer，在这写会把tree覆盖，写在KDGanttTreeUI中
	 * 
	 * @param property
	 */
	protected void resetColumnEditor(ScheduleTaskPropertyInfo property) {
		JTable table = ganttProject.getTree().getTable();
		String name = property.getName();
		TableColumnModel columnModel = this.ganttProject.getTree().getTable()
				.getColumnModel();
		if (GanttTreeTableModelExt.strColState.equals(name)) {
			// 如果是状态列，由于值设置的是数字，需转换成勾、圈显示
			TableColumn column = table.getColumn(GanttTreeTableModelExt.strColState);
			column.setCellRenderer(new TableCellRenderer() {
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
					try {
						Class customStateLabel = Class.forName("com.kingdee.eas.fdc.schedule.client.XHGenerateComponent");
						CustomerGenerateStatePanel o = (CustomerGenerateStatePanel) customStateLabel.newInstance();
						return o.getStateLabel((value!=null?Integer.valueOf(value.toString()).intValue():-1));
					} catch (ClassNotFoundException e) {
						int width = table.getColumn(table.getColumnName(column)).getWidth();
						JLabel lb = new JLabel();
						lb.setFont(new Font("微软雅黑", Font.PLAIN, 20));
						lb.setOpaque(true);
						if (value != null) {
							int stateValue = Integer.valueOf(value.toString()).intValue();
							StringBuffer html = new StringBuffer();
							html.append("<html><table width = ");
							html.append(width);
							html.append("><tr><td align='center'><strong>");
							switch (stateValue) {
							case FDCScheduleConstant.ONTIME:
								html.append("<font color='#0ADC0A' size='5'>√");
								break;
							case FDCScheduleConstant.DELAY:
								html.append("<font color='red' size='5'>√");
								break;
							case FDCScheduleConstant.CONFIRMED:
								html.append("<font color='#DCB400' size='6'>●");
								break;
							case FDCScheduleConstant.DELAYANDNOTCOMPLETE:
								html.append("<font color='red' size='6'>●");
								break;
							}
							html.append("</font></strong></td></tr></table><html>");
							lb.setText(html.toString());
						}
						return lb;
					} catch (InstantiationException e) {
						return null;
					} catch (IllegalAccessException e) {
						return null;
					}
					
				}
			});
		}
		
		for (int i = columnModel.getColumnCount() - 1; i >= 0; i--) {
			String colName = table.getColumnName(i);
			if (name != null && name.equalsIgnoreCase(colName)) {
				TableColumn column = columnModel.getColumn(i);
				TableCellEditor cellEditor = getCellEditor(property);
				if (cellEditor != null) {
					column.setCellEditor(cellEditor);
				}
				return;
			}
		}
		
	}

	public void haneleAdminPerson(KDBizPromptBox prmt) {

		try {
			Object task = TaskPropertiesHandler.this.ganttProject
					.getTaskSelectionContext().getSelectedTasks().get(0);
			KDTask kdTask = (KDTask) task;
			Object orgUnit = ((FDCScheduleTaskInfo) kdTask
					.getScheduleTaskInfo()).get("adminDept");

			CtrlUnitInfo currentCtrlUnit = SysContext.getSysContext()
					.getCurrentCtrlUnit();
			if (!FDCSCHClientHelper.filterRespPerson()) {// 跟合同的责任人保持一致
				if (FDCSCHClientHelper.canSelectOtherOrgPerson()) {
					FDCClientUtils.setPersonF7(prmt, null, null);
				} else {
					if (currentCtrlUnit == null) {
						FDCMsgBox.showWarning("管理单元为空，出错！");
						SysUtil.abort();
					}
					FDCClientUtils.setPersonF7(prmt, null, currentCtrlUnit
							.getId().toString());
				}
			} else {
				// 如果没有选择责任部门
				if (orgUnit == null) {
					if (currentCtrlUnit == null) {
						FDCMsgBox.showWarning("管理单元为空，出错！");
						SysUtil.abort();
					}
					FDCClientUtils.setPersonF7(prmt, null, currentCtrlUnit
							.getId().toString());
				} else {// 选择了责任部门
					if (orgUnit != null
							&& orgUnit instanceof CostCenterOrgUnitInfo) {
						if (((CostCenterOrgUnitInfo) orgUnit).getCU() == null) {
							FDCMsgBox.showWarning("管理单元为空，出错！");
							SysUtil.abort();
						}
						String cuID = ((CostCenterOrgUnitInfo) orgUnit).getCU()
								.getId().toString();
						FDCClientUtils.setPersonF7(prmt, null, cuID);
					} else if (orgUnit != null
							&& orgUnit instanceof FullOrgUnitInfo) {
						if (((FullOrgUnitInfo) orgUnit).getCU() == null) {
							FDCMsgBox.showWarning("管理单元为空，出错！");
							SysUtil.abort();
						}
						String cuID = ((FullOrgUnitInfo) orgUnit).getCU()
								.getId().toString();
						FDCClientUtils.setPersonF7(prmt, null, cuID);
					}
				}
			}
		} catch (Exception exc) {
		} finally {
		}

	}

	protected TableCellEditor getCellEditor(ScheduleTaskPropertyInfo property) {
		String valueType = property.getValueType();
		String valueAtt = property.getValueAtt();
		KDTDefaultCellEditor kdCellEditor = null;
		/*
		 * if(property.getPropertyId()!=null&&property.getPropertyId().equals("tpc0"
		 * )){ //WBS编码 return new ScheduleTreeCellEditor(); }
		 */

		if (FDCHelper.isEmpty(valueType)) {
			return null;
		} else if (valueType.equalsIgnoreCase("int")) {
			KDFormattedTextField kdc = new KDFormattedTextField();
			kdc.setDataType(KDFormattedTextField.INTEGER);
			kdc.setPrecision(0);
			kdc.setRemoveingZeroInDispaly(true);
			kdc.setRemoveingZeroInEdit(true);
			kdc.setMinimumValue(FDCHelper.MIN_VALUE);
			kdc.setMaximumValue(FDCHelper.MAX_VALUE);
			kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
			kdc.setSupportedEmpty(true);
			kdc.setVisible(true);
			kdc.setEnabled(true);
			kdCellEditor = new KDTDefaultCellEditor(kdc);
		} else if (valueType.equalsIgnoreCase("boolean")) {
			KDCheckBox kdc = new KDCheckBox();
			kdCellEditor = new KDTDefaultCellEditor(kdc);
		} else if (valueType.equalsIgnoreCase("text")) {
			int len;
			try {
				len = Integer.parseInt(valueAtt);
			} catch (Exception e) {
				len = 100;
			}
			KDTextField kdc = new KDTextField();
			kdc.setMaxLength(len);
			kdCellEditor = new KDTDefaultCellEditor(kdc);
		} else if (valueType.equalsIgnoreCase("double")
				|| valueType.equalsIgnoreCase("java.math.BigDecimal")) {
			 KDFormattedTextField kdc = new KDFormattedTextField();
			kdc.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
			int precision;
			try {
				precision = Integer.parseInt(valueAtt);
			} catch (Exception e) {
				precision = 0;
			}
			// /*
			// * kdc.setPrecision(2); kdc.setRemoveingZeroInDispaly(false);
			// * kdc.setRemoveingZeroInEdit(false);
			// */
			// // 目前没有小数位
			kdc.setPrecision(precision);
			kdc.setRemoveingZeroInDispaly(true);
			kdc.setRemoveingZeroInEdit(true);
			kdc.setDisplayFormatter(new AbstractFormatter() {

				public Object stringToValue(String text) throws ParseException {
					/* TODO 自动生成方法存根 */
					return FDCHelper.toBigDecimal(text);
				}

				public String valueToString(Object value) throws ParseException {

					if (value == null || FDCHelper.toBigDecimal(value).compareTo(FDCHelper.ZERO) == 0) {
						return "0.00";
					}
					String temp = value.toString();
					int percentIndex = temp.lastIndexOf(".");
					if (percentIndex > 1) {
						return temp.substring(0, percentIndex + 2);
					} else {
						return temp + ".00";
					}

				}
			});

			kdc.setMinimumValue(FDCHelper.MIN_VALUE);
			kdc.setMaximumValue(FDCHelper.MAX_VALUE);
			kdc.setHorizontalAlignment(KDFormattedTextField.RIGHT);
			kdc.setSupportedEmpty(true);
			kdc.setVisible(true);
			kdc.setEnabled(true);
			
			

			kdCellEditor = new KDTDefaultCellEditor(kdc);
		} else if (valueType.equalsIgnoreCase("com.kingdee.bos.dao.IObjectValue")) {
			final KDBizPromptBox prmt = new KDBizPromptBox();
			if (!FDCHelper.isEmpty(valueAtt)) {
				prmt.setQueryInfo(valueAtt);
				if (valueAtt.equals("com.kingdee.eas.basedata.org.app.AdminOrgUnitQuery")) {
					FDCClientUtils.setRespDeptF7(prmt,null);
				}else if (valueAtt.equals("com.kingdee.eas.basedata.person.app.F7PersonQuery")) {
					FDCClientUtils.setPersonF7(prmt,null,null);
				}
				prmt.setDisplayFormat("$name$");
				prmt.setEditFormat("$number$");
				prmt.setCommitFormat("$number$");

				kdCellEditor = new KDTDefaultCellEditor(prmt);
			}
		} else if (valueType.equalsIgnoreCase("date")) {
			KDDatePicker date = new KDDatePicker();
			kdCellEditor = new KDTDefaultCellEditor(date);
		} else if (valueType.equalsIgnoreCase("enum")) {
			KDTextField kdc = new KDTextField();
			kdc.setMaxLength(100);
			kdCellEditor = new KDTDefaultCellEditor(kdc);
		}

		if (kdCellEditor != null) {
			return new ScheduleCellEditor(kdCellEditor, ganttProject.getTree().getTable());
		}
		return null;
	}
	
}
