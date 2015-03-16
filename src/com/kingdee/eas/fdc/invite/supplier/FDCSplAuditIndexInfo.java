package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;

public class FDCSplAuditIndexInfo extends AbstractFDCSplAuditIndexInfo implements Serializable 
{
    public FDCSplAuditIndexInfo()
    {
        super();
    }
    protected FDCSplAuditIndexInfo(String pkField)
    {
        super(pkField);
    }
}