package com.kingdee.eas.fdc.tenancy.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.tenancy.TenancyModificationInfo;
import com.kingdee.eas.fdc.tenancy.TenancyModificationCollection;
import java.util.Date;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface TenancyModificationController extends TenBillBaseController
{
    public TenancyModificationInfo getTenancyModificationInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public TenancyModificationInfo getTenancyModificationInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public TenancyModificationInfo getTenancyModificationInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public TenancyModificationCollection getTenancyModificationCollection(Context ctx) throws BOSException, RemoteException;
    public TenancyModificationCollection getTenancyModificationCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public TenancyModificationCollection getTenancyModificationCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public boolean incNewAddCheck(Context ctx, String tenBillId, Date incNewDate) throws BOSException, RemoteException;
    public boolean incNewEditCheck(Context ctx, String tenBillId, Date incNewDate) throws BOSException, RemoteException;
    public boolean freesNewAddCheck(Context ctx, String tenBillId, Date stratDate, Date endDate) throws BOSException, RemoteException;
    public boolean freesNewEditCheck(Context ctx, String tenBillId, Date startDate, Date endDate) throws BOSException, RemoteException;
    public Date getLeastPaidDate(Context ctx, String tenBillID) throws BOSException, RemoteException;
}