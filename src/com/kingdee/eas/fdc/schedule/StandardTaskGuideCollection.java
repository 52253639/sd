package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class StandardTaskGuideCollection extends AbstractObjectCollection 
{
    public StandardTaskGuideCollection()
    {
        super(StandardTaskGuideInfo.class);
    }
    public boolean add(StandardTaskGuideInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(StandardTaskGuideCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(StandardTaskGuideInfo item)
    {
        return removeObject(item);
    }
    public StandardTaskGuideInfo get(int index)
    {
        return(StandardTaskGuideInfo)getObject(index);
    }
    public StandardTaskGuideInfo get(Object key)
    {
        return(StandardTaskGuideInfo)getObject(key);
    }
    public void set(int index, StandardTaskGuideInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(StandardTaskGuideInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(StandardTaskGuideInfo item)
    {
        return super.indexOf(item);
    }
}