/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.invite.AssessSetting;
import com.kingdee.eas.fdc.invite.DescriptionEnum;
import com.kingdee.eas.fdc.invite.HeadColumnInfo;
import com.kingdee.eas.fdc.invite.NewListingEntryCollection;
import com.kingdee.eas.fdc.invite.NewListingEntryFactory;
import com.kingdee.eas.fdc.invite.NewListingEntryInfo;
import com.kingdee.eas.fdc.invite.NewListingFactory;
import com.kingdee.eas.fdc.invite.NewListingInfo;
import com.kingdee.eas.fdc.invite.NewListingPageCollection;
import com.kingdee.eas.fdc.invite.NewListingPageInfo;
import com.kingdee.eas.fdc.invite.NewListingValueCollection;
import com.kingdee.eas.fdc.invite.NewListingValueFactory;
import com.kingdee.eas.fdc.invite.NewListingValueInfo;
import com.kingdee.eas.fdc.invite.NewQuotingPriceCollection;
import com.kingdee.eas.fdc.invite.NewQuotingPriceFactory;
import com.kingdee.eas.fdc.invite.NewQuotingPriceInfo;
import com.kingdee.eas.fdc.invite.QuoteStandEnum;
import com.kingdee.eas.fdc.invite.QuotingPriceTypeEnum;

public class QuotingPriceOverRangeUI extends AbstractQuotingPriceOverRangeUI {
	/**
	 *
	 */
	private static final long serialVersionUID = -3526375754504501541L;

	private static final Logger logger = CoreUIObject.getLogger(QuotingPriceOverRangeUI.class);

	List tables = new ArrayList(10);

	KDTabbedPane tablePane = kDTabbedPane1;

	private NewListingInfo listingInfo;

	private NewQuotingPriceInfo priceContentInfo;

	private AssessSetting setting;

	private static BigDecimal zero = FDCHelper.ZERO;

	NewQuotingPriceCollection quotingPrices = null;

	Map sheetsMap = new HashMap(100);

	Map standPriceMap;// 基准价集合,以entryID为key,amount为value

	Map unitMap;// 单位

	public QuotingPriceOverRangeUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		init();
		super.printAction.setEnabled(true);
		super.prePrintAction.setEnabled(true);
		super.kDWorkButton2.setEnabled(true);
	}

	private void init() throws Exception {
		tablePane.removeAll();
		tables.clear();
		sheetsMap.clear();
		// standPriceMap.clear();
		// unitMap.clear();
		initPrivateData();
		initSheet();
		initOverRangeData();
		// this.repaint();
		InviteHelper.setAotuHeight(tablePane);
	}

	/**
	 * 功能：初始化变量
	 *
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws SQLException
	 */

	public void initPrivateData() throws EASBizException, BOSException, SQLException {
		String quotingPriceContentId = (String) getUIContext().get("quotingPriceContentId");
		SelectorItemCollection selectorItemCollection = new SelectorItemCollection();
		selectorItemCollection.add(new SelectorItemInfo("*"));
		selectorItemCollection.add(new SelectorItemInfo("supplier.*"));
		priceContentInfo = NewQuotingPriceFactory.getRemoteInstance().getNewQuotingPriceInfo(new ObjectUuidPK(quotingPriceContentId), selectorItemCollection);

		String inviteListingId = (String) getUIContext().get("inviteListingId");
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("pages.*"));
		sic.add(new SelectorItemInfo("pages.pageHead.*"));

		listingInfo = NewListingFactory.getRemoteInstance().getNewListingInfo(new ObjectUuidPK(inviteListingId), sic);
		setting = new AssessSetting(listingInfo);
		quotingPrices = getQuotingPrices();
		standPriceMap = getEntryCollection(setting.getStand());
		// unitMap = getUnitCollection();
	}

	/**
	 * 获取该清单的所有投标
	 *
	 * @return
	 * @throws BOSException
	 */
	private NewQuotingPriceCollection getQuotingPrices() throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		view.getSelector().add("*");
		view.getSelector().add("supplier.*");
		String inviteListingId = (String) this.getUIContext().get("inviteListingId");
		filter.getFilterItems().add(new FilterItemInfo("listing.id", inviteListingId));
		filter.getFilterItems().add(new FilterItemInfo("hasEffected", Boolean.TRUE));
		return NewQuotingPriceFactory.getRemoteInstance().getNewQuotingPriceCollection(view);
	}

	/**
	 * 功能：初始化页签头
	 *
	 * @throws EASBizException
	 * @throws BOSException
	 * @throws SQLException
	 */
	public void initSheet() throws BOSException, SQLException {
		NewListingPageCollection pages = listingInfo.getPages();
		for (int i = 0; i < pages.size(); i++) {

			NewListingPageInfo sheetInfo = pages.get(i);
			if (!sheetInfo.getPageHead().getName().equals(FDCExcelImportExportUtil.sheetName_ProjectDescription)
					&& !sheetInfo.getPageHead().getName().equals(FDCExcelImportExportUtil.sheetName_ProjectGather)) {
				// 取得各叶签的子目基准价集合
				KDTable table = new KDTable();
				tables.add(table);
				table.setName(sheetInfo.getPageHead().getName());
				final int row = 0;
				int col = 0;

				table.addHeadRow();
				table.addColumn().setKey("1");
				table.getHeadRow(row).getCell(col++).setValue("序号");
				table.addColumn().setKey("2");
				table.getColumn("2").getStyleAttributes().setHided(true);
				table.getHeadRow(row).getCell(col++).setValue("子目编码");
				IColumn c = table.addColumn();
				c.setKey("bt");
				c.getStyleAttributes().setWrapText(true);
				table.getHeadRow(row).getCell(col++).setValue("子目名称");
				table.addColumn().setKey("3");
				table.getHeadRow(row).getCell(col++).setValue("单位");
				table.addColumn().setKey("4");
				table.getHeadRow(row).getCell(col++).setValue("工程量");// 工程量小计
				table.addColumn().setKey("0");
				table.getHeadRow(row).getCell(col++).setValue("异常部分");
				table.addColumn().setKey("5");
				table.getHeadRow(row).getCell(col++).setValue("基准单价（元）");
				table.addColumn().setKey("6");
				table.getHeadRow(row).getCell(col++).setValue("投标单价（元）");
				table.addColumn().setKey("7");
				table.getHeadRow(row).getCell(col++).setValue("超出范围");
				table.addColumn().setKey("8");
				table.getHeadRow(row).getCell(col++).setValue("超出金额（元）");
				table.addColumn().setKey("9");
				table.getHeadRow(row).getCell(col++).setValue("100%基准");

				table.checkParsed();
				sheetsMap.put(table.getName(), table);
				tablePane.add(table, table.getName() + "(" + priceContentInfo.getSupplier().getName() + ")");
				table.getStyleAttributes().setLocked(true);
				FDCHelper.formatTableNumber(table, new int[] { 4, 6, 7, 8, 9 });

				// for (int j = 0; j < table.getColumnCount(); j++) {
				// table.getColumn(j).setWidth(120);
				//
				// }
			}
		}

	}

	/**
	 * 获取子目的基准价集合
	 *
	 * @param page
	 * @param columnId
	 * @param stand
	 * @return
	 * @throws BOSException
	 * @throws SQLException
	 */
	private Map getEntryCollection(QuoteStandEnum stand) throws BOSException, SQLException {
		// String sql = "";
		// // 查询综合单价小计
		Map map = new HashMap(100);

		// 出最低标价
		if (setting.getStand() == QuoteStandEnum.BidderMinPrice) {
			for (int i = 0; i < listingInfo.getPages().size(); i++) {
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				SelectorItemCollection selector = view.getSelector();
				view.setFilter(filter);

				NewListingPageInfo page = listingInfo.getPages().get(i);
				filter.getFilterItems().add(new FilterItemInfo("head.id", page.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
				selector.add("id");
				view.getSorter().add(new SorterItemInfo("seq"));
				NewListingEntryCollection entryCollection = NewListingEntryFactory.getRemoteInstance().getNewListingEntryCollection(view);

				for (int j = 0; j < entryCollection.size(); j++) {
					NewListingEntryInfo entry = entryCollection.get(j);

					view = new EntityViewInfo();
					filter = new FilterInfo();
					selector = view.getSelector();
					view.setFilter(filter);
					filter.getFilterItems().add(new FilterItemInfo("entry.id", entry.getId().toString()));
					filter.appendFilterItem("type", QuotingPriceTypeEnum.modify);
					filter.appendFilterItem("column.headColumn.property", DescriptionEnum.TOTALPRICESUM_VALUE);
					selector.add("*");
					NewListingValueCollection valueCollection = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(view);
					BigDecimal temp = null;
					for (int k = 0; k < valueCollection.size(); k++) {
						NewListingValueInfo value = valueCollection.get(k);
						if (value.getAmount() != null) {
							if (temp == null || temp.floatValue() > value.getAmount().floatValue())
								temp = value.getAmount();
						}

					}
					map.put(entry.getId().toString(), temp != null ? temp : FDCHelper._ONE);
				}
			}

			// 投标报价平均值
		} else if (setting.getStand() == QuoteStandEnum.QuotingPriceAvg) {
			for (int i = 0; i < listingInfo.getPages().size(); i++) {
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				SelectorItemCollection selector = view.getSelector();
				view.setFilter(filter);

				NewListingPageInfo page = listingInfo.getPages().get(i);
				filter.getFilterItems().add(new FilterItemInfo("head.id", page.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
				selector.add("id");
				selector.add("itemName");
				view.getSorter().add(new SorterItemInfo("seq"));
				NewListingEntryCollection entryCollection = NewListingEntryFactory.getRemoteInstance().getNewListingEntryCollection(view);

				for (int j = 0; j < entryCollection.size(); j++) {
					NewListingEntryInfo entry = entryCollection.get(j);

					view = new EntityViewInfo();
					filter = new FilterInfo();
					selector = view.getSelector();
					view.setFilter(filter);

					filter.getFilterItems().add(new FilterItemInfo("entry.id", entry.getId().toString()));
					filter.appendFilterItem("type", QuotingPriceTypeEnum.MODIFY_VALUE);
					filter.appendFilterItem("column.headColumn.property", DescriptionEnum.TOTALPRICESUM_VALUE);

					Set quotingSet=new HashSet();
					for (int k = 0; k < this.quotingPrices.size(); k++) {
						quotingSet.add(this.quotingPrices.get(k).getId().toString());
					}
					filter.getFilterItems().add(
							new FilterItemInfo("quotingPrice.id", quotingSet,CompareType.INCLUDE));

					selector.add("*");
					NewListingValueCollection valueCollection = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(view);
					BigDecimal sum = FDCHelper.ZERO;
					int count = 0;
					for (int k = 0; k < valueCollection.size(); k++) {
						NewListingValueInfo value = valueCollection.get(k);
						// if (value.getAmount() != null) {
						if (value.getAmount() == null)
							value.setAmount(FDCHelper.ZERO);
						sum = sum.add(value.getAmount());
						count++;
						// }

					}
					if (count != 0)
						map.put(entry.getId().toString(), sum.divide(new BigDecimal("" + count), BigDecimal.ROUND_HALF_UP));
					else
						map.put(entry.getId().toString(), FDCHelper._ONE);
				}
			}

			// 参考价
		} else if (setting.getStand() == QuoteStandEnum.ConsultPrice) {

			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			SelectorItemCollection selector = view.getSelector();
			view.setFilter(filter);

			filter.getFilterItems().add(new FilterItemInfo("head.head.id", listingInfo.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
			// filter.appendFilterItem("type", null);
			selector.add("id");
			view.getSorter().add(new SorterItemInfo("seq"));
			NewListingEntryCollection entryCollection = NewListingEntryFactory.getRemoteInstance().getNewListingEntryCollection(view);

			for (int j = 0; j < entryCollection.size(); j++) {
				NewListingEntryInfo entry = entryCollection.get(j);

				view = new EntityViewInfo();
				filter = new FilterInfo();
				selector = view.getSelector();
				view.setFilter(filter);

				filter.getFilterItems().add(new FilterItemInfo("entry.id", entry.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("quotingPrice", null));
				filter.appendFilterItem("column.headColumn.property", DescriptionEnum.TOTALPRICESUM_VALUE);
				selector.add("id");
				selector.add("amount");
				NewListingValueCollection valueCollection = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(view);
				for (int k = 0; k < valueCollection.size(); k++) {
					NewListingValueInfo value = valueCollection.get(k);
					map.put(entry.getId().toString(), value.getAmount() != null ? value.getAmount() : FDCHelper._ONE);
				}

			}
			// }

			// 最低总报价的投标人
		} else if (setting.getStand() == QuoteStandEnum.LowestTotalPrice) {
			NewQuotingPriceInfo temp = null;
			for (int xx = 0; xx < quotingPrices.size(); xx++) {
				NewQuotingPriceInfo q = quotingPrices.get(xx);
				if (q.getTotoalPrice() != null) {
					if (temp == null || q.getTotoalPrice().floatValue() < temp.getTotoalPrice().floatValue()) {
						temp = q;
					}
				}
			}
			if (temp == null)
				return new HashMap();

			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			SelectorItemCollection selector = view.getSelector();
			view.setFilter(filter);
			view = new EntityViewInfo();
			filter = new FilterInfo();
			selector = view.getSelector();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("quotingPrice.listing.id", listingInfo.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("quotingPrice.supplier.id", temp.getSupplier().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("entry.isLeaf", Boolean.TRUE));
			filter.appendFilterItem("type", QuotingPriceTypeEnum.modify);
			filter.appendFilterItem("column.headColumn.property", DescriptionEnum.TOTALPRICESUM_VALUE);
			selector.add("id");
			selector.add("entry.id");
			selector.add("amount");
			NewListingValueCollection valueCollection = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(view);
			for (int k = 0; k < valueCollection.size(); k++) {
				NewListingValueInfo value = valueCollection.get(k);
				map.put(value.getEntry().getId().toString(), value.getAmount() != null ? value.getAmount() : FDCHelper._ONE);
			}
		}

		return map;
	}

	/**
	 * 功能描述：开始计算并充超出范围的子目的数据
	 *
	 * @throws BOSException
	 * @throws SQLException
	 */

	private void initOverRangeData() throws BOSException, SQLException {
		NewListingPageCollection sheets = listingInfo.getPages();
		BigDecimal high = setting.getAllowHigh();
		BigDecimal low = setting.getAllowLow();
		BigDecimal oHUNDRED = new BigDecimal("100");
		for (int i = 0; i < sheets.size(); i++) {
			NewListingPageInfo sheetInfo = sheets.get(i);
			if (!sheetInfo.getPageHead().getName().equals(FDCExcelImportExportUtil.sheetName_ProjectDescription)
					&& !sheetInfo.getPageHead().getName().equals(FDCExcelImportExportUtil.sheetName_ProjectGather)) {
				KDTable table = (KDTable) sheetsMap.get(sheetInfo.getPageHead().getName());
				// 先将当前页所有子目选择出来
				EntityViewInfo viewInfo = new EntityViewInfo();
				FilterInfo filterInfo = new FilterInfo();
				filterInfo.appendFilterItem("head.id", sheetInfo.getId().toString());
				filterInfo.appendFilterItem("isLeaf", "1");
				viewInfo.setFilter(filterInfo);
				viewInfo.getSelector().add("*");
				viewInfo.getSelector().add("item.id");
				viewInfo.getSelector().add("item.number");
				viewInfo.getSorter().add(new SorterItemInfo("seq"));
				NewListingEntryCollection entryCollection = NewListingEntryFactory.getRemoteInstance().getNewListingEntryCollection(viewInfo);
				int seq = 1;
				for (int m = 0; m < entryCollection.size(); m++) {
					NewListingEntryInfo entryInfo = entryCollection.get(m);
					// 得到基准价
					BigDecimal base = (BigDecimal) standPriceMap.get(entryInfo.getId().toString());
					Map map = getDataByEntry(entryInfo);
					// 得到当前报价
					BigDecimal value = (BigDecimal) map.get("投标单价");
					// 保证基准价和投标价都不为空
					if (base == null){
						base = FDCHelper._ONE;
					}
					if (value == null){
						value = FDCHelper._ONE;
					}
//					 if (base == null || value == null || base.floatValue() <= 0)
//						continue;
					// /////开始填充
					if (base.floatValue() == 0
							|| value.multiply(oHUNDRED).divide(base, 2,
									BigDecimal.ROUND_HALF_UP).compareTo(high) == 1
							|| value.multiply(oHUNDRED).divide(base, 2,
									BigDecimal.ROUND_HALF_UP).compareTo(low) == -1) {
						int col = 0;
						int row = table.addRow().getRowIndex();
						ICell cell = table.getCell(row, col++);

						cell.setValue("" + seq++);// 序号

						cell = table.getCell(row, col++);
						cell.setValue(entryInfo.getItem() != null ? entryInfo.getItem().getNumber() : entryInfo.getItemNumber());// 编号

						cell = table.getCell(row, col++);
						cell.setValue(entryInfo.getItemName());// 名称

						cell = table.getCell(row, col++);
						cell.setValue(map.get("单位"));// 单位

						cell = table.getCell(row, col++);
						cell.setValue(map.get("工程量"));// 工程量

						cell = table.getCell(row, col++);
						cell.setValue("综合单价");// 异常部分

						cell = table.getCell(row, col++);
						if(base.intValue() < 0)
							cell.setValue("没有基准价");
						else
							cell.setValue(base);// 参考价(基准价)

						cell = table.getCell(row, col++);
						cell.setValue(value);// 投标价

						cell = table.getCell(row, col++);
						if (base.intValue() > 0) {
							TableScriptHelper.setTableDivideFormalu(cell, table.getCell(row, cell.getColumnIndex() - 1), table.getCell(row, cell
									.getColumnIndex() - 2));
							cell.getStyleAttributes().setNumberFormat("#,#0.00%;-#,#0.00%");
						}
						else if(base.intValue() == 0){
							cell.setValue("基准价为零");
						}
						else {
							cell.setValue("没有基准价");
							// rangeSa.setHorizontalAlign(HorizontalAlignment.RIGHT);
						}
						cell = table.getCell(row, col++);
						// 超出金额
						if (base.intValue() > 0) {
							TableScriptHelper
							.setTableSubFormalu(cell, table.getCell(row, cell.getColumnIndex() - 2), table.getCell(row, cell.getColumnIndex() - 3));
							cell.getStyleAttributes().setNumberFormat("#,#0.00;-#,#0.00");
						}
						else if(base.intValue() == 0){
							cell.setValue("基准价为零");
						}
						else {
							cell.setValue("没有基准价");
							// rangeSa.setHorizontalAlign(HorizontalAlignment.RIGHT);
						}
						

						cell = table.getCell(row, col++);
						cell.setValue(setting.getStand().getAlias());// 100%基准
					}
					// //////填充结束
				}
//				table.addRow();
//				IRow row = table.addRow();
//				row.getCell(1).setValue("修正金额总计");
//				BigDecimal sum = FDCHelper.ZERO;
//				int tableRowCount = table.getRowCount();
//				for (int j = 0; j < tableRowCount - 2; j++) {
//					sum = sum.add((BigDecimal) table.getRow(j).getCell(9).getValue());
//				}
//				row.getCell(9).setValue(sum);
//				table.getMergeManager().mergeBlock(tableRowCount - 2, 0, tableRowCount - 2, 10);
//				table.getMergeManager().mergeBlock(tableRowCount - 1, 0, tableRowCount - 1, 8);
				// table.getMergeManager().mergeBlock(tableRowCount - 1, 9,
				// tableRowCount - 1, 10);
			}
		}
	}

	private Map getDataByEntry(NewListingEntryInfo entryInfo) throws BOSException {
		Map map = new HashMap(10);
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("entry", entryInfo.getId().toString());
		filterInfo.appendFilterItem("quotingPrice", priceContentInfo.getId().toString());
		filterInfo.appendFilterItem("type", QuotingPriceTypeEnum.modify);// 修正报价
		filterInfo.appendFilterItem("quotingPrice", null);
		filterInfo.appendFilterItem("type", null);// 参考报价
		// filterInfo.appendFilterItem("column.headColumn.property",
		// "4TotalPriceSum");// 综合单价小计
		// filterInfo.appendFilterItem("column.headColumn.property",
		// "3TotalPrice"/* DescriptionEnum.TotalPrice */);//
		// 综合单价
		filterInfo.setMaskString("(#0 and (#1 and #2 or #3 and #4))");
		viewInfo.setFilter(filterInfo);
		viewInfo.getSelector().add("*");
		viewInfo.getSelector().add("column.*");
		viewInfo.getSelector().add("column.headColumn.*");
		NewListingValueCollection valueCollection = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(viewInfo);
		for (int i = 0; i < valueCollection.size(); i++) {
			NewListingValueInfo value = valueCollection.get(i);
			HeadColumnInfo headColumn = value.getColumn().getHeadColumn();
			if (value.getType() == null && "单位".equals(headColumn.getName())) {
				map.put("单位", value.getText());
			} else if (value.getType() == null && DescriptionEnum.ProjectAmtSum.equals(value.getColumn().getHeadColumn().getProperty())) {
				map.put("工程量", value.getAmount());
			} else if (value.getType() != null && headColumn.getProperty().equals(DescriptionEnum.TotalPriceSum)) {
				map.put("投标单价", value.getAmount() != null ? value.getAmount() : FDCHelper.ZERO);
			}
		}
		return map;
	}

	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		InviteHelper.exportToExcel(tables, true);
	}

	public void prePrintAction_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = (KDTable) tablePane.getSelectedComponent();
		InviteHelper.setPrintTitle(table, "超出范围综合单价明细表", 0);
		table.getPrintManager().printPreview();
	}

	public void printAction_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = (KDTable) tablePane.getSelectedComponent();
		InviteHelper.setPrintTitle(table, "超出范围综合单价明细表", 0);
		table.getPrintManager().print();
	}

	public void refreshAction_actionPerformed(ActionEvent e) throws Exception {
		init();
	}

	// private void del() throws BOSException, EASBizException {
	//
	// NewListingValueCollection valueCollection =
	// NewListingValueFactory.getRemoteInstance().getNewListingValueCollection();
	// for (int i = 0; i < valueCollection.size(); i++) {
	// NewListingValueInfo value = valueCollection.get(i);
	// if (value.getQuotingPrice() == null)
	// continue;
	// EntityViewInfo view = new EntityViewInfo();
	// FilterInfo filter = new FilterInfo();
	// view.setFilter(filter);
	// filter.appendFilterItem("id", value.getQuotingPrice().getId());
	// NewQuotingPriceCollection qc =
	// NewQuotingPriceFactory.getRemoteInstance().getNewQuotingPriceCollection(view);
	// if (qc.size() == 0) {
	// String sql = "delete from T_INV_NewListingValue where fid='" +
	// value.getId() + "'";
	// ISQLExecutor executor = SQLExecutorFactory.getRemoteInstance(sql);
	// executor.executeSQL();
	// }
	// }
	// }
}
