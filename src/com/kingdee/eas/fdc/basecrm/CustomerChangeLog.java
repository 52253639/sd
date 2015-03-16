package com.kingdee.eas.fdc.basecrm;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basecrm.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class CustomerChangeLog extends FDCDataBase implements ICustomerChangeLog
{
    public CustomerChangeLog()
    {
        super();
        registerInterface(ICustomerChangeLog.class, this);
    }
    public CustomerChangeLog(Context ctx)
    {
        super(ctx);
        registerInterface(ICustomerChangeLog.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E7FDDB5A");
    }
    private CustomerChangeLogController getController() throws BOSException
    {
        return (CustomerChangeLogController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public CustomerChangeLogInfo getCustomerChangeLogInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCustomerChangeLogInfo(getContext(), oql);
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
    public CustomerChangeLogInfo getCustomerChangeLogInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCustomerChangeLogInfo(getContext(), pk, selector);
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
    public CustomerChangeLogInfo getCustomerChangeLogInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCustomerChangeLogInfo(getContext(), pk);
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
    public CustomerChangeLogCollection getCustomerChangeLogCollection(String oql) throws BOSException
    {
        try {
            return getController().getCustomerChangeLogCollection(getContext(), oql);
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
    public CustomerChangeLogCollection getCustomerChangeLogCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCustomerChangeLogCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public CustomerChangeLogCollection getCustomerChangeLogCollection() throws BOSException
    {
        try {
            return getController().getCustomerChangeLogCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}