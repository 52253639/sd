package com.kingdee.eas.fdc.invite;

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

public interface INewListTempletValue extends ICoreBillEntryBase
{
    public NewListTempletValueInfo getNewListTempletValueInfo(IObjectPK pk) throws BOSException, EASBizException;
    public NewListTempletValueInfo getNewListTempletValueInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public NewListTempletValueInfo getNewListTempletValueInfo(String oql) throws BOSException, EASBizException;
    public NewListTempletValueCollection getNewListTempletValueCollection() throws BOSException;
    public NewListTempletValueCollection getNewListTempletValueCollection(EntityViewInfo view) throws BOSException;
    public NewListTempletValueCollection getNewListTempletValueCollection(String oql) throws BOSException;
    public NewListTempletValueCollection getCollectionBySQL(String listingIds) throws BOSException, EASBizException;
}