package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class LiquidatedManageCollection extends AbstractObjectCollection 
{
    public LiquidatedManageCollection()
    {
        super(LiquidatedManageInfo.class);
    }
    public boolean add(LiquidatedManageInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(LiquidatedManageCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(LiquidatedManageInfo item)
    {
        return removeObject(item);
    }
    public LiquidatedManageInfo get(int index)
    {
        return(LiquidatedManageInfo)getObject(index);
    }
    public LiquidatedManageInfo get(Object key)
    {
        return(LiquidatedManageInfo)getObject(key);
    }
    public void set(int index, LiquidatedManageInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(LiquidatedManageInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(LiquidatedManageInfo item)
    {
        return super.indexOf(item);
    }
}