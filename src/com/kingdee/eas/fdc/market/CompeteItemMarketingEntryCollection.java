package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CompeteItemMarketingEntryCollection extends AbstractObjectCollection 
{
    public CompeteItemMarketingEntryCollection()
    {
        super(CompeteItemMarketingEntryInfo.class);
    }
    public boolean add(CompeteItemMarketingEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CompeteItemMarketingEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CompeteItemMarketingEntryInfo item)
    {
        return removeObject(item);
    }
    public CompeteItemMarketingEntryInfo get(int index)
    {
        return(CompeteItemMarketingEntryInfo)getObject(index);
    }
    public CompeteItemMarketingEntryInfo get(Object key)
    {
        return(CompeteItemMarketingEntryInfo)getObject(key);
    }
    public void set(int index, CompeteItemMarketingEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CompeteItemMarketingEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CompeteItemMarketingEntryInfo item)
    {
        return super.indexOf(item);
    }
}