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

public class FDCMonthTempTaskEntry extends CoreBillEntryBase implements IFDCMonthTempTaskEntry
{
    public FDCMonthTempTaskEntry()
    {
        super();
        registerInterface(IFDCMonthTempTaskEntry.class, this);
    }
    public FDCMonthTempTaskEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCMonthTempTaskEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("86AC6008");
    }
    private FDCMonthTempTaskEntryController getController() throws BOSException
    {
        return (FDCMonthTempTaskEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCMonthTempTaskEntryInfo getFDCMonthTempTaskEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCMonthTempTaskEntryInfo(getContext(), pk);
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
    public FDCMonthTempTaskEntryInfo getFDCMonthTempTaskEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCMonthTempTaskEntryInfo(getContext(), pk, selector);
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
    public FDCMonthTempTaskEntryInfo getFDCMonthTempTaskEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCMonthTempTaskEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCMonthTempTaskEntryCollection getFDCMonthTempTaskEntryCollection() throws BOSException
    {
        try {
            return getController().getFDCMonthTempTaskEntryCollection(getContext());
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
    public FDCMonthTempTaskEntryCollection getFDCMonthTempTaskEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCMonthTempTaskEntryCollection(getContext(), view);
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
    public FDCMonthTempTaskEntryCollection getFDCMonthTempTaskEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCMonthTempTaskEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}