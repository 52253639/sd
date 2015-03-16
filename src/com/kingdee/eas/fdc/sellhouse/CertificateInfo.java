package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class CertificateInfo extends AbstractCertificateInfo implements Serializable 
{
    public CertificateInfo()
    {
        super();
    }
    protected CertificateInfo(String pkField)
    {
        super(pkField);
    }
}