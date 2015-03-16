package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PageHeadCollection extends AbstractObjectCollection 
{
    public PageHeadCollection()
    {
        super(PageHeadInfo.class);
    }
    public boolean add(PageHeadInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PageHeadCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PageHeadInfo item)
    {
        return removeObject(item);
    }
    public PageHeadInfo get(int index)
    {
        return(PageHeadInfo)getObject(index);
    }
    public PageHeadInfo get(Object key)
    {
        return(PageHeadInfo)getObject(key);
    }
    public void set(int index, PageHeadInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PageHeadInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PageHeadInfo item)
    {
        return super.indexOf(item);
    }
}