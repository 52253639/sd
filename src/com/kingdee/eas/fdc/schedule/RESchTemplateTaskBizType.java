package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class RESchTemplateTaskBizType extends CoreBase implements IRESchTemplateTaskBizType
{
    public RESchTemplateTaskBizType()
    {
        super();
        registerInterface(IRESchTemplateTaskBizType.class, this);
    }
    public RESchTemplateTaskBizType(Context ctx)
    {
        super(ctx);
        registerInterface(IRESchTemplateTaskBizType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7184C55F");
    }
    private RESchTemplateTaskBizTypeController getController() throws BOSException
    {
        return (RESchTemplateTaskBizTypeController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public RESchTemplateTaskBizTypeInfo getRESchTemplateTaskBizTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRESchTemplateTaskBizTypeInfo(getContext(), pk);
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
    public RESchTemplateTaskBizTypeInfo getRESchTemplateTaskBizTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRESchTemplateTaskBizTypeInfo(getContext(), pk, selector);
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
    public RESchTemplateTaskBizTypeInfo getRESchTemplateTaskBizTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRESchTemplateTaskBizTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RESchTemplateTaskBizTypeCollection getRESchTemplateTaskBizTypeCollection() throws BOSException
    {
        try {
            return getController().getRESchTemplateTaskBizTypeCollection(getContext());
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
    public RESchTemplateTaskBizTypeCollection getRESchTemplateTaskBizTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRESchTemplateTaskBizTypeCollection(getContext(), view);
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
    public RESchTemplateTaskBizTypeCollection getRESchTemplateTaskBizTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getRESchTemplateTaskBizTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}