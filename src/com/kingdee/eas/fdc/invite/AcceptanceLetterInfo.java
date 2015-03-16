package com.kingdee.eas.fdc.invite;

import java.io.Serializable;

public class AcceptanceLetterInfo extends AbstractAcceptanceLetterInfo implements Serializable 
{
    public AcceptanceLetterInfo()
    {
        super();
    }
    protected AcceptanceLetterInfo(String pkField)
    {
        super(pkField);
    }
}