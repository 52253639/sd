package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IBuildingProperty extends IFDCDataBase
{
    public BuildingPropertyInfo getBuildingPropertyInfo(IObjectPK pk) throws BOSException, EASBizException;
    public BuildingPropertyInfo getBuildingPropertyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public BuildingPropertyInfo getBuildingPropertyInfo(String oql) throws BOSException, EASBizException;
    public BuildingPropertyCollection getBuildingPropertyCollection() throws BOSException;
    public BuildingPropertyCollection getBuildingPropertyCollection(String oql) throws BOSException;
    public BuildingPropertyCollection getBuildingPropertyCollection(EntityViewInfo view) throws BOSException;
}