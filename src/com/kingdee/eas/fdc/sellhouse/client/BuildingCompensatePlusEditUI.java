/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class BuildingCompensatePlusEditUI extends AbstractBuildingCompensatePlusEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(BuildingCompensatePlusEditUI.class);
    
    /**
     * output class constructor
     */
    public BuildingCompensatePlusEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

	protected String getEditUIName() {
		return RoomAreaCompensateEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomAreaCompensateFactory.getRemoteInstance();
	}

	protected void execQuery() {
		FilterInfo filter = new FilterInfo();
		if(getUIContext().get("buildingId")!=null){
			filter.getFilterItems().add(new FilterItemInfo("building.id",getUIContext().get("buildingId").toString()));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("id", null));
		}
//		if (this.mainQuery.getFilter() == null) {
			mainQuery.setFilter(filter);
//		} else {
//			try {
//				mainQuery.getFilter().mergeFilter(filter, "and");
//			} catch (BOSException e) {
//				logger.error(e);
//			}
//		}
		super.execQuery();
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		execQuery();
	}
	
	protected String getKeyFieldName() {
		return "roomAreaCompensate.id";
	}
	
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		if (getSelectedKeyValue() != null) {
			super.actionView_actionPerformed(e);
		} else {
			MsgBox.showInfo(this, getRes("notCompensate"));
			return;
		}
	}
	
	public static String getRes(String resName) {
		return EASResource.getString(
				"com.kingdee.eas.fdc.sellhouse.SellHouseResource", resName);
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put("roomId", getSelectedCostBillID());
		uiContext.put("isFromWorkFlow","true");
	}
	

	protected String getSelectedCostBillID() {
		// return super.getSelectedKeyValue();

		int selectIndex = -1;

		// KDTSelectBlock selectBlock = tblMain.getSelectManager().get();
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);

		// if (selectBlock != null) {
		if (selectRows.length > 0) {
			// int rowIndex = selectBlock.getTop();
			int rowIndex = selectRows[0];
			IRow row = tblMain.getRow(rowIndex);
			if (row == null) {
				return null;
			}

			selectIndex = rowIndex;
			// ICell cell = row.getCell(getKeyFieldName());
			ICell cell = row.getCell("id");

			if (cell == null) {
				MsgBox.showError(EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Error_KeyField_Fail"));
				SysUtil.abort();
			}

			Object keyValue = cell.getValue();

			if (keyValue != null) {
				return keyValue.toString();
			}
		}

		return null;
	}
}