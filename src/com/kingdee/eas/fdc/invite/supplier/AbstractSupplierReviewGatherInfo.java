package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierReviewGatherInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractSupplierReviewGatherInfo()
    {
        this("id");
    }
    protected AbstractSupplierReviewGatherInfo(String pkField)
    {
        super(pkField);
        put("contractEntry", new com.kingdee.eas.fdc.invite.supplier.SupplierRGContractEntryCollection());
        put("evaluationEntry", new com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherContractEntryCollection());
        put("entry", new com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherEntryCollection());
    }
    /**
     * Object: 供应商评审汇总 's 评审类型 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationTypeInfo getEvaluationType()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationTypeInfo)get("evaluationType");
    }
    public void setEvaluationType(com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationTypeInfo item)
    {
        put("evaluationType", item);
    }
    /**
     * Object: 供应商评审汇总 's 供应商评审汇总分录 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherEntryCollection)get("entry");
    }
    /**
     * Object: 供应商评审汇总 's 供应商 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo getSupplier()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object: 供应商评审汇总 's 等级 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo getGrade()
    {
        return (com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo)get("grade");
    }
    public void setGrade(com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo item)
    {
        put("grade", item);
    }
    /**
     * Object: 供应商评审汇总 's 评审前等级 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo getSrcGrade()
    {
        return (com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo)get("srcGrade");
    }
    public void setSrcGrade(com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo item)
    {
        put("srcGrade", item);
    }
    /**
     * Object:供应商评审汇总's 是否合格property 
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
     * Object:供应商评审汇总's 评审前是否合格property 
     */
    public com.kingdee.eas.fdc.invite.supplier.IsGradeEnum getSrcIsPass()
    {
        return com.kingdee.eas.fdc.invite.supplier.IsGradeEnum.getEnum(getInt("srcIsPass"));
    }
    public void setSrcIsPass(com.kingdee.eas.fdc.invite.supplier.IsGradeEnum item)
    {
		if (item != null) {
        setInt("srcIsPass", item.getValue());
		}
    }
    /**
     * Object: 供应商评审汇总 's 评审模板 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateInfo getTemplate()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateInfo)get("template");
    }
    public void setTemplate(com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateInfo item)
    {
        put("template", item);
    }
    /**
     * Object: 供应商评审汇总 's 评审分录 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherContractEntryCollection getEvaluationEntry()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherContractEntryCollection)get("evaluationEntry");
    }
    /**
     * Object: 供应商评审汇总 's 评审前级别 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.LevelSetUpInfo getSrcLevel()
    {
        return (com.kingdee.eas.fdc.invite.supplier.LevelSetUpInfo)get("srcLevel");
    }
    public void setSrcLevel(com.kingdee.eas.fdc.invite.supplier.LevelSetUpInfo item)
    {
        put("srcLevel", item);
    }
    /**
     * Object: 供应商评审汇总 's 级别 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.LevelSetUpInfo getLevel()
    {
        return (com.kingdee.eas.fdc.invite.supplier.LevelSetUpInfo)get("level");
    }
    public void setLevel(com.kingdee.eas.fdc.invite.supplier.LevelSetUpInfo item)
    {
        put("level", item);
    }
    /**
     * Object:供应商评审汇总's 是否终止property 
     */
    public boolean isIsOver()
    {
        return getBoolean("isOver");
    }
    public void setIsOver(boolean item)
    {
        setBoolean("isOver", item);
    }
    /**
     * Object:供应商评审汇总's 履约过程评估得分property 
     */
    public java.math.BigDecimal getLygcScore()
    {
        return getBigDecimal("lygcScore");
    }
    public void setLygcScore(java.math.BigDecimal item)
    {
        setBigDecimal("lygcScore", item);
    }
    /**
     * Object:供应商评审汇总's 履约过程评估所占比重（%）property 
     */
    public java.math.BigDecimal getLygcRate()
    {
        return getBigDecimal("lygcRate");
    }
    public void setLygcRate(java.math.BigDecimal item)
    {
        setBigDecimal("lygcRate", item);
    }
    /**
     * Object:供应商评审汇总's 履约后评估得分property 
     */
    public java.math.BigDecimal getLyhScroe()
    {
        return getBigDecimal("lyhScroe");
    }
    public void setLyhScroe(java.math.BigDecimal item)
    {
        setBigDecimal("lyhScroe", item);
    }
    /**
     * Object:供应商评审汇总's 履约后评估所占比重（%）property 
     */
    public java.math.BigDecimal getLyhRate()
    {
        return getBigDecimal("lyhRate");
    }
    public void setLyhRate(java.math.BigDecimal item)
    {
        setBigDecimal("lyhRate", item);
    }
    /**
     * Object: 供应商评审汇总 's 合同分录 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierRGContractEntryCollection getContractEntry()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierRGContractEntryCollection)get("contractEntry");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BEAF2997");
    }
}