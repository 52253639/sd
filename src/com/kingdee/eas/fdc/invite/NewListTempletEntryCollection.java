package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NewListTempletEntryCollection extends AbstractObjectCollection 
{
    public NewListTempletEntryCollection()
    {
        super(NewListTempletEntryInfo.class);
    }
    public boolean add(NewListTempletEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NewListTempletEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NewListTempletEntryInfo item)
    {
        return removeObject(item);
    }
    public NewListTempletEntryInfo get(int index)
    {
        return(NewListTempletEntryInfo)getObject(index);
    }
    public NewListTempletEntryInfo get(Object key)
    {
        return(NewListTempletEntryInfo)getObject(key);
    }
    public void set(int index, NewListTempletEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NewListTempletEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NewListTempletEntryInfo item)
    {
        return super.indexOf(item);
    }
}