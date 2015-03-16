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

public class DayPurchaseBld extends CoreBase implements IDayPurchaseBld
{
    public DayPurchaseBld()
    {
        super();
        registerInterface(IDayPurchaseBld.class, this);
    }
    public DayPurchaseBld(Context ctx)
    {
        super(ctx);
        registerInterface(IDayPurchaseBld.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("17CE2AF8");
    }
    private DayPurchaseBldController getController() throws BOSException
    {
        return (DayPurchaseBldController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public DayPurchaseBldInfo getDayPurchaseBldInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDayPurchaseBldInfo(getContext(), pk);
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
    public DayPurchaseBldInfo getDayPurchaseBldInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDayPurchaseBldInfo(getContext(), pk, selector);
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
    public DayPurchaseBldInfo getDayPurchaseBldInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDayPurchaseBldInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public DayPurchaseBldCollection getDayPurchaseBldCollection() throws BOSException
    {
        try {
            return getController().getDayPurchaseBldCollection(getContext());
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
    public DayPurchaseBldCollection getDayPurchaseBldCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDayPurchaseBldCollection(getContext(), view);
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
    public DayPurchaseBldCollection getDayPurchaseBldCollection(String oql) throws BOSException
    {
        try {
            return getController().getDayPurchaseBldCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}