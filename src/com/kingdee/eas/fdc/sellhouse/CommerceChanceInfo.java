package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class CommerceChanceInfo extends AbstractCommerceChanceInfo implements Serializable 
{
    public CommerceChanceInfo()
    {
        super();
    }
    protected CommerceChanceInfo(String pkField)
    {
        super(pkField);
    }
    
    public String toString() {
        if (this.getDisplayFormat() != null)
        {
            return super.toString();
        }
        else
        {
            String name = "";
            name = this.getName();
            return name;
        }
    }
    
    
}