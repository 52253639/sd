package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class NewPlanIndexCollection extends AbstractObjectCollection 
{
    public NewPlanIndexCollection()
    {
        super(NewPlanIndexInfo.class);
    }
    public boolean add(NewPlanIndexInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(NewPlanIndexCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(NewPlanIndexInfo item)
    {
        return removeObject(item);
    }
    public NewPlanIndexInfo get(int index)
    {
        return(NewPlanIndexInfo)getObject(index);
    }
    public NewPlanIndexInfo get(Object key)
    {
        return(NewPlanIndexInfo)getObject(key);
    }
    public void set(int index, NewPlanIndexInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(NewPlanIndexInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(NewPlanIndexInfo item)
    {
        return super.indexOf(item);
    }
}