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

public class RoomEyeshot extends FDCDataBase implements IRoomEyeshot
{
    public RoomEyeshot()
    {
        super();
        registerInterface(IRoomEyeshot.class, this);
    }
    public RoomEyeshot(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomEyeshot.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("1028B195");
    }
    private RoomEyeshotController getController() throws BOSException
    {
        return (RoomEyeshotController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public RoomEyeshotInfo getRoomEyeshotInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomEyeshotInfo(getContext(), pk);
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
    public RoomEyeshotInfo getRoomEyeshotInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomEyeshotInfo(getContext(), pk, selector);
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
    public RoomEyeshotInfo getRoomEyeshotInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomEyeshotInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public RoomEyeshotCollection getRoomEyeshotCollection() throws BOSException
    {
        try {
            return getController().getRoomEyeshotCollection(getContext());
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
    public RoomEyeshotCollection getRoomEyeshotCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomEyeshotCollection(getContext(), view);
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
    public RoomEyeshotCollection getRoomEyeshotCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomEyeshotCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}