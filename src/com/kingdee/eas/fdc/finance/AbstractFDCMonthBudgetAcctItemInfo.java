package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCMonthBudgetAcctItemInfo extends com.kingdee.eas.fdc.finance.FDCBudgetAcctItemInfo implements Serializable 
{
    public AbstractFDCMonthBudgetAcctItemInfo()
    {
        this("id");
    }
    protected AbstractFDCMonthBudgetAcctItemInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 项目月度计划项 's 计划项所属分录 property 
     */
    public com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryInfo getEntry()
    {
        return (com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryInfo)get("entry");
    }
    public void setEntry(com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryInfo item)
    {
        put("entry", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5ACD06F5");
    }
}