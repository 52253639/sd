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
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import java.util.Map;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface AimCostController extends CoreBillBaseController
{
    public AimCostInfo getAimCostInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public AimCostInfo getAimCostInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public AimCostCollection getAimCostCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public AimCostCollection getAimCostCollection(Context ctx) throws BOSException, RemoteException;
    public AimCostCollection getAimCostCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
    public void unaudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void unaudit(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
    public Map getAimCostVers(Context ctx, Map param) throws BOSException, EASBizException, RemoteException;
    public void recense(Context ctx, AimCostInfo model) throws BOSException, EASBizException, RemoteException;
}