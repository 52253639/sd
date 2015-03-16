package com.kingdee.eas.fdc.invite.supplier;

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
import com.kingdee.eas.fdc.invite.supplier.app.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public abstract class FDCSplAuditBaseBill extends FDCBill implements IFDCSplAuditBaseBill
{
    public FDCSplAuditBaseBill()
    {
        super();
        registerInterface(IFDCSplAuditBaseBill.class, this);
    }
    public FDCSplAuditBaseBill(Context ctx)
    {
        super(ctx);
        registerInterface(IFDCSplAuditBaseBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3F2E1651");
    }
    private FDCSplAuditBaseBillController getController() throws BOSException
    {
        return (FDCSplAuditBaseBillController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public FDCSplAuditBaseBillInfo getFDCSplAuditBaseBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplAuditBaseBillInfo(getContext(), pk);
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
    public FDCSplAuditBaseBillInfo getFDCSplAuditBaseBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplAuditBaseBillInfo(getContext(), pk, selector);
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
    public FDCSplAuditBaseBillInfo getFDCSplAuditBaseBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getFDCSplAuditBaseBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public FDCSplAuditBaseBillCollection getFDCSplAuditBaseBillCollection() throws BOSException
    {
        try {
            return getController().getFDCSplAuditBaseBillCollection(getContext());
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
    public FDCSplAuditBaseBillCollection getFDCSplAuditBaseBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getFDCSplAuditBaseBillCollection(getContext(), view);
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
    public FDCSplAuditBaseBillCollection getFDCSplAuditBaseBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getFDCSplAuditBaseBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}