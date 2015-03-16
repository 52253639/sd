package com.kingdee.eas.fdc.finance;

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
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class DeductBill extends FDCBill implements IDeductBill
{
    public DeductBill()
    {
        super();
        registerInterface(IDeductBill.class, this);
    }
    public DeductBill(Context ctx)
    {
        super(ctx);
        registerInterface(IDeductBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("953E59B9");
    }
    private DeductBillController getController() throws BOSException
    {
        return (DeductBillController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public DeductBillInfo getDeductBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getDeductBillInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public DeductBillInfo getDeductBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getDeductBillInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public DeductBillInfo getDeductBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getDeductBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public DeductBillCollection getDeductBillCollection() throws BOSException
    {
        try {
            return getController().getDeductBillCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public DeductBillCollection getDeductBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getDeductBillCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public DeductBillCollection getDeductBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getDeductBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *甲供扣款-User defined method
     *@param paymentId 付款单ID
     */
    public void createPartADeductBill(String paymentId) throws BOSException, EASBizException
    {
        try {
            getController().createPartADeductBill(getContext(), paymentId);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}