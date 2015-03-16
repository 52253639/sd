package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCDepMonBudgetAcctEntryInfo extends com.kingdee.eas.fdc.finance.FDCBudgetAcctEntryInfo implements Serializable 
{
    public AbstractFDCDepMonBudgetAcctEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCDepMonBudgetAcctEntryInfo(String pkField)
    {
        super(pkField);
        put("items", new com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctItemCollection());
    }
    /**
     * Object: 部门月度计划申报表分录 's 部门月度预算 property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 部门月度计划申报表分录 's 计划项 property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctItemCollection getItems()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctItemCollection)get("items");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("614DC5C7");
    }
}