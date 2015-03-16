package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSinPurSaleMansEntryInfo extends com.kingdee.eas.fdc.sellhouse.TranSaleManEntryInfo implements Serializable 
{
    public AbstractSinPurSaleMansEntryInfo()
    {
        this("id");
    }
    protected AbstractSinPurSaleMansEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 预约排号置业顾问分录 's 预约排号头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo getSinPur()
    {
        return (com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo)get("sinPur");
    }
    public void setSinPur(com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo item)
    {
        put("sinPur", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C6EF0942");
    }
}