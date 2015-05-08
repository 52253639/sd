package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TemplateTaskWithBizTypeCollection extends AbstractObjectCollection 
{
    public TemplateTaskWithBizTypeCollection()
    {
        super(TemplateTaskWithBizTypeInfo.class);
    }
    public boolean add(TemplateTaskWithBizTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TemplateTaskWithBizTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TemplateTaskWithBizTypeInfo item)
    {
        return removeObject(item);
    }
    public TemplateTaskWithBizTypeInfo get(int index)
    {
        return(TemplateTaskWithBizTypeInfo)getObject(index);
    }
    public TemplateTaskWithBizTypeInfo get(Object key)
    {
        return(TemplateTaskWithBizTypeInfo)getObject(key);
    }
    public void set(int index, TemplateTaskWithBizTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TemplateTaskWithBizTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TemplateTaskWithBizTypeInfo item)
    {
        return super.indexOf(item);
    }
}