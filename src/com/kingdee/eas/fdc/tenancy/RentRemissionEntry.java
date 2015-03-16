package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class RentRemissionEntry extends CoreBillEntryBase implements IRentRemissionEntry
{
    public RentRemissionEntry()
    {
        super();
        registerInterface(IRentRemissionEntry.class, this);
    }
    public RentRemissionEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IRentRemissionEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("09A090FB");
    }
    private RentRemissionEntryController getController() throws BOSException
    {
        return (RentRemissionEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public RentRemissionEntryInfo getRentRemissionEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRentRemissionEntryInfo(getContext(), pk);
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
    public RentRemissionEntryInfo getRentRemissionEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRentRemissionEntryInfo(getContext(), pk, selector);
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
    public RentRemissionEntryInfo getRentRemissionEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRentRemissionEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public RentRemissionEntryCollection getRentRemissionEntryCollection() throws BOSException
    {
        try {
            return getController().getRentRemissionEntryCollection(getContext());
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
    public RentRemissionEntryCollection getRentRemissionEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRentRemissionEntryCollection(getContext(), view);
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
    public RentRemissionEntryCollection getRentRemissionEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getRentRemissionEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}