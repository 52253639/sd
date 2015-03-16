package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractConPayPlanSplitEntryInfo extends com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo implements Serializable 
{
    public AbstractConPayPlanSplitEntryInfo()
    {
        this("id");
    }
    protected AbstractConPayPlanSplitEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 合同付款计划分录 's 父结点 property 
     */
    public com.kingdee.eas.fdc.finance.ConPayPlanSplitInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.ConPayPlanSplitInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.ConPayPlanSplitInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3B6A7676");
    }
}