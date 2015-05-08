package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.eas.fdc.schedule.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class OpReportBase extends FDCBill implements IOpReportBase
{
    public OpReportBase()
    {
        super();
        registerInterface(IOpReportBase.class, this);
    }
    public OpReportBase(Context ctx)
    {
        super(ctx);
        registerInterface(IOpReportBase.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("B7B3F5BC");
    }
    private OpReportBaseController getController() throws BOSException
    {
        return (OpReportBaseController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public OpReportBaseInfo getOpReportBaseInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getOpReportBaseInfo(getContext(), pk);
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
    public OpReportBaseInfo getOpReportBaseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getOpReportBaseInfo(getContext(), pk, selector);
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
    public OpReportBaseInfo getOpReportBaseInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getOpReportBaseInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public OpReportBaseCollection getOpReportBaseCollection() throws BOSException
    {
        try {
            return getController().getOpReportBaseCollection(getContext());
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
    public OpReportBaseCollection getOpReportBaseCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getOpReportBaseCollection(getContext(), view);
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
    public OpReportBaseCollection getOpReportBaseCollection(String oql) throws BOSException
    {
        try {
            return getController().getOpReportBaseCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}