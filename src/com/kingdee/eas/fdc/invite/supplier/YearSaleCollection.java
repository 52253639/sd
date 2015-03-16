package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class YearSaleCollection extends AbstractObjectCollection 
{
    public YearSaleCollection()
    {
        super(YearSaleInfo.class);
    }
    public boolean add(YearSaleInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(YearSaleCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(YearSaleInfo item)
    {
        return removeObject(item);
    }
    public YearSaleInfo get(int index)
    {
        return(YearSaleInfo)getObject(index);
    }
    public YearSaleInfo get(Object key)
    {
        return(YearSaleInfo)getObject(key);
    }
    public void set(int index, YearSaleInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(YearSaleInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(YearSaleInfo item)
    {
        return super.indexOf(item);
    }
}