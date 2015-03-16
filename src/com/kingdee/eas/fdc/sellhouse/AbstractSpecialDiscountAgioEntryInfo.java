package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSpecialDiscountAgioEntryInfo extends com.kingdee.eas.fdc.sellhouse.AgioEntryInfo implements Serializable 
{
    public AbstractSpecialDiscountAgioEntryInfo()
    {
        this("id");
    }
    protected AbstractSpecialDiscountAgioEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 特殊折扣公共信息分录 's 头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6A8F21E7");
    }
}