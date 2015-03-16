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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;

public interface ICostSplitEntry extends ICoreBase
{
    public CostSplitEntryInfo getCostSplitEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public CostSplitEntryInfo getCostSplitEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public CostSplitEntryInfo getCostSplitEntryInfo(String oql) throws BOSException, EASBizException;
    public CostSplitEntryCollection getCostSplitEntryCollection() throws BOSException;
    public CostSplitEntryCollection getCostSplitEntryCollection(EntityViewInfo view) throws BOSException;
    public CostSplitEntryCollection getCostSplitEntryCollection(String oql) throws BOSException;
}