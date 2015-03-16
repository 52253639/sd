/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.List;
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
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.PeriodFactory;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolUtils;
import com.kingdee.eas.basedata.assistant.VoucherTypeFactory;
import com.kingdee.eas.basedata.master.account.AccountViewFactory;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
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
import com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellOrderRoomEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectResourceEnum;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.fi.gl.EntryDC;
import com.kingdee.eas.fi.gl.SourceType;
import com.kingdee.eas.fi.gl.VoucherEntryCollection;
import com.kingdee.eas.fi.gl.VoucherEntryInfo;
import com.kingdee.eas.fi.gl.VoucherFactory;
import com.kingdee.eas.fi.gl.VoucherInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class RoomListUI extends AbstractRoomListUI
{
	private static final Logger logger = CoreUIObject
			.getLogger(RoomDesListUI.class);

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	RoomDisplaySetting setting = new RoomDisplaySetting();
	Integer floor = null;
	Integer unit = null;
	BuildingUnitInfo buildUnit = null;
	BuildingFloorEntryInfo buildFloorEntry = null;
	BuildingInfo build = null;
	RoomInfo selectedRoom = null;

	/**
	 * output class constructor
	 */
	public RoomListUI() throws Exception
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

	protected String getEditUIName()
	{
		return RoomEditUI.class.getName();
	}

	protected void initTree() throws Exception
	{
		this.treeMain.setModel(FDCTreeHelper.getUnitTree(this.actionOnLoad,null));
		// this.treeMain.setModel(SHEHelper.getPlanisphere(this.actionOnLoad));
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

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception
	{
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null)
		{
			return;
		}

		FilterInfo filter = new FilterInfo();
		setColumnNotHidden();

		if (node.getUserObject() instanceof BuildingUnitInfo
				|| node.getUserObject() instanceof BuildingInfo
				|| node.getUserObject() instanceof SubareaInfo
				|| node.getUserObject() instanceof SellProjectInfo)
			SHEHelper.fillRoomListTableByNode(this.tblGraph, node, null,
					setting, this.kDScrollPane1);

		// RoomDisplaySetViewUI.insertUIToScrollPanel(this.kDScrollPane1);
		this.mainQuery.getSorter().clear();
		floor = null;
		buildUnit = null;
		build = null;
		buildFloorEntry = null;
		selectedRoom = null;
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
			// SHEHelper.fillRoomTableByNode(this.tblGraph,node, null, null);
			if (saleOrg.isIsBizUnit())
			{
				this.actionAddNew.setEnabled(true);
				if (this.tblGraph.getRowCount() < 1)
				{
					this.btnSeqAdjustmentShow.setEnabled(false);
				} else
				{
					this.btnSeqAdjustmentShow.setEnabled(true);
					buildUnit = unit;
				}
			}

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
			if (saleOrg.isIsBizUnit())
			{
				this.actionAddNew.setEnabled(true);
				if (this.tblGraph.getRowCount() < 1)
				{
					this.btnSeqAdjustmentShow.setEnabled(false);
				} else
				{
					this.btnSeqAdjustmentShow.setEnabled(true);
					build = building;
				}
			}
		} else if (node.getUserObject() instanceof SubareaInfo)
		{ // 分区
			SubareaInfo subarea = (SubareaInfo) node.getUserObject();
			filter.getFilterItems().add(
					new FilterItemInfo("building.subarea.id", subarea.getId()
							.toString()));
			this.mainQuery.getSorter().add(new SorterItemInfo("building.id"));
			this.mainQuery.getSorter().add(new SorterItemInfo("unit"));

			tblMain.getColumn("subarea").getStyleAttributes().setHided(true);
			if (saleOrg.isIsBizUnit())
			{
				this.actionAddNew.setEnabled(false);
			}
			this.btnSeqAdjustmentShow.setEnabled(false);
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
			if (saleOrg.isIsBizUnit())
			{
				// this.actionAddNew.setEnabled(true);
				this.actionAddNew.setEnabled(false);// Add By wenyaowei
				// 2009-06-05
			}
			this.btnSeqAdjustmentShow.setEnabled(false);
		} else
		{
			filter.getFilterItems().add(new FilterItemInfo("id", null));
			tblGraph.removeColumns();
			tblGraph.removeHeadRows();
			tblGraph.removeRows();
			this.actionAddNew.setEnabled(false);
			this.btnSeqAdjustmentShow.setEnabled(false);
		}
		this.mainQuery.setFilter(filter);
		// this.mainQuery.getSorter().add(new SorterItemInfo("floor"));
		// this.mainQuery.getSorter().add(new SorterItemInfo("serialNumber"));

		this.mainQuery.getSorter().add(new SorterItemInfo("number"));
		this.tblMain.removeRows();
	}

	public void onLoad() throws Exception
	{
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		this.actionEditRoomBind.setEnabled(true);
		this.actionMerge.setEnabled(true);
		this.actionSplit.setEnabled(true);
		this.actionQuery.setVisible(false);
		this.btnSeqAdjustmentShow.setVisible(true);
		this.btnSeqAdjustmentShow.setEnabled(true);
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
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.isIsBizUnit())
		{
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionMerge.setEnabled(false);
			this.actionSplit.setEnabled(false);
			this.actionEditRoomBind.setEnabled(false);
		}
		// UI的排序对未绑定字段的Table忽略，这里重简单实现排序
		SimpleKDTSortManager.setTableSortable(tblMain);
		RoomDisplaySetViewUI.insertUIToScrollPanel(this.kDScrollPane1,
				SellProjectResourceEnum.DEVELOPER);
		this.actionInit.setEnabled(false);
		this.actionInit.setVisible(false);
		
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
		treeMain_valueChanged(null);
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
				if (room.getHouseProperty()
						.equals(HousePropertyEnum.Attachment))
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

	private boolean checkCanMergeAndSplit(List roomList,boolean isMerge) throws EASBizException, BOSException{
		String warning="拆分！";
		boolean isRow=true;
		if(isMerge){
			warning="合并！";
		}
		if(this.tblGraph.getSelectManager().size()!=1){
			FDCMsgBox.showWarning(this,"请选择同层或者同序号的房间进行"+warning);
			SysUtil.abort();
		}
		for (int b = 0; b < this.tblGraph.getSelectManager().size(); b++){
			KDTSelectBlock block = this.tblGraph.getSelectManager().get(b);
			if(block.getBeginRow()!=block.getEndRow()&& block.getBeginCol()!=block.getEndCol()){
				FDCMsgBox.showWarning(this,"请选择同层或者同序号的房间进行"+warning);
				SysUtil.abort();
			}
			if(block.getBeginRow()!=block.getEndRow()){
				isRow=false;
			}
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
						if (SellOrderRoomEntryFactory.getRemoteInstance().exists(filter)){
							FDCMsgBox.showWarning(this,"有选择的房间已经进入推盘管理,不能再"+warning);
							SysUtil.abort();
						}
					}
					roomList.add(room);
				}
			}
		}
		return isRow;
	}
	public void actionMerge_actionPerformed(ActionEvent e) throws Exception {
		Set deleteRoomSet = new HashSet();
		List roomList = new ArrayList();
		boolean isRow=checkCanMergeAndSplit(roomList,true);
		RoomInfo firstRoom=null;
		String sub="";
		if(!isRow){
			List reRoomList = new ArrayList();
			for(int i=roomList.size()-1;i>=0;i--){
				reRoomList.add(roomList.get(i));
			}
			roomList=reRoomList;
		}
		for (int i=0;i<roomList.size();i++){
			RoomInfo room = (RoomInfo) roomList.get(i);
			if (room == null){
				continue;
			}
			if(room.getSub()!=null&&room.getSub().indexOf(",")>0){
				FDCMsgBox.showWarning(this,"请拆分已合并房间后再进行拆分！");
				SysUtil.abort();
			}
			sub=sub+","+room.getFloor()+"-"+room.getSerialNumber();
			
			if (firstRoom==null){
				firstRoom = room;
			} else{
				deleteRoomSet.add(room.getId().toString());
				
				if (firstRoom.getId().toString().equals(room.getId().toString())){
					continue;
				}
				String firstUnitIdStr = "";
				String thisRoomUnitIdStr = "";
				if(firstRoom.getBuildUnit()!=null) firstUnitIdStr = firstRoom.getBuildUnit().getId().toString();
				if(room.getBuildUnit()!=null) thisRoomUnitIdStr = room.getBuildUnit().getId().toString();
				if (!firstUnitIdStr.equals(thisRoomUnitIdStr)) {
					FDCMsgBox.showWarning(this,"请选择同单元的房间合并！");
					SysUtil.abort();
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
			}
		}
		if (deleteRoomSet.size() == 0)
		{
			FDCMsgBox.showWarning(this,"请选择多个房间合并！");
			SysUtil.abort();
		} else
		{
			if (MsgBox.showConfirm2New(this, "确认合并房间吗？") != MsgBox.YES)
			{
				return;
			}
		}
		firstRoom.setSub(sub.replaceFirst(",", ""));
		firstRoom.setRoomPropNo(firstRoom.getNumber());

		RoomFactory.getRemoteInstance().submit(firstRoom);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", deleteRoomSet, CompareType.INCLUDE));
		RoomFactory.getRemoteInstance().delete(filter);
		this.refresh(null);
	}
	private void setAverRoomArea(int rowCount,int rowIndex,RoomInfo room,RoomInfo newRoom){
		if(!(rowIndex==rowCount-1)){//当不等于最后一行的时候 每行位总数的均值
			if (room.getBuildingArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getBuildingArea()) != 0)
					newRoom.setBuildingArea(FDCHelper.divide(room.getBuildingArea(), new Integer(rowCount),3,4));}
			if (room.getRoomArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getRoomArea()) != 0)
					newRoom.setRoomArea(FDCHelper.divide(room.getRoomArea(), new Integer(rowCount),3,4));}
			if (room.getApportionArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getApportionArea()) != 0)
					newRoom.setApportionArea(FDCHelper.divide(room.getApportionArea(), new Integer(rowCount),3,4));}
			if (room.getBalconyArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getBalconyArea()) != 0)
					newRoom.setBalconyArea(FDCHelper.divide(room.getBalconyArea(), new Integer(rowCount),3,4));}
			if (room.getGuardenArea() != null ){
				if( FDCHelper.ZERO.compareTo((BigDecimal)room.getGuardenArea()) != 0)
					newRoom.setGuardenArea(FDCHelper.divide(room.getGuardenArea(), new Integer(rowCount),3,4));}
			if (room.getCarbarnArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getCarbarnArea()) != 0)
					newRoom.setCarbarnArea(FDCHelper.divide((BigDecimal)room.getCarbarnArea(), new Integer(rowCount),3,4));}
			if (room.getUseArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getUseArea()) != 0)
					newRoom.setUseArea(FDCHelper.divide(room.getUseArea(), new Integer(rowCount),3,4));}
			if (room.getFlatArea() != null ){
				if(FDCHelper.ZERO.compareTo( (BigDecimal)room.getFlatArea()) != 0)
					newRoom.setFlatArea(FDCHelper.divide(room.getFlatArea(), new Integer(rowCount),3,4));}
			if (room.getActualBuildingArea() != null  ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getActualBuildingArea())!= 0)
					newRoom.setActualBuildingArea(FDCHelper.divide(room.getActualBuildingArea(), new Integer(rowCount),3,4));}
			if (room.getActualRoomArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getActualRoomArea()) != 0)
					newRoom.setActualRoomArea(FDCHelper.divide(room.getActualRoomArea(), new Integer(rowCount),3,4));}
			if (room.getTenancyArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getTenancyArea()) != 0)
					newRoom.setTenancyArea(FDCHelper.divide(room.getTenancyArea(), new Integer(rowCount),3,4));}
		}else{//当为最后一行是，本行= 总行 - 之前行的值之和; 
			if (room.getBuildingArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getBuildingArea()) != 0)
					newRoom.setBuildingArea(room.getBuildingArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getBuildingArea(), new Integer(rowCount),3,4),new Integer(rowIndex))));}
			if (room.getRoomArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getRoomArea()) != 0)
					newRoom.setRoomArea(room.getRoomArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getRoomArea(), new Integer(rowCount),3,4),new Integer(rowIndex))));}
			if (room.getApportionArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getApportionArea()) != 0)
					newRoom.setApportionArea(room.getApportionArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getApportionArea(), new Integer(rowCount),3,4),new Integer(rowIndex))));}
			if (room.getBalconyArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getBalconyArea()) != 0)
					newRoom.setBalconyArea(room.getBalconyArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getBalconyArea(), new Integer(rowCount),3,4),new Integer(rowIndex))));}
			if (room.getGuardenArea() != null ){
				if( FDCHelper.ZERO.compareTo((BigDecimal)room.getGuardenArea()) != 0)
					newRoom.setGuardenArea(room.getGuardenArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getGuardenArea(), new Integer(rowCount),3,4),new Integer(rowIndex))));}
			if (room.getCarbarnArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getCarbarnArea()) != 0)
					newRoom.setCarbarnArea(room.getCarbarnArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getCarbarnArea(), new Integer(rowCount),3,4),new Integer(rowIndex))));}
			if (room.getUseArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getUseArea()) != 0)
					newRoom.setUseArea(room.getUseArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getUseArea(), new Integer(rowCount),3,4),new Integer(rowIndex))));}
			if (room.getFlatArea() != null ){
				if(FDCHelper.ZERO.compareTo( (BigDecimal)room.getFlatArea()) != 0)
					newRoom.setFlatArea(room.getFlatArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getFlatArea(), new Integer(rowCount),3,4),new Integer(rowIndex))));}
			if (room.getActualBuildingArea() != null  ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getActualBuildingArea())!= 0)
					newRoom.setActualBuildingArea(room.getActualBuildingArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getActualBuildingArea(), new Integer(rowCount),3,4),new Integer(rowIndex))));}
			if (room.getActualRoomArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getActualRoomArea()) != 0)
					newRoom.setActualRoomArea(room.getActualRoomArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getActualRoomArea(), new Integer(rowCount),3,4),new Integer(rowIndex))));}
			if (room.getTenancyArea() != null ){
				if(FDCHelper.ZERO.compareTo((BigDecimal)room.getTenancyArea()) != 0)
					newRoom.setTenancyArea(room.getTenancyArea().subtract(FDCHelper.multiply(FDCHelper.divide(room.getTenancyArea() ,new Integer(rowCount),3,4),new Integer(rowIndex))));
				}
		}
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
	public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
		Set roomId=new HashSet();
		String id=null;
		List roomList = new ArrayList();
		checkCanMergeAndSplit(roomList,false);
		
		for (int i=0;i<roomList.size();i++){
			RoomInfo room = (RoomInfo) roomList.get(i);
			if (room == null){
				continue;
			}
			id=room.getId().toString();
			roomId.add(room.getId().toString());
		}
		if(roomId.size()>1){
			FDCMsgBox.showWarning(this,"请选择已经合并房间进行拆分！");
			SysUtil.abort();
		}
		if(((RoomInfo)roomList.get(0)).getSub()==null||((RoomInfo)roomList.get(0)).getSub().indexOf(",")<0){
			FDCMsgBox.showWarning(this,"请选择已经合并房间进行拆分！");
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

			int serialNumber=Integer.parseInt(sub[i].split("-")[1]);
			
			newRoom.setSerialNumber(Integer.parseInt(sub[i].split("-")[1]));
			newRoom.setEndSerialNumber(Integer.parseInt(sub[i].split("-")[1]));
			
			String floor=sub[i].split("-")[0];
			
			String strNum=""+serialNumber;
			if (serialNumber < 10) {
				strNum = "0" + serialNumber;
			}
			strNum=room.getUnit()+"-"+floor+strNum;
			
			newRoom.setDisplayName(strNum);
			
			//存长编码，长名称，掉高明方法，目前存短的
			newRoom.setName(strNum);
			newRoom.setNumber(strNum);
			
			
			newRoom.setRoomNo(strNum);
			newRoom.setRoomPropNo(strNum);
			
			setAverRoomArea(sub.length,i,room,newRoom);
			
			newRoom.setRentType(null);
			newRoom.setStandardRent(null);
			newRoom.setStandardRentPrice(null);
			
			BuildingFloorEntryInfo buildingFloorEntryInfo=getBuildingFloorEntryInfo(newRoom.getBuilding(),sub[i].split("-")[0]);
			if(buildingFloorEntryInfo!=null){
				newRoom.setBuildingFloor(buildingFloorEntryInfo);
			}
			
			newRoom.setFloor(Integer.parseInt(sub[i].split("-")[0]));
			newRoom.setSub(sub[i]);
			RoomFactory.getRemoteInstance().submit(newRoom);
		}
		
		this.refresh(null);
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
					if (!room.getSellState().equals(RoomSellStateEnum.Init))
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
		checkSelected();
		if (confirmRemove())
		{
			// prepareRemove(null).callHandler();
			Remove();
			refresh(e);
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception
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
					if (!room.getSellState().equals(RoomSellStateEnum.Init)
							&& !room.getSellState().equals(
									RoomSellStateEnum.OnShow))
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
		this.refresh(null);
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
		if(SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit()){
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
		}else{
			MsgBox.showWarning("非实体销售组织，无法批改房号面积！");
		}
	}
	
	/**
	 * 批改房间资料
	 */
	public void actionBatchModifyRoomUpdate_actionPerformed(ActionEvent e)
			throws Exception {
		if(SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit()){
			List list=getSelectedIdValues();
			if(list.size()>0){
				UIContext uiContext=new UIContext();
				uiContext.put("idList",list);
				IUIWindow iuiWindow=UIFactory.createUIFactory(UIFactoryName.MODEL).create
				(RoomUpdateBatchEditUI.class.getName(),uiContext,null,OprtState.ADDNEW);
				iuiWindow.show();
				this.refreshList();
			}else{
				MsgBox.showWarning("请先选择房间！");
			}
		}else{
			MsgBox.showWarning("非实体销售组织，无法批改房间资料！");
		}
	}
}