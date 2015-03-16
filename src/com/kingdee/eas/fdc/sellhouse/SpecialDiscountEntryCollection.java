package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SpecialDiscountEntryCollection extends AbstractObjectCollection 
{
    public SpecialDiscountEntryCollection()
    {
        super(SpecialDiscountEntryInfo.class);
    }
    public boolean add(SpecialDiscountEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SpecialDiscountEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SpecialDiscountEntryInfo item)
    {
        return removeObject(item);
    }
    public SpecialDiscountEntryInfo get(int index)
    {
        return(SpecialDiscountEntryInfo)getObject(index);
    }
    public SpecialDiscountEntryInfo get(Object key)
    {
        return(SpecialDiscountEntryInfo)getObject(key);
    }
    public void set(int index, SpecialDiscountEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SpecialDiscountEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SpecialDiscountEntryInfo item)
    {
        return super.indexOf(item);
    }
}