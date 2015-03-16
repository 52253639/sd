/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.event.ChangeEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
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
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum;
import com.kingdee.eas.fdc.sellhouse.BatchManageCollection;
import com.kingdee.eas.fdc.sellhouse.BatchManageFactory;
import com.kingdee.eas.fdc.sellhouse.BatchManageInfo;
import com.kingdee.eas.fdc.sellhouse.BatchManageSourceEnum;
import com.kingdee.eas.fdc.sellhouse.BatchRoomEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BatchRoomEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BatchRoomEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.FunctionSetting;
import com.kingdee.eas.fdc.sellhouse.HabitationRecordFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinCollection;
import com.kingdee.eas.fdc.sellhouse.RoomJoinFactory;
import com.kingdee.eas.fdc.sellhouse.RoomJoinInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.mm.control.client.TableCellComparator;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RoomJoinListUI extends AbstractRoomJoinListUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RoomJoinListUI.class);

	protected static final BOSUuid splitBillNullID = BOSUuid.create("null");

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	private boolean isAddNew = true;

	private int sortType = KDTSortManager.SORT_ASCEND;
	/**
	 * output class constructor
	 */
	public RoomJoinListUI() throws Exception {
		super();	
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		this.initF7RoomSelect();
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() instanceof OrgStructureInfo) {
			this.tblBatch.removeRows();
			this.tblMain.removeRows();
			
			this.actionEdit.setEnabled(false);
			this.actionQuery.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAddBatch.setEnabled(false);
			this.actionJoinAlert.setEnabled(false);
			this.actionBatchJoin.setEnabled(false);
			
			return;
		}
		if (FDCSysContext.getInstance().checkIsSHEOrg()){
			if(this.tabbedPanel.getSelectedIndex() == 1){
				this.actionEdit.setEnabled(true);
				this.actionQuery.setEnabled(true);
				this.actionRemove.setEnabled(true);
				this.actionBatchJoin.setEnabled(true);
			}
			this.actionAddBatch.setEnabled(true);
			this.actionJoinAlert.setEnabled(true);
		}
		
		execQuery();
		
		this.fillBatchTab();
	}
	
	/**
	 * 填充批次页签数据
	 * @throws BOSException 
	 */
	private void fillBatchTab() throws BOSException{
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (!(node.getUserObject() instanceof SellProjectInfo)) { 
			return ;
		}
		this.tblBatch.removeRows();
		
		SellProjectInfo sellProject = (SellProjectInfo)node.getUserObject();
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("sellProject.id");
		view.getSelector().add("sellProject.name");
		view.getSelector().add("transactor.name");
		view.getSelector().add("creator.name");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("source", BatchManageSourceEnum.JOIN_VALUE));
		view.setFilter(filter);
		
		BatchManageCollection batchManageCol = BatchManageFactory.getRemoteInstance().getBatchManageCollection(view);
		if(batchManageCol!=null && batchManageCol.size()>0){
			for(int i=0; i<batchManageCol.size(); i++){
				BatchManageInfo batchInfo = batchManageCol.get(i);
				IRow row = this.tblBatch.addRow();
				row.getCell("id").setValue(batchInfo.getId().toString());
				row.getCell("sellProject.id").setValue(batchInfo.getSellProject().getId().toString());
				row.getCell("sellProject").setValue(batchInfo.getSellProject().getName());
				row.getCell("number").setValue(batchInfo.getNumber());
				row.getCell("transactor").setValue(batchInfo.getTransactor().getName());
				row.getCell("creator").setValue(batchInfo.getCreator().getName());
				row.getCell("createTime").setValue(batchInfo.getCreateTime());
			}
		}
	}
	
	protected String getEditUIName() {
		return RoomJoinEditUI.class.getName();
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	public void onLoad() throws Exception {
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		// 不设为false,则会执行tHelper = new
		// TableListPreferencesHelper(this);导致表格设置不能保存样式
		actionQuery.setEnabled(false);
		super.onLoad();

		FDCClientHelper.setActionEnable(new ItemAction[] { actionAddNew},
				false);
		if (!FDCSysContext.getInstance().checkIsSHEOrg()) {
			FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[] {actionAddBatch,
					actionEdit, actionRemove, actionBatchJoin }, false);
		}
		
		this.btnAddBatch.setIcon(EASResource.getIcon("imgTbtn_gathering"));
		this.btnJoinNotice.setIcon(EASResource.getIcon("imgTbtn_closeinitialize"));
		
		this.btnPrint.setVisible(false);
		this.btnPrintPreview.setVisible(false);
		
		this.tblBatch.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblBatch.getStyleAttributes().setLocked(true);
		this.tblBatch.checkParsed();
		
		SellProjectInfo sellProject=(SellProjectInfo) this.getUIContext().get("sellProject");
		if(sellProject!=null){
			treeMain.setVisible(false);
			pnlMain.setDividerLocation(0);
			DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) this.treeMain.getModel().getRoot();
			DefaultKingdeeTreeNode node = SHEManageHelper.findNode(root, null, null ,sellProject);
			this.treeMain.setSelectionNode(node);
			RoomInfo room=(RoomInfo) this.getUIContext().get("room");
			if(room!=null){
				room=RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(room.getId()));
			}
			this.f7RoomSelect.setValue(room);
			search();
		}
		
		this.initF7Customer();
		this.initF7RoomSelect();
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo) {
		viewInfo = (EntityViewInfo) mainQuery.clone();
		// 合并查询条件
		try {
			//加上销售项目查询条件
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			FilterInfo filter = new FilterInfo();
			
			if (node.getUserObject() instanceof SellProjectInfo) { // 销售项目
				SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("room.building.sellProject.id", sellProject.getId().toString()));
			}
			else{
				filter.getFilterItems().add(new FilterItemInfo("id", "null"));
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
	
	protected void execQuery() {
		super.execQuery();
		//设置【款项是否收齐】一列
		for(int i=0; i<this.tblMain.getRowCount(); i++){
			if(this.tblMain.getRow(i).getCell("sign.id").getValue() != null){
				String signId = this.tblMain.getRow(i).getCell("sign.id").getValue().toString();
				boolean isAllRev = this.checkIsAllRev(signId);
				if(!isAllRev){
					this.tblMain.getRow(i).getCell("isAllRev").setValue("未收齐");
				}
			}
			
		}
	}
	
	protected void tabbedPanel_stateChanged(ChangeEvent e) throws Exception {
		super.tabbedPanel_stateChanged(e);
		
		if(this.tabbedPanel.getSelectedIndex() == 0){  //批次管理页签
			FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[] { actionView, actionEdit,
					actionRefresh, actionQuery, actionBatchJoin, actionRemove }, false);
		}
		else{  //产权管理页签
			FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[] { actionView, actionEdit,
					actionRefresh, actionQuery, actionBatchJoin, actionRemove }, true);
		}
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (!FDCSysContext.getInstance().checkIsSHEOrg() || node == null || node.getUserObject() instanceof OrgStructureInfo) {
			FDCClientHelper.setActionEnableAndNotSetVisible(new ItemAction[] {actionAddBatch, actionQuery,
					actionEdit, actionRemove, actionBatchJoin }, false);
		}
	}
	/**
	 * 检查款项是否收齐
	 * @param signId
	 * @return
	 */
	private boolean checkIsAllRev(String signId){
		boolean result = false;
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("moneyDefine.*");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("head.id", signId));
		view.setFilter(filter);
		
		try {
			SignPayListEntryCollection payEntryCol = SignPayListEntryFactory.getRemoteInstance()
				.getSignPayListEntryCollection(view);
			if(payEntryCol!=null && payEntryCol.size()>0){
				for(int i=0; i<payEntryCol.size(); i++){
					BigDecimal appAmount = payEntryCol.get(i).getAppAmount();
					BigDecimal actRevAmount = payEntryCol.get(i).getActRevAmount();
					if(appAmount == null){
						appAmount = FDCHelper.ZERO;
					}
					if(actRevAmount == null){
						actRevAmount = FDCHelper.ZERO;
					}
					if(actRevAmount.compareTo(appAmount) <= 0){  //实收小于应收
						result = false;
					}
					else {
						result = true;
					}
				}
			}
		} catch (BOSException e) {
			logger.error(e);
		}
		
		return result;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		BizEnumValueInfo joinState = (BizEnumValueInfo)this.tblMain.getRow(rowIndex).getCell("joinState").getValue();
		if(joinState!=null && AFMortgagedStateEnum.STOPTRANSACT.getName().equals(joinState.getName())){
			FDCMsgBox.showInfo("当前状态下的单据不能修改");
			SysUtil.abort();
		}
		super.actionEdit_actionPerformed(e);
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomJoinFactory.getRemoteInstance();
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		ItemAction act = getActionFromActionEvent(e);
		if (act.equals(actionAddNew) || act.equals(actionEdit)
				|| (act.equals(actionView)) || act.equals(actionBatchJoin)) {
			/*
			 * 把当前选中的工程项目和合同类型传给EditUI
			 
			if (getSelectedKeyValue() != null) {
				uiContext.put(UIContext.ID, getSelectedKeyValue());
				uiContext.put("roomId", getSelectedCostBillID());
			} else {
				uiContext.put(UIContext.ID, getSelectedCostBillID());
				uiContext.put("roomId", getSelectedCostBillID());
			}*/
			
			// 获取所选项的销售项目的Id，避免在编辑界面在取一次
			if (tblMain.getSelectManager().getActiveRowIndex() >= 0) {
				IRow tmpRow = tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex());
				uiContext.put("sellProject.id", String.valueOf(tmpRow.getCell("sellProject.id").getValue()));
			}
		}
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		isAddNew = false;
		if (this.tabbedPanel.getSelectedIndex() == 0) {
			IRow selectRow = tblBatch.getRow(tblBatch.getSelectManager().getActiveRowIndex());

			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			if (node == null || node.getUserObject() instanceof OrgStructureInfo) {
				FDCMsgBox.showInfo("请选择项目");
				SysUtil.abort();
			}
			String batchId = String.valueOf(selectRow.getCell("id").getValue());
			UIContext uiContext = new UIContext(this);
			uiContext.put("sellProject", (SellProjectInfo)node.getUserObject());
			uiContext.put("source", "roomJoin");
			uiContext.put(UIContext.ID, batchId);

			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(BatchRoomManageUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		} else {
			super.actionView_actionPerformed(e);
		}
	}

	protected void checkBeforeRemove() throws EASBizException, BOSException,
			Exception {
		// return super.getSelectedKeyValue();
		// KDTSelectBlock selectBlock = tblMain.getSelectManager().get();
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);

		// if (selectBlock != null) {
		for (int i = 0, size = selectRows.length; i < size; i++) {
			// int rowIndex = selectBlock.getTop();
			int rowIndex = selectRows[i];
			IRow row = tblMain.getRow(rowIndex);
			if (row == null) {
				MsgBox.showInfo(this, "没有选择行！");
				SysUtil.abort();
			}
			Object valueCom = row.getCell("id").getValue();
			Object valueRoom = row.getCell("room.id").getValue();
			if (valueCom == null) {
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("room.id", valueRoom.toString()));
				if (!getBizInterface().exists(filter)) {
					MsgBox.showInfo(this, "存在未办理入伙的记录,不能进行此操作！");
					SysUtil.abort();
				}
			}
			
			if(valueRoom != null){
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("room.id", valueRoom.toString()));
				filter.getFilterItems().add(new FilterItemInfo("joinVoucher.id", null, CompareType.NOTEQUALS));
				if (HabitationRecordFactory.getRemoteInstance().exists(filter)) {
					MsgBox.showInfo(this, "存在已经进行了物业登记的纪录,不能进行此操作！");
					SysUtil.abort();
				}
			}
		}
	}

	/**
	 * 需要考虑是否删除批次表及房间分录中的关联记录
	 * 取消入伙.原来的删除入伙功能.如果关联房源的入住分录已经进行了物业登记,那么不允许取消入伙.
	 * */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		isAddNew = false;
		checkBeforeRemove();
		ArrayList ids = getSelectedIdValues();
		if (ids != null && ids.size() > 0) {
			if (FDCMsgBox.isYes(MsgBox.showConfirm2(this, "是否取消入伙！"))) {
				for (int i = 0; i < ids.size(); i++) {
					//获取入伙单据对象
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add(new SelectorItemInfo("*"));
					selector.add(new SelectorItemInfo("room.id"));
					selector.add(new SelectorItemInfo("promiseDate"));
					selector.add(new SelectorItemInfo("actualFinishDate"));
					selector.add(new SelectorItemInfo("batchManage.id"));
					
					RoomJoinInfo joinInfo = RoomJoinFactory.getRemoteInstance()
						.getRoomJoinInfo(new ObjectUuidPK(ids.get(i).toString()), selector);
					
					//更新入伙单据状态
					joinInfo.setJoinState(AFMortgagedStateEnum.STOPTRANSACT);
					RoomJoinFactory.getRemoteInstance().update(new ObjectUuidPK(joinInfo.getId().toString()), joinInfo);
					
					//更新房间入伙状态
					if (joinInfo.getRoom() != null) {
						joinInfo.getRoom().setRoomJoinState(RoomJoinStateEnum.NOTJOIN);
						SelectorItemCollection roomSelector = new SelectorItemCollection();
						selector.add("roomJoinState");
						RoomFactory.getRemoteInstance().updatePartial(joinInfo.getRoom(), roomSelector);
					}
					
					//将批次对应的房间分录置为无效
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("batchManage.source", BatchManageSourceEnum.JOIN_VALUE));
					filter.getFilterItems().add(new FilterItemInfo("room.id", joinInfo.getRoom().getId()));
					filter.getFilterItems().add(new FilterItemInfo("batchManage.id", joinInfo.getBatchManage().getId()));
					filter.getFilterItems().add(new FilterItemInfo("isValid", Boolean.TRUE));
					
					view.setFilter(filter);
					BatchRoomEntryCollection batchRoomCol = BatchRoomEntryFactory.getRemoteInstance()
						.getBatchRoomEntryCollection(view);
					
					if(batchRoomCol!=null && !batchRoomCol.isEmpty()){
						BatchRoomEntryInfo batchRoomEntry = batchRoomCol.get(0);
						batchRoomEntry.setIsValid(false);
						
						SelectorItemCollection updateSelector = new SelectorItemCollection();
						updateSelector.add("isValid");
						BatchRoomEntryFactory.getRemoteInstance().updatePartial(batchRoomEntry, updateSelector);
					}
					
					//更新业务总览中的对应的业务
					this.deleteTransactionBiz(joinInfo);
				}
				FDCClientUtils.showOprtOK(this);
				refreshList();
			}
		}
	}
	
	/**
	 * 删除业务总览中对应的服务
	 * @param roomLoan
	 */
	private void deleteTransactionBiz(RoomJoinInfo joinInfo){
		try {
			SHEManageHelper.updateTransactionOverView(null, joinInfo.getRoom(), SHEManageHelper.JOIN,
					joinInfo.getPromiseFinishDate(), joinInfo.getActualFinishDate(), true);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public static String getRes(String resName) {
		return EASResource.getString(
				"com.kingdee.eas.fdc.sellhouse.SellHouseResource", resName);
	}

	public void actionAddBatch_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddBatch_actionPerformed(e);
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() instanceof OrgStructureInfo) {
			FDCMsgBox.showInfo("请选择项目");
			SysUtil.abort();
		}
		
		UIContext bookContext = new UIContext(this);
		bookContext.put("sellProject", (SellProjectInfo)node.getUserObject());
		bookContext.put("source", "roomJoin");

		IUIWindow window = UIFactory.createUIFactory(UIFactoryName.MODEL).create(BatchRoomManageUI.class.getName(), 
				bookContext, null, OprtState.ADDNEW);
		window.show();
		
		refresh(null);
	}
	
	public void actionJoinAlert_actionPerformed(ActionEvent e) throws Exception {
		super.actionJoinAlert_actionPerformed(e);
		
		UIContext uiContext = new UIContext(this);
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(RoomJoinNoticeUI.class.getName(), uiContext,
						null, OprtState.ADDNEW);
		uiWindow.show();
	}

	public void actionBatchJoin_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		
		//检查选择的记录是否可以批量修改
		checkSelect();
		
		//上面已校验过是否有选中行,所以selectedCount肯定>0
		isAddNew = true;
		
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		List idList = new ArrayList();
		for(int i=0; i<selectRows.length; i++){
			IRow row = this.tblMain.getRow(selectRows[i]);
			idList.add(row.getCell("id").getValue().toString());
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put("roomJoinIdList", idList);
		uiContext.put("sellProjectId", this.tblMain.getRow(selectRows[0]).getCell("sellProject.id").getValue());
		uiContext.put("batchId", this.tblMain.getRow(selectRows[0]).getCell("batchManage.id").getValue());
		uiContext.put("schemeId", this.tblMain.getRow(selectRows[0]).getCell("joinDoScheme.id").getValue());
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
			.create(RoomJoinBatchEditUI.class.getName(), uiContext, null, "ADDNEW");
		uiWindow.show();
		refresh(e);
	}

	protected void checkSelect() throws EASBizException, BOSException,
			Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
//		 付清全款控制
		for (int i = 0, size = selectRows.length; i < size; i++) {
			int rowIndex = selectRows[i];
			IRow row = tblMain.getRow(rowIndex);
			if (row == null) {
				MsgBox.showInfo(this, "没有选择行！");
				SysUtil.abort();
			}
			//检查是否收齐
//			verifyPayAll(row);
		}
		
		IRow firstRow = this.tblMain.getRow(selectRows[0]);
		String schemeId = String.valueOf(firstRow.getCell("joinDoScheme.id").getValue());
		String batchId = String.valueOf(firstRow.getCell("batchManage.id").getValue());
		//先检查选择的第一行入伙状态
		BizEnumValueInfo joinState = (BizEnumValueInfo)firstRow.getCell("joinState").getValue();
		if(joinState!=null && AFMortgagedStateEnum.STOPTRANSACT.getName().equals(joinState.getName())){
			FDCMsgBox.showInfo("手工终止的单据不能修改");
			SysUtil.abort();
		}
		//遍历检查其他行
		for (int i = 1; i < selectRows.length; i++) {
			int rowIndex = selectRows[i];
			IRow row = tblMain.getRow(rowIndex);
			if (row == null) {
				MsgBox.showInfo(this, "没有选择行！");
				SysUtil.abort();
			}
			BizEnumValueInfo rowJoinState = (BizEnumValueInfo)row.getCell("joinState").getValue();
			if(rowJoinState!=null && AFMortgagedStateEnum.STOPTRANSACT.getName().equals(rowJoinState.getName())){
				FDCMsgBox.showInfo("手工终止的单据不能修改");
				SysUtil.abort();
			}
			
			String tmpSchemeId = String.valueOf(row.getCell("joinDoScheme.id").getValue());
			if(batchId!=null){
				if(row.getCell("batchManage.id").getValue() == null 
					|| !batchId.equals(row.getCell("batchManage.id").getValue().toString())){
					MsgBox.showWarning("所选项的批次不一致，不能执行批量产权办理！");
					abort();
				}
			}
			
			if (!tmpSchemeId.equals(schemeId)) {
				MsgBox.showWarning("所选项的产权方案不一致，不能执行批量产权办理！");
				abort();
			}
		}
	}

	protected String getKeyFieldName() {
		if (isAddNew)
			return super.getKeyFieldName();
		else
			return "id";
	}

	protected String getSelectedKeyValue() {
		String keyValue;
		keyValue = super.getSelectedKeyValue();

		if (keyValue == null || keyValue.equals(splitBillNullID.toString())) {
			String costBillID = getSelectedCostBillID();
			if (costBillID == null) {
				return null;
			}
		}

		return keyValue;
	}

	protected String getSelectedCostBillID() {
		int selectIndex = -1;

		// KDTSelectBlock selectBlock = tblMain.getSelectManager().get();
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);

		// if (selectBlock != null) {
		if (selectRows.length > 0) {
			// int rowIndex = selectBlock.getTop();
			int rowIndex = selectRows[0];
			IRow row = tblMain.getRow(rowIndex);
			if (row == null) {
				return null;
			}

			selectIndex = rowIndex;
			// ICell cell = row.getCell(getKeyFieldName());
			ICell cell = row.getCell("id");

			if (cell == null) {
				MsgBox.showError(EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Error_KeyField_Fail"));
				SysUtil.abort();
			}

			Object keyValue = cell.getValue();

			if (keyValue != null) {
				return keyValue.toString();
			}
		}

		return null;
	}

	protected void refresh(ActionEvent e) throws Exception {
		treeMain_valueChanged(null);
	}

	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);
		if (e.getSource() == btnEdit || e.getSource() == menuItemEdit
				|| e.getSource() == btnBatchJoin
				|| e.getSource() == menuItemBatchJoin) {
			try {
				refresh(e);
			} catch (Exception e1) {
				logger.error(e1);
			}
		}
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
	
	private RoomDisplaySetting setting= new RoomDisplaySetting();
	
	protected String getLongNumberFieldName() {
		return "number";
	}
	public void verifyPayAll(IRow row) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("head.id", row.getCell("sign.id").getValue()));
		view.setFilter(filter);
		view.getSorter().add(new SorterItemInfo("seq"));
		view.getSelector().add("id");
		view.getSelector().add("appAmount");
		view.getSelector().add("actRevAmount");
		view.getSelector().add("moneyDefine.moneyType");
		
		SignPayListEntryCollection listEntrys = SignPayListEntryFactory.getRemoteInstance().getSignPayListEntryCollection(view);

		if (listEntrys == null || listEntrys.size() == 0) {
			MsgBox.showWarning("房间(" + row.getCell("number").getValue()
					+ ")没有付清全款!");
			abort();
		}
		
		boolean isSignGathering = isSignGathering();
		//已启用签约收款控制,非按揭款或公积金必须收齐
		if (isSignGathering) {
			for(int i=0; i<listEntrys.size(); i++){
				SignPayListEntryInfo pay = listEntrys.get(i);
				MoneyDefineInfo moneyDefine = pay.getMoneyDefine();
				if(moneyDefine == null) continue;
				if(!MoneyTypeEnum.AccFundAmount.equals(moneyDefine.getMoneyType()) && !MoneyTypeEnum.LoanAmount.equals(moneyDefine.getMoneyType())){
					BigDecimal apAmount = pay.getAppAmount();
					BigDecimal actAmount = pay.getActRevAmount();
					if (apAmount == null) {
						apAmount = FDCHelper.ZERO;
					}
					if (actAmount == null) {
						actAmount = FDCHelper.ZERO;
					}
					if (apAmount.compareTo(actAmount) != 0) {
						MsgBox.showWarning("已启用签约收款控制,房间(" + row.getCell("room.number").getValue() + ")非按揭款或公积金必须收齐!");
						abort();
					}
				}
			}
		}
	}

	/**
	 * 根据配置返回该项目是否设置了 签约收款控制
	 * */
	private boolean isSignGathering() {
		boolean isSignGathering = true;//默认为设置了
		HashMap functionSetMap = (HashMap) setting.getFunctionSetMap();
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null) {
			BuildingInfo building = null;
			if (node.getUserObject() instanceof Integer) {   //已作废
				building = (BuildingInfo) ((DefaultKingdeeTreeNode)node.getParent()).getUserObject();
			}else if (node.getUserObject() instanceof BuildingUnitInfo) {
				building = (BuildingInfo) ((DefaultKingdeeTreeNode)node.getParent()).getUserObject();
			}else if(node.getUserObject() instanceof BuildingInfo){
				building = (BuildingInfo) node.getUserObject();
			}else{
				logger.error("impossiable.");
				return isSignGathering;
			}
			
			SellProjectInfo sellProject = building.getSellProject();
			String sellProjectId = sellProject.getId().toString();
			
			FunctionSetting funcSet = (FunctionSetting)functionSetMap.get(sellProjectId);
			if (funcSet == null) {
				logger.warn("没有该项目的参数设置...");
			} else {
				isSignGathering = funcSet.getIsSignGathering().booleanValue();;
			}
		}
		return isSignGathering;
	}
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception
	{
		int temp[] = KDTableUtil.getSelectedRows(this.tblMain);
		if(temp == null || temp.length < 1)
		{
			MsgBox.showWarning("请选择需要打印的数据！");
			this.abort();
		}
		Set set = new HashSet();
		for(int i = 0; i < temp.length; i ++)
		{
			IRow row = this.tblMain.getRow(temp[i]);
			String id = (String)row.getCell("id").getValue();
			set.add(id);
		}
		if(set.size() < 1)
			return;
		
		
		RoomJoinPrintDataProvider data = new RoomJoinPrintDataProvider(set,new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.JoinSettleTDQuery"));
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print("/bim/fdc/sellhouse/roomjoin", data,javax.swing.SwingUtilities.getWindowAncestor(this));
	}
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
	{
		int temp[] = KDTableUtil.getSelectedRows(this.tblMain);
		if(temp == null || temp.length < 1)
		{
			MsgBox.showWarning("请选择需要打印预览的数据！");
			this.abort();
		}
		Set set = new HashSet();
		for(int i = 0; i < temp.length; i ++)
		{
			IRow row = this.tblMain.getRow(temp[i]);
			String id = (String)row.getCell("id").getValue();
			set.add(id);
		}
		if(set.size() < 1)
			return;
		
		RoomJoinPrintDataProvider data = new RoomJoinPrintDataProvider(set,new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.NewRoomJoinQuery"));
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview("/bim/fdc/sellhouse/roomjoin", data,javax.swing.SwingUtilities.getWindowAncestor(this));
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
		
        if (isOrderForClickTableHead() && e.getType() == KDTStyleConstants.HEAD_ROW
                && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1
                && this.tblMain.getColumnKey(e.getColIndex()).equals("isAllRev")) {
            if (sortType == KDTSortManager.SORT_ASCEND) {
            	sortType = KDTSortManager.SORT_DESCEND;
            }
            else {
            	sortType = KDTSortManager.SORT_ASCEND;
            }
        	
            List rows = this.tblMain.getBody().getRows();
            Collections.sort(rows, new TableCellComparator(e.getColIndex(), sortType));
        	this.tblMain.setRefresh(true);
        }
        
        IRow row = KDTableUtil.getSelectedRow(this.tblMain);
        if(row == null){
        	return;
        }
        try{
        	RoomJoinInfo info = RoomJoinFactory.getRemoteInstance().getRoomJoinInfo("select id,joinState where id='"+row.getCell("id").getValue().toString()+"'");
        	if(info!=null && info.getJoinState().equals(AFMortgagedStateEnum.TRANSACTED)){
        		this.actionJoinAlert.setEnabled(false);
        	}else{
        		this.actionJoinAlert.setEnabled(true);
        	}
        }catch(BOSException ex){
        	logger.error(ex.getMessage());
        }catch(Exception ex){
        	logger.error(ex.getMessage());
        }
        
	}
	
	protected void tblBatch_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			if (e.getType() == 0) {
				return;
			}
			int rowIndex = tblBatch.getSelectManager().getActiveRowIndex();
			if (rowIndex < 0)
				return;
			IRow selectRow = tblBatch.getRow(rowIndex);

			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			if (node == null || node.getUserObject() instanceof OrgStructureInfo) {
				FDCMsgBox.showInfo("请选择项目");
				SysUtil.abort();
			}
			String batchId = String.valueOf(selectRow.getCell("id").getValue());
			UIContext uiContext = new UIContext(this);
			uiContext.put("sellProject", (SellProjectInfo)node.getUserObject());
			uiContext.put("source", "roomJoin");
			uiContext.put(UIContext.ID, batchId);

			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(BatchRoomManageUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}
	private void search() throws BOSException{
		HashMap batchManageMap = new HashMap();
		
		RoomInfo qryRoom = null;
		SHECustomerInfo qryCustomer = null;
		if(this.f7RoomSelect.getValue() != null){
			qryRoom = (RoomInfo)this.f7RoomSelect.getValue();
		}
		if(this.f7Customer.getValue() != null){
			qryCustomer = (SHECustomerInfo)this.f7Customer.getValue();
		}
		
		if(qryRoom==null && qryCustomer==null){
			FDCMsgBox.showInfo("请输入查询条件");
			SysUtil.abort();
		}
		
		//根据条件获取产权单据中的批次id
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("batchManage.*");
		view.getSelector().add("batchManage.sellProject.id");
		view.getSelector().add("batchManage.sellProject.name");
		view.getSelector().add("batchManage.transactor.name");
		view.getSelector().add("batchManage.creator.name");
		
		FilterInfo roomJoinFilter = new FilterInfo();
		roomJoinFilter.getFilterItems().add(new FilterItemInfo("batchManage.source", BatchManageSourceEnum.JOIN_VALUE));
		
		if(qryRoom != null){  //添加房间限制条件
			roomJoinFilter.getFilterItems().add(new FilterItemInfo("room.id", qryRoom.getId().toString()));
		}
		if(qryCustomer != null){  //根据客户查询签约单的客户分录，获取签约单id，添加签约单限制条件
			EntityViewInfo signView = new EntityViewInfo();
			signView.getSelector().add("head.id");
			
			FilterInfo signFilter = new FilterInfo();
			signFilter.getFilterItems().add(new FilterItemInfo("customer.id", qryCustomer.getId().toString()));
			
			SignCustomerEntryCollection signCustomerCol = SignCustomerEntryFactory.getRemoteInstance()
				.getSignCustomerEntryCollection(signView);
			if(signCustomerCol!=null && signCustomerCol.size()>0){
				HashSet signIdSet = new HashSet();
				for(int i=0; i<signCustomerCol.size(); i++){
					signIdSet.add(signCustomerCol.get(i).getHead().getId().toString());
				}
				
				roomJoinFilter.getFilterItems().add(new FilterItemInfo("sign.id", signIdSet, CompareType.INCLUDE));
			}
		}
		
		view.setFilter(roomJoinFilter);
		
		//从入伙单据中得到批次map
		RoomJoinCollection roomJoinCol = RoomJoinFactory.getRemoteInstance().getRoomJoinCollection(view);
		if(roomJoinCol!=null && roomJoinCol.size()>0){
			for(int i=0; i<roomJoinCol.size(); i++){
				BatchManageInfo batchManage = roomJoinCol.get(i).getBatchManage();
				if(batchManage != null){
					batchManageMap.put(batchManage.getId().toString(), batchManage);
				}
			}
		}
		
		//填充批次表
		this.tblBatch.removeRows();
		if(batchManageMap.size() > 0){
			Iterator batchIterator = batchManageMap.values().iterator();
			while(batchIterator.hasNext()){
				BatchManageInfo batchInfo = (BatchManageInfo)batchIterator.next();
				IRow row = this.tblBatch.addRow();
				row.getCell("id").setValue(batchInfo.getId().toString());
				row.getCell("sellProject.id").setValue(batchInfo.getSellProject().getId().toString());
				row.getCell("sellProject").setValue(batchInfo.getSellProject().getName());
				row.getCell("number").setValue(batchInfo.getNumber());
				row.getCell("transactor").setValue(batchInfo.getTransactor().getName());
				row.getCell("creator").setValue(batchInfo.getCreator().getName());
				row.getCell("createTime").setValue(batchInfo.getCreateTime());
			}
		}
	}
	protected void btnSearch_actionPerformed(ActionEvent e) throws Exception {
		search();
	}
	
	private void initF7RoomSelect(){
		this.f7RoomSelect.setEditable(false);
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || !(node.getUserObject() instanceof SellProjectInfo)) {  //非项目节点
			filter.getFilterItems().add(new FilterItemInfo("id", "null"));
		}
		else{
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
			filter.getFilterItems().add(new FilterItemInfo("building.sellProject.id", sellProject.getId().toString()));
		}
		
		filter.getFilterItems().add(new FilterItemInfo("building.sellProject.isForSHE", "true"));  // 售楼属性
		filter.getFilterItems().add(new FilterItemInfo("sellState", RoomSellStateEnum.Sign.getValue()));  //签约状态
		view.setFilter(filter);

		this.f7RoomSelect.setEntityViewInfo(view);
	}
	
	private void initF7Customer() throws EASBizException, BOSException{
		this.f7Customer.setEditable(false);
	}
}