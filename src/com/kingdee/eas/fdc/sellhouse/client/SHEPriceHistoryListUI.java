/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;

import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.apache.velocity.runtime.parser.node.GetExecutor;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class SHEPriceHistoryListUI extends AbstractSHEPriceHistoryListUI
{
    private static final Logger logger = CoreUIObject.getLogger(SHEPriceHistoryListUI.class);
    
    private ItemAction[] hiddenAction ={this.actionAddNew,this.actionEdit,this.actionRemove,this.actionLocate,this.actionView,this.actionQuery};
    
    /**
     * output class constructor
     */
    public SHEPriceHistoryListUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception
    {
    	super.onLoad();
    	
    	this.initTable();
    	this.fillBasalInfo();
    	FDCClientHelper.setActionVisible(this.hiddenAction,false);
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	protected String getEditUIName()
	{
		return null;
	}
	
	private void initTable()
	{
		this.tblMain.checkParsed();
		
		this.txtRoomArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtRoomArea.setPrecision(2);
		
		this.txtBuildingArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBuildingArea.setPrecision(2);
		
		this.txtActualBuildingArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtActualBuildingArea.setPrecision(2);
		
		this.txtActualRoomArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtActualRoomArea.setPrecision(2);
		
		this.txtApportionArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtApportionArea.setPrecision(2);
		
		this.txtApportionArea.setHorizontalAlignment(JTextField.RIGHT);
		
		
		this.txtStandardTotalAmount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtStandardTotalAmount.setPrecision(2);
		
		this.txtStandardPrice.setHorizontalAlignment(JTextField.RIGHT);
		this.txtStandardPrice.setPrecision(2);
		
		this.txtDealTotalAmount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtDealTotalAmount.setPrecision(2);
		
		this.txtDealPrice.setHorizontalAlignment(JTextField.RIGHT);
		this.txtDealPrice.setPrecision(2);
		
		this.tblMain.getColumn("head.standardTotalAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.tblMain.getColumn("head.standardTotalAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	this.tblMain.getColumn("buildPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.tblMain.getColumn("buildPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	
    	this.tblMain.getColumn("roomPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
    	this.tblMain.getColumn("roomPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	}

	protected ICoreBase getBizInterface() throws Exception
	{
		return null;
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo)
	{
		String roomId = this.getUIContext().get("ID").toString();
		viewInfo = (EntityViewInfo) mainQuery.clone();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("head.id",roomId));
		// 合并查询条件
		try
		{
			
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
		} catch (Exception e)
		{
			this.handUIException(e);
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}
	/**
	 * 填充房间的基本信息
	 * @throws UuidException 
	 * @throws BOSException 
	 * @throws EASBizException 
	 *
	 */
	private void fillBasalInfo() throws EASBizException, BOSException, UuidException
	{
		String roomId = (String) this.getUIContext().get("ID");
		if(roomId == null)
			return;
		
		SelectorItemCollection sels = super.getSelectors();
		sels.add("*");
		sels.add("roomModel.*");
		sels.add("buildingProperty.*");
		sels.add("direction.*");
		sels.add("sight.*");
		
		RoomInfo room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(BOSUuid.read(roomId)),sels);
		this.txtBuildingArea.setValue(room.getBuildingArea());
		this.txtRoomArea.setValue(room.getRoomArea());
		this.txtActualBuildingArea.setValue(room.getActualBuildingArea());
		this.txtActualRoomArea.setValue(room.getActualRoomArea());
		this.txtApportionArea.setValue(room.getApportionArea());
		this.txtBalconyArea.setValue(room.getBalconyArea());
		this.txtFloorHeight.setValue(room.getFloorHeight());
		this.f7Direction.setValue(room.getDirection());
		this.f7Sight.setValue(room.getSight());
		this.f7RoomModel.setValue(room.getRoomModel());
		this.f7BuildingProperty.setValue(room.getBuildingProperty());
		this.comboHouseProperty.setSelectedItem(room.getHouseProperty());
		this.txtRoomNumber.setText(room.getDisplayName());
		if (room.isIsCalByRoomArea()) {
			this.txtStandardPrice.setValue(room.getRoomPrice());
		} else {
			this.txtStandardPrice.setValue(room.getBuildPrice());
		}
		this.txtStandardTotalAmount.setValue(room.getStandardTotalAmount());
		this.txtDealPrice.setValue(room.getDealPrice());
		this.txtDealTotalAmount.setValue(room.getDealTotalAmount());
		//this.chkIsCalByRoom.setSelected(room.isIsCalByRoomArea());
		this.comboRoomState.setSelectedItem(room.getSellState());
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception
	{
		if(e.getClickCount() != 2)
			return;
		int rowIndex = e.getRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		if(row == null)
			return;
		
		String roomPriceBillId = null;
		if(row.getCell("roomPriceBill.id").getValue() != null)
		{
			roomPriceBillId = row.getCell("roomPriceBill.id").getValue().toString(); 
		}
		String adjustPriceBillId = null;
		
		if(row.getCell("priceAdjustBill.id").getValue() != null)
		{
			adjustPriceBillId = row.getCell("priceAdjustBill.id").getValue().toString();
		}
		
		if(roomPriceBillId != null)
		{
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", roomPriceBillId);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
						RoomPriceBillEditUI.class.getName(),uiContext, null, "VIEW");
				uiWindow.show();
		}
		else if(adjustPriceBillId != null)
		{
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", adjustPriceBillId);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
						PriceAdjustEditUI.class.getName(),uiContext, null, "VIEW");
				uiWindow.show();
		}
	}
	protected boolean isIgnoreCUFilter()
	{
		return true;
	}
	
}