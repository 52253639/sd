package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomCollection;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomCusEntry;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomCusEntryInfo;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomFactory;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomInfo;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomSet;
import com.kingdee.eas.fdc.sellhouse.ChooseRoomStateEnum;
import com.kingdee.eas.fdc.sellhouse.CluesManageFactory;
import com.kingdee.eas.fdc.sellhouse.CluesManageInfo;
import com.kingdee.eas.fdc.sellhouse.CluesSourceEnum;
import com.kingdee.eas.fdc.sellhouse.CluesStatusEnum;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerFactory;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEParamConstant;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.basecrm.CreateWayEnum;
import com.kingdee.eas.fdc.basecrm.CustomerTypeEnum;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerInfo;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fm.common.DateHelper;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

public class ChooseRoomControllerBean extends AbstractChooseRoomControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.ChooseRoomControllerBean");



	protected boolean _isValid(Context ctx, String billId) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
//		RoomDisplaySetting set = new RoomDisplaySetting(ctx,new String[]{SHEParamConstant.CHOOSE_ROOM_SET_MAP});
		//参数
		Map chooseRoomParam =null;
		//当前时间
		Date CurTime  = new Date( System.currentTimeMillis());
		//选房日期
		Date bizDate = null;
		//房间
		String froomId =null;
		//楼栋
		String fBuildingId =null;
//		SelectorItemCollection selector = new SelectorItemCollection();
//		selector.add("*");
//		selector.add("room.building.id");
//		ChooseRoomInfo chooseRoom = new ChooseRoomInfo();
		FDCSQLBuilder dataSql = new FDCSQLBuilder();
		 dataSql.appendSql( "select fbizDate BIZDATE ,rm.fid ROOMID,bd.fid BUILDINGID from T_SHE_ChooseRoom cr ");
		 dataSql.appendSql("left outer join t_she_room rm on cr.froomid = rm.fid ");
		 dataSql.appendSql("left outer join t_she_building bd on rm.fbuildingid = bd.fid ");
		 dataSql.appendSql("where cr.fid = '"+billId.toString()+"'");
		 IRowSet rs = dataSql.executeQuery(ctx);
		 try {
			while (rs.next()) {
				bizDate=rs.getTimestamp("BIZDATE");
				froomId = rs.getString("ROOMID");
				fBuildingId = rs.getString("BUILDINGID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("房屋参数取值异常！");
			SysUtil.abort();
		}
//		ChooseRoomInfo chooseRoom = (ChooseRoomInfo) this.getChooseRoomInfo(ctx, new ObjectUuidPK(BOSUuid.read(billId)),selector);
//		java.text.SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
//		bizDate =FDCDateHelper.stringToDate(df.format(chooseRoom.getBizDate().toString()));
//		bizDate=chooseRoom.getBizDate();
		if(null!=bizDate){
		if(null!=froomId){
			if(null!=fBuildingId ){
				chooseRoomParam = RoomDisplaySetting.getNewChooseRoomSet(ctx,fBuildingId);
//				Date beginDate = (Date) chooseRoomParam.get(SHEParamConstant.T3_BEGIN_DATE);
//				
				if(chooseRoomParam == null){
					return false;
				}
				int expiryDate = ((Integer)chooseRoomParam.get(SHEParamConstant.T3_EXPIRY_DATE)).intValue();
//				FDCDateHelper.
//				Calendar cal =  new GregorianCalendar(chooseRoom.getBizDate().getTime());    
				
				
				
				
					Calendar calTime = Calendar.getInstance();
					calTime.setTime(bizDate);
					
					calTime.add(Calendar.MINUTE,expiryDate);
			
					if (CurTime.after(calTime.getTime())) {
						return false;
					} else {
						return true;
					}
				}
				
			}
		}
		
		
		
		

//		// 注意要去掉取消选房的
//		// 当前时间-
//		if (null != chooseRoom && null != chooseRoom.getRoom()) {
//			chooseRoom.getRoom().getBuilding();
//			int validtime = 0;
//			Calendar cal = Calendar.getInstance();
//			cal.set(chooseRoom.getBizDate().getYear(), chooseRoom.getBizDate().getMonth(), chooseRoom.getBizDate().getDate(), chooseRoom.getBizDate().getHours(), chooseRoom.getBizDate().getMinutes()
//					+ validtime);
//
//			if (FDCHelper.getCurrentDate().after(cal.getTime())) {
//				return false;
//			} else {
//				return true;
//			}
//		}
	
	return false;
	}
	


	protected void _cancelChooseRoom(Context ctx, String billID) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		
		ChooseRoomInfo chooseRoom = new ChooseRoomInfo();
		chooseRoom.setId(BOSUuid.read(billID));
		chooseRoom.setChooseRoomStateEnum(ChooseRoomStateEnum.CancelChoose);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("chooseRoomStateEnum");
		this.updatePartial(ctx, chooseRoom, selector);
	}
	protected void _updateTrans(Context ctx, String billID, ChooseRoomStateEnum chooseRoomState) throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		//判断选房单状态
		ChooseRoomInfo chooseRoom = new ChooseRoomInfo();
		chooseRoom.setId(BOSUuid.read(billID));
		chooseRoom.setChooseRoomStateEnum(chooseRoomState);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("chooseRoomStateEnum");
		this.updatePartial(ctx, chooseRoom, selector);
		//判断更新房间销售状态
		RoomInfo room = chooseRoom.getRoom();
		SelectorItemCollection selectorRoom = new SelectorItemCollection();
		selectorRoom.add("sellState");
		if(ChooseRoomStateEnum.TransPrePur.equals(chooseRoomState)){
			room.setSellState(RoomSellStateEnum.PrePurchase);
		}else if(ChooseRoomStateEnum.TransPurchase.equals(chooseRoomState)){
			room.setSellState(RoomSellStateEnum.Purchase);
		}else if(ChooseRoomStateEnum.TransSign.equals(chooseRoomState)){
			room.setSellState(RoomSellStateEnum.Sign);
		}
		
		RoomFactory.getLocalInstance(ctx).updatePartial(room, selectorRoom);
	}

	protected void checkBill(Context ctx, IObjectValue model)throws BOSException, EASBizException{
		
	}
	 protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
    	ChooseRoomInfo chooseRoom =  (ChooseRoomInfo)model;
    	if(null==chooseRoom.getId()){
    		chooseRoom.setChooseRoomStateEnum(ChooseRoomStateEnum.Affirm);
    	}
    	chooseRoom.setProject(chooseRoom.getRoom().getBuilding().getSellProject());
    	chooseRoom.setOrgUnit(chooseRoom.getRoom().getBuilding().getSellProject().getOrgUnit());
		return  super._submit(ctx, chooseRoom);
	}
}