/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomChangeFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.framework.ICoreBase;

public class QuitRoomChangeListUI extends AbstractQuitRoomChangeListUI
{
	private static final Logger logger = CoreUIObject.getLogger(QuitRoomChangeListUI.class);

	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad,
				MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
	}

	public QuitRoomChangeListUI() throws Exception
	{
		super();
	}

	public void storeFields()
	{
		super.storeFields();
	}
	protected  OrgType getMainBizOrgType()
	{
		return OrgType.Sale;
	}
	public void onLoad() throws Exception {
		initTree();
		super.onLoad();
		this.actionRemove.setVisible(false);
		this.actionRemove.setEnabled(false);
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}

		FilterInfo filter = new FilterInfo();
		if (node.getUserObject() instanceof BuildingUnitInfo) {
			BuildingUnitInfo buildUnit = (BuildingUnitInfo) node
			.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(
					new FilterItemInfo("building.id", buildingId));
			filter.getFilterItems().add(
					new FilterItemInfo("room.buildUnit.id", buildUnit.getId()
							.toString()));
		} else if (node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(
					new FilterItemInfo("building.id", buildingId));
		} else if (node.getUserObject() instanceof SubareaInfo) {
			SubareaInfo subarea = (SubareaInfo) node.getUserObject();
			filter.getFilterItems()
			.add(
					new FilterItemInfo("subarea.id", subarea.getId()
							.toString()));

		} else if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node
			.getUserObject();
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", sellProject.getId()
							.toString()));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", null));
		}
		this.mainQuery.setFilter(filter);
		this.tblMain.removeRows();
}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

protected ICoreBase getBizInterface() throws Exception {
	return QuitRoomChangeFactory.getRemoteInstance();
}

protected String getEditUIName() {
	return QuitRoomChangeEditUI.class.getName();
}

}