package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SellOrderCollection extends AbstractObjectCollection 
{
    public SellOrderCollection()
    {
        super(SellOrderInfo.class);
    }
    public boolean add(SellOrderInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SellOrderCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SellOrderInfo item)
    {
        return removeObject(item);
    }
    public SellOrderInfo get(int index)
    {
        return(SellOrderInfo)getObject(index);
    }
    public SellOrderInfo get(Object key)
    {
        return(SellOrderInfo)getObject(key);
    }
    public void set(int index, SellOrderInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SellOrderInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SellOrderInfo item)
    {
        return super.indexOf(item);
    }
}