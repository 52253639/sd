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

public class PriceAdjustEntry extends DataBase implements IPriceAdjustEntry
{
    public PriceAdjustEntry()
    {
        super();
        registerInterface(IPriceAdjustEntry.class, this);
    }
    public PriceAdjustEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IPriceAdjustEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9E38785A");
    }
    private PriceAdjustEntryController getController() throws BOSException
    {
        return (PriceAdjustEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public PriceAdjustEntryInfo getPriceAdjustEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPriceAdjustEntryInfo(getContext(), pk, selector);
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
    public PriceAdjustEntryInfo getPriceAdjustEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPriceAdjustEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *addnew-System defined method
     *@param model model
     *@return
     */
    public IObjectPK addnew(PriceAdjustEntryInfo model) throws BOSException, EASBizException
    {
        try {
            return getController().addnew(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public PriceAdjustEntryCollection getPriceAdjustEntryCollection() throws BOSException
    {
        try {
            return getController().getPriceAdjustEntryCollection(getContext());
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
    public PriceAdjustEntryCollection getPriceAdjustEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPriceAdjustEntryCollection(getContext(), view);
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
    public PriceAdjustEntryCollection getPriceAdjustEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getPriceAdjustEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}