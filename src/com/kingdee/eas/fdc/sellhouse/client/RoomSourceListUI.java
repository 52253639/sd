/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BirdEyeFactory;
import com.kingdee.eas.fdc.sellhouse.BirdEyeInfo;
import com.kingdee.eas.fdc.sellhouse.BirdEyePicFactory;
import com.kingdee.eas.fdc.sellhouse.BirdEyePicInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.DecorationStandardFactory;
import com.kingdee.eas.fdc.sellhouse.DecorationStandardInfo;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.PossessionStandardFactory;
import com.kingdee.eas.fdc.sellhouse.PossessionStandardInfo;
import com.kingdee.eas.fdc.sellhouse.PriceAdjustEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PushManageHisEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectResourceEnum;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
//只是把下方的控件在元数据上隐藏了
public class RoomSourceListUI extends AbstractRoomSourceListUI
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5985273488918028333L;

	private static final Logger logger = CoreUIObject
			.getLogger(RoomDesListUI.class);

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	RoomDisplaySetting setting = new RoomDisplaySetting();
	Integer floor = null;
	Integer unit = null;
	BuildingUnitInfo buildUnit = null;
	BuildingFloorEntryInfo buildFloorEntry = null;
	BuildingInfo build = null;
	
	Map orgMap = FDCSysContext.getInstance().getOrgMap();
	RoomInfo selectedRoom = null;

	/**
	 * output class constructor
	 */
	public RoomSourceListUI() throws Exception
	{
		super();
	}

	protected String getEditUIModal()
	{
		return UIFactoryName.NEWTAB;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields()
	{
		super.storeFields();
	}

	protected ITreeBase getTreeInterface() throws Exception
	{
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception
	{
		return RoomFactory.getRemoteInstance();
	}


	protected void initTree() throws Exception
	{
		this.treeMain.setModel(CRMTreeHelper.getBuildingTree(actionOnLoad,true));
//		 this.treeMain.setModel(SHEHelper.getPlanisphere(this.actionOnLoad));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel()
				.getRoot());
		this.tblMain.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
	}

	private void setColumnNotHidden()
	{
		tblMain.getColumn("subarea").getStyleAttributes().setHided(false);
		tblMain.getColumn("building").getStyleAttributes().setHided(false);
		tblMain.getColumn("unit.name").getStyleAttributes().setHided(false);
	}
	
	public void actionInit_actionPerformed(ActionEvent e) throws Exception {	
		String str="确认要执行数据升级吗？\r\n\r\n本次是房间交房标准和装修标准资料的升级，\r\n\r\n不升级房间将不能更改交房标准和装修标准资料!";		
		if(MsgBox.showConfirm2(str)==MsgBox.YES){	
			String message = null;
			CoreBaseCollection coreCol = new CoreBaseCollection();
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql(" select distinct FDeliverHouseStandard from T_SHE_Room where ");
			builder.appendSql(" FDeliverHouseStandard not in (select FName_l2 from T_SHE_PossessionStandard)");
			IRowSet nameSet = null;	
			int iCount =0;
			try {
				nameSet = builder.executeQuery();
				while (nameSet.next()) {
					if(nameSet.getString("FDeliverHouseStandard")!=null){
						iCount++;
						PossessionStandardInfo poStd = new PossessionStandardInfo();
						poStd.setNumber(nameSet.getString("FDeliverHouseStandard"));
						poStd.setName(nameSet.getString("FDeliverHouseStandard"));
						coreCol.add(poStd);						
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(iCount>0){
				PossessionStandardFactory.getRemoteInstance().addnew(coreCol);				
				String udateStr = " update T_SHE_Room ";				
				udateStr+="set FPosseStdID=(select FID from T_SHE_PossessionStandard AA WHERE AA.FName_l2=T_SHE_Room.FDeliverHouseStandard)";
				udateStr+="where FID in (select FID from T_SHE_Room where  FDeliverHouseStandard  in (select FName_l2 from T_SHE_PossessionStandard))";
				builder.clear();
				builder.appendSql(udateStr);
				builder.execute();				
				message="交房标准成功升级了"+iCount+"条数据";
			}	
			coreCol.clear();
			builder.clear();
			
			builder.appendSql(" select distinct FFitmentStandard from T_SHE_Room where ");
			builder.appendSql(" FFitmentStandard not in (select FName_l2 from T_SHE_DecorationStandare) ");
			nameSet = null;	
			iCount =0;
			try {
				nameSet = builder.executeQuery();
				while (nameSet.next()) {
					if(nameSet.getString("FFitmentStandard")!=null){
						iCount++;
						DecorationStandardInfo decorInfo = new DecorationStandardInfo();
						decorInfo.setNumber(nameSet.getString("FFitmentStandard"));
						decorInfo.setName(nameSet.getString("FFitmentStandard"));
						coreCol.add(decorInfo);						
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(iCount>0){
				DecorationStandardFactory.getRemoteInstance().addnew(coreCol);				
				String udateStr = " update T_SHE_Room ";				
				udateStr+="set FDecoraStdID=(select FID from T_SHE_DecorationStandare AA WHERE AA.FName_l2=T_SHE_Room.FFitmentStandard)";
				udateStr+="where FID in (select FID from T_SHE_Room where  FFitmentStandard  in (select FName_l2 from T_SHE_DecorationStandare))";
				builder.clear();
				builder.appendSql(udateStr);
				builder.execute();				
				message+="\r\n装修标准成功升级了"+iCount+"条数据";
			}	
			
			if(message!=null){
				MsgBox.showInfo(message);
			}
			else{
				MsgBox.showInfo("已经升级或者房间均没有设置交房标准和装修标准！");
			}
			
		}		
	}
	
	public void forRefresh() throws BOSException{
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();

if (node == null)
{
	return;
}

	FilterInfo filter = new FilterInfo();
	setColumnNotHidden();


//	RoomDisplaySetViewUI.insertUIToScrollPanel(this.kDScrollPane1);
	this.mainQuery.getSorter().clear();
	floor = null;
	buildUnit = null;
	build = null;
	buildFloorEntry = null;
	selectedRoom = null;
	this.getUIContext().put("unit", null);
	if (node.getUserObject() instanceof BuildingUnitInfo)
	{
		BuildingUnitInfo unit = (BuildingUnitInfo) node.getUserObject();
		BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
				.getParent()).getUserObject();
		String buildingId = building.getId().toString();
		filter.getFilterItems().add(
				new FilterItemInfo("building.id", buildingId));
		filter.getFilterItems()
				.add(
						new FilterItemInfo("buildUnit.id", unit.getId()
								.toString()));
		tblMain.getColumn("subarea").getStyleAttributes().setHided(true);
		tblMain.getColumn("building").getStyleAttributes().setHided(true);
		tblMain.getColumn("unit.name").getStyleAttributes().setHided(true);
		
		this.getUIContext().put("unit", node.getUserObject());
		// SHEHelper.fillRoomTableByNode(this.tblGraph,node, null, null);
		//不用销售组织判断,改为售楼组织
		FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
		String idStr =null;
		if(null!= orgUnit.getId()){
			idStr = orgUnit.getId().toString();
		}
		if(null==orgMap.get(idStr)){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.btnRoomDefine.setEnabled(false);
			this.btRoomImport.setEnabled(false);
//			this.actionRemove.setEnabled(false);
//			this.actionCreateRoom.setEnabled(false);
		
		}else{
			this.actionAddNew.setEnabled(true);
			this.actionEdit.setEnabled(true);
			this.btnRoomDefine.setEnabled(true);
			this.btRoomImport.setEnabled(true);
			if (this.tblGraph.getRowCount() < 1)
			{
//				this.btnSeqAdjustmentShow.setEnabled(false);
			} else
			{
//				this.btnSeqAdjustmentShow.setEnabled(true);
				buildUnit = unit;
			}
		}
//		if (saleOrg.isIsBizUnit())
//		{
//			this.actionAddNew.setEnabled(true);
//			if (this.tblGraph.getRowCount() < 1)
//			{
//				this.btnSeqAdjustmentShow.setEnabled(false);
//			} else
//			{
//				this.btnSeqAdjustmentShow.setEnabled(true);
//				buildUnit = unit;
//			}
//		}

	} else if (node.getUserObject() instanceof BuildingInfo)
	{
		BuildingInfo building = (BuildingInfo) node.getUserObject();
		// ---可查看非明细节点的数据时,这里不区分楼栋是否分单元 zhicheng_jin 090228
		String buildingId = building.getId().toString();
		filter.getFilterItems().add(
				new FilterItemInfo("building.id", buildingId));
		this.mainQuery.getSorter().add(new SorterItemInfo("unit"));
		if (building.getUnitCount() == 0)
		{
			tblMain.getColumn("unit.name").getStyleAttributes().setHided(
					true);
		}
		tblMain.getColumn("subarea").getStyleAttributes().setHided(true);
		tblMain.getColumn("building").getStyleAttributes().setHided(true);
		// SHEHelper.fillRoomTableByNode(this.tblGraph,node, null, null);
		
		//不用销售组织判断,改为售楼组织
		FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
		String idStr =null;
		if(null!= orgUnit.getId()){
			idStr = orgUnit.getId().toString();
		}
		if(null==orgMap.get(idStr)){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.btnRoomDefine.setEnabled(false);
			this.btRoomImport.setEnabled(false);
		
		}else{
			this.actionAddNew.setEnabled(true);
			this.actionEdit.setEnabled(true);
			this.btnRoomDefine.setEnabled(true);
			this.btRoomImport.setEnabled(true);
			if (this.tblGraph.getRowCount() < 1)
			{
//				this.btnSeqAdjustmentShow.setEnabled(false);
			} else
			{
//				this.btnSeqAdjustmentShow.setEnabled(true);
				build = building;
			}
		}
//		if (saleOrg.isIsBizUnit())
//		{
//			this.actionAddNew.setEnabled(true);
//			if (this.tblGraph.getRowCount() < 1)
//			{
//				this.btnSeqAdjustmentShow.setEnabled(false);
//			} else
//			{
//				this.btnSeqAdjustmentShow.setEnabled(true);
//				build = building;
//			}
//		}
	} else if (node.getUserObject() instanceof SubareaInfo)
	{ // 分区
		SubareaInfo subarea = (SubareaInfo) node.getUserObject();
		filter.getFilterItems().add(
				new FilterItemInfo("building.subarea.id", subarea.getId()
						.toString()));
		this.mainQuery.getSorter().add(new SorterItemInfo("building.id"));
		this.mainQuery.getSorter().add(new SorterItemInfo("unit"));

		tblMain.getColumn("subarea").getStyleAttributes().setHided(true);
		//不用销售组织判断,改为售楼组织
		FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
		String idStr =null;
		if(null!= orgUnit.getId()){
			idStr = orgUnit.getId().toString();
		}
		if(null==orgMap.get(idStr)){
//			this.actionAddNew.setEnabled(false);
//			this.actionEdit.setEnabled(false);
//			this.actionRemove.setEnabled(false);
//			this.actionCreateRoom.setEnabled(false);
		
		}else{
			this.actionAddNew.setEnabled(false);
		}
//		if (saleOrg.isIsBizUnit())
//		{
//			this.actionAddNew.setEnabled(false);
//		}
//		this.btnSeqAdjustmentShow.setEnabled(false);
	} else if (node.getUserObject() instanceof SellProjectInfo)
	{ // 销售项目
		SellProjectInfo sellProject = (SellProjectInfo) node
				.getUserObject();
		filter.getFilterItems().add(
				new FilterItemInfo("building.sellProject.id", sellProject
						.getId().toString()));

		this.mainQuery.getSorter().add(
				new SorterItemInfo("building.subarea.id"));
		this.mainQuery.getSorter().add(new SorterItemInfo("building.id"));
		this.mainQuery.getSorter().add(new SorterItemInfo("unit"));
		if (sellProject.getSubarea() == null
				|| sellProject.getSubarea().isEmpty())
		{
			tblMain.getColumn("subarea").getStyleAttributes()
					.setHided(true);
		}
		//不用销售组织判断,改为售楼组织
		FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
		String idStr =null;
		if(null!= orgUnit.getId()){
			idStr = orgUnit.getId().toString();
		}
		if(null==orgMap.get(idStr)){
//			this.actionAddNew.setEnabled(false);
//			this.actionEdit.setEnabled(false);
//			this.actionRemove.setEnabled(false);
//			this.actionCreateRoom.setEnabled(false);
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.btnRoomDefine.setEnabled(false);
			this.btRoomImport.setEnabled(false);
		
		}else{
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(true);
			this.btnRoomDefine.setEnabled(true);
			this.btRoomImport.setEnabled(true);
		}
//		if (saleOrg.isIsBizUnit())
//		{
//			// this.actionAddNew.setEnabled(true);
//			this.actionAddNew.setEnabled(false);// Add By wenyaowei
//			// 2009-06-05
//		}
//		this.btnSeqAdjustmentShow.setEnabled(false);
	} else
	{
		filter.getFilterItems().add(new FilterItemInfo("id", null));
		tblGraph.removeColumns();
		tblGraph.removeHeadRows();
		tblGraph.removeRows();
		this.actionAddNew.setEnabled(false);
//		this.btnSeqAdjustmentShow.setEnabled(false);
	}
	this.mainQuery.setFilter(filter);
	// this.mainQuery.getSorter().add(new SorterItemInfo("floor"));
	// this.mainQuery.getSorter().add(new SorterItemInfo("serialNumber"));

	this.mainQuery.getSorter().add(new SorterItemInfo("number"));
	this.tblMain.removeRows();


	if (node.getUserObject() instanceof BuildingUnitInfo
			|| node.getUserObject() instanceof BuildingInfo
			|| node.getUserObject() instanceof SubareaInfo
			|| node.getUserObject() instanceof SellProjectInfo)
		
		SHEHelper.fillRoomListTableByNode(this.tblGraph, node, null,
				setting, this.kDScrollPane1);
		
	//不用销售组织判断,改为售楼组织
	FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
	String idStr =null;
	if(null!= orgUnit.getId()){
		idStr = orgUnit.getId().toString();
	}
	if(null==orgMap.get(idStr)){
//		this.actionAddNew.setEnabled(false);
//		this.actionEdit.setEnabled(false);
//		this.actionRemove.setEnabled(false);
//		this.actionCreateRoom.setEnabled(false);
	
	}else{
		this.actionAddNew.setEnabled(true);
		if (this.tblGraph.getRowCount() < 1)
		{
//			this.btnSeqAdjustmentShow.setEnabled(false);
		} 
	}


	}
	public boolean isIgnoreRowCount() {
		return false;
	}
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception
	{
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		
		if (node == null)
		{
			return;
		}
		int index = kDTabbedPane1.getSelectedIndex();
		this.mainQuery.getSorter().clear();
		this.mainQuery.getSorter().add(new SorterItemInfo("building.id")); 
    	
		this.mainQuery.getSorter().add(new SorterItemInfo("buildUnit.seq"));
		this.mainQuery.getSorter().add(new SorterItemInfo("floor"));
		this.mainQuery.getSorter().add(new SorterItemInfo("serialNumber"));
		//by tim _gao  图与查询分开写 ，不合起来写了解决性能问题
    	if(index ==0 ){
    		FilterInfo filter = new FilterInfo();
    		setColumnNotHidden();


//    		RoomDisplaySetViewUI.insertUIToScrollPanel(this.kDScrollPane1);
    	
    		floor = null;
    		buildUnit = null;
    		build = null;
    		buildFloorEntry = null;
    		selectedRoom = null;
    		this.getUIContext().put("unit", null);
    		if (node.getUserObject() instanceof BuildingUnitInfo)
    		{
    			kDPanel1.remove(scrollPanel); //add by wanping
    			kDPanel1.add(tblGraph); //add by wanping
    			BuildingUnitInfo unit = (BuildingUnitInfo) node.getUserObject();
    			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
    					.getParent()).getUserObject();
    			String buildingId = building.getId().toString();
    			filter.getFilterItems().add(
    					new FilterItemInfo("building.id", buildingId));
    			filter.getFilterItems()
    					.add(
    							new FilterItemInfo("buildUnit.id", unit.getId()
    									.toString()));
    			tblMain.getColumn("subarea").getStyleAttributes().setHided(true);
    			tblMain.getColumn("building").getStyleAttributes().setHided(true);
    			tblMain.getColumn("unit.name").getStyleAttributes().setHided(true);
    			
    			this.getUIContext().put("unit", node.getUserObject());
    			// SHEHelper.fillRoomTableByNode(this.tblGraph,node, null, null);
    			//不用销售组织判断,改为售楼组织
    			FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
    			String idStr =null;
    			if(null!= orgUnit.getId()){
    				idStr = orgUnit.getId().toString();
    			}
    			if(null==orgMap.get(idStr)){
    				this.actionAddNew.setEnabled(false);
    				this.actionEdit.setEnabled(false);
    				this.btnRoomDefine.setEnabled(false);
    				this.btRoomImport.setEnabled(false);
//    				this.actionRemove.setEnabled(false);
//    				this.actionCreateRoom.setEnabled(false);
    			
    			}else{
    				this.actionAddNew.setEnabled(true);
    				this.actionEdit.setEnabled(true);
    				this.btnRoomDefine.setEnabled(true);
    				this.btRoomImport.setEnabled(true);
    				if (this.tblGraph.getRowCount() < 1)
    				{
//    					this.btnSeqAdjustmentShow.setEnabled(false);
    				} else
    				{
//    					this.btnSeqAdjustmentShow.setEnabled(true);
    					buildUnit = unit;
    				}
    			}
//    			if (saleOrg.isIsBizUnit())
//    			{
//    				this.actionAddNew.setEnabled(true);
//    				if (this.tblGraph.getRowCount() < 1)
//    				{
//    					this.btnSeqAdjustmentShow.setEnabled(false);
//    				} else
//    				{
//    					this.btnSeqAdjustmentShow.setEnabled(true);
//    					buildUnit = unit;
//    				}
//    			}

    		} else if (node.getUserObject() instanceof BuildingInfo)
    		{
    			kDPanel1.remove(scrollPanel); //add by wanping
    			kDPanel1.add(tblGraph); //add by wanping
    			BuildingInfo building = (BuildingInfo) node.getUserObject();
    			// ---可查看非明细节点的数据时,这里不区分楼栋是否分单元 zhicheng_jin 090228
    			String buildingId = building.getId().toString();
    			filter.getFilterItems().add(
    					new FilterItemInfo("building.id", buildingId));
//    			this.mainQuery.getSorter().add(new SorterItemInfo("unit"));
    			if (building.getUnitCount() == 0)
    			{
    				tblMain.getColumn("unit.name").getStyleAttributes().setHided(
    						true);
    			}
    			tblMain.getColumn("subarea").getStyleAttributes().setHided(true);
    			tblMain.getColumn("building").getStyleAttributes().setHided(true);
    			// SHEHelper.fillRoomTableByNode(this.tblGraph,node, null, null);
    			
    			//不用销售组织判断,改为售楼组织
    			FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
    			String idStr =null;
    			if(null!= orgUnit.getId()){
    				idStr = orgUnit.getId().toString();
    			}
    			if(null==orgMap.get(idStr)){
    				this.actionAddNew.setEnabled(false);
    				this.actionEdit.setEnabled(false);
    				this.btnRoomDefine.setEnabled(false);
    				this.btRoomImport.setEnabled(false);
    			
    			}else{
    				this.actionAddNew.setEnabled(true);
    				this.actionEdit.setEnabled(true);
    				this.btnRoomDefine.setEnabled(true);
    				this.btRoomImport.setEnabled(true);
    				if (this.tblGraph.getRowCount() < 1)
    				{
//    					this.btnSeqAdjustmentShow.setEnabled(false);
    				} else
    				{
//    					this.btnSeqAdjustmentShow.setEnabled(true);
    					build = building;
    				}
    			}
//    			if (saleOrg.isIsBizUnit())
//    			{
//    				this.actionAddNew.setEnabled(true);
//    				if (this.tblGraph.getRowCount() < 1)
//    				{
//    					this.btnSeqAdjustmentShow.setEnabled(false);
//    				} else
//    				{
//    					this.btnSeqAdjustmentShow.setEnabled(true);
//    					build = building;
//    				}
//    			}
    		} else if (node.getUserObject() instanceof SubareaInfo)
    		{ // 分区
    			kDPanel1.remove(scrollPanel); //add by wanping
    			kDPanel1.add(tblGraph); //add by wanping
    			SubareaInfo subarea = (SubareaInfo) node.getUserObject();
    			filter.getFilterItems().add(
    					new FilterItemInfo("building.subarea.id", subarea.getId()
    							.toString()));
//    			this.mainQuery.getSorter().add(new SorterItemInfo("building.id"));
//    			this.mainQuery.getSorter().add(new SorterItemInfo("unit"));

    			tblMain.getColumn("subarea").getStyleAttributes().setHided(true);
    			//不用销售组织判断,改为售楼组织
    			FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
    			String idStr =null;
    			if(null!= orgUnit.getId()){
    				idStr = orgUnit.getId().toString();
    			}
    			if(null==orgMap.get(idStr)){
//    				this.actionAddNew.setEnabled(false);
//    				this.actionEdit.setEnabled(false);
//    				this.actionRemove.setEnabled(false);
//    				this.actionCreateRoom.setEnabled(false);
    			
    			}else{
    				this.actionAddNew.setEnabled(false);
    			}
//    			if (saleOrg.isIsBizUnit())
//    			{
//    				this.actionAddNew.setEnabled(false);
//    			}
//    			this.btnSeqAdjustmentShow.setEnabled(false);
    		} else if (node.getUserObject() instanceof SellProjectInfo)
    		{ // 销售项目
    			kDPanel1.remove(tblGraph); //add by wanping
    			BirdEyeShowUI birdui = new BirdEyeShowUI();
    			birdui.showBirdEyeLable(BirdEyePanel2, node,pnlMain,kDPanel1); //add by wanping
    			scrollPanel.setViewportView(BirdEyePanel2);//add by wanping
    			kDPanel1.add(scrollPanel); //add by wanping
//    			scrollPanel.repaint(); //add by wanping
    			SellProjectInfo sellProject = (SellProjectInfo) node
    					.getUserObject();
    			filter.getFilterItems().add(
    					new FilterItemInfo("building.sellProject.id", sellProject
    							.getId().toString()));

//    			this.mainQuery.getSorter().add(
//    					new SorterItemInfo("building.subarea.id"));
//    			this.mainQuery.getSorter().add(new SorterItemInfo("building.id"));
//    			this.mainQuery.getSorter().add(new SorterItemInfo("unit"));
    			if (sellProject.getSubarea() == null
    					|| sellProject.getSubarea().isEmpty())
    			{
    				tblMain.getColumn("subarea").getStyleAttributes()
    						.setHided(true);
    			}
    			//不用销售组织判断,改为售楼组织
    			FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
    			String idStr =null;
    			if(null!= orgUnit.getId()){
    				idStr = orgUnit.getId().toString();
    			}
    			if(null==orgMap.get(idStr)){
//    				this.actionAddNew.setEnabled(false);
//    				this.actionEdit.setEnabled(false);
//    				this.actionRemove.setEnabled(false);
//    				this.actionCreateRoom.setEnabled(false);
    				this.actionAddNew.setEnabled(false);
    				this.actionEdit.setEnabled(false);
    				this.btnRoomDefine.setEnabled(false);
    				this.btRoomImport.setEnabled(false);
    			
    			}else{
    				this.actionAddNew.setEnabled(false);
    				this.actionEdit.setEnabled(true);
    				this.btnRoomDefine.setEnabled(true);
    				this.btRoomImport.setEnabled(true);
    			}
//    			if (saleOrg.isIsBizUnit())
//    			{
//    				// this.actionAddNew.setEnabled(true);
//    				this.actionAddNew.setEnabled(false);// Add By wenyaowei
//    				// 2009-06-05
//    			}
//    			this.btnSeqAdjustmentShow.setEnabled(false);
    			
    		} else
    		{
    			filter.getFilterItems().add(new FilterItemInfo("id", null));
    			tblGraph.removeColumns();
    			tblGraph.removeHeadRows();
    			tblGraph.removeRows();
    			this.actionAddNew.setEnabled(false);
//    			this.btnSeqAdjustmentShow.setEnabled(false);
    		}
    		this.mainQuery.setFilter(filter);
    		// this.mainQuery.getSorter().add(new SorterItemInfo("floor"));
    		// this.mainQuery.getSorter().add(new SorterItemInfo("serialNumber"));

//    		this.mainQuery.getSorter().add(new SorterItemInfo("number"));
    		this.tblMain.removeRows();
    	}else if(index == 1){  
    	
    		if (node.getUserObject() instanceof BuildingUnitInfo
    				|| node.getUserObject() instanceof BuildingInfo
    				|| node.getUserObject() instanceof SubareaInfo
//    				|| node.getUserObject() instanceof SellProjectInfo  // update by wanping 选择项目时，显示鸟瞰图
    				){
    			kDPanel1.remove(scrollPanel); //add by wanping
    			kDPanel1.add(tblGraph); //add by wanping
    			SHEHelper.fillRoomListTableByNode(this.tblGraph, node, null,
    					setting, this.kDScrollPane1);
    		}else if(node.getUserObject() instanceof SellProjectInfo){ //add by wanping
    			kDPanel1.remove(tblGraph); //add by wanping
    			BirdEyeShowUI birdui = new BirdEyeShowUI();
    			birdui.showBirdEyeLable(BirdEyePanel2, node,pnlMain,kDPanel1); //add by wanping
    			scrollPanel.setViewportView(BirdEyePanel2);//add by wanping
    			kDPanel1.add(scrollPanel); //add by wanping
    			scrollPanel.repaint();
    		}
    		//不用销售组织判断,改为售楼组织
			FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
			String idStr =null;
			if(null!= orgUnit.getId()){
				idStr = orgUnit.getId().toString();
			}
			if(null==orgMap.get(idStr)){
//				this.actionAddNew.setEnabled(false);
//				this.actionEdit.setEnabled(false);
//				this.actionRemove.setEnabled(false);
//				this.actionCreateRoom.setEnabled(false);
			
			}else{
				this.actionAddNew.setEnabled(true);
				if (this.tblGraph.getRowCount() < 1)
				{
//					this.btnSeqAdjustmentShow.setEnabled(false);
				} 
			}
    	}
//    	SorterItemInfo sort = new SorterItemInfo("unit");
//    	SorterItemCollection sortCol = new SorterItemCollection();
//    	this.mainQuery.g
    	
	}

	public void onLoad() throws Exception
	{
		BirdEyePanel2 = new SHE2ImagePanel(); //add by wanping
		scrollPanel = new KDScrollPane();//add by wanping
		BirdEyePanel2.setLayout(null);//add by wanping
		this.btnRoomDefine.setIcon(EASResource.getIcon("imgTbtn_duizsetting"));
		this.btnSave.setIcon(EASResource.getIcon("imgTbtn_save")); //add by wanping
		this.btnUpLoadPic.setIcon(EASResource.getIcon("imgTbtn_impress")); //add by wanping
		
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		this.actionEditRoomBind.setEnabled(true);
		this.actionMerge.setEnabled(true);
		this.actionSplit.setEnabled(true);
		this.actionQuery.setVisible(false);
		this.actionQuery.setEnabled(false);
//		this.btnSeqAdjustmentShow.setVisible(true);
//		this.btnSeqAdjustmentShow.setEnabled(true);
		this.menuItemSeqAdjustmentShow.setVisible(true);
		this.menuItemSeqAdjustmentShow.setEnabled(true);
		super.onLoad();
		this.tblMain.getColumn("tenancyArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("tenancyArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(3));
		this.tblMain.getColumn("buildingArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("buildingArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(3));
		this.tblMain.getColumn("roomArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("roomArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(3));
		this.tblMain.getColumn("apportionArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("apportionArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(3));
		this.tblMain.getColumn("balconyArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("balconyArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(3));
		this.tblMain.getColumn("guardenArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("guardenArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(3));
		this.tblMain.getColumn("carbarnArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("carbarnArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(3));
		this.tblMain.getColumn("useArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("useArea").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("flatArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("flatArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(3));
		this.tblMain.getColumn("actualBuildingArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("actualBuildingArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(3));
		this.tblMain.getColumn("actualRoomArea").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("actualRoomArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(3));
		//by tim_gao 计租面积 2011-2-21
		this.tblMain.getColumn("tenancyArea").getStyleAttributes()
			.setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("tenancyArea").getStyleAttributes().setLocked(false);
		this.tblMain.getColumn("tenancyArea").getStyleAttributes()
			.setNumberFormat(FDCHelper.getNumberFtm(3));
		this.kDTabbedPane1.setSelectedIndex(1);
		//不用销售组织判断,改为售楼组织
		FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
		String idStr =null;
		if(null!= orgUnit.getId()){
			idStr = orgUnit.getId().toString();
		}
		if(null==orgMap.get(idStr)){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionMerge.setEnabled(false);
			this.actionSplit.setEnabled(false);
			this.actionEditRoomBind.setEnabled(false);
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.btnRoomDefine.setEnabled(false);
			this.btRoomImport.setEnabled(false);
		
		}else{
			this.actionAddNew.setEnabled(true);
			this.actionEdit.setEnabled(true);
			this.btnRoomDefine.setEnabled(true);
			this.btRoomImport.setEnabled(true);
		}
//		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
//		if (!saleOrg.isIsBizUnit())
//		{
//			this.actionAddNew.setEnabled(false);
//			this.actionEdit.setEnabled(false);
//			this.actionRemove.setEnabled(false);
//			this.actionMerge.setEnabled(false);
//			this.actionSplit.setEnabled(false);
//			this.actionEditRoomBind.setEnabled(false);
//		}
		// UI的排序对未绑定字段的Table忽略，这里重简单实现排序
		SimpleKDTSortManager.setTableSortable(tblMain);
		RoomDisplaySetViewUI.insertUIToScrollPanel(this.kDScrollPane1,
				SellProjectResourceEnum.DEVELOPER);
		this.actionInit.setEnabled(false);
		this.actionInit.setVisible(false);
		
		//房间生成下拉按钮
//		KDMenuItem menuItem1 = new KDMenuItem();
//		menuItem1.setAction(this.actionRoomDefine);
//		menuItem1.setText("房间新增");
//		menuItem1.setIcon(EASResource.getIcon("imgTbtn_discount"));
//		this.btnRoomDefine.addAssistMenuItem(menuItem1);
//		KDMenuItem menuItem2 = new KDMenuItem();
//		menuItem2.setAction(this.actionRoomSourceDisFixed);
//		menuItem2.setText("房源修改");
//		menuItem2.setIcon(EASResource.getIcon("imgTbtn_finitialize"));
//		this.btnRoomDefine.addAssistMenuItem(menuItem2);
		//进入先显示列表，就不会有提示选择行的问题
		this.btnRoomDefine.setIcon(EASResource.getIcon("imgTbtn_discount"));
		this.btnRoomSourceDisFixed.setIcon(EASResource.getIcon("imgTbtn_finitialize"));
		this.kDTabbedPane1.setSelectedIndex(0);
		
		this.actionBatchModifyRoomPropNo.setVisible(false);
		this.actionEdit.setVisible(false);
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e)
	{
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		setUIContextAboutTreeNode(uiContext);
	}

	private void setUIContextAboutTreeNode(UIContext uiContext)
	{
		uiContext.put("floor", floor);
		uiContext.put("buildUnit", buildUnit);
		uiContext.put("buildFloorEntry", buildFloorEntry);
		uiContext.put("selectedRoom", selectedRoom);

		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if(null==node){
			FDCMsgBox.showWarning("请选择楼栋节点！");
			SysUtil.abort();
		}
		if (node.getUserObject() instanceof BuildingUnitInfo)
		{
			BuildingUnitInfo unit = (BuildingUnitInfo) node.getUserObject();
			if (unit != null)
			{
				uiContext.put("unit", unit);
				uiContext.put("buildUnit", buildUnit);
			}
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			uiContext.put("building", building);
			uiContext.put("sellProject", building.getSellProject());

		} else if (node.getUserObject() instanceof BuildingInfo)
		{
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			uiContext.put("building", building);
			uiContext.put("sellProject", building.getSellProject());
		} else if (node.getUserObject() instanceof SellProjectInfo)
		{
			SellProjectInfo sellProject = (SellProjectInfo) node
					.getUserObject();
			uiContext.put("sellProject", sellProject);

		} else if (node.getUserObject() instanceof SubareaInfo)
		{
			SubareaInfo area = (SubareaInfo) node.getUserObject();
			SellProjectInfo sp = area.getSellProject();
			uiContext.put("sellProject", sp);
		}

	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception
	{
		super.tblMain_tableSelectChanged(e);
		EventListener[] listeners = tblGraph
				.getListeners(KDTSelectListener.class);
		for (int i = 0; i < listeners.length; i++)
		{
			tblGraph.removeKDTSelectListener((KDTSelectListener) listeners[i]);
		}
		this.tblGraph.getSelectManager().removeAll();
		for (int b = 0; b < this.tblMain.getSelectManager().size(); b++)
		{
			KDTSelectBlock block = this.tblMain.getSelectManager().get(b);
			for (int i = block.getBeginRow(); i <= block.getEndRow(); i++)
			{
				String roomId = (String) this.tblMain.getRow(i).getCell(
						this.getKeyFieldName()).getValue();
				for (int j = 0; j < this.tblGraph.getRowCount(); j++)
				{
					IRow row = this.tblGraph.getRow(j);
					for (int k = 0; k < this.tblGraph.getColumnCount(); k++)
					{
						RoomInfo room = (RoomInfo) row.getCell(k)
								.getUserObject();
						if (room == null)
						{
							continue;
						}
						if (room.getId().toString().equals(roomId))
						{
							this.tblGraph.getSelectManager().add(j, k);
						}
					}
				}
			}
		}
		for (int i = 0; i < listeners.length; i++)
		{
			tblGraph.addKDTSelectListener((KDTSelectListener) listeners[i]);
		}
	}

	protected void tblGraph_tableSelectChanged(KDTSelectEvent e)
			throws Exception
	{
		super.tblGraph_tableSelectChanged(e);
		EventListener[] listeners = tblMain
				.getListeners(KDTSelectListener.class);
		for (int i = 0; i < listeners.length; i++)
		{
			tblMain.removeKDTSelectListener((KDTSelectListener) listeners[i]);
		}
		this.tblMain.getSelectManager().removeAll();
		for (int b = 0; b < this.tblGraph.getSelectManager().size(); b++)
		{
			KDTSelectBlock block = this.tblGraph.getSelectManager().get(b);
			int endBlockRow = block.getEndRow();
			if (block.getMode() == KDTSelectBlock.COLUMN_BLOCK)
			{
				endBlockRow = tblGraph.getRowCount() - 1;
			}
			for (int i = block.getBeginRow(); i <= endBlockRow; i++)
			{
				for (int a = 0; a < tblGraph.getColumnCount(); a++)
				{
					RoomInfo room = (RoomInfo) this.tblGraph.getCell(i, a)
							.getUserObject();
					if (room != null)
					{
						selectedRoom = room;
						buildUnit = room.getBuildUnit();
						buildFloorEntry = room.getBuildingFloor();
						break;
					}

				}
				for (int j = block.getBeginCol(); j <= block.getEndCol(); j++)
				{
					RoomInfo room = (RoomInfo) this.tblGraph.getCell(i, j)
							.getUserObject();
					if (room != null)
					{
						buildUnit = room.getBuildUnit();
						buildFloorEntry = room.getBuildingFloor();
						selectedRoom = room;
						KDTSelectBlock newBlock = new KDTSelectBlock();
						newBlock.setMode(KDTSelectBlock.ROW_BLOCK);
						for (int k = 0; k < this.tblMain.getRowCount(); k++)
						{
							IRow row = this.tblMain.getRow(k);
							String roomId = (String) row.getCell(
									this.getKeyFieldName()).getValue();
							if (roomId.equals(room.getId().toString()))
							{
								newBlock.setTop(k);
								newBlock.setBottom(k);
								newBlock.setLeft(-1);
								newBlock.setRight(-1);
								this.tblMain.getSelectManager().add(newBlock);
							}
						}
					}
				}
			}
		}
		for (int i = 0; i < listeners.length; i++)
		{
			tblMain.addKDTSelectListener((KDTSelectListener) listeners[i]);
		}
	}

	protected void refresh(ActionEvent e) throws Exception
	{
	//刷新的时候图与数据合起来
		forRefresh();
//		this.initTree();
	}

	public void actionEditRoomBind_actionPerformed(ActionEvent e)
			throws Exception
	{
		super.actionEditRoomBind_actionPerformed(e);
		ICell cell = tblGraph.getCell(tblGraph.getSelectManager()
				.getActiveRowIndex(), tblGraph.getSelectManager()
				.getActiveColumnIndex());
		if (cell != null)
		{
			RoomInfo room = (RoomInfo) cell.getUserObject();

			if (room != null)
			{
				room = SHEHelper.queryRoomInfo(room.getId().toString());
				if (HousePropertyEnum.Attachment
						.equals(room.getHouseProperty()))
				{
					MsgBox.showInfo("附属房产,不能设置绑定其他房间!");
					return;
				}
				/*
				 * 认购单上有附属房产分录,且可以选择是否认购附属房产,所以这里不再做控制.也是为了实现下面功能:
				 * 房间已认购,再对房间进行绑定附属房产,可以对认购单进行变更以认购附属房产 -zhicheng_jin 090224 if
				 * (room.getSellState().equals(RoomSellStateEnum.PrePurchase) ||
				 * room.getSellState().equals( RoomSellStateEnum.Purchase) ||
				 * room.getSellState().equals(RoomSellStateEnum.Sign)) {
				 * MsgBox.showInfo("选择的房间已经认购,不能设置绑定,请修改认购单!"); return; }
				 */
				String roomId = room.getId().toString();
				boolean isEdit = RoomBindUI.showEditUI(this, roomId);
				if (isEdit)
				{
					this.refresh(null);
				}
			}
		}
	}

	protected void tblGraph_tableClicked(KDTMouseEvent e) throws Exception
	{
		super.tblGraph_tableClicked(e);
		if (e.getButton() == 1 && e.getClickCount() == 2)
		{
			RoomInfo room = (RoomInfo) this.tblGraph.getCell(e.getRowIndex(),
					e.getColIndex()).getUserObject();
			if (room == null)
			{
				return;
			}
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, room.getId().toString());

			setUIContextAboutTreeNode(uiContext);

			IUIWindow newUI = UIFactory.createUIFactory(getEditUIModal())
					.create(getEditUIName(), uiContext, null, OprtState.VIEW);
			newUI.show();
		}
	}

	// BUG BT303833房间双击按钮事件
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception
	{
		super.tblMain_tableClicked(e);
		if (e.getButton() == 1 && e.getClickCount() == 2)
		{
			String roomID = (String) this.tblMain.getRow(e.getRowIndex())
					.getCell(this.getKeyFieldName()).getValue();
			if (roomID == null)
			{
				return;
			}
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, roomID);
			setUIContextAboutTreeNode(uiContext);
			IUIWindow newUI = UIFactory.createUIFactory(getEditUIModal())
					.create(getEditUIName(), uiContext, null, OprtState.VIEW);
			newUI.show();
		}
	}
	private void checkCanMergeAndSplit(RoomCollection roomList,boolean isMerge) throws EASBizException, BOSException{
		
		int index = kDTabbedPane1.getSelectedIndex();
		String warning="拆分！";
		if(isMerge){
			warning="合并！";
		}
		if(index ==0){
    		FDCMsgBox.showWarning(this,"请选择平面图页签进行"+warning);
			SysUtil.abort();
    	}
		for (int b = 0; b < this.tblGraph.getSelectManager().size(); b++){
			KDTSelectBlock block = this.tblGraph.getSelectManager().get(b);

			for (int i = block.getBeginRow(); i <= block.getEndRow(); i++){
				for (int j = block.getBeginCol(); j <= block.getEndCol(); j++) {
					RoomInfo room = (RoomInfo) this.tblGraph.getCell(i, j).getUserObject();
					if (room == null){
						continue;
					} else{
						room = SHEHelper.queryRoomInfo(room.getId().toString());
						if ((!room.getSellState().equals(RoomSellStateEnum.Init))
								&& (!room.getSellState().equals(RoomSellStateEnum.OnShow))){
							FDCMsgBox.showWarning(this,"有选择的房间不是初始或可售状态,不能"+warning);
							SysUtil.abort();
						}
						if(room.getTenancyState()!=null){
							if(!(room.getTenancyState().equals(TenancyStateEnum.unTenancy) 
									||room.getTenancyState().equals(TenancyStateEnum.waitTenancy))){
								FDCMsgBox.showWarning(this,"有选择的房间不是初始、可售或可租状态，不能"+warning);
								SysUtil.abort();
							}
						}
						if (room.getAttachmentEntry().size() > 0){
							FDCMsgBox.showWarning(this,"有选择的房间已绑定其他房间不能"+warning);
							SysUtil.abort();
						}
						if (room.isIsForSHE()&&room.getStandardTotalAmount() != null
								&& room.getStandardTotalAmount().compareTo(
										FDCHelper.ZERO) != 0){
							FDCMsgBox.showWarning(this,"有选择的房间已经定价,不能"+warning);
							SysUtil.abort();
						}
						FilterInfo filter = new FilterInfo();
						filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
						if (RoomAttachmentEntryFactory.getRemoteInstance().exists(filter)){
							FDCMsgBox.showWarning(this,"有选择的房间已经被绑定,不能再"+warning);
							SysUtil.abort();
						}
						filter = new FilterInfo();
						filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
						if (PushManageHisEntryFactory.getRemoteInstance().exists(filter)){
							FDCMsgBox.showWarning(this,"有选择的房间已经进入推盘管理,不能再"+warning);
							SysUtil.abort();
						}
					}
					roomList.add(room);
				}
			}
		}

		if(roomList.size()==0){
			FDCMsgBox.showWarning(this,"请选择房间！");
			SysUtil.abort();
		}
		if(isMerge){
			if(roomList.size()==1){
				FDCMsgBox.showWarning(this,"请选择多个房间合并！");
				SysUtil.abort();
			}
			RoomInfo room1=(RoomInfo)roomList.get(0);
			RoomInfo room2=(RoomInfo)roomList.get(1);
			if(room1.getFloor()!=room2.getFloor()&&room1.getSerialNumber()!=room1.getSerialNumber()){
				FDCMsgBox.showWarning(this,"请选择同层或者同序号的房间"+warning);
				SysUtil.abort();
			}
			if(room1.getFloor()!=room2.getFloor()){
				CRMHelper.sortCollection(roomList, "floor", true);
				for(int i=0;i<roomList.size();i++){
					RoomInfo room=roomList.get(i);
					if(room.getSub()!=null&&room.getSub().indexOf(",")>0){
						FDCMsgBox.showWarning(this,"请拆分已合并房间后再合并！");
						SysUtil.abort();
					}
				}
			}else{
				CRMHelper.sortCollection(roomList, "serialNumber", true);
				RoomInfo preRoom=null;
				for(int i=0;i<roomList.size();i++){
					RoomInfo room=roomList.get(i);
					if(i>0){
						preRoom=roomList.get(i-1);
						if(room.getSerialNumber()-preRoom.getSerialNumber()!=1){
							FDCMsgBox.showWarning(this,"请选择相邻的房间或者同单元的房间"+warning);
							SysUtil.abort();
						}
					}
					if(room.getSub()!=null&&room.getSub().indexOf(",")>0){
						FDCMsgBox.showWarning(this,"请拆分已合并房间后再合并！");
						SysUtil.abort();
					}
				}
			}
			
		}else{
			if(roomList.size()!=1){
				FDCMsgBox.showWarning(this,"请选择已经合并房间拆分！");
				SysUtil.abort();
			}
		}
	}

	public void actionMerge_actionPerformed(ActionEvent e) throws Exception
	{
		
		Set deleteRoomSet = new HashSet();
		RoomCollection roomList = new RoomCollection();
		checkCanMergeAndSplit(roomList,true);
		RoomInfo firstRoom=null;
		String sub="";
		for (int i=0;i<roomList.size();i++){
			RoomInfo room = (RoomInfo) roomList.get(i);
			if (room == null){
				continue;
			}
			sub=sub+","+room.getFloor()+";"+room.getSerialNumber()+";"+room.getDisplayName();
			
			if (firstRoom==null){
				firstRoom = room;
			} else{
				deleteRoomSet.add(room.getId().toString());
				
				if (firstRoom.getId().toString().equals(room.getId().toString())){
					continue;
				}
				firstRoom.setEndSerialNumber(room.getEndSerialNumber());
				
				if (room.getBuildingArea() != null)
				{
					if (firstRoom.getBuildingArea() == null){
						firstRoom.setBuildingArea(room.getBuildingArea());
					} else{
						firstRoom.setBuildingArea(firstRoom.getBuildingArea().add(room.getBuildingArea()));
					}
				}
				if (room.getTenancyArea() != null)
				{
					if (firstRoom.getTenancyArea() == null)
					{
						firstRoom.setTenancyArea(room.getTenancyArea());
					} else
					{
						firstRoom.setTenancyArea(firstRoom.getTenancyArea().add(room.getTenancyArea()));
					}
				}
				if (room.getRoomArea() != null)
				{
					if (firstRoom.getRoomArea() == null)
					{
						firstRoom.setRoomArea(room.getRoomArea());
					} else
					{
						firstRoom.setRoomArea(firstRoom.getRoomArea().add(room.getRoomArea()));
					}
				}
				if (room.getBalconyArea() != null)
				{
					if (firstRoom.getBalconyArea() == null)
					{
						firstRoom.setBalconyArea(room.getBalconyArea());
					} else
					{
						firstRoom.setBalconyArea(firstRoom.getBalconyArea().add(room.getBalconyArea()));
					}
				}
				if (room.getApportionArea() != null)
				{
					if (firstRoom.getApportionArea() == null)
					{
						firstRoom.setApportionArea(room.getApportionArea());
					} else
					{
						firstRoom.setApportionArea(firstRoom.getApportionArea().add(room.getApportionArea()));
					}
				}
				if (room.getGuardenArea() != null)
				{
					if (firstRoom.getGuardenArea() == null)
					{
						firstRoom.setGuardenArea(room.getGuardenArea());
					} else
					{
						firstRoom.setGuardenArea(firstRoom.getGuardenArea().add(room.getGuardenArea()));
					}
				}
				if (room.getCarbarnArea() != null)
				{
					if (firstRoom.getCarbarnArea() == null)
					{
						firstRoom.setCarbarnArea(room.getCarbarnArea());
					} else
					{
						firstRoom.setCarbarnArea(firstRoom.getCarbarnArea().add(room.getCarbarnArea()));
					}
				}
				if (room.getUseArea() != null)
				{
					if (firstRoom.getUseArea() == null)
					{
						firstRoom.setUseArea(room.getUseArea());
					} else
					{
						firstRoom.setUseArea(firstRoom.getUseArea().add(room.getUseArea()));
					}
				}
				if (room.getFlatArea() != null)
				{
					if (firstRoom.getFlatArea() == null)
					{
						firstRoom.setFlatArea(room.getFlatArea());
					} else
					{
						firstRoom.setFlatArea(firstRoom.getFlatArea().add(room.getFlatArea()));
					}
				}
				if (room.getActualBuildingArea() != null)
				{
					if (firstRoom.getActualBuildingArea() == null)
					{
						firstRoom.setActualBuildingArea(room.getActualBuildingArea());
					} else
					{
						firstRoom.setActualBuildingArea(firstRoom.getActualBuildingArea().add(room.getActualBuildingArea()));
					}
				}
				if (room.getActualRoomArea() != null)
				{
					if (firstRoom.getActualRoomArea() == null)
					{
						firstRoom.setActualRoomArea(room.getActualRoomArea());
					} else
					{
						firstRoom.setActualRoomArea(firstRoom.getActualRoomArea().add(room.getActualRoomArea()));
					}
				}
				if (room.getPlanBuildingArea() != null)
				{
					if (firstRoom.getPlanBuildingArea() == null)
					{
						firstRoom.setPlanBuildingArea(room.getPlanBuildingArea());
					} else
					{
						firstRoom.setPlanBuildingArea(firstRoom.getPlanBuildingArea().add(room.getPlanBuildingArea()));
					}
				}
				if (room.getPlanRoomArea() != null)
				{
					if (firstRoom.getPlanRoomArea() == null)
					{
						firstRoom.setPlanRoomArea(room.getPlanRoomArea());
					} else
					{
						firstRoom.setPlanRoomArea(firstRoom.getPlanRoomArea().add(room.getPlanRoomArea()));
					}
				}
			}
		}
		if (MsgBox.showConfirm2New(this, "确认合并房间吗？") != MsgBox.YES)
		{
			return;
		} 
		firstRoom.setSub(sub.replaceFirst(",", ""));
		firstRoom.setRoomPropNo(firstRoom.getNumber());

		RoomFactory.getRemoteInstance().submit(firstRoom);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", deleteRoomSet, CompareType.INCLUDE));
		RoomFactory.getRemoteInstance().delete(filter);
		this.refresh(null);
	}

	public void actionSplit_actionPerformed(ActionEvent e) throws Exception
	{
		String id=null;
		RoomCollection roomList = new RoomCollection();
		checkCanMergeAndSplit(roomList,false);
		
		for (int i=0;i<roomList.size();i++){
			RoomInfo room = (RoomInfo) roomList.get(i);
			if (room == null){
				continue;
			}
			id=room.getId().toString();
		}
		if(((RoomInfo)roomList.get(0)).getSub()==null||((RoomInfo)roomList.get(0)).getSub().indexOf(",")<0){
			FDCMsgBox.showWarning(this,"请选择已经合并房间拆分！");
			SysUtil.abort();
		}
		if(MsgBox.showConfirm2New(this, "确认拆分房间吗？") != MsgBox.YES){
			return;
		}
		RoomInfo room=(RoomInfo) roomList.get(0);
		String[]  sub=room.getSub().split(",");
		
		RoomInfo oldroom=(RoomInfo)room.clone(); 
		RoomFactory.getRemoteInstance().delete(new ObjectUuidPK(id));
		for (int i = 0; i < sub.length; i++) {
			RoomInfo newRoom = (RoomInfo) oldroom.clone();
			newRoom.setId(BOSUuid.create(newRoom.getBOSType()));

			int serialNumber=Integer.parseInt(sub[i].split(";")[1]);
			
			newRoom.setSerialNumber(serialNumber);
			newRoom.setEndSerialNumber(serialNumber);
			
			
			String strNum=sub[i].split(";")[2];
			
			newRoom.setDisplayName(strNum);
			
			newRoom.setRoomNo(strNum);
			newRoom.setRoomPropNo(strNum);
			
			setAverRoomArea(sub.length,i,room,newRoom);
			
			newRoom.setRentType(null);
			newRoom.setStandardRent(null);
			newRoom.setStandardRentPrice(null);
			
			BuildingFloorEntryInfo buildingFloorEntryInfo=getBuildingFloorEntryInfo(newRoom.getBuilding(),sub[i].split(";")[0]);
			if(buildingFloorEntryInfo!=null){
				newRoom.setBuildingFloor(buildingFloorEntryInfo);
			}
			
			newRoom.setFloor(Integer.parseInt(sub[i].split(";")[0]));
			newRoom.setSub(sub[i]);
			
			
			//存长编码，长名称，掉高明方法，目前存短的
			newRoom = SHEHelper.getLongCodeRoom(newRoom);
//			newRoom.setName(strNum);
//			newRoom.setNumber(strNum);
			RoomFactory.getRemoteInstance().submit(newRoom);
		}
		
		this.refresh(null);

	}
	
	private BuildingFloorEntryInfo getBuildingFloorEntryInfo(BuildingInfo info,String floor) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("floor",floor));
		filter.getFilterItems().add(new FilterItemInfo("building.id", info.getId()));
		filter.getFilterItems().add(new FilterItemInfo("isEnable", new Boolean(true)));
		view.setFilter(filter);
		BuildingFloorEntryCollection col=BuildingFloorEntryFactory.getRemoteInstance().getBuildingFloorEntryCollection(view);
		if(col.size()>0){
			return col.get(0);
		}
		return null;
	}
	private void setAverRoomArea(int rowCount,int rowIndex,RoomInfo room,RoomInfo newRoom){
		newRoom.setBuildingArea(null);
		newRoom.setRoomArea(null);
		newRoom.setApportionArea(null);
		newRoom.setBalconyArea(null);
		newRoom.setGuardenArea(null);
		newRoom.setCarbarnArea(null);
		newRoom.setUseArea(null);
		newRoom.setFlatArea(null);
		newRoom.setActualBuildingArea(null);
		newRoom.setActualRoomArea(null);
		newRoom.setTenancyArea(null);
		newRoom.setPlanBuildingArea(null);
		newRoom.setPlanRoomArea(null);
//		if(!(rowIndex==rowCount-1)){//当不等于最后一行的时候 每行位总数的均值
//			if (room.getBuildingArea() != null ){
//				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getBuildingArea()) != 0)
//					newRoom.setBuildingArea(FDCHelper.divide(room.getBuildingArea(), new Integer(rowCount),3,4));}
//			if (room.getRoomArea() != null ){
//				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getRoomArea()) != 0)
//					newRoom.setRoomArea(FDCHelper.divide(room.getRoomArea(), new Integer(rowCount),3,4));}
//			if (room.getApportionArea() != null ){
//				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getApportionArea()) != 0)
//					newRoom.setApportionArea(FDCHelper.divide(room.getApportionArea(), new Integer(rowCount),3,4));}
//			if (room.getBalconyArea() != null ){
//				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getBalconyArea()) != 0)
//					newRoom.setBalconyArea(FDCHelper.divide(room.getBalconyArea(), new Integer(rowCount),3,4));}
//			if (room.getGuardenArea() != null ){
//				if( FDCHelper.ZERO.compareTo((BigDecimal)room.getGuardenArea()) != 0)
//					newRoom.setGuardenArea(FDCHelper.divide(room.getGuardenArea(), new Integer(rowCount),3,4));}
//			if (room.getCarbarnArea() != null ){
//				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getCarbarnArea()) != 0)
//					newRoom.setCarbarnArea(FDCHelper.divide((BigDecimal)room.getCarbarnArea(), new Integer(rowCount),3,4));}
//			if (room.getUseArea() != null ){
//				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getUseArea()) != 0)
//					newRoom.setUseArea(FDCHelper.divide(room.getUseArea(), new Integer(rowCount),3,4));}
//			if (room.getFlatArea() != null ){
//				if(FDCHelper.ZERO.compareTo( (BigDecimal)room.getFlatArea()) != 0)
//					newRoom.setFlatArea(FDCHelper.divide(room.getFlatArea(), new Integer(rowCount),3,4));}
//			if (room.getActualBuildingArea() != null  ){
//				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getActualBuildingArea())!= 0)
//					newRoom.setActualBuildingArea(FDCHelper.divide(room.getActualBuildingArea(), new Integer(rowCount),3,4));}
//			if (room.getActualRoomArea() != null ){
//				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getActualRoomArea()) != 0)
//					newRoom.setActualRoomArea(FDCHelper.divide(room.getActualRoomArea(), new Integer(rowCount),3,4));}
//			if (room.getTenancyArea() != null ){
//				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getTenancyArea()) != 0)
//					newRoom.setTenancyArea(FDCHelper.divide(room.getTenancyArea(), new Integer(rowCount),3,4));}
//		}else{//当为最后一行是，本行= 总行 - 之前行的值之和; 
//			if (room.getBuildingArea() != null ){
//				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getBuildingArea()) != 0)
//					newRoom.setBuildingArea(room.getBuildingArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getBuildingArea(), new Integer(rowCount),3,4),new Integer(rowIndex))));}
//			if (room.getRoomArea() != null ){
//				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getRoomArea()) != 0)
//					newRoom.setRoomArea(room.getRoomArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getRoomArea(), new Integer(rowCount),3,4),new Integer(rowIndex))));}
//			if (room.getApportionArea() != null ){
//				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getApportionArea()) != 0)
//					newRoom.setApportionArea(room.getApportionArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getApportionArea(), new Integer(rowCount),3,4),new Integer(rowIndex))));}
//			if (room.getBalconyArea() != null ){
//				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getBalconyArea()) != 0)
//					newRoom.setBalconyArea(room.getBalconyArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getBalconyArea(), new Integer(rowCount),3,4),new Integer(rowIndex))));}
//			if (room.getGuardenArea() != null ){
//				if( FDCHelper.ZERO.compareTo((BigDecimal)room.getGuardenArea()) != 0)
//					newRoom.setGuardenArea(room.getGuardenArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getGuardenArea(), new Integer(rowCount),3,4),new Integer(rowIndex))));}
//			if (room.getCarbarnArea() != null ){
//				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getCarbarnArea()) != 0)
//					newRoom.setCarbarnArea(room.getCarbarnArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getCarbarnArea(), new Integer(rowCount),3,4),new Integer(rowIndex))));}
//			if (room.getUseArea() != null ){
//				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getUseArea()) != 0)
//					newRoom.setUseArea(room.getUseArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getUseArea(), new Integer(rowCount),3,4),new Integer(rowIndex))));}
//			if (room.getFlatArea() != null ){
//				if(FDCHelper.ZERO.compareTo( (BigDecimal)room.getFlatArea()) != 0)
//					newRoom.setFlatArea(room.getFlatArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getFlatArea(), new Integer(rowCount),3,4),new Integer(rowIndex))));}
//			if (room.getActualBuildingArea() != null  ){
//				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getActualBuildingArea())!= 0)
//					newRoom.setActualBuildingArea(room.getActualBuildingArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getActualBuildingArea(), new Integer(rowCount),3,4),new Integer(rowIndex))));}
//			if (room.getActualRoomArea() != null ){
//				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getActualRoomArea()) != 0)
//					newRoom.setActualRoomArea(room.getActualRoomArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getActualRoomArea(), new Integer(rowCount),3,4),new Integer(rowIndex))));}
//			if (room.getTenancyArea() != null ){
//				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getTenancyArea()) != 0)
//					newRoom.setTenancyArea(room.getTenancyArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getTenancyArea() ,new Integer(rowCount),3,4),new Integer(rowIndex))));
//				}
//		}
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception
	{
		
		for (int b = 0; b < this.tblGraph.getSelectManager().size(); b++)
		{
			KDTSelectBlock block = this.tblGraph.getSelectManager().get(b);
			for (int i = block.getBeginRow(); i <= block.getEndRow(); i++)
			{
				for (int j = block.getBeginCol(); j <= block.getEndCol(); j++)
				{
					RoomInfo room = (RoomInfo) this.tblGraph.getCell(i, j)
							.getUserObject();
					if (room == null)
					{
						continue;
					}
					if (!RoomSellStateEnum.Init.equals(room.getSellState()))
					{
						MsgBox.showInfo("非初始房间不能删除!");
						return;
					}
					if(room.getTenancyState()!=null){
						if(!(room.getTenancyState().equals(TenancyStateEnum.unTenancy) 
								||room.getTenancyState().equals(TenancyStateEnum.waitTenancy))){
							MsgBox.showInfo("只有未推盘、未放租、待售或待租的房间才能删除!");
							return;
						}
					}
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("room.id", room.getId()
									.toString()));
					if (RoomPriceBillEntryFactory.getRemoteInstance().exists(
							filter))
					{
						MsgBox.showInfo("房间已参与定价,不能删除!");
						return;
					}

					if (PriceAdjustEntryFactory.getRemoteInstance().exists(
							filter))
					{
						MsgBox.showInfo("房间已参与调价,不能删除!");
						return;
					}

					if (room.getAttachmentEntry() != null
							&& !room.getAttachmentEntry().isEmpty())
					{
						MsgBox.showInfo("已绑定其他房间,不能删除!");
						return;
					}

					if (RoomAttachmentEntryFactory.getRemoteInstance().exists(
							filter))
					{
						MsgBox.showInfo("已被其他房间绑定,不能删除！");
						return;
					}
				}
			}
		}
		int index = this.kDTabbedPane1.getSelectedIndex();
		if(index == 0){

			checkSelected();
		}else{
			if(this.tblGraph.getSelectManager().size()<1){
				MsgBox.showInfo("请选中房间！");
				this.abort();
			}
		}
		if (confirmRemove())
		{
			// prepareRemove(null).callHandler();
			Remove();
			refresh(e);
		}
	}

	
	public void seqAdjustmentUIShow_actionPerformed(ActionEvent e)
			throws Exception
	{
		if (this.tblGraph.getRowCount() < 1)
		{
			MsgBox.showInfo("没有房间，不能调整！");
			this.abort();
		}
		super.seqAdjustmentUIShow_actionPerformed(e);
		UIContext uiContext = new UIContext(this);
		setUIContextAboutTreeNode(uiContext);

		SellProjectInfo sellProject = (SellProjectInfo) uiContext
				.get("sellProject");
		if (sellProject == null)
		{
			MsgBox.showInfo("该房间租售项目信息不全，不能调整！");
			this.abort();
		}

		BuildingInfo building = (BuildingInfo) uiContext.get("building");
		if (building == null)
		{
			MsgBox.showInfo("该房间楼栋信息不全，不能调整！");
			this.abort();
		}
		IUIWindow newUI = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(SeqAdjustmentUI.class.getName(), uiContext, null,
						OprtState.EDIT);
		newUI.show();
	}

	public void actionExport_actionPerformed(ActionEvent e) throws Exception
	{
		handlePermissionForItemAction(actionExport);
		super.actionExport_actionPerformed(e);
	}

	public void actionExportData_actionPerformed(ActionEvent e)
			throws Exception
	{
		handlePermissionForItemAction(actionExportData);
		super.actionExportData_actionPerformed(e);
	}

	public void actionExportSelected_actionPerformed(ActionEvent e)
			throws Exception
	{
		handlePermissionForItemAction(actionExportSelected);
		super.actionExportSelected_actionPerformed(e);

	}

	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception
	{
		handlePermissionForItemAction(actionToExcel);
		super.actionToExcel_actionPerformed(e);
	}

	protected String getLongNumberFieldName()
	{
		return "number";
	}

	// 修改物业房号 luxiaoling 091124
	public void actionBatchModifyRoomPropNo_actionPerformed(ActionEvent e)
			throws Exception
	{
		//不用销售组织判断,改为售楼组织
		FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
		String idStr =null;
		if(null!= orgUnit.getId()){
			idStr = orgUnit.getId().toString();
		}
		if(null==orgMap.get(idStr)){
			MsgBox.showWarning("非售楼组织，无法批改房号面积！");
		
		}else{
			 DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)
			 treeMain.getLastSelectedPathComponent();
			 if (node == null) {
			 return;
			 }
					 
			 UIContext uiContext=new UIContext();
			 uiContext.put("nodeid",node);
			 IUIWindow
			 iuiWindow=UIFactory.createUIFactory(UIFactoryName.MODEL).create
			 (RoomPropNoBatchEditUI.class.getName(),uiContext,null);
			 iuiWindow.show();
			 this.refreshList();
		}
//		if(SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit()){
//			 DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)
//			 treeMain.getLastSelectedPathComponent();
//			 if (node == null) {
//			 return;
//			 }
//					 
//			 UIContext uiContext=new UIContext();
//			 uiContext.put("nodeid",node);
//			 IUIWindow
//			 iuiWindow=UIFactory.createUIFactory(UIFactoryName.MODEL).create
//			 (RoomPropNoBatchEditUI.class.getName(),uiContext,null);
//			 iuiWindow.show();
//			 this.refreshList();
//		}else{
//			MsgBox.showWarning("非实体销售组织，无法批改房号面积！");
//		}
	}
	
	/**
	 * 批改房间资料
	 */
	public void actionBatchModifyRoomUpdate_actionPerformed(ActionEvent e)
			throws Exception {
		//楼栋带入
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		String buildingId = null;
		if(null==node){
			FDCMsgBox.showWarning("请选择楼栋！");
			SysUtil.abort();
			return;
		}
		if (node.getUserObject() instanceof BuildingUnitInfo)
		{
			BuildingUnitInfo unit = (BuildingUnitInfo) node.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			 buildingId = building.getId().toString();
		}
		else if (node.getUserObject() instanceof BuildingInfo)
		{
		
			BuildingInfo building = (BuildingInfo)  node.getUserObject();
			 buildingId = building.getId().toString();
		}
		if(null==buildingId){
			FDCMsgBox.showWarning("请选择楼栋！");
			SysUtil.abort();
		}
		//不用销售组织判断,改为售楼组织
		FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
		String idStr =null;
		if(null!= orgUnit.getId()){
			idStr = orgUnit.getId().toString();
		}
		if(null==orgMap.get(idStr)){
			MsgBox.showWarning("非售楼组织，无法批改房间资料！");
		
		}else{
			/**
			 * 当点击左上角全部的时候,getSelectedIdValues这个方法失效
			 */
		
			int index = kDTabbedPane1.getSelectedIndex();
	//by tim _gao  图与查询分开写 ，不合起来写了解决性能问题
	if(index ==0 ){
			List list=new ArrayList();
			int [] selectRows  = KDTableUtil.getSelectedRows(this.tblMain);
			if(selectRows.length<=0){
				FDCMsgBox.showWarning("请选择房间！");
				SysUtil.abort();
			}
			int select = 0;
			for (int i = 0; i < selectRows.length; i++) {
				select  = selectRows[i];
				IRow row = this.tblMain.getRow(select);
				if(row==null){
					continue;
				}
				if(row.getCell("id").getValue()!=null){
					list.add(row.getCell("id").getValue().toString());
				}
			}
			if(list!=null && list.size()>0){
				UIContext uiContext=new UIContext();
				uiContext.put("idList",list);
				uiContext.put("buildID",buildingId);
				IUIWindow iuiWindow=UIFactory.createUIFactory(UIFactoryName.NEWWIN).create
				(RoomUpdateBatchEditUI.class.getName(),uiContext,null,OprtState.ADDNEW);
				iuiWindow.show();
				this.refreshList();
			}else{
				MsgBox.showWarning("请先选择房间！");
			}
		}
		else if(index == 1){
	
		 if (node == null) {
		 return;
		 }
		 List list=new ArrayList();
			for (int b = 0; b < this.tblGraph.getSelectManager().size(); b++)
			{
				KDTSelectBlock block = this.tblGraph.getSelectManager().get(b);
				for (int i = block.getBeginRow(); i <= block.getEndRow(); i++)
				{
					for (int j = block.getBeginCol(); j <= block.getEndCol(); j++)
					{
						RoomInfo room = (RoomInfo) this.tblGraph.getCell(i, j)
								.getUserObject();
						
						if(null!=room.getId()){
							
							list.add(room.getId().toString());
						}
					}
				}
			}
		 UIContext uiContext=new UIContext();
		 uiContext.put("nodeid",node);
		 uiContext.put("idList",list);
		 IUIWindow iuiWindow=UIFactory.createUIFactory(UIFactoryName.NEWWIN).create
			(RoomUpdateBatchEditUI.class.getName(),uiContext,null,OprtState.ADDNEW);
		 iuiWindow.show();
	}
		}
//		if(SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit()){
//			List list=getSelectedIdValues();
//			if(list.size()>0){
//				UIContext uiContext=new UIContext();
//				uiContext.put("idList",list);
//				IUIWindow iuiWindow=UIFactory.createUIFactory(UIFactoryName.MODEL).create
//				(RoomUpdateBatchEditUI.class.getName(),uiContext,null,OprtState.ADDNEW);
//				iuiWindow.show();
//				this.refreshList();
//			}else{
//				MsgBox.showWarning("请先选择房间！");
//			}
//		}else{
//			MsgBox.showWarning("非实体销售组织，无法批改房间资料！");
//		}
	}
	

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.sellhouse.client.RoomSourceEditUI.class.getName();
    }
    
    /**
     * output actionCreateRoom_actionPerformed method
     */
    public void actionCreateRoom_actionPerformed(ActionEvent e) throws Exception{
    	
    	
		UIContext uiContext = new UIContext(this);
		prepareUIContext(uiContext, e);
		try {
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create (RoomDefineEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();
		} catch (UIException ee) {
			ee.printStackTrace();
		}
    	

	  
//    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
//		.getLastSelectedPathComponent();
//		if (node != null && node.getUserObject() instanceof BuildingInfo) {
//			BuildingInfo building = (BuildingInfo) node.getUserObject();
//			RoomCollection rooms2 = SHEHelper.getRooms(building);
//			for(int i=0;i<rooms2.size();i++)
//			{
//				RoomInfo room = (RoomInfo)rooms2.get(i);
//				if(room.isIsAreaAudited() || room.isIsActualAreaAudited())
//				{
//					MsgBox.showInfo("有房间售前复核或者实测复核过，不能重新生成房间!");
//					this.abort();
//				}
//			}
//			RoomCollection rooms = SHEHelper.getRoomsByDes(building.getId()
//					.toString());
//			if(rooms.size()==0){
//				return;
//			}
//			RoomNewCreateUI.showWindows(this, rooms);
//			
//		}
    }
    
    
    /**
     * output actionRoomImport_actionPerformed method
     */
    public void actionRoomImport_actionPerformed(ActionEvent e) throws Exception
    {
    	
		UIContext uiContext = new UIContext(this);
		
		prepareUIContext(uiContext, e);
		try {
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create (ImportExcelRoomUIForSHE.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();
		} catch (UIException ee) {
			ee.printStackTrace();
		}
//		this.initTree();
		this.refresh(null);
    }
    
    
    /**
     * output actionRoomDefine_actionPerformed method
     */
    public void actionRoomDefine_actionPerformed(ActionEvent e) throws Exception
    {
    	//楼栋带入
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		if(null==node){
    		FDCMsgBox.showWarning("请选择楼栋节点！");
    		SysUtil.abort();
    	}
		BuildingInfo building = null;
		 if (node.getUserObject() instanceof BuildingInfo)
		{
		
			building = (BuildingInfo)  node.getUserObject();
			
		}
		if(null==building){
			FDCMsgBox.showWarning("请选择楼栋！");
			SysUtil.abort();
		}
    	 super.actionAddNew_actionPerformed(e);
    	this.initTree();
    }

    /**
     * output actionAddNew_actionPerformed method
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	//楼栋带入
    	int selectRow =treeMain.getSelectionCount();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
		BuildingInfo building = null;
		if(node==null){
			FDCMsgBox.showWarning("请选择楼栋！");
			SysUtil.abort();
		}
		 if (node.getUserObject() instanceof BuildingInfo)
		{
		
			building = (BuildingInfo)  node.getUserObject();
			
		}
		if(null==building){
			FDCMsgBox.showWarning("请选择楼栋！");
			SysUtil.abort();
		}
	if (node != null && node.getUserObject() instanceof BuildingInfo) {
		 building = (BuildingInfo) node.getUserObject();
	//做是否复核过的判断
	RoomCollection rooms2 = SHEHelper.getRooms(building);
	for(int i=0;i<rooms2.size();i++)
	{
		RoomInfo room = (RoomInfo)rooms2.get(i);
		if(room.isIsPre() || room.isIsActualAreaAudited()||room.isIsPlan())
		{
			MsgBox.showInfo("房间已复核，不能重新生成房间!");
			this.abort();
		}
	}
  	UIContext uiContext = new UIContext(this);
	uiContext.put("node", node);
	
	prepareUIContext(uiContext, e);

		try {
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create (RoomDefineEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
			uiWindow.show();
		} catch (UIException ee) {
			ee.printStackTrace();
		}
		
		
    	
	}
//	//回写值，因为在房源图形生成界面里如果点退出，不生成，生成的新单元信息便成为脏数据所以要回写下
//	if(null!=building){
//		BuildingUnitFactory.getRemoteInstance().updateBuildingUnitbyBuild(building);
//	}
	refresh(null);
	this.initTree();
	this.treeMain.setSelectionRow(selectRow);
	
       
    }

    /**
     * output actionRoomSourceDisFixed_actionPerformed method
     */
    public void actionRoomSourceDisFixed_actionPerformed(ActionEvent e) throws Exception
    {

    	
    	
		for (int b = 0; b < this.tblGraph.getSelectManager().size(); b++)
		{
			KDTSelectBlock block = this.tblGraph.getSelectManager().get(b);
			for (int i = block.getBeginRow(); i <= block.getEndRow(); i++)
			{
				for (int j = block.getBeginCol(); j <= block.getEndCol(); j++)
				{
					RoomInfo room = (RoomInfo) this.tblGraph.getCell(i, j)
							.getUserObject();
					if (room == null)
					{
						return;
					}
					if (!RoomSellStateEnum.Init.equals(room.getSellState())
							&& !RoomSellStateEnum.OnShow.equals(room.getSellState()))
					{
						MsgBox.showInfo("未推盘或待售房间才能修改!");
						return;
					}
					if(room.getTenancyState()!=null){
						if(!(room.getTenancyState().equals(TenancyStateEnum.unTenancy) 
								||room.getTenancyState().equals(TenancyStateEnum.waitTenancy))){
							MsgBox.showInfo("只有未推盘、未放租、待售或待租的房间才能修改!");
							return;
						}
					}
				}
			}
		}
		super.actionEdit_actionPerformed(e);
//		this.refresh(null);
    }
    
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
	{
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
		.getLastSelectedPathComponent();
    	
    	if(node ==null){
    		FDCMsgBox.showWarning("请选中楼栋！");
    		SysUtil.abort();
    	}
    	
    	if(node.getUserObject() instanceof BuildingInfo){
    		KDTree treeMain = new KDTree();
//        	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)this.getUIContext().get("node");
        	treeMain.setModel(FDCTreeHelper.getUnitTreeForSHE(actionOnLoad,MoneySysTypeEnum.SalehouseSys));
        	Map idNodeMap = FDCTreeHelper.getAllObjectIdMap((TreeNode)treeMain.getModel().getRoot(),"Building");
    		String id = ((BuildingInfo)node.getUserObject()).getId().toString();
        	node = (DefaultKingdeeTreeNode)idNodeMap.get(id);
    	}
    	
    	
    	BuildingInfo building = null;
	
	if (node != null && node.getUserObject() instanceof BuildingInfo) {
		 building = (BuildingInfo) node.getUserObject();
		//不做是否复核过的判断
//		RoomCollection rooms2 = SHEHelper.getRooms(building);
//		for(int i=0;i<rooms2.size();i++)
//		{
//			RoomInfo room = (RoomInfo)rooms2.get(i);
//			if(room.isIsAreaAudited() || room.isIsActualAreaAudited())
//			{
//				MsgBox.showInfo("有房间售前复核或者实测复核过，不能重新生成房间!");
//				this.abort();
//			}
//		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filterroom = new FilterInfo();
		filterroom.getFilterItems().add(new FilterItemInfo("building",((BuildingInfo)node.getUserObject()).getId().toString()));
		view.setFilter(filterroom);
		
		SelectorItemCollection sel= SHEHelper.getRoomSelector(new SelectorItemCollection());
		sel.add("sub");
		view.setSelector(sel);
//		view.getSelector().add("*");
//		view.getSelector().add("building.id");
//		view.getSelector().add("building.*");
//		view.getSelector().add("building.name");
//		view.getSelector().add("building.number");
//		view.getSelector().add("building.sellProject.*");
//		view.getSelector().add("buildUnit.*");
//		view.getSelector().add("displayName");
//
//		view.getSelector().add("building.sellProject.projectResource");
//
//		view.getSelector().add("name");
//		view.getSelector().add("number");
//		view.getSelector().add("unitBegin");
//		view.getSelector().add("unitEnd");
//		view.getSelector().add("floorBegin");
//		view.getSelector().add("floorEnd");
//		view.getSelector().add("natrueFloorBegin");
//		view.getSelector().add("natrueFloorEnd");
//		view.getSelector().add("serialNumberBegin");
//		view.getSelector().add("serialNumberEnd");
//		view.getSelector().add("buildingArea");
//		view.getSelector().add("roomArea");
//		view.getSelector().add("apportionArea");
//		view.getSelector().add("displayName");
//		
//		view.getSelector().add("floorHeight");
//		view.getSelector().add("roomModel.*");
//		view.getSelector().add("buildingProperty.*");
//		view.getSelector().add("houseProperty");
//
//		view.getSelector().add("actualBuildingArea");
//		view.getSelector().add("actualRoomArea");
//		view.getSelector().add("direction.*");
//
//		view.getSelector().add("productType.*");
//		view.getSelector().add("isForSHE");
//		view.getSelector().add("isForTen");
//		view.getSelector().add("isForPPM");
//
//		view.getSelector().add("flatArea");
//		view.getSelector().add("carbarnArea");
//		view.getSelector().add("planRoomArea");
//		view.getSelector().add("planBuildingArea");
//		view.getSelector().add("useArea");
//		view.getSelector().add("guardenArea");
//		view.getSelector().add("builStruct.*");
//		view.getSelector().add("bizDate");
//		view.getSelector().add("newSight.*");
//		
//		view.getSelector().add("buildUnit.*");
//		//合并值
//		view.getSelector().add("sub");
		RoomCollection rooms = RoomFactory.getRemoteInstance().getRoomCollection(view);
		if(rooms.size()==0){
			FDCMsgBox.showWarning("请先生成房间！");
			return;
		}
		
		RoomNewCreateUI.showWindows(this, rooms,node,Boolean.FALSE);
//		RoomCreateUI.showWindows(this, rooms);
	}
//	//回写值，因为在房源图形生成界面里如果点退出，不生成，生成的新单元信息便成为脏数据所以要回写下
//	if(null!=building){
//		BuildingUnitFactory.getRemoteInstance().updateBuildingUnitbyBuild(building);
//	}
    refresh(null);
    	
    	
    	
    	
	}

    /**
     * 响应刷新按钮
     */
    public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
    {
    	/* yinkang	20070828	开启了虚模式查询缓存，刷新时不再清空缓存区，损失太大 */
    	/*
    	if (e!=null) {
    		CacheServiceFactory.getInstance().discardQuery(this.mainQueryPK);
    	}
    	 */
    	/* 修改结束 */
        refresh(null);
//        this.initTree();
    }
    /**
     * output kDTabbedPane1_stateChanged method
     */
    String tabbedPanelName = "";
    protected void kDTabbedPane1_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {	
    	treeMain_valueChanged(null);
    }
    public DefaultKingdeeTreeNode getTreeRoot(){
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode)treeMain.getModel().getRoot();
		return root;
	}
    /**
	 * 鸟瞰图穿透到楼栋后，树状节点也选中该楼栋
	 * add by wanping
	 * */
	public void setTreeMain_valueChanged(DefaultKingdeeTreeNode root,DataBaseInfo obj){
		for(int i = 0 ; i < root.getChildCount() ; i++){
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)root.getChildAt(i);
			if(obj  instanceof BuildingInfo){
				BuildingInfo building = (BuildingInfo)obj;
				if(node!=null && node.getUserObject()!=null && node.getUserObject() instanceof BuildingInfo
						&& ((BuildingInfo)node.getUserObject()).getId().toString().equals(building.getId().toString())){
					treeMain.setSelectionNode(node);
					break;
				}
			}
			else if(obj  instanceof SellProjectInfo){
				SellProjectInfo project = (SellProjectInfo)obj;
				if(node!=null && node.getUserObject()!=null && node.getUserObject() instanceof SellProjectInfo
						&& ((SellProjectInfo)node.getUserObject()).getId().toString().equals(project.getId().toString())){
					treeMain.setSelectionNode(node);
					break;
				}
			}
			setTreeMain_valueChanged(node,obj);
		}
	}
    /**
	 * 保存标签 add by wanping
	 * */
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		Component[] com = BirdEyePanel2.getComponents();
    	for(int i=0;i<com.length;i++){
    		if(com[i] instanceof KDLabel){
    			KDLabel lab = (KDLabel)com[i];
        		BirdEyeInfo info = BirdEyeShowUI.getLabeltBirdEyeInfo(lab.getName());
        		info.setLocationX(lab.getX());
        		info.setLocationY(lab.getY());
        		info.setSellProjectOrBuildingId(lab.getName());// itv7jtdhT5e0ba1luJafZy/75aw=
        		BirdEyeFactory.getRemoteInstance().save(info);
    		}
    	}
    	DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(node!=null && node.getUserObject() instanceof SellProjectInfo){
			SellProjectInfo project = (SellProjectInfo)node.getUserObject();
			BirdEyePicInfo picInfo = BirdEyeShowUI.getBirdEyePicInfo(project.getId().toString());
			if(pic!=null){
				picInfo.setPicFile(pic.getPicFile());
				picInfo.setPicPath(pic.getPicPath());
				picInfo.setSellProject(project);
				BirdEyePicFactory.getRemoteInstance().save(picInfo);
			}
			//alter table  t_she_birdeyepic modify column FPICFILE blob;
		}
		MsgBox.showInfo("保存成功！");
	}
	 /**
	 * 上传鸟瞰图 add by wanping
	 * */
	public void actionPic_actionPerformed(ActionEvent e) throws Exception {
		super.actionPic_actionPerformed(e);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(node!=null && node.getUserObject() instanceof SellProjectInfo){
			pic = BirdEyeShowUI.showImgChooser(BirdEyePanel2);
		}
	}
	BirdEyePicInfo pic ;
	SHE2ImagePanel BirdEyePanel2;
	KDScrollPane scrollPanel;
}
