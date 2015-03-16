package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;

public abstract class FDCSplAuditBaseBillInfo extends AbstractFDCSplAuditBaseBillInfo implements Serializable 
{
    public FDCSplAuditBaseBillInfo()
    {
        super();
    }
    protected FDCSplAuditBaseBillInfo(String pkField)
    {
        super(pkField);
    }
}