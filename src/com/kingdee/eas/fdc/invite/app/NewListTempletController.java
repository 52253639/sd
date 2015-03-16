package com.kingdee.eas.fdc.invite.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.invite.NewListTempletInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.invite.NewListTempletCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface NewListTempletController extends FDCBillController
{
    public NewListTempletInfo getNewListTempletInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public NewListTempletInfo getNewListTempletInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public NewListTempletInfo getNewListTempletInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public NewListTempletCollection getNewListTempletCollection(Context ctx) throws BOSException, RemoteException;
    public NewListTempletCollection getNewListTempletCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public NewListTempletCollection getNewListTempletCollection(Context ctx, String oql) throws BOSException, RemoteException;
}