package com.kingdee.eas.fdc.invite.markesupplier.marketbase.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAreaInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAreaCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseController;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface MarketSplAreaController extends FDCDataBaseController
{
    public MarketSplAreaInfo getMarketSplAreaInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public MarketSplAreaInfo getMarketSplAreaInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public MarketSplAreaInfo getMarketSplAreaInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public MarketSplAreaCollection getMarketSplAreaCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public MarketSplAreaCollection getMarketSplAreaCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public MarketSplAreaCollection getMarketSplAreaCollection(Context ctx) throws BOSException, RemoteException;
    public void IsNdelete(Context ctx, String areaName) throws BOSException, EASBizException, RemoteException;
}