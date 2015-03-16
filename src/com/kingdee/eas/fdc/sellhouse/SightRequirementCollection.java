package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SightRequirementCollection extends AbstractObjectCollection 
{
    public SightRequirementCollection()
    {
        super(SightRequirementInfo.class);
    }
    public boolean add(SightRequirementInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SightRequirementCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SightRequirementInfo item)
    {
        return removeObject(item);
    }
    public SightRequirementInfo get(int index)
    {
        return(SightRequirementInfo)getObject(index);
    }
    public SightRequirementInfo get(Object key)
    {
        return(SightRequirementInfo)getObject(key);
    }
    public void set(int index, SightRequirementInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SightRequirementInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SightRequirementInfo item)
    {
        return super.indexOf(item);
    }
}