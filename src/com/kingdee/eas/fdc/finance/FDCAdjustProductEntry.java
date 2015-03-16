package com.kingdee.eas.fdc.finance;

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
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class FDCAdjustProductEntry extends CoreBase implements IFDCAdjustProductEntry
{
    public FDCAdjustProductEntry()
    {
        super();
        registerInterface(IFDCAdjustProductEntry.class, this);
    }
    public FDCAdjustProductEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCAdjustProductEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2AEE5488");
    }
    private FDCAdjustProductEntryController getController() throws BOSException
    {
        return (FDCAdjustProductEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCAdjustProductEntryInfo getFDCAdjustProductEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCAdjustProductEntryInfo(getContext(), pk);
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
    public FDCAdjustProductEntryInfo getFDCAdjustProductEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCAdjustProductEntryInfo(getContext(), pk, selector);
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
    public FDCAdjustProductEntryInfo getFDCAdjustProductEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCAdjustProductEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCAdjustProductEntryCollection getFDCAdjustProductEntryCollection() throws BOSException
    {
        try {
            return getController().getFDCAdjustProductEntryCollection(getContext());
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
    public FDCAdjustProductEntryCollection getFDCAdjustProductEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCAdjustProductEntryCollection(getContext(), view);
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
    public FDCAdjustProductEntryCollection getFDCAdjustProductEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCAdjustProductEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}