package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ChargeTypeCollection extends AbstractObjectCollection 
{
    public ChargeTypeCollection()
    {
        super(ChargeTypeInfo.class);
    }
    public boolean add(ChargeTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ChargeTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ChargeTypeInfo item)
    {
        return removeObject(item);
    }
    public ChargeTypeInfo get(int index)
    {
        return(ChargeTypeInfo)getObject(index);
    }
    public ChargeTypeInfo get(Object key)
    {
        return(ChargeTypeInfo)getObject(key);
    }
    public void set(int index, ChargeTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ChargeTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ChargeTypeInfo item)
    {
        return super.indexOf(item);
    }
}