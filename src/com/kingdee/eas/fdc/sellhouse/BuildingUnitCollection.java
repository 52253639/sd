package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildingUnitCollection extends AbstractObjectCollection 
{
    public BuildingUnitCollection()
    {
        super(BuildingUnitInfo.class);
    }
    public boolean add(BuildingUnitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildingUnitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildingUnitInfo item)
    {
        return removeObject(item);
    }
    public BuildingUnitInfo get(int index)
    {
        return(BuildingUnitInfo)getObject(index);
    }
    public BuildingUnitInfo get(Object key)
    {
        return(BuildingUnitInfo)getObject(key);
    }
    public void set(int index, BuildingUnitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildingUnitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildingUnitInfo item)
    {
        return super.indexOf(item);
    }
}