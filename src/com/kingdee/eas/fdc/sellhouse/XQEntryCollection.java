package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class XQEntryCollection extends AbstractObjectCollection 
{
    public XQEntryCollection()
    {
        super(XQEntryInfo.class);
    }
    public boolean add(XQEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(XQEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(XQEntryInfo item)
    {
        return removeObject(item);
    }
    public XQEntryInfo get(int index)
    {
        return(XQEntryInfo)getObject(index);
    }
    public XQEntryInfo get(Object key)
    {
        return(XQEntryInfo)getObject(key);
    }
    public void set(int index, XQEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(XQEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(XQEntryInfo item)
    {
        return super.indexOf(item);
    }
}