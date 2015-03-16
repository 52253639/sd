package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class RoomPayEntry extends DataBase implements IRoomPayEntry
{
    public RoomPayEntry()
    {
        super();
        registerInterface(IRoomPayEntry.class, this);
    }
    public RoomPayEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomPayEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("95A06100");
    }
    private RoomPayEntryController getController() throws BOSException
    {
        return (RoomPayEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public RoomPayEntryInfo getRoomPayEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPayEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public RoomPayEntryInfo getRoomPayEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPayEntryInfo(getContext(), pk);
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
    public RoomPayEntryInfo getRoomPayEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPayEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RoomPayEntryCollection getRoomPayEntryCollection() throws BOSException
    {
        try {
            return getController().getRoomPayEntryCollection(getContext());
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
    public RoomPayEntryCollection getRoomPayEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomPayEntryCollection(getContext(), view);
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
    public RoomPayEntryCollection getRoomPayEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomPayEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}