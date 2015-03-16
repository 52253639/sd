package com.kingdee.eas.fdc.sellhouse.app;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.BatchManageFactory;
import com.kingdee.eas.fdc.sellhouse.BatchManageSourceEnum;
import com.kingdee.eas.fdc.sellhouse.BatchRoomEntryFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.HabitationRecordFactory;
import com.kingdee.eas.fdc.sellhouse.HabitationRecordInfo;
import com.kingdee.eas.fdc.sellhouse.HabitationStatusEnum;
import com.kingdee.eas.fdc.sellhouse.IRoom;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinCollection;
import com.kingdee.eas.fdc.sellhouse.RoomJoinInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinStateEnum;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.util.app.ContextUtil;

public class RoomJoinControllerBean extends AbstractRoomJoinControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.RoomJoinControllerBean");

	protected void _batchSave(Context ctx, IObjectCollection roomJoins) throws BOSException, EASBizException {
		for(int i=0; i<roomJoins.size(); i++){
			this.submit(ctx, (CoreBaseInfo) roomJoins.getObject(i));
		}
	}
	
	protected void _batchSave(Context ctx, List idList, Map valueMap)
			throws BOSException, EASBizException {
		FilterInfo filterexist = new FilterInfo();
		RoomJoinCollection roomJoinColl = new RoomJoinCollection();
		IRoom iRoom = RoomFactory.getLocalInstance(ctx);
		Date joinDate = (Date) valueMap.get("joinDate");
		Time joinStartDate = (Time) valueMap.get("joinStartDate");
		Time joinEndDate = (Time) valueMap.get("joinEndDate");
		Date appJoinDate = (Date) valueMap.get("appJoinDate");
		Date calcFeeDate = (Date) valueMap.get("calcFeeDate");
		Date finishDate = (Date) valueMap.get("finishDate");
		String description = (String) valueMap.get("description");
		UserInfo transactor = (UserInfo) valueMap.get("transactor");
		UserInfo creator = ContextUtil.getCurrentUserInfo(ctx);
		Timestamp creatTime = new Timestamp(new Date().getTime());
		for (int i = 0, size = idList.size(); i < size; i++) {
			filterexist = new FilterInfo();
			filterexist.getFilterItems().add(new FilterItemInfo("room.id", idList.get(i)));
			if (super.exists(ctx, filterexist)) {
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(filterexist);
				RoomJoinInfo roomJoin = super.getRoomJoinCollection(ctx, view).get(0);
				roomJoin.setJoinDate(joinDate);
				roomJoin.setJoinStartDate(joinStartDate);
				roomJoin.setJoinEndDate(joinEndDate);
				roomJoin.setApptJoinDate(appJoinDate);
				roomJoin.setCalcFeeDate(calcFeeDate);
				roomJoin.setFinishDate(finishDate);
				if (description != null && (!description.equals("")))
					roomJoin.setDescription(description);
				roomJoin.setTransactor(transactor);
				_save(ctx, roomJoin);
			} else {
				RoomInfo room = iRoom.getRoomInfo(new ObjectUuidPK(BOSUuid.read(idList.get(i).toString())));
				if (joinDate != null) {
					room.setRoomJoinState(RoomJoinStateEnum.JOINED);
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("roomJoinState");
					iRoom.updatePartial(room, selector);
				} else {
					room.setRoomJoinState(RoomJoinStateEnum.NOTJOIN);
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("roomJoinState");
					iRoom.updatePartial(room, selector);
				}
				RoomJoinInfo roomJoin = new RoomJoinInfo();
				roomJoin.setRoom(room);
				roomJoin.setJoinDate(joinDate);
				roomJoin.setJoinStartDate(joinStartDate);
				roomJoin.setJoinEndDate(joinEndDate);
				roomJoin.setApptJoinDate(appJoinDate);
				roomJoin.setCalcFeeDate(calcFeeDate);
				roomJoin.setFinishDate(finishDate);
				roomJoin.setDescription(description);
				roomJoin.setTransactor(transactor);
				roomJoin.setCreator(creator);
				roomJoin.setCreateTime(creatTime);
				_save(ctx, roomJoin);
				// roomJoinColl.add(roomJoin);
			}
		}

		// if (roomJoinColl.size() > 0)
		// super._addnew(ctx, roomJoinColl);
	}

	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		RoomJoinInfo info = (RoomJoinInfo) model;
		IRoom iRoom = RoomFactory.getLocalInstance(ctx);
		if (info.getRoom() != null && info.getRoom().getId() != null
				&& info.getActualFinishDate() != null) {
			RoomInfo room = iRoom.getRoomInfo(new ObjectUuidPK(info.getRoom().getId()));
			room.setRoomJoinState(RoomJoinStateEnum.JOINED);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("roomJoinState");
			iRoom.updatePartial(room, selector);
		} else if (info.getRoom() != null && info.getRoom().getId() != null) {
			RoomInfo room = iRoom.getRoomInfo(new ObjectUuidPK(info.getRoom().getId()));
			room.setRoomJoinState(RoomJoinStateEnum.JOINING);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("roomJoinState");
			iRoom.updatePartial(room, selector);
		}
		return super._save(ctx, model);
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		RoomJoinInfo info = (RoomJoinInfo) model;
		IRoom iRoom = RoomFactory.getLocalInstance(ctx);		
		
		if (info.getRoom() != null && info.getRoom().getId() != null) {
			RoomInfo room = iRoom.getRoomInfo(new ObjectUuidPK(info.getRoom().getId()));
			if(info.getActualFinishDate() != null){
				room.setRoomJoinState(RoomJoinStateEnum.JOINED);
				
				//新增房间入住记录
				HabitationRecordInfo record = new HabitationRecordInfo();
				
				//入伙办理自动写成业主
				record.setHabitationStatus(HabitationStatusEnum.Owner);
				record.setHabitationDate(info.getJoinEndDate());
				
				HabitationRecordFactory.getLocalInstance(ctx).addnew(record);
			}else{
				room.setRoomJoinState(RoomJoinStateEnum.JOINING);
			}
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("roomJoinState");
			iRoom.updatePartial(room, selector);
			
			//增加客户的物业属性
			PurchaseInfo purchaseInfo = room.getLastPurchase();
			if(purchaseInfo != null){
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				view.setFilter(filter);
				view.getSelector().add("id");
				view.getSelector().add("customer.id");
				view.getSelector().add("customer.isForPPM");
				filter.getFilterItems().add(new FilterItemInfo("head.id", purchaseInfo.getId().toString()));
				PurchaseCustomerInfoCollection purchaseCustomerInfoCollection = PurchaseCustomerInfoFactory.getLocalInstance(ctx).getPurchaseCustomerInfoCollection(view);
				for(int i=0; i<purchaseCustomerInfoCollection.size(); i++){
					PurchaseCustomerInfoInfo purchaseCustomerInfoInfo = purchaseCustomerInfoCollection.get(i);
					FDCCustomerInfo fdcCustomer = purchaseCustomerInfoInfo.getCustomer();
					if(fdcCustomer != null){
						if(!fdcCustomer.isIsForPPM()){
							fdcCustomer.setIsForPPM(true);
							SelectorItemCollection sels = new SelectorItemCollection();
							sels.add("isForPPM");
							FDCCustomerFactory.getLocalInstance(ctx).updatePartial(fdcCustomer, sels);	
						}
					}
				}
			}
		}
		return super._submit(ctx, model);
	}

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		RoomJoinInfo info = super.getRoomJoinInfo(ctx, pk);
		if (info.getRoom() != null) {
			info.getRoom().setRoomJoinState(RoomJoinStateEnum.NOTJOIN);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("roomJoinState");
			RoomFactory.getLocalInstance(ctx).updatePartial(info.getRoom(),
					selector);
		}
		super._delete(ctx, pk);
	}

	protected boolean isUseName() {
		return false;
	}

	protected void checkBill(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		FDCBillInfo FDCBillInfo = ((FDCBillInfo) model);
		if (FDCBillInfo.getNumber() != null) {
			super.checkBill(ctx, model);
		}
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK)
			throws BOSException, EASBizException {
		for (int i = 0; i < arrayPK.length; i++) {
			//处理房间实体的入伙状态
			RoomJoinInfo info = super.getRoomJoinInfo(ctx, arrayPK[i]);
			if (info.getRoom() != null) {
				info.getRoom().setRoomJoinState(RoomJoinStateEnum.NOTJOIN);
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("roomJoinState");
				RoomFactory.getLocalInstance(ctx).updatePartial(info.getRoom(), selector);
			}
			
			//删除批次对应的房间分录
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("batchManage.source", BatchManageSourceEnum.JOIN_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("room.id", info.getRoom().getId()));
			BatchRoomEntryFactory.getLocalInstance(ctx).delete(filter);
			
			//如果批次下没有房间分录，则删除批次
			if(info.getBatchManage() != null ){
				FilterInfo batchRoomFilter = new FilterInfo();
				batchRoomFilter.getFilterItems().add(new FilterItemInfo("batchManage.id", info.getBatchManage().getId().toString()));
				//批次下不存在房间分录，删除批次
				if(!BatchRoomEntryFactory.getLocalInstance(ctx).exists(batchRoomFilter)){
					BatchManageFactory.getLocalInstance(ctx).delete(new ObjectUuidPK(info.getBatchManage().getId().toString()));
				}
			}
		}
		super._delete(ctx, arrayPK);
	}
	// 设置组织
	protected void setPropsForBill(Context ctx, FDCBillInfo fDCBillInfo)
			throws EASBizException, BOSException {
		if (fDCBillInfo.getOrgUnit() == null) {
			FullOrgUnitInfo orgUnit = ContextUtil.getCurrentSaleUnit(ctx)
					.castToFullOrgUnitInfo();
			fDCBillInfo.setOrgUnit(orgUnit);
		}
	}
	protected String getCurrentOrgId(Context ctx) {
		SaleOrgUnitInfo org = ContextUtil.getCurrentSaleUnit(ctx);
		String curOrgId = org.getId().toString();
		return curOrgId;
	}

}