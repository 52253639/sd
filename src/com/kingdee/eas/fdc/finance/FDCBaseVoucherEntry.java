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
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.BillEntryBase;
import com.kingdee.eas.framework.IBillEntryBase;

public class FDCBaseVoucherEntry extends BillEntryBase implements IFDCBaseVoucherEntry
{
    public FDCBaseVoucherEntry()
    {
        super();
        registerInterface(IFDCBaseVoucherEntry.class, this);
    }
    public FDCBaseVoucherEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCBaseVoucherEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("69327BEB");
    }
    private FDCBaseVoucherEntryController getController() throws BOSException
    {
        return (FDCBaseVoucherEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCBaseVoucherEntryInfo getFDCBaseVoucherEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCBaseVoucherEntryInfo(getContext(), pk);
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
    public FDCBaseVoucherEntryInfo getFDCBaseVoucherEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCBaseVoucherEntryInfo(getContext(), pk, selector);
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
    public FDCBaseVoucherEntryInfo getFDCBaseVoucherEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCBaseVoucherEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCBaseVoucherEntryCollection getFDCBaseVoucherEntryCollection() throws BOSException
    {
        try {
            return getController().getFDCBaseVoucherEntryCollection(getContext());
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
    public FDCBaseVoucherEntryCollection getFDCBaseVoucherEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCBaseVoucherEntryCollection(getContext(), view);
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
    public FDCBaseVoucherEntryCollection getFDCBaseVoucherEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCBaseVoucherEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}