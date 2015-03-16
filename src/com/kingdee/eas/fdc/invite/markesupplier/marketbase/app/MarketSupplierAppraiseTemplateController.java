package com.kingdee.eas.fdc.invite.markesupplier.marketbase.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.app.DataBaseController;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface MarketSupplierAppraiseTemplateController extends DataBaseController
{
    public MarketSupplierAppraiseTemplateInfo getMarketSupplierAppraiseTemplateInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public MarketSupplierAppraiseTemplateInfo getMarketSupplierAppraiseTemplateInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public MarketSupplierAppraiseTemplateInfo getMarketSupplierAppraiseTemplateInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public MarketSupplierAppraiseTemplateCollection getMarketSupplierAppraiseTemplateCollection(Context ctx) throws BOSException, RemoteException;
    public MarketSupplierAppraiseTemplateCollection getMarketSupplierAppraiseTemplateCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public MarketSupplierAppraiseTemplateCollection getMarketSupplierAppraiseTemplateCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public void audit(Context ctx, MarketSupplierAppraiseTemplateInfo model) throws BOSException, RemoteException;
    public void unaudit(Context ctx, MarketSupplierAppraiseTemplateInfo model) throws BOSException, RemoteException;
}