package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCMonthTempTaskEntryCollection extends AbstractObjectCollection 
{
    public FDCMonthTempTaskEntryCollection()
    {
        super(FDCMonthTempTaskEntryInfo.class);
    }
    public boolean add(FDCMonthTempTaskEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCMonthTempTaskEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCMonthTempTaskEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCMonthTempTaskEntryInfo get(int index)
    {
        return(FDCMonthTempTaskEntryInfo)getObject(index);
    }
    public FDCMonthTempTaskEntryInfo get(Object key)
    {
        return(FDCMonthTempTaskEntryInfo)getObject(key);
    }
    public void set(int index, FDCMonthTempTaskEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCMonthTempTaskEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCMonthTempTaskEntryInfo item)
    {
        return super.indexOf(item);
    }
}