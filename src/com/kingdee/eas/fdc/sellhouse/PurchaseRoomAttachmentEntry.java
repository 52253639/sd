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

public class PurchaseRoomAttachmentEntry extends DataBase implements IPurchaseRoomAttachmentEntry
{
    public PurchaseRoomAttachmentEntry()
    {
        super();
        registerInterface(IPurchaseRoomAttachmentEntry.class, this);
    }
    public PurchaseRoomAttachmentEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IPurchaseRoomAttachmentEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("6668A438");
    }
    private PurchaseRoomAttachmentEntryController getController() throws BOSException
    {
        return (PurchaseRoomAttachmentEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public PurchaseRoomAttachmentEntryInfo getPurchaseRoomAttachmentEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseRoomAttachmentEntryInfo(getContext(), pk);
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
    public PurchaseRoomAttachmentEntryInfo getPurchaseRoomAttachmentEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseRoomAttachmentEntryInfo(getContext(), pk, selector);
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
    public PurchaseRoomAttachmentEntryInfo getPurchaseRoomAttachmentEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPurchaseRoomAttachmentEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public PurchaseRoomAttachmentEntryCollection getPurchaseRoomAttachmentEntryCollection() throws BOSException
    {
        try {
            return getController().getPurchaseRoomAttachmentEntryCollection(getContext());
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
    public PurchaseRoomAttachmentEntryCollection getPurchaseRoomAttachmentEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPurchaseRoomAttachmentEntryCollection(getContext(), view);
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
    public PurchaseRoomAttachmentEntryCollection getPurchaseRoomAttachmentEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getPurchaseRoomAttachmentEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}