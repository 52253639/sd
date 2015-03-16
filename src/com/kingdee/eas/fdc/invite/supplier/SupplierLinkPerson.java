package com.kingdee.eas.fdc.invite.supplier;

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
import com.kingdee.eas.fdc.invite.supplier.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class SupplierLinkPerson extends CoreBillEntryBase implements ISupplierLinkPerson
{
    public SupplierLinkPerson()
    {
        super();
        registerInterface(ISupplierLinkPerson.class, this);
    }
    public SupplierLinkPerson(Context ctx)
    {
        super(ctx);
        registerInterface(ISupplierLinkPerson.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("17612059");
    }
    private SupplierLinkPersonController getController() throws BOSException
    {
        return (SupplierLinkPersonController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SupplierLinkPersonInfo getSupplierLinkPersonInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierLinkPersonInfo(getContext(), pk);
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
    public SupplierLinkPersonInfo getSupplierLinkPersonInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierLinkPersonInfo(getContext(), pk, selector);
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
    public SupplierLinkPersonInfo getSupplierLinkPersonInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierLinkPersonInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SupplierLinkPersonCollection getSupplierLinkPersonCollection() throws BOSException
    {
        try {
            return getController().getSupplierLinkPersonCollection(getContext());
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
    public SupplierLinkPersonCollection getSupplierLinkPersonCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSupplierLinkPersonCollection(getContext(), view);
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
    public SupplierLinkPersonCollection getSupplierLinkPersonCollection(String oql) throws BOSException
    {
        try {
            return getController().getSupplierLinkPersonCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}