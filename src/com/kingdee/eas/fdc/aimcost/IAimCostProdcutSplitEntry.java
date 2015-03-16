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

public interface IAimCostProdcutSplitEntry extends IDataBase
{
    public AimCostProdcutSplitEntryInfo getAimCostProdcutSplitEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public AimCostProdcutSplitEntryInfo getAimCostProdcutSplitEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public AimCostProdcutSplitEntryInfo getAimCostProdcutSplitEntryInfo(String oql) throws BOSException, EASBizException;
    public AimCostProdcutSplitEntryCollection getAimCostProdcutSplitEntryCollection() throws BOSException;
    public AimCostProdcutSplitEntryCollection getAimCostProdcutSplitEntryCollection(EntityViewInfo view) throws BOSException;
    public AimCostProdcutSplitEntryCollection getAimCostProdcutSplitEntryCollection(String oql) throws BOSException;
}