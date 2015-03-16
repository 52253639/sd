package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ExpertQualifyEntryCollection extends AbstractObjectCollection 
{
    public ExpertQualifyEntryCollection()
    {
        super(ExpertQualifyEntryInfo.class);
    }
    public boolean add(ExpertQualifyEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ExpertQualifyEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ExpertQualifyEntryInfo item)
    {
        return removeObject(item);
    }
    public ExpertQualifyEntryInfo get(int index)
    {
        return(ExpertQualifyEntryInfo)getObject(index);
    }
    public ExpertQualifyEntryInfo get(Object key)
    {
        return(ExpertQualifyEntryInfo)getObject(key);
    }
    public void set(int index, ExpertQualifyEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ExpertQualifyEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ExpertQualifyEntryInfo item)
    {
        return super.indexOf(item);
    }
}