package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class PolicyManageEntryInfo extends AbstractPolicyManageEntryInfo implements Serializable 
{
    public PolicyManageEntryInfo()
    {
        super();
    }
    protected PolicyManageEntryInfo(String pkField)
    {
        super(pkField);
    }
}