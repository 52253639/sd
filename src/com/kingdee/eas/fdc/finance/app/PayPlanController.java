package com.kingdee.eas.fdc.finance.app;

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
import com.kingdee.eas.fdc.finance.PayPlanInfo;
import com.kingdee.eas.fdc.finance.PayPlanCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.framework.app.BillBaseController;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface PayPlanController extends BillBaseController
{
    public PayPlanInfo getPayPlanInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public PayPlanInfo getPayPlanInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public PayPlanInfo getPayPlanInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public PayPlanCollection getPayPlanCollection(Context ctx) throws BOSException, RemoteException;
    public PayPlanCollection getPayPlanCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public PayPlanCollection getPayPlanCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public Map getData(Context ctx, String orgUnitId, Map param) throws BOSException, EASBizException, RemoteException;
    public Map getData(Context ctx, Set orgIds, Map param) throws BOSException, EASBizException, RemoteException;
    public Map getData(Context ctx, Set orgIds, Set conTypeIdS, Map param) throws BOSException, EASBizException, RemoteException;
    public Map getData(Context ctx, String orgUnitId, Set conTypeIds, Map param) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, Map param) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, Map param) throws BOSException, EASBizException, RemoteException;
}