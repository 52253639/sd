package com.kingdee.eas.fdc.basecrm.app;

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
import com.kingdee.eas.framework.app.TreeBaseController;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataGroupInfo;
import com.kingdee.eas.fdc.basecrm.FDCCusBaseDataGroupCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCCusBaseDataGroupController extends TreeBaseController
{
    public FDCCusBaseDataGroupInfo getFDCCusBaseDataGroupInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public FDCCusBaseDataGroupInfo getFDCCusBaseDataGroupInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public FDCCusBaseDataGroupInfo getFDCCusBaseDataGroupInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public FDCCusBaseDataGroupCollection getFDCCusBaseDataGroupCollection(Context ctx) throws BOSException, RemoteException;
    public FDCCusBaseDataGroupCollection getFDCCusBaseDataGroupCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public FDCCusBaseDataGroupCollection getFDCCusBaseDataGroupCollection(Context ctx, String oql) throws BOSException, RemoteException;
}