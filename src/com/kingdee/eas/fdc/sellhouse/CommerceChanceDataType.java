package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.TreeBase;

public class CommerceChanceDataType extends TreeBase implements ICommerceChanceDataType
{
    public CommerceChanceDataType()
    {
        super();
        registerInterface(ICommerceChanceDataType.class, this);
    }
    public CommerceChanceDataType(Context ctx)
    {
        super(ctx);
        registerInterface(ICommerceChanceDataType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B7442DAE");
    }
    private CommerceChanceDataTypeController getController() throws BOSException
    {
        return (CommerceChanceDataTypeController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public CommerceChanceDataTypeInfo getCommerceChanceDataTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCommerceChanceDataTypeInfo(getContext(), pk);
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
    public CommerceChanceDataTypeInfo getCommerceChanceDataTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCommerceChanceDataTypeInfo(getContext(), pk, selector);
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
    public CommerceChanceDataTypeInfo getCommerceChanceDataTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCommerceChanceDataTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public CommerceChanceDataTypeCollection getCommerceChanceDataTypeCollection() throws BOSException
    {
        try {
            return getController().getCommerceChanceDataTypeCollection(getContext());
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
    public CommerceChanceDataTypeCollection getCommerceChanceDataTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCommerceChanceDataTypeCollection(getContext(), view);
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
    public CommerceChanceDataTypeCollection getCommerceChanceDataTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getCommerceChanceDataTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}