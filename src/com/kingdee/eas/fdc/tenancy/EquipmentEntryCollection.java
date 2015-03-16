package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class EquipmentEntryCollection extends AbstractObjectCollection 
{
    public EquipmentEntryCollection()
    {
        super(EquipmentEntryInfo.class);
    }
    public boolean add(EquipmentEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(EquipmentEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(EquipmentEntryInfo item)
    {
        return removeObject(item);
    }
    public EquipmentEntryInfo get(int index)
    {
        return(EquipmentEntryInfo)getObject(index);
    }
    public EquipmentEntryInfo get(Object key)
    {
        return(EquipmentEntryInfo)getObject(key);
    }
    public void set(int index, EquipmentEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(EquipmentEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(EquipmentEntryInfo item)
    {
        return super.indexOf(item);
    }
}