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

public class OldRentFreeEntry extends CoreBillEntryBase implements IOldRentFreeEntry
{
    public OldRentFreeEntry()
    {
        super();
        registerInterface(IOldRentFreeEntry.class, this);
    }
    public OldRentFreeEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IOldRentFreeEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7708B2EF");
    }
    private OldRentFreeEntryController getController() throws BOSException
    {
        return (OldRentFreeEntryController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk pk
     *@return
     */
    public OldRentFreeEntryInfo getOldRentFreeEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getOldRentFreeEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public OldRentFreeEntryInfo getOldRentFreeEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getOldRentFreeEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql oql
     *@return
     */
    public OldRentFreeEntryInfo getOldRentFreeEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getOldRentFreeEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@return
     */
    public OldRentFreeEntryCollection getOldRentFreeEntryCollection() throws BOSException
    {
        try {
            return getController().getOldRentFreeEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param view view
     *@return
     */
    public OldRentFreeEntryCollection getOldRentFreeEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getOldRentFreeEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql oql
     *@return
     */
    public OldRentFreeEntryCollection getOldRentFreeEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getOldRentFreeEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}