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
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class PurchaseSaleMan extends CoreBase implements IPurchaseSaleMan
{
    public PurchaseSaleMan()
    {
        super();
        registerInterface(IPurchaseSaleMan.class, this);
    }
    public PurchaseSaleMan(Context ctx)
    {
        super(ctx);
        registerInterface(IPurchaseSaleMan.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0B777B17");
    }
    private PurchaseSaleManController getController() throws BOSException
    {
        return (PurchaseSaleManController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public PurchaseSaleManInfo getPurchaseSaleManInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseSaleManInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public PurchaseSaleManInfo getPurchaseSaleManInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseSaleManInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql oql
     *@return
     */
    public PurchaseSaleManInfo getPurchaseSaleManInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseSaleManInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PurchaseSaleManCollection getPurchaseSaleManCollection() throws BOSException
    {
        try {
            return getController().getPurchaseSaleManCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view view
     *@return
     */
    public PurchaseSaleManCollection getPurchaseSaleManCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPurchaseSaleManCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql oql
     *@return
     */
    public PurchaseSaleManCollection getPurchaseSaleManCollection(String oql) throws BOSException
    {
        try {
            return getController().getPurchaseSaleManCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}