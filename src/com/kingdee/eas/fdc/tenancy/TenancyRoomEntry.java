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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class TenancyRoomEntry extends CoreBase implements ITenancyRoomEntry
{
    public TenancyRoomEntry()
    {
        super();
        registerInterface(ITenancyRoomEntry.class, this);
    }
    public TenancyRoomEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ITenancyRoomEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B54A7CE0");
    }
    private TenancyRoomEntryController getController() throws BOSException
    {
        return (TenancyRoomEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public TenancyRoomEntryInfo getTenancyRoomEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyRoomEntryInfo(getContext(), pk);
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
    public TenancyRoomEntryInfo getTenancyRoomEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyRoomEntryInfo(getContext(), pk, selector);
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
    public TenancyRoomEntryInfo getTenancyRoomEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyRoomEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TenancyRoomEntryCollection getTenancyRoomEntryCollection() throws BOSException
    {
        try {
            return getController().getTenancyRoomEntryCollection(getContext());
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
    public TenancyRoomEntryCollection getTenancyRoomEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTenancyRoomEntryCollection(getContext(), view);
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
    public TenancyRoomEntryCollection getTenancyRoomEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getTenancyRoomEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}