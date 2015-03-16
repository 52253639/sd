/**
 * 租赁明细表
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.client.PurchaseEditUI;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class LeaseDetailStatRptUI extends AbstractLeaseDetailStatRptUI
{
	private static final Logger logger = CoreUIObject
			.getLogger(LeaseDetailStatRptUI.class);

	/**
	 * output class constructor
	 */
	public LeaseDetailStatRptUI() throws Exception
	{
		super();
	}

	public void onLoad() throws Exception
	{
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		super.onLoad();
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.tblMain.getColumn("buildingArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("buildingArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("roomArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("roomArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("buildPrice").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("buildPrice").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("roomPrice").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("roomPrice").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("standardTotalAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("standardTotalAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("dealPrice").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("dealPrice").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("dealTotalAmount").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("dealTotalAmount").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("apportionArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);

		this.tblMain.getColumn("balconyArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);

		this.tblMain.getColumn("guardenArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);

		this.tblMain.getColumn("carbarnArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);

		this.tblMain.getColumn("useArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);

		this.tblMain.getColumn("flatArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);

		this.tblMain.getColumn("actualBuildingArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);

		this.tblMain.getColumn("actualRoomArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);

		this.tblMain.getColumn("buildPrice").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);

		this.tblMain.getColumn("roomPrice").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);

		this.tblMain.getColumn("baseRoomPrice").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);

		this.tblMain.getColumn("baseBuildingPrice").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);

		// this.tblMain.getColumn("sellProject.name").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		//		
		// this.tblMain.getColumn("unit.name").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		//		
		// this.tblMain.getColumn("areaCompensateAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		//		
		// this.tblMain.getColumn("sellAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		//		
		// this.tblMain.getColumn("basePrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

	}

	protected void beforeExcutQuery(EntityViewInfo arg0)
	{
		FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(
		// new FilterItemInfo("sellState", RoomSellStateEnum.INIT_VALUE,
		// CompareType.NOTEQUALS));
		// filter.getFilterItems().add(
		// new FilterItemInfo("sellState", RoomSellStateEnum.ONSHOW_VALUE,
		// CompareType.NOTEQUALS));
		// filter.getFilterItems()
		// .add(
		// new FilterItemInfo("sellState",
		// RoomSellStateEnum.KEEPDOWN_VALUE,
		// CompareType.NOTEQUALS));
		String buildingId = (String) this.getUIContext().get("buildingId");
		if (buildingId != null)
		{
			filter.getFilterItems().add(
					new FilterItemInfo("building.id", buildingId));
		}
		Date beginDate = (Date) this.getUIContext().get("beginDate");
		if (beginDate != null)
		{
			filter.getFilterItems().add(
					new FilterItemInfo("toPurchaseDate", beginDate,
							CompareType.GREATER_EQUALS));
		}
		Date endDate = (Date) this.getUIContext().get("endDate");
		if (endDate != null)
		{
			filter.getFilterItems().add(
					new FilterItemInfo("toPurchaseDate", FDCHelper
							.getNextDay(endDate), CompareType.LESS));
		}
		try
		{
			if (arg0.getFilter() == null)
			{
				arg0.setFilter(filter);
			} else
			{
				arg0.getFilter().mergeFilter(filter, "and");
			}
		} catch (BOSException e)
		{
			e.printStackTrace();
		}
		super.beforeExcutQuery(arg0);
	}

	/**
	 * output storeFields method
	 */
	public void storeFields()
	{
		super.storeFields();
	}

	protected boolean isIgnoreCUFilter()
	{
		return true;
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception
	{
		super.tblMain_tableClicked(e);
	}

	protected String getEditUIName()
	{
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception
	{
		return RoomFactory.getRemoteInstance();
	}

	/**
	 * 根据id显示窗体
	 * 
	 * @param filter
	 */
	public static void showUI(IUIObject ui, String buildingId, Date beginDate,
			Date endDate) throws UIException
	{
		UIContext uiContext = new UIContext(ui);
		uiContext.put("buildingId", buildingId);
		uiContext.put("beginDate", beginDate);
		uiContext.put("endDate", endDate);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(LeaseDetailStatRptUI.class.getName(), uiContext, null,
						"VIEW");
		uiWindow.show();
	}

	public void actionViewPurchase_actionPerformed(ActionEvent e)
			throws Exception
	{
		super.actionViewPurchase_actionPerformed(e);
		String roomId = this.getSelectedKeyValue();
		if (roomId != null)
		{
			RoomInfo room = RoomFactory.getRemoteInstance().getRoomInfo(
					new ObjectUuidPK(BOSUuid.read(roomId)));
			UIContext uiContext = new UIContext(ui);
			uiContext.put("ID", room.getLastPurchase().getId().toString());
			// 创建UI对象并显示
			IUIWindow uiWindow = UIFactory
					.createUIFactory(UIFactoryName.NEWTAB).create(
							PurchaseEditUI.class.getName(), uiContext, null,
							"VIEW");
			uiWindow.show();
		}
	}
}