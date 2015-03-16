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

public class Invoice extends DataBase implements IInvoice
{
    public Invoice()
    {
        super();
        registerInterface(IInvoice.class, this);
    }
    public Invoice(Context ctx)
    {
        super(ctx);
        registerInterface(IInvoice.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C29B7FF2");
    }
    private InvoiceController getController() throws BOSException
    {
        return (InvoiceController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public InvoiceInfo getInvoiceInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getInvoiceInfo(getContext(), pk);
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
    public InvoiceInfo getInvoiceInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getInvoiceInfo(getContext(), pk, selector);
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
    public InvoiceInfo getInvoiceInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getInvoiceInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public InvoiceCollection getInvoiceCollection() throws BOSException
    {
        try {
            return getController().getInvoiceCollection(getContext());
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
    public InvoiceCollection getInvoiceCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getInvoiceCollection(getContext(), view);
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
    public InvoiceCollection getInvoiceCollection(String oql) throws BOSException
    {
        try {
            return getController().getInvoiceCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}