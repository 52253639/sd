package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCMonthTaskEntryCollection extends AbstractObjectCollection 
{
    public FDCMonthTaskEntryCollection()
    {
        super(FDCMonthTaskEntryInfo.class);
    }
    public boolean add(FDCMonthTaskEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCMonthTaskEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCMonthTaskEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCMonthTaskEntryInfo get(int index)
    {
        return(FDCMonthTaskEntryInfo)getObject(index);
    }
    public FDCMonthTaskEntryInfo get(Object key)
    {
        return(FDCMonthTaskEntryInfo)getObject(key);
    }
    public void set(int index, FDCMonthTaskEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCMonthTaskEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCMonthTaskEntryInfo item)
    {
        return super.indexOf(item);
    }
}