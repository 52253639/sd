package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
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
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class RoomAssistant extends FDCDataBase implements IRoomAssistant
{
    public RoomAssistant()
    {
        super();
        registerInterface(IRoomAssistant.class, this);
    }
    public RoomAssistant(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomAssistant.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BC8DAD48");
    }
    private RoomAssistantController getController() throws BOSException
    {
        return (RoomAssistantController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public RoomAssistantInfo getRoomAssistantInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomAssistantInfo(getContext(), pk);
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
    public RoomAssistantInfo getRoomAssistantInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomAssistantInfo(getContext(), pk, selector);
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
    public RoomAssistantInfo getRoomAssistantInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomAssistantInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RoomAssistantCollection getRoomAssistantCollection() throws BOSException
    {
        try {
            return getController().getRoomAssistantCollection(getContext());
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
    public RoomAssistantCollection getRoomAssistantCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomAssistantCollection(getContext(), view);
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
    public RoomAssistantCollection getRoomAssistantCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomAssistantCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}