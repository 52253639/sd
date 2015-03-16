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

public class TenancyCustomerEntry extends CoreBase implements ITenancyCustomerEntry
{
    public TenancyCustomerEntry()
    {
        super();
        registerInterface(ITenancyCustomerEntry.class, this);
    }
    public TenancyCustomerEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ITenancyCustomerEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E544FB3D");
    }
    private TenancyCustomerEntryController getController() throws BOSException
    {
        return (TenancyCustomerEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public TenancyCustomerEntryInfo getTenancyCustomerEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyCustomerEntryInfo(getContext(), pk);
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
    public TenancyCustomerEntryInfo getTenancyCustomerEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyCustomerEntryInfo(getContext(), pk, selector);
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
    public TenancyCustomerEntryInfo getTenancyCustomerEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyCustomerEntryInfo(getContext(), oql);
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
    public TenancyCustomerEntryCollection getTenancyCustomerEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTenancyCustomerEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TenancyCustomerEntryCollection getTenancyCustomerEntryCollection() throws BOSException
    {
        try {
            return getController().getTenancyCustomerEntryCollection(getContext());
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
    public TenancyCustomerEntryCollection getTenancyCustomerEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getTenancyCustomerEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}