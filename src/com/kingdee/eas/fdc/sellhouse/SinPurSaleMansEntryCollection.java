package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SinPurSaleMansEntryCollection extends AbstractObjectCollection 
{
    public SinPurSaleMansEntryCollection()
    {
        super(SinPurSaleMansEntryInfo.class);
    }
    public boolean add(SinPurSaleMansEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SinPurSaleMansEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SinPurSaleMansEntryInfo item)
    {
        return removeObject(item);
    }
    public SinPurSaleMansEntryInfo get(int index)
    {
        return(SinPurSaleMansEntryInfo)getObject(index);
    }
    public SinPurSaleMansEntryInfo get(Object key)
    {
        return(SinPurSaleMansEntryInfo)getObject(key);
    }
    public void set(int index, SinPurSaleMansEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SinPurSaleMansEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SinPurSaleMansEntryInfo item)
    {
        return super.indexOf(item);
    }
}