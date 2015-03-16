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
import com.kingdee.eas.fdc.aimcost.TempConstrPlanIndexEntryCollection;
import com.kingdee.eas.framework.app.CoreBillEntryBaseController;
import com.kingdee.eas.fdc.aimcost.TempConstrPlanIndexEntryInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface TempConstrPlanIndexEntryController extends CoreBillEntryBaseController
{
    public TempConstrPlanIndexEntryInfo getTempConstrPlanIndexEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public TempConstrPlanIndexEntryInfo getTempConstrPlanIndexEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public TempConstrPlanIndexEntryInfo getTempConstrPlanIndexEntryInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public TempConstrPlanIndexEntryCollection getTempConstrPlanIndexEntryCollection(Context ctx) throws BOSException, RemoteException;
    public TempConstrPlanIndexEntryCollection getTempConstrPlanIndexEntryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public TempConstrPlanIndexEntryCollection getTempConstrPlanIndexEntryCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public IObjectPK[] delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] delete(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException, RemoteException;
}