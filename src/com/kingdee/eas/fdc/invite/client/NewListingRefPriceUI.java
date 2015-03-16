/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.LanguageManager;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.io.kds.KDSBookToBook;
import com.kingdee.bos.ctrl.excel.model.struct.Book;
import com.kingdee.bos.ctrl.excel.model.struct.Cell;
import com.kingdee.bos.ctrl.excel.model.struct.Row;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.kds.KDSSheet;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.util.SimpleFileFilter;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberConstants;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.ColumnTypeEnum;
import com.kingdee.eas.fdc.invite.DescriptionEnum;
import com.kingdee.eas.fdc.invite.HeadColumnInfo;
import com.kingdee.eas.fdc.invite.HeadTypeFactory;
import com.kingdee.eas.fdc.invite.HeadTypeInfo;
import com.kingdee.eas.fdc.invite.ListingItemInfo;
import com.kingdee.eas.fdc.invite.NewListingColumnCollection;
import com.kingdee.eas.fdc.invite.NewListingColumnFactory;
import com.kingdee.eas.fdc.invite.NewListingColumnInfo;
import com.kingdee.eas.fdc.invite.NewListingEntryCollection;
import com.kingdee.eas.fdc.invite.NewListingEntryFactory;
import com.kingdee.eas.fdc.invite.NewListingEntryInfo;
import com.kingdee.eas.fdc.invite.NewListingFactory;
import com.kingdee.eas.fdc.invite.NewListingInfo;
import com.kingdee.eas.fdc.invite.NewListingPageCollection;
import com.kingdee.eas.fdc.invite.NewListingPageFactory;
import com.kingdee.eas.fdc.invite.NewListingPageInfo;
import com.kingdee.eas.fdc.invite.NewListingValueCollection;
import com.kingdee.eas.fdc.invite.NewListingValueFactory;
import com.kingdee.eas.fdc.invite.NewListingValueInfo;
import com.kingdee.eas.fdc.invite.PageHeadInfo;
import com.kingdee.eas.fdc.invite.RefPriceEntryCollection;
import com.kingdee.eas.fdc.invite.RefPriceEntryInfo;
import com.kingdee.eas.fdc.invite.RefPriceInfo;
import com.kingdee.eas.fi.newrpt.client.designer.io.WizzardIO;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 招标清单 参考价编辑
 */
public class NewListingRefPriceUI extends AbstractNewListingRefPriceUI {


	private boolean isRate = false;

	private static final Logger logger = Logger.getLogger(NewListingRefPriceUI.class);

	private final Color LOCKCOLOR = new Color(0xF0EDD9);

	private final Color REQUIREDCOLOR = new Color(0, true);

	private final Color ERR_COLOR = new Color(0xFF, 0xEA, 0x67);

	private List tables = null;

	final Map columnMap = new HashMap();

	boolean isModify = false;

	private ICellEditor amtEditor = null;

	private ICellEditor dateEditor = null;

	private ICellEditor txtEditor = null;
	
	private final String SUB_NAME = "子目名称";
	/**
	 * 子目名称 所在的列
	 */
	private final int COLUMN_INDEX_FOR_SUB_NAME = 3; 
	

	/**
	 * output class constructor
	 */
	public NewListingRefPriceUI() throws Exception {
		super();
		this.initEditor();
	}

	/**
	 * 初始化金额、日期和txt控件的编辑器
	 * 
	 * @author owen_wen
	 */
	private void initEditor() {
		KDFormattedTextField txtAmtFld = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		// 费率招标
		if (isRate) {
			txtAmtFld.setPrecision(4);
		} else {
			txtAmtFld.setPrecision(2);
		}

		txtAmtFld.setRemoveingZeroInDispaly(false);
		txtAmtFld.setRemoveingZeroInEdit(false);
		txtAmtFld.setNegatived(false);
		txtAmtFld.setMaximumValue(FDCNumberConstants.MAX_VALUE);
		txtAmtFld.setMinimumValue(FDCNumberConstants.MIN_VALUE);
		txtAmtFld.setHorizontalAlignment(SwingConstants.RIGHT);
		amtEditor = new KDTDefaultCellEditor(txtAmtFld);

		KDDatePicker pkDate = new KDDatePicker();
		dateEditor = new KDTDefaultCellEditor(pkDate);

		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		txtEditor = new KDTDefaultCellEditor(textField);
	}

	public void actionImportExcel_actionPerformed(ActionEvent e) throws Exception {
		if (tabbedPnlTable.getSelectedIndex() == 0) {
			MsgBox.showError("当前页签不允许导入Excel！");
			return;
		}
		if (MsgBox.OK != MsgBox.showConfirm2("此操作将覆盖以前的数据!")) {
			return;
		}
		File file = getImportFile();
		importExcel(file);
	}

	private File getImportFile() {
		KDFileChooser fileChooser = new KDFileChooser();
		String XLS = "xls";
		String Key_File = "Key_File";
		SimpleFileFilter Filter_Excel = new SimpleFileFilter(XLS, "MS Excel"
				+ LanguageManager.getLangMessage(Key_File, WizzardIO.class, "操作失败"));
		fileChooser.addChoosableFileFilter(Filter_Excel);
		int ret = fileChooser.showOpenDialog(this);
		if (ret == JFileChooser.APPROVE_OPTION) {
			return fileChooser.getSelectedFile();
		} else
			return null;
	}

	public void importExcel(File file) throws Exception {
		if (file == null || tabbedPnlTable.getTabCount() <= 0 || tabbedPnlTable.getSelectedIndex() == 0)
			return;

		String fileName = file.getAbsolutePath();
		KDSBook kdsbook = InviteHelper.excelReaderParse(this, fileName);
		if (kdsbook != null) {
			Book importBook = KDSBookToBook.traslate(kdsbook);
			KDTable table = (KDTable) this.tables.get(this.tabbedPnlTable.getSelectedIndex() - 1);
			Sheet excelSheet = importBook.getSheet(0);

			List errList = InviteHelper.validateImportExcelHeadRow(table, excelSheet);
			if (InviteHelper.validateErrorInfoList(this, errList)) {
				KDSSheet kdsSheet = kdsbook.getSheet(new Integer(0));
				importSheetToTable(table, excelSheet, kdsSheet);
				this.setUnionData(table);
			}
		}
	}

	private void importSheetToTable(KDTable table, Sheet excelSheet, KDSSheet kdsSheet) throws Exception {
		logger.debug("import refPrice xls data");
		int itemNameIndex = table.getColumnIndex(SUB_NAME);
		int unitIndex = table.getColumnIndex("单位");
		int maxRow = excelSheet.getMaxRowIndex();
		int maxColumn = excelSheet.getMaxColIndex();
		for (int rowIndex = 3; rowIndex <= maxRow; rowIndex++) {
			Row excelRow = excelSheet.getRow(rowIndex, true);
			Variant rawVal = excelRow.getCell(itemNameIndex, true).getValue();
			if (Variant.isNull(rawVal)) {
				continue;
			}
			String excelItemName = (String) kdsSheet.getCell(rowIndex, itemNameIndex, true).getValue();
			String excelUnit = (String) kdsSheet.getCell(rowIndex, unitIndex, true).getValue();
			excelUnit = (excelUnit == null) ? "" : excelUnit;
			for (int i = 1; i < table.getRowCount(); i++) {
				NewListingEntryInfo entryInfo = (NewListingEntryInfo) table.getRow(i).getUserObject();
				if (!entryInfo.isIsLeaf())
					continue;

				// 检查excel数据的子目名称+单位和界面上的是否一致
				String name = (String) table.getCell(i, itemNameIndex).getValue();
				String unit = (String) table.getCell(i, unitIndex).getValue();
				unit = (unit == null) ? "" : unit;
				if (!(name.equals(excelItemName) && unit.equals(excelUnit))) {
					continue;
				}

				for (int col = 1; col <= maxColumn; col++) {
					ICell cell = table.getCell(i, col);
					if (cell == null || cell.getStyleAttributes().isLocked()) {
						continue;
					}
					NewListingColumnInfo colInfo = (NewListingColumnInfo) this.columnMap.get(this.generateKey(table, table.getColumn(col)));
					Cell excelCell = excelSheet.getCell(excelRow, col, true);
					rawVal = excelCell.getValue();
					if (rawVal.equals(Variant.nullVariant)) {
						cell.setValue(null);
					} else {
						logger.debug("col:" + col + ", val:" + rawVal);
						ColumnTypeEnum colType = colInfo.getHeadColumn().getColumnType();
						InviteHelper.loadExcelVal(cell, rawVal, colType);
						// if (colType.equals(ColumnTypeEnum.Amount)) {
						// BigDecimal colValue = rawVal.toBigDecimal();
						// cell.setValue(colValue);
						// } else if (colType.equals(ColumnTypeEnum.DATE)) {
						// Date dateVal = null;
						// if (cellRawVal.getVt() == Variant.VT_CALENDAR) {
						// dateVal = ((Calendar) cellRawVal.getValue())
						// .getTime();
						// } else {
						// dateVal = (Date) cellRawVal.getValue();
						// }
						// cell.setValue(dateVal);
						// } else {
						// String colValue = rawVal.toString();
						// cell.setValue(colValue);
						// }
					}
				}
			}
		}
		// for (int row = 3; row <= maxRow; row++) {
		// for (int col = 1; col <= maxColumn; col++) {
		// if (table.getCell(row - 2, col) != null
		// && !table.getCell(row - 2, col).getStyleAttributes()
		// .isLocked()) {
		// if (excelSheet.getCell(row, col, true).getValue() !=
		// Variant.nullVariant) {
		// String colValue = excelSheet.getCell(row, col, true)
		// .getValue().toString();
		// if (colValue.equals("是")) {
		// table.getCell(row - 2, col).setValue(
		// Boolean.valueOf(true));
		// } else if (colValue.equals("否")) {
		// table.getCell(row - 2, col).setValue(
		// Boolean.valueOf(false));
		// } else {
		// if (table.getColumn(col).getKey().equals("子目名称")) {
		// if (colValue.length() > 500) {
		// colValue = colValue.substring(499);
		// }
		// } else {
		// if (colValue.length() > 80) {
		// colValue = colValue.substring(79);
		// }
		// }
		// table.getCell(row - 2, col).setValue(colValue);
		// }
		// }
		// }
		// }
		// }
	}

	public KDTabbedPane getTabbedPane() {
		return tabbedPnlTable;
	}

	public boolean isModify() {
		return isModify;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return NewListingFactory.getRemoteInstance();
	}

	protected IObjectValue createNewData() {
		return null;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initControl();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				InviteHelper.setAotuHeight(tabbedPnlTable);
			}
		});
//		// 20101107 zhougang 招标清单编制供应商自定义页签不能看到反写的子目；参考价编制也同样处理
//		for (int i = 1; i < this.tabbedPnlTable.getTabCount(); i++) {
//			KDTable table = (KDTable) this.tabbedPnlTable.getComponentAt(i);
//			NewListingPageInfo info = (NewListingPageInfo) table.getUserObject();
//			if(info != null && info.getHeadType() != null){
////				HeadTypeInfo hdInfo;
////				hdInfo = HeadTypeFactory.getRemoteInstance().getHeadTypeInfo("where id = '" + info.getHeadType().getString("id") + "'");
//				if(info.getHeadType().isIsDefined())
//				{
//					for(int j = 1; j < table.getRowCount(); j++)
//					{
//						table.getRow(j).getStyleAttributes().setHided(true);
//					}
//				}
//			}
//		}
	}

	private void initControl() {
		this.actionAuditResult.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setVisible(false);
		this.actionEdit.setEnabled(false);
		this.actionCopy.setVisible(false);
		this.actionCopy.setEnabled(false);
		this.actionRemove.setVisible(false);
		this.actionRemove.setEnabled(false);
		actionAddLine.setVisible(false);
		actionInsertLine.setVisible(false);
		actionRemoveLine.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.actionSubmit.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionWorkFlowG.setVisible(false);
		this.txtNumber.setEnabled(false);
		this.txtName.setEnabled(false);
		this.prmtCurProject.setEnabled(false);
		this.prmtInviteType.setEnabled(false);
		this.txtDescription.setEnabled(false);
		this.actionRefPrice.setEnabled(true);
		btnEditRefPrice.setIcon(EASResource.getIcon("imgTbtn_edit"));
		this.actionExportRefPrice.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuView.setVisible(false);
		this.menuTable1.setVisible(false);
		this.menuWorkflow.setVisible(false);
		this.menuSubmitOption.setVisible(false);
	}

	public void loadFields() {
		super.loadFields();
		loadPages();
	}

	private void loadPages() {
		tabbedPnlTable.removeAll();
		tables = new ArrayList();
		NewListingPageCollection coll = editData.getPages();
		int size = coll.size();
		for (int i = 0; i < size; i++) {
			NewListingPageInfo page = (NewListingPageInfo) coll.get(i);
			PageHeadInfo pageHead = page.getPageHead();
			KDTable table = new KDTable();
			this.tables.add(table);
			table.setName(pageHead.getName());
			initTable(table, page);
			loadTableData(table, page);
			setUnionData(table);
			this.tabbedPnlTable.add(table, pageHead.getName());
		}
		if (size > 0) {
			initTotalPageTable();
		}
		this.storeFields();
	}

	public void storeFields() {
		super.storeFields();
		this.initOldData(this.editData);
	}

	private void updateTotalPageData() {
		if (tabbedPnlTable.getTabCount() == 0) {
			return;
		}
		KDTable table = (KDTable) tabbedPnlTable.getComponentAt(0);
		if (!table.getName().equals("报价汇总表")) {
			return;
		}
		int pageCount = tabbedPnlTable.getTabCount() - 1;
		BigDecimal sum = FDCNumberConstants.ZERO;
		for (int i = 1; i < pageCount + 1; i++) {
			KDTable page = (KDTable) tabbedPnlTable.getComponentAt(i);
			for (int j = 3; j < page.getColumnCount(); j++) {
				IColumn col = page.getColumn(j);
				NewListingColumnInfo colInfo = (NewListingColumnInfo) this.columnMap.get(generateKey(page, col));
				if (colInfo != null) {
					DescriptionEnum property = colInfo.getHeadColumn().getProperty();
					if (property.equals(DescriptionEnum.AmountSum)) {
						BigDecimal value = FDCNumberHelper.toBigDecimal(page.getCell(0, j).getValue());
						table.getCell(i - 1, 2).setValue(value);
						if (value != null) {
							sum = sum.add(value);
						}
						break;
					}
				}
			}
		}
		table.getCell(pageCount, "amount").setValue(sum);
		BigDecimal scale = FDCNumberConstants.ZERO;
		Object amtVal = table.getCell(pageCount + 1, "amount").getValue();
		if (amtVal != null) {
			scale = FDCNumberHelper.toBigDecimal(amtVal);
		}
		BigDecimal amount = sum.multiply(scale).divide(FDCNumberConstants.ONE_HUNDRED, BigDecimal.ROUND_HALF_UP);
		table.getCell(pageCount + 2, "amount").setValue(amount);
		table.getCell(pageCount + 3, "amount").setValue(sum.add(amount));
	}

	private void initTotalPageTable() {
		final KDTable table = new KDTable();
		table.addKDTEditListener(new KDTEditAdapter() {
			public void editStopped(KDTEditEvent e) {
				updateTotalPageData();
			}
		});
		table.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		table.setName("报价汇总表");
		table.addHeadRows(2);
		IRow row = table.getHeadRow(1);
		table.addColumns(3);
		table.getColumn(0).setKey("number");
		table.getColumn(0).getStyleAttributes().setLocked(true);
		table.getColumn(1).setKey("name");
		table.getColumn(1).getStyleAttributes().setLocked(true);
		table.getColumn(2).setKey("amount");
		row.getCell(0).setValue("序号");
		row.getCell(1).setValue("项目");
		row.getCell(2).setValue("金额");
		table.getHeadRow(0).getCell(0).setValue("报价汇总表");
		table.getHeadMergeManager().mergeBlock(0, 0, 0, 2);
		int pageCount = tabbedPnlTable.getTabCount();
		table.addRows(pageCount + 4);
		for (int i = 0; i < pageCount; i++) {
			table.getCell(i, "number").setValue(new Integer(i + 1));
			table.getCell(i, "name").setValue(tabbedPnlTable.getTitleAt(i));
			table.getCell(i, "amount").getStyleAttributes().setLocked(true);
		}
		table.getCell(pageCount, "name").setValue("小计");
		table.getCell(pageCount, "amount").getStyleAttributes().setLocked(true);
		table.getCell(pageCount + 1, "name").setValue("税率(%)");
		table.getCell(pageCount + 1, "amount").setValue(this.editData.getTax());
		table.getCell(pageCount + 1, "amount").getStyleAttributes().setLocked(true);
		table.getCell(pageCount + 2, "name").setValue("税金");
		table.getCell(pageCount + 2, "amount").getStyleAttributes().setLocked(true);
		table.getCell(pageCount + 3, "name").setValue("总造价");
		table.getCell(pageCount + 3, "amount").getStyleAttributes().setLocked(true);
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
		formattedTextField.setPrecision(2);
		formattedTextField.setRemoveingZeroInDispaly(false);
		formattedTextField.setRemoveingZeroInEdit(false);
		formattedTextField.setNegatived(false);
		formattedTextField.setMaximumValue(FDCNumberConstants.MAX_VALUE);
		formattedTextField.setMinimumValue(FDCNumberConstants.MIN_VALUE);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		table.getColumn(2).setEditor(numberEditor);
		FDCHelper.formatTableNumber(table, "amount");
		this.tabbedPnlTable.add(table, 0);
		updateTotalPageData();
	}

	private void initTable(final KDTable table, NewListingPageInfo page) {
		table.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		tHelper.setCanSetTable(true);
		table.setUserObject(page);
		setTableEvent(table);
		setTableColumn(table, page);
		setTableRow(table, page);
		table.getTreeColumn().setDepth(getMaxLevel(table) + 1);
	}

	private void setTableEvent(final KDTable table) {
		table.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
			public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
				try {
					tblMain_tableClicked(e);
				} catch (Exception exc) {
					handUIException(exc);
				} finally {
				}
			}
		});
		table.addKDTEditListener(new KDTEditAdapter() {
			// 编辑结束后
			public void editStopped(KDTEditEvent e) {
				IRow row = table.getRow(e.getRowIndex());
				IColumn col = table.getColumn(e.getColIndex());
				Object newVlaue = row.getCell(e.getColIndex()).getValue();
				if (e.getOldValue() == null) {
					if (newVlaue != null) {
						isModify = true;
					}
				} else if (!e.getOldValue().equals(newVlaue)) {
					isModify = true;
				}
				if (columnMap.get(generateKey(table, col)) != null
						&& columnMap.get(generateKey(table, col)) instanceof NewListingColumnInfo) {
					NewListingColumnInfo colInfo = (NewListingColumnInfo) columnMap.get(generateKey(table, col));
					DescriptionEnum property = colInfo.getHeadColumn().getProperty();
					if (property.equals(DescriptionEnum.TotalPrice)
							|| property.equals(DescriptionEnum.TotalPriceSum)) {
						setUnionData(table);
					}
				}
			}
		});
	}

	private void setTableLock(final KDTable table) {
		int priceCount = 0;
		for (int i = 3, colCount = table.getColumnCount(); i < colCount; i++) {
			IColumn col = table.getColumn(i);
			NewListingColumnInfo colInfo = (NewListingColumnInfo) this.columnMap.get(generateKey(table, col));
			DescriptionEnum property = colInfo.getHeadColumn().getProperty();
			if (property.equals(DescriptionEnum.TotalPrice)) {
				priceCount++;
			}
		}

		for (int i = 1; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			boolean isCompose = false;
			if (table.getRow(i).getCell("isCompose").getValue() != null) {
				isCompose = ((Boolean) table.getRow(i).getCell("isCompose").getValue()).booleanValue();
			}
			boolean isLeafRow = isLeafRow(table, i);
			for (int j = 0; j < table.getColumnCount(); j++) {
				IColumn column = table.getColumn(j);
				ICell cell = row.getCell(j);
				cell.getStyleAttributes().setLocked(true);
				NewListingColumnInfo colInfo = (NewListingColumnInfo) this.columnMap.get(generateKey(table, column));
				if (colInfo == null) {
					continue;
				}
				if (isLeafRow) {
					DescriptionEnum property = colInfo.getHeadColumn().getProperty();
					if (property.equals(DescriptionEnum.TotalPrice)) {
						if (isCompose) {
							cell.getStyleAttributes().setLocked(false);
						} else {
							cell.getStyleAttributes().setBackground(Color.lightGray);
						}
					} else if (property.equals(DescriptionEnum.TotalPriceSum)) {
						if (!isCompose || priceCount == 0) {
							cell.getStyleAttributes().setLocked(false);
						}
					} else if (property.equals(DescriptionEnum.Personal)) {
						if (colInfo.isIsQuoting()) {
							cell.getStyleAttributes().setLocked(false);
						}
					}
				}
			}
			if (!isLeafRow) {
				row.getCell(2).setValue(null);
				table.getRow(i).getStyleAttributes().setBackground(LOCKCOLOR);
			}
		}
	}

	private void setTableRow(final KDTable table, NewListingPageInfo page) {
		NewListingEntryCollection collEntry = page.getEntrys();
		int sizeEntry = collEntry.size();
		IRow totalRow = table.addRow();
		totalRow.getCell(3).setValue("合计");
		totalRow.getStyleAttributes().setBackground(Color.lightGray);
		totalRow.getStyleAttributes().setLocked(true);
		for (int k = 0; k < sizeEntry; k++) {
			NewListingEntryInfo infoEntry = collEntry.get(k);
			IRow dataRow = table.addRow();
			dataRow.setUserObject(infoEntry);
			dataRow.getCell("level").setValue(new Integer(infoEntry.getLevel()));
			dataRow.getCell("isKey").setValue(Boolean.valueOf(infoEntry.isIsKey()));
			dataRow.getCell("isCompose").setValue(Boolean.valueOf(infoEntry.isIsCompose()));
			dataRow.setTreeLevel(infoEntry.getLevel());
		}
	}

	private void setTableColumn(final KDTable table, NewListingPageInfo page) {
		NewListingColumnCollection collColumn = page.getColumns();
		int sizeColumn = collColumn.size();
		if (sizeColumn > 0) {
			table.addHeadRows(2);
			IRow row = table.getHeadRow(1);
			IRow parentRow = table.getHeadRow(0);
			IColumn col = table.addColumn(0);
			col.setKey("level");
			col.setWidth(30);
			row.getCell("level").setValue("级次");
			col.getStyleAttributes().setLocked(true);
			IColumn colIsKey = table.addColumn(1);
			colIsKey.setKey("isKey");
			colIsKey.setWidth(60);
			row.getCell("isKey").setValue("关键子目");
			IColumn colIsCompose = table.addColumn(2);
			colIsCompose.setKey("isCompose");
			colIsCompose.setWidth(60);
			row.getCell("isCompose").setValue("单价构成");
			int count = 0;
			KDTMergeManager mm = table.getHeadMergeManager();
			mm.mergeBlock(0, 0, 1, 0);
			mm.mergeBlock(0, 1, 1, 1);
			mm.mergeBlock(0, 2, 1, 2);
			int baseCount = 3;
			for (int j = 0; j < sizeColumn; j++) {
				NewListingColumnInfo colInfo = collColumn.get(j);
				HeadColumnInfo headColInfo = colInfo.getHeadColumn();
				IColumn column = table.addColumn();
				if (headColInfo.getParent() != null) {
					column.setKey(headColInfo.getParent().getName() + headColInfo.getName());
				} else {
					column.setKey(headColInfo.getName());
				}
				
				column.setUserObject(colInfo);
				String key = generateKey(table, headColInfo);
				this.columnMap.put(key, colInfo);
				ColumnTypeEnum columnType = headColInfo.getColumnType();
				if (columnType.equals(ColumnTypeEnum.Amount)) {
					// KDFormattedTextField formattedTextField = new
					// KDFormattedTextField(
					// KDFormattedTextField.BIGDECIMAL_TYPE);
					// formattedTextField.setPrecision(2);
					// formattedTextField.setRemoveingZeroInDispaly(false);
					// formattedTextField.setRemoveingZeroInEdit(false);
					// formattedTextField.setNegatived(false);
					// formattedTextField.setSupportedEmpty(true);
					// formattedTextField.setMaximumValue(FDCHelper.MAX_VALUE);
					// formattedTextField.setMinimumValue(FDCHelper.MIN_VALUE);
					// ICellEditor numberEditor = new KDTDefaultCellEditor(
					// formattedTextField);
					// column.setEditor(numberEditor);
					column.setEditor(amtEditor);
					if (isRate && colInfo.isIsQuoting()) {
						column.getStyleAttributes().setNumberFormat("#,##0.0000;-#,##0.0000");
						((com.kingdee.bos.ctrl.swing.KDFormattedTextField) amtEditor.getComponent())
								.setPrecision(4);
					} else {
						column.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
					}
					column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				} else if (columnType.equals(ColumnTypeEnum.Date)) {
					// KDDatePicker pkDate = new KDDatePicker();
					// ICellEditor dateEditor = new KDTDefaultCellEditor(
					// pkDate);
					column.setEditor(dateEditor);
					column.getStyleAttributes().setNumberFormat("yyyy-MM-dd");
				} else if (columnType.equals(ColumnTypeEnum.String)) {
					// KDTextField textField = new KDTextField();
					// textField.setMaxLength(80);
					// ICellEditor txtEditor = new
					// KDTDefaultCellEditor(textField);
					column.setEditor(txtEditor);
					column.getStyleAttributes().setWrapText(true);
				}
				row.getCell(j + baseCount).setValue(headColInfo.getName());
				if ((headColInfo.getParent() != null)) {
					parentRow.getCell(j + baseCount).setValue(headColInfo.getParent().getName());
					if (j < sizeColumn - 1) {
						HeadColumnInfo infoNext = collColumn.get(j + 1).getHeadColumn();
						if (infoNext.getParent() != null && headColInfo.getParent().equals(infoNext.getParent())) {
							count++;
						} else {
							if (count == 0) {
								mm.mergeBlock(0, j + baseCount, 1, j + baseCount, KDTMergeManager.SPECIFY_MERGE);
							} else {
								mm.mergeBlock(0, j - count + baseCount, 0, j + baseCount,
										KDTMergeManager.DATA_UNIFY);
								count = 0;
							}
						}
					} else {
						mm.mergeBlock(0, j - count + baseCount, 0, j + baseCount, KDTMergeManager.DATA_UNIFY);
						count = 0;
					}
				} else {
					mm.mergeBlock(0, j + baseCount, 1, j + baseCount);
				}
			}
		}
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getColIndex() == -1) {
			return;
		}
		if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
			KDTable table = (KDTable) this.tabbedPnlTable.getSelectedComponent();
			if (table.getColumnKey(e.getColIndex()).equals(SUB_NAME)) {
				this.actionRefPrice_actionPerformed(null);
			}
			if (table.getColumnKey(e.getColIndex()).equals(SUB_NAME)) {
				this.actionRefPrice_actionPerformed(null);
			}
		} else if (e.getClickCount() == 2) {
			KDTable table = (KDTable) this.tabbedPnlTable.getSelectedComponent();
			IColumn sumCol = null;
			for (int i = 3; i < table.getColumnCount(); i++) {
				IColumn column = table.getColumn(i);
				if (this.columnMap.get(generateKey(table, column)) != null
						&& this.columnMap.get(generateKey(table, column)) instanceof NewListingColumnInfo) {
					NewListingColumnInfo col = (NewListingColumnInfo) this.columnMap.get(generateKey(table, column));
					if (col.getHeadColumn().getProperty().equals(DescriptionEnum.ProjectAmtSum)) {
						sumCol = column;
					}
				}
			}
			if (e.getRowIndex() != 0 || e.getColIndex() != sumCol.getColumnIndex()) {
				return;
			}
			String colName = (String) table.getHeadRow(1).getCell(sumCol.getColumnIndex()).getValue();
			boolean isHide = false;
			if (colName.indexOf("...") == -1) {
				isHide = true;
				table.getHeadRow(1).getCell(sumCol.getColumnIndex()).setValue(colName + "...");
			} else {
				isHide = false;
				int sub = colName.indexOf("...");
				colName = colName.substring(0, colName.length() - sub - 1);
				table.getHeadRow(1).getCell(sumCol.getColumnIndex()).setValue(colName);
			}

			for (int i = 3; i < table.getColumnCount(); i++) {
				IColumn column = table.getColumn(i);
				if (this.columnMap.get(generateKey(table, column)) != null
						&& this.columnMap.get(generateKey(table, column)) instanceof NewListingColumnInfo) {
					NewListingColumnInfo col = (NewListingColumnInfo) this.columnMap.get(generateKey(table, column));
					if (col.getHeadColumn().getProperty().equals(DescriptionEnum.ProjectAmt)) {
						column.getStyleAttributes().setHided(isHide);
					}
				}
			}

		}
	}

	private int getMaxLevel(KDTable table) {
		int count = table.getRowCount();
		int levelthis;
		int levelmax = 0;
		for (int i = 1; i < count; i++) {
			levelthis = Integer.parseInt(table.getCell(i, "level").getValue().toString());
			if (levelthis > levelmax) {
				levelmax = levelthis;
			}
		}
		return levelmax;
	}

	public boolean isLeafRow(KDTable table, int rowIndex) {
		if (rowIndex + 1 >= table.getRowCount()) {
			return true;
		}
		int rowLevel = table.getRow(rowIndex).getTreeLevel();
		int nextLevel = table.getRow(rowIndex + 1).getTreeLevel();
		if (nextLevel <= rowLevel) {
			return true;
		}
		return false;
	}

	private void setTableScript(KDTable table) {
		int rowCount = table.getRowCount();
		int amountIndex = 0;
		int amountSum = 0;
		int projectAmtIndex = 0;
		int projectAmtSum = 0;
		int totalPriceIndex = 0;
		int totalPriceSum = 0;
		for (int i = 3, colCount = table.getColumnCount(); i < colCount; i++) {
			IColumn col = table.getColumn(i);
			if (this.columnMap.get(generateKey(table, col)) != null
					&& this.columnMap.get(generateKey(table, col)) instanceof NewListingColumnInfo) {
				NewListingColumnInfo column = (NewListingColumnInfo) this.columnMap.get(generateKey(table, col));
				DescriptionEnum property = column.getHeadColumn().getProperty();
				if (property.equals(DescriptionEnum.ProjectAmt)) {
					if (projectAmtIndex == 0) {
						projectAmtIndex = i;
					}
				} else if (property.equals(DescriptionEnum.ProjectAmtSum)) {
					projectAmtSum = i;
					if (projectAmtIndex == 0) {
						projectAmtIndex = i;
					}
				} else if (property.equals(DescriptionEnum.TotalPrice)) {
					if (totalPriceIndex == 0) {
						totalPriceIndex = i;
					}
				} else if (property.equals(DescriptionEnum.TotalPriceSum)) {
					totalPriceSum = i;
					if (totalPriceIndex == 0) {
						totalPriceIndex = i;
					}
				} else if (property.equals(DescriptionEnum.Amount)) {
					if (amountIndex == 0) {
						amountIndex = i;
					}
				} else if (property.equals(DescriptionEnum.AmountSum)) {
					amountSum = i;
					if (amountIndex == 0) {
						amountIndex = i;
					}
				}
			}
		}
		for (int i = 1; i < rowCount; i++) {
			if (isLeafRow(table, i)) {
				if (projectAmtIndex < projectAmtSum) {
					TableScriptHelper.setTableRowSumFormalu(table.getCell(i, projectAmtSum), projectAmtSum
							- projectAmtIndex);
				}
				if (totalPriceIndex < totalPriceSum) {
					if (table.getCell(i, "isCompose").getValue() != null) {
						boolean isCompose = ((Boolean) table.getCell(i, "isCompose").getValue()).booleanValue();
						if (isCompose) {
							TableScriptHelper.setTableRowSumFormalu(table.getCell(i, totalPriceSum),
									totalPriceSum - totalPriceIndex);
						}
					}
				}
				for (int j = 0; j <= projectAmtSum - projectAmtIndex; j++) {
					TableScriptHelper.setTableMultiplyFormalu(table.getCell(i, amountIndex + j), table.getCell(i,
							projectAmtIndex + j), table.getCell(i, totalPriceSum));
				}
			}
		}
	}

	/**
	 * 设置父科目汇总数
	 * 
	 * @param table
	 * @param amountColumns
	 */
	public void setUnionData(KDTable table) {
		// 20101107 zhougang 招标清单编制供应商自定义页签不能看到反写的子目；参考价编制也同样处理
//		for (int i = 1; i < this.tabbedPnlTable.getTabCount(); i++) {
//			KDTable table = (KDTable) this.tabbedPnlTable.getComponentAt(i);
			NewListingPageInfo info = (NewListingPageInfo) table.getUserObject();
			if(info != null && info.getHeadType() != null){
//				HeadTypeInfo hdInfo;
//				hdInfo = HeadTypeFactory.getRemoteInstance().getHeadTypeInfo("where id = '" + info.getHeadType().getString("id") + "'");
				if(info.getHeadType().isIsDefined())
				{
					for(int j = table.getRowCount() - 1; j > 0; j--)
					{
//						table.getRow(j).getStyleAttributes().setHided(true);
						table.removeRow(j);
					}
				}
			}
//		}
		
		setTableScript(table);
		List colList = new ArrayList();
		for (int i = 3; i < table.getColumnCount(); i++) {
			IColumn column = table.getColumn(i);
			if (this.columnMap.get(generateKey(table, column)) != null
					&& this.columnMap.get(generateKey(table, column)) instanceof NewListingColumnInfo) {
				NewListingColumnInfo col = (NewListingColumnInfo) this.columnMap.get(generateKey(table, column));
				if (col != null && (col.getHeadColumn().getProperty().equals(DescriptionEnum.AmountSum))) {
					colList.add(new Integer(i));
				}
			}
		}
		for (int i = 1; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			// 设置汇总行
			int level = row.getTreeLevel();
			int maxLevel = this.getMaxLevel(table);
			if (level == maxLevel) {
				continue;
			}
			List aimRowList = new ArrayList();
			for (int j = i + 1; j < table.getRowCount(); j++) {
				IRow rowAfter = table.getRow(j);
				if (rowAfter.getTreeLevel() <= level) {
					break;
				}
				if (isLeafRow(table, j))
					aimRowList.add(rowAfter);

			}
			if (aimRowList.size() > 0) {
				for (int j = 0; j < colList.size(); j++) {
					Integer colNum = (Integer) colList.get(j);
					int colIndex = colNum.intValue();
					BigDecimal sum = null;
					for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) aimRowList.get(rowIndex);
						Object value = rowAdd.getCell(colIndex).getValue();
						if (value != null) {
							if (sum == null) {
								sum = FMConstants.ZERO;
							}
							sum = sum.add(FDCNumberHelper.toBigDecimal(value));
						}
					}
					row.getCell(colIndex).setValue(sum);
				}
			}
		}
		setTableTotal(table);
		table.getScriptManager().runAll();
		updateTotalPageData();
		setTableLock(table);
	}

	/** 生成comlumnMap的key，组成方式为 "表名_列名_列名ID"
	 * @author owen_wen 2010-11-16
	 * @param table
	 * @param column
	 * @return
	 */
	private String generateKey(KDTable table, IColumn column) {
		return table.getName() +  column.getKey();
	}
	
	/**
	 * 生成comlumnMap的key，组成方式为 "表名_列名_列名ID"
	 * @author owen_wen 2010年11月16日
	 * @param table 所在表
	 * @param headColInfo 表头列
	 * @return
	 */
	private String generateKey(final KDTable table, HeadColumnInfo headColInfo) {
		String key = table.getName();
		if (headColInfo.getParent() != null) {
			key += headColInfo.getParent().getName();
		}
		key += headColInfo.getName();
		return key;
	}

	private void setTableTotal(KDTable table) {
		int amountSumCol = -1;
		for (int i = 3; i < table.getColumnCount(); i++) {
			IColumn column = table.getColumn(i);
			NewListingColumnInfo col = (NewListingColumnInfo) this.columnMap.get(generateKey(table, column));
			if (col != null && (col.getHeadColumn().getProperty().equals(DescriptionEnum.AmountSum))) {
				amountSumCol = i;
				break;
			}
		}
		if (amountSumCol == -1) {
			return;
		}
		IRow row = (IRow) table.getRow(0);
		List aimRowList = new ArrayList();
		for (int j = 1; j < table.getRowCount(); j++) {
			IRow rowAfter = table.getRow(j);
			if (isLeafRow(table, j))
				aimRowList.add(rowAfter);
		}
		if (aimRowList.size() > 0) {
			BigDecimal sum = null;
			for (int rowIndex = 0; rowIndex < aimRowList.size(); rowIndex++) {
				IRow rowAdd = (IRow) aimRowList.get(rowIndex);
				Object value = rowAdd.getCell(amountSumCol).getValue();
				if (value != null) {
					if (sum == null) {
						sum = FMConstants.ZERO;
					}
					sum = sum.add(FDCNumberHelper.toBigDecimal(value));
				}
			}
			row.getCell(amountSumCol).setValue(sum);
		}
	}

	/**
	 * 功能描述：加载页面时,从数据库读取数据填充相应单元格-获取数据
	 */
	public void loadTableData(KDTable table, NewListingPageInfo page) {
		NewListingValueCollection valueCollection = null;
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("entry.head", page.getId().toString());
		filterInfo.appendFilterItem("quotingPrice", null);
		viewInfo.setFilter(filterInfo);
		viewInfo.getSelector().add(new SelectorItemInfo("*"));
		viewInfo.getSelector().add(new SelectorItemInfo("entry.*"));
		viewInfo.getSelector().add(new SelectorItemInfo("column.*"));
		try {
			valueCollection = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(viewInfo);

		} catch (BOSException e) {
			e.printStackTrace();
		}
		if (valueCollection == null)
			return;
		Map values = new HashMap();
		for (int i = 0; i < valueCollection.size(); i++) {
			NewListingValueInfo valueInfo = valueCollection.get(i);
			String entryId = valueInfo.getEntry().getId().toString();
			String colId = valueInfo.getColumn().getId().toString();
			String key = entryId + colId;
			values.put(key, valueInfo);
		}
		for (int colIndex = 0; colIndex < table.getColumnCount(); colIndex++) {
			IColumn column = table.getColumn(colIndex);
			NewListingColumnInfo colInfo = (NewListingColumnInfo) this.columnMap.get(generateKey(table, column));
			if (colInfo != null) {
				ColumnTypeEnum colType = colInfo.getHeadColumn().getColumnType();
				for (int rowIndex = 1; rowIndex < table.getRowCount(); rowIndex++) {
					IRow row = table.getRow(rowIndex);
					NewListingEntryInfo entry = (NewListingEntryInfo) row.getUserObject();
					NewListingValueInfo value = (NewListingValueInfo) values.get(entry.getId().toString()
							+ colInfo.getId().toString());
					InviteHelper.loadListingValue(row.getCell(colIndex), value, colType);
					// if (value != null) {
					// if (colType.equals(ColumnTypeEnum.Amount)) {
					// row.getCell(colIndex).setValue(value.getAmount());
					// } else if (colType.equals(ColumnTypeEnum.Date)) {
					// row.getCell(colIndex)
					// .setValue(value.getDateValue());
					// } else {
					// row.getCell(colIndex).setValue(value.getText());
					// }
					// }
				}
			}
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("inviteType.*");
		sic.add("orgUnit.*");
		sic.add("oriOrg.*");
		sic.add("curProject.*");
		sic.add("currency.*");
		sic.add("inviteProject.isRate");
		return sic;
	}

	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		NewListingInfo listing = (NewListingInfo) super.getValue(pk);
		if (listing.getInviteProject() != null) {
			isRate = listing.getInviteProject().isIsRate();
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("head.id", listing.getId().toString()));
		SelectorItemCollection sic = view.getSelector();
		sic.add("*");
		sic.add("pageHead.*");
		sic.add("headType.*");

		// view.getSelector().add("*");
		view.getSorter().add(new SorterItemInfo("seq"));
		NewListingPageCollection sheets = NewListingPageFactory.getRemoteInstance().getNewListingPageCollection(
				view);
		listing.getPages().clear();
		listing.getPages().addCollection(sheets);
		for (int i = 0; i < listing.getPages().size(); i++) {
			NewListingPageInfo sheet = listing.getPages().get(i);
			view = new EntityViewInfo();
			filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("page.id", sheet.getId().toString()));
			sic = view.getSelector();
			sic.add("*");
			sic.add("headColumn.*");
			sic.add("headColumn.headType.*");
			sic.add("headColumn.parent.*");
			view.getSorter().add(new SorterItemInfo("seq"));
			NewListingColumnCollection columns = null;
			columns = NewListingColumnFactory.getRemoteInstance().getNewListingColumnCollection(view);
			sheet.getColumns().clear();
			sheet.getColumns().addCollection(columns);

			view = new EntityViewInfo();
			filter = new FilterInfo();
			view.setFilter(filter);
			view.getSelector().add("*");
			view.getSelector().add("item.*");
			view.getSelector().add("values.*");
			filter.getFilterItems().add(new FilterItemInfo("head.id", sheet.getId().toString()));
			view.getSorter().add(new SorterItemInfo("seq"));
			NewListingEntryCollection entrys = null;
			entrys = NewListingEntryFactory.getRemoteInstance().getNewListingEntryCollection(view);
			sheet.getEntrys().clear();
			sheet.getEntrys().addCollection(entrys);
		}
		return listing;
	}

	public void actionRefPrice_actionPerformed(ActionEvent e) throws Exception {
		if (tabbedPnlTable.getTabCount() <= 0)
			return;

		KDTable table = (KDTable) this.tabbedPnlTable.getSelectedComponent();
		int rowIndex = table.getSelectManager().getActiveRowIndex();
		if (rowIndex < 0) {
			return;
		}
		if (!isLeafRow(table, rowIndex)) {
			return;
		}
		IRow row = table.getRow(rowIndex);
		if (row == null) {
			return;
		}
		if (row.getUserObject() instanceof NewListingEntryInfo) {
			NewListingPageInfo page = (NewListingPageInfo) table.getUserObject();
			Map map = new HashMap();
			NewListingColumnInfo itemCol = (NewListingColumnInfo) this.columnMap.get(this.generateKey(table, table.getColumn(SUB_NAME)));
			String itemName = (String) row.getCell(SUB_NAME).getValue();
			HeadTypeInfo headType = page.getHeadType();
			map.put(itemCol.getHeadColumn().getId().toString(), itemName);
			RefPriceInfo refInfo = RefPriceSelectUI.showWindows(this, headType.getId().toString(), map);
			if (refInfo != null) {
				Map refMap = new HashMap();
				RefPriceEntryCollection refEntrys = refInfo.getEntrys();
				BigDecimal priceSum = FDCNumberConstants.ZERO;
				for (int j = 0; j < refEntrys.size(); j++) {
					RefPriceEntryInfo refEntry = refEntrys.get(j);
					if (refEntry.getColumn().getProperty().equals(DescriptionEnum.TotalPrice)
							|| refEntry.getColumn().getProperty().equals(DescriptionEnum.TotalPriceSum)) {
						ColumnTypeEnum colType = refEntry.getColumn().getColumnType();
						if (colType.equals(ColumnTypeEnum.Amount)) {
							refMap.put(refEntry.getColumn().getId().toString(), refEntry.getValue());
							if (refEntry.getValue() != null) {
								priceSum = priceSum.add(refEntry.getValue());
							}
						} else if (colType.equals(ColumnTypeEnum.Date)) {
							refMap.put(refEntry.getColumn().getId().toString(), refEntry.getDateValue());
						} else {
							refMap.put(refEntry.getColumn().getId().toString(), refEntry.getText());
						}
					}
				}
				NewListingEntryInfo entry = (NewListingEntryInfo) row.getUserObject();

				int priceCount = 0;
				for (int i = 0; i < table.getColumnCount(); i++) {
					IColumn column = table.getColumn(i);
					NewListingColumnInfo colInfo = (NewListingColumnInfo) this.columnMap.get(generateKey(table, column));
					if (colInfo != null && colInfo.isIsQuoting()) {
						if (colInfo.getHeadColumn().getProperty().equals(DescriptionEnum.TotalPrice)) {
							priceCount++;
						}
					}
				}

				for (int i = 0; i < table.getColumnCount(); i++) {
					IColumn column = table.getColumn(i);
					NewListingColumnInfo colInfo = (NewListingColumnInfo) this.columnMap.get(generateKey(table, column));
					if (colInfo != null && colInfo.isIsQuoting()) {
						Object value = refMap.get(colInfo.getHeadColumn().getId().toString());
						if (entry.isIsCompose()) {
							if (priceCount != 0) {
								if (colInfo.getHeadColumn().getProperty().equals(DescriptionEnum.TotalPrice)) {
									row.getCell(i).setValue(value);
								}
							} else {
								if (colInfo.getHeadColumn().getProperty().equals(DescriptionEnum.TotalPriceSum)) {
									row.getCell(i).setValue(value);
								}
							}
						} else {
							if (colInfo.getHeadColumn().getProperty().equals(DescriptionEnum.TotalPriceSum)) {
								row.getCell(i).setValue(value);
							}
						}
						refMap.remove(colInfo.getHeadColumn().getId().toString());
					}
				}
				if (entry.isIsCompose()) {
					Collection values = refMap.values();
					boolean isCom = true;
					for (Iterator iter = values.iterator(); iter.hasNext();) {
						Object element = iter.next();
						if (element != null && element instanceof BigDecimal) {
							BigDecimal value = (BigDecimal) element;
							if (value.compareTo(FDCNumberConstants.ZERO) > 0) {
								isCom = false;
							}
						}
					}
					if (!isCom) {
						MsgBox.showInfo("表头类型有价格明细列的值无法在此清单上找到匹配列,所以小计金额将自动减少!");
					}
				}
			}
			this.setUnionData(table);
		}
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		actionSave_actionPerformed(e);
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		if (tabbedPnlTable.getTabCount() <= 0) {
			logger.warn("listing RefPrice has nothing data!");
			return;
		}

		verifyData();
		for (int i = 0; i < this.editData.getPages().size(); i++) {
			StringBuffer sql = new StringBuffer("delete t_inv_newlistingvalue where fColumnid in ('null'");
			NewListingPageInfo page = this.editData.getPages().get(i);
			for (int j = 0; j < page.getColumns().size(); j++) {
				NewListingColumnInfo col = page.getColumns().get(j);
				if (col.isIsQuoting() || col.getHeadColumn().getProperty().equals(DescriptionEnum.AmountSum)) {
					sql.append(",'");
					sql.append(col.getId().toString());
					sql.append("'");
				}
			}
			sql.append(") and fentryid in ('null'");
			for (int j = 0; j < page.getEntrys().size(); j++) {
				NewListingEntryInfo entry = page.getEntrys().get(j);
				sql.append(",'");
				sql.append(entry.getId().toString());
				sql.append("'");
			}
			sql.append(") and fquotingpriceid is null");
			FDCSQLBuilder sqlBuilder = new FDCSQLBuilder();
			sqlBuilder.appendSql(sql.toString());
			sqlBuilder.execute();
		}

		for (int i = 0; i < tables.size(); i++) {
			KDTable table = (KDTable) tables.get(i);
			for (int j = 0; j < table.getRowCount(); j++) {
				IRow row = table.getRow(j);
				if (row.getUserObject() instanceof NewListingEntryInfo) {
					NewListingEntryInfo entryInfo = (NewListingEntryInfo) row.getUserObject();
					int colCount = table.getColumnCount();
					for (int k = 0; k < colCount; k++) {
						IColumn column = table.getColumn(k);
						if (this.columnMap.get(generateKey(table, column)) instanceof NewListingColumnInfo) {
							NewListingColumnInfo columnInfo = (NewListingColumnInfo) this.columnMap.get(generateKey(table, column));
							DescriptionEnum property = columnInfo.getHeadColumn().getProperty();
							if (columnInfo.isIsQuoting() || property.equals(DescriptionEnum.AmountSum)) {
								if (property.equals(DescriptionEnum.TotalPrice)
										|| property.equals(DescriptionEnum.TotalPriceSum)
										|| property.equals(DescriptionEnum.Personal)
										|| property.equals(DescriptionEnum.AmountSum)) {
									NewListingValueInfo valueInfo = new NewListingValueInfo();
									valueInfo.setColumn(columnInfo);
									valueInfo.setEntry(entryInfo);
									Object cellVal = row.getCell(k).getValue();
									if (cellVal != null) {
										ColumnTypeEnum colType = columnInfo.getHeadColumn().getColumnType();
										InviteHelper.storeListingValue(valueInfo, cellVal, colType);
										// if (colType
										// .equals(ColumnTypeEnum.Amount)) {
										// valueInfo.setAmount(FDCHelper
										// .toBigDecimal(cellVal));
										// } else if (colType
										// .equals(ColumnTypeEnum.Date)) {
										// valueInfo
										// .setDateValue((Date) cellVal);
										// } else {
										// valueInfo.setText((String) cellVal);
										// }
									}
									NewListingValueFactory.getRemoteInstance().submit(valueInfo);
								}
							}
						}
					}
				}
			}
		}

		KDTable table = (KDTable) tabbedPnlTable.getComponentAt(0);
		int count = table.getRowCount();
		BigDecimal sum = (BigDecimal) table.getRow(count - 1).getCell("amount").getValue();
		this.editData.setTotalAmount(sum);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("totalAmount");
		NewListingFactory.getRemoteInstance().updatePartial(this.editData, sels);
		setMessageText("参考价保存成功");
		isModify = false;
		this.showMessage();
	}

	public void verifyData() {
		boolean isErr = false;
		for (int i = 1; i < this.tabbedPnlTable.getTabCount(); i++) {
			KDTable table = (KDTable) this.tabbedPnlTable.getComponentAt(i);
			// 校验同名称价格是否相同
			Map sameItemMap = new HashMap();
			for (int j = 1; j < table.getRowCount(); j++) {
				IRow row = table.getRow(j);
				ICell nameCell = row.getCell(this.SUB_NAME);
				// ICell unitCell = row.getCell("单位");
				String name = null;// , unit = null;
				if (nameCell.getValue() instanceof String) {
					name = (String) nameCell.getValue();
				} else {
					if (nameCell.getValue() != null) {
						name = ((ListingItemInfo) nameCell.getValue()).getName();
					}
				}

				int colCount = table.getColumnCount();
				String key = name;
				if (sameItemMap.containsKey(key)) {
					IRow fRow = (IRow) sameItemMap.get(key);
					for (int k = 0; k < colCount; k++) {
						NewListingColumnInfo colInfo = (NewListingColumnInfo) this.columnMap.get(this.generateKey(table, table.getColumn(k)));
						ICell cell = row.getCell(k);
						if (!cell.getStyleAttributes().isLocked()) {
							cell.getStyleAttributes().setBackground(REQUIREDCOLOR);
							if (colInfo.getHeadColumn().getColumnType().equals(ColumnTypeEnum.Amount)) {
								BigDecimal fValue = FDCNumberHelper.toBigDecimal(fRow.getCell(k).getValue());
								BigDecimal value = FDCNumberHelper.toBigDecimal((BigDecimal) row.getCell(k)
										.getValue());
								if (value.compareTo(fValue) != 0) {
									cell.getStyleAttributes().setBackground(ERR_COLOR);
									isErr = true;
								}
							} 
						}
					}

				} else {
					sameItemMap.put(key, row);
				}
			}
		}
		if (isErr) {
			MsgBox.showInfo("存在子目名称相同的行报价不同!");// +单位
			this.abort();
		}
	}

	public void actionExportRefPrice_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportRefPrice_actionPerformed(e);
	}

	protected void attachListeners() {
	}

	protected void detachListeners() {
	}

	protected KDTable getDetailTable() {
		return null;
	}
}