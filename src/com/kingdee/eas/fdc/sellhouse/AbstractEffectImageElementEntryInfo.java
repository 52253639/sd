package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractEffectImageElementEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractEffectImageElementEntryInfo()
    {
        this("id");
    }
    protected AbstractEffectImageElementEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:效果图分录's 效果图对应类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.EffectImageElementEnum getType()
    {
        return com.kingdee.eas.fdc.sellhouse.EffectImageElementEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.sellhouse.EffectImageElementEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object:效果图分录's 热点1property 
     */
    public String getHotspot1()
    {
        return getString("hotspot1");
    }
    public void setHotspot1(String item)
    {
        setString("hotspot1", item);
    }
    /**
     * Object:效果图分录's 热点2property 
     */
    public String getHotspot2()
    {
        return getString("hotspot2");
    }
    public void setHotspot2(String item)
    {
        setString("hotspot2", item);
    }
    /**
     * Object: 效果图分录 's 表头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.EffectImageInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.EffectImageInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.EffectImageInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:效果图分录's 元素名称 property 
     */
    public String getElementName()
    {
        return getString("elementName");
    }
    public void setElementName(String item)
    {
        setString("elementName", item);
    }
    /**
     * Object:效果图分录's 元素IDproperty 
     */
    public String getElementID()
    {
        return getString("elementID");
    }
    public void setElementID(String item)
    {
        setString("elementID", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DFF59105");
    }
}