package com.kingdee.eas.fdc.schedule.framework.util;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class DocCellEditor extends AbstractCellEditor implements
		TableCellEditor {
	JPanel field = new JPanel();
	List items = new ArrayList();

	public boolean stopCellEditing() {
		// String s = (String) getCellEditorValue();
		return super.stopCellEditing();
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		field.removeAll();
		items.clear();
		if (value instanceof List) {
			List ls = (List) value;
			for (int i = 0; i < ls.size(); i++) {
				JLabel label = (JLabel) ls.get(i);
				label.setForeground(Color.BLUE);
				// label.setBorder(BorderFactory.createLineBorder(Color.black));
				// table.setRowHeight(row, getPreferredSize().height + row *
				// 10);
				field.add(label);
				items.add(label);
			}
		}
		return field;
	}

	public boolean isCellEditable(EventObject evt) {
		// if (evt instanceof MouseEvent) {
		// int clickCount;
		// clickCount = 2;
		// return ((MouseEvent)evt).getClickCount() >= clickCount;
		// }
		return true;
	}

	public Object getCellEditorValue() {
		return items;
	}
}
