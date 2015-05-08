package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import java.lang.String;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;

public class ScheduleTaskProgressReport extends ProgressReportBase implements IScheduleTaskProgressReport
{
    public ScheduleTaskProgressReport()
    {
        super();
        registerInterface(IScheduleTaskProgressReport.class, this);
    }
    public ScheduleTaskProgressReport(Context ctx)
    {
        super(ctx);
        registerInterface(IScheduleTaskProgressReport.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("75F545F3");
    }
    private ScheduleTaskProgressReportController getController() throws BOSException
    {
        return (ScheduleTaskProgressReportController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public ScheduleTaskProgressReportInfo getScheduleTaskProgressReportInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleTaskProgressReportInfo(getContext(), pk);
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
    public ScheduleTaskProgressReportInfo getScheduleTaskProgressReportInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleTaskProgressReportInfo(getContext(), pk, selector);
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
    public ScheduleTaskProgressReportInfo getScheduleTaskProgressReportInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getScheduleTaskProgressReportInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public ScheduleTaskProgressReportCollection getScheduleTaskProgressReportCollection() throws BOSException
    {
        try {
            return getController().getScheduleTaskProgressReportCollection(getContext());
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
    public ScheduleTaskProgressReportCollection getScheduleTaskProgressReportCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getScheduleTaskProgressReportCollection(getContext(), view);
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
    public ScheduleTaskProgressReportCollection getScheduleTaskProgressReportCollection(String oql) throws BOSException
    {
        try {
            return getController().getScheduleTaskProgressReportCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *进度汇报后反写数据-User defined method
     *@param model model
     */
    public void afterschreportwriteBack(IObjectValue model) throws BOSException
    {
        try {
            getController().afterschreportwriteBack(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}