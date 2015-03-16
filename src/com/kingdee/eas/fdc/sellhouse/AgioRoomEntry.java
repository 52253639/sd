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

public class AgioRoomEntry extends DataBase implements IAgioRoomEntry
{
    public AgioRoomEntry()
    {
        super();
        registerInterface(IAgioRoomEntry.class, this);
    }
    public AgioRoomEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IAgioRoomEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7D3A3AF0");
    }
    private AgioRoomEntryController getController() throws BOSException
    {
        return (AgioRoomEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public AgioRoomEntryInfo getAgioRoomEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAgioRoomEntryInfo(getContext(), pk);
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
    public AgioRoomEntryInfo getAgioRoomEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAgioRoomEntryInfo(getContext(), pk, selector);
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
    public AgioRoomEntryInfo getAgioRoomEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getAgioRoomEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public AgioRoomEntryCollection getAgioRoomEntryCollection() throws BOSException
    {
        try {
            return getController().getAgioRoomEntryCollection(getContext());
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
    public AgioRoomEntryCollection getAgioRoomEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAgioRoomEntryCollection(getContext(), view);
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
    public AgioRoomEntryCollection getAgioRoomEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getAgioRoomEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}