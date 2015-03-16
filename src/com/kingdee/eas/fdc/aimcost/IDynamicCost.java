package com.kingdee.eas.fdc.aimcost;

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
import com.kingdee.eas.framework.IBillBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IDynamicCost extends IBillBase
{
    public DynamicCostInfo getDynamicCostInfo(IObjectPK pk) throws BOSException, EASBizException;
    public DynamicCostInfo getDynamicCostInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public DynamicCostInfo getDynamicCostInfo(String oql) throws BOSException, EASBizException;
    public DynamicCostCollection getDynamicCostCollection() throws BOSException;
    public DynamicCostCollection getDynamicCostCollection(EntityViewInfo view) throws BOSException;
    public DynamicCostCollection getDynamicCostCollection(String oql) throws BOSException;
}