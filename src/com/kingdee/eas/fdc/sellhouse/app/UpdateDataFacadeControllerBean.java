package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.SQLException;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.ctrl.common.util.DBUtil;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.BuildingCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class UpdateDataFacadeControllerBean extends AbstractUpdateDataFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.UpdateDataFacadeControllerBean");
    
    /**
     * 先 升级 楼栋 实体上的 楼层分录的数据，再升级 房间上面， 楼层 关联属性的数据
     */
    protected boolean _updateBuildingFloorForRoom(Context ctx)throws BOSException, EASBizException
    {
    	boolean debug = false;
    	
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	
    	view.getSelector().add("floorCount");
    	view.getSelector().add("subFloorCount");
    	view.getSelector().add("floorEntry.*");
    	view.getSelector().add("id");
    	
    	BuildingCollection buildingColl = BuildingFactory.getLocalInstance(ctx).getBuildingCollection(view);
    	
    	for(int i = 0; i < buildingColl.size(); i ++)
    	{
    		BuildingInfo building = buildingColl.get(i);
    		
    		BuildingFloorEntryCollection floorColl = building.getFloorEntry();
    		
    		int floorCount = building.getFloorCount();
			int subFloorCount = building.getSubFloorCount();
			if((floorCount != 0 || subFloorCount != 0) && (floorColl == null || floorColl.size() < 1) )
    		{
    			this.generateFloorEntry(ctx, building);
    		}
    	}
    	
    	//更新房间上 楼层关联属性数据
    	
    /*	view = new EntityViewInfo();
    	filter = new FilterInfo();
    	view.setFilter(filter);
    	
    	view.getSelector().add("id");
    	view.getSelector().add("buildingFloor.id");
    	view.getSelector().add("building.id");
    	view.getSelector().add("floor");
    	
    	filter.getFilterItems().add(new FilterItemInfo("buildingFloor",null));
    	
    	RoomCollection roomColl = RoomFactory.getLocalInstance(ctx).getRoomCollection(view);
    	
    	for(int i = 0; i < roomColl.size(); i ++)
    	{
    		RoomInfo room = roomColl.get(i);
    		
    		BuildingFloorEntryInfo entry = this.getBuildingFloorEntryInfo(ctx, room);
    		
    		room.setBuildingFloor(entry);
    		
    		SelectorItemCollection selColl = new SelectorItemCollection();
    		selColl.add("buildingFloor");
    		
    		RoomFactory.getLocalInstance(ctx).updatePartial(room, selColl);
    		
    	}*/
    	
    	String sql = " update t_she_room as r set fbuildingfloorid = " +
    			" (select fid from t_she_buildingfloorentry as b where r.fbuildingid = b.fbuildingid and r.ffloor = b.ffloor) " +
    			" where r.fbuildingfloorid is null ";
    	
    	DbUtil.execute(ctx, sql);
    	
    	
        return debug;
    }
    
    /**
     * 
     * @param building
     * @param room
     * @return
     * @throws BOSException 
     */
    private BuildingFloorEntryInfo getBuildingFloorEntryInfo(Context ctx, RoomInfo room) throws BOSException
    {
    	BuildingFloorEntryInfo info = null;
    	
    	int floor = room.getFloor();
    	String id = room.getBuilding().getId().toString();
    	
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.setFilter(filter);
    	
    	filter.getFilterItems().add(new FilterItemInfo("building",id));
    	filter.getFilterItems().add(new FilterItemInfo("floor",new Integer(floor)));
    	
    	BuildingFloorEntryCollection entryColl = BuildingFloorEntryFactory.getLocalInstance(ctx).getBuildingFloorEntryCollection(view);
    	
    	if(entryColl.size() == 1)
    	{
    		info = entryColl.get(0);
    	}
    	else
    	{
    		logger.error("房间ID "+ room.getId()+ " ,楼层 为 "+ floor+ " 在 ，楼层分录中，有多个楼层对应");
    	}
    	return info;
    }
    
    
    
    /**
     * 更新 楼栋 楼层分录数据
     * @param ctx
     * @param building
     * @throws EASBizException
     * @throws BOSException
     */
    private void generateFloorEntry(Context ctx,BuildingInfo building) throws EASBizException, BOSException
    {
    	int floorCount = building.getFloorCount();
		int subFloorCount = building.getSubFloorCount();
		
		if(floorCount <= subFloorCount)
		{
			logger.error("ID为 "+ building.getId() + " 的 楼栋，楼层设置错误...");
		}
		

		for(int i = subFloorCount; i <= floorCount; i ++)
		{
			if(i == 0)
				continue;
			
			BuildingFloorEntryInfo entry = new BuildingFloorEntryInfo();
			entry.setBuilding(building);
			entry.setFloor(i);
			entry.setFloorAlias(String.valueOf(i));
			entry.setIsEnable(true);
			
			BuildingFloorEntryFactory.getLocalInstance(ctx).addnew(entry);
		}

    }
    
    
    
    
    /**
     * 检测 楼栋上，楼层的分录数据是否要进行升级
     */
    protected boolean _isNeedUpdateBuildingFloorForRoom(Context ctx)throws BOSException, EASBizException
    {
    	boolean debug = false;
    	
    	String sql = "select fid from t_she_building";
    	
    	IRowSet rowSet =  DbUtil.executeQuery(ctx, sql);
    	
    	try
		{
			while(rowSet.next())
			{
				String fid = rowSet.getString("fid");
				
				String tempSql ="select fid from T_SHE_BuildingFloorEntry where FBuildingID = '"+ fid + "'";
				IRowSet tempSet = DbUtil.executeQueryNoTx(ctx, tempSql);
			
				if(!tempSet.next())
				{
					debug = true;
					return debug;
				}
				
			}
		} catch (SQLException e)
		{
			throw new BOSException(e);
		}
    	
        return debug;
    }
}