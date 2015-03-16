package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SpecialDiscountAgioEntryCollection extends AbstractObjectCollection 
{
    public SpecialDiscountAgioEntryCollection()
    {
        super(SpecialDiscountAgioEntryInfo.class);
    }
    public boolean add(SpecialDiscountAgioEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SpecialDiscountAgioEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SpecialDiscountAgioEntryInfo item)
    {
        return removeObject(item);
    }
    public SpecialDiscountAgioEntryInfo get(int index)
    {
        return(SpecialDiscountAgioEntryInfo)getObject(index);
    }
    public SpecialDiscountAgioEntryInfo get(Object key)
    {
        return(SpecialDiscountAgioEntryInfo)getObject(key);
    }
    public void set(int index, SpecialDiscountAgioEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SpecialDiscountAgioEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SpecialDiscountAgioEntryInfo item)
    {
        return super.indexOf(item);
    }
}