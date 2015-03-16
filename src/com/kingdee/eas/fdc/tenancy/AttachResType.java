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

public class AttachResType extends DataBase implements IAttachResType
{
    public AttachResType()
    {
        super();
        registerInterface(IAttachResType.class, this);
    }
    public AttachResType(Context ctx)
    {
        super(ctx);
        registerInterface(IAttachResType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("488276EC");
    }
    private AttachResTypeController getController() throws BOSException
    {
        return (AttachResTypeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public AttachResTypeInfo getAttachResTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAttachResTypeInfo(getContext(), pk);
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
    public AttachResTypeInfo getAttachResTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAttachResTypeInfo(getContext(), pk, selector);
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
    public AttachResTypeInfo getAttachResTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getAttachResTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public AttachResTypeCollection getAttachResTypeCollection() throws BOSException
    {
        try {
            return getController().getAttachResTypeCollection(getContext());
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
    public AttachResTypeCollection getAttachResTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAttachResTypeCollection(getContext(), view);
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
    public AttachResTypeCollection getAttachResTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getAttachResTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}