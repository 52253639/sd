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
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class RoomPropertyBatch extends FDCBill implements IRoomPropertyBatch
{
    public RoomPropertyBatch()
    {
        super();
        registerInterface(IRoomPropertyBatch.class, this);
    }
    public RoomPropertyBatch(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomPropertyBatch.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("96195BCF");
    }
    private RoomPropertyBatchController getController() throws BOSException
    {
        return (RoomPropertyBatchController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public RoomPropertyBatchInfo getRoomPropertyBatchInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPropertyBatchInfo(getContext(), pk);
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
    public RoomPropertyBatchInfo getRoomPropertyBatchInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPropertyBatchInfo(getContext(), pk, selector);
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
    public RoomPropertyBatchInfo getRoomPropertyBatchInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPropertyBatchInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RoomPropertyBatchCollection getRoomPropertyBatchCollection() throws BOSException
    {
        try {
            return getController().getRoomPropertyBatchCollection(getContext());
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
    public RoomPropertyBatchCollection getRoomPropertyBatchCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomPropertyBatchCollection(getContext(), view);
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
    public RoomPropertyBatchCollection getRoomPropertyBatchCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomPropertyBatchCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}