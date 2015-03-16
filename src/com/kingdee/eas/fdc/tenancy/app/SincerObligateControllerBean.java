package com.kingdee.eas.fdc.tenancy.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.tenancy.BizStateEnum;
import com.kingdee.eas.fdc.tenancy.SinObligateRoomsEntryCollection;
import com.kingdee.eas.fdc.tenancy.SinObligateRoomsEntryInfo;
import com.kingdee.eas.fdc.tenancy.SincerObligateInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;

public class SincerObligateControllerBean extends AbstractSincerObligateControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.SincerObligateControllerBean");
    
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	super._audit(ctx, billId);
    	SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("sinRoomList.*");
		sels.add("sinRoomList.room.*");
		SincerObligateInfo sinGateInfo = (SincerObligateInfo) this.getValue(ctx,
				new ObjectUuidPK(billId), sels);
		sinGateInfo.setBizState(BizStateEnum.AUDITTED);
		sels = new SelectorItemCollection();
		sels.add("bizState");
		this.updatePartial(ctx, sinGateInfo, sels);
    }
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	super._unAudit(ctx, billId);
    	SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("sinRoomList.*");
		sels.add("sinRoomList.room.*");
		SincerObligateInfo sinGateInfo = (SincerObligateInfo) this.getValue(ctx,
				new ObjectUuidPK(billId), sels);
		sinGateInfo.setBizState(BizStateEnum.SUBMIT);
		sels = new SelectorItemCollection();
		sels.add("bizState");
		this.updatePartial(ctx, sinGateInfo, sels);
    }
    
    protected boolean _execute(Context ctx, String id) throws BOSException, EASBizException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("sinRoomList.*");
		sels.add("sinRoomList.room.*");
		SincerObligateInfo sinGateInfo = (SincerObligateInfo) this.getValue(ctx,
				new ObjectUuidPK(BOSUuid.read(id)), sels);
		SinObligateRoomsEntryCollection sinEntryColl = sinGateInfo.getSinRoomList();
		for(int i=0;i<sinEntryColl.size();i++)
		{
			SinObligateRoomsEntryInfo sinEntryInfo = sinEntryColl.get(i);
			sinEntryInfo.setTenRoomState(TenancyStateEnum.sincerObligate);
			RoomInfo room = sinEntryInfo.getRoom();
			sinEntryColl.add(sinEntryInfo);
			room.setTenancyState(TenancyStateEnum.sincerObligate);
			sels = new SelectorItemCollection();
			sels.add("tenancyState");
			RoomFactory.getLocalInstance(ctx).updatePartial(room, sels);
		}
		sinGateInfo.setIsExecuted(true);
		sinGateInfo.setBizState(BizStateEnum.SINCEROBLIGATED);
		sinGateInfo.setExecutedTime(FDCSQLFacadeFactory.getLocalInstance(ctx)
				.getServerTime());
		sels = new SelectorItemCollection();
		sels.add("isExecuted");
		sels.add("executedTime");
		sels.add("bizState");
		this.updatePartial(ctx, sinGateInfo, sels);
		return true;
	}

	protected boolean _cancelSincer(Context ctx, String id)
			throws BOSException, EASBizException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("sinRoomList.*");
		sels.add("sinRoomList.room.*");
		SincerObligateInfo sinGateInfo = (SincerObligateInfo) this.getValue(ctx,
				new ObjectUuidPK(BOSUuid.read(id)), sels);
		SinObligateRoomsEntryCollection sinEntryColl = sinGateInfo.getSinRoomList();
		for(int i=0;i<sinEntryColl.size();i++)
		{
			SinObligateRoomsEntryInfo sinEntryInfo = sinEntryColl.get(i);
			sinEntryInfo.setTenRoomState(TenancyStateEnum.waitTenancy);
			RoomInfo room = sinEntryInfo.getRoom();
			sinEntryColl.add(sinEntryInfo);
			room.setTenancyState(TenancyStateEnum.waitTenancy);
			sels = new SelectorItemCollection();
			sels.add("tenancyState");
			RoomFactory.getLocalInstance(ctx).updatePartial(room, sels);
		}
		sinGateInfo.setBizState(BizStateEnum.CANCEL);
		sinGateInfo.setState(FDCBillStateEnum.INVALID);
		sinGateInfo.setIsExecuted(false);
		sels = new SelectorItemCollection();
		sels.add("state");
		sels.add("bizState");
		sels.add("isExecuted");
		this.updatePartial(ctx, sinGateInfo, sels);
		return true;
	}
}