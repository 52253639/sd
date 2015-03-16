package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChargeTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractChargeTypeInfo()
    {
        this("id");
    }
    protected AbstractChargeTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 费用类别 's 父节点 property 
     */
    public com.kingdee.eas.fdc.market.ChargeTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.ChargeTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.ChargeTypeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("AAF3C09F");
    }
}