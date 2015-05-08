package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RESchTemplateTaskBizTypeCollection extends AbstractObjectCollection 
{
    public RESchTemplateTaskBizTypeCollection()
    {
        super(RESchTemplateTaskBizTypeInfo.class);
    }
    public boolean add(RESchTemplateTaskBizTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RESchTemplateTaskBizTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RESchTemplateTaskBizTypeInfo item)
    {
        return removeObject(item);
    }
    public RESchTemplateTaskBizTypeInfo get(int index)
    {
        return(RESchTemplateTaskBizTypeInfo)getObject(index);
    }
    public RESchTemplateTaskBizTypeInfo get(Object key)
    {
        return(RESchTemplateTaskBizTypeInfo)getObject(key);
    }
    public void set(int index, RESchTemplateTaskBizTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RESchTemplateTaskBizTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RESchTemplateTaskBizTypeInfo item)
    {
        return super.indexOf(item);
    }
}