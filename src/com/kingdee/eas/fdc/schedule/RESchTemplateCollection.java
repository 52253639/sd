package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RESchTemplateCollection extends AbstractObjectCollection 
{
    public RESchTemplateCollection()
    {
        super(RESchTemplateInfo.class);
    }
    public boolean add(RESchTemplateInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RESchTemplateCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RESchTemplateInfo item)
    {
        return removeObject(item);
    }
    public RESchTemplateInfo get(int index)
    {
        return(RESchTemplateInfo)getObject(index);
    }
    public RESchTemplateInfo get(Object key)
    {
        return(RESchTemplateInfo)getObject(key);
    }
    public void set(int index, RESchTemplateInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RESchTemplateInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RESchTemplateInfo item)
    {
        return super.indexOf(item);
    }
}