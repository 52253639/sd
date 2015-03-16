/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class HopedRoomsAnalyseRptUI extends AbstractHopedRoomsAnalyseRptUI {
	private static final Logger logger = CoreUIObject.getLogger(HopedRoomsAnalyseRptUI.class);

	Map rowMap = new HashMap();

	private HopedRoomsAnalyseFilterUI filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;

	private ItemAction[] hideAction = { this.actionEdit, this.actionAddNew, this.actionRemove, this.actionLocate, this.actionView };

	private String allBuildingIds = null; // 所包含楼栋id
	
	private int activeRowIndex = 0;
	private int activeColumnIndex = 0;
	private String orgUnitId = null;
	private String sellProjectId = null;
	private String saleProjectId = null;
	private String buildingId = null;
	private String unit = null;
	private String roomId = null;
	private String UIName = null;
	private String seq = null;
	

	/**
	 * output class constructor
	 */
	public HopedRoomsAnalyseRptUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		FDCClientHelper.setActionVisible(hideAction, false);
		this.btnUnion.setEnabled(true);
		this.menuItemUnion.setVisible(true);
		this.menuItemUnion.setEnabled(true);
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.menuItemUnion.setVisible(true);
		this.menuItemUnion.setEnabled(true);
		this.menuItemCancelCancel.setVisible(false);
		this.menuItemCancel.setVisible(false);
		initTree();
		this.tblMain.checkParsed();
		this.initTable();
		this.refresh(null);
		if (this.treeMain.getRowCount() > 0) {
			this.treeMain.setSelectionRow(0);
		}
	}

	public void actionUnion_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		UIContext context = new UIContext(ui);
		context.put("seq", seq);
		context.put("orgUnitId", orgUnitId);
		context.put("sellProjectId", sellProjectId);
		context.put("saleProjectId", saleProjectId);		
		context.put("buildingId", buildingId);
		context.put("unit", unit);
		context.put("roomId", roomId);
		context.put(UIContext.ID, roomId);
		context.put(UIContext.OWNER, HopedRoomsAnalyseRptUI.this);
		if(UIName==null){
			return;			
		}
		try {
			IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL).create(UIName, context, null, OprtState.VIEW);
			window.show();
			UIName = null;
		} catch (UIException e1) {
			this.handleException(e1);
		}
		
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() != 1)
			return;		
		activeRowIndex = e.getRowIndex();
		activeColumnIndex = e.getColIndex();
		orgUnitId = this.tblMain.getRow(activeRowIndex).getCell("orgUnitId").getValue().toString();
		sellProjectId = this.tblMain.getRow(activeRowIndex).getCell("sellProjectId").getValue().toString();
		saleProjectId = this.tblMain.getRow(activeRowIndex).getCell("saleProjectId").getValue().toString();
		buildingId = this.tblMain.getRow(activeRowIndex).getCell("buildingId").getValue().toString();
		if(this.tblMain.getRow(activeRowIndex).getCell("unit").getValue()!=null){
			unit = this.tblMain.getRow(activeRowIndex).getCell("unit").getValue().toString();
		}
		else{
			unit="0";
		}
		roomId = this.tblMain.getRow(activeRowIndex).getCell("id").getValue().toString();		
		if (activeColumnIndex == this.tblMain.getColumnIndex("number")) {
			UIName = RoomEditUI.class.getName();			
		} else if (activeColumnIndex == this.tblMain.getColumnIndex("first")) {			
			seq = "1";
			UIName = HopedRoomsCommerceChanceListUI.class.getName();			
		} else if (activeColumnIndex == this.tblMain.getColumnIndex("second")) {			
			seq = "2";
			UIName = HopedRoomsCommerceChanceListUI.class.getName();			
		} else if (activeColumnIndex == this.tblMain.getColumnIndex("third")) {			
			seq = "3";
			UIName = HopedRoomsCommerceChanceListUI.class.getName();			
		} else if (activeColumnIndex == this.tblMain.getColumnIndex("total")) {			
			seq = null;
			UIName = HopedRoomsCommerceChanceListUI.class.getName();			
		} else {
			return;
		}
		if(e.getClickCount()!=2){
			return;
		}
		UIContext context = new UIContext(ui);
		context.put("seq", seq);
		context.put("orgUnitId", orgUnitId);
		context.put("sellProjectId", sellProjectId);
		context.put("saleProjectId", saleProjectId);		
		context.put("buildingId", buildingId);
		context.put("unit", unit);
		context.put("roomId", roomId);
		context.put(UIContext.ID, roomId);
		context.put(UIContext.OWNER, HopedRoomsAnalyseRptUI.this);
		try {
			IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL).create(UIName, context, null, OprtState.VIEW);
			window.show();
			UIName = null;
		} catch (UIException e1) {
			this.handleException(e1);
		}

	}

	private void GetSearchResult(String nodeName, String AllBuildings) throws Exception {
		this.tblMain.removeRows();
		this.tblMain.checkParsed();
		this.tblMain.getHeadRow(0).getCell("orgUnit").getStyleAttributes().setHided(false);
		this.tblMain.getColumn("orgUnit").getStyleAttributes().setHided(false);
		this.tblMain.getHeadRow(0).getCell("sellProject").getStyleAttributes().setHided(false);
		this.tblMain.getColumn("sellProject").getStyleAttributes().setHided(false);
		this.tblMain.getHeadRow(0).getCell("building").getStyleAttributes().setHided(false);
		this.tblMain.getColumn("building").getStyleAttributes().setHided(false);

		if ("sellProject".equals(nodeName.toString())) {
			this.tblMain.getHeadRow(0).getCell("orgUnit").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("orgUnit").getStyleAttributes().setHided(true);
			this.tblMain.getHeadRow(0).getCell("sellProject").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("sellProject").getStyleAttributes().setHided(true);
		} else if ("buiding".equals(nodeName.toString())) {
			this.tblMain.getHeadRow(0).getCell("orgUnit").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("orgUnit").getStyleAttributes().setHided(true);
			this.tblMain.getHeadRow(0).getCell("sellProject").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("sellProject").getStyleAttributes().setHided(true);
			this.tblMain.getHeadRow(0).getCell("building").getStyleAttributes().setHided(true);
			this.tblMain.getColumn("building").getStyleAttributes().setHided(true);
		} else {
			this.tblMain.getHeadRow(0).getCell("orgUnit").getStyleAttributes().setHided(false);
			this.tblMain.getColumn("orgUnit").getStyleAttributes().setHided(false);
			this.tblMain.getHeadRow(0).getCell("sellProject").getStyleAttributes().setHided(false);
			this.tblMain.getColumn("sellProject").getStyleAttributes().setHided(false);
			this.tblMain.getHeadRow(0).getCell("building").getStyleAttributes().setHided(false);
			this.tblMain.getColumn("building").getStyleAttributes().setHided(false);
		}
		FDCSQLBuilder detailBuilder = new FDCSQLBuilder();
		detailBuilder.appendSql("SELECT  ").
		appendSql(" COMMERCECHANCE.FSysType AS system,  ").
		appendSql(" ORGUNIT.FID AS orgUnitId,  ").
		appendSql(" ORGUNIT.FName_l2 AS orgUnit,  ").		
		appendSql(" SALEPROJECT.FID AS saleProjectId,  ").
		appendSql(" SELLPROJECT.FID AS sellProjectId,  ").
		appendSql(" SELLPROJECT.FName_l2 AS sellProject,  ").
		appendSql(" BUILDING.FID AS buildingId,  ").
		appendSql(" BUILDING.FName_l2 AS building,  ").
		appendSql(" ROOM.FUnit AS unit,  ").
		appendSql(" ROOM.FID AS id,  ").
		appendSql(" ROOM.FDisplayName AS number,  ").
		appendSql(" PRODUCTTYPE.FName_l2 AS productType,  ").
		appendSql(" ROOMMODEL.FName_l2 AS model,  ").
		appendSql(" ROOM.FBuildingArea AS buildingArea,  ").
		appendSql(" SUM(CASE HOPEDROOMS.FSeq WHEN  1 THEN 1 ELSE 0 END) AS  f, ").
		appendSql(" SUM(CASE HOPEDROOMS.FSeq WHEN  2 THEN 1 ELSE 0 END)  AS  s, ").
		appendSql(" SUM(CASE HOPEDROOMS.FSeq WHEN  3 THEN 1 ELSE 0 END)  AS t, ").
		appendSql(" SUM(CASE WHEN HOPEDROOMS.FSeq IS NOT NULL THEN 1 ELSE 0 END)  AS ta  ").
		appendSql(" FROM T_SHE_CommerceChance AS COMMERCECHANCE  ").
		appendSql(" RIGHT OUTER JOIN T_SHE_CommerceRoomEntry AS HOPEDROOMS ").
		appendSql(" ON COMMERCECHANCE.FID = HOPEDROOMS.FCommerceChanceID ").					
		appendSql(" LEFT OUTER  JOIN T_SHE_Room AS ROOM ").
		appendSql(" ON HOPEDROOMS.FRoomID = ROOM.FID ").
		appendSql(" LEFT OUTER  JOIN T_SHE_Building AS BUILDING ").
		appendSql(" ON ROOM.FBuildingID = BUILDING.FID ").
		appendSql(" LEFT OUTER  JOIN T_SHE_RoomModel AS ROOMMODEL ").
		appendSql(" ON ROOM.FRoomModelID = ROOMMODEL.FID ").
		appendSql(" LEFT OUTER  JOIN T_FDC_ProductType AS PRODUCTTYPE ").
		appendSql(" ON ROOM.FProductTypeID = PRODUCTTYPE.FID ").
		appendSql(" LEFT OUTER  JOIN T_SHE_SellProject AS SELLPROJECT ").
		appendSql(" ON BUILDING.FSellProjectID = SELLPROJECT.FID ").
		appendSql(" LEFT OUTER JOIN T_SHE_SellProject AS SALEPROJECT ").
		appendSql(" ON COMMERCECHANCE.FSellProjectID = SALEPROJECT.FID ").
		appendSql(" LEFT OUTER  JOIN T_ORG_BaseUnit AS ORGUNIT ").
		appendSql(" ON SELLPROJECT.FOrgUnitID = ORGUNIT.FID ").
		appendSql(" where COMMERCECHANCE.FCommerceStatus='Intent'");	
			
		this.appendSysFilterSql(detailBuilder, "COMMERCECHANCE.FSysType");
		this.appendDateFilterSql(detailBuilder, "COMMERCECHANCE.FCommerceDate");
		this.appendStringFilterSql(detailBuilder, "BUILDING.FID", AllBuildings);

		detailBuilder.appendSql(" GROUP BY ").
		appendSql(" COMMERCECHANCE.FSysType,  ").
		appendSql(" ORGUNIT.FID,  ").
		appendSql(" ORGUNIT.FName_l2,  ").
		appendSql(" SALEPROJECT.FID,  ").
		appendSql(" SELLPROJECT.FID,  ").
		appendSql(" SELLPROJECT.FName_l2,  ").
		appendSql(" BUILDING.FID,  ").
		appendSql(" BUILDING.FName_l2, "). 
		appendSql(" ROOM.FUnit,  ").
		appendSql(" ROOM.FID,  ").
		appendSql(" ROOM.FDisplayName,  "). 
		appendSql(" PRODUCTTYPE.FName_l2,  ").
		appendSql(" ROOMMODEL.FName_l2,  ").
		appendSql(" ROOM.FBuildingArea  ");

		detailBuilder.appendSql(" ORDER BY ").
		appendSql(" COMMERCECHANCE.FSysType,  ").
		appendSql(" ORGUNIT.FID,  ").
		appendSql(" ORGUNIT.FName_l2, "). 
		appendSql(" SALEPROJECT.FID,  ").
		appendSql(" SELLPROJECT.FID,  ").
		appendSql(" SELLPROJECT.FName_l2, "). 
		appendSql(" BUILDING.FID,  ").
		appendSql(" BUILDING.FName_l2, "). 
		appendSql(" ROOM.FUnit,  ").
		appendSql(" ROOM.FID,  ").
		appendSql(" ROOM.FDisplayName,  ").
		appendSql(" PRODUCTTYPE.FName_l2, ").
		appendSql(" ROOMMODEL.FName_l2,  ").
		appendSql(" ROOM.FBuildingArea  ");
		
		IRowSet detailSet = detailBuilder.executeQuery();
		BigDecimal buildingAreaSum = FDCHelper.ZERO;
		BigDecimal firstSum = FDCHelper.ZERO;
		BigDecimal secondSum = FDCHelper.ZERO;
		BigDecimal thirdSum = FDCHelper.ZERO;
		BigDecimal totalSum = FDCHelper.ZERO;

		while (detailSet.next()) {
			IRow row = this.tblMain.addRow();
			row.getCell("orgUnitId").setValue(detailSet.getString("orgUnitId"));
			row.getCell("orgUnit").setValue(detailSet.getString("orgUnit"));
			row.getCell("saleProjectId").setValue(detailSet.getString("saleProjectId"));
			row.getCell("sellProjectId").setValue(detailSet.getString("sellProjectId"));
			row.getCell("sellProject").setValue(detailSet.getString("sellProject"));
			row.getCell("buildingId").setValue(detailSet.getString("buildingId"));
			row.getCell("building").setValue(detailSet.getString("building"));
			row.getCell("unit").setValue(detailSet.getString("unit"));
			row.getCell("number").setValue(detailSet.getString("number"));
			row.getCell("productType").setValue(detailSet.getString("productType"));
			row.getCell("model").setValue(detailSet.getString("model"));
			row.getCell("buildingArea").setValue(detailSet.getString("buildingArea"));
			row.getCell("first").setValue(detailSet.getInt("f") + "");
			row.getCell("second").setValue(detailSet.getInt("s") + "");
			row.getCell("third").setValue(detailSet.getInt("t") + "");
			row.getCell("total").setValue(detailSet.getInt("ta") + "");
			row.getCell("id").setValue(detailSet.getString("id"));
			
			buildingAreaSum.add(FDCHelper.toBigDecimal(detailSet.getString("buildingArea")));
			firstSum.add(FDCHelper.toBigDecimal(detailSet.getInt("f") + ""));
			secondSum.add(FDCHelper.toBigDecimal(detailSet.getInt("s") + ""));
			thirdSum.add(FDCHelper.toBigDecimal(detailSet.getInt("t") + ""));
			totalSum.add(FDCHelper.toBigDecimal(detailSet.getInt("ta") + ""));
		}
		this.tblMain.getMergeManager().mergeBlock(0, 0, this.tblMain.getRowCount() - 1, 3, KDTMergeManager.FREE_MERGE);		
	}

	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		try {
			commonQueryDialog.addUserPanel(this.getFilterUI());
		} catch (Exception e) {
			super.handUIException(e);
		}
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}

	/**
	 * 添加时间过滤
	 * 
	 * @param builder
	 * @param proName
	 * @throws Exception
	 */
	private void appendDateFilterSql(FDCSQLBuilder builder, String proName) throws Exception {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		if (para.isNotNull("isShowAll") && !para.getBoolean("isShowAll")) {
			Date beginDate = this.getBeginQueryDate();
			if (beginDate != null) {
				builder.appendSql(" and " + proName + " >= ? ");
				builder.addParam(FDCDateHelper.getSqlDate(beginDate));
			}
			Date endDate = this.getEndQueryDate();
			if (endDate != null) {
				builder.appendSql(" and " + proName + " < ? ");
				builder.addParam(FDCDateHelper.getNextDay(endDate));
			}
		}
	}

	/**
	 * 添加系统属性过滤
	 * 
	 * @param builder
	 * @param proName
	 * @throws Exception
	 */
	private void appendSysFilterSql(FDCSQLBuilder builder, String proName) throws Exception {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		if (para.isNotNull("isSalehouseSys") && !para.getBoolean("isSalehouseSys")) {
			builder.appendSql(" and " + proName + " = ? ");
			builder.addParam("SalehouseSys");
		}
		if (para.isNotNull("isTenancySys") && !para.getBoolean("isTenancySys")) {
			builder.appendSql(" and " + proName + " = ? ");
			builder.addParam("TenancySys");
		}
	}

	/**
	 * 添加字符串过滤
	 * 
	 * @param builder
	 * @param proName
	 * @throws Exception
	 */
	private void appendStringFilterSql(FDCSQLBuilder builder, String proName, String proValue) throws Exception {
		if (proName != null && proValue != null) {
			builder.appendSql(" and " + proName + " in (" + proValue + ")");
		}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected void checkTableParsed() {
		super.checkTableParsed();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}

	private HopedRoomsAnalyseFilterUI getFilterUI() throws Exception {
		if (this.filterUI == null) {
			this.filterUI = new HopedRoomsAnalyseFilterUI(this, this.actionOnLoad);
		}
		return this.filterUI;
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getBuildingTree(this.actionOnLoad, MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		allBuildingIds = null;
		String nodeName = null;
		if (node == null) {
			return;
		}
		this.getAllBuildingIds(node);
		if (node.getUserObject() instanceof OrgStructureInfo) {
			nodeName = "company";
		} else if (node.getUserObject() instanceof SellProjectInfo) {
			nodeName = "sellProject";

		} else if (node.getUserObject() instanceof BuildingInfo) {
			nodeName = "buiding";
		} else {
			return;
		}

		GetSearchResult(nodeName, allBuildingIds);
	}

	/**
	 * 查询所有的楼栋id
	 * 
	 * @param treeNode
	 */
	private void getAllBuildingIds(TreeNode treeNode) {
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
		if (thisNode.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) thisNode.getUserObject();
			if(allBuildingIds==null){
				allBuildingIds = "'" + building.getId().toString() + "'";
			}else{
				allBuildingIds = allBuildingIds + ",'" + building.getId().toString() + "'";
			}			
		}

		if (!treeNode.isLeaf()) {
			int childCount = treeNode.getChildCount();
			while (childCount > 0) {
				getAllBuildingIds(treeNode.getChildAt(childCount - 1));
				childCount--;
			}
		}
	}

	protected OrgUnitInfo getOrgUnitInfo() {
		return SysContext.getSysContext().getCurrentOrgUnit(this.getMainBizOrgType());
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			FilterInfo filter = new FilterInfo();
			if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo pro = (SellProjectInfo) node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", pro.getId().toString()));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}

			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			handleException(e);
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}

	private void initTable() {
		this.tblMain.removeRows();
		this.tblMain.checkParsed();
		// this.tblMain.addFootRow(0);
		// if(this.tblMain.getFootRow(0)!=null) {
		// this.tblMain.getFootRow(0).getStyleAttributes().setNumberFormat(
		// FDCHelper.getNumberFtm(2));
		// this.tblMain.getFootRow(0).getCell(0).setValue("合计：")
		// }
	}

	protected void initTableParams() {
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
	}

	protected String getKeyFieldName() {
		return "id";
	}

	private Date getBeginQueryDate() throws Exception {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return this.getFilterUI().getBeginQueryDate(para);
	}

	private Date getEndQueryDate() throws Exception {
		FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
		return this.getFilterUI().getEndQueryDate(para);
	}

	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionExport);
		super.actionExport_actionPerformed(e);
	}

	public void actionExportData_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionExportData);
		super.actionExportData_actionPerformed(e);
	}

	public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionExportSelected);
		super.actionExportSelected_actionPerformed(e);

	}

	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionToExcel);
		super.actionToExcel_actionPerformed(e);
	}

	public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception {
		handlePermissionForItemAction(actionPublishReport);
		super.actionPublishReport_actionPerformed(e);
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected boolean initDefaultFilter() {
		return true;
	}

	protected boolean isAllowDefaultSolutionNull() {
		return false;
	}
}