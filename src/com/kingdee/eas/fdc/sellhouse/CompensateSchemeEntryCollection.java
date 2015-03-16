package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CompensateSchemeEntryCollection extends AbstractObjectCollection 
{
    public CompensateSchemeEntryCollection()
    {
        super(CompensateSchemeEntryInfo.class);
    }
    public boolean add(CompensateSchemeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CompensateSchemeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CompensateSchemeEntryInfo item)
    {
        return removeObject(item);
    }
    public CompensateSchemeEntryInfo get(int index)
    {
        return(CompensateSchemeEntryInfo)getObject(index);
    }
    public CompensateSchemeEntryInfo get(Object key)
    {
        return(CompensateSchemeEntryInfo)getObject(key);
    }
    public void set(int index, CompensateSchemeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CompensateSchemeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CompensateSchemeEntryInfo item)
    {
        return super.indexOf(item);
    }
}