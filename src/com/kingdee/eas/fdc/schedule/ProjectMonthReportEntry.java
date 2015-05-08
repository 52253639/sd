package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class ProjectMonthReportEntry extends OpReportEntryBase implements IProjectMonthReportEntry
{
    public ProjectMonthReportEntry()
    {
        super();
        registerInterface(IProjectMonthReportEntry.class, this);
    }
    public ProjectMonthReportEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectMonthReportEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5285F9A1");
    }
    private ProjectMonthReportEntryController getController() throws BOSException
    {
        return (ProjectMonthReportEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public ProjectMonthReportEntryInfo getProjectMonthReportEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectMonthReportEntryInfo(getContext(), oql);
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
    public ProjectMonthReportEntryInfo getProjectMonthReportEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectMonthReportEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ProjectMonthReportEntryInfo getProjectMonthReportEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectMonthReportEntryInfo(getContext(), pk);
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
    public ProjectMonthReportEntryCollection getProjectMonthReportEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getProjectMonthReportEntryCollection(getContext(), oql);
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
    public ProjectMonthReportEntryCollection getProjectMonthReportEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProjectMonthReportEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ProjectMonthReportEntryCollection getProjectMonthReportEntryCollection() throws BOSException
    {
        try {
            return getController().getProjectMonthReportEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}