package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProductSettleBillCollection extends AbstractObjectCollection 
{
    public ProductSettleBillCollection()
    {
        super(ProductSettleBillInfo.class);
    }
    public boolean add(ProductSettleBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProductSettleBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProductSettleBillInfo item)
    {
        return removeObject(item);
    }
    public ProductSettleBillInfo get(int index)
    {
        return(ProductSettleBillInfo)getObject(index);
    }
    public ProductSettleBillInfo get(Object key)
    {
        return(ProductSettleBillInfo)getObject(key);
    }
    public void set(int index, ProductSettleBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProductSettleBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProductSettleBillInfo item)
    {
        return super.indexOf(item);
    }
}