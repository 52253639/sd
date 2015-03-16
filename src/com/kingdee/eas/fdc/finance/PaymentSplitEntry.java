package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCSplitBillEntry;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basedata.FDCSplitBillEntry;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class PaymentSplitEntry extends FDCSplitBillEntry implements IPaymentSplitEntry
{
    public PaymentSplitEntry()
    {
        super();
        registerInterface(IPaymentSplitEntry.class, this);
    }
    public PaymentSplitEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IPaymentSplitEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("27BEF6EF");
    }
    private PaymentSplitEntryController getController() throws BOSException
    {
        return (PaymentSplitEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public PaymentSplitEntryInfo getPaymentSplitEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPaymentSplitEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public PaymentSplitEntryInfo getPaymentSplitEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPaymentSplitEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public PaymentSplitEntryInfo getPaymentSplitEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPaymentSplitEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PaymentSplitEntryCollection getPaymentSplitEntryCollection() throws BOSException
    {
        try {
            return getController().getPaymentSplitEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public PaymentSplitEntryCollection getPaymentSplitEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPaymentSplitEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public PaymentSplitEntryCollection getPaymentSplitEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getPaymentSplitEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}