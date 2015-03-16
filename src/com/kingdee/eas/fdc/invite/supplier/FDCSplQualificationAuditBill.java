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

public class FDCSplQualificationAuditBill extends FDCSplAuditBaseBill implements IFDCSplQualificationAuditBill
{
    public FDCSplQualificationAuditBill()
    {
        super();
        registerInterface(IFDCSplQualificationAuditBill.class, this);
    }
    public FDCSplQualificationAuditBill(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCSplQualificationAuditBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("36BC9435");
    }
    private FDCSplQualificationAuditBillController getController() throws BOSException
    {
        return (FDCSplQualificationAuditBillController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCSplQualificationAuditBillInfo getFDCSplQualificationAuditBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplQualificationAuditBillInfo(getContext(), pk);
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
    public FDCSplQualificationAuditBillInfo getFDCSplQualificationAuditBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplQualificationAuditBillInfo(getContext(), pk, selector);
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
    public FDCSplQualificationAuditBillInfo getFDCSplQualificationAuditBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplQualificationAuditBillInfo(getContext(), oql);
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
    public FDCSplQualificationAuditBillCollection getFDCSplQualificationAuditBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCSplQualificationAuditBillCollection(getContext(), oql);
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
    public FDCSplQualificationAuditBillCollection getFDCSplQualificationAuditBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCSplQualificationAuditBillCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCSplQualificationAuditBillCollection getFDCSplQualificationAuditBillCollection() throws BOSException
    {
        try {
            return getController().getFDCSplQualificationAuditBillCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}