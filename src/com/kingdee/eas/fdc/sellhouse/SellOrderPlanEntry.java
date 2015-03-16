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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class SellOrderPlanEntry extends DataBase implements ISellOrderPlanEntry
{
    public SellOrderPlanEntry()
    {
        super();
        registerInterface(ISellOrderPlanEntry.class, this);
    }
    public SellOrderPlanEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISellOrderPlanEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FBA9F928");
    }
    private SellOrderPlanEntryController getController() throws BOSException
    {
        return (SellOrderPlanEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SellOrderPlanEntryInfo getSellOrderPlanEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSellOrderPlanEntryInfo(getContext(), pk);
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
    public SellOrderPlanEntryInfo getSellOrderPlanEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSellOrderPlanEntryInfo(getContext(), pk, selector);
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
    public SellOrderPlanEntryInfo getSellOrderPlanEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSellOrderPlanEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SellOrderPlanEntryCollection getSellOrderPlanEntryCollection() throws BOSException
    {
        try {
            return getController().getSellOrderPlanEntryCollection(getContext());
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
    public SellOrderPlanEntryCollection getSellOrderPlanEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSellOrderPlanEntryCollection(getContext(), view);
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
    public SellOrderPlanEntryCollection getSellOrderPlanEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSellOrderPlanEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}