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

public class RoomMortagage extends DataBase implements IRoomMortagage
{
    public RoomMortagage()
    {
        super();
        registerInterface(IRoomMortagage.class, this);
    }
    public RoomMortagage(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomMortagage.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("ED19B09F");
    }
    private RoomMortagageController getController() throws BOSException
    {
        return (RoomMortagageController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public RoomMortagageInfo getRoomMortagageInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomMortagageInfo(getContext(), pk);
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
    public RoomMortagageInfo getRoomMortagageInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomMortagageInfo(getContext(), pk, selector);
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
    public RoomMortagageInfo getRoomMortagageInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomMortagageInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RoomMortagageCollection getRoomMortagageCollection() throws BOSException
    {
        try {
            return getController().getRoomMortagageCollection(getContext());
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
    public RoomMortagageCollection getRoomMortagageCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomMortagageCollection(getContext(), view);
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
    public RoomMortagageCollection getRoomMortagageCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomMortagageCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}