package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TemplateFileCategoryCollection extends AbstractObjectCollection 
{
    public TemplateFileCategoryCollection()
    {
        super(TemplateFileCategoryInfo.class);
    }
    public boolean add(TemplateFileCategoryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TemplateFileCategoryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TemplateFileCategoryInfo item)
    {
        return removeObject(item);
    }
    public TemplateFileCategoryInfo get(int index)
    {
        return(TemplateFileCategoryInfo)getObject(index);
    }
    public TemplateFileCategoryInfo get(Object key)
    {
        return(TemplateFileCategoryInfo)getObject(key);
    }
    public void set(int index, TemplateFileCategoryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TemplateFileCategoryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TemplateFileCategoryInfo item)
    {
        return super.indexOf(item);
    }
}