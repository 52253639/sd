package com.kingdee.eas.fdc.aimcost;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TemplateNewPlanIndexPTCollection extends AbstractObjectCollection 
{
    public TemplateNewPlanIndexPTCollection()
    {
        super(TemplateNewPlanIndexPTInfo.class);
    }
    public boolean add(TemplateNewPlanIndexPTInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TemplateNewPlanIndexPTCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TemplateNewPlanIndexPTInfo item)
    {
        return removeObject(item);
    }
    public TemplateNewPlanIndexPTInfo get(int index)
    {
        return(TemplateNewPlanIndexPTInfo)getObject(index);
    }
    public TemplateNewPlanIndexPTInfo get(Object key)
    {
        return(TemplateNewPlanIndexPTInfo)getObject(key);
    }
    public void set(int index, TemplateNewPlanIndexPTInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TemplateNewPlanIndexPTInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TemplateNewPlanIndexPTInfo item)
    {
        return super.indexOf(item);
    }
}