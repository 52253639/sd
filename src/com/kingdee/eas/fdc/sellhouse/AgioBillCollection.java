package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AgioBillCollection extends AbstractObjectCollection 
{
    public AgioBillCollection()
    {
        super(AgioBillInfo.class);
    }
    public boolean add(AgioBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AgioBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AgioBillInfo item)
    {
        return removeObject(item);
    }
    public AgioBillInfo get(int index)
    {
        return(AgioBillInfo)getObject(index);
    }
    public AgioBillInfo get(Object key)
    {
        return(AgioBillInfo)getObject(key);
    }
    public void set(int index, AgioBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AgioBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AgioBillInfo item)
    {
        return super.indexOf(item);
    }
}