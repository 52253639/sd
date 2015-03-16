package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ExpertCollection extends AbstractObjectCollection 
{
    public ExpertCollection()
    {
        super(ExpertInfo.class);
    }
    public boolean add(ExpertInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ExpertCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ExpertInfo item)
    {
        return removeObject(item);
    }
    public ExpertInfo get(int index)
    {
        return(ExpertInfo)getObject(index);
    }
    public ExpertInfo get(Object key)
    {
        return(ExpertInfo)getObject(key);
    }
    public void set(int index, ExpertInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ExpertInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ExpertInfo item)
    {
        return super.indexOf(item);
    }
}