package com.kingdee.eas.fdc.market;

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
import com.kingdee.eas.framework.IBillBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IStoreSubject extends IBillBase
{
    public StoreSubjectInfo getStoreSubjectInfo(IObjectPK pk) throws BOSException, EASBizException;
    public StoreSubjectInfo getStoreSubjectInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public StoreSubjectInfo getStoreSubjectInfo(String oql) throws BOSException, EASBizException;
    public StoreSubjectCollection getStoreSubjectCollection() throws BOSException;
    public StoreSubjectCollection getStoreSubjectCollection(EntityViewInfo view) throws BOSException;
    public StoreSubjectCollection getStoreSubjectCollection(String oql) throws BOSException;
}