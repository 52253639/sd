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

public class CommerceRoomEntry extends CoreBillEntryBase implements ICommerceRoomEntry
{
    public CommerceRoomEntry()
    {
        super();
        registerInterface(ICommerceRoomEntry.class, this);
    }
    public CommerceRoomEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ICommerceRoomEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E9CCEE41");
    }
    private CommerceRoomEntryController getController() throws BOSException
    {
        return (CommerceRoomEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public CommerceRoomEntryInfo getCommerceRoomEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCommerceRoomEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public CommerceRoomEntryInfo getCommerceRoomEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCommerceRoomEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql oql
     *@return
     */
    public CommerceRoomEntryInfo getCommerceRoomEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCommerceRoomEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public CommerceRoomEntryCollection getCommerceRoomEntryCollection() throws BOSException
    {
        try {
            return getController().getCommerceRoomEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view view
     *@return
     */
    public CommerceRoomEntryCollection getCommerceRoomEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCommerceRoomEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql oql
     *@return
     */
    public CommerceRoomEntryCollection getCommerceRoomEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getCommerceRoomEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}