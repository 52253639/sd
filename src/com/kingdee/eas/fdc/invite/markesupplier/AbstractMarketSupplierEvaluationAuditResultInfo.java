package com.kingdee.eas.fdc.invite.markesupplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierEvaluationAuditResultInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractMarketSupplierEvaluationAuditResultInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierEvaluationAuditResultInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:考察结论's 是否参与考察property 
     */
    public boolean isIsAudit()
    {
        return getBoolean("isAudit");
    }
    public void setIsAudit(boolean item)
    {
        setBoolean("isAudit", item);
    }
    /**
     * Object:考察结论's 考察前状态property 
     */
    public String getBeforeState()
    {
        return getString("beforeState");
    }
    public void setBeforeState(String item)
    {
        setString("beforeState", item);
    }
    /**
     * Object:考察结论's 考察得分property 
     */
    public java.math.BigDecimal getScore()
    {
        return getBigDecimal("score");
    }
    public void setScore(java.math.BigDecimal item)
    {
        setBigDecimal("score", item);
    }
    /**
     * Object:考察结论's 供应商等级property 
     */
    public String getGrade()
    {
        return getString("grade");
    }
    public void setGrade(String item)
    {
        setString("grade", item);
    }
    /**
     * Object: 考察结论 's 考察单据 property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationInfo getAuditBill()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationInfo)get("auditBill");
    }
    public void setAuditBill(com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationInfo item)
    {
        put("auditBill", item);
    }
    /**
     * Object: 考察结论 's 供应商类型 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeInfo getSupplierType()
    {
        return (com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeInfo)get("supplierType");
    }
    public void setSupplierType(com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeInfo item)
    {
        put("supplierType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9B82C566");
    }
}