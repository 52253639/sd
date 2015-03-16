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

public class QuitOldPayListEntry extends CoreBase implements IQuitOldPayListEntry
{
    public QuitOldPayListEntry()
    {
        super();
        registerInterface(IQuitOldPayListEntry.class, this);
    }
    public QuitOldPayListEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IQuitOldPayListEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9EA7D169");
    }
    private QuitOldPayListEntryController getController() throws BOSException
    {
        return (QuitOldPayListEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public QuitOldPayListEntryInfo getQuitOldPayListEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getQuitOldPayListEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public QuitOldPayListEntryInfo getQuitOldPayListEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getQuitOldPayListEntryInfo(getContext(), pk);
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
    public QuitOldPayListEntryInfo getQuitOldPayListEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getQuitOldPayListEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public QuitOldPayListEntryCollection getQuitOldPayListEntryCollection() throws BOSException
    {
        try {
            return getController().getQuitOldPayListEntryCollection(getContext());
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
    public QuitOldPayListEntryCollection getQuitOldPayListEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getQuitOldPayListEntryCollection(getContext(), view);
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
    public QuitOldPayListEntryCollection getQuitOldPayListEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getQuitOldPayListEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}