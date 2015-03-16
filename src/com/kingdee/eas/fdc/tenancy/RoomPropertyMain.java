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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class RoomPropertyMain extends DataBase implements IRoomPropertyMain
{
    public RoomPropertyMain()
    {
        super();
        registerInterface(IRoomPropertyMain.class, this);
    }
    public RoomPropertyMain(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomPropertyMain.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("CB1D0952");
    }
    private RoomPropertyMainController getController() throws BOSException
    {
        return (RoomPropertyMainController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public RoomPropertyMainInfo getRoomPropertyMainInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPropertyMainInfo(getContext(), pk);
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
    public RoomPropertyMainInfo getRoomPropertyMainInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPropertyMainInfo(getContext(), pk, selector);
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
    public RoomPropertyMainInfo getRoomPropertyMainInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPropertyMainInfo(getContext(), oql);
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
    public RoomPropertyMainCollection getRoomPropertyMainCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomPropertyMainCollection(getContext(), view);
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
    public RoomPropertyMainCollection getRoomPropertyMainCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomPropertyMainCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RoomPropertyMainCollection getRoomPropertyMainCollection() throws BOSException
    {
        try {
            return getController().getRoomPropertyMainCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}