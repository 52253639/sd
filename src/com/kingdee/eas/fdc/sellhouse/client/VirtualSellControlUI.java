/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionListener;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectListener;
import com.kingdee.bos.ctrl.kdf.util.style.LineStyle;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.Position;
import com.kingdee.bos.ctrl.swing.KDLabel;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FunctionSetting;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.PlanisphereElementEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PlanisphereInfo;
import com.kingdee.eas.fdc.sellhouse.PlanisphereTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractCollection;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.sellhouse.client.AbstractRoomIntentListUI.AcionSignContract;
import com.kingdee.eas.fdc.sellhouse.client.AbstractRoomIntentListUI.ActionBuyingRoomPlan;
import com.kingdee.eas.fdc.sellhouse.client.AbstractRoomIntentListUI.ActionKeepDown;
import com.kingdee.eas.fdc.sellhouse.client.AbstractRoomIntentListUI.ActionPrePurchase;
import com.kingdee.eas.fdc.sellhouse.client.AbstractRoomIntentListUI.ActionPurchase;
import com.kingdee.eas.fdc.sellhouse.client.AbstractRoomIntentListUI.ActionSinPurchase;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class VirtualSellControlUI extends AbstractVirtualSellControlUI {
	private static final Logger logger = CoreUIObject.getLogger(VirtualSellControlUI.class);
	CoreUIObject detailUI = null;

	SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

	final RoomDisplaySetting setting = new RoomDisplaySetting();

	Map sellProAndBuildingMap = new HashMap();
	SHE2ImagePanel BirdEyePanel2; //add by wanping
	KDScrollPane scrollPanel; //add by wanping
	/**
	 * output class constructor
	 */
	public VirtualSellControlUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		initControl();
		super.onLoad();
		if (!saleOrg.isIsBizUnit()) {			
		}
		this.tblMain.removeColumns();
		VirtualDisplaySetViewUI.insertUIToScrollPanel(this.kDScrollPane1);
		BirdEyePanel2 = new SHE2ImagePanel(); //add by wanping
		scrollPanel = new KDScrollPane(); //add by wanping
		BirdEyePanel2.setLayout(null); //add by wanping
	}

	public void onShow() throws Exception {
		super.onShow();
		
	}

	private DefaultKingdeeTreeNode findFirstNode(DefaultKingdeeTreeNode node) {
		if (node.getUserObject() instanceof BuildingInfo) {
			BuildingInfo buildingInfo = (BuildingInfo) node.getUserObject();
			if (!buildingInfo.getCodingType().equals(CodingTypeEnum.UnitFloorNum)) {
				return node;
			}
		} else if (node.getUserObject() instanceof Integer) {
			return node;
		}

		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultKingdeeTreeNode findNode = findFirstNode((DefaultKingdeeTreeNode) node.getChildAt(i));
			if (findNode != null) {
				return findNode;
			}

		}
		return null;
	}

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
//		this.menuBiz.setVisible(false);
		
	}

	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getButton() == 1) {
			RoomInfo room = this.getSelectRoom();
			if (room != null) {
				this.getUIContext().put("roomId", room.getId().toString());
				if (e.getClickCount() == 2) {
					if (room != null) {
						UIContext uiContext = new UIContext(this);
						uiContext.put("ID", room.getId().toString());
						IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(RoomSourceEditUI.class.getName(), uiContext, null, "VIEW");
						uiWindow.show();
					}

					reflashProOrBuildByPlanisphere();
				}
			}
		}
	}

	protected void tblMain_tableSelectChanged(KDTSelectEvent e) throws Exception {
		RoomInfo room = this.getSelectRoom(true);
		changeButtonState();
		if(room == null){
			return ;
		}
		RoomSellStateEnum state = room.getSellState();
		this.getUIContext().put("roomId", room.getId().toString());
		this.getUIContext().put("buildingId", room.getBuilding().getId().toString());
		this.getUIContext().put("sellProject", room.getBuilding().getSellProject());
		this.getUIContext().put("state", state);
		this.getUIContext().put("totalAmount", room.getStandardTotalAmount());
		this.getUIContext().put("pricingType", room.getCalcType()!=null?room.getCalcType().getAlias():"");
		if (room != null) {
			if (detailUI == null) {
				UIContext uiContext = new UIContext(ui);
				uiContext.put(UIContext.ID, room.getId().toString());
				uiContext.put("isVirtual",Boolean.TRUE);
				detailUI = (CoreUIObject) UIFactoryHelper.initUIObject(VirtualRoomFullInfoUI.class.getName(), uiContext, null, "VIEW");
				
				sclPanel.setViewportView(detailUI);
				sclPanel.setKeyBoardControl(true);
			} else {
				detailUI.getUIContext().put(UIContext.ID, room.getId().toString());
				detailUI.onLoad();
			}
			
		}

		reflashRoomInfoByPlanisphere();
//		toggleOperActionState(room);

	}
	
	private void changeButtonState() throws EASBizException, BOSException, UuidException {
		Map actionMap= (Map)getUIContext().get("actionMap"); 
		RoomInfo room = this.getSelectRoom(false);
		
		ActionBuyingRoomPlan actionBuyingRoomPlan = (ActionBuyingRoomPlan)actionMap.get("actionBuyingRoomPlan");
		ActionKeepDown actionKeepDown = (ActionKeepDown)actionMap.get("actionKeepDown"); 
		ActionSinPurchase actionSinPurchase = (ActionSinPurchase)actionMap.get("actionSinPurchase");
		ActionPrePurchase actionPrePurchase = (ActionPrePurchase)actionMap.get("actionPrePurchase");
		ActionPurchase actionPurchase = (ActionPurchase)actionMap.get("actionPurchase");
		AcionSignContract acionSignContract = (AcionSignContract)actionMap.get("acionSignContract");
		if (room == null)
			return;
		RoomSellStateEnum state = room.getSellState();
		if (state == null)
			return;
		
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
			actionSinPurchase.setEnabled(false);
			actionPrePurchase.setEnabled(false);
			actionPurchase.setEnabled(false);
			acionSignContract.setEnabled(false);
		}else{
			if (state.equals(RoomSellStateEnum.OnShow) || state.equals(RoomSellStateEnum.SincerPurchase)) {
				actionBuyingRoomPlan.setEnabled(true);
				actionKeepDown.setEnabled(true);
//				actionKeepDown.setVisible(true);
				actionSinPurchase.setEnabled(true);
				actionPrePurchase.setEnabled(true);
				actionPurchase.setEnabled(true);
				acionSignContract.setEnabled(true);
			}else{
				actionBuyingRoomPlan.setEnabled(false);
				actionKeepDown.setEnabled(false);
				actionSinPurchase.setEnabled(false);
				actionPrePurchase.setVisible(false);
				actionPrePurchase.setEnabled(false);
				actionPurchase.setEnabled(false);
				acionSignContract.setEnabled(false);
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
	}

	private void toggleOperActionState(RoomInfo room) throws EASBizException, BOSException {
		if(room == null) {
			actionSimulate.setEnabled(false);
			return;}
		boolean flag = isCanBeOperByColor(room);
		boolean flagS =  isImmediacySign(room);
		actionSimulate.setEnabled(true);
//		actionPurchase.setEnabled(flag);
		actionSign.setEnabled(flag && flagS);
	}

	/**
	 * 如果是点击的是平面图的 房间 则选中房间轮廓
	 */
	private void reflashRoomInfoByPlanisphere() {
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

	public RoomInfo getSelectRoom() throws EASBizException, BOSException, UuidException {
		return getSelectRoom(true);
	}

	protected void setActionState() {
		// super.setActionState();
	}

	protected void initTree() throws Exception {
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		this.treeMain.setModel(FDCTreeHelper.getUnitTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	// 关闭所有的楼栋的子节点 , 并且把项目和楼点存到映射中去
	private void closeAllUnitLeaf(TreeNode treeNode) {
		int childrenCount = treeNode.getChildCount();
		while (childrenCount > 0) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeNode.getChildAt(childrenCount - 1);
			childrenCount--;
			if (node.isLeaf()) {
				if (node.getUserObject() instanceof Integer) {
					DefaultMutableTreeNode pnode = (DefaultMutableTreeNode) node.getParent();
					TreePath path = new TreePath(pnode.getPath());
					this.treeMain.collapsePath(path);
				}
			}

			if (node.getUserObject() != null) {
				if (node.getUserObject() instanceof SellProjectInfo || node.getUserObject() instanceof BuildingInfo) {
					FDCDataBaseInfo databaseInfo = (FDCDataBaseInfo) node.getUserObject();
					sellProAndBuildingMap.put(databaseInfo.getId().toString(), node);
				}
			}

			closeAllUnitLeaf(node);
		}
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		refresh(null);
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
	public DefaultKingdeeTreeNode getTreeRoot(){
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode)treeMain.getModel().getRoot();
		return root;
	}
	protected void refresh(ActionEvent e) throws BOSException {

		KDTSelectBlock block = this.tblMain.getSelectManager().get();
		int left = 1;
		int top = 0;
		if (block != null) {
			if (block.getLeft() != -1) {
				left = block.getLeft();
			}
			if (block.getTop() != -1) {
				top = block.getTop();
			}
		}
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		

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
				this.kDScrollPane1.setVisible(true);
				DefaultKingdeeTreeNode newNode = (DefaultKingdeeTreeNode) node.clone();
				CommerceHelper.cloneTree(newNode, node);
				newNode.setParent((DefaultKingdeeTreeNode) node.getParent()); // 调用接口时会查询其父节点的对象
																				// 　
				CommerceHelper.removePlanisphereNode(newNode);
				SHEHelper.fillVirtualRoomTableByNode(this.tblMain, newNode, MoneySysTypeEnum.SalehouseSys, null, setting,true);
			}
			if(node.getUserObject() instanceof SellProjectInfo){ // add by wanping 选择项目时，显示鸟瞰图
    			pnlMain.remove(scrollPanel);
    			try {
    				BirdEyeShowUI birdui = new BirdEyeShowUI();
        			birdui.showBirdEyeLable(BirdEyePanel2, node,pnlMain,kDSplitPane1);//add by wanping
					scrollPanel.setViewportView(BirdEyePanel2);
	    			pnlMain.add(scrollPanel, "right"); 
	    			this.pnlMain.setDividerLocation(200); 
					Component[] com = BirdEyePanel2.getComponents(); 
			    	for(int i=0;i<com.length;i++){//add by wanping
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

	
	

	public void actionSignContract_actionPerformed(ActionEvent e) throws Exception {
		RoomInfo room = this.getSelectRoom();
		if (room == null) {
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("isBlankOut", Boolean.FALSE));
		RoomSignContractCollection signs = RoomSignContractFactory.getRemoteInstance().getRoomSignContractCollection(view);
		RoomSignContractInfo sign = signs.get(0);
		if (sign == null) {
			showAddSignContract(room);
		} else {
			showEditSignContract(sign);
		}
	}

	private void showEditSignContract(RoomSignContractInfo sign) throws UIException {
		UIContext uiContext = new UIContext(this);
		uiContext.put("ID", sign.getId().toString());
//		uiContext.put("isVirtual", true);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(RoomSignContractEditUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}

	private void showAddSignContract(RoomInfo room) throws UIException {
		UIContext uiContext = new UIContext(this);
		uiContext.put("room", room);
//		uiContext.put("isVirtual", true);
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

		UIContext uiContext = new UIContext(this);
		uiContext.put("room", room);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(SHEReceiveBillEditUI.class.getName(), uiContext, null, "ADDNEW");
		uiWindow.show();
		this.refresh(null);
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

	public void actionSimulate_actionPerformed(ActionEvent e) throws Exception {
		super.actionSimulate_actionPerformed(e);
		RoomInfo room = this.getSelectRoom();
		if (room == null) {
			return;
		}
		
		checkRoomLastestState(room);
		UIContext uiContext = new UIContext(this);
		uiContext.put("isFromVirtual", "isFromVirtual");
		uiContext.put("roomId", room.getId().toString());
		uiContext.put("room", room);
		
		/**
		 * 在SimulantSellUI中已经有取roomId的方法，并且有初始化房间信息的方法
		 */
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SimulantSellUI.class.getName(), uiContext,null,null);
		uiWindow.show();
	}
	
	private void checkRoomLastestState(RoomInfo room) throws EASBizException, BOSException {
		if(!isCanBeOperByColor(room)){
			FDCMsgBox.showInfo("房间为已售。不允许进行再次交易！");
			String buildingId = room.getBuilding().getId().toString();
			int unitNum = room.getUnit();
			String key = buildingId + unitNum;
			Integer minRow = (Integer) tblMain.getRow(0).getUserObject();
			for (int j = room.getSerialNumber(); j <= room.getEndSerialNumber(); j++) {
				ICell cell = tblMain.getCell(tblMain.getRowCount() + minRow.intValue() - 1 - room.getFloor(), key + j);
				cell.getStyleAttributes().setFont(setting.getFont());
				cell.getStyleAttributes().setFontColor(setting.getFrontColor());
				cell.getStyleAttributes().setBackground(getRoomCellColor(room));
			}
			this.abort();
		}
		
	}

	public void actionPurchase_actionPerformed(ActionEvent e) throws Exception {
//		super.actionPurchase_actionPerformed(e);
		RoomInfo room = this.getSelectRoom();
		if (room == null) {
			return;
		}
		checkRoomLastestState(room);
		// --附属房产不能单独销售 zhicheng_jin 090324
		if (HousePropertyEnum.Attachment.equals(room.getHouseProperty())) {
			MsgBox.showInfo("附属房产不能单独销售!");
			return;
		}
		// --
		String oprt = null;
		PurchaseInfo purchase = room.getLastPurchase();
		UIContext uiContext = new UIContext(this);
		if (purchase != null) {
			uiContext.put("ID", purchase.getId().toString());
			oprt = "EDIT";
		} else {
			uiContext.put("room", room);
			uiContext.put("isPrePurchase", Boolean.FALSE);// 将认购与预定区分
			uiContext.put("sellProject", room.getBuilding().getSellProject());
			oprt = "ADDNEW";
		}
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(PurchaseEditUI.class.getName(), uiContext, null, oprt);
		uiWindow.show();
		//this.refresh(null);
	}
	
	public void actionSign_actionPerformed(ActionEvent e) throws Exception {
		super.actionSign_actionPerformed(e);
		RoomInfo room = this.getSelectRoom();
		if (room == null) {
			return;
		}
		checkRoomLastestState4Sign(room);
		if(room.getStandardTotalAmount()==null){
			MsgBox.showWarning(this, "房间尚未定价，请选择其它房间操作！");
			SysUtil.abort();
		}
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("isBlankOut", Boolean.FALSE));

		RoomSignContractCollection signs = RoomSignContractFactory.getRemoteInstance().getRoomSignContractCollection(view);
		RoomSignContractInfo sign = signs.get(0);
		if (sign == null) {
			showAddSignContract(room);
		} else {
			showEditSignContract(sign);
		}
	}

	private void checkRoomLastestState4Sign(RoomInfo room) throws EASBizException, BOSException {
		if(! isCanBeSign(room)){
			FDCMsgBox.showInfo("不允许直接签约！");
			this.abort();
		}
		
	}

	private boolean  isCanBeSign(RoomInfo room) throws EASBizException, BOSException{
		  if(! isCanBeOperByColor(room)){return false; }
		  if(! isImmediacySign(room)) {return false;}
		  return true;
	}
	
	public boolean isImmediacySign(RoomInfo room) throws EASBizException, BOSException, UuidException{
		boolean debug = false;
		RoomInfo room2 = null;
		room2 = SHEHelper.queryRoomInfo(room.getId().toString());
		if(room2 == null ) return debug;
		String id = room2.getBuilding().getSellProject().getId().toString();
		RoomDisplaySetting setting= new RoomDisplaySetting();
		HashMap functionSetMap = (HashMap) setting.getFunctionSetMap();
		FunctionSetting funcSet = (FunctionSetting)functionSetMap.get(id);
		debug = funcSet == null || !funcSet.getIsActGathering().booleanValue() &&  !funcSet.getIsSignGathering().booleanValue();
		/*			if(funcSet == null)	{
				debug = true ;
			}else{
				if(funcSet.getIsActGathering().booleanValue()
					|| funcSet.getIsSignGathering().booleanValue()){
					debug = false ;
				}else{
					debug = true ;
				}
			}*/
		return debug;
	}

	
	/** 
	 * 刷新room的颜色 因为颜色是根据房屋/销售状态决定的
	 * 因此可以根据颜色判断 是否可以操作 （认购，签约）
	 * 如果房间代售 但关联的认购单 则做已售处理
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private boolean isCanBeOperByColor(RoomInfo room) throws EASBizException, BOSException{
		Color color = getRoomCellColor( room);
		if(setting.getBaseRoomSetting().getOnShowColor().equals(color) ){
			return true;
		}
		return false;
	}

	//房间对应cell的最新color
	private Color getRoomCellColor( RoomInfo room ) throws EASBizException, BOSException {
		RoomInfo refreshedRoom = getNewestStateRoom(room);
		Color color = SHEHelper.getRoomColorByState(refreshedRoom, setting);
//		if (Color.WHITE .equals(color) && isAttachPurchase(room) ){
//			color = Color.RED;
//		}
		return color;
	}

	/**
	 * 房间是否有某几种状态之一的认购单
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private boolean isAttachPurchase(RoomInfo room) throws EASBizException, BOSException {
		if(room.getSellState().equals(RoomSellStateEnum.SincerPurchase)){
			return false;
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString(), CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.PURCHASEAPPLY_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.PURCHASEAUDITING_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.PURCHASEAUDIT_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.PREPURCHASEAPPLY_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.PREPURCHASECHECK_VALUE));
		//用set 保存purchasestate报错 不知为啥
		filter.setMaskString("#0 and (#1 or #2 or #3 or #4 or #5)");
		return PurchaseFactory.getRemoteInstance().exists(filter);
	}

	private RoomInfo getNewestStateRoom(RoomInfo room) throws EASBizException, BOSException {
		if(room == null || room.getId() == null){
			return null;
		}
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("sellState"));
		sic.add(new SelectorItemInfo("building.id"));
		sic.add(new SelectorItemInfo("building.name"));
		sic.add(new SelectorItemInfo("building.sellProject.id"));
		sic.add(new SelectorItemInfo("building.sellProject.name"));
		
		return RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(room.getId().toString()), sic);
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
}