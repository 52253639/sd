package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class ProofOfPaymentInfo extends AbstractProofOfPaymentInfo implements Serializable 
{
    public ProofOfPaymentInfo()
    {
        super();
    }
    protected ProofOfPaymentInfo(String pkField)
    {
        super(pkField);
    }
}