package com.kingdee.eas.fdc.tenancy;

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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class HandleRoomEntrys extends DataBase implements IHandleRoomEntrys
{
    public HandleRoomEntrys()
    {
        super();
        registerInterface(IHandleRoomEntrys.class, this);
    }
    public HandleRoomEntrys(Context ctx)
    {
        super(ctx);
        registerInterface(IHandleRoomEntrys.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("89BC1AED");
    }
    private HandleRoomEntrysController getController() throws BOSException
    {
        return (HandleRoomEntrysController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public HandleRoomEntrysInfo getHandleRoomEntrysInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getHandleRoomEntrysInfo(getContext(), pk);
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
    public HandleRoomEntrysInfo getHandleRoomEntrysInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getHandleRoomEntrysInfo(getContext(), pk, selector);
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
    public HandleRoomEntrysInfo getHandleRoomEntrysInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getHandleRoomEntrysInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public HandleRoomEntrysCollection getHandleRoomEntrysCollection() throws BOSException
    {
        try {
            return getController().getHandleRoomEntrysCollection(getContext());
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
    public HandleRoomEntrysCollection getHandleRoomEntrysCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getHandleRoomEntrysCollection(getContext(), view);
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
    public HandleRoomEntrysCollection getHandleRoomEntrysCollection(String oql) throws BOSException
    {
        try {
            return getController().getHandleRoomEntrysCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}