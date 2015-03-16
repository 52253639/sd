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

public class RoomPriceBillEntry extends DataBase implements IRoomPriceBillEntry
{
    public RoomPriceBillEntry()
    {
        super();
        registerInterface(IRoomPriceBillEntry.class, this);
    }
    public RoomPriceBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomPriceBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C236E938");
    }
    private RoomPriceBillEntryController getController() throws BOSException
    {
        return (RoomPriceBillEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public RoomPriceBillEntryInfo getRoomPriceBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPriceBillEntryInfo(getContext(), pk);
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
    public RoomPriceBillEntryInfo getRoomPriceBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPriceBillEntryInfo(getContext(), pk, selector);
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
    public RoomPriceBillEntryInfo getRoomPriceBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPriceBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RoomPriceBillEntryCollection getRoomPriceBillEntryCollection() throws BOSException
    {
        try {
            return getController().getRoomPriceBillEntryCollection(getContext());
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
    public RoomPriceBillEntryCollection getRoomPriceBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomPriceBillEntryCollection(getContext(), view);
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
    public RoomPriceBillEntryCollection getRoomPriceBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomPriceBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}