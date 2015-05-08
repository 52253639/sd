package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RESchTemplateTaskPredecessorCollection extends AbstractObjectCollection 
{
    public RESchTemplateTaskPredecessorCollection()
    {
        super(RESchTemplateTaskPredecessorInfo.class);
    }
    public boolean add(RESchTemplateTaskPredecessorInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RESchTemplateTaskPredecessorCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RESchTemplateTaskPredecessorInfo item)
    {
        return removeObject(item);
    }
    public RESchTemplateTaskPredecessorInfo get(int index)
    {
        return(RESchTemplateTaskPredecessorInfo)getObject(index);
    }
    public RESchTemplateTaskPredecessorInfo get(Object key)
    {
        return(RESchTemplateTaskPredecessorInfo)getObject(key);
    }
    public void set(int index, RESchTemplateTaskPredecessorInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RESchTemplateTaskPredecessorInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RESchTemplateTaskPredecessorInfo item)
    {
        return super.indexOf(item);
    }
}