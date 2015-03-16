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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class DayRecepType extends CoreBase implements IDayRecepType
{
    public DayRecepType()
    {
        super();
        registerInterface(IDayRecepType.class, this);
    }
    public DayRecepType(Context ctx)
    {
        super(ctx);
        registerInterface(IDayRecepType.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BC933D14");
    }
    private DayRecepTypeController getController() throws BOSException
    {
        return (DayRecepTypeController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public DayRecepTypeInfo getDayRecepTypeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDayRecepTypeInfo(getContext(), pk);
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
    public DayRecepTypeInfo getDayRecepTypeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDayRecepTypeInfo(getContext(), pk, selector);
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
    public DayRecepTypeInfo getDayRecepTypeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDayRecepTypeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public DayRecepTypeCollection getDayRecepTypeCollection() throws BOSException
    {
        try {
            return getController().getDayRecepTypeCollection(getContext());
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
    public DayRecepTypeCollection getDayRecepTypeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDayRecepTypeCollection(getContext(), view);
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
    public DayRecepTypeCollection getDayRecepTypeCollection(String oql) throws BOSException
    {
        try {
            return getController().getDayRecepTypeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}