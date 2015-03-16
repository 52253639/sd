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

public interface IOldRentFreeEntry extends ICoreBillEntryBase
{
    public OldRentFreeEntryInfo getOldRentFreeEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public OldRentFreeEntryInfo getOldRentFreeEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public OldRentFreeEntryInfo getOldRentFreeEntryInfo(String oql) throws BOSException, EASBizException;
    public OldRentFreeEntryCollection getOldRentFreeEntryCollection() throws BOSException;
    public OldRentFreeEntryCollection getOldRentFreeEntryCollection(EntityViewInfo view) throws BOSException;
    public OldRentFreeEntryCollection getOldRentFreeEntryCollection(String oql) throws BOSException;
}