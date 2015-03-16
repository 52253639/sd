package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SaleBlanceSetCollection extends AbstractObjectCollection 
{
    public SaleBlanceSetCollection()
    {
        super(SaleBlanceSetInfo.class);
    }
    public boolean add(SaleBlanceSetInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SaleBlanceSetCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SaleBlanceSetInfo item)
    {
        return removeObject(item);
    }
    public SaleBlanceSetInfo get(int index)
    {
        return(SaleBlanceSetInfo)getObject(index);
    }
    public SaleBlanceSetInfo get(Object key)
    {
        return(SaleBlanceSetInfo)getObject(key);
    }
    public void set(int index, SaleBlanceSetInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SaleBlanceSetInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SaleBlanceSetInfo item)
    {
        return super.indexOf(item);
    }
}