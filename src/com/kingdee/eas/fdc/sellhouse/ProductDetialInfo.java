package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class ProductDetialInfo extends AbstractProductDetialInfo implements Serializable 
{
    public ProductDetialInfo()
    {
        super();
    }
    protected ProductDetialInfo(String pkField)
    {
        super(pkField);
    }
}