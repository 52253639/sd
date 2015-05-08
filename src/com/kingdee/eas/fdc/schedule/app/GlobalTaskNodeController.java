package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.schedule.GlobalTaskNodeCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseController;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.schedule.GlobalTaskNodeInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface GlobalTaskNodeController extends FDCDataBaseController
{
    public GlobalTaskNodeInfo getGlobalTaskNodeInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public GlobalTaskNodeInfo getGlobalTaskNodeInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public GlobalTaskNodeInfo getGlobalTaskNodeInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK addnew(Context ctx, GlobalTaskNodeInfo model) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] delete(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException, RemoteException;
    public void delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public GlobalTaskNodeCollection getGlobalTaskNodeCollection(Context ctx) throws BOSException, RemoteException;
    public GlobalTaskNodeCollection getGlobalTaskNodeCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public GlobalTaskNodeCollection getGlobalTaskNodeCollection(Context ctx, String oql) throws BOSException, RemoteException;
}