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

public class ProjectWeekReport extends OpReportBase implements IProjectWeekReport
{
    public ProjectWeekReport()
    {
        super();
        registerInterface(IProjectWeekReport.class, this);
    }
    public ProjectWeekReport(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectWeekReport.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("23D2312B");
    }
    private ProjectWeekReportController getController() throws BOSException
    {
        return (ProjectWeekReportController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ProjectWeekReportInfo getProjectWeekReportInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectWeekReportInfo(getContext(), pk);
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
    public ProjectWeekReportInfo getProjectWeekReportInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectWeekReportInfo(getContext(), pk, selector);
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
    public ProjectWeekReportInfo getProjectWeekReportInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectWeekReportInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ProjectWeekReportCollection getProjectWeekReportCollection() throws BOSException
    {
        try {
            return getController().getProjectWeekReportCollection(getContext());
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
    public ProjectWeekReportCollection getProjectWeekReportCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProjectWeekReportCollection(getContext(), view);
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
    public ProjectWeekReportCollection getProjectWeekReportCollection(String oql) throws BOSException
    {
        try {
            return getController().getProjectWeekReportCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}