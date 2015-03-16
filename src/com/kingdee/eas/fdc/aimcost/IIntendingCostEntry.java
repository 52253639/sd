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
import com.kingdee.eas.framework.IBillEntryBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IIntendingCostEntry extends IBillEntryBase
{
    public IntendingCostEntryCollection getIntendingCostEntryCollection() throws BOSException;
    public IntendingCostEntryCollection getIntendingCostEntryCollection(EntityViewInfo view) throws BOSException;
    public IntendingCostEntryCollection getIntendingCostEntryCollection(String oql) throws BOSException;
    public IntendingCostEntryInfo getIntendingCostEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public IntendingCostEntryInfo getIntendingCostEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public IntendingCostEntryInfo getIntendingCostEntryInfo(String oql) throws BOSException, EASBizException;
}