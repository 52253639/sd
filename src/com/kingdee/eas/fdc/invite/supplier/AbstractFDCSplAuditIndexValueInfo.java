package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCSplAuditIndexValueInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractFDCSplAuditIndexValueInfo()
    {
        this("id");
    }
    protected AbstractFDCSplAuditIndexValueInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 评审单指标值 's 评审单据 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.FDCSplAuditBaseBillInfo getAuditBill()
    {
        return (com.kingdee.eas.fdc.invite.supplier.FDCSplAuditBaseBillInfo)get("auditBill");
    }
    public void setAuditBill(com.kingdee.eas.fdc.invite.supplier.FDCSplAuditBaseBillInfo item)
    {
        put("auditBill", item);
    }
    /**
     * Object: 评审单指标值 's 评审模版分录 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryInfo getTemplateEntry()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryInfo)get("templateEntry");
    }
    public void setTemplateEntry(com.kingdee.eas.fdc.invite.supplier.SupplierGuideEntryInfo item)
    {
        put("templateEntry", item);
    }
    /**
     * Object:评审单指标值's 评审得分property 
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
     * Object:评审单指标值's 评审人property 
     */
    public String getAuditPerson()
    {
        return getString("auditPerson");
    }
    public void setAuditPerson(String item)
    {
        setString("auditPerson", item);
    }
    /**
     * Object:评审单指标值's 评审部门property 
     */
    public String getAuditDept()
    {
        return getString("auditDept");
    }
    public void setAuditDept(String item)
    {
        setString("auditDept", item);
    }
    /**
     * Object:评审单指标值's 评审时间property 
     */
    public java.util.Date getAuditTime()
    {
        return getDate("auditTime");
    }
    public void setAuditTime(java.util.Date item)
    {
        setDate("auditTime", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A660E158");
    }
}