package com.kingdee.eas.fdc.invite.markesupplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketSupplierReviewGatherEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractMarketSupplierReviewGatherEntryInfo()
    {
        this("id");
    }
    protected AbstractMarketSupplierReviewGatherEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:供应商评审汇总分录's 综合得分property 
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
     * Object: 供应商评审汇总分录 's 供应商评审汇总 property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherInfo getHead()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 供应商评审汇总分录 's 供应商评审分录 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditTemplateInfo getAuditTemplate()
    {
        return (com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditTemplateInfo)get("auditTemplate");
    }
    public void setAuditTemplate(com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditTemplateInfo item)
    {
        put("auditTemplate", item);
    }
    /**
     * Object:供应商评审汇总分录's 评审维度property 
     */
    public String getGuideType()
    {
        return getString("guideType");
    }
    public void setGuideType(String item)
    {
        setString("guideType", item);
    }
    /**
     * Object: 供应商评审汇总分录 's 指标名称 property 
     */
    public com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexInfo getGuideName()
    {
        return (com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexInfo)get("guideName");
    }
    public void setGuideName(com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexInfo item)
    {
        put("guideName", item);
    }
    /**
     * Object:供应商评审汇总分录's 3分标准/标准property 
     */
    public String getFullNum()
    {
        return getString("fullNum");
    }
    public void setFullNum(String item)
    {
        setString("fullNum", item);
    }
    /**
     * Object:供应商评审汇总分录's 权重(%)property 
     */
    public java.math.BigDecimal getWeight()
    {
        return getBigDecimal("weight");
    }
    public void setWeight(java.math.BigDecimal item)
    {
        setBigDecimal("weight", item);
    }
    /**
     * Object:供应商评审汇总分录's 情况描述property 
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
     * Object:供应商评审汇总分录's 合格与否property 
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
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("435B3029");
    }
}