package com.kingdee.eas.fdc.schedule.framework.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class DocCellRenderer extends JPanel implements TableCellRenderer {

	public DocCellRenderer(JTable table) {
		this.setBackground(Color.WHITE);
		this.setBounds(new Rectangle(10, 10, 50, 50));
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		removeAll();
		invalidate();
		if (value instanceof List) {
			List ls = (List) value;
			int h = 0;
			int w = table.getColumnModel().getColumn(column).getWidth();
			for (int i = 0; i < ls.size(); i++) {
				// Map v = (Map)ls.get(i);
				// Icon icon = new ImageIcon("/11.jpeg");
				// JLabel label = new JLabel((String)v.get("name"));
				// label.setForeground(Color.BLUE);
				// label.setText((String)v.get("name"));
				// label.setSize(this.getPreferredSize());
				// this.setLayout(new FlowLayout(FlowLayout.LEFT));
				// label.setBorder(BorderFactory.createLineBorder(Color.black));
				JComponent label = (JComponent) ls.get(i);
				label.setSize(w - 20, 20);
				this.add(label);
				h += 20;

				//((DocTable)table).getSynchronizeHandler().synchronizeHeight(row
				// , getPreferredSize().height + row * 10 );
			}
			((DocTable) table).synchronizeRowHeight(row,
					getPreferredSize().height + h);
			this.setLayout(new GridLayout(ls.size(), 1));
		}
		return this;
	}

}
