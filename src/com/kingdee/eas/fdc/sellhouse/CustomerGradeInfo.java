package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

public class CustomerGradeInfo extends AbstractCustomerGradeInfo implements Serializable 
{
    public CustomerGradeInfo()
    {
        super();
    }
    protected CustomerGradeInfo(String pkField)
    {
        super(pkField);
    }
}