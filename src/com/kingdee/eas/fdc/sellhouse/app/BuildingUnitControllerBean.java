package com.kingdee.eas.fdc.sellhouse.app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDesFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;

public class BuildingUnitControllerBean extends AbstractBuildingUnitControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.BuildingUnitControllerBean");
    
        
   protected void _checkNameDup(Context ctx, IObjectValue model)
		throws BOSException, EASBizException {
   }
   
   protected void _checkNumberDup(Context ctx, IObjectValue model)
		throws BOSException, EASBizException {
   
   }

protected void _updateBuildingUnitbyBuild(Context ctx, IObjectValue building) throws BOSException, EASBizException {
	// TODO Auto-generated method stub
	BuildingInfo build = (BuildingInfo)building;
	EntityViewInfo view = new EntityViewInfo();
	FilterInfo filter = new FilterInfo();
	filter.getFilterItems().add(new FilterItemInfo("building.id",build.getId().toString()));
	view.setFilter(filter);
	BuildingUnitCollection buildUnits = this.getBuildingUnitCollection(ctx, view);
	Iterator itUnits = buildUnits.iterator();
	Set delSet = new HashSet();
	boolean updateHaveUnit = false;
	int sum = buildUnits.size();
	List unitUpdList = new ArrayList();
	while(itUnits.hasNext()){
		BuildingUnitInfo builUnit = (BuildingUnitInfo) itUnits.next();
		FilterInfo roomFilter = new FilterInfo();
		roomFilter.getFilterItems().add(new FilterItemInfo("buildUnit.id",builUnit.getId().toString()));
		if(!RoomFactory.getLocalInstance(ctx).exists(roomFilter)){
			delSet.add(builUnit.getId().toString());
		}else{
			if(true==builUnit.isHaveUnit()&&sum>1){
				unitUpdList.add(builUnit.getId().toString());
			}
		}
		
	}
	//删除没有房间的单元
	if(null!=delSet&&delSet.size()>0){
		FilterInfo delFilter = new FilterInfo();
		delFilter.getFilterItems().add(new FilterItemInfo("id",delSet,CompareType.INCLUDE));
		delFilter.getFilterItems().add(new FilterItemInfo("building.id",build.getId().toString()));
		this._delete(ctx, delFilter);
		//同事删除房间定义
		FilterInfo delDesFilter = new FilterInfo();
		delDesFilter.getFilterItems().add(new FilterItemInfo("unit.id",delSet,CompareType.INCLUDE));
		delDesFilter.getFilterItems().add(new FilterItemInfo("building.id",build.getId().toString()));
		RoomDesFactory.getRemoteInstance().delete(delDesFilter);
		//更新楼栋数值
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("unitCount");
		BuildingInfo upBuild = new BuildingInfo();
		upBuild.setUnitCount(sum-delSet.size());
		BuildingFactory.getLocalInstance(ctx).updatePartial(upBuild, sel);
	}
	
	if(null!=unitUpdList&&unitUpdList.size()>0){
		//更新单元显示值
		String sql = "update T_SHE_BuildingUnit set fhaveunit = 0 where fid = ?";
		FDCSQLBuilder sqlBuilder = new FDCSQLBuilder(ctx);
		sqlBuilder.setPrepareStatementSql(sql);
		sqlBuilder.setBatchType(FDCSQLBuilder.PREPARESTATEMENT_TYPE);
		for (int i = 0; i < unitUpdList.size(); i++) {
			String id = (String) unitUpdList.get(i);
			sqlBuilder.addParam(id);
			sqlBuilder.addBatch();
		}
		sqlBuilder.executeBatch();	
	}
	
	
	
	
}
    
    
}