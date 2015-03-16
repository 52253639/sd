package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface IHeadType extends ITreeBase
{
    public HeadTypeInfo getHeadTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public HeadTypeInfo getHeadTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public HeadTypeInfo getHeadTypeInfo(String oql) throws BOSException, EASBizException;
    public HeadTypeCollection getHeadTypeCollection() throws BOSException;
    public HeadTypeCollection getHeadTypeCollection(EntityViewInfo view) throws BOSException;
    public HeadTypeCollection getHeadTypeCollection(String oql) throws BOSException;
    public boolean updateRelateData(String oldID, String newID, Object[] tables) throws BOSException;
    public Object[] getRelateData(String id, String[] tables) throws BOSException;
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(HeadTypeInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, HeadTypeInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, HeadTypeInfo model) throws BOSException, EASBizException;
    public void updatePartial(HeadTypeInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, HeadTypeInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
}