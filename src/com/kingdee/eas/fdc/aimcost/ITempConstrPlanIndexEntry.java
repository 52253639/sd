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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface ITempConstrPlanIndexEntry extends ICoreBillEntryBase
{
    public TempConstrPlanIndexEntryInfo getTempConstrPlanIndexEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TempConstrPlanIndexEntryInfo getTempConstrPlanIndexEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TempConstrPlanIndexEntryInfo getTempConstrPlanIndexEntryInfo(String oql) throws BOSException, EASBizException;
    public TempConstrPlanIndexEntryCollection getTempConstrPlanIndexEntryCollection() throws BOSException;
    public TempConstrPlanIndexEntryCollection getTempConstrPlanIndexEntryCollection(EntityViewInfo view) throws BOSException;
    public TempConstrPlanIndexEntryCollection getTempConstrPlanIndexEntryCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
}