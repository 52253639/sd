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

public class QuitNewPayListEntry extends CoreBase implements IQuitNewPayListEntry
{
    public QuitNewPayListEntry()
    {
        super();
        registerInterface(IQuitNewPayListEntry.class, this);
    }
    public QuitNewPayListEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IQuitNewPayListEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("CB755F62");
    }
    private QuitNewPayListEntryController getController() throws BOSException
    {
        return (QuitNewPayListEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public QuitNewPayListEntryInfo getQuitNewPayListEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getQuitNewPayListEntryInfo(getContext(), pk);
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
    public QuitNewPayListEntryInfo getQuitNewPayListEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getQuitNewPayListEntryInfo(getContext(), pk, selector);
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
    public QuitNewPayListEntryInfo getQuitNewPayListEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getQuitNewPayListEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public QuitNewPayListEntryCollection getQuitNewPayListEntryCollection() throws BOSException
    {
        try {
            return getController().getQuitNewPayListEntryCollection(getContext());
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
    public QuitNewPayListEntryCollection getQuitNewPayListEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getQuitNewPayListEntryCollection(getContext(), view);
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
    public QuitNewPayListEntryCollection getQuitNewPayListEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getQuitNewPayListEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}