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
import com.kingdee.eas.framework.ICoreBase;

public interface ITenAttachResourceEntry extends ICoreBase
{
    public TenAttachResourceEntryInfo getTenAttachResourceEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TenAttachResourceEntryInfo getTenAttachResourceEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TenAttachResourceEntryInfo getTenAttachResourceEntryInfo(String oql) throws BOSException, EASBizException;
    public TenAttachResourceEntryCollection getTenAttachResourceEntryCollection() throws BOSException;
    public TenAttachResourceEntryCollection getTenAttachResourceEntryCollection(EntityViewInfo view) throws BOSException;
    public TenAttachResourceEntryCollection getTenAttachResourceEntryCollection(String oql) throws BOSException;
}