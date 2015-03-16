package com.kingdee.eas.fdc.market;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CompetePriceRecordCollection extends AbstractObjectCollection 
{
    public CompetePriceRecordCollection()
    {
        super(CompetePriceRecordInfo.class);
    }
    public boolean add(CompetePriceRecordInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CompetePriceRecordCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CompetePriceRecordInfo item)
    {
        return removeObject(item);
    }
    public CompetePriceRecordInfo get(int index)
    {
        return(CompetePriceRecordInfo)getObject(index);
    }
    public CompetePriceRecordInfo get(Object key)
    {
        return(CompetePriceRecordInfo)getObject(key);
    }
    public void set(int index, CompetePriceRecordInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CompetePriceRecordInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CompetePriceRecordInfo item)
    {
        return super.indexOf(item);
    }
}