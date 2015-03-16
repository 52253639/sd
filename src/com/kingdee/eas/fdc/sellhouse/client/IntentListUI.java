/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.resource.BizEnumValueInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDataStyle;
import com.kingdee.bos.ctrl.kdf.table.KDTGroupManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryCollection;
import com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.BusTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomSet;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageCollection;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.ProjectSet;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillCollection;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillFactory;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEParamConstant;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageCollection;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewCollection;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewFactory;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;

/**
 * output class name
 */
public class IntentListUI extends AbstractIntentListUI
{
    private static final Logger logger = CoreUIObject.getLogger(IntentListUI.class);
    
    CoreUIObject detailUI = null;
    
    Map actionMap = new HashMap();
    
    /**
     * output class constructor
     */
    public IntentListUI() throws Exception
    {
        super();
    }

	public void loadFields()
	{
		super.loadFields();
	}

	private void changButton(int comIndex) throws EASBizException, BOSException{
		if(comIndex == 0){
			this.actionQuery.setEnabled(false);
		} else{
			this.actionQuery.setEnabled(true);
		}
	}
	protected String getEditUIModal()
	{
		return UIFactoryName.NEWTAB;
	}
	
	public void actionCancelKeepDown_actionPerformed(ActionEvent e) throws Exception
	{
		String keepDownBillId = "";
		if(detailUI.getUIContext().get("roomId") == null){
			 MsgBox.showInfo("请选择房间！");
				this.abort();
		}
		RoomSellStateEnum sellState = (RoomSellStateEnum)detailUI.getUIContext().get("sellState");
		String roomId = detailUI.getUIContext().get("roomId").toString();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		view.getSelector().add("state");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("room.id", roomId));
		filter.getFilterItems().add(new FilterItemInfo("cancelDate", null));
		filter.getFilterItems().add(new FilterItemInfo("cacelStaff", null));
		RoomKeepDownBillCollection col = (RoomKeepDownBillCollection)RoomKeepDownBillFactory.getRemoteInstance().getRoomKeepDownBillCollection(view);
		if(col != null && col.size()>0){
			RoomKeepDownBillInfo info = (RoomKeepDownBillInfo)col.get(0);
			if(FDCBillStateEnum.AUDITTING.equals(info.getState())){
				FDCMsgBox.showWarning("只有提交和审核状态下的单据可取消预留！");
				SysUtil.abort();
			}
			keepDownBillId = info.getId().toString();
		}
		RoomKeepDownBillFactory.getRemoteInstance().cancelKeepDown(roomId, keepDownBillId);
		FDCClientUtils.showOprtOK(this);
		((SellControlUI)detailUI).refresh(null);
	}
	 
	public void actionBuyingRoomPlan_actionPerformed(ActionEvent e) throws Exception
	 {
		 if(detailUI.getUIContext().get("roomId") == null){
			 MsgBox.showInfo("请选择房间！");
				this.abort();
		 }
		 RoomSellStateEnum sellState = (RoomSellStateEnum)detailUI.getUIContext().get("sellState");
		 String roomId = detailUI.getUIContext().get("roomId").toString();
		 BigDecimal totalAmount = (BigDecimal)detailUI.getUIContext().get("totalAmount");
//		 if(roomId ==null || "".equals(roomId)){
//			 MsgBox.showInfo("请选择房间！");
//				this.abort();
//		 }
		 UIContext uiContext = new UIContext(ui);
		 uiContext.put("ID", roomId);
		 uiContext.put("totalAmount", totalAmount);
		 uiContext.put("pricingType", detailUI.getUIContext().get("pricingType"));
		 // 创建UI对象并显示
		 IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(BuyingRoomPlanUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		 uiWindow.show();
	 }
	
	public void actionPayPlan_actionPerformed(ActionEvent e) throws Exception
	{
		checkSelected();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (!(node.getUserObject() instanceof SellProjectInfo))
		{
			return;
		}
		SellProjectInfo project = (SellProjectInfo) node.getUserObject();
		UIContext uiContext = new UIContext(ui);
		String projectID = project.getId().toString();
		uiContext.put("sellProjectID", projectID);
		String roomId = this.getSelectedKeyValue();
		if(roomId!=null)
			SimulantSellUI.showUI(this, roomId, projectID);
	}

	protected boolean initDefaultFilter()
	{
		return false;
	}
	
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    public void onLoad() throws Exception
	{
    	this.menuItemCancelKeepDown.setIcon(EASResource.getIcon("imgTbtn_cancelcase"));
    	this.btnCancelKeepDown.setIcon(EASResource.getIcon("imgTbtn_cancelcase"));
    	
    	this.menuItemCustomer.setIcon(EASResource.getIcon("imgTbtn_new"));
    	this.btnCustomer.setIcon(EASResource.getIcon("imgTbtn_new"));
    	this.menuItemReceiveBill.setIcon(EASResource.getIcon("imgTbtn_monadismpostil"));
    	this.btnReceiveBill.setIcon(EASResource.getIcon("imgTbtn_monadismpostil"));
    	
    	this.menuSpecialBiz.setIcon(EASResource.getIcon("imgTbtn_disassemble"));
    	this.menuItemCustomerChangeName.setIcon(EASResource.getIcon("imgTbtn_recieversetting"));
    	this.menuItemChangeRoom.setIcon(EASResource.getIcon("imgTbtn_assetchange"));
    	this.menuItemQuitRoom.setIcon(EASResource.getIcon("imgTbtn_quit"));
    	this.menuItemPriceChange.setIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
    	this.btnSpecialBiz.setIcon(EASResource.getIcon("imgTbtn_disassemble"));
    	
    	this.actionReceiveBill.setEnabled(true);
    	this.actionCustomer.setEnabled(true);
    	
    	KDMenuItem menuItem1 = new KDMenuItem();
		menuItem1.setAction(this.actionCustomerChangeName);
		menuItem1.setText("更名");
		menuItem1.setIcon(EASResource.getIcon("imgTbtn_recieversetting"));
		this.btnSpecialBiz.addAssistMenuItem(menuItem1);

		KDMenuItem menuItem2 = new KDMenuItem();
		menuItem2.setAction(this.actionChangeRoom);
		menuItem2.setText("换房");
		menuItem2.setIcon(EASResource.getIcon("imgTbtn_assetchange"));
		this.btnSpecialBiz.addAssistMenuItem(menuItem2);

		KDMenuItem menuItem3 = new KDMenuItem();
		menuItem3.setAction(this.actionQuitRoom);
		menuItem3.setText("退房");
		menuItem3.setIcon(EASResource.getIcon("imgTbtn_quit"));
		this.btnSpecialBiz.addAssistMenuItem(menuItem3);
		
		KDMenuItem menuItem4 = new KDMenuItem();
		menuItem4.setAction(this.actionPriceChange);
		menuItem4.setText("价格变更");
		menuItem4.setIcon(EASResource.getIcon("imgTbtn_assistantaccount"));
		this.btnSpecialBiz.addAssistMenuItem(menuItem4);
    	
		super.onLoad();
		this.menuEdit.setVisible(false);
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.menuItemCancelCancel.setVisible(false);
		this.menuItemCancel.setVisible(false);
		this.menuItemPayplan.setEnabled(true);
		this.btnPayPlan.setEnabled(true);
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionView.setVisible(false);
		this.actionLocate.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionPayPlan.setVisible(false);
		this.actionBuyingRoomPlan.setEnabled(true);
		this.actionKeepDown.setEnabled(true);
		this.actionSinPurchase.setEnabled(true);
		this.actionPrePurchase.setEnabled(true);
		this.actionPurchase.setEnabled(true);
		this.acionSignContract.setEnabled(true);
//		this.btnSignContract.setEnabled(true);
//		this.menuItemSignContract.setEnabled(true);
		this.actionBuyingRoomPlan.setVisible(true);
		initCtrl();
		initAction();
		initRoomQuery();
		this.actionQuery.setEnabled(false);
		this.tabNew.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				int comIndex = tabNew.getSelectedIndex();
				try {
					changButton(comIndex);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		this.actionPriceChange.setVisible(false);
		
		this.btnSpecialAgio.setIcon(EASResource.getIcon("imgTbtn_new"));
		pnlMain.setDividerLocation(200);
		
		this.actionChangeRoom.setVisible(false);
		this.actionSpecialAgio.setVisible(false);
	}

    private void initAction(){
    	actionMap.put("actionBuyingRoomPlan",actionBuyingRoomPlan);
    	actionMap.put("actionKeepDown",actionKeepDown);
    	actionMap.put("actionCancelKeepDown",actionCancelKeepDown);
    	actionMap.put("actionSinPurchase",actionSinPurchase);
    	actionMap.put("actionPrePurchase",actionPrePurchase);
    	actionMap.put("actionPurchase",actionPurchase);
    	actionMap.put("acionSignContract",acionSignContract);
    	actionMap.put("actionReceiveBill",actionReceiveBill);
    	actionMap.put("actionCustomer",actionCustomer);
    	actionMap.put("actionCustomerChangeName",actionCustomerChangeName);
    	actionMap.put("actionChangeRoom",actionChangeRoom);
    	actionMap.put("actionQuitRoom",actionQuitRoom);
    	actionMap.put("actionPriceChange",actionPriceChange);
    	actionMap.put("btnSpecialBiz",btnSpecialBiz);
    	actionMap.put("menuSpecialBiz",menuSpecialBiz);
    	
    }
	private void initRoomQuery() throws Exception{
		if (detailUI == null) {
			UIContext uiContext = new UIContext(ui);
//			uiContext.put(UIContext.ID, room.getId().toString());
//			uiContext.put("isRecover", "Y");
			uiContext.put("actionMap", actionMap);
			detailUI = (CoreUIObject) UIFactoryHelper.initUIObject(SellControlUI.class.getName(), uiContext, null, "VIEW");
			// detailUI.setDataObject(room);
			sclPanel.setViewportView(detailUI);
			sclPanel.setKeyBoardControl(true);
			// return;
		} else {
//			detailUI.getUIContext().put("isRecover", "Y");
//			detailUI.getUIContext().put(UIContext.ID, room.getId().toString());
			detailUI.onLoad();
		}
	}
	/**
	 * 对数据进行分组显示
	 * 
	 * @author laiquan_luo
	 * 
	 */
	private void setDataGroupBySellState()
	{
		
		this.tblMain.getGroupManager().setGroup(true);
		this.tblMain.getColumn("sellState").setGroup(true);
		this.tblMain.getColumn("sellState").setMergeable(true);
		// this.tblMain.getGroupManager().setTotalize(true);
		this.tblMain.getColumn(1).setStat(true);

		this.tblMain.getGroupManager().setOrientation(KDTStyleConstants.DOWN);

		// 定义两个变量
		IRow row0;
		KDTDataStyle ds;

		// 获取第0级统计的模板
		row0 = (IRow) this.tblMain.getGroupManager().getStatRowTemplate(0);
		// 设置第0级统计行的背景色
		row0.getStyleAttributes().setBackground(
				FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
		// 设置第0级统计行的第0个单元的值
		row0.getCell(1).setValue("小计");
		// 设置第0级统计行第3个单元的统计公式
		row0.getCell("buildingArea").setExpressions(KDTGroupManager.STAT_SUM);
		row0.getCell("roomArea").setExpressions(KDTGroupManager.STAT_SUM);
		row0.getCell("actualBuildingArea").setExpressions(
				KDTGroupManager.STAT_SUM);
		row0.getCell("actualRoomArea").setExpressions(KDTGroupManager.STAT_SUM);
		// row0.getCell("buildPrice").setExpressions(KDTGroupManager.STAT_SUM);
		// 建筑单价不合计modified by wenyaowei20090611
		// row0.getCell("roomPrice").setExpressions(KDTGroupManager.STAT_SUM);
		// 套内单价不合计modified by wenyaowei200906012
		row0.getCell("standardTotalAmount").setExpressions(
				KDTGroupManager.STAT_SUM);

	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception
	{
		if (this.getSelectedKeyValue() != null)
			super.tblMain_tableClicked(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception
	{
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex < 0)
		{
			FDCMsgBox.showInfo("请选择行!");
			this.abort();
		}
		IRow row = this.tblMain.getRow(rowIndex);
		String roomId = (String)row.getCell("id").getValue();
		if(roomId ==null || "".equals(roomId)){
			FDCMsgBox.showInfo("请选择房间!");
			this.abort();
		}
		String sellProjectId = (String)row.getCell("sellProjectId").getValue();
		RoomInfo info = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(roomId));
		RoomSellStateEnum state = info.getSellState();
		//非可售房源是否可以预约排号
		boolean isNoSellCanSin = false;
//		RoomDisplaySetting set = new RoomDisplaySetting(null,SHEParamConstant.PROJECT_SET_MAP);
		Map detailSet = RoomDisplaySetting.getNewProjectSet(null,sellProjectId);
		if(detailSet!=null){
			isNoSellCanSin  = ((Boolean)detailSet.get(SHEParamConstant.T2_IS_NO_SELL_CAN_SIN)).booleanValue();
		}
		
		//不用销售组织判断,改为售楼组织
		Map orgMap = FDCSysContext.getInstance().getOrgMap();
		FullOrgUnitInfo orgUnit = SHEHelper.getCurrentSaleOrg().castToFullOrgUnitInfo();
		String idStr =null;
		if(null!= orgUnit.getId()){
			idStr = orgUnit.getId().toString();
		}
		if(null==orgMap.get(idStr)){
			this.actionBuyingRoomPlan.setEnabled(false);
			this.actionKeepDown.setEnabled(false);
			this.actionCancelKeepDown.setEnabled(false);
			this.actionSinPurchase.setEnabled(false);
			this.actionPrePurchase.setEnabled(false);
			this.actionPurchase.setEnabled(false);
			this.acionSignContract.setEnabled(false);
			this.actionReceiveBill.setEnabled(false);
			this.actionCustomer.setEnabled(false);
			this.actionCustomerChangeName.setEnabled(false);
			this.actionChangeRoom.setEnabled(false);
			this.actionQuitRoom.setEnabled(false);
			this.actionPriceChange.setEnabled(false);
			this.btnSpecialBiz.setEnabled(false);
			this.menuSpecialBiz.setEnabled(false);
		}else {
			if(state.equals(RoomSellStateEnum.Init)){
				if(isNoSellCanSin){
					actionSinPurchase.setEnabled(true);
				}else{
					actionSinPurchase.setEnabled(false);
				}
				actionBuyingRoomPlan.setEnabled(false);
				actionKeepDown.setEnabled(true);
				actionKeepDown.setVisible(true);
				actionCancelKeepDown.setVisible(false);
				actionCancelKeepDown.setEnabled(false);
				actionPrePurchase.setEnabled(false);
				actionPurchase.setEnabled(false);
				acionSignContract.setEnabled(false);
				actionReceiveBill.setEnabled(false);
				actionCustomer.setEnabled(true);
				actionCustomerChangeName.setEnabled(false);
				actionChangeRoom.setEnabled(false);
				actionQuitRoom.setEnabled(false);
				actionPriceChange.setEnabled(false);
				btnSpecialBiz.setEnabled(false);
				menuSpecialBiz.setEnabled(false);
			} else if (state.equals(RoomSellStateEnum.OnShow)) {
				actionBuyingRoomPlan.setEnabled(true);
				actionKeepDown.setEnabled(true);
				actionKeepDown.setVisible(true);
				actionCancelKeepDown.setVisible(false);
				actionCancelKeepDown.setEnabled(false);
				//待售房间也可进行预约排号、预定、认购、签约的操作
				actionSinPurchase.setEnabled(true);
				actionPrePurchase.setEnabled(true);
				actionPurchase.setEnabled(true);
				acionSignContract.setEnabled(true);
				actionReceiveBill.setEnabled(false);
				actionCustomer.setEnabled(true);
				actionCustomerChangeName.setEnabled(false);
				actionChangeRoom.setEnabled(false);
				actionQuitRoom.setEnabled(false);
				actionPriceChange.setEnabled(false);
				btnSpecialBiz.setEnabled(false);
				menuSpecialBiz.setEnabled(false);
			}else if (state.equals(RoomSellStateEnum.KeepDown)) {
				actionBuyingRoomPlan.setEnabled(false);
				actionKeepDown.setEnabled(false);
				actionCancelKeepDown.setVisible(true);
				actionCancelKeepDown.setEnabled(true);
				actionSinPurchase.setEnabled(false);
				actionPrePurchase.setVisible(true);
				actionPrePurchase.setEnabled(true);
				actionPurchase.setEnabled(true);
				acionSignContract.setVisible(true);
				acionSignContract.setEnabled(true);
				actionReceiveBill.setEnabled(false);
				actionCustomer.setEnabled(true);
				actionCustomerChangeName.setEnabled(false);
				actionChangeRoom.setEnabled(false);
				actionQuitRoom.setEnabled(false);
				actionPriceChange.setEnabled(false);
				btnSpecialBiz.setEnabled(false);
				menuSpecialBiz.setEnabled(false);
			} else if(RoomSellStateEnum.SincerPurchase.equals(state)){
				//可以针对排号状态的预约单进行退号、转预定、认购和签约的操作
				actionBuyingRoomPlan.setEnabled(false);
				actionKeepDown.setEnabled(false);
				actionKeepDown.setVisible(true);
				actionCancelKeepDown.setVisible(false);
				actionCancelKeepDown.setEnabled(false);
				//还差一个退号的功能
				actionSinPurchase.setEnabled(true);
				actionPrePurchase.setEnabled(true);
				actionPurchase.setEnabled(true);
				acionSignContract.setEnabled(true);
				actionReceiveBill.setEnabled(true);
				actionCustomer.setEnabled(true);
				actionCustomerChangeName.setEnabled(false);
				actionChangeRoom.setEnabled(false);
				actionQuitRoom.setEnabled(false);
				actionPriceChange.setEnabled(false);
				btnSpecialBiz.setEnabled(false);
				menuSpecialBiz.setEnabled(false);
				//预定、认购、签约的房间可以使用特殊业务的功能，包括更名、换房、价格变更、退房
			} else if (state.equals(RoomSellStateEnum.PrePurchase)) {
				actionBuyingRoomPlan.setEnabled(false);
				actionKeepDown.setEnabled(false);
				actionKeepDown.setVisible(false);
				actionCancelKeepDown.setVisible(false);
				actionCancelKeepDown.setEnabled(false);
				actionSinPurchase.setEnabled(false);
				actionPrePurchase.setEnabled(false);
				actionPurchase.setEnabled(true);
				acionSignContract.setEnabled(true);
				actionReceiveBill.setEnabled(true);
				actionCustomer.setEnabled(true);
				actionCustomerChangeName.setEnabled(true);
				actionChangeRoom.setEnabled(true);
				actionQuitRoom.setEnabled(true);
				actionPriceChange.setEnabled(true);
				btnSpecialBiz.setEnabled(true);
				menuSpecialBiz.setEnabled(true);
			} else if (state.equals(RoomSellStateEnum.Purchase)) {
				actionBuyingRoomPlan.setEnabled(false);
				actionKeepDown.setEnabled(false);
				actionKeepDown.setVisible(false);
				actionCancelKeepDown.setVisible(false);
				actionCancelKeepDown.setEnabled(false);
				actionSinPurchase.setEnabled(false);
				actionPrePurchase.setEnabled(false);
				actionPurchase.setEnabled(false);
				acionSignContract.setEnabled(true);
				actionReceiveBill.setEnabled(true);
				actionCustomer.setEnabled(true);
				actionCustomerChangeName.setEnabled(true);
				actionChangeRoom.setEnabled(true);
				actionQuitRoom.setEnabled(true);
				actionPriceChange.setEnabled(true);
				btnSpecialBiz.setEnabled(true);
				menuSpecialBiz.setEnabled(true);
			} else if (state.equals(RoomSellStateEnum.Sign)) {
				actionBuyingRoomPlan.setEnabled(false);
				actionKeepDown.setEnabled(false);
				actionKeepDown.setVisible(false);
				actionCancelKeepDown.setVisible(false);
				actionCancelKeepDown.setEnabled(false);
				actionSinPurchase.setEnabled(false);
				actionPrePurchase.setEnabled(false);
				actionPurchase.setEnabled(false);
				acionSignContract.setEnabled(false);
				actionReceiveBill.setEnabled(true);
				actionCustomer.setEnabled(true);
				actionCustomerChangeName.setEnabled(true);
				actionChangeRoom.setEnabled(true);
				actionQuitRoom.setEnabled(true);
				actionPriceChange.setEnabled(true);
				btnSpecialBiz.setEnabled(true);
				menuSpecialBiz.setEnabled(true);
			}
		}
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception
	{
		this.execQuery();
		((SellControlUI)detailUI).refresh(null);
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception
	{
		this.execQuery();
	}

	protected ITreeBase getTreeInterface() throws Exception
	{
		return null;
	}

	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK,
			EntityViewInfo viewInfo)
	{
		
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if(node==null) node = (DefaultKingdeeTreeNode)treeMain.getModel().getRoot();
		
		String allSpIdStr = FDCTreeHelper.getStringFromSet(FDCTreeHelper.getAllObjectIdMap(node, "SellProject").keySet());
		if(allSpIdStr.trim().length()==0)
			allSpIdStr = "'nullnull'"; 
		
		try	{
			FilterInfo filter = new FilterInfo();
			if(node!=null){
				if (node.getUserObject() instanceof SellProjectInfo)		{
					SellProjectInfo spInfo = (SellProjectInfo)node.getUserObject();
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id", spInfo.getId().toString()));
				}else if (node.getUserObject() instanceof SubareaInfo)		{
					SubareaInfo subInfo = (SubareaInfo) node.getUserObject();
					filter.getFilterItems().add(new FilterItemInfo("subarea.id", subInfo.getId().toString()));
				}else if (node.getUserObject() instanceof BuildingInfo)		{
					BuildingInfo bdInfo = (BuildingInfo)node.getUserObject();
					filter.getFilterItems().add(new FilterItemInfo("building.id", bdInfo.getId().toString()));
				}else if (node.getUserObject() instanceof BuildingUnitInfo)		{
					BuildingInfo bdInfo = (BuildingInfo)((DefaultKingdeeTreeNode)node.getParent()).getUserObject();
					BuildingUnitInfo buInfo = (BuildingUnitInfo)node.getUserObject();
//					filter.getFilterItems().add(new FilterItemInfo("building.id", bdInfo.getId().toString()));
//					filter.getFilterItems().add(new FilterItemInfo("room.unit", new Integer(buInfo.getSeq())));
//					现在已近改为buildUnit这个字段 ，这里的过滤条件也改掉 xiaoao_liu
					filter.getFilterItems().add(new FilterItemInfo("room.buildUnit.id", buInfo.getId().toString()));
				}
			}
			if(filter.getFilterItems().size()==0)
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", allSpIdStr ,CompareType.INNER));
			
			
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
		} catch (Exception e)
		{
			handleException(e);
		}
		initCtrl();
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	protected ICoreBase getBizInterface() throws Exception
	{
		return RoomFactory.getRemoteInstance();
	}

	protected String getEditUIName()
	{
		return RoomSourceEditUI.class.getName();
	}

	protected void initTree() throws Exception {
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		this.treeMain.setModel(FDCTreeHelper.getUnitTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	protected void initCtrl()
	{
		this.tblMain.getColumn("buildingArea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("buildingArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("roomArea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("roomArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("actualBuildingArea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("actualBuildingArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("actualRoomArea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("actualRoomArea").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("buildPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("buildPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("roomPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("roomPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.tblMain.getColumn("standardTotalAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("standardTotalAmount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		this.setDataGroupBySellState();
	}

	protected boolean isIgnoreCUFilter()
	{
		return true;
	}

	protected String getLongNumberFieldName()
	{
		return "number";
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception
	{
		checkSelected();
		if (confirmRemove())
		{
			// prepareRemove(null).callHandler();
			Remove();
			refresh(e);
		}
	}

	// 暴露treeMain，供RoomintentFilterUI中根据选中的节点设置一些过滤
	public KDTree getTreeMain()
	{
		return this.treeMain;
	}

	  /**
     * output actionSinPurchase_actionPerformed method
     */
    public void actionSinPurchase_actionPerformed(ActionEvent e) throws Exception
    {
    	String buildingId = null;
    	String  roomId = null;
    	String sellProjectId = null;
    	SellProjectInfo sellProject = null;
    	int index = tabNew.getSelectedIndex();
    	if(index == 0){
    		roomId = (String)detailUI.getUIContext().get("roomId");
    		buildingId = (String)detailUI.getUIContext().get("buildingId");
    		sellProject = (SellProjectInfo)detailUI.getUIContext().get("sellProject");
    	}else if(index == 1){
    		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
    		if (rowIndex == -1)
    		{
    			FDCMsgBox.showInfo("请选择行!");
    			this.abort();
    		}
    		IRow row = this.tblMain.getRow(rowIndex);
    		roomId = (String)row.getCell("id").getValue();
    		buildingId = (String)row.getCell("buildingId").getValue();
    		sellProjectId = (String)row.getCell("sellProjectId").getValue();
    		sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProjectId));
    	}
    	if(roomId == null || buildingId == null){
    		FDCMsgBox.showInfo("请选择房间!");
			this.abort();
    	}
    	boolean isEnable = SHEManageHelper.RoomSelectParamIsEnable(buildingId);
    	if(!isEnable){
    		UIContext uiContext = new UIContext(this);
    		uiContext.put("roomId", roomId);
    		uiContext.put("sellProject", sellProject);
    		IUIWindow uiWindow = UIFactory.createUIFactory(
    				UIFactoryName.MODEL).create(
    						SincerityPurchaseChangeNameUI.class.getName(),
    				uiContext, null, OprtState.ADDNEW);
    		uiWindow.show();
    	}
    	((SellControlUI)detailUI).refresh(null);
    }
    
    
    public void actionKeepDown_actionPerformed(ActionEvent e) throws Exception
    {
    	String buildingId = null;
    	String  roomId = null;
    	String sellProjectId = null;
    	SellProjectInfo sellProject = null;
    	int index = tabNew.getSelectedIndex();
    	if(index == 0){
    		roomId = (String)detailUI.getUIContext().get("roomId");
    		buildingId = (String)detailUI.getUIContext().get("buildingId");
    		sellProject = (SellProjectInfo)detailUI.getUIContext().get("sellProject");
    	}else if(index == 1){
    		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
    		if (rowIndex == -1)
    		{
    			FDCMsgBox.showInfo("请选择行!");
    			this.abort();
    		}
    		IRow row = this.tblMain.getRow(rowIndex);
    		roomId = (String)row.getCell("id").getValue();
    		buildingId = (String)row.getCell("buildingId").getValue();
    		sellProjectId = (String)row.getCell("sellProjectId").getValue();
    		sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProjectId));
    	}
    	if(roomId == null || buildingId == null){
    		FDCMsgBox.showInfo("请选择房间!");
			this.abort();
    	}
    	boolean isEnable = SHEManageHelper.RoomSelectParamIsEnable(buildingId);
    	if(!isEnable){
    		UIContext uiContext = new UIContext(this);
    		uiContext.put("roomId", roomId);
    		uiContext.put("sellProject", sellProject);
    		IUIWindow uiWindow = UIFactory.createUIFactory(
    				UIFactoryName.MODEL).create(
    						RoomKeepDownBillEditUI.class.getName(),
    				uiContext, null, OprtState.ADDNEW);
    		uiWindow.show();
    	}
    	((SellControlUI)detailUI).refresh(null);
    }
    	
    

    /**
     * output actionPrePurchase_actionPerformed method
     */
    public void actionPrePurchase_actionPerformed(ActionEvent e) throws Exception
    {
    	String sincerityID = null;
    	boolean isAdd = false;
    	String buildingId = null;
    	String  roomId = null;
    	String sellProjectId = null;
    	SellProjectInfo sellProject = null;
    	RoomSellStateEnum state = null;
    	BizEnumValueInfo sellState=null;
    	int index = tabNew.getSelectedIndex();
    	if(index == 0){
    		roomId = (String)detailUI.getUIContext().get("roomId");
    		buildingId = (String)detailUI.getUIContext().get("buildingId");
    		sellProject = (SellProjectInfo)detailUI.getUIContext().get("sellProject");
    		state = (RoomSellStateEnum)detailUI.getUIContext().get("state");
    		if(RoomSellStateEnum.SincerPurchase.equals(state)){
    			KDTable sincerityPurchaseTable = (KDTable)detailUI.getUIContext().get("sincerityPurchaseTable");
        		sincerityPurchaseTable.checkParsed();
        		int rowIndex = sincerityPurchaseTable.getSelectManager().getActiveRowIndex();
        		if(rowIndex < 0){
        			if(sincerityPurchaseTable.getRowCount() > 0){
        				if (MsgBox.showConfirm2("未选择【预约排号单】，是否继续？")!= MsgBox.YES){
            				return;
            			}
        			}
        			isAdd = true;
        		}else {
        			IRow row = sincerityPurchaseTable.getRow(rowIndex);
        			sincerityID = row.getCell("id").getValue().toString();
        			isAdd = false;
        		}
    		}
    	}else if(index == 1){
    		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
    		if (rowIndex == -1)
    		{
    			FDCMsgBox.showInfo("请选择行!");
    			this.abort();
    		}
    		IRow row = this.tblMain.getRow(rowIndex);
    		roomId = (String)row.getCell("id").getValue();
    		buildingId = (String)row.getCell("buildingId").getValue();
    		sellProjectId = (String)row.getCell("sellProjectId").getValue();
    		sellState = (BizEnumValueInfo) row.getCell("sellState").getValue();
    		sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProjectId));
    	}
    	if(roomId == null || buildingId == null){
    		FDCMsgBox.showInfo("请选择房间!");
			this.abort();
    	}
    	if((index == 0 && (RoomSellStateEnum.SincerPurchase.equals(state) || RoomSellStateEnum.OnShow.equals(state))) 
    			|| (index == 1 && (RoomSellStateEnum.SINCERPURCHASE_VALUE.equals(sellState.getValue()) 
    					|| RoomSellStateEnum.OnShow.equals(sellState.getValue())))){
	  		boolean isEnable = SHEManageHelper.RoomSelectParamIsEnable(buildingId);
	  		if(isEnable){
	  			return;
	  		}
    	}
    	
    	if((index == 0 && RoomSellStateEnum.SincerPurchase.equals(state) && !isAdd)){
    		//预约排号转签约
    		SincerityPurchaseInfo info = SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(sincerityID));
			SHEManageHelper.toTransactionBill(BOSUuid.read(info.getId().toString()), info.getBizState(),this,PrePurchaseManageEditUI.class.getName());
    	}else {
    		SHEManageHelper.toTransactionBill(BOSUuid.read(roomId),sellProject, this,PrePurchaseManageEditUI.class.getName());
    	}
    	((SellControlUI)detailUI).refresh(null);
    }
    	

    /**
     * output actionPurchase_actionPerformed method
     */
    public void actionPurchase_actionPerformed(ActionEvent e) throws Exception
    {
    	String sincerityID = null;
    	boolean isAdd = false;
    	String buildingId = null;
    	String  roomId = null;
    	String sellProjectId = null;
    	SellProjectInfo sellProject = null;
    	RoomSellStateEnum state = null;
    	BizEnumValueInfo sellState=null;
    	int index = tabNew.getSelectedIndex();
    	if(index == 0){
    		roomId = (String)detailUI.getUIContext().get("roomId");
    		buildingId = (String)detailUI.getUIContext().get("buildingId");
    		sellProject = (SellProjectInfo)detailUI.getUIContext().get("sellProject");
    		state = (RoomSellStateEnum)detailUI.getUIContext().get("state");
    		if(RoomSellStateEnum.SincerPurchase.equals(state)){
	    		KDTable sincerityPurchaseTable = (KDTable)detailUI.getUIContext().get("sincerityPurchaseTable");
	    		sincerityPurchaseTable.checkParsed();
	    		int rowIndex = sincerityPurchaseTable.getSelectManager().getActiveRowIndex();
	    		if(rowIndex < 0){
	    			if(sincerityPurchaseTable.getRowCount() > 0){
	    				if (MsgBox.showConfirm2("未选择【预约排号单】，是否继续？")!= MsgBox.YES){
	        				return;
	        			}
	    			}
	    			isAdd = true;
	    		}else {
	    			IRow row = sincerityPurchaseTable.getRow(rowIndex);
	    			sincerityID = row.getCell("id").getValue().toString();
	    			isAdd = false;
	    		}
    		}
    	}else if(index == 1){
    		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
    		if (rowIndex == -1)
    		{
    			FDCMsgBox.showInfo("请选择行!");
    			this.abort();
    		}
    		IRow row = this.tblMain.getRow(rowIndex);
    		roomId = (String)row.getCell("id").getValue();
    		buildingId = (String)row.getCell("buildingId").getValue();
    		sellProjectId = (String)row.getCell("sellProjectId").getValue();
    		sellState = (BizEnumValueInfo) row.getCell("sellState").getValue();
    		sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProjectId));
    	}
    	if(roomId == null || buildingId == null){
    		FDCMsgBox.showInfo("请选择房间!");
			this.abort();
    	}
    	
    	if((index == 0 && (RoomSellStateEnum.SincerPurchase.equals(state) || RoomSellStateEnum.OnShow.equals(state))) 
    			|| (index == 1 && (RoomSellStateEnum.SINCERPURCHASE_VALUE.equals(sellState.getValue()) 
    					|| RoomSellStateEnum.OnShow.equals(sellState.getValue())))){
	  		boolean isEnable = SHEManageHelper.RoomSelectParamIsEnable(buildingId);
	  		if(isEnable){
	  			return;
	  		}
    	}
    	RoomInfo room = null;
    	if(roomId != null && !"".equals(roomId)){
    		room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(roomId));
    	}
    	if((index == 0 && RoomSellStateEnum.SincerPurchase.equals(state) && !isAdd)){
    		//预约排号转签约
    		SincerityPurchaseInfo info = SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(sincerityID));
			SHEManageHelper.toTransactionBill(BOSUuid.read(info.getId().toString()), info.getBizState(),this,PurchaseManageEditUI.class.getName());
    	}else if((index == 0 && RoomSellStateEnum.PrePurchase.equals(state)) || (index == 1 && RoomSellStateEnum.PREPURCHASE_VALUE.equals(sellState.getValue()))){
    		PrePurchaseManageInfo info = getRoomPrePurchaseManage(room);
			
			if(info==null){
				FDCMsgBox.showWarning(this,"该单据不是审批状态，不能进行转认购操作！");
				this.abort();
			}
			SHEManageHelper.toTransactionBill(BOSUuid.read(info.getId().toString()), info.getBizState(),this,PurchaseManageEditUI.class.getName());
		}else{
			SHEManageHelper.toTransactionBill(BOSUuid.read(roomId),sellProject,this,PurchaseManageEditUI.class.getName());
		}
    	((SellControlUI)detailUI).refresh(null);
    }
    public static PrePurchaseManageInfo getRoomPrePurchaseManage(RoomInfo room) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.PREAUDIT_VALUE));
		
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("bizState");
		PrePurchaseManageCollection prePurchaseManages = PrePurchaseManageFactory.getRemoteInstance().getPrePurchaseManageCollection(view);
		if (prePurchaseManages.size() > 1) {
			MsgBox.showInfo("多个有效预定单,系统错误!");
			SysUtil.abort();
		}
		if (prePurchaseManages.size() == 0) {
			return null;
		}
		return prePurchaseManages.get(0);
	}
    
    public static PurchaseManageInfo getRoomPurchaseManage(RoomInfo room) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.PURAUDIT_VALUE));
		
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("bizState");
		PurchaseManageCollection purchaseManages = PurchaseManageFactory.getRemoteInstance().getPurchaseManageCollection(view);
		if (purchaseManages.size() > 1) {
			MsgBox.showInfo("多个有效认购单,系统错误!");
			SysUtil.abort();
		}
		if (purchaseManages.size() == 0) {
			return null;
		}
		return purchaseManages.get(0);
	}
	
	public static SignManageInfo getRoomSignManage(RoomInfo room) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("bizState",TransactionStateEnum.SIGNAUDIT_VALUE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("bizState");
		SignManageCollection signManages = SignManageFactory.getRemoteInstance().getSignManageCollection(view);
		if (signManages.size() > 1) {
			MsgBox.showInfo("多个有效签约单,系统错误!");
			SysUtil.abort();
		}
		if (signManages.size() == 0) {
			return null;
		}
		return signManages.get(0);
	}

    /**
     * output acionSignContract_actionPerformed method
     */
    public void acionSignContract_actionPerformed(ActionEvent e) throws Exception
    {
    	String sincerityID = null;
    	boolean isAdd = false;
    	String buildingId = null;
    	String  roomId = null;
    	String sellProjectId = null;
    	SellProjectInfo sellProject = null;
    	RoomSellStateEnum state = null;
    	BizEnumValueInfo sellState=null;
    	int index = tabNew.getSelectedIndex();
    	if(index == 0){
    		roomId = (String)detailUI.getUIContext().get("roomId");
    		buildingId = (String)detailUI.getUIContext().get("buildingId");
    		sellProject = (SellProjectInfo)detailUI.getUIContext().get("sellProject");
    		state = (RoomSellStateEnum)detailUI.getUIContext().get("state");
    		if(RoomSellStateEnum.SincerPurchase.equals(state)){
	    		KDTable sincerityPurchaseTable = (KDTable)detailUI.getUIContext().get("sincerityPurchaseTable");
	    		sincerityPurchaseTable.checkParsed();
	    		int rowIndex = sincerityPurchaseTable.getSelectManager().getActiveRowIndex();
	    		if(rowIndex < 0){
	    			if(sincerityPurchaseTable.getRowCount() > 0){
	    				if (MsgBox.showConfirm2("未选择【预约排号单】，是否继续？")!= MsgBox.YES){
	        				return;
	        			}
	    			}
	    			isAdd = true;
	    		}else {
	    			IRow row = sincerityPurchaseTable.getRow(rowIndex);
	    			sincerityID = row.getCell("id").getValue().toString();
	    			isAdd = false;
	    		}
    		}
    	}else if(index == 1){
    		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
    		if (rowIndex == -1)
    		{
    			FDCMsgBox.showInfo("请选择行!");
    			this.abort();
    		}
    		IRow row = this.tblMain.getRow(rowIndex);
    		roomId = (String)row.getCell("id").getValue();
    		buildingId = (String)row.getCell("buildingId").getValue();
    		sellProjectId = (String)row.getCell("sellProjectId").getValue();
    		sellState = (BizEnumValueInfo) row.getCell("sellState").getValue();
    		sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProjectId));
    	}
    	if(roomId == null || buildingId == null){
    		FDCMsgBox.showInfo("请选择房间!");
			this.abort();
    	}
    	if((index == 0 && (RoomSellStateEnum.SincerPurchase.equals(state) || RoomSellStateEnum.OnShow.equals(state))) 
    			|| (index == 1 && (RoomSellStateEnum.SINCERPURCHASE_VALUE.equals(sellState.getValue()) 
    					|| RoomSellStateEnum.OnShow.equals(sellState.getValue())))){
	  		boolean isEnable = SHEManageHelper.RoomSelectParamIsEnable(buildingId);
	  		if(isEnable){
	  			return;
	  		}
    	}
    	RoomInfo room = null;
    	if(roomId != null && !"".equals(roomId)){
    		room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(roomId));
    	}
    	if((index == 0 && RoomSellStateEnum.SincerPurchase.equals(state) && !isAdd)){
    		//预约排号转签约
    		SincerityPurchaseInfo info = SincerityPurchaseFactory.getRemoteInstance().getSincerityPurchaseInfo(new ObjectUuidPK(sincerityID));
			SHEManageHelper.toTransactionBill(BOSUuid.read(info.getId().toString()), info.getBizState(),this,SignManageEditUI.class.getName());
    	}else if((index == 0 && RoomSellStateEnum.PrePurchase.equals(state)) || (index == 1 && RoomSellStateEnum.PREPURCHASE_VALUE.equals(sellState.getValue()))){
			PrePurchaseManageInfo info = getRoomPrePurchaseManage(room);
			if(info==null){
				FDCMsgBox.showWarning(this,"该单据不是审批状态，不能进行转签约操作！");
				this.abort();
			}
			SHEManageHelper.toTransactionBill(BOSUuid.read(info.getId().toString()), info.getBizState(),this,SignManageEditUI.class.getName());
		}else if((index == 0 && RoomSellStateEnum.Purchase.equals(state)) || (index == 1 && RoomSellStateEnum.PURCHASE_VALUE.equals(sellState.getValue()))){
			PurchaseManageInfo info = getRoomPurchaseManage(room);
			if(info==null){
				FDCMsgBox.showWarning(this,"该单据不是审批状态，不能进行转签约操作！");
				this.abort();
			}
			SHEManageHelper.toTransactionBill(BOSUuid.read(info.getId().toString()), info.getBizState(),this,SignManageEditUI.class.getName());
		}else{
			SHEManageHelper.toTransactionBill(BOSUuid.read(roomId),sellProject,this,SignManageEditUI.class.getName());
		}
    	((SellControlUI)detailUI).refresh(null);
    }
    /**
     * output actionReceiveBill_actionPerformed method
     */
    public void actionReceiveBill_actionPerformed(ActionEvent e) throws Exception
    {
    	String buildingId = null;
    	String  roomId = null;
    	String sellProjectId = null;
    	SellProjectInfo sellProject = null;
    	RoomSellStateEnum state = null;
    	BizEnumValueInfo sellState=null;
    	int index = tabNew.getSelectedIndex();
    	if(index == 0){
    		roomId = (String)detailUI.getUIContext().get("roomId");
    		buildingId = (String)detailUI.getUIContext().get("buildingId");
    		sellProject = (SellProjectInfo)detailUI.getUIContext().get("sellProject");
    		state = (RoomSellStateEnum)detailUI.getUIContext().get("state");
    	}else if(index == 1){
    		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
    		if (rowIndex == -1)
    		{
    			FDCMsgBox.showInfo("请选择行!");
    			this.abort();
    		}
    		IRow row = this.tblMain.getRow(rowIndex);
    		roomId = (String)row.getCell("id").getValue();
    		buildingId = (String)row.getCell("buildingId").getValue();
    		sellProjectId = (String)row.getCell("sellProjectId").getValue();
    		sellState = (BizEnumValueInfo) row.getCell("sellState").getValue();
    		sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProjectId));
    	}
    	if(roomId == null || buildingId == null){
    		FDCMsgBox.showInfo("请选择房间!");
			this.abort();
    	}
    	BaseTransactionInfo info = null;
    	Object[] custObjs= null;
    	String sqlSelectStr = "select id,number,transactionID,bizDate,sellProject.*  ";
    	String sqlWhereStr = " where room.id='"+roomId+ "'";
    	if((index == 0 && RoomSellStateEnum.PrePurchase.equals(state)) || (index == 1 && RoomSellStateEnum.PREPURCHASE_VALUE.equals(sellState.getValue()))){
    		String viewString = sqlSelectStr +",prePurchaseCustomerEntry.customer.name,prePurchaseCustomerEntry.customer.number "
				+ sqlWhereStr + " and (bizState = '"+TransactionStateEnum.PREAPPLE_VALUE+"' or bizState='"+TransactionStateEnum.PREAUDIT_VALUE+"') ";
    		PrePurchaseManageCollection prePurColl = PrePurchaseManageFactory.getRemoteInstance().getPrePurchaseManageCollection(viewString);
    		if(prePurColl.size()==1) {
    			info = prePurColl.get(0);
	    		PrePurchaseManageInfo prePurchaseManageInfo = (PrePurchaseManageInfo)info; 
	    		custObjs= new Object[prePurchaseManageInfo.getPrePurchaseCustomerEntry().size()];
	    		for(int i=0;i<prePurchaseManageInfo.getPrePurchaseCustomerEntry().size();i++){
	    			custObjs[i]=prePurchaseManageInfo.getPrePurchaseCustomerEntry().get(i).getCustomer();
	    		}//,purCustomerEntry.*,,prePurchaseCustomerEntry.*，,signCustomerEntry.*
    		}else{
    			return;
    		}
    	}else if((index == 0 && RoomSellStateEnum.Purchase.equals(state)) || (index == 1 && RoomSellStateEnum.PURCHASE_VALUE.equals(sellState.getValue()))){
    		PurchaseManageCollection purColl = PurchaseManageFactory.getRemoteInstance().getPurchaseManageCollection(
    				sqlSelectStr +",purCustomerEntry.customer.name,purCustomerEntry.customer.number "+ sqlWhereStr + " and (bizState = '"+TransactionStateEnum.PURAPPLE_VALUE+"' or bizState='"+TransactionStateEnum.PURAUDIT_VALUE+"') ");
    		if(purColl.size()==1) {
    			info = purColl.get(0);
	    		PurchaseManageInfo purchaseManageInfo = (PurchaseManageInfo)info; 
	    		custObjs= new Object[purchaseManageInfo.getPurCustomerEntry().size()];
	    		for(int i=0;i<purchaseManageInfo.getPurCustomerEntry().size();i++){
	    			custObjs[i]=purchaseManageInfo.getPurCustomerEntry().get(i).getCustomer();
	    		}
    		}else{
    			return;
    		}
    	}else if((index == 0 && RoomSellStateEnum.Sign.equals(state)) || (index == 1 && RoomSellStateEnum.SIGN_VALUE.equals(sellState.getValue()))){
    		SignManageCollection signColl = SignManageFactory.getRemoteInstance().getSignManageCollection(
    				sqlSelectStr +",signCustomerEntry.customer.name,signCustomerEntry.customer.number "+ sqlWhereStr + " and (bizState = '"+TransactionStateEnum.SIGNAPPLE_VALUE+"' or bizState='"+TransactionStateEnum.SIGNAUDIT_VALUE+"') "); 
    		if(signColl.size()==1) {
    			info = signColl.get(0);
	    		SignManageInfo signManageInfo = (SignManageInfo)info; 
	    		custObjs= new Object[signManageInfo.getSignCustomerEntry().size()];
	    		for(int i=0;i<signManageInfo.getSignCustomerEntry().size();i++){
	    			custObjs[i]=signManageInfo.getSignCustomerEntry().get(i).getCustomer();
	    		}
    		}else{
    			return;
    		}
    	}else if((index == 0 && RoomSellStateEnum.SincerPurchase.equals(state)) || (index == 1 && RoomSellStateEnum.SINCERPURCHASE_VALUE.equals(sellState.getValue()))){
    		SincerityPurchaseCollection sinPurColl = SincerityPurchaseFactory.getRemoteInstance()
    										.getSincerityPurchaseCollection(sqlSelectStr +",customer.customer.* "+ sqlWhereStr  + " and bizState = '"+TransactionStateEnum.BAYNUMBER_VALUE+"'  ");
    		if(sinPurColl.size()==0) return;
    		int indexNum = 0;
    		if(sinPurColl.size()>1){
        		KDTable sincerityPurchaseTable = (KDTable)detailUI.getUIContext().get("sincerityPurchaseTable");
        		sincerityPurchaseTable.checkParsed();
        		int rowIndex = sincerityPurchaseTable.getSelectManager().getActiveRowIndex();
        		if(rowIndex < 0){
        			FDCMsgBox.showInfo("请先选择【预约排号单】！");
        			return;
        		}else{
        			String idTempStr = (String)sincerityPurchaseTable.getRow(rowIndex).getCell("id").getValue();
        			for (int i = 0; i < sinPurColl.size(); i++) {
						if(idTempStr.equals(sinPurColl.get(i).getId().toString()))  indexNum = i;
					}
        		}
    		}
    		
    		SincerityPurchaseInfo sinPurInfo = (SincerityPurchaseInfo)sinPurColl.get(indexNum); 
    		custObjs= new Object[sinPurInfo.getCustomer().size()];
    		for(int i=0;i<sinPurInfo.getCustomer().size();i++){
    			custObjs[i]=sinPurInfo.getCustomer().get(i).getCustomer();
    		}
    		info = sinPurInfo;
    	}    	
    	
		if(info.isIsValid()||TransactionStateEnum.CNNULLIFY.equals(info.getBizState())||
				TransactionStateEnum.CRNULLIFY.equals(info.getBizState())||TransactionStateEnum.QRNULLIFY.equals(info.getBizState())||
					TransactionStateEnum.PCNULLIFY.equals(info.getBizState())){
			
			FDCMsgBox.showWarning(this,"该签约单据业务状态不能进行收款操作！");
			SysUtil.abort();
		}
		

		Set transEntryIdSet = new HashSet();
		if(info!=null && info.getTransactionID()!=null) {
			TranBusinessOverViewCollection transViewColl = TranBusinessOverViewFactory.getRemoteInstance()
						.getTranBusinessOverViewCollection("select appAmount,moneyDefine.name,moneyDefine.number,moneyDefine.moneyType " +
								"where head.id = '"+info.getTransactionID()+"' and type = '"+BusTypeEnum.PAY_VALUE+"' ");
			if(transViewColl.size()>0){
				for (int j = 0; j < transViewColl.size(); j++) {
					TranBusinessOverViewInfo transViewInfo = transViewColl.get(j);
					transEntryIdSet.add(transViewInfo.getId().toString());
				}				
			}
		}
		CRMClientHelper.openTheGatherRevBillWindow(this, null, info.getSellProject(),info ,custObjs,transEntryIdSet);
		((SellControlUI)detailUI).refresh(null);
    }

	public void actionChangeRoom_actionPerformed(ActionEvent e) throws Exception {
		String  roomId = null;
    	int index = tabNew.getSelectedIndex();
    	if(index == 0){
    		roomId = (String)detailUI.getUIContext().get("roomId");
    	}else if(index == 1){
    		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
    		if (rowIndex == -1)
    		{
    			FDCMsgBox.showInfo("请选择行!");
    			this.abort();
    		}
    		IRow row = this.tblMain.getRow(rowIndex);
    		roomId = (String)row.getCell("id").getValue();
    	}
    	if(roomId == null ){
    		FDCMsgBox.showInfo("请选择房间!");
			this.abort();
    	}
		SHEManageHelper.toChangeManage(ChangeBizTypeEnum.CHANGEROOM, BOSUuid.read(roomId), this, true);
		((SellControlUI)detailUI).refresh(null);
	}

	public void actionCustomerChangeName_actionPerformed(ActionEvent e) throws Exception {
		String  roomId = null;
    	int index = tabNew.getSelectedIndex();
    	if(index == 0){
    		roomId = (String)detailUI.getUIContext().get("roomId");
    	}else if(index == 1){
    		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
    		if (rowIndex == -1)
    		{
    			FDCMsgBox.showInfo("请选择行!");
    			this.abort();
    		}
    		IRow row = this.tblMain.getRow(rowIndex);
    		roomId = (String)row.getCell("id").getValue();
    	}
    	if(roomId == null ){
    		FDCMsgBox.showInfo("请选择房间!");
			this.abort();
    	}
		SHEManageHelper.toChangeManage(ChangeBizTypeEnum.CHANGENAME, BOSUuid.read(roomId), this, true);
		((SellControlUI)detailUI).refresh(null);
	}

	public void actionPriceChange_actionPerformed(ActionEvent e) throws Exception {
    	String  roomId = null;
    	int index = tabNew.getSelectedIndex();
    	if(index == 0){
    		roomId = (String)detailUI.getUIContext().get("roomId");
    	}else if(index == 1){
    		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
    		if (rowIndex == -1)
    		{
    			FDCMsgBox.showInfo("请选择行!");
    			this.abort();
    		}
    		IRow row = this.tblMain.getRow(rowIndex);
    		roomId = (String)row.getCell("id").getValue();
    	}
    	if(roomId == null ){
    		FDCMsgBox.showInfo("请选择房间!");
			this.abort();
    	}
		SHEManageHelper.toChangeManage(ChangeBizTypeEnum.PRICECHANGE, BOSUuid.read(roomId), this, true);
		((SellControlUI)detailUI).refresh(null);
	}

	public void actionQuitRoom_actionPerformed(ActionEvent e) throws Exception {
		String  roomId = null;
    	int index = tabNew.getSelectedIndex();
    	if(index == 0){
    		roomId = (String)detailUI.getUIContext().get("roomId");
    	}else if(index == 1){
    		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
    		if (rowIndex == -1)
    		{
    			FDCMsgBox.showInfo("请选择行!");
    			this.abort();
    		}
    		IRow row = this.tblMain.getRow(rowIndex);
    		roomId = (String)row.getCell("id").getValue();
    	}
    	if(roomId == null ){
    		FDCMsgBox.showInfo("请选择房间!");
			this.abort();
    	}
		SHEManageHelper.toChangeManage(ChangeBizTypeEnum.QUITROOM, BOSUuid.read(roomId), this, true);
		((SellControlUI)detailUI).refresh(null);
	}	

	public void actionCustomer_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(
				CustomerRptListUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
		super.actionCustomer_actionPerformed(e);
	}
	public void actionSpecialAgio_actionPerformed(ActionEvent e) throws Exception {
		String  roomId = null;
    	int index = tabNew.getSelectedIndex();
    	SellProjectInfo sellProject=null;
    	String sellProjectId = null;
    	if(index == 0){
    		roomId = (String)detailUI.getUIContext().get("roomId");
    		sellProject = (SellProjectInfo)detailUI.getUIContext().get("sellProject");
    	}else if(index == 1){
    		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
    		if (rowIndex == -1)
    		{
    			FDCMsgBox.showInfo("请选择行!");
    			this.abort();
    		}
    		IRow row = this.tblMain.getRow(rowIndex);
    		roomId = (String)row.getCell("id").getValue();
    		sellProjectId = (String)row.getCell("sellProjectId").getValue();
    		sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProjectId));
    	}
    	if(roomId == null ){
    		FDCMsgBox.showInfo("请选择房间!");
			this.abort();
    	}
		UIContext uiContext = new UIContext(this);
		uiContext.put("roomId", roomId);
		uiContext.put("sellProject", sellProject);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SpecialDiscountEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}

}