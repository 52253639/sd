package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IObjectBase;

public interface IDynCostSnapShot extends IObjectBase
{
    public DynCostSnapShotCollection getDynCostSnapShotCollection() throws BOSException;
    public DynCostSnapShotCollection getDynCostSnapShotCollection(EntityViewInfo view) throws BOSException;
    public DynCostSnapShotCollection getDynCostSnapShotCollection(String oql) throws BOSException;
    public DynCostSnapShotInfo getDynCostSnapShotInfo(IObjectPK pk) throws BOSException, EASBizException;
    public DynCostSnapShotInfo getDynCostSnapShotInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public DynCostSnapShotInfo getDynCostSnapShotInfo(String oql) throws BOSException, EASBizException;
    public Map getHistoryByMonth(Map selectMonth) throws BOSException, EASBizException;
}