package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InvestorHouseSourceCollection extends AbstractObjectCollection 
{
    public InvestorHouseSourceCollection()
    {
        super(InvestorHouseSourceInfo.class);
    }
    public boolean add(InvestorHouseSourceInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InvestorHouseSourceCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InvestorHouseSourceInfo item)
    {
        return removeObject(item);
    }
    public InvestorHouseSourceInfo get(int index)
    {
        return(InvestorHouseSourceInfo)getObject(index);
    }
    public InvestorHouseSourceInfo get(Object key)
    {
        return(InvestorHouseSourceInfo)getObject(key);
    }
    public void set(int index, InvestorHouseSourceInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InvestorHouseSourceInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InvestorHouseSourceInfo item)
    {
        return super.indexOf(item);
    }
}