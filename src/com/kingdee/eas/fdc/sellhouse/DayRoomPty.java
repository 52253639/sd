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

public class DayRoomPty extends CoreBase implements IDayRoomPty
{
    public DayRoomPty()
    {
        super();
        registerInterface(IDayRoomPty.class, this);
    }
    public DayRoomPty(Context ctx)
    {
        super(ctx);
        registerInterface(IDayRoomPty.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A53F25F9");
    }
    private DayRoomPtyController getController() throws BOSException
    {
        return (DayRoomPtyController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public DayRoomPtyInfo getDayRoomPtyInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDayRoomPtyInfo(getContext(), pk);
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
    public DayRoomPtyInfo getDayRoomPtyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDayRoomPtyInfo(getContext(), pk, selector);
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
    public DayRoomPtyInfo getDayRoomPtyInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDayRoomPtyInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public DayRoomPtyCollection getDayRoomPtyCollection() throws BOSException
    {
        try {
            return getController().getDayRoomPtyCollection(getContext());
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
    public DayRoomPtyCollection getDayRoomPtyCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDayRoomPtyCollection(getContext(), view);
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
    public DayRoomPtyCollection getDayRoomPtyCollection(String oql) throws BOSException
    {
        try {
            return getController().getDayRoomPtyCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}