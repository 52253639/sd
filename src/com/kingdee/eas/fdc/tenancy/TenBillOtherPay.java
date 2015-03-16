package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basecrm.IRevList;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basecrm.RevList;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class TenBillOtherPay extends RevList implements ITenBillOtherPay
{
    public TenBillOtherPay()
    {
        super();
        registerInterface(ITenBillOtherPay.class, this);
    }
    public TenBillOtherPay(Context ctx)
    {
        super(ctx);
        registerInterface(ITenBillOtherPay.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E17EA893");
    }
    private TenBillOtherPayController getController() throws BOSException
    {
        return (TenBillOtherPayController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public TenBillOtherPayInfo getTenBillOtherPayInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTenBillOtherPayInfo(getContext(), pk);
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
    public TenBillOtherPayInfo getTenBillOtherPayInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTenBillOtherPayInfo(getContext(), pk, selector);
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
    public TenBillOtherPayInfo getTenBillOtherPayInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTenBillOtherPayInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TenBillOtherPayCollection getTenBillOtherPayCollection() throws BOSException
    {
        try {
            return getController().getTenBillOtherPayCollection(getContext());
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
    public TenBillOtherPayCollection getTenBillOtherPayCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTenBillOtherPayCollection(getContext(), view);
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
    public TenBillOtherPayCollection getTenBillOtherPayCollection(String oql) throws BOSException
    {
        try {
            return getController().getTenBillOtherPayCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}