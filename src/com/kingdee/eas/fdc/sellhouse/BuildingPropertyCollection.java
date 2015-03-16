package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildingPropertyCollection extends AbstractObjectCollection 
{
    public BuildingPropertyCollection()
    {
        super(BuildingPropertyInfo.class);
    }
    public boolean add(BuildingPropertyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildingPropertyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildingPropertyInfo item)
    {
        return removeObject(item);
    }
    public BuildingPropertyInfo get(int index)
    {
        return(BuildingPropertyInfo)getObject(index);
    }
    public BuildingPropertyInfo get(Object key)
    {
        return(BuildingPropertyInfo)getObject(key);
    }
    public void set(int index, BuildingPropertyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildingPropertyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildingPropertyInfo item)
    {
        return super.indexOf(item);
    }
}