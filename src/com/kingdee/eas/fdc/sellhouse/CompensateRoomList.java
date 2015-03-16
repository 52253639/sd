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

public class CompensateRoomList extends CoreBase implements ICompensateRoomList
{
    public CompensateRoomList()
    {
        super();
        registerInterface(ICompensateRoomList.class, this);
    }
    public CompensateRoomList(Context ctx)
    {
        super(ctx);
        registerInterface(ICompensateRoomList.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5EC8BC8B");
    }
    private CompensateRoomListController getController() throws BOSException
    {
        return (CompensateRoomListController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public CompensateRoomListInfo getCompensateRoomListInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCompensateRoomListInfo(getContext(), pk);
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
    public CompensateRoomListInfo getCompensateRoomListInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCompensateRoomListInfo(getContext(), pk, selector);
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
    public CompensateRoomListInfo getCompensateRoomListInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCompensateRoomListInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public CompensateRoomListCollection getCompensateRoomListCollection() throws BOSException
    {
        try {
            return getController().getCompensateRoomListCollection(getContext());
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
    public CompensateRoomListCollection getCompensateRoomListCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCompensateRoomListCollection(getContext(), view);
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
    public CompensateRoomListCollection getCompensateRoomListCollection(String oql) throws BOSException
    {
        try {
            return getController().getCompensateRoomListCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}