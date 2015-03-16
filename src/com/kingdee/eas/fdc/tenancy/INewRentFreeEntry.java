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

public interface INewRentFreeEntry extends ICoreBillEntryBase
{
    public NewRentFreeEntryInfo getNewRentFreeEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public NewRentFreeEntryInfo getNewRentFreeEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public NewRentFreeEntryInfo getNewRentFreeEntryInfo(String oql) throws BOSException, EASBizException;
    public NewRentFreeEntryCollection getNewRentFreeEntryCollection() throws BOSException;
    public NewRentFreeEntryCollection getNewRentFreeEntryCollection(EntityViewInfo view) throws BOSException;
    public NewRentFreeEntryCollection getNewRentFreeEntryCollection(String oql) throws BOSException;
}