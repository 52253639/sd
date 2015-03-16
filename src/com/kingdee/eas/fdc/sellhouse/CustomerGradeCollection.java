package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CustomerGradeCollection extends AbstractObjectCollection 
{
    public CustomerGradeCollection()
    {
        super(CustomerGradeInfo.class);
    }
    public boolean add(CustomerGradeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CustomerGradeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CustomerGradeInfo item)
    {
        return removeObject(item);
    }
    public CustomerGradeInfo get(int index)
    {
        return(CustomerGradeInfo)getObject(index);
    }
    public CustomerGradeInfo get(Object key)
    {
        return(CustomerGradeInfo)getObject(key);
    }
    public void set(int index, CustomerGradeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CustomerGradeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CustomerGradeInfo item)
    {
        return super.indexOf(item);
    }
}