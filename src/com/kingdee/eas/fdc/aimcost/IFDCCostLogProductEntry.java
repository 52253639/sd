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

public interface IFDCCostLogProductEntry extends ICoreBase
{
    public FDCCostLogProductEntryInfo getFDCCostLogProductEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public FDCCostLogProductEntryInfo getFDCCostLogProductEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public FDCCostLogProductEntryInfo getFDCCostLogProductEntryInfo(String oql) throws BOSException, EASBizException;
    public FDCCostLogProductEntryCollection getFDCCostLogProductEntryCollection() throws BOSException;
    public FDCCostLogProductEntryCollection getFDCCostLogProductEntryCollection(EntityViewInfo view) throws BOSException;
    public FDCCostLogProductEntryCollection getFDCCostLogProductEntryCollection(String oql) throws BOSException;
}