/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.invite.IListingItem;
import com.kingdee.eas.fdc.invite.ListingItemCollection;
import com.kingdee.eas.fdc.invite.ListingItemFactory;
import com.kingdee.eas.fdc.invite.ListingItemInfo;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.KDTableUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ListingItemF7UI extends AbstractListingItemF7UI {
	private static final Logger logger = CoreUIObject
			.getLogger(ListingItemF7UI.class);
	protected boolean isCanceled = true;
	/**
	 * output class constructor
	 */
	public ListingItemF7UI() throws Exception {
		super();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		if (e.getClickCount() == 2) {
			// modify to view when doubleClick row by Jacky 2005-1-7
			if (e.getType() == 0) {
				return;
			}
			String id = getSelectedKeyValue();
			if (id == null) {
				MsgBox.showWarning(EASResource.getString(resourcePath,
						"SelectListingItem"));
				SysUtil.abort();
			}

			this.getUIContext().put("ListingItem", getListingItemInfo(id));
			this.destroyWindow();
			isCanceled = false;
		}
	}

	public ListingItemInfo getListingItemInfo(String id) throws BOSException {
		EntityViewInfo viewInfo = new EntityViewInfo();

		FilterInfo filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("id", id);
		viewInfo.setFilter(filterInfo);

		viewInfo.getSelector().add(new SelectorItemInfo("*"));
		viewInfo.getSelector().add(new SelectorItemInfo("entrys.*"));

		SorterItemInfo sorterItemInfo = new SorterItemInfo("entrys.date");
		sorterItemInfo.setSortType(SortType.DESCEND);
		viewInfo.getSorter().add(sorterItemInfo);

		ListingItemCollection listingItemCollection = ListingItemFactory
				.getRemoteInstance().getListingItemCollection(viewInfo);
		return listingItemCollection.get(0);
	}

	/**
	 * output kDButtonok_actionPerformed method
	 */
	protected void kDButtonok_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.kDButtonok_actionPerformed(e);
		String id = getSelectedKeyValue();
		if (id == null) {
			MsgBox.showWarning(EASResource.getString(resourcePath,
					"SelectListingItem"));
			SysUtil.abort();
		}
		this.getUIContext().put("ListingItem", getListingItemInfo(id));
		isCanceled = false;
		this.destroyWindow();
	}

	public static ListingItemInfo showViewUI(CoreUI ui) throws UIException {
		UIContext uiContext = new UIContext(ui);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(ListingItemF7UI.class.getName(), uiContext, null,
						"View");
		uiWindow.show();
		ListingItemInfo info = (ListingItemInfo) uiWindow.getUIObject()
				.getUIContext().get("ListingItem");
		return info;
	}

	/**
	 * output kDButtoncancel_actionPerformed method
	 */
	protected void kDButtoncancel_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.kDButtoncancel_actionPerformed(e);
		this.getUIContext().put("ListingItem", null);
		isCanceled = true;
		disposeUIWindow();
	}

	public ListingItemInfo[] getReturnValues() throws Exception {
		int[] rowID = KDTableUtil.getSelectedRows(this.tblMain);
		ListingItemInfo[] selectInfo = new ListingItemInfo[rowID.length];
		if (rowID.length < 1) {
			return selectInfo;
		}
		for (int i = 0; i < rowID.length; i++) {
			String id = tblMain.getRow(rowID[i]).getCell("id").getValue().toString();
			try {
				IListingItem iCostAccount = ListingItemFactory.getRemoteInstance();			
				selectInfo[i] = iCostAccount.getListingItemInfo(new ObjectUuidPK(BOSUuid.read(id)));
				
			} catch (BOSException ex) {
				handUIException(ex);
				// MsgBox.showError(this,ex.getMessage()) ;
				return selectInfo;
			}
		}

		return selectInfo;
	}
}