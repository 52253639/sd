package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class HopedDirectionCollection extends AbstractObjectCollection 
{
    public HopedDirectionCollection()
    {
        super(HopedDirectionInfo.class);
    }
    public boolean add(HopedDirectionInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(HopedDirectionCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(HopedDirectionInfo item)
    {
        return removeObject(item);
    }
    public HopedDirectionInfo get(int index)
    {
        return(HopedDirectionInfo)getObject(index);
    }
    public HopedDirectionInfo get(Object key)
    {
        return(HopedDirectionInfo)getObject(key);
    }
    public void set(int index, HopedDirectionInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(HopedDirectionInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(HopedDirectionInfo item)
    {
        return super.indexOf(item);
    }
}