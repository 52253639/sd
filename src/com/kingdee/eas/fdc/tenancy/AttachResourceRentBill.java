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

public class AttachResourceRentBill extends TenBillBase implements IAttachResourceRentBill
{
    public AttachResourceRentBill()
    {
        super();
        registerInterface(IAttachResourceRentBill.class, this);
    }
    public AttachResourceRentBill(Context ctx)
    {
        super(ctx);
        registerInterface(IAttachResourceRentBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E8050E5C");
    }
    private AttachResourceRentBillController getController() throws BOSException
    {
        return (AttachResourceRentBillController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public AttachResourceRentBillInfo getAttachResourceRentBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAttachResourceRentBillInfo(getContext(), pk);
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
    public AttachResourceRentBillInfo getAttachResourceRentBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAttachResourceRentBillInfo(getContext(), pk, selector);
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
    public AttachResourceRentBillInfo getAttachResourceRentBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getAttachResourceRentBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public AttachResourceRentBillCollection getAttachResourceRentBillCollection() throws BOSException
    {
        try {
            return getController().getAttachResourceRentBillCollection(getContext());
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
    public AttachResourceRentBillCollection getAttachResourceRentBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAttachResourceRentBillCollection(getContext(), view);
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
    public AttachResourceRentBillCollection getAttachResourceRentBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getAttachResourceRentBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *执行-User defined method
     *@param id ID
     *@return
     */
    public boolean execute(String id) throws BOSException, EASBizException
    {
        try {
            return getController().execute(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}