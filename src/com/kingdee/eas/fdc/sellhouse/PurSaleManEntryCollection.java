package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurSaleManEntryCollection extends AbstractObjectCollection 
{
    public PurSaleManEntryCollection()
    {
        super(PurSaleManEntryInfo.class);
    }
    public boolean add(PurSaleManEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurSaleManEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurSaleManEntryInfo item)
    {
        return removeObject(item);
    }
    public PurSaleManEntryInfo get(int index)
    {
        return(PurSaleManEntryInfo)getObject(index);
    }
    public PurSaleManEntryInfo get(Object key)
    {
        return(PurSaleManEntryInfo)getObject(key);
    }
    public void set(int index, PurSaleManEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurSaleManEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurSaleManEntryInfo item)
    {
        return super.indexOf(item);
    }
}