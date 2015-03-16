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
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.invite.InviteSupplierEnterBillInfo;
import com.kingdee.eas.fdc.invite.InviteSupplierEnterBillCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface InviteSupplierEnterBillController extends FDCBillController
{
    public InviteSupplierEnterBillInfo getInviteSupplierEnterBillInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public InviteSupplierEnterBillInfo getInviteSupplierEnterBillInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public InviteSupplierEnterBillInfo getInviteSupplierEnterBillInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public InviteSupplierEnterBillCollection getInviteSupplierEnterBillCollection(Context ctx) throws BOSException, RemoteException;
    public InviteSupplierEnterBillCollection getInviteSupplierEnterBillCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public InviteSupplierEnterBillCollection getInviteSupplierEnterBillCollection(Context ctx, String oql) throws BOSException, RemoteException;
}