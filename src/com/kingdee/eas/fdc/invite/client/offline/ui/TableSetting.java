package com.kingdee.eas.fdc.invite.client.offline.ui;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.eas.fdc.invite.client.offline.Constants;
import com.kingdee.eas.fdc.invite.client.offline.util.ObjectUtil;
import com.kingdee.eas.fdc.invite.client.offline.util.TableSettingHelper;

public class TableSetting extends AbstractTableSetting {

	// 是否设置过冻结列
	private boolean hasSetFreeColumn = false;

	// 是否允许设置对齐方式
	private static final boolean alignEnable = false;

	public TableSetting(JFrame frame, KDTable table) {
		super(frame, table);
		initTable();
	}

	private void initTable() {
		final KDTable table = super.table;
		IRow row = table.addHeadRow();
		IColumn column = null;
		column = table.addColumn();
		column.setKey("列名");
		column.getStyleAttributes().setLocked(true);
		row.getCell("列名").setValue("列名");

		column = table.addColumn();
		column.setKey("列宽度");
		row.getCell("列宽度").setValue("列宽度");

		KDFormattedTextField tableWidth = new KDFormattedTextField(KDFormattedTextField.INTEGER);
		// tableWidth.setMinimumValue(new Comparable())
		tableWidth.setMinimumValue(new Integer(1));
		tableWidth.setMaximumValue(new Integer(1000));

		column.setEditor(new KDTDefaultCellEditor(tableWidth));

		if (alignEnable) {
			column = table.addColumn();
			column.setKey("对齐方式");
			row.getCell("对齐方式").setValue("对齐方式");
			Vector values = new Vector();
			values.addElement("左");
			values.addElement("中");
			values.addElement("右");
			JComboBox c = new JComboBox(values);
			column.setEditor(new KDTDefaultCellEditor(c));
		}

		table.addColumn().setKey("隐藏");
		row.getCell("隐藏").setValue("隐藏");
		table.addColumn().setKey("列冻结");
		row.getCell("列冻结").setValue("列冻结");

		table.getColumn(0).setWidth(120);
		int cloumnCount = alignEnable ? 5 : 4;
		for (int i = 1; i < cloumnCount; i++) {
			table.getColumn(i).setWidth(60);
		}

		table.checkParsed();
		KDTable settingTable = super.settingTable;
		int freezeCol = settingTable.getViewManager().getFreezeCol();
		for (int i = 0; i < settingTable.getColumnCount(); i++) {
			row = table.addRow();
			// 列名
			row.getCell(0).setValue(settingTable.getHeadRow(settingTable.getHeadRowCount() - 1).getCell(i).getValue());
			row.setUserObject(new Integer(i));
			if (alignEnable)
				if ("子目名称".equals(row.getCell(0).getValue()) || "单位".equals(row.getCell(0).getValue())) {
					row.getCell(2).getStyleAttributes().setBackground(Color.lightGray);
					row.getCell(2).getStyleAttributes().setLocked(true);
				}

			int width = settingTable.getColumn(i).getKDTColumn().getWidth();
			if (width <= 0)
				width = settingTable.getColumn(i).getWidth();
			row.getCell("列宽度").setValue(new Integer(width));
			if (alignEnable)
				row.getCell("对齐方式").setValue(getAlignName(settingTable.getColumn(i).getStyleAttributes().getHorizontalAlign()));
			row.getCell("隐藏").setValue(new Boolean(settingTable.getColumn(i).getStyleAttributes().isHided()));
			row.getCell("列冻结").setValue(new Boolean(freezeCol > 0 && i < freezeCol));
		}

		height.setValue(new Integer(settingTable.getRow(0).getHeight()));

		if (alignEnable && (Constants.Total_Table.equals(settingTable.getName()) || !Constants.Desc_Table.equals(settingTable.getName()))) {
			table.getColumn(4).getStyleAttributes().setLocked(true);
		} else {
			// 不准冻结最后一列
			final int lastColumn = alignEnable ? 4 : 3;
			table.getCell(table.getRowCount() - 1, lastColumn).getStyleAttributes().setLocked(true);
			table.addKDTEditListener(new KDTEditAdapter() {
				public void editValueChanged(KDTEditEvent e) {
					if (e.getColIndex() != lastColumn)
						return;
					hasSetFreeColumn = true;
					final int rowIndex = e.getRowIndex();
					if (!((Boolean) table.getCell(rowIndex, lastColumn).getValue()).booleanValue()) {
						JOptionPane.showMessageDialog(null, "此列之前的所有列也会被冻结", "冻结列提示", JOptionPane.OK_OPTION);
					}
					IRow row = table.getRow(rowIndex);
					ICell cell = row.getCell(lastColumn);
					if (((Boolean) cell.getValue()).booleanValue()) {
						for (int i = rowIndex + 1; i < table.getRowCount(); i++) {
							table.getRow(i).getCell(lastColumn).setValue(Boolean.FALSE);
						}
					} else {
						for (int i = 0; i < rowIndex; i++) {
							table.getRow(i).getCell(lastColumn).setValue(Boolean.TRUE);
						}
					}

				}
			});
		}

	}

	/**
	 * @param str
	 * @return 返回对齐方式
	 */
	private HorizontalAlignment getAlign(String str) {
		if (str.equals("左"))
			return HorizontalAlignment.LEFT;
		if (str.equals("中"))
			return HorizontalAlignment.CENTER;
		if (str.equals("右"))
			return HorizontalAlignment.RIGHT;
		return HorizontalAlignment.LEFT;
	}

	private String getAlignName(HorizontalAlignment align) {
		if (HorizontalAlignment.LEFT.equals(align))
			return "左";
		if (HorizontalAlignment.CENTER.equals(align))
			return "中";
		if (HorizontalAlignment.RIGHT.equals(align))
			return "右";
		return "左";
	}

	public void setDefault() {
		TableSettingHelper.setDefaultFormat(settingTable);
		//
		height.setValue(new Integer(TableSettingHelper.defaultHeight));
		for (int i = 0; i < settingTable.getColumnCount(); i++) {
			IRow row = table.getRow(i);
			int width = settingTable.getColumn(i).getKDTColumn().getWidth();
			if (width <= 0)
				width = settingTable.getColumn(i).getWidth();
			row.getCell("列宽度").setValue(new Integer(width));
			if (alignEnable)
				row.getCell("对齐方式").setValue(settingTable.getColumn(i).getStyleAttributes().getHorizontalAlign());
			row.getCell("隐藏").setValue(Boolean.FALSE);
			row.getCell("列冻结").setValue(Boolean.FALSE);
		}
		if (hasSetFreeColumn) {
			TableSettingHelper.setFreeze(settingTable);
			hasSetFreeColumn = false;
		}
	}

	public void OK() {
		KDTable table = super.table;
		int freezeCol = -1;
		final int rowCount = table.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			IRow row = table.getRow(i);
			int col = ((Integer) row.getUserObject()).intValue();
			IColumn column = super.settingTable.getColumn(col);
			column.setWidth(ObjectUtil.convertToInt(row.getCell("列宽度").getValue(), 100));
			StyleAttributes sa = super.settingTable.getColumn(col).getStyleAttributes();
			if (alignEnable)
				sa.setHorizontalAlign(getAlign(row.getCell("对齐方式").getValue().toString()));
			sa.setHided(((Boolean) row.getCell("隐藏").getValue()).booleanValue());
			if (((Boolean) row.getCell("列冻结").getValue()).booleanValue()) {
				freezeCol = col + 1;
			}
			column.setStyleAttributes(sa);

		}
		if (heightChanged)
			for (int i = 0; i < super.settingTable.getRowCount(); i++) {
				super.settingTable.getRow(i).setHeight(ObjectUtil.convertToInt(height.getText(), 20));
			}
		if (!Constants.Total_Table.equals(settingTable.getName()) && !Constants.Desc_Table.equals(settingTable.getName())) {
			super.settingTable.getViewManager().freeze(-1, freezeCol);
			if (hasSetFreeColumn) {
				TableSettingHelper.setFreeze(settingTable);
				hasSetFreeColumn = false;
			}
		}
		this.dispose();
	}

	public void cancel() {
		this.dispose();
	}

}
