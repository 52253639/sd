package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCustomerOriginInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractCustomerOriginInfo()
    {
        this("id");
    }
    protected AbstractCustomerOriginInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 客户来源 's 客户来源分组 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CustomerOriginGroupInfo getGroup()
    {
        return (com.kingdee.eas.fdc.sellhouse.CustomerOriginGroupInfo)get("group");
    }
    public void setGroup(com.kingdee.eas.fdc.sellhouse.CustomerOriginGroupInfo item)
    {
        put("group", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BD1B083F");
    }
}