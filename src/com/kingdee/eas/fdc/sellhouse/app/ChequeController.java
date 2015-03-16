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
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;
import com.kingdee.eas.fdc.sellhouse.ChequeCollection;
import com.kingdee.eas.fdc.sellhouse.ChequeInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ChequeController extends CoreBillBaseController
{
    public ChequeInfo getChequeInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ChequeInfo getChequeInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ChequeInfo getChequeInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ChequeCollection getChequeCollection(Context ctx) throws BOSException, RemoteException;
    public ChequeCollection getChequeCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ChequeCollection getChequeCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void addBatch(Context ctx, ChequeCollection model) throws BOSException, EASBizException, RemoteException;
    public void vc(Context ctx, List ids) throws BOSException, EASBizException, RemoteException;
    public void abandon(Context ctx, List ids) throws BOSException, EASBizException, RemoteException;
    public void distribute(Context ctx, String[] ids, String newKeepOrgUnitId, String newKeeperId) throws BOSException, EASBizException, RemoteException;
}