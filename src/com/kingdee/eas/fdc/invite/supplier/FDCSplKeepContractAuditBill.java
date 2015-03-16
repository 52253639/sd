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

public class FDCSplKeepContractAuditBill extends FDCSplAuditBaseBill implements IFDCSplKeepContractAuditBill
{
    public FDCSplKeepContractAuditBill()
    {
        super();
        registerInterface(IFDCSplKeepContractAuditBill.class, this);
    }
    public FDCSplKeepContractAuditBill(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCSplKeepContractAuditBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("FC287569");
    }
    private FDCSplKeepContractAuditBillController getController() throws BOSException
    {
        return (FDCSplKeepContractAuditBillController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param oql oql
     *@return
     */
    public FDCSplKeepContractAuditBillInfo getFDCSplKeepContractAuditBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplKeepContractAuditBillInfo(getContext(), oql);
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
    public FDCSplKeepContractAuditBillInfo getFDCSplKeepContractAuditBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplKeepContractAuditBillInfo(getContext(), pk, selector);
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
    public FDCSplKeepContractAuditBillInfo getFDCSplKeepContractAuditBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplKeepContractAuditBillInfo(getContext(), pk);
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
    public FDCSplKeepContractAuditBillCollection getFDCSplKeepContractAuditBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCSplKeepContractAuditBillCollection(getContext(), oql);
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
    public FDCSplKeepContractAuditBillCollection getFDCSplKeepContractAuditBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCSplKeepContractAuditBillCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCSplKeepContractAuditBillCollection getFDCSplKeepContractAuditBillCollection() throws BOSException
    {
        try {
            return getController().getFDCSplKeepContractAuditBillCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}