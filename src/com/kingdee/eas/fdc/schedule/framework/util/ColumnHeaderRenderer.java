package com.kingdee.eas.fdc.schedule.framework.util;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/**
 * 每一行第一个单元格代表的表头
 * <p>
 * 模拟表头样式构建
 * 
 * @author emanon
 * 
 */
public class ColumnHeaderRenderer extends JLabel implements TableCellRenderer {

	/**
	 * 构建一个表头单元格
	 * 
	 * @param table
	 */
	public ColumnHeaderRenderer(JTable table) {
		JTableHeader header = table.getTableHeader();
		// 不透明
		setOpaque(true);
		// 对齐方式
		setHorizontalAlignment(CENTER);
		setVerticalAlignment(CENTER);
		// 边框模拟表头，但是右边不画造成浮雕效果
		setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, Color.WHITE));
		// 颜色
		setForeground(header.getForeground());
		setBackground(header.getBackground());
		// 字体
		setFont(header.getFont());
	}

	/**
	 * 设置表头文字
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		setText((value == null) ? "" : value.toString());
		return this;
	}

}
