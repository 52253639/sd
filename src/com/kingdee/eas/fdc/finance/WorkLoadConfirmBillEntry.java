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

public class WorkLoadConfirmBillEntry extends BillEntryBase implements IWorkLoadConfirmBillEntry
{
    public WorkLoadConfirmBillEntry()
    {
        super();
        registerInterface(IWorkLoadConfirmBillEntry.class, this);
    }
    public WorkLoadConfirmBillEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IWorkLoadConfirmBillEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("043A7F91");
    }
    private WorkLoadConfirmBillEntryController getController() throws BOSException
    {
        return (WorkLoadConfirmBillEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public WorkLoadConfirmBillEntryInfo getWorkLoadConfirmBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getWorkLoadConfirmBillEntryInfo(getContext(), pk);
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
    public WorkLoadConfirmBillEntryInfo getWorkLoadConfirmBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getWorkLoadConfirmBillEntryInfo(getContext(), pk, selector);
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
    public WorkLoadConfirmBillEntryInfo getWorkLoadConfirmBillEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getWorkLoadConfirmBillEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public WorkLoadConfirmBillEntryCollection getWorkLoadConfirmBillEntryCollection() throws BOSException
    {
        try {
            return getController().getWorkLoadConfirmBillEntryCollection(getContext());
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
    public WorkLoadConfirmBillEntryCollection getWorkLoadConfirmBillEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getWorkLoadConfirmBillEntryCollection(getContext(), view);
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
    public WorkLoadConfirmBillEntryCollection getWorkLoadConfirmBillEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getWorkLoadConfirmBillEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}