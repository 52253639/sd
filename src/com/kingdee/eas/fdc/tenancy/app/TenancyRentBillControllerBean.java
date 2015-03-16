package com.kingdee.eas.fdc.tenancy.app;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.tenancy.BuildigRentEntrysCollection;
import com.kingdee.eas.fdc.tenancy.BuildigRentEntrysInfo;
import com.kingdee.eas.fdc.tenancy.BuildingRoomEntrysCollection;
import com.kingdee.eas.fdc.tenancy.BuildingRoomEntrysFactory;
import com.kingdee.eas.fdc.tenancy.BuildingRoomEntrysInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRentBillInfo;

public class TenancyRentBillControllerBean extends AbstractTenancyRentBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.TenancyRentBillControllerBean");

	protected boolean _execute(Context ctx, String id) throws BOSException, EASBizException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("project.*");
		sels.add("creator.*");
		sels.add("buildingEntrys.*");
		sels.add("buildingEntrys.buildings.*");
		sels.add("roomEntrys.*");
		sels.add("roomEntrys.rooms.*");
		TenancyRentBillInfo tenBill = (TenancyRentBillInfo) this.getValue(ctx,
				new ObjectUuidPK(BOSUuid.read(id)), sels);
		BuildigRentEntrysCollection buildingRentEntrysColl = tenBill.getBuildingEntrys();
		if(buildingRentEntrysColl.size()>0)
		{
			for(int i=0;i<buildingRentEntrysColl.size();i++)
			{
				BuildigRentEntrysInfo buildingRentInfo = buildingRentEntrysColl.get(i);
				EntityViewInfo view1 = new EntityViewInfo();
				view1.getSelector().add("*");
				view1.getSelector().add("rooms.*");
				view1.getSelector().add("rooms.building.*");
				view1.getSelector().add("rooms.roomModel.*");
				FilterInfo filter1 = new FilterInfo();
				filter1.getFilterItems().add(
						new FilterItemInfo("head.id",buildingRentInfo.getId().toString(),CompareType.EQUALS));
				view1.setFilter(filter1);
				BuildingRoomEntrysCollection roomColl = BuildingRoomEntrysFactory.getLocalInstance(ctx).getBuildingRoomEntrysCollection(view1);
				for(int k=0;k<roomColl.size();k++)
				{
					BuildingRoomEntrysInfo roomEntrysInfo = roomColl.get(k);
					RoomInfo room = roomEntrysInfo.getRooms();
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
					BigDecimal buildingArea = roomEntrysInfo.getBuildingArea();
					BigDecimal roomArea = roomEntrysInfo.getRoomArea();
					if(buildingArea.compareTo(actualBuildingArea)!=0 || roomArea.compareTo(actualRoomArea)!=0)
					{
						continue;
					}
					room.setStandardRent(roomEntrysInfo.getStandardRent());
					room.setStandardRentPrice(roomEntrysInfo.getStandardRentPrice());
					room.setRentType(roomEntrysInfo.getRentType());
					room.setTenancyState(roomEntrysInfo.getTenancyState());
					room.setTenancyModel(roomEntrysInfo.getTenancyModel());
					room.setBuildingRentPrice(roomEntrysInfo.getBuildingRentPrice());
					room.setRoomRentPrice(roomEntrysInfo.getRoomRentPrice());
					room.setDayPrice(roomEntrysInfo.getDayPrice());
					sels = new SelectorItemCollection();
					sels.add("standardRent");
					sels.add("standardRentPrice");
					sels.add("rentType");
					sels.add("tenancyState");
					sels.add("tenancyModel");
					sels.add("buildingRentPrice");
					sels.add("roomRentPrice");
					// by tim_gao 加入日单价
					sels.add("dayPrice");
					RoomFactory.getLocalInstance(ctx).updatePartial(room, sels);
				}
			}
		}else
		{
			BuildingRoomEntrysCollection buildRoomEntryColl = tenBill.getRoomEntrys();
			for(int i=0;i<buildRoomEntryColl.size();i++)
			{
				BuildingRoomEntrysInfo roomEntrysInfo = buildRoomEntryColl.get(i);
				RoomInfo room = roomEntrysInfo.getRooms();
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
				BigDecimal buildingArea = roomEntrysInfo.getBuildingArea();
				BigDecimal roomArea = roomEntrysInfo.getRoomArea();
				if(buildingArea.compareTo(actualBuildingArea)!=0 || roomArea.compareTo(actualRoomArea)!=0)
				{
					continue;
				}
				room.setStandardRent(roomEntrysInfo.getStandardRent());
				room.setStandardRentPrice(roomEntrysInfo.getStandardRentPrice());
				room.setRentType(roomEntrysInfo.getRentType());
				room.setTenancyState(roomEntrysInfo.getTenancyState());
				room.setTenancyModel(roomEntrysInfo.getTenancyModel());
				room.setBuildingRentPrice(roomEntrysInfo.getBuildingRentPrice());
				room.setRoomRentPrice(roomEntrysInfo.getRoomRentPrice());
				room.setDayPrice(roomEntrysInfo.getDayPrice());
				sels = new SelectorItemCollection();
				sels.add("standardRent");
				sels.add("standardRentPrice");
				sels.add("rentType");
				sels.add("tenancyState");
				sels.add("tenancyModel");
				sels.add("buildingRentPrice");
				sels.add("roomRentPrice");
				// by tim_gao 加入日单价
				sels.add("dayPrice");
				RoomFactory.getLocalInstance(ctx).updatePartial(room, sels);
			}
		}
		
		tenBill.setIsExecuted(true);
		tenBill.setExecutedTime(FDCSQLFacadeFactory.getLocalInstance(ctx)
				.getServerTime());
		sels = new SelectorItemCollection();
		sels.add("isExecuted");
		sels.add("executedTime");
		this.updatePartial(ctx, tenBill, sels);
		
		return true;
	}
}