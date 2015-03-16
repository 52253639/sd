package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.fdc.finance.app.*;
import com.kingdee.bos.framework.*;
import java.util.List;
import com.kingdee.eas.fi.cas.PaymentBillCollection;

public class TraceOldSplitVoucherFacade extends AbstractBizCtrl implements ITraceOldSplitVoucherFacade
{
    public TraceOldSplitVoucherFacade()
    {
        super();
        registerInterface(ITraceOldSplitVoucherFacade.class, this);
    }
    public TraceOldSplitVoucherFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ITraceOldSplitVoucherFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("3D68BA7F");
    }
    private TraceOldSplitVoucherFacadeController getController() throws BOSException
    {
        return (TraceOldSplitVoucherFacadeController)getBizController();
    }
    /**
     *待处理合同-User defined method
     *@param contractID 合同id
     */
    public void traceSplitVoucher(String contractID) throws BOSException, EASBizException
    {
        try {
            getController().traceSplitVoucher(getContext(), contractID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *拆分合同下付款单-User defined method
     *@param contractID 合同id
     *@param paymentBill 付款单集合
     *@return
     */
    public IObjectPK[] splitContract(String contractID, PaymentBillCollection paymentBill) throws BOSException, EASBizException
    {
        try {
            return getController().splitContract(getContext(), contractID, paymentBill);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *拆分付款-User defined method
     *@param model 成本付款拆分
     *@return
     */
    public IObjectPK splitPayment(PaymentSplitInfo model) throws BOSException, EASBizException
    {
        try {
            return getController().splitPayment(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *拆分财务类付款 -User defined method
     *@param model 财务类付款拆分
     *@return
     */
    public IObjectPK splitNoCostPayment(PaymentNoCostSplitInfo model) throws BOSException, EASBizException
    {
        try {
            return getController().splitNoCostPayment(getContext(), model);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *处理多个合同-User defined method
     *@param idList 合同id集合
     */
    public void traceContracts(List idList) throws BOSException, EASBizException
    {
        try {
            getController().traceContracts(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *无文本合同Id集合-User defined method
     *@param idList id集合
     */
    public void traceContractNoText(List idList) throws BOSException, EASBizException
    {
        try {
            getController().traceContractNoText(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *合同出调整凭证-User defined method
     *@param idList 合同IDS
     *@param isNoText 是否无文本合同
     *@param voucherAdjustReasonEnum 调整凭证类型
     */
    public void traceAdjustContracts(List idList, boolean isNoText, VoucherAdjustReasonEnum voucherAdjustReasonEnum) throws BOSException, EASBizException
    {
        try {
            getController().traceAdjustContracts(getContext(), idList, isNoText, voucherAdjustReasonEnum);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}