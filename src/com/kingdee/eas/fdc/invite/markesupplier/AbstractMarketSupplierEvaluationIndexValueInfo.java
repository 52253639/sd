package com.kingdee.eas.fdc.invite.markesupplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierEvaluationIndexValueInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMarketSupplierEvaluationIndexValueInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierEvaluationIndexValueInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:分录's 评分property 
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
     * Object: 分录 's 评审人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getAuditPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("auditPerson");
    }
    public void setAuditPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("auditPerson", item);
    }
    /**
     * Object: 分录 's 评审部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getAuditDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("auditDept");
    }
    public void setAuditDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("auditDept", item);
    }
    /**
     * Object:分录's 评审时间property 
     */
    public java.util.Date getAuditTime()
    {
        return getDate("auditTime");
    }
    public void setAuditTime(java.util.Date item)
    {
        setDate("auditTime", item);
    }
    /**
     * Object: 分录 's 主实体 property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierEvaluationInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:分录's 合格与否property 
     */
    public com.kingdee.eas.fdc.invite.supplier.IsGradeEnum getIsPass()
    {
        return com.kingdee.eas.fdc.invite.supplier.IsGradeEnum.getEnum(getInt("isPass"));
    }
    public void setIsPass(com.kingdee.eas.fdc.invite.supplier.IsGradeEnum item)
    {
		if (item != null) {
        setInt("isPass", item.getValue());
		}
    }
    /**
     * Object:分录's 情况描述property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:分录's 评审维度property 
     */
    public String getMguideType()
    {
        return getString("MguideType");
    }
    public void setMguideType(String item)
    {
        setString("MguideType", item);
    }
    /**
     * Object: 分录 's 指标名称 property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexInfo getMguideName()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexInfo)get("MguideName");
    }
    public void setMguideName(com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexInfo item)
    {
        put("MguideName", item);
    }
    /**
     * Object:分录's 3分标准/标准property 
     */
    public String getThreeBz()
    {
        return getString("threeBz");
    }
    public void setThreeBz(String item)
    {
        setString("threeBz", item);
    }
    /**
     * Object:分录's 权重(%)property 
     */
    public java.math.BigDecimal getMweight()
    {
        return getBigDecimal("Mweight");
    }
    public void setMweight(java.math.BigDecimal item)
    {
        setBigDecimal("Mweight", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9B553C71");
    }
}