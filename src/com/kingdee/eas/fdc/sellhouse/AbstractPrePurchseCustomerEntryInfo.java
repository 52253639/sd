package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPrePurchseCustomerEntryInfo extends com.kingdee.eas.fdc.sellhouse.TranCustomerEntryInfo implements Serializable 
{
    public AbstractPrePurchseCustomerEntryInfo()
    {
        this("id");
    }
    protected AbstractPrePurchseCustomerEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 预定客户信息分录 's 预订单 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("43F3B998");
    }
}