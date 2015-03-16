package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class ShareSellerInfo extends AbstractShareSellerInfo implements Serializable 
{
    public ShareSellerInfo()
    {
        super();
    }
    protected ShareSellerInfo(String pkField)
    {
        super(pkField);
    }
}