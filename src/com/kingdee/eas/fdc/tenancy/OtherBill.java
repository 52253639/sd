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

public class OtherBill extends TenBillBase implements IOtherBill
{
    public OtherBill()
    {
        super();
        registerInterface(IOtherBill.class, this);
    }
    public OtherBill(Context ctx)
    {
        super(ctx);
        registerInterface(IOtherBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("73402BAE");
    }
    private OtherBillController getController() throws BOSException
    {
        return (OtherBillController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public OtherBillInfo getOtherBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherBillInfo(getContext(), pk);
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
    public OtherBillInfo getOtherBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherBillInfo(getContext(), pk, selector);
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
    public OtherBillInfo getOtherBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public OtherBillCollection getOtherBillCollection() throws BOSException
    {
        try {
            return getController().getOtherBillCollection(getContext());
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
    public OtherBillCollection getOtherBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getOtherBillCollection(getContext(), view);
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
    public OtherBillCollection getOtherBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getOtherBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}