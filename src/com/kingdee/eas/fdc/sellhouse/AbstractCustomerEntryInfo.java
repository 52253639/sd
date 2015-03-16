package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCustomerEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractCustomerEntryInfo()
    {
        this("id");
    }
    protected AbstractCustomerEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 客户分录 's head property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 客户分录 's customer property 
     */
    public com.kingdee.eas.basedata.master.cssp.CustomerInfo getCustomer()
    {
        return (com.kingdee.eas.basedata.master.cssp.CustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.basedata.master.cssp.CustomerInfo item)
    {
        put("customer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4FDDA839");
    }
}