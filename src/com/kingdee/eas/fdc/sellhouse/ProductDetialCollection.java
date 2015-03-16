package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProductDetialCollection extends AbstractObjectCollection 
{
    public ProductDetialCollection()
    {
        super(ProductDetialInfo.class);
    }
    public boolean add(ProductDetialInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProductDetialCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProductDetialInfo item)
    {
        return removeObject(item);
    }
    public ProductDetialInfo get(int index)
    {
        return(ProductDetialInfo)getObject(index);
    }
    public ProductDetialInfo get(Object key)
    {
        return(ProductDetialInfo)getObject(key);
    }
    public void set(int index, ProductDetialInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProductDetialInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProductDetialInfo item)
    {
        return super.indexOf(item);
    }
}