package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SignSaleManEntryCollection extends AbstractObjectCollection 
{
    public SignSaleManEntryCollection()
    {
        super(SignSaleManEntryInfo.class);
    }
    public boolean add(SignSaleManEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SignSaleManEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SignSaleManEntryInfo item)
    {
        return removeObject(item);
    }
    public SignSaleManEntryInfo get(int index)
    {
        return(SignSaleManEntryInfo)getObject(index);
    }
    public SignSaleManEntryInfo get(Object key)
    {
        return(SignSaleManEntryInfo)getObject(key);
    }
    public void set(int index, SignSaleManEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SignSaleManEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SignSaleManEntryInfo item)
    {
        return super.indexOf(item);
    }
}