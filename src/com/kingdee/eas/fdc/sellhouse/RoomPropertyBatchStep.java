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

public class RoomPropertyBatchStep extends CoreBillEntryBase implements IRoomPropertyBatchStep
{
    public RoomPropertyBatchStep()
    {
        super();
        registerInterface(IRoomPropertyBatchStep.class, this);
    }
    public RoomPropertyBatchStep(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomPropertyBatchStep.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("EF80573B");
    }
    private RoomPropertyBatchStepController getController() throws BOSException
    {
        return (RoomPropertyBatchStepController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public RoomPropertyBatchStepInfo getRoomPropertyBatchStepInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPropertyBatchStepInfo(getContext(), pk);
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
    public RoomPropertyBatchStepInfo getRoomPropertyBatchStepInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPropertyBatchStepInfo(getContext(), pk, selector);
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
    public RoomPropertyBatchStepInfo getRoomPropertyBatchStepInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPropertyBatchStepInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RoomPropertyBatchStepCollection getRoomPropertyBatchStepCollection() throws BOSException
    {
        try {
            return getController().getRoomPropertyBatchStepCollection(getContext());
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
    public RoomPropertyBatchStepCollection getRoomPropertyBatchStepCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomPropertyBatchStepCollection(getContext(), view);
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
    public RoomPropertyBatchStepCollection getRoomPropertyBatchStepCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomPropertyBatchStepCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}