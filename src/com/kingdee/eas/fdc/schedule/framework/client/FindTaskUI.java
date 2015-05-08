/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.framework.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.sourceforge.ganttproject.task.CustomColumnsValues;
import net.sourceforge.ganttproject.task.Task;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo;
import com.kingdee.eas.fdc.schedule.client.FDCScheduleBaseEditUI;
import com.kingdee.eas.fdc.schedule.client.MainScheduleEditUI;
import com.kingdee.eas.fdc.schedule.client.MainScheduleExecuteUI;
import com.kingdee.eas.fdc.schedule.client.TotalScheduleEditUI;
import com.kingdee.eas.fdc.schedule.framework.ext.GanttTreeTableModelExt;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.ext.ScheduleGanttProject;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.util.enums.EnumUtils;

/**
 * 甘特图任务定位界面
 * <p>
 * 默认只支持编码、名称列定位
 * 2012-2-27 增加责任部门、业务类型、任务类别过滤
 * 
 * @author emanon
 */
public class FindTaskUI extends AbstractFindTaskUI {
	private static final Logger logger = CoreUIObject
			.getLogger(FindTaskUI.class);
	FDCScheduleBaseEditUI owner;
	// 甘特图
	ScheduleGanttProject gantt;
	// 甘特图包含的任务集合
	private Task[] tasks;
	// 当前选中的任务行
	private int curSelecte = -1;
	
	private FindTaskUI ui = this;

	/**
	 * output class constructor
	 */
	public FindTaskUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();

		Object ownerUI = getUIContext().get("Owner");
		if (ownerUI == null || !(ownerUI instanceof FDCScheduleBaseEditUI)) {
			logger.warn("当前定位界面只支持甘特图编辑界面定位。请继承FDCScheduleBaseEditUI。");
			destroyWindow();
		}
		owner = (FDCScheduleBaseEditUI) ownerUI;
		gantt = owner.ganttProject;
		tasks = gantt.getTaskManager().getTasks();
		List tasks = gantt.getTaskSelectionContext().getSelectedTasks();
		if (tasks != null && tasks.size() > 0) {
			curSelecte = ((KDTask) tasks.get(0)).getTaskID();
		}
		Object lstItem = null;
		List newValue = new ArrayList();
		if(!(owner instanceof MainScheduleEditUI) && !(owner instanceof TotalScheduleEditUI) && !(owner instanceof MainScheduleExecuteUI)){
			for(int i=0;i<lstColumns.getElementCount();i++){
				lstItem  = lstColumns.getModel().getElementAt(i);
				if(!lstItem.equals(GanttTreeTableModelExt.strColBizType)){
					newValue.add(lstItem.toString());
				}
			}
			lstColumns.removeAllElements();
			lstColumns.setListData(newValue.toArray());
			
		}
		
		
		//增加责任部门f7
		final KDBizPromptBox prmtRespDept = new KDBizPromptBox();
		FDCClientUtils.setRespDeptF7(prmtRespDept, this);
		prmtRespDept.setSize(txtContent.getSize());
		prmtRespDept.setBounds(txtContent.getBounds());
		add(prmtRespDept);
		prmtRespDept.addDataChangeListener(new DataChangeListener(){

			public void dataChanged(DataChangeEvent eventObj) {
				AdminOrgUnitInfo org =(AdminOrgUnitInfo) prmtRespDept.getValue();
				txtContent.setText(org.getName());
			}});
		prmtRespDept.setEditable(false);
		
		//增加业务类型f7
		final KDBizPromptBox prmtBizType = new KDBizPromptBox();
		prmtBizType.setSize(txtContent.getSize());
		prmtBizType.setBounds(txtContent.getBounds());
		prmtBizType.setEnabledMultiSelection(true);
		prmtBizType.setDisplayFormat("$name$");
		prmtBizType.setEditFormat("$name");
		add(prmtBizType);
		prmtBizType.setEditable(false);
		
		prmtBizType.setQueryInfo("com.kingdee.eas.fdc.schedule.app.ScheduleBizTypeQuery");
		prmtBizType.getQueryAgent().getQueryInfo().setAlias("业务类型");
		prmtBizType.addDataChangeListener(new DataChangeListener(){

			public void dataChanged(DataChangeEvent eventObj) {
				Object[] values = (Object[]) prmtBizType.getValue();
				ScheduleBizTypeInfo bizType = null;
				StringBuffer str = new StringBuffer();
				for(int i=0;i<values.length;i++){
					bizType = (ScheduleBizTypeInfo) values[i];
					str.append(bizType.getName());
					if(i<values.length-1){
						str.append(",");
					}
				}
				txtContent.setText(str.toString());
			}});
		
		//增加任务类别f7
		final KDComboBox cmb = new KDComboBox();
    	cmb.setSize(txtContent.getSize());
    	cmb.setBounds(txtContent.getBounds());
    	cmb.addItems(EnumUtils.getEnumAliases("com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum"));
    	cmb.addItemListener(new ItemListener(){

			public void itemStateChanged(ItemEvent e) {
				if(cmb!=null && cmb.getSelectedItem() != null){
					txtContent.setText(cmb.getSelectedItem().toString());
				}
				
			}});
    	add(cmb);
    	cmb.setSelectedIndex(0);
		lstColumns.addListSelectionListener(new ListSelectionListener(){

			public void valueChanged(ListSelectionEvent e) {
			    if(lstColumns.getSelectedValue().equals(GanttTreeTableModelExt.strColTaskType)){
			    	txtContent.setVisible(false);
			    	prmtBizType.setVisible(false);
			    	prmtRespDept.setVisible(false);
                    cmb.setVisible(true);
                    txtContent.setText("一般任务");
			    	
			    }else if(lstColumns.getSelectedValue().equals(GanttTreeTableModelExt.strColBizType)){
			    	txtContent.setVisible(false);
			    	prmtRespDept.setVisible(false);
			    	cmb.setVisible(false);
			    	prmtBizType.setVisible(true);
			    }else if(lstColumns.getSelectedValue().equals(GanttTreeTableModelExt.strColAdminDept)){
			    	txtContent.setVisible(false);
			    	prmtRespDept.setVisible(true);
			    	cmb.setVisible(false);
			    }else{
			    	cmb.setVisible(false);
			    	prmtBizType.setVisible(false);
			    	prmtRespDept.setVisible(false);
			    	txtContent.setText("");
			    	txtContent.setVisible(true);
			    }
				
				
			}});
	}

	/**
	 * 没有继承CoreUI，此处onShow()方法没用，在doLayout中选中输入框
	 */
	public void doLayout() {
		super.doLayout();
		txtContent.requestFocus();
	}

	/**
	 * 定位
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		// 取值列名
		String columnName = (String) lstColumns.getSelectedValue();
		// 是否模糊
		boolean isFuzzy = cbFuzzy.isSelected();
		// 查找内容
		String content = txtContent.getText();
		// 查找方向
		boolean isBack = rbBack.isSelected();
		if (FDCHelper.isEmpty(content)) {
			return;
		}
		if (isBack) {
			for (int i = curSelecte + 1; i < tasks.length; i++) {
				if (isThisTask(columnName, isFuzzy, content, i)) {
					break;
				}
			}
		} else {
			for (int i = curSelecte - 1; i >= 0; i--) {
				if (isThisTask(columnName, isFuzzy, content, i)) {
					break;
				}
			}
		}
	}

	/**
	 * 该任务是否满足查询条件
	 * <p>
	 * 抽取此方法方便在向前和向后查询时都可调用，只判断当前第i行是否符合条件
	 * 
	 * @param columnName
	 *            查询列名
	 * @param isFuzzy
	 *            是否模糊
	 * @param content
	 *            查询内容
	 * @param i
	 *            行号
	 * @return
	 */
	protected boolean isThisTask(String columnName, boolean isFuzzy,
			String content, int i) {
		if (tasks[i] instanceof KDTask) {
			KDTask task = (KDTask) tasks[i];
			CustomColumnsValues cusValues = task.getCustomValues();
			if (cusValues != null) {
				String value = null;
				Object taskValue = cusValues.getValue(columnName);
				if( taskValue instanceof String){
					value = (String) cusValues.getValue(columnName);
				}else if(taskValue instanceof DataBaseInfo)
				   value = ((DataBaseInfo) cusValues.getValue(columnName)).getName();
				if (FDCHelper.isEmpty(value)) {
					return false;
				} else {
					if ("任务编码".equals(columnName)) {
						value = value.replaceAll("!", ".");
					}
					if (isFuzzy) {
						// 模糊查询
						if (value.indexOf(content) >= 0) {
							selectAndCenter(i, task);
							return true;
						} else {
							return false;
						}
					} else {
						// 精确查询
						if (content.equals(value)) {
							selectAndCenter(i, task);
							return true;
						} else {
							return false;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * 选中一条任务并居中
	 * 
	 * @param i
	 * @param task
	 */
	protected void selectAndCenter(int i, KDTask task) {
		gantt.getTree().selectTask(task, false);
		curSelecte = i;
		try {
			owner.actionZoomCenter_actionPerformed(null);
		} catch (Exception e) {
			// 不能居中不强求，不需抛异常
			logger.warn(e);
		}
	}

	/**
	 * 关闭
	 */
	public void actionClose_actionPerformed(ActionEvent e) throws Exception {
		destroyWindow();
	}

}