package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
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
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo;
import com.kingdee.eas.fdc.sellhouse.GatheringEnum;
import com.kingdee.eas.fdc.sellhouse.GatheringObjectEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiveBillTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCRoomPromptDialog;
import com.kingdee.eas.fdc.sellhouse.client.ReceiveBillEidtUI;
import com.kingdee.eas.fdc.sellhouse.client.SelectPaymentListUI;
import com.kingdee.eas.fdc.tenancy.ITenancyPayListInfo;
import com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillCollection;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryInfo;
import com.kingdee.eas.fi.cas.ReceivingBillInfo;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

public class TENReceiveBillEditUI extends ReceiveBillEidtUI {
	
	private static final Logger logger = CoreUIObject.getLogger(TENReceiveBillEditUI.class);
	
	public TENReceiveBillEditUI() throws Exception {
		super();
	}
	
	protected MoneySysTypeEnum getSystemType() {
		return MoneySysTypeEnum.TenancySys;
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		
		//TODO
		MsgBox.showInfo(this, "租赁收款单已使用新菜单，请使用新收款单菜单。");
		
		//旧收款单功能屏蔽只让查看
		this.actionEdit.setVisible(false);
		this.actionVoucher.setVisible(false);
		this.actionAudit.setVisible(false);
		this.actionRec.setVisible(false);
	}
	
	/**
	 * 租赁系统装载数据
	 */
	protected void loadFieldsForSys(ReceivingBillInfo receivingBillInfo,FDCReceiveBillInfo fdcReceiveBillInfo)
	{
		//前面已经判断，这里其实不可能为空
		if(fdcReceiveBillInfo.getMoneySysType() == null)		{
			this.gatheringObject = GatheringObjectEnum.room;
		}else	{
			this.gatheringObject = fdcReceiveBillInfo.getGatheringObject();
		}
		
		RoomInfo room = fdcReceiveBillInfo.getRoom();
		if (room != null){
			this.f7Room.setUserObject(room);
			this.f7Room.setValue(room);
			this.f7Room.setText(room.getBuilding().getName() + "-" + room.getNumber());
			//TODO 为租赁合同设置过滤条件
			
			this.f7SellOrder.setValue(null);//租赁没有盘次，可能会增加放租盘次
		}
		TenancyBillInfo tenancyBillInfo = fdcReceiveBillInfo.getTenancyContract();
		if (tenancyBillInfo != null)
		{
			this.f7Salesman.setValue(tenancyBillInfo.getTenancyAdviser());
		}
		
		if (this.getOprtState().equals(OprtState.EDIT)) {
			this.f7Room.setEnabled(false);
			this.f7TenancyContract.setEnabled(false);

			if (fdcReceiveBillInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.LateFee) || fdcReceiveBillInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.CommissionCharge)
					|| fdcReceiveBillInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.ElseAmount)) {
				this.txtReceiveAmount.setEnabled(true);
			} else {
			}
		} else if (this.getOprtState().equals(OprtState.VIEW)) {
			this.f7Room.setEnabled(false);
			this.f7TenancyContract.setEnabled(false);
		}

		try {
			this.setF7SysCustomerFilterForTEN();
		} catch (BOSException e) {
			this.handleException(e);
		}
	}
	
	
	/**
	 * 租赁系统 设置客户的过滤条件
	 * 主要是通过租赁合同来过滤的，这里有想过通过房间来过滤，虽然可以过滤掉一部分客户，但是没有实际意义，范围太广
	 */
	private void setF7SysCustomerFilterForTEN() throws BOSException
	{
		EntityViewInfo queryView = new EntityViewInfo();
		queryView.getSelector().add("fdcCustomer.sysCustomer.*");
		FilterInfo queryFilter = new FilterInfo();
		TenancyBillInfo tenancyBillInfo = (TenancyBillInfo) this.f7TenancyContract.getValue();
		if (tenancyBillInfo != null)
		{
			queryFilter.getFilterItems().add(new FilterItemInfo("tenancyBill", tenancyBillInfo.getId().toString()));
		}
		queryView.setFilter(queryFilter);
		TenancyCustomerEntryCollection tenancyCustomerEntryColl = TenancyCustomerEntryFactory.getRemoteInstance().getTenancyCustomerEntryCollection(queryView);

		Set set = new HashSet();
		for (int i = 0; i < tenancyCustomerEntryColl.size(); i++)
		{
			TenancyCustomerEntryInfo tenancyCustomerEntryInfo = tenancyCustomerEntryColl.get(i);
			FDCCustomerInfo fdcCustomerInfo = tenancyCustomerEntryInfo.getFdcCustomer();
			CustomerInfo sysCustomerInfo = fdcCustomerInfo.getSysCustomer();
			if (sysCustomerInfo == null)
			{
				logger.warn("认购的房地产客户未同步,请仔细检查. fdcCustomerId:"+ fdcCustomerInfo.getId().toString());
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
		if(set.isEmpty()){
			filter.getFilterItems().add(new FilterItemInfo("id", "idnull"));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("id", set, CompareType.INCLUDE));
		}
		this.f7Customer.setEntityViewInfo(view);
	}
	
	
	/**
	 * 租赁合同F7事件
	 */
	protected void f7TenancyContract_dataChanged(DataChangeEvent e) throws Exception
	{
		if(!this.lcTenancyContract.isVisible())
		{
			return;
		}
		RoomInfo room = (RoomInfo) this.f7Room.getValue();
		TenancyBillInfo tenancyBillInfo = (TenancyBillInfo) this.f7TenancyContract.getValue();
		//以防有些属性带不出来，重新取属性
		if(tenancyBillInfo != null)
		{
			tenancyBillInfo = TenancyHelper.getTenancyBillInfo(TenancyBillFactory.getRemoteInstance(),tenancyBillInfo.getId().toString());
		}
		
		//如果是从租赁合同那边，直接传合同参数过来收款则不判断房间是否为空
		if (this.getUIContext().get(KEY_TENANCYBILLID) != null)
		{
			if (tenancyBillInfo != null)
			{
				this.f7Salesman.setValue(tenancyBillInfo.getTenancyAdviser());
				TenancyCustomerEntryCollection tenancyCustomerEntryColl = tenancyBillInfo.getTenCustomerList();;
				if (tenancyCustomerEntryColl.size() > 0)
				{
					CustomerInfo sysCustomer = tenancyCustomerEntryColl.get(0).getFdcCustomer().getSysCustomer();
					this.f7Customer.setValue(sysCustomer);
				}
			} 
			else
			{
				this.txtReceiveAmount.setValue(null);
			}
		}
		else
		{
			if (room == null)
			{
				MsgBox.showInfo("请先选择房间！");
				return;
			}
			if (tenancyBillInfo != null)
			{
				this.f7Salesman.setValue(tenancyBillInfo.getTenancyAdviser());
				
				TenancyCustomerEntryCollection tenancyCustomerEntryColl = tenancyBillInfo.getTenCustomerList();;
				if (tenancyCustomerEntryColl.size() > 0)
				{
					CustomerInfo sysCustomer = tenancyCustomerEntryColl.get(0).getFdcCustomer().getSysCustomer();
					this.f7Customer.setValue(sysCustomer);
				}
			} 
		}
		this.setF7SysCustomerFilterForTEN();
		
		//对f7选择房间事件进行范围过滤
		if(tenancyBillInfo != null)
		{
			TenancyRoomEntryCollection treColl = tenancyBillInfo.getTenancyRoomList();
			RoomCollection roomColl = new RoomCollection();
			if(treColl != null)
			{
				for(int i = 0; i < treColl.size(); i ++)
				{
					roomColl.add(treColl.get(i).getRoom());
				}
			}
			if(roomColl.size() > 0)
			{
				f7Room.setSelector(new FDCRoomPromptDialog(Boolean.FALSE,buildingInfo,buildUnit,MoneySysTypeEnum.TenancySys, roomColl));
			}
			else
			{
				f7Room.setSelector(new FDCRoomPromptDialog(Boolean.FALSE,buildingInfo,buildUnit,MoneySysTypeEnum.TenancySys, null));
			}
		}
		else
		{
			f7Room.setSelector(new FDCRoomPromptDialog(Boolean.FALSE,buildingInfo,buildUnit,MoneySysTypeEnum.TenancySys, null));
		}
		this.kDTable1.removeRows();
	}
	
	
	
	/**
	 * 添加租赁的付款明细
	 * @param payColl
	 * @param settlementMap
	 */
	private void addRowByTenancyRoomPayEntryColl(IObjectCollection payColl,Map settlementMap)
	{
		if(payColl==null) return;
		for(int i = 0;i < payColl.size(); i ++)
		{
			IRow row = this.kDTable1.addRow();
			ITenancyPayListInfo info = (ITenancyPayListInfo) payColl.getObject(i);
			
			BigDecimal appAmount = info.getAppAmount();
			if(appAmount != null)
				appAmount = appAmount.setScale(2,BigDecimal.ROUND_HALF_UP);
			else
				appAmount = FDCHelper.ZERO;
			
			BigDecimal actAmount = info.getActAmount();
			if(actAmount != null)
				actAmount = actAmount.setScale(2,BigDecimal.ROUND_HALF_UP);
			else
				actAmount = FDCHelper.ZERO;
			
			row.getCell("moneyType").setValue(info.getMoneyDefine());
			//根据款项自动带出科目
			setAccountByMoneyDefine(info.getMoneyDefine(), row);
			
			row.getCell("appAmount").setValue(appAmount);
			row.getCell("actAmount").setValue(actAmount);
			BigDecimal canRefundmentAmount = info.getCanRefundmentAmount();
			if(canRefundmentAmount != null)
				canRefundmentAmount = canRefundmentAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			else
				canRefundmentAmount = FDCHelper.ZERO;
			row.getCell("canRefundmentAmount").setValue(canRefundmentAmount);
			
			String id = info.getStrId();
			
			row.getCell("id").setValue(id);
			
			if(settlementMap.get(id) != null)
			{
				if (settlementMap.get(id) instanceof Object[])
				{
					Object[] o = (Object[]) settlementMap.get(id);

					if (o != null)
					{
						for (int j = 0; j < o.length; j++)
						{
							if (j == 0)
							{
								row.getCell("settlementType").setValue(o[j]);
								
								//如果只有一行的話，就帶出收款金額或者退款金額
								if (o.length == 1)
								{
									BigDecimal gatheringAmount = appAmount.subtract(actAmount);

									row.getCell("refundmentAmount").setValue(canRefundmentAmount);
									row.getCell("gatheringAmount").setValue(gatheringAmount);
									updateAmountTotal();
								}
							} else
							{
								IRow tempRow = this.kDTable1.addRow();
								tempRow.getCell("id").setValue(id);
								tempRow.getCell("moneyType").setValue(
										info.getMoneyDefine());
								tempRow.getCell("settlementType")
										.setValue(o[j]);
								
								//根据款项自动带出科目
								setAccountByMoneyDefine(info.getMoneyDefine(), tempRow);
							}
						}
						// 融合单元格
						this.kDTable1.getMergeManager().mergeBlock(
								row.getRowIndex(), 0,
								row.getRowIndex() + o.length - 1, 0);

						this.kDTable1.getMergeManager().mergeBlock(
								row.getRowIndex(),
								row.getCell("appAmount").getColumnIndex(),
								row.getRowIndex() + o.length - 1,
								row.getCell("appAmount").getColumnIndex());

						this.kDTable1.getMergeManager().mergeBlock(
								row.getRowIndex(),
								row.getCell("actAmount").getColumnIndex(),
								row.getRowIndex() + o.length - 1,
								row.getCell("actAmount").getColumnIndex());
					}
				}
				else if(settlementMap.get(id) instanceof Object)
				{
					Object o =  settlementMap.get(id);
					if (o != null)
					{
						row.getCell("settlementType").setValue(o);
					}
				}
			}	
		}
	}

	
	
	protected void btnChoose_actionPerformed(ActionEvent e) throws Exception
	{
		this.verifyChoose();
		this.kDTable1.removeRows();
		
		if(moneyAccountMapping == null){
			initMoneyAccountMapping();
		}
		
		GatheringEnum selectedItem = (GatheringEnum) this.comboGathering.getSelectedItem();
		
		UIContext uiContext = new UIContext(ui);
		
		uiContext.put(KEY_MONEYTSYSTYPE, MoneySysTypeEnum.TenancySys);
		uiContext.put(KEY_BILLTYPE,this.recBillType);
		uiContext.put(KEY_GATHERING, selectedItem);			
		
		TenancyBillInfo tenancyBill = (TenancyBillInfo) this.f7TenancyContract.getValue();
		RoomInfo room = (RoomInfo) this.f7Room.getValue();
		TenAttachResourceEntryInfo tenAttach = (TenAttachResourceEntryInfo) this.f7Accessorial.getValue();
		uiContext.put(KEY_TenancyBill, tenancyBill);
		uiContext.put(Key_Room, room);
		uiContext.put(KEY_TEN_ATTACH, tenAttach);		
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SelectPaymentListUI.class.getName(), uiContext, null,OprtState.VIEW);
		uiWindow.show();
		
		if (uiWindow.getUIObject().getUIContext().get(KEY_OPT) == null ||
				!((Boolean) uiWindow.getUIObject().getUIContext().get(KEY_OPT)).booleanValue()){
			return;
		}
		
		Map settlementMap = (HashMap) uiWindow.getUIObject().getUIContext().get(KEY_SettlementType);
		Boolean isSettlement = (Boolean) uiWindow.getUIObject().getUIContext().get(Key_IsSettlement);
		if(isSettlement == null)
			isSettlement = Boolean.FALSE;
		//设置全局参数
		this.isSettlement = isSettlement.booleanValue();
		
		if (isSettlement.booleanValue()) {
			this.cbIsSettlement.setSelected(true);
			this.hongChongFdcRecEntryColl = (FDCReceiveBillEntryCollection) uiWindow.getUIObject().getUIContext().get(KEY_HongChongRecEntryColl);
		} else {
			this.cbIsSettlement.setSelected(false);
		}
		
		// 添加表格行
		if (this.isSettlement) {
			this.kDTable1.getColumn("gatheringAmount").getStyleAttributes().setLocked(true);
		} else {
			this.kDTable1.getColumn("gatheringAmount").getStyleAttributes().setLocked(false);
		}
		
		//以下分系统添加付款明细到列表中
		IObjectCollection tenPayEntryColl = (IObjectCollection) uiWindow.getUIObject().getUIContext().get(KEY_TenEntryPayColl);
		this.addRowByTenancyRoomPayEntryColl(tenPayEntryColl,settlementMap);

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
		
		this.doSomeThingAsTenancyRoomchosen(e);

		this.kDTable1.removeRows();
	}
	
	
	/**
	 * 当选择租赁房间的时候，所做的操作
	 * @throws UuidException 
	 * @throws EASBizException 
	 */
	private void doSomeThingAsTenancyRoomchosen(DataChangeEvent e) throws BOSException, EASBizException, UuidException
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
		Set tempSet = getQueryBillStates();
		TenancyBillCollection tenancyBillColl = this.getValidTenancyContractByRoomAndState(room, tempSet);		
		if(tenancyBillColl != null && tenancyBillColl.size() < 1)
		{
			logger.warn("未取得房间号为 "+ room.getNumber() +" 的，有效合同！");
			this.f7TenancyContract.setValue(null);
			this.f7TenancyContract.setEnabled(false);
			if(this.getOprtState() != OprtState.VIEW && this.getOprtState() != OprtState.EDIT)
			{
				MsgBox.showInfo("该房间没有进行收款操作的合同！");
			}
			this.abort();
		}
		
		this.f7Room.setUserObject(room);
		this.f7Room.setValue(room);
		this.f7SellOrder.setValue(room.getSellOrder());
		this.f7TenancyContract.setEnabled(true);
		this.setF7TenancyContractFilterByRoom(room, tempSet);
		
		if (tenancyBillColl != null && tenancyBillColl.size() == 1)
		{
			this.f7TenancyContract.setValue(tenancyBillColl.get(0));
			TenancyBillInfo tenancyBillInfo = tenancyBillColl.get(0);
			// 在租赁这边叫租赁顾问
			this.f7Salesman.setValue(tenancyBillInfo.getTenancyAdviser());

			TenancyCustomerEntryCollection tenancyCustomerEntryColl = tenancyBillInfo.getTenCustomerList();
			
			if (tenancyCustomerEntryColl != null && tenancyCustomerEntryColl.size() > 0)
			{
				CustomerInfo sysCustomer = tenancyCustomerEntryColl.get(0).getFdcCustomer().getSysCustomer();
				this.f7Customer.setValue(sysCustomer);
			}
		}//如果不给租赁合同赋值，则需判断租赁合同那里是不是已经有值了
		else
		{
			TenancyBillInfo tenancyBillInfo = (TenancyBillInfo) this.f7TenancyContract.getValue();
			MoneyDefineCollection moneyDefineColl = this.getValidMoneyNameByTenancyContract(tenancyBillInfo,room);
			if (moneyDefineColl !=null && moneyDefineColl.size() > 0)
			{
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				view.setFilter(filter);
				Set idSet = new HashSet();
				for (int i = 0; i < moneyDefineColl.size(); i++)
				{
					idSet.add(moneyDefineColl.get(i).getId().toString());
				}
				filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
			} 
			else
			{
			}
		}
		String sellProjectId = room.getBuilding().getSellProject().getId().toString();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("project.*");
		SellProjectInfo sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(BOSUuid.read(sellProjectId)), sels);
		this.f7SellProject.setValue(sellProject);
	}
	
	
	/**
	 * 获取该房间下可以进行收款操作的合同
	 * @param room
	 * @param billStateSet 合同的状态
	 * @return
	 * @throws BOSException
	 */
	private TenancyBillCollection getValidTenancyContractByRoomAndState(RoomInfo room, Set billStateSet) throws BOSException
	{
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("tenancy.id");
		view.setFilter(filter);
		
		filter.getFilterItems().add(new FilterItemInfo("room",room.getId().toString()));

		if(billStateSet != null && billStateSet.size() > 0)
		{
			filter.getFilterItems().add(new FilterItemInfo("tenancy.tenancyState",billStateSet,CompareType.INCLUDE));
		}
		TenancyRoomEntryCollection tenancyRoomEntryColl = TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryCollection(view);
		
		Set tenIds = new HashSet();
		for(int i =0; i < tenancyRoomEntryColl.size(); i ++)
		{
			tenIds.add(tenancyRoomEntryColl.get(i).getTenancy().getId().toString());
		}
		
		EntityViewInfo tenView = new EntityViewInfo();
		FilterInfo tenFilter = new FilterInfo();
		tenView.setFilter(tenFilter);
		tenFilter.getFilterItems().add(new FilterItemInfo("id", tenIds, CompareType.INCLUDE));
		tenView.getSelector().add("*");
		tenView.getSelector().add("tenCustomerList.sysCustomer.name");
		tenView.getSelector().add("tenCustomerList.fdcCustomer.*");
		tenView.getSelector().add("tenCustomerList.fdcCustomer.sysCustomer.name");
		tenView.getSelector().add("tenancyRoomList");
		
		return TenancyBillFactory.getRemoteInstance().getTenancyBillCollection(tenView);
	}
	
	
	/**
	 * 通过房间来设置合同的过滤条件
	 */
	private void setF7TenancyContractFilterByRoom(RoomInfo room, Set billStateSet) throws BOSException
	{
		TenancyRoomEntryCollection tenancyRoomEntryColl = new TenancyRoomEntryCollection();
		EntityViewInfo tenancyRoomEntryView = new EntityViewInfo();
		tenancyRoomEntryView.getSelector().add("tenancy.id");
		FilterInfo tenancyRoomEntryFilter = new FilterInfo();
		tenancyRoomEntryFilter.getFilterItems().add(new FilterItemInfo("room.id",room.getId().toString()));
		tenancyRoomEntryView.setFilter(tenancyRoomEntryFilter);
		tenancyRoomEntryColl = TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryCollection(tenancyRoomEntryView);
		
		Set tempSet = new HashSet();
		for(int i =0; i < tenancyRoomEntryColl.size(); i ++)
		{
			tempSet.add(tenancyRoomEntryColl.get(i).getTenancy().getId().toString());
		}
		
		EntityViewInfo view = new EntityViewInfo();
	
		FilterInfo filter = new FilterInfo();
		if(tempSet == null || tempSet.size() < 1)
		{
			filter.getFilterItems().add(new FilterItemInfo("id","never equal"));
		}
		else
		{
			filter.getFilterItems().add(new FilterItemInfo("id",tempSet,CompareType.INCLUDE));
		}
		//合同状态过滤
		if(billStateSet != null && billStateSet.size() > 0)
		{
			filter.getFilterItems().add(new FilterItemInfo("tenancyState",billStateSet,CompareType.INCLUDE));
		}
		
		view.setFilter(filter);
		this.f7TenancyContract.setEntityViewInfo(view);
	}
	
	
	/**
	 * 获得某个合同的某个房间的付款项
	 * @param tenancyBillInfo
	 * @param room
	 * @return
	 * @throws BOSException 
	 */
	private  MoneyDefineCollection getValidMoneyNameByTenancyContract(TenancyBillInfo tenancyBillInfo,RoomInfo room) throws BOSException
	{
		MoneyDefineCollection moneyDefineColl = new MoneyDefineCollection();
		if(tenancyBillInfo == null || room == null)
		{
			return moneyDefineColl;
		}
		
		//如果是退款，则直接返回退款
		if (ReceiveBillTypeEnum.refundment.equals(this.getUIContext().get(KEY_BILLTYPE)))
		{
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("*");
			view.getSelector().add("revBillType.*");
			FilterInfo filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("moneyType",	MoneyTypeEnum.REFUNDMENT_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.TENANCYSYS_VALUE));
			moneyDefineColl = MoneyDefineFactory.getRemoteInstance().getMoneyDefineCollection(view);
			return moneyDefineColl;
		}
		
		FilterInfo filter = new FilterInfo();
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("moneyDefine.*");
        view.getSelector().add("*");
		view.setFilter(filter);
		
		SorterItemCollection selColl = new SorterItemCollection();
		selColl.add(new SorterItemInfo("seq"));
		view.setSorter(selColl);
		filter.getFilterItems().add(new FilterItemInfo("tenRoom.room",room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("tenRoom.tenancy",tenancyBillInfo.getId().toString()));
		
		TenancyRoomPayListEntryCollection tenancyRoomPayListEntryColl = TenancyRoomPayListEntryFactory.getRemoteInstance().getTenancyRoomPayListEntryCollection(view);
	
		for(int i = 0; i < tenancyRoomPayListEntryColl.size(); i ++)
		{
			TenancyRoomPayListEntryInfo temp = tenancyRoomPayListEntryColl.get(i);
			BigDecimal actAmount = temp.getActAmount();
			if(actAmount == null)
			{
				actAmount = FDCHelper.ZERO;
			}
			BigDecimal appAmount = temp.getAppAmount();
			if(appAmount == null)
			{
				appAmount = FDCHelper.ZERO;
			}
			//未达到应缴金额
			if(actAmount.compareTo(appAmount) < 0)
			{
				moneyDefineColl.add(temp.getMoneyDefine());
//				查看的是，不能取按照常理赋值
				if(!OprtState.VIEW.equals(this.getOprtState()))
				{
					this.seq = temp.getSeq();
				}
				break;
			}
			else
			{
				continue;
			}
		}
		return moneyDefineColl;
	}
	
	
	
	/**
	 * 租赁系统 的 新建数据
	 */
	protected IObjectValue createNewDataForSys(ReceivingBillInfo receivingBillInfo, FDCReceiveBillInfo fdcReceiveBillInfo) throws EASBizException, BOSException, UuidException
	{
		String  roomId = (String)this.getUIContext().get(KEY_ROOMID);
		RoomInfo room = null;
		if(roomId != null)
		{
			SelectorItemCollection selColl = new SelectorItemCollection();
			selColl.add("*");
			selColl.add("building.*");
			selColl.add("building.sellProject.*");
			room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(BOSUuid.read(roomId)),selColl);
		}
		if (room != null)
		{
			fdcReceiveBillInfo.setRoom(room);
			String sellProjectId = room.getBuilding().getSellProject().getId().toString();
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("project.*");
			//TODO 这个查询有什么用？
			SellProjectInfo sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(
								new ObjectUuidPK(BOSUuid.read(sellProjectId)),sels);
			fdcReceiveBillInfo.setSellProject(sellProject);
		}
		//从租赁合同收款那边传过来的参数
		String tenancyBillId = (String) this.getUIContext().get(KEY_TENANCYBILLID);
		if (tenancyBillId == null)
		{
			if(room != null)
			{
				Set tempSet = getQueryBillStates();
				TenancyBillCollection tenancyBillColl = this.getValidTenancyContractByRoomAndState(room, tempSet);
				if(tenancyBillColl.size() == 1)
				{
					TenancyBillInfo tenancyBillInfo = tenancyBillColl.get(0);
					fdcReceiveBillInfo.setTenancyContract(tenancyBillInfo);
				}
				else if(tenancyBillColl.size() < 1)
				{
					MsgBox.showInfo("该房间没有符合收款操作的合同！");
					this.abort();
				}
			}
		} else
		{
			fdcReceiveBillInfo.setTenancyContract(TenancyHelper.getTenancyBillInfo(TenancyBillFactory.getRemoteInstance(),tenancyBillId));
		}
		
		if (fdcReceiveBillInfo.getTenancyContract() != null)
		{
			TenancyBillInfo tenancyBillInfo = fdcReceiveBillInfo.getTenancyContract();
			if (room != null)
			{
				//TODO 这个款项设置有用吗？
				MoneyDefineCollection moneyDefineColl = this.getValidMoneyNameByTenancyContract(tenancyBillInfo,room );
				if (moneyDefineColl.size() == 1)
				{
					fdcReceiveBillInfo.setMoneyDefine(moneyDefineColl.get(0));
				}
			}
			TenancyCustomerEntryCollection tenancyCustomerEntryColl = tenancyBillInfo.getTenCustomerList();
			if (tenancyCustomerEntryColl != null && tenancyCustomerEntryColl.size() > 0)
			{
				CustomerInfo sysCustomer = tenancyCustomerEntryColl.get(0).getFdcCustomer().getSysCustomer();
				if (sysCustomer != null)
				{
					receivingBillInfo.setPayerID(sysCustomer.getId().toString());
					receivingBillInfo.setPayerNumber(sysCustomer.getNumber());
					receivingBillInfo.setPayerName(sysCustomer.getName());
				}
			}
		}
		return receivingBillInfo;
	}
	
	
	/**
	 * 校验选择按钮
	 */
	protected void verifyChoose()
	{
			if(this.f7TenancyContract.getValue() == null)
			{
				MsgBox.showWarning("租赁合同为空，无法进行当前操作！"	);
				this.abort();
			}
			
			if(GatheringObjectEnum.room.equals(ComboGatheringOjbect.getSelectedItem())){
				if(this.f7Room.getValue() == null)
				{
					MsgBox.showWarning("请选择房间！");
					this.abort();
				}
			}else if(GatheringObjectEnum.accessorialResource.equals(ComboGatheringOjbect.getSelectedItem())){
				if(this.f7Accessorial.getValue() == null)
				{
					MsgBox.showWarning("请选择配套资源！");
					this.abort();
				}
			}else{
				MsgBox.showWarning("租赁系统下没有对设备的收款！");
				this.abort();
			}

	}
	
	
	/**
	 * 通过所选择的资金系统来控制界面上一些显示
	 * @param moneySysTypeEnum
	 * @param itemEvent
	 * @author laiquan_luo
	 */
	protected void  changeUIByMoneySys(MoneySysTypeEnum moneySysTypeEnum,ItemEvent itemEvent)
	{
			//如果租赁合同有值的话，那它就会设置，就不用这里设置
			if(this.f7TenancyContract.getValue() == null)
			{
				f7Room.setSelector(new FDCRoomPromptDialog(Boolean.FALSE,buildingInfo,buildUnit,moneySysTypeEnum, null));
			}
			this.lcTenancyContract.setVisible(true);
			this.getParent();
			if(this.f7Room.getValue() == null)
			{
				this.f7TenancyContract.setEnabled(false);
			}
			else
			{
				this.f7TenancyContract.setEnabled(true);
			}
			if(OprtState.EDIT.equalsIgnoreCase(this.getOprtState()))
			{
				this.f7TenancyContract.setEnabled(false);
			}
			this.contPurchase.setVisible(false);
			//直接传合同过来的，则需要选择房间
			if(this.getUIContext().get(KEY_MONEYTSYSTYPE) != null &&
					this.getUIContext().get(KEY_MONEYTSYSTYPE).equals(MoneySysTypeEnum.TenancySys))
			{
			   //this.btnChooseRoom.setVisible(false);
			   //this.contRoom.setVisible(false);
			   this.lcTenancyContract.setVisible(true);
			}
			this.contSalesman.setBoundLabelText("租赁顾问");
			
			if(this.kDTable1.getColumn("derateAmount") != null)
			this.kDTable1.getColumn("derateAmount").getStyleAttributes().setHided(true);
	}
	
	
	protected void setControlByGathering(GatheringEnum gathering) {
		if(GatheringEnum.ObligatedRoom.equals(gathering)){
			setControlVisible(true, false, false, false, false, true);
			this.f7Room.setRequired(true);
		}else if(GatheringEnum.ObligatedAttach.equals(gathering)){
			setControlVisible(false, true, false, false, false, true);
			this.f7Room.setRequired(false);
		}else if(GatheringEnum.TenRoom.equals(gathering)){
			setControlVisible(true, false, false, true, false, false);
			this.f7Room.setRequired(true);
		}else if(GatheringEnum.TenAttach.equals(gathering)){
			setControlVisible(false, true, false, true, false, false);
			this.f7Room.setRequired(false);
		}else if(GatheringEnum.CustomerRev.equals(gathering))	{
			setControlVisible(true, false, false, false, false, false);
			this.f7Room.setRequired(false);
		}
	}
	
	
	
	
	
	
}
