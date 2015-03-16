package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.CustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo;
import com.kingdee.eas.fdc.sellhouse.GatheringEnum;
import com.kingdee.eas.fdc.sellhouse.GatheringObjectEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.QuitRoomFactory;
import com.kingdee.eas.fdc.sellhouse.ReceiveBillTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCompensateStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fi.cas.ReceivingBillFactory;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;


public class SHEReceiveBillEditUI extends ReceiveBillEidtUI {

	private static final Logger logger = CoreUIObject.getLogger(SHEReceiveBillEditUI.class);
	
	public SHEReceiveBillEditUI() throws Exception {
		super();
	}

	
	protected MoneySysTypeEnum getSystemType() {
		return MoneySysTypeEnum.SalehouseSys;
	}	
	
	
	public void onLoad() throws Exception {
		
		this.btnAddNew.setVisible(true);
		this.btnAddNew.setEnabled(true);
		
		super.onLoad();

		this.actionTraceDown.setVisible(true);
		
		if(!this.oprtState.equals(OprtState.ADDNEW))
			this.actionViewBill.setVisible(true);
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		if(OprtState.EDIT == this.getOprtState()){
			MsgBox.showInfo("不能在编辑状态下新增！");
			return;
		}
		super.actionAddNew_actionPerformed(e);
		f7Room.setEnabled(true);
		f7Room.setValue(null);
		f7Cheque.setVisible(true);
		f7Cheque.setEnabled(true);
		pkBillDate.setEnabled(true);
		comboGathering.setEnabled(true);
		f7Currency.setEnabled(false);
		comboBelongSys.setEnabled(true);
		f7SellOrder.setEnabled(false);
		f7SellOrder.setValue(null);
		f7Purchase.setValue(null);
		f7Purchase.setEnabled(false);
		f7Customer.setValue(null);
		f7Salesman.setValue(null);
		f7Salesman.setEnabled(false);
		txtReceiveAmount.setValue(null);
		f7Currency.setEnabled(false);
		f7SellOrder.setValue(null);
		f7SellOrder.setEnabled(false);
		kDTable1.removeRows();
		this.btnAudit.setEnabled(false);
	}
	
	public void onShow() throws Exception {
		super.onShow();
		if(this.oprtState.equals(STATUS_ADDNEW)){
			this.btnAddNew.setEnabled(false);
			this.menuItemAddNew.setEnabled(false);
		}else{
			this.btnAddNew.setVisible(true);
			this.menuItemAddNew.setVisible(true);
			this.btnAddNew.setEnabled(true);
			this.menuItemAddNew.setEnabled(true);
		}
		if(!"收款".equals( getUIContext().get("billType"))){
			this.btnAddNew.setVisible(false);
			this.menuItemAddNew.setVisible(false);
			this.btnAddNew.setEnabled(false);
			this.menuItemAddNew.setEnabled(false);
		}
		if(!SysContext.getSysContext().getCurrentSaleUnit().isIsBizUnit()){
			this.btnAddNew.setEnabled(false);
			this.btnEdit.setEnabled(false);
			this.menuItemAddNew.setEnabled(false);
			this.menuItemEdit.setEnabled(false);
		}
	}
	
	public void loadFields() {
		super.loadFields();
		if(this.oprtState.equals(STATUS_ADDNEW)){
			this.btnAddNew.setEnabled(false);
			this.menuItemAddNew.setEnabled(false);
		}else{
			this.btnAddNew.setVisible(true);
			this.menuItemAddNew.setVisible(true);
			this.btnAddNew.setEnabled(true);
			this.menuItemAddNew.setEnabled(true);
		}
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}
	
	/**
	 * 创建 售楼系统中的初始数据
	 */
	protected IObjectValue createNewDataForSys(ReceivingBillInfo receivingBillInfo, FDCReceiveBillInfo fdcReceiveBillInfo) throws EASBizException, BOSException, UuidException
	{
		RoomInfo room = (RoomInfo) this.getUIContext().get("room");
		fdcReceiveBillInfo.setRoom(room);
		// 约定如下:售楼系统中,如果传入的房间参数,一定要传入 收款对象 参数.(销售房间或者诚意认购)
		GatheringEnum gathering = (GatheringEnum) this.getUIContext().get(KEY_GATHERING);
		if (gathering == null)
			gathering = GatheringEnum.SaleRoom;
		fdcReceiveBillInfo.setGathering(gathering);
		if (GatheringEnum.SinPurchase.equals(gathering)) {
			SincerityPurchaseInfo sinPur = (SincerityPurchaseInfo) this.getUIContext().get(KEY_SIN_PURCHASE);
			if (sinPur == null) {
				logger.error("诚意认购收款,居然不传诚意认购单.NND.");
				this.abort();
			}
			fdcReceiveBillInfo.setSellProject(sinPur.getSellProject());
			fdcReceiveBillInfo.setSinPurchase(sinPur);
			fdcReceiveBillInfo.setRoom(sinPur.getRoom());
			
			//by tim_gao 预约排号，客户字段数据结构变化 201
//			if(sinPur.getCustomer() != null  &&  sinPur.getCustomer().getSysCustomer() != null){
//				CustomerInfo sysCustomer = sinPur.getCustomer().getSysCustomer();
//				receivingBillInfo.setPayerID(sysCustomer.getId().toString());
//				receivingBillInfo.setPayerNumber(sysCustomer.getNumber());
//				receivingBillInfo.setPayerName(sysCustomer.getName());
//				
//				CustomerEntryInfo cus = new CustomerEntryInfo();
//				cus.setCustomer(sysCustomer);
//				fdcReceiveBillInfo.getCustomerEntrys().add(cus);
//			}
		} else if (GatheringEnum.SaleRoom.equals(gathering)) {
			String purchaseId = (String) this.getUIContext().get("purchaseId");
			if (room != null) {
				fdcReceiveBillInfo.setRoom(room);
				String sellProjectId = room.getBuilding().getSellProject().getId().toString();
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("*");
				sels.add("project.*");
				// TODO 这个查询没用啊..
				SellProjectInfo sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(BOSUuid.read(sellProjectId)), sels);
				fdcReceiveBillInfo.setSellProject(sellProject);
				
				if (purchaseId == null) {
					PurchaseCollection purchases = getValidPurchaseByRoom(room);
					if (purchases.size() >= 1) {
						PurchaseInfo purchase = purchases.get(0);
						fdcReceiveBillInfo.setPurchase(purchase);
					}
					if (purchases.size() == 0) {
						if (ReceiveBillTypeEnum.refundment.equals(this.getUIContext().get(KEY_BILLTYPE))) {
							if (!RoomSellStateEnum.OnShow.equals(room.getSellState())) {
								MsgBox.showInfo("该房间存在变更状态的认购单，无法进行退款！");
								this.abort();
							} else {
								MsgBox.showInfo("没有人认购此房间，或者预定没有复核，无法进行退款！");
								this.abort();
							}
						} else {
							if (!RoomSellStateEnum.OnShow.equals(room.getSellState())) {
								MsgBox.showInfo("该房间存在变更状态的认购单，无法进行收款！");
								this.abort();
							} else {
								MsgBox.showInfo("没有人认购此房间，或者预定没有复核，无法收款!");
								this.abort();
							}
						}
					} else if (purchases.size() == 1) {
						if (!ReceiveBillTypeEnum.refundment.equals(this.getUIContext().get(KEY_BILLTYPE)) && !OprtState.VIEW.equalsIgnoreCase(this.getOprtState())) {
							if (this.isExistValidQuitBill(purchases.get(0), room)) {
								MsgBox.showWarning("该认购单存在有效状态下的退房单，不能进行收款操作！");
								this.f7Purchase.setValue(null);
								this.abort();
							}
						}
					}
				}
			}
			if (purchaseId != null) {
				PurchaseInfo purchase = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK(BOSUuid.read(purchaseId)), getPurchaseSelectorItemColl());
				fdcReceiveBillInfo.setPurchase(purchase);
				fdcReceiveBillInfo.setRoom(purchase.getRoom());
			}
		}
		if (fdcReceiveBillInfo.getPurchase() != null) {
			PurchaseInfo purchase = fdcReceiveBillInfo.getPurchase();
			MoneyDefineCollection moneyNames = getValidMoneyNameByPurchase(room, purchase);
			//TODO 房地产收款单上的款项类型 没用了吧？这里应该不需要维护了
			if (moneyNames.size() == 1) {
				fdcReceiveBillInfo.setMoneyDefine(moneyNames.get(0));
			} else {
				MoneyDefineInfo temp = this.getMoneyDefineOfPlannedInSHE(purchase);
				if (temp != null) {
					if (moneyNames.contains(temp)) {
						fdcReceiveBillInfo.setMoneyDefine(temp);
					}
				}
			}
			PurchaseCustomerInfoCollection purchaseCustomerColl = purchase.getCustomerInfo();
			if (purchaseCustomerColl.size() > 0) {
				CustomerInfo sysCustomer = purchaseCustomerColl.get(0).getCustomer().getSysCustomer();
				if (sysCustomer != null) {
					receivingBillInfo.setPayerID(sysCustomer.getId().toString());
					receivingBillInfo.setPayerNumber(sysCustomer.getNumber());
					receivingBillInfo.setPayerName(sysCustomer.getName());
					
					CustomerEntryInfo cus = new CustomerEntryInfo();
					cus.setCustomer(sysCustomer);
					fdcReceiveBillInfo.getCustomerEntrys().add(cus);
				}
			}
		}
		return receivingBillInfo;
	}
	
	
	
	/**
	 * 售楼系统装载数据
	 */
	protected void loadFieldsForSys(ReceivingBillInfo receivingBillInfo,FDCReceiveBillInfo fdcReceiveBillInfo)
	{
		GatheringObjectEnum gatheringObj = fdcReceiveBillInfo.getGatheringObject();
		if(gatheringObj == null){
			gatheringObj = GatheringObjectEnum.room;
		}
		this.gatheringObject = gatheringObj;
		
		GatheringEnum gathering = fdcReceiveBillInfo.getGathering();
		if(gathering == null){
			//售楼旧数据没有该字段,旧数据全部为认购单收款
			gathering = GatheringEnum.SaleRoom;
		}
		this.comboGathering.setSelectedItem(gathering);
		setControlByGathering(gathering);
		
		//售楼系统下,只有可能是诚意认购收款和销售房间收款
		if(GatheringEnum.SinPurchase.equals(gathering)){
			
		} else if (GatheringEnum.SaleRoom.equals(gathering)) {
			RoomInfo room = fdcReceiveBillInfo.getRoom();
			if (room != null) {
				this.f7Room.setUserObject(room);
				this.f7Room.setText(room.getBuilding().getName() + "-" + room.getNumber());
				this.setPurchaseFilter(room);
				this.f7SellOrder.setValue(room.getSellOrder());
				this.f7Purchase.setEnabled(true);
			} else {// 这里没必要，房间为空，一般认购单肯定也是空的.而且后期如果针对认购单收款的话，还会有问题 TODO
				this.f7Purchase.setEnabled(false);
			}
			PurchaseInfo purchase = fdcReceiveBillInfo.getPurchase();
			if (purchase != null) {
				this.f7Salesman.setValue(purchase.getSalesman());
			}
		}
		
		if (this.getOprtState().equals(OprtState.EDIT)  ||  this.getOprtState().equals(OprtState.VIEW)) {
			this.f7Room.setEnabled(false);
			this.f7Purchase.setEnabled(false);
		}

		try {
			this.setF7SysCustomerFilterForSHE();
		} catch (BOSException e) {
			this.handleException(e);
		}
	}
	
	
	protected void setControlByGathering(GatheringEnum gathering) {
		if(GatheringEnum.SinPurchase.equals(gathering)){
			setControlVisible(true, false, false, false, true, false);
			this.f7Room.setRequired(false);
		}else if(GatheringEnum.SaleRoom.equals(gathering)){
			setControlVisible(true, false, true, false, false, false);
			this.f7Room.setRequired(true);
		}else if(GatheringEnum.ObligatedRoom.equals(gathering)){
			setControlVisible(true, false, false, false, false, true);
			this.f7Room.setRequired(true);
		}else if(GatheringEnum.ObligatedAttach.equals(gathering)){
			setControlVisible(false, true, false, false, false, true);
			this.f7Room.setRequired(false);
		}else if(GatheringEnum.CustomerRev.equals(gathering))
		{
			setControlVisible(true, false, false, false, false, false);
			this.f7Room.setRequired(false);
		}
	}	
	
	
	/**
	 * 售楼系统 设置客户的过滤条件
	 * 主要是取得特定认购单下的所有客户信息
	 */
	private void setF7SysCustomerFilterForSHE() throws BOSException
	{
		if(GatheringEnum.SinPurchase.equals(this.comboGathering.getSelectedItem())){
			SincerityPurchaseInfo sinPur = (SincerityPurchaseInfo) this.f7SinPurchase.getValue();
			if(sinPur != null){
				//by tim_gao 预约排号，客户字段数据结构变化 2011-06-15 直接注掉了，没有实现了，因为说单据不用了
//				FDCCustomerInfo fdcCus = sinPur.getCustomer();
//				CustomerInfo sysCustomerInfo = fdcCus.getSysCustomer();
//				if(sysCustomerInfo != null){
//					f7Customer.getQueryAgent().setDefaultFilterInfo(null);
//					f7Customer.getQueryAgent().setHasCUDefaultFilter(false);
//					f7Customer.getQueryAgent().resetRuntimeEntityView();
//
//					EntityViewInfo view = new EntityViewInfo();
//					FilterInfo filter = new FilterInfo();
//					view.setFilter(filter);
//					filter.getFilterItems().add(
//							new FilterItemInfo("id", sysCustomerInfo.getId().toString()));
//					this.f7Customer.setEntityViewInfo(view);
//				}
			}
			return;
		}
		
		RoomInfo room = (RoomInfo) this.f7Room.getValue();
		if (room == null)
		{
			return;
		}
		EntityViewInfo queryView = new EntityViewInfo();
		queryView.getSelector().add("customer.sysCustomer.*");
		FilterInfo queryFilter = new FilterInfo();
		//这里不明白为什么要加这个条件，下面直接有认购单的ID就可以了啊
		queryFilter.getFilterItems().add(
				new FilterItemInfo("head.room.id", room.getId().toString()));
		PurchaseInfo purchaseInfo = (PurchaseInfo) this.f7Purchase.getValue();
		if (purchaseInfo != null)
		{
			queryFilter.getFilterItems().add(
					new FilterItemInfo("head.id", purchaseInfo.getId().toString()));
		}
		queryView.setFilter(queryFilter);
		PurchaseCustomerInfoCollection purchaseCustomerColl = PurchaseCustomerInfoFactory.getRemoteInstance().getPurchaseCustomerInfoCollection(queryView);

		Set set = new HashSet();
		for (int i = 0; i < purchaseCustomerColl.size(); i++)
		{
			PurchaseCustomerInfoInfo purchaseCustomerInfo = purchaseCustomerColl.get(i);
			FDCCustomerInfo fdcCustomerInfo = purchaseCustomerInfo.getCustomer();
			CustomerInfo sysCustomerInfo = fdcCustomerInfo.getSysCustomer();
			if (sysCustomerInfo == null)
			{
				logger.warn("认购的房地产客户未同步,请仔细检查. fdcCustomerId:"
						+ fdcCustomerInfo.getId().toString());
				continue;
			}
			set.add(sysCustomerInfo.getId().toString());
		}
		//防止更之前的过滤条件相叠加
		f7Customer.getQueryAgent().setDefaultFilterInfo(null);
		f7Customer.getQueryAgent().setHasCUDefaultFilter(false);
		f7Customer.getQueryAgent().resetRuntimeEntityView();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		if (set.isEmpty()) {
			filter.getFilterItems().add(new FilterItemInfo("id", "null"));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("id", set, CompareType.INCLUDE));
		}

		this.f7Customer.setEntityViewInfo(view);
	}
	
	
	
	/**
	 * 认购单事件
	 */
	protected void f7Purchase_dataChanged(DataChangeEvent e) throws Exception
	{
		if(!this.contPurchase.isVisible())
		{
			return;
		}
		RoomInfo room = (RoomInfo) this.f7Room.getValue();
		if(room == null && OprtState.ADDNEW !=this.getOprtState())
		{
			if(OprtState.VIEW != this.getOprtState() )
			MsgBox.showInfo("请先选择房间！");
			return;
		}
		PurchaseInfo purchaseInfo = (PurchaseInfo) this.f7Purchase.getValue();
		if (purchaseInfo != null)
		{
			if(PurchaseStateEnum.QuitRoomBlankOut.equals(purchaseInfo.getPurchaseState())){
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("purchase.id", purchaseInfo.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("isBlance", Boolean.TRUE , CompareType.NOTEQUALS));
				filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
				if(QuitRoomFactory.getRemoteInstance().exists(filter)){
					MsgBox.showInfo(this, "请先进行退房结算，再进行其他应收的收款！");
					this.f7Purchase.setValue(e.getOldValue());
					this.f7Customer.setValue(null);
					this.abort();
				}
			}
			
			this.f7Salesman.setValue(purchaseInfo.getSalesman());
		
			PurchaseCustomerInfoCollection purchaseCustomerColl = purchaseInfo.getCustomerInfo();
			CRMHelper.sortCollection(purchaseCustomerColl, "seq",true);
			if (purchaseCustomerColl != null) {
				Object[] o = new Object[purchaseCustomerColl.size()];
				for (int i = 0; i < purchaseCustomerColl.size(); i++) {
					PurchaseCustomerInfoInfo purCustInfo = purchaseCustomerColl.get(i);
					if (purCustInfo!= null && purCustInfo.getCustomer()!=null)
						o[i] = purCustInfo.getCustomer().getSysCustomer();
				}
				this.f7Customer.setValue(o);
			}
			
			
		}
		this.setF7SysCustomerFilterForSHE();
		this.kDTable1.removeRows();
	}

	
	/**
	 * 当选择售楼房间的时候，所做的操作
	 */
	private void doSomeThingAsSellRoomChosen(DataChangeEvent e) throws BOSException, EASBizException, UuidException
	{
		RoomInfo room = null;
		if(e.getNewValue() instanceof RoomInfo)
		{
		   room = (RoomInfo) e.getNewValue();
		}
		if (room == null)
		{
			return;
		}
		
		GatheringEnum gathering = (GatheringEnum) this.comboGathering.getSelectedItem();
		if(gathering == null) gathering = GatheringEnum.SaleRoom;
		
		if(GatheringEnum.SinPurchase.equals(gathering)){
			//TODO
			
		}else if(GatheringEnum.SaleRoom.equals(gathering)){
			if (room.getSellState().equals(RoomSellStateEnum.KeepDown))
			{
				MsgBox.showInfo("房间已经被保留!");
				return;
			}
			if (room.getSellState().equals(RoomSellStateEnum.Init))
			{
				MsgBox.showInfo("房间没有开盘!");
				return;
			}
			if (room != null)
			{
				PurchaseCollection purchaseColl = this.getValidPurchaseByRoom(room);
				if (!ReceiveBillTypeEnum.refundment.equals(this.getUIContext().get(
						KEY_BILLTYPE)))
				{
//					if (purchaseColl.size() == 0)
//					{
//						if (this.getOprtState() != OprtState.VIEW
//								&& this.getOprtState() != OprtState.EDIT)
//						{
//							MsgBox.showInfo("没有人认购此房间,或者预定没有复核,无法收款!");
//						}
//						this.f7Room.setValue(e.getOldValue());
//						this.f7Room.setUserObject(e.getOldValue());
//						this.abort();
//					}
				}
				this.f7Room.setUserObject(room);
				this.f7Room.setValue(room);
				this.f7Purchase.setValue(null);
				this.f7Customer.setValue(null);
				//this.f7Room.setText(room.getBuilding().getName() + "-"	+ room.getNumber());
				this.f7SellOrder.setValue(room.getSellOrder());
				this.f7Purchase.setEnabled(true);
				this.f7TenancyContract.setEnabled(true);
				
				this.setPurchaseFilter(room);
				this.setF7SysCustomerFilterForSHE();
				
				if (purchaseColl.size() >= 1)
				{
					this.f7Purchase.setValue(purchaseColl.get(0));
					PurchaseInfo purchase = purchaseColl.get(0);
					this.f7Salesman.setValue(purchase.getSalesman());
					
					PurchaseCustomerInfoCollection purchaseCustomerColl = (PurchaseCustomerInfoCollection)purchase.getCustomerInfo();
					if (purchaseCustomerColl != null && purchaseCustomerColl.size() > 0)
					{
						CRMHelper.sortCollection(purchaseCustomerColl, "seq",true);
						if (purchaseCustomerColl != null) {
							Object[] o = new Object[purchaseCustomerColl.size()];
							for (int i = 0; i < purchaseCustomerColl.size(); i++) {
								PurchaseCustomerInfoInfo purCustInfo = purchaseCustomerColl.get(i);
								if (purCustInfo!= null && purCustInfo.getCustomer()!=null)
									o[i] = purCustInfo.getCustomer().getSysCustomer();
							}
							this.f7Customer.setValue(o);
						}
					}
				}
				String sellProjectId = room.getBuilding().getSellProject().getId().toString();
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("*");
				sels.add("project.*");
				SellProjectInfo sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(
									new ObjectUuidPK(BOSUuid.read(sellProjectId)), sels);
				this.f7SellProject.setValue(sellProject);
			} else
			{
				this.f7Purchase.setEnabled(false);
				this.f7Purchase.setValue(null);
			}
		}
	}
	
	/**
	 * 设置认购单的过滤条件
	 * 不是预定申请的，且不是无效状态的的认购单
	 */
	private void setPurchaseFilter(RoomInfo room)
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(
				new FilterItemInfo("purchaseState",
						PurchaseStateEnum.PREPURCHASEAPPLY_VALUE,
						CompareType.NOTEQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.QUITROOMBLANKOUT_VALUE));
		filter.setMaskString("#0 and #1 and (#2 or #3)");
		view.setFilter(filter);
		this.f7Purchase.setEntityViewInfo(view);
	}
	
	/**
	 * 取得该房间有效状态下的认购单
	 */
	private PurchaseCollection getValidPurchaseByRoom(RoomInfo room) throws BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sels = getPurchaseSelectorItemColl();
		view.getSelector().addObjectCollection(sels);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		
		if(ReceiveBillTypeEnum.refundment.equals(this.getUIContext().get(KEY_BILLTYPE)))
		{
			String stateStr = "'"+PurchaseStateEnum.QUITROOMBLANKOUT_VALUE+"','"+PurchaseStateEnum.PREPURCHASEAPPLY_VALUE+"'";
			stateStr += ",'"+PurchaseStateEnum.PREPURCHASECHECK_VALUE+"','"+PurchaseStateEnum.PURCHASEAPPLY_VALUE+"'";
			stateStr += ",'"+PurchaseStateEnum.CHANGEROOMBLANKOUT_VALUE+"','"+PurchaseStateEnum.PURCHASECHANGE_VALUE+"'";
			stateStr += ",'"+PurchaseStateEnum.PURCHASEAUDITING_VALUE+"','"+PurchaseStateEnum.PURCHASEAUDIT_VALUE+"'";
			filter.getFilterItems().add(new FilterItemInfo("purchaseState",stateStr,CompareType.INNER));
			filter.setMaskString(" #0 and #1  ");
		}
		else
		{
			filter.getFilterItems().add(new FilterItemInfo("purchaseState",	PurchaseStateEnum.PrePurchaseApply,	CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("purchaseState",	PurchaseStateEnum.ChangeRoomBlankOut,CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("purchaseState",	PurchaseStateEnum.QuitRoomBlankOut,CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("purchaseState",	PurchaseStateEnum.AdjustBlankOut,CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("purchaseState",	PurchaseStateEnum.ManualBlankOut,CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("purchaseState",PurchaseStateEnum.NoPayBlankOut,CompareType.NOTEQUALS));
			filter.getFilterItems().add(new FilterItemInfo("purchaseState",PurchaseStateEnum.PurchaseChange,CompareType.NOTEQUALS));
		}		
		view.setFilter(filter);
		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo sortInfo = new SorterItemInfo("purchaseDate");
		sortInfo.setSortType(SortType.DESCEND);
		sorter.add(sortInfo);
		view.setSorter(sorter);
		return PurchaseFactory.getRemoteInstance().getPurchaseCollection(view);
	}
	
	

	
	
	/**
	 * 房间选择事件
	 */
	protected void f7Room_dataChanged(DataChangeEvent e) throws Exception
	{
		if(isLoadField){
			return;
		}
		
		if("VIEW".equalsIgnoreCase(this.getOprtState()) || "FINDVIEW".equalsIgnoreCase(this.getOprtState())){
			return;
		}
		
		this.doSomeThingAsSellRoomChosen(e);

		this.kDTable1.removeRows();
	}
	
	

	
	
	private  boolean isExistValidQuitBill(PurchaseInfo purchase,RoomInfo room) throws EASBizException, BOSException
	{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("purchase.id",purchase.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("room.id",room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.SAVED_VALUE,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.INVALID_VALUE,CompareType.NOTEQUALS));
		
		return QuitRoomFactory.getRemoteInstance().exists(filter);
	}
	
	
	/**
	 * 获得当前有效的款项
	 * 1：如果是退款，则直接返回退款；2：如果是签约的房间，且面积补差已审批，则加入补差款项；3：手续费，滞纳金，其他 款项是一定放进去的；
	 * @param room
	 * @param purchaseInfo
	 * @return
	 * @throws BOSException
	 */
	private MoneyDefineCollection getValidMoneyNameByPurchase(RoomInfo room,PurchaseInfo purchaseInfo) throws BOSException
	{
		MoneyDefineCollection moneyDefineColl = new MoneyDefineCollection();
		
		//如果是退款，则直接返回退款
		if (ReceiveBillTypeEnum.refundment.equals(this.getUIContext().get(KEY_BILLTYPE)))
		{
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("*");
			view.getSelector().add("revBillType.*");
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("moneyType",	MoneyTypeEnum.REFUNDMENT_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.SALEHOUSESYS_VALUE));
			moneyDefineColl = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
			return moneyDefineColl;
		}
		
		RoomSellStateEnum roomState = room.getSellState();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		if (roomState.equals(RoomSellStateEnum.Sign))
		{
			RoomAreaCompensateInfo roomAreaCompensationInfo = SHEHelper	.getRoomAreaCompensation(room);
			//如果是签约了，且办理补差的，则需要补差款
			if (roomAreaCompensationInfo != null
					&& roomAreaCompensationInfo.getCompensateState() != null
					&& roomAreaCompensationInfo.getCompensateState().equals(RoomCompensateStateEnum.COMAUDITTED))
			{
				filter.getFilterItems().add(new FilterItemInfo("moneyType",MoneyTypeEnum.COMPENSATEAMOUNT_VALUE));
			}
		}
		if (filter.getFilterItems().size() > 0)
		{
			String maskStr = "#0";
			for (int i = 1; i < filter.getFilterItems().size(); i++)
			{
				maskStr += " or #" + i;
			}
			filter.setMaskString(maskStr);
			
			int temp = filter.getFilterItems().size()-1;
		
			filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.SALEHOUSESYS_VALUE));
			
			filter.setMaskString("("+maskStr+")" + " and #"+(temp+1));
			MoneyDefineCollection tempMoneyDefineColl = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
			moneyDefineColl.addCollection(tempMoneyDefineColl);
		}
		//对预收款的判断
		if (!purchaseInfo.getState().equals(FDCBillStateEnum.AUDITTED))
		{
			boolean isHavePay = isHavePay(purchaseInfo);
			if (isHavePay)
			{
				return moneyDefineColl;
			} else
			{
				view = new EntityViewInfo();
				view.getSelector().add("*");
				view.getSelector().add("revBillType.*");
				filter = new FilterInfo();
				view.setFilter(filter);
				filter.getFilterItems().add(new FilterItemInfo("moneyType",	MoneyTypeEnum.PRECONCERTMONEY_VALUE));
				filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.SALEHOUSESYS_VALUE));
				moneyDefineColl.addCollection(MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view));
			}
		} //以下是对计划款项的操作
		else
		{
			PurchasePayListEntryCollection purchasePayListEntryColl = this.getPurchasePayListOrderBySeq(purchaseInfo);
			if(purchasePayListEntryColl == null)
			{
				logger.warn("认购单为："+purchaseInfo.getName()+"   "+purchaseInfo.getNumber()+" 下面，没有付款明细!"	);
			}
			else
			{
				for(int i = 0; i < purchasePayListEntryColl.size(); i ++)
				{
					PurchasePayListEntryInfo purchasePayListEntryInfo = purchasePayListEntryColl.get(i);
					BigDecimal actPay = purchasePayListEntryInfo.getActPayAmount();
					BigDecimal appPay = purchasePayListEntryInfo.getApAmount();
					
					if(actPay == null)
					{
						actPay = FDCHelper.ZERO;
					}
					if(appPay == null)
					{
						appPay = FDCHelper.ZERO;
					}
					if(actPay.compareTo(appPay) < 0)
					{
						moneyDefineColl.add(purchasePayListEntryInfo.getMoneyDefine());
						//查看的是，不能取按照常理赋值
						if(!OprtState.VIEW.equals(this.getOprtState()))
						{
							this.seq = purchasePayListEntryInfo.getSeq();
						}
						break;
					}
					else
					{
						continue;
					}
				}
			}
		}
		
        //加上非计划性的款项
		//MoneyDefineCollection unPlannedMoneyDefineColl = this.getUnplannedMoneyDefineByMoneySysType(MoneySysTypeEnum.SalehouseSys);
		MoneyDefineCollection unPlannedMoneyDefineColl = this.getUnplannedMoneyDefineInSHE(purchaseInfo);
		moneyDefineColl.addCollection(unPlannedMoneyDefineColl);
		
		return moneyDefineColl;
	}
	

	/**
	 * 判断该认购单是否有付款过
	 * @author laiquan_luo
	 * @param purchase
	 * @return
	 */
	private boolean isHavePay(PurchaseInfo purchase)
	{
		PurchasePayListEntryCollection purchasePayListEntryColl = purchase.getPayListEntry();
		boolean isHaveActRev = false;
		for (int i = 0; i < purchasePayListEntryColl.size(); i++)
		{
			PurchasePayListEntryInfo entry = purchasePayListEntryColl.get(i);
			BigDecimal actAmount = entry.getActPayAmount();
			if (actAmount != null && actAmount.compareTo(FDCHelper.ZERO) > 0)
			{
				isHaveActRev = true;
				break;
			}
		}
		return isHaveActRev;
	}
	
	
	/**
	 * 获得认购单的付款明细，是以付款明细的  sel 的升序来排列的
	 * @param purchaseInfo
	 * @return
	 * @throws BOSException
	 */
	private PurchasePayListEntryCollection getPurchasePayListOrderBySeq(PurchaseInfo purchaseInfo) throws BOSException
	{
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("moneyDefine.*");
		view.getSelector().add("*");
		view.setFilter(filter);
		
		SorterItemCollection sorterColl = new SorterItemCollection();
		sorterColl.add(new SorterItemInfo("seq"));
		
		view.setSorter(sorterColl);
		
		filter.getFilterItems().add(new FilterItemInfo("head",purchaseInfo.getId().toString()));
		
		PurchasePayListEntryCollection purchasePayListEntryColl = PurchasePayListEntryFactory.getRemoteInstance().getPurchasePayListEntryCollection(view);
		return purchasePayListEntryColl;
	}
	
	/**
	 * 返回计划款项的值，以填充默认值
	 * @param purchaseInfo
	 * @return
	 * @throws BOSException
	 */
	protected MoneyDefineInfo getMoneyDefineOfPlannedInSHE(PurchaseInfo purchaseInfo) throws BOSException
	{
		MoneyDefineInfo moneyDefineInfo = null;
		PurchasePayListEntryCollection purchasePayListEntryColl = this.getPurchasePayListOrderBySeq(purchaseInfo);
		if(purchasePayListEntryColl == null)
		{
			logger.warn("认购单为："+purchaseInfo.getName()+"   "+purchaseInfo.getNumber()+" 下面，没有付款明细!"	);
		}
		else
		{
			for(int i = 0; i < purchasePayListEntryColl.size(); i ++)
			{
				PurchasePayListEntryInfo purchasePayListEntryInfo = purchasePayListEntryColl.get(i);
				BigDecimal actPay = purchasePayListEntryInfo.getActPayAmount();
				BigDecimal appPay = purchasePayListEntryInfo.getApAmount();
				
				if(actPay == null)
				{
					actPay = FDCHelper.ZERO;
				}
				if(appPay == null)
				{
					appPay = FDCHelper.ZERO;
				}
				if(actPay.compareTo(appPay) < 0)
				{
					moneyDefineInfo = purchasePayListEntryInfo.getMoneyDefine();
					break;
				}
				else
				{
					continue;
				}
			}
		}
		return moneyDefineInfo;
	}
	
	/**
	 * 获取认购中系统中的 非计划性款项
	 * @param purchase
	 * @return
	 */
	private MoneyDefineCollection getUnplannedMoneyDefineInSHE(PurchaseInfo purchase)
	{
		MoneyDefineCollection moneyColl = new MoneyDefineCollection();
		PurchaseElsePayListEntryCollection payColl = purchase.getElsePayListEntry();
		if(payColl == null)
			return moneyColl;
		for(int i = 0; i < payColl.size(); i ++)
		{
			PurchaseElsePayListEntryInfo info = payColl.get(i);
			moneyColl.add(info.getMoneyDefine());
		}
		return moneyColl;
	}
	
	
	protected void f7SinPurchase_dataChanged(DataChangeEvent e) throws Exception {
		if(!this.contSinPurchase.isVisible())
		{
			return;
		}
		
		if(e.getOldValue() != null  &&  e.getNewValue()!= null  &&  
				e.getNewValue().equals(e.getOldValue())){
			return;
		}
		
		SincerityPurchaseInfo sinPurchase = (SincerityPurchaseInfo) this.f7SinPurchase.getValue();
		
		if (sinPurchase != null)		{
			sinPurchase = getSinPurchase(sinPurchase.getId().toString());
			this.f7SinPurchase.setValue(sinPurchase);			
			this.f7Salesman.setValue(sinPurchase.getSalesman());
			this.f7SellProject.setValue(sinPurchase.getSellProject());
			
			//by tim_gao 预约排号，客户字段数据结构变化 2011-06-15
//			FDCCustomerInfo fdcCus = sinPurchase.getCustomer();
//			if(fdcCus != null){
//				CustomerInfo sysCustomer = fdcCus.getSysCustomer();
//				this.f7Customer.setValue(sysCustomer);
//			}
			
			this.f7Room.setValue(sinPurchase.getRoom());
		}

		this.setF7SysCustomerFilterForSHE();

		this.kDTable1.removeRows();
	}
	
	
	/**
	 * 校验选择按钮
	 */
	protected void verifyChoose()
	{
		GatheringEnum gathering = (GatheringEnum) this.comboGathering.getSelectedItem();
			if(GatheringEnum.SinPurchase.equals(gathering)){
				if(this.f7SinPurchase.getValue() == null){
					MsgBox.showWarning("诚意认购单为空，无法进行当前操作！");
					this.abort();		
				}
			}else if(GatheringEnum.SaleRoom.equals(gathering)){
				if(this.f7Purchase.getValue() == null)
				{
					MsgBox.showWarning("认购单为空，无法进行当前操作！");
					this.abort();
				}
				//售楼收款会对客户收款分录做红冲收款,故需要客户必录
				if(this.f7Customer.getValue() == null)
				{
					MsgBox.showWarning("客户为空，无法进行当前操作！");
					this.abort();
				}
			}
	}
	
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		//如果是对预订符合的认购单收款时，如果房间状态是待售状态，则收的所有预订金合计必须要大于设置中的最低预订金标准
		// (如果有诚意收款的预收款，比较时需要加上后比较)
		PurchaseInfo purChaseInfo = (PurchaseInfo)this.f7Purchase.getValue();
		if(purChaseInfo!=null) {
			purChaseInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(
							"select prePurLevelAmount,purchaseState,room.sellState,sincerityPurchase where id = '"+purChaseInfo.getId()+"'");
			if(purChaseInfo.getPurchaseState()!=null && purChaseInfo.getPurchaseState().equals(PurchaseStateEnum.PrePurchaseCheck)){
				if(purChaseInfo.getRoom().getSellState()!=null) {
					if(purChaseInfo.getRoom().getSellState().equals(RoomSellStateEnum.OnShow) || purChaseInfo.getRoom().getSellState().equals(RoomSellStateEnum.PrePurchase)) {
					BigDecimal totalPreRev = FDCHelper.ZERO;
					for(int i=0;i<this.kDTable1.getRowCount();i++) {
						IRow row = this.kDTable1.getRow(i);
						MoneyDefineInfo money = (MoneyDefineInfo)row.getCell("moneyType").getValue();
						if(money.getMoneyType().equals(MoneyTypeEnum.PreconcertMoney)) {
							BigDecimal gatheringAmount = (BigDecimal)row.getCell("gatheringAmount").getValue();
							totalPreRev = totalPreRev.add(gatheringAmount==null?FDCHelper.ZERO:gatheringAmount);
						}	
					}
					
					if(purChaseInfo.getSincerityPurchase()!=null) { //如果有诚意收款的预收款，比较时需要加上后比较
						FDCReceiveBillEntryCollection fdcRevBillEntryColl = FDCReceiveBillEntryFactory.getRemoteInstance().getFDCReceiveBillEntryCollection(
							"select canCounteractAmount where moneyDefine.moneyType='"+MoneyTypeEnum.PREMONEY_VALUE+"' and  receivingBill.fdcReceiveBill.moneySysType = '"+MoneySysTypeEnum.SALEHOUSESYS_VALUE+"' " +
									"and canCounteractAmount >0 and receivingBill.fdcReceiveBill.isBlankOut =0 and receivingBill.fdcReceiveBill.billTypeEnum = '"+ReceiveBillTypeEnum.GATHERING_VALUE+"' " +
											"and receivingBill.fdcReceiveBill.sinPurchase.id='"+purChaseInfo.getSincerityPurchase().getId().toString()+"'");
						for(int i=0;i<fdcRevBillEntryColl.size();i++) {
							totalPreRev = totalPreRev.add(fdcRevBillEntryColl.get(i).getCanCounteractAmount());
						}
					}
					
					if(totalPreRev.compareTo(purChaseInfo.getPrePurLevelAmount())<0){
						MsgBox.showWarning("预订金收款合计不能小于预定金的最低标准！");
						abort();
					}
				}
				}
			}
		}
		
		super.actionSubmit_actionPerformed(e);
		this.btnAudit.setEnabled(true);
	}
	
	
	
	public void actionViewBill_actionPerformed(ActionEvent e) throws Exception {
		if (this.editData.getId() == null)	{
			return;
		}
		SelectorItemCollection selectorColl = new SelectorItemCollection();
		selectorColl.add("fdcReceiveBill.purchase");
		ReceivingBillInfo receivingBill = ReceivingBillFactory.getRemoteInstance().getReceivingBillInfo(new ObjectUuidPK(this.editData.getId()),selectorColl);
		FDCReceiveBillInfo fdcReceiveBillInfo = receivingBill.getFdcReceiveBill();
		if(fdcReceiveBillInfo.getPurchase() != null)	{
			String billId = fdcReceiveBillInfo.getPurchase().getId().toString();
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", billId);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(PurchaseEditUI.class.getName(), uiContext,null, "VIEW");
			uiWindow.show();
		}
	}
	
	
}
