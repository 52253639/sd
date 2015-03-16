package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RefPriceCollection extends AbstractObjectCollection 
{
    public RefPriceCollection()
    {
        super(RefPriceInfo.class);
    }
    public boolean add(RefPriceInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RefPriceCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RefPriceInfo item)
    {
        return removeObject(item);
    }
    public RefPriceInfo get(int index)
    {
        return(RefPriceInfo)getObject(index);
    }
    public RefPriceInfo get(Object key)
    {
        return(RefPriceInfo)getObject(key);
    }
    public void set(int index, RefPriceInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RefPriceInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RefPriceInfo item)
    {
        return super.indexOf(item);
    }
}