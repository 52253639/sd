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
import com.kingdee.eas.fdc.basedata.FDCSplitBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCSplitBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import java.util.List;

public class PaymentSplit extends FDCSplitBill implements IPaymentSplit
{
    public PaymentSplit()
    {
        super();
        registerInterface(IPaymentSplit.class, this);
    }
    public PaymentSplit(Context ctx)
    {
        super(ctx);
        registerInterface(IPaymentSplit.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("962DB343");
    }
    private PaymentSplitController getController() throws BOSException
    {
        return (PaymentSplitController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public PaymentSplitInfo getPaymentSplitInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getPaymentSplitInfo(getContext(), pk);
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
    public PaymentSplitInfo getPaymentSplitInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getPaymentSplitInfo(getContext(), pk, selector);
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
    public PaymentSplitInfo getPaymentSplitInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getPaymentSplitInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public PaymentSplitCollection getPaymentSplitCollection() throws BOSException
    {
        try {
            return getController().getPaymentSplitCollection(getContext());
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
    public PaymentSplitCollection getPaymentSplitCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getPaymentSplitCollection(getContext(), view);
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
    public PaymentSplitCollection getPaymentSplitCollection(String oql) throws BOSException
    {
        try {
            return getController().getPaymentSplitCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *凭证前数据处理-User defined method
     *@param idList 集合
     */
    public void traceData(List idList) throws BOSException, EASBizException
    {
        try {
            getController().traceData(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *凭证时数据处理-User defined method
     *@param sourceBillCollection 单据集合
     */
    public void afterVoucher(PaymentSplitCollection sourceBillCollection) throws BOSException, EASBizException
    {
        try {
            getController().afterVoucher(getContext(), sourceBillCollection);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *通过付款单ID同步对应付款拆分的分录会计科目-User defined method
     *@param id 付款单ID
     */
    public void traceSplitByPay(String id) throws BOSException, EASBizException
    {
        try {
            getController().traceSplitByPay(getContext(), id);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}