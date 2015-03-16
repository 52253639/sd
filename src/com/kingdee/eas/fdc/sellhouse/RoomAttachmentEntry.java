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

public class RoomAttachmentEntry extends DataBase implements IRoomAttachmentEntry
{
    public RoomAttachmentEntry()
    {
        super();
        registerInterface(IRoomAttachmentEntry.class, this);
    }
    public RoomAttachmentEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomAttachmentEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("05680093");
    }
    private RoomAttachmentEntryController getController() throws BOSException
    {
        return (RoomAttachmentEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public RoomAttachmentEntryInfo getRoomAttachmentEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomAttachmentEntryInfo(getContext(), pk);
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
    public RoomAttachmentEntryInfo getRoomAttachmentEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomAttachmentEntryInfo(getContext(), oql);
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
    public RoomAttachmentEntryInfo getRoomAttachmentEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomAttachmentEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RoomAttachmentEntryCollection getRoomAttachmentEntryCollection() throws BOSException
    {
        try {
            return getController().getRoomAttachmentEntryCollection(getContext());
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
    public RoomAttachmentEntryCollection getRoomAttachmentEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomAttachmentEntryCollection(getContext(), view);
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
    public RoomAttachmentEntryCollection getRoomAttachmentEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomAttachmentEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}