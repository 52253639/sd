package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.invite.app.*;
import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class InviteTypeEntry extends CoreBillEntryBase implements IInviteTypeEntry
{
    public InviteTypeEntry()
    {
        super();
        registerInterface(IInviteTypeEntry.class, this);
    }
    public InviteTypeEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IInviteTypeEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5BBD18AB");
    }
    private InviteTypeEntryController getController() throws BOSException
    {
        return (InviteTypeEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public InviteTypeEntryInfo getInviteTypeEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getInviteTypeEntryInfo(getContext(), pk);
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
    public InviteTypeEntryInfo getInviteTypeEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getInviteTypeEntryInfo(getContext(), pk, selector);
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
    public InviteTypeEntryInfo getInviteTypeEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getInviteTypeEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public InviteTypeEntryCollection getInviteTypeEntryCollection() throws BOSException
    {
        try {
            return getController().getInviteTypeEntryCollection(getContext());
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
    public InviteTypeEntryCollection getInviteTypeEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getInviteTypeEntryCollection(getContext(), view);
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
    public InviteTypeEntryCollection getInviteTypeEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getInviteTypeEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}