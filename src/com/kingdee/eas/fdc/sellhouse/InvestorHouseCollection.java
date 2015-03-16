package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InvestorHouseCollection extends AbstractObjectCollection 
{
    public InvestorHouseCollection()
    {
        super(InvestorHouseInfo.class);
    }
    public boolean add(InvestorHouseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InvestorHouseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InvestorHouseInfo item)
    {
        return removeObject(item);
    }
    public InvestorHouseInfo get(int index)
    {
        return(InvestorHouseInfo)getObject(index);
    }
    public InvestorHouseInfo get(Object key)
    {
        return(InvestorHouseInfo)getObject(key);
    }
    public void set(int index, InvestorHouseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InvestorHouseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InvestorHouseInfo item)
    {
        return super.indexOf(item);
    }
}