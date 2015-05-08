package com.kingdee.eas.fdc.schedule.framework.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocUtil {
	public static String[] convert2Array(List colNames) {
		String[] cols = new String[colNames.size()];
		// 列名
		for (int i = 0; i < colNames.size(); i++) {
			Map m = (Map) colNames.get(i);
			String colName = (String) m.get(Config.NAME);
			cols[i] = colName;
		}
		return cols;
	}

	public static Object[][] convert2Array(List colNames, List rowNames,
			List datas) {
		String[] cols = new String[colNames.size()];
		String[] colIds = new String[colNames.size()];
		// 列名
		for (int i = 0; i < colNames.size(); i++) {
			Map m = (Map) colNames.get(i);
			String colName = (String) m.get(Config.NAME);
			String colId = (String) m.get(Config.ID);
			colIds[i] = colId;
			cols[i] = colName;
		}
		// 行名
		String[] rows = new String[rowNames.size()];
		String[] rowIds = new String[rowNames.size()];
		for (int i = 0; i < rowNames.size(); i++) {
			Map m = (Map) rowNames.get(i);
			rowIds[i] = (String) m.get(Config.ID);
			rows[i] = (String) m.get(Config.NAME);

		}
		// 数据
		Object[][] dt = new Object[rows.length][cols.length];
		Map dataMap = new HashMap();
		for (int i = 0; i < datas.size(); i++) {
			Map m = (Map) datas.get(i);
			String rowId = (String) m.get(Config.ROW_KEY);
			if (rowId == null)
				continue;
			List ls = (List) dataMap.get(rowId);
			if (ls == null) {
				ls = new ArrayList();
				dataMap.put(rowId, ls);
			}
			ls.add(m);

		}

		for (int i = 0; i < dt.length; i++) {
			String rowId = rowIds[i];
			// dt[i][0] = rows[i];
			List ls = (List) dataMap.get(rowId);
			if (ls == null)
				continue;

			for (int k = 0; k < colIds.length; k++) {
				for (int j = 0; j < ls.size(); j++) {

					Map m = (Map) ls.get(j);
					String colId = (String) m.get(Config.COL_KEY);
					if (colId != null && colId.equals(colIds[k])) {
						if (dt[i][k] == null) {
							dt[i][k] = new ArrayList();
						}

						DocLabel label = new DocLabel((String) m
								.get(Config.NAME), m);
						((List) dt[i][k]).add(label);
					}
				}
			}

		}

		return dt;
	}
}
