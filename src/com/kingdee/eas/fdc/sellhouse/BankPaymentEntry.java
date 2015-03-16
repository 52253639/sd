package com.kingdee.eas.fdc.sellhouse;

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
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class BankPaymentEntry extends CoreBillEntryBase implements IBankPaymentEntry
{
    public BankPaymentEntry()
    {
        super();
        registerInterface(IBankPaymentEntry.class, this);
    }
    public BankPaymentEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IBankPaymentEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A1A86B43");
    }
    private BankPaymentEntryController getController() throws BOSException
    {
        return (BankPaymentEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public BankPaymentEntryInfo getBankPaymentEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getBankPaymentEntryInfo(getContext(), pk);
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
    public BankPaymentEntryInfo getBankPaymentEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getBankPaymentEntryInfo(getContext(), pk, selector);
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
    public BankPaymentEntryInfo getBankPaymentEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getBankPaymentEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public BankPaymentEntryCollection getBankPaymentEntryCollection() throws BOSException
    {
        try {
            return getController().getBankPaymentEntryCollection(getContext());
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
    public BankPaymentEntryCollection getBankPaymentEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getBankPaymentEntryCollection(getContext(), view);
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
    public BankPaymentEntryCollection getBankPaymentEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getBankPaymentEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}