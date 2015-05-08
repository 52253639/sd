package com.kingdee.eas.fdc.schedule.framework.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import org.apache.log4j.Logger;

public class DocTable extends JTable {
	Logger logger = Logger.getLogger(DocTable.class.getName());

	// 当前选中的标签
	private DocLabel curSelectedLabel;
	// 用于固定列的table
	private JTable fixedTable;

	private JScrollPane container;

	SynchronizeHandler synchronizeHandler;

	public JTable getFixedTable() {
		return fixedTable;
	}

	public DocTable(DocTableModel dm, JTable fixedTable) {
		super(dm);
		JTableHeader header = this.getTableHeader();
		header.setBackground(Config.HEADER_COLOR);
		header.setReorderingAllowed(false);
		this.fixedTable = fixedTable;

		setDefaultRenderer(Object.class, new DocCellRenderer(this));
		MouseAdapterListener listener = new MouseAdapterListener(this);
		addMouseListener(listener);
		addMouseMotionListener(listener);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setDragEnabled(false);
		this.setRowHeight(50);
	}

	public JScrollPane getContainer() {
		return this.container;
	}

	public void setContainer(JScrollPane con) {
		this.container = con;
	}

	public void setSelectedItem(DocLabel label) {
		this.curSelectedLabel = label;
	}

	public DocLabel getSelectedItem() {
		return this.curSelectedLabel;
	}

	public Map getSelectedItemValue() {
		return this.curSelectedLabel == null ? null : this.curSelectedLabel
				.getData();
	}

	public SynchronizeHandler getSynchronizeHandler() {
		return synchronizeHandler;
	}

	public void setSynchronizeHandler(SynchronizeHandler synchronizeHandler) {
		this.synchronizeHandler = synchronizeHandler;
	}

	public void setColumnWidth(int width) {
		TableColumnModel colModel = getColumnModel();
		for (int i = 0; i < colModel.getColumnCount(); i++) {

			colModel.getColumn(i).setPreferredWidth(width);
		}
	}

	public void reLoad(List header1, List header2, List datas) {
		Object[][] newData = DocUtil.convert2Array(header1, header2, datas);
		String[] cols = DocUtil.convert2Array(header1);
		((DocTableModel) this.getModel()).setDataVector(newData, cols);

		((DocTableModel) this.getModel()).fireTableDataChanged();
	}

	public void synchronizeRowHeight(int row, int rowHeight) {
		this.setRowHeight(row, rowHeight);
		if (this.fixedTable != null) {
			this.fixedTable.setRowHeight(row, rowHeight);
		}
	}

	class MouseAdapterListener extends java.awt.event.MouseMotionAdapter
			implements MouseListener {
		int oldY = 0;
		int newY = 0;
		int row = 0;
		int oldHeight = 0;
		boolean drag = false;
		int increase = 0;
		JTable table;
		DocLabel selectedLabel;
		DocLabel preSelectedLabel;

		public MouseAdapterListener(JTable table) {
			this.table = table;
		}

		public void mouseMoved(MouseEvent e) {
			int onRow = table.rowAtPoint(e.getPoint());

			int height = 0;
			for (int i = 0; i <= onRow; i++) {
				height = height + table.getRowHeight(i);
			}

			if (height - e.getY() < 3) {
				// 跟renderer的synchronizeRowHeight 有冲突， 先禁用。
				// drag = true;
				// table.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
			} else {
				drag = false;
				table.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			/*
			 * JTable table = (JTable) e.getSource(); int col =
			 * table.getSelectedColumn(); int row = table.getSelectedRow(); if
			 * (row <0 || col < 0) return; Object va = table.getValueAt(row,
			 * col); if (va instanceof List) { List coms = (List)va; Rectangle
			 * rect = table.getCellRect(row, col, true); double cellX=
			 * rect.getX(); double cellY = rect.getY(); for (int i = 0; i <
			 * coms.size(); i++) { Component com = (Component) coms.get(i); int
			 * x = e.getX(); int y = e.getY();
			 * 
			 * if ( com instanceof DocLabel ) { double cx = com.getX() + cellX;
			 * double cy = com.getY() + cellY;
			 * 
			 * int width = com.getWidth(); int comHeight = com.getHeight();
			 * 
			 * if (x > cx && x < (cx + width) && y > cy && y < (cy + comHeight))
			 * { if (preSelectedLabel != null) {
			 * preSelectedLabel.setBorder(null); } preSelectedLabel =
			 * ((DocLabel)com);
			 * preSelectedLabel.setBorder(BorderFactory.createLineBorder
			 * (Color.blue)); table.repaint(); //
			 * JOptionPane.showMessageDialog(null, "Row : " + row + ", col:"
			 * +col+ "  " + selectedLabel.getText()); }
			 * 
			 * } } }
			 */
		}

		public void mouseDragged(MouseEvent e) {
			// logger.info(e.getX() + "  " + e.getY());
			if (drag) {
				int value = oldHeight + e.getY() - oldY;
				if (value < 30) {
					table.setRowHeight(row, 30);
					((DocTable) table).getFixedTable().setRowHeight(row, 30);
				} else {

					table.setRowHeight(row, oldHeight + e.getY() - oldY);
					((DocTable) table).getFixedTable().setRowHeight(row,
							oldHeight + e.getY() - oldY);
				}

				table.setRowSelectionInterval(row, row);
				((DocTable) table).getFixedTable().setRowSelectionInterval(row,
						row);
			}
		}

		public void mousePressed(MouseEvent e) {
			oldY = e.getY();
			row = table.rowAtPoint(e.getPoint());
			oldHeight = table.getRowHeight(row);
			table.setRowSelectionInterval(row, row);
		}

		public void mouseReleased(MouseEvent e) {
			newY = e.getY();
			table.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			((DocTable) table).getFixedTable().setCursor(
					new Cursor(Cursor.DEFAULT_CURSOR));
		}

		public void mouseClicked(MouseEvent e) {
			JTable table = (JTable) e.getSource();
			int col = table.getSelectedColumn();
			int row = table.getSelectedRow();
			Object va = table.getValueAt(row, col);
			if (va instanceof List) {
				List coms = (List) va;
				Rectangle rect = table.getCellRect(row, col, true);
				double cellX = rect.getX();
				double cellY = rect.getY();
				for (int i = 0; i < coms.size(); i++) {
					Component com = (Component) coms.get(i);
					int x = e.getX();
					int y = e.getY();

					if (com instanceof DocLabel) {
						double cx = com.getX() + cellX;
						double cy = com.getY() + cellY;

						int width = com.getWidth();
						int height = com.getHeight();

						if (x > cx && x < (cx + width) && y > cy
								&& y < (cy + height)) {
							if (selectedLabel != null) {
								selectedLabel.setIsSelected(false);
								selectedLabel.setBackground(Color.white);
								selectedLabel.setBorder(null);
							}
							selectedLabel = ((DocLabel) com);
							((DocTable) table).setSelectedItem(selectedLabel);
							selectedLabel
									.setBackground(Config.BG_SELECTED_COLOR);
							selectedLabel
									.setBorder(BorderFactory
											.createLineBorder(Config.BG_SELECTED_COLOR));
							selectedLabel.setIsSelected(true);
							table.repaint();
							// JOptionPane.showMessageDialog(null, "Row : " +
							// row + ", col:" +col+ "  " +
							// selectedLabel.getText());
						}

					}
				}
			}
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}
	}

}
