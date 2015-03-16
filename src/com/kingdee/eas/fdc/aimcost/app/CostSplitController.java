package com.kingdee.eas.fdc.aimcost.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.aimcost.CostSplitInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.aimcost.CostSplitCollection;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.framework.app.CoreBaseController;
import java.util.List;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface CostSplitController extends CoreBaseController
{
    public CostSplitInfo getCostSplitInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public CostSplitInfo getCostSplitInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public CostSplitInfo getCostSplitInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public CostSplitCollection getCostSplitCollection(Context ctx) throws BOSException, RemoteException;
    public CostSplitCollection getCostSplitCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public CostSplitCollection getCostSplitCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException, RemoteException;
    public void audit(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
    public void unAudit(Context ctx, List idList) throws BOSException, EASBizException, RemoteException;
}