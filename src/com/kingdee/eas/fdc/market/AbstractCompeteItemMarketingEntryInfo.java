package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCompeteItemMarketingEntryInfo extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractCompeteItemMarketingEntryInfo()
    {
        this("id");
    }
    protected AbstractCompeteItemMarketingEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 营销活动 's null property 
     */
    public com.kingdee.eas.fdc.market.CompeteItemInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.CompeteItemInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.CompeteItemInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:营销活动's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DF68A3B5");
    }
}