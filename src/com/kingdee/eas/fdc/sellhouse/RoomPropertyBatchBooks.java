package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class RoomPropertyBatchBooks extends CoreBillEntryBase implements IRoomPropertyBatchBooks
{
    public RoomPropertyBatchBooks()
    {
        super();
        registerInterface(IRoomPropertyBatchBooks.class, this);
    }
    public RoomPropertyBatchBooks(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomPropertyBatchBooks.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FF98E01B");
    }
    private RoomPropertyBatchBooksController getController() throws BOSException
    {
        return (RoomPropertyBatchBooksController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public RoomPropertyBatchBooksInfo getRoomPropertyBatchBooksInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPropertyBatchBooksInfo(getContext(), pk);
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
    public RoomPropertyBatchBooksInfo getRoomPropertyBatchBooksInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPropertyBatchBooksInfo(getContext(), pk, selector);
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
    public RoomPropertyBatchBooksInfo getRoomPropertyBatchBooksInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPropertyBatchBooksInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RoomPropertyBatchBooksCollection getRoomPropertyBatchBooksCollection() throws BOSException
    {
        try {
            return getController().getRoomPropertyBatchBooksCollection(getContext());
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
    public RoomPropertyBatchBooksCollection getRoomPropertyBatchBooksCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomPropertyBatchBooksCollection(getContext(), view);
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
    public RoomPropertyBatchBooksCollection getRoomPropertyBatchBooksCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomPropertyBatchBooksCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}