package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SpecialDiscountCollection extends AbstractObjectCollection 
{
    public SpecialDiscountCollection()
    {
        super(SpecialDiscountInfo.class);
    }
    public boolean add(SpecialDiscountInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SpecialDiscountCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SpecialDiscountInfo item)
    {
        return removeObject(item);
    }
    public SpecialDiscountInfo get(int index)
    {
        return(SpecialDiscountInfo)getObject(index);
    }
    public SpecialDiscountInfo get(Object key)
    {
        return(SpecialDiscountInfo)getObject(key);
    }
    public void set(int index, SpecialDiscountInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SpecialDiscountInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SpecialDiscountInfo item)
    {
        return super.indexOf(item);
    }
}