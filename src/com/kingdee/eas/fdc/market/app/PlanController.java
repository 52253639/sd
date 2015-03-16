package com.kingdee.eas.fdc.market.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.market.PlanCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.market.PlanInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface PlanController extends FDCBillController
{
    public PlanInfo getPlanInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public PlanInfo getPlanInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public PlanInfo getPlanInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public PlanCollection getPlanCollection(Context ctx) throws BOSException, RemoteException;
    public PlanCollection getPlanCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public PlanCollection getPlanCollection(Context ctx, String oql) throws BOSException, RemoteException;
}