package com.kingdee.eas.fdc.schedule.framework;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractScheduleTaskPropertyInfo extends com.kingdee.eas.fdc.schedule.framework.SchedulePropBaseInfo implements Serializable 
{
    public AbstractScheduleTaskPropertyInfo()
    {
        this("id");
    }
    protected AbstractScheduleTaskPropertyInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:任务属性's 类型property 
     */
    public com.kingdee.eas.fdc.schedule.framework.TaskPropertyTypeEnum getType()
    {
        return com.kingdee.eas.fdc.schedule.framework.TaskPropertyTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.schedule.framework.TaskPropertyTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object:任务属性's 值类型property 
     */
    public String getValueType()
    {
        return getString("valueType");
    }
    public void setValueType(String item)
    {
        setString("valueType", item);
    }
    /**
     * Object:任务属性's 属性IDproperty 
     */
    public String getPropertyId()
    {
        return getString("propertyId");
    }
    public void setPropertyId(String item)
    {
        setString("propertyId", item);
    }
    /**
     * Object:任务属性's 属性键property 
     */
    public String getKey()
    {
        return getString("key");
    }
    public void setKey(String item)
    {
        setString("key", item);
    }
    /**
     * Object:任务属性's 映射键property 
     */
    public String getMapKey()
    {
        return getString("mapKey");
    }
    public void setMapKey(String item)
    {
        setString("mapKey", item);
    }
    /**
     * Object:任务属性's 值属性property 
     */
    public String getValueAtt()
    {
        return getString("valueAtt");
    }
    public void setValueAtt(String item)
    {
        setString("valueAtt", item);
    }
    /**
     * Object:任务属性's 备注property 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    /**
     * Object:任务属性's 包含的显示界面property 
     */
    public int getDisplayUI()
    {
        return getInt("displayUI");
    }
    public void setDisplayUI(int item)
    {
        setInt("displayUI", item);
    }
    /**
     * Object:任务属性's 包含的隐藏界面property 
     */
    public int getHideUI()
    {
        return getInt("hideUI");
    }
    public void setHideUI(int item)
    {
        setInt("hideUI", item);
    }
    /**
     * Object:任务属性's 默认列宽property 
     */
    public int getWidth()
    {
        return getInt("width");
    }
    public void setWidth(int item)
    {
        setInt("width", item);
    }
    /**
     * Object:任务属性's 对齐property 
     */
    public int getAlign()
    {
        return getInt("align");
    }
    public void setAlign(int item)
    {
        setInt("align", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6CD1CA37");
    }
}