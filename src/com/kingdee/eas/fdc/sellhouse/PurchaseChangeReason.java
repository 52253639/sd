package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class PurchaseChangeReason extends FDCDataBase implements IPurchaseChangeReason
{
    public PurchaseChangeReason()
    {
        super();
        registerInterface(IPurchaseChangeReason.class, this);
    }
    public PurchaseChangeReason(Context ctx)
    {
        super(ctx);
        registerInterface(IPurchaseChangeReason.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("17105910");
    }
    private PurchaseChangeReasonController getController() throws BOSException
    {
        return (PurchaseChangeReasonController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public PurchaseChangeReasonInfo getPurchaseChangeReasonInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseChangeReasonInfo(getContext(), pk);
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
    public PurchaseChangeReasonInfo getPurchaseChangeReasonInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseChangeReasonInfo(getContext(), pk, selector);
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
    public PurchaseChangeReasonInfo getPurchaseChangeReasonInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseChangeReasonInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public PurchaseChangeReasonCollection getPurchaseChangeReasonCollection() throws BOSException
    {
        try {
            return getController().getPurchaseChangeReasonCollection(getContext());
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
    public PurchaseChangeReasonCollection getPurchaseChangeReasonCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPurchaseChangeReasonCollection(getContext(), view);
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
    public PurchaseChangeReasonCollection getPurchaseChangeReasonCollection(String oql) throws BOSException
    {
        try {
            return getController().getPurchaseChangeReasonCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}