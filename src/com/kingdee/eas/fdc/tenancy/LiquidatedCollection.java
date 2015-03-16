package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class LiquidatedCollection extends AbstractObjectCollection 
{
    public LiquidatedCollection()
    {
        super(LiquidatedInfo.class);
    }
    public boolean add(LiquidatedInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(LiquidatedCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(LiquidatedInfo item)
    {
        return removeObject(item);
    }
    public LiquidatedInfo get(int index)
    {
        return(LiquidatedInfo)getObject(index);
    }
    public LiquidatedInfo get(Object key)
    {
        return(LiquidatedInfo)getObject(key);
    }
    public void set(int index, LiquidatedInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(LiquidatedInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(LiquidatedInfo item)
    {
        return super.indexOf(item);
    }
}