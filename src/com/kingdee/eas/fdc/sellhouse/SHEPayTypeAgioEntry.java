package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class SHEPayTypeAgioEntry extends AgioEntry implements ISHEPayTypeAgioEntry
{
    public SHEPayTypeAgioEntry()
    {
        super();
        registerInterface(ISHEPayTypeAgioEntry.class, this);
    }
    public SHEPayTypeAgioEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISHEPayTypeAgioEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("14552C39");
    }
    private SHEPayTypeAgioEntryController getController() throws BOSException
    {
        return (SHEPayTypeAgioEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SHEPayTypeAgioEntryInfo getSHEPayTypeAgioEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSHEPayTypeAgioEntryInfo(getContext(), pk);
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
    public SHEPayTypeAgioEntryInfo getSHEPayTypeAgioEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSHEPayTypeAgioEntryInfo(getContext(), pk, selector);
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
    public SHEPayTypeAgioEntryInfo getSHEPayTypeAgioEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSHEPayTypeAgioEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SHEPayTypeAgioEntryCollection getSHEPayTypeAgioEntryCollection() throws BOSException
    {
        try {
            return getController().getSHEPayTypeAgioEntryCollection(getContext());
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
    public SHEPayTypeAgioEntryCollection getSHEPayTypeAgioEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSHEPayTypeAgioEntryCollection(getContext(), view);
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
    public SHEPayTypeAgioEntryCollection getSHEPayTypeAgioEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSHEPayTypeAgioEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}