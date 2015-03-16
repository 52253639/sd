package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CommerceChanceDataTypeCollection extends AbstractObjectCollection 
{
    public CommerceChanceDataTypeCollection()
    {
        super(CommerceChanceDataTypeInfo.class);
    }
    public boolean add(CommerceChanceDataTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CommerceChanceDataTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CommerceChanceDataTypeInfo item)
    {
        return removeObject(item);
    }
    public CommerceChanceDataTypeInfo get(int index)
    {
        return(CommerceChanceDataTypeInfo)getObject(index);
    }
    public CommerceChanceDataTypeInfo get(Object key)
    {
        return(CommerceChanceDataTypeInfo)getObject(key);
    }
    public void set(int index, CommerceChanceDataTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CommerceChanceDataTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CommerceChanceDataTypeInfo item)
    {
        return super.indexOf(item);
    }
}