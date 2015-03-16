package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InvestorHouseTrackRecordCollection extends AbstractObjectCollection 
{
    public InvestorHouseTrackRecordCollection()
    {
        super(InvestorHouseTrackRecordInfo.class);
    }
    public boolean add(InvestorHouseTrackRecordInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InvestorHouseTrackRecordCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InvestorHouseTrackRecordInfo item)
    {
        return removeObject(item);
    }
    public InvestorHouseTrackRecordInfo get(int index)
    {
        return(InvestorHouseTrackRecordInfo)getObject(index);
    }
    public InvestorHouseTrackRecordInfo get(Object key)
    {
        return(InvestorHouseTrackRecordInfo)getObject(key);
    }
    public void set(int index, InvestorHouseTrackRecordInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InvestorHouseTrackRecordInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InvestorHouseTrackRecordInfo item)
    {
        return super.indexOf(item);
    }
}