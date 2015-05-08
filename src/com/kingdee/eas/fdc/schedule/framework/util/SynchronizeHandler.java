package com.kingdee.eas.fdc.schedule.framework.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;

public class SynchronizeHandler {

	private List tables = new ArrayList();

	private Map rowHeights = new HashMap();
	
	public void add(JTable table) {
		tables.add(table);
	}
	
	public void remove(JTable table) {
		tables.remove(table);
	}
	
	public void synchronizeHeight(int row, int height) {
		String key = String.valueOf(row);
		Object h = rowHeights.get(key);
		if (h == null) {
			rowHeights.put(key, new Integer(height));
			return;
		}
		Integer curH = (Integer) h;
		if (curH.intValue() < height) {
			curH = new Integer(height);
			rowHeights.put(key, curH);
		}
		for (int i = 0; i < tables.size(); i++) {
			JTable t = (JTable)tables.get(i);
				t.setRowHeight(row, curH.intValue());
		}
	}
}
