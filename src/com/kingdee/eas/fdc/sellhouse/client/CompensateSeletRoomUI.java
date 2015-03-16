/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDComboBoxMultiColumnItem;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class CompensateSeletRoomUI extends AbstractCompensateSeletRoomUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7831496651841098867L;
	private static final Logger logger = CoreUIObject
			.getLogger(CompensateSeletRoomUI.class);
	protected Boolean isMultiSelect = Boolean.FALSE;
	public static final String SELECTROOM = "selectRoom";

	public static final String CUSTOMER ="客户";
	public static final String ROOM ="房间";
	public CompensateSeletRoomUI() throws Exception {
		super();
	}

	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		// super.tblMain_tableClicked(e);
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		if (this.getUIContext().get("isMultiSelect") != null) {
			this.isMultiSelect = (Boolean) this.getUIContext().get(
					"isMultiSelect");
		}

		if (isMultiSelect.booleanValue()) {
			this.tblMain.getSelectManager().setSelectMode(
					KDTSelectManager.MULTIPLE_CELL_SELECT);
		} else {
			this.tblMain.getSelectManager().setSelectMode(
					KDTSelectManager.ROW_SELECT);
		}

		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionQuery.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionView.setVisible(false);
		this.actionRefresh.setVisible(false);

		// this.tblMain.getSelectManager().select(-1,-1);
		KDTableUtil.setSelectedRow(this.tblMain, 0);
		
		String[] fields=new String[this.tblMain.getColumnCount()];
		for(int i=0;i<this.tblMain.getColumnCount();i++){
			fields[i]=this.tblMain.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(this.tblMain,fields);
		
		this.comboFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { ROOM }));
		this.comboFilter.addItem(new KDComboBoxMultiColumnItem(new String[] { CUSTOMER }));
	}
	protected String getTextValue(KDTextField text) {
		if (text.getText().trim().equals("")) {
			return null;
		} else {
			return text.getText();
		}
	}
	protected void btnSelect_actionPerformed(ActionEvent e) throws Exception {
		this.refresh(null);
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		FilterInfo filter = new FilterInfo();
	
		String customer=null;
		String room=null;
		if (comboFilter.getSelectedItem().toString().equals(CUSTOMER)) {
			customer = getTextValue(txtFilterValue);
		}
		if (comboFilter.getSelectedItem().toString().equals(ROOM)) {
			room = getTextValue(txtFilterValue);
		}
		if(customer!=null){
			filter.getFilterItems().add(new FilterItemInfo("signManage.customerNames","%"+customer+"%",CompareType.LIKE));
		}
		if(room!=null){
			filter.getFilterItems().add(new FilterItemInfo("name","%"+room+"%",CompareType.LIKE));
		}
		filter.getFilterItems().add(
				new FilterItemInfo("signManage.bizState",TransactionStateEnum.SIGNAUDIT_VALUE, CompareType.EQUALS));
		
		if (this.getUIContext().get("selectSellProject") != null) {
			SellProjectInfo  sellProject = (SellProjectInfo)this.getUIContext().get("selectSellProject");
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id",
							sellProject.getId().toString(), CompareType.EQUALS));
		}
		if (this.getUIContext().get("selectBuilding") != null) {
			BuildingInfo build = (BuildingInfo)this.getUIContext().get("selectBuilding");
			filter.getFilterItems().add(new FilterItemInfo("building.id", build.getId().toString(),CompareType.EQUALS));
		}
		if (this.getUIContext().get("selectUnit") != null) {
			BuildingUnitInfo unit = (BuildingUnitInfo)this.getUIContext().get("selectUnit");
			filter.getFilterItems().add(new FilterItemInfo("unit.id", unit.getId().toString(),CompareType.EQUALS));
		}
		filter.getFilterItems().add(new FilterItemInfo("isActualAreaAudited", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("roomArea.compensateState", null,CompareType.EQUALS));
	
		// 合并查询条件
		viewInfo = (EntityViewInfo) mainQuery.clone();
		try {
			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected void btnOK_actionPerformed(ActionEvent e) throws Exception {
		super.btnOK_actionPerformed(e);
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if (selectRows == null || selectRows.length <= 0) {
			FDCMsgBox.showWarning(this, "请先选择记录!");
			SysUtil.abort();
		}

		Object[] object = new Object[selectRows.length];
		for (int i = 0; i < selectRows.length; i++) {
			int select = selectRows[i];
			IRow row = this.tblMain.getRow(select);
			if (row == null) {
				continue;
			}
			Map map = new HashMap();
			if (row.getCell("id").getValue() != null) {
				map.put("id", row.getCell("id").getValue());
			}
			if (row.getCell("name").getValue() != null) {
				map.put("name", row.getCell("name").getValue());
			}
			if (row.getCell("signManage.contractBuildPrice").getValue() != null) {
				map.put("signManage.contractBuildPrice", row.getCell(
						"signManage.contractBuildPrice").getValue());
			}
			if (row.getCell("signManage.contractRoomPrice").getValue() != null) {
				map.put("signManage.contractRoomPrice", row.getCell(
						"signManage.contractRoomPrice").getValue());
			}
			if (row.getCell("actualBuildingArea").getValue() != null) {
				map.put("actualBuildingArea", row.getCell("actualBuildingArea")
						.getValue());
			}
			if (row.getCell("actualRoomArea").getValue() != null) {
				map.put("actualRoomArea", row.getCell("actualRoomArea")
						.getValue());
			}
			if (row.getCell("signManage.contractTotalAmount").getValue() != null) {
				map.put("signManage.contractTotalAmount", row.getCell(
						"signManage.contractTotalAmount").getValue());
			}
			if (row.getCell("signManage.sellAmount").getValue() != null) {
				map.put("signManage.sellAmount", row.getCell(
						"signManage.sellAmount").getValue());
			}
			if (row.getCell("sellProject.id").getValue() != null) {
				map.put("sellProject.id", row.getCell("sellProject.id")
						.getValue());
			}
			if (row.getCell("building.id").getValue() != null) {
				map.put("building.id", row.getCell("building.id").getValue());
			}
			if (row.getCell("unit.id").getValue() != null) {
				map.put("unit.id", row.getCell("unit.id").getValue());
			}
			if (row.getCell("buildArea").getValue() != null) {
				map.put("buildArea", row.getCell("buildArea").getValue());
			}
			if (row.getCell("roomArea").getValue() != null) {
				map.put("roomArea", row.getCell("roomArea").getValue());
			}
			if (row.getCell("customer").getValue() != null) {
				map.put("customer", row.getCell("customer").getValue());
			}
			if (row.getCell("signManage.id").getValue() != null) {
				map.put("signManage.id", row.getCell("signManage.id")
						.getValue());
			}

			object[i] = map;
		}
		if (object != null && object.length > 0) {
			Map uiContext = getUIContext();
			uiContext.put(SELECTROOM, object);
		}
		this.uiWindow.close();
	}

	protected void btnQuit_actionPerformed(ActionEvent e) throws Exception {
		super.btnQuit_actionPerformed(e);
		this.uiWindow.close();
	}

}