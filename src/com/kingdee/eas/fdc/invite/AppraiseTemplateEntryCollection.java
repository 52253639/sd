package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AppraiseTemplateEntryCollection extends AbstractObjectCollection 
{
    public AppraiseTemplateEntryCollection()
    {
        super(AppraiseTemplateEntryInfo.class);
    }
    public boolean add(AppraiseTemplateEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AppraiseTemplateEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AppraiseTemplateEntryInfo item)
    {
        return removeObject(item);
    }
    public AppraiseTemplateEntryInfo get(int index)
    {
        return(AppraiseTemplateEntryInfo)getObject(index);
    }
    public AppraiseTemplateEntryInfo get(Object key)
    {
        return(AppraiseTemplateEntryInfo)getObject(key);
    }
    public void set(int index, AppraiseTemplateEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AppraiseTemplateEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AppraiseTemplateEntryInfo item)
    {
        return super.indexOf(item);
    }
}