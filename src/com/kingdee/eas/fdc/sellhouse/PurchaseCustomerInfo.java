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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class PurchaseCustomerInfo extends DataBase implements IPurchaseCustomerInfo
{
    public PurchaseCustomerInfo()
    {
        super();
        registerInterface(IPurchaseCustomerInfo.class, this);
    }
    public PurchaseCustomerInfo(Context ctx)
    {
        super(ctx);
        registerInterface(IPurchaseCustomerInfo.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("54C584A8");
    }
    private PurchaseCustomerInfoController getController() throws BOSException
    {
        return (PurchaseCustomerInfoController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public PurchaseCustomerInfoInfo getPurchaseCustomerInfoInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseCustomerInfoInfo(getContext(), pk);
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
    public PurchaseCustomerInfoInfo getPurchaseCustomerInfoInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseCustomerInfoInfo(getContext(), pk, selector);
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
    public PurchaseCustomerInfoInfo getPurchaseCustomerInfoInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseCustomerInfoInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public PurchaseCustomerInfoCollection getPurchaseCustomerInfoCollection() throws BOSException
    {
        try {
            return getController().getPurchaseCustomerInfoCollection(getContext());
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
    public PurchaseCustomerInfoCollection getPurchaseCustomerInfoCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPurchaseCustomerInfoCollection(getContext(), view);
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
    public PurchaseCustomerInfoCollection getPurchaseCustomerInfoCollection(String oql) throws BOSException
    {
        try {
            return getController().getPurchaseCustomerInfoCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}