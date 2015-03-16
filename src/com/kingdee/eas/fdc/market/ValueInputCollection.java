package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ValueInputCollection extends AbstractObjectCollection 
{
    public ValueInputCollection()
    {
        super(ValueInputInfo.class);
    }
    public boolean add(ValueInputInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ValueInputCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ValueInputInfo item)
    {
        return removeObject(item);
    }
    public ValueInputInfo get(int index)
    {
        return(ValueInputInfo)getObject(index);
    }
    public ValueInputInfo get(Object key)
    {
        return(ValueInputInfo)getObject(key);
    }
    public void set(int index, ValueInputInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ValueInputInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ValueInputInfo item)
    {
        return super.indexOf(item);
    }
}