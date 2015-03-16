package com.kingdee.eas.fdc.tenancy.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.Timestamp;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
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
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.BillBaseControllerBean;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomKeepDownBillInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.tenancy.KeepRoomDownInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.BillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.tenancy.KeepRoomDownCollection;
import com.kingdee.eas.util.app.DbUtil;

public class KeepRoomDownControllerBean extends AbstractKeepRoomDownControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.KeepRoomDownControllerBean");

    /**
     * 取消房间保留
     */
	protected void _cancelKeepRoom(Context ctx, String roomId) throws BOSException
	{
		final String updateRoomSql = "update T_SHE_Room set FTenancyState=? where FID=?";
    	DbUtil.execute(ctx, updateRoomSql, new Object[]{TenancyStateEnum.WAITTENANCY_VALUE, roomId});
    	
    	final String updateBillSql = "update T_TEN_KeepRoomDown set FCancelDate=? where FRoomID=?";
    	DbUtil.execute(ctx, updateBillSql, new Object[]{new Timestamp(new java.util.Date().getTime()), roomId});
   
	}
	/**
	 * 保存单据
	 */
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException
	{
		KeepRoomDownInfo keepRoomDownInfo = (KeepRoomDownInfo) model;
		keepRoomDownInfo.setBizDate(new java.util.Date());
    	IObjectPK pk = super._save(ctx, model);
    	
    	
    	//改变房间上租赁的状态
    	RoomInfo room = keepRoomDownInfo.getRoom();
    	room.setTenancyState(TenancyStateEnum.keepTenancy);
    	SelectorItemCollection sels=new SelectorItemCollection();
		sels.add("tenancyState");
		RoomFactory.getLocalInstance(ctx).updatePartial(room, sels);
		return pk;
	}
}