package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurCustomerEntryInfo extends com.kingdee.eas.fdc.sellhouse.TranCustomerEntryInfo implements Serializable 
{
    public AbstractPurCustomerEntryInfo()
    {
        this("id");
    }
    protected AbstractPurCustomerEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 认购客户信息分录 's 认购单id property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9727BE02");
    }
}