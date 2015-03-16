package com.kingdee.eas.fdc.invite.markesupplier.app;

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
import com.kingdee.eas.fdc.invite.supplier.app.FDCSplAuditBaseBillController;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface MarketSupplierEvaluationController extends FDCSplAuditBaseBillController
{
    public MarketSupplierEvaluationInfo getMarketSupplierEvaluationInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public MarketSupplierEvaluationInfo getMarketSupplierEvaluationInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public MarketSupplierEvaluationInfo getMarketSupplierEvaluationInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public MarketSupplierEvaluationCollection getMarketSupplierEvaluationCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public MarketSupplierEvaluationCollection getMarketSupplierEvaluationCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public MarketSupplierEvaluationCollection getMarketSupplierEvaluationCollection(Context ctx) throws BOSException, RemoteException;
}