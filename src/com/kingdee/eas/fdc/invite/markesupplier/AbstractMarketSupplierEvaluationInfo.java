package com.kingdee.eas.fdc.invite.markesupplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierEvaluationInfo extends com.kingdee.eas.fdc.invite.supplier.FDCSplAuditBaseBillInfo implements Serializable 
{
    public AbstractMarketSupplierEvaluationInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierEvaluationInfo(String pkField)
    {
        super(pkField);
        put("contractEntry", new com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationContractEntryCollection());
        put("indexValue", new com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationIndexValueCollection());
        put("auditResult", new com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationAuditResultCollection());
    }
    /**
     * Object:供应商评审's 考察地点property 
     */
    public String getAddress()
    {
        return getString("address");
    }
    public void setAddress(String item)
    {
        setString("address", item);
    }
    /**
     * Object:供应商评审's 参与人员property 
     */
    public String getPerson()
    {
        return getString("person");
    }
    public void setPerson(String item)
    {
        setString("person", item);
    }
    /**
     * Object: 供应商评审 's 考察结论 property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationAuditResultCollection getAuditResult()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationAuditResultCollection)get("auditResult");
    }
    /**
     * Object: 供应商评审 's 分录 property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationIndexValueCollection getIndexValue()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationIndexValueCollection)get("indexValue");
    }
    /**
     * Object: 供应商评审 's 评审类型 property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketAccreditationTypeInfo getEvaluationType()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketAccreditationTypeInfo)get("evaluationType");
    }
    public void setEvaluationType(com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketAccreditationTypeInfo item)
    {
        put("evaluationType", item);
    }
    /**
     * Object: 供应商评审 's 合同分录 property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationContractEntryCollection getContractEntry()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationContractEntryCollection)get("contractEntry");
    }
    /**
     * Object: 供应商评审 's 评审模板 property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateInfo getSupplierTemple()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateInfo)get("supplierTemple");
    }
    public void setSupplierTemple(com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateInfo item)
    {
        put("supplierTemple", item);
    }
    /**
     * Object: 供应商评审 's 供应商编码 property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo getMarketsupplier()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo)get("Marketsupplier");
    }
    public void setMarketsupplier(com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo item)
    {
        put("Marketsupplier", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5B8D6312");
    }
}