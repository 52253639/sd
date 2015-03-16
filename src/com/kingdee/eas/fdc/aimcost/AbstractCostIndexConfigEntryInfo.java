package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCostIndexConfigEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractCostIndexConfigEntryInfo()
    {
        this("id");
    }
    protected AbstractCostIndexConfigEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 造价指标库配置分录 's 造价指标库配置 property 
     */
    public com.kingdee.eas.fdc.aimcost.CostIndexConfigInfo getHead()
    {
        return (com.kingdee.eas.fdc.aimcost.CostIndexConfigInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.aimcost.CostIndexConfigInfo item)
    {
        put("head", item);
    }
    /**
     * Object:造价指标库配置分录's 字段名称property 
     */
    public String getFieldName()
    {
        return getString("fieldName");
    }
    public void setFieldName(String item)
    {
        setString("fieldName", item);
    }
    /**
     * Object:造价指标库配置分录's 字段类型property 
     */
    public com.kingdee.eas.fdc.aimcost.FieldTypeEnum getFieldType()
    {
        return com.kingdee.eas.fdc.aimcost.FieldTypeEnum.getEnum(getString("fieldType"));
    }
    public void setFieldType(com.kingdee.eas.fdc.aimcost.FieldTypeEnum item)
    {
		if (item != null) {
        setString("fieldType", item.getValue());
		}
    }
    /**
     * Object:造价指标库配置分录's 字段隐藏property 
     */
    public boolean isIsHide()
    {
        return getBoolean("isHide");
    }
    public void setIsHide(boolean item)
    {
        setBoolean("isHide", item);
    }
    /**
     * Object:造价指标库配置分录's 字段必录property 
     */
    public boolean isIsRequired()
    {
        return getBoolean("isRequired");
    }
    public void setIsRequired(boolean item)
    {
        setBoolean("isRequired", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8B0067E2");
    }
}