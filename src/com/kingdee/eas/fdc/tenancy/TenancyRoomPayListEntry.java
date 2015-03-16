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

public class TenancyRoomPayListEntry extends RevList implements ITenancyRoomPayListEntry
{
    public TenancyRoomPayListEntry()
    {
        super();
        registerInterface(ITenancyRoomPayListEntry.class, this);
    }
    public TenancyRoomPayListEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ITenancyRoomPayListEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("31D11A7E");
    }
    private TenancyRoomPayListEntryController getController() throws BOSException
    {
        return (TenancyRoomPayListEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public TenancyRoomPayListEntryInfo getTenancyRoomPayListEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyRoomPayListEntryInfo(getContext(), pk);
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
    public TenancyRoomPayListEntryInfo getTenancyRoomPayListEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyRoomPayListEntryInfo(getContext(), pk, selector);
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
    public TenancyRoomPayListEntryInfo getTenancyRoomPayListEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyRoomPayListEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TenancyRoomPayListEntryCollection getTenancyRoomPayListEntryCollection() throws BOSException
    {
        try {
            return getController().getTenancyRoomPayListEntryCollection(getContext());
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
    public TenancyRoomPayListEntryCollection getTenancyRoomPayListEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTenancyRoomPayListEntryCollection(getContext(), view);
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
    public TenancyRoomPayListEntryCollection getTenancyRoomPayListEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getTenancyRoomPayListEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}