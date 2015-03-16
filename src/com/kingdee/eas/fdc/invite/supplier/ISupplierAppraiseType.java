package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface ISupplierAppraiseType extends ITreeBase
{
    public SupplierAppraiseTypeInfo getSupplierAppraiseTypeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SupplierAppraiseTypeInfo getSupplierAppraiseTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SupplierAppraiseTypeInfo getSupplierAppraiseTypeInfo(String oql) throws BOSException, EASBizException;
    public SupplierAppraiseTypeCollection getSupplierAppraiseTypeCollection(EntityViewInfo view) throws BOSException;
    public SupplierAppraiseTypeCollection getSupplierAppraiseTypeCollection(String oql) throws BOSException;
    public SupplierAppraiseTypeCollection getSupplierAppraiseTypeCollection() throws BOSException;
    public IObjectPK addnew(SupplierAppraiseTypeInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, SupplierAppraiseTypeInfo model) throws BOSException, EASBizException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
}