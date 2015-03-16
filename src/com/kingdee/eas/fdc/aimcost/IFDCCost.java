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
import com.kingdee.eas.framework.ICoreBase;

public interface IFDCCost extends ICoreBase
{
    public FDCCostCollection getFDCCostCollection() throws BOSException;
    public FDCCostCollection getFDCCostCollection(EntityViewInfo view) throws BOSException;
    public FDCCostCollection getFDCCostCollection(String oql) throws BOSException;
    public FDCCostInfo getFDCCostInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCCostInfo getFDCCostInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCCostInfo getFDCCostInfo(String oql) throws BOSException, EASBizException;
    public Map getHistoryByMonth(Map selectMonth) throws BOSException, EASBizException;
}