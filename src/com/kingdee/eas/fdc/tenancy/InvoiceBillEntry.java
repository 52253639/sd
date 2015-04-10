package com.kingdee.eas.fdc.tenancy;

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
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class InvoiceBillEntry extends CoreBillEntryBase implements IInvoiceBillEntry
{
    public InvoiceBillEntry()
    {
        super();
        registerInterface(IInvoiceBillEntry.class, this);
    }
    public InvoiceBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IInvoiceBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B6AABFA7");
    }
    private InvoiceBillEntryController getController() throws BOSException
    {
        return (InvoiceBillEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public InvoiceBillEntryInfo getInvoiceBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getInvoiceBillEntryInfo(getContext(), pk);
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
    public InvoiceBillEntryInfo getInvoiceBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getInvoiceBillEntryInfo(getContext(), pk, selector);
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
    public InvoiceBillEntryInfo getInvoiceBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getInvoiceBillEntryInfo(getContext(), oql);
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
    public InvoiceBillEntryCollection getInvoiceBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getInvoiceBillEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public InvoiceBillEntryCollection getInvoiceBillEntryCollection() throws BOSException
    {
        try {
            return getController().getInvoiceBillEntryCollection(getContext());
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
    public InvoiceBillEntryCollection getInvoiceBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getInvoiceBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}