package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IBuildingUnit extends IDataBase
{
    public BuildingUnitInfo getBuildingUnitInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BuildingUnitInfo getBuildingUnitInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BuildingUnitInfo getBuildingUnitInfo(String oql) throws BOSException, EASBizException;
    public BuildingUnitCollection getBuildingUnitCollection() throws BOSException;
    public BuildingUnitCollection getBuildingUnitCollection(EntityViewInfo view) throws BOSException;
    public BuildingUnitCollection getBuildingUnitCollection(String oql) throws BOSException;
    public void updateBuildingUnitbyBuild(BuildingInfo building) throws BOSException, EASBizException;
}