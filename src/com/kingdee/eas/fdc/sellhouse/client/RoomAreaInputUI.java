/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.kds.KDSSheet;
import com.kingdee.bos.ctrl.kdf.read.POIXlsReader;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomAreaChangeTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateCollection;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateFactory;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class RoomAreaInputUI extends AbstractRoomAreaInputUI {
	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	
	KDBizPromptBox roomModelBox = new KDBizPromptBox();
	
	TreePath curPath = null;
	
	private Map mapRoomModel = null;
	
	/**
	 * output class constructor
	 */
	public RoomAreaInputUI() throws Exception {
		super();
	}
	
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}
	
	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
	throws Exception {
		if (isOrderForClickTableHead() && e.getType() == KDTStyleConstants.HEAD_ROW
				&& e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1){
			//重写，使用SimpleSortManager排序
			return;
		}
		super.tblMain_tableClicked(e);
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			uiContext.put("buildingID",building.getId().toString());
		}if(((DefaultKingdeeTreeNode) node
				.getParent()).getUserObject() instanceof BuildingInfo)
		{
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			uiContext.put("buildingID",building.getId().toString());
		}
		super.prepareUIContext(uiContext, e);
	}
	
	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
	throws Exception {
	}
	
	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(
			java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}
	
	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
	throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (this.curPath != null) {
			if (e.getNewLeadSelectionPath().equals(this.curPath)) {
				return;
			}
		}
		this.verifySaved(e.getOldLeadSelectionPath());
		this.curPath = e.getNewLeadSelectionPath();
		fillTable();
	}
	
	private void fillTable() throws BOSException {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		
		this.tblMain.removeRows(false);

		this.tblMain.getColumn("subArea").getStyleAttributes().setHided(false);
		this.tblMain.getColumn("building").getStyleAttributes().setHided(false);
		this.tblMain.getColumn("unit").getStyleAttributes().setHided(false);
		
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		view.getSelector().add("*");
		view.getSelector().add("building.name");
		view.getSelector().add("buildUnit.name");
		view.getSelector().add("building.sellProject.name");
		view.getSelector().add("building.subarea.name");
		view.getSelector().add("roomModel.*");
		

		
		if (node.getUserObject() instanceof Integer) {   // 已作废
			Integer unit = (Integer) node.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("building.id", building.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("unit", unit));
			
			hideTheColumns(new String[]{"subArea","building","unit"});
			
			setTheViewSorter(view,new String[]{ "displayName"  });
		}else if (node.getUserObject() instanceof BuildingUnitInfo) {   //单元
			BuildingUnitInfo buildUnit = (BuildingUnitInfo) node.getUserObject();
			BuildingInfo building = (BuildingInfo)((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("building.id", building.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("buildUnit.id", buildUnit.getId().toString()));
			
			hideTheColumns(new String[]{"subArea","building","unit"});
			
			setTheViewSorter(view,new String[]{ "displayName"  });
		}else if (node.getUserObject() instanceof BuildingInfo) {   //楼栋
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("building.id", building.getId().toString()));
				
			hideTheColumns(new String[]{"subArea","building"  ,building.getUnitCount()==0?"unit":""});
			
			setTheViewSorter(view,new String[]{ building.getUnitCount()==0?"":"unit","displayName"  });
		}else if(node.getUserObject() instanceof SubareaInfo){     //分区
			SubareaInfo subarea = (SubareaInfo)node.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("building.subarea.id",subarea.getId().toString()));
		    
			hideTheColumns(new String[]{"subArea"});
			
			setTheViewSorter(view,new String[]{ "building.id","unit","displayName"  });
		}else if(node.getUserObject() instanceof SellProjectInfo) { //销售项目
			SellProjectInfo sellProject = (SellProjectInfo)node.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("building.sellProject.id",sellProject.getId().toString()));
			
			setTheViewSorter(view,new String[]{ "building.sellProject.id","building.id","unit","displayName"  });
		}else{			
			return;
		}
		
		
		RoomCollection rooms = RoomFactory.getRemoteInstance().getRoomCollection(view);
		fillTableByRooms(rooms);
		
		
		if (!saleOrg.isIsBizUnit()) {
			this.actionSubmit.setEnabled(false);
			this.actionImportExcel.setEnabled(false);
			this.actionAreaAudit.setEnabled(false);
			this.actionActualAreaAudit.setEnabled(false);
			this.actionCancelActualAudit.setEnabled(false);
			this.actionCancelAreaAudit.setEnabled(false);
			this.tblMain.setEditable(false);
		}
	}

	
	
    /**
     * 隐藏指定的列   (列名有 : subArea,building,unit,displayName)
     * @param colNames
     */
	private void hideTheColumns(String[] colNames)  {
		if(colNames!=null && colNames.length>0) {
			for(int i=0,j=colNames.length;i<j;i++) {
				if(colNames[i]!=null && !colNames[i].equals(""))
					this.tblMain.getColumn(colNames[i]).getStyleAttributes().setHided(true);
			}
		}
		
	}
		
    /**
     * view的排序方式  (可排序的字段名有:  building.sellProject.id , building.id ,unit ,displayName) 
     * @param view
     * @param itemNames
     */
	private void setTheViewSorter(EntityViewInfo view,String[] itemNames) {
		if(view!=null)  {
			if(itemNames!=null && itemNames.length>0) {
				for(int i=0,j=itemNames.length;i<j;i++) {
					if(itemNames[i]!=null && !itemNames[i].equals(""))
						view.getSorter().add(new SorterItemInfo(itemNames[i]));
				}
			}
			
		}
	}
	
	
	
	
	
	
	private void fillTableByRooms(RoomCollection rooms) {
		if(rooms == null){
			return;
		}

		
		for (int i = 0; i < rooms.size(); i++) {
			IRow row = this.tblMain.addRow();
			RoomInfo room = rooms.get(i);
			// 加上  计租面积的判断
			if (room.getStandardTotalAmount() == null  &&  !room.isIsAreaAudited()) {				
				row.getCell("buildArea").getStyleAttributes().setLocked(false);
				row.getCell("roomArea").getStyleAttributes().setLocked(false);
				row.getCell("roomModel.roomModelType.name").getStyleAttributes().setLocked(false);
				row.getCell("tenancyArea").getStyleAttributes().setLocked(false);
				row.getCell("roomModel.roomModelType.name").getStyleAttributes().setBackground(Color.WHITE);
				row.getCell("buildArea").getStyleAttributes().setBackground(Color.WHITE);
				row.getCell("roomArea").getStyleAttributes().setBackground(Color.WHITE);
				row.getCell("tenancyArea").getStyleAttributes().setBackground(Color.WHITE);
			} else {
				row.getCell("tenancyArea").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
				row.getCell("roomModel.roomModelType.name").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
				row.getCell("buildArea").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
				row.getCell("roomArea").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
			}
			
			if(!room.isIsActualAreaAudited()){
				
				row.getCell("actualBuildArea").getStyleAttributes().setBackground(Color.WHITE);
				row.getCell("actualRoomArea").getStyleAttributes().setBackground(Color.WHITE);
			
				row.getCell("actualBuildArea").getStyleAttributes().setLocked(false);
				row.getCell("actualRoomArea").getStyleAttributes().setLocked(false);
				
				
			}else{
				row.getCell("actualBuildArea").getStyleAttributes().setLocked(true);
				row.getCell("actualRoomArea").getStyleAttributes().setLocked(true);

				row.getCell("actualBuildArea").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
				row.getCell("actualRoomArea").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
				
			}
			//by tim_gao 带出计租面积 2010-2-22
			if(room.getTenancyArea()!=null){
				row.getCell("tenancyArea").setValue(room.getTenancyArea());
			}else{
				row.getCell("tenancyArea").setValue(null);
			}
			row.setUserObject(room);
			row.getCell("id").setValue(room.getId().toString());
			SubareaInfo subArea = room.getBuilding().getSubarea();
			if(subArea!=null)
				row.getCell("subArea").setValue(subArea.getName());
			else
				row.getCell("subArea").setValue("");
			row.getCell("building").setValue(room.getBuilding().getName());
			row.getCell("unit").setValue(room.getBuildUnit()==null?"":room.getBuildUnit().getName());
			
			row.getCell("number").setValue(room.getDisplayName());
			
			KDBizPromptBox f7Prompt = new KDBizPromptBox();
			f7Prompt.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelQuery");
			f7Prompt.setEditable(true);
			f7Prompt.setDisplayFormat("$name$");
			f7Prompt.setEditFormat("$name$");
			f7Prompt.setCommitFormat("$number$");
			EntityViewInfo viewInfo = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("building.id",room.getBuilding().getId().toString()));
			viewInfo.setFilter(filter);
			f7Prompt.setEntityViewInfo(viewInfo);
			ICellEditor f7editor = new KDTDefaultCellEditor(f7Prompt);
			row.getCell("roomModel.roomModelType.name").setEditor(f7editor);
			row.getCell("roomModel.roomModelType.name").setValue(room.getRoomModel());
			
			row.getCell("buildArea").setValue(room.getBuildingArea());
			row.getCell("roomArea").setValue(room.getRoomArea());
			row.getCell("actualBuildArea").setValue(
					room.getActualBuildingArea());
			row.getCell("actualRoomArea").setValue(room.getActualRoomArea());
			row.getCell("isAreaAudited").setValue(new Boolean(room.isIsAreaAudited()));
			row.getCell("isActualAreaAudited").setValue(new Boolean(room.isIsActualAreaAudited()));
			row.getCell("roomNo").setValue(room.getRoomNo());
			if(RoomJoinStateEnum.JOINED.equals(room.getRoomJoinState()))
			{
				row.getCell("roomNo").getStyleAttributes().setLocked(true);
			}
			else
			{
				row.getCell("roomNo").getStyleAttributes().setLocked(false);
			}
		}
	}
	
	public boolean isEditedTable() {
		KDTable table = this.tblMain;
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getUserObject() != null) {
				if (this.isEditedRow(row))
					return true;
			}
		}
		return false;
	}
	
	private void verifySaved(TreePath treePath) {
		// this.tblMain.getSelectManager().select(0, 0);
		if (this.isEditedTable()) {
			if (MsgBox.showConfirm2New(this, "是否保存?") == MsgBox.YES) {
				if (!this.verify()) {
					if (treePath != null) {
						this.treeMain.setSelectionPath(treePath);
					}
					this.abort();
				}
				this.btnSubmit.doClick();
			}
		}
	}
	
	public boolean destroyWindow() {
		verifySaved(null);
		return super.destroyWindow();
	}
	
	public boolean isEditedRow(IRow row) {
		if (row.getUserObject() == null) {
			return false;
		}
		//by tim_gao 2011-2-28
		RoomInfo room = (RoomInfo) row.getUserObject();
		String[] compareyKeys = new String[] { "buildingArea", "roomArea",
				"actualBuildingArea", "actualRoomArea","roomModel","roomNo","tenancyArea"};
		String[] columnKeys = new String[] { "buildArea", "roomArea",
				"actualBuildArea", "actualRoomArea","roomModel.roomModelType.name","roomNo" ,"tenancyArea"}; 
		for (int i = 0; i < compareyKeys.length; i++) {
			if (!FDCHelper.isEqual(room.get(compareyKeys[i]), row.getCell(
					columnKeys[i]).getValue())) {
				return true;
			}
		}
		return false;
	}
	
	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return RoomFactory.getRemoteInstance();
	}
	
	protected String getEditUIName() {
		return RoomEditUI.class.getName();
	}
	
	protected void initTree() throws Exception {
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
	}
	
	public void onLoad() throws Exception {
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		initControl();
		super.onLoad();
		this.tblMain.getColumn("buildArea").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("buildArea").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(3));
		this.tblMain.getColumn("roomArea").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("roomArea").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(3));
		this.tblMain.getColumn("actualBuildArea").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("actualBuildArea").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(3));
		this.tblMain.getColumn("actualRoomArea").getStyleAttributes()
		.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("actualRoomArea").getStyleAttributes()
		.setNumberFormat(FDCHelper.getNumberFtm(3));
		this.tblMain.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(3);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.tblMain.getColumn("buildArea").setEditor(numberEditor);
		this.tblMain.getColumn("roomArea").setEditor(numberEditor);
		this.tblMain.getColumn("actualBuildArea").setEditor(numberEditor);
		this.tblMain.getColumn("actualRoomArea").setEditor(numberEditor);
		//by tim_gao 计租面积
		this.tblMain.getColumn("tenancyArea").setEditor(numberEditor);
		KDCheckBox checkBox = new KDCheckBox();
		KDTDefaultCellEditor chkEditor = new KDTDefaultCellEditor(checkBox);
		this.tblMain.getColumn("isAreaAudited").setEditor(chkEditor);
		this.tblMain.getColumn("isActualAreaAudited").setEditor(chkEditor);
		
		this.tblMain.getColumn("number").getStyleAttributes().setLocked(true);
		this.tblMain.getColumn("roomModel.roomModelType.name").getStyleAttributes().setLocked(true);
		this.tblMain.getColumn("buildArea").getStyleAttributes()
		.setLocked(true);
		this.tblMain.getColumn("roomArea").getStyleAttributes().setLocked(true);
		this.tblMain.getColumn("actualBuildArea").getStyleAttributes()
		.setLocked(false);
		this.tblMain.getColumn("actualRoomArea").getStyleAttributes()
		.setLocked(false);
		
		this.tblMain.getColumn("roomNo").getStyleAttributes()
		.setLocked(false);
		//by tim_gao 计租面积
		this.tblMain.getColumn("tenancyArea").getStyleAttributes()
		.setLocked(true);
//	
//		roomModelBox.setEditable(true);
//		roomModelBox.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelTypeQuery");
//		roomModelBox.setDisplayFormat("$name$");
//		roomModelBox.setCommitFormat("$number");
//		roomModelBox.setEditFormat("$number");
//
//		KDTDefaultCellEditor roomModelEditor = new KDTDefaultCellEditor(roomModelBox);
		//this.tblMain.getColumn("roomModel.roomModelType.name").setEditor(roomModelEditor);
		if (!saleOrg.isIsBizUnit()) {
			this.tblMain.getColumn("actualBuildArea").getStyleAttributes()
			.setLocked(true);
			this.tblMain.getColumn("actualRoomArea").getStyleAttributes()
			.setLocked(true);
			this.tblMain.getColumn("roomModel.roomModelType.name").getStyleAttributes()
			.setLocked(true);
			this.actionSubmit.setEnabled(false);
			this.actionImportExcel.setEnabled(false);
		}
		this.treeMain.setSelectionRow(0);
		
		//ListUI的排序对未绑定字段的Table忽略，这里重简单实现排序
		SimpleKDTSortManager.setTableSortable(tblMain);
//		sm.setSortAuto(true);
//		tblMain.setSortMange(sm);
	}
	
	private void initControl() {
		this.actionImportData.setVisible(true);
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setVisible(false);
		this.actionRemove.setEnabled(false);
		this.actionView.setVisible(false);
		this.actionView.setEnabled(false);
		this.actionQuery.setVisible(false);
		this.actionQuery.setEnabled(false);
		this.actionLocate.setVisible(true);
		this.actionQuery.setEnabled(false);
		this.menuItemImportData.setVisible(false);
	}
	
	protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
	}
	
	protected void refresh(ActionEvent arg0) throws Exception {
		// super.refresh(arg0);
		this.verifySaved(null);
		fillTable();
	}
	
	public boolean verify() {
		List tempList = new ArrayList();
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			if (this.isEditedRow(row)) {
				BigDecimal buildAreaCellValue = (BigDecimal) row.getCell("buildArea").getValue();
				BigDecimal buildArea = buildAreaCellValue == null ? FDCHelper.ZERO : buildAreaCellValue;				
				BigDecimal roomArea = (BigDecimal) row.getCell("roomArea").getValue();
				Object cellName = row.getCell("roomModel.roomModelType.name").getValue();
				if(cellName==null) {
					MsgBox.showInfo("第" + (i + 1) + "行,请选择户型!");
					return false;
				}else if(cellName instanceof String || cellName instanceof BigDecimal){
					RoomInfo room = (RoomInfo)row.getUserObject();
					RoomModelInfo modelInfo = getRoomModelByNameStr(room.getBuilding().getId().toString(),cellName.toString());
					if(modelInfo==null) {
						MsgBox.showInfo("第" + (i + 1) + "行,户型导入不正确!");
						return false;
					}else{
						row.getCell("roomModel.roomModelType.name").setValue(modelInfo);
					}
				}
				
				String roomNo = (String)row.getCell("roomNo").getValue();
				if(roomNo != null && roomNo.length() > 0)
				{
					if(tempList.contains(roomNo))
					{
						MsgBox.showInfo("第" + (i + 1) + "行,实测房号发生重复!");
					
						return false;
					}
					else
					{
						tempList.add(roomNo);
					}
				}


//				录入建筑面积的情况下，套内面积不允许为空
				if(buildAreaCellValue != null  &&  roomArea == null){
					MsgBox.showInfo("第" + (i + 1) + "行,请录入套内面积!");
					return false;
				} else if(buildAreaCellValue != null && buildAreaCellValue.compareTo(FDCHelper.ZERO) == -1)
				{
					MsgBox.showInfo("建筑面积不能为负数!");
					return false;
				}
				if (roomArea == null) {
					roomArea = FDCHelper.ZERO;
				} else if(roomArea != null && roomArea.compareTo(FDCHelper.ZERO) == -1)
				{
					MsgBox.showInfo("套内面积不能为负数!");
					return false;
				}
				if (roomArea.compareTo(buildArea) > 0) {
					MsgBox.showInfo("第" + (i + 1) + "行,套内面积大于建筑面积");
					return false;
				}
				BigDecimal actBuildArea = (BigDecimal) row.getCell(
				"actualBuildArea").getValue();
				if (actBuildArea == null) {
					actBuildArea = FDCHelper.ZERO;
				}else if(actBuildArea != null && actBuildArea.compareTo(FDCHelper.ZERO) == -1)
				{
					MsgBox.showInfo("实测建筑面积不能为负数!");
					return false;
				}
				BigDecimal actRoomArea = (BigDecimal) row.getCell(
				"actualRoomArea").getValue();
				if (actRoomArea == null) {
					actRoomArea = FDCHelper.ZERO;
				}else if(actRoomArea != null && actRoomArea.compareTo(FDCHelper.ZERO) == -1)
				{
					MsgBox.showInfo("实测套内面积不能为负数!");
					return false;
				}
				if (actRoomArea.compareTo(actBuildArea) > 0) {
					MsgBox.showInfo("第" + (i + 1) + "行,实测套内面积大于实测建筑面积");
					return false;
				}
			}
		}
		return true;
	}
	
	protected void tblMain_editStopped(KDTEditEvent e) throws Exception {
		super.tblMain_editStopped(e);
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if (!this.verify()) {
			return;
		}
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			if (this.isEditedRow(row)) {
				RoomInfo room = (RoomInfo) row.getUserObject();
				BigDecimal buildArea = (BigDecimal) row.getCell("buildArea")
				.getValue();
				room.setBuildingArea(buildArea);
				BigDecimal roomArea = (BigDecimal) row.getCell("roomArea")
				.getValue();
				room.setRoomArea(roomArea);
				BigDecimal actBuildArea = (BigDecimal) row.getCell(
				"actualBuildArea").getValue();
				room.setActualBuildingArea(actBuildArea);
				BigDecimal actRoomArea = (BigDecimal) row.getCell(
				"actualRoomArea").getValue();
				room.setActualRoomArea(actRoomArea);
				RoomModelInfo  roomModel = (RoomModelInfo)row.getCell("roomModel.roomModelType.name").getValue();
				room.setRoomModel(roomModel);
				BigDecimal tenancyArea = (BigDecimal) row.getCell(
				"tenancyArea").getValue();
				room.setTenancyArea(tenancyArea);
				
				if(buildArea != null  &&  roomArea != null){
					room.setApportionArea(buildArea.subtract(roomArea));
				}
				
				if(row.getCell("roomNo").getValue() != null)
				{
					String temp = (String)row.getCell("roomNo").getValue();
					room.setRoomNo(temp);
				}
				
				/*
				if (!FDCHelper.isEqual(room.getBuildingArea(), buildArea)) {
					room.setStandardTotalAmount(null);
					room.setBuildPrice(null);
					room.setRoomPrice(null);
				}
				
				if (!FDCHelper.isEqual(room.getRoomArea(), roomArea)) {
					room.setStandardTotalAmount(null);
					room.setBuildPrice(null);
					room.setRoomPrice(null);
				}
				*/
				
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("buildingArea");
				sels.add("roomArea");
				sels.add("actualBuildingArea");
				sels.add("actualRoomArea");
				sels.add("apportionArea");
				sels.add("roomModel");
				sels.add("roomNo");
				//by tim_gao 增加计租面积
				sels.add("tenancyArea");
				
				RoomFactory.getRemoteInstance().updatePartial(room, sels);
			}
		}
		MsgBox.showInfo("保存成功!");
		super.actionSubmit_actionPerformed(e);
	}
	
	public void actionImportExcel_actionPerformed(ActionEvent e)
	throws Exception {
		super.actionImportExcel_actionPerformed(e);
		String fileName = SHEHelper.showExcelSelectDlg(this);
		int numIndex = 4;
		if(this.tblMain.getColumn("subArea").getStyleAttributes().isHided())
			numIndex--;
		if(this.tblMain.getColumn("building").getStyleAttributes().isHided())
			numIndex--;
		if(this.tblMain.getColumn("unit").getStyleAttributes().isHided())
			numIndex--;
		
		this.importExcelToTable2(fileName, this.tblMain, 1, numIndex);
	}
	
	public int importExcelToTable2(String fileName, KDTable table,
			int headRowCount, int keyColumnCount) throws Exception {
		if (headRowCount > 1) {
			MsgBox.showError("多层表头没有实现,你愿意写就来写!");
			SysUtil.abort();
		}
		KDSBook kdsbook = null;
		try {
			
			kdsbook = POIXlsReader.parse2(fileName);
		} catch (Exception e) {
			e.printStackTrace();
			MsgBox.showError("读EXCEL出错,EXCEl格式不匹配!");
			SysUtil.abort();
		}
		if (kdsbook == null) {
			SysUtil.abort();
		}
		//Sheet excelSheet = KDSBookToBook.traslate(kdsbook).getSheet(0);
		KDSSheet excelSheet =kdsbook.getSheet(new Integer(0));
		Map colNameMap = new HashMap();
		//int maxRow = excelSheet.getMaxRowIndex();
		//int maxColumn = excelSheet.getMaxColIndex();		
		int maxRow = excelSheet.getRowCount();
	    int maxColumn = excelSheet.getColumnCount();

		for (int col = 0; col <= maxColumn; col++) {
			String excelColName = excelSheet.getCell(0, col, true).getText();
			colNameMap.put(excelColName, new Integer(col));
		}
		int successCount = 0;
		for (int rowIndex = 1; rowIndex <= maxRow; rowIndex++) {
			String key = "";
			int totalKeyCount = 0;
			int count = 0;
			for (int i = 0; i < table.getColumnCount(); i++) {
				if (count >= keyColumnCount) {
					break;
				}
				totalKeyCount++;
				if (table.getColumn(i).getStyleAttributes().isHided()) {
					continue;
				}
				String colName = (String) table.getHeadRow(0).getCell(i)
				.getValue();
				Integer colInt = (Integer) colNameMap.get(colName);
				if (colInt == null) {
					MsgBox.showError("表头结构不一致!表格上的关键列:" + colName
							+ "在EXCEL中没有出现!");
					SysUtil.abort();
				}
				String text = excelSheet.getCell(rowIndex, colInt.intValue(),
						true).getText();
				if (!StringUtils.isEmpty(text)) {
					key += text;
				}
				count++;
			}
			
			IRow row = getRowByRoomKey(table, key, keyColumnCount);
			if (row == null) {
				continue;
			}
			successCount++;
			for (int i = totalKeyCount; i < table.getColumnCount(); i++) {
				ICell tblCell = row.getCell(i);
				if (tblCell == null || tblCell.getStyleAttributes().isLocked())
					continue;
				String colName = (String) table.getHeadRow(0).getCell(i)
				.getValue();
				Integer colInt = (Integer) colNameMap.get(colName);
				if (colInt == null) {
					continue;
				}
//				Variant cellRawVal = excelSheet.getCell(rowIndex,
//						colInt.intValue(), true).getValue();
//				if (Variant.isNull(cellRawVal)) {
//					continue;
//				}
//				if (cellRawVal.isNumeric()) {
//					BigDecimal colValue = cellRawVal.toBigDecimal();
//					tblCell.setValue(colValue);
//				} else if (cellRawVal.isString()) {
//					String colValue = cellRawVal.toString();
//					tblCell.setValue(colValue);
//				}
				Object cellRawVal = excelSheet.getCell(rowIndex,
						colInt.intValue(), true).getValue();
				if (cellRawVal==null||cellRawVal=="") {
					continue;
				}
				if("户型".equals(colName) || "实测房号".equals(colName))
				{
					tblCell.setValue(cellRawVal.toString());
				}
				else
				{
					tblCell.setValue(FDCHelper.toBigDecimal(cellRawVal));
				}
			}
		}
		return successCount;
	}
	
	private static IRow getRowByRoomKey(KDTable table, String key,
			int keyColumnCount) {
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			String aKey = "";
			int count = 0;
			for (int j = 0; j < table.getColumnCount(); j++) {
				if (count >= keyColumnCount) {
					break;
				}
				if (table.getColumn(j).getStyleAttributes().isHided()) {
					continue;
				}
				Object value = row.getCell(j).getValue();
				if (value != null) {
					aKey += value.toString();
				}
				count++;
			}
			if (aKey.equals(key)) {
				return row;
			}
		}
		return null;
	}
	
	/**
	 * 售前复核操作
	 * */
	public void actionAreaAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAreaAudit_actionPerformed(e);
		this.checkSelected();
		List idList = new ArrayList();
		//先检查保存
		if (this.isEditedTable()) {
			MsgBox.showInfo("请先保存!");
			return;
		}
		
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		for(int i=0; i<selectRows.length; i++){
			IRow row = this.tblMain.getRow(selectRows[i]);
			RoomInfo room = (RoomInfo) row.getUserObject();
			if(room.getRoomModel()==null){
				MsgBox.showInfo("第"+ (1+selectRows[i])+"行的户型为空!");
				return;
			}			
			
			if(FDCHelper.isNullZero(room.getBuildingArea())){
				MsgBox.showInfo("第"+ (1+selectRows[i])+"行的建筑面积为0!");
				return;
			}
			if(FDCHelper.isNullZero(room.getRoomArea())){
				MsgBox.showInfo("第"+ (1+selectRows[i])+"行的套内面积为0!");
				return;
			}
			
			if(row.getUserObject()!=null){
				idList.add(room);
			}
		}
		
		RoomFactory.getRemoteInstance().doAreaAudit(getSelectedIdValues());
		
		if(idList!=null && idList.size()>0){
			try{
				RoomFactory.getRemoteInstance().addRoomAreaChange(idList,RoomAreaChangeTypeEnum.PRESALES);
			}catch(BOSException ex){
				this.handleException(ex);
			}
			
		}
		
		
		refresh(e);
	}
	
	/**
	 * 实测复核操作
	 * */
	public void actionActualAreaAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionActualAreaAudit_actionPerformed(e);
		this.checkSelected();
		List idList = new ArrayList();
		//先检查保存
		if (this.isEditedTable()) {
			MsgBox.showInfo("请先保存!");
			return;
		}
		
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		for(int i=0; i<selectRows.length; i++){
			IRow row = this.tblMain.getRow(selectRows[i]);
			RoomInfo room = (RoomInfo) row.getUserObject();
			if(FDCHelper.isNullZero(room.getActualBuildingArea())){
				MsgBox.showInfo("第"+ (1+selectRows[i])+"行的建筑实测为0!");
				return;
			}
			if(FDCHelper.isNullZero(room.getActualRoomArea())){
				MsgBox.showInfo("第"+ (1+selectRows[i])+"行的套内实测为0!");
				return;
			}
			//必须售前复核过
			if(!room.isIsAreaAudited()){
				MsgBox.showInfo("第"+ (1+selectRows[i])+"行的未售前复核!");
				return;
			}
			
			if(row.getUserObject()!=null){
				idList.add(room);
			}
		}
		
		RoomFactory.getRemoteInstance().doActualAreaAudit(getSelectedIdValues());
		
		if(idList!=null && idList.size()>0){
			try{
				RoomFactory.getRemoteInstance().addRoomAreaChange(idList,RoomAreaChangeTypeEnum.ACTUAL);
			}catch(BOSException ex){
				this.handleException(ex);
			}
			
		}
		refresh(e);
	}
	

	/*
	 * 反售前复核
	 * @ActionEvent
	 */
	public void actionCancelAreaAudit_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		
		//先检查保存
		if (this.isEditedTable()) {
			MsgBox.showInfo("请先保存!");
			return;
		}
		
		if(MsgBox.YES != MsgBox.showConfirm2(this, "该操作会清掉所选房间的标准总价，是否继续？")){
			 return;
		}
		
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		CoreBaseCollection roomColl = new CoreBaseCollection();
		for(int i=0; i<selectRows.length; i++){
			IRow row = this.tblMain.getRow(selectRows[i]);
			RoomInfo room = (RoomInfo) row.getUserObject();
			if(room.isIsActualAreaAudited()){
				MsgBox.showInfo("第"+ (1+selectRows[i])+"行必须先反实测复核!");
				return;
			}

			//检查该房间的认购单是否有未作废的
			if(room.getLastPurchase()!=null){
				MsgBox.showInfo("第"+ (1+selectRows[i])+"行房间已经存在审批后的认购单!");
				return;
			}else{
				EntityViewInfo view = new EntityViewInfo();
				view.getSelector().add("number");
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("room.id",room.getId().toString()));

				filter.getFilterItems().add(new FilterItemInfo("purchaseState",PurchaseStateEnum.CHANGEROOMBLANKOUT_VALUE,CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("purchaseState",PurchaseStateEnum.QUITROOMBLANKOUT_VALUE,CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("purchaseState",PurchaseStateEnum.ADJUSTBLANKOUT_VALUE,CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("purchaseState",PurchaseStateEnum.NOPAYBLANKOUT_VALUE,CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("purchaseState",PurchaseStateEnum.MANUALBLANKOUT_VALUE,CompareType.NOTEQUALS));
				view.setFilter(filter);				
				PurchaseCollection purchases = PurchaseFactory.getRemoteInstance().getPurchaseCollection(view);
				if(purchases!=null && purchases.size()>0) {
					MsgBox.showInfo("第"+ (1+selectRows[i])+"行房间存在未作废的认购单!");
					return;
				}				
			}
			
			//更改所选房间的售前复核状态 ,并且清除房间的定价
			if(room.isIsAreaAudited()) {
				room.setIsAreaAudited(false);
				room.setBuildPrice(null);
				room.setRoomPrice(null);
				room.setStandardTotalAmount(null);
				roomColl.add(room);
			}
		}
		
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("isAreaAudited");
		sels.add("buildPrice");
		sels.add("roomPrice");
		sels.add("standardTotalAmount");
		
		for(int i=0; i<roomColl.size(); i++){
			RoomInfo room = (RoomInfo) roomColl.get(i);
			RoomFactory.getRemoteInstance().updatePartial(room, sels);	
		}
		refresh(e);
	}	
	

	
	/*
	 * 反实测复核
	 * @ActionEvent
	 */
	public void actionCancelActualAudit_actionPerformed(ActionEvent e) throws Exception {
/*
n	已经实测复核不能反售前复核
n	房间存在非作废状态的认购单都不能反售前复核
n	房间已经有补差单或者已经有非作废状态的现售认购单不能反实测复核
n   反售前复核后必须清除房间的定价，包括（buildPrice，roomPrice，standardTotalAmount）

 */
		this.checkSelected();

		//先检查保存
		if (this.isEditedTable()) {
			MsgBox.showInfo("请先保存!");
			return;
		}
		
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		CoreBaseCollection roomColl = new CoreBaseCollection();
		for(int i=0; i<selectRows.length; i++){
			IRow row = this.tblMain.getRow(selectRows[i]);
			RoomInfo room = (RoomInfo) row.getUserObject();

			/*
			//检查该房间的认购单是否有未作废的
			if(room.getLastAreaCompensate()!=null){
				MsgBox.showInfo("第"+ (1+selectRows[i])+"行房间已经存在审批后的补差单!");
				return;
			}else if(room.getLastPurchase()!=null){
				MsgBox.showInfo("第"+ (1+selectRows[i])+"行房间已经存在审批后的认购单!");
				return;
			}else{
				*/
				EntityViewInfo view = new EntityViewInfo();
				view.getSelector().add("number");
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("room.id",room.getId().toString()));
				view.setFilter(filter);
				RoomAreaCompensateCollection areaCompsate = RoomAreaCompensateFactory.getRemoteInstance().getRoomAreaCompensateCollection(view);
				if(areaCompsate!=null && areaCompsate.size()>0){
					MsgBox.showInfo("第"+ (1+selectRows[i])+"行房间已存在补差单!");
					return;
				}
				
				filter.getFilterItems().add(new FilterItemInfo("purchaseState",PurchaseStateEnum.CHANGEROOMBLANKOUT_VALUE,CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("purchaseState",PurchaseStateEnum.QUITROOMBLANKOUT_VALUE,CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("purchaseState",PurchaseStateEnum.ADJUSTBLANKOUT_VALUE,CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("purchaseState",PurchaseStateEnum.NOPAYBLANKOUT_VALUE,CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("purchaseState",PurchaseStateEnum.MANUALBLANKOUT_VALUE,CompareType.NOTEQUALS));
				//--反实测复核对现售才有影响 BT337527 by zhicheng_jin
				filter.getFilterItems().add(new FilterItemInfo("sellType",SellTypeEnum.LOCALESELL_VALUE));
				//---
				view.setFilter(filter);				
				PurchaseCollection purchases = PurchaseFactory.getRemoteInstance().getPurchaseCollection(view);
				if(purchases!=null && purchases.size()>0) {
					MsgBox.showInfo("第"+ (1+selectRows[i])+"行房间存在未作废的现售认购单!");
					return;
				}
				
				//--售后补差后不允许再反实测复核
				FilterInfo areaCompFilter = new FilterInfo();
				areaCompFilter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
				if(RoomAreaCompensateFactory.getRemoteInstance().exists(areaCompFilter)){
					MsgBox.showInfo("第"+ (1+selectRows[i])+"行房间存在补差单!");
					return;
				}
				
			// --- 反实测复核时不用清空 房间的单价和总价.  ---- zhicheng_jin  090616
			//更改所选房间的售前复核状态 ,并且清除房间的定价
			if(room.isIsActualAreaAudited()) {
				room.setIsActualAreaAudited(false);
//				room.setBuildPrice(null);
//				room.setRoomPrice(null);
//				room.setStandardTotalAmount(null);
				roomColl.add(room);
			}
		}
		
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("isActualAreaAudited");
		for(int i=0; i<roomColl.size(); i++){
			RoomInfo room = (RoomInfo) roomColl.get(i);
			RoomFactory.getRemoteInstance().updatePartial(room, sels);	
		}
		
		refresh(e);
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
	
	
	private RoomModelInfo getRoomModelByNameStr(String buildingId,String cellName){
		if(cellName==null || cellName.trim().equals("")) return null;
		if(buildingId==null || buildingId.trim().equals("")) return null;
		
		if(mapRoomModel==null) {
			try{
				mapRoomModel = CommerceHelper.getRoomModelByBuildingIdMap(buildingId);
			}catch(BOSException e) {this.abort();}
		}
    	return (RoomModelInfo)mapRoomModel.get(cellName.trim());
	}
	
	
	
}