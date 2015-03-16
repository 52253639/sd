package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

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
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class TenAttachResourcePayListEntry extends CoreBase implements ITenAttachResourcePayListEntry
{
    public TenAttachResourcePayListEntry()
    {
        super();
        registerInterface(ITenAttachResourcePayListEntry.class, this);
    }
    public TenAttachResourcePayListEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ITenAttachResourcePayListEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FF2704B3");
    }
    private TenAttachResourcePayListEntryController getController() throws BOSException
    {
        return (TenAttachResourcePayListEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public TenAttachResourcePayListEntryInfo getTenAttachResourcePayListEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTenAttachResourcePayListEntryInfo(getContext(), pk);
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
    public TenAttachResourcePayListEntryInfo getTenAttachResourcePayListEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTenAttachResourcePayListEntryInfo(getContext(), pk, selector);
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
    public TenAttachResourcePayListEntryInfo getTenAttachResourcePayListEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTenAttachResourcePayListEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TenAttachResourcePayListEntryCollection getTenAttachResourcePayListEntryCollection() throws BOSException
    {
        try {
            return getController().getTenAttachResourcePayListEntryCollection(getContext());
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
    public TenAttachResourcePayListEntryCollection getTenAttachResourcePayListEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTenAttachResourcePayListEntryCollection(getContext(), view);
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
    public TenAttachResourcePayListEntryCollection getTenAttachResourcePayListEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getTenAttachResourcePayListEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}