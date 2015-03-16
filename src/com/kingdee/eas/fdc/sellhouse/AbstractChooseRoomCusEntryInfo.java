package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChooseRoomCusEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractChooseRoomCusEntryInfo()
    {
        this("id");
    }
    protected AbstractChooseRoomCusEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 选房单客户分录 's 客户信息 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECustomerInfo getCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.fdc.sellhouse.SHECustomerInfo item)
    {
        put("customer", item);
    }
    /**
     * Object: 选房单客户分录 's 选房单头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChooseRoomInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChooseRoomInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.ChooseRoomInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BA24967E");
    }
}