package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.DataBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.sellhouse.AdapterLogInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.sellhouse.AdapterLogCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface AdapterLogController extends DataBaseController
{
    public AdapterLogInfo getAdapterLogInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public AdapterLogInfo getAdapterLogInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public AdapterLogInfo getAdapterLogInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public AdapterLogCollection getAdapterLogCollection(Context ctx) throws BOSException, RemoteException;
    public AdapterLogCollection getAdapterLogCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public AdapterLogCollection getAdapterLogCollection(Context ctx, String oql) throws BOSException, RemoteException;
}