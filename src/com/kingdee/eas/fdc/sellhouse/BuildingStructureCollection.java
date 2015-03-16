package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildingStructureCollection extends AbstractObjectCollection 
{
    public BuildingStructureCollection()
    {
        super(BuildingStructureInfo.class);
    }
    public boolean add(BuildingStructureInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildingStructureCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildingStructureInfo item)
    {
        return removeObject(item);
    }
    public BuildingStructureInfo get(int index)
    {
        return(BuildingStructureInfo)getObject(index);
    }
    public BuildingStructureInfo get(Object key)
    {
        return(BuildingStructureInfo)getObject(key);
    }
    public void set(int index, BuildingStructureInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildingStructureInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildingStructureInfo item)
    {
        return super.indexOf(item);
    }
}