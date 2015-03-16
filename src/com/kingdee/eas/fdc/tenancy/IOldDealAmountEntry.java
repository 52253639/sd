package com.kingdee.eas.fdc.tenancy;

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
import com.kingdee.eas.framework.ICoreBillEntryBase;

public interface IOldDealAmountEntry extends ICoreBillEntryBase
{
    public OldDealAmountEntryInfo getOldDealAmountEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public OldDealAmountEntryInfo getOldDealAmountEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public OldDealAmountEntryInfo getOldDealAmountEntryInfo(String oql) throws BOSException, EASBizException;
    public OldDealAmountEntryCollection getOldDealAmountEntryCollection() throws BOSException;
    public OldDealAmountEntryCollection getOldDealAmountEntryCollection(EntityViewInfo view) throws BOSException;
    public OldDealAmountEntryCollection getOldDealAmountEntryCollection(String oql) throws BOSException;
}