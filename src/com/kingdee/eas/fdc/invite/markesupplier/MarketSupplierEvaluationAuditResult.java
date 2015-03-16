package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.invite.markesupplier.app.*;
import com.kingdee.eas.framework.ICoreBase;

public class MarketSupplierEvaluationAuditResult extends CoreBase implements IMarketSupplierEvaluationAuditResult
{
    public MarketSupplierEvaluationAuditResult()
    {
        super();
        registerInterface(IMarketSupplierEvaluationAuditResult.class, this);
    }
    public MarketSupplierEvaluationAuditResult(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSupplierEvaluationAuditResult.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9B82C566");
    }
    private MarketSupplierEvaluationAuditResultController getController() throws BOSException
    {
        return (MarketSupplierEvaluationAuditResultController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MarketSupplierEvaluationAuditResultInfo getMarketSupplierEvaluationAuditResultInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierEvaluationAuditResultInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public MarketSupplierEvaluationAuditResultInfo getMarketSupplierEvaluationAuditResultInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierEvaluationAuditResultInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public MarketSupplierEvaluationAuditResultInfo getMarketSupplierEvaluationAuditResultInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierEvaluationAuditResultInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public MarketSupplierEvaluationAuditResultCollection getMarketSupplierEvaluationAuditResultCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSupplierEvaluationAuditResultCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public MarketSupplierEvaluationAuditResultCollection getMarketSupplierEvaluationAuditResultCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSupplierEvaluationAuditResultCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MarketSupplierEvaluationAuditResultCollection getMarketSupplierEvaluationAuditResultCollection() throws BOSException
    {
        try {
            return getController().getMarketSupplierEvaluationAuditResultCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}