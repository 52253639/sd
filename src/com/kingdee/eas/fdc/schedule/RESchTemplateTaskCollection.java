package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RESchTemplateTaskCollection extends AbstractObjectCollection 
{
    public RESchTemplateTaskCollection()
    {
        super(RESchTemplateTaskInfo.class);
    }
    public boolean add(RESchTemplateTaskInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RESchTemplateTaskCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RESchTemplateTaskInfo item)
    {
        return removeObject(item);
    }
    public RESchTemplateTaskInfo get(int index)
    {
        return(RESchTemplateTaskInfo)getObject(index);
    }
    public RESchTemplateTaskInfo get(Object key)
    {
        return(RESchTemplateTaskInfo)getObject(key);
    }
    public void set(int index, RESchTemplateTaskInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RESchTemplateTaskInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RESchTemplateTaskInfo item)
    {
        return super.indexOf(item);
    }
}