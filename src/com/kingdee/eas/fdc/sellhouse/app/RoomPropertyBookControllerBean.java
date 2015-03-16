package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;

import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.sellhouse.IRoom;
import com.kingdee.eas.fdc.sellhouse.RoomBookStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinCollection;
import com.kingdee.eas.fdc.sellhouse.RoomJoinInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.sellhouse.RoomPropertyBookCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.app.DataBaseControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;

public class RoomPropertyBookControllerBean extends
		AbstractRoomPropertyBookControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.RoomPropertyBookControllerBean");

	protected void _batchSave(Context ctx, List idList, Map valueMap)
			throws BOSException, EASBizException {
		FilterInfo filterexist = new FilterInfo();
		RoomPropertyBookCollection roomPropertyBookColl = new RoomPropertyBookCollection();
		IRoom iRoom = RoomFactory.getLocalInstance(ctx);
		Date reportDate = (Date) valueMap.get("reportDate");
		Date approveDate = (Date) valueMap.get("approveDate");
		Date drawDate = (Date) valueMap.get("drawDate");
		Date transactDate = (Date) valueMap.get("transactDate");
		Date actFinishDate = null;
		if(valueMap.get("actFinishDate") != null){
			actFinishDate = (Date)valueMap.get("actFinishDate");
		}
		Date recordDate = (Date) valueMap.get("recordDate");
		String description = (String) valueMap.get("description");
		UserInfo transactor = (UserInfo) valueMap.get("transactor");
		UserInfo creator = ContextUtil.getCurrentUserInfo(ctx);
		Timestamp creatTime = new Timestamp(new Date().getTime());
		SelectorItemCollection selectorRoom = new SelectorItemCollection();
		selectorRoom.add("id");
		selectorRoom.add("*");
		for (int i = 0, size = idList.size(); i < size; i++) {
			filterexist = new FilterInfo();
			filterexist.getFilterItems().add(
					new FilterItemInfo("room.id", idList.get(i)));
			if (super.exists(ctx, filterexist)) {
				EntityViewInfo view = new EntityViewInfo();
				view.setFilter(filterexist);
				RoomPropertyBookInfo roomPropertyBook = super
						.getRoomPropertyBookCollection(ctx, view).get(0);
				if (reportDate != null)
					roomPropertyBook.setReportDate(reportDate);
				if (approveDate != null)
					roomPropertyBook.setApproveDate(approveDate);
				if (drawDate != null)
					roomPropertyBook.setDrawDate(drawDate);
				if (transactDate != null)
					roomPropertyBook.setTransactDate(transactDate);
				if (recordDate != null)
					roomPropertyBook.setRecordDate(recordDate);
				if (description != null && (!description.equals("")))
					roomPropertyBook.setDescription(description);
				if (transactor != null)
					roomPropertyBook.setTransactor(transactor);
				_save(ctx, roomPropertyBook);
			} else {
				RoomInfo room = iRoom.getRoomInfo(new ObjectUuidPK(BOSUuid
						.read(idList.get(i).toString())), selectorRoom);
				if (actFinishDate != null) {
					room.setRoomBookState(RoomBookStateEnum.BOOKED);
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("roomBookState");
					iRoom.updatePartial(room, selector);
				} else {
					room.setRoomBookState(RoomBookStateEnum.NOTBOOKED);
					SelectorItemCollection selector = new SelectorItemCollection();
					selector.add("roomBookState");
					iRoom.updatePartial(room, selector);
				}
				RoomPropertyBookInfo roomPropertyBook = new RoomPropertyBookInfo();
				roomPropertyBook.setRoom(room);
				roomPropertyBook.setReportDate(reportDate);
				roomPropertyBook.setApproveDate(approveDate);
				roomPropertyBook.setDrawDate(drawDate);
				roomPropertyBook.setTransactDate(transactDate);
				roomPropertyBook.setRecordDate(recordDate);
				roomPropertyBook.setDescription(description);
				roomPropertyBook.setTransactor(transactor);
				roomPropertyBook.setCreator(creator);
				roomPropertyBook.setCreateTime(creatTime);
				_save(ctx, roomPropertyBook);
				// roomPropertyBookColl.add(roomPropertyBook);
			}
		}

		// if(roomPropertyBookColl.size()>0)
		// super._save(ctx, roomPropertyBookColl);
	}

	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		RoomPropertyBookInfo info = (RoomPropertyBookInfo) model;
		IRoom iRoom = RoomFactory.getLocalInstance(ctx);
		if (info.getRoom() != null && info.getRoom().getId() != null
				&& info.getActualFinishDate() != null) {  //办理日期有值，表示办理完成
			RoomInfo room = iRoom.getRoomInfo(new ObjectUuidPK(info.getRoom().getId()));
			room.setRoomBookState(RoomBookStateEnum.BOOKED);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("roomBookState");
			iRoom.updatePartial(room, selector);
		} else if (info.getRoom() != null && info.getRoom().getId() != null) {  //否则，办理中
			RoomInfo room = iRoom.getRoomInfo(new ObjectUuidPK(info.getRoom().getId()));
			room.setRoomBookState(RoomBookStateEnum.BOOKING);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("roomBookState");
			iRoom.updatePartial(room, selector);
		}
		return super._save(ctx, model);
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		RoomPropertyBookInfo info = (RoomPropertyBookInfo) model;
		IRoom iRoom = RoomFactory.getLocalInstance(ctx);
		if (info.getRoom() != null && info.getRoom().getId() != null
				&& info.getActualFinishDate() != null) {
			RoomInfo room = iRoom.getRoomInfo(new ObjectUuidPK(info.getRoom().getId()));
			room.setRoomBookState(RoomBookStateEnum.BOOKED);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("roomBookState");
			iRoom.updatePartial(room, selector);
		} else if (info.getRoom() != null && info.getRoom().getId() != null) {
			RoomInfo room = iRoom.getRoomInfo(new ObjectUuidPK(info.getRoom().getId()));
			room.setRoomBookState(RoomBookStateEnum.NOTBOOKED);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("roomBookState");
			iRoom.updatePartial(room, selector);
		}
		return super._submit(ctx, model);
	}

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		RoomPropertyBookInfo info = super.getRoomPropertyBookInfo(ctx, pk);
		if (info.getRoom() != null) {
			info.getRoom().setRoomBookState(RoomBookStateEnum.NOTBOOKED);
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("roomBookState");
			RoomFactory.getLocalInstance(ctx).updatePartial(info.getRoom(),
					selector);
		}
		super._delete(ctx, pk);
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK)
			throws BOSException, EASBizException {
		for (int i = 0; i < arrayPK.length; i++) {
			RoomPropertyBookInfo info = super.getRoomPropertyBookInfo(ctx,
					arrayPK[i]);
			if (info.getRoom() != null) {
				info.getRoom().setRoomBookState(RoomBookStateEnum.NOTBOOKED);
				SelectorItemCollection selector = new SelectorItemCollection();
				selector.add("roomBookState");
				RoomFactory.getLocalInstance(ctx).updatePartial(info.getRoom(),
						selector);
			}
		}
		super._delete(ctx, arrayPK);
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