package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FamillyEarningCollection extends AbstractObjectCollection 
{
    public FamillyEarningCollection()
    {
        super(FamillyEarningInfo.class);
    }
    public boolean add(FamillyEarningInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FamillyEarningCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FamillyEarningInfo item)
    {
        return removeObject(item);
    }
    public FamillyEarningInfo get(int index)
    {
        return(FamillyEarningInfo)getObject(index);
    }
    public FamillyEarningInfo get(Object key)
    {
        return(FamillyEarningInfo)getObject(key);
    }
    public void set(int index, FamillyEarningInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FamillyEarningInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FamillyEarningInfo item)
    {
        return super.indexOf(item);
    }
}