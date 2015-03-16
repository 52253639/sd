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

public class DayInitDataPty extends CoreBase implements IDayInitDataPty
{
    public DayInitDataPty()
    {
        super();
        registerInterface(IDayInitDataPty.class, this);
    }
    public DayInitDataPty(Context ctx)
    {
        super(ctx);
        registerInterface(IDayInitDataPty.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("96C36A1A");
    }
    private DayInitDataPtyController getController() throws BOSException
    {
        return (DayInitDataPtyController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public DayInitDataPtyInfo getDayInitDataPtyInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDayInitDataPtyInfo(getContext(), pk);
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
    public DayInitDataPtyInfo getDayInitDataPtyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDayInitDataPtyInfo(getContext(), pk, selector);
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
    public DayInitDataPtyInfo getDayInitDataPtyInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDayInitDataPtyInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public DayInitDataPtyCollection getDayInitDataPtyCollection() throws BOSException
    {
        try {
            return getController().getDayInitDataPtyCollection(getContext());
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
    public DayInitDataPtyCollection getDayInitDataPtyCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDayInitDataPtyCollection(getContext(), view);
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
    public DayInitDataPtyCollection getDayInitDataPtyCollection(String oql) throws BOSException
    {
        try {
            return getController().getDayInitDataPtyCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}