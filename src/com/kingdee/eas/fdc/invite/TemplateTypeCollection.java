package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TemplateTypeCollection extends AbstractObjectCollection 
{
    public TemplateTypeCollection()
    {
        super(TemplateTypeInfo.class);
    }
    public boolean add(TemplateTypeInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TemplateTypeCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TemplateTypeInfo item)
    {
        return removeObject(item);
    }
    public TemplateTypeInfo get(int index)
    {
        return(TemplateTypeInfo)getObject(index);
    }
    public TemplateTypeInfo get(Object key)
    {
        return(TemplateTypeInfo)getObject(key);
    }
    public void set(int index, TemplateTypeInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TemplateTypeInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TemplateTypeInfo item)
    {
        return super.indexOf(item);
    }
}