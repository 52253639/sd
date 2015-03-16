package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WebMarkSchemaCollection extends AbstractObjectCollection 
{
    public WebMarkSchemaCollection()
    {
        super(WebMarkSchemaInfo.class);
    }
    public boolean add(WebMarkSchemaInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WebMarkSchemaCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WebMarkSchemaInfo item)
    {
        return removeObject(item);
    }
    public WebMarkSchemaInfo get(int index)
    {
        return(WebMarkSchemaInfo)getObject(index);
    }
    public WebMarkSchemaInfo get(Object key)
    {
        return(WebMarkSchemaInfo)getObject(key);
    }
    public void set(int index, WebMarkSchemaInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WebMarkSchemaInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WebMarkSchemaInfo item)
    {
        return super.indexOf(item);
    }
}