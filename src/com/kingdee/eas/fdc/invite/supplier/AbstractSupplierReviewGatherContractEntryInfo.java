package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierReviewGatherContractEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSupplierReviewGatherContractEntryInfo()
    {
        this("id");
    }
    protected AbstractSupplierReviewGatherContractEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 供应商评审汇总评审分录 's 供应商评审 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillInfo getSupplierEvaluation()
    {
        return (com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillInfo)get("supplierEvaluation");
    }
    public void setSupplierEvaluation(com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillInfo item)
    {
        put("supplierEvaluation", item);
    }
    /**
     * Object: 供应商评审汇总评审分录 's 供应商评审汇总 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherInfo getHead()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A4717629");
    }
}