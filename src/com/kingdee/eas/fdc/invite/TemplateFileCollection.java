package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TemplateFileCollection extends AbstractObjectCollection 
{
    public TemplateFileCollection()
    {
        super(TemplateFileInfo.class);
    }
    public boolean add(TemplateFileInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TemplateFileCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TemplateFileInfo item)
    {
        return removeObject(item);
    }
    public TemplateFileInfo get(int index)
    {
        return(TemplateFileInfo)getObject(index);
    }
    public TemplateFileInfo get(Object key)
    {
        return(TemplateFileInfo)getObject(key);
    }
    public void set(int index, TemplateFileInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TemplateFileInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TemplateFileInfo item)
    {
        return super.indexOf(item);
    }
}