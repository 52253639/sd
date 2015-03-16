package com.kingdee.eas.fdc.market;

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
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.market.app.*;
import com.kingdee.eas.framework.IDataBase;

public class ChannelType extends DataBase implements IChannelType
{
    public ChannelType()
    {
        super();
        registerInterface(IChannelType.class, this);
    }
    public ChannelType(Context ctx)
    {
        super(ctx);
        registerInterface(IChannelType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("878CC46C");
    }
    private ChannelTypeController getController() throws BOSException
    {
        return (ChannelTypeController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ChannelTypeInfo getChannelTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getChannelTypeInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public ChannelTypeInfo getChannelTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getChannelTypeInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public ChannelTypeInfo getChannelTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getChannelTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ChannelTypeCollection getChannelTypeCollection() throws BOSException
    {
        try {
            return getController().getChannelTypeCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public ChannelTypeCollection getChannelTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getChannelTypeCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public ChannelTypeCollection getChannelTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getChannelTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}