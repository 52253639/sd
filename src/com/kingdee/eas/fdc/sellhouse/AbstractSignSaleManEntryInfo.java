package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSignSaleManEntryInfo extends com.kingdee.eas.fdc.sellhouse.TranSaleManEntryInfo implements Serializable 
{
    public AbstractSignSaleManEntryInfo()
    {
        this("id");
    }
    protected AbstractSignSaleManEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 签约置业顾问分录 's 签约管理 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SignManageInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.SignManageInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.SignManageInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9FD877D7");
    }
}