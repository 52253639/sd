package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCollectionInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractCollectionInfo()
    {
        this("id");
    }
    protected AbstractCollectionInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 代收费用 's 款项名称 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMoneyName()
    {
        return (com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo)get("moneyName");
    }
    public void setMoneyName(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item)
    {
        put("moneyName", item);
    }
    /**
     * Object: 代收费用 's 项目名称 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object:代收费用's 计算方式property 
     */
    public com.kingdee.eas.fdc.sellhouse.CalculateTypeEnum getCalculateType()
    {
        return com.kingdee.eas.fdc.sellhouse.CalculateTypeEnum.getEnum(getString("calculateType"));
    }
    public void setCalculateType(com.kingdee.eas.fdc.sellhouse.CalculateTypeEnum item)
    {
		if (item != null) {
        setString("calculateType", item.getValue());
		}
    }
    /**
     * Object:代收费用's 保留位property 
     */
    public com.kingdee.eas.fdc.sellhouse.HoldEnum getHold()
    {
        return com.kingdee.eas.fdc.sellhouse.HoldEnum.getEnum(getString("hold"));
    }
    public void setHold(com.kingdee.eas.fdc.sellhouse.HoldEnum item)
    {
		if (item != null) {
        setString("hold", item.getValue());
		}
    }
    /**
     * Object:代收费用's 取整方式property 
     */
    public com.kingdee.eas.fdc.sellhouse.IntegerTypeEnum getIntegerType()
    {
        return com.kingdee.eas.fdc.sellhouse.IntegerTypeEnum.getEnum(getString("integerType"));
    }
    public void setIntegerType(com.kingdee.eas.fdc.sellhouse.IntegerTypeEnum item)
    {
		if (item != null) {
        setString("integerType", item.getValue());
		}
    }
    /**
     * Object:代收费用's 因素property 
     */
    public com.kingdee.eas.fdc.sellhouse.FactorEnum getFactor()
    {
        return com.kingdee.eas.fdc.sellhouse.FactorEnum.getEnum(getString("factor"));
    }
    public void setFactor(com.kingdee.eas.fdc.sellhouse.FactorEnum item)
    {
		if (item != null) {
        setString("factor", item.getValue());
		}
    }
    /**
     * Object:代收费用's 运算符property 
     */
    public com.kingdee.eas.fdc.sellhouse.OperateEnum getOperate()
    {
        return com.kingdee.eas.fdc.sellhouse.OperateEnum.getEnum(getString("operate"));
    }
    public void setOperate(com.kingdee.eas.fdc.sellhouse.OperateEnum item)
    {
		if (item != null) {
        setString("operate", item.getValue());
		}
    }
    /**
     * Object:代收费用's 比较值property 
     */
    public java.math.BigDecimal getComparaValue()
    {
        return getBigDecimal("comparaValue");
    }
    public void setComparaValue(java.math.BigDecimal item)
    {
        setBigDecimal("comparaValue", item);
    }
    /**
     * Object:代收费用's 固定金额property 
     */
    public java.math.BigDecimal getFixedAmount()
    {
        return getBigDecimal("fixedAmount");
    }
    public void setFixedAmount(java.math.BigDecimal item)
    {
        setBigDecimal("fixedAmount", item);
    }
    /**
     * Object:代收费用's 公式说明property 
     */
    public String getFormulaDescription()
    {
        return getString("formulaDescription");
    }
    public void setFormulaDescription(String item)
    {
        setString("formulaDescription", item);
    }
    /**
     * Object:代收费用's 比较符property 
     */
    public com.kingdee.eas.fdc.sellhouse.CompareEnum getCompare()
    {
        return com.kingdee.eas.fdc.sellhouse.CompareEnum.getEnum(getString("compare"));
    }
    public void setCompare(com.kingdee.eas.fdc.sellhouse.CompareEnum item)
    {
		if (item != null) {
        setString("compare", item.getValue());
		}
    }
    /**
     * Object:代收费用's 公式脚本property 
     */
    public String getStdFormulaScript()
    {
        return getString("stdFormulaScript");
    }
    public void setStdFormulaScript(String item)
    {
        setString("stdFormulaScript", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D3F07FB9");
    }
}