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
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.sellhouse.AgioBillCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.sellhouse.AgioBillInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface AgioBillController extends FDCBillController
{
    public AgioBillInfo getAgioBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public AgioBillInfo getAgioBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public AgioBillInfo getAgioBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public AgioBillCollection getAgioBillCollection(Context ctx) throws BOSException, RemoteException;
    public AgioBillCollection getAgioBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public AgioBillCollection getAgioBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void enable(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public void disEnable(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
}