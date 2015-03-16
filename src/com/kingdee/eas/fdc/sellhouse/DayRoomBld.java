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

public class DayRoomBld extends CoreBase implements IDayRoomBld
{
    public DayRoomBld()
    {
        super();
        registerInterface(IDayRoomBld.class, this);
    }
    public DayRoomBld(Context ctx)
    {
        super(ctx);
        registerInterface(IDayRoomBld.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A53EF05E");
    }
    private DayRoomBldController getController() throws BOSException
    {
        return (DayRoomBldController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public DayRoomBldInfo getDayRoomBldInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDayRoomBldInfo(getContext(), pk);
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
    public DayRoomBldInfo getDayRoomBldInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDayRoomBldInfo(getContext(), pk, selector);
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
    public DayRoomBldInfo getDayRoomBldInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDayRoomBldInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public DayRoomBldCollection getDayRoomBldCollection() throws BOSException
    {
        try {
            return getController().getDayRoomBldCollection(getContext());
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
    public DayRoomBldCollection getDayRoomBldCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDayRoomBldCollection(getContext(), view);
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
    public DayRoomBldCollection getDayRoomBldCollection(String oql) throws BOSException
    {
        try {
            return getController().getDayRoomBldCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}