package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SellProjectCollection extends AbstractObjectCollection 
{
    public SellProjectCollection()
    {
        super(SellProjectInfo.class);
    }
    public boolean add(SellProjectInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SellProjectCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SellProjectInfo item)
    {
        return removeObject(item);
    }
    public SellProjectInfo get(int index)
    {
        return(SellProjectInfo)getObject(index);
    }
    public SellProjectInfo get(Object key)
    {
        return(SellProjectInfo)getObject(key);
    }
    public void set(int index, SellProjectInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SellProjectInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SellProjectInfo item)
    {
        return super.indexOf(item);
    }
}