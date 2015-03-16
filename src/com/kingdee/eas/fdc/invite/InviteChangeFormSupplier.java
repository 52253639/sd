package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.invite.app.*;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;

public class InviteChangeFormSupplier extends CoreBillEntryBase implements IInviteChangeFormSupplier
{
    public InviteChangeFormSupplier()
    {
        super();
        registerInterface(IInviteChangeFormSupplier.class, this);
    }
    public InviteChangeFormSupplier(Context ctx)
    {
        super(ctx);
        registerInterface(IInviteChangeFormSupplier.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B0D6B88D");
    }
    private InviteChangeFormSupplierController getController() throws BOSException
    {
        return (InviteChangeFormSupplierController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public InviteChangeFormSupplierInfo getInviteChangeFormSupplierInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getInviteChangeFormSupplierInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public InviteChangeFormSupplierCollection getInviteChangeFormSupplierCollection() throws BOSException
    {
        try {
            return getController().getInviteChangeFormSupplierCollection(getContext());
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
    public InviteChangeFormSupplierCollection getInviteChangeFormSupplierCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getInviteChangeFormSupplierCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}