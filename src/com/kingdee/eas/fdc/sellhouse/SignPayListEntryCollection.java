package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SignPayListEntryCollection extends AbstractObjectCollection 
{
    public SignPayListEntryCollection()
    {
        super(SignPayListEntryInfo.class);
    }
    public boolean add(SignPayListEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SignPayListEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SignPayListEntryInfo item)
    {
        return removeObject(item);
    }
    public SignPayListEntryInfo get(int index)
    {
        return(SignPayListEntryInfo)getObject(index);
    }
    public SignPayListEntryInfo get(Object key)
    {
        return(SignPayListEntryInfo)getObject(key);
    }
    public void set(int index, SignPayListEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SignPayListEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SignPayListEntryInfo item)
    {
        return super.indexOf(item);
    }
}