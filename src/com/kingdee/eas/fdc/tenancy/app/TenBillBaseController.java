package com.kingdee.eas.fdc.tenancy.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.framework.app.CoreBillBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.tenancy.TenBillBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.tenancy.TenBillBaseInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface TenBillBaseController extends CoreBillBaseController
{
    public TenBillBaseInfo getTenBillBaseInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public TenBillBaseInfo getTenBillBaseInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public TenBillBaseInfo getTenBillBaseInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public TenBillBaseCollection getTenBillBaseCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public TenBillBaseCollection getTenBillBaseCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public TenBillBaseCollection getTenBillBaseCollection(Context ctx) throws BOSException, RemoteException;
    public void audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void setAudittingStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public boolean checkCanSubmit(Context ctx, String id) throws BOSException, EASBizException, RemoteException;
    public Map fetchInitData(Context ctx, Map paramMap) throws BOSException, EASBizException, RemoteException;
    public Map fetchFilterInitData(Context ctx, Map paramMap) throws BOSException, EASBizException, RemoteException;
}