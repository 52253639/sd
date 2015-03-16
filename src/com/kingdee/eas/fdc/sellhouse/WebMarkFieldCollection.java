package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WebMarkFieldCollection extends AbstractObjectCollection 
{
    public WebMarkFieldCollection()
    {
        super(WebMarkFieldInfo.class);
    }
    public boolean add(WebMarkFieldInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WebMarkFieldCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WebMarkFieldInfo item)
    {
        return removeObject(item);
    }
    public WebMarkFieldInfo get(int index)
    {
        return(WebMarkFieldInfo)getObject(index);
    }
    public WebMarkFieldInfo get(Object key)
    {
        return(WebMarkFieldInfo)getObject(key);
    }
    public void set(int index, WebMarkFieldInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WebMarkFieldInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WebMarkFieldInfo item)
    {
        return super.indexOf(item);
    }
}