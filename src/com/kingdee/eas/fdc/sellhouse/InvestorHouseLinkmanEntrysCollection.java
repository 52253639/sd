package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InvestorHouseLinkmanEntrysCollection extends AbstractObjectCollection 
{
    public InvestorHouseLinkmanEntrysCollection()
    {
        super(InvestorHouseLinkmanEntrysInfo.class);
    }
    public boolean add(InvestorHouseLinkmanEntrysInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InvestorHouseLinkmanEntrysCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InvestorHouseLinkmanEntrysInfo item)
    {
        return removeObject(item);
    }
    public InvestorHouseLinkmanEntrysInfo get(int index)
    {
        return(InvestorHouseLinkmanEntrysInfo)getObject(index);
    }
    public InvestorHouseLinkmanEntrysInfo get(Object key)
    {
        return(InvestorHouseLinkmanEntrysInfo)getObject(key);
    }
    public void set(int index, InvestorHouseLinkmanEntrysInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InvestorHouseLinkmanEntrysInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InvestorHouseLinkmanEntrysInfo item)
    {
        return super.indexOf(item);
    }
}