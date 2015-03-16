package com.kingdee.eas.fdc.invite.client.offline.ui;

import java.awt.event.ActionListener;
import java.beans.EventHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.eas.fdc.invite.client.offline.util.ExportToExcel;
import com.kingdee.eas.fdc.invite.client.offline.util.TableSettingHelper;

public class TablePopuMenu {
	private static Logger logger = Logger.getLogger(TablePopuMenu.class);

	final KDTable table;

	JPopupMenu menu;
	JFrame frame;

	public JPopupMenu getMenu() {
		if (this.menu == null)
			this.menu = createDefaultMenu();
		return menu;
	}

	public TablePopuMenu(final JFrame frame, final KDTable table) {
		this.frame = frame;
		this.table = table;
		table.addKDTMouseListener(new KDTMouseListener() {
			public void tableClicked(KDTMouseEvent e) {
				if ((e.getButton() == KDTMouseEvent.BUTTON3) && (e.getClickCount() == 1)) {
					getMenu().show(e.getOriginView(), e.getX(), e.getY());
				}
			}
		});
	}

	protected JPopupMenu createDefaultMenu() {
		JPopupMenu menu = new JPopupMenu();

//		JMenuItem item1 = new JMenuItem("导出到Excel");
//		item1.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "export"));
//		menu.add(item1);

		JMenuItem tiem2 = new JMenuItem("导出选择部分到Excel");
		tiem2.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "exportPart"));
		menu.add(tiem2);

		JMenuItem tiem3 = new JMenuItem("导出全部表到Excel");
		tiem3.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "exportAll"));
		menu.add(tiem3);

		menu.addSeparator();
		JMenuItem item4 = new JMenuItem("表格设置");
		item4.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "tableSetting"));
		menu.add(item4);

		JMenuItem item5 = new JMenuItem("恢复默认设置");
		item5.addActionListener((ActionListener) EventHandler.create(ActionListener.class, this, "defaultSetting"));
		menu.add(item5);

		return menu;
	}

	public void export() {
		try {
			ExportToExcel.exportToExcel(table);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public void exportAll() {
		try {
			List tables = new ArrayList();
			KDTabbedPane tabbedPane = (KDTabbedPane) table.getParent();
			for (int i = 0; i < tabbedPane.getTabCount(); i++) {
				tables.add(tabbedPane.getComponentAt(i));
			}
			ExportToExcel.exportToExcel(tables);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public void exportPart() throws IOException {
		ExportToExcel.exportExcelToTempFile(table);
	}

	public void tableSetting() throws Exception {
		new TableSetting(frame, table).show();
	}

	public void defaultSetting() {
		TableSettingHelper.setDefaultFormat(table);
		TableSettingHelper.setFreeze(table);
	}
}
