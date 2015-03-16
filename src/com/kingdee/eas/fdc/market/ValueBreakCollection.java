package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ValueBreakCollection extends AbstractObjectCollection 
{
    public ValueBreakCollection()
    {
        super(ValueBreakInfo.class);
    }
    public boolean add(ValueBreakInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ValueBreakCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ValueBreakInfo item)
    {
        return removeObject(item);
    }
    public ValueBreakInfo get(int index)
    {
        return(ValueBreakInfo)getObject(index);
    }
    public ValueBreakInfo get(Object key)
    {
        return(ValueBreakInfo)getObject(key);
    }
    public void set(int index, ValueBreakInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ValueBreakInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ValueBreakInfo item)
    {
        return super.indexOf(item);
    }
}