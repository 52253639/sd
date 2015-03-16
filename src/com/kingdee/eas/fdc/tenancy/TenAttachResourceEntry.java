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

public class TenAttachResourceEntry extends CoreBase implements ITenAttachResourceEntry
{
    public TenAttachResourceEntry()
    {
        super();
        registerInterface(ITenAttachResourceEntry.class, this);
    }
    public TenAttachResourceEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ITenAttachResourceEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("F5FE744B");
    }
    private TenAttachResourceEntryController getController() throws BOSException
    {
        return (TenAttachResourceEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public TenAttachResourceEntryInfo getTenAttachResourceEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTenAttachResourceEntryInfo(getContext(), pk);
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
    public TenAttachResourceEntryInfo getTenAttachResourceEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTenAttachResourceEntryInfo(getContext(), pk, selector);
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
    public TenAttachResourceEntryInfo getTenAttachResourceEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTenAttachResourceEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TenAttachResourceEntryCollection getTenAttachResourceEntryCollection() throws BOSException
    {
        try {
            return getController().getTenAttachResourceEntryCollection(getContext());
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
    public TenAttachResourceEntryCollection getTenAttachResourceEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTenAttachResourceEntryCollection(getContext(), view);
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
    public TenAttachResourceEntryCollection getTenAttachResourceEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getTenAttachResourceEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}