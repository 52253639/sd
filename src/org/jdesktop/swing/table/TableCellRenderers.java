package org.jdesktop.swing.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.swing.data.Link;

public class TableCellRenderers {
	private static HashMap typeMap = new HashMap();

	static {
		typeMap.put(Number.class, "org.jdesktop.swing.table.TableCellRenderers$NumberRenderer");
		typeMap.put(Double.class, "org.jdesktop.swing.table.TableCellRenderers$DoubleRenderer");
		typeMap.put(Float.class, "org.jdesktop.swing.table.TableCellRenderers$DoubleRenderer");
		typeMap.put(Date.class, "org.jdesktop.swing.table.TableCellRenderers$DateRenderer");
		typeMap.put(Icon.class, "org.jdesktop.swing.table.TableCellRenderers$IconRenderer");
		typeMap.put(Boolean.class, "org.jdesktop.swing.table.TableCellRenderers$BooleanRenderer");
		typeMap.put(Link.class, "org.jdesktop.swing.table.TableCellRenderers$LinkRenderer");
	}

	private static String getRendererClassName(Class columnClass) {
		String rendererClassName = (String) typeMap.get(columnClass);
		return (rendererClassName != null) ? rendererClassName : "javax.swing.table.DefaultTableCellRenderer";
	}

	public static void setDefaultRenderer(Class columnClass, String rendererClassName) {
		typeMap.put(columnClass, rendererClassName);
	}

	public static TableCellRenderer getNewDefaultRenderer(Class columnClass) {
		TableCellRenderer renderer = null;
		String rendererClassName = getRendererClassName(columnClass);
		try {
			Class rendererClass = Class.forName(rendererClassName);
			renderer = (TableCellRenderer) rendererClass.newInstance();
		} catch (Exception e) {
			renderer = new DefaultTableCellRenderer();
		}
		return renderer;
	}

	static Rectangle getTextBounds(Graphics g, JLabel label) {
		FontMetrics fm = g.getFontMetrics();
		Rectangle2D r2d = fm.getStringBounds(label.getText(), g);
		Rectangle rect = r2d.getBounds();

		int xOffset = 0;
		switch (label.getHorizontalAlignment()) {
		case 4:
		case 11:
			xOffset = label.getBounds().width - rect.width;
			break;
		case 0:
			xOffset = (label.getBounds().width - rect.width) / 2;
			break;
		case 1:
		case 2:
		case 3:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		default:
			xOffset = 0;
		}

		int yOffset = 0;
		switch (label.getVerticalAlignment()) {
		case 1:
			yOffset = 0;
			break;
		case 0:
			yOffset = (label.getBounds().height - rect.height) / 2;
			break;
		case 3:
			yOffset = label.getBounds().height - rect.height;
		case 2:
		}
		return new Rectangle(xOffset, yOffset, rect.width, rect.height);
	}

	static class NumberRenderer extends DefaultTableCellRenderer {
		public NumberRenderer() {
			setHorizontalAlignment(4);
		}
	}

	static class DoubleRenderer extends TableCellRenderers.NumberRenderer {
		NumberFormat formatter;

		public void setValue(Object value) {
			if (this.formatter == null) {
				this.formatter = NumberFormat.getInstance();
			}
			setText((value == null) ? "" : this.formatter.format(value));
		}
	}

	public static class DateRenderer extends DefaultTableCellRenderer {
		DateFormat formatter;

		public void setValue(Object value) {
			if (this.formatter == null) {
				this.formatter = DateFormat.getDateInstance();
			}
			setText((value == null) ? "" : this.formatter.format(value));
		}
	}

	static class IconRenderer extends DefaultTableCellRenderer {
		public IconRenderer() {
			setHorizontalAlignment(0);
		}

		public void setValue(Object value) {
			setIcon((value instanceof Icon) ? (Icon) value : null);
		}
	}

	static class BooleanRenderer extends JCheckBox implements TableCellRenderer {
		public BooleanRenderer() {
			setHorizontalAlignment(0);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			this.setOpaque(true);
			setSelected((value != null) && (((Boolean) value).booleanValue()));
			return this;
		}
	}

	public static class LinkRenderer extends DefaultTableCellRenderer {
		private static Color colorLive = new Color(0, 0, 238);
		private static Color colorVisited = new Color(82, 24, 139);

		public void setValue(Object value) {
			if ((value != null) && (value instanceof Link)) {
				Link link = (Link) value;

				setText(link.getText());
				setToolTipText(link.getURL().toString());

				if (link.getVisited()) {
					setForeground(colorVisited);
				} else
					setForeground(colorLive);
			} else {
				super.setValue((value != null) ? value.toString() : "");
			}
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (getText().equals("")) {
				return;
			}

			Rectangle rect = TableCellRenderers.getTextBounds(g, this);

			FontMetrics fm = g.getFontMetrics();
			int descent = fm.getDescent();

      g.drawLine(rect.x, rect.y + rect.height - descent + 1, rect.x + rect.width, rect.y + rect.height - descent + 1);
		}
	}
}