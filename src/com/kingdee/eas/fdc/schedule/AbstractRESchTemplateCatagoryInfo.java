package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRESchTemplateCatagoryInfo extends com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo implements Serializable 
{
    public AbstractRESchTemplateCatagoryInfo()
    {
        this("id");
    }
    protected AbstractRESchTemplateCatagoryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:模板分类's 产品类型property 
     */
    public String getSrcProductType()
    {
        return getString("srcProductType");
    }
    public void setSrcProductType(String item)
    {
        setString("srcProductType", item);
    }
    /**
     * Object: 模板分类 's 上级模板分类 property 
     */
    public com.kingdee.eas.fdc.schedule.RESchTemplateCatagoryInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.RESchTemplateCatagoryInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.RESchTemplateCatagoryInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:模板分类's 模板分类类型property 
     */
    public com.kingdee.eas.fdc.schedule.ScheduleTemplateTypeEnum getTemplateType()
    {
        return com.kingdee.eas.fdc.schedule.ScheduleTemplateTypeEnum.getEnum(getString("templateType"));
    }
    public void setTemplateType(com.kingdee.eas.fdc.schedule.ScheduleTemplateTypeEnum item)
    {
		if (item != null) {
        setString("templateType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F425DEA3");
    }
}