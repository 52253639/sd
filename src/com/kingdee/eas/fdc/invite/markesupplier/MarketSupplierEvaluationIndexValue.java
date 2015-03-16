package com.kingdee.eas.fdc.invite.markesupplier;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.invite.markesupplier.app.*;

public class MarketSupplierEvaluationIndexValue extends CoreBillEntryBase implements IMarketSupplierEvaluationIndexValue
{
    public MarketSupplierEvaluationIndexValue()
    {
        super();
        registerInterface(IMarketSupplierEvaluationIndexValue.class, this);
    }
    public MarketSupplierEvaluationIndexValue(Context ctx)
    {
        super(ctx);
        registerInterface(IMarketSupplierEvaluationIndexValue.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9B553C71");
    }
    private MarketSupplierEvaluationIndexValueController getController() throws BOSException
    {
        return (MarketSupplierEvaluationIndexValueController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public MarketSupplierEvaluationIndexValueInfo getMarketSupplierEvaluationIndexValueInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierEvaluationIndexValueInfo(getContext(), pk);
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
    public MarketSupplierEvaluationIndexValueInfo getMarketSupplierEvaluationIndexValueInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierEvaluationIndexValueInfo(getContext(), pk, selector);
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
    public MarketSupplierEvaluationIndexValueInfo getMarketSupplierEvaluationIndexValueInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getMarketSupplierEvaluationIndexValueInfo(getContext(), oql);
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
    public MarketSupplierEvaluationIndexValueCollection getMarketSupplierEvaluationIndexValueCollection(String oql) throws BOSException
    {
        try {
            return getController().getMarketSupplierEvaluationIndexValueCollection(getContext(), oql);
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
    public MarketSupplierEvaluationIndexValueCollection getMarketSupplierEvaluationIndexValueCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getMarketSupplierEvaluationIndexValueCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public MarketSupplierEvaluationIndexValueCollection getMarketSupplierEvaluationIndexValueCollection() throws BOSException
    {
        try {
            return getController().getMarketSupplierEvaluationIndexValueCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}