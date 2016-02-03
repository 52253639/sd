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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public class CommissionApply extends FDCBill implements ICommissionApply
{
    public CommissionApply()
    {
        super();
        registerInterface(ICommissionApply.class, this);
    }
    public CommissionApply(Context ctx)
    {
        super(ctx);
        registerInterface(ICommissionApply.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("616C187A");
    }
    private CommissionApplyController getController() throws BOSException
    {
        return (CommissionApplyController)getBizController();
    }
    /**
     *getValue-System defined method
     *@param pk pk
     *@return
     */
    public CommissionApplyInfo getCommissionApplyInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCommissionApplyInfo(getContext(), pk);
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
    public CommissionApplyInfo getCommissionApplyInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCommissionApplyInfo(getContext(), pk, selector);
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
    public CommissionApplyInfo getCommissionApplyInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCommissionApplyInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *getCollection-System defined method
     *@return
     */
    public CommissionApplyCollection getCommissionApplyCollection() throws BOSException
    {
        try {
            return getController().getCommissionApplyCollection(getContext());
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
    public CommissionApplyCollection getCommissionApplyCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCommissionApplyCollection(getContext(), view);
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
    public CommissionApplyCollection getCommissionApplyCollection(String oql) throws BOSException
    {
        try {
            return getController().getCommissionApplyCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *Ö§¸¶-User defined method
     *@param id id
     */
    public void pay(BOSUuid id) throws BOSException, EASBizException
    {
        try {
            getController().pay(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}