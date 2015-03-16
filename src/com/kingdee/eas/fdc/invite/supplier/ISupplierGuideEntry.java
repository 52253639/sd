package com.kingdee.eas.fdc.invite.supplier;

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

public interface ISupplierGuideEntry extends ICoreBillEntryBase
{
    public SupplierGuideEntryInfo getSupplierGuideEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SupplierGuideEntryInfo getSupplierGuideEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SupplierGuideEntryInfo getSupplierGuideEntryInfo(String oql) throws BOSException, EASBizException;
    public SupplierGuideEntryCollection getSupplierGuideEntryCollection() throws BOSException;
    public SupplierGuideEntryCollection getSupplierGuideEntryCollection(EntityViewInfo view) throws BOSException;
    public SupplierGuideEntryCollection getSupplierGuideEntryCollection(String oql) throws BOSException;
}