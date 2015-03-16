package com.kingdee.eas.fdc.invite.client;

import java.util.List;

import com.kingdee.bos.ctrl.kdf.table.ICell;

public class TableScriptHelper {
	static void setTableSumFormalu(ICell cell, List cellList) {
		StringBuffer expression = new StringBuffer("=SUM(");
		for (int i = 0; i < cellList.size(); i++) {
			ICell aCell = (ICell) cellList.get(i);
			StringBuffer text = new StringBuffer("CELL(");
			text.append(aCell.getRowIndex() + 1);
			text.append(",");
			text.append(aCell.getColumnIndex() + 1);
			text.append(").getValue()");
			if (!expression.toString().equals("=SUM(")) {
				expression.append(",");
			}
			expression.append(text);
		}
		expression.append(")");
		cell.setExpressions(expression.toString());
	}

	public static void setTableRowSumFormalu(ICell cell, int count) {
		StringBuffer expression = new StringBuffer("=SUM(");
		for (int i = 0; i < count; i++) {
			StringBuffer text = new StringBuffer("CELL(");
			text.append("ROW_INDEX");
			text.append(",");
			text.append("COLUMN_INDEX-" + (count - i));
			text.append(").getValue()");
			if (!expression.toString().equals("=SUM(")) {
				expression.append(",");
			}
			expression.append(text);
		}
		expression.append(")");
		cell.setExpressions(expression.toString());
	}

	public static void setTableMultiplyFormalu(ICell cell, ICell cell1, ICell cell2) {
		StringBuffer expression = new StringBuffer("=");
		expression.append("CELL(" + (cell1.getRowIndex() + 1));
		expression.append(",");
		expression.append(cell1.getColumnIndex() + 1);
		expression.append(").getValue()*");
		expression.append("CELL(" + (cell2.getRowIndex() + 1));
		expression.append(",");
		expression.append(cell2.getColumnIndex() + 1);
		expression.append(").getValue()");
		cell.setExpressions(expression.toString());
	}
	
	static void setTableSubFormalu(ICell cell, ICell cell1, ICell cell2) {
		StringBuffer expression = new StringBuffer("=");
		expression.append("CELL(" + (cell1.getRowIndex() + 1));
		expression.append(",");
		expression.append(cell1.getColumnIndex() + 1);
		expression.append(").getValue()-");
		expression.append("CELL(" + (cell2.getRowIndex() + 1));
		expression.append(",");
		expression.append(cell2.getColumnIndex() + 1);
		expression.append(").getValue()");
		cell.setExpressions(expression.toString());
	}
	
	static void setTableDivideFormalu(ICell cell, ICell cell1, ICell cell2) {
		String expression = "=";
		expression += "CELL(" + (cell1.getRowIndex() + 1);
		expression += ",";
		expression += cell1.getColumnIndex() + 1;
		expression += ").getValue()/";
		expression += "CELL(" + (cell2.getRowIndex() + 1);
		expression += ",";
		expression += cell2.getColumnIndex() + 1;
		expression += ").getValue()";
		cell.setExpressions(expression);
	}
}
