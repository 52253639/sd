package com.kingdee.eas.fdc.basecrm.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.eas.fdc.basecrm.MarketUnitSaleManCollection;
import java.lang.String;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basecrm.MarketUnitSaleManInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface MarketUnitSaleManController extends BizController
{
    public boolean exists(Context ctx, IObjectPK pk) throws BOSException, RemoteException;
    public boolean exists(Context ctx, FilterInfo filter) throws BOSException, RemoteException;
    public boolean exists(Context ctx, String oql) throws BOSException, RemoteException;
    public MarketUnitSaleManInfo getValue(Context ctx, IObjectPK pk) throws BOSException, RemoteException;
    public MarketUnitSaleManInfo getValue(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, RemoteException;
    public MarketUnitSaleManInfo getValue(Context ctx, String oql) throws BOSException, RemoteException;
    public IObjectPK addnew(Context ctx, MarketUnitSaleManInfo model) throws BOSException, RemoteException;
    public void addnew(Context ctx, IObjectPK pk, MarketUnitSaleManInfo model) throws BOSException, RemoteException;
    public void update(Context ctx, IObjectPK pk, MarketUnitSaleManInfo model) throws BOSException, RemoteException;
    public void updatePartial(Context ctx, MarketUnitSaleManInfo model, SelectorItemCollection selector) throws BOSException, RemoteException;
    public void updateBigObject(Context ctx, IObjectPK pk, MarketUnitSaleManInfo model) throws BOSException, RemoteException;
    public void delete(Context ctx, IObjectPK pk) throws BOSException, RemoteException;
    public IObjectPK[] getPKList(Context ctx) throws BOSException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, String oql) throws BOSException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, FilterInfo filter, SorterItemCollection sorter) throws BOSException, RemoteException;
    public MarketUnitSaleManCollection getCollection(Context ctx) throws BOSException, RemoteException;
    public MarketUnitSaleManCollection getCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public MarketUnitSaleManCollection getCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public IObjectPK[] delete(Context ctx, FilterInfo filter) throws BOSException, RemoteException;
    public IObjectPK[] delete(Context ctx, String oql) throws BOSException, RemoteException;
    public void delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, RemoteException;
}