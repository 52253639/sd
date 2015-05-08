package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class GTNBizType extends CoreBillEntryBase implements IGTNBizType
{
    public GTNBizType()
    {
        super();
        registerInterface(IGTNBizType.class, this);
    }
    public GTNBizType(Context ctx)
    {
        super(ctx);
        registerInterface(IGTNBizType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("EB4FCAE2");
    }
    private GTNBizTypeController getController() throws BOSException
    {
        return (GTNBizTypeController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public GTNBizTypeInfo getGTNBizTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getGTNBizTypeInfo(getContext(), pk);
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
    public GTNBizTypeInfo getGTNBizTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getGTNBizTypeInfo(getContext(), pk, selector);
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
    public GTNBizTypeInfo getGTNBizTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getGTNBizTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public GTNBizTypeCollection getGTNBizTypeCollection() throws BOSException
    {
        try {
            return getController().getGTNBizTypeCollection(getContext());
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
    public GTNBizTypeCollection getGTNBizTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getGTNBizTypeCollection(getContext(), view);
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
    public GTNBizTypeCollection getGTNBizTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getGTNBizTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}