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

public class RentFreeEntry extends CoreBillEntryBase implements IRentFreeEntry
{
    public RentFreeEntry()
    {
        super();
        registerInterface(IRentFreeEntry.class, this);
    }
    public RentFreeEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IRentFreeEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("7D898324");
    }
    private RentFreeEntryController getController() throws BOSException
    {
        return (RentFreeEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public RentFreeEntryInfo getRentFreeEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRentFreeEntryInfo(getContext(), pk);
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
    public RentFreeEntryInfo getRentFreeEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRentFreeEntryInfo(getContext(), pk, selector);
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
    public RentFreeEntryInfo getRentFreeEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRentFreeEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RentFreeEntryCollection getRentFreeEntryCollection() throws BOSException
    {
        try {
            return getController().getRentFreeEntryCollection(getContext());
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
    public RentFreeEntryCollection getRentFreeEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRentFreeEntryCollection(getContext(), view);
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
    public RentFreeEntryCollection getRentFreeEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getRentFreeEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}