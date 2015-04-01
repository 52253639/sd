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
import com.kingdee.eas.fdc.tenancy.OtherBillInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.tenancy.OtherBillCollection;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface OtherBillController extends TenBillBaseController
{
    public OtherBillInfo getOtherBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public OtherBillInfo getOtherBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public OtherBillInfo getOtherBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public OtherBillCollection getOtherBillCollection(Context ctx) throws BOSException, RemoteException;
    public OtherBillCollection getOtherBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public OtherBillCollection getOtherBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
}