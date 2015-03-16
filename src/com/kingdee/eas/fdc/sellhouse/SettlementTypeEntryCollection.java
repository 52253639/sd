package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SettlementTypeEntryCollection extends AbstractObjectCollection 
{
    public SettlementTypeEntryCollection()
    {
        super(SettlementTypeEntryInfo.class);
    }
    public boolean add(SettlementTypeEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SettlementTypeEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SettlementTypeEntryInfo item)
    {
        return removeObject(item);
    }
    public SettlementTypeEntryInfo get(int index)
    {
        return(SettlementTypeEntryInfo)getObject(index);
    }
    public SettlementTypeEntryInfo get(Object key)
    {
        return(SettlementTypeEntryInfo)getObject(key);
    }
    public void set(int index, SettlementTypeEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SettlementTypeEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SettlementTypeEntryInfo item)
    {
        return super.indexOf(item);
    }
}