/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

/**
 * @author 肖飙彪
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.QueryInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.commonquery.IQuerySolutionFacade;
import com.kingdee.eas.base.commonquery.QuerySolutionFacadeFactory;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.Util;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.invite.ColumnTypeEnum;
import com.kingdee.eas.fdc.invite.DescriptionEnum;
import com.kingdee.eas.fdc.invite.HeadColumnInfo;
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
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class InviteListingItemFilterUI extends
		AbstractInviteListingItemFilterUI {

	private CommonQueryDialog dialog = null;

	MetaDataPK queryPK = new MetaDataPK("com.kingdee.eas.fdc.invite.app",
			"NewListingEntryQuery");

	private List tables = null;

	private NewListingInfo listingInfo;

	/**
	 * output class constructor
	 */
	public InviteListingItemFilterUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		getInviteListing();
		this.actionFilter_actionPerformed(null);
		this.actionExportExcel.setVisible(false);
	}

	private void loadPages(EntityViewInfo view) throws BOSException {
		tabbedPnlTable.removeAll();
		tables = new ArrayList();
		NewListingPageCollection coll = listingInfo.getPages();
		int size = coll.size();
		for (int i = 0; i < size; i++) {
			NewListingPageInfo page = (NewListingPageInfo) coll.get(i);
			PageHeadInfo pageHead = page.getPageHead();
			KDTable table = new KDTable();
			this.tables.add(table);
			table.setName(pageHead.getNumber());
			initTable(table, page, view);
			setUnionData(table);
			this.tabbedPnlTable.add(table, pageHead.getName());
		}
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
			if (col.getUserObject() != null
					&& col.getUserObject() instanceof NewListingColumnInfo) {
				NewListingColumnInfo column = (NewListingColumnInfo) col
						.getUserObject();
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
		for (int i = 0; i < rowCount; i++) {
			if (isLeafRow(table, i)) {
				if (projectAmtIndex < projectAmtSum) {
					TableScriptHelper.setTableRowSumFormalu(table.getCell(i,
							projectAmtSum), projectAmtSum - projectAmtIndex);
				}
				if (totalPriceIndex < totalPriceSum) {
					if (table.getCell(i, "isCompose").getValue() != null) {
						boolean isCompose = ((Boolean) table.getCell(i,
								"isCompose").getValue()).booleanValue();
						if (isCompose) {
							TableScriptHelper.setTableRowSumFormalu(table
									.getCell(i, totalPriceSum), totalPriceSum
									- totalPriceIndex);
						}
					}
				}
				for (int j = 0; j <= projectAmtSum - projectAmtIndex; j++) {
					TableScriptHelper.setTableMultiplyFormalu(table.getCell(i,
							amountIndex + j), table.getCell(i, projectAmtIndex
							+ j), table.getCell(i, totalPriceSum));
				}
			}
		}
	}

	public boolean isLeafRow(KDTable table, int rowIndex) {
		if (rowIndex + 1 == table.getRowCount()) {
			return true;
		}
		int rowLevel = table.getRow(rowIndex).getTreeLevel();
		int nextLevel = table.getRow(rowIndex + 1).getTreeLevel();
		if (nextLevel <= rowLevel) {
			return true;
		}
		return false;
	}

	/**
	 * 设置汇总项
	 *
	 * @param table
	 * @param amountColumns
	 */
	public void setUnionData(KDTable table) {
		setTableScript(table);
		List colList = new ArrayList();
		for (int i = 3; i < table.getColumnCount(); i++) {
			IColumn column = table.getColumn(i);
			if (column.getUserObject() != null
					&& column.getUserObject() instanceof NewListingColumnInfo) {
				NewListingColumnInfo col = (NewListingColumnInfo) column
						.getUserObject();
				if (col != null
						&& (col.getHeadColumn().getProperty()
								.equals(DescriptionEnum.AmountSum))) {
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
							sum = sum.add(FDCHelper.toBigDecimal(value));
						}
					}
					row.getCell(colIndex).setValue(sum);
				}
			}
		}
		table.getScriptManager().runAll();
//		for (int i = 0; i < table.getRowCount(); i++) {
//			if (!isLeafRow(table, i)) {
//				table.getRow(i).getStyleAttributes().setBackground(
//						new Color(0xF0EDD9));
//			} else {
//				table.getRow(i).getStyleAttributes().setBackground(Color.WHITE);
//			}
//		}
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
			valueCollection = NewListingValueFactory.getRemoteInstance()
					.getNewListingValueCollection(viewInfo);

		} catch (BOSException e) {
			e.printStackTrace();
		}
		Map values = new HashMap();
		if(valueCollection==null)
			return;
		for (int i = 0; i < valueCollection.size(); i++) {
			NewListingValueInfo valueInfo = valueCollection.get(i);
			String entryId = valueInfo.getEntry().getId().toString();
			String colId = valueInfo.getColumn().getId().toString();
			String key = entryId + colId;
			values.put(key, valueInfo);
		}
		for (int colIndex = 0; colIndex < table.getColumnCount(); colIndex++) {
			IColumn column = table.getColumn(colIndex);
			NewListingColumnInfo colInfo = (NewListingColumnInfo) column
					.getUserObject();
			if (colInfo != null
					&& !colInfo.getHeadColumn().getName().equals("子目名称")) {
				ColumnTypeEnum colType = colInfo.getHeadColumn().getColumnType();
				for (int rowIndex = 1; rowIndex < table.getRowCount(); rowIndex++) {
					IRow row = table.getRow(rowIndex);
					NewListingEntryInfo entry = (NewListingEntryInfo) row
							.getUserObject();
					NewListingValueInfo value = (NewListingValueInfo) values
							.get(entry.getId().toString()
									+ colInfo.getId().toString());
					InviteHelper.loadListingValue(row.getCell(colIndex), value,
							colType);
//					if (value != null) {
//						if (colType.equals(
//								ColumnTypeEnum.Amount)) {
//							row.getCell(colIndex).setValue(value.getAmount());
//						} else if (colType.equals(
//								ColumnTypeEnum.Date)) {
//							row.getCell(colIndex).setValue(value.getDateValue());
//						} else {
//							row.getCell(colIndex).setValue(value.getText());
//						}
//					}
				}
			}
		}
	}

	private void initTable(final KDTable table, NewListingPageInfo page,
			EntityViewInfo view) throws BOSException {
		tHelper.setCanSetTable(true);
		table.setUserObject(page);
		setTableColumn(table, page);
		if (page.getColumns().size() > 0) {
			filterItemBySheet(view, table, page);
		}
		setTableLock(table);
		table.getTreeColumn().setDepth(getMaxLevel(table) + 1);
	}

	private void setTableLock(final KDTable table) {
		table.getStyleAttributes().setLocked(true);
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
				HeadColumnInfo infoColumn = collColumn.get(j).getHeadColumn();
				if (infoColumn != null) {
					IColumn column = table.addColumn();
					column.setKey(infoColumn.getName());
					column.setUserObject(collColumn.get(j));
					row.getCell(j + baseCount).setValue(infoColumn.getName());
					if (infoColumn.getColumnType()
							.equals(ColumnTypeEnum.Amount)) {
						column.getStyleAttributes().setNumberFormat(
								"#,##0.00;-#,##0.00");
						column.getStyleAttributes().setHorizontalAlign(
								HorizontalAlignment.RIGHT);
						// FDCHelper.formatTableNumber(table,
						// infoColumn.getName());
					} else if (infoColumn.getColumnType().equals(
							ColumnTypeEnum.Date)) {
						column.getStyleAttributes().setNumberFormat(
								"%L{yyyy-MM-dd}t");
					} else if (infoColumn.getColumnType().equals(
							ColumnTypeEnum.String)) {
						column.getStyleAttributes().setWrapText(true);
					}
//					if (infoColumn.getProperty().equals(
//							DescriptionEnum.TotalPrice)
//							|| infoColumn.getProperty().equals(
//									DescriptionEnum.TotalPriceSum)) {
//						// column.getStyleAttributes().setLocked(true);
//					}
					if ((infoColumn.getParent() != null)) {
						parentRow.getCell(j + baseCount).setValue(
								infoColumn.getParent().getName());
						if (j < sizeColumn - 1) {
							HeadColumnInfo infoNext = collColumn.get(j + 1)
									.getHeadColumn();
							if (infoNext.getParent() != null
									&& infoColumn.getParent().equals(
											infoNext.getParent())) {
								count++;
							} else {
								if (count == 0) {
									mm.mergeBlock(0, j + baseCount, 1, j
											+ baseCount,
											KDTMergeManager.SPECIFY_MERGE);
								} else {
									mm.mergeBlock(0, j - count + baseCount, 0,
											j + baseCount,
											KDTMergeManager.DATA_UNIFY);
									count = 0;
								}
							}
						} else {
							mm.mergeBlock(0, j - count + baseCount, 0, j
									+ baseCount, KDTMergeManager.DATA_UNIFY);
							count = 0;
						}
					} else {
						mm.mergeBlock(0, j + baseCount, 1, j + baseCount);
					}
				}
			}
		}
		table.getColumn("子目名称").setWidth(400);
	}

	private int getMaxLevel(KDTable table) {
		int count = table.getRowCount();
		int levelthis;
		int levelmax = 0;
		for (int i = 1; i < count; i++) {
			levelthis = Integer.parseInt(table.getCell(i, "level").getValue()
					.toString());
			if (levelthis > levelmax) {
				levelmax = levelthis;
			}
		}
		return levelmax;
	}

	/**
	 * 功能：初始化变量
	 *
	 * @throws EASBizException
	 * @throws BOSException
	 */

	public void getInviteListing() throws EASBizException, BOSException {
		String inviteListingId = (String) getUIContext().get("inviteListingId");
		listingInfo = NewListingFactory.getRemoteInstance().getNewListingInfo(
				new ObjectUuidPK(inviteListingId));
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("head.id", inviteListingId));
		SelectorItemCollection sic = view.getSelector();
		sic.add("*");
		sic.add("pageHead.*");
		sic.add("headType.*");
		sic.add("columns.*");
		sic.add("entrys.*");
		view.getSorter().add(new SorterItemInfo("seq"));
		NewListingPageCollection sheets = NewListingPageFactory
				.getRemoteInstance().getNewListingPageCollection(view);
		listingInfo.getPages().clear();
		listingInfo.getPages().addCollection(sheets);
		for (int i = 0; i < listingInfo.getPages().size(); i++) {
			NewListingPageInfo sheet = listingInfo.getPages().get(i);
			view = new EntityViewInfo();
			filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(
					new FilterItemInfo("page.id", sheet.getId().toString()));
			sic = view.getSelector();
			sic.add("*");
			sic.add("headColumn.*");
			sic.add("headColumn.parent.*");
			view.getSorter().add(new SorterItemInfo("seq"));
			NewListingColumnCollection columns = null;
			columns = NewListingColumnFactory.getRemoteInstance()
					.getNewListingColumnCollection(view);
			sheet.getColumns().clear();
			sheet.getColumns().addCollection(columns);

			view = new EntityViewInfo();
			filter = new FilterInfo();
			view.setFilter(filter);
			view.getSelector().add("*");
			view.getSelector().add("item.*");
			view.getSelector().add("values.*");
			filter.getFilterItems().add(
					new FilterItemInfo("head.id", sheet.getId().toString()));
			view.getSorter().add(new SorterItemInfo("seq"));
			NewListingEntryCollection entrys = null;
			entrys = NewListingEntryFactory.getRemoteInstance()
					.getNewListingEntryCollection(view);
			sheet.getEntrys().clear();
			sheet.getEntrys().addCollection(entrys);
		}
	}

	public void actionFilter_actionPerformed(ActionEvent e) throws Exception {
		boolean isFirst = false;
		if (dialog == null) {
			IQuerySolutionFacade iQuery = QuerySolutionFacadeFactory
					.getRemoteInstance();
			IMetaDataLoader loader = MetaDataLoaderFactory
					.getRemoteMetaDataLoader();
			QueryInfo query = loader.getQuery(queryPK);
			QuerySolutionInfo defaultSolution = iQuery.getDefaultSolution(this
					.getClass().getName(), query.getFullName());
			if (defaultSolution == null) {
				dialog = initCommonQueryDialog(null);
			} else {
				if (defaultSolution.getEntityViewInfo() != null) {
					EntityViewInfo view = Util
							.getInnerFilterInfo(defaultSolution);
					dialog = initCommonQueryDialog(view);
				} else {
					dialog = initCommonQueryDialog(null);
				}
			}
			isFirst = true;
		}
		if (!isFirst) {
			boolean b = dialog.show();
			if (b) {
				loadPages(dialog.getEntityViewInfoResult());
			}
		} else {
			IQuerySolutionFacade iQuery = QuerySolutionFacadeFactory
					.getRemoteInstance();
			IMetaDataLoader loader = MetaDataLoaderFactory
					.getRemoteMetaDataLoader();
			QueryInfo query = loader.getQuery(queryPK);
			QuerySolutionInfo defaultSolution = iQuery.getDefaultSolution(this
					.getClass().getName(), query.getFullName());
			if (defaultSolution == null) {
				boolean b = dialog.show();
				if (b) {
					loadPages(dialog.getEntityViewInfoResult());
				} else {
					this.abort();
					this.destroyWindow();
				}
			} else {
				if (defaultSolution.getEntityViewInfo() != null) {
					EntityViewInfo view = Util
							.getInnerFilterInfo(defaultSolution);
					loadPages(view);
				} else {
					EntityViewInfo view = new EntityViewInfo();
					view.setFilter(new FilterInfo());
					loadPages(view);
				}
			}
		}
	}

	protected CommonQueryDialog initCommonQueryDialog(EntityViewInfo view) {
		CommonQueryDialog dialog = new CommonQueryDialog();
		if (this.getUIWindow() == null) {
			dialog.setOwner((Component) getUIContext().get(
					UIContext.OWNERWINDOW));
		} else {
			dialog.setOwner(this);
		}
		dialog.setUiObject(this);
		dialog.setParentUIClassName(this.getClass().getName());
		dialog.setQueryObjectPK(queryPK);
		dialog.setTitle(this.getUITitle()
				+ " - "
				+ EASResource.getString(FrameWorkClientUtils.strResource
						+ "Query_Filter"));
		if (view != null) {
			dialog.setEntityViewInfo(view);
		}
		return dialog;
	}

	public void filterItem(EntityViewInfo view) throws BOSException {
		for (int i = 0, size = tables.size(); i < size; i++) {
			KDTable table = (KDTable) this.tables.get(i);
			NewListingPageInfo info = listingInfo.getPages().get(i);
			if (info.getColumns().size() > 0) {
				filterItemBySheet(view, table, info);
				this.setUnionData(table);
				table.getTreeColumn().setDepth(getMaxLevel(table) + 1);
			}
		}
	}

	public void filterItemBySheet(EntityViewInfo view, KDTable table,
			NewListingPageInfo sheetInfo) throws BOSException {
		// 先清除
		table.removeRows();

		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo newFilterInfo = (FilterInfo) view.getFilter().clone();
		for (int i = 0; i < newFilterInfo.getFilterItems().size(); i++) {
			FilterItemInfo filterItemInfo = newFilterInfo.getFilterItems().get(
					i);
			if(filterItemInfo.getCompareType().equals(CompareType.IS)
					&&filterItemInfo.getCompareValue().equals("EMPTY")){
				filterItemInfo.setCompareType(CompareType.EQUALS);
				filterItemInfo.setCompareValue(null);
			}
			else if(filterItemInfo.getCompareType().equals(CompareType.ISNOT)
					&&filterItemInfo.getCompareValue().equals("EMPTY")){
				filterItemInfo.setCompareType(CompareType.NOTEQUALS);
				filterItemInfo.setCompareValue(null);
			}
			if ("level".equals(filterItemInfo.get("propertyName"))) {
				if (filterItemInfo.get("compareValue") == null) {
					filterItemInfo.put("compareValue", "0");
				}
			}
		}
		newFilterInfo.appendFilterItem("head.id", sheetInfo.getId().toString());
		if (newFilterInfo.getFilterItems().size() > 1) {
			newFilterInfo.setMaskString("(" + newFilterInfo.getMaskString()
					+ ")" + " AND #"
					+ (newFilterInfo.getFilterItems().size() - 1));
		} else {
			newFilterInfo.setMaskString("#"
					+ (newFilterInfo.getFilterItems().size() - 1));
		}
		viewInfo.setFilter(newFilterInfo);
		viewInfo.getSelector().add("*");
		viewInfo.getSelector().add("values.*");
		viewInfo.getSelector().add("values.column.*");
		viewInfo.getSelector().add("item.*");
		viewInfo.getSorter().add(new SorterItemInfo("seq"));
		NewListingEntryCollection entryCollection = NewListingEntryFactory
				.getRemoteInstance().getNewListingEntryCollection(viewInfo);
		for (int i = 0; i < entryCollection.size(); i++) {
			NewListingEntryInfo entryInfo = entryCollection.get(i);
			NewListingValueCollection valueCollection = entryInfo.getValues();
			HashMap map = new HashMap();// columnName
			for (int m = 0; m < valueCollection.size(); m++) {
				NewListingValueInfo valueInfo = valueCollection.get(m);
				if (valueInfo.getQuotingPrice() == null) {
					NewListingColumnInfo columnInfo = valueInfo.getColumn();
					map.put(columnInfo.getName(), valueInfo);
				}
			}
			entryInfo.put("valueMap", map);
			filterItemByEntry(table, entryInfo);
		}
	}

	public void filterItemByEntry(KDTable table, NewListingEntryInfo entryInfo) {
		IRow dataRow = table.addRow();
		dataRow.setUserObject(entryInfo);
		if (entryInfo.getItem() != null) {
			dataRow.getCell("子目名称").setValue(entryInfo.getItem().getName());
		} else if (entryInfo.getItemName() != null) {
			dataRow.getCell("子目名称").setValue(entryInfo.getItemName());
		}
		dataRow.getCell("level").setValue(new Integer(entryInfo.getLevel()));
		dataRow.getCell("isKey").setValue(Boolean.valueOf(entryInfo.isIsKey()));
		dataRow.getCell("isCompose").setValue(
				Boolean.valueOf(entryInfo.isIsCompose()));
		dataRow.setTreeLevel(entryInfo.getLevel());
		NewListingValueCollection valueCollection = null;
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("entry.id", entryInfo.getId().toString());
		filterInfo.appendFilterItem("quotingPrice", null);
		viewInfo.setFilter(filterInfo);
		viewInfo.getSelector().add(new SelectorItemInfo("*"));
		viewInfo.getSelector().add(new SelectorItemInfo("entry.*"));
		viewInfo.getSelector().add(new SelectorItemInfo("column.*"));
		try {
			valueCollection = NewListingValueFactory.getRemoteInstance()
					.getNewListingValueCollection(viewInfo);

		} catch (BOSException e) {
			e.printStackTrace();
		}
		Map values = new HashMap();
		if(valueCollection==null)
			return;
		for (int i = 0; i < valueCollection.size(); i++) {
			NewListingValueInfo valueInfo = valueCollection.get(i);
			String entryId = valueInfo.getEntry().getId().toString();
			String colId = valueInfo.getColumn().getId().toString();
			String key = entryId + colId;
			values.put(key, valueInfo);
		}
		for (int colIndex = 0; colIndex < table.getColumnCount(); colIndex++) {
			IColumn column = table.getColumn(colIndex);
			NewListingColumnInfo colInfo = (NewListingColumnInfo) column
					.getUserObject();
			if (colInfo != null
					&& !colInfo.getHeadColumn().getName().equals("子目名称")) {
				ColumnTypeEnum colType = colInfo.getHeadColumn()
						.getColumnType();

				NewListingValueInfo value = (NewListingValueInfo) values
						.get(entryInfo.getId().toString()
								+ colInfo.getId().toString());
				InviteHelper.loadListingValue(dataRow.getCell(colIndex), value,
						colType);
//				if (value != null) {
//					if (colType.equals(ColumnTypeEnum.Amount)) {
//						dataRow.getCell(colIndex).setValue(value.getAmount());
//					} else if (colType.equals(ColumnTypeEnum.Date)) {
//						dataRow.getCell(colIndex)
//								.setValue(value.getDateValue());
//					} else {
//						dataRow.getCell(colIndex).setValue(value.getText());
//					}
//				}

			}
		}
	}

	public void actionExportExcel_actionPerformed(ActionEvent e)
			throws Exception {
		// FDCExcelImportExportUtil.exportExcel(this, book);
		// KDTable table=(KDTable)this.tabbedPnlTable.getSelectedComponent();
		// table.
	}
}