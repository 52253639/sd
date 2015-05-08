package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class ScheduleTaskBizType extends CoreBase implements IScheduleTaskBizType
{
    public ScheduleTaskBizType()
    {
        super();
        registerInterface(IScheduleTaskBizType.class, this);
    }
    public ScheduleTaskBizType(Context ctx)
    {
        super(ctx);
        registerInterface(IScheduleTaskBizType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0B475AFB");
    }
    private ScheduleTaskBizTypeController getController() throws BOSException
    {
        return (ScheduleTaskBizTypeController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ScheduleTaskBizTypeInfo getScheduleTaskBizTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleTaskBizTypeInfo(getContext(), pk);
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
    public ScheduleTaskBizTypeInfo getScheduleTaskBizTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleTaskBizTypeInfo(getContext(), pk, selector);
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
    public ScheduleTaskBizTypeInfo getScheduleTaskBizTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleTaskBizTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ScheduleTaskBizTypeCollection getScheduleTaskBizTypeCollection() throws BOSException
    {
        try {
            return getController().getScheduleTaskBizTypeCollection(getContext());
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
    public ScheduleTaskBizTypeCollection getScheduleTaskBizTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getScheduleTaskBizTypeCollection(getContext(), view);
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
    public ScheduleTaskBizTypeCollection getScheduleTaskBizTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getScheduleTaskBizTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}