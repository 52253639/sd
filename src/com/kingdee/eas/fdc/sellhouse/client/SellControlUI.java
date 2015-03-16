/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.sql.RowSet;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.BasicView;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTView;
import com.kingdee.bos.ctrl.kdf.table.KDTViewManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.util.style.LineStyle;
import com.kingdee.bos.ctrl.kdf.util.style.ShareStyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.Position;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.KDMenu;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomCollection;
import com.kingdee.eas.fdc.sellhouse.ChangeRoomFactory;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FunctionSetting;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.IRoomRecoverHistory;
import com.kingdee.eas.fdc.sellhouse.PlanisphereElementEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PlanisphereInfo;
import com.kingdee.eas.fdc.sellhouse.PlanisphereTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.QuitRoomCollection;
import com.kingdee.eas.fdc.sellhouse.QuitRoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateFactory;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinFactory;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillFactory;
import com.kingdee.eas.fdc.sellhouse.RoomLoanCollection;
import com.kingdee.eas.fdc.sellhouse.RoomLoanFactory;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookFactory;
import com.kingdee.eas.fdc.sellhouse.RoomRecoverHistoryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomRecoverHistoryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEParamConstant;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.client.AbstractIntentListUI.AcionSignContract;
import com.kingdee.eas.fdc.sellhouse.client.AbstractIntentListUI.ActionBuyingRoomPlan;
import com.kingdee.eas.fdc.sellhouse.client.AbstractIntentListUI.ActionCancelKeepDown;
import com.kingdee.eas.fdc.sellhouse.client.AbstractIntentListUI.ActionChangeRoom;
import com.kingdee.eas.fdc.sellhouse.client.AbstractIntentListUI.ActionCustomer;
import com.kingdee.eas.fdc.sellhouse.client.AbstractIntentListUI.ActionCustomerChangeName;
import com.kingdee.eas.fdc.sellhouse.client.AbstractIntentListUI.ActionKeepDown;
import com.kingdee.eas.fdc.sellhouse.client.AbstractIntentListUI.ActionPrePurchase;
import com.kingdee.eas.fdc.sellhouse.client.AbstractIntentListUI.ActionPriceChange;
import com.kingdee.eas.fdc.sellhouse.client.AbstractIntentListUI.ActionPurchase;
import com.kingdee.eas.fdc.sellhouse.client.AbstractIntentListUI.ActionQuitRoom;
import com.kingdee.eas.fdc.sellhouse.client.AbstractIntentListUI.ActionReceiveBill;
import com.kingdee.eas.fdc.sellhouse.client.AbstractIntentListUI.ActionSinPurchase;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class SellControlUI extends AbstractSellControlUI {
	private static final Logger logger = CoreUIObject.getLogger(SellControlUI.class);

	CoreUIObject detailUI = null;

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	RoomDisplaySetting setting = new RoomDisplaySetting();

	Map sellProAndBuildingMap = new HashMap();
	protected boolean isSaleHouseOrg= FDCSysContext.getInstance().getOrgMap().containsKey(SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
	protected boolean isControler = SHEHelper.isControl();
	
	boolean hasOnLoadFlag = false;

	public static int noSellhouseCount = 0;
	public static int initCount = 0;
	public static int onShowCount = 0;
	public static int prePur = 0;
	public static int purchaseCount = 0;
	public static int signContractCount = 0;
	public static int keepDownCount = 0;
	public static int sincerPurCount = 0;
	public static int sinReCount = 0;
	public static int controlCount = 0;
	SHE2ImagePanel BirdEyePanel2; //add by wanping
	KDScrollPane scrollPanel;
	public static int birdFlag = 0; // add by wanping 
	/**
	 * output class constructor
	 */
	public SellControlUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		initControl();
		super.onLoad();
//		if (!saleOrg.isIsBizUnit()) {
//			this.actionSinPurchase.setEnabled(false);
//			this.actionPurchase.setEnabled(false);
//			this.actionKeepDown.setEnabled(false);
//			this.actionQuitRoom.setEnabled(false);
//			this.actionChangeRoom.setEnabled(false);
//			this.actionSignContract.setEnabled(false);
//			this.actionReceiveBill.setEnabled(false);
//			this.actionChangeName.setEnabled(false);
//		}

		// TODO 调整单先不使用
		this.actionBillAdjust.setVisible(false);
		hasOnLoadFlag = true;
		DisplaySetViewUI.insertUIToScrollPanel(this.kDScrollPane1);
		
		
		//此处是为了解决选择平面图时，选中区域有延迟的问题
		KDTViewManager dkmanager = this.tblMain.getViewManager();
		BasicView view = dkmanager.getView(dkmanager.getDefaultActiveViewIndex());
		view.addMouseListener(new MouseListener(){
			public void mousePressed(MouseEvent e) {
					reflashRoomInfoByPlanisphere();
			}
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
		});
		BirdEyePanel2 = new SHE2ImagePanel(); //add by wanping
		scrollPanel = new KDScrollPane(); //add by wanping
		BirdEyePanel2.setLayout(null); //add by wanping
		
		//添加悬浮
		addFolatTip();
		pnlMain.setDividerLocation(200);
	}
	
	private void addFolatTip(){
		realView = (KDTView)this.tblMain.getViewManager().getView(5);
		if(realView != null ){
			realView.addMouseMotionListener(new MouseMotionAdapter(){
				public void mouseMoved(MouseEvent e) {
						if(cell != null ){
							if(room == null || !room.equals(getSelectCell())){
								room = getSelectCell();
								try {
									roomInfo = SHEHelper.queryRoomInfo(room.getId().toString());
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
							if(isMouseInCell(e)){
								try {
									if(uiObject == null){
										UIContext uiContext = new UIContext();
										uiContext.put("room", roomInfo);
										uiObject = (ShowRoomDetailInfoUI) UIFactoryHelper.initUIObject(ShowRoomDetailInfoUI.class.getName(), uiContext, null, "VIEW");
									} else {
										uiObject.getUIContext().put("room", roomInfo);
										uiObject.onLoad();
									}
									if(frame == null){
										frame  = new ShowRoomDetailInfoFrame();
										frame.add(uiObject,BorderLayout.CENTER);
									}
									
									frame.show(new Double(realView.getLocationOnScreen().getX()).intValue()+e.getX()+10, new Double(realView.getLocationOnScreen().getY()).intValue()+e.getY()+10);
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}  else {
								if(frame!=null){
									frame.setVisible(false);
								}
							}
						}
				}

				private boolean isMouseInCell(MouseEvent e) {
					Rectangle tempCell = cell;
					if(e.getX() <= (tempCell.getX() +tempCell.getWidth()) && e.getX() >= tempCell.getX() 
							&& e.getY() >= tempCell.getY()&& e.getY()<= (tempCell.getY() + tempCell.getHeight())){
						return true;
					}else{
						return false;
					}
					
				}
				
			});
		}
	}
	RoomInfo roomInfo = null;
	RoomInfo room = null;
	ShowRoomDetailInfoFrame frame = null;
	ShowRoomDetailInfoUI uiObject  = null;
	KDTView realView = null;

	public void initControl() {
		this.menuItemImportData.setVisible(false);
		this.menuEdit.setVisible(false);
		this.actionImportData.setVisible(true);
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
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
		this.actionLocate.setVisible(false);
		this.actionQuery.setEnabled(false);
		this.actionImportData.setVisible(false);
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		this.treeView.setShowControlPanel(true);
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getButton() == 1 && e.getClickCount() == 2 ) {
			RoomInfo room = this.getSelectRoom();
			if(room == null){
				return ;
			}
			RoomSellStateEnum sellState = (RoomSellStateEnum)room.getSellState();
			this.getUIContext().put("roomId", room.getId().toString());
			this.getUIContext().put("sellState", sellState);
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", room.getId().toString());
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(RoomSourceEditUI.class.getName(), uiContext, null, "VIEW");
			uiWindow.show();
			reflashProOrBuildByPlanisphere();
		}
		
	}

	/**
	 *设置选中房间的颜色
	 */
	private void SetSelectRoomColor(KDTSelectBlock block) throws Exception {
		if (block == null) {
			return;
		}
		int left = block.getLeft();
		int top = block.getTop();
		ICell cell = this.tblMain.getCell(top, left);
		if (cell == null) {
			return;
		}
		RoomInfo roomInfo = (RoomInfo) cell.getUserObject();
		if (roomInfo == null)
			return;
		roomInfo = RoomFactory.getRemoteInstance().getRoomInfo("select isForSHE,sellState where id ='" + roomInfo.getId() + "'");
		cell.getStyleAttributes().setBackground(SHEHelper.getRoomColorBySys(roomInfo, MoneySysTypeEnum.SalehouseSys, setting));
	}

	private Rectangle cell = null;
	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {

		//非平面图显示时
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if(node!=null && !(node.getUserObject() instanceof PlanisphereInfo)) SetSelectRoomColor(e.getPrevSelectBlock());
		RoomInfo room = this.getSelectRoom();
		if(room == null){
			return ;
		}
		changeButtonState();
		RoomSellStateEnum state = room.getSellState();
		this.getUIContext().put("roomId", room.getId().toString());
		this.getUIContext().put("room", room);
		this.getUIContext().put("buildingId", room.getBuilding().getId().toString());
		this.getUIContext().put("sellProject", room.getBuilding().getSellProject());
		this.getUIContext().put("state", state);
		this.getUIContext().put("totalAmount", room.getStandardTotalAmount());
		this.getUIContext().put("pricingType", room.getCalcType()!=null?room.getCalcType().getAlias():"");
		if (room != null) {
			if (detailUI == null) {
				UIContext uiContext = new UIContext(ui);
				uiContext.put("roomId", room.getId().toString());
//				uiContext.put("buildingId", room.getBuilding().getId().toString());
				uiContext.put(UIContext.ID, room.getId().toString());
				uiContext.put("state", state);
				uiContext.put("sellProject", room.getBuilding().getSellProject());
				detailUI = (CoreUIObject) UIFactoryHelper.initUIObject(VirtualRoomFullInfoUI.class.getName(), uiContext, null, "VIEW");
				//销控的总览，入伙，按揭，产权等页面都按照这个页面来开发RoomFullInfoUI
//				detailUI = (CoreUIObject) UIFactoryHelper.initUIObject(RoomFullInfoUI.class.getName(), uiContext, null, "VIEW");
				// detailUI.setDataObject(room);
				sclPanel.setViewportView(detailUI);
				sclPanel.setKeyBoardControl(true);
				// return;
			} else {
				detailUI.getUIContext().put(UIContext.ID, room.getId().toString());
				detailUI.getUIContext().put("roomId", room.getId().toString());
				detailUI.getUIContext().put("state", state);
				detailUI.onLoad();
			}
			this.getUIContext().put("sincerityPurchaseTable",detailUI.getUIContext().get("sincerityPurchaseTable"));
			// detailUI.setDataObject(detailUI.onLoad());
			// detailUI.loadFields();
		}		
		
		//模拟悬浮
		KDTView view = (KDTView)this.tblMain.getViewManager().getView(5);
		if(view != null){
			cell = view.getCellRectangle( e.getSelectBlock().getBeginRow(), e.getSelectBlock().getBeginCol());
		}
	}
	
	protected boolean checkBeforeWindowClosing() {
		return super.checkBeforeWindowClosing();
	}
		
	/**
	 * 如果是点击的是平面图的 房间 则选中房间轮廓
	 */
	private void reflashRoomInfoByPlanisphere() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if(node==null || !(node.getUserObject() instanceof PlanisphereInfo)) return;
			
		KDTSelectBlock block = this.tblMain.getSelectManager().get();
		if (block == null)
			return;

		int left = block.getLeft();
		int top = block.getTop();
		ICell cell = this.tblMain.getCell(top, left);
		if (cell == null)
			return;
		if (cell.getUserObject() == null)
			return;

		if (cell.getUserObject() instanceof RoomInfo) {
			setRoomSelected((RoomInfo) cell.getUserObject());
		}
	}

	/**
	 * 如果是双击的是平面图的 项目 或 楼栋元素，则刷新树上的响应节点
	 */
	private void reflashProOrBuildByPlanisphere() {
		KDTSelectBlock block = this.tblMain.getSelectManager().get();
		if (block == null)
			return;

		int left = block.getLeft();
		int top = block.getTop();
		ICell cell = this.tblMain.getCell(top, left);
		if (cell == null)
			return;
		if (cell.getUserObject() == null)
			return;

		if (cell.getUserObject() instanceof BuildingInfo || cell.getUserObject() instanceof SellProjectInfo) {
			if (sellProAndBuildingMap.size() == 0) {
				sellProAndBuildingMap = FDCTreeHelper.getAllObjectIdMap((TreeNode) this.treeMain.getModel().getRoot(), "SellProject");
				sellProAndBuildingMap.putAll(FDCTreeHelper.getAllObjectIdMap((TreeNode) this.treeMain.getModel().getRoot(), "Building"));
			}
			FDCDataBaseInfo databaseInfo = (FDCDataBaseInfo) cell.getUserObject();
			DefaultMutableTreeNode thisNode = (DefaultMutableTreeNode) this.sellProAndBuildingMap.get(databaseInfo.getId().toString());
			if (thisNode != null)
				this.treeMain.setSelectionNode((DefaultKingdeeTreeNode) thisNode);
		}

	}

	private void setRoomSelected(RoomInfo room) {
		if (room != null) {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			if (node != null && node.getUserObject() instanceof PlanisphereInfo) {
				EventListener[] listeners = this.tblMain.getListeners(KDTSelectListener.class);
				for (int i = 0; i < listeners.length; i++)
					this.tblMain.removeKDTSelectListener((KDTSelectListener) listeners[i]);

				PlanisphereInfo phInfo = (PlanisphereInfo) node.getUserObject();
				PlanisphereElementEntryCollection eleEntryColl = phInfo.getElementEntry();
				for (int i = 0; i < eleEntryColl.size(); i++) {
					RoomInfo roomInfo = eleEntryColl.get(i).getRoomEntry();
					if (roomInfo != null && roomInfo.getId().equals(room.getId())) {
						TableDrawManager thisPicTable = new TableDrawManager();
						thisPicTable.setTable(this.tblMain);
						thisPicTable.setSelected(CommerceHelper.ByteArrayToListObject(eleEntryColl.get(i).getOutLineLocationData()), room);
					}
				}

				for (int i = 0; i < listeners.length; i++)
					this.tblMain.addKDTSelectListener((KDTSelectListener) listeners[i]);
			}
		}
	}

	private void changeButtonState() throws EASBizException, BOSException, UuidException {
		Map actionMap= (Map)getUIContext().get("actionMap"); 
		RoomInfo room = this.getSelectRoom();
		
		ActionBuyingRoomPlan actionBuyingRoomPlan = (ActionBuyingRoomPlan)actionMap.get("actionBuyingRoomPlan");
		ActionKeepDown actionKeepDown = (ActionKeepDown)actionMap.get("actionKeepDown"); 
		ActionCancelKeepDown actionCancelKeepDown = (ActionCancelKeepDown)actionMap.get("actionCancelKeepDown");
		ActionSinPurchase actionSinPurchase = (ActionSinPurchase)actionMap.get("actionSinPurchase");
		ActionPrePurchase actionPrePurchase = (ActionPrePurchase)actionMap.get("actionPrePurchase");
		ActionPurchase actionPurchase = (ActionPurchase)actionMap.get("actionPurchase");
		AcionSignContract acionSignContract = (AcionSignContract)actionMap.get("acionSignContract");
		ActionReceiveBill actionReceiveBill = (ActionReceiveBill)actionMap.get("actionReceiveBill");
		ActionCustomer actionCustomer = (ActionCustomer)actionMap.get("actionCustomer");
		ActionCustomerChangeName actionCustomerChangeName = (ActionCustomerChangeName)actionMap.get("actionCustomerChangeName");
		ActionChangeRoom actionChangeRoom = (ActionChangeRoom)actionMap.get("actionChangeRoom");
		ActionQuitRoom actionQuitRoom = (ActionQuitRoom)actionMap.get("actionQuitRoom");
		ActionPriceChange actionPriceChange = (ActionPriceChange)actionMap.get("actionPriceChange");
		KDWorkButton btnSpecialBiz =(KDWorkButton)actionMap.get("btnSpecialBiz");
		KDMenu menuSpecialBiz =(KDMenu)actionMap.get("menuSpecialBiz");
		
		RoomSellStateEnum state = room.getSellState();
		if (state == null)
			return;

//		boolean debug = false;
//		String id = room.getBuilding().getSellProject().getId().toString();
//		RoomDisplaySetting setting2= new RoomDisplaySetting();
//		HashMap functionSetMap2 = (HashMap) setting2.getFunctionSetMap();
//		FunctionSetting funcSet2 = (FunctionSetting)functionSetMap2.get(id);
//		if(funcSet2 == null)	{
//			debug = true ;
//		}else{
//			if(funcSet2.getIsActGathering().booleanValue()
//				|| funcSet2.getIsSignGathering().booleanValue()){
//				debug = false ;
//			}else{
//				debug = true ;
//			}
//		}
		boolean isNoSellCanSin = false;
		SellProjectInfo sellProject = (SellProjectInfo)room.getBuilding().getSellProject();
//		RoomDisplaySetting set = new RoomDisplaySetting(null,SHEParamConstant.PROJECT_SET_MAP);
		Map detailSet = RoomDisplaySetting.getNewProjectSet(null,sellProject.getId().toString());
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
			actionBuyingRoomPlan.setEnabled(false);
			actionKeepDown.setEnabled(false);
			actionCancelKeepDown.setEnabled(false);
			actionSinPurchase.setEnabled(false);
			actionPrePurchase.setEnabled(false);
			actionPurchase.setEnabled(false);
			acionSignContract.setEnabled(false);
			actionReceiveBill.setEnabled(false);
			actionCustomer.setEnabled(false);
			actionCustomerChangeName.setEnabled(false);
			actionChangeRoom.setEnabled(false);
			actionQuitRoom.setEnabled(false);
			actionPriceChange.setEnabled(false);
			btnSpecialBiz.setEnabled(false);
			menuSpecialBiz.setEnabled(false);
		}else{
			if (state.equals(RoomSellStateEnum.Init)) {
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
		
		if (room != null) {
			if (room.isIsForSHE()) {
				this.actionSimulate.setEnabled(true);
			} else {
				this.actionSimulate.setEnabled(false);
			}
		} else {
			this.actionSimulate.setEnabled(false);
		}
		// 解决在售楼设置里设置了允许诚意认购未推盘房间在销售控制中还不能诚意认购的问题
		if(room.getSellState().equals(RoomSellStateEnum.Init)) {
			String projectID = room.getBuilding().getSellProject().getId().toString();
			Map functionSetMap = setting.getFunctionSetMap();
			if (functionSetMap.get(projectID) != null) {
				FunctionSetting funcSet = (FunctionSetting) functionSetMap.get(projectID);
				if (funcSet.getIsSincerSellOrder() != null && funcSet.getIsSincerSellOrder().booleanValue()) {
//					this.actionSinPurchase.setEnabled(true);
				}
			}
		}
		
	}
	public void actionSinPurchase_actionPerformed(ActionEvent e) throws Exception {
//		super.actionSinPurchase_actionPerformed(e);
		RoomInfo room = this.getSelectRoom();
		if (room == null) {
			return;
		}
		if (HousePropertyEnum.Attachment.equals(room.getHouseProperty())) {
			MsgBox.showInfo("附属房产不能单独销售!");
			return;
		}
		if (room.getSellState().equals(RoomSellStateEnum.Init) 
				|| room.getSellState().equals(RoomSellStateEnum.OnShow) 
				|| RoomSellStateEnum.SincerPurchase.equals(room.getSellState())) {
			SellProjectInfo projectInfo = room.getBuilding().getSellProject();
			UIContext uiContext = new UIContext(this);
			uiContext.put("sellProject", projectInfo);
			uiContext.put("sellOrder", room.getSellOrder());
			uiContext.put("room", room);
			// 创建UI对象并显示
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SincerityPurchaseEditUI.class.getName(), uiContext, null, "ADDNEW");
			uiWindow.show();
		} else {
			return;
		}
	}

	/**
	 * 获得选中的房间
	 * 
	 * @param reQuery
	 *            是否根据选中房间的ID重新查询,获得更多的字段值
	 * @throws UuidException
	 * @throws BOSException
	 * @throws EASBizException
	 * */
	private ICell selCell =null;
	public RoomInfo getSelectRoom(boolean reQuery) throws EASBizException, BOSException, UuidException {
		KDTSelectBlock block = this.tblMain.getSelectManager().get();
		if (block == null) {
			return null;
		}
		int left = block.getLeft();
		int top = block.getTop();
		ICell cell = this.tblMain.getCell(top, left);
		if (cell == null) {
			return null;
		}
		if(selCell!=null){
			selCell.getStyleAttributes().setBorderColor(Position.LEFT, Color.BLACK);
			selCell.getStyleAttributes().setBorderColor(Position.TOP, Color.BLACK);
			selCell.getStyleAttributes().setBorderColor(Position.RIGHT, Color.BLACK);
			selCell.getStyleAttributes().setBorderColor(Position.BOTTOM, Color.BLACK);
			selCell.getStyleAttributes().setBorderLineStyle(Position.LEFT, LineStyle.NULL_LINE);
			selCell.getStyleAttributes().setBorderLineStyle(Position.TOP, LineStyle.NULL_LINE);
			selCell.getStyleAttributes().setBorderLineStyle(Position.RIGHT, LineStyle.NULL_LINE);
			selCell.getStyleAttributes().setBorderLineStyle(Position.BOTTOM, LineStyle.NULL_LINE);
		}
		selCell=cell;
		
		selCell.getStyleAttributes().setBorderColor(Position.LEFT, Color.BLUE);
		selCell.getStyleAttributes().setBorderColor(Position.TOP, Color.BLUE);
		selCell.getStyleAttributes().setBorderColor(Position.RIGHT, Color.BLUE);
		selCell.getStyleAttributes().setBorderColor(Position.BOTTOM, Color.BLUE);
		selCell.getStyleAttributes().setBorderLineStyle(Position.LEFT, LineStyle.DOUBLE_LINE_A);
		selCell.getStyleAttributes().setBorderLineStyle(Position.TOP, LineStyle.DOUBLE_LINE_A);
		selCell.getStyleAttributes().setBorderLineStyle(Position.RIGHT, LineStyle.DOUBLE_LINE_A);
		selCell.getStyleAttributes().setBorderLineStyle(Position.BOTTOM, LineStyle.DOUBLE_LINE_A);
		
		RoomInfo room = null;
		if (cell.getUserObject() != null && cell.getUserObject() instanceof RoomInfo)
			room = (RoomInfo) cell.getUserObject();
		if (room == null) {
			return null;
		}

		// 为了效率从userObject中只放了一个ID，所以需要再查一遍
		if (reQuery) {
			room = SHEHelper.queryRoomInfo(room.getId().toString());
			cell.setUserObject(room);
		}
		return room;
	}
	
	public RoomInfo getSelectCell() {
		KDTSelectBlock block = this.tblMain.getSelectManager().get();
		if (block == null) {
			return null;
		}
		int left = block.getLeft();
		int top = block.getTop();
		ICell cell = this.tblMain.getCell(top, left);
		if (cell == null) {
			return null;
		}
		RoomInfo room = null;
		if (cell.getUserObject() != null && cell.getUserObject() instanceof RoomInfo)
			room = (RoomInfo) cell.getUserObject();
		if (room == null) {
			return null;
		}
		return room;
	}

	public RoomInfo getSelectRoom() throws EASBizException, BOSException, UuidException {
		return getSelectRoom(true);
	}

	protected void setActionState() {
		// super.setActionState();
	}

	
	protected void initTree() throws Exception {
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		
		this.treeMain.setModel(CRMTreeHelper.getBuildingTree(this.actionOnLoad,true)); 
		
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		refresh(null);
	}
	/**
	 * 鸟瞰图穿透到项目/楼栋后，树状节点也选中该项目/楼栋
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
	public DefaultKingdeeTreeNode getTreeRoot(){
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode)treeMain.getModel().getRoot();
		return root;
	}
	private void setSellStateCountByColor() throws BOSException {
		 noSellhouseCount = 0;
		 initCount = 0;
		 onShowCount = 0;
		 prePur = 0;
		 purchaseCount = 0;
		 signContractCount = 0;
		 keepDownCount = 0;
		 sincerPurCount = 0;
		 sinReCount = 0;
		 controlCount=0;
		RoomDisplaySetting setting = new RoomDisplaySetting();
		Set roomId=new HashSet();
		for (int j = 0; j < this.tblMain.getRowCount(); j++) {
			IRow row = this.tblMain.getRow(j);
			for (int i = 0; i < this.tblMain.getColumnCount(); i++) {
				if(row.getCell(i).getUserObject()!=null&&row.getCell(i).getUserObject() instanceof RoomInfo){
					RoomInfo room=(RoomInfo) row.getCell(i).getUserObject();
					if(roomId.contains(room.getId())){
						continue;
					}else{
						roomId.add(room.getId());
					}
				    ICell cell = row.getCell(i);
				    if (cell != null) {
				    	if (setting.getBaseRoomSetting().getNoSellhouseColor().equals(cell.getStyleAttributes().getBackground())) {
						  noSellhouseCount++;
					  } else if (setting.getBaseRoomSetting().getInitColor().equals(cell.getStyleAttributes().getBackground())) {
						  initCount++;
					  } else if (setting.getBaseRoomSetting().getOnShowColor().equals(cell.getStyleAttributes().getBackground())) {
						  onShowCount++;
					  } else if (setting.getBaseRoomSetting().getPrePurColor().equals(cell.getStyleAttributes().getBackground())) {
						  prePur++;
					  } else if (setting.getBaseRoomSetting().getPurColor().equals(cell.getStyleAttributes().getBackground())) {
						  purchaseCount++;
					  } else if (setting.getBaseRoomSetting().getSignColor().equals(cell.getStyleAttributes().getBackground())) {
						  signContractCount++;
					  } else if (setting.getBaseRoomSetting().getKeepDownColor().equals(cell.getStyleAttributes().getBackground())) {
						  keepDownCount++;
					  } else if(setting.getBaseRoomSetting().getSincerPurColor().equals(cell.getStyleAttributes().getBackground())) {
						  sincerPurCount++;
					  } else if(setting.getBaseRoomSetting().getControlColor().equals(cell.getStyleAttributes().getBackground())) {
						  controlCount++;
					  }
						//增加认购收款的统计  by zgy 
					   else if (setting.getBaseRoomSetting().getSinReColor().equals(cell.getStyleAttributes().getBackground())){
							sinReCount++;
					  }
				   }
				}
			}
		}
		DisplaySetViewUI.insertUIToScrollPanel(this.kDScrollPane1);
	}

	protected void refresh(ActionEvent e) throws BOSException {
//		try {
//			cancelRoom();
//		} catch (EASBizException e1) {
//			e1.printStackTrace();
//		}
		selCell=null;
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}

		if (!hasOnLoadFlag) { // 防止第一次打开时默认选择根节点导致房间数量过大，报10000行错误
			this.tblMain.removeColumns();
			this.tblMain.removeHeadRows();
			this.tblMain.removeRows();
			return;
		}

		// logger.info("构建房间图---- "+ System.currentTimeMillis() );

		// 由于平面图树是在单元树的基础上增加平面图节点生成的，会对单元树的depth产生影响,并影响房间表头的显示，
		// 因此需把所选节点克隆一份，并除去所有的平面图节点，然后再传递进去构建房间列表
		if (node.getUserObject() instanceof PlanisphereInfo) {
			// 显示平面图
			PlanisphereInfo phInfo = (PlanisphereInfo) node.getUserObject();
			if (phInfo.getPtype().equals(PlanisphereTypeEnum.PicBuildPlane)) {
				this.kDSplitPane1.setDividerLocation(550);
				this.kDScrollPane1.setVisible(true);
			} else {
				this.kDSplitPane1.setDividerLocation(this.kDSplitPane1.getWidth());
				this.kDScrollPane1.setVisible(false);
			}
			SHEHelper.showPlanisphereTable(this.tblMain, phInfo, setting);
		} else {
			if (node.getUserObject() instanceof BuildingUnitInfo
    				|| node.getUserObject() instanceof BuildingInfo
    				|| node.getUserObject() instanceof SubareaInfo
    				){
				pnlMain.remove( scrollPanel); // add by wanping
    			pnlMain.add(kDSplitPane1, "right"); // add by wanping
    			pnlMain.setDividerLocation(200);
    			DefaultKingdeeTreeNode newNode = (DefaultKingdeeTreeNode) node.clone();
    			CommerceHelper.cloneTree(newNode, node);
    			newNode.setParent((DefaultKingdeeTreeNode) node.getParent()); // 调用接口时会查询其父节点的对象
    			// 　
    			CommerceHelper.removePlanisphereNode(newNode);
    			SHEHelper.fillRoomTableByNode(this.tblMain, newNode, MoneySysTypeEnum.SalehouseSys, null, setting);
    		}
    		if(node.getUserObject() instanceof SellProjectInfo){ // add by wanping 选择项目时，显示鸟瞰图
    			pnlMain.remove(kDSplitPane1);
    			try {
    				BirdEyeShowUI birdui = new BirdEyeShowUI();
        			birdui.showBirdEyeLable(BirdEyePanel2, node,pnlMain,kDSplitPane1);
	    			scrollPanel.setViewportView(BirdEyePanel2);//add by wanping
	    			pnlMain.add(scrollPanel, "right");
	    			pnlMain.setDividerLocation(200);
					Component[] com = BirdEyePanel2.getComponents();
			    	for(int i=0;i<com.length;i++){
			    		if(com[i] instanceof KDLabel){
			    			KDLabel lab = (KDLabel)com[i];
			    			MouseMotionListener[] listeners =lab.getMouseMotionListeners();
			    			for(int j=0;j<listeners.length;j++){
			    				lab.removeMouseMotionListener(listeners[j]);
			    			}
			    		}
			    	}
				} catch (Exception e1) {
					e1.printStackTrace();
				} //add by wanping
    		}
		}

		// logger.info("构建房间图---- "+ System.currentTimeMillis());
		setSellStateCountByColor();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}

	public void actionChangeRoom_actionPerformed(ActionEvent e) throws Exception {
//		super.actionChangeRoom_actionPerformed(e);
		RoomInfo room = this.getSelectRoom();
		if(room==null) return;
		
		if (room.getSellState() == null || (!room.getSellState().equals(RoomSellStateEnum.PrePurchase) && !room.getSellState().equals(RoomSellStateEnum.Purchase))) {
			MsgBox.showWarning("只有预订或认购的房间才能换房！");
			return;
		}

		RoomInfo tempRoom = RoomFactory.getRemoteInstance().getRoomInfo("select lastPurchase.purchaseState where id='" + room.getId().toString() + "'");
		if (tempRoom == null || tempRoom.getLastPurchase() == null) {
			MsgBox.showInfo("该房间对应的认购单状态非‘认购审批’或‘预订复核’，不能换房！");
			return;
		} else {
			PurchaseStateEnum purState = tempRoom.getLastPurchase().getPurchaseState();
			if (purState == null || (!purState.equals(PurchaseStateEnum.PrePurchaseCheck) && !purState.equals(PurchaseStateEnum.PurchaseAudit))) {
				MsgBox.showInfo("该房间对应的认购单状态非‘认购审批’或‘预订复核’，不能换房！");
				return;
			}
		}

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("oldRoom.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("auditor", null));
		ChangeRoomCollection changes = ChangeRoomFactory.getRemoteInstance().getChangeRoomCollection(view);
		if (changes.size() > 1) {
			MsgBox.showWarning("存在多个提交状态的换房单，请与管理员联系！");
			return;
		} else {
			UIContext uiContext = new UIContext(this);
			String oprtStateStr = OprtState.ADDNEW;
			if (changes.size() == 1) {// 修改
				uiContext.put("ID", changes.get(0).getId().toString());
				oprtStateStr = OprtState.EDIT;
			} else { // 新增
				uiContext.put("RoomInfo", room);
			}
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ChangeRoomEditUI.class.getName(), uiContext, null, oprtStateStr);
			uiWindow.show();
		}
		this.refresh(null);
	}

	public void actionKeepDown_actionPerformed(ActionEvent e) throws Exception {
//		super.actionKeepDown_actionPerformed(e);
		RoomInfo room = this.getSelectRoom();
		if(room==null) return;

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.ChangeRoomBlankOut, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.ManualBlankOut, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.NoPayBlankOut, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.QuitRoomBlankOut, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.AdjustBlankOut, CompareType.NOTEQUALS));

		if (PurchaseFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo("已经存在认购单,请先作废!");
			return;
		}

		RoomSellStateEnum sellState = room.getSellState();
		if (sellState.equals(RoomSellStateEnum.OnShow)) {
			UIContext uiContext = new UIContext(this);
			uiContext.put("room", room);
			// 创建UI对象并显示
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomKeepDownBillEditUI.class.getName(), uiContext, null, "ADDNEW");
			uiWindow.show();
		} else if (sellState.equals(RoomSellStateEnum.KeepDown)) {
			int result = MsgBox.showConfirm2("是否确定取消保留!");
			if (result != MsgBox.YES) {
				SysUtil.abort();
			}
			RoomKeepDownBillFactory.getRemoteInstance().cancelKeepDown(room.getId().toString(),null);
		}
		this.refresh(null);
	}

	public void actionQuitRoom_actionPerformed(ActionEvent e) throws Exception {
//		super.actionQuitRoom_actionPerformed(e);
		RoomInfo room = this.getSelectRoom();
		if(room==null) return;
		SellProjectInfo sellProject = room.getBuilding().getSellProject();
		if (room == null) {
			MsgBox.showWarning("请选择房间！");
			this.abort();
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("auditor", null));
		QuitRoomCollection quits = QuitRoomFactory.getRemoteInstance().getQuitRoomCollection(view);
		if (quits.size() > 1) {
			MsgBox.showInfo("多次退房,系统错误!");
			return;
		} else if (quits.size() == 1) {
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", quits.get(0).getId().toString());
			uiContext.put("sellProject", sellProject);
			// 创建UI对象并显示
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(QuitRoomEditUI.class.getName(), uiContext, null, "EIDT");
			uiWindow.show();
		} else {
			UIContext uiContext = new UIContext(this);
			uiContext.put("roomId", room.getId().toString());
			uiContext.put("sellProject", sellProject);
			// 创建UI对象并显示
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(QuitRoomEditUI.class.getName(), uiContext, null, "ADDNEW");
			uiWindow.show();
		}
		// this.refresh();
	}

	public void actionReserve_actionPerformed(ActionEvent e) throws Exception {
		super.actionReserve_actionPerformed(e);
	}

	public void actionPurchase_actionPerformed(ActionEvent e) throws Exception {
		RoomInfo room = this.getSelectRoom();
		if (room == null) {
			return;
		}
//		SHEManageHelper.toPurchaseManage(room.getId(), this, null);
	}
	public void actionPrePurchase_actionPerformed(ActionEvent e) throws Exception {
		RoomInfo room = this.getSelectRoom();
		if (room == null) {
			return;
		}
//		SHEManageHelper.toPrePurchaseManage(room.getId(), this, null);
	}
	public void actionSignContract_actionPerformed(ActionEvent e) throws Exception {
		RoomInfo room = this.getSelectRoom();
		if (room == null) {
			return;
		}
//		SHEManageHelper.toSignManage(room.getId(), this, null);
	}

	private void showEditSignContract(RoomSignContractInfo sign) throws UIException {
		BuildingInfo building = sign.getRoom().getBuilding();
		UIContext uiContext = new UIContext(this);
		uiContext.put("ID", sign.getId().toString());
		uiContext.put("building", building);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomSignContractEditUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}

	private void showAddSignContract(RoomInfo room) throws UIException {
		UIContext uiContext = new UIContext(this);
		uiContext.put("room", room);
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node.getUserObject() instanceof Integer) {
			Integer unit = (Integer) node.getUserObject();
			uiContext.put("unit", unit);
			BuildingInfo building = (BuildingInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
			uiContext.put("building", building);
		} else if (node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo building = (BuildingInfo) node.getUserObject();
			if (!building.getCodingType().equals(CodingTypeEnum.UnitFloorNum)) {
				uiContext.put("building", building);
				uiContext.put("unit", new Integer(0));
			}
		}else{
			BuildingInfo building = room.getBuilding();
			uiContext.put("building", building);
		}
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id",room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("sincerityState",SincerityPurchaseStateEnum.ARRANGENUM_VALUE));
		view.setFilter(filter);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("customer.*");
		sels.add("room.*");
		sels.add("room.building.*");
		sels.add("room.roomModel.*");
		sels.add("room.building.sellProject.name");
		sels.add("sellProject.*");
		view.setSelector(sels);
		SincerityPurchaseCollection sinColl =null;
		try {
			sinColl = SincerityPurchaseFactory
					.getRemoteInstance().getSincerityPurchaseCollection(view);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if(sinColl!=null && sinColl.size()>0){
			uiContext.put("sincerity", sinColl.get(0));
		}
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomSignContractEditUI.class.getName(), uiContext, null, "ADDNEW");
		uiWindow.show();
	}

	public void actionReceiveBill_actionPerformed(ActionEvent e) throws Exception {
		RoomInfo room = this.getSelectRoom();
		if (room == null) {
			MsgBox.showWarning("请先选择房间进行收款操作！");
			return;
		}

		// 如果是附属房产,不能单独出售，如果要收款，需要针对主房产进行收款
		if (HousePropertyEnum.Attachment.equals(room.getHouseProperty())) {
			MsgBox.showInfo("附属房产不能单独收款，请选择其主房产进行收款！");
			this.abort();
		}

		
		RoomSellStateEnum sellState = room.getSellState();
		if(sellState==null)	return;
		String purWhereSql = "where room.id = '"+room.getId()+"' " +
						"and (purchaseState ='"+PurchaseStateEnum.PREPURCHASECHECK_VALUE+"' or purchaseState ='"+PurchaseStateEnum.PURCHASEAPPLY_VALUE+"' " +" or purchaseState ='"+PurchaseStateEnum.PURCHASECHANGE_VALUE+"' " +
								"or purchaseState='"+PurchaseStateEnum.PURCHASEAUDITING_VALUE+"' or purchaseState ='"+PurchaseStateEnum.PURCHASEAUDIT_VALUE+"')";
		if((PurchaseFactory.getRemoteInstance().exists(purWhereSql))) {	//针对认购单的收款
			//房间存在 预订复合、认购申请、认购审批中、认购审批的认购单时可以收款，前两者只能收预订金
			PurchaseCollection purColl = PurchaseFactory.getRemoteInstance().getPurchaseCollection(
					"select id,name,number,purchaseState "+ purWhereSql +" order by createTime desc");
			UIContext uiContext = new UIContext(this);
			uiContext.put(SHEReceivingBillEditUI.KEY_SELL_PROJECT, room.getBuilding().getSellProject());
			uiContext.put(SHEReceivingBillEditUI.KEY_REV_BILL_TYPE, RevBillTypeEnum.gathering);
			//uiContext.put(SHEReceivingBillEditUI.KEY_IS_LOCK_BILL_TYPE, new Boolean(true));
			uiContext.put(SHEReceivingBillEditUI.KEY_REV_BIZ_TYPE, RevBizTypeEnum.purchase);
			uiContext.put(SHEReceivingBillEditUI.KEY_SELL_PURCHASE, purColl.get(0));
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
					.create(SHEReceivingBillEditUI.class.getName(), uiContext, null,"ADDNEW");
			uiWindow.show();		
			
			this.refresh(null);
		}else{	//针对诚意认购单的收款
			SincerityPurchaseCollection sinColl = SincerityPurchaseFactory.getRemoteInstance()
					.getSincerityPurchaseCollection("select id,name,number,isReceiveEnterAccount,sellProject.name,sellProject.number " +
							"where room.id = '"+room.getId()+"' and isReceiveEnterAccount=1 and sincerityState ='"+SincerityPurchaseStateEnum.ARRANGENUM_VALUE+"' order by createTime desc");
			if(sinColl.size()>0) {
				UIContext uiContext = new UIContext(this);
				uiContext.put(SHEReceivingBillEditUI.KEY_SELL_PROJECT, sinColl.get(0).getSellProject());
				uiContext.put(SHEReceivingBillEditUI.KEY_REV_BILL_TYPE, RevBillTypeEnum.gathering);
				uiContext.put(SHEReceivingBillEditUI.KEY_IS_LOCK_BILL_TYPE, new Boolean(true));	
				uiContext.put(SHEReceivingBillEditUI.KEY_REV_BIZ_TYPE, RevBizTypeEnum.sincerity);
				uiContext.put(SHEReceivingBillEditUI.KEY_SELL_SINCERITY, sinColl.get(0));
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
						.create(SHEReceivingBillEditUI.class.getName(), uiContext, null,"ADDNEW");
				uiWindow.show();		
				
				this.refresh(null);
			}
		}
		
		
	}

	public void actionChangeName_actionPerformed(ActionEvent e) throws Exception {
//		super.actionChangeName_actionPerformed(e);
		RoomInfo room = this.getSelectRoom();
		if (room == null) {
			return;
		}
		PurchaseInfo purchase = room.getLastPurchase();
		if (purchase != null) {
			UIContext uiContext = new UIContext(this);
			uiContext.put("purchaseID", purchase.getId().toString());
			// 创建UI对象并显示
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(PurchaseChangeCustomerEditUI.class.getName(), uiContext, null, "ADDNEW");
			uiWindow.show();
		}
	}

	public void actionPurchaseChange_actionPerformed(ActionEvent e) throws Exception {
//		super.actionPurchaseChange_actionPerformed(e);
		RoomInfo room = this.getSelectRoom();
		if (room == null) {
			return;
		}
		PurchaseInfo purchase = room.getLastPurchase();
		if (purchase != null) {
			//应该与序时薄上做一样的控制 1、如果有未审批 的认购变更，那么不能新增新的变更单。2、如果已经生成公积金和按揭分录，就不能进行变更
			int unAudittedNumber = 0;
			String purchaseId = purchase.getId().toString();
			//添加款项是否收齐额判断 by renliang 2010-11-12
			/*if(!checkPurchaseFundComp(purchaseId)){
				FDCMsgBox.showWarning(this,"所有款项已经收齐，不能变更!");
				SysUtil.abort();
			}*/
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql("select count(*) from t_she_purchasechange where  fpurchaseid = ? and fstate in('1SAVED','2SUBMITTED')");
			builder.addParam(purchaseId);
			RowSet rs = builder.executeQuery();
			while(rs.next()){
				unAudittedNumber = rs.getInt(1);
			}
			
			if(unAudittedNumber > 0){
				FDCMsgBox.showError("此房间有变更单处于变更中，不能再次进行变更！");
				abort();
			}
			
			RoomLoanCollection roomLoan=RoomLoanFactory.getRemoteInstance()	.getRoomLoanCollection("where purchase = '"+purchaseId+"' and aFMortgagedState = 1");
			if(roomLoan!=null && roomLoan.size()>0){
				FDCMsgBox.showWarning("此房间所对应的认购单已经生成公积金/按揭单，不能再进行变更！");
				SysUtil.abort();
			}
			
			
			UIContext uiContext = new UIContext(this);
			uiContext.put("src", "SELLCONTROL");
			uiContext.put("purchaseID", purchase.getId().toString());
			// 创建UI对象并显示
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(PurchaseChangeBillEditUI.class.getName(), uiContext, null, "ADDNEW");
			uiWindow.show();
		}
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		RoomInfo room = this.getSelectRoom();
		if (room != null) {
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", room.getId().toString());
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomSourceEditUI.class.getName(), uiContext, null, "VIEW");
			uiWindow.show();
		}
	}

	protected String getLongNumberFieldName() {
		return "number";
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		if (confirmRemove()) {
			// prepareRemove(null).callHandler();
			Remove();
			refresh(e);
		}
	}

	/**
	 * 房间回收
	 * */
	public void actionReclaimRoom_actionPerformed(ActionEvent e) throws Exception {
		super.actionReclaimRoom_actionPerformed(e);

		checkSelected();
		RoomInfo room = this.getSelectRoom();

		room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(room.getId().toString()));
		RoomSellStateEnum sellState = room.getSellState();
		if (!RoomSellStateEnum.PrePurchase.equals(sellState) && !RoomSellStateEnum.Purchase.equals(sellState) && !RoomSellStateEnum.Sign.equals(sellState)) {
			MsgBox.showInfo(this, "只有预定，认购或者签约状态的房间才需要使用此功能！");
			return;
		}

		// ---- 控制，存在这些单据时不允许回收房间 zhicheng_jin 090616
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));

		StringBuffer errMsg = new StringBuffer();

		if (RoomPropertyBookFactory.getRemoteInstance().exists(filter)) {
			errMsg.append("该房间存在产权办理单，不能回收！");
		}

		if (RoomJoinFactory.getRemoteInstance().exists(filter)) {
			if (!StringUtils.isEmpty(errMsg.toString()))
				errMsg.append("\r\n");
			errMsg.append("该房间存在入伙单，不能回收！");
		}

		if (RoomLoanFactory.getRemoteInstance().exists(filter)) {
			if (!StringUtils.isEmpty(errMsg.toString()))
				errMsg.append("\r\n");
			errMsg.append("该房间存在按揭办理单，不能回收！");
		}

		if (RoomAreaCompensateFactory.getRemoteInstance().exists(filter)) {
			if (!StringUtils.isEmpty(errMsg.toString()))
				errMsg.append("\r\n");
			errMsg.append("该房间存在面积补差单，不能回收！");
		}

		if (!StringUtils.isEmpty(errMsg.toString())) {
			MsgBox.showInfo(this, errMsg.toString());
			return;
		}
		// MsgBox.showInfo(this, "执行该操作前,请先清除按揭,产权办理,入伙的记录!");
		// --------------

		if (MsgBox.isYes(MsgBox.showConfirm2(this, "此操作将删除该房间的当前生效认购单及认购单对应的收款单，签约单，商机跟进记录，同时房间变为待售状态，是否继续？"))) {
			RoomFactory.getRemoteInstance().reclaimRoom(room.getId().toString());
			MsgBox.showInfo(this, "房间清理成功！");
		//   2010-04-08  lixin  标示回收房间后的操作
			IRoomRecoverHistory historyService=RoomRecoverHistoryFactory.getRemoteInstance();
			RoomRecoverHistoryInfo roomRecoverHistory=new RoomRecoverHistoryInfo();
			roomRecoverHistory.setId(BOSUuid.create(roomRecoverHistory.getBOSType()));
			roomRecoverHistory.setCaoZuoRen(SysContext.getSysContext().getCurrentUserInfo());
			roomRecoverHistory.setOperatingDate(new Timestamp((new Date()).getTime()));
			roomRecoverHistory.setRoom(room);
			historyService.addnew(roomRecoverHistory);
			if (room != null) {
				if (detailUI == null) {
					UIContext uiContext = new UIContext(ui);
					uiContext.put(UIContext.ID, room.getId().toString());
					uiContext.put("isRecover", "Y");
					detailUI = (CoreUIObject) UIFactoryHelper.initUIObject(VirtualRoomFullInfoUI.class.getName(), uiContext, null, "VIEW");
					//销控的总览，入伙，按揭，产权等页面都按照这个页面来开发RoomFullInfoUI
//					detailUI = (CoreUIObject) UIFactoryHelper.initUIObject(RoomFullInfoUI.class.getName(), uiContext, null, "VIEW");
					// detailUI.setDataObject(room);
					sclPanel.setViewportView(detailUI);
					sclPanel.setKeyBoardControl(true);
					// return;
				} else {
//					detailUI.getUIContext().put("isRecover", "Y");
					detailUI.getUIContext().put(UIContext.ID, room.getId().toString());
					detailUI.onLoad();
				}
				// detailUI.setDataObject(detailUI.onLoad());
				// detailUI.loadFields();
			}		
			this.refresh(null);
		}
	}

	public void actionQuitOrder_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(QuitOrderEditUI.class.getName(), uiContext, null, "ADDNEW");
		uiWindow.show();
		this.refresh(null);
	}

	public void actionRefundment_actionPerformed(ActionEvent e) throws Exception {
		RoomInfo room = this.getSelectRoom();
		if (room == null) {
			MsgBox.showWarning("请先选择房间进行退款操作！");
			return;
		}
		

		PurchaseCollection lastPurColl = PurchaseFactory.getRemoteInstance().getPurchaseCollection(
				"select id,name,number,purchaseState where room.id = '"+room.getId()+"' order by createTime desc");
		if(lastPurColl.size()==0) return;
		
		UIContext uiContext = new UIContext(this);
		uiContext.put(SHEReceivingBillEditUI.KEY_SELL_PROJECT, room.getBuilding().getSellProject());
		uiContext.put(SHEReceivingBillEditUI.KEY_REV_BILL_TYPE, RevBillTypeEnum.refundment);
		uiContext.put(SHEReceivingBillEditUI.KEY_IS_LOCK_BILL_TYPE, new Boolean(true));
		uiContext.put(SHEReceivingBillEditUI.KEY_REV_BIZ_TYPE, RevBizTypeEnum.purchase);
		uiContext.put(SHEReceivingBillEditUI.KEY_SELL_PURCHASE, lastPurColl.get(0));
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(SHEReceivingBillEditUI.class.getName(), uiContext, null,"ADDNEW");
		uiWindow.show();	
		
		
		this.refresh(null);
	}

	public void actionBillAdjust_actionPerformed(ActionEvent e) throws Exception {
		RoomInfo room = this.getSelectRoom();

		UIContext uiContext = new UIContext(this);
		if (room.getLastPurchase() == null) {
			MsgBox.showWarning("房间的当前认购单为空！");
			return;
		}
		uiContext.put("purchaseId", room.getLastPurchase().getId().toString());
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(BillAdjustEditUI.class.getName(), uiContext, null, "ADDNEW");
		uiWindow.show();
		this.refresh(null);
	}

	public void actionZhuanKuan_actionPerformed(ActionEvent e) throws Exception {

		RoomInfo room = this.getSelectRoom();
		if (room == null) {
			MsgBox.showWarning("请先选择房间再进行转款操作！");
			return;
		}

		UIContext uiContext = new UIContext(this);
		if (room.getLastPurchase() == null) {
			// MsgBox.showWarning("房间的当前认购单为空！");
			// return;
		}
		uiContext.put("purchaseId", room.getLastPurchase() == null ? null : room.getLastPurchase().getId().toString());
		uiContext.put("room", room);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ZhuanKuanEditUI.class.getName(), uiContext, null, "ADDNEW");
		uiWindow.show();
		this.refresh(null);
	}


	public void actionShowRoomDetail_actionPerformed(ActionEvent e)
			throws Exception {	
		super.actionShowRoomDetail_actionPerformed(e);		


	}
	
	
	private boolean checkPurchaseFundComp(String purchaseId){
		
		boolean result = true;
		try{
			FDCSQLBuilder builder = new FDCSQLBuilder();
			builder.appendSql(" select ");		
			
			builder.appendSql(" sum(case when FisEarnestInHouseAmount=1   ");
			builder.appendSql("          and (moneyDefine.FMoneyType='FisrtAmount' or moneyDefine.FMoneyType='HouseAmount'   ");
			builder.appendSql("               or moneyDefine.FMoneyType='PurchaseAmount') then payListEntry.FAppAmount        ");
			builder.appendSql("          when FisEarnestInHouseAmount=0                                                      ");
			builder.appendSql("          and (moneyDefine.FMoneyType='FisrtAmount' or moneyDefine.FMoneyType='HouseAmount')  ");
			builder.appendSql("                                                           then payListEntry.FAppAmount        ");
			builder.appendSql("          else 0 end) as apAmount,		                                                     ");
			builder.appendSql(" sum(case when FisEarnestInHouseAmount=1                                                      ");
			builder.appendSql("          and (moneyDefine.FMoneyType='FisrtAmount' or moneyDefine.FMoneyType='HouseAmount'   ");
			builder.appendSql("               or moneyDefine.FMoneyType='PurchaseAmount') then payListEntry.FActrevAmount    ");
			builder.appendSql("          when FisEarnestInHouseAmount=0                                                      ");
			builder.appendSql("          and (moneyDefine.FMoneyType='FisrtAmount' or moneyDefine.FMoneyType='HouseAmount')  ");
			builder.appendSql("                                                           then payListEntry.FActrevAmount    ");
			builder.appendSql("          else 0 end) as actPayAmount                                                         ");
			builder.appendSql(" FROM T_SHE_Purchase   PURCHASE ");
			builder.appendSql(" left join T_SHE_PurchasePayListEntry   payListEntry  ");
			builder.appendSql(" on payListEntry.FHeadID=PURCHASE.FID  ");
			builder.appendSql(" left join T_SHE_MoneyDefine moneyDefine on  ");
			builder.appendSql(" moneyDefine.fid=payListEntry.FMoneyDefineID   ");
			builder.appendSql(" LEFT OUTER JOIN T_SHE_Room ROOM ");
			builder.appendSql(" ON PURCHASE.FRoomID = ROOM.FID ");
			builder.appendSql(" INNER JOIN T_SHE_Building BUILDING ");
			builder.appendSql(" ON ROOM.FBuildingID = BUILDING.FID ");
			builder.appendSql(" LEFT OUTER JOIN T_SHE_BuildingUnit BUILDUNIT ");
			builder.appendSql(" ON ROOM.FBuildUnitID = BUILDUNIT.FID ");
			builder.appendSql(" INNER JOIN T_SHE_SellProject SELLPROJECT ");
			builder.appendSql(" ON BUILDING.FSellProjectID = SELLPROJECT.FID ");
			builder.appendSql(" LEFT OUTER JOIN T_SHE_Subarea SUBAREA ");
			builder.appendSql(" ON BUILDING.FSubareaID = SUBAREA.FID ");
			builder.appendSql(" where  1=1 ");
			builder.appendSql(" and PURCHASE.fid='"+purchaseId+"'");
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			if (node.getUserObject() instanceof BuildingUnitInfo) {
				BuildingUnitInfo buildUnit = (BuildingUnitInfo) node.getUserObject();
				builder.appendSql(" and buildUnit.fid='" + buildUnit.getId() + "'  ");
			} else if (node.getUserObject() instanceof BuildingInfo) {
				BuildingInfo building = (BuildingInfo) node.getUserObject();
				builder.appendSql(" and building.fid='" + building.getId() + "'  ");
			} else if (node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo sellProInfo = (SellProjectInfo) node.getUserObject();
				builder.appendSql(" and sellProject.fid='" + sellProInfo.getId() + "'  ");
			} else if (node.getUserObject() instanceof SubareaInfo) {
				SubareaInfo subAreaInfo = (SubareaInfo) node.getUserObject();
				builder.appendSql(" and subarea.fid='" + subAreaInfo.getId() + "'  ");
			} 
		
			IRowSet termQuitSellSet = builder.executeQuery();
			if (termQuitSellSet.next()) {
				BigDecimal apAmount = FDCHelper.toBigDecimal(termQuitSellSet.getString("apAmount"));
				BigDecimal payAmount = FDCHelper.toBigDecimal(termQuitSellSet.getString("actPayAmount"));		
				if (payAmount.compareTo(apAmount) >= 0&&payAmount.compareTo(FDCHelper.ZERO)>0) {
					//FDCMsgBox.showWarning(this,"所有款项已经收齐，不能变更!");
					//SysUtil.abort();
					result = false;
				}
			}
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
		
		return result;
		
	}
	
	/**
	 * 添加模拟销售愿意
	 * by renliang at 2011-3-9
	 */
	public void actionSimulate_actionPerformed(ActionEvent e) throws Exception {
		super.actionSimulate_actionPerformed(e);
		RoomInfo room = this.getSelectRoom();
		if (room == null) {
			return;
		}if (room.getSellState().equals(RoomSellStateEnum.KeepDown)) {
			MsgBox.showInfo("房间已经被保留!");
			return;
		}
		if (room.getSellState().equals(RoomSellStateEnum.Init)) {
			MsgBox.showInfo("房间没有开盘!");
			return;
		}
		if (room.getSellState().equals(RoomSellStateEnum.PrePurchase)) {
			MsgBox.showInfo("房间已经被预定!");
			return;
		}
		if (room.getSellState().equals(RoomSellStateEnum.Sign)) {
			MsgBox.showInfo("房间已经签约!");
			return;
		}
		if (room.getSellState().equals(RoomSellStateEnum.Purchase)) {
			MsgBox.showInfo("房间已经被认购!");
			return;
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put("roomId", room.getId().toString());
		uiContext.put("room", room);
		/**
		 * 在SimulantSellUI中已经有取roomId的方法，并且有初始化房间信息的方法
		 */
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SimulantSellUI.class.getName(), uiContext,null,null);
		uiWindow.show();
	
	}
}