package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDepConPayPlanSplitEntryInfo extends com.kingdee.eas.fdc.basedata.FDCSplitBillEntryInfo implements Serializable 
{
    public AbstractDepConPayPlanSplitEntryInfo()
    {
        this("id");
    }
    protected AbstractDepConPayPlanSplitEntryInfo(String pkField)
    {
        super(pkField);
        put("items", new com.kingdee.eas.fdc.finance.DepConPayPlanSplitItemCollection());
    }
    /**
     * Object: 部门合同付款计划拆分分录 's 头 property 
     */
    public com.kingdee.eas.fdc.finance.DepConPayPlanSplitBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.DepConPayPlanSplitBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.DepConPayPlanSplitBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 部门合同付款计划拆分分录 's 条目 property 
     */
    public com.kingdee.eas.fdc.finance.DepConPayPlanSplitItemCollection getItems()
    {
        return (com.kingdee.eas.fdc.finance.DepConPayPlanSplitItemCollection)get("items");
    }
    /**
     * Object: 部门合同付款计划拆分分录 's 对应付款计划分录 property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepConPayPlanEntryInfo getPayPlanEntry()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepConPayPlanEntryInfo)get("payPlanEntry");
    }
    public void setPayPlanEntry(com.kingdee.eas.fdc.finance.FDCDepConPayPlanEntryInfo item)
    {
        put("payPlanEntry", item);
    }
    /**
     * Object:部门合同付款计划拆分分录's 是否是待签订合同property 
     */
    public boolean isIsUnsettledCon()
    {
        return getBoolean("isUnsettledCon");
    }
    public void setIsUnsettledCon(boolean item)
    {
        setBoolean("isUnsettledCon", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("339A63A7");
    }
}