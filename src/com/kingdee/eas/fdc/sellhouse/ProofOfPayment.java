package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ObjectBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IObjectBase;

public class ProofOfPayment extends ObjectBase implements IProofOfPayment
{
    public ProofOfPayment()
    {
        super();
        registerInterface(IProofOfPayment.class, this);
    }
    public ProofOfPayment(Context ctx)
    {
        super(ctx);
        registerInterface(IProofOfPayment.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("EC661AE6");
    }
    private ProofOfPaymentController getController() throws BOSException
    {
        return (ProofOfPaymentController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ProofOfPaymentInfo getProofOfPaymentInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProofOfPaymentInfo(getContext(), pk);
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
    public ProofOfPaymentInfo getProofOfPaymentInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProofOfPaymentInfo(getContext(), pk, selector);
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
    public ProofOfPaymentInfo getProofOfPaymentInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProofOfPaymentInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ProofOfPaymentCollection getProofOfPaymentCollection() throws BOSException
    {
        try {
            return getController().getProofOfPaymentCollection(getContext());
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
    public ProofOfPaymentCollection getProofOfPaymentCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProofOfPaymentCollection(getContext(), view);
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
    public ProofOfPaymentCollection getProofOfPaymentCollection(String oql) throws BOSException
    {
        try {
            return getController().getProofOfPaymentCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}