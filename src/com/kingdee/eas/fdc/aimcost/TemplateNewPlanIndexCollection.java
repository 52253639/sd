package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TemplateNewPlanIndexCollection extends AbstractObjectCollection 
{
    public TemplateNewPlanIndexCollection()
    {
        super(TemplateNewPlanIndexInfo.class);
    }
    public boolean add(TemplateNewPlanIndexInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TemplateNewPlanIndexCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TemplateNewPlanIndexInfo item)
    {
        return removeObject(item);
    }
    public TemplateNewPlanIndexInfo get(int index)
    {
        return(TemplateNewPlanIndexInfo)getObject(index);
    }
    public TemplateNewPlanIndexInfo get(Object key)
    {
        return(TemplateNewPlanIndexInfo)getObject(key);
    }
    public void set(int index, TemplateNewPlanIndexInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TemplateNewPlanIndexInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TemplateNewPlanIndexInfo item)
    {
        return super.indexOf(item);
    }
}