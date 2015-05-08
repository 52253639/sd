package com.kingdee.eas.fdc.schedule.framework.client;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

import net.sourceforge.ganttproject.task.TaskNode;

import org.jdesktop.swing.decorator.ComponentAdapter;
import org.jdesktop.swing.decorator.Highlighter;

import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;

public class KDGanttRowHighligther extends Highlighter {
	private JTree tree=null;
	public KDGanttRowHighligther(JTree tree){
		this.tree=tree;
	}
	protected Color computeBackground(Component renderer, ComponentAdapter adapter) {
		TreePath pathForRow = tree.getPathForRow(adapter.row);
		Object node = pathForRow.getLastPathComponent();
		Color computeBackground = super.computeBackground(renderer, adapter);
		if(adapter.isSelected()){
			return computeBackground;
		}
		if(node instanceof TaskNode){
			Object task = ((TaskNode)node).getUserObject();
			if(task instanceof KDTask){
				KDTask kdTask=(KDTask)task;
				if(kdTask.getBackground()!=null){
					computeBackground=kdTask.getBackground();
				}
			}
		}
		if(adapter.isSelected()){
			computeBackground=computeBackgroundSeed(computeBackground);
		}
		return computeBackground;
	}

	protected Color computeForeground(Component renderer, ComponentAdapter adapter) {
		TreePath pathForRow = tree.getPathForRow(adapter.row);
		Object node = pathForRow.getLastPathComponent();
		if(node instanceof TaskNode){
			Object task = ((TaskNode)node).getUserObject();
			if(task instanceof KDTask){
				KDTask kdTask=(KDTask)task;
				if(kdTask.getForeground()!=null){
					return kdTask.getForeground();
				}
			}
		}
		return super.computeForeground(renderer, adapter);
	}
	
	protected Color computeSelectedBackground(Color seed) {
		return super.computeSelectedBackground(seed);
	}
	
	protected Color computeSelectedForeground(Color seed) {
		return super.computeSelectedForeground(seed);
	}
	
	protected Color computeBackgroundSeed(Color seed) {
		return new Color(Math.max((int) ((double) seed.getRed() * 0.94999999999999996D), 0), Math.max((int) ((double) seed.getGreen() * 0.94999999999999996D), 0), Math.max((int) ((double) seed
				.getBlue() * 0.94999999999999996D), 0));
	}
}
