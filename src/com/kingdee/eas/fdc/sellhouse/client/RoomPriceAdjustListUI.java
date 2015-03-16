/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTabbedPane;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomActChangeStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPlanChangeStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomPreChangeStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBuildingEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBuildingEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPriceManageFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPriceManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

public class RoomPriceAdjustListUI extends AbstractRoomPriceAdjustListUI {
	private static final Logger logger = CoreUIObject.getLogger(RoomPriceAdjustListUI.class);
	
	private boolean isKdpRoom = true;
	public RoomPriceAdjustListUI() throws Exception {
		super();
	}

	public void storeFields() {
		super.storeFields();
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node!=null && !(node.getUserObject() instanceof OrgStructureInfo) && FDCSysContext.getInstance().checkIsSHEOrg()) {
			this.actionAddNew.setEnabled(true);
			if(this.kDTabbedPane1.getSelectedIndex() == 1){
				this.actionEdit.setEnabled(true);
				this.actionRemove.setEnabled(true);
				this.actionAudit.setEnabled(true);
				this.actionExecute.setEnabled(true);
			}
		}
		else {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionExecute.setEnabled(false);
		}
		this.execQuery();
	}
	
	protected void kdRoomTree_valueChanged(TreeSelectionEvent e)
			throws Exception {
		fillRoomPriceData();
	}

	protected void tblRoomPrice_tableClicked(KDTMouseEvent e) throws Exception {
		int temp = e.getClickCount();
		if (temp != 2)
			return;
		int i = e.getRowIndex();
		IRow row = this.tblRoomPrice.getRow(i);
		if (row == null)
			return;

		String id = row.getCell("id").getValue().toString();

		UIContext uiContext = new UIContext(this);
		uiContext.put("ID", id);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(RoomPriceHistoryListUI.class.getName(), uiContext, null, "VIEW");
		uiWindow.show();
	}

	public void onLoad() throws Exception {
		super.onLoad();

		initControl();

		this.initTree();

		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblRoomPrice.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		this.tblRoomPrice.getStyleAttributes().setLocked(true);

		SimpleKDTSortManager.setTableSortable(tblMain);

		this.tHelper = new TablePreferencesHelper(this);

		this.kDTabbedPane1.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				setBtnState();
			}

		});
		String[] fields=new String[tblRoomPrice.getColumnCount()];
		for(int i=0;i<tblRoomPrice.getColumnCount();i++){
			fields[i]=tblRoomPrice.getColumnKey(i);
		}
		KDTableHelper.setSortedColumn(tblRoomPrice,fields);
	}
	
	public void onShow() throws Exception {
		super.onShow();
		int tabIndex = this.kDTabbedPane1.getSelectedIndex();
		if (1 == tabIndex) { // 定价管理
			this.actionWorkFlowG.setEnabled(true);
		} else { // 房间价格
			this.actionWorkFlowG.setEnabled(false);
		}
	}

	private void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad, MoneySysTypeEnum.SalehouseSys));
		
		this.kdRoomTree.setModel(FDCTreeHelper.getSellProjectForSHESellProject(this.actionOnLoad, MoneySysTypeEnum.SalehouseSys));
	}

	private void initControl() {
		txtSumAmount.setVisible(true);
		txtBuildArea.setVisible(true);
		txtRoomArea.setVisible(true);
		txtAvgAmount.setVisible(true);
		txtAvgBuild.setVisible(true);
		txtAvgRoom.setVisible(true);

		this.actionAddNew.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setEnabled(false);
		this.actionExecute.setEnabled(false);
		this.actionAudit.setEnabled(false);
		this.actionView.setEnabled(false);

		this.actionAttachment.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.actionAuditResult.setVisible(true);

		this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
		this.btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
		
		this.btnExecute.setIcon(EASResource.getIcon("imgTbtn_execute"));

		this.btnViewPriceHis.setIcon(EASResource.getIcon("imgTbtn_demandcollateresult"));

		this.setBtnState();

		// 非实体销售组织下才显示组织名称
		if (!FDCSysContext.getInstance().checkIsSHEOrg()) { // 非实体销售组织
			tblMain.getColumn("orgName").getStyleAttributes().setHided(false);
		} else {
			tblMain.getColumn("orgName").getStyleAttributes().setHided(true);
		}
		
		this.actionWorkFlowG.setVisible(true);
		this.actionWorkFlowG.setEnabled(true);
		
		HashMap value = SHEManageHelper.getCRMConstants(SysContext.getSysContext().getCurrentOrgUnit().getId());
		
//		this.tblRoomPrice.getColumn("sumAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(Integer.parseInt(value.get(CRMConstants.FDCSHE_PARAM_TOLAMOUNT_BIT).toString())));
		this.tblRoomPrice.getColumn("buildArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblRoomPrice.getColumn("roomArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblRoomPrice.getColumn("sumAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblRoomPrice.getColumn("buildPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblRoomPrice.getColumn("roomPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
//		this.tblRoomPrice.getColumn("buildPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(Integer.parseInt(value.get(CRMConstants.FDCSHE_PARAM_PRICE_BIT).toString())));
//		this.tblRoomPrice.getColumn("roomPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(Integer.parseInt(value.get(CRMConstants.FDCSHE_PARAM_PRICE_BIT).toString())));
	
	
		txtSumAmount.setPrecision(Integer.parseInt(value.get(CRMConstants.FDCSHE_PARAM_TOLAMOUNT_BIT).toString()));
		txtBuildArea.setPrecision(2);
		txtRoomArea.setPrecision(2);
		txtAvgAmount.setPrecision(Integer.parseInt(value.get(CRMConstants.FDCSHE_PARAM_PRICE_BIT).toString()));
		txtAvgBuild.setPrecision(Integer.parseInt(value.get(CRMConstants.FDCSHE_PARAM_PRICE_BIT).toString()));
		txtAvgRoom.setPrecision(Integer.parseInt(value.get(CRMConstants.FDCSHE_PARAM_PRICE_BIT).toString()));
		
		txtSumAmount.setRemoveingZeroInDispaly(false);
		txtBuildArea.setRemoveingZeroInDispaly(false);
		txtRoomArea.setRemoveingZeroInDispaly(false);
		txtAvgAmount.setRemoveingZeroInDispaly(false);
		txtAvgBuild.setRemoveingZeroInDispaly(false);
		txtAvgRoom.setRemoveingZeroInDispaly(false);
	}

	private void setBtnState() {
		int tabIndex = this.kDTabbedPane1.getSelectedIndex();
		if (1 == tabIndex) { // 定价管理
			// 除了【历史价格查询】按钮，其他按钮亮显
			ItemAction[] priceActions = new ItemAction[] { actionAddNew,
					actionEdit, actionView, actionQuery, actionRefresh,
					actionRemove, actionPrint, actionPrintPreview, actionAudit,actionUnAudit,
					actionExecute };
			FDCClientHelper.setActionEnableAndNotSetVisible(priceActions, true);

			ItemAction[] roomActions = new ItemAction[] { actionViewPriceHis };
			FDCClientHelper.setActionEnableAndNotSetVisible(roomActions, false);
			this.actionWorkFlowG.setEnabled(true);
		} else { // 房间价格
			// 【历史价格查询】按钮和【新增】按钮亮显
			ItemAction[] roomActions = new ItemAction[] { actionViewPriceHis,
					actionAddNew };
			FDCClientHelper.setActionEnableAndNotSetVisible(roomActions, true);

			ItemAction[] priceActions = new ItemAction[] { actionEdit,
					actionView, actionQuery, actionRemove,
					actionPrint, actionPrintPreview, actionAudit,actionUnAudit, actionExecute };
			FDCClientHelper
					.setActionEnableAndNotSetVisible(priceActions, false);
			
			
		}
		/*DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node!=null && !(node.getUserObject() instanceof OrgStructureInfo) && FDCSysContext.getInstance().checkIsSHEOrg()) {
			this.actionAddNew.setEnabled(true);
			if(this.kDTabbedPane1.getSelectedIndex() == 1){
				this.actionEdit.setEnabled(true);
				this.actionRemove.setEnabled(true);
				this.actionAudit.setEnabled(true);
				this.actionExecute.setEnabled(true);
			}
		}
		else {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionExecute.setEnabled(false);
		}*/
	}

	protected String getLongNumberFieldName() {
		return "number";
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		this.refresh(null);
		fillRoomPriceData();
	}

	public void kDTabbedPane1_stateChanged(javax.swing.event.ChangeEvent e) throws Exception{
	  super.kDTabbedPane1_stateChanged(e);
		if (e.getSource() != null) {
			if (((KDTabbedPane) e.getSource()).getSelectedComponent()
					.getName().equals("roomTab")) {
				isKdpRoom = true;
			} else {
				isKdpRoom = false;
			}
		}
		
	}
	private int getExistChild() {
		DefaultKingdeeTreeNode node = null;
		int number = 0;
		if(this.isKdpRoom){
			node = (DefaultKingdeeTreeNode) kdRoomTree
			.getLastSelectedPathComponent();
			if(node.getUserObject() instanceof OrgStructureInfo){
				number++;
			}else if(node.getUserObject() instanceof SellProjectInfo){
				SellProjectInfo info = (SellProjectInfo)node.getUserObject();
				Map map = FDCTreeHelper.getAllObjectIdMap(node, "SellProject");
				if(!map.isEmpty()){
					map.remove(info.getId().toString());
					if(!map.isEmpty()){
						number++;
					}
				}
			}
			
		}else{
			node = (DefaultKingdeeTreeNode) treeMain
			.getLastSelectedPathComponent();
			if (node != null) {
				number = node.getChildCount();
			}
		}
		return number;
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		if(getExistChild()>0){
			FDCMsgBox.showWarning(this,"非末级项目不能新增!");
			this.abort();
		}
		super.actionAddNew_actionPerformed(e);
	}
	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
			if (!FDCBillStateEnum.SUBMITTED.equals(getBizState(id.get(i).toString()))) {
				FDCMsgBox.showWarning("单据不是提交状态，不能进行审批操作！");
				return;
			}
			((IFDCBill)getBizInterface()).audit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		SelectorItemCollection sels =new SelectorItemCollection();
		sels.add("isExecuted");
		
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
			if (!FDCBillStateEnum.AUDITTED.equals(getBizState(id.get(i).toString()))) {
				FDCMsgBox.showWarning("单据不是审批状态，不能进行反审批操作！");
				return;
			}
			RoomPriceManageInfo price=RoomPriceManageFactory.getRemoteInstance().getRoomPriceManageInfo(new ObjectUuidPK(id.get(i).toString()), sels);
			if(price.isIsExecuted()){
				if (MsgBox.showConfirm2New(this, "定价单已执行，确定反审批?") == MsgBox.YES) {
					((IFDCBill)getBizInterface()).unAudit(BOSUuid.read(id.get(i).toString()));
				}else{
					return;
				}
			}else{
				((IFDCBill)getBizInterface()).unAudit(BOSUuid.read(id.get(i).toString()));
			}
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		FDCClientUtils.checkBillInWorkflow(this, id);
		
		FDCBillStateEnum state = getBizState(id);
		if (!FDCBillStateEnum.SAVED.equals(state)&&!FDCBillStateEnum.SUBMITTED.equals(state)) {
			FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行修改操作！");
			return;
		}
		super.actionEdit_actionPerformed(e);
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		FDCClientUtils.checkBillInWorkflow(this, id);
		
		FDCBillStateEnum state = getBizState(id);
		if (!FDCBillStateEnum.SAVED.equals(state)&&!FDCBillStateEnum.SUBMITTED.equals(state)) {
			FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行删除操作！");
			return;
		}
		super.actionRemove_actionPerformed(e);
	}
	public FDCBillStateEnum getBizState(String id) throws EASBizException, BOSException, Exception{
    	if(id==null) return null;
    	SelectorItemCollection sels =new SelectorItemCollection();
    	sels.add("state");
    	return ((FDCBillInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels)).getState();
    }
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);

		// 非实体销售组织下才显示组织名称
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!FDCSysContext.getInstance().checkIsSHEOrg()) { // 非售楼组织
			tblMain.getColumn("orgName").getStyleAttributes().setHided(false);
		} else {
			tblMain.getColumn("orgName").getStyleAttributes().setHided(true);
		}
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		try {
			//房间页签不能进行查询，所以只需考虑定价的左树即可
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			FilterInfo filter = new FilterInfo();
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			
			if (node != null && node.getUserObject() instanceof SellProjectInfo) {
				if (node.getUserObject() instanceof SellProjectInfo) { // 项目
					String allSpIdStr = FDCTreeHelper
							.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(
									node, "SellProject").keySet());
					if (allSpIdStr.trim().length() == 0) {
						allSpIdStr = "'nullnull'";
					}

					filter.getFilterItems().add(
							new FilterItemInfo("sellProject.id", allSpIdStr,
									CompareType.INNER));
				}
				// 根据楼栋查询对应的楼栋分录，获取定价单id集合
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}

			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			this.handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	public void actionExecute_actionPerformed(ActionEvent e) throws Exception {
		super.actionExecute_actionPerformed(e);

		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1) {
			MsgBox.showInfo("请选择行!");
			return;
		}
		IRow row = this.tblMain.getRow(rowIndex);
		Boolean isExecuted = (Boolean) row.getCell("isExecute").getValue();
		if (isExecuted.booleanValue()) {
			MsgBox.showInfo("定价单已经执行!");
			return;
		}
		
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		FDCClientUtils.checkBillInWorkflow(this, id);
		if (!FDCBillStateEnum.AUDITTED.equals(getBizState(id))) {
			FDCMsgBox.showWarning("定价单没有审核!");
			return;
		}
		if (MsgBox.showConfirm2New(this, "是否执行?") == MsgBox.YES) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", id));
			view.setFilter(filter);
			view.getSelector().add("*");
			view.getSelector().add("room.*");
			view.getSelector().add("room.sellState");

			RoomPriceAdjustEntryCollection entrys = RoomPriceAdjustEntryFactory.getRemoteInstance()
					.getRoomPriceAdjustEntryCollection(view);
			for (int i = 0; i < entrys.size(); i++) {
				RoomPriceAdjustEntryInfo entry = entrys.get(i);
				RoomInfo room = entry.getRoom();

				// 面积改变检测
				boolean isEdit = false;
				BigDecimal entryBuildingArea = entry.getNewBuildingArea();
				BigDecimal entryRoomArea = entry.getNewRoomArea();
				if (entryBuildingArea == null) {
					entryBuildingArea = FDCHelper.ZERO;
				}
				if (entryRoomArea == null) {
					entryRoomArea = FDCHelper.ZERO;
				}

				BigDecimal buildingArea = null;
				BigDecimal roomArea = null;
				// 根据销售方式取面积
				if (entry.getSellType().equals(SellTypeEnum.LocaleSell)) { // 现售
					buildingArea = room.getActualBuildingArea();
					roomArea = room.getActualRoomArea();
				} else if (entry.getSellType().equals(SellTypeEnum.PreSell)) { // 预售
					buildingArea = room.getBuildingArea();
					roomArea = room.getRoomArea();
				} else { // 规划
					buildingArea = room.getPlanBuildingArea();
					roomArea = room.getPlanRoomArea();
				}

				if (buildingArea == null) {
					buildingArea = FDCHelper.ZERO;
				}
				if (roomArea == null) {
					roomArea = FDCHelper.ZERO;
				}
				
				if(room.isIsChangeSellArea()){
					isEdit = true;
				}
				else{
					if (roomArea.compareTo(entryRoomArea) != 0) {
						isEdit = true;
					}
					if (buildingArea.compareTo(entryBuildingArea) != 0) {
						isEdit = true;
					}
				}

				if (isEdit) {
					MsgBox.showInfo("定价单中房间" + room.getNumber()
							+ " 定价后面积变化,将自动跳过!");
				}
			}
			if (RoomPriceManageFactory.getRemoteInstance().excute(id)) {
				MsgBox.showInfo("执行成功!");
				this.refresh(null);
				fillRoomPriceData();
			} else {
				MsgBox.showInfo("定价单没有审核!");
			}
		}
	}

	public void actionViewPriceHis_actionPerformed(ActionEvent e)
			throws Exception {
		if (0 == this.kDTabbedPane1.getSelectedIndex()) {
			String id = FDCClientHelper.getSelectedKeyValue(tblRoomPrice, "id");
			if (id == null || "".equals(id.toString())) {
				MsgBox.showInfo("请选择要查询的房间");
				SysUtil.abort();
			}

			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", id);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(RoomPriceHistoryListUI.class.getName(), uiContext,
							null, "VIEW");
			uiWindow.show();
		} else {
			MsgBox.showInfo("请选择要查询的房间");
			SysUtil.abort();
		}
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	protected String getEditUIName() {
		return RoomPriceAdjustEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomPriceManageFactory.getRemoteInstance();
	}

	
	private Set getSelectedRows(int [] selectRows){
			TreeSet set = new TreeSet();
		int select = 0;
		for (int i = 0; i < selectRows.length; i++) {
			select = selectRows[i];
			set.add(new Integer(select));
		}
		return set;
	}
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		// 当前为【房间价格标签】时，获取选中行，传递选中的房间id
		DefaultKingdeeTreeNode node = null;
		if (0 == this.kDTabbedPane1.getSelectedIndex()) {
			int [] selectRows = KDTableUtil.getSelectedRows(tblRoomPrice);
			Set rowIndexSet = getSelectedRows(selectRows);
			Set roomIdSet = null;
			if (rowIndexSet != null && !rowIndexSet.isEmpty()) {
				roomIdSet = new HashSet();
				Iterator rowIndexIt = rowIndexSet.iterator();
				while (rowIndexIt.hasNext()) {
					Integer rowIndex = (Integer) rowIndexIt.next();
					roomIdSet.add(tblRoomPrice.getRow(rowIndex.intValue())
							.getCell("id").getValue());
				}
			}
			uiContext.put("roomIdSet", roomIdSet);
			
			node = (DefaultKingdeeTreeNode) kdRoomTree.getLastSelectedPathComponent();
		}
		else{
			uiContext.put("roomIdSet", null);
			
			node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		}
		
		SellProjectInfo sellProject = null;
		if (node != null) {
			if (node.getUserObject() instanceof SellProjectInfo) {
				sellProject = (SellProjectInfo) node.getUserObject();
			} else if (node.getUserObject() instanceof SubareaInfo) {
				SubareaInfo subArea = (SubareaInfo) node.getUserObject();
				sellProject = subArea.getSellProject();
			} else if (node.getUserObject() instanceof BuildingInfo) {
				sellProject = ((BuildingInfo) node.getUserObject()).getSellProject();
			} else if(node.getUserObject() instanceof BuildingUnitInfo){
				sellProject = ((BuildingUnitInfo) node.getUserObject()).getBuilding().getSellProject();
			}
			if(sellProject != null && sellProject.getId()!=null){
				String sellProjectId = sellProject.getId().toString();
				try {
					sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProjectId));
				} catch (Exception ex) {
					
				}
			}
			uiContext.put("sellProject", sellProject);
		}
	}

	private void fillRoomPriceData() throws BOSException {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) kdRoomTree.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("displayName");
		view.getSelector().add("sellType");
		view.getSelector().add("calcType");
		view.getSelector().add("roomNo");
		view.getSelector().add("standardTotalAmount");
		view.getSelector().add("buildingArea");
		view.getSelector().add("planBuildingArea");
		view.getSelector().add("actualBuildingArea");
		view.getSelector().add("buildPrice");
		view.getSelector().add("roomArea");
		view.getSelector().add("planRoomArea");
		view.getSelector().add("actualRoomArea");
		view.getSelector().add("roomPrice");
		view.getSelector().add("sellState");
		view.getSelector().add("changeState");
		view.getSelector().add("planChangeState");
		view.getSelector().add("preChangeState");
		view.getSelector().add("actChangeState");
		view.getSelector().add("isChangeSellType");
		view.getSelector().add("id");
		view.getSelector().add("floor");
		view.getSelector().add("number");
		view.getSelector().add("building.name");
		view.getSelector().add("building.sellProject.name");
		view.getSelector().add("building.subarea.name");
		view.getSelector().add("buildUnit.name");
		view.getSelector().add("buildUnit.seq");

		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);

		if (node.getUserObject() instanceof BuildingUnitInfo) {
			BuildingUnitInfo buildUnit = (BuildingUnitInfo) node.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("building.id", buildingId));
			filter.getFilterItems().add(new FilterItemInfo("buildUnit.id", buildUnit.getId().toString()));
		} else if (node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("building.id", buildingId));
			view.getSorter().add(new SorterItemInfo("unit"));
		} else if (node.getUserObject() instanceof SubareaInfo) {
			SubareaInfo subarea = (SubareaInfo) node.getUserObject();
			String subareaId = subarea.getId().toString();
			filter.getFilterItems().add(new FilterItemInfo("building.subarea.id", subareaId));
			view.getSorter().add(new SorterItemInfo("building.id"));
			view.getSorter().add(new SorterItemInfo("unit"));
		} else if (node.getUserObject() instanceof SellProjectInfo) {
			String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper
					.getAllObjectIdMap(node, "SellProject").keySet());
			if (allSpIdStr.trim().length() == 0) {
				allSpIdStr = "'nullnull'";
			}
			filter.getFilterItems().add(new FilterItemInfo("building.sellProject.id",allSpIdStr,CompareType.INNER));
		} else {
			this.tblRoomPrice.removeRows();
			return;
		}

		view.getSorter().add(new SorterItemInfo("building.number"));
		view.getSorter().add(new SorterItemInfo("unit"));
		view.getSorter().add(new SorterItemInfo("floor"));
		view.getSorter().add(new SorterItemInfo("number"));

		this.tblRoomPrice.removeRows();

		RoomCollection rooms = RoomFactory.getRemoteInstance().getRoomCollection(view);
		for (int i = 0; i < rooms.size(); i++) {
			RoomInfo room = rooms.get(i);
			IRow row = this.tblRoomPrice.addRow();
			row.setUserObject(room);
			
			BigDecimal sumAmount = room.getStandardTotalAmount()==null ? new BigDecimal("0.0000") 
				: room.getStandardTotalAmount().divide(FDCHelper.ONE, 4, BigDecimal.ROUND_UP);
			BigDecimal buildArea = room.getBuildingArea()==null ? new BigDecimal("0.0000") 
				: room.getBuildingArea().divide(FDCHelper.ONE, 4, BigDecimal.ROUND_UP);
			BigDecimal buildPrice = room.getBuildPrice()==null ? new BigDecimal("0.0000") 
				: room.getBuildPrice().divide(FDCHelper.ONE, 4, BigDecimal.ROUND_UP);
			BigDecimal roomArea = room.getRoomArea()==null ? new BigDecimal("0.0000") 
				: room.getRoomArea().divide(FDCHelper.ONE, 4, BigDecimal.ROUND_UP);
			BigDecimal roomPrice = room.getRoomPrice()==null ? new BigDecimal("0.0000") 
				: room.getRoomPrice().divide(FDCHelper.ONE, 4, BigDecimal.ROUND_UP);
		
			row.getCell("id").setValue(room.getId().toString());
//			row.getCell("number").setValue(room.getNumber());
			row.getCell("building").setValue(room.getBuilding().getName());
			if(room.getBuildUnit()!=null){
				row.getCell("unit").setValue(room.getBuildUnit().getName());
			}
			row.getCell("roomNo").setValue(room.getDisplayName()); // 房号
			SellTypeEnum sellType = room.getSellType(); // 销售方式
			if (sellType != null) {
				row.getCell("sellType").setValue(sellType);
				//根据销售方式取房间面积
				if(sellType.equals(SellTypeEnum.PlanningSell)){  //预估
					buildArea = room.getPlanBuildingArea();
					roomArea = room.getPlanRoomArea();
				}
				else if(sellType.equals(SellTypeEnum.LocaleSell)){  //现售
					buildArea = room.getActualBuildingArea();
					roomArea = room.getActualRoomArea();
				}
				else{  //预售
					buildArea = room.getBuildingArea();
					roomArea = room.getRoomArea();
				}
				//保留位
				buildArea = buildArea==null ? new BigDecimal("0.0000") 
					: buildArea.divide(FDCHelper.ONE, 4, BigDecimal.ROUND_UP);
				roomArea = roomArea==null ? new BigDecimal("0.0000") 
					: roomArea.divide(FDCHelper.ONE, 4, BigDecimal.ROUND_UP);
			}
			
			row.getCell("sumAmount").setValue(sumAmount); // 标准总价
			row.getCell("buildArea").setValue(buildArea); // 建筑面积
			row.getCell("buildPrice").setValue(buildPrice); // 建筑单价
			row.getCell("roomArea").setValue(roomArea); // 套内面积
			row.getCell("roomPrice").setValue(roomPrice); // 套内单价

			RoomSellStateEnum sellState = room.getSellState(); // 销售状态，除了预定、认购、签约外的房间都是未售
			if (RoomSellStateEnum.PrePurchase.equals(sellState)
					|| RoomSellStateEnum.Purchase.equals(sellState)
					|| RoomSellStateEnum.Sign.equals(sellState)) {
				row.getCell("sellState").setValue("已售");
			} else {
				row.getCell("sellState").setValue("未售");
			}

			ChangeStateEnum changeState = room.getChangeState(); // 变更状态
			if (changeState != null) {
				ICell changeStateCell = row.getCell("changeState");
				changeStateCell.setValue(changeState);
				changeStateCell.getStyleAttributes().setBackground(Color.RED);
			}
			
			if(room.getStandardTotalAmount() != null  &&  room.getStandardTotalAmount().compareTo(FDCHelper.ZERO) != 0){
				if(room.isIsChangeSellArea()){
					ICell changeStateCell = row.getCell("areaState");
					changeStateCell.setValue("面积变更");
					changeStateCell.getStyleAttributes().setBackground(Color.RED);
				}else{
					if(room.getPlanChangeState() != null  && room.getPlanChangeState().equals(RoomPlanChangeStateEnum.CHANGE)
							|| room.getPreChangeState() != null && room.getPreChangeState().equals(RoomPreChangeStateEnum.CHANGE)
							|| room.getActChangeState() != null && room.getActChangeState().equals(RoomActChangeStateEnum.CHANGE)){
						ICell changeStateCell = row.getCell("areaState");
						changeStateCell.setValue("面积变更");
						changeStateCell.getStyleAttributes().setBackground(Color.RED);
					}
				}	
			}
			
			
			row.getCell("priceType").setValue(room.getCalcType());
			
		}
		// 设置合计
		fillRoomPriceTotal();
	}

	private void fillRoomPriceTotal() {
		if (tblRoomPrice.getRowCount() > 0) {
			BigDecimal sumAmount = new BigDecimal("0.0000");
			BigDecimal buildArea = new BigDecimal("0.0000");
			BigDecimal roomArea = new BigDecimal("0.0000");
			BigDecimal avgAmount = new BigDecimal("0.0000");
			BigDecimal avgBuild = new BigDecimal("0.0000");
			BigDecimal avgRoom = new BigDecimal("0.0000");

			BigDecimal sumAmountCell;
			BigDecimal buildAreaCell;
			BigDecimal roomAreaCell;

			IRow row;
			for (int i = 0; i < tblRoomPrice.getRowCount(); i++) {
				row = tblRoomPrice.getRow(i);
				// 标准总价
				sumAmountCell = row.getCell("sumAmount").getValue() == null ? new BigDecimal(
						"0.0000")
						: (BigDecimal) row.getCell("sumAmount").getValue();
				// 建筑面积
				buildAreaCell = row.getCell("buildArea").getValue() == null ? new BigDecimal(
						"0.0000")
						: (BigDecimal) row.getCell("buildArea").getValue();
				// 套内面积
				roomAreaCell = row.getCell("roomArea").getValue() == null ? new BigDecimal(
						"0.0000")
						: (BigDecimal) row.getCell("roomArea").getValue();

				sumAmount = sumAmount.add(sumAmountCell);
				buildArea = buildArea.add(buildAreaCell);
				roomArea = roomArea.add(roomAreaCell);
			}
			// 标准均价
			BigDecimal roomCount = new BigDecimal(this.tblRoomPrice.getRowCount());
			avgAmount = sumAmount.divide(roomCount, 4, BigDecimal.ROUND_HALF_UP);
			// 建筑均价=总标准价/建筑面积
			if(buildArea!=null && buildArea.compareTo(FDCHelper.ZERO)!=0){
				avgBuild = sumAmount.divide(buildArea, 4, BigDecimal.ROUND_HALF_UP);
			}
			// 套内均价=总标准价/套内面积
			if(roomArea!=null && roomArea.compareTo(FDCHelper.ZERO)!=0){
				avgRoom = sumAmount.divide(roomArea, 4, BigDecimal.ROUND_HALF_UP);
			}

			txtSumAmount.setValue(sumAmount);
			txtBuildArea.setValue(buildArea);
			txtRoomArea.setValue(roomArea);
			txtAvgAmount.setValue(avgAmount);
			txtAvgBuild.setValue(avgBuild);
			txtAvgRoom.setValue(avgRoom);
		} else {
			txtSumAmount.setValue(null);
			txtBuildArea.setValue(null);
			txtRoomArea.setValue(null);
			txtAvgAmount.setValue(null);
			txtAvgBuild.setValue(null);
			txtAvgRoom.setValue(null);
		}
	}

	private void fillPriceBillData() throws BOSException {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("head.id");
		view.getSelector().add("head.orgUnit.name");
		view.getSelector().add("head.number");
		view.getSelector().add("head.name");
		view.getSelector().add("head.type");
		view.getSelector().add("head.priceBillMode");
		view.getSelector().add("head.priceBillType");
		view.getSelector().add("head.state");
		view.getSelector().add("head.isExecuted");
		view.getSelector().add("head.description");
		view.getSelector().add("head.creator");
		view.getSelector().add("head.createTime");
		view.getSelector().add("head.auditor");
		view.getSelector().add("head.auditTime");

		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);

		if (node.getUserObject() instanceof BuildingUnitInfo) { // 楼栋单元，暂时屏蔽单元节点
			BuildingUnitInfo buildUnit = (BuildingUnitInfo) node
					.getUserObject();
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node
					.getParent()).getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(
					new FilterItemInfo("building.id", buildingId));
			filter.getFilterItems().add(
					new FilterItemInfo("building.units.id", buildUnit.getId()
							.toString()));
		} else if (node.getUserObject() instanceof BuildingInfo) { // 楼栋
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			String buildingId = building.getId().toString();
			filter.getFilterItems().add(
					new FilterItemInfo("building.id", buildingId));
			view.getSorter().add(new SorterItemInfo("building.units.id"));
		} else if (node.getUserObject() instanceof SubareaInfo) { // 分区
			SubareaInfo subarea = (SubareaInfo) node.getUserObject();
			String subareaId = subarea.getId().toString();
			filter.getFilterItems().add(
					new FilterItemInfo("building.subarea.id", subareaId));
			view.getSorter().add(new SorterItemInfo("building.id"));
			view.getSorter().add(new SorterItemInfo("building.units.id"));
		} else if (node.getUserObject() instanceof SellProjectInfo) { // 项目
			SellProjectInfo sellProject = (SellProjectInfo) node
					.getUserObject();
			String sellProjectId = sellProject.getId().toString();
			filter.getFilterItems()
					.add(
							new FilterItemInfo("building.sellProject.id",
									sellProjectId));
			view.getSorter().add(new SorterItemInfo("building.subarea.id"));
			view.getSorter().add(new SorterItemInfo("building.id"));
			view.getSorter().add(new SorterItemInfo("building.units.id"));
		} else {
			this.tblMain.removeRows();
			return;
		}
		view.getSorter().add(new SorterItemInfo("head.name"));

		this.tblMain.removeRows();

		RoomPriceBuildingEntryCollection roomPriceBuildings = RoomPriceBuildingEntryFactory
				.getRemoteInstance().getRoomPriceBuildingEntryCollection(view);

		if (roomPriceBuildings == null || roomPriceBuildings.isEmpty()) {
			return;
		}
		// 剔除重复的价目单(价目单关联了多个楼栋分录)
		Set billSet = new HashSet();
		for (int i = 0; i < roomPriceBuildings.size(); i++) {
			RoomPriceManageInfo billInfo = roomPriceBuildings.get(i).getHead();
			billSet.add(billInfo);
		}

		Iterator billIterator = billSet.iterator();
		while (billIterator.hasNext()) {
			RoomPriceManageInfo priceAdjustBill = (RoomPriceManageInfo) billIterator
					.next();

			IRow row = this.tblMain.addRow();
			row.setUserObject(priceAdjustBill);
			if (priceAdjustBill.getOrgUnit() != null) {
				row.getCell("orgName").setValue(
						priceAdjustBill.getOrgUnit().getName());
			}
			row.getCell("billNo").setValue(priceAdjustBill.getNumber());
			row.getCell("billName").setValue(priceAdjustBill.getName());

			if (priceAdjustBill.getPriceBillMode() != null) { // 定调价方式
				row.getCell("priceMode").setValue(
						priceAdjustBill.getPriceBillMode());
			} else {
				row.getCell("priceMode").setValue(null);
			}

			row.getCell("priceType").setValue(
					priceAdjustBill.getPriceBillType());

			row.getCell("state").setValue(priceAdjustBill.getState());

			boolean isExecuted = priceAdjustBill.isIsExecuted();
			if (isExecuted) {
				row.getCell("isExecute").setValue("是");
			} else {
				row.getCell("isExecute").setValue("否");
			}

			row.getCell("description").setValue(
					priceAdjustBill.getDescription());
			row.getCell("creator").setValue(priceAdjustBill.getCreator());
			row.getCell("createTime").setValue(priceAdjustBill.getCreateTime());
			row.getCell("auditor").setValue(priceAdjustBill.getAuditor());
			row.getCell("auditTime").setValue(priceAdjustBill.getAuditTime());
		}
	}
}