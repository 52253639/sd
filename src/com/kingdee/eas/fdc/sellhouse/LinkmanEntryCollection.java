package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class LinkmanEntryCollection extends AbstractObjectCollection 
{
    public LinkmanEntryCollection()
    {
        super(LinkmanEntryInfo.class);
    }
    public boolean add(LinkmanEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(LinkmanEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(LinkmanEntryInfo item)
    {
        return removeObject(item);
    }
    public LinkmanEntryInfo get(int index)
    {
        return(LinkmanEntryInfo)getObject(index);
    }
    public LinkmanEntryInfo get(Object key)
    {
        return(LinkmanEntryInfo)getObject(key);
    }
    public void set(int index, LinkmanEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(LinkmanEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(LinkmanEntryInfo item)
    {
        return super.indexOf(item);
    }
}