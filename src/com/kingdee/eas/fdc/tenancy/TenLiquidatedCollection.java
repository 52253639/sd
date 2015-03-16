package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenLiquidatedCollection extends AbstractObjectCollection 
{
    public TenLiquidatedCollection()
    {
        super(TenLiquidatedInfo.class);
    }
    public boolean add(TenLiquidatedInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenLiquidatedCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenLiquidatedInfo item)
    {
        return removeObject(item);
    }
    public TenLiquidatedInfo get(int index)
    {
        return(TenLiquidatedInfo)getObject(index);
    }
    public TenLiquidatedInfo get(Object key)
    {
        return(TenLiquidatedInfo)getObject(key);
    }
    public void set(int index, TenLiquidatedInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenLiquidatedInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenLiquidatedInfo item)
    {
        return super.indexOf(item);
    }
}