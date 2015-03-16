package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class RoomPropertyReport extends DataBase implements IRoomPropertyReport
{
    public RoomPropertyReport()
    {
        super();
        registerInterface(IRoomPropertyReport.class, this);
    }
    public RoomPropertyReport(Context ctx)
    {
        super(ctx);
        registerInterface(IRoomPropertyReport.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("80C3D68D");
    }
    private RoomPropertyReportController getController() throws BOSException
    {
        return (RoomPropertyReportController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public RoomPropertyReportInfo getRoomPropertyReportInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPropertyReportInfo(getContext(), pk);
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
    public RoomPropertyReportInfo getRoomPropertyReportInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPropertyReportInfo(getContext(), pk, selector);
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
    public RoomPropertyReportInfo getRoomPropertyReportInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getRoomPropertyReportInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public RoomPropertyReportCollection getRoomPropertyReportCollection() throws BOSException
    {
        try {
            return getController().getRoomPropertyReportCollection(getContext());
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
    public RoomPropertyReportCollection getRoomPropertyReportCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getRoomPropertyReportCollection(getContext(), view);
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
    public RoomPropertyReportCollection getRoomPropertyReportCollection(String oql) throws BOSException
    {
        try {
            return getController().getRoomPropertyReportCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}