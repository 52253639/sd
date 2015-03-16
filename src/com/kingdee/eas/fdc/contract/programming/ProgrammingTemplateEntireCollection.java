package com.kingdee.eas.fdc.contract.programming;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProgrammingTemplateEntireCollection extends AbstractObjectCollection 
{
    public ProgrammingTemplateEntireCollection()
    {
        super(ProgrammingTemplateEntireInfo.class);
    }
    public boolean add(ProgrammingTemplateEntireInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProgrammingTemplateEntireCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProgrammingTemplateEntireInfo item)
    {
        return removeObject(item);
    }
    public ProgrammingTemplateEntireInfo get(int index)
    {
        return(ProgrammingTemplateEntireInfo)getObject(index);
    }
    public ProgrammingTemplateEntireInfo get(Object key)
    {
        return(ProgrammingTemplateEntireInfo)getObject(key);
    }
    public void set(int index, ProgrammingTemplateEntireInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProgrammingTemplateEntireInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProgrammingTemplateEntireInfo item)
    {
        return super.indexOf(item);
    }
}