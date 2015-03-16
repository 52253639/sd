package com.kingdee.eas.fdc.sellhouse;

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
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class CustomerOrigin extends FDCDataBase implements ICustomerOrigin
{
    public CustomerOrigin()
    {
        super();
        registerInterface(ICustomerOrigin.class, this);
    }
    public CustomerOrigin(Context ctx)
    {
        super(ctx);
        registerInterface(ICustomerOrigin.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BD1B083F");
    }
    private CustomerOriginController getController() throws BOSException
    {
        return (CustomerOriginController)getBizController();
    }
    /**
     *取集合-System defined method
     *@param oql oql
     *@return
     */
    public CustomerOriginCollection getCustomerOriginCollection(String oql) throws BOSException
    {
        try {
            return getController().getCustomerOriginCollection(getContext(), oql);
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
    public CustomerOriginCollection getCustomerOriginCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCustomerOriginCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public CustomerOriginCollection getCustomerOriginCollection() throws BOSException
    {
        try {
            return getController().getCustomerOriginCollection(getContext());
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
    public CustomerOriginInfo getCustomerOriginInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCustomerOriginInfo(getContext(), oql);
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
    public CustomerOriginInfo getCustomerOriginInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCustomerOriginInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public CustomerOriginInfo getCustomerOriginInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCustomerOriginInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}