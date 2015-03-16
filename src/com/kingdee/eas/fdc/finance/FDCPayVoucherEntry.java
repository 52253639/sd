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

public class FDCPayVoucherEntry extends FDCBaseVoucherEntry implements IFDCPayVoucherEntry
{
    public FDCPayVoucherEntry()
    {
        super();
        registerInterface(IFDCPayVoucherEntry.class, this);
    }
    public FDCPayVoucherEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCPayVoucherEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("AF19A856");
    }
    private FDCPayVoucherEntryController getController() throws BOSException
    {
        return (FDCPayVoucherEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCPayVoucherEntryInfo getFDCPayVoucherEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCPayVoucherEntryInfo(getContext(), pk);
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
    public FDCPayVoucherEntryInfo getFDCPayVoucherEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCPayVoucherEntryInfo(getContext(), pk, selector);
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
    public FDCPayVoucherEntryInfo getFDCPayVoucherEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCPayVoucherEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCPayVoucherEntryCollection getFDCPayVoucherEntryCollection() throws BOSException
    {
        try {
            return getController().getFDCPayVoucherEntryCollection(getContext());
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
    public FDCPayVoucherEntryCollection getFDCPayVoucherEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCPayVoucherEntryCollection(getContext(), view);
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
    public FDCPayVoucherEntryCollection getFDCPayVoucherEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCPayVoucherEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}