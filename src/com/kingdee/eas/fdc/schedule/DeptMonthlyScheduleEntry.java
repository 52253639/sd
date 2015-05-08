package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class DeptMonthlyScheduleEntry extends CoreBillEntryBase implements IDeptMonthlyScheduleEntry
{
    public DeptMonthlyScheduleEntry()
    {
        super();
        registerInterface(IDeptMonthlyScheduleEntry.class, this);
    }
    public DeptMonthlyScheduleEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IDeptMonthlyScheduleEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2B7250C9");
    }
    private DeptMonthlyScheduleEntryController getController() throws BOSException
    {
        return (DeptMonthlyScheduleEntryController)getBizController();
    }
    /**
     *exists-System defined method
     *@param pk pk
     *@return
     */
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().exists(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *exists-System defined method
     *@param filter filter
     *@return
     */
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException
    {
        try {
            return getController().exists(getContext(), filter);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *exists-System defined method
     *@param oql oql
     *@return
     */
    public boolean exists(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().exists(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public DeptMonthlyScheduleEntryInfo getDeptMonthlyScheduleEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDeptMonthlyScheduleEntryInfo(getContext(), pk);
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
    public DeptMonthlyScheduleEntryInfo getDeptMonthlyScheduleEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDeptMonthlyScheduleEntryInfo(getContext(), pk, selector);
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
    public DeptMonthlyScheduleEntryInfo getDeptMonthlyScheduleEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDeptMonthlyScheduleEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public DeptMonthlyScheduleEntryCollection getDeptMonthlyScheduleEntryCollection() throws BOSException
    {
        try {
            return getController().getDeptMonthlyScheduleEntryCollection(getContext());
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
    public DeptMonthlyScheduleEntryCollection getDeptMonthlyScheduleEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDeptMonthlyScheduleEntryCollection(getContext(), view);
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
    public DeptMonthlyScheduleEntryCollection getDeptMonthlyScheduleEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getDeptMonthlyScheduleEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}