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

public class ProjectWeekReportEntry extends OpReportEntryBase implements IProjectWeekReportEntry
{
    public ProjectWeekReportEntry()
    {
        super();
        registerInterface(IProjectWeekReportEntry.class, this);
    }
    public ProjectWeekReportEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectWeekReportEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5A44EA07");
    }
    private ProjectWeekReportEntryController getController() throws BOSException
    {
        return (ProjectWeekReportEntryController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public ProjectWeekReportEntryInfo getProjectWeekReportEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectWeekReportEntryInfo(getContext(), oql);
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
    public ProjectWeekReportEntryInfo getProjectWeekReportEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectWeekReportEntryInfo(getContext(), pk, selector);
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
    public ProjectWeekReportEntryInfo getProjectWeekReportEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectWeekReportEntryInfo(getContext(), pk);
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
    public ProjectWeekReportEntryCollection getProjectWeekReportEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getProjectWeekReportEntryCollection(getContext(), oql);
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
    public ProjectWeekReportEntryCollection getProjectWeekReportEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProjectWeekReportEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ProjectWeekReportEntryCollection getProjectWeekReportEntryCollection() throws BOSException
    {
        try {
            return getController().getProjectWeekReportEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}