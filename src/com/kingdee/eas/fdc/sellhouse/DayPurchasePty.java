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

public class DayPurchasePty extends CoreBase implements IDayPurchasePty
{
    public DayPurchasePty()
    {
        super();
        registerInterface(IDayPurchasePty.class, this);
    }
    public DayPurchasePty(Context ctx)
    {
        super(ctx);
        registerInterface(IDayPurchasePty.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("17CE6093");
    }
    private DayPurchasePtyController getController() throws BOSException
    {
        return (DayPurchasePtyController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public DayPurchasePtyInfo getDayPurchasePtyInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDayPurchasePtyInfo(getContext(), pk);
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
    public DayPurchasePtyInfo getDayPurchasePtyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDayPurchasePtyInfo(getContext(), pk, selector);
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
    public DayPurchasePtyInfo getDayPurchasePtyInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDayPurchasePtyInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public DayPurchasePtyCollection getDayPurchasePtyCollection() throws BOSException
    {
        try {
            return getController().getDayPurchasePtyCollection(getContext());
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
    public DayPurchasePtyCollection getDayPurchasePtyCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDayPurchasePtyCollection(getContext(), view);
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
    public DayPurchasePtyCollection getDayPurchasePtyCollection(String oql) throws BOSException
    {
        try {
            return getController().getDayPurchasePtyCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}