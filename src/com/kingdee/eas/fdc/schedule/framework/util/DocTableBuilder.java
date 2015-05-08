package com.kingdee.eas.fdc.schedule.framework.util;

import java.util.List;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;

public class DocTableBuilder extends AbsctractTableBuilder {
	public DocTableBuilder() {

	}

	protected FixedColumnModel buildFixedModel(List rowNames) {
		String[][] names = new String[rowNames.size()][1];
		for (int i = 0; i < names.length; i++) {
			Map m = (Map) rowNames.get(i);
			names[i][0] = (String) m.get(Config.NAME);
		}
		FixedColumnModel dm = new FixedColumnModel();
		dm.setDataVector(names, new String[] { "" });
		return dm;
	}

	protected DocTableModel buildModel(List colNames, List rowNames, List datas) {
		DocTableModel dm = new DocTableModel();
		Object[][] data = DocUtil.convert2Array(colNames, rowNames, datas);
		String[] cols = DocUtil.convert2Array(colNames);

		dm.setDataVector(data, cols);

		return dm;
	}

	protected DocTable buildTable(DocTableModel tableModel,
			FixedColumnModel fixedModel) {

		// fixedTable 用于固定标题列
		FixedTable fixedTable = new FixedTable(fixedModel);

		DocTable table = new DocTable(tableModel, fixedTable);

		JScrollPane scroll = new JScrollPane(table);

		JViewport viewport = new JViewport();
		viewport.setView(fixedTable);
		viewport.setPreferredSize(fixedTable.getPreferredSize());
		scroll.setRowHeaderView(viewport);
		scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER, fixedTable
				.getTableHeader());
		table.setContainer(scroll);
		// synchronizeTableHeight(fixedTable, table);
		SynchronizeHandler synchronizeHandler = new SynchronizeHandler();
		synchronizeHandler.add(fixedTable);
		synchronizeHandler.add(table);
		fixedTable.setSynchronizeHandler(synchronizeHandler);
		table.setSynchronizeHandler(synchronizeHandler);

		return table;
	}

	private void synchronizeTableHeight(JTable fixedTable, JTable docTable) {
		int rowNum = fixedTable.getRowCount();
		for (int i = 0; i < rowNum; i++) {
			int fixedHeight = fixedTable.getRowHeight(i);
			int docHeight = docTable.getRowHeight(i);
			if (fixedHeight > docHeight) {
				docTable.setRowHeight(i, fixedHeight);
			} else {
				fixedTable.setRowHeight(i, docHeight);
			}
		}
	}

}
