package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProgrammingTemplateCollection extends AbstractObjectCollection 
{
    public ProgrammingTemplateCollection()
    {
        super(ProgrammingTemplateInfo.class);
    }
    public boolean add(ProgrammingTemplateInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProgrammingTemplateCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProgrammingTemplateInfo item)
    {
        return removeObject(item);
    }
    public ProgrammingTemplateInfo get(int index)
    {
        return(ProgrammingTemplateInfo)getObject(index);
    }
    public ProgrammingTemplateInfo get(Object key)
    {
        return(ProgrammingTemplateInfo)getObject(key);
    }
    public void set(int index, ProgrammingTemplateInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProgrammingTemplateInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProgrammingTemplateInfo item)
    {
        return super.indexOf(item);
    }
}