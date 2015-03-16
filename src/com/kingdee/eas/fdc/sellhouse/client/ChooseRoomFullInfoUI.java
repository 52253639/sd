/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomCollection;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomFactory;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomStateEnum;
import com.kingdee.eas.fdc.sellhouse.IChooseRoom;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomCollection;
import com.kingdee.eas.fdc.sellhouse.QuitRoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class ChooseRoomFullInfoUI extends AbstractChooseRoomFullInfoUI
{
    private static final Logger logger = CoreUIObject.getLogger(ChooseRoomFullInfoUI.class);
    
    private PurchaseInfo purchase = null;

	private RoomSignContractInfo sign = null;

	private RoomJoinInfo join = null;

	private RoomAreaCompensateInfo areaCompensate = null;

	private RoomLoanInfo loan = null;

	private RoomPropertyBookInfo propertyBook = null;

	private QuitRoomCollection quits = new QuitRoomCollection();
	
	CoreUIObject detailUI = null;

	public void loadFields() {
		super.loadFields();
		RoomInfo room = (RoomInfo) this.editData;
		this.txtNumber.setText(room.getNumber());
		
		if(room.getRoomModel()!=null){
			this.f7RoomModel.setValue(room.getRoomModel().getName());
			if(room.getRoomModel().getRoomModelType()!=null){
				this.txtRoomModelType.setText(room.getRoomModel().getRoomModelType().getName());
			}
		}
		
		
		this.comboHouseProperty.setSelectedItem(room.getHouseProperty());
	
		SellTypeEnum sellType = (SellTypeEnum)room.getSellType();
		if(SellTypeEnum.LocaleSell.equals(sellType)){
			this.txtBuildingArea.setValue(room.getActualBuildingArea());
			this.txtRoomArea.setValue(room.getActualRoomArea());
		}
		else{
			this.txtBuildingArea.setValue(room.getBuildingArea());
			this.txtRoomArea.setValue(room.getRoomArea());
		}
		this.txtStandardBuildPrice.setValue(room.getBuildPrice());
		this.txtStandardRoomPrice.setValue(room.getRoomPrice());
		this.txtStandardTotalAmount.setValue(room.getStandardTotalAmount());
		
//		this.tabBizInfo.removeAll();
		
		quits = getQuitRoomsByRoomId(room.getId().toString());
		
		if (room.getSellState().equals(RoomSellStateEnum.PrePurchase)
				|| room.getSellState().equals(RoomSellStateEnum.Purchase)
				|| room.getSellState().equals(RoomSellStateEnum.Sign)) {
			purchase = room.getLastPurchase();
			sign = room.getLastSignContract();
			join = SHEHelper.getRoomJoinIn(room);
			areaCompensate = SHEHelper.getRoomAreaCompensation(room);
			loan = SHEHelper.getRoomLoan(room);
			propertyBook = SHEHelper.getRoomPropertyBook(room);
		}else{
			purchase = null;
			sign = null;
			join = null;
			areaCompensate = null;
			loan = null;
			propertyBook = null;
		}
	}
	

	/**
	 * 根据房间ID获得该房间的退房记录
	 * */
	private QuitRoomCollection getQuitRoomsByRoomId(String roomId) {
		QuitRoomCollection quitRooms = null;
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("number");
		view.getSelector().add("state");
		view.getSelector().add("quitDate");
		view.getSelector().add("description");
		view.getSelector().add("purchase.id");

		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("room.id", roomId));
		try {
			quitRooms = QuitRoomFactory.getRemoteInstance().getQuitRoomCollection(view);
		} catch (BOSException e) {
			this.handleException(e);
		}
		return quitRooms;
	}




	/**
	 * output class constructor
	 */
	public ChooseRoomFullInfoUI() throws Exception {
		super();
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sels = super.getSelectors();
		sels.add("id");
		sels.add("buildingArea");
		sels.add("roomArea");
		sels.add("actualBuildingArea");
		sels.add("actualRoomArea");
		
		sels.add("apportionArea");
		sels.add("balconyArea");
		sels.add("floorHeight");
		sels.add("direction.name");
		
		sels.add("sight.name");
		sels.add("roomModel.name");
		sels.add("roomModel.roomModelType.*");
		sels.add("buildingProperty.name");
		sels.add("houseProperty");
		
		sels.add("roomPrice");
		sels.add("buildPrice");
		sels.add("standardTotalAmount");
		sels.add("dealPrice");
		sels.add("dealTotalAmount");
		sels.add("isCalByRoomArea");
		sels.add("sellState");
		
		sels.add("lastPurchase.id");
		sels.add("lastPurchase.payType.id");
		
		sels.add("lastSignContract.id");
		sels.add("lastSignContract.signDate");
		
		sels.add("toPurchaseDate");
		sels.add("roomCompensateState");
		sels.add("roomLoanState");
		sels.add("roomACCFundState");
		sels.add("roomBookState");
		
		//显示预测房号和实测放号
		sels.add("number");
		sels.add("roomNo");
		
		return sels;
	}

	private void setTextFormat(KDFormattedTextField text){
		text.setRemoveingZeroInDispaly(false);
		text.setRemoveingZeroInEdit(false);
		text.setPrecision(2);
		text.setHorizontalAlignment(JTextField.RIGHT);
		text.setSupportedEmpty(true);
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCopy.setVisible(false);
		
		setTextFormat(txtBuildingArea);
		setTextFormat(txtRoomArea);
		setTextFormat(txtStandardBuildPrice);
		setTextFormat(txtStandardRoomPrice);
		setTextFormat(txtStandardTotalAmount);
//		UIContext uiContext = new UIContext(ui);
//		uiContext.put("dataMap", new HashMap());
//		CoreUIObject plUI = null;
//		plUI = (CoreUIObject) UIFactoryHelper.initUIObject(BuyingRoomPlanUI.class.getName(), uiContext,
//				null, "VIEW");
		initBuyRoomPlan();
	}
	
	private void initBuyRoomPlan() throws Exception{
		RoomInfo room = (RoomInfo) this.editData;
		
		// by tim_gao 增加加载界面
		
		UIContext uiContext = new UIContext(ui);
		
		if(null!=this.getUIContext().get("DownPaneUI")){
			Color background = (Color)this.getUIContext().get("background");
			if(null!=background){
				if(background.equals(ChooseRoomDisplaySetting.chooseRoomSelled)){//已售状态
					detailUI=null;
				}else if(background.equals(ChooseRoomDisplaySetting.chooseRoomUnconfirmed)){//未确认状态
					uiContext.put("room",room.getId().toString());
					detailUI = (CoreUIObject) UIFactoryHelper.initUIObject(ChooseRoomEditUI.class.getName(), uiContext, null, this.STATUS_ADDNEW);
//					detailUI.setPreferredSize(new Dimension(401,180));
				}else if(background.equals(ChooseRoomDisplaySetting.chooseRoomAffirm)){//确认状态
					EntityViewInfo view =new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					if(null==room.getId()){
						filter.getFilterItems().add(
								new FilterItemInfo("room.id", this.getUIContext().get(UIContext.ID).toString()));
					}else{
						filter.getFilterItems().add(
								new FilterItemInfo("room.id", room.getId().toString()));
					}
//					detailUI.setPreferredSize(new Dimension(401,180));
					filter.getFilterItems().add(
							new FilterItemInfo("chooseRoomStateEnum",ChooseRoomStateEnum.AFFIRM_VALUE));
					view.setFilter(filter);
					uiContext.put("room",room.getId().toString());
					IChooseRoom ichooseRoom = ChooseRoomFactory.getRemoteInstance();
					ChooseRoomCollection chooseRoomCol=ichooseRoom.getChooseRoomCollection(view);
					if(null!=chooseRoomCol&&chooseRoomCol.size()>0){//加校验,有有效的选房单，就打开编辑界面，没有就新增
					for(int i = 0 ; i<chooseRoomCol.size();i++){
//						if(ichooseRoom.isValid(chooseRoomCol.get(i).getId().toString())){
//							uiContext.put(UIContext.ID,chooseRoomCol.get(i).getId().toString());
//							detailUI = (CoreUIObject) UIFactoryHelper.initUIObject(ChooseRoomEditUI.class.getName(), uiContext, null, this.STATUS_EDIT);
//						}else{
//							detailUI = (CoreUIObject) UIFactoryHelper.initUIObject(ChooseRoomEditUI.class.getName(), uiContext, null, this.STATUS_ADDNEW);
//						}
						if(null!=chooseRoomCol.get(i).getId().toString()){
							uiContext.put(UIContext.ID,chooseRoomCol.get(i).getId().toString());
							detailUI = (CoreUIObject) UIFactoryHelper.initUIObject(ChooseRoomEditUI.class.getName(), uiContext, null, this.STATUS_VIEW);
							break;
						}else{
							detailUI = (CoreUIObject) UIFactoryHelper.initUIObject(ChooseRoomEditUI.class.getName(), uiContext, null, this.STATUS_ADDNEW);
							break;
						}
						
					}
					}else{
						
						detailUI = (CoreUIObject) UIFactoryHelper.initUIObject(ChooseRoomEditUI.class.getName(), uiContext, null, this.STATUS_ADDNEW);
					}
//					detailUI.setPreferredSize(new Dimension(401,180));
				}
			if (detailUI == null) {//已售状态的房间不加载界面，未确认状态的房间加载最新业务时间的单据，确认状态的房间
			
				}
				
				// detailUI.setDataObject(room);
				buyRoomPlanPane.setViewportView(detailUI);
				this.buyRoomPlanPane.setAutoscrolls(true);
				buyRoomPlanPane.setKeyBoardControl(true);
				// return;
			} else {
//				detailUI.getUIContext().put("isRecover", "Y");
//				detailUI.getUIContext().put(UIContext.ID, room.getId().toString());
				detailUI.onLoad();
//				detailUI.setPreferredSize(new Dimension(401,180));
			}
		}else{
		if (detailUI == null) {
			
			uiContext.put(UIContext.ID, this.getUIContext().get(UIContext.ID));
			uiContext.put("totalAmount", room.getStandardTotalAmount());
			
			detailUI = (CoreUIObject) UIFactoryHelper.initUIObject(BuyingRoomPlanUI.class.getName(), uiContext, null, this.STATUS_EDIT);
//			detailUI.setPreferredSize(new Dimension(401,180));
			// detailUI.setDataObject(room);
			buyRoomPlanPane.setViewportView(detailUI);
			buyRoomPlanPane.setKeyBoardControl(true);
			// return;
		} else {
//			detailUI.getUIContext().put("isRecover", "Y");
//			detailUI.getUIContext().put(UIContext.ID, room.getId().toString());
			detailUI.onLoad();
//			detailUI.setPreferredSize(new Dimension(401,180));
		}
		}
		this.buyRoomPlanPane.setAutoscrolls(true);
		this.buyRoomPlanPane.setWheelScrollingEnabled(true);
	}

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomFactory.getRemoteInstance();
	}
	
	/*
	class BizAboutRoom{
		private String bizType;
		private String number;
		private String state;
		private String occurDate;
		private String customer;
		private String salesman;
		private String amount;
		private String description;
		public String getAmount() {
			return amount;
		}
		public void setAmount(String amount) {
			this.amount = amount;
		}
		public String getBizType() {
			return bizType;
		}
		public void setBizType(String bizType) {
			this.bizType = bizType;
		}
		public String getCustomer() {
			return customer;
		}
		public void setCustomer(String customer) {
			this.customer = customer;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getNumber() {
			return number;
		}
		public void setNumber(String number) {
			this.number = number;
		}
		public String getOccurDate() {
			return occurDate;
		}
		public void setOccurDate(String occurDate) {
			this.occurDate = occurDate;
		}
		public String getSalesman() {
			return salesman;
		}
		public void setSalesman(String salesman) {
			this.salesman = salesman;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
	}
	
	class RoomBizView implements KDTMouseListener{
		private Class viewClazz;
		private String keyName = "id";
		
		public void tableClicked(KDTMouseEvent e) {
			try {
				if (e.getButton() == 1 && e.getClickCount() == 2) {
					int rowIndex = e.getRowIndex();
					if (rowIndex == -1) {
						return;
					}
					
					IRow row = bizTable.getRow(rowIndex);
					PurchaseInfo pur = (PurchaseInfo) row.getUserObject();
					UIContext uiContext = new UIContext(this);
					uiContext.put("ID", pur.getId());
					IUIWindow uiWindow = UIFactory.createUIFactory(
							UIFactoryName.MODEL).create(
							PurchaseEditUI.class.getName(), uiContext,
							null, "VIEW");
					uiWindow.show();
				}
			} catch (Exception exc) {
				handUIException(exc);
			} finally {
			}
		}
	}
	*/

}