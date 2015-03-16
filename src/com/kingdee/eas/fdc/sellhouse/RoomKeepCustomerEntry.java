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
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.BillEntryBase;
import com.kingdee.eas.framework.IBillEntryBase;

public class RoomKeepCustomerEntry extends BillEntryBase implements IRoomKeepCustomerEntry
{
    public RoomKeepCustomerEntry()
    {
        super();
        registerInterface(IRoomKeepCustomerEntry.class, this);
    }
    public RoomKeepCustomerEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomKeepCustomerEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("615EA819");
    }
    private RoomKeepCustomerEntryController getController() throws BOSException
    {
        return (RoomKeepCustomerEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public RoomKeepCustomerEntryInfo getRoomKeepCustomerEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomKeepCustomerEntryInfo(getContext(), pk);
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
    public RoomKeepCustomerEntryInfo getRoomKeepCustomerEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomKeepCustomerEntryInfo(getContext(), pk, selector);
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
    public RoomKeepCustomerEntryInfo getRoomKeepCustomerEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomKeepCustomerEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RoomKeepCustomerEntryCollection getRoomKeepCustomerEntryCollection() throws BOSException
    {
        try {
            return getController().getRoomKeepCustomerEntryCollection(getContext());
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
    public RoomKeepCustomerEntryCollection getRoomKeepCustomerEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomKeepCustomerEntryCollection(getContext(), view);
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
    public RoomKeepCustomerEntryCollection getRoomKeepCustomerEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomKeepCustomerEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}