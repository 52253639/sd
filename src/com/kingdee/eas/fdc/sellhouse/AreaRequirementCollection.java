package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AreaRequirementCollection extends AbstractObjectCollection 
{
    public AreaRequirementCollection()
    {
        super(AreaRequirementInfo.class);
    }
    public boolean add(AreaRequirementInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AreaRequirementCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AreaRequirementInfo item)
    {
        return removeObject(item);
    }
    public AreaRequirementInfo get(int index)
    {
        return(AreaRequirementInfo)getObject(index);
    }
    public AreaRequirementInfo get(Object key)
    {
        return(AreaRequirementInfo)getObject(key);
    }
    public void set(int index, AreaRequirementInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AreaRequirementInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AreaRequirementInfo item)
    {
        return super.indexOf(item);
    }
}