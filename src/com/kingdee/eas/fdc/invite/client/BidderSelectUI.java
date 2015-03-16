/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.util.Iterator;

import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.invite.NewQuotingPriceCollection;
import com.kingdee.eas.fdc.invite.NewQuotingPriceFactory;
import com.kingdee.eas.fdc.invite.NewQuotingPriceInfo;
import com.kingdee.eas.fdc.invite.QuotingPriceStatusEnum;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class BidderSelectUI extends AbstractBidderSelectUI {
	/**
	 * output class constructor
	 */
	public BidderSelectUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output btnYes_actionPerformed method
	 */
	protected void btnYes_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnYes_actionPerformed(e);
		int selectCount = 0;
		NewQuotingPriceCollection editColl = new NewQuotingPriceCollection();
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			NewQuotingPriceInfo quoting = (NewQuotingPriceInfo) row
					.getUserObject();
			boolean newSelected = ((Boolean) row.getCell("hasEffected")
					.getValue()).booleanValue();
			boolean oldSelected = ((Boolean) row.getCell("hasEffected")
					.getUserObject()).booleanValue();
			if (newSelected) {
				selectCount++;
			}
			if (newSelected != oldSelected) {
				if(!(quoting.getStatus().equals(QuotingPriceStatusEnum.ImportBase)||
						quoting.getStatus().equals(QuotingPriceStatusEnum.ImportToDB)))
				{
					quoting.setHasEffected(newSelected);
					if (newSelected) {
						quoting.setStatus(QuotingPriceStatusEnum.Evaluated);
					} else {
						quoting.setStatus(QuotingPriceStatusEnum.ImportedPrice);
					}


					editColl.add(quoting);
				}
			}
		}
		if (selectCount == 0 && this.tblMain.getRowCount() != 0) {
			MsgBox.showError("必须选择至少一家报价商!");
		} else {
			for (int i = 0; i < editColl.size(); i++) {
				NewQuotingPriceInfo info = editColl.get(i);
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("hasEffected");
				sels.add("status");
				NewQuotingPriceFactory.getRemoteInstance().updatePartial(info,
						sels);
			}
			QuotingPriceListUI listUI = (QuotingPriceListUI) getUIContext()
					.get(UIContext.OWNER);
			listUI.refreshQuotingPriceListByInviteListingRow();
			this.destroyWindow();
		}
	}

	public void onLoad() throws Exception {
		super.onLoad();
		String listingId = (String) this.getUIContext().get("listingId");
		this.tblMain.removeRows();
		this.tblMain.getStyleAttributes().setLocked(true);
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("supplier.*");
		view.getSelector().add("creator.*");
		view.getSelector().add("listing.curProject.*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems()
				.add(new FilterItemInfo("listing.id", listingId));
		filter.getFilterItems().add(
				new FilterItemInfo("status",
						QuotingPriceStatusEnum.NoImportPrice,
						CompareType.NOTEQUALS));
		NewQuotingPriceCollection didderCollection = NewQuotingPriceFactory
				.getRemoteInstance().getNewQuotingPriceCollection(view);
		tblMain.checkParsed();
		for (Iterator iter = didderCollection.iterator(); iter.hasNext();) {
			NewQuotingPriceInfo quoting = (NewQuotingPriceInfo) iter.next();
			IRow row = this.tblMain.addRow();
			row.setUserObject(quoting);
			row.getCell("id").setValue(quoting.getId().toString());
			row.getCell("hasEffected").setValue(
					Boolean.valueOf(quoting.isHasEffected()));
			row.getCell("hasEffected").setUserObject(
					Boolean.valueOf(quoting.isHasEffected()));
			row.getCell("supplier.name").setValue(
					quoting.getSupplier().getName());
			row.getCell("supplier.number").setValue(
					quoting.getSupplier().getNumber());
			
			if(quoting.getListing().getCurProject() != null)
			{
				row.getCell("listing.name").setValue(
						quoting.getListing().getCurProject().getName());
			}
			row.getCell("status").setValue(quoting.getStatus());

			ICell priceCell = row.getCell("totoalPrice");
			priceCell.setValue(quoting.getTotoalPrice() + "");
			priceCell.getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
			priceCell.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);

			row.getCell("creator.name")
					.setValue(quoting.getCreator().getName());
			row.getCell("createTime").setValue(quoting.getCreateTime());
			
			if (!(quoting.getStatus().equals(QuotingPriceStatusEnum.ImportBase)||
					quoting.getStatus().equals(QuotingPriceStatusEnum.ImportToDB))) {
				row.getCell("hasEffected").getStyleAttributes()
						.setLocked(false);
			}else{
				row.getCell("hasEffected").getStyleAttributes()
				.setLocked(true);
				row.getCell("hasEffected").setValue(Boolean.TRUE);
			}
		}
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		String formatString = "yyyy-MM-dd";
		tblMain.getColumn("createTime").getStyleAttributes().setNumberFormat(
				formatString);

	}

	/**
	 * output btnNo_actionPerformed method
	 */
	protected void btnNo_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnNo_actionPerformed(e);
		this.destroyWindow();
	}

	public static void showUI(CoreUI ui, String listingId) throws UIException {
		UIContext uiContext = new UIContext(ui);
		uiContext.put("listingId", listingId);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory
				.createUIFactory(UIFactoryName.MODEL)
				.create(BidderSelectUI.class.getName(), uiContext, null, "View");
		uiWindow.show();
	}
}