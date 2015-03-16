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

public class NewListTempletEntry extends TreeBase implements INewListTempletEntry
{
    public NewListTempletEntry()
    {
        super();
        registerInterface(INewListTempletEntry.class, this);
    }
    public NewListTempletEntry(Context ctx)
    {
        super(ctx);
        registerInterface(INewListTempletEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("DC1036C5");
    }
    private NewListTempletEntryController getController() throws BOSException
    {
        return (NewListTempletEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public NewListTempletEntryInfo getNewListTempletEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getNewListTempletEntryInfo(getContext(), pk);
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
    public NewListTempletEntryInfo getNewListTempletEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getNewListTempletEntryInfo(getContext(), pk, selector);
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
    public NewListTempletEntryInfo getNewListTempletEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getNewListTempletEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public NewListTempletEntryCollection getNewListTempletEntryCollection() throws BOSException
    {
        try {
            return getController().getNewListTempletEntryCollection(getContext());
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
    public NewListTempletEntryCollection getNewListTempletEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getNewListTempletEntryCollection(getContext(), oql);
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
    public NewListTempletEntryCollection getNewListTempletEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getNewListTempletEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}