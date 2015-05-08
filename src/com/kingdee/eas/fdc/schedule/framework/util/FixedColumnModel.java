package com.kingdee.eas.fdc.schedule.framework.util;

import javax.swing.table.DefaultTableModel;

public class FixedColumnModel extends DefaultTableModel {

	public boolean isCellEditable(int row, int col) {
		return false;
	}

}
