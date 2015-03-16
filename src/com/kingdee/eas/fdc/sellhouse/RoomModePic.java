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

public class RoomModePic extends CoreBase implements IRoomModePic
{
    public RoomModePic()
    {
        super();
        registerInterface(IRoomModePic.class, this);
    }
    public RoomModePic(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomModePic.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A63545D1");
    }
    private RoomModePicController getController() throws BOSException
    {
        return (RoomModePicController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public RoomModePicInfo getRoomModePicInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomModePicInfo(getContext(), pk);
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
    public RoomModePicInfo getRoomModePicInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomModePicInfo(getContext(), pk, selector);
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
    public RoomModePicInfo getRoomModePicInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomModePicInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RoomModePicCollection getRoomModePicCollection() throws BOSException
    {
        try {
            return getController().getRoomModePicCollection(getContext());
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
    public RoomModePicCollection getRoomModePicCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomModePicCollection(getContext(), view);
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
    public RoomModePicCollection getRoomModePicCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomModePicCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}