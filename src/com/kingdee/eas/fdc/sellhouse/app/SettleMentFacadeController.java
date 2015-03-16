package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.framework.*;
import java.util.Set;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface SettleMentFacadeController extends BizController
{
    public Set dealQuitRoom(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public Set dealChangeRoom(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public void dealSaleBalance(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public void dealAntiSaleBalance(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
}