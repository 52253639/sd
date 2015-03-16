package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class FDCCostVoucherEntry extends FDCBaseVoucherEntry implements IFDCCostVoucherEntry
{
    public FDCCostVoucherEntry()
    {
        super();
        registerInterface(IFDCCostVoucherEntry.class, this);
    }
    public FDCCostVoucherEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCCostVoucherEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("2E718AE7");
    }
    private FDCCostVoucherEntryController getController() throws BOSException
    {
        return (FDCCostVoucherEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCCostVoucherEntryInfo getFDCCostVoucherEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCostVoucherEntryInfo(getContext(), pk);
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
    public FDCCostVoucherEntryInfo getFDCCostVoucherEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCostVoucherEntryInfo(getContext(), pk, selector);
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
    public FDCCostVoucherEntryInfo getFDCCostVoucherEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCCostVoucherEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCCostVoucherEntryCollection getFDCCostVoucherEntryCollection() throws BOSException
    {
        try {
            return getController().getFDCCostVoucherEntryCollection(getContext());
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
    public FDCCostVoucherEntryCollection getFDCCostVoucherEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCCostVoucherEntryCollection(getContext(), view);
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
    public FDCCostVoucherEntryCollection getFDCCostVoucherEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCCostVoucherEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}