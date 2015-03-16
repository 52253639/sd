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

public class DayInitDataBld extends CoreBase implements IDayInitDataBld
{
    public DayInitDataBld()
    {
        super();
        registerInterface(IDayInitDataBld.class, this);
    }
    public DayInitDataBld(Context ctx)
    {
        super(ctx);
        registerInterface(IDayInitDataBld.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("96C3347F");
    }
    private DayInitDataBldController getController() throws BOSException
    {
        return (DayInitDataBldController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public DayInitDataBldInfo getDayInitDataBldInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDayInitDataBldInfo(getContext(), pk);
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
    public DayInitDataBldInfo getDayInitDataBldInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDayInitDataBldInfo(getContext(), pk, selector);
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
    public DayInitDataBldInfo getDayInitDataBldInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDayInitDataBldInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public DayInitDataBldCollection getDayInitDataBldCollection() throws BOSException
    {
        try {
            return getController().getDayInitDataBldCollection(getContext());
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
    public DayInitDataBldCollection getDayInitDataBldCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDayInitDataBldCollection(getContext(), view);
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
    public DayInitDataBldCollection getDayInitDataBldCollection(String oql) throws BOSException
    {
        try {
            return getController().getDayInitDataBldCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}