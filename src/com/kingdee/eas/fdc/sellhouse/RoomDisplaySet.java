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

public class RoomDisplaySet extends DataBase implements IRoomDisplaySet
{
    public RoomDisplaySet()
    {
        super();
        registerInterface(IRoomDisplaySet.class, this);
    }
    public RoomDisplaySet(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomDisplaySet.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4CB06F16");
    }
    private RoomDisplaySetController getController() throws BOSException
    {
        return (RoomDisplaySetController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public RoomDisplaySetInfo getRoomDisplaySetInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomDisplaySetInfo(getContext(), pk, selector);
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
    public RoomDisplaySetInfo getRoomDisplaySetInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomDisplaySetInfo(getContext(), pk);
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
    public RoomDisplaySetInfo getRoomDisplaySetInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomDisplaySetInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RoomDisplaySetCollection getRoomDisplaySetCollection() throws BOSException
    {
        try {
            return getController().getRoomDisplaySetCollection(getContext());
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
    public RoomDisplaySetCollection getRoomDisplaySetCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomDisplaySetCollection(getContext(), view);
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
    public RoomDisplaySetCollection getRoomDisplaySetCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomDisplaySetCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}