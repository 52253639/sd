package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TranSaleManEntryCollection extends AbstractObjectCollection 
{
    public TranSaleManEntryCollection()
    {
        super(TranSaleManEntryInfo.class);
    }
    public boolean add(TranSaleManEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TranSaleManEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TranSaleManEntryInfo item)
    {
        return removeObject(item);
    }
    public TranSaleManEntryInfo get(int index)
    {
        return(TranSaleManEntryInfo)getObject(index);
    }
    public TranSaleManEntryInfo get(Object key)
    {
        return(TranSaleManEntryInfo)getObject(key);
    }
    public void set(int index, TranSaleManEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TranSaleManEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TranSaleManEntryInfo item)
    {
        return super.indexOf(item);
    }
}