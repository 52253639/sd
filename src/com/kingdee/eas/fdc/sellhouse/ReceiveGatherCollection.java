package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ReceiveGatherCollection extends AbstractObjectCollection 
{
    public ReceiveGatherCollection()
    {
        super(ReceiveGatherInfo.class);
    }
    public boolean add(ReceiveGatherInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ReceiveGatherCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ReceiveGatherInfo item)
    {
        return removeObject(item);
    }
    public ReceiveGatherInfo get(int index)
    {
        return(ReceiveGatherInfo)getObject(index);
    }
    public ReceiveGatherInfo get(Object key)
    {
        return(ReceiveGatherInfo)getObject(key);
    }
    public void set(int index, ReceiveGatherInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ReceiveGatherInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ReceiveGatherInfo item)
    {
        return super.indexOf(item);
    }
}