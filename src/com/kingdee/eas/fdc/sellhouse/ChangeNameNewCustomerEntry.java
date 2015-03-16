package com.kingdee.eas.fdc.sellhouse;

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
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class ChangeNameNewCustomerEntry extends CoreBillEntryBase implements IChangeNameNewCustomerEntry
{
    public ChangeNameNewCustomerEntry()
    {
        super();
        registerInterface(IChangeNameNewCustomerEntry.class, this);
    }
    public ChangeNameNewCustomerEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IChangeNameNewCustomerEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7920110A");
    }
    private ChangeNameNewCustomerEntryController getController() throws BOSException
    {
        return (ChangeNameNewCustomerEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ChangeNameNewCustomerEntryInfo getChangeNameNewCustomerEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeNameNewCustomerEntryInfo(getContext(), pk);
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
    public ChangeNameNewCustomerEntryInfo getChangeNameNewCustomerEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeNameNewCustomerEntryInfo(getContext(), pk, selector);
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
    public ChangeNameNewCustomerEntryInfo getChangeNameNewCustomerEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeNameNewCustomerEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ChangeNameNewCustomerEntryCollection getChangeNameNewCustomerEntryCollection() throws BOSException
    {
        try {
            return getController().getChangeNameNewCustomerEntryCollection(getContext());
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
    public ChangeNameNewCustomerEntryCollection getChangeNameNewCustomerEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getChangeNameNewCustomerEntryCollection(getContext(), view);
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
    public ChangeNameNewCustomerEntryCollection getChangeNameNewCustomerEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getChangeNameNewCustomerEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}