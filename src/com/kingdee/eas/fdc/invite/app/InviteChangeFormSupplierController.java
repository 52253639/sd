package com.kingdee.eas.fdc.invite.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.app.CoreBillEntryBaseController;
import com.kingdee.eas.fdc.invite.InviteChangeFormSupplierCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.invite.InviteChangeFormSupplierInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface InviteChangeFormSupplierController extends CoreBillEntryBaseController
{
    public InviteChangeFormSupplierInfo getInviteChangeFormSupplierInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public InviteChangeFormSupplierCollection getInviteChangeFormSupplierCollection(Context ctx) throws BOSException, RemoteException;
    public InviteChangeFormSupplierCollection getInviteChangeFormSupplierCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
}