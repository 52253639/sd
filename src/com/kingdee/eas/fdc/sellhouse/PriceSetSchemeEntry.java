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

public class PriceSetSchemeEntry extends CoreBase implements IPriceSetSchemeEntry
{
    public PriceSetSchemeEntry()
    {
        super();
        registerInterface(IPriceSetSchemeEntry.class, this);
    }
    public PriceSetSchemeEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IPriceSetSchemeEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5470A2F9");
    }
    private PriceSetSchemeEntryController getController() throws BOSException
    {
        return (PriceSetSchemeEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public PriceSetSchemeEntryInfo getPriceSetSchemeEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPriceSetSchemeEntryInfo(getContext(), pk);
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
    public PriceSetSchemeEntryInfo getPriceSetSchemeEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPriceSetSchemeEntryInfo(getContext(), pk, selector);
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
    public PriceSetSchemeEntryInfo getPriceSetSchemeEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPriceSetSchemeEntryInfo(getContext(), oql);
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
    public PriceSetSchemeEntryCollection getPriceSetSchemeEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPriceSetSchemeEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public PriceSetSchemeEntryCollection getPriceSetSchemeEntryCollection() throws BOSException
    {
        try {
            return getController().getPriceSetSchemeEntryCollection(getContext());
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
    public PriceSetSchemeEntryCollection getPriceSetSchemeEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getPriceSetSchemeEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}