package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPropertyDoSchemeEntryTwoInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractPropertyDoSchemeEntryTwoInfo()
    {
        this("id");
    }
    protected AbstractPropertyDoSchemeEntryTwoInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 第2个表体 's null property 
     */
    public com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo getParent1()
    {
        return (com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo)get("parent1");
    }
    public void setParent1(com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo item)
    {
        put("parent1", item);
    }
    /**
     * Object:第2个表体's 编码property 
     */
    public String getNumber()
    {
        return getString("number");
    }
    public void setNumber(String item)
    {
        setString("number", item);
    }
    /**
     * Object:第2个表体's 名称property 
     */
    public String getName()
    {
        return getString("name");
    }
    public void setName(String item)
    {
        setString("name", item);
    }
    /**
     * Object:第2个表体's 说明property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5716E07A");
    }
}