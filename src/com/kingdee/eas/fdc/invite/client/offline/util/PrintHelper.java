package com.kingdee.eas.fdc.invite.client.offline.util;

import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.headfootdesigner.HeadFootModel;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.file.KDF;
import com.kingdee.bos.ctrl.kdf.util.file.KDFException;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.ctrl.print.config.ui.HeaderFooterModel;
import com.kingdee.bos.ctrl.print.printjob.IPrintJob;
import com.kingdee.eas.fdc.invite.client.offline.Constants;
import com.kingdee.eas.fdc.invite.client.offline.ui.MainFrame;

public class PrintHelper {
	private static Logger logger = Logger.getLogger(PrintHelper.class);

	private PrintHelper() {

	}

	private static File file = new File("printConfig.kdf");

	private static String jobID = "最后一个夏天(金莎)";

	private static void hide(KDTable table, boolean hide) {
		for (int i = 0; i < 3; i++) {
			table.getColumn(i).getStyleAttributes().setHided(hide);
		}

		String[] str = ((String) MainFrame.withoutPrintMap.get(table.getName())).split(";");
		for (int i = 0; i < str.length; i++) {
			int col = Integer.parseInt(str[i]);
			if (col > 0)
				table.getColumn(col).getStyleAttributes().setHided(hide);
		}
	}

	/**
	 * 打印预览
	 * 
	 * @param table
	 */
	public static void printPreview(KDTable table) {
		KDF kdf = null;
		if (!Constants.Desc_Table.equals(table.getName()) && !Constants.Total_Table.equals(table.getName()))
			hide(table, true);
		try {
			if (file.exists()) {
				kdf = new KDF(file.getAbsolutePath());
				setPrintTitle(table, kdf);
				table.getPrintManager().printPreview();

			} else {
				setPrintTitle(table, null);
				table.getPrintManager().printPreview();
			}
			table.getPrintManager().saveToKDF(kdf = (kdf != null ? kdf : new KDF()));
			kdf.saveToFile(file.getAbsolutePath());
		} catch (KDFException e) {
			logger.error(e);
		}
		if (!Constants.Desc_Table.equals(table.getName()) && !Constants.Total_Table.equals(table.getName()))
			hide(table, false);
	}

	/**
	 * 直接打印全部
	 * 
	 * @param tabbedPane
	 * @param copies
	 */
	public static void print(JTabbedPane tabbedPane, final int copies) {
		if (tabbedPane.getTabCount() < 2) {
			logger.info("报价文件页签数不能少于2");
			JOptionPane.showMessageDialog(null, "报价文件页签数不能少于2");
			return;
		}
		// 招标说明
		KDTable table = (KDTable) tabbedPane.getComponentAt(0);
		// 报价汇总
		KDTable table2 = (KDTable) tabbedPane.getComponentAt(1);
		try {
			int myCopy = copies;
			if (file.exists()) {
				KDF kdf = new KDF(file.getAbsolutePath());
				myCopy = copies;
				setPrintTitle(table, kdf);
				while (myCopy-- > 0) {
					table.getPrintManager().printDirect();
				}

				myCopy = copies;
				setPrintTitle(table2, kdf);
				while (myCopy-- > 0) {
					table2.getPrintManager().printDirect();
				}
				for (int i = 2, count = tabbedPane.getTabCount(); i < count; i++) {
					myCopy = copies;
					KDTable t = (KDTable) tabbedPane.getComponentAt(i);
					hide(t, true);
					setPrintTitle(t, kdf);
					while (myCopy-- > 0)
						t.getPrintManager().printDirect();
					hide(t, false);
				}

			} else {
				myCopy = copies;
				setPrintTitle(table, null);
				while (myCopy-- > 0)
					table.getPrintManager().printDirect();

				myCopy = copies;
				setPrintTitle(table2, null);
				while (myCopy-- > 0) {
					table2.getPrintManager().printDirect();
				}
				for (int i = 2, count = tabbedPane.getTabCount(); i < count; i++) {
					myCopy = copies;
					KDTable t = (KDTable) tabbedPane.getComponentAt(i);
					hide(t, true);
					setPrintTitle(t, null);
					while (myCopy-- > 0)
						t.getPrintManager().printDirect();
					hide(t, false);
				}
			}
		} catch (KDFException e) {
			logger.error(e);
		}

	}

	private static void setPrintTitle(KDTable table, KDF kdf) {
		IPrintJob job = table.getPrintManager().getNewPrintManager().createPrintJobs();
		job.setID(jobID);
		if (kdf != null)
			table.getPrintManager().setFromKDF(kdf);
		HeadFootModel header = new HeadFootModel();
		StyleAttributes sa = Styles.getDefaultSA();
		sa.setFontSize(14);
		sa.setBold(true);
		header.addRow(table.getName(), sa);
		header.addRow("");
		HeaderFooterModel model = job.getConfig().getHeaderFooterModel();
		model.setHeaderModel(header);
	}

}
