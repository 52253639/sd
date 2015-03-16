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
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class CommerceChangeRoom extends CoreBase implements ICommerceChangeRoom
{
    public CommerceChangeRoom()
    {
        super();
        registerInterface(ICommerceChangeRoom.class, this);
    }
    public CommerceChangeRoom(Context ctx)
    {
        super(ctx);
        registerInterface(ICommerceChangeRoom.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B1B06801");
    }
    private CommerceChangeRoomController getController() throws BOSException
    {
        return (CommerceChangeRoomController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public CommerceChangeRoomInfo getCommerceChangeRoomInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCommerceChangeRoomInfo(getContext(), pk);
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
    public CommerceChangeRoomInfo getCommerceChangeRoomInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCommerceChangeRoomInfo(getContext(), pk, selector);
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
    public CommerceChangeRoomInfo getCommerceChangeRoomInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCommerceChangeRoomInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public CommerceChangeRoomCollection getCommerceChangeRoomCollection() throws BOSException
    {
        try {
            return getController().getCommerceChangeRoomCollection(getContext());
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
    public CommerceChangeRoomCollection getCommerceChangeRoomCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCommerceChangeRoomCollection(getContext(), view);
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
    public CommerceChangeRoomCollection getCommerceChangeRoomCollection(String oql) throws BOSException
    {
        try {
            return getController().getCommerceChangeRoomCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}