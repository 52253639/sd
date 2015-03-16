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

public class CustomerOriginGroup extends FDCDataBase implements ICustomerOriginGroup
{
    public CustomerOriginGroup()
    {
        super();
        registerInterface(ICustomerOriginGroup.class, this);
    }
    public CustomerOriginGroup(Context ctx)
    {
        super(ctx);
        registerInterface(ICustomerOriginGroup.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("52294D80");
    }
    private CustomerOriginGroupController getController() throws BOSException
    {
        return (CustomerOriginGroupController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public CustomerOriginGroupInfo getCustomerOriginGroupInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCustomerOriginGroupInfo(getContext(), pk);
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
    public CustomerOriginGroupInfo getCustomerOriginGroupInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCustomerOriginGroupInfo(getContext(), pk, selector);
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
    public CustomerOriginGroupInfo getCustomerOriginGroupInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCustomerOriginGroupInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public CustomerOriginGroupCollection getCustomerOriginGroupCollection() throws BOSException
    {
        try {
            return getController().getCustomerOriginGroupCollection(getContext());
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
    public CustomerOriginGroupCollection getCustomerOriginGroupCollection(String oql) throws BOSException
    {
        try {
            return getController().getCustomerOriginGroupCollection(getContext(), oql);
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
    public CustomerOriginGroupCollection getCustomerOriginGroupCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCustomerOriginGroupCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}