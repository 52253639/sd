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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface TranBusinessOverViewController extends TranPayListEntryController
{
    public TranBusinessOverViewInfo getTranBusinessOverViewInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public TranBusinessOverViewInfo getTranBusinessOverViewInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public TranBusinessOverViewInfo getTranBusinessOverViewInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public TranBusinessOverViewCollection getTranBusinessOverViewCollection(Context ctx) throws BOSException, RemoteException;
    public TranBusinessOverViewCollection getTranBusinessOverViewCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public TranBusinessOverViewCollection getTranBusinessOverViewCollection(Context ctx, String oql) throws BOSException, RemoteException;
}