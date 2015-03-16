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
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basecrm.CustomerChangeLogInfo;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseController;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basecrm.CustomerChangeLogCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface CustomerChangeLogController extends FDCDataBaseController
{
    public CustomerChangeLogInfo getCustomerChangeLogInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public CustomerChangeLogInfo getCustomerChangeLogInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public CustomerChangeLogInfo getCustomerChangeLogInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public CustomerChangeLogCollection getCustomerChangeLogCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public CustomerChangeLogCollection getCustomerChangeLogCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public CustomerChangeLogCollection getCustomerChangeLogCollection(Context ctx) throws BOSException, RemoteException;
}