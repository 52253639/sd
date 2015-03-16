package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCMonthBudgetAcctInfo extends com.kingdee.eas.fdc.finance.FDCBudgetAcctInfo implements Serializable 
{
    public AbstractFDCMonthBudgetAcctInfo()
    {
        this("id");
    }
    protected AbstractFDCMonthBudgetAcctInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryCollection());
    }
    /**
     * Object: 项目月度计划申报表 's 分录 property 
     */
    public com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.finance.FDCMonthBudgetAcctEntryCollection)get("entrys");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("096048C2");
    }
}