package com.kingdee.eas.fdc.finance;

import java.io.Serializable;

public abstract class FDCBudgetAcctItemInfo extends AbstractFDCBudgetAcctItemInfo implements Serializable 
{
    public FDCBudgetAcctItemInfo()
    {
        super();
    }
    protected FDCBudgetAcctItemInfo(String pkField)
    {
        super(pkField);
    }
}