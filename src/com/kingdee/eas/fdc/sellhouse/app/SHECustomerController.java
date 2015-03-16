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
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import java.util.Map;
import java.util.HashMap;
import com.kingdee.eas.fdc.basecrm.app.FDCBaseCustomerController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.sellhouse.SHECustomerCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SHECustomerController extends FDCBaseCustomerController
{
    public SHECustomerInfo getSHECustomerInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public SHECustomerInfo getSHECustomerInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public SHECustomerInfo getSHECustomerInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public SHECustomerCollection getSHECustomerCollection(Context ctx) throws BOSException, RemoteException;
    public SHECustomerCollection getSHECustomerCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public SHECustomerCollection getSHECustomerCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void updateData(Context ctx, List ids) throws BOSException, EASBizException, RemoteException;
    public void mergeCustomer(Context ctx, List srcIds, String toId) throws BOSException, EASBizException, RemoteException;
    public void changeName(Context ctx, SHECustomerInfo model, Map map) throws BOSException, EASBizException, RemoteException;
    public void shareCustomer(Context ctx, SHECustomerCollection objectColl, Map map) throws BOSException, EASBizException, RemoteException;
    public void deliverCustomer(Context ctx, SHECustomerInfo model, Map map) throws BOSException, EASBizException, RemoteException;
    public void importCustomer(Context ctx, SHECustomerCollection res, SellProjectInfo sellProject) throws BOSException, EASBizException, RemoteException;
    public void submitEnterpriceCustomer(Context ctx, SHECustomerInfo model, String name, String phone) throws BOSException, EASBizException, RemoteException;
    public IObjectPK submitAll(Context ctx, HashMap hashMap, IObjectValue editData) throws BOSException, EASBizException, RemoteException;
}