/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.invite.ListingPageSumEntryCollection;
import com.kingdee.eas.fdc.invite.ListingPageSumEntryInfo;
import com.kingdee.eas.fdc.invite.NewListingFactory;
import com.kingdee.eas.fdc.invite.NewListingInfo;
import com.kingdee.eas.fdc.invite.NewListingPageCollection;
import com.kingdee.eas.fdc.invite.NewListingPageFactory;
import com.kingdee.eas.fdc.invite.NewListingPageInfo;
import com.kingdee.eas.fdc.invite.NewQuotingPriceCollection;
import com.kingdee.eas.fdc.invite.NewQuotingPriceFactory;
import com.kingdee.eas.fdc.invite.NewQuotingPriceInfo;
import com.kingdee.eas.fdc.invite.QuotingPriceTypeEnum;

/**
 * 报价明细汇总表
 */
public class QuotingPriceSumUI extends AbstractQuotingPriceSumUI {
	private static final Logger logger = CoreUIObject.getLogger(QuotingPriceSumUI.class);

	NewQuotingPriceCollection quotingPrices = null;

	NewListingInfo listing = null;

	/**
	 * output class constructor
	 */
	public QuotingPriceSumUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		init();
		// super.kDWorkButton2.getAction().setEnabled(true);
		// super.kDWorkButton3.getAction().setEnabled(true);
		// super.kDWorkButton2.setEnabled(true);
		// super.kDWorkButton3.setEnabled(true);
	}

	private void init() throws Exception {
		tblMain.removeColumns();
		this.quotingPrices = this.getQuotingPrice();
		this.listing = this.getlisting();
		initTable();
		fillTable();
	}

	private void fillTable() {
		NewListingPageCollection pages = listing.getPages();
		for (int i = 0; i < pages.size(); i++) {
			NewListingPageInfo page = pages.get(i);
			IRow row = tblMain.addRow();
			row.getCell("pageName").setValue(page.getPageHead().getName());
			ListingPageSumEntryCollection sumEntry = page.getSumEntry();
			for (int j = 0; j < sumEntry.size(); j++) {
				ListingPageSumEntryInfo entry = sumEntry.get(j);
				String quotingId = entry.getQuotingPrice().getId().toString();
				if (entry.getType().equals(QuotingPriceTypeEnum.original)) {
					quotingId += "0";
				} else if (entry.getType().equals(QuotingPriceTypeEnum.modify)) {
					quotingId += "1";
				}
				if (row.getCell(quotingId) != null) {
					row.getCell(quotingId).setValue(entry.getAmount());
				} else {
					logger.warn("no quotingId:" + quotingId);
				}
			}
		}
		int rowCount = tblMain.getRowCount();
		IRow sumRow = tblMain.addRow();
		sumRow.getCell("pageName").setValue("小计");
		IRow taxProRow = tblMain.addRow();
		taxProRow.getCell("pageName").setValue("税率（%）");
		IRow taxRow = tblMain.addRow();
		taxRow.getCell("pageName").setValue("税金（元）");
		IRow totalRow = tblMain.addRow();
		totalRow.getCell("pageName").setValue("总造价（元）");
		for (int j = 1; j < tblMain.getColumnCount(); j++) {

			BigDecimal taxPro = FDCHelper.ZERO;
			if (listing.getTax() != null) {
				taxPro = listing.getTax();
			}
			BigDecimal tax = FDCHelper.ZERO;
			BigDecimal sum = FDCHelper.ZERO;
			for (int k = 0; k < rowCount; k++) {
				BigDecimal value = (BigDecimal) tblMain.getCell(k, j).getValue();
				if (value != null) {
					sum = sum.add(value);
				}
			}
			sum = sum.setScale(2, BigDecimal.ROUND_HALF_UP);
			tax = (sum.multiply(taxPro)).divide(FDCHelper.ONE_HUNDRED, BigDecimal.ROUND_UP);
			sumRow.getCell(j).setValue(sum);
			taxProRow.getCell(j).setValue(taxPro);
			taxRow.getCell(j).setValue(tax);
			totalRow.getCell(j).setValue(sum.add(tax));
		}

	}

	private void initTable() {
		this.tblMain.addHeadRows(2);
		IColumn column = this.tblMain.addColumn();
		column.setKey("pageName");
		tblMain.getHeadRow(0).getCell("pageName").setValue("页签");
		tblMain.getHeadMergeManager().mergeBlock(0, 0, 1, 0);
		for (int i = 0; i < this.quotingPrices.size(); i++) {
			NewQuotingPriceInfo quoting = this.quotingPrices.get(i);

			column = this.tblMain.addColumn();
			column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			column.setKey(quoting.getId() + "0");
			tblMain.getHeadRow(0).getCell(quoting.getId() + "0").setValue(quoting.getSupplier().getName());

			tblMain.getHeadRow(1).getCell(quoting.getId().toString() + "0").setValue("原始报价（元）");

			column = this.tblMain.addColumn();
			column.setWidth(120);
			column.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			column.setKey(quoting.getId() + "1");

			tblMain.getHeadRow(1).getCell(quoting.getId() + "1").setValue("修正最终报价（元）");

			tblMain.getHeadMergeManager().mergeBlock(0, i * 2 + 1, 0, i * 2 + 2);
		}
		tblMain.getStyleAttributes().setLocked(true);
	}

	private NewQuotingPriceCollection getQuotingPrice() throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		view.getSelector().add("*");
		view.getSelector().add("supplier.*");
		String inviteListingId = (String) this.getUIContext().get("inviteListingId");
		filter.getFilterItems().add(new FilterItemInfo("listing.id", inviteListingId));
		filter.getFilterItems().add(new FilterItemInfo("hasEffected", Boolean.TRUE));
		NewQuotingPriceCollection quotingPrices = NewQuotingPriceFactory.getRemoteInstance().getNewQuotingPriceCollection(view);
		return quotingPrices;
	}

	private NewListingInfo getlisting() throws Exception {
		String inviteListingId = (String) this.getUIContext().get("inviteListingId");
		NewListingInfo listing = null;
		listing = NewListingFactory.getRemoteInstance().getNewListingInfo(new ObjectUuidPK(BOSUuid.read(inviteListingId)));
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("head.id", listing.getId().toString()));
		SelectorItemCollection sic = view.getSelector();
		sic.add("*");
		sic.add("pageHead.*");
		sic.add("sumEntry.*");
		sic.add("sumEntry.quotingPrice.*");
		view.getSorter().add(new SorterItemInfo("seq"));
		NewListingPageCollection pages = NewListingPageFactory.getRemoteInstance().getNewListingPageCollection(view);
		listing.getPages().clear();
		listing.getPages().addCollection(pages);
		return listing;
	}

	public void actionExportToExcel_actionPerformed(ActionEvent e) throws IOException {
		KDTable table = this.tblMain;
		File tempFile = File.createTempFile("eastemp", ".xls");
		table.getIOManager().setTempFileDirection(tempFile.getPath());
		table.getIOManager().exportExcelToTempFile(false);
		tempFile.deleteOnExit();
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		InviteHelper.setPrintTitle(tblMain, "报价明细汇总表", 0);
		tblMain.getPrintManager().printPreview();
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		InviteHelper.setPrintTitle(tblMain, "报价明细汇总表", 0);
		tblMain.getPrintManager().print();
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		init();
	}

}
