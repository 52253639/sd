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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface IStoreSubjectClass extends ITreeBase
{
    public StoreSubjectClassInfo getStoreSubjectClassInfo(IObjectPK pk) throws BOSException, EASBizException;
    public StoreSubjectClassInfo getStoreSubjectClassInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public StoreSubjectClassInfo getStoreSubjectClassInfo(String oql) throws BOSException, EASBizException;
    public StoreSubjectClassCollection getStoreSubjectClassCollection() throws BOSException;
    public StoreSubjectClassCollection getStoreSubjectClassCollection(EntityViewInfo view) throws BOSException;
    public StoreSubjectClassCollection getStoreSubjectClassCollection(String oql) throws BOSException;
}