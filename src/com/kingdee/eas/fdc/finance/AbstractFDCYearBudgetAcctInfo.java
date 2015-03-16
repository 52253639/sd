package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCYearBudgetAcctInfo extends com.kingdee.eas.fdc.finance.FDCBudgetAcctInfo implements Serializable 
{
    public AbstractFDCYearBudgetAcctInfo()
    {
        this("id");
    }
    protected AbstractFDCYearBudgetAcctInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.finance.FDCYearBudgetAcctEntryCollection());
    }
    /**
     * Object: 项目年度预算申报表 's 分录 property 
     */
    public com.kingdee.eas.fdc.finance.FDCYearBudgetAcctEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.finance.FDCYearBudgetAcctEntryCollection)get("entrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("38B3E6AB");
    }
}