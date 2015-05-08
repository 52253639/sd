package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import java.util.Set;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class TaskLoadEntry extends CoreBillEntryBase implements ITaskLoadEntry
{
    public TaskLoadEntry()
    {
        super();
        registerInterface(ITaskLoadEntry.class, this);
    }
    public TaskLoadEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ITaskLoadEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E71E0311");
    }
    private TaskLoadEntryController getController() throws BOSException
    {
        return (TaskLoadEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public TaskLoadEntryInfo getTaskLoadEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTaskLoadEntryInfo(getContext(), pk);
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
    public TaskLoadEntryInfo getTaskLoadEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTaskLoadEntryInfo(getContext(), pk, selector);
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
    public TaskLoadEntryInfo getTaskLoadEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTaskLoadEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public TaskLoadEntryCollection getTaskLoadEntryCollection() throws BOSException
    {
        try {
            return getController().getTaskLoadEntryCollection(getContext());
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
    public TaskLoadEntryCollection getTaskLoadEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTaskLoadEntryCollection(getContext(), view);
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
    public TaskLoadEntryCollection getTaskLoadEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getTaskLoadEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *更新确认状态-User defined method
     *@param idSet idSet
     *@param isConfirm 状态
     */
    public void updateConfirmStatus(Set idSet, boolean isConfirm) throws BOSException, EASBizException
    {
        try {
            getController().updateConfirmStatus(getContext(), idSet, isConfirm);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}