package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class HelpDeptEntryCollection extends AbstractObjectCollection 
{
    public HelpDeptEntryCollection()
    {
        super(HelpDeptEntryInfo.class);
    }
    public boolean add(HelpDeptEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(HelpDeptEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(HelpDeptEntryInfo item)
    {
        return removeObject(item);
    }
    public HelpDeptEntryInfo get(int index)
    {
        return(HelpDeptEntryInfo)getObject(index);
    }
    public HelpDeptEntryInfo get(Object key)
    {
        return(HelpDeptEntryInfo)getObject(key);
    }
    public void set(int index, HelpDeptEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(HelpDeptEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(HelpDeptEntryInfo item)
    {
        return super.indexOf(item);
    }
}