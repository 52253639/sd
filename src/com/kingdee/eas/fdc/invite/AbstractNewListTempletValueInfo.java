package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractNewListTempletValueInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractNewListTempletValueInfo()
    {
        this("id");
    }
    protected AbstractNewListTempletValueInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 模板数据 's 属于哪个分录 property 
     */
    public com.kingdee.eas.fdc.invite.NewListTempletEntryInfo getEntry()
    {
        return (com.kingdee.eas.fdc.invite.NewListTempletEntryInfo)get("entry");
    }
    public void setEntry(com.kingdee.eas.fdc.invite.NewListTempletEntryInfo item)
    {
        put("entry", item);
    }
    /**
     * Object: 模板数据 's 属于哪一列 property 
     */
    public com.kingdee.eas.fdc.invite.NewListTempletColumnInfo getColumn()
    {
        return (com.kingdee.eas.fdc.invite.NewListTempletColumnInfo)get("column");
    }
    public void setColumn(com.kingdee.eas.fdc.invite.NewListTempletColumnInfo item)
    {
        put("column", item);
    }
    /**
     * Object:模板数据's 金额property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:模板数据's 文本property 
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
     * Object:模板数据's 日期型值property 
     */
    public java.util.Date getDateValue()
    {
        return getDate("dateValue");
    }
    public void setDateValue(java.util.Date item)
    {
        setDate("dateValue", item);
    }
    /**
     * Object:模板数据's 报价类型property 
     */
    public com.kingdee.eas.fdc.invite.QuotingPriceTypeEnum getType()
    {
        return com.kingdee.eas.fdc.invite.QuotingPriceTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.invite.QuotingPriceTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DCF9BFC4");
    }
}