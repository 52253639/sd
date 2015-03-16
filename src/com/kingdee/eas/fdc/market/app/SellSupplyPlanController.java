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
import com.kingdee.eas.fdc.market.SellSupplyPlanCollection;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.SellSupplyPlanInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SellSupplyPlanController extends CoreBillBaseController
{
    public SellSupplyPlanCollection getSellSupplyPlanCollection(Context ctx) throws BOSException, RemoteException;
    public SellSupplyPlanCollection getSellSupplyPlanCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SellSupplyPlanCollection getSellSupplyPlanCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public SellSupplyPlanInfo getSellSupplyPlanInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SellSupplyPlanInfo getSellSupplyPlanInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SellSupplyPlanInfo getSellSupplyPlanInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
}