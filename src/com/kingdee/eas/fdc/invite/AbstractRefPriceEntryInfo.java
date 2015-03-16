package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRefPriceEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractRefPriceEntryInfo()
    {
        this("id");
    }
    protected AbstractRefPriceEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 参考价分录 's 头 property 
     */
    public com.kingdee.eas.fdc.invite.RefPriceInfo getHead()
    {
        return (com.kingdee.eas.fdc.invite.RefPriceInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.invite.RefPriceInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 参考价分录 's 列 property 
     */
    public com.kingdee.eas.fdc.invite.HeadColumnInfo getColumn()
    {
        return (com.kingdee.eas.fdc.invite.HeadColumnInfo)get("column");
    }
    public void setColumn(com.kingdee.eas.fdc.invite.HeadColumnInfo item)
    {
        put("column", item);
    }
    /**
     * Object:参考价分录's 值property 
     */
    public java.math.BigDecimal getValue()
    {
        return getBigDecimal("value");
    }
    public void setValue(java.math.BigDecimal item)
    {
        setBigDecimal("value", item);
    }
    /**
     * Object:参考价分录's 字符property 
     */
    public String getText()
    {
        return getString("text");
    }
    public void setText(String item)
    {
        setString("text", item);
    }
    /**
     * Object:参考价分录's 日期型值property 
     */
    public java.util.Date getDateValue()
    {
        return getDate("dateValue");
    }
    public void setDateValue(java.util.Date item)
    {
        setDate("dateValue", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8B68BCF8");
    }
}