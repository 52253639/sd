package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.app.CoreBaseController;
import com.kingdee.eas.fdc.sellhouse.ShareSaleManInfo;
import com.kingdee.eas.fdc.sellhouse.ShareSaleManCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ShareSaleManController extends CoreBaseController
{
    public ShareSaleManInfo getShareSaleManInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ShareSaleManInfo getShareSaleManInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ShareSaleManInfo getShareSaleManInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ShareSaleManCollection getShareSaleManCollection(Context ctx) throws BOSException, RemoteException;
    public ShareSaleManCollection getShareSaleManCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ShareSaleManCollection getShareSaleManCollection(Context ctx, String oql) throws BOSException, RemoteException;
}