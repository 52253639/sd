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

public class InviteEntSuppChkBill extends FDCBill implements IInviteEntSuppChkBill
{
    public InviteEntSuppChkBill()
    {
        super();
        registerInterface(IInviteEntSuppChkBill.class, this);
    }
    public InviteEntSuppChkBill(Context ctx)
    {
        super(ctx);
        registerInterface(IInviteEntSuppChkBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3F160E2D");
    }
    private InviteEntSuppChkBillController getController() throws BOSException
    {
        return (InviteEntSuppChkBillController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public InviteEntSuppChkBillInfo getInviteEntSuppChkBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getInviteEntSuppChkBillInfo(getContext(), pk);
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
    public InviteEntSuppChkBillInfo getInviteEntSuppChkBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getInviteEntSuppChkBillInfo(getContext(), pk, selector);
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
    public InviteEntSuppChkBillInfo getInviteEntSuppChkBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getInviteEntSuppChkBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public InviteEntSuppChkBillCollection getInviteEntSuppChkBillCollection() throws BOSException
    {
        try {
            return getController().getInviteEntSuppChkBillCollection(getContext());
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
    public InviteEntSuppChkBillCollection getInviteEntSuppChkBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getInviteEntSuppChkBillCollection(getContext(), view);
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
    public InviteEntSuppChkBillCollection getInviteEntSuppChkBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getInviteEntSuppChkBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}