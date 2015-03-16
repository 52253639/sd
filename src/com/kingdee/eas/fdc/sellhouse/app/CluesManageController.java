package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseController;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.sellhouse.CluesManageInfo;
import com.kingdee.eas.fdc.sellhouse.CluesManageCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface CluesManageController extends FDCDataBaseController
{
    public CluesManageInfo getCluesManageInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public CluesManageInfo getCluesManageInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public CluesManageInfo getCluesManageInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public CluesManageCollection getCluesManageCollection(Context ctx) throws BOSException, RemoteException;
    public CluesManageCollection getCluesManageCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public CluesManageCollection getCluesManageCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void shareClues(Context ctx, CluesManageCollection objectColl, Map map) throws BOSException, EASBizException, RemoteException;
    public void deliverClues(Context ctx, CluesManageInfo model, Map map) throws BOSException, EASBizException, RemoteException;
    public void importClues(Context ctx, CluesManageCollection res) throws BOSException, EASBizException, RemoteException;
    public SHECustomerInfo updateCluesStatus(Context ctx, CluesManageInfo model, String firstLinkMan) throws BOSException, EASBizException, RemoteException;
}