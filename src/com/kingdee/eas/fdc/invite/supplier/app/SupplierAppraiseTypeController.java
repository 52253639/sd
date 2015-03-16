package com.kingdee.eas.fdc.invite.supplier.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTypeInfo;
import com.kingdee.eas.framework.app.TreeBaseController;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTypeCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SupplierAppraiseTypeController extends TreeBaseController
{
    public SupplierAppraiseTypeInfo getSupplierAppraiseTypeInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SupplierAppraiseTypeInfo getSupplierAppraiseTypeInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SupplierAppraiseTypeInfo getSupplierAppraiseTypeInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public SupplierAppraiseTypeCollection getSupplierAppraiseTypeCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SupplierAppraiseTypeCollection getSupplierAppraiseTypeCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public SupplierAppraiseTypeCollection getSupplierAppraiseTypeCollection(Context ctx) throws BOSException, RemoteException;
    public IObjectPK addnew(Context ctx, SupplierAppraiseTypeInfo model) throws BOSException, EASBizException, RemoteException;
    public void addnew(Context ctx, IObjectPK pk, SupplierAppraiseTypeInfo model) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] delete(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException, RemoteException;
}