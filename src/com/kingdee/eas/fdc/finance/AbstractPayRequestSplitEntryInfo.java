package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPayRequestSplitEntryInfo extends com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo implements Serializable 
{
    public AbstractPayRequestSplitEntryInfo()
    {
        this("id");
    }
    protected AbstractPayRequestSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 付款申请拆分分录 's 父结点 property 
     */
    public com.kingdee.eas.fdc.finance.PayRequestSplitInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.PayRequestSplitInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.PayRequestSplitInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B1A8612E");
    }
}