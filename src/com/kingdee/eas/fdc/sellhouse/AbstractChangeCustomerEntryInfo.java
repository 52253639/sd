package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChangeCustomerEntryInfo extends com.kingdee.eas.fdc.sellhouse.TranCustomerEntryInfo implements Serializable 
{
    public AbstractChangeCustomerEntryInfo()
    {
        this("id");
    }
    protected AbstractChangeCustomerEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 变更客户分录 's 变更单ID property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeManageInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.ChangeManageInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.ChangeManageInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E202AAA9");
    }
}