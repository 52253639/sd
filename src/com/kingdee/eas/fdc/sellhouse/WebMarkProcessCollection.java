package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WebMarkProcessCollection extends AbstractObjectCollection 
{
    public WebMarkProcessCollection()
    {
        super(WebMarkProcessInfo.class);
    }
    public boolean add(WebMarkProcessInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WebMarkProcessCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WebMarkProcessInfo item)
    {
        return removeObject(item);
    }
    public WebMarkProcessInfo get(int index)
    {
        return(WebMarkProcessInfo)getObject(index);
    }
    public WebMarkProcessInfo get(Object key)
    {
        return(WebMarkProcessInfo)getObject(key);
    }
    public void set(int index, WebMarkProcessInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WebMarkProcessInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WebMarkProcessInfo item)
    {
        return super.indexOf(item);
    }
}