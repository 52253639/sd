package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ExpertAppraiseCollection extends AbstractObjectCollection 
{
    public ExpertAppraiseCollection()
    {
        super(ExpertAppraiseInfo.class);
    }
    public boolean add(ExpertAppraiseInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ExpertAppraiseCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ExpertAppraiseInfo item)
    {
        return removeObject(item);
    }
    public ExpertAppraiseInfo get(int index)
    {
        return(ExpertAppraiseInfo)getObject(index);
    }
    public ExpertAppraiseInfo get(Object key)
    {
        return(ExpertAppraiseInfo)getObject(key);
    }
    public void set(int index, ExpertAppraiseInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ExpertAppraiseInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ExpertAppraiseInfo item)
    {
        return super.indexOf(item);
    }
}