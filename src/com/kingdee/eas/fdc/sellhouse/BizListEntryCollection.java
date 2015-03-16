package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BizListEntryCollection extends AbstractObjectCollection 
{
    public BizListEntryCollection()
    {
        super(BizListEntryInfo.class);
    }
    public boolean add(BizListEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BizListEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BizListEntryInfo item)
    {
        return removeObject(item);
    }
    public BizListEntryInfo get(int index)
    {
        return(BizListEntryInfo)getObject(index);
    }
    public BizListEntryInfo get(Object key)
    {
        return(BizListEntryInfo)getObject(key);
    }
    public void set(int index, BizListEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BizListEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BizListEntryInfo item)
    {
        return super.indexOf(item);
    }
}