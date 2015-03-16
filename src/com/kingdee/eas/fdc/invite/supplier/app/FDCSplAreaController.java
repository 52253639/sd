package com.kingdee.eas.fdc.invite.supplier.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseController;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAreaInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAreaCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCSplAreaController extends FDCDataBaseController
{
    public FDCSplAreaInfo getFDCSplAreaInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public FDCSplAreaInfo getFDCSplAreaInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public FDCSplAreaInfo getFDCSplAreaInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public FDCSplAreaCollection getFDCSplAreaCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public FDCSplAreaCollection getFDCSplAreaCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public FDCSplAreaCollection getFDCSplAreaCollection(Context ctx) throws BOSException, RemoteException;
    public void IsNdelete(Context ctx, String areaName) throws BOSException, EASBizException, RemoteException;
}