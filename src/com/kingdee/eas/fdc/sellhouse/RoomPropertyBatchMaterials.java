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

public class RoomPropertyBatchMaterials extends CoreBillEntryBase implements IRoomPropertyBatchMaterials
{
    public RoomPropertyBatchMaterials()
    {
        super();
        registerInterface(IRoomPropertyBatchMaterials.class, this);
    }
    public RoomPropertyBatchMaterials(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomPropertyBatchMaterials.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("78AADD1D");
    }
    private RoomPropertyBatchMaterialsController getController() throws BOSException
    {
        return (RoomPropertyBatchMaterialsController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public RoomPropertyBatchMaterialsInfo getRoomPropertyBatchMaterialsInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPropertyBatchMaterialsInfo(getContext(), pk);
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
    public RoomPropertyBatchMaterialsInfo getRoomPropertyBatchMaterialsInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPropertyBatchMaterialsInfo(getContext(), pk, selector);
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
    public RoomPropertyBatchMaterialsInfo getRoomPropertyBatchMaterialsInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPropertyBatchMaterialsInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RoomPropertyBatchMaterialsCollection getRoomPropertyBatchMaterialsCollection() throws BOSException
    {
        try {
            return getController().getRoomPropertyBatchMaterialsCollection(getContext());
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
    public RoomPropertyBatchMaterialsCollection getRoomPropertyBatchMaterialsCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomPropertyBatchMaterialsCollection(getContext(), view);
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
    public RoomPropertyBatchMaterialsCollection getRoomPropertyBatchMaterialsCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomPropertyBatchMaterialsCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}