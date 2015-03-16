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
import com.kingdee.eas.fdc.invite.supplier.IFDCSplAuditBaseBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.invite.markesupplier.app.*;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditBaseBill;

public class MarketSupplierEvaluation extends FDCSplAuditBaseBill implements IMarketSupplierEvaluation
{
    public MarketSupplierEvaluation()
    {
        super();
        registerInterface(IMarketSupplierEvaluation.class, this);
    }
    public MarketSupplierEvaluation(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSupplierEvaluation.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5B8D6312");
    }
    private MarketSupplierEvaluationController getController() throws BOSException
    {
        return (MarketSupplierEvaluationController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MarketSupplierEvaluationInfo getMarketSupplierEvaluationInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierEvaluationInfo(getContext(), pk);
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
    public MarketSupplierEvaluationInfo getMarketSupplierEvaluationInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierEvaluationInfo(getContext(), pk, selector);
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
    public MarketSupplierEvaluationInfo getMarketSupplierEvaluationInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierEvaluationInfo(getContext(), oql);
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
    public MarketSupplierEvaluationCollection getMarketSupplierEvaluationCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSupplierEvaluationCollection(getContext(), oql);
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
    public MarketSupplierEvaluationCollection getMarketSupplierEvaluationCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSupplierEvaluationCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MarketSupplierEvaluationCollection getMarketSupplierEvaluationCollection() throws BOSException
    {
        try {
            return getController().getMarketSupplierEvaluationCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}