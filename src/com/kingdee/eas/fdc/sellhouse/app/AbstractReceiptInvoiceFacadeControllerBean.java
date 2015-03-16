package com.kingdee.eas.fdc.sellhouse.app;

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

import java.lang.String;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import java.util.Map;
import com.kingdee.bos.util.BOSUuid;
import java.util.List;
import com.kingdee.eas.fdc.sellhouse.RecordTypeEnum;



public abstract class AbstractReceiptInvoiceFacadeControllerBean extends AbstractBizControllerBean implements ReceiptInvoiceFacadeController
{
    protected AbstractReceiptInvoiceFacadeControllerBean()
    {
    }

    protected BOSObjectType getBOSType()
    {
        return new BOSObjectType("4B3B8E2A");
    }

    public IObjectCollection getRecord(Context ctx, BOSUuid id) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("63bb0752-5284-492a-a008-fd4ac96989d0"), new Object[]{ctx, id});
            invokeServiceBefore(svcCtx);
            IObjectCollection retValue = (IObjectCollection)_getRecord(ctx, id);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract IObjectCollection _getRecord(Context ctx, BOSUuid id) throws BOSException;

    public void updateRecord(Context ctx, int billType, String pk, RecordTypeEnum recordType, String content, String description) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("b7cb8a62-b213-4438-844c-e58d5caa8059"), new Object[]{ctx, new Integer(billType), pk, recordType, content, description});
            invokeServiceBefore(svcCtx);
            _updateRecord(ctx, billType, pk, recordType, content, description);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _updateRecord(Context ctx, int billType, String pk, RecordTypeEnum recordType, String content, String description) throws BOSException;

    public void updateReceipt(Context ctx, String pk, String fdcPK, String receiptID, String receiptNum, String oldReceiptNum, String description) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("2cb3f727-88ea-4a52-83fb-18cea888d98e"), new Object[]{ctx, pk, fdcPK, receiptID, receiptNum, oldReceiptNum, description});
            invokeServiceBefore(svcCtx);
            _updateReceipt(ctx, pk, fdcPK, receiptID, receiptNum, oldReceiptNum, description);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _updateReceipt(Context ctx, String pk, String fdcPK, String receiptID, String receiptNum, String oldReceiptNum, String description) throws BOSException;

    public void retackReceipt(Context ctx, String pk, String fdcPK, String receiptNum, String description, Map paraMap) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("d2340261-d3dd-431b-a552-6ab50a2fc798"), new Object[]{ctx, pk, fdcPK, receiptNum, description, paraMap});
            invokeServiceBefore(svcCtx);
            _retackReceipt(ctx, pk, fdcPK, receiptNum, description, paraMap);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _retackReceipt(Context ctx, String pk, String fdcPK, String receiptNum, String description, Map paraMap) throws BOSException;

    public void updateInvoice(Context ctx, String pk, String oldInvoice, String chequePK, String newInvoice, String description) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("c7c75bff-a04a-42ac-847e-36d12aa6880d"), new Object[]{ctx, pk, oldInvoice, chequePK, newInvoice, description});
            invokeServiceBefore(svcCtx);
            _updateInvoice(ctx, pk, oldInvoice, chequePK, newInvoice, description);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _updateInvoice(Context ctx, String pk, String oldInvoice, String chequePK, String newInvoice, String description) throws BOSException;

    public List getChequebyChgID(Context ctx, String chgID) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a674c397-caf1-4947-9fe0-4b65e5e76911"), new Object[]{ctx, chgID});
            invokeServiceBefore(svcCtx);
            List retValue = (List)_getChequebyChgID(ctx, chgID);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract List _getChequebyChgID(Context ctx, String chgID) throws BOSException;

    public void updateChequeForChg(Context ctx, List param) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("a755caea-c3c9-428a-84fb-b9970599827c"), new Object[]{ctx, param});
            invokeServiceBefore(svcCtx);
            _updateChequeForChg(ctx, param);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _updateChequeForChg(Context ctx, List param) throws BOSException;

    public List getChequeForRoom(Context ctx, String sourceID, String sourceType) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("3bfaf8c7-a47c-4fd8-9d5f-caeab5289084"), new Object[]{ctx, sourceID, sourceType});
            invokeServiceBefore(svcCtx);
            List retValue = (List)_getChequeForRoom(ctx, sourceID, sourceType);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract List _getChequeForRoom(Context ctx, String sourceID, String sourceType) throws BOSException;

    public void retakeChequeForRoom(Context ctx, List param) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("081ea7e3-6626-427f-81dc-5b3bfb449d44"), new Object[]{ctx, param});
            invokeServiceBefore(svcCtx);
            _retakeChequeForRoom(ctx, param);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _retakeChequeForRoom(Context ctx, List param) throws BOSException;

    public void retackInvoice(Context ctx, String pk, String number, String description) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("511c50f6-92e6-491c-aa67-0c49d157ba61"), new Object[]{ctx, pk, number, description});
            invokeServiceBefore(svcCtx);
            _retackInvoice(ctx, pk, number, description);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _retackInvoice(Context ctx, String pk, String number, String description) throws BOSException;

    public void clearInvoice(Context ctx, String pk, boolean isClearAll) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0a551064-c199-4074-b3e4-33b863f065c3"), new Object[]{ctx, pk, new Boolean(isClearAll)});
            invokeServiceBefore(svcCtx);
            _clearInvoice(ctx, pk, isClearAll);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _clearInvoice(Context ctx, String pk, boolean isClearAll) throws BOSException;

    public FDCCustomerInfo getCustomerByRoom(Context ctx, String roomID) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("0de47feb-7d33-479d-966e-b941292139b5"), new Object[]{ctx, roomID});
            invokeServiceBefore(svcCtx);
            FDCCustomerInfo retValue = (FDCCustomerInfo)_getCustomerByRoom(ctx, roomID);
            svcCtx.setMethodReturnValue(retValue);
            invokeServiceAfter(svcCtx);
            return retValue;
        } catch (BOSException ex) {
            this.setRollbackOnly();
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract IObjectValue _getCustomerByRoom(Context ctx, String roomID) throws BOSException;

    public void retackReceiptBatch(Context ctx, Map paramMap, String fdcPK, String receiptNum, String description) throws BOSException
    {
        try {
            ServiceContext svcCtx = createServiceContext(new MetaDataPK("09bef0a5-24be-4560-867b-9e0d917a6156"), new Object[]{ctx, paramMap, fdcPK, receiptNum, description});
            invokeServiceBefore(svcCtx);
            _retackReceiptBatch(ctx, paramMap, fdcPK, receiptNum, description);
            invokeServiceAfter(svcCtx);
        } catch (BOSException ex) {
            throw ex;
        } finally {
            super.cleanUpServiceState();
        }
    }
    protected abstract void _retackReceiptBatch(Context ctx, Map paramMap, String fdcPK, String receiptNum, String description) throws BOSException;

}