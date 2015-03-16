package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AppraiseTemplateCollection extends AbstractObjectCollection 
{
    public AppraiseTemplateCollection()
    {
        super(AppraiseTemplateInfo.class);
    }
    public boolean add(AppraiseTemplateInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AppraiseTemplateCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AppraiseTemplateInfo item)
    {
        return removeObject(item);
    }
    public AppraiseTemplateInfo get(int index)
    {
        return(AppraiseTemplateInfo)getObject(index);
    }
    public AppraiseTemplateInfo get(Object key)
    {
        return(AppraiseTemplateInfo)getObject(key);
    }
    public void set(int index, AppraiseTemplateInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AppraiseTemplateInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AppraiseTemplateInfo item)
    {
        return super.indexOf(item);
    }
}