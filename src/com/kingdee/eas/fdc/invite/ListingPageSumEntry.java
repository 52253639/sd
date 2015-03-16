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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class ListingPageSumEntry extends CoreBase implements IListingPageSumEntry
{
    public ListingPageSumEntry()
    {
        super();
        registerInterface(IListingPageSumEntry.class, this);
    }
    public ListingPageSumEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IListingPageSumEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5B9B8396");
    }
    private ListingPageSumEntryController getController() throws BOSException
    {
        return (ListingPageSumEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ListingPageSumEntryInfo getListingPageSumEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getListingPageSumEntryInfo(getContext(), pk);
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
    public ListingPageSumEntryInfo getListingPageSumEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getListingPageSumEntryInfo(getContext(), pk, selector);
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
    public ListingPageSumEntryInfo getListingPageSumEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getListingPageSumEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ListingPageSumEntryCollection getListingPageSumEntryCollection() throws BOSException
    {
        try {
            return getController().getListingPageSumEntryCollection(getContext());
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
    public ListingPageSumEntryCollection getListingPageSumEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getListingPageSumEntryCollection(getContext(), view);
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
    public ListingPageSumEntryCollection getListingPageSumEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getListingPageSumEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}