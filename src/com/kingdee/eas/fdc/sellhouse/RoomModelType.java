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

public class RoomModelType extends FDCDataBase implements IRoomModelType
{
    public RoomModelType()
    {
        super();
        registerInterface(IRoomModelType.class, this);
    }
    public RoomModelType(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomModelType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("608DFA90");
    }
    private RoomModelTypeController getController() throws BOSException
    {
        return (RoomModelTypeController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public RoomModelTypeInfo getRoomModelTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomModelTypeInfo(getContext(), pk);
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
    public RoomModelTypeInfo getRoomModelTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomModelTypeInfo(getContext(), pk, selector);
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
    public RoomModelTypeInfo getRoomModelTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomModelTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RoomModelTypeCollection getRoomModelTypeCollection() throws BOSException
    {
        try {
            return getController().getRoomModelTypeCollection(getContext());
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
    public RoomModelTypeCollection getRoomModelTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomModelTypeCollection(getContext(), view);
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
    public RoomModelTypeCollection getRoomModelTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomModelTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}