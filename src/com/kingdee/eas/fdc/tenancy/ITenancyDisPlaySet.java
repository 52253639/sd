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
import com.kingdee.eas.framework.IDataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface ITenancyDisPlaySet extends IDataBase
{
    public TenancyDisPlaySetInfo getTenancyDisPlaySetInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TenancyDisPlaySetInfo getTenancyDisPlaySetInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TenancyDisPlaySetInfo getTenancyDisPlaySetInfo(String oql) throws BOSException, EASBizException;
    public TenancyDisPlaySetCollection getTenancyDisPlaySetCollection() throws BOSException;
    public TenancyDisPlaySetCollection getTenancyDisPlaySetCollection(EntityViewInfo view) throws BOSException;
    public TenancyDisPlaySetCollection getTenancyDisPlaySetCollection(String oql) throws BOSException;
}