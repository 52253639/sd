package com.kingdee.eas.fdc.invite.client.offline.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.common.LanguageManager;
import com.kingdee.bos.ctrl.kdf.export.ExportManager;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.kds.KDSSheet;
import com.kingdee.bos.ctrl.kdf.table.KDTRange;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.Kdt2Kds;
import com.kingdee.bos.ctrl.swing.KDOptionPane;

public class ExportToExcel {
	private static Logger logger = Logger.getLogger(ExportToExcel.class);

	private static void openFile(String file) {
		try {
			Runtime.getRuntime().exec("cmd /c \"" + file + "\"");
		} catch (IOException e) {
			logger.error(e);
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public static void exportToExcel(List tables) {
		File tempFile = null;
		try {
			tempFile = File.createTempFile("fdc" + System.currentTimeMillis(), ".xls");
		} catch (IOException e) {
			logger.error(e);
		}
		if (tempFile != null) {
			String tempFileName = tempFile.getAbsolutePath();
			KDSSheet sheet = null;
			// 保存成临时文件
			ExportManager man = new ExportManager();
			KDSBook book = null;
			book = new KDSBook("kdtable export");
			for (int i = 0; i < tables.size(); i++) {
				KDTable table = (KDTable) tables.get(i);
				sheet = getKDSSheet(table, book, true);
				try {
					book.addSheet(null, sheet);
				} catch (Exception e) {
					logger.error(e);
				}
			}
			// 统一了异常处理的接口
			man.exportToExcel(book, tempFileName);
			// 尝试用excel打开
			openFile(tempFileName);
		}
	}

	public static void exportToExcel(KDTable table) {
		List list = new ArrayList();
		list.add(table);
		exportToExcel(list);
	}

	private static KDSSheet getKDSSheet(KDTable table, KDSBook book, boolean wantHead) {
		Kdt2Kds trans = new Kdt2Kds(table, book);
		trans.setWithHead(wantHead);
		trans.setWithIndexColumn(false);
		trans.setWithHiddenCol(false);
		trans.setRange(null);
		return trans.transform(table.getName() != null ? table.getName() : "sheet" + table.getID());
	}

	public static void exportExcelToTempFile(KDTable table) throws IOException {
		KDTRange range = null;
		range = table.getSelectManager().toRange();
		range = range.getSequenceRange();
		if (range == null) {
			String msg = LanguageManager.getLangMessage("export2Excel.error", KDTable.class, "");
			KDOptionPane.showMessageDialog(table, msg);
			return;
		}

		File tempFile = File.createTempFile("fdc" + System.currentTimeMillis(), ".xls");
		String tempFileName = tempFile.getAbsolutePath();
		KDSSheet sheet = null;
		// 保存成临时文件
		ExportManager man = new ExportManager();
		KDSBook book = null;
		book = new KDSBook("kdtable export");
		if (!exportColumnConfirm(table)) {
			return;
		}

		sheet = saveToKDSSheet(table, book, true, false, false, "sheet1", table.getSelectManager().toRange());

		try {
			book.addSheet(null, sheet);
		} catch (Exception e1) {
			return;
		}
		// 统一了异常处理的接口
		man.exportToExcel(book, tempFileName);

		openFile(tempFileName);

	}

	public static KDSSheet saveToKDSSheet(KDTable table, KDSBook book, boolean withHead, boolean withIndex,
			boolean withHiddenCol, String sheetName, KDTRange range) {
		Kdt2Kds trans = new Kdt2Kds(table, book);
		trans.setWithHead(withHead);
		trans.setWithIndexColumn(withIndex);
		trans.setWithHiddenCol(withHiddenCol);
		trans.setRange(range);
		return trans.transform(sheetName);
	}

	private static boolean exportColumnConfirm(KDTable table) {
		final int columntCount = table.getColumnCount();
		if (columntCount > 256) {
			String msg2 = LanguageManager.getLangMessage("export2Excel.maxColumnLimit", KDTable.class, "");
			KDOptionPane.showMessageDialog(table, msg2);
			return false;
		}
		return true;
	}

}
