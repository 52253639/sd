package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCDepMonBudgetAcctItemInfo extends com.kingdee.eas.fdc.finance.FDCBudgetAcctItemInfo implements Serializable 
{
    public AbstractFDCDepMonBudgetAcctItemInfo()
    {
        this("id");
    }
    protected AbstractFDCDepMonBudgetAcctItemInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 部门月度计划申报表月份分录 's 计划项所属分录 property 
     */
    public com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctEntryInfo getEntry()
    {
        return (com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctEntryInfo)get("entry");
    }
    public void setEntry(com.kingdee.eas.fdc.finance.FDCDepMonBudgetAcctEntryInfo item)
    {
        put("entry", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EA5F3F1E");
    }
}