package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class LoanDataEntryInfo extends AbstractLoanDataEntryInfo implements Serializable 
{
    public LoanDataEntryInfo()
    {
        super();
    }
    protected LoanDataEntryInfo(String pkField)
    {
        super(pkField);
    }
}