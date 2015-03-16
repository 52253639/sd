/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.BizEnumValueDTO;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.FeeMoneyTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevMoneyTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeBalanceObjectEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomFactory;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomInfo;
import com.kingdee.eas.fdc.sellhouse.CusRevListFactory;
import com.kingdee.eas.fdc.sellhouse.CusRevListInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FeeFromTypeEnum;
import com.kingdee.eas.fdc.sellhouse.GenFdcTrasfBillFactory;
import com.kingdee.eas.fdc.sellhouse.InvoiceInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneySubjectContrastInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SettleMentFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ChangeRoomListUI extends AbstractChangeRoomListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ChangeRoomListUI.class);

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	public ChangeRoomListUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		super.onLoad();
		//this.actionAttachment.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionUnAudit.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.actionAuditResult.setVisible(false);
		if(!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		
		
		initTree();
		
		//add by jiyu_guan
		actionRetakeCheque.setEnabled(true);

	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getUnitTree(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.treeMain.setSelectionNode((DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot());
	}


	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {

		this.execQuery();
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ChangeRoomFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return ChangeRoomEditUI.class.getName();
	}
	
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {

		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();

		//orgUnit.name	sellProject.name	building.name	buildUnit.name
		FilterInfo filter = new FilterInfo();
		if(node!=null) {
			if (node.getUserObject() instanceof BuildingUnitInfo) {
				this.tblMain.getColumn("orgUnit.name").getStyleAttributes().setHided(true);
				this.tblMain.getColumn("sellProject.name").getStyleAttributes().setHided(true);
				this.tblMain.getColumn("building.name").getStyleAttributes().setHided(true);
				this.tblMain.getColumn("buildUnit.name").getStyleAttributes().setHided(true);	
				BuildingUnitInfo buildUnit = (BuildingUnitInfo) node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("oldRoom.buildUnit.id", buildUnit.getId().toString()));
			} else if (node.getUserObject() instanceof BuildingInfo) {
				this.tblMain.getColumn("orgUnit.name").getStyleAttributes().setHided(true);
				this.tblMain.getColumn("sellProject.name").getStyleAttributes().setHided(true);
				this.tblMain.getColumn("building.name").getStyleAttributes().setHided(true);
				this.tblMain.getColumn("buildUnit.name").getStyleAttributes().setHided(false);	
				BuildingInfo building = (BuildingInfo) node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("building.id", building.getId().toString()));
			} else if (node.getUserObject() instanceof SellProjectInfo) {
				this.tblMain.getColumn("orgUnit.name").getStyleAttributes().setHided(true);
				this.tblMain.getColumn("sellProject.name").getStyleAttributes().setHided(true);
				this.tblMain.getColumn("building.name").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("buildUnit.name").getStyleAttributes().setHided(false);					
				SellProjectInfo sellPro = (SellProjectInfo)node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellPro.getId().toString()));
			} else if (node.getUserObject() instanceof SubareaInfo) {
				SubareaInfo subAreaInfo = (SubareaInfo)node.getUserObject();
				this.tblMain.getColumn("orgUnit.name").getStyleAttributes().setHided(true);
				this.tblMain.getColumn("sellProject.name").getStyleAttributes().setHided(true);
				this.tblMain.getColumn("building.name").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("buildUnit.name").getStyleAttributes().setHided(false);					
				SellProjectInfo sellPro = subAreaInfo.getSellProject();
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellPro.getId().toString()));
			} else if(node.getUserObject() instanceof OrgStructureInfo){
				OrgStructureInfo orgInfo = (OrgStructureInfo)node.getUserObject();
				Map sellProMap = FDCTreeHelper.getAllObjectIdMap(node, "SellProject");
				Iterator iter = sellProMap.keySet().iterator();
				Set sellProIdSet = new HashSet();
				while(iter.hasNext())
					sellProIdSet.add(iter.next());
				if(sellProIdSet.size()>0)
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProIdSet, CompareType.INCLUDE));
				else
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", null));
				if(orgInfo.isIsLeaf())
					this.tblMain.getColumn("orgUnit.name").getStyleAttributes().setHided(true);
				else
					this.tblMain.getColumn("orgUnit.name").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("sellProject.name").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("building.name").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("buildUnit.name").getStyleAttributes().setHided(false);				
			}else{
				filter.getFilterItems().add(new FilterItemInfo("oldRoom.id", "roomIDNull"));
				this.tblMain.getColumn("orgUnit.name").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("sellProject.name").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("building.name").getStyleAttributes().setHided(false);
				this.tblMain.getColumn("buildUnit.name").getStyleAttributes().setHided(false);
			}
			
			if(!(node.getUserObject() instanceof BuildingUnitInfo)) {
				Map unitMap = FDCTreeHelper.getAllObjectIdMap(node, "BuildingUnit");
				if(unitMap.size()==0) 
					this.tblMain.getColumn("buildUnit.name").getStyleAttributes().setHided(true);
				else
					this.tblMain.getColumn("buildUnit.name").getStyleAttributes().setHided(false);
			}
		}
		
		viewInfo = (EntityViewInfo) this.mainQuery.clone();
		if (viewInfo.getFilter() == null)
			viewInfo.setFilter(filter);
		else {
			try {
				viewInfo.getFilter().mergeFilter(filter, "AND");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}		
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(node!=null) {
			if (node.getUserObject() instanceof BuildingUnitInfo) {
				BuildingUnitInfo buildUnit = (BuildingUnitInfo) node.getUserObject();
				uiContext.put("BuildUnit", buildUnit);
				DefaultKingdeeTreeNode parentNode = (DefaultKingdeeTreeNode)node.getParent();
				BuildingInfo building = (BuildingInfo)parentNode.getUserObject();
				uiContext.put("Building", building);
			} else if (node.getUserObject() instanceof BuildingInfo) {
				BuildingInfo building = (BuildingInfo) node.getUserObject();
				uiContext.put("Building", building);
			} else if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo sellPro = (SellProjectInfo)node.getUserObject();
				uiContext.put("SellProject", sellPro);
			} else if(node.getUserObject() instanceof OrgStructureInfo){
				OrgStructureInfo objectInfo = (OrgStructureInfo)node.getUserObject();
				uiContext.put("OrgStructure", objectInfo);
			}
		}
	}	


	

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择要修改的换房单！");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo state = (BizEnumValueInfo)row.getCell("state").getValue();
		if (state==null || (!state.getValue().equals(FDCBillStateEnum.SAVED_VALUE) && !state.getValue().equals(FDCBillStateEnum.SUBMITTED_VALUE))) {
			MsgBox.showInfo("非保存或提交状态的换房单不能修改！");
			return;
		}
		super.actionEdit_actionPerformed(e);
	}
	
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择要删除的换房单！");
			return;
		}
		
 		IRow row = this.tblMain.getRow(rowIndex);
		BizEnumValueInfo state = (BizEnumValueInfo)row.getCell("state").getValue();
		if (state==null || (!state.getValue().equals(FDCBillStateEnum.SAVED_VALUE) && !state.getValue().equals(FDCBillStateEnum.SUBMITTED_VALUE))) {
			MsgBox.showInfo("非保存或提交状态的换房单不能删除！");
			return;
		}
		super.actionRemove_actionPerformed(e);
	}
	
	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		String idStr = this.getSelectedKeyValue();
		ChangeRoomInfo tempChangeInfo = ChangeRoomFactory.getRemoteInstance()
							.getChangeRoomInfo(new ObjectUuidPK(BOSUuid.read(idStr)));
		if (tempChangeInfo.getState()== null || !tempChangeInfo.getState().equals(FDCBillStateEnum.SUBMITTED)) {
			MsgBox.showInfo("非提交状态的换房单不能审批!");
			return;
		}
		ChangeRoomFactory.getRemoteInstance().audit(BOSUuid.read(idStr));
		this.refresh(null);
	}
	
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		String idStr = this.getSelectedKeyValue();
		ChangeRoomInfo tempChangeInfo = ChangeRoomFactory.getRemoteInstance()
							.getChangeRoomInfo(new ObjectUuidPK(BOSUuid.read(idStr)));
		if (tempChangeInfo.getState()== null || !tempChangeInfo.getState().equals(FDCBillStateEnum.AUDITTED)) {
			MsgBox.showInfo("非审批状态的换房单不能反审批!");
			return;
		}
		ChangeRoomFactory.getRemoteInstance().unAudit(BOSUuid.read(idStr));
		this.refresh(null);
	}
	
	
	
	//换房结算
	public void actionSettlement_actionPerformed(ActionEvent e)		throws Exception {
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择要结算的换房单!");
			return;
		}
						
		String idStr = this.getSelectedKeyValue();
		if (MsgBox.showConfirm2("您确定要结算吗？")!= MsgBox.YES) return;	
		ChangeRoomFactory.getRemoteInstance().settleMent(BOSUuid.read(idStr));
		MsgBox.showInfo("结算成功！");

	}
	
	
	
	/**
	 * 回收票据 add by jiyu_guan
	 */
	public void actionRetakeCheque_actionPerformed(ActionEvent e)
			throws Exception {
		IRow row = KDTableUtil.getSelectedRow(tblMain);
		if (row == null) {
			MsgBox.showWarning(this, "请先选中一张换房单!");
			SysUtil.abort();
		}
		String stateName = ((BizEnumValueDTO) row.getCell("state").getValue())
				.getName();
		if (!stateName.equals(FDCBillStateEnum.AUDITTED.getName())) {
			MsgBox.showWarning(this, "当前单据非审批状态，不能做回收票据操作!");
			return;
		}
		Map ctx = new UIContext(this);
		String chrID = (String) row.getCell("id").getValue();
		ctx.put("sourceID", chrID);
		ctx.put("sourceType", "换房");
		ctx.put(UIContext.OWNER, this);

		IUIWindow uiWindow = null;
		// 弹出模式
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
				MackOutRetakeChequeUI.class.getName(), ctx, null,
				OprtState.ADDNEW);

		// 开始展现UI
		uiWindow.show();
		// 清缓存，重新从数据库查一次
		CacheServiceFactory.getInstance().discardType(
				new InvoiceInfo().getBOSType());
		refresh(null);
	}
	
	
	
	
	
	
	
}