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

public class RoomUsage extends FDCDataBase implements IRoomUsage
{
    public RoomUsage()
    {
        super();
        registerInterface(IRoomUsage.class, this);
    }
    public RoomUsage(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomUsage.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B13C7D0B");
    }
    private RoomUsageController getController() throws BOSException
    {
        return (RoomUsageController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public RoomUsageInfo getRoomUsageInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomUsageInfo(getContext(), pk);
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
    public RoomUsageInfo getRoomUsageInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomUsageInfo(getContext(), pk, selector);
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
    public RoomUsageInfo getRoomUsageInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomUsageInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public RoomUsageCollection getRoomUsageCollection() throws BOSException
    {
        try {
            return getController().getRoomUsageCollection(getContext());
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
    public RoomUsageCollection getRoomUsageCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomUsageCollection(getContext(), view);
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
    public RoomUsageCollection getRoomUsageCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomUsageCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}