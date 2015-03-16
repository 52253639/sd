package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPolicyManageEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractPolicyManageEntryInfo()
    {
        this("id");
    }
    protected AbstractPolicyManageEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.market.PolicyManageInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.PolicyManageInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.PolicyManageInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5B0D17CA");
    }
}