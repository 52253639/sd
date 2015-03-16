package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SettledMonthlyCollection extends AbstractObjectCollection 
{
    public SettledMonthlyCollection()
    {
        super(SettledMonthlyInfo.class);
    }
    public boolean add(SettledMonthlyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SettledMonthlyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SettledMonthlyInfo item)
    {
        return removeObject(item);
    }
    public SettledMonthlyInfo get(int index)
    {
        return(SettledMonthlyInfo)getObject(index);
    }
    public SettledMonthlyInfo get(Object key)
    {
        return(SettledMonthlyInfo)getObject(key);
    }
    public void set(int index, SettledMonthlyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SettledMonthlyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SettledMonthlyInfo item)
    {
        return super.indexOf(item);
    }
}