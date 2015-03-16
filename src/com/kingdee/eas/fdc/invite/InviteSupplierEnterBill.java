package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.invite.app.*;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class InviteSupplierEnterBill extends FDCBill implements IInviteSupplierEnterBill
{
    public InviteSupplierEnterBill()
    {
        super();
        registerInterface(IInviteSupplierEnterBill.class, this);
    }
    public InviteSupplierEnterBill(Context ctx)
    {
        super(ctx);
        registerInterface(IInviteSupplierEnterBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4675F6E6");
    }
    private InviteSupplierEnterBillController getController() throws BOSException
    {
        return (InviteSupplierEnterBillController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public InviteSupplierEnterBillInfo getInviteSupplierEnterBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getInviteSupplierEnterBillInfo(getContext(), pk);
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
    public InviteSupplierEnterBillInfo getInviteSupplierEnterBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getInviteSupplierEnterBillInfo(getContext(), pk, selector);
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
    public InviteSupplierEnterBillInfo getInviteSupplierEnterBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getInviteSupplierEnterBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public InviteSupplierEnterBillCollection getInviteSupplierEnterBillCollection() throws BOSException
    {
        try {
            return getController().getInviteSupplierEnterBillCollection(getContext());
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
    public InviteSupplierEnterBillCollection getInviteSupplierEnterBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getInviteSupplierEnterBillCollection(getContext(), view);
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
    public InviteSupplierEnterBillCollection getInviteSupplierEnterBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getInviteSupplierEnterBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}