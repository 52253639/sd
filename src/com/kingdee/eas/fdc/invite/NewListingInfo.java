package com.kingdee.eas.fdc.invite;

import java.io.Serializable;

public class NewListingInfo extends AbstractNewListingInfo implements Serializable 
{
    public NewListingInfo()
    {
        super();
    }
    protected NewListingInfo(String pkField)
    {
        super(pkField);
    }
}