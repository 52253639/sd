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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.TreeBase;

public class RoomAssistantType extends TreeBase implements IRoomAssistantType
{
    public RoomAssistantType()
    {
        super();
        registerInterface(IRoomAssistantType.class, this);
    }
    public RoomAssistantType(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomAssistantType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("380DCCA2");
    }
    private RoomAssistantTypeController getController() throws BOSException
    {
        return (RoomAssistantTypeController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public RoomAssistantTypeInfo getRoomAssistantTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomAssistantTypeInfo(getContext(), pk);
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
    public RoomAssistantTypeInfo getRoomAssistantTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomAssistantTypeInfo(getContext(), pk, selector);
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
    public RoomAssistantTypeInfo getRoomAssistantTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomAssistantTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RoomAssistantTypeCollection getRoomAssistantTypeCollection() throws BOSException
    {
        try {
            return getController().getRoomAssistantTypeCollection(getContext());
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
    public RoomAssistantTypeCollection getRoomAssistantTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomAssistantTypeCollection(getContext(), view);
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
    public RoomAssistantTypeCollection getRoomAssistantTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomAssistantTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}