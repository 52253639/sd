package com.kingdee.eas.fdc.invite;

import java.io.Serializable;

public class InquiryFileInfo extends AbstractInquiryFileInfo implements Serializable 
{
    public InquiryFileInfo()
    {
        super();
    }
    protected InquiryFileInfo(String pkField)
    {
        super(pkField);
    }
}