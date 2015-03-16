package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.invite.app.*;
import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.TreeBase;

public class NewListingEntry extends TreeBase implements INewListingEntry
{
    public NewListingEntry()
    {
        super();
        registerInterface(INewListingEntry.class, this);
    }
    public NewListingEntry(Context ctx)
    {
        super(ctx);
        registerInterface(INewListingEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D5E62E6A");
    }
    private NewListingEntryController getController() throws BOSException
    {
        return (NewListingEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public NewListingEntryInfo getNewListingEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getNewListingEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public NewListingEntryInfo getNewListingEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getNewListingEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public NewListingEntryInfo getNewListingEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getNewListingEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public NewListingEntryCollection getNewListingEntryCollection() throws BOSException
    {
        try {
            return getController().getNewListingEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public NewListingEntryCollection getNewListingEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getNewListingEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public NewListingEntryCollection getNewListingEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getNewListingEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}