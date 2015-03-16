package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SettlementgGatheringCollection extends AbstractObjectCollection 
{
    public SettlementgGatheringCollection()
    {
        super(SettlementgGatheringInfo.class);
    }
    public boolean add(SettlementgGatheringInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SettlementgGatheringCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SettlementgGatheringInfo item)
    {
        return removeObject(item);
    }
    public SettlementgGatheringInfo get(int index)
    {
        return(SettlementgGatheringInfo)getObject(index);
    }
    public SettlementgGatheringInfo get(Object key)
    {
        return(SettlementgGatheringInfo)getObject(key);
    }
    public void set(int index, SettlementgGatheringInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SettlementgGatheringInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SettlementgGatheringInfo item)
    {
        return super.indexOf(item);
    }
}