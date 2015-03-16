package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInsteadCollectOutBillEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractInsteadCollectOutBillEntryInfo()
    {
        this("id");
    }
    protected AbstractInsteadCollectOutBillEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.InsteadCollectOutBillInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8DAFA3C0");
    }
}