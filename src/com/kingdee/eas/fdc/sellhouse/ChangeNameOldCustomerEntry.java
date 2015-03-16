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

public class ChangeNameOldCustomerEntry extends CoreBillEntryBase implements IChangeNameOldCustomerEntry
{
    public ChangeNameOldCustomerEntry()
    {
        super();
        registerInterface(IChangeNameOldCustomerEntry.class, this);
    }
    public ChangeNameOldCustomerEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IChangeNameOldCustomerEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("0C3BDFE3");
    }
    private ChangeNameOldCustomerEntryController getController() throws BOSException
    {
        return (ChangeNameOldCustomerEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ChangeNameOldCustomerEntryInfo getChangeNameOldCustomerEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeNameOldCustomerEntryInfo(getContext(), pk);
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
    public ChangeNameOldCustomerEntryInfo getChangeNameOldCustomerEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeNameOldCustomerEntryInfo(getContext(), pk, selector);
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
    public ChangeNameOldCustomerEntryInfo getChangeNameOldCustomerEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getChangeNameOldCustomerEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ChangeNameOldCustomerEntryCollection getChangeNameOldCustomerEntryCollection() throws BOSException
    {
        try {
            return getController().getChangeNameOldCustomerEntryCollection(getContext());
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
    public ChangeNameOldCustomerEntryCollection getChangeNameOldCustomerEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getChangeNameOldCustomerEntryCollection(getContext(), view);
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
    public ChangeNameOldCustomerEntryCollection getChangeNameOldCustomerEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getChangeNameOldCustomerEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}