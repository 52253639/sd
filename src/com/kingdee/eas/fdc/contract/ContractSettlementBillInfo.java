package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractSettlementBillInfo extends AbstractContractSettlementBillInfo implements Serializable 
{
    public ContractSettlementBillInfo()
    {
        super();
    }
    protected ContractSettlementBillInfo(String pkField)
    {
        super(pkField);
    }
}