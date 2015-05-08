package com.kingdee.eas.fdc.schedule.framework.util;

import java.util.List;

abstract public class AbsctractTableBuilder implements TableBuilder {

	public DocTable createTable(List header1, List header2, List datas) {
		FixedColumnModel fixedModel = buildFixedModel(header2);
		DocTableModel tableModel = buildModel(header1, header2, datas);
		DocTable table = buildTable(tableModel, fixedModel);
		return table;
	}

	abstract protected FixedColumnModel buildFixedModel(List header2);

	abstract protected DocTable buildTable(DocTableModel tableModel,
			FixedColumnModel fixedModel);

	abstract protected DocTableModel buildModel(List colNames, List rowNames,
			List datas);

}
