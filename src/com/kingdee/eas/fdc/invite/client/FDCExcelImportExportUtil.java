package com.kingdee.eas.fdc.invite.client;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import com.kingdee.bos.ctrl.common.LanguageManager;
import com.kingdee.bos.ctrl.excel.io.kds.BookToKDSBook;
import com.kingdee.bos.ctrl.excel.model.struct.Book;
import com.kingdee.bos.ctrl.excel.model.struct.Cell;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.ctrl.kdf.export.ExporterParameter;
import com.kingdee.bos.ctrl.kdf.export.POIExportException;
import com.kingdee.bos.ctrl.kdf.export.POIXlsExporter;
import com.kingdee.bos.ctrl.kdf.export.XlsExporterParameter;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.util.printout.PrintableBook;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.util.SimpleFileFilter;
import com.kingdee.eas.fi.newrpt.client.designer.io.WizzardIO;
import com.kingdee.eas.fi.rpt.util.IOHelper;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.client.MsgBox;

/**
 *
 * @author 肖飙彪
 *
 */
public class FDCExcelImportExportUtil {

	public final static String sheetName_ProjectDescription = "招标项目说明";

	public final static String sheetName_ProjectGather = "项目汇总表";

	/**
	 * 功能描述:将book对象到出为excel
	 *
	 * @param ui
	 * @param book
	 */
	public static void exportExcel(CoreUI ui, Book book) {

		String fileName = getExportFileName(ui);
		if (fileName == null) {
			return;
		}
		Book tempBook = changeSheetBG(book);
		exportExcelFile(tempBook, fileName);
	}


	/**
	 * 功能描述:得到输出的文件全路径
	 *
	 * @param ui
	 * @return
	 */
	public static String getExportFileName(CoreUI ui) {
		KDFileChooser chsFile = new KDFileChooser();
		String XLS = "xls";
		String Key_File = "Key_File";
		SimpleFileFilter Filter_Excel = new SimpleFileFilter(XLS, "MS Excel"
				+ LanguageManager.getLangMessage(Key_File, WizzardIO.class,
						"操作失败"));
		chsFile.addChoosableFileFilter(Filter_Excel);
		chsFile.showSaveDialog(ui);
		String fileName = "";
		if (0 == JFileChooser.APPROVE_OPTION) {
			File file = chsFile.getSelectedFile();
			if (file == null)
				return null;
			fileName = file.getAbsolutePath();
		} else {
			return null;
		}

		if (fileName.equals("")) {
			return null;
		}
		if (!fileName.endsWith(".xls")) {
			fileName += ".xls";
		}
		return fileName;
	}

	/**
	 * 功能描述:输出文件
	 *
	 * @param book
	 * @param fileName
	 */
	public static void exportExcelFile(Book book, String fileName) {
		try {
			POIXlsExporter xlsExporter = new POIXlsExporter();
			KDSBook kdsBook = BookToKDSBook.traslate(book);
			PrintableBook printableBook = new PrintableBook(kdsBook);
			xlsExporter.getParameters().put(ExporterParameter.KD_Print,
					printableBook);
			xlsExporter.getParameters().put(ExporterParameter.EXPORT_FORMULA,
					"true");
			xlsExporter.getParameters().put(ExporterParameter.OUTPUT_FILE_NAME,
					fileName);
			xlsExporter.getParameters().put(
					XlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			xlsExporter.export();
		} catch (POIExportException e) {
			if (e.toString().indexOf(POIExportException.EXPORT_OCCUR_ERROR) != 0) {
				MsgBox.showWarning("你当前覆盖的文件正在被使用,覆盖失败!");
			}
		}
	}

	/**
	 * 功能描述:改变背景色(当是黑色的时候,主要正对报价清单,其他无此现象)
	 *
	 * @param myBook
	 * @return
	 */
	public static Book changeSheetBG(Book myBook) {
		Book book = cloneDIY(myBook);
		for (int i = 0; i < book.getSheetCount(); i++) {
			Sheet sheet = book.getSheet(i);
			if (!sheet.getSheetName().equals(sheetName_ProjectDescription)
					&& !sheet.getSheetName().equals(sheetName_ProjectGather)) {
				int rowMax = sheet.getMaxRowIndex();
				int colMax = sheet.getMaxColIndex();
				for (int row = 0; row <= rowMax; row++) {
					for (int col = 0; col <= colMax; col++) {
						Cell cell = sheet.getCell(row, col, true);
						if (cell.getSSA() != null
								&& cell.getSSA().getBackground() != null) {
							if (cell.getSSA().getBackground().getBlue() == 0
									&& cell.getSSA().getBackground().getGreen() == 0
									&& cell.getSSA().getBackground().getRed() == 0) {
								StyleAttributes sa = Styles.getEmptySA();
								sa.append(cell.getSSA(), true);
								sa.setBackground(Color.white);
								cell.setSSA(sa);
							}
						}
//						if (cell.getFormula().startsWith("=")) {
//						}
					}
				}
			}
		}
		return book;
	}

	public static Book cloneDIY(Book obj) {
		try {
			byte[] b = IOHelper.packBook(obj);
			return IOHelper.unpackBook(b);
		} catch (IOException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return null;

	}
}
