package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDefaultCollectionInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractDefaultCollectionInfo()
    {
        this("id");
    }
    protected AbstractDefaultCollectionInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:违约金计算公式设置's 计算方式property 
     */
    public com.kingdee.eas.fdc.sellhouse.CalculateTypedefaultEnum getCalculateType()
    {
        return com.kingdee.eas.fdc.sellhouse.CalculateTypedefaultEnum.getEnum(getString("calculateType"));
    }
    public void setCalculateType(com.kingdee.eas.fdc.sellhouse.CalculateTypedefaultEnum item)
    {
		if (item != null) {
        setString("calculateType", item.getValue());
		}
    }
    /**
     * Object:违约金计算公式设置's 保留位property 
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
     * Object:违约金计算公式设置's 取整方式property 
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
     * Object:违约金计算公式设置's 公式说明property 
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
     * Object:违约金计算公式设置's 公式脚本property 
     */
    public String getStdFormulaScript()
    {
        return getString("stdFormulaScript");
    }
    public void setStdFormulaScript(String item)
    {
        setString("stdFormulaScript", item);
    }
    /**
     * Object:违约金计算公式设置's 比例property 
     */
    public java.math.BigDecimal getScale()
    {
        return getBigDecimal("scale");
    }
    public void setScale(java.math.BigDecimal item)
    {
        setBigDecimal("scale", item);
    }
    /**
     * Object:违约金计算公式设置's 备注property 
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
     * Object: 违约金计算公式设置 's 项目名称 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("project", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("74410E44");
    }
}