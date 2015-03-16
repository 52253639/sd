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
import com.kingdee.eas.fdc.sellhouse.FDCCustomerCollection;
import com.kingdee.eas.framework.app.DataBaseController;
import java.util.Map;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface FDCCustomerController extends DataBaseController
{
    public FDCCustomerInfo getFDCCustomerInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public FDCCustomerInfo getFDCCustomerInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public FDCCustomerInfo getFDCCustomerInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public FDCCustomerCollection getFDCCustomerCollection(Context ctx) throws BOSException, RemoteException;
    public FDCCustomerCollection getFDCCustomerCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public FDCCustomerCollection getFDCCustomerCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void addToSysCustomer(Context ctx, String id) throws BOSException, EASBizException, RemoteException;
    public void blankOut(Context ctx, List idList) throws BOSException, RemoteException;
    public void pickUp(Context ctx, List idList) throws BOSException, RemoteException;
    public void modifyName(Context ctx, FDCCustomerInfo fdccustomer) throws BOSException, RemoteException;
    public void signImportantTrack(Context ctx, List idList) throws BOSException, RemoteException;
    public void cancelImportantTrack(Context ctx, List idList) throws BOSException, RemoteException;
    public void switchTo(Context ctx, List idList, String salesmanId) throws BOSException, RemoteException;
    public void addToSysCustomer(Context ctx, String id, List list) throws BOSException, EASBizException, RemoteException;
    public Map verifySave(Context ctx, FDCCustomerInfo model) throws BOSException, EASBizException, RemoteException;
    public void setStatus(Context ctx, List idList) throws BOSException, RemoteException;
    public Map verifySave(Context ctx, IObjectValue model, boolean isSingle) throws BOSException, EASBizException, RemoteException;
    public void updateTenancyBill(Context ctx, String fdcCustID) throws BOSException, EASBizException, RemoteException;
}