package com.kingdee.eas.fdc.tenancy.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.tenancy.SincerObligateInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.tenancy.SincerObligateCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SincerObligateController extends TenBillBaseController
{
    public SincerObligateInfo getSincerObligateInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SincerObligateInfo getSincerObligateInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SincerObligateInfo getSincerObligateInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public SincerObligateCollection getSincerObligateCollection(Context ctx) throws BOSException, RemoteException;
    public SincerObligateCollection getSincerObligateCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SincerObligateCollection getSincerObligateCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public boolean execute(Context ctx, String id) throws BOSException, EASBizException, RemoteException;
    public boolean cancelSincer(Context ctx, String id) throws BOSException, EASBizException, RemoteException;
}