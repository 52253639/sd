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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class HandleTenancy extends TenBillBase implements IHandleTenancy
{
    public HandleTenancy()
    {
        super();
        registerInterface(IHandleTenancy.class, this);
    }
    public HandleTenancy(Context ctx)
    {
        super(ctx);
        registerInterface(IHandleTenancy.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("03B8A0AF");
    }
    private HandleTenancyController getController() throws BOSException
    {
        return (HandleTenancyController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public HandleTenancyInfo getHandleTenancyInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getHandleTenancyInfo(getContext(), pk);
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
    public HandleTenancyInfo getHandleTenancyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getHandleTenancyInfo(getContext(), pk, selector);
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
    public HandleTenancyInfo getHandleTenancyInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getHandleTenancyInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public HandleTenancyCollection getHandleTenancyCollection() throws BOSException
    {
        try {
            return getController().getHandleTenancyCollection(getContext());
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
    public HandleTenancyCollection getHandleTenancyCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getHandleTenancyCollection(getContext(), view);
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
    public HandleTenancyCollection getHandleTenancyCollection(String oql) throws BOSException
    {
        try {
            return getController().getHandleTenancyCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *租赁交接-User defined method
     *@param handleRoomEntryColl handleRoomEntryColl
     *@param tenancyBillInfo tenancyBillInfo
     */
    public void handleTenancyRoom(IObjectCollection handleRoomEntryColl, IObjectValue tenancyBillInfo) throws BOSException
    {
        try {
            getController().handleTenancyRoom(getContext(), handleRoomEntryColl, tenancyBillInfo);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}