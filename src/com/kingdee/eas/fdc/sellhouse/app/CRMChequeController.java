package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.CRMChequeCollection;
import java.util.Map;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.eas.fdc.sellhouse.CRMChequeInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface CRMChequeController extends CoreBillBaseController
{
    public CRMChequeInfo getCRMChequeInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public CRMChequeInfo getCRMChequeInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public CRMChequeInfo getCRMChequeInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public CRMChequeCollection getCRMChequeCollection(Context ctx) throws BOSException, RemoteException;
    public CRMChequeCollection getCRMChequeCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public CRMChequeCollection getCRMChequeCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void addBatch(Context ctx, CRMChequeCollection model) throws BOSException, EASBizException, RemoteException;
    public void vc(Context ctx, Map ids) throws BOSException, EASBizException, RemoteException;
    public void abandon(Context ctx, List ids) throws BOSException, EASBizException, RemoteException;
    public void distribute(Context ctx, String[] ids, String newKeepOrgUnitId, String newKeeperId) throws BOSException, EASBizException, RemoteException;
    public void pickCheque(Context ctx, Map dataMap) throws BOSException, RemoteException;
}