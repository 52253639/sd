package com.kingdee.eas.fdc.schedule.framework.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.TreePath;

import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskNode;

import com.kingdee.bos.ctrl.kdf.data.logging.Logger;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.ext.ScheduleGanttProject;

public class KDGanttTreeUI extends BasicTreeUI {
	private final static Color COMMONLY = Color.BLACK;
	private final static Color KEY = new Color(0, 0, 255);
	private final static Color MILESTONE = Color.RED;
	private final static Color NORMAL = new Color(150, 150, 50);

	private Color hashColor;
	private ScheduleGanttProject ganttProject;

	public KDGanttTreeUI(ScheduleGanttProject scheduleGanttProject) {
		this.ganttProject = scheduleGanttProject;
	}

	public Color getHashColor() {
		return hashColor;
	}

	public void setHashColor(Color color) {
		super.setHashColor(color);
	}

	public void setMyHashColor(Color color) {
		hashColor = color;
		super.setHashColor(color);
	}

	/**
	 * 该方法实现任务名称根据任务类别改变颜色
	 * <p>
	 * 修改纪录：
	 *   当任务是普通任务时不需要在此修改其颜色
	 * 
	 */
	protected void paintRow(Graphics g, Rectangle clipBounds, Insets insets,
			Rectangle bounds, TreePath path, int row, boolean isExpanded,
			boolean hasBeenExpanded, boolean isLeaf) {
		Component component = currentCellRenderer.getTreeCellRendererComponent(
				tree, path.getLastPathComponent(), tree.isRowSelected(row),
				isExpanded, isLeaf, row, false);
		
//		KDTask task = (KDTask) ganttProject.getTaskManager().getTask(row);
		if((((TaskNode)path.getLastPathComponent()).getUserObject() instanceof KDTask)){
			KDTask task = (KDTask) ((net.sourceforge.ganttproject.task.TaskNode)path.getLastPathComponent()).getUserObject();
			String taskType = null;
			if (task != null) {
				Object o = task.getScheduleTaskInfo().get("tasktype");
				if(o instanceof String){
					taskType = o.toString();
				}else if(o instanceof RESchTaskTypeEnum){
					taskType = ((RESchTaskTypeEnum)o).getValue();
				}
				
			}
			if (!FDCHelper.isEmpty(taskType)) {
//				if (RESchTaskTypeEnum.COMMONLY_VALUE.equals(taskType)) {
//					component.setForeground(COMMONLY);
//				} else
				if (RESchTaskTypeEnum.KEY_VALUE.equals(taskType)) {
					component.setForeground(KEY);
				} else if (RESchTaskTypeEnum.MILESTONE_VALUE.equals(taskType)) {
					component.setForeground(MILESTONE);
				} else if (RESchTaskTypeEnum.NORMAL_VALUE.equals(taskType)) {
					component.setForeground(NORMAL);
				} 
//				else {
//					component.setForeground(COMMONLY);
//				}
			}
		}else if(((TaskNode)path.getLastPathComponent()).getUserObject() instanceof Task){
			
		}
		
		rendererPane.paintComponent(g, component, tree, bounds.x, bounds.y,
				bounds.width, bounds.height, true);
	}
}
