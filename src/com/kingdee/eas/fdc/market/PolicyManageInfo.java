package com.kingdee.eas.fdc.market;

import java.io.Serializable;

public class PolicyManageInfo extends AbstractPolicyManageInfo implements Serializable 
{
    public PolicyManageInfo()
    {
        super();
    }
    protected PolicyManageInfo(String pkField)
    {
        super(pkField);
    }
}