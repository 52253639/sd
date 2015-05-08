package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseController;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.schedule.ScheduleBizTypeCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ScheduleBizTypeController extends FDCDataBaseController
{
    public boolean exists(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ScheduleBizTypeInfo getScheduleBizTypeInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ScheduleBizTypeInfo getScheduleBizTypeInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ScheduleBizTypeInfo getScheduleBizTypeInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ScheduleBizTypeCollection getScheduleBizTypeCollection(Context ctx) throws BOSException, RemoteException;
    public ScheduleBizTypeCollection getScheduleBizTypeCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ScheduleBizTypeCollection getScheduleBizTypeCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public String getBiggestNumber(Context ctx) throws BOSException, RemoteException;
}