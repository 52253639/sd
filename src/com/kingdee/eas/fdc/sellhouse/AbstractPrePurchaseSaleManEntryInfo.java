package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPrePurchaseSaleManEntryInfo extends com.kingdee.eas.fdc.sellhouse.TranSaleManEntryInfo implements Serializable 
{
    public AbstractPrePurchaseSaleManEntryInfo()
    {
        this("id");
    }
    protected AbstractPrePurchaseSaleManEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 预定置业顾问分录 's 预定管理 property 
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
        return new BOSObjectType("38D7D248");
    }
}