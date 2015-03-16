package com.kingdee.eas.fdc.invite;

import java.io.Serializable;

public class NewListingEntryInfo extends AbstractNewListingEntryInfo implements Serializable 
{
    public NewListingEntryInfo()
    {
        super();
    }
    protected NewListingEntryInfo(String pkField)
    {
        super(pkField);
    }
}