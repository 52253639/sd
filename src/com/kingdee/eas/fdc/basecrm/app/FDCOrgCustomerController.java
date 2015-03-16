package com.kingdee.eas.fdc.basecrm.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerInfo;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basecrm.FDCOrgCustomerCollection;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCOrgCustomerController extends FDCBaseCustomerController
{
    public FDCOrgCustomerInfo getFDCOrgCustomerInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public FDCOrgCustomerInfo getFDCOrgCustomerInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public FDCOrgCustomerInfo getFDCOrgCustomerInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public FDCOrgCustomerCollection getFDCOrgCustomerCollection(Context ctx) throws BOSException, RemoteException;
    public FDCOrgCustomerCollection getFDCOrgCustomerCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public FDCOrgCustomerCollection getFDCOrgCustomerCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void changeCusName(Context ctx, String cusId, String newName) throws BOSException, EASBizException, RemoteException;
    public void updateCustomerInfo(Context ctx, List ids) throws BOSException, EASBizException, RemoteException;
    public void shareCustomer(Context ctx, List cusIds, String shareOrgId) throws BOSException, EASBizException, RemoteException;
    public void mergeCustomer(Context ctx, List srcIds, String toId) throws BOSException, EASBizException, RemoteException;
    public void importCustomer(Context ctx, IObjectCollection res) throws BOSException, EASBizException, RemoteException;
    public void submitEnterpriceCustomer(Context ctx, FDCOrgCustomerInfo model, String name, String phone) throws BOSException, EASBizException, RemoteException;
    public void updateEnterpriceCustomer(Context ctx, Set set, String str1, String str2, boolean isLinkMan) throws BOSException, EASBizException, RemoteException;
}