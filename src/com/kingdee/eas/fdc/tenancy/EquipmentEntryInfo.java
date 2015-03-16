package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class EquipmentEntryInfo extends AbstractEquipmentEntryInfo implements Serializable 
{
    public EquipmentEntryInfo()
    {
        super();
    }
    protected EquipmentEntryInfo(String pkField)
    {
        super(pkField);
    }
}