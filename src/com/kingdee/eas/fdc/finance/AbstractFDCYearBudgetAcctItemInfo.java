package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCYearBudgetAcctItemInfo extends com.kingdee.eas.fdc.finance.FDCBudgetAcctItemInfo implements Serializable 
{
    public AbstractFDCYearBudgetAcctItemInfo()
    {
        this("id");
    }
    protected AbstractFDCYearBudgetAcctItemInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 项目月度计划项 's 计划项所属分录 property 
     */
    public com.kingdee.eas.fdc.finance.FDCYearBudgetAcctEntryInfo getEntry()
    {
        return (com.kingdee.eas.fdc.finance.FDCYearBudgetAcctEntryInfo)get("entry");
    }
    public void setEntry(com.kingdee.eas.fdc.finance.FDCYearBudgetAcctEntryInfo item)
    {
        put("entry", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("59DD885E");
    }
}