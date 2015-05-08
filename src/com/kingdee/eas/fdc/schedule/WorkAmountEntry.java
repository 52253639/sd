package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.framework.CoreBillEntryBase;
import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;

public class WorkAmountEntry extends CoreBillEntryBase implements IWorkAmountEntry
{
    public WorkAmountEntry()
    {
        super();
        registerInterface(IWorkAmountEntry.class, this);
    }
    public WorkAmountEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IWorkAmountEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("44A9A893");
    }
    private WorkAmountEntryController getController() throws BOSException
    {
        return (WorkAmountEntryController)getBizController();
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public WorkAmountEntryCollection getWorkAmountEntryCollection() throws BOSException
    {
        try {
            return getController().getWorkAmountEntryCollection(getContext());
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
    public WorkAmountEntryCollection getWorkAmountEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getWorkAmountEntryCollection(getContext(), view);
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
    public WorkAmountEntryCollection getWorkAmountEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getWorkAmountEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}