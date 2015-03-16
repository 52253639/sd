/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomDesFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RoomDesListUI extends AbstractRoomDesListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RoomDesListUI.class);

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	/**
	 * output class constructor
	 */
	public RoomDesListUI() throws Exception {
		super();
	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.btnCreateRoom.setIcon(EASResource
				.getIcon("imgTbtn_associatecreate"));
	}

	public void onLoad() throws Exception {
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		this.actionQuery.setVisible(false);
		this.actionLocate.setVisible(false);
		super.onLoad();
		this.tblMain.getColumn("buildingArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("buildingArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("roomArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("roomArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("floorHeight").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("floorHeight").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(2));
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionCreateRoom.setEnabled(false);
		}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomDesFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return RoomDesEditUI.class.getName();
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getBuildingTree(actionOnLoad, null));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		FilterInfo filter = new FilterInfo();
		if (node != null && node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("building.id", buildingId));
			if (saleOrg.isIsBizUnit()) {
				this.actionAddNew.setEnabled(true);
				this.actionCreateRoom.setEnabled(true);
			}
		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", null));
			this.actionAddNew.setEnabled(false);
			this.actionCreateRoom.setEnabled(false);

		}
		this.mainQuery.setFilter(filter);
		this.tblMain.removeRows();
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		DefaultKingdeeTreeNode buildingNode = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		uiContext.put("building", buildingNode.getUserObject());
	}

	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}

	public void actionCreateRoom_actionPerformed(ActionEvent e)
	throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node != null && node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			RoomCollection rooms2 = SHEHelper.getRooms(building);
			for(int i=0;i<rooms2.size();i++)
			{
				RoomInfo room = (RoomInfo)rooms2.get(i);
				if(room.isIsAreaAudited() || room.isIsActualAreaAudited())
				{
					MsgBox.showInfo("有房间售前复核或者实测复核过，不能重新生成房间!");
					this.abort();
				}
			}
			RoomCollection rooms = SHEHelper.getRoomsByDes(building.getId()
					.toString());
			if(rooms.size()==0){
				return;
			}
			RoomCreateUI.showWindows(this, rooms);
			
		}
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
	}
	
	protected String getLongNumberFieldName() {
		return "number";
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		if (confirmRemove()) {
//			prepareRemove(null).callHandler();
			Remove();
			refresh(e);
		}
	}
}