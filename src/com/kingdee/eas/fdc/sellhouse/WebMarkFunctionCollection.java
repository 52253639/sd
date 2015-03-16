package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class WebMarkFunctionCollection extends AbstractObjectCollection 
{
    public WebMarkFunctionCollection()
    {
        super(WebMarkFunctionInfo.class);
    }
    public boolean add(WebMarkFunctionInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(WebMarkFunctionCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(WebMarkFunctionInfo item)
    {
        return removeObject(item);
    }
    public WebMarkFunctionInfo get(int index)
    {
        return(WebMarkFunctionInfo)getObject(index);
    }
    public WebMarkFunctionInfo get(Object key)
    {
        return(WebMarkFunctionInfo)getObject(key);
    }
    public void set(int index, WebMarkFunctionInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(WebMarkFunctionInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(WebMarkFunctionInfo item)
    {
        return super.indexOf(item);
    }
}