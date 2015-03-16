package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.invite.supplier.app.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public class FDCSplPeriodAuditBill extends FDCSplAuditBaseBill implements IFDCSplPeriodAuditBill
{
    public FDCSplPeriodAuditBill()
    {
        super();
        registerInterface(IFDCSplPeriodAuditBill.class, this);
    }
    public FDCSplPeriodAuditBill(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCSplPeriodAuditBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3BD7751F");
    }
    private FDCSplPeriodAuditBillController getController() throws BOSException
    {
        return (FDCSplPeriodAuditBillController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCSplPeriodAuditBillInfo getFDCSplPeriodAuditBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplPeriodAuditBillInfo(getContext(), pk);
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
    public FDCSplPeriodAuditBillInfo getFDCSplPeriodAuditBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplPeriodAuditBillInfo(getContext(), pk, selector);
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
    public FDCSplPeriodAuditBillInfo getFDCSplPeriodAuditBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplPeriodAuditBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCSplPeriodAuditBillCollection getFDCSplPeriodAuditBillCollection() throws BOSException
    {
        try {
            return getController().getFDCSplPeriodAuditBillCollection(getContext());
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
    public FDCSplPeriodAuditBillCollection getFDCSplPeriodAuditBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCSplPeriodAuditBillCollection(getContext(), view);
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
    public FDCSplPeriodAuditBillCollection getFDCSplPeriodAuditBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCSplPeriodAuditBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}