package com.kingdee.eas.fdc.aimcost.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.aimcost.NewPlanIndexInfo;
import com.kingdee.eas.framework.app.CoreBillEntryBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.aimcost.NewPlanIndexCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface NewPlanIndexController extends CoreBillEntryBaseController
{
    public NewPlanIndexInfo getNewPlanIndexInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public NewPlanIndexInfo getNewPlanIndexInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public NewPlanIndexInfo getNewPlanIndexInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public NewPlanIndexCollection getNewPlanIndexCollection(Context ctx) throws BOSException, RemoteException;
    public NewPlanIndexCollection getNewPlanIndexCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public NewPlanIndexCollection getNewPlanIndexCollection(Context ctx, String oql) throws BOSException, RemoteException;
}