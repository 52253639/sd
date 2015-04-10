package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class InvoiceBill extends TenBillBase implements IInvoiceBill
{
    public InvoiceBill()
    {
        super();
        registerInterface(IInvoiceBill.class, this);
    }
    public InvoiceBill(Context ctx)
    {
        super(ctx);
        registerInterface(IInvoiceBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F345578B");
    }
    private InvoiceBillController getController() throws BOSException
    {
        return (InvoiceBillController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public InvoiceBillInfo getInvoiceBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getInvoiceBillInfo(getContext(), pk);
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
    public InvoiceBillInfo getInvoiceBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getInvoiceBillInfo(getContext(), pk, selector);
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
    public InvoiceBillInfo getInvoiceBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getInvoiceBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public InvoiceBillCollection getInvoiceBillCollection() throws BOSException
    {
        try {
            return getController().getInvoiceBillCollection(getContext());
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
    public InvoiceBillCollection getInvoiceBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getInvoiceBillCollection(getContext(), view);
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
    public InvoiceBillCollection getInvoiceBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getInvoiceBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}