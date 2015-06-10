package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NewPlanIndexPTCollection extends AbstractObjectCollection 
{
    public NewPlanIndexPTCollection()
    {
        super(NewPlanIndexPTInfo.class);
    }
    public boolean add(NewPlanIndexPTInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NewPlanIndexPTCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NewPlanIndexPTInfo item)
    {
        return removeObject(item);
    }
    public NewPlanIndexPTInfo get(int index)
    {
        return(NewPlanIndexPTInfo)getObject(index);
    }
    public NewPlanIndexPTInfo get(Object key)
    {
        return(NewPlanIndexPTInfo)getObject(key);
    }
    public void set(int index, NewPlanIndexPTInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NewPlanIndexPTInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NewPlanIndexPTInfo item)
    {
        return super.indexOf(item);
    }
}