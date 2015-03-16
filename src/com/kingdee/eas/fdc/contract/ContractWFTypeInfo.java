package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

import com.kingdee.eas.fdc.contract.AbstractContractWFTypeInfo;

public class ContractWFTypeInfo extends AbstractContractWFTypeInfo implements Serializable 
{
    public ContractWFTypeInfo()
    {
        super();
    }
    protected ContractWFTypeInfo(String pkField)
    {
        super(pkField);
    }
}