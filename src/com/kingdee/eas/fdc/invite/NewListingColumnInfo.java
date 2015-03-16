package com.kingdee.eas.fdc.invite;

import java.io.Serializable;

public class NewListingColumnInfo extends AbstractNewListingColumnInfo implements Serializable 
{
    public NewListingColumnInfo()
    {
        super();
    }
    protected NewListingColumnInfo(String pkField)
    {
        super(pkField);
    }
}