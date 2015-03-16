package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BasePriceControlBuildingEntryCollection extends AbstractObjectCollection 
{
    public BasePriceControlBuildingEntryCollection()
    {
        super(BasePriceControlBuildingEntryInfo.class);
    }
    public boolean add(BasePriceControlBuildingEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BasePriceControlBuildingEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BasePriceControlBuildingEntryInfo item)
    {
        return removeObject(item);
    }
    public BasePriceControlBuildingEntryInfo get(int index)
    {
        return(BasePriceControlBuildingEntryInfo)getObject(index);
    }
    public BasePriceControlBuildingEntryInfo get(Object key)
    {
        return(BasePriceControlBuildingEntryInfo)getObject(key);
    }
    public void set(int index, BasePriceControlBuildingEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BasePriceControlBuildingEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BasePriceControlBuildingEntryInfo item)
    {
        return super.indexOf(item);
    }
}