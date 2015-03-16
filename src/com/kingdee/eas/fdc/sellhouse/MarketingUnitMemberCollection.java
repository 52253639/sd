package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MarketingUnitMemberCollection extends AbstractObjectCollection 
{
    public MarketingUnitMemberCollection()
    {
        super(MarketingUnitMemberInfo.class);
    }
    public boolean add(MarketingUnitMemberInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MarketingUnitMemberCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MarketingUnitMemberInfo item)
    {
        return removeObject(item);
    }
    public MarketingUnitMemberInfo get(int index)
    {
        return(MarketingUnitMemberInfo)getObject(index);
    }
    public MarketingUnitMemberInfo get(Object key)
    {
        return(MarketingUnitMemberInfo)getObject(key);
    }
    public void set(int index, MarketingUnitMemberInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MarketingUnitMemberInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MarketingUnitMemberInfo item)
    {
        return super.indexOf(item);
    }
}