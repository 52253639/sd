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

public class RoomRecoverHistory extends DataBase implements IRoomRecoverHistory
{
    public RoomRecoverHistory()
    {
        super();
        registerInterface(IRoomRecoverHistory.class, this);
    }
    public RoomRecoverHistory(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomRecoverHistory.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E80BEE66");
    }
    private RoomRecoverHistoryController getController() throws BOSException
    {
        return (RoomRecoverHistoryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public RoomRecoverHistoryInfo getRoomRecoverHistoryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomRecoverHistoryInfo(getContext(), pk);
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
    public RoomRecoverHistoryInfo getRoomRecoverHistoryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomRecoverHistoryInfo(getContext(), pk, selector);
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
    public RoomRecoverHistoryInfo getRoomRecoverHistoryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomRecoverHistoryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *新增-System defined method
     *@param pk pk
     *@param model model
     */
    public void addnew(IObjectPK pk, RoomRecoverHistoryInfo model) throws BOSException, EASBizException
    {
        try {
            getController().addnew(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *新增-System defined method
     *@param model model
     *@return
     */
    public IObjectPK addnew(RoomRecoverHistoryInfo model) throws BOSException, EASBizException
    {
        try {
            return getController().addnew(getContext(), model);
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
    public RoomRecoverHistoryCollection getRoomRecoverHistoryCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomRecoverHistoryCollection(getContext(), oql);
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
    public RoomRecoverHistoryCollection getRoomRecoverHistoryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomRecoverHistoryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public RoomRecoverHistoryCollection getRoomRecoverHistoryCollection() throws BOSException
    {
        try {
            return getController().getRoomRecoverHistoryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}