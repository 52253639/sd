package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BanBasedataEntryListCollection extends AbstractObjectCollection 
{
    public BanBasedataEntryListCollection()
    {
        super(BanBasedataEntryListInfo.class);
    }
    public boolean add(BanBasedataEntryListInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BanBasedataEntryListCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BanBasedataEntryListInfo item)
    {
        return removeObject(item);
    }
    public BanBasedataEntryListInfo get(int index)
    {
        return(BanBasedataEntryListInfo)getObject(index);
    }
    public BanBasedataEntryListInfo get(Object key)
    {
        return(BanBasedataEntryListInfo)getObject(key);
    }
    public void set(int index, BanBasedataEntryListInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BanBasedataEntryListInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BanBasedataEntryListInfo item)
    {
        return super.indexOf(item);
    }
}