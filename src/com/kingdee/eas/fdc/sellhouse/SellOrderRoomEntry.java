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

public class SellOrderRoomEntry extends DataBase implements ISellOrderRoomEntry
{
    public SellOrderRoomEntry()
    {
        super();
        registerInterface(ISellOrderRoomEntry.class, this);
    }
    public SellOrderRoomEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISellOrderRoomEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3EB8C2B6");
    }
    private SellOrderRoomEntryController getController() throws BOSException
    {
        return (SellOrderRoomEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SellOrderRoomEntryInfo getSellOrderRoomEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSellOrderRoomEntryInfo(getContext(), pk);
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
    public SellOrderRoomEntryInfo getSellOrderRoomEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSellOrderRoomEntryInfo(getContext(), pk, selector);
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
    public SellOrderRoomEntryInfo getSellOrderRoomEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSellOrderRoomEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SellOrderRoomEntryCollection getSellOrderRoomEntryCollection() throws BOSException
    {
        try {
            return getController().getSellOrderRoomEntryCollection(getContext());
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
    public SellOrderRoomEntryCollection getSellOrderRoomEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSellOrderRoomEntryCollection(getContext(), view);
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
    public SellOrderRoomEntryCollection getSellOrderRoomEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSellOrderRoomEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}