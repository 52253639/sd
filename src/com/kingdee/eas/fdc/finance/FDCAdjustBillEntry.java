package com.kingdee.eas.fdc.finance;

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
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class FDCAdjustBillEntry extends CoreBillEntryBase implements IFDCAdjustBillEntry
{
    public FDCAdjustBillEntry()
    {
        super();
        registerInterface(IFDCAdjustBillEntry.class, this);
    }
    public FDCAdjustBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCAdjustBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("18143306");
    }
    private FDCAdjustBillEntryController getController() throws BOSException
    {
        return (FDCAdjustBillEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCAdjustBillEntryInfo getFDCAdjustBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCAdjustBillEntryInfo(getContext(), pk);
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
    public FDCAdjustBillEntryInfo getFDCAdjustBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCAdjustBillEntryInfo(getContext(), pk, selector);
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
    public FDCAdjustBillEntryInfo getFDCAdjustBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCAdjustBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCAdjustBillEntryCollection getFDCAdjustBillEntryCollection() throws BOSException
    {
        try {
            return getController().getFDCAdjustBillEntryCollection(getContext());
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
    public FDCAdjustBillEntryCollection getFDCAdjustBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCAdjustBillEntryCollection(getContext(), view);
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
    public FDCAdjustBillEntryCollection getFDCAdjustBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCAdjustBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}