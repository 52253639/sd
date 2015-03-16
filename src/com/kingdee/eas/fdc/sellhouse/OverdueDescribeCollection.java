package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class OverdueDescribeCollection extends AbstractObjectCollection 
{
    public OverdueDescribeCollection()
    {
        super(OverdueDescribeInfo.class);
    }
    public boolean add(OverdueDescribeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(OverdueDescribeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(OverdueDescribeInfo item)
    {
        return removeObject(item);
    }
    public OverdueDescribeInfo get(int index)
    {
        return(OverdueDescribeInfo)getObject(index);
    }
    public OverdueDescribeInfo get(Object key)
    {
        return(OverdueDescribeInfo)getObject(key);
    }
    public void set(int index, OverdueDescribeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(OverdueDescribeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(OverdueDescribeInfo item)
    {
        return super.indexOf(item);
    }
}