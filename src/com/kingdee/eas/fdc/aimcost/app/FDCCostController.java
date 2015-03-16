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
import com.kingdee.eas.fdc.aimcost.FDCCostInfo;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.aimcost.FDCCostCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.app.CoreBaseController;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCCostController extends CoreBaseController
{
    public FDCCostCollection getFDCCostCollection(Context ctx) throws BOSException, RemoteException;
    public FDCCostCollection getFDCCostCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public FDCCostCollection getFDCCostCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public FDCCostInfo getFDCCostInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public FDCCostInfo getFDCCostInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public FDCCostInfo getFDCCostInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public Map getHistoryByMonth(Context ctx, Map selectMonth) throws BOSException, EASBizException, RemoteException;
}