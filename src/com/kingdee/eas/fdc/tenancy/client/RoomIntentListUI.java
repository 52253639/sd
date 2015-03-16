/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.tenancy.client.RoomIntentFilterUI;
import com.kingdee.eas.fdc.sellhouse.client.RoomEditUI;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.sellhouse.client.SimpleKDTSortManager;
import com.kingdee.eas.framework.*;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class RoomIntentListUI extends AbstractRoomIntentListUI
{
	private static final Logger logger = CoreUIObject.getLogger(RoomIntentListUI.class);
	
	private CustomerQueryPanel filterUI = null;
	
	private CommonQueryDialog commonQueryDialog = null;
	
	/**
	 * output class constructor
	 */
	public RoomIntentListUI() throws Exception
	{
		super();
	}
	
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	
	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new RoomIntentFilterUI(this, this.actionOnLoad);
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}
	
	protected boolean initDefaultFilter() {
		return true;
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		this.menuEdit.setVisible(false);
		this.menuBiz.setVisible(false);
		//this.menuBiz.setEnabled(false);
		this.menuItemCancelCancel.setVisible(false);
		this.menuItemCancel.setVisible(false);
		this.menuItemPayplan.setVisible(false);
		this.btnPayPlan.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		
		this.tblMain.getColumn("buildingArea").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("buildingArea").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(3));
		
		this.tblMain.getColumn("tencyArea").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("tencyArea").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(3));
		
		this.tblMain.getColumn("actualBuildingArea").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("actualBuildingArea").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(3));
		
		
		this.tblMain.getColumn("building.name").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("number").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("tenancyState").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("roomModel.name").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("buildingProperty.name").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("roomArea").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("roomArea").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(3));
		this.tblMain.getColumn("standardRent").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("standardRent").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("standardRentPrice").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("standardRentPrice").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(2));
	}
	
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
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
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
	throws Exception {
		super.tblMain_tableClicked(e);
	}
	
	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception
	{
	}
	
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		this.tblMain.removeRows(false);
		IMetaDataPK queryPK = this.getMainQueryPK();
		EntityViewInfo viewInfo = new EntityViewInfo();
		try {
		IQueryExecutor queryExecutor = this.getQueryExecutor(queryPK,viewInfo);	
		IRowSet rowSet = queryExecutor.executeQuery();
		while(rowSet.next())
		{
				IRow row = this.tblMain.addRow();
				BigDecimal buildingArea = rowSet.getBigDecimal("buildingArea");
				BigDecimal actualBuildingArea = rowSet.getBigDecimal("actualBuildingArea");
				BigDecimal roomArea = rowSet.getBigDecimal("roomArea");
				BigDecimal actualRoomArea = rowSet.getBigDecimal("actualRoomArea");
				row.getCell("id").setValue(rowSet.getString("id"));
				row.getCell("building.name").setValue(rowSet.getString("building.name"));
				row.getCell("number").setValue(rowSet.getString("number"));
				row.getCell("tenancyState").setValue(rowSet.getString("tenancyState"));
				
				//增加建筑面积和计租面积初始化
				row.getCell("tencyArea").setValue(rowSet.getString("tenancyArea"));
//				row.getCell("buildingArea").setValue(rowSet.getString("buildingArea"));
				
					row.getCell("roomArea").setValue(roomArea);
				
				//vbuildingArea
				
					row.getCell("buildingArea").setValue(buildingArea);
				
				row.getCell("roomModel.name").setValue(rowSet.getString("roomModel.name"));
				row.getCell("buildingProperty.name").setValue(rowSet.getString("buildingProperty.name"));
				row.getCell("standardRent").setValue(rowSet.getString("standardRent"));
				row.getCell("standardRentPrice").setValue(rowSet.getString("standardRentPrice"));
			}
			rowSet.beforeFirst();
		} catch (BOSException ex) {
			ex.printStackTrace();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}	
	}
	
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,EntityViewInfo viewInfo) {
		try {
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
			.getLastSelectedPathComponent();
			FilterInfo filter = new FilterInfo();
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo project = (SellProjectInfo) node
				.getUserObject();
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", project.getId()
								.toString()));
			} else {
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", null));
			}
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
	
	protected ICoreBase getBizInterface() throws Exception {
		return RoomFactory.getRemoteInstance();
	}
	
	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}
	
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
	protected String getLongNumberFieldName() {
		return "number";
	}
	
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		treeMain_valueChanged(null);
		 this.treeMain_valueChanged(null);
	}
	
	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}
	
	protected String getEditUIName() {
		return RoomEditUI.class.getName();
	}
	
	public void refreshListForOrder() throws Exception{
        this.treeMain_valueChanged(null);
	}


}