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

public class RoomPriceBuildingEntry extends DataBase implements IRoomPriceBuildingEntry
{
    public RoomPriceBuildingEntry()
    {
        super();
        registerInterface(IRoomPriceBuildingEntry.class, this);
    }
    public RoomPriceBuildingEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomPriceBuildingEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C565102B");
    }
    private RoomPriceBuildingEntryController getController() throws BOSException
    {
        return (RoomPriceBuildingEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public RoomPriceBuildingEntryInfo getRoomPriceBuildingEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPriceBuildingEntryInfo(getContext(), pk);
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
    public RoomPriceBuildingEntryInfo getRoomPriceBuildingEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPriceBuildingEntryInfo(getContext(), pk, selector);
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
    public RoomPriceBuildingEntryInfo getRoomPriceBuildingEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPriceBuildingEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *addnew-System defined method
     *@param model model
     *@return
     */
    public IObjectPK addnew(RoomPriceBuildingEntryInfo model) throws BOSException, EASBizException
    {
        try {
            return getController().addnew(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *addnew-System defined method
     *@param pk pk
     *@param model model
     */
    public void addnew(IObjectPK pk, RoomPriceBuildingEntryInfo model) throws BOSException, EASBizException
    {
        try {
            getController().addnew(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *update-System defined method
     *@param pk pk
     *@param model model
     */
    public void update(IObjectPK pk, RoomPriceBuildingEntryInfo model) throws BOSException, EASBizException
    {
        try {
            getController().update(getContext(), pk, model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *updatePartial-System defined method
     *@param model model
     *@param selector selector
     */
    public void updatePartial(RoomPriceBuildingEntryInfo model, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            getController().updatePartial(getContext(), model, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RoomPriceBuildingEntryCollection getRoomPriceBuildingEntryCollection() throws BOSException
    {
        try {
            return getController().getRoomPriceBuildingEntryCollection(getContext());
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
    public RoomPriceBuildingEntryCollection getRoomPriceBuildingEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomPriceBuildingEntryCollection(getContext(), view);
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
    public RoomPriceBuildingEntryCollection getRoomPriceBuildingEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomPriceBuildingEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}