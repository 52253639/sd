package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SHEFunProductTypeEntryCollection extends AbstractObjectCollection 
{
    public SHEFunProductTypeEntryCollection()
    {
        super(SHEFunProductTypeEntryInfo.class);
    }
    public boolean add(SHEFunProductTypeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SHEFunProductTypeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SHEFunProductTypeEntryInfo item)
    {
        return removeObject(item);
    }
    public SHEFunProductTypeEntryInfo get(int index)
    {
        return(SHEFunProductTypeEntryInfo)getObject(index);
    }
    public SHEFunProductTypeEntryInfo get(Object key)
    {
        return(SHEFunProductTypeEntryInfo)getObject(key);
    }
    public void set(int index, SHEFunProductTypeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SHEFunProductTypeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SHEFunProductTypeEntryInfo item)
    {
        return super.indexOf(item);
    }
}