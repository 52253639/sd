/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTGroupManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseDetailFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class PrePurchaseDetailUI extends AbstractPrePurchaseDetailUI
{
    private static final Logger logger = CoreUIObject.getLogger(PrePurchaseDetailUI.class);

    private String allBuildingIds = "null";			//所包含楼栋id
    
    Map filterMap;
    Map roomIdMap;
    
    public PrePurchaseDetailUI() throws Exception
    {
        super();
    }
    
    
    public void onLoad() throws Exception {
		super.onLoad();
		initTree();
		this.actionAddNew.setVisible(false);
		this.actionView.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuEdit.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.treeMain.setSelectionRow(0);
		
		this.actionLocate.setVisible(false);
		
    }
    protected CommonQueryDialog initCommonQueryDialog() {
    	
    	CommonQueryDialog commonpanel =   super.initCommonQueryDialog();
    	commonpanel.setShowSorter(false);
    	commonpanel.setShowFilter(false);

    	return commonpanel;
    }
    
    
    protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getUnitTree(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys,SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit()));
		
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}
    
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		this.tblMain.removeHeadRow(1);
		allBuildingIds = "null";
		int unitNumber = 0; // 单元过滤，代表无单元过滤
		TreeNode buildingNode = (TreeNode) this.treeMain.getLastSelectedPathComponent();
		if (buildingNode != null) {
			DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) buildingNode;
			if (thisNode.getUserObject() != null) {
				if (thisNode.getUserObject() instanceof BuildingInfo) {
					BuildingInfo building = (BuildingInfo) thisNode.getUserObject();
					
					 allBuildingIds += "," + building.getId().toString();
				} else if (thisNode.getUserObject() instanceof Integer) { // 已作废
					DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode) thisNode.getParent();
					BuildingInfo parentBuilding = (BuildingInfo) parentNode.getUserObject();
					
					 allBuildingIds += "," + parentBuilding.getId().toString();
					unitNumber = ((Integer) thisNode.getUserObject()).intValue();
				} else if (thisNode.getUserObject() instanceof BuildingUnitInfo) { //
					DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode) thisNode.getParent();
					BuildingInfo parentBuilding = (BuildingInfo) parentNode.getUserObject();
					
					 allBuildingIds += "," + parentBuilding.getId().toString();
					unitNumber = ((BuildingUnitInfo) thisNode.getUserObject()).getSeq();
				} else {
					this.getAllBuildingIds(buildingNode);
				}
			}
		} else {
			this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
			this.getAllBuildingIds((TreeNode) this.treeMain.getModel().getRoot());
		}
		
		FilterInfo buildInfo = new FilterInfo();
		buildInfo.getFilterItems().add(new FilterItemInfo("room.fsellstate",RoomSellStateEnum.PREPURCHASE_VALUE));
		buildInfo.getFilterItems().add(new FilterItemInfo("ROOM.FBuildingID", allBuildingIds, CompareType.INCLUDE));
		
		if(unitNumber!=0) {
			buildInfo.getFilterItems().add(new FilterItemInfo("BUILDINGUNIT.FSEQ", unitNumber+""));
		}
		filterMap = new HashMap();
		filterMap.put("filter", buildInfo);
		
    	this.execQuery();
    }
    
    protected void execQuery() {
		//this.tblMain.removeRows();
    	tblMain.getColumn("amount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
    	tblMain.getColumn("buildingArea").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
    	tblMain.getColumn("roomArea").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
    	tblMain.getColumn("actualBuildingArea").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
    	tblMain.getColumn("actualRoomArea").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
    	tblMain.getColumn("recEarnestMoney").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
    	tblMain.getColumn("standardPrice").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		super.execQuery();
		
		try {
			Map dataMap = PrePurchaseDetailFacadeFactory.getRemoteInstance().getPrePurchaseData(filterMap);
			
			IRowSet querySet = (IRowSet)dataMap.get("querySet");
			IRowSet prePurchaseSet = (IRowSet)dataMap.get("prePurchaseSet");
			IRowSet amountSet = (IRowSet)dataMap.get("amountSet");
			IRowSet amount2Set = (IRowSet)dataMap.get("amount2Set");

			fillQueryData(querySet);
			
			roomIdMap = new HashMap();
			for(int i=0;i<this.tblMain.getRowCount();i++) {
				IRow row = this.tblMain.getRow(i);
				roomIdMap.put(row.getCell("id").getValue(),row);
			}
			
			fillPrePurchase(prePurchaseSet);
			fillRecEarnestMoney(amountSet,amount2Set);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit()){
			for(int i = 0;i<tblMain.getRowCount();i++){
				IRow row = this.tblMain.getRow(i);
				row.getCell("orgUnit.name").setValue(SysContext.getSysContext().getCurrentSaleUnit());
			}
		}
		
		this.tblMain.getGroupManager().setTotalize(true);
		IRow row0 = (IRow)tblMain.getGroupManager().getStatRowTemplate(-1);
		row0.getStyleAttributes().setBackground(CommerceHelper.BK_COLOR_MUST);
		int firstIndex = 0;
		for(int i=0;i<this.tblMain.getColumnCount();i++) 
			if(!this.tblMain.getColumn(i).getStyleAttributes().isHided()) { 
				firstIndex = i;			 break;
			}
		row0.getCell(2).setValue("");
		row0.getCell(3).setValue("");
		row0.getCell(4).setValue("");
		row0.getCell(5).setValue("");
		row0.getCell(firstIndex).setValue("总计:");
		row0.getCell("buildingArea").setExpressions(KDTGroupManager.STAT_SUM);
		row0.getCell("roomArea").setExpressions(KDTGroupManager.STAT_SUM);
		row0.getCell("actualBuildingArea").setExpressions(KDTGroupManager.STAT_SUM);
		row0.getCell("actualRoomArea").setExpressions(KDTGroupManager.STAT_SUM);
		row0.getCell("amount").setExpressions(KDTGroupManager.STAT_SUM);
		row0.getCell("standardPrice").setExpressions(KDTGroupManager.STAT_SUM);
		row0.getCell("recEarnestMoney").setExpressions(KDTGroupManager.STAT_SUM);
		row0.getStyleAttributes().setLocked(true);
		this.tblMain.getGroupManager().group();			
    }
    
    protected void fillQueryData(IRowSet querySet)throws Exception{
    	while(querySet.next()){
    		IRow row = this.tblMain.addRow();
    		
    		//初始化建筑面积、套内面积、实测建筑面积、实测套内面积、实收预定金、实收定金、标准总价
    		row.getCell("buildingArea").setValue(new BigDecimal("0.00"));
    		row.getCell("roomArea").setValue(new BigDecimal("0.00"));
    		row.getCell("actualBuildingArea").setValue(new BigDecimal("0.00"));
    		row.getCell("actualRoomArea").setValue(new BigDecimal("0.00"));
    		row.getCell("amount").setValue(new BigDecimal("0.00"));
    		row.getCell("recEarnestMoney").setValue(new BigDecimal("0.00"));
    		row.getCell("standardPrice").setValue(new BigDecimal("0.00"));
    		
    		
    		row.getCell("id").setValue(querySet.getString("roomId"));
    		row.getCell("orgUnit.name").setValue(querySet.getString("orgName"));
    		row.getCell("sellProject.name").setValue(querySet.getString("projectName"));
    		row.getCell("building.name").setValue(querySet.getString("builderName"));
    		row.getCell("productType.name").setValue(querySet.getString("typeName"));
    		row.getCell("subareaName").setValue(querySet.getString("subArea"));
//    		row.getCell("orgUnit.name").setValue(querySet.getString("productDetail"));
    		row.getCell("unit").setValue(querySet.getString("unit"));
    		row.getCell("floor").setValue(querySet.getString("floor"));
    		if(querySet.getString("buildingArea")!=null){
        		row.getCell("buildingArea").setValue(querySet.getString("buildingArea"));
    		}
    		if(querySet.getString("roomArea")!=null){
        		row.getCell("roomArea").setValue(querySet.getString("roomArea"));
    		}
    		if(querySet.getString("actualBuildingArea")!=null){
        		row.getCell("actualBuildingArea").setValue(querySet.getString("actualBuildingArea"));
    		}
    		if(querySet.getString("actualRoomArea")!=null){
        		row.getCell("actualRoomArea").setValue(querySet.getString("actualRoomArea"));
    		}
    		row.getCell("number").setValue(querySet.getString("number"));
    		row.getCell("standardPrice").setValue(querySet.getString("standardAmount"));
    		row.getCell("roomModel.name").setValue(querySet.getString("modelName"));
    	}
    }
    
    protected void fillPrePurchase(IRowSet prePurchaseSet) throws Exception
    {
       if(roomIdMap.size()==0) return;	
		
			while (prePurchaseSet.next()) {
				String roomId = prePurchaseSet.getString("roomid");
				Date purchaseDate = prePurchaseSet.getDate("purchaseDate");
				Date valaidDate = prePurchaseSet.getDate("valaidDate");
				String customerNames = prePurchaseSet.getString("customerNames");
				customerNames = customerNames.replace(',', ';');
				IRow row = (IRow)roomIdMap.get(roomId);
				if(row==null) continue;
				row.getCell("purchaseDate").setValue(purchaseDate);
				row.getCell("valaidDate").setValue(valaidDate);
				row.getCell("customerNames").setValue(customerNames);
			}

    }
    
    protected void fillRecEarnestMoney(IRowSet amountSet,IRowSet amount2Set) throws Exception
    {
    	if(roomIdMap.size()==0)
    	{
    		return;
    	}
    	while (amountSet.next()) {
			String roomId = amountSet.getString("roomid");
			IRow row = (IRow)roomIdMap.get(roomId);
			if(row==null) continue;
			row.getCell("recEarnestMoney").setValue(amountSet.getString("amount"));
		}
    	while (amount2Set.next()) {
			String roomId = amount2Set.getString("roomid");
			IRow row = (IRow)roomIdMap.get(roomId);
			if(row==null) continue;
			row.getCell("amount").setValue(amount2Set.getString("amount"));
		}
    }
    
    private void addToCellCount(IRow row,String cellName,BigDecimal ctrlAmount) {		
		Object cellObject = row.getCell(cellName).getValue();
		if(cellObject!=null && cellObject instanceof BigDecimal){
			BigDecimal oldCount = (BigDecimal)cellObject;
			row.getCell(cellName).setValue(oldCount.add(ctrlAmount));
		}else{
			row.getCell(cellName).setValue(ctrlAmount);			
		}		
	}
    
    /**
	 * 查询所有的楼栋id
	 * @param treeNode
	 */
	private void getAllBuildingIds(TreeNode treeNode) {	
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode)treeNode;
		if(thisNode.getUserObject() instanceof BuildingInfo){
			BuildingInfo building = (BuildingInfo)thisNode.getUserObject();
			allBuildingIds += "," + building.getId().toString();
		}
			
		if(!treeNode.isLeaf()){
			int childCount = treeNode.getChildCount();
			while(childCount>0) {				
				getAllBuildingIds(treeNode.getChildAt(childCount-1));		
				childCount --;
			}	
		}	
	}

    public void storeFields()
    {
        super.storeFields();
    }
    
    protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
    	
    }

	protected ICoreBase getBizInterface() throws Exception {
		return RoomFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return RoomEditUI.class.getName();
	}


}