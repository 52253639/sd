package com.kingdee.eas.fdc.aimcost.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.app.CoreBaseController;
import com.kingdee.eas.fdc.aimcost.FDCCostLogCollection;
import com.kingdee.eas.fdc.aimcost.FDCCostLogInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCCostLogController extends CoreBaseController
{
    public FDCCostLogCollection getFDCCostLogCollection(Context ctx) throws BOSException, RemoteException;
    public FDCCostLogCollection getFDCCostLogCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public FDCCostLogCollection getFDCCostLogCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public FDCCostLogInfo getFDCCostLogInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public FDCCostLogInfo getFDCCostLogInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public FDCCostLogInfo getFDCCostLogInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public Map getHistoryByMonth(Context ctx, Map selectMonth) throws BOSException, EASBizException, RemoteException;
}