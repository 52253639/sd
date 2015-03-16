package com.kingdee.eas.fdc.finance;

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

public interface IPlanEntry extends IBillBase
{
    public PlanEntryInfo getPlanEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public PlanEntryInfo getPlanEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public PlanEntryInfo getPlanEntryInfo(String oql) throws BOSException, EASBizException;
    public PlanEntryCollection getPlanEntryCollection() throws BOSException;
    public PlanEntryCollection getPlanEntryCollection(EntityViewInfo view) throws BOSException;
    public PlanEntryCollection getPlanEntryCollection(String oql) throws BOSException;
}