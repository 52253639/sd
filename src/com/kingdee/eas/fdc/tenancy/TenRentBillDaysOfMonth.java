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

public class TenRentBillDaysOfMonth extends DataBase implements ITenRentBillDaysOfMonth
{
    public TenRentBillDaysOfMonth()
    {
        super();
        registerInterface(ITenRentBillDaysOfMonth.class, this);
    }
    public TenRentBillDaysOfMonth(Context ctx)
    {
        super(ctx);
        registerInterface(ITenRentBillDaysOfMonth.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9675751E");
    }
    private TenRentBillDaysOfMonthController getController() throws BOSException
    {
        return (TenRentBillDaysOfMonthController)getBizController();
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public TenRentBillDaysOfMonthCollection getTenRentBillDaysOfMonthCollection() throws BOSException
    {
        try {
            return getController().getTenRentBillDaysOfMonthCollection(getContext());
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
    public TenRentBillDaysOfMonthCollection getTenRentBillDaysOfMonthCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTenRentBillDaysOfMonthCollection(getContext(), view);
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
    public TenRentBillDaysOfMonthCollection getTenRentBillDaysOfMonthCollection(String oql) throws BOSException
    {
        try {
            return getController().getTenRentBillDaysOfMonthCollection(getContext(), oql);
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
    public TenRentBillDaysOfMonthInfo getTenRentBillDaysOfMonthInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTenRentBillDaysOfMonthInfo(getContext(), pk);
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
    public TenRentBillDaysOfMonthInfo getTenRentBillDaysOfMonthInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTenRentBillDaysOfMonthInfo(getContext(), pk, selector);
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
    public TenRentBillDaysOfMonthInfo getTenRentBillDaysOfMonthInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTenRentBillDaysOfMonthInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}