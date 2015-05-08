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

public class DeptTaskProgressReport extends ProgressReportBase implements IDeptTaskProgressReport
{
    public DeptTaskProgressReport()
    {
        super();
        registerInterface(IDeptTaskProgressReport.class, this);
    }
    public DeptTaskProgressReport(Context ctx)
    {
        super(ctx);
        registerInterface(IDeptTaskProgressReport.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("5D06FD21");
    }
    private DeptTaskProgressReportController getController() throws BOSException
    {
        return (DeptTaskProgressReportController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public DeptTaskProgressReportInfo getDeptTaskProgressReportInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDeptTaskProgressReportInfo(getContext(), pk);
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
    public DeptTaskProgressReportInfo getDeptTaskProgressReportInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDeptTaskProgressReportInfo(getContext(), pk, selector);
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
    public DeptTaskProgressReportInfo getDeptTaskProgressReportInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDeptTaskProgressReportInfo(getContext(), oql);
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
    public DeptTaskProgressReportCollection getDeptTaskProgressReportCollection(String oql) throws BOSException
    {
        try {
            return getController().getDeptTaskProgressReportCollection(getContext(), oql);
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
    public DeptTaskProgressReportCollection getDeptTaskProgressReportCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDeptTaskProgressReportCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public DeptTaskProgressReportCollection getDeptTaskProgressReportCollection() throws BOSException
    {
        try {
            return getController().getDeptTaskProgressReportCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}