package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BuildingCompensateCollection extends AbstractObjectCollection 
{
    public BuildingCompensateCollection()
    {
        super(BuildingCompensateInfo.class);
    }
    public boolean add(BuildingCompensateInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BuildingCompensateCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BuildingCompensateInfo item)
    {
        return removeObject(item);
    }
    public BuildingCompensateInfo get(int index)
    {
        return(BuildingCompensateInfo)getObject(index);
    }
    public BuildingCompensateInfo get(Object key)
    {
        return(BuildingCompensateInfo)getObject(key);
    }
    public void set(int index, BuildingCompensateInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BuildingCompensateInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BuildingCompensateInfo item)
    {
        return super.indexOf(item);
    }
}