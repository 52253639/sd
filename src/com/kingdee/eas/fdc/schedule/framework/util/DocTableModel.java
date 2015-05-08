package com.kingdee.eas.fdc.schedule.framework.util;

import javax.swing.table.DefaultTableModel;

public class DocTableModel extends DefaultTableModel {

	public void setValueAt(Object obj, int row, int col) {
		super.setValueAt(obj, row, col);
	}

	public Object getValueAt(int row, int col) {
		return super.getValueAt(row, col);
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}
}
