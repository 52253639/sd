package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractChangeBillInfo extends AbstractContractChangeBillInfo implements Serializable 
{
    public ContractChangeBillInfo()
    {
        super();
    }
    protected ContractChangeBillInfo(String pkField)
    {
        super(pkField);
    }
}