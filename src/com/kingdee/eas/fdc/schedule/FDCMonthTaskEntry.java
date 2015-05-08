package com.kingdee.eas.fdc.schedule;

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
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class FDCMonthTaskEntry extends CoreBillEntryBase implements IFDCMonthTaskEntry
{
    public FDCMonthTaskEntry()
    {
        super();
        registerInterface(IFDCMonthTaskEntry.class, this);
    }
    public FDCMonthTaskEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCMonthTaskEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("A3CB94FC");
    }
    private FDCMonthTaskEntryController getController() throws BOSException
    {
        return (FDCMonthTaskEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCMonthTaskEntryInfo getFDCMonthTaskEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCMonthTaskEntryInfo(getContext(), pk);
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
    public FDCMonthTaskEntryInfo getFDCMonthTaskEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCMonthTaskEntryInfo(getContext(), pk, selector);
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
    public FDCMonthTaskEntryInfo getFDCMonthTaskEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCMonthTaskEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCMonthTaskEntryCollection getFDCMonthTaskEntryCollection() throws BOSException
    {
        try {
            return getController().getFDCMonthTaskEntryCollection(getContext());
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
    public FDCMonthTaskEntryCollection getFDCMonthTaskEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCMonthTaskEntryCollection(getContext(), view);
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
    public FDCMonthTaskEntryCollection getFDCMonthTaskEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCMonthTaskEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}