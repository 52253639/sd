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

public class ProjectMonthReport extends OpReportBase implements IProjectMonthReport
{
    public ProjectMonthReport()
    {
        super();
        registerInterface(IProjectMonthReport.class, this);
    }
    public ProjectMonthReport(Context ctx)
    {
        super(ctx);
        registerInterface(IProjectMonthReport.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("58784951");
    }
    private ProjectMonthReportController getController() throws BOSException
    {
        return (ProjectMonthReportController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public ProjectMonthReportInfo getProjectMonthReportInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectMonthReportInfo(getContext(), oql);
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
    public ProjectMonthReportInfo getProjectMonthReportInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectMonthReportInfo(getContext(), pk, selector);
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
    public ProjectMonthReportInfo getProjectMonthReportInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getProjectMonthReportInfo(getContext(), pk);
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
    public ProjectMonthReportCollection getProjectMonthReportCollection(String oql) throws BOSException
    {
        try {
            return getController().getProjectMonthReportCollection(getContext(), oql);
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
    public ProjectMonthReportCollection getProjectMonthReportCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getProjectMonthReportCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ProjectMonthReportCollection getProjectMonthReportCollection() throws BOSException
    {
        try {
            return getController().getProjectMonthReportCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}