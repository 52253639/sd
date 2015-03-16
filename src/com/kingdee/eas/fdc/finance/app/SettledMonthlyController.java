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
import com.kingdee.eas.fdc.finance.SettledMonthlyCollection;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.Result;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.finance.SettledMonthlyInfo;
import com.kingdee.eas.framework.app.CoreBaseController;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SettledMonthlyController extends CoreBaseController
{
    public SettledMonthlyInfo getSettledMonthlyInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SettledMonthlyInfo getSettledMonthlyInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SettledMonthlyInfo getSettledMonthlyInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public SettledMonthlyCollection getSettledMonthlyCollection(Context ctx) throws BOSException, RemoteException;
    public SettledMonthlyCollection getSettledMonthlyCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SettledMonthlyCollection getSettledMonthlyCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public Result addnew(Context ctx, CoreBaseCollection colls) throws BOSException, EASBizException, RemoteException;
}