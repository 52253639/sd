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

public class AgioEntry extends CoreBase implements IAgioEntry
{
    public AgioEntry()
    {
        super();
        registerInterface(IAgioEntry.class, this);
    }
    public AgioEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IAgioEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5EC88F8B");
    }
    private AgioEntryController getController() throws BOSException
    {
        return (AgioEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public AgioEntryInfo getAgioEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAgioEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql oql
     *@return
     */
    public AgioEntryInfo getAgioEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getAgioEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public AgioEntryInfo getAgioEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAgioEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public AgioEntryCollection getAgioEntryCollection() throws BOSException
    {
        try {
            return getController().getAgioEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view view
     *@return
     */
    public AgioEntryCollection getAgioEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAgioEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql oql
     *@return
     */
    public AgioEntryCollection getAgioEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getAgioEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}