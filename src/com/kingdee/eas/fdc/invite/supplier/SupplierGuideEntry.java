package com.kingdee.eas.fdc.invite.supplier;

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
import com.kingdee.eas.fdc.invite.supplier.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class SupplierGuideEntry extends CoreBillEntryBase implements ISupplierGuideEntry
{
    public SupplierGuideEntry()
    {
        super();
        registerInterface(ISupplierGuideEntry.class, this);
    }
    public SupplierGuideEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ISupplierGuideEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("ED86A740");
    }
    private SupplierGuideEntryController getController() throws BOSException
    {
        return (SupplierGuideEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public SupplierGuideEntryInfo getSupplierGuideEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierGuideEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public SupplierGuideEntryInfo getSupplierGuideEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierGuideEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public SupplierGuideEntryInfo getSupplierGuideEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getSupplierGuideEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public SupplierGuideEntryCollection getSupplierGuideEntryCollection() throws BOSException
    {
        try {
            return getController().getSupplierGuideEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param view view
     *@return
     */
    public SupplierGuideEntryCollection getSupplierGuideEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getSupplierGuideEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@param oql oql
     *@return
     */
    public SupplierGuideEntryCollection getSupplierGuideEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getSupplierGuideEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}