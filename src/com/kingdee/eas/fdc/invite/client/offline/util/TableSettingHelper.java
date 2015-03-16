package com.kingdee.eas.fdc.invite.client.offline.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.eas.fdc.invite.client.offline.ui.MainFrame;

public class TableSettingHelper {

	private static Logger logger = Logger.getLogger(TableSettingHelper.class);

	// 默认高度
	public static final int defaultHeight = 20;
	// 普通宽度
	public static final int normalHeight = 100;

	public static void setDefaultFormat(KDTable table) {
		// 解冻
		table.getViewManager().unFreeze();
		// 默认行高
		KDTableHelper.setAllRowHeight(table, defaultHeight);

		for (int i = 0; i < table.getColumnCount(); i++) {
			IColumn column = table.getColumn(i);
			// 解隐藏
			column.getStyleAttributes().setHided(false);
			if (i == 0) {
				column.setWidth(30);
			} else if (i == 1 || i == 2) {
				column.setWidth(60);
			} else {
				column.setWidth(normalHeight);
			}
		}
	}

	public static void setFreeze(KDTable settingTable) {
		int seperatorIndex = MainFrame.currentFile.lastIndexOf('\\');
		if (seperatorIndex < 1)
			seperatorIndex = MainFrame.currentFile.lastIndexOf('/');
		String dir = MainFrame.currentFile.substring(0, seperatorIndex);
		String filename = MainFrame.currentFile.substring(seperatorIndex + 1);
		JTabbedPane tabbedPane = (JTabbedPane) settingTable.getParent();

		Properties p = new Properties();
		File file = new File(dir, filename + ".config");
		FileOutputStream fis = null;
		try {
			fis = new FileOutputStream(file);
			for (int i = 1; i < tabbedPane.getTabCount(); i++) {
				KDTable table = (KDTable) tabbedPane.getComponentAt(i);
				int freezeCol = table.getViewManager().getFreezeCol();
				if (freezeCol > -1)
					p.put(table.getName(), freezeCol + "");
			}
			p.store(fis, "This is a configure file.Please do not edit it.You can delete it by yourself");
		} catch (FileNotFoundException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}

	}
	//
	// /**
	// *
	// * @param table
	// * @param settingTable
	// */
	// public static void loadConfig(KDTable table, KDTable settingTable) {
	//
	// }
}
