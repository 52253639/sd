package com.kingdee.eas.fdc.sellhouse.app;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
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
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.sellhouse.IRoom;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomKeepCustomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillCollection;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillFactory;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillInfo;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBizEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
//import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBizEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.fi.gl.common.UIContextUtil;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;

public class RoomKeepDownBillControllerBean extends AbstractRoomKeepDownBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.RoomKeepDownBillControllerBean");
    
    protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	
    	RoomKeepDownBillInfo billInfo = (RoomKeepDownBillInfo) model;
    	billInfo.setBizDate(new java.util.Date());
    	IObjectPK pk = super._save(ctx, model);
    	
    	RoomInfo room = billInfo.getRoom();
    	room.setSellState(RoomSellStateEnum.KeepDown);
    	room.setLastKeepDownBill(billInfo);
    	SelectorItemCollection sels=new SelectorItemCollection();
		sels.add("sellState");
		sels.add("lastKeepDownBill.*");
		RoomFactory.getLocalInstance(ctx).updatePartial(room, sels);
		return pk;
    }
   
    protected void checkNameDup(Context ctx, FDCBillInfo billInfo)
	throws BOSException, EASBizException {
    
    }



    protected void _cancelKeepDown(Context ctx, String roomId, String billId) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		//db2 的不好处理
//		final String updateRoomSql = "update T_SHE_Room set FSellState=?,FLastKeepDownBillID=? where FID=?";
//    	DbUtil.executeQuery(ctx, updateRoomSql, new Object[]{RoomSellStateEnum.ONSHOW_VALUE, null, roomId});
//		final String updateBillSql = "update T_SHE_RoomKeepDownBill set FCancelDate=?,FCancelStaffID=? where FRoomID=? and FCancelDate is null";
//    	DbUtil.execute(ctx, updateBillSql, new Object[]{new Timestamp(new java.util.Date().getTime()),user, roomId});
//    	
		IRoom iroom = RoomFactory.getLocalInstance(ctx);
    	RoomInfo room = new RoomInfo();
    	if(null!=roomId){
			room.setId(BOSUuid.read(roomId));
    
    	SelectorItemCollection seleRoom = new SelectorItemCollection();
    	seleRoom.add("isPush");
    	RoomInfo roomIsPush = iroom.getRoomInfo(new ObjectUuidPK(BOSUuid.read(roomId)), seleRoom);
    	if(true==roomIsPush.isIsPush()){
    		room.setSellState(RoomSellStateEnum.OnShow);
    	}else{
    		room.setSellState(RoomSellStateEnum.Init);
    	}
    	room.setLastKeepDownBill(null);
    	SelectorItemCollection updatRoom = new SelectorItemCollection();
    	updatRoom.add("sellState");
    	updatRoom.add("lastKeepDownBill");
    	iroom.updatePartial(room, updatRoom);
		}
    	
		if(null!=billId){
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("id");
		    selector.add("cancelDate");
		    selector.add("cacelStaff");
		    selector.add("bizState");
		    UserInfo userinfo = (UserInfo)ContextUtil.getCurrentUserInfo(ctx);
		    
		    RoomKeepDownBillInfo roomKeepDownBill = new RoomKeepDownBillInfo();
		    roomKeepDownBill.setId(BOSUuid.read(billId));
		    roomKeepDownBill.setCancelDate(new Timestamp(new java.util.Date().getTime()));
		    roomKeepDownBill.setCacelStaff(userinfo);
		    roomKeepDownBill.setBizState(RoomKeepDownBizEnum.CancelKeepDown);	
		    
		    RoomKeepDownBillFactory.getLocalInstance(ctx).updatePartial(roomKeepDownBill, selector);
		}
    	}
	
	 protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		 RoomInfo room = ((RoomKeepDownBillInfo)model).getRoom();
		 if(null!=room ){
		 SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("sellState");
			room.setSellState(RoomSellStateEnum.KeepDown);
			RoomFactory.getLocalInstance(ctx).updatePartial(room, selector);
		 }
		 //业务状态变化
		 ((RoomKeepDownBillInfo)model).setBizState(RoomKeepDownBizEnum.Submitted);
		return  super._submit(ctx, model);
		}
	 /**
		 * @description 根据当前客户，能否对房间进行转认购，转签约等操作
		 * @author tim_gao
		 */

	protected boolean _checkRoomKeepDown(Context ctx, String roomId, IObjectValue sheCustomer) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		SHECustomerInfo sheCus =(SHECustomerInfo)sheCustomer;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id",roomId));
		filter.getFilterItems().add(new FilterItemInfo("cancelDate",null));
		filter.getFilterItems().add(new FilterItemInfo("cacelStaff",null));
		view.setFilter(filter);
		RoomKeepDownBillCollection roomKeepDownBillCol = RoomKeepDownBillFactory.getLocalInstance(ctx).getRoomKeepDownBillCollection(view);
		if(null!=roomKeepDownBillCol&&roomKeepDownBillCol.size()>0){
			for(int i =0 ; i<roomKeepDownBillCol.size();i++){
				RoomKeepDownBillInfo roomKeepDownBill = roomKeepDownBillCol.get(i);
				for(int j =0 ; i<roomKeepDownBill.getCustomerEntry().size() ; j++){
					RoomKeepCustomerEntryInfo roomKeepDownCus = roomKeepDownBill.getCustomerEntry().get(j);
					//客户ID的对比
					if(sheCus.getId().equals(roomKeepDownCus.getCustomer().getId())){
						return true;
					}
				}
			}
		}
		return false;
	}

	protected void _setAudittingStatus(Context ctx, BOSUuid billId)
	throws BOSException, EASBizException {
		super._setAudittingStatus(ctx, billId);
		RoomKeepDownBillInfo billInfo = new RoomKeepDownBillInfo();
		
		billInfo.setId(billId);
		billInfo.setBizState(RoomKeepDownBizEnum.Auditting);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("bizState");
	
	
		_updatePartial(ctx, billInfo, selector);
		

	}

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
	EASBizException {
		super._audit(ctx, billId);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("bizState");
		RoomKeepDownBillInfo billInfo = new RoomKeepDownBillInfo();
		
		billInfo.setId(billId);
		billInfo.setBizState(RoomKeepDownBizEnum.Auditted);
		this._updatePartial(ctx,  billInfo, selector);
	
	}
	
}