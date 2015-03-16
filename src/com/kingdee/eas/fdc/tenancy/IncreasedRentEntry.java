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

public class IncreasedRentEntry extends CoreBillEntryBase implements IIncreasedRentEntry
{
    public IncreasedRentEntry()
    {
        super();
        registerInterface(IIncreasedRentEntry.class, this);
    }
    public IncreasedRentEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IIncreasedRentEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3E3035E0");
    }
    private IncreasedRentEntryController getController() throws BOSException
    {
        return (IncreasedRentEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public IncreasedRentEntryInfo getIncreasedRentEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getIncreasedRentEntryInfo(getContext(), pk);
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
    public IncreasedRentEntryInfo getIncreasedRentEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getIncreasedRentEntryInfo(getContext(), pk, selector);
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
    public IncreasedRentEntryInfo getIncreasedRentEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getIncreasedRentEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public IncreasedRentEntryCollection getIncreasedRentEntryCollection() throws BOSException
    {
        try {
            return getController().getIncreasedRentEntryCollection(getContext());
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
    public IncreasedRentEntryCollection getIncreasedRentEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getIncreasedRentEntryCollection(getContext(), view);
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
    public IncreasedRentEntryCollection getIncreasedRentEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getIncreasedRentEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}