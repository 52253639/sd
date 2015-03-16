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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IAIMAimCostProductSplitEntry extends IDataBase
{
    public AIMAimCostProductSplitEntryInfo getAIMAimCostProductSplitEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public AIMAimCostProductSplitEntryInfo getAIMAimCostProductSplitEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public AIMAimCostProductSplitEntryInfo getAIMAimCostProductSplitEntryInfo(String oql) throws BOSException, EASBizException;
    public AIMAimCostProductSplitEntryCollection getAIMAimCostProductSplitEntryCollection() throws BOSException;
    public AIMAimCostProductSplitEntryCollection getAIMAimCostProductSplitEntryCollection(EntityViewInfo view) throws BOSException;
    public AIMAimCostProductSplitEntryCollection getAIMAimCostProductSplitEntryCollection(String oql) throws BOSException;
}