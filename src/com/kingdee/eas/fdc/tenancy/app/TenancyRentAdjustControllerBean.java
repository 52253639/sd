package com.kingdee.eas.fdc.tenancy.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
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
import java.math.BigDecimal;

import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRentAdjustInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.tenancy.RentAdjustEntrysCollection;
import com.kingdee.eas.fdc.tenancy.RentAdjustEntrysInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRentAdjustCollection;
import com.kingdee.eas.fdc.tenancy.TenBillBaseCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRentBillInfo;

public class TenancyRentAdjustControllerBean extends AbstractTenancyRentAdjustControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.TenancyRentAdjustControllerBean");

	protected boolean _execute(Context ctx, String id) throws BOSException, EASBizException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("project.*");
		sels.add("creator.*");
		sels.add("entrys.*");
		sels.add("entrys.room.*");
		sels.add("entrys.room.building.*");
		TenancyRentAdjustInfo tenAdjust = (TenancyRentAdjustInfo) this.getValue(ctx,
				new ObjectUuidPK(BOSUuid.read(id)), sels);
		RentAdjustEntrysCollection rentEntrysColl = tenAdjust.getEntrys();
		for(int i=0;i<rentEntrysColl.size();i++)
		{
			RentAdjustEntrysInfo rentAdjustEntrys = rentEntrysColl.get(i);
			RoomInfo room = rentAdjustEntrys.getRoom();
			BigDecimal actualBuildingArea = FDCHelper.ZERO;
			BigDecimal actualRoomArea = FDCHelper.ZERO;
			if(room.isIsActualAreaAudited())
			{
				 actualBuildingArea =  room.getActualBuildingArea();
				 actualRoomArea =  room.getActualRoomArea();
				if(actualBuildingArea==null)
				{
					actualBuildingArea = FDCHelper.ZERO;
				}
				if(actualRoomArea== null)
				{
					actualRoomArea = FDCHelper.ZERO;
				}
			}else
			{
				actualBuildingArea = room.getBuildingArea();
				actualRoomArea = room.getRoomArea();
			}
			BigDecimal buildingArea = rentAdjustEntrys.getOldBuildingArea();
			BigDecimal roomArea = rentAdjustEntrys.getOldRoomArea();
			if(buildingArea.compareTo(actualBuildingArea)!=0 || roomArea.compareTo(actualRoomArea)!=0)
			{
				continue;
			}
			room.setTenancyModel(rentAdjustEntrys.getNewTenancyModel());
			room.setRoomRentPrice(rentAdjustEntrys.getNewRoomRentPrice());
			room.setStandardRentPrice(rentAdjustEntrys.getNewRentPrice());
			room.setStandardRent(rentAdjustEntrys.getNewStandardRent());
			room.setRentType(rentAdjustEntrys.getNewRentType());
			room.setBuildingRentPrice(rentAdjustEntrys.getNewRentPrice());
			sels = new SelectorItemCollection();
			sels.add("standardRent");
			sels.add("standardRentPrice");
			sels.add("rentType");
			//sels.add("tenancyState");
			sels.add("tenancyModel");
			sels.add("buildingRentPrice");
			sels.add("roomRentPrice");
			RoomFactory.getLocalInstance(ctx).updatePartial(room, sels);
		}
		tenAdjust.setIsExecuted(true);
		tenAdjust.setExecutedTime(FDCSQLFacadeFactory.getLocalInstance(ctx)
				.getServerTime());
		sels = new SelectorItemCollection();
		sels.add("isExecuted");
		sels.add("executedTime");
		this.updatePartial(ctx, tenAdjust, sels);
		
		return true;
	}
}