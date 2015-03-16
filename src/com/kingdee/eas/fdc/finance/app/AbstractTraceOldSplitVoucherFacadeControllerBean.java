package com.kingdee.eas.fdc.finance.app;

import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;
import com.kingdee.eas.framework.Result;
import com.kingdee.eas.framework.LineResult;
import com.kingdee.eas.framework.exception.EASMultiException;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;

import com.kingdee.eas.fdc.finance.VoucherAdjustReasonEnum;
import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitInfo;
import com.kingdee.bos.dao.IObjectPK;
import java.util.List;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;



public abstract class AbstractTraceOldSplitVoucherFacadeControllerBean extends AbstractBizControllerBean implements TraceOldSplitVoucherFacadeController
{
    protected AbstractTraceOldSplitVoucherFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("3D68BA7F");
    }

    public void traceSplitVoucher(Context ctx, String contractID) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("84f6aa49-0116-1000-e000-0073c0a812cd"), new Object[]{ctx, contractID});
            invokeServiceBefore(svcCtx);
            _traceSplitVoucher(ctx, contractID);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _traceSplitVoucher(Context ctx, String contractID) throws BOSException, EASBizException;

    public IObjectPK[] splitContract(Context ctx, String contractID, PaymentBillCollection paymentBill) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("84f6aa49-0116-1000-e000-0075c0a812cd"), new Object[]{ctx, contractID, paymentBill});
            invokeServiceBefore(svcCtx);
            IObjectPK[] retValue = (IObjectPK[])_splitContract(ctx, contractID, paymentBill);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract IObjectPK[] _splitContract(Context ctx, String contractID, IObjectCollection paymentBill) throws BOSException, EASBizException;

    public IObjectPK splitPayment(Context ctx, PaymentSplitInfo model) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("84f6aa49-0116-1000-e000-0076c0a812cd"), new Object[]{ctx, model});
            invokeServiceBefore(svcCtx);
            IObjectPK retValue = (IObjectPK)_splitPayment(ctx, model);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract IObjectPK _splitPayment(Context ctx, IObjectValue model) throws BOSException, EASBizException;

    public IObjectPK splitNoCostPayment(Context ctx, PaymentNoCostSplitInfo model) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("84f6aa49-0116-1000-e000-007bc0a812cd"), new Object[]{ctx, model});
            invokeServiceBefore(svcCtx);
            IObjectPK retValue = (IObjectPK)_splitNoCostPayment(ctx, model);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract IObjectPK _splitNoCostPayment(Context ctx, IObjectValue model) throws BOSException, EASBizException;

    public void traceContracts(Context ctx, List idList) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("9fce6515-0116-1000-e000-0074c0a812cd"), new Object[]{ctx, idList});
            invokeServiceBefore(svcCtx);
            _traceContracts(ctx, idList);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _traceContracts(Context ctx, List idList) throws BOSException, EASBizException;

    public void traceContractNoText(Context ctx, List idList) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("af87d44d-0117-1000-e000-0028c0a812cd"), new Object[]{ctx, idList});
            invokeServiceBefore(svcCtx);
            _traceContractNoText(ctx, idList);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _traceContractNoText(Context ctx, List idList) throws BOSException, EASBizException;

    public void traceAdjustContracts(Context ctx, List idList, boolean isNoText, VoucherAdjustReasonEnum voucherAdjustReasonEnum) throws BOSException, EASBizException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("106d3fe2-011e-1000-e000-014e7f000001"), new Object[]{ctx, idList, new Boolean(isNoText), voucherAdjustReasonEnum});
            invokeServiceBefore(svcCtx);
            _traceAdjustContracts(ctx, idList, isNoText, voucherAdjustReasonEnum);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } catch (EASBizException ex0) {
            this.setRollbackOnly();
            throw ex0;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _traceAdjustContracts(Context ctx, List idList, boolean isNoText, VoucherAdjustReasonEnum voucherAdjustReasonEnum) throws BOSException, EASBizException;

}