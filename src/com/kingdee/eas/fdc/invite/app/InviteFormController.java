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
import com.kingdee.eas.fdc.invite.InviteFormInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.invite.InviteFormCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseController;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface InviteFormController extends FDCDataBaseController
{
    public InviteFormInfo getInviteFormInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public InviteFormInfo getInviteFormInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public InviteFormInfo getInviteFormInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public InviteFormCollection getInviteFormCollection(Context ctx) throws BOSException, RemoteException;
    public InviteFormCollection getInviteFormCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public InviteFormCollection getInviteFormCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public boolean updateRelateData(Context ctx, String oldID, String newID, Object[] tables) throws BOSException, RemoteException;
    public Object[] getRelateData(Context ctx, String id, String[] tables) throws BOSException, RemoteException;
}