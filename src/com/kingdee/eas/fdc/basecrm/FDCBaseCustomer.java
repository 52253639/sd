package com.kingdee.eas.fdc.basecrm;

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
import com.kingdee.eas.fdc.basecrm.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class FDCBaseCustomer extends DataBase implements IFDCBaseCustomer
{
    public FDCBaseCustomer()
    {
        super();
        registerInterface(IFDCBaseCustomer.class, this);
    }
    public FDCBaseCustomer(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCBaseCustomer.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BD3B36F8");
    }
    private FDCBaseCustomerController getController() throws BOSException
    {
        return (FDCBaseCustomerController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public FDCBaseCustomerInfo getFDCBaseCustomerInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCBaseCustomerInfo(getContext(), pk, selector);
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
    public FDCBaseCustomerInfo getFDCBaseCustomerInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCBaseCustomerInfo(getContext(), pk);
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
    public FDCBaseCustomerInfo getFDCBaseCustomerInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCBaseCustomerInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCBaseCustomerCollection getFDCBaseCustomerCollection() throws BOSException
    {
        try {
            return getController().getFDCBaseCustomerCollection(getContext());
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
    public FDCBaseCustomerCollection getFDCBaseCustomerCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCBaseCustomerCollection(getContext(), view);
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
    public FDCBaseCustomerCollection getFDCBaseCustomerCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCBaseCustomerCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}