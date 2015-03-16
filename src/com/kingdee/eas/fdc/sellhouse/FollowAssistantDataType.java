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

public class FollowAssistantDataType extends TreeBase implements IFollowAssistantDataType
{
    public FollowAssistantDataType()
    {
        super();
        registerInterface(IFollowAssistantDataType.class, this);
    }
    public FollowAssistantDataType(Context ctx)
    {
        super(ctx);
        registerInterface(IFollowAssistantDataType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F203F616");
    }
    private FollowAssistantDataTypeController getController() throws BOSException
    {
        return (FollowAssistantDataTypeController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FollowAssistantDataTypeInfo getFollowAssistantDataTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFollowAssistantDataTypeInfo(getContext(), pk);
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
    public FollowAssistantDataTypeInfo getFollowAssistantDataTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFollowAssistantDataTypeInfo(getContext(), pk, selector);
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
    public FollowAssistantDataTypeInfo getFollowAssistantDataTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFollowAssistantDataTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FollowAssistantDataTypeCollection getFollowAssistantDataTypeCollection() throws BOSException
    {
        try {
            return getController().getFollowAssistantDataTypeCollection(getContext());
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
    public FollowAssistantDataTypeCollection getFollowAssistantDataTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFollowAssistantDataTypeCollection(getContext(), view);
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
    public FollowAssistantDataTypeCollection getFollowAssistantDataTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getFollowAssistantDataTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}