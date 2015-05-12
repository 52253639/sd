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

public class DepositDealBill extends TenBillBase implements IDepositDealBill
{
    public DepositDealBill()
    {
        super();
        registerInterface(IDepositDealBill.class, this);
    }
    public DepositDealBill(Context ctx)
    {
        super(ctx);
        registerInterface(IDepositDealBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("756F2288");
    }
    private DepositDealBillController getController() throws BOSException
    {
        return (DepositDealBillController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public DepositDealBillInfo getDepositDealBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDepositDealBillInfo(getContext(), pk);
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
    public DepositDealBillInfo getDepositDealBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDepositDealBillInfo(getContext(), pk, selector);
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
    public DepositDealBillInfo getDepositDealBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDepositDealBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public DepositDealBillCollection getDepositDealBillCollection() throws BOSException
    {
        try {
            return getController().getDepositDealBillCollection(getContext());
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
    public DepositDealBillCollection getDepositDealBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDepositDealBillCollection(getContext(), view);
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
    public DepositDealBillCollection getDepositDealBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getDepositDealBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}