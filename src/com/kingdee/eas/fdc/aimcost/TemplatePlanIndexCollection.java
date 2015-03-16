package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TemplatePlanIndexCollection extends AbstractObjectCollection 
{
    public TemplatePlanIndexCollection()
    {
        super(TemplatePlanIndexInfo.class);
    }
    public boolean add(TemplatePlanIndexInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TemplatePlanIndexCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TemplatePlanIndexInfo item)
    {
        return removeObject(item);
    }
    public TemplatePlanIndexInfo get(int index)
    {
        return(TemplatePlanIndexInfo)getObject(index);
    }
    public TemplatePlanIndexInfo get(Object key)
    {
        return(TemplatePlanIndexInfo)getObject(key);
    }
    public void set(int index, TemplatePlanIndexInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TemplatePlanIndexInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TemplatePlanIndexInfo item)
    {
        return super.indexOf(item);
    }
}