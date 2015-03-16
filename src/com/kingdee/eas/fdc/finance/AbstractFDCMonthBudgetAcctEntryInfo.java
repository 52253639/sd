package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCMonthBudgetAcctEntryInfo extends com.kingdee.eas.fdc.finance.FDCBudgetAcctEntryInfo implements Serializable 
{
    public AbstractFDCMonthBudgetAcctEntryInfo()
    {
        this("id");
    }
    protected AbstractFDCMonthBudgetAcctEntryInfo(String pkField)
    {
        super(pkField);
        put("items", new com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctItemCollection());
    }
    /**
     * Object: 项目月度计划申报表分录 's 月度预算 property 
     */
    public com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctInfo getParent()
    {
        return (com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 项目月度计划申报表分录 's 计划项 property 
     */
    public com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctItemCollection getItems()
    {
        return (com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctItemCollection)get("items");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("FE98F8D0");
    }
}