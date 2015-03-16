package com.kingdee.eas.fdc.finance.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.finance.AcctConCostCollection;
import com.kingdee.eas.fdc.finance.AcctConCostInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.app.CoreBaseController;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface AcctConCostController extends CoreBaseController
{
    public AcctConCostInfo getAcctConCostInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public AcctConCostInfo getAcctConCostInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public AcctConCostInfo getAcctConCostInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void update(Context ctx, IObjectPK pk, AcctConCostInfo model) throws BOSException, EASBizException, RemoteException;
    public void updatePartial(Context ctx, AcctConCostInfo model, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public void updateBigObject(Context ctx, IObjectPK pk, AcctConCostInfo model) throws BOSException, RemoteException;
    public void delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public AcctConCostCollection getAcctConCostCollection(Context ctx) throws BOSException, RemoteException;
    public AcctConCostCollection getAcctConCostCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public AcctConCostCollection getAcctConCostCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public IObjectPK[] delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] delete(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException, RemoteException;
}