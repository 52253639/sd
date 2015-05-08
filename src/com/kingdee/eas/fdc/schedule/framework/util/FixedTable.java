package com.kingdee.eas.fdc.schedule.framework.util;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;

public class FixedTable extends JTable {

	private SynchronizeHandler synchronizeHandler;

	public FixedTable(FixedColumnModel dm) {
		super(dm);
		JTableHeader header = this.getTableHeader();
		header.setBackground(Config.HEADER_COLOR);
		setDefaultRenderer(Object.class, new ColumnHeaderRenderer(this));
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getTableHeader().setResizingAllowed(false);
		getTableHeader().setReorderingAllowed(false);
		getColumnModel().getColumn(0).setPreferredWidth(150);
		this.setRowHeight(50);
	}

	public SynchronizeHandler getSynchronizeHandler() {
		return synchronizeHandler;
	}

	public void setSynchronizeHandler(SynchronizeHandler synchronizeHandler) {
		this.synchronizeHandler = synchronizeHandler;
	}
}
