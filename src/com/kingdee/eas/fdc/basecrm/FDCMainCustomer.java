package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basecrm.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class FDCMainCustomer extends FDCBaseCustomer implements IFDCMainCustomer
{
    public FDCMainCustomer()
    {
        super();
        registerInterface(IFDCMainCustomer.class, this);
    }
    public FDCMainCustomer(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCMainCustomer.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BD66CDE0");
    }
    private FDCMainCustomerController getController() throws BOSException
    {
        return (FDCMainCustomerController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCMainCustomerInfo getFDCMainCustomerInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCMainCustomerInfo(getContext(), pk);
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
    public FDCMainCustomerInfo getFDCMainCustomerInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCMainCustomerInfo(getContext(), pk, selector);
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
    public FDCMainCustomerInfo getFDCMainCustomerInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCMainCustomerInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCMainCustomerCollection getFDCMainCustomerCollection() throws BOSException
    {
        try {
            return getController().getFDCMainCustomerCollection(getContext());
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
    public FDCMainCustomerCollection getFDCMainCustomerCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCMainCustomerCollection(getContext(), view);
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
    public FDCMainCustomerCollection getFDCMainCustomerCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCMainCustomerCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *同步到系统用户-User defined method
     *@param mainCus 房地产主客户
     */
    public void addToSysCustomer(FDCMainCustomerInfo mainCus) throws BOSException, EASBizException
    {
        try {
            getController().addToSysCustomer(getContext(), mainCus);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}