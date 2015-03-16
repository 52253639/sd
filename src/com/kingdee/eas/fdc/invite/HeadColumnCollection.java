package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class HeadColumnCollection extends AbstractObjectCollection 
{
    public HeadColumnCollection()
    {
        super(HeadColumnInfo.class);
    }
    public boolean add(HeadColumnInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(HeadColumnCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(HeadColumnInfo item)
    {
        return removeObject(item);
    }
    public HeadColumnInfo get(int index)
    {
        return(HeadColumnInfo)getObject(index);
    }
    public HeadColumnInfo get(Object key)
    {
        return(HeadColumnInfo)getObject(key);
    }
    public void set(int index, HeadColumnInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(HeadColumnInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(HeadColumnInfo item)
    {
        return super.indexOf(item);
    }
}